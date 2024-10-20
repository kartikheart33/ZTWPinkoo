package com.ztb.pinkoo.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ztb.pinkoo.R
import com.ztb.pinkoo.databinding.FragmentFirebaseAuthBinding
import com.ztb.pinkoo.utils.AppUtil
import com.ztb.pinkoo.utils.SharedPreferenceUtil


class FirebaseAuthFragment : Fragment() {
    lateinit var binding:FragmentFirebaseAuthBinding
    var apputil: AppUtil = AppUtil()
    var fcm_token: String = ""
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    val Req_Code: Int = 123
    var isUserNew=false
    private lateinit var firebaseAuth: FirebaseAuth
    var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    var email = ""
    var uid = ""
    var photoUrl = ""
    var displayName = ""
    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirebaseAuthBinding.inflate(layoutInflater,container,false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnSignOut.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                findNavController().navigateUp()
            }
        }
        binding.btSignIn.setOnClickListener { view: View? ->
            if (apputil.isInternetAvailable(requireContext())){
                signInGoogle()
            }else{
                apputil.internetDialog(requireActivity(),"Check Your Internet Connection")
            }
        }
        return binding.root
    }


    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user=firebaseAuth.currentUser
                if (user != null) {
                    val userInfoList = user!!.providerData
                    for (i in 0 until userInfoList.size) {
                        email = userInfoList[i].email!!
                        if (email != "") {
                            uid = userInfoList[i].uid
                            displayName = userInfoList[i].displayName!!
                            photoUrl = userInfoList[i].photoUrl.toString()
                            Glide.with(this).load(photoUrl).into(binding.ivProfile)
                            break
                        }
                    }
                    Log.d("TAG", "UpdateUI: "+email.toString())
                }
            }
        }

    }
    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(requireContext()) != null) {
            Toast.makeText(requireContext(), "Already signed in", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val TAG = FirebaseAuthFragment::class.java.name
    }
}