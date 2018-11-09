package pl.strazpozarna.inspekcja.testypsp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.android.testypsp.R;

public class ActivitySettingTest extends AppCompatActivity {

    EditText timeEditTextView;
    int hours;
    int minutes;
    int seconds;
    boolean show;

    SharedPreferences timeSharedPreferences;
    SharedPreferences.Editor timeEditor;

    Switch showNotificationSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_test);
        setDataFromPreviousActivityIntent();

        timeSharedPreferences = getSharedPreferences("time_sp", MODE_PRIVATE);
        timeEditor = timeSharedPreferences.edit();

        //Find EditText view for last period date
        timeEditTextView = (EditText) findViewById(R.id.time_edit_text_view);

        timeEditTextView.setText(getTimeString());
        addListenerOnButton(timeEditTextView);

        //Find save button
        Button saveButton = (Button) findViewById(R.id.save_button);

        //Handle click on save
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimeToJson(timeEditor);

                //closing previous activity. Only when press save
                ActivityMain.mainActivity.finish();


                Intent i = new Intent(ActivitySettingTest.this, ActivityMain.class);

                startActivity(i);
                finish();
            }
        });

        //Find the CANCEL button View
        Button cancel = (Button) findViewById(R.id.cancel_button);

        // Set a click listener on CANCEL button View
        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                //Create intent for general result
                Intent i = new Intent(ActivitySettingTest.this, ActivityMain.class);
                //Start GeneralResult activity
                startActivity(i);
                //it prevent user to enter this activity when press back button
                finish();
            }
        });

        // Find switch
        showNotificationSwitch = (Switch) findViewById(R.id.show_notification);

        setSwitch(show);

        showNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (showNotificationSwitch.isChecked()){
                    show = true;
                    timeEditTextView.setEnabled(true);
                    timeEditTextView.setTextColor(ContextCompat.getColor(ActivitySettingTest.this, R.color.colorPrimary));
                }
                else{
                    show = false;
                    timeEditTextView.setEnabled(false);
                    timeEditTextView.setTextColor(Color.parseColor("#7f7f7f"));
                }
            }
        });
    }

    public void addListenerOnButton(EditText lastPeriodDateView) {
        lastPeriodDateView.setInputType(InputType.TYPE_NULL);


        lastPeriodDateView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchTimePicker();
            }
        });

    }

    // Launch Time Picker Dialog
    private void launchTimePicker() {
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        hours = hourOfDay;
                        minutes = minute;
                        seconds = 0;

                        final EditText timeEditTextView = (EditText) findViewById(R.id.time_edit_text_view);
                        timeEditTextView.setText(new StringBuilder().append(hours).append(" : ").append(convertSingleMinuteToTwoNumbers(minutes))
                        );
                    }
                }, hours, minutes, false
        );
        tpd.show();
    }

    private String convertSingleMinuteToTwoNumbers(int min){
        String twoNumbersMin;
        if (min<10){
            twoNumbersMin = "0"+min;
        }
        else{
            twoNumbersMin = String.valueOf(min);
        }

        return twoNumbersMin;
    }

    private void setDataFromPreviousActivityIntent() {
        hours = Integer.valueOf(getIntent().getStringExtra("hours"));
        minutes = Integer.valueOf(getIntent().getStringExtra("minutes"));
        seconds = Integer.valueOf(getIntent().getStringExtra("seconds"));
        String s;
        s = getIntent().getStringExtra("show");
        if (s.equals("true")){
            show = true;
        }
        else {
            show = false;
        }

    }

    private void saveTimeToJson(SharedPreferences.Editor timeEditor){
        // Writing data to SharedPreferences
        timeEditor.putString("hoursKey", String.valueOf(hours));
        timeEditor.putString("minutesKey", String.valueOf(minutes));
        timeEditor.putString("secondsKey", String.valueOf(seconds));
        timeEditor.putBoolean("showKey", show);

        timeEditor.commit();
    }

    //It kill activity on back button press
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private String getTimeString(){
        String time = hours + " : " + convertSingleMinuteToTwoNumbers(minutes);
        return time;
    }

    private void setSwitch(boolean show){
        if (show){
            showNotificationSwitch.setChecked(true);
            timeEditTextView.setEnabled(true);
            timeEditTextView.setTextColor(ContextCompat.getColor(ActivitySettingTest.this, R.color.colorPrimary));
        }
        else{
            showNotificationSwitch.setChecked(false);
            timeEditTextView.setEnabled(false);
            timeEditTextView.setTextColor(Color.parseColor("#7f7f7f"));

        }
    }
}
