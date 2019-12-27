package com.android.piece;

import java.util.ArrayList;

/**
 * This class is for Rook that inherit Piece
 * 
 * @author Jaehyun
 * @author Drew
 */
public class Rook extends Piece{

	/**
	 * This is Rook constructor that include name and color
	 * 
	 * @param name this is for name
	 * @param color this is for color
	 */
	public Rook(String name, Color color) {
		super(name,color);
	}
	public Rook(String name, Color color, int number) {
		super(name,color,number);
	}
	/**
	 * This method is for checking available moving position that Rook can move.
	 * 
	 * @param from getting original position.
	 * @param to getting destination position.
	 * @param checkPieces getting board to check Rook and another pieces
	 * @return return true whether Rook can move, or not false.
	 */
	@Override
	public boolean valid_move(Point from, Point to, Piece[][] checkPieces) {
		if(to.getX()<8 && to.getY() <8 && to.getX()>-1 && to.getY() > -1){
			int i = 1;

			int toX = to.getX();
			int toY = to.getY();
			int fromX = from.getX();
			int fromY = from.getY();
			
			int diff = Math.abs(toX - fromX);
			int diffY = Math.abs(toY - fromY);

			ArrayList<Point> tmpPath = new ArrayList<>();

			while(i<8){
				int cntTrue = 0;
				if( (to.getX() == from.getX()+i) && (to.getY() == from.getY())){ // down

					for(int j = fromX; j<toX; j++){ // down check
						if (checkPieces[j][to.getY()] == null ){ //from to to-1 empty check
							tmpPath.add(new Point(j,fromY));
							cntTrue++;
						}
					}
					if (checkPieces[to.getX()][to.getY()] == null){ // to position empty
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
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else if((to.getX() == from.getX()) && (to.getY() == from.getY()+i)) { // right
					for(int j = fromY; j<toY; j++){ // down check
						if (checkPieces[to.getX()][j] == null ){
							tmpPath.add(new Point(fromX,j));
							cntTrue++;
						}
					}
					if (checkPieces[to.getX()][to.getY()] == null){
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}
						cntTrue++;
					}
					if (cntTrue == diffY){
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else if ((to.getX() == from.getX()) && (to.getY() == from.getY()-i)){ //left
					for(int j = fromY; j>toY; j--){ // down check
						if(checkPieces[to.getX()][j] == null){
							tmpPath.add(new Point(fromX,j));
							cntTrue++;
						}
					}
					if (checkPieces[to.getX()][to.getY()] == null){
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}
						cntTrue++;
					}
					if (cntTrue == diffY){
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else if ((to.getX() == from.getX()-i) && (to.getY() == from.getY())){ //up
					for(int j = fromX; j>toX; j--){ // down check
						if(checkPieces[j][to.getY()] == null){
							tmpPath.add(new Point(j,fromY));
							cntTrue++;
						}
					}
					if (checkPieces[to.getX()][to.getY()] == null){
						cntTrue++;
					}
					else if(checkPieces[to.getX()][to.getY()] != null
							&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()){
						if(checkPieces[to.getX()][to.getY()] instanceof King){
							setCheckKing(true);
//							tmpPath.add(new Point(fromX,fromY));
							setPath_pieceToKing(tmpPath);
						}
						cntTrue++;
					}
					if (cntTrue == diff){
						setMove(true);
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
