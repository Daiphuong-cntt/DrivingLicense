package com.example.traficapplication.activities.api;

import com.example.traficapplication.activities.models.QuestionCategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionApi {

    @GET("learn/getLearnFollowIdCategory/629c40b8d25db7c7fe47346f")
    Call<QuestionCategoryResponse> getChapter1();
    @GET("learn/getLearnFollowIdCategory/629c40d5d25db7c7fe473471")
    Call<QuestionCategoryResponse> getChapter2();
    @GET("learn/getLearnFollowIdCategory/629c412bd25db7c7fe473473")
    Call<QuestionCategoryResponse> getChapter3();
    @GET("learn/getLearnFollowIdCategory/629c413ed25db7c7fe473475")
    Call<QuestionCategoryResponse> getChapter4();
    @GET("learn/getLearnFollowIdCategory/629c415ed25db7c7fe473477")
    Call<QuestionCategoryResponse> getChapter5();
    @GET("learn/getLearnFollowIdCategory/629c418ed25db7c7fe473479")
    Call<QuestionCategoryResponse> getChapter6();
    @GET("learn/getLearnFollowIdCategory/629c41fad25db7c7fe47347b")
    Call<QuestionCategoryResponse> getChapter7();
}
