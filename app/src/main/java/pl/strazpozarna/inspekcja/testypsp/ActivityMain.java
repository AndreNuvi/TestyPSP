package pl.strazpozarna.inspekcja.testypsp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testypsp.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kuassivi.view.ProgressProfileView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pl.strazpozarna.inspekcja.testypsp.ListViewAllRemembered.ActivityShowRemembered;
import pl.strazpozarna.inspekcja.testypsp.ListViewAllStarQuestions.ActivityShowQuestions;
import pl.strazpozarna.inspekcja.testypsp.ListViewChapters.ActivityChapters;

import static pl.strazpozarna.inspekcja.testypsp.Util.tintMenuIcon;

public class ActivityMain extends AppCompatActivity {
    Button buttonChapters;
    Button buttonFullList;
    Button buttonRememberedQuestions;
    Button buttonTest;
    ProgressProfileView profile;
    ImageView grade1;
    ImageView grade2;
    TextView grade;
    LinearLayout gradeStatus;

    Database questionsDatabase;
    ArrayList<Question> learnQuestionsArrayList = new ArrayList<>();
    ArrayList<Question> progressAnswersArrayLiust = new ArrayList<>();

    SharedPreferences sharedPreferencesFirstRunAnn;
    boolean firstRunAnn;

    // variables for notification
    int hours;
    int minutes;
    int seconds;
    boolean show;

    // Shared preferences for notification
    SharedPreferences timeSharedPreferencesAnn;

    SharedPreferences.Editor timeEditor;
    // needed to close this activity from settingsActivity when press save
    public static Activity mainActivity;

    private static final String TAG = "MainActivity";

