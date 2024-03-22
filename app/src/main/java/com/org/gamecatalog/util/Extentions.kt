package com.org.gamecatalog.util

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.org.gamecatalog.R

fun Context.showShortToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.dpToPx(dp: Float): Float {
  return TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    this.resources.displayMetrics,
  )
}

fun TextView.clickAble(
  onClick: (text: String) -> Unit
) {
  if(text.isEmpty()) return

  val ssb = SpannableStringBuilder(text)
  ssb.setSpan(
    object : ClickableSpan() {

      override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = true
      }

      override fun onClick(widget: View) {
        onClick(text.toString())
      }
    }, 0, text.length, 0
  )

  text = ssb
  movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.addSuffixAndClickAble(
  suffixText: String? = null,
  maxLine: Int,
  onClick: () -> Unit
) {
  if(lineCount == 0 || lineCount < maxLine || text.isEmpty()) return

  val sf = suffixText ?: ""

  val lineEndIndex = layout.getLineEnd(maxLine - 1)
  val newText = "${text.subSequence(0, lineEndIndex - sf.length + 1)}$sf"

  val ssb = SpannableStringBuilder(newText)

  val statIndex = newText.indexOf(sf)
  val endIndex = statIndex + sf.length
  ssb.setSpan(
    object: ClickableSpan() {
      override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
        ds.color = ContextCompat.getColor(context, R.color.white)
      }
      override fun onClick(widget: View) {
        onClick()
      }
    },
    statIndex,
    endIndex,
    0
  )

  text = ssb
  movementMethod = LinkMovementMethod.getInstance()
}