package pl.strazpozarna.inspekcja.testypsp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testypsp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;


public class ActivityQuestionsLearn extends AppCompatActivity {
    TextView questionCounter;
    TextView question;
    TextView answerA;
    TextView answerB;
    TextView answerC;
    TextView questionChaper;
    TextView questionCorrectAnswerCounter;
    TextView questionWrongAnswerCounter;
    TextView questionStar;

    LinearLayout chapterMessage;
    LinearLayout starQuestion;
    LinearLayout displayQuestionCounter;


    View viewA;
    View viewB;
    View viewC;

    //Buttons
    ImageButton next;
    ImageButton prev;
    ImageButton imageButton;


    //Checked answer
    int answer;

    //Question index
    int index;
    int indexStart;
    int indexEnd;
    int switcher;

    //Chapter variable;
    String actualChapter;
    String numberChapter;

    //Star variable
    int star;

    ArrayList<Question> learnQuestionsArrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_reset:
                learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 0, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                colorNotify();
                savetoGSON();

                break;

            default:
                finish();

        }


        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_learn);


        // Initialize Fields
        questionCounter = (TextView) findViewById(R.id.questionTestCounterID);
//        questionCorrectAnswerCounter = (TextView) findViewById(R.id.questionTestScoreCorrectId);
//        questionWrongAnswerCounter = (TextView) findViewById(R.id.questionTestScoreWrongId);

        //imageButton = (ImageButton) findViewById(R.id.imageButtonId);

        question = (TextView) findViewById(R.id.questionId);
        answerA = (TextView) findViewById(R.id.answerAId);
        answerB = (TextView) findViewById(R.id.answerBId);
        answerC = (TextView) findViewById(R.id.answerCId);
        questionChaper = (TextView) findViewById(R.id.questionChapterId);
        questionStar = (TextView) findViewById(R.id.questionTestScoreFlagId);

        chapterMessage = (LinearLayout) findViewById(R.id.chapterMessageId);
        starQuestion = (LinearLayout) findViewById(R.id.flagQuestionId);
        displayQuestionCounter = (LinearLayout) findViewById(R.id.dispayQuestionCounterId);

        viewA = findViewById(R.id.viewAId);
        viewB = findViewById(R.id.viewBId);
        viewC = findViewById(R.id.viewCId);

        next = (ImageButton) findViewById(R.id.nextButtonId);
        prev = (ImageButton) findViewById(R.id.prevButtonId);
        imageButton = (ImageButton) findViewById(R.id.imageButtonId);

        // Read from GSON
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("QuestionKey", "");
        learnQuestionsArrayList = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
        }.getType());


        //Get index from previous Activity
        final Intent intent = getIntent();
        indexStart = intent.getIntExtra("IndexStart", 0);
        indexEnd = intent.getIntExtra("IndexEnd", 0);
        switcher = intent.getIntExtra("SwitchIntent", 0);

        //Initialize index first time;
        index = indexStart;


        //Set first question
        nextQuestion(index);

        //Update Counter
//        updateCounter();

        //Update Chapter
        updateChapter(index);

        //Update star
        updateStar();


        //Headers
        chapterMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityQuestionsLearn.this, actualChapter, Toast.LENGTH_SHORT).show();
            }
        });
        starQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityQuestionsLearn.this, "Ilość zapamiętanych pytań", Toast.LENGTH_SHORT).show();
            }
        });
        displayQuestionCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityQuestionsLearn.this, "Licznik dostępnych pytań", Toast.LENGTH_SHORT).show();
            }
        });


        //Answers
        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 1;
                if (answer == learnQuestionsArrayList.get(index).getCorrectAnswer()) {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 1, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                    colorNotify();
//                    updateCounter();
                    savetoGSON();
                } else {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 2, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                    colorNotify();
//                    updateCounter();
                    savetoGSON();
                }
            }
        });
        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 2;
                if (answer == learnQuestionsArrayList.get(index).getCorrectAnswer()) {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 3, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                    colorNotify();
//                    updateCounter();
                    savetoGSON();
                } else {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 4, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                    colorNotify();
//                    updateCounter();
                    savetoGSON();
                }
            }
        });
        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 3;
                if (answer == learnQuestionsArrayList.get(index).getCorrectAnswer()) {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 5, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                    colorNotify();
//                    updateCounter();
                    savetoGSON();

                } else {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), 6, learnQuestionsArrayList.get(index).getIndexLocal(), learnQuestionsArrayList.get(index).getStar()));
                    colorNotify();
