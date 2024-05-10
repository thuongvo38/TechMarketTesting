package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.adapter.LoaiSpAdapter;
import com.example.appbanhangonlinereal.model.LoaiSp;
import com.example.appbanhangonlinereal.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeAdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewAdmin;
    DrawerLayout drawerLayoutAdmin;
    NavigationView navigationView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSale apiSale;
    List<LoaiSp> mangLoaiSp;
    LoaiSpAdapter loaiSpAdapter;
    CardView add, remove, edit, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        initView();
        initControl();
        ActionBar();
        getFunctionAmin();
    }
    public void getFunctionAmin(){
        compositeDisposable.add(apiSale.getfunction()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            mangLoaiSp = loaiSpModel.getResult();
                            loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangLoaiSp);
                            listViewAdmin.setAdapter(loaiSpAdapter);
                        }
                ));
    }
    private void ActionBar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutAdmin.openDrawer(GravityCompat.START);
            }
        });

    }
    private void initControl(){
        listViewAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        Intent home = new Intent(getApplicationContext(),HomeAdminActivity.class);
                        startActivity(home);
                        break;
                    }
                    case 1: {
                        Intent logout = new Intent(getApplicationContext(), LoginAdminActivity.class);
                        startActivity(logout);
                        break;
                    }
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewOrder = new Intent(getApplicationContext(), ViewOrderAdminActivity.class);
                startActivity(viewOrder);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAdd = new Intent(getApplicationContext(), AddProductAdminActivity.class);
                startActivity(viewAdd);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getApplicationContext(), EditProductAdminActivity.class);
                startActivity(edit);
            }
        });
    }
    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        toolbar = findViewById(R.id.toolbarAdmin);
        listViewAdmin = findViewById(R.id.listviewAdmin);
        drawerLayoutAdmin = findViewById(R.id.drawerlayoutAdmin);
        navigationView = findViewById(R.id.navigationViewAdmin);
        mangLoaiSp = new ArrayList<>();
        add = findViewById(R.id.card_addProduct);
        edit = findViewById(R.id.card_editProduct);
        view = findViewById(R.id.card_viewOrder);

    }
}