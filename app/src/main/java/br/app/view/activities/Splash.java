package br.app.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private ImageView img;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();

        animacao(txt, -320f);
        animacao(img, -320f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    public void animacao(View view, float dist){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationY", dist);
        animation.setDuration(1000);
        animation.start();
    }

    public void init(){
        txt = findViewById(R.id.txt_splash);
        img = findViewById(R.id.img_splash);
    }
}