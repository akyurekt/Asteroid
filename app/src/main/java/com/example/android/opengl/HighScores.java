package com.example.android.opengl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.opengl.test.OpenGLES20Activity;

/**
 * Created by Thomas on 08/12/2014.
 */
public class HighScores extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high);
        TextView scoreView = (TextView)findViewById(R.id.high_scores_list);
        SharedPreferences scorePrefs = getSharedPreferences(OpenGLES20Activity.GAME_PREFS, 0);
        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score+"\n");
        }
        //display scores
        scoreView.setText(scoreBuild.toString());

        View background = findViewById(R.id.high_background);

        if(Menu.stagenumber==0) {
            background.setBackgroundResource(R.drawable.space);
        }
        else if(Menu.stagenumber==1){
            background.setBackgroundResource(R.drawable.space2);
        }
    }

}




