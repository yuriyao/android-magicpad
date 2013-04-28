package org.footoo.magictrackpad;

import org.footoo.magictrackpad.view.FBView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class LeftActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rightview);

    FBView<Integer> seekBar = new FBView<Integer>(60, 140, this);
    seekBar.setOnRangeSeekBarChangeListener(new FBView.OnRangeSeekBarChangeListener<Integer>() {
      @Override
      public void onRangeSeekBarValuesChanged(FBView<?> bar, Integer minValue, Integer maxValue) {}

    });

    ViewGroup layout = (ViewGroup) findViewById(R.id.container);
    layout.addView(seekBar);
  }
}
