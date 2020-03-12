package com.j.scorecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button buttonOne;
    private Button buttonTwo;
    private Button clearButton;
    private TextView yankText;
    private TextView bostText;
    static int scoreOne = 0;
    static int scoreTwo =0;
    static int diff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = (Button) findViewById(R.id.scoreButton1);
        buttonTwo = (Button) findViewById(R.id.scoreButton2);
        yankText = (TextView)findViewById(R.id.scoreOne);
        bostText = (TextView)findViewById(R.id.scoreTwo);
        clearButton = (Button)findViewById(R.id.clearButton);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scoreOne<5) {
                    scoreOne++;
                    yankText.setText(scoreOne + "");
                }
                if(scoreOne == 5){
                    diff = Math.abs(scoreOne-scoreTwo);
                    Intent i = new Intent(MainActivity.this,WinnerActivity.class);
                        i.putExtra("team", "Yankees");
                        i.putExtra("diff",diff);
                        startActivity(i);

                }
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scoreTwo < 5){
                    scoreTwo++;
                    bostText.setText(scoreTwo + "");
                }
                if(scoreTwo == 5){
                    diff = Math.abs(scoreOne-scoreTwo);
                    Intent i = new Intent(MainActivity.this,WinnerActivity.class);
                    i.putExtra("team", "Red Sox");
                    i.putExtra("diff",diff);
                    startActivity(i);

                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yankText.setText("0");
                bostText.setText("0");
                scoreOne = 0;
                scoreTwo = 0;
            }
        });

        if(savedInstanceState!= null){
            scoreOne = savedInstanceState.getInt("yank");
            scoreTwo = savedInstanceState.getInt("bost");
            yankText.setText(scoreOne);
            bostText.setText(scoreTwo);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("yank", scoreOne);
        outState.putInt("bost", scoreTwo);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


}
