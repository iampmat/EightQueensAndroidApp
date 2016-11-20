package com.patrickmatherly.eightqueens;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean[][] chessBoard;
    List pieces;
    int piecesOut;
    Button iGiveUp;
    Button restart;
    GridLayout board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iGiveUp = (Button) findViewById(R.id.button0);

        restart = (Button) findViewById(R.id.button1);

        board = (GridLayout) findViewById(R.id.gridLayout);

        // new chess board set to false
        chessBoard = new boolean[8][8];



        initBoard();
    }

    public void initBoard() {
        board.setEnabled(true);
        board.setFocusable(true);
        board.setClickable(true);
        enableDisableView(board, true);


        // reset the queen peices
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    String id = "imageButton" + i + "" + j;
                    int resID = getResources().getIdentifier(id, "id", getPackageName());

                    ImageButton b = (ImageButton) findViewById(resID);
                    b.setImageResource(android.R.color.transparent);

                    chessBoard[i][j] = false;
            }
        }

        // Reset buttons
        restart.setEnabled(false);
        iGiveUp.setEnabled(true);


        piecesOut = 0;


        // Create list of queens
        pieces = new ArrayList();
        createPieceList();
    }

    // This is for the restart, it requires View v as a param
    public void initBoard(View v) {
        board.setEnabled(true);
        board.setFocusable(true);
        board.setClickable(true);
        enableDisableView(board, true);


        // reset the queen peices
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    String id = "imageButton" + i + "" + j;
                    int resID = getResources().getIdentifier(id, "id", getPackageName());

                    ImageButton b = (ImageButton) findViewById(resID);
                    b.setImageResource(android.R.color.transparent);

                    chessBoard[i][j] = false;
            }
        }

        // Reset buttons
        restart.setEnabled(false);
        iGiveUp.setEnabled(true);


        piecesOut = 0;


        // Create list of queens
        pieces = new ArrayList();
        createPieceList();
    }

    public void createPieceList() {
        pieces.add(null);
        ImageView v0 = (ImageView) findViewById(R.id.imageView0);
        v0.setVisibility(View.VISIBLE);
        pieces.add(v0);
        ImageView v1 = (ImageView) findViewById(R.id.imageView1);
        v1.setVisibility(View.VISIBLE);
        pieces.add(v1);
        ImageView v2 = (ImageView) findViewById(R.id.imageView2);
        v2.setVisibility(View.VISIBLE);
        pieces.add(v2);
        ImageView v3 = (ImageView) findViewById(R.id.imageView3);
        v3.setVisibility(View.VISIBLE);
        pieces.add(v3);
        ImageView v4 = (ImageView) findViewById(R.id.imageView4);
        v4.setVisibility(View.VISIBLE);
        pieces.add(v4);
        ImageView v5 = (ImageView) findViewById(R.id.imageView5);
        v5.setVisibility(View.VISIBLE);
        pieces.add(v5);
        ImageView v6 = (ImageView) findViewById(R.id.imageView6);
        v6.setVisibility(View.VISIBLE);
        pieces.add(v6);
        ImageView v7 = (ImageView) findViewById(R.id.imageView7);
        v7.setVisibility(View.VISIBLE);
        pieces.add(v7);
    }

    public void onClick (View v) {
        int tag = Integer.valueOf((String) v.getTag());
        ImageButton b = (ImageButton) findViewById(v.getId());

        int y = tag / 10;
        int x = tag % 10;


        if (chessBoard[y][x] == true) {
            chessBoard[y][x] = false;
            // remove queen image
            b.setImageResource(android.R.color.transparent);

            ImageView iv = (ImageView) pieces.get(piecesOut--);
            iv.setVisibility(View.VISIBLE);
        }
        else if (checkMove(y,x)) {
            Log.w("board: ", "successful move");
            // change imagebutton image
            b.setImageResource(R.drawable.queen1);

            ImageView iv = (ImageView) pieces.get(++piecesOut);
            iv.setVisibility(View.INVISIBLE);
        }
        else {
            Log.w("board: ", "unsuccessful move");
            // toast message: invalid move
            Context context = getApplicationContext();
            CharSequence text = "You can't move there.";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        // Check for win
        if (piecesOut > 7) {
            // Remove the "I give up" Button and allow for restart
            iGiveUp.setEnabled(false);
            restart.setEnabled(true);

            Context context = getApplicationContext();
            CharSequence text = "You win!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public boolean checkMove(int y, int x) {
        Log.w("board: ", ""+y+x);

        // Set queen temporaily
        chessBoard[y][x] = true;

        int leftDX;
        int leftDY;
        int rightDX;
        int rightDY;

        // check left top diagonal
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (chessBoard[j][i]) {
//                    if (Math.abs(y-j) == Math.abs(x-i)) {
//                        chessBoard[y][x] = false;
//                        return false;
//                    }
//                }
//            }
//        }

        // Checks x and y
        for (int i = 0; i < chessBoard.length; i++) {
            if (chessBoard[y][i] == true && i != x) {
                chessBoard[y][x] = false;
                return false;
            }
            if (chessBoard[i][x] == true && i != y) {
                chessBoard[y][x] = false;
                return false;
            }
        }

        int tempX = x;
        int tempY = y;
        tempX--;
        tempY--;
        while (tempY >= 0 && tempY <= 7 && tempX >= 0 && tempX <= 7) {
            if (chessBoard[tempY--][tempX--] == true) {
                chessBoard[y][x] = false;
                return false;
            }
        }
        // check left bottom diagonal
        tempX = x;
        tempY = y;
        tempY++;
        tempX++;
        while (tempY >= 0 && tempY <= 7 && tempX >= 0 && tempX <= 7) {
            if (chessBoard[tempY++][tempX++] == true) {
                chessBoard[y][x] = false;
                return false;
            }
        }
        // check right top diagonal
        tempX = x;
        tempY = y;
        tempY--;
        tempX++;
        while (tempY >= 0 && tempY <= 7 && tempX >= 0 && tempX <= 7) {
            if (chessBoard[tempY--][tempX++] == true) {
                chessBoard[y][x] = false;
                return false;
            }
        }
        // check right bottom diagonal
        tempX = x;
        tempY = y;
        tempY++;
        tempX--;
        while (tempY >= 0 && tempY <= 7 && tempX >= 0 && tempX <= 7) {
            if (chessBoard[tempY++][tempX--] == true) {
                chessBoard[y][x] = false;
                return false;
            }
        }

        return true;
    }

    public void solvePuzzle(View v) {
        int blank = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[j][i]) {
                    blank = i;
                    break;
                }
            }
        }

        Log.w("First Blank is here: ", "" + blank);

        if (backTrack(blank+1, 8)) {
            // print solution
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    if (chessBoard[i][j]) {
                        String id = "imageButton" + i + "" + j;
                        int resID = getResources().getIdentifier(id, "id", getPackageName());

                        ImageButton b = (ImageButton) findViewById(resID);
                        b.setImageResource(R.drawable.queen1);

                        Log.w("backtrack", id);
                    }
                }
            }

            // Remove pieces from list
            Log.w("Pieces Out", "" + piecesOut);
            for (int i = 1; i < 9; i++) {
                ImageView iv = (ImageView) pieces.get(i);
                iv.setVisibility(View.INVISIBLE);
            }

            board.setEnabled(false);
            board.setFocusable(false);
            board.setClickable(false);
            enableDisableView(board, false);


            // Remove the "I give up" Button and allow for restart
            iGiveUp.setEnabled(false);
            restart.setEnabled(true);
        }
        else {
            initBoard();

            Context context = getApplicationContext();
            CharSequence text = "Solution does not exist. Try again.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    public boolean backTrack(int col, int len) {
        if (col >= len) {
            return true;
        }

        for (int i = 0; i < len; i++) {
            if (checkMove(i, col)) {
                // Set piece
//                String id = "R.id.imageButton" + col + "" + len;
//                int resID = getResources().getIdentifier(id, "id", getPackageName());
//
//                ImageButton b = (ImageButton) findViewById(resID);
//                b.setImageResource(R.drawable.queen1);

                chessBoard[i][col] = true;
                piecesOut++;

                if (backTrack(col + 1, 8)) {
                    return true;
                }

                chessBoard[i][col] = false;
                piecesOut--;
            }
        }
        return false;
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

}