//                    updateCounter();
                    savetoGSON();
                }
            }
        });


        //Buttons next and Prev Handle
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < indexEnd) {
                    if (switcher == 1 || switcher == 2) {
                        index++;
                        next.setClickable(true);
                        next.setVisibility(View.VISIBLE);
                        next.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                        prev.setClickable(true);
                        prev.setVisibility(View.VISIBLE);
                        prev.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                        nextQuestion(index);
                    } else if (switcher == 3) {
                        for (int i = index; i < learnQuestionsArrayList.size() - 1; i++) {
                            if (learnQuestionsArrayList.get(i + 1).getStar() == 0) {
                                index++;

                            } else if (learnQuestionsArrayList.get(i + 1).getStar() == 1) {
                                index++;
                                next.setVisibility(View.VISIBLE);
                                next.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                                prev.setClickable(true);
                                prev.setVisibility(View.VISIBLE);
                                prev.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                                nextQuestion(index);
                                break;
                            }
                        }


                    }


                }


            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > indexStart) {
                    if (switcher == 1 || switcher == 2) {
                        index--;
                        prev.setClickable(true);
                        prev.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                        prev.setVisibility(View.VISIBLE);

                        next.setClickable(true);
                        next.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                        next.setVisibility(View.VISIBLE);
                        nextQuestion(index);

                    } else if (switcher == 3) {
                        for (int i = index; i > 0; i--) {
                            if (learnQuestionsArrayList.get(i - 1).getStar() == 0) {
                                index--;

                            } else if (learnQuestionsArrayList.get(i - 1).getStar() == 1) {
                                index--;

                                prev.setClickable(true);
                                prev.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                                prev.setVisibility(View.VISIBLE);

                                next.setClickable(true);
                                next.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                                next.setVisibility(View.VISIBLE);
                                nextQuestion(index);
                                break;
                            }
                        }
                    }


                }


            }
        });

        //Button star Handle
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (learnQuestionsArrayList.get(index).getStar() == 0) {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), learnQuestionsArrayList.get(index).getNotify(), learnQuestionsArrayList.get(index).getIndexLocal(), 1));
                    Toast.makeText(ActivityQuestionsLearn.this, "Pytanie dodano do zapamiętanych", Toast.LENGTH_SHORT).show();
                    star++;
                    colorNotify();
                    updateStar();
                    savetoGSON();
                } else if (learnQuestionsArrayList.get(index).getStar() == 1) {
                    learnQuestionsArrayList.set(index, new Question(learnQuestionsArrayList.get(index).getIndexGlobal(), learnQuestionsArrayList.get(index).getQuestion(), learnQuestionsArrayList.get(index).getAnswerA(), learnQuestionsArrayList.get(index).getAnswerB(), learnQuestionsArrayList.get(index).getAnswerC(), learnQuestionsArrayList.get(index).getAnswerD(), learnQuestionsArrayList.get(index).getCorrectAnswer(), learnQuestionsArrayList.get(index).getNotify(), learnQuestionsArrayList.get(index).getIndexLocal(), 0));
                    Toast.makeText(ActivityQuestionsLearn.this, "Pytanie usunięto z zapamiętanych", Toast.LENGTH_SHORT).show();
                    star--;
                    colorNotify();
                    updateStar();
                    savetoGSON();

                }
            }
        });

    }


    // Show Question
    private void nextQuestion(int index) {
        int indexTotal;
        if (switcher == 1) {
            indexTotal = indexEnd - indexStart + 1;
            questionCounter.setText(String.valueOf(learnQuestionsArrayList.get(index).getIndexLocal()) + "/" + indexTotal);
        } else if (switcher == 2) {
            indexTotal = 999;
            questionCounter.setText(String.valueOf(learnQuestionsArrayList.get(index).getIndexGlobal()) + "/" + indexTotal);
        } else if (switcher == 3) {
            indexTotal = 999;
            questionCounter.setText(String.valueOf(learnQuestionsArrayList.get(index).getIndexGlobal()) + "/" + indexTotal);


        }
        question.setText(learnQuestionsArrayList.get(index).getQuestion());
        answerA.setText(learnQuestionsArrayList.get(index).getAnswerA());
        answerB.setText(learnQuestionsArrayList.get(index).getAnswerB());
        answerC.setText(learnQuestionsArrayList.get(index).getAnswerC());
        colorNotify();
        updateChapter(index);

        if (index == indexEnd) {
            //Toast.makeText(getApplicationContext(), "THIS IS END", Toast.LENGTH_SHORT).show();
            next.setClickable(false);
            next.setColorFilter(ContextCompat.getColor(this, R.color.colorDividerTExt), PorterDuff.Mode.SRC_ATOP);
            //next.setVisibility(View.INVISIBLE);
        }

        if (index == indexStart) {
            //Toast.makeText(getApplicationContext(), "THIS IS START", Toast.LENGTH_SHORT).show();
            prev.setClickable(false);
            prev.setColorFilter(ContextCompat.getColor(this, R.color.colorDividerTExt), PorterDuff.Mode.SRC_ATOP);
            //prev.setVisibility(View.INVISIBLE);

        }

    }


    // Check answer by color something

    private void colorNotify() {

        if (learnQuestionsArrayList.get(index).getNotify() == 0) {
            //ColorDefault
            colorQuestionFieldNeutral();
            colorANeuatral();
            colorBNeutral();
            colorCNeutral();

        } else if (learnQuestionsArrayList.get(index).getNotify() == 1) {
            //ColorCorrectA
            colorQuestionFieldGreen();
            colorAGreen();
            colorBNeutral();
            colorCNeutral();
        } else if (learnQuestionsArrayList.get(index).getNotify() == 2) {
            //ColorWrongA
            colorQuestionFieldRed();
            colorARed();
            colorBNeutral();
            colorCNeutral();
        } else if (learnQuestionsArrayList.get(index).getNotify() == 3) {
            //ColorCorrectB
            colorQuestionFieldGreen();
            colorBGreen();
            colorANeuatral();
            colorCNeutral();
        } else if (learnQuestionsArrayList.get(index).getNotify() == 4) {
            //ColorWrongB
            colorQuestionFieldRed();
            colorBRed();
            colorANeuatral();
            colorCNeutral();
        } else if (learnQuestionsArrayList.get(index).getNotify() == 5) {
            //ColorCorrectC
            colorQuestionFieldGreen();
            colorCGreen();
            colorANeuatral();
            colorBNeutral();
        } else if (learnQuestionsArrayList.get(index).getNotify() == 6) {
            //ColorWrongC
            colorQuestionFieldRed();
            colorCRed();
            colorANeuatral();
            colorBNeutral();
        }

        if (learnQuestionsArrayList.get(index).getStar() == 0) {
            imageButton.setImageResource(R.drawable.ic_star_outline_black_48dp);
        } else if (learnQuestionsArrayList.get(index).getStar() == 1) {
            imageButton.setImageResource(R.drawable.ic_star_black_48dp);
        }

    }

    public void colorQuestionFieldNeutral() {
        question.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
    }

    public void colorQuestionFieldRed() {
        question.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightRed));
    }

    public void colorQuestionFieldGreen() {
        question.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightGreen));
    }

    public void colorANeuatral() {
        viewA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDividerTExt));
        answerA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
    }

    public void colorARed() {
        viewA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        answerA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightRed));
    }

    public void colorAGreen() {
        viewA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
        answerA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightGreen));
    }

    public void colorBNeutral() {
        viewB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDividerTExt));
        answerB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
    }

    public void colorBRed() {
        viewB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        answerB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightRed));
    }

    public void colorBGreen() {
        viewB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
        answerB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightGreen));
    }

    public void colorCNeutral() {
        viewC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDividerTExt));
        answerC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight));
    }

    public void colorCRed() {
        viewC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        answerC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightRed));
    }

    public void colorCGreen() {
        viewC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
        answerC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorLightGreen));
    }


    //SaveToGson
    private void savetoGSON() {
        String questionJsonList = new Gson().toJson(learnQuestionsArrayList);
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("QuestionKey", questionJsonList);
        editor.apply();
    }

