package org.footoo.magictrackpad;

import android.app.Activity;
import android.os.Bundle;

public class RightActivity extends Activity {


  private static final String TAG = RightActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rightview);
    USBCommunicator.getInstance().startConnect();
  }



}