    private Calendar alarmStartTime = new GregorianCalendar();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appmenu, menu);


        //TODO You have to change keys  in 4 places after update

        //Check if app is first run after update
        SharedPreferences readFirstRun2 = getSharedPreferences("isFirstRunKey8", MODE_PRIVATE);
        boolean betaRun2 = readFirstRun2.getBoolean("FirstRun8", true);


        boolean isFirstRun2 = false;
        SharedPreferences.Editor editor12 = getSharedPreferences("isFirstRunKey8", MODE_PRIVATE).edit();
        editor12.putBoolean("FirstRun8", isFirstRun2);
        editor12.apply();

        Log.v("First run after update", String.valueOf(betaRun2));

        if (betaRun2 == true) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_priority_high_white_48dp));
            MenuItem menuItem = menu.findItem(R.id.action_info);


            //Color menu item with static method
            if (menuItem != null) {
                tintMenuIcon(this, menuItem, R.color.colorAccent);
            }

            //PUT CHANGES TO BASE
            SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
            String httpParamJSONList = prefs.getString("QuestionKey", "");
            learnQuestionsArrayList = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
            }.getType());

            //questionsData.add(new Question(120,"Dwoma motopompami pożarniczymi M16/8 przy założeniu pracy w obszarze parametrów nominalnych można napełnić zbiornik samochodu  o pojemności 18 m3 w czasie ok:","2,1 minuty","4,1 minuty","5,6 minuty","NO",3,0,26,0));
            //questionsData.add(new Question(157,"Ciśnienie próbne dla pożarniczego węża tłocznego W 52 to ciśnienie:","1,4 MPa","1,6 MPa","1,8 MPa","NO",3,0,63,0));
            //questionsData.add(new Question(159,"Maksymalne ciśnienie robocze pożarniczych węży tłocznych wynosi:","1,4 MPa","1,2 MPa","1,6 MPa","NO",2,0,65,0));
            //questionsData.add(new Question(163,"Wartość ciśnienia roboczego dla pożarniczego węża tłocznego W75 wynosi:","1,2 MPa","1,8 MPa","4,0 MPa","NO",1,0,69,0));
            //questionsData.add(new Question(484,"Na samochodzie GBA 2,5/16 zainstalowano działko o wydatku 1600 l/min. Ile czasu można podawać skuteczny prąd gaśniczy z tego działka przy zasilaniu samochodu z hydrantu o wydatku 10 l/s?","Około 15 min","Około 25 min","Około 5 min","NO",2,0,189,0));

            learnQuestionsArrayList.set(119, new Question(learnQuestionsArrayList.get(119).getIndexGlobal(), learnQuestionsArrayList.get(119).getQuestion(), "2,1 minut", "4,1 minut", "5,6 minut", learnQuestionsArrayList.get(119).getAnswerD(), learnQuestionsArrayList.get(119).getCorrectAnswer(), learnQuestionsArrayList.get(119).getNotify(), learnQuestionsArrayList.get(119).getIndexLocal(), learnQuestionsArrayList.get(119).getStar()));
            learnQuestionsArrayList.set(156, new Question(learnQuestionsArrayList.get(156).getIndexGlobal(), learnQuestionsArrayList.get(156).getQuestion(), "1,4 MPa", "1,6 MPa", "1,8 MPa", learnQuestionsArrayList.get(156).getAnswerD(), learnQuestionsArrayList.get(156).getCorrectAnswer(), learnQuestionsArrayList.get(156).getNotify(), learnQuestionsArrayList.get(156).getIndexLocal(), learnQuestionsArrayList.get(156).getStar()));
            learnQuestionsArrayList.set(158, new Question(learnQuestionsArrayList.get(158).getIndexGlobal(), learnQuestionsArrayList.get(158).getQuestion(), "1,4 MPa", "1,2 MPa", "1,6 MPa", learnQuestionsArrayList.get(158).getAnswerD(), learnQuestionsArrayList.get(158).getCorrectAnswer(), learnQuestionsArrayList.get(158).getNotify(), learnQuestionsArrayList.get(158).getIndexLocal(), learnQuestionsArrayList.get(158).getStar()));
            learnQuestionsArrayList.set(162, new Question(learnQuestionsArrayList.get(162).getIndexGlobal(), learnQuestionsArrayList.get(162).getQuestion(), "1,2 MPa", "1,8 MPa", "4,0 MPa", learnQuestionsArrayList.get(162).getAnswerD(), learnQuestionsArrayList.get(162).getCorrectAnswer(), learnQuestionsArrayList.get(162).getNotify(), learnQuestionsArrayList.get(162).getIndexLocal(), learnQuestionsArrayList.get(162).getStar()));
            learnQuestionsArrayList.set(483, new Question(learnQuestionsArrayList.get(483).getIndexGlobal(), learnQuestionsArrayList.get(483).getQuestion(), "Około 1,5 min", "Około 2,5 min", "Około 5 min", learnQuestionsArrayList.get(483).getAnswerD(), learnQuestionsArrayList.get(483).getCorrectAnswer(), learnQuestionsArrayList.get(483).getNotify(), learnQuestionsArrayList.get(483).getIndexLocal(), learnQuestionsArrayList.get(483).getStar()));

            String questionJsonList = new Gson().toJson(learnQuestionsArrayList);
            prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("QuestionKey", questionJsonList);
            editor.apply();


        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_information_outline_white_48dp));

        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_info:
                Intent intent = new Intent(getApplicationContext(), ActivityInfo.class);
                startActivity(intent);
            default:
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // SharedPreferences for first run
//        sharedPreferencesFirstRunAnn = getSharedPreferences("first_run", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editorAnn = sharedPreferencesFirstRunAnn.edit();
//        firstRunAnn = sharedPreferencesFirstRunAnn.getBoolean("firstRun", true);
//
//        //Shared preferences for time scheduled notification
//        timeSharedPreferencesAnn = getSharedPreferences("time_sp", MODE_PRIVATE);
//        timeEditor = timeSharedPreferencesAnn.edit();
//
//        //Verify if application is run first time
//        if(firstRunAnn) {
//            // Set first run to false
//            editorAnn.putBoolean("firstRun", false);
//            editorAnn.commit();
//
//            //set default time for scheduled notification - only when run 1st time
//            hours = 14;
//            minutes = 30;
//            seconds = 0;
//            show = true;
//            saveTimeToJson(timeEditor);
//
//        } else {
//
//            Log.v("MainActivity","NOT FIRST RUN");
//
//            //set time for scheduled notification from shared preferences
//            setTimeValuesFromSharedPrefrences();
//        }
//
//        //needed to close this activity from settingsActivity when press save
//        mainActivity = this;
//
//        //show notification only if "show" is true (switch in settings is turned on)
//        if (show) {
//
//            scheduleNotification(getNotification());
//
//        }



