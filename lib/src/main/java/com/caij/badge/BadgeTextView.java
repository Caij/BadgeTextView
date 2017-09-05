package com.caij.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Created by Ca1j on 2016/12/15.
 */

public class BadgeTextView extends TextView {

    private int badgeColor;
    private int preTextSize = 0;

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

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.BadgeTextView, defStyleAttr, defStyleRes);
        badgeColor = typedArray.getColor(R.styleable.BadgeTextView_badge_color,
                getResources().getColor(R.color.default_badge_color));
        typedArray.recycle();

        setGravity(Gravity.CENTER);
        setIncludeFontPadding(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int currentTextSize = getCSize(getText());
        if (currentTextSize != this.preTextSize) {
            refreshBackgroundDrawable();
            this.preTextSize = currentTextSize;
        }
    }

    public void setBadgeColor(int colorInt) {
        if (badgeColor != colorInt) {
            this.badgeColor = colorInt;
            refreshBackgroundDrawable();
        }
    }

    private static int getCSize(CharSequence charSequence) {
        if (charSequence == null) return 0;
        return charSequence.length();
    }

    private void refreshBackgroundDrawable() {
        CharSequence text = getText();
        if (text == null) {
            return;
        }

        RectShape rectShape;
        if (text.length() == 1) {
            rectShape = new Oval();
        }else {
            // 外部矩形弧度
            int ra = getHeight() / 2;
            float[] outerR = new float[] {ra, ra, ra, ra, ra, ra, ra, ra };
            rectShape = new RoundRectShape(outerR, null, null);
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setColor(badgeColor);
        setBackgroundDrawable(shapeDrawable);
    }

    private class Oval extends OvalShape {

        public Oval() {
            super();
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            final int viewWidth = BadgeTextView.this.getWidth();
            final int viewHeight = BadgeTextView.this.getHeight();
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, Math.min(viewWidth / 2, viewHeight / 2), paint);
        }
    }

}
