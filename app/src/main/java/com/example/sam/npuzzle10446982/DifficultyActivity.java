/*
Sam Ferwerda
10446982
Vrijdag 12 December 2014
sam.ferwerda@hotmail.com
*/

package com.example.sam.npuzzle10446982;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DifficultyActivity extends ActionBarActivity implements View.OnClickListener {

    int Resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity5);
        Button Ebutton= (Button) findViewById(R.id.Easybutton);
        Ebutton.setOnClickListener(this);
        Button Mbutton= (Button) findViewById(R.id.Mediumbutton);
        Mbutton.setOnClickListener(this);
        Button Hbutton= (Button) findViewById(R.id.Hardbutton);
        Hbutton.setOnClickListener(this);

        // Retrieving the ID of the picture chosen.
        Resource = this.getIntent().getIntExtra("ImageButton", -1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // When a difficulty is clicked we want to erase the memory of the last puzzle.
        // And make a new puzzle with the new difficulty.
        SharedPreferences prefs = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor Editor1 = prefs.edit();
        Editor1.clear();
        Editor1.commit();

        switch(v.getId()) {
            // The following cases do exactly the same with one tiny difference. The given difficulty.
            case R.id.Easybutton:
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("Difficulty", 3);
                intent.putExtra("ImageButton", Resource);
                startActivity(intent);
                break;

            case R.id.Mediumbutton:
                Intent myintent = new Intent(this, GameActivity.class);
                myintent.putExtra("Difficulty", 4);
                myintent.putExtra("ImageButton", Resource);
                startActivity(myintent);
                break;

            case R.id.Hardbutton:
                Intent mysecondintent= new Intent(this, GameActivity.class);
                mysecondintent.putExtra("Difficulty", 5);
                mysecondintent.putExtra("ImageButton", Resource);
                startActivity(mysecondintent);
                break;
        }
    }
}
