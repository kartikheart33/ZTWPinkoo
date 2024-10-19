package com.ztb.pinkoo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.*
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.ztb.pinkoo.BuildConfig
import com.ztb.pinkoo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.Serializable
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.absoluteValue
import kotlin.random.Random


private const val TAG = "Extension"

fun NavController.isOnBackStack(@IdRes id: Int): Boolean = try { getBackStackEntry(id); true } catch (
    e: Throwable
) { false }

fun rand(start: Int, end: Int): Int {
    require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
    return Random(System.nanoTime()).nextInt(end - start + 1) + start
}

fun calculateProgress(uploaded: Int, total: Int): Double {

    val percentage = (uploaded.toDouble() / total) * 100
//        return String.format("%.2f%%", percentage) // Format to two decimal places
    return percentage
}

fun View.setSingleClickListener(onSingleClick: (View) -> Unit) {
    val clickListener = OneClickListener {
        onSingleClick(it)
    }
    setOnClickListener(clickListener)
}

fun View.setSingleClickListener(intervalInMillis: Int, onSingleClick: (View) -> Unit) {
    val clickListener = OneClickListener(intervalInMillis) {
        onSingleClick(it)
    }
    setOnClickListener(clickListener)
}

fun MenuItem.setSingleMenuClickListener(onSingleClick: (MenuItem) -> Unit) {
    val menu = MenuItemOneClickListener {
        onSingleClick(it)
    }
    setOnMenuItemClickListener(menu)
}

fun MenuItem.setSingleMenuClickListener(intervalInMillis: Int, onSingleClick: (MenuItem) -> Unit) {
    val menu = MenuItemOneClickListener {
        onSingleClick(it)
    }
    setOnMenuItemClickListener(menu)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun EditText.getValue(): String {
    return this.text.toString().trim()
}

fun Double.getAmount(): String {
    return NumberFormat.getNumberInstance().format(this)
}

fun TextView.getValue(): String {
    return this.text.toString().trim()
}

fun String.oneOrMore(count: Any?): String {
    if (count == null) {
        return this
    }
    val value = count.toString().toInt()
    return if (value == 0 || value == 1) this else "${this}s"
}

fun EditText.isEmpty(): Boolean {
    return this.text.trim().isEmpty()
}

fun TextView.isEmpty(): Boolean {
    return this.text.trim().isEmpty()
}

fun File.copyTo(file: File) {
    inputStream().use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute() // runs in Main Thread
    val result = withContext(Dispatchers.IO) {
        doInBackground() // runs in background thread without blocking the Main Thread
    }
    onPostExecute(result) // runs in Main Thread
}

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Activity.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun String.isEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPhone() = android.util.Patterns.PHONE.matcher(this).matches()

fun String.isValidMobile(): Boolean {
    return if (!Pattern.matches("[a-zA-Z]+", this)) {
        this.length in 7..12
    } else false
}

fun String.isValidPassword(): Boolean {
    val pattern = Pattern.compile(AppConstants.PASSWORD_REGEX)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}


fun String.getUrlName(): String {
    return this.subSequence(this.lastIndexOf("/") + 1, this.length).toString()
}

fun AppCompatEditText.placeCursorToEnd() {
    this.setSelection(this.getValue().length)
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key, T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

fun ImageView.animatedRotate(currentRotation: Float, clockwise: Boolean = true): Float {
    // 0f here is configurable, you can restart the rotation cycle
    // with your choice of initial angle
    val fromRotation = if (currentRotation.absoluteValue == 360f) 0f else currentRotation
    val rotateDegrees = if (clockwise) 180f else -180f
    val toRotation = (fromRotation + rotateDegrees) % 450f
    Log.e("animatedRotate", "Rotating from $fromRotation to $toRotation")
    val rotateAnimation = RotateAnimation(
        fromRotation, toRotation, width / 2f, height / 2f
    ).apply {
        duration = 400 // configurable
        fillAfter = true
    }
    startAnimation(rotateAnimation)
    return toRotation
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isAcceptingText) imm.hideSoftInputFromWindow(windowToken, 0)
}



fun TextView.setOnTouchListener(setClickListener: () -> Unit) {
    this.setOnTouchListener(
        View.OnTouchListener { _, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= this.right - this.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    setClickListener()
                    return@OnTouchListener true
                }
            }
            false
        }
    )
}

