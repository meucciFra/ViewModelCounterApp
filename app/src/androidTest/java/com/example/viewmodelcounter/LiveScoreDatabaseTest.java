package com.example.viewmodelcounter;

import static org.junit.Assert.assertEquals;
import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class LiveScoreDatabaseTest {
    private ScoreDao scoreDao;
    private AppDatabase db;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        scoreDao = db.getScoreDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void rawQueryTotalScoreByDateLiveData() throws Exception {

        //ATTACH Observable Object to LiveData, means start watching changes in the DataBase
        //USE THE RawQuery Feature, passing the Custom Query tht return a Custom Object that differ from Score Object
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT score_date as date, sum(score_value) as sumOfScoresByDate FROM scores GROUP BY score_date");
        LiveData<List<ScoreSummary>> liveResults = scoreDao.getScoreSummaryLive(query);

        Score score1 = new Score();
        score1.setScoreValue(1);
        score1.setScoreDate(format.parse("2021-09-30"));
        //WHEN YOU INSERT THE Score IN THE DATABASE YOU NEED TO RETURN THE ID AND SAVE IT IN THE in-Memory Object REPRESENTATION (need to Update and Delete methods)
        score1.setUid((int)scoreDao.insertScore(score1));
        Score score2 = new Score();
        score2.setScoreValue(2);
        score2.setScoreDate(format.parse("2021-09-30"));
        score2.setUid((int)scoreDao.insertScore(score2));
        Score score3 = new Score();
        score3.setScoreValue(3);
        score3.setScoreDate(format.parse("2021-09-30"));
        score3.setUid((int)scoreDao.insertScore(score3));
        Long scoreSix = new Long(6);
        assertEquals("The total score of 2021-09-30 is not 6 as expected",scoreSix, LiveDataTestUtil.getOrAwaitValue(liveResults).get(0).getSumOfScoresByDate());

        Score score4 = new Score();
        score4.setScoreValue(1);
        score4.setScoreDate(format.parse("2021-10-1"));
        score4.setUid((int)scoreDao.insertScore(score4));
        Score score5 = new Score();
        score5.setScoreValue(2);
        score5.setScoreDate(format.parse("2021-10-1"));
        score5.setUid((int)scoreDao.insertScore(score5));
        Long scoreThree = new Long(3);
        assertEquals("The total score of 2021-09-30 is not 3 as expected",scoreThree, LiveDataTestUtil.getOrAwaitValue(liveResults).get(1).getSumOfScoresByDate());
    }

    @Test
    public void getAllLiveData() throws Exception {
        //ATTACH Observable Object to LiveData, means start watching changes in the DataBase
        LiveData<List<Score>> liveScores = scoreDao.getAllLive();
        Score score1 = new Score();
        score1.setScoreValue(1);
        score1.setScoreDate(format.parse("2021-09-30"));
        score1.setUid((int)scoreDao.insertScore(score1)); //Need to Set the Uid in the in-memory Object representation
        assertEquals("The score 1 of 2021-09-30 is not 1 as expected",1,LiveDataTestUtil.getOrAwaitValue(liveScores).get(0).getScoreValue());
        // add another score
        score1.setScoreValue(3);
        scoreDao.updateScore(score1);
        assertEquals("The score 1 of 2021-09-30 is not 3 as expected",3, LiveDataTestUtil.getOrAwaitValue(liveScores).get(0).getScoreValue());
    }
}
