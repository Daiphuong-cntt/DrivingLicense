package com.example.traficapplication.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traficapplication.R;
import com.example.traficapplication.activities.models.Question;

import java.util.List;

//public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>
 public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    List<Question> questions;

    public QuestionAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question,parent,false);
        return new QuestionAdapter.QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question q = questions.get(position);
        ((QuestionViewHolder)holder).itemTitle.setText(q.getQuestion());
    }


//    @NonNull
//    @Override
//    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_question,parent,false);
//        return new QuestionAdapter.QuestionViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
//        Question q = questions.get(position);
//        holder.itemTitle.setText(q.getQuestion());
//    }

    @Override
    public int getItemCount() {
        if (questions != null){
            return questions.size();
        }
        return 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.tv_item_quest_title);
        }
    }


}
