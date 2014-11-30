package com.example.android.opengl.test;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

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
    public float rotatex;

    private float mAngle;
    public volatile float[] quat = {1,0,0,0};


    public float screenheight;
    public float screenwidth;
    private boolean pressed = false;
    private Point position;
    private final float[] invertedViewProjectionMatrix = new float[16];


    /**
     * Store our model data in a float buffer.
     */


    /**
     * This will be used to pass in the texture.
     */
    private int mTextureUniformHandle;

    /**
     * This will be used to pass in model texture coordinate information.
     */
    private int mTextureCoordinateHandle;

    /**
     * Size of the texture coordinate data in elements.
     */
    private final int mTextureCoordinateDataSize = 2;
    private int mTextureDataHandle;

    /**
     * This is a handle to our texture data.
     */

    //mActivityContext = activityContext;
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
        // position= new Point(0f ,0.5f , 0.4f);
    }

    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }


    @Override
    public void onDrawFrame(GL10 unused) {


        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);


        // Calculate the projection and view transformation
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
       // Matrix.setRotateM(mRotationMatrix,0, angle,quat[1],quat[2],quat[3]);
       //  Matrix.setRotateM(mRotationMatrix,0,rotatex*100f,1f,1f,1f);
        Matrix.setRotateM(mRotationMatrix,0, (float)(2.0f*Math.acos(quat[0])*180.0f/Math.PI),quat[1],quat[2],quat[3]);
        Matrix.multiplyMM(scratch, 0,mMVPMatrix , 0,mRotationMatrix, 0);
         Matrix.scaleM(scratch,1,.5f,.5f,.5f);
        Matrix.translateM(scratch,0,1f,1f,1f);
        // Draw square
        mShip.draw(scratch);


        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
      //  Matrix.setRotateM(mRotationMatrix,0, (float)(2.0f*Math.acos(quat[0])*180.0f/Math.PI),quat[1],quat[2],quat[3]);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        mTriangle.draw(mMVPMatrix);


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






