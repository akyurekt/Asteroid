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

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.example.android.opengl.R;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
class MyGLSurfaceView extends GLSurfaceView implements SensorEventListener{

    private final MyGLRenderer mRenderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    private float mPreviousX2;
    private float mPreviousY2;

    boolean loaded = false;

    private SensorManager mSensorManager;
    private Sensor mSensor;


    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            // mSensor=mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
          //  mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);


        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);



    }
    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Log.i("On", "Resume "  );

    }
    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        Log.i("On", "Pause "  );
        Sound(1);
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }






// Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;



    public void onSensorChanged(SensorEvent event) {

       // Log.i("sensor", "test"+event);
        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (event.timestamp - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];


            // Calculate the angular speed of the sample
            float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude >1) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            mSensorManager.getQuaternionFromVector(mRenderer.quat,event.values);
        /*
            Log.i("Sensor Orientation GyroScope", "axisX: " + mRenderer.quat[0] + //
                    " axisY: " +mRenderer.quat[1] + //
                    " axisZ: " + mRenderer.quat[2]+
                    "omegamagnitude" +omegaMagnitude
                    );
           */
        }
        timestamp = event.timestamp;

    }






    public void Sound(int file){




       if(file==0) {
           MediaPlayer mp = MediaPlayer.create(OpenGLES20Activity.getAppContext(), R.raw.bayo_after_burner);


           if (mp.isPlaying()) {

               mp.pause();
               mp.stop();
               mp.release();
               Log.i("is", "playing  "  );
           } else {
                mp.start();
           }
                file = -1;
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                    }
                });
            }



         else if(file==0) {
            MediaPlayer mp2 = MediaPlayer.create(OpenGLES20Activity.getAppContext(), R.raw.sound_file_1);
            mp2.start();
            file = -1;
            mp2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp2) {
                    mp2.stop();
                //    mp.reset();
                    mp2.release();
                }
            });
        }






    };

    @Override
    public boolean onTouchEvent(MotionEvent e) {

      //  mRenderer.iftouch=false;

        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;


                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }
                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                float sceneY = (y / 1280) * -2.0f + 1.0f;
                float sceneX = -((x / 720) * -2.0f + 1.0f);
                mRenderer.x = sceneX;
                mRenderer.y = sceneY;
                requestRender();
                setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);



                mPreviousX = x;
                mPreviousY = y;


            case MotionEvent.ACTION_POINTER_INDEX_MASK:
                int pointerCount = e.getPointerCount();
                if(pointerCount==2) {
                    float x2=e.getX(1);
                    float y2=e.getX(1);
                    float distancesqrt=(((x2-x)*(x2-x)) +((y2-y)*(y2-y)));


                    float prevdistancesqrt=(((mPreviousX2-mPreviousX)*(mPreviousX2-mPreviousX)) +((mPreviousY2-mPreviousY)*(mPreviousY2-mPreviousY)));

                    if (x > (0.8 * (mRenderer.getscreenwidth()))) {
                        x = (float) ((0.8 * (mRenderer.getscreenwidth())));
                    }

                    if(distancesqrt>prevdistancesqrt) {
                        mRenderer.iftouch = true;

                    }
                    if(distancesqrt<prevdistancesqrt)
                    {
                        mRenderer.ifpinch=true;

                        Sound(0);

                    }
                    mPreviousX2=x2;
                    mPreviousY2=y2;
                    Sound(2);
                }

                else{
                    Log.i("One", "finger");
                }
           // mRenderer.iftouch=false;


        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}


