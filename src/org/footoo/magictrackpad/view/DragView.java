package org.footoo.magictrackpad.view;

import org.footoo.magictrackpad.R;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DragView extends LinearLayout {

  private static final float LENGTH_THRESHOLD = 120.0f;
  /* view members */
  private TextView infoView;
  private GestureOverlayView overlayView;
  /* useful staff */
  private Gesture gesture;
  private final GestureOverlayView.OnGestureListener onGestureListener =
      new GestureOverlayView.OnGestureListener() {

        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
          gesture = null;
        }

        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
          gesture = overlay.getGesture();
          if (gesture.getLength() < LENGTH_THRESHOLD) {
            overlay.clear(false);
          }
        }

        @Override
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {}

        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {}
      };



  public DragView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public DragView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DragView(Context context) {
    super(context);
  }

  /**
   * Creates a view.
   * 
   * @param parent parent view
   * @param resId resource id
   * @return view
   */
  public static DragView newInstance(ViewGroup parent) {
    return (DragView) LayoutInflater.from(parent.getContext()).inflate(R.layout.drapview, parent,
        false);
  }

  /**
   * Creates a view.
   * 
   * @param context context
   * @param resId resource id
   * @return view
   */
  public static DragView newInstance(Context context) {
    return (DragView) LayoutInflater.from(context).inflate(R.layout.drapview, null);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    infoView = (TextView) findViewById(R.id.info);
    overlayView = (GestureOverlayView) findViewById(R.id.gestures_overlay);
    initViews();
  }

  private void initViews() {
    overlayView.addOnGestureListener(onGestureListener);
  }
}
