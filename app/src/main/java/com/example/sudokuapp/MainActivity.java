package com.example.sudokuapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get value at index 2 (indexing starts at 0)
        TableRow rowOne = (TableRow) findViewById(R.id.row1);

        //Convert that value to a text view object
        TextView items = (TextView) rowOne.getChildAt(2);

        //Button used to display the value
        Button btn = findViewById(R.id.bttnClickMe);

        //Behaviour of button on click
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.i("Test"+items.getText().toString(), "banana");
                onButtonClick(items.getText().toString());

            }
        });
    }

    protected void onButtonClick(String myNum)
    {
        Log.i("Test" + myNum, "onbclick func");
        Toast.makeText(this, myNum,Toast.LENGTH_SHORT);
    }

}