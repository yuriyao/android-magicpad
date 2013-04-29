package org.footoo.magictrackpad;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

public class USBCommunicator {

  private static final String TAG = USBCommunicator.class.getSimpleName();
  private static final int TIMEOUT = 10;
  private List<String> stringList = null;
  private ServerSocket server = null;
  private Socket client = null;
  private int portNumber = 38301;

  private static USBCommunicator instance;

  public static synchronized USBCommunicator getInstance() {
    if (instance == null) {
      instance = new USBCommunicator();
    }
    return instance;
  }

  private USBCommunicator() {}

  public void startConnect() {
    new Thread(startConnection).start();
  }

  public void sendData(final String code) {
    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

      @Override
      protected Void doInBackground(Void... params) {
        Log.d(TAG, "send data " + code);
        Globals.socketOut.println(code);
        return null;
      }
    };

    task.execute();
  }

  private final Runnable startConnection = new Thread() {

    @Override
    public void run() {
      super.run();


      // initialize server socket
      try {
        server = new ServerSocket(portNumber);
        Log.d(TAG, "waiting for connection");
        while (true) {
          // listen for incoming clients
          client = server.accept();
          try {
            Globals.socketIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Globals.socketOut = new PrintWriter(client.getOutputStream(), true);

            Globals.socketOut.println("Hello from the server!");


            // new Thread(getData).start();

            String line = null;
            stringList = new ArrayList<String>();
            // reading in the input from the usb socket
            while ((line = Globals.socketIn.readLine()) != null) {
              stringList.add(line);
            }

          } catch (Exception e) {
            Log.d(TAG, "lost client");
            e.printStackTrace();
          } // try/catch
        } // while(true)
      } catch (Exception e) {
        Log.d(TAG, "error, disconnected");
        e.printStackTrace();
      } // try/catch
    }
  };



  public static class Globals {
    public static boolean connected;
    public static PrintWriter socketOut;
    public static BufferedReader socketIn;
  }
}
