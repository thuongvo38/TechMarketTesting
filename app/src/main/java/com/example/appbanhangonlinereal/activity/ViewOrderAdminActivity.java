package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.adapter.OrderAdapter;
import com.example.appbanhangonlinereal.model.EventBus.EditEvent;
import com.example.appbanhangonlinereal.model.EventBus.EditOrderEvent;
import com.example.appbanhangonlinereal.model.MessageModel;
import com.example.appbanhangonlinereal.model.Order;
import com.example.appbanhangonlinereal.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewOrderAdminActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSale apiSale;
    RecyclerView recyclerViewOrder;
    Toolbar toolbar;
    Order order;
    int statusOrder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_admin);
        initView();
        initToolbar();
        getOrder();
    }

    private void getOrder() {
        compositeDisposable.add(apiSale.viewOrderAdmin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            OrderAdapter orderAdapter = new OrderAdapter(getApplicationContext(), orderModel.getResult());
                            recyclerViewOrder.setAdapter(orderAdapter);
                        },
                        throwable -> {
                            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        recyclerViewOrder = findViewById(R.id.recycleview_order);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(layoutManager);
        toolbar = findViewById(R.id.toolbar_order);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventEdit(EditOrderEvent event){
        if(event != null){
            order = event.getOrder();
            showCustomDialog();
        }
    }

    private void showCustomDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.update_status_order, null);
        Spinner spinner = view.findViewById(R.id.spinner_status);
        Button btnUpdateStatus = view.findViewById(R.id.btnUpdate_status);
        List<String> statusList = new ArrayList<>();
        statusList.clear();
        statusList.add("Processing");
        statusList.add("Delivering");
        statusList.add("Completed");
        statusList.add("Canceled");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusList);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(order.getStatus());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statusOrder = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatusOrder();
            }
        });
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setView(view);
        alertDialog = aBuilder.create();
        alertDialog.show();
    }
    private void updateStatusOrder(){
        compositeDisposable.add(apiSale.editstatus(order.getId(), statusOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                            getOrder();
                            alertDialog.dismiss();
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
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
}