fun uriFromFile(context: Context, file: File): Uri {
    return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
}

fun Context.shareImagePdf(hasImage: Boolean = true, file: File) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    if (hasImage) {
        shareIntent.type = "image/*"
    } else {
        shareIntent.type = "application/pdf"
    }
    val uri = FileProvider.getUriForFile(
        this, BuildConfig.APPLICATION_ID + ".provider", file
    )
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(Intent.createChooser(shareIntent, "Share file"))
}

@SuppressLint("ClickableViewAccessibility")
fun TextView.setDrawableRightTouch(setClickListener: () -> Unit) {
    this.setOnTouchListener(
        View.OnTouchListener { _, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= this.right - this.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    setClickListener()
                    return@OnTouchListener true
                }
            }
            false
        }
    )
}



fun TextView.spannableTextReturn(spanTitle: String?, colorString: String?, termLink: String?) {

    if (spanTitle?.contains("//") == true) {
        Log.d(TAG, "spannableTextReturn: ==>$spanTitle")
        val strikethroughSpan = StrikethroughSpan()
        val foregroundColorSpanRed = ForegroundColorSpan(Color.RED)
        var tempText = spanTitle
        var newSpan = spanTitle.indexOf("//")
        var lastSpan = spanTitle.lastIndexOf("//") - 2
        tempText = tempText.replace("//", "")

        val spannableString = SpannableString(tempText.toString())
        spannableString.setSpan(
            foregroundColorSpanRed,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            strikethroughSpan,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = spannableString
    } else if (spanTitle?.contains("<a>") == true) {

        val old = spanTitle.indexOf("<a>")
        val new = spanTitle.lastIndexOf("<a>") - 3

        var startt = spanTitle.indexOf("<a>") + 3
        var endd = spanTitle.lastIndexOf("<a>")

        var dummytext = spanTitle
        dummytext = dummytext.replace("<a>", "")

        val spannableString = SpannableString(dummytext.toString())
        var clickString: String = spanTitle.substring(startt, endd)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                if (!clickString.startsWith("http://") && !clickString.startsWith("https://")) {
                    clickString = "http://$clickString"
                }
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(clickString))
                context.startActivity(browserIntent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(context, R.color.navy_link)
            }
        }

        spannableString.setSpan(
            clickableSpan,
            old,
            new,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.linksClickable = true
        this.isClickable = true
        this.movementMethod = LinkMovementMethod.getInstance()

        this.text = spannableString
    } else if (spanTitle?.contains("<e>") == true) {

        val old = spanTitle.indexOf("<e>")
        val new = spanTitle.lastIndexOf("<e>") - 3

        var startt = spanTitle.indexOf("<e>") + 3
        var endd = spanTitle.lastIndexOf("<e>")

        var dummytext = spanTitle
        dummytext = dummytext.replace("<e>", "")

        val spannableString = SpannableString(dummytext.toString())
        var clickString: String = spanTitle.substring(startt, endd)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                textView.invalidate()
                try {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.setData(Uri.parse("mailto:"))
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(clickString))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "")
                    context.startActivity(Intent.createChooser(intent, "Send Email"))
                } catch (e: Exception) {
                    Toast.makeText(context, "No Email Apps Found", Toast.LENGTH_SHORT).show()
                }

                /*  if (!clickString.startsWith("http://") && !clickString.startsWith("https://")) {
                      clickString = "http://$clickString"
                  }
                  val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(clickString))
                  context.startActivity(browserIntent)*/
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = ContextCompat.getColor(context, R.color.navy_link)
            }
        }

        spannableString.setSpan(
            clickableSpan,
            old,
            new,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.linksClickable = true
        this.isClickable = true
        this.movementMethod = LinkMovementMethod.getInstance()

        this.text = spannableString
    } else if (spanTitle?.contains("{") == true) {
        var tempText = spanTitle
        val newSpan = spanTitle.indexOf("{")
        val lastSpan = spanTitle.lastIndexOf("}") - 1
        tempText = tempText.replace("{", "").replace("}", "")
        val spannableString = SpannableString(tempText.toString())
        var foregroundColorSpanGray =
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.navy_link))
        val underlineSpan = UnderlineSpan()

        spannableString.setSpan(
            underlineSpan,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpanGray,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.linksClickable = true
        this.isClickable = false
        this.movementMethod = LinkMovementMethod.getInstance()
        this.text = spannableString
    } else if (spanTitle?.contains("(") == true) {
        var tempText = spanTitle
        val newSpan = spanTitle.indexOf("(")
        val lastSpan = spanTitle.lastIndexOf(")") - 1
        tempText = tempText.replace("(", "").replace(")", "")
        val spannableString = SpannableString(tempText.toString())
        var foregroundColorSpanGray =
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_50))
        if (colorString == "") {

            foregroundColorSpanGray =
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.white))
        } else {
            foregroundColorSpanGray =
                ForegroundColorSpan(Color.parseColor(colorString))
        }
        val underlineSpan = UnderlineSpan()

        spannableString.setSpan(
            underlineSpan,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpanGray,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.linksClickable = true
        this.isClickable = false
        this.movementMethod = LinkMovementMethod.getInstance()
        this.text = spannableString
    } else if (spanTitle?.contains("<") == true) {
        var tempText = spanTitle
        val newSpan = spanTitle.indexOf("<")
        val lastSpan = spanTitle.lastIndexOf(">") - 1
        tempText = tempText.replace("<", "").replace(">", "")
        val spannableString = SpannableString(tempText.toString())
        var foregroundColorSpanGray =
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_50))
        if (colorString == "") {

            foregroundColorSpanGray =
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_50))
        } else {
            foregroundColorSpanGray =
                ForegroundColorSpan(Color.parseColor(colorString))
        }

        val underlineSpan = UnderlineSpan()
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        spannableString.setSpan(
            clickableSpan,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            underlineSpan,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpanGray,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.linksClickable = true
        this.isClickable = true
        this.movementMethod = LinkMovementMethod.getInstance()
        this.text = spannableString
    } else if (spanTitle?.contains("!") == true) {
        var tempText = spanTitle
        val newSpan = spanTitle.indexOf("!")
        val lastSpan = spanTitle.lastIndexOf("!") - 1
        tempText = tempText.replace("!", "").replace("!", "")
        val spannableString = SpannableString(tempText.toString())
        var foregroundColorSpanGray =
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_primary))
        if (colorString == "") {

            foregroundColorSpanGray =
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_primary))
        } else {
            foregroundColorSpanGray =
                ForegroundColorSpan(Color.parseColor(colorString))
        }
        val underlineSpan = UnderlineSpan()

        spannableString.setSpan(
            underlineSpan,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpanGray,
            newSpan,
            lastSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.linksClickable = true
        this.isClickable = false
        this.movementMethod = LinkMovementMethod.getInstance()
        this.text = spannableString
    } else {
        this.text = spanTitle
    }
}



fun <T : Serializable?> Intent.getSerializable(
    name: String,
    clazz: Class<T>
): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) this.getSerializableExtra(
        name, clazz
    )!!
    else this.getSerializableExtra(name) as T
}

fun <T : Parcelable> Intent.getParcelable(
    name: String,
    clazz: Class<T>
): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) this.getParcelableExtra(
        name, clazz
    )!!
    else this.getParcelableExtra(name)!!
}

fun String.toBitmap(context: Context, listener: (Bitmap) -> Unit) {
    try {
        Glide.with(context).asBitmap().load(this).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                listener(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String?.getDoubleAmount(): Double {
    val format = NumberFormat.getInstance(Locale.getDefault())
    val number = this?.let { format.parse(it) }
    return number?.toDouble() ?: 0.0
}


fun Activity.setWindowStatusBarColor(color: Int = R.color.theme_grey_40) {
    val window: Window = this.window

    // clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    // finally change the color
    window.statusBarColor = ContextCompat.getColor(this, color)
}
