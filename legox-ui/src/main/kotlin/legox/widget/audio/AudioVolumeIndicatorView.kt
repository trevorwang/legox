package legox.widget.audio

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import legox.core.dp
import mingsin.audio.widget.R

class AudioVolumeIndicatorView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) :
    View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint = Paint()
    private val columnCount: Int
    private val columnHeightArray: Array<Int>
    private val columnMargin: Int
    private val verticalEdge: Int
    private val minColumnHeight: Int
    var audioVolumeFunction: (() -> Int)? = null
    private val postRunnable = {
        updateColumnHeight()
        postInvalidate()
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.AudioVolumeIndicatorView,
            0, 0
        ).apply {
            try {
                paint.color =
                    getColor(R.styleable.AudioVolumeIndicatorView_indicatorColor, Color.LTGRAY)
                columnMargin =
                    getDimensionPixelSize(R.styleable.AudioVolumeIndicatorView_columnMargin, 4.dp)
                verticalEdge =
                    getDimensionPixelSize(R.styleable.AudioVolumeIndicatorView_verticalEdge, 10.dp)
                minColumnHeight = getDimensionPixelSize(
                    R.styleable.AudioVolumeIndicatorView_minColumnHeight, 2.dp
                )
                columnCount = getInteger(R.styleable.AudioVolumeIndicatorView_columnCount, 20)
            } finally {
                recycle()
            }
        }

        columnHeightArray = Array(columnCount) { 0 }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { c ->
            columnRects().forEach { c.drawRect(it, paint) }
        }
        sendRedrawMessage()
    }

    private fun columnRects(): List<Rect> {
        val rects = arrayListOf<Rect>()
        (0 until columnCount).forEach { index ->
            val h = columnHeightArray[index].coerceAtLeast(minColumnHeight)
            val top = (height - verticalEdge - h) / 2
            val left = columnMargin + (columnWidth + columnMargin) * index
            rects.add(
                Rect(
                    left, top, left + columnWidth, top + h
                )
            )
        }
        return rects
    }


    private val columnWidth: Int
        get() = (width - paddingStart - paddingEnd - columnMargin) / columnCount

    private val columnHeight: Int
        get() = height - paddingTop - paddingBottom - verticalEdge * 2

    private fun addNextColumnHeight(height: Int) {
        for (i in 0 until columnCount - 1) {
            columnHeightArray[i] = columnHeightArray[i + 1]
        }
        columnHeightArray[columnCount - 1] = height
    }

    private fun updateColumnHeight() {
        val h = audioVolumeFunction?.invoke() ?: 0
        addNextColumnHeight(h)
    }

    private fun sendRedrawMessage() {
        postDelayed(postRunnable, 150)
    }

    fun reset() {
        (0 until columnCount).forEach { index ->
            columnHeightArray[index] = 0
        }
    }
}