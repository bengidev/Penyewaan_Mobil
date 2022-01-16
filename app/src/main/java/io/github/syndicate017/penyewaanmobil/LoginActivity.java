package io.github.syndicate017.penyewaanmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    ImageView logo_image;
    TextView logo_name, slogan_name;
    Button login_button, callSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        logo_image = findViewById(R.id.logo_image);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        login_button = findViewById(R.id.login_button);
        callSignUp = findViewById(R.id.signup_screen);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

                //This code is for animate/transition the logo and text
                //The object View from the image/text
                //The object String from the transitionName in the xml layout
                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(logo_image, "logo_car_transition");
                pairs[1] = new Pair<View, String>(logo_name, "logo_text_transition");
                pairs[2] = new Pair<View, String>(slogan_name, "sign_text_transition");
                pairs[3] = new Pair<View, String>(login_button, "login_button_transition");
                pairs[4] = new Pair<View, String>(callSignUp, "sign_button_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());

//
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(intent);
            }
        });
    }
}