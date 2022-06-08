package com.example.traficapplication.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.traficapplication.R;
import com.example.traficapplication.activities.adapters.QuestionAdapter;
import com.example.traficapplication.activities.adapters.SignAdapter;
import com.example.traficapplication.activities.api.ApiClient;
import com.example.traficapplication.activities.models.Question;
import com.example.traficapplication.activities.models.QuestionCategoryResponse;
import com.example.traficapplication.activities.models.SignResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Question> questions;
    private QuestionAdapter questionAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_detail);
        initUI();
        getIn4();
        setRecylerView();
    }



    private void setRecylerView() {
        layoutManager = new LinearLayoutManager(DocumentDetailActivity.this);
        questionAdapter = new QuestionAdapter(DocumentDetailActivity.this,questions);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(DocumentDetailActivity.this,layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(questionAdapter);
    }

    private void initUI() {
        recyclerView = findViewById(R.id.rcv_1);
    }
    private void getIn4(){
        questions = new ArrayList<>();
        Call<QuestionCategoryResponse> responseDTOCall = (Call<QuestionCategoryResponse>) ApiClient.questionApi().getChapter1();
        responseDTOCall.enqueue(new Callback<QuestionCategoryResponse>() {
            @Override
            public void onResponse(Call<QuestionCategoryResponse> call, Response<QuestionCategoryResponse> response) {
                questions.addAll(response.body().getData());
            }
            @Override
            public void onFailure(Call<QuestionCategoryResponse> call, Throwable t) {
                Toast.makeText(DocumentDetailActivity.this, "Connecting... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}