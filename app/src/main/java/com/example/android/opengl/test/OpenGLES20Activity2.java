/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.opengl.R;
import com.example.android.opengl.Score;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpenGLES20Activity2 extends Activity implements SensorEventListener {

    private GLSurfaceView mGLView;
    private static Context context;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    public double aceelerator_dx;
    public double aceelerator_dy;
    public float prev_axisx;
    public float prev_axisy;

    public int sensornumber=0;
    public float firstx;
    public float firsty;



    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    int seconds=0;

    public static boolean ifhard=false;
    public static boolean ifnormal=false;

    static final float ALPHA = 0.f;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this,null);
        GLSurfaceView glSurfaceView=(GLSurfaceView)findViewById(R.id.glsurfaceview);
       // glSurfaceView.setRenderer(mGLView);
       // setContentView(mGLView);
       // mGLView= (MyGLSurfaceView)findViewById(R.id.glsurfaceview);

        setContentView(R.layout.surfaceview);
        MyGLRenderer.isalive=true;


        OpenGLES20Activity2.context = getApplicationContext();

        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                   score();

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

       Intent svc=new Intent(this,MusicService.class);
       startService(svc); //OR stopService(svc);

      //  if(ifhard)
      //  {
            MyGLRenderer.loopcounter_increment =0.07f;
            Log.i("Hard mode", "here");
      //  }
      //  else if(ifnormal)
      //  {
      //      MyGLRenderer.loopcounter_increment=0.03f;
      //      Log.i("easy mode", "here");
      //  }



    }

    private void setHighScore(){
    //set high score
        int exScore = seconds;
       // if(!MyGLRenderer.isalive) {
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
        DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
       // String dateOutput = dateForm.format(new Date());
       SharedPreferences myPrefs = this.getSharedPreferences("userfile", MODE_WORLD_READABLE);
       String nameOutput =myPrefs.getString("username","user1");

        String scores = gamePrefs.getString("highScores", "");
        if(scores.length()>0){
            //we have existing scores
            List<Score> scoreStrings = new ArrayList<Score>();
            String[] exScores = scores.split("\\|");
            for(String eSc : exScores){
                String[] parts = eSc.split(" - ");
                scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }
            Score newScore = new Score(nameOutput, exScore);
            scoreStrings.add(newScore);
            Collections.sort(scoreStrings);
            StringBuilder scoreBuild = new StringBuilder("");
            for(int s=0; s<scoreStrings.size(); s++){
                if(s>=10) break;//only want ten
                if(s>0) scoreBuild.append("|");//pipe separate the score strings
                scoreBuild.append(scoreStrings.get(s).getScoreText());
            }
//write to prefs
            scoreEdit.putString("highScores", scoreBuild.toString());
            scoreEdit.commit();
        }
        else{
            //no existing scores
            scoreEdit.putString("highScores", ""+nameOutput+" - "+exScore);
            scoreEdit.commit();
        }
       // }
    }

    public static Context getAppContext() {
        return OpenGLES20Activity2.context;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    void score()
    {
        if(MyGLRenderer.isalive)
        seconds=seconds+(1*((int)(10f*MyGLRenderer.loopcounter)));
        if(!MyGLRenderer.isalive) {
            stop_music();

        }


        TextView textElement = (TextView) findViewById(R.id.this_is_id_name);

       // if(!MyGLRenderer.isalive)
          //  setHighScore();
        //  seconds=seconds++;
        textElement.setText("Score: "+ seconds); //leave this line to assign a specific text


    }
    protected void onDestroy(){
        setHighScore();
        Intent svc=new Intent(this,MusicService.class);
        stopService(svc);
        super.onDestroy();
    }

    public  void stop_music()
    {
        Intent svc = new Intent(this, MusicService.class);
        stopService(svc);


    }



    @Override
    protected void onPause() {
        super.onPause();

      //  mGLView.stopSensors();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
        mSensorManager.unregisterListener(this);
        Intent svc=new Intent(this,MusicService.class);
        stopService(svc);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
         mGLView.onResume();
      // mGLView.startSensors();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onSensorChanged(SensorEvent event) {

        // Log.i("sensor", "test"+event);
        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
      //  if(sensornumber==1) {
      //      firstx=event.values[0];
      //      firsty=event.values[1];
//
      //  }
//
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            axisX=axisX-firstx;
            axisY=axisY-firsty;

               aceelerator_dx=Math.sqrt((axisX-prev_axisx)*(axisX-prev_axisx));
               aceelerator_dy =Math.sqrt((axisY-prev_axisy)*(axisY-prev_axisy));


                   if (axisX>4)
                       axisX=4;
                   if(axisX<-4)
                      axisX=-4;
                    if(axisY>4)
                       axisY=4;
                   if(axisY<-4)
                      axisY=-4;
            if(aceelerator_dx<0.15) {
               // MyGLRenderer.axisx = prev_axisx;

            }
            else {
                Log.i("axisx  difference", "here" + aceelerator_dx);
                MyGLRenderer.axisx = axisX;

            }
            if(aceelerator_dy<0.15) {
            } // MyGLRenderer.axisy=prev_axisy;
            else {
                MyGLRenderer.axisy = axisY;

            }

            prev_axisx=axisX;
            prev_axisy=axisY;

                // MyGLRen1erer.axisx=axisX;

               //  MyGLRenderer.axisy=axisY;
                  //Log.i("axisy", "here" + axisY);




//



            //  ((MyGLSurfaceView)this.getApplication()).setpositions(axisX,axisY);
            // MyGLSurfaceView.So





            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)


            //  mSensorManager.getQuaternionFromVector(mRenderer.quat,event.values);

            //  Log.i("Sensor Orientation GyroScope", "axisX: " + axisX +
            //                  " axisY: " + axisY +
            //                  " axisZ: " + axisZ +
            //                  "omegamagnitude" + omegaMagnitude
            // );

        }
        timestamp = event.timestamp;
        sensornumber++;
      // Log.i("timestamp ", "here" + sensornumber);

    }







}