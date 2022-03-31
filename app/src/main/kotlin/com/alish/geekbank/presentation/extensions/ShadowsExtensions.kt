package com.alish.geekbank.presentation.extensions

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Color
import android.graphics.SweepGradient
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import java.lang.Math.ceil
import kotlin.math.ceil


val Int.dp: Int
    get() = ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)).toInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
fun createShadowDrawable(
    @ColorInt colors: IntArray,
    cornerRadius: Float,
    elevation: Float,
    centerX: Float,
    centerY: Float
): ShapeDrawable {

    val shadowDrawable = ShapeDrawable()
    shadowDrawable.paint.setShadowLayer(
        elevation, // размер тени
        0f, // смещение тени по оси Х
        0f, // по У
        Color.BLACK // цвет тени
    )

    shadowDrawable.paint.shader = SweepGradient(
        centerX,
        centerY,
        colors,
        null
    )

    // Делаем закугление углов
    val outerRadius = FloatArray(8) { cornerRadius }
    shadowDrawable.shape = RoundRectShape(outerRadius, null, null)

    return shadowDrawable
}

fun createColorDrawable(
    @ColorInt backgroundColor: Int,
    cornerRadius: Float
) = GradientDrawable().apply {
    setColor(backgroundColor)
    setCornerRadius(cornerRadius)
}

fun View.setColorShadowBackground(
    shadowDrawable: ShapeDrawable,
    colorDrawable: Drawable,
    padding: Int
) {
    val drawable = LayerDrawable(arrayOf(shadowDrawable, colorDrawable))
    drawable.setLayerInset(0, padding, padding, padding, padding)
    drawable.setLayerInset(1, padding, padding, padding, padding)
    setPadding(padding, padding, padding, padding)
    background = drawable
}

fun animateShadow(
    shapeDrawable: ShapeDrawable,
    @ColorInt startColors: IntArray,
    @ColorInt endColors: IntArray,
    duration: Long,
    centerX: Float,
    centerY: Float
) {
    /**
     * Меняем значение с 0f до 1f для применения плавного изменения
     * цвета с помощью [ColorUtils.blendARGB]
     */
    ValueAnimator.ofFloat(0f, 1f).apply {
        // Задержка перерисовки тени. Грубо говоря, фпс анимации
        val invalidateDelay = 100
        var deltaTime = System.currentTimeMillis()

        // Новый массив со смешанными цветами
        val mixedColors = IntArray(startColors.size)

        addUpdateListener { animation ->
            if (System.currentTimeMillis() - deltaTime > invalidateDelay) {
                val animatedFraction = animation.animatedValue as Float
                deltaTime = System.currentTimeMillis()

                // Смешиваем цвета
                for (i in 0..mixedColors.lastIndex) {
                    mixedColors[i] =
                        ColorUtils.blendARGB(startColors[i], endColors[i], animatedFraction)
                }

                // Устанавливаем новую тень
                shapeDrawable.paint.shader = SweepGradient(
                    centerX,
                    centerY,
                    mixedColors,
                    null
                )
                shapeDrawable.invalidateSelf()
            }
        }
        repeatMode = ValueAnimator.REVERSE
        repeatCount = Animation.INFINITE
        setDuration(duration)
        start()
    }
}