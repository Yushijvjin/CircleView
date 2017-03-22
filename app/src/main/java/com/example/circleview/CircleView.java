package com.example.circleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 1. 类的用途
 * 2. @author： 李小兵
 * 3. @date：2017/3/21 22:35
 */

public class CircleView extends View {

//画笔
    private Paint mpaint;
    //坐标
    private float mCurrX;
    private float mCurrY;
//圆的半径
    private float radius=100;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mpaint = new Paint();

        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.parseColor("#ff0000"));

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                float eventX = motionEvent.getX();
                float eventY = motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (isOk(eventX, eventY, radius)) {
                            mCurrX = eventX;
                            mCurrY = eventY;
                            invalidate();
                            break;
                        }
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCurrX = getWidth() / 2;
        mCurrY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCurrX, mCurrY, radius, mpaint);
    }
//触摸后重新绘制圆
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float dis = getDisByXY(event);
        if (dis != 0) {
            if (isOk(mCurrX, mCurrY, dis)) {
                radius = dis;
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }
//判断是否出屏外
    private boolean isOk(float eventX, float eventY, float radius) {
        return eventX >= radius && eventY >= radius && eventX <= getWidth() - radius && eventY <= getHeight() - radius;
    }
//获得多个手指触摸坐标
    private float getDisByXY(MotionEvent event) {

        if (event.getPointerCount() >= 2) {

            float x = event.getX();
            float y = event.getY();

            float x1 = event.getX(1);
            float y1 = event.getY(1);

            return (float) Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
        }
        return 0;
    }
}
