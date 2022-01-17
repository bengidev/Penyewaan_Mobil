package io.github.syndicate017.penyewaanmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    //For transition to the next page
    private static final int SPLASH_SCREEN_TIME = 3600;

    ImageView logoCar;
    TextView logoText, sloganText, copyrightText;
    Animation topAnim, bottomAnim;
    LottieAnimationView logoAnimLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This line for hide the statusbar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks for animations
        logoCar = findViewById(R.id.logoCar);
        logoText = findViewById(R.id.logoText);
        sloganText = findViewById(R.id.sloganText);
        copyrightText = findViewById(R.id.copyrightText);
        logoAnimLottie = findViewById(R.id.logoAnimLottie);

        //Apply the animations
        logoCar.setAnimation(topAnim);
        logoText.setAnimation(bottomAnim);
        sloganText.setAnimation(bottomAnim);
        copyrightText.setAnimation(bottomAnim);
        logoAnimLottie.setAnimation(bottomAnim);

        //Animations from lottie
        logoAnimLottie = findViewById(R.id.logoAnimLottie);

        //For fix the animation from android version KitKat and Above
        logoAnimLottie.enableMergePathsForKitKatAndAbove(true);
        logoAnimLottie.animate().translationX(-3000).setDuration(500).setStartDelay(3000);

        //For set the transition per pages
        //Using android.os Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                //This code is for animate/transition the logo and text
                //The object View from the image/text
                //The object String from the transitionName in the xml layout
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logoCar, "logo_car_transition");
                pairs[1] = new Pair<View, String>(logoText, "logo_text_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());

//                startActivity(intent);
//                finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}