//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//
//        mAdView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAdView.setVisibility(View.INVISIBLE);
//                Toast.makeText(ActivityMain.this, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


        buttonChapters = (Button) findViewById(R.id.buttonChapters);
        buttonFullList = (Button) findViewById(R.id.buttonFullList);
        buttonRememberedQuestions = (Button) findViewById(R.id.buttonRememberedList);
        buttonTest = (Button) findViewById(R.id.buttonCustomTestId);
        gradeStatus = (LinearLayout) findViewById(R.id.idGradeText);

        gradeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "Twoja obecna ranga", Toast.LENGTH_SHORT).show();
            }
        });


        //Check if app is first time run
        SharedPreferences readFirstRun = getSharedPreferences("isFirstRunKey", MODE_PRIVATE);
        boolean betaRun = readFirstRun.getBoolean("FirstRun", true);


        boolean isFirstRun = false;
        SharedPreferences.Editor editor1 = getSharedPreferences("isFirstRunKey", MODE_PRIVATE).edit();
        editor1.putBoolean("FirstRun", isFirstRun);
        editor1.apply();

        Log.v("Is first run?", String.valueOf(betaRun));


        // If app first run initialize database
        if (betaRun == true) {
            questionsDatabase = new Database();
            questionsDatabase.generateOriginDatabase();


            String questionJsonList = new Gson().toJson(questionsDatabase.getQuestionsData());
            SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("QuestionKey", questionJsonList);
            editor.apply();

        }


        buttonChapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityChapters.class);
                startActivity(intent);
            }
        });

        buttonFullList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowQuestions.class);
                startActivity(intent);
            }
        });
        buttonRememberedQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowRemembered.class);
                startActivity(intent);
            }
        });

        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityTestSettings.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        setGrade(progressTotal());

        //setGrade(320);
        //TEST

