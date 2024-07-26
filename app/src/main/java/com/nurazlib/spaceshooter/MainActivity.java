package com.nurazlib.spaceshooter;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView player;
    private ImageView enemy;
    private View playerBullet;
    private View enemyBullet;
    private int score = 0;
    private int level = 1;
    private TextView levelText;
    private TextView scoreText;
    private Handler handler = new Handler();
    private Random random = new Random();
    private boolean isPlayerShooting = false;
    private boolean isEnemyShooting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = findViewById(R.id.player);
        enemy = findViewById(R.id.enemy);
        playerBullet = findViewById(R.id.playerBullet);
        enemyBullet = findViewById(R.id.enemyBullet);
        levelText = findViewById(R.id.levelText);
        scoreText = findViewById(R.id.scoreText);

        player.setOnClickListener(view -> {
            score++;
            updateLevel();
            scoreText.setText("Score: " + score);
            movePlayer();
        });

        startEnemyMovement();
        startPlayerShooting();
        startEnemyShooting();
    }

    private void movePlayer() {
        player.setX(random.nextFloat() * (getWindow().getDecorView().getWidth() - player.getWidth()));
        player.setY(random.nextFloat() * (getWindow().getDecorView().getHeight() - player.getHeight()));
    }

    private void updateLevel() {
        if (score % 10 == 0 && level < 5) {
            level++;
        }
        if (level == 5 && score % 10 == 0) {
            levelText.setText("Level: " + level + " - Boss Level!");
        } else {
            levelText.setText("Level: " + level);
        }
    }

    private void startEnemyMovement() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveEnemy();
                handler.postDelayed(this, 1000); // Move enemy every second
            }
        }, 1000);
    }

    private void moveEnemy() {
        enemy.setX(random.nextFloat() * (getWindow().getDecorView().getWidth() - enemy.getWidth()));
        enemy.setY(random.nextFloat() * (getWindow().getDecorView().getHeight() - enemy.getHeight()));
    }

    private void startPlayerShooting() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isPlayerShooting) {
                    shootPlayerBullet();
                }
                handler.postDelayed(this, 500); // Shoot player bullet every half second
            }
        }, 500);
    }

    private void shootPlayerBullet() {
        isPlayerShooting = true;
        playerBullet.setX(player.getX() + player.getWidth() / 2);
        playerBullet.setY(player.getY());
        playerBullet.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerBullet.setVisibility(View.INVISIBLE);
                isPlayerShooting = false;
            }
        }, 1000); // Bullet travels for 1 second
    }

    private void startEnemyShooting() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isEnemyShooting) {
                    shootEnemyBullet();
                }
                handler.postDelayed(this, 2000); // Shoot enemy bullet every 2 seconds
            }
        }, 2000);
    }

    private void shootEnemyBullet() {
        isEnemyShooting = true;
        enemyBullet.setX(enemy.getX() + enemy.getWidth() / 2);
        enemyBullet.setY(enemy.getY());
        enemyBullet.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enemyBullet.setVisibility(View.INVISIBLE);
                isEnemyShooting = false;
            }
        }, 1000); // Bullet travels for 1 second
    }
                   }
