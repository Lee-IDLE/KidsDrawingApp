package eu.tutorials.kidsdrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas? = null

    init{
        setUpDrawing()
    }

    private fun setUpDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)

        if(mDrawPaint != null){
            mDrawPaint!!.color = color
            mDrawPaint!!.style = Paint.Style.STROKE
            mDrawPaint!!.strokeJoin = Paint.Join.ROUND  // 선 시작
            mDrawPaint!!.strokeCap = Paint.Cap.ROUND    // 선 끝 (stroke 또는 StrokeAndFill 때만 사용해라)
        }

        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20.toFloat()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 화면이 수정될 때마다 bitmap을 크기에 맞게 수정하고
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        // canvas에 넣는다
        canvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 특정한 비트맵을 상단 왼쪽을 기준으로 위치정보(x,y)에 명시된 곳에 현재 행렬에
        // 의해 변환된 지
        canvas?.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)
        if(mDrawPath != null && mDrawPaint != null){
            mDrawPaint!!.color = mDrawPath!!.color
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            canvas?.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                mDrawPath!!.reset()
                if(touchX != null && touchY != null)
                    mDrawPath!!.moveTo(touchX, touchY)
            }
            MotionEvent.ACTION_MOVE -> {
                if(touchX != null && touchY != null)
                    mDrawPath!!.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP ->{
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }

        // 화면이 그려질 때 뒤에는 무효화 하고 그려지게 한다.
        // 화면 스레드에서만 사용해야하고 화면스레드가 아닌 경우 postinvalidate를 사용해야 한다
        invalidate()

        return true
    }

    // android.Path
    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }
}