package com.android.chess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.android.board.Game;
import com.android.board.Move;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class LoadGameActivity extends AppCompatActivity {

    ListView listView;
    Button dateSortBT;
    Button titleSortBT;

    Game game = Game.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context c = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadgame);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        InitializeView();
        deSerialize();


        listView = findViewById(R.id.listView);
        final LoadGameAdapter loadGameAdapter = new LoadGameAdapter(this,game.getGameSaver());
        listView.setAdapter(loadGameAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, final int position, long id){
                Toast.makeText(getApplicationContext(), loadGameAdapter.getItem(position).getTitle(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder ad = new AlertDialog.Builder(LoadGameActivity.this);

                ad.setTitle("Choose");

                ad.setPositiveButton("View Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoadGameActivity.this, LoadPlayGameActivity.class); //just this means onClickListener so explicit exact class name.
                        intent.putExtra("title",loadGameAdapter.getItem(position).getTitle()); // key and value
//                        System.out.println("title: "+ loadGameAdapter.getItem(position).getTitle());
                        startActivity(intent);
                    }
                });

                ad.setNegativeButton("Delete Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < game.getGameSaver().size(); i++) {
                            if (loadGameAdapter.getItem(position).getTitle().equals(game.getGameSaver().get(i).getTitle())) {
                                game.getGameSaver().remove(i);
                                break;
                            }
                        }
                        serialize();
                        listView = findViewById(R.id.listView);
                        final LoadGameAdapter loadGameAdapter = new LoadGameAdapter(c, game.getGameSaver());
                        listView.setAdapter(loadGameAdapter);

                        dialog.dismiss();
                    }
                });

                ad.show();
//                String title = loadGameAdapter.getItem(position).getTitle();
//                Intent intent = new Intent(LoadGameActivity.this, LoadPlayGameActivity.class); //just this means onClickListener so explicit exact class name.
//                intent.putExtra("title",title); // key and value

//                startActivity(intent);
            }
        });




    }

    public void InitializeView() {
        titleSortBT = findViewById(R.id.titleSortBT);
        dateSortBT = findViewById(R.id.dateSortBT);
    }

    public ArrayList<Move> sortByTitle(){
        Collections.sort(game.getGameSaver(), Game.nameComparator);
        return game.getGameSaver();
    }
    public ArrayList<Move> sortByDate(){
        Collections.sort(game.getGameSaver(), Game.dateComparator.reversed());
        return game.getGameSaver();
    }

    public void titleSortBT_handler(View v){
        if(v.getId() == R.id.titleSortBT){
            ArrayList<Move> sortedTitles = sortByTitle();
            game.setGameSaver(sortedTitles);
            listView = findViewById(R.id.listView);
            final LoadGameAdapter loadGameAdapter = new LoadGameAdapter(this, game.getGameSaver());
            listView.setAdapter(loadGameAdapter);
        }
    }

    public void dateSortBT_handler(View v){
        if(v.getId() == R.id.dateSortBT){
            ArrayList<Move> sortedDates = sortByDate();
            game.setGameSaver(sortedDates);
            listView = findViewById(R.id.listView);
            final LoadGameAdapter loadGameAdapter = new LoadGameAdapter(this, game.getGameSaver());
            listView.setAdapter(loadGameAdapter);
        }
    }

    private void deSerialize(){
        try {
            FileInputStream fis = openFileInput("serial.ser");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream in = new ObjectInputStream(bis);

            game.gameSaver = (ArrayList<Move>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serialize(){
        try {
            FileOutputStream fos = openFileOutput("serial.ser", Context.MODE_PRIVATE);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream out = new ObjectOutputStream(bos);

            out.writeObject(game.gameSaver);

            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}