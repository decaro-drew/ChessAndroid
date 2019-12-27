package com.android.chess;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.piece.Piece;

public class IconAdapter extends BaseAdapter {
    private Context context;
//    int[][] board;
    Piece[][] origin_board;
    int rowPosition, columnPosition, count;
    boolean color;

    public IconAdapter(Context c, Piece[][] content) {
        context = c;
        count = 0;
        origin_board = new Piece[content.length][content[0].length];
//        board = new int[content.length][content[0].length];
        for (int i = 0; i < origin_board.length; i++)
        {
            for (int j = 0; j < origin_board[i].length; j++)
            {
                origin_board[i][j] = content[i][j];
                count++;
            }
        }
        rowPosition = 0;
        columnPosition = 0;
    }

    public void changeBoard(Piece[][] content){
        for (int i = 0; i < origin_board.length; i++)
        {
            for (int j = 0; j < origin_board[i].length; j++)
            {
                origin_board[i][j] = content[i][j];
            }
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return count;
    }

    public int getRowCount() {
        return origin_board.length;
    }

    public int getColumnCount() {
        return origin_board[0].length;
    }

    public Object getItem(int rowNum, int columnNum) {
        return origin_board[rowNum][columnNum];
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(93, 93));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else {
            imageView = (ImageView) convertView;
        }

        columnPosition = position % origin_board[0].length;
        rowPosition = (position - columnPosition) / origin_board[0].length;

        if(origin_board[rowPosition][columnPosition] == null){
            imageView.setImageResource(0);
        }
        else {
            switch (origin_board[rowPosition][columnPosition].getName()) {
                case "bR":
                    imageView.setImageResource(R.drawable.brook);
                    break;
                case "bN":
                    imageView.setImageResource(R.drawable.bknight);
                    break;
                case "bB":
                    imageView.setImageResource(R.drawable.black_bishop);
                    break;
                case "bQ":
                    imageView.setImageResource(R.drawable.black_queen);
                    break;
                case "bK":
                    imageView.setImageResource(R.drawable.black_king);
                    break;
            }

            switch (origin_board[rowPosition][columnPosition].getName()) {
                case "wR":
                    imageView.setImageResource(R.drawable.wrook);
                    break;
                case "wN":
                    imageView.setImageResource(R.drawable.wknight);
                    break;
                case "wB":
                    imageView.setImageResource(R.drawable.wbishop);
                    break;
                case "wQ":
                    imageView.setImageResource(R.drawable.white_queen);
                    break;
                case "wK":
                    imageView.setImageResource(R.drawable.white_king);
                    break;
            }

            if (origin_board[rowPosition][columnPosition].getName().equals("bP")) {
                imageView.setImageResource(R.drawable.bpawn);
            }

            if (origin_board[rowPosition][columnPosition].getName().equals("wP")) {
                imageView.setImageResource(R.drawable.wpawn);
            }

            if (origin_board[rowPosition][columnPosition].getName()==null) {
                imageView.setImageResource(0);
            }
        }
        return imageView;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
