package com.example.tictactoe;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private boolean playerX = true;   // X starts first
    private int moveCount = 0;
    private TextView textStatus;
    private int[][] winPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},   // rows
            {0,3,6}, {1,4,7}, {2,5,8},   // columns
            {0,4,8}, {2,4,6}             // diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStatus = findViewById(R.id.textStatus);

        // Initialize buttons
        for (int i = 0; i < 9; i++) {
            String btnID = "btn" + i;
            int resID = getResources().getIdentifier(btnID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this::onButtonClick);
        }

        // Reset button
        Button reset = findViewById(R.id.btnReset);
        reset.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(View view) {
        Button b = (Button) view;

        // If already clicked
        if (!b.getText().toString().equals("")) return;

        // Set text
        b.setText(playerX ? "X" : "O");
        moveCount++;

        // Check win
        if (checkWinner()) {
            textStatus.setText("Player " + (playerX ? "X" : "O") + " wins!");
            disableButtons();
            return;
        }

        // Draw
        if (moveCount == 9) {
            textStatus.setText("Draw!");
            return;
        }

        // Switch turn
        playerX = !playerX;
        textStatus.setText("Player " + (playerX ? "X" : "O") + "'s turn");
    }

    private boolean checkWinner() {
        for (int[] pos : winPositions) {
            String a = buttons[pos[0]].getText().toString();
            String b = buttons[pos[1]].getText().toString();
            String c = buttons[pos[2]].getText().toString();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private void disableButtons() {
        for (Button b : buttons) {
            b.setEnabled(false);
        }
    }

    private void resetGame() {
        for (Button b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
        playerX = true;
        moveCount = 0;
        textStatus.setText("Player X's turn");
    }
}