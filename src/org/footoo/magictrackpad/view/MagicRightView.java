package org.footoo.magictrackpad.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MagicRightView extends View {

  private static final String TAG = MagicRightView.class.getSimpleName();

  private int fingures = 0;
  private ViewType currentViewType = ViewType.DRAGPAD;

  public MagicRightView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public MagicRightView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MagicRightView(Context context) {
    super(context);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    final int action = event.getAction();
    switch (action & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_MOVE:
        if (fingures == 1) {
          Log.d(TAG, "DRAG");
        }
        else if (fingures == 2) {
          Log.d(TAG, "TWO MOVE");
        }
        break;
      case MotionEvent.ACTION_UP:
        fingures = 0;
        break;
      case MotionEvent.ACTION_DOWN:
        fingures = 1;
        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        if (fingures >= 1) {
          fingures++;
        }
        break;
      case MotionEvent.ACTION_POINTER_UP:
        fingures--;
        break;
      case MotionEvent.ACTION_CANCEL:
        fingures = 0;
        break;
    }
    return true;

  }

  private enum ViewType {
    FORWARDANDBACKWARD,
    DRAGPAD
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // TODO Auto-generated method stub
    super.onDraw(canvas);
  }



}
