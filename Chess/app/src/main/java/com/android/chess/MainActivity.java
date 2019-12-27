package com.android.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_start;
    Button loadGameBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Test newTest = new Test("e1","e2");

        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = "e3";

                Intent intent = new Intent(MainActivity.this,BoardActivity.class); //just this means onClickListener so explicit exact class name.
                intent.putExtra("current",current); // key and value

                startActivity(intent);
            }
        });

        loadGameBT = findViewById(R.id.loadGameBT);
        loadGameBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,LoadGameActivity.class); //just this means onClickListener so explicit exact class name.

                startActivity(intent);
            }
        });
    }
}
