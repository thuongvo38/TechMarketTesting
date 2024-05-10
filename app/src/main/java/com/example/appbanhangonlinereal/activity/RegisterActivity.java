package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    TextView txt_login;
    EditText email, username ,password, repass, mobile;
    Button btnRegister;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSale apiSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }
    private void initControl() {
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String str_email = email.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        String str_pass = password.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();



//        if ( str_username.length()==0 ||str_email.length()==0 || str_pass.length()==0 ||str_repass.length()==0 ||str_mobile.length()==0)
//        {
//            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            if (TextUtils.isEmpty(str_email) || !Patterns.EMAIL_ADDRESS.matcher(str_email).matches() || !str_email.endsWith("@gmail.com")) {
//                Toast.makeText(this, "Enter a valid Gmail address", Toast.LENGTH_SHORT).show();
//            }
//            else if (!isValidUser(str_username)) {
//                Toast.makeText(this, "Username must not be more than 12 characters,not begin with a number or special character and no blanks inside", Toast.LENGTH_SHORT).show();
//            }
//            else if (!isValidPass(str_pass)) {
//                Toast.makeText(this, "Password must contain at least 8 charaters,having letter,digit,at least 1 capital letter and special symbol", Toast.LENGTH_SHORT).show();
//            }
//            else if ( str_pass.compareTo(str_repass)==0){
//                Toast.makeText(this, "Password re-entered does not match", Toast.LENGTH_SHORT).show();
//            }
//            else if (TextUtils.isEmpty(str_mobile)) {
//                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                compositeDisposable.add(apiSale.register(str_email, str_pass, str_username, str_mobile)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                userModel -> {
//                                    if(userModel.isSuccess()){
//                                        Utils.user_current.setEmail(str_email);
//                                        Utils.user_current.setPass(str_pass);
//                                        Intent intent_register = new Intent(getApplicationContext(), LoginActivity.class);
//                                        startActivity(intent_register);
//                                        finish();
//                                    }else {
//                                        Toast.makeText(this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                        ));
//            }
//        }
//


///----------------------------------------PART WE TESTING-----------------------------------------

        // Email validation
        if (str_email.length()==0 || !Patterns.EMAIL_ADDRESS.matcher(str_email).matches() || !str_email.endsWith("@gmail.com")) {
            //Màn hình sẽ hiện thị
            Toast.makeText(this, "Enter a valid Gmail address", Toast.LENGTH_SHORT).show();
        }
        // Username validation
        else if (!isValidUser(str_username)|| str_username.length()==0 ) {
            Toast.makeText(this, "Username must not be more than 12 characters,not begin with a number or special character and no blanks inside", Toast.LENGTH_SHORT).show();
        }
        // Password validation
        else if (!isValidPass(str_pass) ) {
            Toast.makeText(this, "Password must contain at least 8 charaters,having letter,digit,at least 1 capital letter and special symbol", Toast.LENGTH_SHORT).show();
        }
        // Re-entered password validation
        else if (TextUtils.isEmpty(str_repass) || !str_pass.equals(str_repass)) {
            Toast.makeText(this, "Password re-entered does not match", Toast.LENGTH_SHORT).show();
        }
        // Mobile validation (assuming mobile should not be empty)
        else if (isValidPhone(str_mobile) == false) {
            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
        }
        // All validations passed, proceed with registration
        else {
            compositeDisposable.add(apiSale.register(str_email, str_pass, str_username, str_mobile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){
                                    Utils.user_current.setEmail(str_email);
                                    Utils.user_current.setPass(str_pass);
                                    Intent intent_register = new Intent(getApplicationContext(), LoginActivity.class);
                                    Toast.makeText(this, "Regist successfully !", Toast.LENGTH_SHORT).show();
                                    startActivity(intent_register);
                                    finish();
                                }else {
                                    Toast.makeText(this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ));
        }
    }

    //-----------------------------------------------END------------------------------------------------------------------

    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        txt_login = findViewById(R.id.txt_login);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_pass);
        repass = findViewById(R.id.register_repass);
        mobile = findViewById(R.id.register_mobile);
        username = findViewById(R.id.register_username);
        btnRegister = findViewById(R.id.btnRegister);
    }



    ///----------------------------------------FUNCTON WE TESTING-----------------------------------------

    public static boolean isValidUser(String userhere)
    {
        // Check if the length is not more than 12 characters
        if (userhere.length() > 12) {
            return false;
        }

        // Check if the username does not begin with a number or special character
        char firstChar = userhere.charAt(0);
        if (Character.isDigit(firstChar) || !Character.isLetterOrDigit(firstChar)) {
            return false;
        }

        // Check if there are no blanks inside the username
        if (userhere.contains(" ")) {
            return false;
        }

        // If all conditions are met, the username is valid
        return true;
    }
    protected static boolean isValidPass( String passwordhere)
    {
        // Check if the length is at least 8 characters
        if (passwordhere.length() < 8) {
            return false;
        }

        // Check if the password contains at least one letter
        if (!passwordhere.matches(".*[a-zA-Z]+.*")) {
            return false;
        }

        // Check if the password contains at least one digit
        if (!passwordhere.matches(".*\\d+.*")) {
            return false;
        }

        // Check if the password contains at least one capital letter
        if (!passwordhere.matches(".*[A-Z]+.*")) {
            return false;
        }

        // Check if the password contains at least one special symbol
        if (!passwordhere.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*")) {
            return false;
        }

        // If all conditions are met, the password is valid
        return true;
    }
    public static boolean isValidPhone (String phone ){
        // Check if the phone number is not empty
        if (phone.length()==0) {
            return false;
        }

        // Check if the phone number contains only digits
        if (!phone.matches("\\d+")) {
            return false;
        }

        // Check if the phone number does not contain any special characters
        if (!phone.matches("[0-9]+")) {
            return false;
        }

        // Check if the phone number has exactly 10 digits
        if (phone.length() != 10) {
            return false;
        }

        // Check if the phone number starts with the digit 0
        if (phone.charAt(0) != '0') {
            return false;
        }

        // If all conditions are met, the phone number is valid
        return true;
    }

    //-----------------------------------------------END------------------------------------------------------------------



    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}
//chao ban
