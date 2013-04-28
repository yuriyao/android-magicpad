package org.footoo.magictrackpad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MagicLeftView extends View {

	private int px, py;
	private Paint gPaint;	//画手势路径的paint
	private Paint outerPaint;
	private Paint innerPaint;
	
	public MagicLeftView(Context context) {
		super(context);
		setPaint();
	}
	
	public MagicLeftView(Context context, AttributeSet attr){
		super(context, attr);
		setPaint();
	}
	public MagicLeftView(Context context, AttributeSet attr, int arg){
		super(context, attr, arg);
		setPaint();
	}
	
	private void setPaint(){
		gPaint = new Paint();
		gPaint.setColor(0x44000000);
		gPaint.setStrokeWidth(2);
		
		outerPaint = new Paint();
		outerPaint.setColor(0xFFFFA500);
		outerPaint.setStrokeWidth(2);
		innerPaint = new Paint();
		innerPaint.setColor(0x00000000);
		innerPaint.setStrokeWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawCircle(px, py, px,  outerPaint);
		canvas.drawCircle(px, py, px, innerPaint);
		innerPaint.setColor(0xFF8B4726);
		canvas.drawCircle(px, py, 2*px/3, innerPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		float x = event.getX();
		float y = event.getY();
		return true;
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
