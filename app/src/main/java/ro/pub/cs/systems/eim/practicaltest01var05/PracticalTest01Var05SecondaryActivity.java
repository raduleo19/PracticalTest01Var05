package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {
    Button verifyButton, cancelButton;
    EditText text;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.verify_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        verifyButton = (Button) findViewById(R.id.verify_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        text = (EditText) findViewById(R.id.textView2);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.TEXT_VAL)) {
            String textIntend = intent.getStringExtra(Constants.TEXT_VAL);
            text.setText(textIntend);
        }

        verifyButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}