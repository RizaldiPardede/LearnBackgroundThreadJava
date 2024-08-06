package com.example.backgroundtrade;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    TextView angka;
    Button btnstart,btnstop;
    boolean play = false;

    AngkaTask asyn1;
    ExecutorService exe1,exepool;
    int[]rangeangka = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        angka = findViewById(R.id.angka);
        btnstart = findViewById(R.id.play);
        btnstop = findViewById(R.id.stop);

        exe1 = Executors.newSingleThreadExecutor();
        exepool=Executors.newFixedThreadPool(1);
        asyn1 = new AngkaTask(angka);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == btnstart.getId()){
                        asyn1._play=true;
                        exepool.execute(asyn1);
                        btnstart.setText("STOP");
                        play = !play;
                }
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == btnstop.getId()){
                        asyn1._play=false;
                        btnstart.setText("PLAY");
                        play=!play;

                }
            }
        });


    }




    class AngkaTask implements Runnable{
        TextView Angka;
        Random _random = new Random();
        public  boolean _play =true;
        int i;

        public AngkaTask(TextView angka) {
            this.Angka = angka;
            i=0;
            _play=true;
        }

        @Override
        public void run() {
            while (_play){
                i  = _random.nextInt(10);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Angka.setText(String.valueOf(rangeangka[i]));
                    }
                });
            }
            try {
                Thread.sleep(_random.nextInt(500));
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }


        }
    }
}