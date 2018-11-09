package pl.strazpozarna.inspekcja.testypsp.ListViewChapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.testypsp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import pl.strazpozarna.inspekcja.testypsp.ListItem;
import pl.strazpozarna.inspekcja.testypsp.Question;


public class ActivityChapters extends AppCompatActivity {

    ArrayList<Question> learnQuestionsArrayList;
    ArrayList<ListItem> tempArray;
    ListView listView;



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.chaptermenu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        switch (item.getItemId()) {
//
//            case R.id.action_star:
//                Intent intent = new Intent(getApplicationContext(), ActivityShowQuestions.class);
//                startActivity(intent);
//
//                break;
//            case R.id.action_rememebered:
//                Intent intent2 = new Intent(getApplicationContext(), ActivityShowRemembered.class);
//                startActivity(intent2);
//                break;
//
//            default:
//                finish();
//
//        }
//
//
//        return true;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

    }

    @Override
    protected void onStart() {

        learnQuestionsArrayList = new ArrayList<Question>();
        // Read from GSON
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("QuestionKey", "");
        learnQuestionsArrayList = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
        }.getType());

        tempArray = new ArrayList<ListItem>();


        //Create new Array


        addItemtoList("I.", 0, 56, 0, 0, 0);
        addItemtoList("II.", 57, 93, 1, 0, 0);
        addItemtoList("III.", 94, 272, 2, 0, 0);
        addItemtoList("IV.", 273, 294, 3, 0, 0);
        addItemtoList("V.", 295, 490, 4, 0, 0);
        addItemtoList("VI.", 491, 571, 5, 0, 0);
        addItemtoList("VII.", 572, 647, 6, 0, 0);
        addItemtoList("VIII.", 648, 661, 7, 0, 0);
        addItemtoList("IX.", 662, 686, 8, 0, 0);
        addItemtoList("X.", 687, 706, 9, 0, 0);
        addItemtoList("XI.", 707, 750, 10, 0, 0);
        addItemtoList("XII.", 751, 790, 11, 0, 0);
        addItemtoList("XIII.", 791, 854, 12, 0, 0);
        addItemtoList("XIV.", 855, 933, 13, 0, 0);
        addItemtoList("XV.", 934, 998, 14, 0, 0);


        // Create the adapter to convert the array to views
        LearnAdapter learnAdapter = new LearnAdapter(this, tempArray);

        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.chaptersListId);

        listView.setAdapter(learnAdapter);

        super.onStart();
    }


    public void addItemtoList(String chapterRome, int indexStart, int indexEnd, int chapterNumber, int doneCounter, int rememberedCounter) {
        String[] chapter = new String[15];
        chapter[0] = "Bezpieczeństwo pożarowe obiektów i budynków";
        chapter[1] = "Środki Gaśnicze Neutralizatory i Sorbenty";
        chapter[2] = "Wyposażenie Techniczne I Sprzęt";
        chapter[3] = "Prawa I Obowiązki Operatora Pojazdu / Sprzętu Pożarniczego";
        chapter[4] = "Taktyka działań gaśniczych";
        chapter[5] = "Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo techniczne i chemiczne";
        chapter[6] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo techniczne i chemiczne";
        chapter[7] = "Taktyka działań ratowniczych - dla jednostek podstawowych - Działania Poszukiwawczo-Ratownicze";
        chapter[8] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych - Działania Poszukiwawczo-Ratownicze";
        chapter[9] = "Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo wodne";
        chapter[10] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo wodno-nurkowe";
        chapter[11] = "Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo Wysokościowe";
        chapter[12] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo Wysokościowe";
        chapter[13] = "Łączność";
        chapter[14] = "Praca Stanowisk Kierowania";

        int totalCounter = (indexEnd - indexStart) + 1;


        for (int i = indexStart; i <= indexEnd; i++) {
            if (learnQuestionsArrayList.get(i).getNotify() != 0) {
                doneCounter++;
            }
            if (learnQuestionsArrayList.get(i).getStar() == 1) {
                rememberedCounter++;
            }

        }
        tempArray.add(new ListItem(chapterRome, indexStart, indexEnd, chapter[chapterNumber], totalCounter, doneCounter, rememberedCounter));
    }


}
