package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

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

        findViewById(R.id.right_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFlashcards.size() == 0){
                    return;
                }

                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex = getRandomNumber(0, allFlashcards.size() - 1);
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getAnswer());

            }
        });

        findViewById(R.id.trash_can).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFlashcards.size() == 0){
                    return;
                }

                flashcardDatabase.deleteCard(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                allFlashcards = flashcardDatabase.getAllCards();

                if (allFlashcards.size() == 0){

                    ((TextView) findViewById(R.id.flashcard_question)).setText("Who is the 44th President of the United States?");
                    ((TextView) findViewById(R.id.flashcard_answer)).setText("Barack Obama");

                }

                else{

                    currentCardDisplayedIndex--;

                    if (currentCardDisplayedIndex < 0){
                        currentCardDisplayedIndex = allFlashcards.size() - 1;
                    }

                    Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                    ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getAnswer());

                }

            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {

            if (data != null){

                String question = data.getExtras().getString("question");
                String answer = data.getExtras().getString("answer");
                String answer1 = data.getExtras().getString("answer1");
                String answer2 = data.getExtras().getString("answer2");

                ((TextView) findViewById(R.id.flashcard_question)).setText(question);
                ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);
                ((TextView) findViewById(R.id.answer1)).setText(answer1);
                ((TextView) findViewById(R.id.answer2)).setText(answer2);
                ((TextView) findViewById(R.id.answer3)).setText(answer);

                flashcardDatabase.insertCard(new Flashcard(question, answer));
                allFlashcards = flashcardDatabase.getAllCards();

                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Flashcard successfully created",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }

        }

    }

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

}