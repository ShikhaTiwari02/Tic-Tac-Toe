package com.example.tictactoe;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    private int rountCount;

    private int Player1points;
    private int Player2points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                //btn_00
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //R.id.btn_00
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }
        Button buttonReset = findViewById(R.id.btn_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });

    }

    @Override
    public void onClick(View v) {
        Log.d("", String.valueOf(v.getId()));
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        rountCount++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (rountCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        Player1points++;
        Toast.makeText(this, "PLAYER 1 WINS!!!", Toast.LENGTH_LONG).show();
        updatePointText();
        resetBoard();
    }

    private void player2Wins() {
        Player2points++;
        Toast.makeText(this, "PLAYER 2 WINS!!!", Toast.LENGTH_LONG).show();
        updatePointText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void updatePointText() {
        textViewPlayer1.setText("Player 1: " + Player1points);
        textViewPlayer2.setText("Player 2: " + Player2points);
    }

    private void resetBoard() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Button button = buttons[i][j];
                        button.setText("");
                    }
                }
                rountCount = 0;
                player1Turn = true;
            }
        });
        thread.start();
    }

    private void resetGame() {
        Player1points = 0;
        Player2points = 0;
        updatePointText();
        resetBoard();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("roundCount", rountCount);
        outState.putInt("Player1points", Player1points);
        outState.putInt("Player2points", Player2points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    //to restore game after orientaion
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rountCount = savedInstanceState.getInt("roundCount");
        Player1points = savedInstanceState.getInt("Player1points", Player1points);
        Player2points = savedInstanceState.getInt("Player2points", Player2points);


    }
}

/*
interface MyInterface{
    void hello();
}

class Demo implements MyInterface{
    @Override
    public void hello() {

    }
}


class Test{
    MyInterface myInterface = new Demo();

    public void  help(){
        myInterface = new MyInterface() {
            @Override
            public void hello() {

            }
        };
    }

}*/
