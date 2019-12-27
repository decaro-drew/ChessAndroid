package com.android.chess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.board.Game;
import com.android.board.Move;

import java.util.ArrayList;

public class LoadGameAdapter extends BaseAdapter {
    Game game;
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Move> sample;

    public LoadGameAdapter(Context context, ArrayList<Move> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        game = Game.getInstance();
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public Move getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_custom_listview, null);

        TextView title = view.findViewById(R.id.titleTV);
        TextView date = view.findViewById(R.id.dateTV);

        title.setText(sample.get(position).getTitle());
        date.setText(sample.get(position).getDateToString());

        return view;
    }
}
