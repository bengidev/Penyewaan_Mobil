package io.github.syndicate017.penyewaanmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    TextInputLayout regFullName, regUserName, regEmail, regPhoneNumber, regPassword;
    Button regBtn, regToLoginBtn;

    //Variables for FIreBase Database usage
    FirebaseDatabase rootNode;
    DatabaseReference reference;

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
                //Use the link on getInstance from FireBase Database
                rootNode = FirebaseDatabase.getInstance("https://penyewaan-mobil-default-rtdb.asia-southeast1.firebasedatabase.app");
                reference = rootNode.getReference("users");

                //Get all the values
                String fullName = regFullName.getEditText().getText().toString();
                String userName = regUserName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNumber = regPhoneNumber.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(fullName, userName, email, phoneNumber, password);

                reference.child(phoneNumber).setValue(helperClass);
//
//                Toast toast = Toast.makeText(SignUpActivity.this, "Your data was successfully created!", Toast.LENGTH_LONG);
//                toast.show();

                Snackbar.make(view, "Your data was successfully created!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }); //Register Button method end
    } //onCreate method end
}