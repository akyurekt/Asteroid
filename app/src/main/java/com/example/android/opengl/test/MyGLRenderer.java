package com.example.android.opengl.test;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

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
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;
    private Triangle mTriangle2;
    private Ship mShip;
    private Cube mCube;

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


    private float mAngle;
    public volatile float[] quat = {1,0,0,0};

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

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // trianglepostion = new Point(of,tr)
        // Load the texture

        //   mTextureDataHandle = TextureHelper.loadTexture(OpenGLES20Activity.getAppContext(), R.drawable.bumpy_bricks_public_domain);

        mTriangle = new Triangle();
        mTriangle2 = new Triangle();
        mShip = new Ship();
        mCube = new Cube();

    }

    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }


    @Override
    public void onDrawFrame(GL10 unused) {


        float[] scratch = new float[16];
        long time = SystemClock.uptimeMillis() % 4000L;
        timex++;


        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //Matrix.translateM(mViewMatrix,0,0,0,timex);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);





        // Calculate the projection and view transformation
      //  mSquare.draw(mMVPMatrix);
        Matrix.setRotateM(mRotationMatrix,0, (float)(2.0f*Math.acos(quat[0])*180.0f/Math.PI),quat[1],quat[2],quat[3]);

       // Matrix.setRotateM(mRotationMatrix,0,0f,0f,0f,0f);
        //scratch=mMVPMatrix.clone();
          Matrix.multiplyMM(scratch, 0,mMVPMatrix , 0,mRotationMatrix, 0);
        //  Matrix.scaleM(scratch,1,0.1f,0.1f,0.1f);
          Matrix.scaleM(mMVPMatrix,0,0.5f,0.5f,0.5f);

        //draw and position the astreoids
        mTempMatrix=mMVPMatrix.clone();

        float pos1=250f-timex;
        float pos2=170f-timex;
        float pos3=150f-timex;
        float pos4=200f-timex;



        if((numberofass<-30)) {
            timex = timex - 350f;
            numberofass = 4;

            //new seeds.securerandom has better seed and longer bit
            SecureRandom x1= new SecureRandom();
            SecureRandom y1= new SecureRandom();
            SecureRandom x2= new SecureRandom();
            SecureRandom y2= new SecureRandom();
            SecureRandom x3= new SecureRandom();
            SecureRandom y3= new SecureRandom();
            SecureRandom x4= new SecureRandom();
            SecureRandom y4= new SecureRandom();

            //use it obtain value
            float randomValue1  =  -5f  +  (5f +5f) * x1.nextFloat();
            float randomValue2  =  -5f  +  (5f +5f) * y1.nextFloat();
            float randomValue3  =  -5f  +  (5f +5f) * x2.nextFloat();
            float randomValue4  =  -5f  +  (5f +5f) * y2.nextFloat();
            float randomValue5  =  -5f  +  (5f +5f) * x3.nextFloat();
            float randomValue6  =  -5f  +  (5f +5f) * y3.nextFloat();
            float randomValue7  =  -5f  +  (5f +5f) * x4.nextFloat();
            float randomValue8  =  -5f  +  (5f +5f) * y4.nextFloat();

            xpos1=randomValue1;
            ypos1=randomValue2;
            ypos2=randomValue3;
            xpos2=randomValue4;
            ypos3=randomValue5;
            xpos3=randomValue6;
            ypos4=randomValue7;
            xpos4=randomValue8;


            Log.i("xpos1", "ypos1 " + xpos1 + ypos1 );
            Log.i("xpos2", "ypos2 " +xpos2+ypos2  );
            Log.i("xpos3", "ypos3 " + xpos3+ ypos4  );
            Log.i("xpos4", "ypos4 " + xpos4 +ypos4  );

        }

        if((300f-timex)<0)
            numberofass--;

        Matrix.translateM(mTempMatrix,0,xpos1,ypos1,pos1);
        mCube.draw(mTempMatrix);
        mTempMatrix=mMVPMatrix.clone();
        Matrix.translateM(mTempMatrix,0,xpos2,ypos2,pos2);
        mCube.draw(mTempMatrix);
        mTempMatrix=mMVPMatrix.clone();
        Matrix.translateM(mTempMatrix,0,xpos3,ypos3,pos3);
        mCube.draw(mTempMatrix);
        mTempMatrix=mMVPMatrix.clone();
        Matrix.translateM(mTempMatrix,0,xpos4,ypos4,pos4);
        mCube.draw(mTempMatrix);


       // Log.i("number of", "asstroids: " + numberofass  );
       // Log.i("time", "value  " + timex  );





        // Draw square
        Matrix.scaleM(scratch,0,0.1f,0.1f,0.1f);
      //  Matrix.translateM(scratch,0, 0f,0f,timex);

         mShip.draw(scratch);





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
}






