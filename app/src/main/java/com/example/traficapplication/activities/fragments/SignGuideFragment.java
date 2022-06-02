package com.example.traficapplication.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traficapplication.R;
import com.example.traficapplication.activities.adapters.SignAdapter;
import com.example.traficapplication.activities.api.ApiClient;
import com.example.traficapplication.activities.models.Sign;
import com.example.traficapplication.activities.models.SignResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignGuideFragment extends Fragment {

    private View guideSignalView;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private EditText edtSearch;
    private SignAdapter signAdapter;
    private  ArrayList<Sign> signs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        guideSignalView = inflater.inflate(R.layout.fragment_sign_guide, container, false);
        initUi();
        setRecylerView();
        getIn4();
        search();
        return guideSignalView;
    }

    private void setRecylerView() {
        layoutManager = new LinearLayoutManager(this.guideSignalView.getContext());
        signAdapter = new SignAdapter(signs,this.guideSignalView.getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.guideSignalView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void initUi() {
        recyclerView = guideSignalView.findViewById(R.id.rv_guide_signal);
        edtSearch = guideSignalView.findViewById(R.id.edt_search_guide);
    }
    private void getIn4(){
        Call<SignResponse> responseDTOCall = (Call<SignResponse>) ApiClient.getApi().getProhibitSign();
        responseDTOCall.enqueue(new Callback<SignResponse>() {
            @Override
            public void onResponse(Call<SignResponse> call, Response<SignResponse> response) {
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(signAdapter);
                signs.addAll(response.body().getData());
            }
            @Override
            public void onFailure(Call<SignResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Connecting... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void search(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }
    private void filter(String text) {
        List<Sign> filteredList = new ArrayList<>();
        for (Sign item : signs) {
            if (item.getName().toUpperCase().contains(text.toUpperCase())) {
                filteredList.add(item);
            }
        }
        signAdapter.filterListSign(filteredList);
    }
}