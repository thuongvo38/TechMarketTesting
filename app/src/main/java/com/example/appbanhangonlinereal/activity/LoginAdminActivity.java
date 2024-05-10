package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginAdminActivity extends AppCompatActivity {
    TextView loginUser;
    EditText username, password;
    Button btnLogin;
    ApiSale apiSale;
    boolean isLogin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        initView();
        initControl();
    }

    private void initControl() {
        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUser = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentUser);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_username = username.getText().toString().trim();
                String str_pass = password.getText().toString().trim();
                if (TextUtils.isEmpty(str_username)) {
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_pass)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                }else{
                    Paper.book().write("username", str_username);
                    Paper.book().write("pass", str_pass);
                    login(str_username, str_pass);
                }
            }
        });
    }

    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        loginUser = findViewById(R.id.txt_loginuser);
        username = findViewById(R.id.edt_admin_username);
        password = findViewById(R.id.edt_admin_password);
        btnLogin = findViewById(R.id.btnLoginAdmin);
    }
    private void login(String uname, String pass) {
        compositeDisposable.add(apiSale.loginadmin(uname, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        adminModel -> {
                            if(adminModel.isSuccess()) {
                                isLogin = true;
                                Paper.book().write("islogin", isLogin);
                                Utils.admin = adminModel.getResult().get(0);
                                Paper.book().write("admin", adminModel.getResult().get(0)); //admin user info
                                Intent intent_login = new Intent(getApplicationContext(), HomeAdminActivity.class);
                                startActivity(intent_login);
                                finish();
                            }
                       },throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.admin.getUsername() != null && Utils.admin.getPass() != null){
            username.setText(Utils.admin.getUsername());
            password.setText(Utils.admin.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}