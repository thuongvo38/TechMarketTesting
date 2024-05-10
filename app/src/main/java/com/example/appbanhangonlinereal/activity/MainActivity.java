package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.adapter.LoaiSpAdapter;
import com.example.appbanhangonlinereal.adapter.SanPhamMoiAdapter;
import com.example.appbanhangonlinereal.model.LoaiSp;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.model.User;
import com.example.appbanhangonlinereal.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangLoaiSp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSale apiSale;
    List<SanPhamMoi> mangSanPhamMoi;
    SanPhamMoiAdapter sanPhamMoiAdapter;
    FrameLayout frameLayout;
    NotificationBadge notificationBadge;
    ImageView searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        Paper.init(this);
        if(Paper.book().read("user") != null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        initView();
        ActionBar();
        ActionViewFlipper();
        getLoaiSanPham();
        getSanPhamMoi();
        getEventClick();
    }
    public void getEventClick() {
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intentSearch);
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intentCart = new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(intentCart);
            }
        });
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0: {
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        break;
                    }
                    case 1: {
                        Intent phone = new Intent(getApplicationContext(), PhoneActivity.class);
                        phone.putExtra("loai", 1);
                        startActivity(phone);
                        break;
                    }
                    case 2: {
                        Intent pc = new Intent(getApplicationContext(), PhoneActivity.class);
                        pc.putExtra("loai", 2);
                        startActivity(pc);
                        break;
                    }
                    case 3: {
                        Intent ac = new Intent(getApplicationContext(), PhoneActivity.class);
                        ac.putExtra("loai", 3);
                        startActivity(ac);
                        break;
                    }
                    case 4: {
                        Intent info = new Intent(getApplicationContext(), InfoActivity.class);
                        startActivity(info);
                        break;
                    }
                    case 5: {
                        Intent history = new Intent(getApplicationContext(), ViewOrderActivity.class);
                        startActivity(history);
                        break;
                    }
                    case 6: {
                        Paper.book().delete("user");
                        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        break;
                    }

                }
            }
        });
    }
    public void getSanPhamMoi(){
        compositeDisposable.add(apiSale.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                mangSanPhamMoi = sanPhamMoiModel.getResult();
                                sanPhamMoiAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSanPhamMoi);
                                recyclerViewMain.setAdapter(sanPhamMoiAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Connect failed to server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    public void getLoaiSanPham(){
        compositeDisposable.add(apiSale.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if(loaiSpModel.isSuccess()){
                                mangLoaiSp = loaiSpModel.getResult();
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangLoaiSp);
                                listViewManHinhChinh.setAdapter(loaiSpAdapter);
                            }
                        }
                ));
    }
    public void ActionViewFlipper(){
        List<Integer> menuQuangCao = new ArrayList<>();
        menuQuangCao.add(R.drawable.banner_1);
        menuQuangCao.add(R.drawable.banner_2);
        menuQuangCao.add(R.drawable.banner_3);
        menuQuangCao.add(R.drawable.banner_4);
        menuQuangCao.add(R.drawable.banner_5);
        for(int i = 0; i < menuQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(menuQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
//        viewFlipper.setOutAnimation(slide_out);
//        viewFlipper.setInAnimation(slide_in);
    }



    private void ActionBar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }
    private void initView(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewMain = findViewById(R.id.mainRecycleView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        frameLayout = findViewById(R.id.framelayout_cart);
        notificationBadge = findViewById(R.id.product_count);
        searchBar = findViewById(R.id.main_find);
        mangLoaiSp = new ArrayList<>();
        mangSanPhamMoi = new ArrayList<>();
        if(Utils.manggiohang == null ){
            Utils.manggiohang = new ArrayList<>();
        }else{
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
        int totalItem = 0;
        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem += Utils.manggiohang.get(i).getSoluong();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}