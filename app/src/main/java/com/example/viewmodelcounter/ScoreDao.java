package com.example.viewmodelcounter;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface ScoreDao {

    @RawQuery
    List<ScoreSummary> getScoreSummary(SupportSQLiteQuery query);

    //HERE WE DO THE SAME BUT WRAPPING IT TO LiveData Object, NEED TO SPECIFY WHICH TABLE NEED TO BE OBSERVED
    @RawQuery(observedEntities = Score.class)
    LiveData<List<ScoreSummary>> getScoreSummaryLive(SupportSQLiteQuery query);

    @Query("SELECT * FROM scores")
    List<Score> getAll();

    // HERE WE GET ALL SCORES BUT WRAPPING THEM To LiveData Object
    @Query("SELECT * FROM scores")
    LiveData<List<Score>> getAllLive();

    @Query("SELECT * FROM scores WHERE rowid = (:scoreId)")
    Score getScoreById(long scoreId);

    @Query("SELECT * FROM scores WHERE rowid = (:scoreId)")
    LiveData<Score> getLiveScoreById(long scoreId);

    @Query("SELECT * FROM scores WHERE rowid IN (:scoreIds)")
    List<Score> loadAllByIds(long[] scoreIds);

    @Query("SELECT * FROM scores WHERE score_value > :first AND score_value <= :last")
    List<Score> findByRange(int first, int last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertScore(Score score);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Score... scores);

    @Update(onConflict = OnConflictStrategy.REPLACE) //@Update doesn't works as expected
    int updateScore(Score score);

    @Query("DELETE FROM scores WHERE rowid = :uid ")
    int deleteScore(int uid);
}