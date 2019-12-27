package com.android.chess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.board.Game;
import com.android.board.Move;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    Game game;

    Button button_home;
    Button exitBT;
    Button saveBT;

    TextView resultTF;

    Move newMove;

    String saveStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        game = Game.getInstance();

        InitializeView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String get = bundle.getString("draw");
        String win = bundle.getString("win");
//        System.out.println(game.gameSaver.size());
        newMove = game.gameSaver.get(game.gameSaver.size()-1);

        if(get != null)
            saveStatus = get;
        else
            saveStatus = win;


        resultTF = findViewById(R.id.resultTF);
        if(get != null){
            resultTF.setText(get);
        }
        else if(win != null){
            resultTF.setText(win);
        }

        button_home = findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String current = "e3";

                Intent intent = new Intent(ResultActivity.this,MainActivity.class); //just this means onClickListener so explicit exact class name.
                startActivity(intent);
            }
        });
    }

    public void InitializeView() {
        exitBT = findViewById(R.id.exitBT);
        saveBT = findViewById(R.id.saveBT);
    }

    public void exitBT_handler(View v) {
        if (v.getId() == R.id.exitBT) {
            Toast.makeText(getApplicationContext(), "exit button clicked", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    String name = null;
    public void saveBT_handler(View v) {
        if (v.getId() == R.id.saveBT) {
            Toast.makeText(getApplicationContext(), "save button clicked", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Name");
            alert.setMessage("Message");

            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    name = value.toString();

                    if(!game.duplicateCheck(name)){
//                        System.out.println("true");
                        newMove.setTitle(name);
                        newMove.setStatus(saveStatus);
                        newMove.setDate(new Date());
                        serialize();

                        Intent intent = new Intent(ResultActivity.this, LoadGameActivity.class); //just this means onClickListener so explicit exact class name.
                        intent.putExtra("title",name); // key and value

                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Duplicate title name.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            alert.show();



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