package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.wrong_red));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.correct_green));
            }
        });

        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.wrong_red));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.correct_green));
            }
        });

        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.correct_green));
            }
        });

        findViewById(R.id.show_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.show_icon).setVisibility(View.INVISIBLE);
                findViewById(R.id.hide_icon).setVisibility(View.VISIBLE);
                findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                findViewById(R.id.answer3).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.hide_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.hide_icon).setVisibility(View.INVISIBLE);
                findViewById(R.id.show_icon).setVisibility(View.VISIBLE);
                findViewById(R.id.answer1).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer2).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer3).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer1).setBackgroundColor(getResources().getColor(R.color.default_background));
                findViewById(R.id.answer2).setBackgroundColor(getResources().getColor(R.color.default_background));
                findViewById(R.id.answer3).setBackgroundColor(getResources().getColor(R.color.default_background));
            }
        });

        findViewById(R.id.plus_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", ((TextView) findViewById(R.id.flashcard_question)).getText());
                intent.putExtra("answer", ((TextView) findViewById(R.id.flashcard_answer)).getText());
                intent.putExtra("answer1", ((TextView) findViewById(R.id.answer1)).getText());
                intent.putExtra("answer2", ((TextView) findViewById(R.id.answer2)).getText());
                intent.putExtra("answer3", ((TextView) findViewById(R.id.answer3)).getText());

                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {

            if (data != null){
                ((TextView) findViewById(R.id.flashcard_question)).setText(data.getExtras().getString("question"));
                ((TextView) findViewById(R.id.flashcard_answer)).setText(data.getExtras().getString("answer"));
                ((TextView) findViewById(R.id.answer1)).setText(data.getExtras().getString("answer1"));
                ((TextView) findViewById(R.id.answer2)).setText(data.getExtras().getString("answer2"));
                ((TextView) findViewById(R.id.answer3)).setText(data.getExtras().getString("answer"));

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Flashcard successfully created",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }

        }
    }

}