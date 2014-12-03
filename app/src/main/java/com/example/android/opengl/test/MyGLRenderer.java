package com.example.android.opengl.test;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.android.opengl.R;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
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
    public float timex=1;
    public int numberofass=4;
    public int counterofdeath=0;


    private float mAngle;
    public volatile float[] quat = {1,0,0,0};
    public float axisx;
    public float axisy;
    public float axisz;
    public boolean collision=true ;

    public float ypos1=2.5f;
    public float xpos1=0f;
    public float xpos2=2.5f;
    public float ypos2=0f;
    public float xpos3=-2.5f;
    public float ypos3=0f;
    public float xpos4=0f;
    public float ypos4=-2.5f;


    public float screenheight;
    public float screenwidth;

    private final Context mActivityContext;
    public MyGLRenderer(final Context activityContext)
    {
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
        mCube = new Cube();
        mSkybox= new Skybox();
        mTriangle=new Triangle(mActivityContext);

        collision=false;

    }

    @Override
    public void onDrawFrame(GL10 unused) {

        //if alive
        if(counterofdeath<1) {

            ifalive();



        }
        //else black screen
        else
        {
            sound(counterofdeath);
            counterofdeath++;
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
            Matrix.scaleM(mMVPMatrix,0,2f,3f,2f);
            mTriangle.Draw(mMVPMatrix);


        }
    }

    public void ifalive()
    {

                float[] scratch = new float[16];
                long time = SystemClock.uptimeMillis() % 4000L;
                timex++;


                // Draw background color
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
                Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                //Matrix.translateM(mViewMatrix,0,0,0,timex);

                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

                //skybox
                mTempMatrix = mMVPMatrix.clone();
                Matrix.scaleM(mTempMatrix, 0, 20f, 20f, 20f);
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


                if ((numberofass < -30)) {
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

                    //use it obtain value
                    float randomValue1 = -5f + (5f + 5f) * x1.nextFloat();
                    float randomValue2 = -5f + (5f + 5f) * y1.nextFloat();
                    float randomValue3 = -5f + (5f + 5f) * x2.nextFloat();
                    float randomValue4 = -5f + (5f + 5f) * y2.nextFloat();
                    float randomValue5 = -5f + (5f + 5f) * x3.nextFloat();
                    float randomValue6 = -5f + (5f + 5f) * y3.nextFloat();
                    float randomValue7 = -5f + (5f + 5f) * x4.nextFloat();
                    float randomValue8 = -5f + (5f + 5f) * y4.nextFloat();

                    xpos1 = randomValue1;
                    ypos1 = randomValue2;
                    ypos2 = randomValue3;
                    xpos2 = randomValue4;
                    ypos3 = randomValue5;
                    xpos3 = randomValue6;
                    ypos4 = randomValue7;
                    xpos4 = randomValue8;




                }

                if ((220f - timex) < 0)
                    numberofass--;

                mTempMatrix = mMVPMatrix.clone();
                Matrix.translateM(mTempMatrix, 0, xpos1, ypos1, pos1);
                // Matrix.translateM(mTempMatrix,0,5f,5f,2f);
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




                // Draw square
                Matrix.scaleM(scratch, 0, 0.1f, 0.1f, 0.1f);
                Matrix.translateM(scratch, 0, axisx * 5, axisy * 5, 0f);

                //collisions test variables
                //finding distance between astroids and ship
                float distancebet1 = ((axisx - xpos1) * (axisx - xpos1)) + ((axisy - ypos1) * (axisy - ypos1));
                float distancebet2 = ((axisx - xpos2) * (axisx - xpos2)) + ((axisy - ypos2) * (axisy - ypos2));
                float distancebet3 = ((axisx - xpos3) * (axisx - xpos3)) + ((axisy - ypos3) * (axisy - ypos3));
                float distancebet4 = ((axisx - xpos4) * (axisx - xpos4)) + ((axisy - ypos4) * (axisy - ypos4));


                //testing for collision
                if (pos1 > 0 && pos1 < 10) {
                    if (distancebet1 < 1) {
                        collision = true;
                    }
                }
                if (pos2 > 0 && pos2 < 10) {
                    if (distancebet2 < 1) {
                        collision = true;
                    }
                }
                if (pos3 > 0 && pos3 < 10) {
                    if (distancebet3 < 1) {
                        collision = true;
                    }
                }
                if (pos4 > 0 && pos4 < 10) {
                    if (distancebet4 < 1) {
                        collision = true;
                    }
                }


                Log.i("The", "collision  " + collision);
                // Log.i("xpos1", "ypos1 " + xpos1 + ypos1 );
                if (collision == false) {
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
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

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
    public void sound(int counter)
    {
        if(counter==1) {
            MediaPlayer mp2 = MediaPlayer.create(OpenGLES20Activity.getAppContext(), R.raw.death_file_1);
            mp2.start();

            mp2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp2) {
                    mp2.stop();
                    //    mp.reset();

                    mp2.release();
                }
            });

        }
    }

}






