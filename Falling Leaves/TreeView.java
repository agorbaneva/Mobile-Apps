package alyss.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class TreeView extends View {

    private Paint mPaint;


    public TreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.skyBlue));
        mPaint.setStyle(Paint.Style.FILL);
        final Path pathB = new Path();

        pathB.moveTo( 0, canvas.getHeight()); //bottom left point
        pathB.lineTo(0, 0); //upper left point
        pathB.lineTo(canvas.getWidth(), 0); //upper right corner
        pathB.lineTo(canvas.getWidth(), canvas.getHeight());
        pathB.lineTo(0, canvas.getHeight()); //bottom left point

        canvas.drawPath(pathB, mPaint);

        mPaint.setColor(getResources().getColor(R.color.treeBrown));

        final Path path = new Path();

        path.moveTo((int) (canvas.getWidth()*0.4), canvas.getHeight()); //bottom left point
        path.lineTo((int) (canvas.getWidth()*0.7), 0); //upper left point
        path.lineTo(canvas.getWidth(), 0); //upper right corner
        path.lineTo(canvas.getWidth(), canvas.getHeight());
        path.lineTo((int) (canvas.getWidth()*0.4), canvas.getHeight()); //bottom left point

        canvas.drawPath(path, mPaint);

    }




}