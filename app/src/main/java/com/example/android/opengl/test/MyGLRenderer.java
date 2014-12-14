package com.example.android.opengl.test;
import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.android.opengl.Menu;
import com.example.android.opengl.R;

import java.security.SecureRandom;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class    MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;
    private Triangle mTriangle2;
    private Ship mShip;
    private Cube mCube;
    private Skybox mSkybox;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private final float[] temporarymatrix = new float[16];
    public float x;
    public float y;
    public boolean iftouch = false;
    public boolean ifzoom = false;
    public boolean ifpinch = false;
    public float counter = 1;
    private float[] mTempMatrix = new float[16];
    public float timex = 1;
    public float timex2 = 1;
    public int numberofass = 4;
    public int numberofass2 = 4;
    public int counterofdeath = 0;


    private float mAngle;
    public volatile float[] quat = {1, 0, 0, 0};
    public static float  axisx;
    public static float axisy;
    public float prev_axisx;
    public float prev_axisy;
    public float axisz;
    public boolean collision = true;

    public float ypos1 = 2.5f;
    public float xpos1 = 0f;
    public float xpos2 = 2.5f;
    public float ypos2 = 0f;
    public float xpos3 = -2.5f;
    public float ypos3 = 0f;
    public float xpos4 = 0f;
    public float ypos4 = -2.5f;

    public float xpos5 = 0.75f;
    public float ypos5 = -0.5f;
    public float xpos6 = -0.750f;
    public float ypos6 = -0.5f;
    public float xpos7 = 0.75f;
    public float ypos7 = -0.5f;
    public float xpos8 = -0.75f;
    public float ypos8 = -0.5f;




    public float xpos9=7f;
    public float ypos9=7f;
    public float xpos10=-7f;
    public float ypos10=-7f;
    public float xpos11=7f;
    public float ypos11=-7f;
    public float xpos12=-7f;
    public float ypos12=7f;


    public float xpos13=10f;
    public float ypos13=10f;
    public float xpos14=-10f;
    public float ypos14=-10f;
    public float xpos15=10f;
    public float ypos15=-10f;
    public float xpos16=-10f;
    public float ypos16=10f;




    public static float loopcounter=1;

   public static  MediaPlayer mp2=null ;
    private static int length=0;
    public int mediaplayervalue=0;
    public float screenheight;
    public float screenwidth;
    public static float loopcounter_increment=0.03f;



    public static boolean isalive=true;

    int[] sounds={R.raw.mario, R.raw.snake_death, R.raw.you_died,R.raw.megaman};

    private final Context mActivityContext;

    public MyGLRenderer(final Context activityContext) {
        mActivityContext = activityContext;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        //  activity=this;

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // trianglepostion = new Point(of,tr)
        // Load the texture

        //   mTextureDataHandle = TextureHelper.loadTexture(OpenGLES20Activity.getAppContext(), R.drawable.bumpy_bricks_public_domain);

        // mTriangle = new Triangle();
        // mTriangle2 = new Triangle();

        mShip = new Ship();
        mCube = new Cube(mActivityContext);
        mSkybox = new Skybox(mActivityContext);
        mTriangle = new Triangle(mActivityContext);

        collision = false;
      //
        Random r = new Random();
        int deathtune = r.nextInt(4) ;
        if(OpenGLES20Activity.ifnormal) {
            mp2 = MediaPlayer.create(OpenGLES20Activity.getAppContext(), sounds[deathtune]);
            Log.i("loaded ", "music for easy  " );
        }
        else
            mp2 = MediaPlayer.create(OpenGLES20Activity2.getAppContext(), sounds[deathtune]);
       // else
       // mp2 = MediaPlayer.create(OpenGLES20Activity2.getAppContext(),sounds[deathtune]);


    }

    @Override
    public void onDrawFrame(GL10 unused) {

        //if alive
        if (counterofdeath < 1) {

            if(Menu.stagenumber==0) {
                ifalive();
            }
            if(Menu.stagenumber==1){
                ifalive2();
            }
            isalive=true;





        }
        //else black screen
        else {
            isalive=false;

            sound(counterofdeath);
            counterofdeath++;
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
            Matrix.scaleM(mMVPMatrix, 0, 2f, 3f, 2f);
            mTriangle.Draw(mMVPMatrix);
            loopcounter=1;


        }
    }
    public static void pause_music()
    {

        if(mp2!=null) {

            Log.i("Trying to pause", "this dumb file  duration    " );
            int duration = mp2.getDuration();
            Log.i("inside duration thing?", "this dumb file  duration    " + duration  );
            if (duration != 0) {

                mp2.stop();
                mp2.release();
            }


        }
    }


    public void ifalive() {


        float[] scratch = new float[16];
        long time = SystemClock.uptimeMillis() % 4000L;
        timex=timex+loopcounter;
        timex2=timex2+loopcounter;


        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

       // Matrix.translateM(mViewMatrix,0,axisx,axisy,0f);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //skybox
        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix, 0, 4f, 4f, 4f);
       // Matrix.translateM(mTempMatrix,0,0f,0f,0f);
        mSkybox.draw(mTempMatrix);


        //  Calculate the projection and view transformation
        //  mSquare.draw(mMVPMatrix);
        //  Matrix.setRotateM(mRotationMatrix,0, (float)(2.0f*Math.acos(quat[0])*180.0f/Math.PI),quat[1],quat[2],quat[3]);
        Matrix.setIdentityM(mRotationMatrix, 0);
        //  Matrix.setRotateM(mRotationMatrix,0,0f,0f,0f,0f);
        //  scratch=mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        //  Matrix.scaleM(scratch,1,0.1f,0.1f,0.1f);
        Matrix.scaleM(mMVPMatrix, 0, 0.5f, 0.5f, 0.5f);

        //draw and position the astreoids


        float pos1 = 250f - timex;
        float pos2 = 170f - timex;
        float pos3 = 150f - timex;
        float pos4 = 200f - timex;

        float pos5 = 275f - timex2;
        float pos6 = 300f - timex2;
        float pos7 = 350f - timex2;
        float pos8 = 400f - timex2;

        //dummy asteroids
        float pos9=225f-timex;
        float pos10=160-timex;
        float pos11=375-timex2;
        float pos12=325-timex2;

        float pos13=180f-timex;
        float pos14=240f-timex;
        float pos15=290f-timex2;
        float pos16=260f-timex2;

      //  Log.i("pos1", pos5 + " pos6 " + pos6);

        //if asstroids are behind,reset the time and set number of asstroids to 4
        if ((numberofass < 0)) {
            timex = timex - 350f;
            numberofass = 4;

            //new seeds.securerandom has better seed and longer bit
            SecureRandom x1 = new SecureRandom();
            SecureRandom y1 = new SecureRandom();
            SecureRandom x2 = new SecureRandom();
            SecureRandom y2 = new SecureRandom();
            SecureRandom x3 = new SecureRandom();
            SecureRandom y3 = new SecureRandom();
            SecureRandom x4 = new SecureRandom();
            SecureRandom y4 = new SecureRandom();




            //use it obtain value.using min +(max-min) *seed
            float randomValue1 = -5f + (5f + 5f) * x1.nextFloat();//anywhere in the whole screen
            float randomValue2 = -5f + (5f + 5f) * y1.nextFloat();//anywhere in the whole screen
            float randomValue3 = -5f + (5f + 5f) * x2.nextFloat();//anywhere in the whole screen
            float randomValue4 = -5f + (5f + 5f) * y2.nextFloat();//anywhere in the whole screen
            float randomValue5 = -5f + (5f + 5f) * x3.nextFloat();//anywhere in the whole screen
            float randomValue6 = -5f + (5f + 5f) * y3.nextFloat();//anywhere in the whole screen
            float randomValue7 = -5f + (5f + 5f) * x4.nextFloat();//anywhere in the whole screen
            float randomValue8 = -5f + (5f + 5f) * y4.nextFloat();//anywhere in the whole screen
//

//
            xpos1 = randomValue1;
            ypos1 = randomValue2;
            ypos2 = randomValue3;
            xpos2 = randomValue4;
            ypos3 = randomValue5;
            xpos3 = randomValue6;
            ypos4 = randomValue7;
            xpos4 = randomValue8;
//  //


             }



        if((numberofass2 < 0)) {
            timex2 = timex2 - 350f;
            numberofass2 = 4;

            SecureRandom x5 = new SecureRandom();
            SecureRandom y5 = new SecureRandom();
            SecureRandom x6 = new SecureRandom();
            SecureRandom y6 = new SecureRandom();
            SecureRandom x7 = new SecureRandom();
            SecureRandom y7 = new SecureRandom();
            SecureRandom x8 = new SecureRandom();
            SecureRandom y8 = new SecureRandom();


            float randomValue9 = -5f + (1f + 5f) * x5.nextFloat();//top left quad.x max is 0
            float randomValue10 = 1 + (5f - 1) * y5.nextFloat();//top left quad.y min is 0
            float randomValue11 = 1f + (5f - 1f) * x6.nextFloat();//top right quad.x min is 0
            float randomValue12 = 1 + (5f - 1) * y6.nextFloat();//top right quad.y min is 0
            float randomValue13 = -5f + ((1 + 5f) * x7.nextFloat());//bottom left quad.x max is 0
            float randomValue14 = -5f + (-1 + 5f) * y7.nextFloat();//bottom left quad.y max is 0
            float randomValue15 = 1 + (5f - 1) * x8.nextFloat();     //bottom right quad.x min is 0
            float randomValue16 = -5f + ((-1 + 5) * y8.nextFloat());//bottom right quad.y max is 0
            // Log.i("x", randomValue15 + "  y " + randomValue16  );

            xpos5 = randomValue9;
            ypos5 = randomValue10;
            xpos6 = randomValue11;
            ypos6 = randomValue12;
            xpos7 = randomValue13;
            ypos7 = randomValue14;
            xpos8 = randomValue15;
            ypos8 = randomValue16;

        }






        //if the first cube has passed the screen decrese number of cubes and wait a bit
        if ((260f - timex) < 0) {
            numberofass--;
            if(loopcounter<10f)
            loopcounter = loopcounter + loopcounter_increment;
            Log.i("loopcounter", loopcounter + " loopcounter   " + loopcounter);
        }

        if((410f-timex2)<0) {
            numberofass2--;
            if(loopcounter<10f)
                loopcounter=loopcounter+loopcounter_increment;

            Log.i("loopcounter", loopcounter + " loopcounter   " + loopcounter);
        }
      //  Log.i("number of ass  ", numberofass2 + " pos6 " + numberofass2);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.translateM(mTempMatrix, 0, xpos1, ypos1, pos1);
       mCube.draw(mTempMatrix);

       mTempMatrix = mMVPMatrix.clone();
       Matrix.translateM(mTempMatrix, 0, xpos2, ypos2, pos2);
       mCube.draw(mTempMatrix);

       mTempMatrix = mMVPMatrix.clone();
       Matrix.translateM(mTempMatrix, 0, xpos3, ypos3, pos3);
       mCube.draw(mTempMatrix);

       mTempMatrix = mMVPMatrix.clone();
       Matrix.translateM(mTempMatrix, 0, xpos4, ypos4, pos4);
       mCube.draw(mTempMatrix);


       mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
       Matrix.translateM(mTempMatrix, 0, xpos5, ypos5, pos5);
       mCube.draw(mTempMatrix);

       mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
       Matrix.translateM(mTempMatrix, 0, xpos6, ypos6, pos6);
       mCube.draw(mTempMatrix);

       mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
       Matrix.translateM(mTempMatrix, 0, xpos7, ypos7, pos7);
       mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos8, ypos8, pos8);
        mCube.draw(mTempMatrix);


        //dummy asteroids
        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos9, ypos9, pos9);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos10, ypos10, pos10);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos11, ypos11, pos11);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos12, ypos12, pos12);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos13, ypos13, pos13);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos14, ypos14, pos14);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos15, ypos15, pos15);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos16, ypos16, pos16);
        mCube.draw(mTempMatrix);




        // Draw square
        Matrix.scaleM(scratch, 0, 0.05f, 0.05f, 0.05f);
        Matrix.translateM(scratch, 0, axisx * 5, axisy * 5, 0f);
       // Log.i("xaxis", axisx + " yaxis   " + axisy);

        //collisions test variables
        //finding distance between astroids and ship
        float distancebet1 = ((axisx - xpos1) * (axisx - xpos1)) + ((axisy - ypos1) * (axisy - ypos1));
        float distancebet2 = ((axisx - xpos2) * (axisx - xpos2)) + ((axisy - ypos2) * (axisy - ypos2));
        float distancebet3 = ((axisx - xpos3) * (axisx - xpos3)) + ((axisy - ypos3) * (axisy - ypos3));
        float distancebet4 = ((axisx - xpos4) * (axisx - xpos4)) + ((axisy - ypos4) * (axisy - ypos4));

        float distancebet5 = ((axisx - xpos5) * (axisx - xpos5)) + ((axisy - ypos5) * (axisy - ypos5));
        float distancebet6 = ((axisx - xpos6) * (axisx - xpos6)) + ((axisy - ypos6) * (axisy - ypos6));
        float distancebet7 = ((axisx - xpos7) * (axisx - xpos7)) + ((axisy - ypos7) * (axisy - ypos7));
        float distancebet8 = ((axisx - xpos8) * (axisx - xpos8)) + ((axisy - ypos8) * (axisy - ypos8));


   //testing for collision
     if (pos1 > 0 && pos1 < 10) {
         if (distancebet1 < 2) {
             collision = true;
         }
     }
     if (pos2 > 0 && pos2 < 10) {
         if (distancebet2 < 2) {
             collision = true;
         }
     }
     if (pos3 > 0 && pos3 < 10) {
         if (distancebet3 < 2) {
             collision = true;
         }
     }
     if (pos4 > 0 && pos4 < 10) {
         if (distancebet4 < 2) {
             collision = true;
         }
     }
     if (pos5 > 0 && pos5 < 10) {
         if (distancebet5 < 2) {
             collision = true;
         }
     }
     if (pos6 > 0 && pos6 < 10) {
         if (distancebet6 < 2) {
             collision = true;
         }
     }
     if (pos7 > 0 && pos7 < 10) {
         if (distancebet7 < 2) {
             collision = true;
         }
     }
     if (pos8 > 0 && pos8 < 10) {
         if (distancebet8 < 2) {
             collision = true;
         }
     }


     //   Log.i("The", "collision  " + collision);
        // Log.i("xpos1", "ypos1 " + xpos1 + ypos1 );
        if (!collision) {
            isalive=true;
            mShip.draw(scratch);
        } else {

            sound(counterofdeath);
            counterofdeath++;

            //code to finish the acitivity after a delay
            // activity.finish();
        }


    }

    public void ifalive2() {


        float[] scratch = new float[16];
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);


        timex=timex+loopcounter;
        timex2=timex2+loopcounter;


        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Matrix.translateM(mViewMatrix,0,axisx,axisy,0f);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //skybox
        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix, 0, 4f, 4f, 4f);
        // Matrix.translateM(mTempMatrix,0,0f,0f,0f);
        mSkybox.draw(mTempMatrix);


        //  Calculate the projection and view transformation
        //  mSquare.draw(mMVPMatrix);
        //  Matrix.setRotateM(mRotationMatrix,0, (float)(2.0f*Math.acos(quat[0])*180.0f/Math.PI),quat[1],quat[2],quat[3]);
        Matrix.setIdentityM(mRotationMatrix, 0);
        //  Matrix.setRotateM(mRotationMatrix,0,0f,0f,0f,0f);
        //  scratch=mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        //  Matrix.scaleM(scratch,1,0.1f,0.1f,0.1f);
        Matrix.scaleM(mMVPMatrix, 0, 0.5f, 0.5f, 0.5f);

        //draw and position the astreoids


        float pos1 = 2500f - timex;
        float pos2 = 1700f - timex;
        float pos3 = 1500f - timex;
        float pos4 = 2000f - timex;

        float pos5 = 2750f - timex2;
        float pos6 = 3000f - timex2;
        float pos7 = 3500f - timex2;
        float pos8 = 4000f - timex2;


        float pos9=2250f-timex;
        float pos10=1600f-timex;
        float pos11=3750f-timex2;
        float pos12=3250f-timex2;

        float pos13=1800f-timex;
        float pos14=2400f-timex;
        float pos15=2900f-timex2;
        float pos16=2600f-timex2;
        //  Log.i("pos1", pos5 + " pos6 " + pos6);

        //if asstroids are behind,reset the time and set number of asstroids to 4
        if ((numberofass < 0)) {
            timex = timex - 350f;
            numberofass = 4;

            //new seeds.securerandom has better seed and longer bit
            SecureRandom x1 = new SecureRandom();
            SecureRandom y1 = new SecureRandom();
            SecureRandom x2 = new SecureRandom();
            SecureRandom y2 = new SecureRandom();
            SecureRandom x3 = new SecureRandom();
            SecureRandom y3 = new SecureRandom();
            SecureRandom x4 = new SecureRandom();
            SecureRandom y4 = new SecureRandom();




            //use it obtain value.using min +(max-min) *seed
            float randomValue1 = -2.5f + (0f + 2.5f) * x1.nextFloat();//top left hexant
            float randomValue2 = 1.6f + (5f - 1.6f) * y1.nextFloat();//top left hexant
            float randomValue3 = 0f + (2.5f -0f) * x2.nextFloat();//top right hexant
            float randomValue4 = 1.6f + (5f - 1.6f) * y2.nextFloat();//top right hexant
            float randomValue5 = -2.5f + (0f + 2.5f) * x3.nextFloat();//middle left hexant
            float randomValue6 = -1.6f + (1.6f + 1.6f) * y3.nextFloat();//middle left hexant
            float randomValue7 = 0f + (2.5f + 0f) * x4.nextFloat();//middle right hexant
            float randomValue8 = -1.6f + (1.6f + 1.6f) * y4.nextFloat();//middle right hexant
//

//
            xpos1 = randomValue1;
            ypos1 = randomValue2;
            ypos2 = randomValue3;
            xpos2 = randomValue4;
            ypos3 = randomValue5;
            xpos3 = randomValue6;
            ypos4 = randomValue7;
            xpos4 = randomValue8;
//  //


        }



        if((numberofass2 < 0)) {
            timex2 = timex2 - 350f;
            numberofass2 = 4;

            SecureRandom x5 = new SecureRandom();
            SecureRandom y5 = new SecureRandom();
            SecureRandom x6 = new SecureRandom();
            SecureRandom y6 = new SecureRandom();
            SecureRandom x7 = new SecureRandom();
            SecureRandom y7 = new SecureRandom();
            SecureRandom x8 = new SecureRandom();
            SecureRandom y8 = new SecureRandom();

            //use it obtain value.using min +(max-min) *seed
            float randomValue9 = -2.5f + (0f + 2.5f) * x5.nextFloat();//bottom left hexant
            float randomValue10 = -5f + (-1.6f + 5) * y5.nextFloat();  //bottom left hexant
            float randomValue11 = 0f + (2.5f - 0f) * x6.nextFloat();//bottom right hexant
            float randomValue12 = -5 + (-1.6f + 5) * y6.nextFloat();  //bottom right hexant
           // float randomValue13 = -5f + ((5 + 5f) * x7.nextFloat());//bottom left quad.x max is 0
           // float randomValue14 = -5f + (5 + 5f) * y7.nextFloat();//bottom left quad.y max is 0
           float randomValue15 = -5f + (5f +5f) * x8.nextFloat();   // anywhere
           float randomValue16 = -5f + ((5f + 5f) * y8.nextFloat());// anywhere
            // Log.i("x", randomValue15 + "  y " + randomValue16  );

            xpos5 = randomValue9;
            ypos5 = randomValue10;
            xpos6 = randomValue11;
            ypos6 = randomValue12;
             xpos7 = prev_axisx;
             ypos7 = prev_axisy;
            xpos8 = randomValue15;
            ypos8 = randomValue16;

        }






        //if the first cube has passed the screen decrese number of cubes and wait a bit
        if ((260f - timex) < 0) {
            numberofass--;
            if(loopcounter<10f)
                loopcounter = loopcounter + loopcounter_increment;
            Log.i("loopcounter", loopcounter + " loopcounter   " + loopcounter);
        }

        if((410f-timex2)<0) {
            numberofass2--;
            if(loopcounter<10f)
                loopcounter=loopcounter+loopcounter_increment;

            Log.i("loopcounter", loopcounter + " loopcounter   " + loopcounter);
        }
        //  Log.i("number of ass  ", numberofass2 + " pos6 " + numberofass2);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);

        Matrix.translateM(mTempMatrix, 0,xpos1, ypos1, pos1);
         mCube.draw(mTempMatrix);
