package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        if (getIntent() != null){

            ((EditText) findViewById(R.id.editQuestion)).setText(getIntent().getStringExtra("question"));
            ((EditText) findViewById(R.id.editAnswer)).setText(getIntent().getStringExtra("answer"));

        }

        findViewById(R.id.x_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((EditText) findViewById(R.id.editQuestion)).getText().toString().equals("") || ((EditText) findViewById(R.id.editAnswer)).getText().toString().equals("") ){

                    Toast toast = Toast.makeText(getApplicationContext(), "Must enter a question and answer", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();

                }

                else{

                    Intent data = new Intent();
                    data.putExtra("question", ((EditText) findViewById(R.id.editQuestion)).getText().toString());
                    data.putExtra("answer", ((EditText) findViewById(R.id.editAnswer)).getText().toString());
                    setResult(RESULT_OK, data);
                    finish();

                }

            }
        });

    }
}