//    private void updateCounter() {
//        int counterGood = 0;
//        int counterBad = 0;
//
//        //Question Counter
//        for (int i = indexStart; i <= indexEnd; i++) {
//            if (learnQuestionsArrayList.get(i).getNotify() == 1 || learnQuestionsArrayList.get(i).getNotify() == 3 || learnQuestionsArrayList.get(i).getNotify() == 5) {
//                counterGood++;
//            } else if (learnQuestionsArrayList.get(i).getNotify() == 2 || learnQuestionsArrayList.get(i).getNotify() == 4 || learnQuestionsArrayList.get(i).getNotify() == 6) {
//                counterBad++;
//            }
//        }
//        questionCorrectAnswerCounter.setText(String.valueOf(counterGood));
//        questionWrongAnswerCounter.setText(String.valueOf(counterBad));
//
//    }


    //Update remember star
    private void updateStar() {
        int star = 0;

        if (switcher == 1) {
            for (int i = indexStart; i <= indexEnd; i++) {
                if (learnQuestionsArrayList.get(i).getStar() == 1) {
                    star++;
                }
                questionStar.setText(String.valueOf(star));
            }

        } else if (switcher == 2 || switcher == 3) {
            for (int i = 0; i <= 998; i++) {
                if (learnQuestionsArrayList.get(i).getStar() == 1) {
                    star++;
                }
                questionStar.setText(String.valueOf(star));
            }
        }


    }

    private void updateChapter(int chapterIndexNumber) {
        String[] chapter = new String[15];
        chapter[0] = "I. Bezpieczeństwo pożarowe obiektów i budynków";
        chapter[1] = "II. Środki Gaśnicze Neutralizatory i Sorbenty";
        chapter[2] = "III. Wyposażenie Techniczne I Sprzęt";
        chapter[3] = "IV. Prawa I Obowiązki Operatora Pojazdu / Sprzętu Pożarniczego";
        chapter[4] = "V. Taktyka działań gaśniczych";
        chapter[5] = "VI. Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo techniczne i chemiczne";
        chapter[6] = "VII. Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo techniczne i chemiczne";
        chapter[7] = "VIII. Taktyka działań ratowniczych - dla jednostek podstawowych - Działania Poszukiwawczo-Ratownicze";
        chapter[8] = "IX. Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych - Działania Poszukiwawczo-Ratownicze";
        chapter[9] = "X. Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo wodne";
        chapter[10] = "XI. Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo wodno-nurkowe";
        chapter[11] = "XII. Taktyka działań ratowniczych - dla jednostek podstawowych – Ratownictwo Wysokościowe";
        chapter[12] = "XIII. Taktyka działań ratowniczych - dla specjalistycznych grup ratowniczych – Ratownictwo Wysokościowe";
        chapter[13] = "XIV. Łączność";
        chapter[14] = "XV. Praca Stanowisk Kierowania";

        if (chapterIndexNumber >= 0 && chapterIndexNumber <= 56) {
            actualChapter = chapter[0];
            numberChapter = "I";
        } else if (chapterIndexNumber >= 57 && chapterIndexNumber <= 93) {
            actualChapter = chapter[1];
            numberChapter = "II";
        } else if (chapterIndexNumber >= 94 && chapterIndexNumber <= 272) {
            actualChapter = chapter[2];
            numberChapter = "III";
        } else if (chapterIndexNumber >= 273 && chapterIndexNumber <= 294) {
            actualChapter = chapter[3];
            numberChapter = "IV";
        } else if (chapterIndexNumber >= 295 && chapterIndexNumber <= 490) {
            actualChapter = chapter[4];
            numberChapter = "V";
        } else if (chapterIndexNumber >= 491 && chapterIndexNumber <= 571) {
            actualChapter = chapter[5];
            numberChapter = "VI";
        } else if (chapterIndexNumber >= 572 && chapterIndexNumber <= 647) {
            actualChapter = chapter[6];
            numberChapter = "VII";
        } else if (chapterIndexNumber >= 648 && chapterIndexNumber <= 661) {
            actualChapter = chapter[7];
            numberChapter = "VIII";
        } else if (chapterIndexNumber >= 662 && chapterIndexNumber <= 686) {
            actualChapter = chapter[8];
            numberChapter = "IX";
        } else if (chapterIndexNumber >= 687 && chapterIndexNumber <= 706) {
            actualChapter = chapter[9];
            numberChapter = "X";
        } else if (chapterIndexNumber >= 707 && chapterIndexNumber <= 750) {
            actualChapter = chapter[10];
            numberChapter = "XI";
        } else if (chapterIndexNumber >= 751 && chapterIndexNumber <= 790) {
            actualChapter = chapter[11];
            numberChapter = "XII";
        } else if (chapterIndexNumber >= 791 && chapterIndexNumber <= 854) {
            actualChapter = chapter[12];
            numberChapter = "XIII";
        } else if (chapterIndexNumber >= 855 && chapterIndexNumber <= 933) {
            actualChapter = chapter[13];
            numberChapter = "XIV";
        } else if (chapterIndexNumber >= 934 && chapterIndexNumber <= 998) {
            actualChapter = chapter[14];
            numberChapter = "XV";
        }

        questionChaper.setText(String.valueOf(numberChapter));

    }

}
