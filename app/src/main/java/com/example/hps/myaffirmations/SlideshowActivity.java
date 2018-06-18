package com.example.hps.myaffirmations;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;

public class SlideshowActivity extends Activity {

    private boolean running;
    public static final String AFFIRMATION_LIST = "affirmationList" ;
    public HashSet<String> myAffirmationsSet;
    ImageView slideshowView;
    Iterator<String> itr ;

   private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
       /* if (savedInstanceState != null) {
            i = savedInstanceState.getInt("i");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }*/
        slideshowView = findViewById(R.id.slideshowView);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActionBar().hide();

        slideshowView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        slideshowView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        slideshowView.setAdjustViewBounds(false);
        slideshowView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        myAffirmationsSet = (HashSet<String>) getIntent().getSerializableExtra(AFFIRMATION_LIST);
        itr = myAffirmationsSet.iterator();
        runSlideshow();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    private void runSlideshow() {

        final TextView textView = findViewById(R.id.textView);
        final Handler handler = new Handler();

        running = true;
        handler.post(new Runnable() {
            @Override
            public void run() {

                /*Context context = getApplicationContext();
                CharSequence text = "Please select at least one affirmation";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, myAffirmationsSet.toString(), duration);
                toast.show();*/


                if(itr.hasNext()){
                    int id = getResources().getIdentifier("com.example.hps.myaffirmations:drawable/image"+i, null, null);
                    slideshowView.setImageResource(id);
                    textView.setText(itr.next());
                    textView.setVisibility(View.VISIBLE);
                    i++;
                }

                handler.postDelayed(this, 5000);
            }
        });
    }
}
