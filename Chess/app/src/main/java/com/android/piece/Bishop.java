package com.android.piece;

import java.util.ArrayList;

/**
 * This class is for Bishop that inherit Piece
 * 
 * @author Jaehyun
 * @author Drew
 */
public class Bishop extends Piece{

	/**
	 * This is constructor is for initializing Bishop which include name and color.
	 * 
	 * @param name getting name 
	 * @param color getting color.
	 */
	public Bishop(String name, Color color) {
		super(name,color);
	}
	public Bishop(String name, Color color, int number) {
		super(name,color,number);
	}
	/**
	 * This method is for checking available moving position that Bishop can move.
	 * 
	 * @param from getting original position.
	 * @param to getting destination position.
	 * @param checkPieces getting board to check Bishop and another pieces
	 * @return return true whether Bishop can move, or not false.
	 */
	@Override
	public boolean valid_move(Point from, Point to, Piece[][] checkPieces) {
		int i = 1;
		if(to.getX()<8 && to.getY() <8 && to.getX()>-1 && to.getY() > -1){
			int toX = to.getX();
			int toY = to.getY();
			int fromX = from.getX();
			int fromY = from.getY();
			
			int diff = Math.abs(toX - fromX);
			int diffY = Math.abs(toY - fromY);
			ArrayList<Point> tmpPath = new ArrayList<>();
			while(i<8){
				int cntTrue = 0;
				if((to.getX() == from.getX()+i) && (to.getY() == from.getY()+i)){// right down

					while (fromX != toX) {

						if (checkPieces[fromX][fromY] == null) {
							tmpPath.add(new Point(fromX,fromY));
							cntTrue++;
						}
						fromX++;
						fromY++;
					}
					if (checkPieces[to.getX()][to.getY()] == null) { // to position empty
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null // if to area has piece then check if team or enemy. if enemy can go
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}

						cntTrue++;
					}

					if (cntTrue == diff){
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else if((to.getX() == from.getX()+i) && (to.getY() == from.getY()-i)) {// left down
					while (fromX != toX) {

						if (checkPieces[fromX][fromY] == null) {
							tmpPath.add(new Point(fromX,fromY));
							cntTrue++;
						}
						fromX++;
						fromY--;
					}
					if (checkPieces[to.getX()][to.getY()] == null) { // to position empty
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null // if to area has piece then check if team or enemy. if enemy can go
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}
						cntTrue++;
					}

					if (cntTrue == diff){
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}

				else if((to.getX() == from.getX()-i) && (to.getY() == from.getY()+i)) { //right up
					while (fromX != toX) {

						if (checkPieces[fromX][fromY] == null) {
							tmpPath.add(new Point(fromX,fromY));
							cntTrue++;
						}
						fromX--;
						fromY++;
					}
					if (checkPieces[to.getX()][to.getY()] == null) { // to position empty
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null // if to area has piece then check if team or enemy. if enemy can go
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}
						cntTrue++;
					}

					if (cntTrue == diff){
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else if((to.getX() == from.getX()-i) && (to.getY() == from.getY()-i))  { //left up
					while (fromX != toX) {

						if (checkPieces[fromX][fromY] == null) {
							tmpPath.add(new Point(fromX,fromY));
							cntTrue++;
						}
						fromX--;
						fromY--;
					}
					if (checkPieces[to.getX()][to.getY()] == null) { // to position empty
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null // if to area has piece then check if team or enemy. if enemy can go
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}
						cntTrue++;
					}

					if (cntTrue == diff){
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else{
					i++;
				}
			}
			return false;
		}

		return false;
	}
}
