package com.example.viewmodelcounter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DisplayWebResultsActivity extends Logger{

    private static final String TAG = DisplayWebResultsActivity.class.getName();
    public static final String EXTRA_MESSAGE = TAG;
    private ScoreViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_web_results);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG, message);


        //SETUP ViewModel
        model = new ViewModelProvider(this).get(ScoreViewModel.class);
        // Create the observer which updates the UI.
        final Observer<List<Score>> scoreObserver = new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                // Update the UI, in this case, a TextView.
                display(scores);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getAllScores().observe(this, scoreObserver);

        //SETUP VIEW WEB RESULTS BUTTON
        Button viewMainActivityButton = (Button) findViewById(R.id.button_goto_main_activity);
        viewMainActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Go to Web Results Activity");
                startMainActivity(v);
            }
        });
    }

    /** Called when the user taps the button_goto_main_activity button */
    public void startMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,"goto main activity");
        startActivity(intent);
    }

    public void display(List<Score> scores) {
        RecyclerView scoresView = (RecyclerView) findViewById(R.id.recicleview_webscores);
    }

}
