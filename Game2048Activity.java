package com.game.sail.game2048;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfan on 2016/10/24.
 */
public class Game2048Activity extends Activity {

    private static int NUMBERROWS = 4;
    private TextView[][] tvCard;
    private GridLayout glGameView;
    private int[][] cardNumber = {{4096,2048,1024,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    private List<Point> emptyPoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvCard = new TextView[NUMBERROWS][NUMBERROWS];
        initViews();
        startGame();
    }

    private void startGame() {
        addRandNum();
        refreshView();
    }

    private void addRandNum() {
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardNumber[x][y] <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        if (emptyPoints.size() > 0) {
            Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
            cardNumber[p.x][p.y] = Math.random() > 0.1 ? 2 : 4;
        }
    }

    private void refreshView() {
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4 ; j++) {
                tvCard[i][j].setText(cardNumber[i][j] == 0 ? "" :cardNumber[i][j]+"");
            }
        }
    }

    private void initViews() {
        glGameView = new GridLayout(this);
        setContentView(glGameView);
        glGameView.setColumnCount(4);
        glGameView.setRowCount(4);
        glGameView.setOrientation(GridLayout.VERTICAL);
        WindowManager wm = this.getWindowManager();
        int displayWidth = wm.getDefaultDisplay().getWidth();

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 4 ; j++) {
                LinearLayout ll = new LinearLayout(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                tvCard[i][j] = new TextView(this);
                tvCard[i][j].setWidth(displayWidth/4-20);
                tvCard[i][j].setHeight(displayWidth/4-20);
                tvCard[i][j].setBackgroundColor(0xff33b5e5);
                tvCard[i][j].setGravity(Gravity.CENTER);
                tvCard[i][j].setLayoutParams(lp);
                tvCard[i][j].setTextSize(32f);
                tvCard[i][j].setText(cardNumber[i][j] == 0 ? "" :cardNumber[i][j]+"");
                ll.addView(tvCard[i][j]);
                glGameView.addView(ll);
            }
        }

        glGameView.setOnTouchListener(new View.OnTouchListener() {
            private float startX,startY,offsetX,offsetY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = motionEvent.getX() - startX;
                        offsetY = motionEvent.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void swipeDown() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 4 - 1; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardNumber[x][y1] > 0) {
                        if (cardNumber[x][y] <= 0) {
                            cardNumber[x][y] = cardNumber[x][y1];
                            cardNumber[x][y1] = 0;
                            y++;
                            merge = true;
                        } else if (cardNumber[x][y] == cardNumber[x][y1]) {
                            cardNumber[x][y] = cardNumber[x][y] * 2;
                            cardNumber[x][y1] = 0;
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandNum();
            refreshView();
            checkComplete();
        }
    }

    private void swipeUp() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardNumber[x][y1] > 0) {
                        if (cardNumber[x][y] <= 0) {
                            cardNumber[x][y]=cardNumber[x][y1];
                            cardNumber[x][y1]=0;
                            y--;
                            merge = true;
                        } else if (cardNumber[x][y]==cardNumber[x][y1]) {
                            cardNumber[x][y]=cardNumber[x][y] * 2;
                            cardNumber[x][y1]=0;
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandNum();
            refreshView();
            checkComplete();
        }
    }

    private void swipeRight() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 4 - 1; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardNumber[x1][y] > 0) {
                        if (cardNumber[x][y] <= 0) {
                            cardNumber[x][y] = cardNumber[x1][y];
                            cardNumber[x1][y] = 0;
                            x++;
                            merge = true;
                        } else if (cardNumber[x][y]==cardNumber[x1][y]) {
                            cardNumber[x][y] *= 2;
                            cardNumber[x1][y]=0;
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandNum();
            refreshView();
            checkComplete();
        }
    }

    private void swipeLeft() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardNumber[x1][y] > 0) {
                        if (cardNumber[x][y] <= 0) {
                            cardNumber[x][y] = cardNumber[x1][y];
                            cardNumber[x1][y]= 0;
                            x--;
                            merge = true;
                        } else if (cardNumber[x][y]==cardNumber[x1][y]) {
                            cardNumber[x][y] *= 2;
                            cardNumber[x1][y] = 0;
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandNum();
            refreshView();
            checkComplete();
        }
    }

    private void checkComplete() {
        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardNumber[x][y] == 0
                        || (x > 0 && cardNumber[x][y] == cardNumber[x - 1][y])
                        || (x < 3 && cardNumber[x][y] == cardNumber[x + 1][y])
                        || (y > 0 && cardNumber[x][y] == cardNumber[x][y - 1])
                        || (y < 3 && cardNumber[x][y] == cardNumber[x][y + 1])) {

                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete) {
//
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_K:
                swipeDown();
                break;
            case KeyEvent.KEYCODE_I:
                swipeUp();
                break;
            case KeyEvent.KEYCODE_J:
                swipeLeft();
                break;
            case KeyEvent.KEYCODE_L:
                swipeRight();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}