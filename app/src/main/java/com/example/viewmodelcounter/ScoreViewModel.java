package com.example.viewmodelcounter;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    // Tracks the score
    private MutableLiveData<Integer> scoreValue;
    private MutableLiveData<Score> score = new MutableLiveData<>();
    private ScoreRepository repositoryScore;
    private LiveData<List<Score>> scores;

    public ScoreViewModel (Application application) {
        super(application);
        repositoryScore = new ScoreRepository(application);
    }

    LiveData<List<Score>> getAllScores() {
        scores = repositoryScore.getAll();
        return scores;
    }

    public void initScore(){
        if (score.getValue()==null){
            //It enters here when change activity, while keep the value when rotating the screen
            score.setValue(repositoryScore.initScore());
        }
    }

    public MutableLiveData<Score> getScore(){
        return score;
    }

    public void setScore(Score score){
        repositoryScore.setScore(score);
        this.score.setValue(score);
    }

    public MutableLiveData<Score> getScoreById(int id) {
        Score scoreTemp = repositoryScore.getScoreById(id).getValue();
        this.score.setValue(scoreTemp);
        return this.score;
    }


    /* //WHITHOUT REPOSITORY PATTERN THIS WORKS PERFECTLY AND IS VERY SIMPLE
    public MutableLiveData<Integer> getCurrentScore() {
        if (score == null) {
            score = new MutableLiveData<Integer>();
            score.setValue(0);
        }
        return score;
    }
    */

}
