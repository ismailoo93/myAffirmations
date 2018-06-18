package com.example.hps.myaffirmations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends Activity {

    ListView myListView;
    String[] listContent = {
            "I believe in myself",
            "I am confident",
            "Nothing is too good for me"
    };
    HashSet<String> affirmationsSet = new HashSet<>();
    SparseBooleanArray checked;

    Parcelable state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            Context context = getApplicationContext();
            CharSequence text = "Calling the saved state";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            state = savedInstanceState.getParcelable("state");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                listContent);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myListView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        myListView =  findViewById(R.id.list);


        if(state != null)
        myListView.onRestoreInstanceState(state);


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked

                checked = myListView.getCheckedItemPositions();
                for (int i = 0; i < myListView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        affirmationsSet.add(listContent[i]);
                    }

                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        CharSequence text = "Saving the state";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        savedInstanceState.putParcelable("state",myListView.onSaveInstanceState());

    }

    @Override
    protected void onStop() {
        super.onStop();
//        affirmationsSet.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_play:
                //Code to run when the play action item is clicked
                if(affirmationsSet.size() != 0){
                    Intent intent = new Intent(MainActivity.this, SlideshowActivity.class);
                    intent.putExtra(SlideshowActivity.AFFIRMATION_LIST, affirmationsSet);
                    startActivity(intent);
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "Please select at least one affirmation";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
