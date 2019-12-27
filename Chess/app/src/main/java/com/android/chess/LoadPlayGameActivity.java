package com.android.chess;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.board.Board;
import com.android.board.Game;
import com.android.board.Move;
import com.android.board.Promotion;
import com.android.piece.Point;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoadPlayGameActivity extends AppCompatActivity {

    Button runBT;

    GridView gridView;
    LoadIconAdapter loadiconAdapter;

    Game game = Game.getInstance();
    Move getSelectedMove;
    Board board_engine;

    int k;
    boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_run_board);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String getTitleFromListview = bundle.getString("title");

        getSelectedMove = game.getSpecificMoveByTitleName(getTitleFromListview);
        if(getSelectedMove.getPromotion() != null)
            Promotion.promotion = getSelectedMove.getPromotion();

        InitializeView();

        gridView = findViewById(R.id.gridview);

        board_engine = new Board(LoadPlayGameActivity.this, gridView);
        board_engine.isPromotionFromLoadGame = true;
        loadiconAdapter = new LoadIconAdapter(this,board_engine.getBoard());
        gridView.setAdapter(loadiconAdapter);
        board_engine.setLoadIconAdapter(loadiconAdapter);
    }

    public void InitializeView() {
        runBT = findViewById(R.id.runBT);
        k = 0;

    }

    public void runBT_handler(View v) {
        if (v.getId() == R.id.runBT) {

            if(getSelectedMove.getTos().size() != k) { // change like this because when user click next button several time it throws exceptions.
                Point from = getSelectedMove.getFroms().get(k);
                Point to = getSelectedMove.getTos().get(k);

                if(board_engine.movingPiece(from, to, ' ')){
                    loadiconAdapter.changeBoard(board_engine.getBoard());
                    loadiconAdapter.notifyDataSetChanged();
                }

                k++;
                if(getSelectedMove.getTos().size() == k){
                    gameOver = true;
                }
            }


            if(gameOver){
                AlertDialog.Builder ad = new AlertDialog.Builder(LoadPlayGameActivity.this);
                ad.setTitle("Game Over");
                ad.setMessage("Return to home Screen?");

                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoadPlayGameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        runBT.setEnabled(false);
                    }
                });

                ad.show();
                return;
            }
        }
    }
}