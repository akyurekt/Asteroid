package com.example.android.opengl;

/**
 * Created by Thomas on 08/12/2014.
 */
public class Score implements Comparable<Score> {

    private String scoreDate;
    public int scoreNum;

    public Score(String date, int num){
        scoreDate=date;
        scoreNum=num;
    }

    public int compareTo(Score sc){
        //return 0 if equal
        //1 if passed greater than this
        //-1 if this greater than passed
        return sc.scoreNum>scoreNum? 1 : sc.scoreNum<scoreNum? -1 : 0;
    }

    public String getScoreText()
    {
        return scoreDate+" - "+scoreNum;
    }
}
