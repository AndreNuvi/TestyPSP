package pl.strazpozarna.inspekcja.testypsp.ListViewChapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.android.testypsp.R;

import java.util.ArrayList;

import pl.strazpozarna.inspekcja.testypsp.ActivityQuestionsLearn;
import pl.strazpozarna.inspekcja.testypsp.ListItem;

/**
 * Created by Andre on 2017-02-23.
 */

public class LearnAdapter extends ArrayAdapter<ListItem> {

    //Chapter variable;
    String actualChapter;
    String actualChapterTitle;


    RoundCornerProgressBar progress1;
    TextView chapterTitleTextView;
    TextView chapterNumberTextView;
    TextView correctAnswerCounterTextView;
    TextView wrongAnswerCounterTextView;
    TextView totaltCounterTextView;
    LinearLayout infoLinear;

    ListItem chapterTitle;
    LinearLayout clickChapterLineraLayout;

    Intent intent;


    public LearnAdapter(Context context, ArrayList<ListItem> chapters) {
        super(context, 0, chapters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.learn_item, parent, false);
        }

        // Get the data item for this position
        chapterTitle = getItem(position);

        // Lookup view for data population
        chapterNumberTextView = (TextView) convertView.findViewById(R.id.chapterItemId);
        chapterTitleTextView = (TextView) convertView.findViewById(R.id.titleChapterItemid);
        correctAnswerCounterTextView = (TextView) convertView.findViewById(R.id.goodItemId);
        wrongAnswerCounterTextView = (TextView) convertView.findViewById(R.id.wrongItemId);
        totaltCounterTextView = (TextView) convertView.findViewById(R.id.totalItemId);
        clickChapterLineraLayout = (LinearLayout) convertView.findViewById(R.id.itemChapterClick);
        progress1 = (RoundCornerProgressBar) convertView.findViewById(R.id.progress_bar);
        infoLinear =(LinearLayout) convertView.findViewById(R.id.infoLinear);


        // Attach the click event handler
        chapterTitleTextView.setTag(position);

        chapterTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                ListItem listItem = getItem(position);

                //Intent declarations
                intent = new Intent(getContext(), ActivityQuestionsLearn.class);
                intent.putExtra("IndexStart", listItem.getStartChapter());
                intent.putExtra("IndexEnd", listItem.getEndChapter());
                intent.putExtra("SwitchIntent", 1);
                getContext().startActivity(intent);


            }
        });

        // Attach the click event handler
        chapterNumberTextView.setTag(position);

        chapterNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                ListItem listItem = getItem(position);

                //Intent declarations
                intent = new Intent(getContext(), ActivityQuestionsLearn.class);
                intent.putExtra("IndexStart", listItem.getStartChapter());
                intent.putExtra("IndexEnd", listItem.getEndChapter());
                intent.putExtra("SwitchIntent", 1);
                getContext().startActivity(intent);


            }
        });

        // Attach the click event handler
        infoLinear.setTag(position);

        infoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                ListItem listItem = getItem(position);

               Toast.makeText(getContext(), String.format("Ilość pytań: %d, Poprawne: %d, Zapamiętane: %d", listItem.getQuestionQuantity(),listItem.getDoneQuantity(),listItem.getRememberedQuantity()), Toast.LENGTH_SHORT).show();





            }
        });

        // Attach the click event handler
        progress1.setTag(position);

        progress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                ListItem listItem = getItem(position);

                Toast.makeText(getContext(), String.format("Ilość pytań: %d, Poprawne: %d, Zapamiętane: %d", listItem.getQuestionQuantity(),listItem.getDoneQuantity(),listItem.getRememberedQuantity()), Toast.LENGTH_SHORT).show();





            }
        });


// Populate the data into the template view using the data object

        chapterNumberTextView.setText(String.valueOf(chapterTitle.getChapterIndex()));
        chapterTitleTextView.setText(String.valueOf(chapterTitle.getTitleChapter()));
        correctAnswerCounterTextView.setText(String.valueOf(chapterTitle.getQuestionQuantity()));
        wrongAnswerCounterTextView.setText(String.valueOf(chapterTitle.getDoneQuantity()));
        totaltCounterTextView.setText(String.valueOf(chapterTitle.getRememberedQuantity()));

        progress1.setMax(chapterTitle.getQuestionQuantity());
        progress1.setSecondaryProgress(chapterTitle.getDoneQuantity());
        progress1.setProgress(chapterTitle.getRememberedQuantity());

        return convertView;


    }

