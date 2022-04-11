package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    Button topLeftButton, topRightButton, bottomLeftButton, bottomRightButton, centerButton, navigateButton;
    EditText textView;
    int numClicks = 0;
    int serviceStatus;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.top_left:
                    textView.setText(textView.getText().toString() + " " + topLeftButton.getText());
                    numClicks++;
                    break;
                case R.id.top_right:
                    textView.setText(textView.getText().toString() + " " + topRightButton.getText());
                    numClicks++;
                    break;
                case R.id.center:
                    textView.setText(textView.getText().toString() + " " + centerButton.getText());
                    numClicks++;
                    break;
                case R.id.bottom_left:
                    textView.setText(textView.getText().toString() + " " + bottomLeftButton.getText());
                    numClicks++;
                    break;
                case R.id.bottom_right:
                    textView.setText(textView.getText().toString() + " " + bottomRightButton.getText());
                    numClicks++;
                    break;
                case R.id.navigate_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                    intent.putExtra(Constants.TEXT_VAL, textView.getText().toString());
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
            if(numClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus ==Constants.SERVICE_STOPPED)

            {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intent.putExtra(Constants.TEXT_VAL, textView.getText().toString());
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);
        intentFilter.addAction(Constants.SEND_ACTION);
        serviceStatus = Constants.SERVICE_STOPPED;
        topLeftButton = (Button) findViewById(R.id.top_left);
        topRightButton = (Button) findViewById(R.id.top_right);
        bottomLeftButton = (Button) findViewById(R.id.bottom_left);
        bottomRightButton = (Button) findViewById(R.id.bottom_right);
        centerButton = (Button) findViewById(R.id.center);
        navigateButton = (Button) findViewById(R.id.navigate_button);

        textView = (EditText) findViewById(R.id.textView);

        topLeftButton.setOnClickListener(buttonClickListener);
        topRightButton.setOnClickListener(buttonClickListener);
        bottomLeftButton.setOnClickListener(buttonClickListener);
        bottomRightButton.setOnClickListener(buttonClickListener);
        centerButton.setOnClickListener(buttonClickListener);
        navigateButton.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.CLICKS_COUNT)) {
                numClicks = savedInstanceState.getInt(Constants.CLICKS_COUNT);
                Toast.makeText(this, "Saved value: " + numClicks, Toast.LENGTH_LONG).show();
            } else {
                numClicks = 0;
            }
        }
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

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var05Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Constants.CLICKS_COUNT, numClicks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.CLICKS_COUNT)) {
            numClicks = savedInstanceState.getInt(Constants.CLICKS_COUNT);
            Toast.makeText(this, "Saved value: " + numClicks, Toast.LENGTH_LONG).show();
        } else {
            numClicks = 0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            numClicks = 0;
            textView.setText("");
        }
    }
}