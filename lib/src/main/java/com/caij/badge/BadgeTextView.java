package com.caij.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Created by Ca1j on 2016/12/15.
 */

public class BadgeTextView extends TextView {

    private Paint mBadgePaint;
    private RectF mBadgeRectF;

    public BadgeTextView(Context context) {
        this(context, null);
    }

    public BadgeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.BadgeTextView, defStyleAttr, defStyleRes);
        int badgeColor = typedArray.getColor(R.styleable.BadgeTextView_badge_color,
                ContextCompat.getColor(getContext(), R.color.default_badge_color));
        typedArray.recycle();

        mBadgePaint = new Paint();
        mBadgePaint.setAntiAlias(true);
        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);
        mBadgePaint.setColor(badgeColor);

        mBadgeRectF = new RectF();

        setGravity(Gravity.CENTER);
        setIncludeFontPadding(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        if (getText() == null || getText().length() <= 1) {
            width = height;
        }

        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBadge(canvas);
        super.onDraw(canvas);
    }

    private void drawBadge(Canvas canvas) {
        // 计算徽章背景的宽高
        int badgeHeight = getHeight();

        int badgeWidth = getWidth();

        // 计算徽章背景上下的值
        mBadgeRectF.top = 0;
        mBadgeRectF.bottom = badgeHeight;

        // 计算徽章背景左右的值
        mBadgeRectF.left = 0;
        mBadgeRectF.right = badgeWidth;

        // 绘制徽章背景
        canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2f, badgeHeight / 2f, mBadgePaint);
    }

    public void setBadgeColor(int color) {
        mBadgePaint.setColor(color);
        invalidate();
    }

    @Override
    @Deprecated
    public void setBackgroundColor(int color) {
        setBadgeColor(color);
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {

    }
}
