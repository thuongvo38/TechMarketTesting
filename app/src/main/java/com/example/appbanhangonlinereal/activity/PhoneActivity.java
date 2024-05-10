package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.adapter.DienThoaiAdapter;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.utils.Utils;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoneActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiSale apiSale;
    DienThoaiAdapter dienThoaiAdapter;
    List<SanPhamMoi> sanPhamMoiList;
    int page = 1;
    int loai;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        loai = getIntent().getIntExtra("loai", 1);
        initView();
        ActionToolBar();
        getData();
    }

    public void getData(){
        compositeDisposable.add(apiSale.getSanPham(loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                if(dienThoaiAdapter == null) {
                                    sanPhamMoiList = sanPhamMoiModel.getResult();
                                    dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                                    recyclerView.setAdapter(dienThoaiAdapter);
                                }else{
                                    int vitri = sanPhamMoiList.size()-1;
                                    int soLuongAdd = sanPhamMoiModel.getResult().size();
                                    for(int i = 0; i < soLuongAdd; i++){
                                        sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                    }
                                    dienThoaiAdapter.notifyItemRangeChanged(vitri, soLuongAdd);
                                }
                            }else {
                                Toast.makeText(this, "Over Data", Toast.LENGTH_SHORT).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Connect failed to server" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
    private void ActionToolBar(){
        setSupportActionBar(toolbar);
        ActionBar a = getSupportActionBar();
        if(loai == 2){
            assert a != null;
            a.setTitle("Computer");
        } else if (loai == 3) {
            assert a != null;
            a.setTitle("Accessory");
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initView(){
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        toolbar = findViewById(R.id.toolbar_dt);
        recyclerView = findViewById(R.id.recycleview_dt);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}