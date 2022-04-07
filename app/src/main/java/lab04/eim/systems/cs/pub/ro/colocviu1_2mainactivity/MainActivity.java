package lab04.eim.systems.cs.pub.ro.colocviu1_2mainactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText nextTerm, allTerms;
    Button addButton, computeButton;

    ButtonListener listener = new ButtonListener();
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String value = nextTerm.getText().toString();
            System.out.println(value);
            if (view.getId() == R.id.add_button && value != null) {
                if (allTerms.getText().toString().equals("0"))
                    allTerms.setText(value);
                else
                    allTerms.setText(allTerms.getText().toString() + " + " + value);
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
        allTerms.setText(String.valueOf(0));
    }
}