package com.example.traficapplication.activities.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traficapplication.R;
import com.example.traficapplication.activities.adapters.QuestionLearnAdapter;
import com.example.traficapplication.activities.adapters.QuestionTestAdapter;
import com.example.traficapplication.activities.api.ApiClient;
import com.example.traficapplication.activities.models.Question;
import com.example.traficapplication.activities.models.QuestionAll;
import com.example.traficapplication.activities.models.QuestionAllResponse;
import com.example.traficapplication.activities.models.QuestionCategoryResponse;
import com.example.traficapplication.activities.utils.Contants;
import com.example.traficapplication.activities.utils.StoreUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestDoingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    private QuestionTestAdapter questionLearnAdapter;
    private Button btnPrevious;
    private Button btnNext;
    private TextView tvCount,tvTime;

    private List<QuestionAll> questionAlls = new ArrayList<>();
    private List<QuestionAll> questions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_doing);
        initUi();
        getData();
        makeRecyclerView();
        time();
        showResult();

    }



    private void showResult() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResult(Gravity.BOTTOM);
            }
        });
    }

    private void time() {
        int durraton = 1500000;
        CountDownTimer Timer = new CountDownTimer(durraton, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTime.setText("Th???i gian c??n l???i: " +millisUntilFinished/60000 +":"+ (millisUntilFinished-(millisUntilFinished/60000)*60000)/1000 +"\nNh???n v??o ????y ????? n???p b??i.");
            }

            public void onFinish() {
                tvTime.setText("H???t gi???");
                result();
            }
        }.start();
    }

    private void result() {
        Intent intent = new Intent(TestDoingActivity.this, TestResultActivity.class);
        startActivity(intent);
        TestDoingActivity.this.finish();
    }

    private void makeRecyclerView() {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int position = getCurrentItem();
                tvCount.setText("Question\n"+"" + (position+1) + " / " + questionLearnAdapter.getItemCount());
            }
        });

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preview();
            }
        });

        //Onlick ?????n c??u h???i
        tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSearch(Gravity.BOTTOM);
            }
        });
        oldPos();
    }


    @Override
    public void onBackPressed() {
        dialogBack(Gravity.BOTTOM);

}

    private void dialogResult(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_test_back);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        window.setAttributes(windowAtribute);

        // show dialog
        Button btnYes = dialog.findViewById(R.id.btn_continue_test);
        Button btnNo = dialog.findViewById(R.id.btn_exit_test);
        TextView tvWarning = dialog.findViewById(R.id.tv_warning);
        btnYes.setText("V??ng");
        btnNo.setText("Kh??ng");
        tvWarning.setText("B???n ch???c ch???? Khi n???p b??i b???n s??? kh??ng ???????c s???a ?????i n???a.");
        TextView tvTestTittle = dialog.findViewById(R.id.tv_testing);
        tvTestTittle.setText("N???p b??i thi ?");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void dialogBack(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_test_back);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        window.setAttributes(windowAtribute);

        // show dialog
        Button btnSave = dialog.findViewById(R.id.btn_continue_test);
        Button btnExit = dialog.findViewById(R.id.btn_exit_test);
        TextView tvTestTittle = dialog.findViewById(R.id.tv_testing);
        tvTestTittle.setText("????? thi ng???u nhi??n h???ng A1");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                if (adapter == null)
                    return;
                int position = getCurrentItem();
                StoreUtils.save(TestDoingActivity.this, Contants.position, String.valueOf(position));
                dialog.dismiss();
                TestDoingActivity.this.finish();

            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestDoingActivity.this.finish();
                SharedPreferences email = TestDoingActivity.this.getSharedPreferences("MySharedPref", 0);
                email.edit().remove("position").commit();
                TestDoingActivity.this.finish();
            }
        });
        dialog.show();
    }
    public boolean hasPreview() {
        return getCurrentItem() > 0;
    }

    public boolean hasNext() {
        return mRecyclerView.getAdapter() != null &&
                getCurrentItem() < (mRecyclerView.getAdapter().getItemCount() - 1);
    }

    public void preview() {
        int position = getCurrentItem();
        if (position > 0)
            setCurrentItem(position - 1, true);
    }

    public void next() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null)
            return;

        int position = getCurrentItem();
        int count = adapter.getItemCount();
        if (position < (count - 1))
            setCurrentItem(position + 1, true);
    }
    ///Dialog t??m c??u h???i
    public void dialogSearch(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_question);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        window.setAttributes(windowAtribute);
        ImageView imgGo = dialog.findViewById(R.id.btn_go);
        EditText edtPage = dialog.findViewById(R.id.edt_page_go);
        TextView tvPage = dialog.findViewById(R.id.tv_page);
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        tvPage.setText("/ "+ String.valueOf(adapter.getItemCount()));
        imgGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter == null)
                    return;
                String edt = String.valueOf(edtPage.getText());
                String n = "";
                int count = adapter.getItemCount();
                if (edt.equals(n))
                {
                    notInsert();
                }
                else {
                    int position =Integer.parseInt(edt);
                    if (position < (count))
                    {
                        if (position>0)
                        {
                            setCurrentItem(position-1, false);
                            dialog.dismiss();
                        }
                        else notInsert();

                    }
                    else {
                        position = count;
                        setCurrentItem(position-1,false);
                        dialog.dismiss();
                        Toast.makeText(TestDoingActivity.this,count +" l?? c??u cu???i r???i",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }
    private void notInsert(){
        Toast.makeText(TestDoingActivity.this,"Ch??a ch???n c??u c???n ?????n",Toast.LENGTH_SHORT).show();
    }
    //// M??? l???i c??u c??
    public void oldPos(){
        String pos = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).getString(Contants.position,"");
        SharedPreferences sharedPreferences = TestDoingActivity.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        if (sharedPreferences.getString(Contants.position, "").isEmpty()){
        }else{
            setCurrentItem(Integer.parseInt(pos),false);
        }
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private void setCurrentItem(int position, boolean smooth) {
        if (smooth)
            mRecyclerView.smoothScrollToPosition(position);
        else
            mRecyclerView.scrollToPosition(position);
    }
    /////L???y c??u h???i
    private void getData() {
        Call<QuestionAllResponse> responseDTOCall = (Call<QuestionAllResponse>) ApiClient.questionApi().getAllQuestion();
        responseDTOCall.enqueue(new Callback<QuestionAllResponse>() {
            @Override
            public void onResponse(Call<QuestionAllResponse> call, Response<QuestionAllResponse> response) {
                questionLearnAdapter = new QuestionTestAdapter(TestDoingActivity.this,questions);
                questionAlls.addAll(response.body().getData());
                Collections.shuffle(questionAlls);
                for (int i =0;i<25;i++)
                {
                    questions.add(questionAlls.get(i));
                }
                mRecyclerView.setAdapter(questionLearnAdapter);
            }
            @Override
            public void onFailure(Call<QuestionAllResponse> call, Throwable t) {
                Toast.makeText(TestDoingActivity.this, "Connecting... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initUi() {
        mRecyclerView = findViewById(R.id.rcv_question_test);
        btnPrevious = findViewById(R.id.btn_prev_test);
        btnNext = findViewById(R.id.btn_next_test);
        tvCount = findViewById(R.id.tv_count_test);
        tvTime = findViewById(R.id.tv_time_remain);
    }


}