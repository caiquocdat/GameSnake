package hehe.caiquocdat.snakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import hehe.caiquocdat.snakegame.customview.GameBoard;
import hehe.caiquocdat.snakegame.data.DatabaseHelper;
import hehe.caiquocdat.snakegame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements GameBoard.GameEventListener {
    private ActivityMainBinding activityMainBinding;
    private int score = 0;
    private String check = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        activityMainBinding.gameBoard.startMoving();
        activityMainBinding.gameBoard.setGameEventListener(this);
        activityMainBinding.buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check.equals("up")) {
                    activityMainBinding.gameBoard.setDirection("down");
                    check = "down";
                }
            }
        });
        activityMainBinding.buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check.equals("down")) {
                    activityMainBinding.gameBoard.setDirection("up");
                    check = "up";
                }
            }
        });
        activityMainBinding.buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check.equals("right")) {
                    activityMainBinding.gameBoard.setDirection("left");
                    check = "left";
                }
            }
        });
        activityMainBinding.buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check.equals("left")) {
                    activityMainBinding.gameBoard.setDirection("right");
                    check = "right";
                }
            }
        });
    }

    @Override
    public void onEatFood() {
        score++;
        activityMainBinding.textScore.setText("Score: " + score);
    }

    @Override
    public void onGameOver() {
        Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.insertScore(score);
        activityMainBinding.gameBoard.initializeGame();
        score = 0;
        activityMainBinding.textScore.setText("Score: " + score);
        finish();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityMainBinding.gameBoard.stopMoving();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
        activityMainBinding.gameBoard.startMoving();
    }

    @Override
    public void onBackPressed() {

    }
}