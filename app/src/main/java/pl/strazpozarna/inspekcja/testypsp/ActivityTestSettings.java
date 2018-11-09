package pl.strazpozarna.inspekcja.testypsp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.android.testypsp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ActivityTestSettings extends AppCompatActivity {


    ArrayList<Question> learnQuestionsArrayList;
    Button sort;
    int[] indexOfQuestions;
    int arrayLength = 20;

    //ODKOMENTOWAC PONIZSZE

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.testmenu, menu);
//
//
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//
//        switch (item.getItemId()) {
//
//            case R.id.action_reminder:
////                Intent intent = new Intent(getApplicationContext(), ActivitySettingTest.class);
//                Intent i = new Intent(this, ActivitySettingTest.class);
//                boolean show = false;
//                i.putExtra("hours", 2 + "");
//                i.putExtra("minutes", 23 + "");
//                i.putExtra("seconds", 23 + "");
//                String s;
//
//                if (show) {
//                    s = "true";
//                } else {
//                    s = "false";
//                }
//
//                i.putExtra("show", s);
//
//
//                startActivities(new Intent[]{i});
//            default:
//        }
//        return true;
//    }


    public void onCheckboxClicked(View view) {
        //This must be implemented

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_settings);

        sort = (Button) findViewById(R.id.sortButtonId);

        // Read from GSON
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("QuestionKey", "");
        learnQuestionsArrayList = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
        }.getType());


        sort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                generateQuestionsNumber();

                Intent intent = new Intent(getApplicationContext(), ActivityQuestionsTest.class);
                intent.putExtra("IndexStart", 0);
                intent.putExtra("IndexEnd", indexOfQuestions.length);
                intent.putExtra("SwitchIntent", 2);
                intent.putExtra("ArrayTest", generateQuestionsNumber());
                startActivity(intent);


            }
        });

    }


    public int[] generateQuestionsNumber() {

        indexOfQuestions = new int[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            indexOfQuestions[i] = makeRandomNumbers();

            if (!((CheckBox) findViewById(R.id.checkBox2)).isChecked()) {
                while (indexOfQuestions[i] >= 572 && indexOfQuestions[i] <= 647) {
                    Log.v("Stwierdzono wyjątek 2", indexOfQuestions[i] + "");
                    indexOfQuestions[i] = makeRandomNumbers();
                    Log.v("Po wyjątku 2", indexOfQuestions[i] + "");
                }
            }

            if (!((CheckBox) findViewById(R.id.checkBox3)).isChecked()) {
                while (indexOfQuestions[i] >= 662 && indexOfQuestions[i] <= 686) {
                    Log.v("Stwierdzono wyjątek 3", indexOfQuestions[i] + "");
                    indexOfQuestions[i] = makeRandomNumbers();
                    Log.v("Po wyjątku 3", indexOfQuestions[i] + "");
                }
            }

            if (!((CheckBox) findViewById(R.id.checkBox4)).isChecked()) {
                while (indexOfQuestions[i] >= 707 && indexOfQuestions[i] <= 750) {
                    Log.v("Stwierdzono wyjątek 4", indexOfQuestions[i] + "");
                    indexOfQuestions[i] = makeRandomNumbers();
                    Log.v("Po wyjątku 4", indexOfQuestions[i] + "");
                }
            }

            if (!((CheckBox) findViewById(R.id.checkBox5)).isChecked()) {
                while (indexOfQuestions[i] >= 791 && indexOfQuestions[i] <= 854) {
                    Log.v("Stwierdzono wyjątek 5", indexOfQuestions[i] + "");
                    indexOfQuestions[i] = makeRandomNumbers();
                    Log.v("Po wyjątku 5", indexOfQuestions[i] + "");
                }
            }

            if (!((CheckBox) findViewById(R.id.checkBox6)).isChecked()) {
                while (indexOfQuestions[i] >= 934 && indexOfQuestions[i] <= 998) {
                    Log.v("Stwierdzono wyjątek 6", indexOfQuestions[i] + "");
                    indexOfQuestions[i] = makeRandomNumbers();
                    Log.v("Po wyjątku 6", indexOfQuestions[i] + "");
                }
            }
        }

        Log.v("Random Array", Arrays.toString(indexOfQuestions));

        return indexOfQuestions;
    }


    public int makeRandomNumbers() {
        int min = 0;
        int max = 998;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
    }
}
