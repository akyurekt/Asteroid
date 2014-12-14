package com.example.android.opengl.test;

/**
 * Created by Thomas on 10/12/2014.
 */
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.android.opengl.R;

import java.util.Random;

public  class MusicService extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    int[] sounds={R.raw.bayo_after_burner,R.raw.chaos_code,R.raw.tank,R.raw.bayo_dance};
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Random r = new Random();
        int background = r.nextInt(4) ;
        player = MediaPlayer.create(this, sounds[background]);

        player.setLooping(true); // Set looping
        player.setVolume(50,50);
        Log.i("music ", "on create ");


    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
   // @Override
   // public void onStart(Intent intent, int startId) {
   //     handleStart(intent, startId);
   // }
//
   // @Override
   // public int onStartCommand(Intent intent, int flags, int startId) {
   //     handleStart(intent, startId);
   //     return START_NOT_STICKY;
   // }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}