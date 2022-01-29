package com.example.testesicredi.rules

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.testesicredi.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@BindingAdapter("app:gone_unless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("loadImage")
fun AppCompatImageView.loadImage(source: String?) {
    source ?: return
    Glide.with(this)
        .load(source)
        .apply(
            RequestOptions.errorOf(R.drawable.not_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        )
        .into(this)
}

@BindingAdapter("eventDate")
fun MaterialTextView.eventDate(date: LocalDateTime?) {
    date ?: return
    val pattern = "dd/MM/yyyy HH:mm"
    text = context.getString(R.string.date_format, date.format(DateTimeFormatter.ofPattern(pattern)))
}

@BindingAdapter("eventDateDetail")
fun MaterialTextView.eventDateDetail(date: LocalDateTime?) {
    date ?: return
    val pattern = "EEEE, dd MMMM yyyy 'as' HH:mm"
    text = context.getString(R.string.date_format, date.format(DateTimeFormatter.ofPattern(pattern)))
}

@BindingAdapter("eventPrice")
fun MaterialTextView.eventPrice(price: Double?) {
    price ?: return
    text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(price)
}

@BindingAdapter("textInputError")
fun TextInputLayout.textInputError(errorInt: Int?) {
    errorInt ?: return
    error = context.getString(errorInt)
}
