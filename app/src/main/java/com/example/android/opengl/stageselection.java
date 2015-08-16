package com.example.android.opengl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;



public class stageselection extends Activity {

    GridView grid;
    String[] web = {
            "Stage 1",
            "Stage 2"

    } ;
    int[] imageId = {
            R.drawable.space_icon,
            R.drawable.space2_icon

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.stageselect);
        View background = findViewById(R.id.stage_background);

        if(Menu.stagenumber==0) {
            background.setBackgroundResource(R.drawable.space);
        }
        else if(Menu.stagenumber==1){
            background.setBackgroundResource(R.drawable.space2);
        }


        CustomGrid adapter = new CustomGrid(stageselection.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
              //  Toast.makeText(stageselection.this, "You selected " +web[position], Toast.LENGTH_SHORT).show();
                View background = findViewById(R.id.stage_background);
                Menu.stagenumber=position;
                if(position==0) {
                  background.setBackgroundResource(R.drawable.space);
                }
                else if(position==1){
                   background.setBackgroundResource(R.drawable.space2);
                }

            }
        });
    }
}





