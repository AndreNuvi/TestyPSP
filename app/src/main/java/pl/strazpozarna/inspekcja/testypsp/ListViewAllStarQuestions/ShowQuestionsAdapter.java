package pl.strazpozarna.inspekcja.testypsp.ListViewAllStarQuestions;

import android.app.Activity;
import android.content.Context;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.testypsp.R;


import pl.strazpozarna.inspekcja.testypsp.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.android.testypsp.R.id.starList;

/**
 * Created by Andre on 2017-02-20.
 */

public class ShowQuestionsAdapter extends ArrayAdapter<Question> {


    private Context context;
    static List<Question> data;
    static ArrayList<Question> arrayList;


    public ShowQuestionsAdapter(Context context, ArrayList<Question> data) {
        super(context, R.layout.questions_item_all);
        this.data = data;
        this.context = context;
        arrayList = new ArrayList<>();
        arrayList.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        QuestionHolder holder = null;


        // Check if an existing view is being reused, otherwise inflate the view
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.questions_item_all, null);

            // Lookup view for data population
            holder = new QuestionHolder();
            holder.number = (TextView) row.findViewById(R.id.questions_item_all_NumberId);
            holder.itemQuestion = (TextView) row.findViewById(R.id.questions_item_all_QuestionId);
            holder.starList = (ImageView) row.findViewById(starList);
            row.setTag(holder);
        } else {
            holder = (QuestionHolder) row.getTag();
        }

        // Get the data item for this position
        Question question = data.get(position);

        // Populate the data into the template view using the data object
        if (question.getNotify() == 1 || question.getNotify() == 3 || question.getNotify() == 5) {
            holder.itemQuestion.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightGreen));
            holder.number.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
        } else if (question.getNotify() == 2 || question.getNotify() == 4 || question.getNotify() == 6) {
            holder.itemQuestion.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightRed));
            holder.number.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        } else if (question.getNotify() == 0) {
            holder.itemQuestion.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight));
            holder.number.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorDividerTExt));
        }

        if (question.getStar() == 1) {
            holder.starList.setVisibility(View.VISIBLE);

        } else {
            holder.starList.setVisibility(View.INVISIBLE);
        }

        holder.itemQuestion.setText(question.getQuestion());
        holder.number.setText(String.valueOf(question.getIndexGlobal()));


        return row;

    }

    static class QuestionHolder {
        TextView itemQuestion;
        TextView number;
        ImageView starList;
    }


    public void filter(String charText) {


        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();


        if (charText.length() == 0) {
            data.addAll(arrayList);
        } else {
            for (Question questionDetails : arrayList) {
                if (charText.length() != 0 && questionDetails.getQuestion().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(questionDetails);
                }
            }
        }
        notifyDataSetChanged();

    }

}

