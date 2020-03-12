package com.j.scorecounter;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class WinnerActivity extends AppCompatActivity {
    String winner;
    int diff;
    String shareText;


    TextView winText;
    TextView diffText;
    Button callBtn, msgBtn, mapBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytwo_main);

        winText = (TextView)findViewById(R.id.winnerText);
        diffText = (TextView)findViewById(R.id.differenceText);
        btnListeners();
        Bundle extra = getIntent().getExtras();

        winner = extra.getString("team");
        diff = extra.getInt("diff");

        winText.setText(winner);
        diffText.setText("Won by " + diff);

        if(savedInstanceState != null){
            savedInstanceState.getInt("diff");
            savedInstanceState.getString("winner");
            winText.setText(winner);
            diffText.setText(diff);
        }




    }

    public void btnListeners(){
        callBtn = findViewById(R.id.callButton);
        mapBtn = findViewById(R.id.mapBtn);
        msgBtn = findViewById(R.id.messageBtn);
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("diff", diff);
        outState.putString("winner",winner);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void mapArena(View view) {

        String location = "baseball near me";
        Uri geoloc = Uri.parse("geo:0,0?q=" + location);
        Intent intent = new Intent(Intent.ACTION_VIEW, geoloc);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    public void msgFriend(View view) {
        shareText = winner + " won by " + diff + "!!!";
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setText(shareText)
                .startChooser();
    }

    public void callFriend(View view) {
        String uri = "tel:9172035302";
        Uri data = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_DIAL, data);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

}
