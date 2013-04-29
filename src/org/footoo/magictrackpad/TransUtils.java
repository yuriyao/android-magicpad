package org.footoo.magictrackpad;

import android.util.Log;

public class TransUtils {
  private static final String TAG = TransUtils.class.getSimpleName();
  private static boolean isTry = false;

  private TransUtils() {

  }

  public static boolean send(Command cmd) {
    return sendViaUSB(cmd.getCode());
  }

  public static boolean send(Command cmd, float delX, float delY) {
    Log.d(TAG, "send command with del");
    return sendViaUSB(cmd.getCode(), delX, delY);
  }

  public enum Command {
    DRAG(0),
    ZOOM_IN(1),
    ZOOM_OUT(2),
    FORWARD(3),
    BACKWARD(4),
    RIGHT(5),
    LEFT(6);
    private int cmdCode;

    Command(int code) {
      cmdCode = code;
    }

    public int getCode() {
      return cmdCode;
    }
  }

  private static boolean sendViaUSB(int cmdCode) {
    USBCommunicator.getInstance().sendData(cmdCode + "");
    return false;
  }

  private static boolean sendViaUSB(int cmdCode, float x, float y) {
    USBCommunicator.getInstance().sendData(cmdCode + "?" + x + ":" + y);
    return false;
  }
}
