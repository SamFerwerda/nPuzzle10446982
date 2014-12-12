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
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn= (ImageButton) findViewById(R.id.imageButton1);
        btn.setOnClickListener(this);
        ImageButton btn2= (ImageButton) findViewById(R.id.imageButton2);
        btn2.setOnClickListener(this);
        ImageButton btn3= (ImageButton) findViewById(R.id.imageButton3);
        btn3.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            // When the player chooses a picture by clicking on it, these cases will make sure
            // that they get the picture they wanted. With standard-difficulty 4. The only
            // difference in the cases are the ID's of the pictures.
            case R.id.imageButton1:
                Intent intent1 = new Intent(this, GameActivity.class);
                intent1.putExtra("ImageButton", R.drawable.image1);
                intent1.putExtra("Difficulty", 4);
                startActivity(intent1);
                break;
            case R.id.imageButton2:
                Intent myintent= new Intent(this, GameActivity.class);
                myintent.putExtra("ImageButton", R.drawable.image2);
                myintent.putExtra("Difficulty", 4);
                startActivity(myintent);
                break;
            case R.id.imageButton3:
                Intent mysecondintent= new Intent(this, GameActivity.class);
                mysecondintent.putExtra("ImageButton", R.drawable.image3);
                mysecondintent.putExtra("Difficulty", 4);
                startActivity(mysecondintent);
                break;


        }
    }
}
