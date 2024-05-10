package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.model.GioHang;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.Objects;

public class DetailProductActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnAdd;
    ImageView imHinhAnh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }
    private void initControl(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intentCart);
            }
        });
    }

    private void addCart() {
        if(Utils.manggiohang.size() > 0){
            boolean flag = false;
            int countProduct = Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                if(Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(countProduct + Utils.manggiohang.get(i).getSoluong());
                    long price = Long.parseLong(sanPhamMoi.getGiasp());
                    Utils.manggiohang.get(i).setGiasp(price);
                    flag = true;
                }
            }
            if(flag == false){
                long price = Long.parseLong(sanPhamMoi.getGiasp());
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(price);
                gioHang.setSoluong(countProduct);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }
        }else {
            int countProduct = Integer.parseInt(spinner.getSelectedItem().toString());
            long price = Long.parseLong(sanPhamMoi.getGiasp());
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(price);
            gioHang.setSoluong(countProduct);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem += Utils.manggiohang.get(i).getSoluong();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPhamMoi = sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        if(sanPhamMoi.getHinhanh().contains("http")){
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imHinhAnh);
        }else {
            String image = Utils.BASE_URL+"images/"+sanPhamMoi.getHinhanh();
            Glide.with(getApplicationContext()).load(image).into(imHinhAnh);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###" );
        giasp.setText("Price: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp())) + "$");
        Integer[] number = new Integer[]{1, 2, 3, 4 ,5};
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, number);
        spinner.setAdapter(spinnerAdapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        tensp = findViewById(R.id.tensp_ct);
        giasp = findViewById(R.id.giasp_ct);
        mota = findViewById(R.id.motasp_ct);
        imHinhAnh = findViewById(R.id.image_ct);
        btnAdd = findViewById(R.id.btnaddtocart);
        spinner = findViewById(R.id.spinnerct);
        toolbar = findViewById(R.id.toolbar_ct);
        notificationBadge = findViewById(R.id.product_count);
        frameLayout = findViewById(R.id.framelayout_cart);

        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem += Utils.manggiohang.get(i).getSoluong();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang != null){
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem += Utils.manggiohang.get(i).getSoluong();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }
}