//    private void fillChapter() {
//        String[] chapter = new String[15];
//        chapter[0] = "Bezpieczeństwo pożarowe obiektów i budynków";
//        chapter[1] = "Środki Gaśnicze Neutralizatory i Sorbenty";
//        chapter[2] = "Wyposażenie Techniczne I Sprzęt";
//        chapter[3] = "Prawa I Obowiązki Operatora Pojazdu / Sprzętu Pożarniczego";
//        chapter[4] = "Taktyka działań gaśniczych";
//        chapter[5] = "Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo techniczne i chemiczne";
//        chapter[6] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo techniczne i chemiczne";
//        chapter[7] = "Taktyka działań ratowniczych - dla jednostek podstawowych - Działania Poszukiwawczo-Ratownicze";
//        chapter[8] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych - Działania Poszukiwawczo-Ratownicze";
//        chapter[9] = "Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo wodne";
//        chapter[10] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo wodno-nurkowe";
//        chapter[11] = "Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo Wysokościowe";
//        chapter[12] = "Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo Wysokościowe";
//        chapter[13] = "Łączność";
//        chapter[14] = "Praca Stanowisk Kierowania";
//
//        String[] chapterNumber = new String[15];
//        chapterNumber[0] = "I.";
//        chapterNumber[1] = "II.";
//        chapterNumber[2] = "III.";
//        chapterNumber[3] = "IV.";
//        chapterNumber[4] = "V.";
//        chapterNumber[5] = "VI.";
//        chapterNumber[6] = "VII.";
//        chapterNumber[7] = "VIII.";
//        chapterNumber[8] = "IX.";
//        chapterNumber[9] = "X.";
//        chapterNumber[10] = "XI.";
//        chapterNumber[11] = "XII.";
//        chapterNumber[12] = "XIII.";
//        chapterNumber[13] = "XIV.";
//        chapterNumber[14] = "XV.";
//
//
//        switch (chapterTitle.getIndexGlobal()) {
//            case 1:
//                actualChapter = chapter[0];
//                actualChapterTitle = chapterNumber[0];
//                break;
//            case 2:
//                actualChapter = chapter[1];
//                actualChapterTitle = chapterNumber[1];
//                break;
//            case 3:
//                actualChapter = chapter[2];
//                actualChapterTitle = chapterNumber[2];
//                break;
//            case 4:
//                actualChapter = chapter[3];
//                actualChapterTitle = chapterNumber[3];
//                break;
//            case 5:
//                actualChapter = chapter[4];
//                actualChapterTitle = chapterNumber[4];
//                break;
//            case 6:
//                actualChapter = chapter[5];
//                actualChapterTitle = chapterNumber[5];
//                break;
//            case 7:
//                actualChapter = chapter[6];
//                actualChapterTitle = chapterNumber[6];
//                break;
//            case 8:
//                actualChapter = chapter[7];
//                actualChapterTitle = chapterNumber[7];
//                break;
//            case 9:
//                actualChapter = chapter[8];
//                actualChapterTitle = chapterNumber[8];
//                break;
//            case 10:
//                actualChapter = chapter[9];
//                actualChapterTitle = chapterNumber[9];
//                break;
//            case 11:
//                actualChapter = chapter[10];
//                actualChapterTitle = chapterNumber[10];
//                break;
//            case 12:
//                actualChapter = chapter[11];
//                actualChapterTitle = chapterNumber[11];
//                break;
//            case 13:
//                actualChapter = chapter[12];
//                actualChapterTitle = chapterNumber[12];
//                break;
//            case 14:
//                actualChapter = chapter[13];
//                actualChapterTitle = chapterNumber[13];
//                break;
//            case 15:
//                actualChapter = chapter[14];
//                actualChapterTitle = chapterNumber[14];
//                break;
//        }

//        chapterTitel.setText(String.valueOf(actualChapter));
//        chapterNumberTextView.setText(String.valueOf(actualChapterTitle));

}





