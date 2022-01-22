package io.github.syndicate017.penyewaanmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {

    //Describe variables
    TextInputLayout fullName, email, phoneNumber, password;
    TextView fullNameLabel, userNameLabel;
    Button btnUpdateProfile;

    String _FULLNAME, _USERNAME, _EMAIL, _PHONENUMBER, _PASSWORD;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Database Firebase accessor
        reference = FirebaseDatabase
                .getInstance("https://penyewaan-mobil-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");

        //Hooks the View into the variables
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        fullNameLabel = findViewById(R.id.fullNameLabel);
        userNameLabel = findViewById(R.id.userNameLabel);

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        //Show all data
        showAllUserData();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFullNameChanged() || isPasswordChanged()) {
                    Toast.makeText(getApplicationContext(), "Your data has been updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Your data still same and cannot be update!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(password.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD = password.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isFullNameChanged() {
        if (!_FULLNAME.equals(fullName.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("fullName").setValue(fullName.getEditText().getText().toString());
            _FULLNAME = fullName.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private void showAllUserData() {
        //Get the data from Intent which stored the data from LoginActivity
        _FULLNAME = getIntent().getStringExtra("fullName");
        _USERNAME = getIntent().getStringExtra("userName");
        _EMAIL = getIntent().getStringExtra("email");
        _PHONENUMBER = getIntent().getStringExtra("phoneNumber");
        _PASSWORD = getIntent().getStringExtra("password");

        //Set the data from Intent which stored the data from LoginActivity before
        fullNameLabel.setText(_FULLNAME);
        userNameLabel.setText(_USERNAME);

        fullName.getEditText().setText(_FULLNAME);
        email.getEditText().setText(_EMAIL);
        phoneNumber.getEditText().setText(_PHONENUMBER);
        password.getEditText().setText(_PASSWORD);
    }
}