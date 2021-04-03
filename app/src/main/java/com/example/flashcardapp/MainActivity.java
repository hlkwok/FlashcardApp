package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };

        startTimer();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

            findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View questionSideView = findViewById(R.id.flashcard_question);

                findViewById(R.id.flashcard_question).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).setCameraDistance(25000);

                questionSideView.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        questionSideView.setVisibility(View.INVISIBLE);
                                        findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        findViewById(R.id.flashcard_answer).setRotationY(-90);
                                        findViewById(R.id.flashcard_answer).animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View answerSideView = findViewById(R.id.flashcard_answer);

                findViewById(R.id.flashcard_question).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).setCameraDistance(25000);

                answerSideView.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        answerSideView.setVisibility(View.INVISIBLE);
                                        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        findViewById(R.id.flashcard_question).setRotationY(-90);
                                        findViewById(R.id.flashcard_question).animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
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
                new ParticleSystem(MainActivity.this, 150, R.drawable.confetti, 2000)
                        .setSpeedRange(0.1f, 0.8f)
                        .oneShot(findViewById(R.id.answer3), 150);
            }
        });

        findViewById(R.id.show_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.show_icon).setVisibility(View.INVISIBLE);
                findViewById(R.id.hide_icon).setVisibility(View.VISIBLE);

                final Animation fastRightInAnim1 = AnimationUtils.loadAnimation(v.getContext(), R.anim.fast_right_in);
                final Animation fastRightInAnim2 = AnimationUtils.loadAnimation(v.getContext(), R.anim.fast_right_in);
                final Animation fastRightInAnim3 = AnimationUtils.loadAnimation(v.getContext(), R.anim.fast_right_in);

                findViewById(R.id.answer1).startAnimation(fastRightInAnim1);
                fastRightInAnim1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        findViewById(R.id.answer1).setVisibility(View.VISIBLE);
                        findViewById(R.id.answer2).startAnimation(fastRightInAnim2);
                        fastRightInAnim2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                findViewById(R.id.answer2).setVisibility(View.VISIBLE);
                                findViewById(R.id.answer3).startAnimation(fastRightInAnim3);
                                findViewById(R.id.answer3).setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

            }
        });

        findViewById(R.id.hide_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.hide_icon).setVisibility(View.INVISIBLE);
                findViewById(R.id.show_icon).setVisibility(View.VISIBLE);

                final Animation bottomOutAnim1 = AnimationUtils.loadAnimation(v.getContext(), R.anim.bottom_out);
                final Animation bottomOutAnim2 = AnimationUtils.loadAnimation(v.getContext(), R.anim.bottom_out);
                final Animation bottomOutAnim3 = AnimationUtils.loadAnimation(v.getContext(), R.anim.bottom_out);

                findViewById(R.id.answer1).startAnimation(bottomOutAnim1);
                bottomOutAnim1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        findViewById(R.id.answer2).startAnimation(bottomOutAnim2);
                        findViewById(R.id.answer3).startAnimation(bottomOutAnim3);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.right_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFlashcards.size() == 0){
                    return;
                }

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        allFlashcards = flashcardDatabase.getAllCards();
                        currentCardDisplayedIndex = getRandomNumber(0, allFlashcards.size() - 1);
                        Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                        ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getQuestion());
                        ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getAnswer());
                        findViewById(R.id.flashcard_question).startAnimation(rightInAnim);
                        startTimer();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

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

                    final Animation topOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.top_out);
                    final Animation leftInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                    findViewById(R.id.flashcard_question).startAnimation(topOutAnim);
                    topOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ((TextView) findViewById(R.id.flashcard_question)).setText("Who is the 44th President of the United States?");
                            ((TextView) findViewById(R.id.flashcard_answer)).setText("Barack Obama");
                            ((TextView) findViewById(R.id.answer1)).setText("Bill Clinton");
                            ((TextView) findViewById(R.id.answer2)).setText("George H. W. Bush");
                            ((TextView) findViewById(R.id.answer3)).setText("Barack Obama");
                            findViewById(R.id.flashcard_question).startAnimation(leftInAnim);
                            startTimer();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }

                else{

                    currentCardDisplayedIndex--;

                    if (currentCardDisplayedIndex < 0){
                        currentCardDisplayedIndex = allFlashcards.size() - 1;
                    }

                    final Animation topOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.top_out);
                    final Animation leftInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                    findViewById(R.id.flashcard_question).startAnimation(topOutAnim);
                    topOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);
                            ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getQuestion());
                            ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getAnswer());
                            findViewById(R.id.flashcard_question).startAnimation(leftInAnim);
                            startTimer();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

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

    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

}