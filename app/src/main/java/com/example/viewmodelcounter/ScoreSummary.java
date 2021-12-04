package com.example.viewmodelcounter;

import java.util.Date;

public class ScoreSummary {

    private Date date;
    private Long sumOfScoresByDate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSumOfScoresByDate() {
        return sumOfScoresByDate;
    }

    public void setSumOfScoresByDate(Long sumOfScoresByDate) {
        this.sumOfScoresByDate = sumOfScoresByDate;
    }

}
