package com.example.android.opengl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.android.opengl.test.OpenGLES20Activity;
import com.example.android.opengl.test.OpenGLES20Activity2;




public class Menu extends ListActivity
{
    private static final String ITEM_IMAGE = "item_image";
    private static final String ITEM_TITLE = "item_title";
    private static final String ITEM_SUBTITLE = "item_subtitle";

    public static int stagenumber=0;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.menu_title);
        setContentView(R.layout.table_of_contents);




        // Initialize data
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        final SparseArray<Class<? extends Activity>> activityMapping = new SparseArray<Class<? extends Activity>>();

        int i = 0;

        {
            final Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_IMAGE, R.drawable.space_icon);
            item.put(ITEM_TITLE, getText(R.string.easy_mode));
            item.put(ITEM_SUBTITLE, getText(R.string.start_game_subtitle));
            data.add(item);
           // OpenGLES20Activity.ifnormal=false;
           // OpenGLES20Activity.ifhard=true;
            Log.i(" click  easy mode", "here");
            activityMapping.put(i++, OpenGLES20Activity.class);
        }
        {
            final Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_IMAGE, R.drawable.space_icon);
            item.put(ITEM_TITLE, getText(R.string.hard_mode));
            item.put(ITEM_SUBTITLE, getText(R.string.start_game_subtitle));
            data.add(item);
           // OpenGLES20Activity.ifhard=false;
           // OpenGLES20Activity.ifnormal=true;
            Log.i(" click  hard mode", "here");
            activityMapping.put(i++, OpenGLES20Activity2.class);
        }
        {
            final Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_IMAGE, R.drawable.space_icon);
            item.put(ITEM_TITLE, getText(R.string.leaderboards_title));
            item.put(ITEM_SUBTITLE, getText(R.string.leaderboard_subtitile));
            data.add(item);
            activityMapping.put(i++, HighScores.class);
        }
        {
            final Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_IMAGE, R.drawable.space_icon);
            item.put(ITEM_TITLE, getText(R.string.user_title));
            item.put(ITEM_SUBTITLE, getText(R.string.user_subtitle));
            data.add(item);
            activityMapping.put(i++, user.class);
        }
        {
            final Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_IMAGE, R.drawable.space_icon);
            item.put(ITEM_TITLE, getText(R.string.select_stage));
            item.put(ITEM_SUBTITLE, getText(R.string.start_game_subtitle));
            data.add(item);
            // OpenGLES20Activity.ifnormal=false;
            // OpenGLES20Activity.ifhard=true;
            Log.i(" click  easy mode", "here");
            activityMapping.put(i++, stageselection.class);
        }
        {
            final Map<String, Object> item = new HashMap<String, Object>();
            item.put(ITEM_IMAGE, R.drawable.space_icon);
            item.put(ITEM_TITLE, getText(R.string.how_to_play));
            item.put(ITEM_SUBTITLE, getText(R.string.start_game_subtitle));
            data.add(item);
            // OpenGLES20Activity.ifnormal=false;
            // OpenGLES20Activity.ifhard=true;
            Log.i(" click  easy mode", "here");
            activityMapping.put(i++, howtoplay.class);
        }




        final SimpleAdapter dataAdapter = new SimpleAdapter(this, data, R.layout.toc_item, new String[] { ITEM_TITLE}, new int[] {R.id.Title});
        setListAdapter(dataAdapter);

        getListView().setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                final Class<? extends Activity> activityToLaunch = activityMapping.get(position);

                if (activityToLaunch != null)
                {
                    final Intent launchIntent = new Intent(Menu.this, activityToLaunch);
                    startActivity(launchIntent);
                }
            }
        });
    }

    @Override protected void onResume() {
       // Toast.makeText(Menu.this, "You Clicked at " +stagenumber, Toast.LENGTH_SHORT).show();
        View background = findViewById(R.id.menu_background);

        if(stagenumber==0) {
            background.setBackgroundResource(R.drawable.space);
        }
        else if(stagenumber==1){
            background.setBackgroundResource(R.drawable.space2);
        }

        super.onResume();
    }

}
