package com.android.piece;

import java.util.ArrayList;

/**
 * This is class Piece which is abstract class. All pieces that include King, Queen, Bishop, Knight, and Rook extends this class.
 * 
 * @author Jaehyun
 * @author Drew
 */
public abstract class Piece {
	/**
	 * Static type of field that count all movements
	 */
	public static int MOVE_SEQUENCE = 0;
	public int number;

	/**
	 * Piece name
	 */
	private String name;
	/**
	 * Piece color
	 */
	private Color color;
	/**
	 * check moved or not
	 */
	private boolean isMove = false;
	/**
	 * check if king is checked or not
	 */
	private boolean isCheckKing = false;
	/**
	 * This field is for enpassant
	 */
	private boolean enPassant;

	private ArrayList<Point> path_pieceToKing;

	/**
	 * This is constructor that include name and color
	 * 
	 * @param name getting name
	 * @param color getting color
	 */
	public Piece(String name, Color color){
		this.name = name;
		this.color = color;
	}

	public Piece(String name, Color color, int number){
		this.name = name;
		this.color = color;
		this.number = number;
	}
	/**
	 * This method is for enpassant (getter).
	 * 
	 * @return return boolean type of empassant
	 */
	public boolean getEnPassant() {
		return enPassant;
	}
	
	/**
	 * This method is for enpassant (setter). 
	 * 
	 * @param enPassant getting enpassant through parameter
	 */
	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}
	
	/**
	 * This method is for setting name
	 * 
	 * @param name getting name and set name field
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method is for getting name
	 * 
	 * @return return name field.
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * This is for getting color
	 * 
	 * @return return color 
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * This is for setting color
	 * 
	 * @param color get color through parameter
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * This is for checking move or not
	 * 
	 * @return return boolean type of isMove field.
	 */
	public boolean isMove() {
		return isMove;
	}

	/**
	 * This method is for setting move.
	 * 
	 * @param isMove get boolean type isMove to set isMove field.
	 */
	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}

	/**
	 * This method is for returning string.
	 * 
	 * @return return name.
	 */
	public String toString(){
		return name+" ";
	}

	/**
	 * This is core method to move all pieces at implementing each class.
	 * This method check their own valid movement. If movement is available, then return true else then return false.
	 * 
	 * @param from get original position
	 * @param to get destination position
	 * @param checkPieces get board
	 * @return return after check if movement is available, then return true else then return false.
	 */
	abstract public boolean valid_move(Point from, Point to, Piece[][] checkPieces); // through parameter need to get row and col points.

	/**
	 * This method is for King is checked or not
	 * 
	 * @return return boolean type field isCheckKing.
	 */
	public boolean isCheckKing() {
		return isCheckKing;
	}

	/**
	 * This method is for setting that King is checed or not.
	 * 
	 * @param checkKing get boolean type of variable to set isCheckKing field.
	 */
	public void setCheckKing(boolean checkKing) {
		isCheckKing = checkKing;
	}

	/**
	 * This method is for getting paths from another side piece on which checks to King.
	 * 
	 * @return return ArrayList type of path
	 */
	public ArrayList<Point> getPath_pieceToKing() {
		return path_pieceToKing;
	}

	/**
	 * This method is for setting path from another side piece on which checks to King. 
	 * 
	 * @param path_pieceToKing get paths through parameter to set field.
	 */
	public void setPath_pieceToKing(ArrayList<Point> path_pieceToKing) {
		this.path_pieceToKing = path_pieceToKing;
	}
	// also needs Piece arrays to check btw current postion and want to move position
}
