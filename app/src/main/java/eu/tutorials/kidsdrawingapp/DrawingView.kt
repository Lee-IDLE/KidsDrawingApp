package eu.tutorials.kidsdrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
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
        mDrawPath = CustomPath(color, mBrsuhSize)

        if(mDrawPaint != null){
            mDrawPaint!!.color = color
            mDrawPaint!!.style = Paint.Style.STROKE
            mDrawPaint!!.strokeJoin = Paint.Join.ROUND  // 선 시작
            mDrawPaint!!.strokeCap = Paint.Cap.ROUND    // 선 끝 (stroke 또는 StrokeAndFill 때만 사용해라)
        }

        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20.toFloat()

    }

    // android.Path
    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }
}