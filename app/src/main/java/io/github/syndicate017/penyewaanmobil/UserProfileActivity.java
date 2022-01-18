package io.github.syndicate017.penyewaanmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserProfileActivity extends AppCompatActivity {

    //Describe variables
    TextInputLayout fullName, email, phoneNumber, password;
    TextView fullNameLabel, userNameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Hooks the View into the variables
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        fullNameLabel = findViewById(R.id.fullNameLabel);
        userNameLabel = findViewById(R.id.userNameLabel);

        //Show all data
        showAllUserData();
    }

    private void showAllUserData() {
        //Get the data from Intent which stored the data from LoginActivity
        Intent intent = getIntent();

        String user_fullName = intent.getStringExtra("fullName");
        String user_userName = intent.getStringExtra("userName");
        String user_email = intent.getStringExtra("email");
        String user_phoneNumber = intent.getStringExtra("phoneNumber");
        String user_password = intent.getStringExtra("password");

        //Set the data from Intent which stored the data from LoginActivity before
        fullNameLabel.setText(user_fullName);
        userNameLabel.setText(user_userName);

        fullName.getEditText().setText(user_fullName);
        email.getEditText().setText(user_email);
        phoneNumber.getEditText().setText(user_phoneNumber);
        password.getEditText().setText(user_password);
    }
}