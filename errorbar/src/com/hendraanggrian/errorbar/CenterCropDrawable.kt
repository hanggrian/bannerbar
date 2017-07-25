package com.hendraanggrian.errorbar

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.DrawableRes
import com.hendraanggrian.kota.content.getActualPath

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class CenterCropDrawable(val bitmap: Bitmap) : Drawable() {

    private val mTempRect = Rect()
    private val mBitmapPaint = Paint(ANTI_ALIAS_FLAG)

    constructor(drawable: Drawable) : this(drawable.toBitmap())

    constructor(resources: Resources, @DrawableRes id: Int) : this(BitmapFactory.decodeResource(resources, id))

    constructor(context: Context, uri: Uri) : this(BitmapFactory.decodeFile(uri.getActualPath(context)))

    override fun draw(canvas: Canvas) {
        if (bounds.height() > 0 && bounds.height() > 0) {
            val scale = Math.min(bitmap.width / bounds.width().toFloat(), bitmap.height / bounds.height().toFloat())
            val bitmapVisibleWidth = scale * bounds.width()
            val bitmapVisibleHeight = scale * bounds.height()
            mTempRect.set((bitmap.width - bitmapVisibleWidth).toInt() / 2, 0, (bitmapVisibleWidth + bitmap.width).toInt() / 2, bitmapVisibleHeight.toInt())
            canvas.drawBitmap(bitmap, mTempRect, bounds, mBitmapPaint)
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun getOpacity() = 100

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    companion object {
        private fun Drawable.toBitmap(): Bitmap {
            var bitmap: Bitmap? = null
            if (this is BitmapDrawable) {
                if (bitmap != null) {
                    return bitmap
                }
            }
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            return bitmap
        }
    }
}