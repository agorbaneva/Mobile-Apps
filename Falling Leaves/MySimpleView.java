package alyss.example.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class MySimpleView extends View {


    private Paint mDrawPaint;
    private ArrayList<ArrayList<MyAnimation>> mAnimations;
    private int canvasHeight, canvasWidth;


    public MySimpleView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        setupPaint();
        mAnimations = new ArrayList<ArrayList<MyAnimation>>();

    }

    private void setupPaint(){
        mDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDrawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mDrawPaint.setColor(getResources().getColor(R.color.orange));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ArrayList<MyAnimation> one = new ArrayList<>();
            int firstX = (int) event.getX();
            int firstY = (int) event.getY();
            Random r = new Random();
            int randomLeaf = r.nextInt(10)+1;
            for(int x=0; x< randomLeaf; x ++){
                if(Math.random()>0.5) one.add(new MyAnimation((int) (firstX+50*Math.pow(-1, x)), firstY+50*x, 50, 0));
                else one.add(new MyAnimation((int) (firstX+50*Math.pow(-1, x)), firstY+50*x, 50, 1));
            }
            if(one!=null) mAnimations.add(one);


        }
        return false;
    }

    protected void onDraw(Canvas canvas){ //called ONCE at start unless u call postInvalidate()
        super.onDraw(canvas);

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        if(mAnimations != null) {
            for (ArrayList<MyAnimation> a : mAnimations) {
                for (MyAnimation ma : a) {

                    if(ma.color==0)
                        mDrawPaint.setColor(getResources().getColor(R.color.leafBrown));
                    else
                        mDrawPaint.setColor(getResources().getColor(R.color.orange));

                    if(ma.cx+ma.cRadius<canvasWidth) canvas.drawCircle(ma.cx, ma.cy, ma.cRadius, mDrawPaint);

                }
            }
        }
    }

    protected class MyAnimation {

        public float cx, cy, cRadius, color;

        public MyAnimation(float x, float y, float radius, int colors) {
            cx = x;
            cy = y;
            cRadius = radius;
            color=colors;
            init();
        }

        private void init() {
            ValueAnimator animation = ValueAnimator.ofFloat((float)cy, canvasHeight);
            animation.setDuration(1500);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    cy = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animation.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mAnimations.remove(MyAnimation.this);
                }
            });
            animation.start();
        }
    }
}

