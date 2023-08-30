package hehe.caiquocdat.snakegame.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.Random;

public class GameBoard extends View {

    private final Paint paint = new Paint();
    private final int boardSize = 10;
    private final LinkedList<int[]> snake = new LinkedList<>();
    private int[] food = new int[2];
    private final Random random = new Random();
    private String currentDirection = "right";

    private final Handler handler = new Handler();
    private boolean isMoving = false;
    public interface GameEventListener {
        void onEatFood();
        void onGameOver();
    }
    private GameEventListener gameEventListener;

    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.GREEN);
        initializeGame();
    }

    public void setDirection(String direction) {
        this.currentDirection = direction;
    }

    public void startMoving() {
        if (!isMoving) {
            isMoving = true;
            handler.post(moveRunnable);
        }
    }

    public void stopMoving() {
        isMoving = false;
        handler.removeCallbacks(moveRunnable);
    }

    private final Runnable moveRunnable = new Runnable() {
        @Override
        public void run() {
            if (move()) {
                handler.postDelayed(this, 500);
            } else {
                stopMoving();
            }
        }
    };

    public void initializeGame() {
        stopMoving();
        snake.clear();
        snake.add(new int[]{5, 5});
        spawnFood();
        invalidate();
    }

    private void spawnFood() {
        int x, y;
        do {
            x = random.nextInt(boardSize);
            y = random.nextInt(boardSize);
        } while (isSnakeAt(x, y));
        food[0] = x;
        food[1] = y;
    }

    private boolean isSnakeAt(int x, int y) {
        for (int[] segment : snake) {
            if (segment[0] == x && segment[1] == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cellSize = (float) getWidth() / boardSize;

        paint.setColor(Color.RED);
        canvas.drawRect(food[0] * cellSize, food[1] * cellSize, (food[0] + 1) * cellSize, (food[1] + 1) * cellSize, paint);

        paint.setColor(Color.GREEN);
        for (int[] segment : snake) {
            canvas.drawRect(segment[0] * cellSize, segment[1] * cellSize, (segment[0] + 1) * cellSize, (segment[1] + 1) * cellSize, paint);
        }
    }

    public boolean move() {
        int[] newHead = new int[]{snake.getFirst()[0], snake.getFirst()[1]};

        switch (currentDirection) {
            case "up":
                newHead[1]--;
                break;
            case "down":
                newHead[1]++;
                break;
            case "left":
                newHead[0]--;
                break;
            case "right":
                newHead[0]++;
                break;
        }

        if (newHead[0] < 0 || newHead[0] >= boardSize || newHead[1] < 0 || newHead[1] >= boardSize || isSnakeAt(newHead[0], newHead[1])) {
            if (gameEventListener != null) {
                gameEventListener.onGameOver();
            }
            return false;
        }

        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            snake.addFirst(newHead);
            spawnFood();
            if (gameEventListener != null) {
                gameEventListener.onEatFood();
            }
        } else {
            snake.addFirst(newHead);
            snake.removeLast();
        }

        invalidate();
        return true;
    }
}
