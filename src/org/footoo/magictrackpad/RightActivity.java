package org.footoo.magictrackpad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class RightActivity extends Activity {

  private int fingures = 0;

  private static final String TAG = RightActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rightview);
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


}
