package app.sato.ken.scratch_newer_spring_2021.customView


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class ScratchView : View, OnTouchListener {
    private var mWallBitmap // 塗りたくったビットマップ
            : Bitmap? = null
    private var mWallCanvas // 元になるキャンバス
            : Canvas? = null

    constructor(context: Context?) : super(context) {
        setOnTouchListener(this)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        setOnTouchListener(this)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        setOnTouchListener(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        // Bitmapを新しく作る
        mWallBitmap = null
        mWallBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        // キャンバスを新しく作る
        mWallCanvas = null
        mWallCanvas = Canvas(mWallBitmap!!)

        // アルファで抜いていく画像を適当な透明度で初期化
        mWallCanvas!!.drawColor(Color.argb(255, 189, 189, 189)) //ここで色を決める
        super.onSizeChanged(w, h, oldw, oldh)
    }

    //     * 描画通知
    override fun onDraw(canvas: Canvas) {

        // 落書き用Bitmapに書かれた内容を表へ転送
        canvas.drawBitmap(mWallBitmap!!, 0f, 0f, null)
        super.onDraw(canvas)
    }

    private val mPath = Path()

    //タッチされた時の処理
    override fun onTouch(v: View, event: MotionEvent): Boolean {

        // タッチされた座標を取り出す
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                // パスをリセットしてから新しい座標をセット
                mPath.reset()
                mPath.moveTo(x, y)

                // ここまでの状態でパスに沿って線を書く。
                // 最初だから○が描かれるだけ
                drawPath(mWallCanvas, mPath)

                // 再描画通知
                this.invalidate()
                return true // ←trueを返さないとACTION_UPがやってこない
            }
            MotionEvent.ACTION_UP -> {

                // ここまでのパスをセット
                mPath.lineTo(x, y)

                // パスに沿って線を描く
                drawPath(mWallCanvas, mPath)

                // 線で繋がれた範囲のバウンディングボックスの計算
                val bounds = RectF()
                mPath.computeBounds(bounds, true)

                // 線の太さ分外側に押し出す
                bounds.left -= 30f
                bounds.right += 30f
                bounds.top -= 30f
                bounds.bottom += 30f

                // 再描画通知
                this.invalidate()
            }
            MotionEvent.ACTION_MOVE -> {

                // パスをセットする
                mPath.lineTo(x, y)

                // ここまでの状態を書き込む
                drawPath(mWallCanvas, mPath)

                // 再描画通知
                this.invalidate()
            }
        }
        return false
    }

    //BOXを描く
    private fun drawBox(canvas: Canvas?, r: RectF) {

        // 描画設定
        val paint = Paint()
        paint.isDither = true
        paint.isAntiAlias = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        paint.color = -0x90000

        // 線を描く
        paint.style = Paint.Style.STROKE

        // 矩形の指定に従って描き込む
        canvas!!.drawRect(r, paint)
    }

    //パスに沿って線を書く
    private fun drawPath(
        canvas: Canvas?,
        path: Path
    ) {

        // 線を書くためのペイント
        val paint = Paint()
        paint.isDither = true
        paint.isAntiAlias = true

        // これをすると塗りつぶす方の色が優先される
        // 背景のアルファを無視して下のレイアウトが見えるようになる
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        paint.color = Color.argb(0, 0x00, 0, 0)

        // 線を引く設定
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND

        // 太めに
        paint.strokeWidth = 60f
        canvas!!.drawPath(path, paint)
    }

}
