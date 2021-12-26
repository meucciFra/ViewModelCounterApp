package com.example.viewmodelcounter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScoreRepository {
    //This is the Single Source of Truth (SSoT) Class responsible for delivering Scores

    //A RAM Cached representation of the Score Object
    private LiveData<List<Score>> scoreCache;
    //A DATABASE queried representation of the Score Object
    private final ScoreDao scoreDao;
    //A WEBSERVICE queried representation of the Score Object
    private final ScoreWebService scoreWebService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    //A TIMEOUT
    private static final int FRESH_TIMEOUT_IN_SECONDS = 180; //3 min
    public static Calendar lastCall;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    ScoreRepository(Application application){
        //INSTANCE OF scoreDAO
        AppDatabase db = AppDatabase.getInstance(application);
        scoreDao = db.getScoreDao();

        //INSTANCE of scoreWebService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/meucciFra/viewmodelcounter/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            // Instance of the API client
        scoreWebService = retrofit.create(ScoreWebService.class);
    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Score>> getAll(){
        // THE SOURCE OF TRUTH IS THE Local Database so the WebService is used to update the Local Database
        refreshScore(); // try to refresh data if possible from Github Api
        return  scoreDao.getAllLive();// return a Data directly from the database.
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void setScore(Score score) {
        AppDatabase.databaseWriteExecutor.execute(() -> scoreDao.updateScore(score));
    }

    public Score initScore(){
        final Integer[] id = new Integer[1];
        // INIT THE SCORE
        Score score = new Score();
        score.setScoreValue(0); //init the Score to 0
        score.setScoreDate(Calendar.getInstance().getTime()); //Init the Date to current date
        AppDatabase.databaseWriteExecutor.execute(() -> {
            id[0] = (int) scoreDao.insertScore(score); //get the id given by Database and it adds it in the Object
        });
        while(id[0]==null){
            //STAY HERE TILL THE DATABASE RESPOND
        }
        score.setUid(id[0]);
        return score;
    }

    public LiveData<Score> getScoreById(int id){
        // THE SOURCE OF TRUTH IS THE Local Database so the WebService is used to update the Local Database
        refreshScore(); // try to refresh data if possible from Github Api
        return scoreDao.getLiveScoreById(id); // return a Data directly from the database.
    }

    private void refreshScore(){
        //CHECK IF THE LAST CALL IS MORE RECENT THAN FRESH_TIMEOUT_IN_MINUTES
        /*long delta;
        if(lastCall== null){ //if lastCall was never set, this force the query to the webService and set lastCall
            delta = FRESH_TIMEOUT_IN_SECONDS+1;
            lastCall.getInstance();
        }else {
            delta = Duration.between( Calendar.getInstance().toInstant(), lastCall.toInstant() ).getSeconds();
        }
        if( delta > FRESH_TIMEOUT_IN_SECONDS ){*/
        // It gets the score from the WebService
            executorService.execute(() -> {
                Call<List<Score>> scoreCall = scoreWebService.getAll();
                //This is ASYNCHRONOUS Call that you can debug it and check the response
                scoreCall.enqueue(new Callback<List<Score>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                        System.out.println("Response received! Response size: "+response.body().size());
                        executorService.execute(() ->{
                            List<Score> refreshedScores = response.body();
                            for(int i = 0 ; i<refreshedScores.size(); i++){
                                // HERE I'M REFRESHING THE LOCAL DATABASE WITH DATA FRESH DATA FROM WebService
                                setScore(refreshedScores.get(i));
                            }
                        });
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Score>> call,@NonNull Throwable t) {
                        System.out.println("Something went wrong...Please try later!");
                    }
                });
            });
        //}
    }
}
