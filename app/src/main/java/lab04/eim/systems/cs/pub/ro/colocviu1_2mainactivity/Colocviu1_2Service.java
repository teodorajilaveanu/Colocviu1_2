package lab04.eim.systems.cs.pub.ro.colocviu1_2mainactivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu1_2Service extends Service {
    MyWorkThread thread = null;

    public Colocviu1_2Service() {
    }



    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new MyWorkThread(this, intent.getStringExtra(Constants.SUM));
        thread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onDestroy() {
        thread.stopThread();
    }
}