//
           mTempMatrix = mMVPMatrix.clone();
            Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
           Matrix.translateM(mTempMatrix, 0, xpos2, ypos2, pos2);
           mCube.draw(mTempMatrix);
////
           mTempMatrix = mMVPMatrix.clone();
            Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
           Matrix.translateM(mTempMatrix, 0, xpos3, ypos3, pos3);
           mCube.draw(mTempMatrix);
////
           mTempMatrix = mMVPMatrix.clone();
            Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
           Matrix.translateM(mTempMatrix, 0, xpos4, ypos4, pos4);
           mCube.draw(mTempMatrix);
////
////
           mTempMatrix = mMVPMatrix.clone();
           Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
           Matrix.translateM(mTempMatrix, 0, xpos5, ypos5, pos5);
           mCube.draw(mTempMatrix);
////
           mTempMatrix = mMVPMatrix.clone();
           Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
           Matrix.translateM(mTempMatrix, 0, xpos6, ypos6, pos6);
           mCube.draw(mTempMatrix);
//
         mTempMatrix = mMVPMatrix.clone();
         Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
         Matrix.translateM(mTempMatrix, 0, xpos7,ypos7, pos7);
         mCube.draw(mTempMatrix);
////
         mTempMatrix = mMVPMatrix.clone();
         Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
         Matrix.translateM(mTempMatrix, 0,xpos8,ypos8, pos8);
         mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos9, ypos9, pos9);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos10, ypos10, pos10);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos11, ypos11, pos11);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos12, ypos12, pos12);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos13, ypos13, pos13);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos14, ypos14, pos14);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos15, ypos15, pos15);
        mCube.draw(mTempMatrix);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.scaleM(mTempMatrix,0,0.6f,0.6f,0.6f);
        Matrix.translateM(mTempMatrix, 0, xpos16, ypos16, pos16);
        mCube.draw(mTempMatrix);






        // Draw square
        Matrix.scaleM(scratch, 0, 0.05f, 0.05f, 0.05f);
        Matrix.translateM(scratch, 0, axisx * 5, axisy * 5, 0f);
        prev_axisx=axisx;
        prev_axisy=axisy;
        // Log.i("xaxis", axisx*5 + " yaxis   " + axisy*5);

        //collisions test variables
        //finding distance between astroids and ship
        float distancebet1 = ((axisx - xpos1) * (axisx - xpos1)) + ((axisy - ypos1) * (axisy - ypos1));
        float distancebet2 = ((axisx - xpos2) * (axisx - xpos2)) + ((axisy - ypos2) * (axisy - ypos2));
        float distancebet3 = ((axisx - xpos3) * (axisx - xpos3)) + ((axisy - ypos3) * (axisy - ypos3));
        float distancebet4 = ((axisx - xpos4) * (axisx - xpos4)) + ((axisy - ypos4) * (axisy - ypos4));

        float distancebet5 = ((axisx - xpos5) * (axisx - xpos5)) + ((axisy - ypos5) * (axisy - ypos5));
        float distancebet6 = ((axisx - xpos6) * (axisx - xpos6)) + ((axisy - ypos6) * (axisy - ypos6));
        float distancebet7 = ((axisx - xpos7) * (axisx - xpos7)) + ((axisy - ypos7) * (axisy - ypos7));
        float distancebet8 = ((axisx - xpos8) * (axisx - xpos8)) + ((axisy - ypos8) * (axisy - ypos8));


        //testing for collision
      if (pos1 > -3 && pos1 < 0) {
          Log.i("distance between the asteroid 1", distancebet1 + " distance between    " + distancebet1);
          if (distancebet1 < 2) {
              collision = true;
          }
      }
        if (pos2 > -3 && pos2 < 0) {
            if (distancebet2 < 2) {
                collision = true;
            }
        }
        if (pos3 > -3 && pos3 < 0) {
            if (distancebet3 < 2) {
                collision = true;
            }
        }
        if (pos4 > -3 && pos4 < 0) {
            if (distancebet4 < 2) {
                collision = true;
            }
        }
        if (pos5 > -3 && pos5 < 0) {
            if (distancebet5 < 2) {
                collision = true;
            }
        }
        if (pos6 > -3 && pos6 < 0) {
            if (distancebet6 < 2) {
                collision = true;
            }
        }
      //  if (pos7 > -3 && pos7 < 0) {
      //      if (distancebet7 < 2) {
      //          collision = true;
      //      }
      //  }
      //  if (pos8 > -3 && pos8 < 0) {
      //      if (distancebet8 < 2) {
      //          collision = true;
      //      }
      //  }


        //   Log.i("The", "collision  " + collision);
        // Log.i("xpos1", "ypos1 " + xpos1 + ypos1 );
        if (!collision) {
            isalive=true;
            mShip.draw(scratch);
        } else {

            sound(counterofdeath);
            counterofdeath++;

            //code to finish the acitivity after a delay
            // activity.finish();
        }


    }




    public void testLogMessage() {
        Log.i("Test", "TAB: " + "Test" + "AAAAAAAAA");
    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);
        screenheight = height;
        screenwidth = width;

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 10000);
        testLogMessage();


    }

    /**
     * Utility method for compiling a OpenGL shader.
     * <p/>
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode) {

          //  GLES20.glEnable(GLES20.GL_CULL_FACE);
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     * <p/>
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    public float getscreenwidth() {
        return screenwidth;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }





    //controlls sound files
    public void sound(int counter) {
        if (counter == 10) {


            Log.i("playing", "death file  " + counter);

            if(OpenGLES20Activity.ifnormal) {

             //   mp2 = MediaPlayer.create(OpenGLES20Activity.getAppContext(),sounds[1]);
             //   mp2.start();

            }
            else
            {
               // mp2 = MediaPlayer.create(OpenGLES20Activity2.getAppContext(),sounds[deathtune]);
               // mp2.start();
            }
            if(mp2!=null) {
                mp2.start();
            }






        }
    }

}






