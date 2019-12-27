package com.android.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.board.AutoChess;
import com.android.board.Board;
import com.android.board.Game;
import com.android.board.Move;
import com.android.board.Promotion;
import com.android.piece.Color;
import com.android.piece.King;
import com.android.piece.Pawn;
import com.android.piece.Piece;
import com.android.piece.Point;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class BoardActivity extends AppCompatActivity {
    boolean firstClick = true;
    boolean user = true;

    Point getStart;
    Point getLand;

    // XML
    GridView gridView;
    TextView textview_worb;
    TextView textview_info;

    Button undoBT;
    Button drawBT;
    Button resignBT;
    Button aiBT;
    //

    Piece[][] origin_board;
    Pawn tempPawn; // to set cnt 0
    King tempKing;

    IconAdapter iconAdapter;

    Board board_engine;

    Move newMove = new Move();
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        game = Game.getInstance();
        deSerialize();
        this.InitializeView(); // set buttons
        undoBT.setEnabled(false); //before moving, undo button will be blocked. This will be released at the second click board.

        gridView = findViewById(R.id.gridview_board);
        textview_worb = findViewById(R.id.textview_worb);

        board_engine = new Board(BoardActivity.this, gridView); //send activity to Board instance.

        iconAdapter = new IconAdapter(this, board_engine.getBoard());
        gridView.setAdapter(iconAdapter);
        board_engine.setIconAdapter(iconAdapter);
        AdapterView.OnItemClickListener boardListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int columncount = 8;
                int xpos = position / columncount;
                int ypos = position % columncount;

                char promote = ' ';   /** need to figure it out how to change promotion ***/

                origin_board = deepCopyIntMatrix(board_engine.getBoard());
                if (firstClick) {
                    if (user) { //if white trun
                        if(board_engine.getBoard()[xpos][ypos] == null){
                            Toast.makeText(getApplicationContext(), "Invalid Move", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (board_engine.getBoard()[xpos][ypos].getColor() == Color.WHITE) {
                                getStart = new Point(xpos, ypos);
                                firstClick = false;

                                if (board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof Pawn) { //when undo button, if pawn count 0, then come back to 0.
                                    tempPawn = (Pawn) origin_board[getStart.getX()][getStart.getY()];
//                                    if (tempPawn.cntMove == 0)
//                                        System.out.println("Pawn");
                                }
                                if (board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof King) { //when undo button, if pawn count 0, then come back to 0.
                                    tempKing = (King) origin_board[getStart.getX()][getStart.getY()];
//
                                }
                            } else {
                                if (board_engine.getBoard()[xpos][ypos] == null)
                                    Toast.makeText(getApplicationContext(), "Invalid Move", Toast.LENGTH_SHORT).show();
                                firstClick = true;
                                user = true;
                            }
                        }
                    } else { //if black turn
                        if(board_engine.getBoard()[xpos][ypos] == null){
                            Toast.makeText(getApplicationContext(), "Invalid Move", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (board_engine.getBoard()[xpos][ypos].getColor() == Color.BLACK) {
                                getStart = new Point(xpos, ypos);
                                firstClick = false;

                                if (board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof Pawn) {
                                    tempPawn = (Pawn) origin_board[getStart.getX()][getStart.getY()];
//                                        System.out.println("Pawn");
                                }
                                if (board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof King) { //when undo button, if pawn count 0, then come back to 0.
                                    tempKing = (King) origin_board[getStart.getX()][getStart.getY()];
//
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Move", Toast.LENGTH_SHORT).show();
                                firstClick = true;
                                user = false;
                            }
                        }
                    }
                } else { //second selection
                    undoBT.setClickable(true);
                    if (user) {
                        getLand = new Point(xpos, ypos);
                        if (!board_engine.movingPiece(getStart, getLand, promote)) {  // If can't move, then return
                            Toast.makeText(getApplicationContext(), "Invalid Move", Toast.LENGTH_SHORT).show();
                            board_engine.setBoard(deepCopyIntMatrix(origin_board));
                            iconAdapter.changeBoard(origin_board);
                            iconAdapter.notifyDataSetChanged();
                            firstClick = true;
                            user = true;
                            return;
                        } else {   // If valid move, then change piece UI.
                            iconAdapter.changeBoard(board_engine.getBoard());
                            iconAdapter.notifyDataSetChanged();
                            undoBT.setEnabled(true);

                            makeNewMove();

                            if(board_engine.checkmateInActivity){
                                game.addGameSaver(newMove);
//                                game.conductSerializing();
                                Intent intent = new Intent(BoardActivity.this, ResultActivity.class); //just this means onClickListener so explicit exact class name.
                                win = "White Win";
                                intent.putExtra("win", win);
                                startActivity(intent);
                            }
                        }
                        textview_worb.setText("Black Turn");
                        user = false;
                    } else {
                        getLand = new Point(xpos, ypos);
                        if (!board_engine.movingPiece(getStart, getLand, promote)) {  // If can't move, then return
                            Toast.makeText(getApplicationContext(), "Invalid Move", Toast.LENGTH_SHORT).show();
                            board_engine.setBoard(deepCopyIntMatrix(origin_board));
                            iconAdapter.changeBoard(origin_board);
                            iconAdapter.notifyDataSetChanged();
                            firstClick = true;
                            user = false;
                            return;
                        } else {   // If valid move, then change piece UI.
                            iconAdapter.changeBoard(board_engine.getBoard());
                            iconAdapter.notifyDataSetChanged();
                            undoBT.setEnabled(true);

                            makeNewMove();

                            if(board_engine.checkmateInActivity){ //checkmate done
                                game.addGameSaver(newMove);
                                Intent intent = new Intent(BoardActivity.this, ResultActivity.class); //just this means onClickListener so explicit exact class name.
                                win = "Black Win";
                                intent.putExtra("win", win);
                                startActivity(intent);
                            }
                        }
                        if(board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof Pawn){
                            tempPawn = (Pawn) origin_board[getStart.getX()][getStart.getY()];
                        }
                        if (board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof King) { //when undo button, if pawn count 0, then come back to 0.
                            tempKing = (King) origin_board[getStart.getX()][getStart.getY()];
//
                        }
                        textview_worb.setText("White Turn");
                        user = true;

                    }
                    firstClick = true;
                }
            } // end of Button (click)
        };
        gridView.setOnItemClickListener(boardListener); //set on click
    }

    public void InitializeView() {
        undoBT = findViewById(R.id.undoBT);
        drawBT = findViewById(R.id.drawBT);
        resignBT = findViewById(R.id.resignBT);
        aiBT = findViewById(R.id.aiBT);
        textview_info = findViewById(R.id.textview_info);
    }

    private void makeNewMove(){
        newMove.addFroms(getStart);
        newMove.addTos(getLand);
//        System.out.println("size of  "+ newMove.getTos().size() +"  " +newMove.getFroms().size());
    }

    public void undoBT_handler(View v) {
        if (v.getId() == R.id.undoBT) {
            textview_info.setText(" "); //remove check word
//            board = deepCopy(temp_board);
            board_engine.setBoard(deepCopyIntMatrix(origin_board));
            iconAdapter.changeBoard(origin_board);
            iconAdapter.notifyDataSetChanged();
            if(user){
                user = false;
                textview_worb.setText("Black Turn");
            }
            else{
                user = true;
                textview_worb.setText("White Turn");
            }

            /** Solved: Pawn counted, so if it is back, then count of pawn will be 0(if first move)*/
            if(board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof Pawn){
                tempPawn = (Pawn) origin_board[getStart.getX()][getStart.getY()];
                tempPawn.cntMove = 0;
            }
            if(board_engine.getBoard()[getStart.getX()][getStart.getY()] instanceof King){
                tempKing = (King) origin_board[getStart.getX()][getStart.getY()];
                tempKing.setMove(false);
            }
            int fromsLength = newMove.fromsLength();
            int tosLength = newMove.tosLength();

            if(fromsLength != 0 && tosLength != 0){
                newMove.deleteFroms(fromsLength-1);
                newMove.deleteTos(tosLength -1);
            }

            undoBT.setEnabled(false);
        }
    }

    public void drawBT_handler(View v) {
        if (v.getId() == R.id.drawBT) {
//            Toast.makeText(getApplicationContext(), "draw button clicked", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder ad = new AlertDialog.Builder(BoardActivity.this);

            ad.setTitle("Draw");
            ad.setMessage("Are you sure to draw game?");

            ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Log.v("hello","Yes Btn Click");
                    if(Promotion.promotion != null){
                        newMove.setPromotion(Promotion.promotion);
                    }
                    game.addGameSaver(newMove);
//                    game.conductSerializing();
                    Intent intent = new Intent(BoardActivity.this, ResultActivity.class); //just this means onClickListener so explicit exact class name.
                    String result = "Draw";
                    intent.putExtra("draw", result);
                    intent.putExtra("move", newMove); // result activity에서 받기
                    startActivity(intent);
//                     dialog.dismiss();     //close
                }
            });

            ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Log.v("hello","No Btn Click");
                    dialog.dismiss();
                    // Event
                }
            });

            ad.show();
        }
    }

    String win = null;
    public void resignBT_handler(View v) {
        if (v.getId() == R.id.resignBT) {
//            Toast.makeText(getApplicationContext(), "resign button clicked", Toast.LENGTH_SHORT).show();

            if(user){
                win = "Black Win";
            }
            else{
                win = "White Win";
            }
            AlertDialog.Builder ad = new AlertDialog.Builder(BoardActivity.this);

            ad.setTitle("Win");
            ad.setMessage(win);

            ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Log.v("hello","Yes Btn Click");
                    if(Promotion.promotion != null){
                        newMove.setPromotion(Promotion.promotion);
                    }
                    game.addGameSaver(newMove);
//                    game.conductSerializing();
                    Intent intent = new Intent(BoardActivity.this, ResultActivity.class); //just this means onClickListener so explicit exact class name.

                    intent.putExtra("win", win);
//                    intent.putExtra("move", newMove); // result activity에서 받기
                    startActivity(intent);
//                     dialog.dismiss();     //close
                }
            });

            ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Log.v("hello","No Btn Click");
                    dialog.dismiss();
                    // Event
                }
            });

            ad.show();
        }
    }

    public void aiBT_handler(View v) {
        int widx;
        int bidx;
        Point getPointByRandomIdx = null;
        Point getPointByRandomIdx_to = null;

        if (v.getId() == R.id.aiBT) {
//            Toast.makeText(getApplicationContext(), "ai button clicked", Toast.LENGTH_SHORT).show();

            findPiecesByColor();

            if(user){
                while(true){
                    getPointByRandomIdx = AutoChess.wPieces.get(getRandomIdx(AutoChess.wPieces));
                    AutoChess.validMove(getPointByRandomIdx, board_engine.getBoard());
                    if(!AutoChess.availableMove.isEmpty())
                    {
                        getPointByRandomIdx_to = AutoChess.availableMove.get(getRandomIdx(AutoChess.availableMove));
                        break;
                    }
                }
                board_engine.getBoard()[getPointByRandomIdx_to.getX()][getPointByRandomIdx_to.getY()]
                        = board_engine.getBoard()[getPointByRandomIdx.getX()][getPointByRandomIdx.getY()];
                board_engine.getBoard()[getPointByRandomIdx.getX()][getPointByRandomIdx.getY()] = null;
                iconAdapter.changeBoard(board_engine.getBoard());
                iconAdapter.notifyDataSetChanged();
                undoBT.setEnabled(true);

                newMove.addFroms(getPointByRandomIdx);
                newMove.addTos(getPointByRandomIdx_to);

                if (board_engine.checkmateInActivity) { //checkmate done
                    game.addGameSaver(newMove);
                    Intent intent = new Intent(BoardActivity.this, ResultActivity.class); //just this means onClickListener so explicit exact class name.
                    win = "White Win";
                    intent.putExtra("win", win);
                    startActivity(intent);
                }


                textview_worb.setText("Black Turn");
                user = false;
            }
            else{
                while(true){
                    getPointByRandomIdx = AutoChess.bPieces.get(getRandomIdx(AutoChess.bPieces));
                    AutoChess.validMove(getPointByRandomIdx, board_engine.getBoard());
                    if(!AutoChess.availableMove.isEmpty())
                    {
                        getPointByRandomIdx_to = AutoChess.availableMove.get(getRandomIdx(AutoChess.availableMove));
                        break;
                    }
                }
                board_engine.getBoard()[getPointByRandomIdx_to.getX()][getPointByRandomIdx_to.getY()]
                        = board_engine.getBoard()[getPointByRandomIdx.getX()][getPointByRandomIdx.getY()];
                board_engine.getBoard()[getPointByRandomIdx.getX()][getPointByRandomIdx.getY()] = null;
                iconAdapter.changeBoard(board_engine.getBoard());
                iconAdapter.notifyDataSetChanged();
                undoBT.setEnabled(true);

                newMove.addFroms(getPointByRandomIdx);
                newMove.addTos(getPointByRandomIdx_to);

                if (board_engine.checkmateInActivity) { //checkmate done
                    game.addGameSaver(newMove);
                    Intent intent = new Intent(BoardActivity.this, ResultActivity.class); //just this means onClickListener so explicit exact class name.
                    win = "Black Win";
                    intent.putExtra("win", win);
                    startActivity(intent);
                }


                textview_worb.setText("White Turn");
                user = true;

            }


        }
    }

    private int getRandomIdx(ArrayList<Point> list){
       double ramdomIdx = Math.random();
        return (int) (ramdomIdx * list.size()-1);
    }

    private void findPiecesByColor(){
        AutoChess.wPieces.clear();
        AutoChess.bPieces.clear();
        AutoChess.availableMove.clear();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board_engine.getBoard()[i][j] != null) {
                    if (board_engine.getBoard()[i][j].getColor() == Color.WHITE) {
                        AutoChess.wPieces.add(new Point(i, j));
                    } else if (board_engine.getBoard()[i][j].getColor() == Color.BLACK) {
                        AutoChess.bPieces.add(new Point(i, j));
                    }
                }
            }
        }
    }

    private Piece[][] deepCopyIntMatrix(Piece[][] input) { // not int matrix it is Piece matrix
        if (input == null)
            return null;
        Piece[][] result = new Piece[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }

    private void deSerialize(){
        try {
            FileInputStream fis = openFileInput("serial.ser");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream in = new ObjectInputStream(bis);

            game.gameSaver = (ArrayList<Move>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}