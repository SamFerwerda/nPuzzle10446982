/*
Sam Ferwerda
10446982
Vrijdag 12 December 2014
sam.ferwerda@hotmail.com
*/

package com.example.sam.npuzzle10446982;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class GameActivity extends ActionBarActivity implements View.OnClickListener {

    // The most variables used in this game are created in the lines below.
    int Resource;
    int Difficulty;
    int Width;
    int Height;
    int Wsteps;
    int Hsteps;
    float Scale;
    int NewWidth;
    int NewHeight;
    ArrayList<Integer> Ids = new ArrayList<Integer>(Difficulty*Difficulty);
    ArrayList<Bitmap> Crops = new ArrayList<Bitmap>();
    GridView gridview;
    Boolean Gameplay;
    Boolean Reset;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        gridview = (GridView) findViewById(R.id.gridview);
        Button Menu= (Button) findViewById(R.id.Mbutton);
        Menu.setOnClickListener(this);

        Resource = this.getIntent().getIntExtra("ImageButton", 0);
        Reset = this.getIntent().getBooleanExtra("Reset", false);

        final SharedPreferences prefs = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor Editor1 = prefs.edit();
        Gameplay = prefs.getBoolean("Gameplay", false);

        if (Gameplay) {
            Difficulty = prefs.getInt("Difficulty", 4);
        }
        else {
            Difficulty = this.getIntent().getIntExtra("Difficulty", 0);
        }

        // We retrieve the screen height and width using the displaymetrics. So we can
        // scale our pictures depending on the width and height of the phone.
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int ScreenWidth = dm.widthPixels;
        int ScreenHeight = dm.heightPixels;

        // In the following lines the bitmap (image) will be made and the
        // height and width of this picture.
        // The Hsteps and Wsteps are the Height and Width steps per image piece.
        final Bitmap bit = BitmapFactory.decodeResource(getResources(), Resource);
        Height = bit.getHeight();
        Width = bit.getWidth();
        Scale = Width/(float)Height;
        NewWidth = ScreenWidth - 20;
        NewHeight = Math.round(ScreenHeight/Scale);
        Hsteps = NewHeight/Difficulty;
        Wsteps = NewWidth/Difficulty;
        final Bitmap NewBit = Bitmap.createScaledBitmap(bit, NewWidth, NewHeight, true);

        // If the reset-button is pressed in the menu, we want to reset the memory.
        if (Reset){
            Editor1.clear();
            Editor1.commit();
        }

        gridview.setNumColumns(Difficulty); //Setting the numbers of colums right.
        gridview.setAdapter(new ImageAdapter(this, Crops));

        // The following loops will divide the image in Difficulty^2 pieces and will put them
        // in a list named Crops. The if-statement makes sure the last piece is black.
        for (int i = 0; i < Difficulty; i++){
            for (int j = 0; j < Difficulty; j++) {
                Bitmap ImagePiece = Bitmap.createBitmap(NewBit, j * Wsteps, i * Hsteps, Wsteps, Hsteps);
                if (i == Difficulty-1 && j == Difficulty-1) {
                    ImagePiece.eraseColor(android.R.color.transparent); // Making the last tile black
                    Crops.add(ImagePiece);
                }
                else {
                    Crops.add(ImagePiece);
                }
            }
        }
        // The following list will give IDs to the imagepieces, but the for-loop already shuffles them.
        // Example: The blacktile is imagepiece 15, at first the index of 15 will be 15 (the position it is on).
        // After playing the index of 15 (blacktile) will stay the same as the position it is on.
        for (int count = 0; count < Difficulty*Difficulty-1; count ++){
            Ids.add(Difficulty*Difficulty-2-count);
        }
        // Add the last (black) tile.
        Ids.add(Difficulty*Difficulty-1);


        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                // The following if-statement will make sure the picture gets shuffled. But only
                // when open a new picture.
                if (!Gameplay || Resource != prefs.getInt("FotoId", 0)) {
                    // Shuffling for difficulty 4 is different from 3 and 5 so we have to apply
                    // another if-statement.
                    if (Difficulty == 4) {
                        for (int counter = 0; counter < 7; counter++) {
                            Bitmap SwapImage1 = Crops.get(counter);
                            Bitmap SwapImage2 = Crops.get(Difficulty * Difficulty - 2 - counter);
                            Crops.set(counter, SwapImage2);
                            Crops.set(Difficulty * Difficulty - 2 - counter, SwapImage1);
                        }
                        gridview.invalidateViews();
                    } else {
                        int counterlimit = (Difficulty * Difficulty - 2) / 2 + 1;
                        for (int counter = 0; counter < counterlimit; counter++) {
                            Bitmap SwapImage1 = Crops.get(counter);
                            Bitmap SwapImage2 = Crops.get(Difficulty * Difficulty - 2 - counter);
                            Crops.set(counter, SwapImage2);
                            Crops.set(Difficulty * Difficulty - 2 - counter, SwapImage1);
                        }
                        gridview.invalidateViews();
                    }
                }


                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Bitmap clickedimage = Crops.get(position); // The image that the player clicked on.
                        int NewPosition;        // So we do not override in our arraylist.
                        int BlackTile = Ids.indexOf(Difficulty*Difficulty-1); // The place of the black tile.

                        // If the blacktile is right of the clicked tile.
                        if (BlackTile == position + 1 && position%Difficulty != Difficulty-1){
                            Crops.set(position, Crops.get(position+1));
                            Crops.set(position+1, clickedimage);
                            NewPosition = Ids.get(position);
                            Ids.set(position, Ids.get(position+1));
                            Ids.set(position+1, NewPosition);
                        }
                        // If the blacktile is left of the clicked tile.
                        else if (BlackTile == position - 1 && position%Difficulty != 0){
                            Crops.set(position, Crops.get(position-1));
                            Crops.set(position-1, clickedimage);
                            NewPosition = Ids.get(position);
                            Ids.set(position, Ids.get(position-1));
                            Ids.set(position-1, NewPosition);
                        }
                        // If the blacktile is beneath the clicked tile.
                        else if (BlackTile == position - Difficulty && position-Difficulty >= 0){
                            Crops.set(position, Crops.get(position-Difficulty));
                            Crops.set(position-Difficulty, clickedimage);
                            NewPosition = Ids.get(position);
                            Ids.set(position, Ids.get(position-Difficulty));
                            Ids.set(position-Difficulty, NewPosition);
                        }
                        // If the tile is above the clicked tile.
                        else if (BlackTile == position + Difficulty && position+Difficulty < Difficulty*Difficulty){
                            Crops.set(position, Crops.get(position+Difficulty));
                            Crops.set(position+Difficulty, clickedimage);
                            NewPosition = Ids.get(position);
                            Ids.set(position, Ids.get(position+Difficulty));
                            Ids.set(position+Difficulty, NewPosition);

                        }
                        // When the player clicked a tile that is not moveable, the next text should appear.
                        else {
                            Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        }

                        gridview.invalidateViews();

                        // The next piece of code checks either or not the player finished the puzzle yet.
                        // I did not want it to run every time the player clicks, so i added 1 extra
                        // condition. The blacktile has to be in the right corner.
                        Boolean finish = false;
                        if (Ids.get(Difficulty*Difficulty-1) == Difficulty*Difficulty-1){
                            // This for-loop checks if the tiles are on the right spot.
                            for (int count=0; count < Difficulty*Difficulty-1; count++){
                                // If one tile isn't in the right spot, we can cancel the loop.
                                if (Ids.get(count) != count) {
                                    finish = false;
                                    break;
                                }
                                else {
                                    finish = true;
                                }
                            }
                        }
                        // This if-statement checks if you are finished yet.
                        // If the player is finished it will redirect him to a new activity.
                        if (finish) {
                            Intent Finish = new Intent(GameActivity.this, VictoryActivity.class);
                            Finish.putExtra("ImageButton", Resource);
                            startActivity(Finish);
                        }
                    }
                });
            }
        }.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity2, menu);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("Save", MODE_PRIVATE);
        String savedString = prefs.getString("string", "");

        // We only want to change the imagepieces if we have something stored. Also it has
        // to be the same picture. Therefore the following if-statement.
        // To retrieve a list of integers i was forced to use a string of the integers. To get
        // the integers in a list again i used StringTokenizer.
        if (!savedString.equals("") && Resource == prefs.getInt("FotoId", 0)) {
            StringTokenizer st = new StringTokenizer(savedString, ",");
            ArrayList<Integer> Ids2 = new ArrayList<Integer>(Difficulty * Difficulty);
            for (int i = 0; i < Difficulty * Difficulty; i++) {
                Ids2.add(Integer.parseInt(st.nextToken()));
            }
            // The following list let us know where the pieces were when the game paused.
            Ids = Ids2;
            // NewCrops is the bitmaplist with the images on the right spot. But the second
            // loop is to make sure the Crops pieces are set like they are in the NewCrops.
            ArrayList<Bitmap> NewCrops = new ArrayList<Bitmap>(Difficulty * Difficulty);
            for (int Count = 0; Count < Difficulty * Difficulty; Count++) {
                NewCrops.add(Crops.get(Ids.get(Count)));
            }
            for (int Count = 0; Count < Difficulty * Difficulty; Count++) {
                Crops.set(Count, NewCrops.get(Count));
            }
            gridview.invalidateViews();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor Editor1 = prefs.edit();
        // We want to save the Ids list because it has all the valuable information. But
        // we can not preference a list, so we have to make a string out of it.
        StringBuilder str = new StringBuilder();
        for (Integer Id : Ids) {
            str.append(Id).append(",");
        }
        Editor1.putBoolean("Gameplay", true);
        Editor1.putString("string", str.toString());
        Editor1.putInt("FotoId", Resource);
        Editor1.putInt("Difficulty", Difficulty);
        Editor1.commit();
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
            case R.id.Mbutton:
                Intent intent= new Intent(this, MenuActivity.class);
                intent.putExtra("ImageButton", Resource);
                intent.putExtra("Difficulty", Difficulty);
                startActivity(intent);
                break;
        }
    }
}
