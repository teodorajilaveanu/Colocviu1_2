package lab04.eim.systems.cs.pub.ro.colocviu1_2mainactivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nextTerm, allTerms;
    Button addButton, computeButton;
    Integer last_value = -1;
    int serviceStatus = Constants.SERVICE_STOPPED;
    ButtonListener listener = new ButtonListener();
    IntentFilter intentFilter = new IntentFilter();


    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(this,"Message: " + intent.getStringExtra(Constants.BROADCAST_EXTRA), Toast.LENGTH_LONG).show();
                Log.d("Message", intent.getStringExtra(Constants.BROADCAST_EXTRA));
        }
    }
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String value = nextTerm.getText().toString();
            System.out.println(value);
            if (view.getId() == R.id.add_button && value != null) {
                if (allTerms.getText().toString().equals("0"))
                    allTerms.setText(value);
                else
                    allTerms.setText(allTerms.getText().toString() + "+" + value);
            }
            if (view.getId() == R.id.compute_button) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2SecondaryActivity.class);
                intent.putExtra(Constants.ALL_TERMS, allTerms.getText().toString());
                startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
            }

            if (last_value > 10 && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_2Service.class);
                serviceStatus = Constants.SERVICE_STARTED;
                intent.putExtra(Constants.SUM, String.valueOf(last_value));
                getApplicationContext().startService(intent);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextTerm = (EditText) findViewById(R.id.next_term);
        allTerms = (EditText) findViewById(R.id.all_terms);
        addButton = (Button) findViewById(R.id.add_button);
        computeButton = (Button) findViewById(R.id.compute_button);
        addButton.setOnClickListener(listener);
        computeButton.setOnClickListener(listener);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.ALL_TERMS)) {
                allTerms.setText(savedInstanceState.getString(Constants.ALL_TERMS));
            } else {
                allTerms.setText(String.valueOf(0));
            }
        } else
            allTerms.setText(String.valueOf(0));

        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.sum");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The computed sum is: " + resultCode, Toast.LENGTH_LONG).show();
        }
        last_value = resultCode;
        allTerms.setText(String.valueOf(resultCode));

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("Colocviu", "onSaveInstanceState was called");
        outState.putString(Constants.ALL_TERMS, allTerms.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d("Colocviu", "onRestoreInstanceState was called");
        if (savedInstanceState.containsKey(Constants.ALL_TERMS)) {
            allTerms.setText(savedInstanceState.getString(Constants.ALL_TERMS));
        } else {
            allTerms.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("Colocviu", "onDestroy was called");
        stopService(new Intent(this, Colocviu1_2Service.class));
        serviceStatus = Constants.SERVICE_STOPPED;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}