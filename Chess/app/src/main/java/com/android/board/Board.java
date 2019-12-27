package com.android.board;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Icon;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.chess.BoardActivity;
import com.android.chess.IconAdapter;
import com.android.chess.LoadIconAdapter;
import com.android.chess.LoadPlayGameActivity;
import com.android.chess.R;
import com.android.piece.Bishop;
import com.android.piece.Color;
import com.android.piece.King;
import com.android.piece.Knight;
import com.android.piece.Pawn;
import com.android.piece.Piece;
import com.android.piece.Point;
import com.android.piece.Queen;
import com.android.piece.Rook;


import java.util.ArrayList;

/**
 * This class is Board
 *
 * @author Jaehyun
 * @author Drew
 */
public class Board extends Application {
    IconAdapter iconAdapter;
    LoadIconAdapter loadIconAdapter;

    private Context context;
    TextView textView;
    @Override
    public void onCreate(){
        super.onCreate();
    }
	/**
	 * board string indicator for column
	 */
	private static final String BOARD_STRING_INDICATOR = " a  b  c  d  e  f  g  h";

    /**
     * This field is for board. Board is 8x8 size and all type is Piece which is abstract class.
     */
    private Piece[][] board = new Piece[8][8];
    /**
     * This field is for checking white king is checked
     */
    private King wKing = null;
    /**
     * This field is for checking black king is checked
     */
    private King bKing = null;

    /**
     * This field is for getting piece which checking King
     */
    private Point getCheckPiece;
    /**
     * This field is for getting specific object that checking king.
     */
    private Piece getCheckPieceObject;

    private int cntWkingChecked;
    private int cntBkingChecked;

    private String strPromotion;

    GridView gridView;

    public int cntValidCheckMate = 0;
    public boolean checkmateInActivity = false;


    public boolean isPromotionFromLoadGame = false;

    /**
     * This is for constructor that initializes board and set pieces on the board.
     */
    public void setIconAdapter(IconAdapter iconAdapter){
        this.iconAdapter = iconAdapter;
        gridView.setAdapter(iconAdapter);

    }
    public void setLoadIconAdapter(LoadIconAdapter loadIconAdapter){
        this.loadIconAdapter = loadIconAdapter;
        gridView.setAdapter(loadIconAdapter);

    }
    public Board(){}
    public  Board(Context context, GridView gridView){
        this.gridView = gridView;
//        iconAdapter = new IconAdapter(this, board);
//        gridView.setAdapter(iconAdapter);

        this.context = context;
//        textView = ((Activity)context).findViewById(R.id.textview_worb);
        board[0][0] = new Rook("bR", Color.BLACK,1);
        board[0][1] = new Knight("bN",Color.BLACK,2);
        board[0][2] = new Bishop("bB",Color.BLACK,3);
        board[0][3] = new Queen("bQ",Color.BLACK,4);
        board[0][4] = new King("bK",Color.BLACK,5);
        bKing = (King) board[0][4];
        bKing.setKingPosition(0,4);
        board[0][5] = new Bishop("bB",Color.BLACK,6);
        board[0][6] = new Knight("bN",Color.BLACK,7);
        board[0][7] = new Rook("bR",Color.BLACK,8);

        for (int p=0; p<8;p++ )
            board[1][p] = new Pawn("bP",Color.BLACK,9);

        board[7][0] = new Rook("wR",Color.WHITE,11);
        board[7][1] = new Knight("wN",Color.WHITE,12);
        board[7][2] = new Bishop("wB",Color.WHITE,13);
        board[7][3] = new Queen("wQ",Color.WHITE,14);
        board[7][4] = new King("wK",Color.WHITE,15);
        wKing = (King) board[7][4];
        wKing.setKingPosition(7,4);
        board[7][5] = new Bishop("wB",Color.WHITE,16);
        board[7][6] = new Knight("wN",Color.WHITE,17);
        board[7][7] = new Rook("wR",Color.WHITE,18);

        for(int b=0; b<8;b++)
            board[6][b] = new Pawn("wP",Color.WHITE);

    }