//        TextView grade = (TextView) findViewById(R.id.grade);
//        ImageView grade1 = (ImageView) findViewById(R.id.grade1);
//        ImageView grade2 = (ImageView) findViewById(R.id.grade2);
//
//        grade.setText("STRAŻAK");
//        grade1.setImageResource(R.drawable.aspirant_right);
//        grade2.setImageResource(R.drawable.starszy_kapitan_left);


        //Set Logo Progress Bar
        profile = (ProgressProfileView) findViewById(R.id.profile);
        profile.setProgress(progressTotal());
        profile.startAnimation();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMain.this, "Poprawnych odpowiedzi: " + progressTotal() + " / 999", Toast.LENGTH_SHORT).show();
            }
        });
        super.onStart();
    }

    private int progressTotal() {
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("QuestionKey", "");
        progressAnswersArrayLiust = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
        }.getType());

        int progress = 0;

        for (int i = 0; i < progressAnswersArrayLiust.size(); i++) {
            if (progressAnswersArrayLiust.get(i).getNotify() != 0) {
                progress++;
            }
        }
        return progress;

    }

    private void setGrade(int progress) {


        int[] digits = new int[]{53, 106, 159, 212, 265, 318, 371, 424, 477, 530, 583, 636, 689, 742, 795, 848, 901, 954, 998};

        String[] stopnie = new String[]{"STRAŻAK", "STARSZY STRAŻAK", "SEKCYJNY", "STARSZY SEKCYJNY", "MŁODSZY OGNIOMISTRZ", "OGNIOMISTRZ", "STARSZY OGNIOMISTRZ", "MŁODSZY ASPIRANT", "ASPIRANT", "STARSZY ASPIRANT"
                , "ASPIRANT SZTABOWY", "MŁODSZY KAPITAN", "KAPITAN", "STARSZY KAPITAN", "MŁODSZY BRYGADIER", "BRYGADIER", "STARSZY BRYGADIER", "NADBRYGADIER", "GENERAŁ BRYGADIER", "STRAŻAK SAM"};

        int[] srce = new int[]{R.drawable.strazak_left, R.drawable.starszy_strazak_left, R.drawable.sekcyjny_left, R.drawable.starszy_sekcyjny_left, R.drawable.mlodszy_ogniomistrz_left, R.drawable.ogniomistrz_left, R.drawable.starszy_ogniomistrz_left,
                R.drawable.mlodszy_aspirant_left, R.drawable.aspirant_left, R.drawable.starszy_aspirant_left, R.drawable.aspirant_sztabowy_left, R.drawable.mlodszy_kapitan_left, R.drawable.kapitan_left, R.drawable.starszy_kapitan_left, R.drawable.mlodszy_brygadier_left,
                R.drawable.brygadier_left, R.drawable.starszy_brygadier_left, R.drawable.nadbrygadier_left, R.drawable.general_brygadier_left, R.drawable.sam};

        int[] srce2 = new int[]{R.drawable.strazak_right, R.drawable.starszy_strazak_right, R.drawable.sekcyjny_right, R.drawable.starszy_sekcyjny_right, R.drawable.mlodszy_ogniomistrz_right, R.drawable.ogniomistrz_right, R.drawable.starszy_ogniomistrz_right,
                R.drawable.mlodszy_aspirant_right, R.drawable.aspirant_right, R.drawable.starszy_aspirant_right, R.drawable.aspirant_sztabowy_right, R.drawable.mlodszy_kapitan_right, R.drawable.kapitan_right, R.drawable.starszy_kapitan_right, R.drawable.mlodszy_brygadier_right,
                R.drawable.brygadier_right, R.drawable.starszy_brygadier_right, R.drawable.nadbrygadier_right, R.drawable.general_brygadier_right, R.drawable.sam};


        if (progress <= digits[0]) {
            setResources(stopnie[0], srce[0], srce2[0]);

        } else if (progress > digits[0] && progress <= digits[1]) {
            setResources(stopnie[1], srce[1], srce2[1]);

        } else if (progress > digits[1] && progress <= digits[2]) {
            setResources(stopnie[2], srce[2], srce2[2]);

        } else if (progress > digits[2] && progress <= digits[3]) {
            setResources(stopnie[3], srce[3], srce2[3]);

        } else if (progress > digits[3] && progress <= digits[4]) {
            setResources(stopnie[4], srce[4], srce2[4]);

        } else if (progress > digits[4] && progress <= digits[5]) {
            setResources(stopnie[5], srce[5], srce2[5]);

        } else if (progress > digits[5] && progress <= digits[6]) {
            setResources(stopnie[6], srce[6], srce2[6]);

        } else if (progress > digits[6] && progress <= digits[7]) {
            setResources(stopnie[7], srce[7], srce2[7]);

        } else if (progress > digits[7] && progress <= digits[8]) {
            setResources(stopnie[8], srce[8], srce2[8]);

        } else if (progress > digits[8] && progress <= digits[9]) {
            setResources(stopnie[9], srce[9], srce2[9]);

        } else if (progress > digits[9] && progress <= digits[10]) {
            setResources(stopnie[10], srce[10], srce2[10]);

        } else if (progress > digits[10] && progress <= digits[11]) {
            setResources(stopnie[11], srce[11], srce2[11]);

        } else if (progress > digits[11] && progress <= digits[12]) {
            setResources(stopnie[12], srce[12], srce2[12]);

        } else if (progress > digits[12] && progress <= digits[13]) {
            setResources(stopnie[13], srce[13], srce2[13]);

        } else if (progress > digits[13] && progress <= digits[14]) {
            setResources(stopnie[14], srce[14], srce2[14]);

        } else if (progress > digits[14] && progress <= digits[15]) {
            setResources(stopnie[15], srce[15], srce2[15]);

        } else if (progress > digits[15] && progress <= digits[16]) {
            setResources(stopnie[16], srce[16], srce2[16]);

        } else if (progress > digits[16] && progress <= digits[17]) {
            setResources(stopnie[17], srce[17], srce2[17]);

        } else if (progress > digits[17] && progress < digits[18]) {
            setResources(stopnie[18], srce[18], srce2[18]);

        } else if (progress == digits[18]) {
            setResources(stopnie[19], srce[19], srce2[19]);
            profile = findViewById(R.id.profile);
            profile.setImageResource(R.drawable.sam);
        }

    }

    private void setResources(String stopien, int src, int src2) {

        TextView grade = findViewById(R.id.grade);
        ImageView grade1 =  findViewById(R.id.grade1);
        ImageView grade2 = findViewById(R.id.grade2);

        grade.setText(stopien);
        grade1.setImageResource(src2);
        grade2.setImageResource(src);
    }

