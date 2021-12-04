package com.example.viewmodelcounter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScoreWebServiceTest {

    public ScoreWebService scoreService;
    public Call<List<Score>> scores;
    public int bodySize, uid1, scoreValue1;
    public Date scoreDate1;
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void init() throws IOException {
        // Initialize the library
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/meucciFra/viewmodelcounter/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Instance of the client
        scoreService = retrofit.create(ScoreWebService.class);
        scores = scoreService.getAll();
        Response<List<Score>> response = scores.execute(); //Synchronous call for testing purpose
        assert response.body() != null;
        bodySize = response.body().size();
        uid1 = response.body().get(0).getUid();
        scoreValue1 = response.body().get(0).getScoreValue();
        scoreDate1 = response.body().get(0).getScoreDate();
    }
    @Test
    public void getScoresSizeFromWeb_isCorrect() {
        assertEquals("List dimension is not 5 ", 5,bodySize);
    }
    @Test
    public void getFirstScoreFromWeb_isCorrect() throws ParseException {
        assertEquals("List dimension is not 5 ", 5,bodySize);
        assertEquals("Expected score uid is not 1",1,uid1);
        assertEquals("Expected score value is not 1",1,scoreValue1);
        assertEquals("Expected score value is not 1",1,scoreValue1);
        assertEquals("Score dates are not equal:",format.parse("2021-09-30"), scoreDate1);
    }
}


/*
This ASYNCHRONOUS  cannot be tested automatically but you can debug it and check the response
 scores.enqueue(new Callback<List<Score>>() {
    @Override
    public void onResponse(@NonNull Call<List<Score>> call,@NonNull  Response<List<Score>> response) {
        System.out.println("Response received!");
        bodySize = response.body().size();
        uid1 = response.body().get(0).getUid();
        scoreValue1 = response.body().get(0).getScoreValue();
    }
    @Override
    public void onFailure(@NonNull Call<List<Score>> call,@NonNull Throwable t) {
        System.out.println("Something went wrong...Please try later!");
        Assert.fail("Response not received");
    }
});
*/