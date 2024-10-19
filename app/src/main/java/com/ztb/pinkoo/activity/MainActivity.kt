package com.ztb.pinkoo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ztb.pinkoo.Factory.HomeFactory
import com.ztb.pinkoo.R
import com.ztb.pinkoo.Repository.HomeRepository
import com.ztb.pinkoo.ViewModel.HomeViewModel
import com.ztb.pinkoo.adapter.CategoryAdapter
import com.ztb.pinkoo.adapter.UserAdapter
import com.ztb.pinkoo.api.ApiInterface
import com.ztb.pinkoo.databinding.ActivityMainBinding
import com.ztb.pinkoo.models.CategoryModel
import com.ztb.pinkoo.models.UserDataModel
import com.ztb.pinkoo.utils.AppConstants
import com.ztb.pinkoo.utils.AppUtil
import com.ztb.pinkoo.utils.SharedPreferenceUtil

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferencesUtils: SharedPreferenceUtil
    private var appUtil = AppUtil()
    lateinit var viewModel: HomeViewModel
    lateinit var adapter:UserAdapter
    lateinit var catadapter:CategoryAdapter
    var catlist:ArrayList<CategoryModel.Data> = ArrayList()
    var userlist:ArrayList<UserDataModel.Data> = ArrayList()
    lateinit var apiInterface: ApiInterface
    var currentPage = 1
    var type = 0
    var isMoreRecords = true
    private var isLoadMore = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorsecond)

        sharedPreferencesUtils = SharedPreferenceUtil(this)
        apiInterface = ApiInterface.create()
        viewModel = ViewModelProvider(
            this,
            HomeFactory(HomeRepository(apiInterface))
        ).get(HomeViewModel::class.java)


        adapter = UserAdapter(userlist,applicationContext,type)
        binding.userRecyclerView.layoutManager =LinearLayoutManager(applicationContext)
        binding.userRecyclerView.adapter = adapter


        binding.list.setOnClickListener {
            if(type==0){
                type=1
                adapter = UserAdapter(userlist,applicationContext,type)
                binding.userRecyclerView.layoutManager =GridLayoutManager(applicationContext,2)
                binding.userRecyclerView.adapter = adapter
                binding.list.setImageResource(R.drawable.ic_list)
            }else{
                type=0
                adapter = UserAdapter(userlist,applicationContext,type)
                binding.userRecyclerView.layoutManager =LinearLayoutManager(applicationContext)
                binding.userRecyclerView.adapter = adapter
                binding.list.setImageResource(R.drawable.ic_grid)

            }
        }

        binding.grid.setOnClickListener {
            binding.grid.visibility=View.GONE
            binding.list.visibility=View.VISIBLE
            type=1

        }



        catadapter= CategoryAdapter(catlist,applicationContext)
        binding.catRecyclerView.adapter=catadapter


        binding.userRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (recyclerView.layoutManager  == recyclerView.layoutManager as LinearLayoutManager ){
                    val positionView =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    if (positionView == userlist.size - 1) {
                        if (isMoreRecords) {
                            isLoadMore = true
                            currentPage += 1
//                        Toast.makeText(context, "page"+currentPage.toString(), Toast.LENGTH_SHORT).show()

                            if (appUtil.isInternetAvailable(applicationContext)){
                                viewModel.getUserPagination("users?page=$currentPage")
                            }else{
                                appUtil.internetDialog(this@MainActivity, "Please Connect to internet")
                            }
                        }
                    }
                }else{
                    val positionView =
                        (recyclerView.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    if (positionView == userlist.size - 1) {
                        if (isMoreRecords) {
                            isLoadMore = true
                            currentPage += 1
//                        Toast.makeText(context, "page"+currentPage.toString(), Toast.LENGTH_SHORT).show()

                            if (appUtil.isInternetAvailable(applicationContext)){
                                viewModel.getUserPagination("users?page=$currentPage")
                            }else{
                                appUtil.internetDialog(this@MainActivity, "Please Connect to internet")
                            }
                        }
                    }
                }




            }
        })

        callApi()
        setObserver()



    }

    private fun setObserver() {
        viewModel.getcatdetail.observe(this ){
            if (it!=null){

                catlist.addAll(it.data)
                catadapter.notifyDataSetChanged()
                Log.d("TAG", "setObserver=====>"+catlist.size.toString())
            }
        }

        viewModel.getuserpagination.observe(this){
            if (it!=null){

                binding.noproduct.text = it.total.toString()+" Product Found"
                isMoreRecords = currentPage != it.totalPages


                if (isLoadMore) {
                    adapter.addData(it.data)
                    adapter.notifyDataSetChanged()

                } else {

                    userlist.addAll(it.data)

                    adapter.notifyDataSetChanged()

                }

                Log.d("TAG", "setObserver=====>"+userlist.size.toString())
            }
        }





        viewModel.errorMessage.observe(this){
            try{
                appUtil.AlertDialogValidation(this, it.toString())
            }catch (e:Exception){
                appUtil.AlertDialogValidation(this,  "Something went wrong, Please try again")

            }
        }
    }

    private fun callApi() {
        if (appUtil.isInternetAvailable(applicationContext)){
            viewModel.getCategoryDetail()
            viewModel.getUserPagination("users?page=$currentPage")
        }else{
            appUtil.internetDialog(this, "Please Connect to internet")
        }
    }
}