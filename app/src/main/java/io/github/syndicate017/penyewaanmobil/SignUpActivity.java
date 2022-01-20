package io.github.syndicate017.penyewaanmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    TextInputLayout regFullName, regUserName, regEmail, regPhoneNumber, regPassword;
    Button regBtn, regToLoginBtn;

    //Variables for FireBase Database usage
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks to all xml elements in activity_sign_up.xml
        regFullName = findViewById(R.id.reg_fullname);
        regUserName = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNumber = findViewById(R.id.reg_phonenumber);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        //Save data in FireBase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check the requirements
                if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePhoneNumber() | !validatePassword()) {
                    return;
                }

                //Use the link on getInstance from FireBase Database
                rootNode = FirebaseDatabase.getInstance("https://penyewaan-mobil-default-rtdb.asia-southeast1.firebasedatabase.app");
                reference = rootNode.getReference("users");

                //Get all the values
                String fullName = regFullName.getEditText().getText().toString();
                String userName = regUserName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNumber = regPhoneNumber.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                Intent intent = new Intent(getApplicationContext(), VerifyPhoneNumberActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);

                //Storing data in firebase
                //UserHelperClass helperClass = new UserHelperClass(fullName, userName, email, phoneNumber, password);
                //reference.child(userName).setValue(helperClass);
//
//                Toast toast = Toast.makeText(SignUpActivity.this, "Your data was successfully created!", Toast.LENGTH_LONG);
//                toast.show();
//
//                Snackbar.make(view, "Your Account has been created successfully!", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();

//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                finish();
            }
        }); //Register Button method end
    } //onCreate method end

    private Boolean validateFullName() {
        String val = regFullName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regFullName.setError("Field cannot be empty!");
            return false;
        } else {
            regFullName.setError(null);
            regFullName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
//                "(?=\\S+$)";

        if (val.isEmpty()) {
            regUserName.setError("Field cannot be empty!");
            return false;
        } else if (val.length() >= 15) {
            regUserName.setError("Username too long!");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUserName.setError("White Spaces are not allowed!");
            return false;
        } else {
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty!");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address!");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = regPhoneNumber.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNumber.setError("Field cannot be empty!");
            return false;
        } else {
            regPhoneNumber.setError(null);
            regPhoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty!");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak!");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
}