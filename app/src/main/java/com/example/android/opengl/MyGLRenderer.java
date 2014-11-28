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
package com.example.android.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;
    private Triangle mTriangle2;
    private Square mSquare;

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

    private float mAngle;
    private boolean trianglepressed = false;
    private Point triangleposition;
    private Point previoustriangleposition;

    private final float leftBound = -0.5f;
    private final float rightBound = 0.5f;
    private final float farBound = -0.8f;
    private final float nearBound = 0.8f;
    public float screenheight;
    public float screenwidth;
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
        mTriangle2= new Triangle();
        mSquare   = new Square();







    }

    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min)); }





    @Override
    public void onDrawFrame(GL10 unused) {



        float[] scratch = new float[16];

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
        mSquare.draw(mMVPMatrix);
        mTextureUniformHandle = GLES20.glGetUniformLocation(mTriangle.returnprogram(), "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mTriangle.returnprogram(), "a_TexCoordinate");

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        long time = SystemClock.uptimeMillis() % 4000L;
        mAngle = 0.090f * ((int) time);
        float sqAngle= -mAngle;
        Matrix.setIdentityM(scratch, 0);
        Matrix.translateM(scratch, 0, x, y, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, scratch, 0, mViewMatrix, 0);
        if(iftouch) {
           // Matrix.scaleM(mMVPMatrix, 0,counter, 1, 1);
           // temporarymatrix=
            counter++;


            iftouch=false;
            testLogMessage();
        }
        if(ifpinch)
        {

            /*
            MediaPlayer mediaPlayer = MediaPlayer.create(OpenGLES20Activity.getAppContext(), R.raw.sound_file_1);
            mediaPlayer.start();
            */




            counter--;
            ifpinch=false;
            if(counter<1f)
            {
                counter=1f;
            }

        }
        Matrix.scaleM(mMVPMatrix, 0,counter*0.3f,counter*0.3f,counter*0.3f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        //rotate in 3d, change last 0
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
        // private Square   mSquare;
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
        Matrix.setIdentityM(mMVPMatrix,0);
      //  Matrix.translateM(mMVPMatrix, 0, 0f, 1f, 0.0f);
       // Matrix.translateM(mMVPMatrix,0,0,1f,mAngle);
       // mTriangle2.draw(mMVPMatrix);

    }
    public void testLogMessage(){
        Log.i("Test", "TAB: "  + "Test" +  "AAAAAAAAA");
    }


    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);
        screenheight=height;
        screenwidth =width;

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

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
    *
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
    public float getscreenwidth(){
        return screenwidth;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }

}