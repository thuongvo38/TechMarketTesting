package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.model.GioHang;
import com.example.appbanhangonlinereal.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PaymentActivity extends AppCompatActivity {
    AppCompatButton btnPay;
    EditText address;
    TextView email, phone, cost;
    Toolbar toolbar;
    long totalCost;
    int totalItem;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSale apiSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        int itemCount = countItem(Utils.manggiohang);
        initControl(itemCount);
    }

    ///----------------------------------------PART WE TESTING-----------------------------------------

    //Đếm số lượng sản phẩm trong đơn hàng
    protected static int countItem(List<GioHang> gioHangList) {
        int totalItem = 0;
        for (GioHang gioHang : gioHangList) {
            totalItem += gioHang.getSoluong();
        }
        return totalItem;
    }
    //-----------------------------------------------END------------------------------------------------------------------

    private void initControl(int itemCount) {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        DecimalFormat decimalFormat = new DecimalFormat("###,###,###" );
        totalCost = getIntent().getLongExtra("cost", 0);
        cost.setText(decimalFormat.format(totalCost) + "$");
        email.setText(Utils.user_current.getEmail());
        phone.setText(Utils.user_current.getMobile());


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_address = address.getText().toString().trim();
                if (TextUtils.isEmpty(str_address)) {
                    Toast.makeText(PaymentActivity.this, "Address can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    // post data
                    String str_email = Utils.user_current.getEmail();
                    String str_phone = Utils.user_current.getMobile();
                    int id = Utils.user_current.getId();

                    compositeDisposable.add(apiSale.createOrder(str_email, str_phone, String.valueOf(totalCost), id, str_address, itemCount, new Gson().toJson(Utils.manggiohang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()) {
                                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            Utils.manggiohang.clear(); // xóa sản phẩm trong giỏ khi đặt hàng thành công
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        cost = findViewById(R.id.cost_pay);
        email = findViewById(R.id.email_pay);
        phone = findViewById(R.id.phone_pay);
        btnPay = findViewById(R.id.btnPay);
        address = findViewById(R.id.address_pay);
        toolbar = findViewById(R.id.toolbar_pay);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}