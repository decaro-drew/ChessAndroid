package com.android.board;

import com.android.piece.Point;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Move implements Serializable {
    private static final long serialVersionUID = 12345L;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat ("yyyy-MM-dd");

    private String status; // white/black win or draw
    private String title;
    private Date date;

    private String promotion;

    private ArrayList<Point> froms = new ArrayList<>();
    private ArrayList<Point> tos = new ArrayList<>();

    public Move(){}
    public Move(String status, String title, Date date){
        this.status = status;
        this.title = title;
        this.date = date;
    }

    public ArrayList<Point> getFroms(){
        return froms;
    }

    public ArrayList<Point> getTos(){
        return tos;
    }

    public void addFroms(Point pt){
        froms.add(pt);
    }

    public void addTos(Point pt){
        tos.add(pt);
    }

    public void deleteFroms(int idx){
        froms.remove(idx);
    }

    public void deleteTos(int idx){
        tos.remove(idx);
    }

    public int fromsLength(){
        if(froms == null){
            return 0;
        }
        else
            return froms.size();
    }

    public int tosLength(){
        if(tos == null)
            return 0;
        else
            return tos.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateToString() {
        return FORMAT.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate(){
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
}