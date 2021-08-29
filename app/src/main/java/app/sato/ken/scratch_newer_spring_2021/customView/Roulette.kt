package app.sato.ken.scratch_newer_spring_2021.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.model.RotateListener

class Roulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context,attrs, defStyleAttr){

    private var textSize = 0f
    private var textColor = Color.BLACK
    private var rouletteBorderLineColor = Color.BLACK
    private var rouletteBorderLineWidth = 0f
    private var colors = listOf("#fe4a49", "#2ab7ca", "#fed766", "#e6e6ea", "#f6abb6", "#005b96", "#7bc043", "#f37735","#E0BF5F","#FF039BE5","#FF9800","#FF757575","#A36200")


    private var rouletteDataList = listOf("JhDroid", "Android")
    private var rouletteSize = rouletteDataList.size

    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()
    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RouletteView,
            defStyleAttr,
            0
        )
        rouletteBorderLineColor = typedArray.getColor( R.styleable.RouletteView_rouletteBorderLineColor, Color.BLACK )
        rouletteBorderLineWidth = typedArray.getDimension( R.styleable.RouletteView_rouletteBorderLineWidth, 20f )
        textColor = typedArray.getColor( R.styleable.RouletteView_textColor, Color.BLACK )
        textSize = typedArray.getDimension( R.styleable.RouletteView_textSize, 60f )
        typedArray.recycle()


        strokePaint.apply{
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 10f
            isAntiAlias = true
        }
        fillPaint.apply {
            style= Paint.Style.FILL
            isAntiAlias = true
        }
        textPaint.apply {
            color = Color.BLACK
            textSize = 60f
            textAlign = Paint.Align.CENTER
        }
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rectLeft = left.toFloat() + 6
        val rectRight = right.toFloat() - 6
        val rectTop = height / 2f - rectRight / 2f - paddingTop
        val rectBottom = height / 2f + rectRight / 2f + paddingRight
        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas?.drawArc(rectF, 0f, 360f, true, strokePaint)

        drawRoulette(canvas,rectF)
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF) {
        canvas?.drawArc(rectF, 0f, 360f, true, strokePaint)
        if (rouletteSize in 2..200) {

            val sweepAngle = 360f/rouletteSize.toFloat()
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val radius = (rectF.right - rectF.left) / 2 * 0.5




                for (i in 0 until rouletteSize) {
                    fillPaint.color = Color.parseColor(colors[i])
                    val startAngle = if (i == 0) 0f else sweepAngle * i
                    canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                    val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
                    val x = (centerX + (radius * kotlin.math.cos(medianAngle))).toFloat()
                    val y = (centerY + (radius * kotlin.math.sin(medianAngle))).toFloat()
                    val text = if (i > rouletteDataList.size - 1) "empty" else rouletteDataList[i]
                    canvas?.drawText(text, x, y, textPaint)
                }

        } else throw RuntimeException("size out of roulette")
    }

    fun rotateRoulette(toDegrees: Float, duration: Long, rotateListener: RotateListener) {
        val animListener = object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }
            override fun onAnimationStart(animation: Animation?) {
                rotateListener.onRotateStart()
            }
            override fun onAnimationEnd(animation: Animation?) {
                rotateListener.onRotateEnd(getRouletteRotateResult(toDegrees))
            }
        }

        val rotateAnim = RotateAnimation( 0f, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true
        rotateAnim.setAnimationListener(animListener)
        startAnimation(rotateAnim)
    }

    private fun getRouletteRotateResult(degrees: Float): String {
        val moveDegrees = degrees % 360
        val resultAngle = if (moveDegrees > 270) 360 - moveDegrees + 270 else 270 - moveDegrees
        for (i in 1..rouletteSize) {
            if (resultAngle < (360 / rouletteSize) * i) {
                if (i - 1 >= rouletteDataList.size) {
                    return "empty" }
                return rouletteDataList[i - 1]
            }
        }
        return ""
    }
    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        invalidate()
    }
    fun setRouletteDataList(list : List<String>){
        this.rouletteDataList = list
        invalidate()
    }

    fun setRouletteColor(colors : List<String>){
        this.colors = colors
        invalidate()
    }

    fun setRoulettesize(size: Int){
        this.rouletteSize = size
        invalidate()
    }
    fun getTextSize(): Float = textSize fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    } fun getTextColor(): Int = textColor
    fun setRouletteBorderLineColor(borderLineColor: Int) {
        this.rouletteBorderLineColor = borderLineColor
        invalidate()
    }
    fun getRouletteBorderLineColor(): Int = rouletteBorderLineColor
    fun setRouletteBorderLineWidth(width: Float) {
        rouletteBorderLineWidth = width
        invalidate()
    }
    fun getRouletteBorderLineWidth(): Float = rouletteBorderLineWidth
}