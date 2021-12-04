package com.example.viewmodelcounter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreDatabaseTest {
    private ScoreDao scoreDao;
    private AppDatabase db;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


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
    public void writeScoreAndReadIt() throws Exception {
        Score score = new Score();
        score.setScoreValue(1);
        score.setScoreDate(format.parse("2021-09-30"));
        long id = scoreDao.insertScore(score);
        Score savedScore = scoreDao.getScoreById(id);
        assertTrue(savedScore.getScoreValue() == score.getScoreValue());
    }

    @Test
    public void writeAllScoresAndReadIt() throws Exception {
        Score score1 = new Score();
        score1.setScoreValue(1);
        score1.setScoreDate(format.parse("2021-09-30"));
        int id1 = (int) scoreDao.insertScore(score1);
        score1.setUid(id1);

        Score score2 = new Score();
        score2.setScoreValue(10);
        score2.setScoreDate(format.parse("2021-09-30"));
        int id2 = (int) scoreDao.insertScore(score2);
        score2.setUid(id2);

        List<Score> scores = scoreDao.getAll();
        //NEED TO REMOVE -1 BECAUSE List BEGIN FROM 0 WHILE ID in DB BEGIN FROM 1
        assertNotEquals("Inserted score are equal to a non expected one",scores.get(id1-1), score2);
        assertEquals("Score1 in DB is not equal to the Score1 in memory",scores.get(id1-1), score1);
        assertEquals("Score2 in DB is not equal to the Score2 in memory",scores.get(id2-1), score2);
    }

    @Test
    public void rawQueryTotalScoreByDate() throws Exception {
        Score score1 = new Score();
        score1.setScoreValue(1);
        score1.setScoreDate(format.parse("2021-09-30"));
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

        Score score4 = new Score();
        score4.setScoreValue(1);
        score4.setScoreDate(format.parse("2021-10-1"));
        score4.setUid((int)scoreDao.insertScore(score4));
        Score score5 = new Score();
        score5.setScoreValue(2);
        score5.setScoreDate(format.parse("2021-10-1"));
        score5.setUid((int)scoreDao.insertScore(score5));
        Long scoreThree = new Long(3);

        //USE THE RawQuery Feature, passing the Custom Query tht return a Custom Object that differ from Score Object
        List<ScoreSummary> results = scoreDao.getScoreSummary(
                new SimpleSQLiteQuery("SELECT score_date as date, sum(score_value) as sumOfScoresByDate FROM scores GROUP BY score_date"));

        assertEquals("The total score of 2021-09-30 is not 6 as expected",scoreSix, results.get(0).getSumOfScoresByDate());
        assertEquals("The total score of 2021-09-30 is not 3 as expected",scoreThree, results.get(1).getSumOfScoresByDate());
    }

    @Test
    public void writeScoreAndDeleteIt() throws Exception {
        Score score = new Score();
        score.setScoreValue(1);
        score.setScoreDate(format.parse("2021-09-30"));
        score.setUid((int)scoreDao.insertScore(score));
        scoreDao.deleteScore(score.getUid());
        assertTrue(scoreDao.getAll().size() == 0);
    }

}
