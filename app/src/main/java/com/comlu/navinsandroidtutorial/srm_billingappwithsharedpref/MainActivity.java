package com.comlu.navinsandroidtutorial.srm_billingappwithsharedpref;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    EditText v1;
    EditText v2;
    TextView t;
    int discount=20;
    SharedPreferences prefs;
    static final String KEY="discountValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1=(EditText)findViewById(R.id.editText);
        v2=(EditText)findViewById(R.id.editText1);
        t=(TextView)findViewById(R.id.discounttext);
        SeekBar sbar=(SeekBar)findViewById(R.id.seekBar);

        prefs = getSharedPreferences("Discount",MODE_PRIVATE);

        //---set the TextView font size to the previously saved values---
         discount= prefs.getInt(KEY, 10);
         t.setText("Discount:"+Integer.toString(discount));

        //---init the SeekBar and EditText---
        sbar.setProgress((int) discount);
        sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getBaseContext(), "Progress bar value "+Integer.toString(discount),
                        Toast.LENGTH_SHORT).show();
                t.setText("Discount:"+Integer.toString(discount));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                discount=progress;


            }
        });

        Button submit=(Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int val1 = Integer.parseInt(v1.getText().toString());
                    int val2 = Integer.parseInt(v2.getText().toString());
                    double result = val1 + val2;
                    result = result - result * ((double) discount / 100);

                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("result", result);
                    startActivity(i);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "One of the field entry is not entered or Wrongly entered",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

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
    protected void onDestroy() {
        //---get the SharedPreferences object---
        //prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        prefs = getSharedPreferences("Discount",MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        //---save the values in the EditText view to preferences---
        editor.putInt(KEY, discount);


        //---saves the values---
        editor.commit();

        //---display file saved message---
        Toast.makeText(getBaseContext(),
                "discount value saved",
                Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
