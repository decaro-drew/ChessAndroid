package com.android.piece;

import java.util.ArrayList;

/**
 * This class is for Pawn that inherit Piece
 * 
 * @author Jaehyun
 * @author Drew
 */
public class Pawn extends Piece{
	/**
	 * This field is for getting sequence number for using enpassant
	 */
	private int ownSequence;
	/**
	 * This is for counting pawn piece moving.
	 */
	public int cntMove = 0;
	/**
	 * This is for enpassant
	 */
	private boolean enPassant;

	/**
	 * This is constructor for initialize Pawn with name and color.
	 * 
	 * @param name get name
	 * @param color get color
	 */
	public Pawn(String name, Color color) {
		super(name,color);
	}
	public Pawn(String name, Color color, int number) {
		super(name,color,number);
	}

	/**
	 * This method is for get sequence number to do enpassant
	 * 
	 * @return return sequence number
	 */
	public int getOwnSequence() {
		return ownSequence;
	}

	/**
	 *This method is getting enpassant which is boolean type.
	 *@return return enPassant variable.
	 */
	@Override
	public boolean getEnPassant() {
		return enPassant;
	}
	
	/**
	 * This is for setting enpassant. 
	 * @param enPassant boolean type of variable
	 */
	@Override
	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}

	/**
	 * This method is for checking available moving position that Pawn can move.
	 * 
	 * @param from getting original position.
	 * @param to getting destination position.
	 * @param checkPieces getting board to check Pawn and another pieces
	 * @return return true whether Pawn can move, or not false.
	 */
	@Override
	public boolean valid_move(Point from, Point to, Piece[][] checkPieces) {
		ArrayList<Point> tmpPath = new ArrayList<>();
		if(to.getX()<8 && to.getY() <8 && to.getX()>-1 && to.getY() > -1){
			if (cntMove == 0) { //First move
				// ** first + WHITE
				if(checkPieces[from.getX()][from.getY()] != null && checkPieces[from.getX()][from.getY()].getColor() == Color.WHITE){
					// one move
					if((to.getX() == from.getX()-1) && (to.getY() == from.getY())){
						if (checkPieces[to.getX()][to.getY()] == null) {
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;   // actually I should have used countMovingWholePieces static field to set ownSequence, It is better to reduce redundant codes, but I forgot this field after I made.
							return true;
						}
						else
							return false; //check is there any piece or not
					}
					// two move
					else if((to.getX() == from.getX()-2) && (to.getY() == from.getY())){
						if (checkPieces[from.getX()-1][from.getY()] == null //check are there any pieces or not
								&& checkPieces[to.getX()][to.getY()] == null) {
							cntMove++;
							this.setEnPassant(true);
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;

							return true;
						}
						else
							return false;
					}
					// attack diagonal move
					else if((to.getX() == from.getX()-1) && (to.getY() == from.getY()+1) // right up
							|| (to.getX() == from.getX()-1) && (to.getY() == from.getY()-1)){ // left up
						if (checkPieces[to.getX()][to.getY()] != null && checkPieces[to.getX()][to.getY()].getColor() == Color.BLACK) {
							if(checkPieces[to.getX()][to.getY()] instanceof King){
								tmpPath.add(new Point(from.getX(),from.getY()));
								setPath_pieceToKing(tmpPath);
								setCheckKing(true);
							}
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						}
						else
							return false;   // if the piece is black then can go.
					}
				}
				// ** first + BLACK
				else {
					// one move
					if((to.getX() == from.getX()+1) && (to.getY() == from.getY())){
						if (checkPieces[to.getX()][to.getY()] == null) {
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						}
						else
							return false; //check is there any piece or not
					}
					// two move
					else if((to.getX() == from.getX()+2) && (to.getY() == from.getY())){
						if (checkPieces[from.getX()+1][from.getY()] == null //check are there any pieces or not
								&& checkPieces[to.getX()][to.getY()] == null) {
							cntMove++;
							this.setEnPassant(true);
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						}
						else
							return false;
					}
					// attack diagonal move
					else if((to.getX() == from.getX()+1) && (to.getY() == from.getY()+1) // right down
							|| (to.getX() == from.getX()+1) && (to.getY() == from.getY()-1)){ // left down
						if (checkPieces[to.getX()][to.getY()] != null && checkPieces[to.getX()][to.getY()].getColor() == Color.WHITE) {
							if(checkPieces[to.getX()][to.getY()] instanceof King){
								tmpPath.add(new Point(from.getX(),from.getY()));
								setPath_pieceToKing(tmpPath);
								setCheckKing(true);
							}
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						}
						else
							return false; // if the piece is black then can go.
					}
				}
				//End First move
			}

			else{ // after first move
				// ** normal + WHITE
				if(checkPieces[from.getX()][from.getY()] != null && checkPieces[from.getX()][from.getY()].getColor() == Color.WHITE) {
					// one move
					if ((to.getX() == from.getX() - 1) && (to.getY() == from.getY())) {
						if (checkPieces[to.getX()][to.getY()] == null) {
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						} else
							return false; //check is there any piece or not
					}
					// attack diagonal move
					else if((to.getX() == from.getX()-1) && (to.getY() == from.getY()+1) // right up
							|| (to.getX() == from.getX()-1) && (to.getY() == from.getY()-1)){ // left up
						if (checkPieces[to.getX()][to.getY()] != null && checkPieces[to.getX()][to.getY()].getColor() == Color.BLACK) {
							if(checkPieces[to.getX()][to.getY()] instanceof King){
								tmpPath.add(new Point(from.getX(),from.getY()));
								setPath_pieceToKing(tmpPath);
								setCheckKing(true);
							}
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						}
						//enpassant:
						else {
							if(to.getY() == from.getY()+1) { // right up move
								Piece p = checkPieces[from.getX()][from.getY()+1];
								if(p != null && p.getEnPassant() && p.getColor() == Color.BLACK ) {

									if (((Pawn) p).getOwnSequence() == Piece.MOVE_SEQUENCE){
										checkPieces[from.getX()][from.getY()+1] = null;
										cntMove++;
										Piece.MOVE_SEQUENCE++;
										ownSequence = Piece.MOVE_SEQUENCE;
//										System.out.println("ENPASSANT!!");
										return true;
									}

								}
								else{
//									System.out.println(Chess.countMovingWholePieces +" "+ cntMove);
									return false;
								}
							}
							else if(to.getY() == from.getY() -1) {	//left up move
								Piece p = checkPieces[from.getX()][from.getY()-1];
								if(p != null && p.getEnPassant() && p.getColor() == Color.BLACK ) {

									if (((Pawn) p).getOwnSequence() == Piece.MOVE_SEQUENCE){
										checkPieces[from.getX()][from.getY()-1] = null;
										cntMove++;
										Piece.MOVE_SEQUENCE++;
										ownSequence = Piece.MOVE_SEQUENCE;
//										System.out.println("ENPASSANT!!");
										return true;
									}

								}
								else{
//									System.out.println(Chess.countMovingWholePieces +" "+ cntMove);
									return false;
								}
							}
							else
								return false; // if the piece is black then can go.
						}

					}
				}
				// ** normal + BLACK
				else {
					// one move
					if((to.getX() == from.getX()+1) && (to.getY() == from.getY())) {
						if (checkPieces[to.getX()][to.getY()] == null) {
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						} else
							return false; //check is there any piece or not
					}
					// attack diagonal move
					else if((to.getX() == from.getX()+1) && (to.getY() == from.getY()+1) // right down
							|| (to.getX() == from.getX()+1) && (to.getY() == from.getY()-1)){ // left down
						if (checkPieces[to.getX()][to.getY()] != null && checkPieces[to.getX()][to.getY()].getColor() == Color.WHITE) {
							if(checkPieces[to.getX()][to.getY()] instanceof King){
								tmpPath.add(new Point(from.getX(),from.getY()));
								setPath_pieceToKing(tmpPath);
								setCheckKing(true);
							}
							cntMove++;
							Piece.MOVE_SEQUENCE++;
							ownSequence = Piece.MOVE_SEQUENCE;
							return true;
						}
						else{
							if(to.getY() == from.getY()+1) { // right up move
								Piece p = checkPieces[from.getX()][from.getY()+1];
								if(p != null && p.getEnPassant() && p.getColor() == Color.WHITE ) {

									if (((Pawn) p).getOwnSequence() == Piece.MOVE_SEQUENCE){
										checkPieces[from.getX()][from.getY()+1] = null;
										cntMove++;
										Piece.MOVE_SEQUENCE++;
										ownSequence = Piece.MOVE_SEQUENCE;
//										System.out.println("ENPASSANT!!");
										return true;
									}
								}
								else{
//									System.out.println("1");
//									System.out.println(Piece.MOVE_SEQUENCE +" "+ ((Pawn) p).getOwnSequence());
									return false;
								}
							}
							else if(to.getY() == from.getY() -1) {	//left up move
								Piece p = checkPieces[from.getX()][from.getY()-1];
								if(p != null && p.getEnPassant() && p.getColor() == Color.WHITE ) {

									if (((Pawn) p).getOwnSequence() == Piece.MOVE_SEQUENCE){
										checkPieces[from.getX()][from.getY()-1] = null;
										cntMove++;
										Piece.MOVE_SEQUENCE++;
										ownSequence = Piece.MOVE_SEQUENCE;
//										System.out.println("ENPASSANT!!");
										return true;
									}
								}
								else{
//									System.out.println(Chess.countMovingWholePieces +" "+ cntMove);
									return false;
								}
							}
							else
								return false; // if the piece is black then can go.
						}

					}
				}
			}
			return false;
		}
		return false;
	}
}
