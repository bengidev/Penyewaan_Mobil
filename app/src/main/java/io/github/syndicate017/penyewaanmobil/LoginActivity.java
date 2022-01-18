package io.github.syndicate017.penyewaanmobil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ImageView logo_image;
    TextView logo_name, slogan_name;
    Button login_button, callSignUp;
    TextInputLayout username, password;

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
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //This code is for move to the another page
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

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //This code is for validate the field
                loginUser();
            }
        });
    }

    public void loginUser() {
        //Validate login info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        //Variables
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        //Database accessor
        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://penyewaan-mobil-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");

        //Check the username validation
        Query checkUser = reference.orderByChild("userName").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {
                        username.setError(null);
                        username.setErrorEnabled(false);


                        String fullNameFromDB = snapshot.child(userEnteredUsername).child("fullName").getValue(String.class);
                        String userNameFromDB = snapshot.child(userEnteredUsername).child("userName").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String phoneNumberFromDB = snapshot.child(userEnteredUsername).child("phoneNumber").getValue(String.class);

                        //Put the data from DB to the intent
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);

                        intent.putExtra("fullName", fullNameFromDB);
                        intent.putExtra("userName", userNameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNumber", phoneNumberFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    } else {
                        password.setError("Wrong Password!");
                        password.requestFocus();
                    }
                } else {
                    username.setError("No such User exist!");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}