package pl.strazpozarna.inspekcja.testypsp.ListViewAllStarQuestions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import pl.strazpozarna.inspekcja.testypsp.ActivityQuestionsLearn;
import pl.strazpozarna.inspekcja.testypsp.Question;

import com.example.android.testypsp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;
import static java.security.AccessController.getContext;

public class ActivityShowQuestions extends AppCompatActivity {

    ArrayList<Question> learnQuestionsArrayList;

    ListView listView;
    int scrollPosition;
    Parcelable state;
    SharedPreferences sharedpreferences;
    ShowQuestionsAdapter showQuestionsAdapter;

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


        // Read from GSON
        SharedPreferences prefs = getSharedPreferences("QuestionKey", Context.MODE_PRIVATE);
        String httpParamJSONList = prefs.getString("QuestionKey", "");
        learnQuestionsArrayList = new Gson().fromJson(httpParamJSONList, new TypeToken<List<Question>>() {
        }.getType());


        // Create the adapter to convert the array to views
        showQuestionsAdapter = new ShowQuestionsAdapter(this, learnQuestionsArrayList);

        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.activity_show_questions_listview_id);
        listView.setAdapter(showQuestionsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent intent = new Intent(ActivityShowQuestions.this, ActivityQuestionsLearn.class);

                intent.putExtra("IndexStart", learnQuestionsArrayList.get(position).getIndexGlobal() - 1);
                intent.putExtra("IndexEnd", 998);
                intent.putExtra("SwitchIntent", 2);
                startActivity(intent);


            }
        });

        listView.setTextFilterEnabled(true);




//        //Load position after destroy
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int position = sharedpreferences.getInt("ListPosition", 0);

        listView.setSelectionFromTop(position, 0);


//        Load position list after onPause
        if (state != null) {
            listView.onRestoreInstanceState(state);
        }


        //Load query after come back from detail question
        //I found BUG in java here, I have to add space before queryText to work
        if (searchView != null) {

            queryText = String.valueOf(searchView.getQuery());

//            if(String.valueOf(queryText).substring(0,1).equals(" ")) {
//                Log.v("substrign1,2", queryText.substring(0,1));
//                StringBuffer substract = new StringBuffer(queryText);
//                Log.v("Substract", substract + "");
//                substract.deleteCharAt(0);
//                queryText = String.valueOf(substract);
//                Log.v("After  substract", queryText);
//
//            }


            searchView.setQuery(" "+queryText, true);
            Log.v("QueryTextSTARTTTTTTTTT", queryText + "");

        }

        super.onStart();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuquestionlist, menu);

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


                showQuestionsAdapter.filter(searchQuery.trim());

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
        editor.putInt("ListPosition", scrollPosition);
        editor.apply();


        super.onDestroy();
    }
}
