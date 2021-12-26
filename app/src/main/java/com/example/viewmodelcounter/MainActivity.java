package com.example.viewmodelcounter;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends Logger {

    private static final String TAG = MainActivity.class.getName();
    public static final String EXTRA_MESSAGE = TAG+".MESSAGE";
    private ScoreViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SETUP ADD BUTTON
        Button addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
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
        //INIT Score
        model.initScore();
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getScore().observe(this, scoreObserver);


        //SETUP VIEW WEB RESULTS BUTTON
        Button viewWeResultsButton = (Button) findViewById(R.id.button_goto_webresults);
        viewWeResultsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Go to Web Results Activity");
                startWebResults(v);
            }
        });

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

    /** Called when the user taps the button_goto_webresults button */
    public void startWebResults(View view) {
        Intent intent = new Intent(this, DisplayWebResultsActivity.class);
        intent.putExtra(EXTRA_MESSAGE,"view_web_results");
        startActivity(intent);
    }

}