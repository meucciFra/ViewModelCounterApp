package com.example.viewmodelcounter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class Logger extends AppCompatActivity {
    private static final String TAG = Logger.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate state");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart state");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume state");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause state");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop state");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy state");
    }
}
