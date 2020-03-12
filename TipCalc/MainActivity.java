package com.j.mp1_josh;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class MainActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {

    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewBillAmount;
    private SeekBar seekBarDrag; // create seekBar
    private TextView seekBarText;
    private TextView showTip;
    private TextView showTotal;
    private TextView perPersonTotal;
    private RadioButton noRadio,tipRadio,totalRadio;
    RadioGroup radioGroup ;
    private Spinner spinner;
    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double percent;
    ArrayAdapter<CharSequence> adapter;
    int progressValue;
    double tip;
    int numPeople = 1;
    double totalAmount;



    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBillAmount = (TextView)findViewById(R.id.textView_BillAmount);
        showTip = (TextView)findViewById(R.id.showTip);
        showTotal = (TextView)findViewById(R.id.showTotal);

        editTextBillAmount = (EditText)findViewById(R.id.editText_BillAmount);
        editTextBillAmount.addTextChangedListener((TextWatcher) this);

        seekBarDrag = (SeekBar)findViewById(R.id.seekBar); // finds seekBar and casts it so it knows we're talking about the actual object and not the view
        seekBarText = (TextView)findViewById(R.id.seekBarText);

        perPersonTotal = (TextView)findViewById(R.id.perPersonTotal);

        noRadio = (RadioButton)findViewById(R.id.noRadio);
        tipRadio = (RadioButton)findViewById(R.id.tipRadio);
        totalRadio = (RadioButton)findViewById(R.id.totalRadio);


        spinner = (Spinner)findViewById(R.id.mySpinner);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.spinnerArr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.noRadio){
                    double perPersonTotalDouble = (double) totalAmount/numPeople;
                    perPersonTotal.setText(currencyFormat.format(perPersonTotalDouble) + "");
                } else if(checkedId == R.id.tipRadio){
                    tip = Math.ceil(tip);
                    showTip.setText(currencyFormat.format(tip) + "");
                    totalAmount= billAmount+tip;
                    showTotal.setText(currencyFormat.format(totalAmount)+ "");
                    perPersonTotal.setText(currencyFormat.format(totalAmount/numPeople));

                }else if(checkedId == R.id.totalRadio){
                    totalAmount = Math.ceil(totalAmount);
                    double perPersonTotalDouble =  (double)(totalAmount/numPeople);
                    showTotal.setText(currencyFormat.format(totalAmount));
                    perPersonTotal.setText(currencyFormat.format(perPersonTotalDouble) +"");
                }
            }
        });

        noRadio.setChecked(true);




        if(spinner!= null){
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
            spinner.bringToFront();
        }




        seekBarDrag.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                noRadio.setChecked(true);
                progressValue = progress;
                seekBarText.setText(String.valueOf(progressValue + "%"));
                percent =(double) progressValue/100;
                calculate();
                perPersonTotal.setText (currencyFormat.format(totalAmount/numPeople));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });





    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /*
    Note:   int i, int i1, and int i2
            represent start, before, count respectively
            The charSequence is converted to a String and parsed to a double for you
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        //charSequence is converted to a String and parsed to a double for you
        try{
            Log.d("MainActivity", charSequence.toString());
        billAmount = (double) Double.parseDouble(charSequence.toString())/100.00; Log.d("MainActivity", "Bill Amount = "+billAmount);
            calculate();
            noRadio.setChecked(true);
        textViewBillAmount.setText(currencyFormat.format(billAmount));
        showTotal.setText(currencyFormat.format(billAmount + tip));
        perPersonTotal.setText(currencyFormat.format((billAmount+tip)/numPeople));
            //calculate();
        } catch(Exception e){
            noRadio.setChecked(true);
            seekBarDrag.setProgress(0);
            textViewBillAmount.setText("$0.00");
            showTip.setText("$0.00");
            showTotal.setText("$0.00");
            Toast myToast = Toast.makeText(getApplicationContext(), "Please Enter Bill Amount", Toast.LENGTH_SHORT);
            myToast.show();
        }


    }





    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void calculate() {
       // calculate the tip and total
        tip = billAmount * percent;

        totalAmount = billAmount + tip;

       showTip.setText(currencyFormat.format(tip) + "");

        showTotal.setText(currencyFormat.format(totalAmount) + "");


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numPeople = Integer.valueOf(parent.getItemAtPosition(position).toString());
        perPersonTotal.setText(currencyFormat.format(totalAmount/numPeople) + "");


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showInfo(View view) {
        AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("The spinner splits the total amount amongst the chosen number")
                .setPositiveButton("OK", null)
                .create();

        builder.show();

    }


    public void shareStuff(View view) {
        //bill,tip,total and per person i
        String message = "The bill is: " + currencyFormat.format(billAmount) + ". The tip is: " + currencyFormat.format(tip) + ". The total is: " + currencyFormat.format(totalAmount) + ". The total per person is: " + currencyFormat.format(totalAmount/numPeople) + ".";
        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText(message)
                .startChooser();
    }
}