package com.android.piece;

/**
 * This is King class that inherit Piece
 * 
 * @author Jaehyun
 * @author Drew
 */
public class King extends Piece{
	/**
	 * This field is for getting King's position.
	 */
	public Point KingPosition;
	/**
	 * This field is for king is checked or not
	 */
	public boolean isChecked = false;
	/**
	 * This field is for king is checkmate or not
	 */
	public boolean isCheckMate = false;

	/**
	 * This is constructor to initialize King that include name and color
	 * 
	 * @param name getting name.
	 * @param color getting color.
	 */
	public King(String name, Color color) {
		super(name,color);
	}
	public King(String name, Color color, int number) {
		super(name,color,number);
	}
	/**
	 * This method is for set king's position.
	 * 
	 * @param x getting row
	 * @param y getting column
	 */
	public void setKingPosition(int x, int y){
		KingPosition = new Point(x,y);
	}
	
	/**
	 * This method is for returning King's position which is Position type.
	 * 
	 * @return return King's position.
	 */
	public Point getKingPosition(){
		return KingPosition;
	}

	/**
	 * This method is for checking available moving position that King can move.
	 * 
	 * @param from getting original position.
	 * @param to getting destination position.
	 * @param checkPieces getting board to check King and another pieces
	 * @return return true whether King can move, or not false.
	 */
	@Override
	public boolean valid_move(Point from, Point to, Piece[][] checkPieces) {
		if(to.getX()<8 && to.getY() <8 && to.getX()>-1 && to.getY() > -1){
			if ((to.getX() == from.getX() + 1) && (to.getY() == from.getY() + 1) ||(to.getX() == from.getX() + 1) && (to.getY() == from.getY() - 1)
					||(to.getX() == from.getX() + 1) && (to.getY() == from.getY()) ||(to.getX() == from.getX()) && (to.getY() == from.getY() + 1)
					||(to.getX() == from.getX()) && (to.getY() == from.getY() - 1) ||(to.getX() == from.getX() - 1) && (to.getY() == from.getY() + 1)
					||(to.getX() == from.getX() - 1) && (to.getY() == from.getY() - 1) ||(to.getX() == from.getX() - 1) && (to.getY() == from.getY())) {


				if (checkPieces[to.getX()][to.getY()] == null){
//					System.out.println(to.getX() + " "+ to.getY() + "  "+from.getX() +" " + from.getY());
					setMove(true); // for castling
					Piece.MOVE_SEQUENCE++;
					return true;
				}
				else if(checkPieces[to.getX()][to.getY()] != null // if 'to' area has piece then check if team or enemy. if enemy can go
						&& checkPieces[from.getX()][from.getY()].getColor() != checkPieces[to.getX()][to.getY()].getColor()) {
					setMove(true);
					Piece.MOVE_SEQUENCE++;
					return true;
				}
				else
					return false;
			}
			//Castling
			//white King and rook
			// Right King - Rook
			else if((from.getX() == 7 && from.getY() == 4) && (to.getX() == 7 && to.getY()==6)){
				if(!checkPieces[from.getX()][from.getY()].isMove()){
					if (checkPieces[7][5] == null && checkPieces[7][6] == null){
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else{
					return false;
				}
			}
			//Left Rook - King
			else if((from.getX() == 7 && from.getY()==4) && (to.getX()==7 && to.getY()==2)){
				if(!checkPieces[from.getX()][from.getY()].isMove()){
					if (checkPieces[7][1] == null && checkPieces[7][2] == null && checkPieces[7][3] == null){
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else{
					return false;
				}
			}

			//Black King and Rook
			//Right king - Rook
			else if((from.getX() == 0 && from.getY() == 4) && (to.getX() == 0 && to.getY() == 6)){
				if(!checkPieces[from.getX()][from.getY()].isMove()){
					if (checkPieces[0][5] == null && checkPieces[0][6] == null){
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else{
					return false;
				}
			}
			//Left rook - king
			else if ((from.getX() == 0 && from.getY() == 4) && (to.getX() == 0 && to.getY() == 2)){
				if(!checkPieces[from.getX()][from.getY()].isMove()){

					if (checkPieces[0][1] == null && checkPieces[0][2] == null && checkPieces[0][3] == null){
						setMove(true);
						Piece.MOVE_SEQUENCE++;
						return true;
					}
					else return false;
				}
				else{
					return false;
				}
			}

			else {
				return false;
			}
		}
		return false;
	}
}
