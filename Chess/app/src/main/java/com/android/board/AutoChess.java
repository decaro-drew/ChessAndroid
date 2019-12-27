package com.android.board;

import com.android.piece.Piece;
import com.android.piece.Point;
import com.android.piece.Queen;

import java.util.ArrayList;

public class AutoChess {
    public static int random_idx = -1;

    public static ArrayList<Point> wPieces = new ArrayList<>();
    public static ArrayList<Point> bPieces = new ArrayList<>();

    public static ArrayList<Point> availableMove = new ArrayList<>();

    public static int whiteRandomIdxGenerator(){
        return (int) (Math.random()* wPieces.size()-1);
    }

    public static int blackRandomIdxGenerator(){
        return (int) (Math.random()* bPieces.size()-1);
    }

    public static int availableMoveIdxGenerator(){
        return (int) (Math.random()* availableMove.size()-1);
    }

    public static boolean validMove(Point getPoint, Piece[][] board){
        int x = getPoint.getX();
        int y = getPoint.getY();

        availableMove.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                System.out.println(board[x][y].getName());
                if(board[x][y] != null) {
                    if (board[x][y].valid_move(getPoint, new Point(i, j), board)) {
//                        System.out.println(x+" "+y);
//                        System.out.println(i+" "+j);
                        availableMove.add(new Point(i, j));
                    }
                }
            }
        }
        if(availableMove.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

}