    /**
     * This method is for castling.
     *
     * @param board get board to check board
     * @param to get Point instance to check King's position
     */
    private void castling(Piece[][] board, Point to){
        //white king
        if(to.getX() == 7 && to.getY() == 6 && !isCheck()){
            if(board[7][7] instanceof Rook){
                board[7][5] = board[7][7];
                board[7][7] = null;
//                System.out.println("Castling");
            }
        }
        else if(to.getX() == 7 && to.getY() == 2 && !isCheck()){
            if(board[7][0] instanceof Rook){
                board[7][3] = board[7][0];
                board[7][0] = null;
//                System.out.println("Castling");
            }
        }
        //Black King
        if(to.getX() == 0 && to.getY() == 6 && !isCheck()){
            if(board[0][7] instanceof Rook){
                board[0][5] = board[0][7];
                board[0][7] = null;
                //System.out.println("Castling");
            }
        }
        else if(to.getX() == 0 && to.getY() == 2 && !isCheck()){
            if(board[0][0] instanceof Rook){
                board[0][3] = board[0][0];
                board[0][0] = null;
                //System.out.println("Castling");
            }
        }
    }


    /**
     * This method is for checking pieces that can protect King when King is on check and checkmate.
     *
     * @return return boolean if same color pieces can protect their King, then return true else return false.
     */
    /**
     * @return return true if piece can rescue king or return false.
     */
    private boolean isRescueKing(){
        boolean validCheckMate = false;
        int cnt = 0;
        outLoop:
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j] != null){
                    if(wKing.isChecked){
                        if(board[i][j].getColor() == Color.WHITE){
                            if (board[i][j].valid_move(new Point(i,j), getCheckPiece, board)){  // 1. can same color piece can catch to enemy which made check to protect King ?
                                return false;
                            }
                            else{
//                                System.out.println("2");
                                validCheckMate = true;
                            }
                        }
                    }
                    else if(bKing.isChecked){
                        if(board[i][j].getColor() == Color.BLACK){
                            if (board[i][j].valid_move(new Point(i,j), getCheckPiece, board)){
                                return false;
                            }
                            else{
//                                System.out.println("4");
                                validCheckMate = true;
                            }
                        }
                    }
                }
                if( board[i][j] != null){   //2. can same color piece can protect King to move path btw made check piece and king.
                    if(wKing.isChecked) {
                        if (board[i][j].getColor() == Color.WHITE) {
                            for (Point p : getCheckPieceObject.getPath_pieceToKing()) {
                                if (!(board[i][j] instanceof King) && board[i][j].valid_move(new Point(i, j), p, board)) {
                                  return false;
                                } else {
                                    validCheckMate = true;
                                }
                            }
                        }
                    }
                    else if(bKing.isChecked) {
                        if (board[i][j].getColor() == Color.BLACK) {
                            for (Point p : getCheckPieceObject.getPath_pieceToKing()) {
                                if (!(board[i][j] instanceof King) && board[i][j].valid_move(new Point(i, j), p, board)) {
                                    return false;
                                } else {
                                    validCheckMate = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return validCheckMate;
    }

    /**
     * This method is for getting valid King's position to avoid from check and checkmate.
     *
     * @param x getting integer position row.
     * @param y getting integer position column.
     * @return return ArrayList that includes king's available position.
     */
    private ArrayList<Point> getValidKingPosition(int x, int y){
        ArrayList<Point> KingCanMovePosition = new ArrayList<>();

        KingCanMovePosition.add(new Point(x+1, y+1));
        KingCanMovePosition.add(new Point(x+1,y-1));
        KingCanMovePosition.add(new Point(x+1, y));
        KingCanMovePosition.add(new Point(x, y+1));
        KingCanMovePosition.add(new Point(x, y-1));
        KingCanMovePosition.add(new Point(x-1, y+1));
        KingCanMovePosition.add(new Point(x-1, y-1));
        KingCanMovePosition.add(new Point(x-1, y));

        return KingCanMovePosition;

    }

    /**
     * This method is for determining checkmate.
     */
    private void checkmate(){
//        System.out.println("checkmate");
        boolean validCheckMate;
//        int cntValidCheckMate = 0;

        ArrayList<Point> getWhiteKingsPosition;
        ArrayList<Point> getBlackKingsPosition;

        getBlackKingsPosition = getValidKingPosition(bKing.getKingPosition().getX(), bKing.getKingPosition().getY());
        getWhiteKingsPosition = getValidKingPosition(wKing.getKingPosition().getX(), wKing.getKingPosition().getY());


        //Protect condition 1
        validCheckMate = isRescueKing(); // it is checked when it is called
        if(!validCheckMate)
            return;

        //Protect condition 3
        Point saveOriginal = wKing.getKingPosition(); //original position of White king.
        Point saveOriginalB = bKing.getKingPosition(); // original position of black king.

        if(wKing.isChecked){ // if white king is checked.
            breakLoop:
            for(Point validKing : getWhiteKingsPosition){
                wKing.KingPosition = saveOriginal;
                if(wKing.valid_move(wKing.getKingPosition(), validKing ,board)){
                    wKing.KingPosition = validKing;
                    if(isCheck()){ //after move check and rescue king.
                        validCheckMate = isRescueKing();
                        if(!validCheckMate){
                            return;
                        }
                    }
                    else {
                        wKing.KingPosition = saveOriginal;
                        return;
                    }
                }
                else {
                    validCheckMate = true;
                }
            }
            if(validCheckMate){
                Toast.makeText(context, "Checkmate", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Black Wins", Toast.LENGTH_SHORT).show();

                checkmateInActivity = true;
            }
        }
        else if(bKing.isChecked){
            breakLoop:
            for(Point validKing : getBlackKingsPosition){
                bKing.KingPosition = saveOriginalB;
                if(bKing.valid_move(bKing.getKingPosition(), validKing, board)){
                    bKing.KingPosition = validKing;
                    if(isCheck()){
                        validCheckMate = isRescueKing();
                       if(!validCheckMate){
                           return;
                       }
                    }
                    else {
                        validCheckMate = false;
                        cntValidCheckMate++;
                        bKing.KingPosition = saveOriginalB;
                        break breakLoop;
                    }
                }
                else {
                    validCheckMate = true;
                }
            }
            if(validCheckMate){
                Toast.makeText(context, "Checkmate", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "White win", Toast.LENGTH_SHORT).show();

                checkmateInActivity = true; // it will be used at BoardActivity to terminate game when it is checkmate
            }
        }
    }

    /**
     * This method is for check King.
     *
     * @return return true when any pieces attack another color's king.
     */
    private boolean isCheck(){
        for(int i =0; i < 8; i++) {
            for(int j = 0; j<8; j++) {
                if(board[i][j] != null){ // if it is piece
                    if(board[i][j].getColor() == Color.BLACK){ // and that piece is black
                        Point from = new Point(i,j); // getting points.
                        if(board[i][j].valid_move(from, wKing.getKingPosition(), board)){ //move piece to king. if king is on the path, check!
                            if (board[i][j].isCheckKing()){ //if it attacks white king
                                getCheckPieceObject = board[i][j]; //get this piece
                                getCheckPiece = new Point(i,j); //get this point of piece.
                                wKing.isChecked = true; //set white king is checked;
                                return true;
                            }
                            else {
                                wKing.isChecked = false;
                                return false;
                            }
                        }
                        else{
                            wKing.isChecked = false;
                        }
                    }
                    else{
                        Point from = new Point(i,j);
                        if(board[i][j].valid_move(from, bKing.getKingPosition(), board)){ // if king is on the path, check!
                            if (board[i][j].isCheckKing()){
                                getCheckPieceObject = board[i][j];
                                getCheckPiece = new Point(i,j);
                                bKing.isChecked = true;
                                return true;
                            }
                            else{
                                bKing.isChecked = false;
                                return false;
                            }
                        }
                        else{
                            bKing.isChecked = false;
                        }
                    }
                }
            }
        }
        wKing.isChecked = false;
        bKing.isChecked = false;
        return false;
    }

    /**
     * This method is moving engine after check valid move.
     *
     * @param cur get current piece
     * @param from get point original position
     * @param to get point destination position
     * @param board get board
     * @param promote to get character to determine replace pawn
     * @return return boolean if all conditions are matching
     */

    private boolean move (final Piece cur, Point from, final Point to, Piece[][] board, final char promote){
        char t;
        if(cur.valid_move(from, to, board)){
            board[to.getX()][to.getY()] = cur;
            board[from.getX()][from.getY()] = null;

            if(cur instanceof King){
                if(cur.getColor() == Color.WHITE){ //update king position to check check
                    wKing = (King) board[to.getX()][to.getY()];
                    wKing.setKingPosition(to.getX(),to.getY());
                }

                else{
                    bKing = (King) board[to.getX()][to.getY()];
                    bKing.setKingPosition(to.getX(),to.getY());
                }

                if (isCheck()){
                    Toast.makeText(context, "Illegal move, try again", Toast.LENGTH_SHORT).show();
                    board[from.getX()][from.getY()] = cur;
                    board[to.getX()][to.getY()] = null;
                    return false;
                }

                castling(board, to);
            }

            //call promotion here:
            boolean callFalse;
            if(cur instanceof Pawn) {
                //call promotion
                if(to.getX() == 0 || to.getX() == 7) {
//                    isPromotionFromLoadGame = true;
                    if(isPromotionFromLoadGame){
//                        System.out.println(Promotion.promotion);
                        Promotion(cur, to, Promotion.promotion.charAt(0));
                    }
                    else {
                        final CharSequence[] items2 = {"Knight", "Rook", "Bishop", "Queen"};
                        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(context);

                        alertDialogBuilder2.setTitle("Select Piece");
                        alertDialogBuilder2.setSingleChoiceItems(items2, -1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        strPromotion = items2[id].toString();
                                        Promotion.promotion = null;
                                        Promotion.promotion = strPromotion;
                                        Promotion(cur, to, strPromotion.charAt(0));
                                        Toast.makeText(context,
                                                strPromotion,
                                                Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                        alertDialogBuilder2.show();
                        Promotion(cur, to, 'Q');
                    }
                }
                //this avoids false enpassant call
                if(to.getX() - from.getX() == 2 || to.getX() -from.getX() == -2) {
//                    return true;
                    callFalse = false;
                }
                else{
                    callFalse = true;
                }
            }
            else{
                callFalse = true;
            }
            if(callFalse)
                falseEnPassant();

            if(isCheck()){

                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if(bKing.isChecked && cur.getColor() == Color.BLACK ){
//                    System.out.println("Black check");
                    board[from.getX()][from.getY()] = cur;
                    board[to.getX()][to.getY()] = null;
//                    cntBkingChe/cked = 0;
                    return false;
                }
                else if(wKing.isChecked && cur.getColor() == Color.WHITE ){
//                    System.out.println("White check");
                    board[from.getX()][from.getY()] = cur;
                    board[to.getX()][to.getY()] = null;
//                    cntWkingChecked = 0;
                    return false;
                }
                checkmate();
                Toast.makeText(context, "check!", Toast.LENGTH_SHORT).show();
                try {
                    textView = ((BoardActivity) context).findViewById(R.id.textview_info);
                    textView.setText("Check!");
                }catch (Exception e){
                    textView = ((LoadPlayGameActivity) context).findViewById(R.id.textview_info);
                    textView.setText("");
                }
            }
            else{
                try {
                    textView = ((BoardActivity) context).findViewById(R.id.textview_info);
                    textView.setText("");
                }catch (Exception e){
                    textView = ((LoadPlayGameActivity) context).findViewById(R.id.textview_info);
                    textView.setText("");
                }
            }
            return true;

        }
        else{
            Toast.makeText(context, "Illegal move, try again", Toast.LENGTH_SHORT).show();
//            System.out.println("Illegal move, try again");
            return false;
        }
    }                    


    /**
     * This method is for checking moving pieces.
     *
     * @param from need to get original position.
     * @param to need to get destination position.
     * @param promote send the indicated piece that a user wants to promote to, if needed
     * @return return boolean if all conditions are matching.
     */
    public boolean movingPiece(Point from, Point to, char promote) {
        boolean isMoving;
        Piece cur = board[from.getX()][from.getY()];

//        System.out.println(from.getX());

        if (cur instanceof King){
//            Toast.makeText(context, "King", Toast.LENGTH_SHORT).show();
//            System.out.println("King");
            isMoving = move(cur, from, to, board, promote);
            return isMoving;
        }
        else if(cur instanceof Queen) {
//            Toast.makeText(context, "Queen", Toast.LENGTH_SHORT).show();
            isMoving = move(cur, from, to, board, promote);
            return isMoving;

        }
        else if (cur instanceof Bishop){
//            System.out.println("Bishop");
//            Toast.makeText(context, "Bishop", Toast.LENGTH_SHORT).show();
            isMoving = move(cur, from, to, board, promote);
            return isMoving;
        }
        else if (cur instanceof Knight){
//            System.out.println("Knight");
//            Toast.makeText(context, "Knight", Toast.LENGTH_SHORT).show();
            isMoving = move(cur, from, to, board, promote);
            return isMoving;
        }
        else if (cur  instanceof Rook){
//            System.out.println("Rook");
//            Toast.makeText(context, "Rook", Toast.LENGTH_SHORT).show();
            isMoving = move(cur, from, to, board, promote);
            return isMoving;
        }
        else if (cur instanceof Pawn){
//            Toast.makeText(context, "Pawn", Toast.LENGTH_SHORT).show();
            isMoving = move(cur, from, to, board, promote);
            return isMoving;
        }
        else{
            //System.out.println("No pieces");
            return false;
        }

    }
    /*
    * This method sets the possibility of en Passant of all pieces to false.
    *
    *
    */
    private void falseEnPassant() {
        for(int i =0; i < 8; i++) {
            for(int j = 0; j<8; j++) {
                if(board[i][j] instanceof Pawn) {
                    board[i][j].setEnPassant(false);
                }
            }
        }
        return;
    }

    /**
     * This method is for promotion. When pawn arrives end of board, then can switch to queen, rook, bishop,and knight.
     *
     * @param cur get current position to check current color
     * @param to get Point instance to know destination.
     * @param promote get the kind of piece a user wants to promote to
     */
    private void Promotion(Piece cur, Point to, char promote) {
//        System.out.println("Promotion!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +promote);
        int x = to.getX();
        int y = to.getY();
        Color color = cur.getColor();

        if(promote == 'K') {
        	if(x == 0){
                board[x][y] = new Knight("wN", color);
            }
            else {
                board[x][y] = new Knight("bN", color);
            }
        }
        else if(promote == 'R') {
        	if(x == 0){
                board[x][y] = new Rook("wR", color);

            }
            else {
                board[x][y] = new Rook("bR", color);
            }
        }
        else if(promote == 'B') {
        	if(x == 0){
                board[x][y] = new Bishop("wB", color);
            }
            else {
                board[x][y] = new Bishop("bB", color);
            }

        }
        else if(promote == 'Q'){
        	if(x == 0){
                board[x][y] = new Queen("wQ", color);
            }
            else {
                board[x][y] = new Queen("bQ", color);
            }
        }
        else{
            if(x == 0){
                board[x][y] = new Queen("wQ", color);
            }
            else {
                board[x][y] = new Queen("bQ", color);
            }
        }
        try {
            iconAdapter.changeBoard(board);
            iconAdapter.notifyDataSetChanged();
        }catch (Exception e){
            loadIconAdapter.changeBoard(board);
            loadIconAdapter.notifyDataSetChanged();
        }

        return;


    }

    /**
     * This method is for getting board to use anywhere.
     *
     * @return return current board.
     */
    public Piece[][] getBoard(){
        return board;
    }

    public void setBoard(Piece[][] board){
        this.board = board;
    }
}
