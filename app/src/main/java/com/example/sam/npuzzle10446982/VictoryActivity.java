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
import android.widget.ImageView;


public class VictoryActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        // Here we identify the button and the imageview.
        ImageView PictureWon = (ImageView) findViewById(R.id.imageView);
        Button PictureButton = (Button) findViewById(R.id.button);
        PictureButton.setOnClickListener(this);

        // We ask for the picture ID so we can set the image in the existing imageview.
        // To show it to the player.
        int Resource = getIntent().getIntExtra("ImageButton", 0);
        PictureWon.setImageResource(Resource);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_victory, menu);
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
        // We want to delete the memory, otherwise the player would instantly win by reopening.
        SharedPreferences prefs = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor Editor1 = prefs.edit();
        Editor1.clear();
        Editor1.commit();

        Intent BackToPictures = new Intent(VictoryActivity.this, MainActivity.class);
        startActivity(BackToPictures);
    }
}
