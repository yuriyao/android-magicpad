package org.footoo.magictrackpad.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MagicLeftView extends View {

	private float px, py;// 圆心
	private Paint gPaint; // 画手势路径的paint
	private Paint outerPaint;
	private Paint innerPaint;
	private boolean currentRotation;
	private double currentX;
	private double currentY;
	private Paint currentPaint;
	private float mx, my;

	public MagicLeftView(Context context) {
		super(context);
		setPaint();
		currentRotation = false;
	}

	public MagicLeftView(Context context, AttributeSet attr) {
		super(context, attr);
		setPaint();
		currentRotation = false;
	}

	public MagicLeftView(Context context, AttributeSet attr, int arg) {
		super(context, attr, arg);
		setPaint();
		currentRotation = false;
	}

	private void setPaint() {
		gPaint = new Paint();
		gPaint.setColor(0x44000000);
		gPaint.setStrokeWidth(2);

		outerPaint = new Paint();
		outerPaint.setColor(0xFFFFA500);
		outerPaint.setStrokeWidth(2);
		innerPaint = new Paint();
		innerPaint.setColor(0x00000000);
		innerPaint.setStrokeWidth(2);
		currentPaint = new Paint();
		currentPaint.setColor(0xFF000000);
		currentPaint.setStrokeWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(px, py, px, outerPaint);
		innerPaint.setColor(0x00000000);
		canvas.drawCircle(px, py, 2*px/3, innerPaint);
		innerPaint.setColor(0xFF8B4726);
		canvas.drawCircle(px, py, 2*px/3, innerPaint);

		if (currentRotation) {
			canvas.drawCircle((float)currentX, (float)currentY, px / 6, currentPaint);
			Log.v("location", currentX+" "+currentY+" "+px+" "+py);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (isRotation(x, y)) {
				currentRotation = true;
				circle_start(x, y);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(currentRotation){
				circle_move(x,y);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			currentRotation = false;
			break;
		}

		return true;
	}

	private void circle_move(float x, float y) {
        float dx = Math.abs(x - mx);
        float dy = Math.abs(y - my);
        
        if (dx >= 1.0 || dy >= 1.0) {
            mx = x;
            my = y;
            circle_start(mx, my);
        }
	}

	//此函数求出当前圆心的位置。
	private void circle_start(float x, float y) {
		double radius;
		double alpha;
		radius = 5*px/6;
		mx = x;
		my = y;
		if (x<px&&y<py){
			alpha = Math.tanh((py-y)/(px-x));
			currentX = px - Math.cos(alpha)*radius;
			currentY = py - Math.sin(alpha)*radius;
		}else if(x<px&&y>py){
			alpha = Math.tanh((px-x)/(y-py));
			currentY = py + Math.cos(alpha)*radius;
			currentX = px - Math.sin(alpha)*radius;
		}else if(x>px&&y<py){
			alpha = Math.tanh((py-y)/(x-px));
			currentX = px + Math.cos(alpha)*radius;
			currentY = py - Math.sin(alpha)*radius;
		}else{
			alpha = Math.tanh((x-px)/(y-py));
			currentY = py + Math.cos(alpha)*radius;
			currentX = px + Math.sin(alpha)*radius;
		}
	}

	private boolean isRotation(float x, float y) {
		Log.v("location touch", x+" "+y);
		if ((Math.pow(x - px, 2) + Math.pow(y - py, 2)) <= Math.pow(2 * px / 3,
				2)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 取得当前屏幕最大化的正方形来制定view
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);
		int d = Math.min(measuredWidth, measuredHeight);
		setMeasuredDimension(d, 6 * d / 5);

		px = getMeasuredWidth() / 2;
		py = getMeasuredHeight() / 2;
	}

	private int measure(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED)
			result = 200;
		else
			result = specSize;
		return result;
	}
}
