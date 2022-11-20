package com.example.lab1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class HelloActivity extends Activity {
    int r = 1;
    int g = 135;
    int b = 134;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        FrameLayout frameLayout = findViewById(R.id.frame);
        frameLayout.setBackgroundColor(Color.rgb(r, g, b));
        button1.setBackgroundColor(Color.rgb(r, g, b));
        button2.setBackgroundColor(Color.rgb(r, g, b));


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setText("НАЖАТО!");
                countClicks();
                r += 15;
                b -= 15;
                g -= 25;
                if(r >= 255){
                    r = 75;
                }
                if(b <= 0){
                    b = 0;
                }
                if(g <= 0){
                    g = 0;
                }
                frameLayout.setBackgroundColor(Color.rgb(r, g, b));
                button1.setBackgroundColor(Color.rgb(255-r, 255-g, 255-b));
                button2.setBackgroundColor(Color.rgb(255-r, 255-g, 255-b));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setText("НАЖАТО!");
                countClicks();
                r -= 15;
                b += 15;
                g -= 25;
                if(r <= 0){
                    r = 0;
                }
                if(b >= 255){
                    b = 75;
                }
                if(g <= 0){
                    g = 0;
                }
                frameLayout.setBackgroundColor(Color.rgb(r, g, b));
                button1.setBackgroundColor(Color.rgb(255-r, 255-g, 255-b));
                button2.setBackgroundColor(Color.rgb(255-r, 255-g, 255-b));
            }
        });
    }

    protected void countClicks() {
        TextView tv = (TextView)findViewById(R.id.textView1);
        int counter = Integer.parseInt(tv.getText().toString()) + 1;
        tv.setText(String.valueOf(counter));
    }
}