package lab04.eim.systems.cs.pub.ro.colocviu1_2mainactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Colocviu1_2SecondaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu12_secondary);


        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.ALL_TERMS)) {
            String all_terms =  intent.getStringExtra(Constants.ALL_TERMS);

            int sum = 0;
            for (int i = 0; i < all_terms.length(); i++) {
                if (Character.isDigit(all_terms.charAt(i)))
                    sum =   sum + Character.getNumericValue(all_terms.charAt(i));
            }
            setResult(Integer.parseInt(String.valueOf(sum)), null);

            finish();
        }

    }
}