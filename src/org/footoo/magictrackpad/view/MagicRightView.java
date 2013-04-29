package org.footoo.magictrackpad.view;

import org.footoo.magictrackpad.TransUtils;
import org.footoo.magictrackpad.TransUtils.Command;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MagicRightView extends View {

  private static final String TAG = MagicRightView.class.getSimpleName();

  private int fingers = 0;
  private ViewType currentViewType = ViewType.DRAGPAD;

  private float startX;
  private float startY;
  private float endX;
  private float endY;

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
        if (fingers == 1) {
          /*
           * //Test Code
           * if (currentViewType == ViewType.DRAGPAD) {
           * Log.d(TAG, "DRAG");
           * } else {
           * Log.d(TAG, "DRAG WRONG!!!");
           * }
           */
          // TODO draw track


        }
        else if (fingers == 2) {
          /*
           * //Test Code
           * if (currentViewType == ViewType.FORWARDANDBACKWARD) {
           * Log.d(TAG, "TWO MOVE");
           * } else {
           * Log.d(TAG, "TWO MOVE WRONG!!!!");
           * }
           */

          // TODO draw track
        }
        break;
      case MotionEvent.ACTION_UP:
        fingers = 0;
        setEndPoint(event.getX(), event.getY());
        TransUtils.send(Command.DRAG, endX - startX, endY - startY);
        break;
      case MotionEvent.ACTION_DOWN:
        setStartPoint(event.getX(), event.getY());
        fingers = 1;
        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        if (fingers >= 1) {
          fingers++;
        }
        if (fingers == 2 && currentViewType == ViewType.DRAGPAD) {
          switchView(ViewType.FORWARDANDBACKWARD);

        }
        break;
      case MotionEvent.ACTION_POINTER_UP:
        fingers--;
        if (fingers == 2 && currentViewType == ViewType.DRAGPAD) {
          switchView(ViewType.FORWARDANDBACKWARD);
        } else if (fingers == 1 && currentViewType == ViewType.FORWARDANDBACKWARD) {
          switchView(ViewType.DRAGPAD);
          if (event.getY() - startY > 0) {
            TransUtils.send(Command.BACKWARD);
          } else {
            TransUtils.send(Command.FORWARD);
          }
        }
        break;
      case MotionEvent.ACTION_CANCEL:
        fingers = 0;
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
    super.onDraw(canvas);
  }

  private void switchView(ViewType type) {
    Log.d(TAG, "Switch to " + type.name());
    currentViewType = type;
    switch (type) {
      case DRAGPAD:
        break;
      case FORWARDANDBACKWARD:
        break;
    }
  }

  private void setStartPoint(float x, float y) {
    startX = x;
    startY = y;
  }

  private void setEndPoint(float x, float y) {
    endX = x;
    endY = y;
  }
}
