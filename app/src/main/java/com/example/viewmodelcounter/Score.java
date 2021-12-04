package com.example.viewmodelcounter;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

//@Fts4 //full-text search (FTS) enabled
@Entity(tableName = "scores")
public class Score {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int uid;

    @ColumnInfo(name = "score_value")
    private int scoreValue;

    @ColumnInfo(name = "score_date")
    private Date scoreDate;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return getUid() == score.getUid() && getScoreValue() == score.getScoreValue() && Objects.equals(getScoreDate(), score.getScoreDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getScoreValue(), getScoreDate());
    }
}
