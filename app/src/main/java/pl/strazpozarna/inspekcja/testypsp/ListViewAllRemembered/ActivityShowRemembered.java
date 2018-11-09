package pl.strazpozarna.inspekcja.testypsp.ListViewAllRemembered;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.testypsp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import pl.strazpozarna.inspekcja.testypsp.ActivityQuestionsLearn;
import pl.strazpozarna.inspekcja.testypsp.Question;



/**
 * Created by Andre on 2017-03-11.
 */

public class ActivityShowRemembered extends AppCompatActivity {

    ArrayList<Question> learnQuestionsArrayList;
    ArrayList<Question> rememberedQuestionsArrayList;

    ListView listView;
    int scrollPosition;
    Parcelable state;
    SharedPreferences sharedpreferences;

    ShowRememberedAdapter showRememberedAdapter;

    MenuItem searchItem;
    SearchView searchView;
    String queryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);
    }

    @Override
    protected void onStart() {
        rememberedQuestionsArrayList = new ArrayList<>();

        // Read from GSON
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("QuestionKey", "");
        learnQuestionsArrayList = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
        }.getType());

        assert learnQuestionsArrayList != null;
        for (int i = learnQuestionsArrayList.size()-1; i>=0; i--){
            int indexRemembered =0;
            if(learnQuestionsArrayList.get(i).getStar()==1){
                rememberedQuestionsArrayList.add(indexRemembered, new Question(learnQuestionsArrayList.get(i).getIndexGlobal(), learnQuestionsArrayList.get(i).getQuestion(), learnQuestionsArrayList.get(i).getAnswerA(), learnQuestionsArrayList.get(i).getAnswerB(), learnQuestionsArrayList.get(i).getAnswerC(), learnQuestionsArrayList.get(i).getAnswerD(), learnQuestionsArrayList.get(i).getCorrectAnswer(), learnQuestionsArrayList.get(i).getNotify(), learnQuestionsArrayList.get(i).getIndexLocal(), learnQuestionsArrayList.get(i).getStar()));
            indexRemembered++;
            }
        }

        // Create the adapter to convert the array to views
        showRememberedAdapter = new ShowRememberedAdapter(this, rememberedQuestionsArrayList);

        // Attach the adapter to a ListView
        listView = findViewById(R.id.activity_show_questions_listview_id);
        listView.setAdapter(showRememberedAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent intent = new Intent(ActivityShowRemembered.this, ActivityQuestionsLearn.class);

                intent.putExtra("IndexStart", rememberedQuestionsArrayList.get(position).getIndexGlobal() - 1);
                intent.putExtra("IndexEnd", 998);
                intent.putExtra("SwitchIntent", 3);
                startActivity(intent);

            }
        });

        listView.setTextFilterEnabled(true);

//        //Load position after destroy
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int position = sharedpreferences.getInt("ListPositionRemembered", 0);
        listView.setSelectionFromTop(position, 0);


//        Load position list after onPause
        if (state != null) {
            listView.onRestoreInstanceState(state);
        }

        //Load query after come back from detail question
        //I found BUG in java here, I have to add space before queryText to work
        if (searchView != null) {

            queryText = String.valueOf(searchView.getQuery());
            searchView.setQuery(" "+queryText, true);

        }

        super.onStart();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menurememberedlist, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Szukaj wg frazy w pytaniu");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                showRememberedAdapter.filter(searchQuery.trim());
                listView.invalidate();
                return true;
            }

        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if (id ==R.id.action_reset){
            for(int i=0;i<learnQuestionsArrayList.size(); i++) {
                if (learnQuestionsArrayList.get(i).getStar() == 1) {
                    learnQuestionsArrayList.set(i, new Question(learnQuestionsArrayList.get(i).getIndexGlobal(), learnQuestionsArrayList.get(i).getQuestion(), learnQuestionsArrayList.get(i).getAnswerA(), learnQuestionsArrayList.get(i).getAnswerB(), learnQuestionsArrayList.get(i).getAnswerC(), learnQuestionsArrayList.get(i).getAnswerD(), learnQuestionsArrayList.get(i).getCorrectAnswer(), 0, learnQuestionsArrayList.get(i).getIndexLocal(), learnQuestionsArrayList.get(i).getStar()));
                }
            }
            savetoGSON();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPause() {

        // Save ListView state after onPause
        state = listView.onSaveInstanceState();
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        //Save list position permanently after onDestroy
        scrollPosition = listView.getFirstVisiblePosition();

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("ListPositionRemembered", scrollPosition);
        editor.apply();

        super.onDestroy();
    }

    private void savetoGSON() {
        String questionJsonList = new Gson().toJson(learnQuestionsArrayList);
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("QuestionKey", questionJsonList);
        editor.apply();
    }


}
