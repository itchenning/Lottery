package com.terry.Lottery

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.random.Random

class LotteryView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mItems = mutableListOf<String>()

    private val mRingPaint = Paint()
    private val mOnePaint = Paint()
    private val mTwoPaint = Paint()
    private val mThreePaint = Paint()
    private val mTextPaint = TextPaint()
    private var mInited = false
    private var mIsRuning = false

    fun setItems(items: MutableList<String>) {
        Log.i("haha", items.joinToString(separator = "#"))
        if (items.isEmpty()) {
            return
        }
        this.mItems.clear()
        for (one in items) {
            if (!TextUtils.isEmpty(one)) {
                this.mItems.add(one)
            }
        }
        initPaints()
        postInvalidate()
    }

    private fun initPaints() {
        if (mInited) {
            return
        }
        mRingPaint.setColor(Color.rgb(245, 123, 122))
        mRingPaint.isAntiAlias = true
        mRingPaint.style = Paint.Style.STROKE
        mRingPaint.strokeWidth = DpUtils.dp2px(context, 16F)

        mOnePaint.setColor(Color.rgb(241, 85, 56))
        mOnePaint.isAntiAlias = true
        mOnePaint.style = Paint.Style.FILL

        mTwoPaint.setColor(Color.rgb(250, 170, 171))
        mTwoPaint.isAntiAlias = true
        mTwoPaint.style = Paint.Style.FILL

        mThreePaint.setColor(Color.WHITE)
        mThreePaint.isAntiAlias = true
        mThreePaint.style = Paint.Style.FILL

        mTextPaint.setColor(Color.BLACK)
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = DpUtils.dp2px(context, 20F)
        mTextPaint.textAlign = Paint.Align.CENTER

        mInited = true
    }

    override fun onDraw(canvas: Canvas) {
        if (mItems.isEmpty()) {
            return
        }
        drawRing(canvas)
        drawItems(canvas)
    }

    private fun drawItems(canvas: Canvas) {
        val oneAngle = 360F / mItems.size
        var startAngle = oneAngle - 90 - oneAngle / 2F
        var centerAngle = 0F
        val rectf = RectF(mRingPaint.strokeWidth, mRingPaint.strokeWidth, width - mRingPaint.strokeWidth, height - mRingPaint.strokeWidth)
        var item: String
        for (index in 0 until mItems.size) {
            if (index == mItems.size - 1 && index % 2 == 0) {
                canvas.drawArc(rectf, startAngle, oneAngle, true, mThreePaint)
            } else if (index % 2 == 0) {
                canvas.drawArc(rectf, startAngle, oneAngle, true, mOnePaint)
            } else {
                canvas.drawArc(rectf, startAngle, oneAngle, true, mTwoPaint)
            }
            item = mItems.get(index)
            canvas.save();
            val layout = StaticLayout(item, mTextPaint, 1, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
            canvas.rotate(centerAngle, width / 2F, height / 2F)
            canvas.translate(width / 2F + mTextPaint.measureText(item.first().toString()) / 2F, DpUtils.dp2px(context, 20F));
            layout.draw(canvas);
            canvas.restore();
            startAngle += oneAngle
            centerAngle += oneAngle
        }
        resetFirst(canvas)
    }

    private fun resetFirst(canvas: Canvas) {
        val layout = StaticLayout(mItems.first(), mTextPaint, 1, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
        canvas.translate(width / 2F + mTextPaint.measureText(mItems.first().first().toString()) / 2F, DpUtils.dp2px(context, 20F));
        layout.draw(canvas);
    }

    private fun drawRing(canvas: Canvas) {
        canvas.drawCircle(width / 2F, width / 2F, width / 2F - mRingPaint.strokeWidth / 2F, mRingPaint)
    }

    fun go() {
        if (mIsRuning) {
            return
        }
        mIsRuning = true
        val oneAngle = 360F / mItems.size
        val totalAngle = oneAngle * (Random.nextInt(100) + 50)
        var dutation = totalAngle * 5F
        dutation = Math.min(dutation, 10 * 1000F)
        val anim = ObjectAnimator.ofFloat(this, "rotation", 0f, totalAngle)
        anim.setDuration(dutation.toLong())
        anim.interpolator = DecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mIsRuning = false
            }
        })
        anim.start()
    }
}