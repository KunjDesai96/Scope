package com.example.kunj.scope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView icon;
    TextView tagline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icon = (ImageView) findViewById(R.id.icon);
        tagline = (TextView) findViewById(R.id.tagline);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,HomePage.class);
                startActivity(intent);
                finish();
            }
        },3000);

        icon.animate().translationYBy(-200f).setDuration(2000);
        tagline.animate().alpha(1f).setDuration(3500);

    }
}
