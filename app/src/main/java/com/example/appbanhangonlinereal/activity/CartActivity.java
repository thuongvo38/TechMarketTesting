package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.adapter.CartAdapter;
import com.example.appbanhangonlinereal.model.EventBus.totalCostEvent;
import com.example.appbanhangonlinereal.model.GioHang;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    TextView txtEmpty, cost;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnBuy;
    long totalCost = 0;
    CartAdapter cartAdapter;
    List<GioHang> cartList;
    List<SanPhamMoi> sanPhamMoiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initControl();
        if (Utils.manggiohang.size() == 0) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(cartAdapter);
        }
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPay = new Intent(getApplicationContext(), PaymentActivity.class);
                intentPay.putExtra("cost", calculateTotalCost(Utils.manggiohang));
                startActivity(intentPay);
            }
        });
    }

    private void costOfCart() {
        totalCost = 0;
         for(int i = 0; i < Utils.manggiohang.size(); i++){
             totalCost = totalCost + (Utils.manggiohang.get(i).getGiasp() * Utils.manggiohang.get(i).getSoluong());
         }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###" );
         cost.setText(decimalFormat.format(totalCost));
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size() == 0){
            txtEmpty.setVisibility(View.VISIBLE);
        }else {
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(cartAdapter);
        }
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPay = new Intent(getApplicationContext(), PaymentActivity.class);
                intentPay.putExtra("cost", totalCost);
                startActivity(intentPay);
            }
        });
    }

    private void initView() {
        txtEmpty = findViewById(R.id.txtEmptyCart);
        toolbar = findViewById(R.id.toolbar_cart);
        recyclerView = findViewById(R.id.recycleview_cart);
        cost = findViewById(R.id.txtCost);
        btnBuy = findViewById(R.id.btnBuy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventCost(totalCostEvent event){
        if(event != null){
            costOfCart();
        }
    }

    ///----------------------------------------PART WE TESTING-----------------------------------------

    public static long calculateTotalCost(List<GioHang> gioHangList) {
        long totalCost = 0;
        for (int i = 0; i < gioHangList.size(); i++) {
            totalCost += (gioHangList.get(i).getGiasp() * gioHangList.get(i).getSoluong());
        }
        return totalCost;
    }
}

//-----------------------------------------------END------------------------------------------------------------------