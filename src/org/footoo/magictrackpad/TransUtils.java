package org.footoo.magictrackpad;

public class TransUtils {
  private TransUtils() {

  }

  public static boolean send() {
    return false;
  }

  public static boolean send(Command cmd, int delX, int delY) {
    return false;
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

}
