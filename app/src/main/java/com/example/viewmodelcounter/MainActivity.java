package com.example.viewmodelcounter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends Logger {

    private static final String TAG = MainActivity.class.getName();
    // Tracks the score for Team A
    int score = 0;
    private ScoreViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SETUP BUTTONS
        Button button = (Button) findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Adding 1");
                addOne();
            }
        });

        //SETUP ViewModel
        model = new ViewModelProvider(this).get(ScoreViewModel.class);
        // Create the observer which updates the UI.
        final Observer<Score> scoreObserver = new Observer<Score>() {
            @Override
            public void onChanged(@Nullable final Score newScore) {
                // Update the UI, in this case, a TextView.
                display(newScore.getScoreValue());
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        //INIT Score
        model.initScore();
        model.getScore().observe(this, scoreObserver);


        /*//SETUP ViewModel
        model = new ViewModelProvider(this).get(ScoreViewModel.class);
        // Create the observer which updates the UI.
        final Observer<Integer> scoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newScore) {
                // Update the UI, in this case, a TextView.
                display(newScore);
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getScoreById(0).observe(this, scoreObserver);*/
    }

    public void addOne() {
        // Get the current score stored in the LiveData, change it adding 1 and set the new score
        Score score = model.getScore().getValue();
        int currentScore = score.getScoreValue();
        int newScoreValue = currentScore + 1;
        score.setScoreValue(newScoreValue);
        model.setScore(score);
        model.getScore().setValue(model.getScore().getValue());
    }

    /*public void addOne() {
        // Get the current score stored in the LiveData, change it adding 1 and set the new score
        model.getScoreById(0).setValue( model.getScoreById(0).getValue() + 1);
    }*/

    public void display(Integer score) {
        TextView scoreView = (TextView) findViewById(R.id.textview_score);
        scoreView.setText(String.valueOf(score));
    }

}