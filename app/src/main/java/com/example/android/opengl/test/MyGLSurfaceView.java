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
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
 public class MyGLSurfaceView extends GLSurfaceView {



    private final MyGLRenderer mRenderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    private float mPreviousX2;
    private float mPreviousY2;

    private float aceelerator_dx;
    private float aceelerator_dy;
    private float prev_axisx;
    private float prev_axisy;

    boolean loaded = false;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    //add reference to prevent garbage collecter from cleaning the object

     public static MediaPlayer background;



    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
        getHolder().addCallback(this);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);


        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer(context);
        setRenderer(mRenderer);



            // mSensor=mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
          //  mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);


        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);


        // background.prepare();
       // if(background!=null) {
       //     background.start();
       // }
       // background.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
       //     @Override
       //     public void onCompletion(MediaPlayer mp) {
       //         mp.stop();
       //         mp.release();
       //     }
       // });


//


    }
    @Override
    public void onResume(){
        super.onResume();


        Log.i("On", "Resume "  );




    }
    @Override
    public void onPause() {
        super.onPause();
        MyGLRenderer.mp2=null;
        Log.i("On", "Pause from surfaceview ");
        MyGLRenderer.pause_music();




    }





  // @Override
  // public boolean onTouchEvent(MotionEvent e) {

  //   //  mRenderer.iftouch=false;

  //     // MotionEvent reports input details from the touch screen
  //     // and other input controls. In this case, you are only
  //     // interested in events where the touch position changed.
  //     float x = e.getX();
  //     float y = e.getY();

  //     switch (e.getAction()) {
  //         //need to add thread safe technique of que runnable.
  //         case MotionEvent.ACTION_MOVE:

  //             float dx = x - mPreviousX;
  //             float dy = y - mPreviousY;


  //             // reverse direction of rotation above the mid-line
  //             if (y > getHeight() / 2) {
  //                 dx = dx * -1;
  //             }
  //             // reverse direction of rotation to left of the mid-line
  //             if (x < getWidth() / 2) {
  //                 dy = dy * -1;
  //             }

  //             mRenderer.setAngle(
  //                     mRenderer.getAngle() +
  //                             ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
  //             setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
  //             float sceneY = (y / 1280) * -2.0f + 1.0f;
  //             float sceneX = -((x / 720) * -2.0f + 1.0f);
  //             mRenderer.x = sceneX;
  //             mRenderer.y = sceneY;
  //             requestRender();
  //             setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);



  //             mPreviousX = x;
  //             mPreviousY = y;


  //         case MotionEvent.ACTION_POINTER_INDEX_MASK:
  //             int pointerCount = e.getPointerCount();
  //             if(pointerCount==2) {
  //                 float x2=e.getX(1);
  //                 float y2=e.getX(1);
  //                 float distancesqrt=(((x2-x)*(x2-x)) +((y2-y)*(y2-y)));


  //                 float prevdistancesqrt=(((mPreviousX2-mPreviousX)*(mPreviousX2-mPreviousX)) +((mPreviousY2-mPreviousY)*(mPreviousY2-mPreviousY)));

  //                 if (x > (0.8 * (mRenderer.getscreenwidth()))) {
  //                     x = (float) ((0.8 * (mRenderer.getscreenwidth())));
  //                 }

  //                 if(distancesqrt>prevdistancesqrt) {
  //                     mRenderer.iftouch = true;

  //                 }
  //                 if(distancesqrt<prevdistancesqrt)
  //                 {
  //                     mRenderer.ifpinch=true;

  //                     Sound(0);

  //                 }
  //                 mPreviousX2=x2;
  //                 mPreviousY2=y2;
  //                 Sound(2);
  //             }

  //             else{
  //                 Log.i("One", "finger");
  //             }
  //        // mRenderer.iftouch=false;


  //     }

  //     mPreviousX = x;
  //     mPreviousY = y;
  //     return true;
  // }


}


