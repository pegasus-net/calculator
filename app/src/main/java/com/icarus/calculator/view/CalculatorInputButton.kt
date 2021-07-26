package com.icarus.calculator.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import com.icarus.calculator.R
import com.icarus.calculator.util.dp2px


class CalculatorInputButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    private val paint = Paint()
    var shadowOffsetX: Int = context.dp2px(0F)
    var shadowOffsetY: Int = context.dp2px(0F)
    var radius = 21
    var rectF = RectF()
    var bgColor: Int
    var textColor = 0xFF333333.toInt()
        set(value) {
            field = value
            postInvalidate()
        }
    var vibratorSet: CalculatorSetButton? = null
    private val normal by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    shadowOffsetX,
                    shadowOffsetY,
                    (width + shadowOffsetX),
                    (height + shadowOffsetY),
                    radius.toFloat()
                )
            }
        }
    }
    private val press by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    (shadowOffsetX * 2 / 3),
                    (shadowOffsetY * 2 / 3),
                    (width + shadowOffsetX * 2 / 3),
                    (height + shadowOffsetY * 2 / 3),
                    radius.toFloat()
                )
            }
        }
    }
    var input: String
    var show: String = ""
        set(value) {
            field = value
            postInvalidate()
        }
    private var textSize = 36
    private var icon: Bitmap? = null

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        paint.textAlign = Paint.Align.CENTER
        paint.isAntiAlias = true
        outlineProvider = normal
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.CalculatorInputButton
            )
        input = typedArray.getString(R.styleable.CalculatorInputButton_inputText) ?: ""
        show = typedArray.getString(R.styleable.CalculatorInputButton_buttonText) ?: ""
        bgColor = typedArray.getColor(R.styleable.CalculatorInputButton_bgColor, 0xFFEFEFEF.toInt())
        textSize = typedArray.getDimensionPixelSize(R.styleable.CalculatorInputButton_textSize, 36)
        textColor =
            typedArray.getColor(R.styleable.CalculatorInputButton_textColor, 0xFF333333.toInt())
        paint.textSize = textSize.toFloat()
        val image = typedArray.getResourceId(R.styleable.CalculatorInputButton_buttonImage, 0)
        icon = if (image != 0) {
            BitmapFactory.decodeResource(context.resources, image)
        } else {
            null
        }
        typedArray.recycle()
        elevation = context.dp2px(4F).toFloat()
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    val vibrator =
                        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    val b = vibratorSet?.state ?: false
                    if (b) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    10,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                        } else {
                            vibrator.vibrate(10)
                        }
                    }
                    outlineProvider = press
                    elevation = context.dp2px(2F).toFloat()
                }
                MotionEvent.ACTION_UP -> {
                    performClick()
                    elevation = context.dp2px(4F).toFloat()
                    outlineProvider = normal

                }
                MotionEvent.ACTION_CANCEL -> {
                    elevation = context.dp2px(4F).toFloat()
                    outlineProvider = normal
                }
            }
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(0F, 0F, w.toFloat(), h.toFloat())
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.run {
            val strokeWidth = context.dp2px(2F).toFloat()

            paint.strokeWidth = 1F
            paint.style = Paint.Style.FILL
            paint.color = bgColor
            drawRoundRect(
                strokeWidth / 2,
                strokeWidth / 2,
                width - strokeWidth / 2,
                height - strokeWidth / 2,
                radius.toFloat(),
                radius.toFloat(),
                paint
            )

            paint.color = textColor
            if (show == "=") {
                paint.textSize = textSize * 1.5F
            }
            drawText(show, rectF, paint)

            paint.color = Color.WHITE
            paint.strokeWidth = strokeWidth
            paint.style = Paint.Style.STROKE
            drawRoundRect(
                strokeWidth / 2,
                strokeWidth / 2,
                width - strokeWidth / 2,
                height - strokeWidth / 2,
                radius.toFloat(),
                radius.toFloat(),
                paint
            )
            if (icon != null) {
                val pic: Bitmap = icon as Bitmap
                drawBitmap(
                    pic,
                    (width - pic.width) / 2F,
                    (height - pic.height) / 2F,
                    paint
                )

            }
        }
    }

    private fun Canvas.drawText(text: String, rectF: RectF, paint: Paint) {
        paint.textAlign = Paint.Align.CENTER
        val fontMetrics = paint.fontMetrics
        val baseline = rectF.centerY() - (fontMetrics.top + fontMetrics.bottom) / 2
        drawText(text, rectF.centerX(), baseline, paint)
    }

}

