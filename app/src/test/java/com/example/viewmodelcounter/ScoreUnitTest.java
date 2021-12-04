package com.example.viewmodelcounter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ScoreUnitTest {
    private Score score, score1, score2;
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void init() throws ParseException {
        score = new Score();
        score.setScoreValue(1);
        score.setScoreDate(format.parse("2021-09-30"));
        score1 = new Score();
        score1.setScoreValue(1);
        score1.setScoreDate(format.parse("2021-09-30"));
        score2 = new Score();
        score2.setScoreValue(2);
        score2.setScoreDate(format.parse("2021-12-09"));
    }

    @Test
    public void createScore_isCorrect() throws ParseException {
        assertEquals("Score values are not the:", 1, score.getScoreValue());
        assertEquals("Score dates are not equal:",format.parse("2021-09-30"), score.getScoreDate());
    }

    @Test
    public void EqualScores_areEqual() {
        assertEquals("Score objects are not equal:", score, score1);
    }

    @Test
    public void DifferentScores_areNotEqual() {
        assertNotEquals("Score objects are equal:", score1, score2);
    }
}
