package com.example.android.opengl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.example.android.opengl.test.OpenGLES20Activity;


public class Menu extends ListActivity
{
    private static final String ITEM_IMAGE = "item_image";
    private static final String ITEM_TITLE = "item_title";
    private static final String ITEM_SUBTITLE = "item_subtitle";


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
            item.put(ITEM_IMAGE, R.drawable.ic_lesson_one);
            item.put(ITEM_TITLE, getText(R.string.model_viewer_title));
            item.put(ITEM_SUBTITLE, getText(R.string.model_viewer_subtitle));
            data.add(item);
            activityMapping.put(i++, OpenGLES20Activity.class);
        }


        final SimpleAdapter dataAdapter = new SimpleAdapter(this, data, R.layout.toc_item, new String[] { ITEM_TITLE, ITEM_SUBTITLE}, new int[] {R.id.Title});
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
}
