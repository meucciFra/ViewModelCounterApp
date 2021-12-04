package com.example.viewmodelcounter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScoreWebService {
    @GET("scores")
    Call<List<Score>> getAll();
}
