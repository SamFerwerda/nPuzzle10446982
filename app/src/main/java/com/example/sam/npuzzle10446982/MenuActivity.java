/*
Sam Ferwerda
10446982
Vrijdag 12 December 2014
sam.ferwerda@hotmail.com
*/

package com.example.sam.npuzzle10446982;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends ActionBarActivity implements View.OnClickListener {

    int Resource;
    int Difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity4);
        Button dbutton= (Button) findViewById(R.id.dbutton);
        dbutton.setOnClickListener(this);
        Button pbutton= (Button) findViewById(R.id.pbutton);
        pbutton.setOnClickListener(this);
        Button resetbutton= (Button) findViewById(R.id.resetbutton);
        resetbutton.setOnClickListener(this);

        // We might need the resource and Difficulty later, because if the player chooses for a
        // new shuffle than we need this information. The same with choosing the difficulty.
        // Then we still want to know the resource.
        Resource = this.getIntent().getIntExtra("ImageButton", 0);
        Difficulty = this.getIntent().getIntExtra("Difficulty", 4);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity4, menu);
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
        switch (v.getId()) {
            case R.id.dbutton:
                Intent myintent3 = new Intent(this, DifficultyActivity.class);
                myintent3.putExtra("ImageButton", Resource);
                startActivity(myintent3);
                break;

            case R.id.pbutton:
                Intent myintent4 = new Intent(this, MainActivity.class);
                startActivity(myintent4);
                break;

            case R.id.resetbutton:
                // When the reset button is pressed we would like to keep the same difficulty, the same
                // image. But of course we want a new shuffle. That's why there is a putExtra with
                // Reset in it.
                Intent myintent5 = new Intent(this, GameActivity.class);
                myintent5.putExtra("ImageButton", Resource);
                myintent5.putExtra("Difficulty", Difficulty);
                myintent5.putExtra("Reset", true);
                startActivity(myintent5);
                break;
        }
    }
}
