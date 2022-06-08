package com.example.traficapplication.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traficapplication.R;
import com.example.traficapplication.activities.adapters.QuestionAdapter;
import com.example.traficapplication.activities.adapters.SignAdapter;
import com.example.traficapplication.activities.api.ApiClient;
import com.example.traficapplication.activities.models.Question;
import com.example.traficapplication.activities.models.QuestionCategoryResponse;
import com.example.traficapplication.activities.models.Sign;
import com.example.traficapplication.activities.models.SignResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    private RecyclerView rcvQuestion;
    private QuestionAdapter questionAdapter;
    private List<Question> list;
    private Button btnPrevious;
    private Button btnNext;
    private TextView tvCount;
    private List<Sign> signs;
    private SignAdapter signAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initUi();
        getData();

//        rcvQuestion.setLayoutManager(new LinearLayoutManager(QuestionActivity.this, LinearLayoutManager.HORIZONTAL,false));
//        rcvQuestion.setHasFixedSize(true);
//        signAdapter = new SignAdapter( signs,QuestionActivity.this);
//        rcvQuestion.setAdapter(questionAdapter);
        rcvQuestion.setLayoutManager(new LinearLayoutManager(QuestionActivity.this, LinearLayoutManager.HORIZONTAL,false));
        rcvQuestion.setHasFixedSize(true);
        questionAdapter = new QuestionAdapter(QuestionActivity.this, list);
        rcvQuestion.setAdapter(questionAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sign.get(1);
                list.get(1);
            }
        });

        tvCount.setText("QuestionApi" + list.size());
    }

    private void getData() {
        Call<QuestionCategoryResponse> responseDTOCall = (Call<QuestionCategoryResponse>) ApiClient.questionApi().getChapter1();
        responseDTOCall.enqueue(new Callback<QuestionCategoryResponse>() {
            @Override
            public void onResponse(Call<QuestionCategoryResponse> call, Response<QuestionCategoryResponse> response) {
                list.addAll(response.body().getData());
                Toast.makeText(QuestionActivity.this, response.body().getData().get(1).getQuestion(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<QuestionCategoryResponse> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "Connecting... ", Toast.LENGTH_SHORT).show();
            }
        });
//        signs = new ArrayList<>();
//        Call<SignResponse> responseDTOCall = (Call<SignResponse>) ApiClient.signApi().getAuxiliarySign();
//        responseDTOCall.enqueue(new Callback<SignResponse>() {
//            @Override
//            public void onResponse(Call<SignResponse> call, Response<SignResponse> response) {
//                signs.addAll(response.body().getData());
//            }
//            @Override
//            public void onFailure(Call<SignResponse> call, Throwable t) {
//                Toast.makeText(QuestionActivity.this, "Connecting... ", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initUi() {
        rcvQuestion = findViewById(R.id.rcv_question);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        tvCount = findViewById(R.id.tv_count);
    }
}