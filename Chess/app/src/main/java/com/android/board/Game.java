package com.android.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Game implements Serializable {

    private static Game instance;

    public ArrayList<Move> gameSaver = new ArrayList<>();

    private Game(){}

    public static Game getInstance(){
        if(instance == null){
            instance = new Game();
        }
        return instance;
    }

    public static Comparator<Move> nameComparator = new Comparator<Move>() {
        @Override
        public int compare(Move m1, Move m2) {
            return (int) (m1.getTitle().compareTo(m2.getTitle()));
        }
    };
    public static Comparator<Move> dateComparator = new Comparator<Move>() {
        @Override
        public int compare(Move m1, Move m2) {
            return (int) (m1.getDate().compareTo(m2.getDate()));
        }
    };

    public void addGameSaver(Move gameMovement){
        gameSaver.add(gameMovement);
    }

    public void setGameSaver(ArrayList<Move> gameSaver){
        this.gameSaver = gameSaver;
    }

    public ArrayList<Move> getGameSaver(){
        return gameSaver;
    }

    public Move getSpecificMoveByTitleName(String title){
        Move get = null;
        if(title != null){
            for(Move v: gameSaver){
                if(v.getTitle().equals(title)){
                    get = v;
                }
            }
        }
        return get;
    }

    public boolean duplicateCheck(String title){
        boolean isDuplicate = false;

        if(title != null){
            for(Move v: gameSaver){
                if(v.getTitle() != null && v.getTitle().equals(title)){
//                    System.out.println("same");
                    isDuplicate = true;
                    break;
                }
                else {
//                    System.out.println("not same");
                    isDuplicate = false;
                }
            }
        }
        return isDuplicate;
    }

}
