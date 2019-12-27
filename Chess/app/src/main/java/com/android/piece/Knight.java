package com.android.piece;

import java.util.ArrayList;

/**
 * This class is for Knight class that inherit Piece
 * 
 * @author Jaehyun
 * @author Drew
 */
public class Knight extends Piece{

	/**
	 * This is constructor for initialize Knight with name and color.
	 * 
	 * @param name getting name.
	 * @param color getting color.
	 */
	public Knight(String name, Color color) {
		super(name,color);
	}
	public Knight(String name, Color color, int number) {
		super(name,color,number);
	}
	/**
	 * This method is for checking available moving position that Knight can move.
	 * 
	 * @param from getting original position.
	 * @param to getting destination position.
	 * @param checkPieces getting board to check Knight and another pieces
	 * @return return true whether Knight can move, or not false.
	 */
	@Override
	public boolean valid_move(Point from, Point to, Piece[][] checkPieces) {
		ArrayList<Point> tmpPath = new ArrayList<>();
		if(to.getX()<8 && to.getY() <8 && to.getX()>-1 && to.getY() > -1){
			if ((to.getX() == from.getX() + 2) && (to.getY() == from.getY() + 1) ||(to.getX() == from.getX() + 2) && (to.getY() == from.getY() - 1) 
				||(to.getX() == from.getX() - 2) && (to.getY() == from.getY() + 1) ||(to.getX() == from.getX() - 2) && (to.getY() == from.getY() - 1) 
				||(to.getX() == from.getX() + 1) && (to.getY() == from.getY() - 2) ||(to.getX() == from.getX() + 1) && (to.getY() == from.getY() + 2) 
				||(to.getX() == from.getX() - 1) && (to.getY() == from.getY() - 2) ||(to.getX() == from.getX() - 1) && (to.getY() == from.getY() + 2)) { 

				if(to.getX()<8 && to.getY() <8 && to.getX()>-1 && to.getY() > -1){
					if (checkPieces[to.getX()][to.getY()] == null){
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else if(checkPieces[to.getX()][to.getY()] != null
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()) {
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							tmpPath.add(new Point(from.getX(),from.getY()));
							setPath_pieceToKing(tmpPath);
							setCheckKing(true);
						}
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else
						return false;
				}
			}
			else {
				return false;
			}
			return false;
		}
		return false;
	}
}
