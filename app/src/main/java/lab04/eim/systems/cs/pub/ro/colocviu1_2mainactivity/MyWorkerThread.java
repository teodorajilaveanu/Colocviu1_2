package lab04.eim.systems.cs.pub.ro.colocviu1_2mainactivity;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Process;

import java.util.Date;
import java.util.Random;

class MyWorkThread extends Thread {
    /* We will do some operation and put it in result to be send */
    Context context;
    boolean isRunning = true;
    String sum;
    /* We will receive an android context to use for sending broadcast messages */
    public MyWorkThread(Context context, String sum) {
        /* We will do some operations with the arguments */
        this.context = context;
        this.sum = sum;
    }

    /* Function gets called to run on the thread */
    @Override
    public void run() {
        Log.d("colocviu", "Thread has started, PID: " + Process.myPid());
        while (isRunning) {
            /* do some work 8 */
            /* We send the results to the main activity via a broadcast receiver */
            sendMessage();
            /* we now sleep and do work again after it */
            sleep();
        }
    }

    /* Useful in general */
    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.practicaltest01.sum");
        /* We store at key result the results of our operation on the thread */
        intent.putExtra(Constants.BROADCAST_EXTRA,
                new Date(System.currentTimeMillis()) + ", The sum is: " + this.sum);
        context.sendBroadcast(intent);
    }

    /* Function called from the service that created this thread to stop it. This will stop the while True
     * from run() */
    public void stopThread() {
        isRunning = false;
    }
}