//    //TODO: W tej metodzie zmieniłam "czas" z intów na zmienne
//    private void scheduleNotification(Notification notification) {
//        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//
//        //--
//        alarmStartTime.set(Calendar.HOUR_OF_DAY, hours);
//        alarmStartTime.set(Calendar.MINUTE, minutes);
//        alarmStartTime.set(Calendar.SECOND, seconds);
//
//        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);
//    }
//
//    //Save time for shedule notification into shared preferences
//    private void saveTimeToJson(SharedPreferences.Editor timeEditor){
//        // Writing data to SharedPreferences
//        timeEditor.putString("hoursKey", String.valueOf(hours));
//        timeEditor.putString("minutesKey", String.valueOf(minutes));
//        timeEditor.putString("secondsKey", String.valueOf(seconds));
//        timeEditor.putBoolean("showKey", show);
//
//        timeEditor.commit();
//    }
//
//    //Set time for shedule notification from shared preferences
//    private void setTimeValuesFromSharedPrefrences(){
//        hours = Integer.valueOf(timeSharedPreferencesAnn.getString("hoursKey","12"));
//        minutes = Integer.valueOf(timeSharedPreferencesAnn.getString("minutesKey","0"));
//        seconds = Integer.valueOf(timeSharedPreferencesAnn.getString("secondsKey","0"));
//        show = timeSharedPreferencesAnn.getBoolean("showKey",true);
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private Notification getNotification() {
//
//
//        //Ten fragment jest potrzebny aby zdefiniowac co sie stanie gdy klikne w notyfikacje
//        //Ja otwieram activity: PoemActivity
//        //Przekazuje niezbedne rzeczy w intencie
//        //Tworze "pIntent" - jest niezbedny w notyfikacji
//        //"pIntent" zostanie pozniej przypisany do buildera (przed ostatnia linijka tej metody)
//        Intent intent = new Intent(this, ActivityTestSettings.class);
//
////        intent.putExtra("poemID", randomId+"");
////        intent.putExtra("poemText", randomPoem);
//
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        //Tutaj usatwiasz co pojawi sie w notyfikacji
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("Pożarniczy Test Wiedzy");
//        builder.setContentText("Zacznij dzisiejszy test");
//        builder.setSmallIcon(R.drawable.logo_material);
//
//
//        builder.setContentIntent(pIntent);
//
//        return builder.build();
//    }

//    //Ja w notyfikacji pokazuje losowy wierszyk
//    private String getRandomPoem(){
//        Random r = new Random();
//        randomId = r.nextInt((poemsArrayList.size()-1)-1) + 1;
//
//        Log.v("RANDOM", "value: "+randomId);
//        randomPoem = poemsArrayList.get(randomId).getPoemText();
//        Log.v("RANDOM", "text: "+randomPoem);
//
//        return randomPoem;
//    }

//    private int getInterval() {
//        int days = 1;
//        int hours = 24;
//        int minutes = 60;
//        int seconds = 60;
//        int milliseconds = 1000;
//        int repeatMS = days * hours * minutes * seconds * milliseconds;
//        return repeatMS;
//    }


}
