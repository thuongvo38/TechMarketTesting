package com.example.appbanhangonlinereal.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.databinding.ActivityAddProductAdminBinding;
import com.example.appbanhangonlinereal.model.MessageModel;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductAdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    int loaiSp = 0;
    ImageView camera;
    ActivityAddProductAdminBinding binding;
    ApiSale apiSale;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String mediaPath;
    SanPhamMoi updateProduct;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_admin);
        binding = ActivityAddProductAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        updateProduct = (SanPhamMoi) getIntent().getSerializableExtra("Edit");
        if(updateProduct == null){
            //them moi khi san pham bang null
            flag = false;
        }else {
            //update
            flag = true;
            binding.btnAddProduct.setText("Update");
            binding.addProductName.setText(updateProduct.getTensp());
            binding.addPriceProduct.setText(updateProduct.getGiasp());
            binding.addProductImage.setText(updateProduct.getHinhanh());
            binding.addDescribeProduct.setText(updateProduct.getMota());
        }
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        ActionBar a = getSupportActionBar();
        if(flag == true){
            assert a != null;
            a.setTitle("Edit Product");
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Please enter product type");
        stringList.add("Phone");
        stringList.add("Computer");
        stringList.add("Accessory");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loaiSp = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == false){
                    insertProduct();
                }else {
                    updateProduct();
                }
            }
        });
        binding.addCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddProductAdminActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    private void updateProduct() {
        String str_name = binding.addProductName.getText().toString().trim();
        String str_price = binding.addPriceProduct.getText().toString().trim();
        String str_describe = binding.addDescribeProduct.getText().toString().trim();
        String str_image = binding.addProductImage.getText().toString().trim();
        if(isValidInsert(str_name,str_price,str_describe)=="Invalid name product")
        {
            Toast.makeText(getApplicationContext(), "Name product is not empty, starts with a digit, or contains special characters", Toast.LENGTH_SHORT).show();
        }
        else if(isValidInsert(str_name,str_price,str_describe)=="Invalid price product")
        {
            Toast.makeText(getApplicationContext(), " Price is not empty or less than 1000", Toast.LENGTH_SHORT).show();
        }
        else if(isValidInsert(str_name,str_price,str_describe)=="Invalid describe product")
        {
            Toast.makeText(getApplicationContext(), " Describe should not be empty", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(str_name) ||TextUtils.isEmpty(str_price) ||TextUtils.isEmpty(str_describe) ||TextUtils.isEmpty(str_image) || loaiSp == 0){
            Toast.makeText(getApplicationContext(), "Please enter all information", Toast.LENGTH_SHORT).show();
        }else {
            compositeDisposable.add(apiSale.update(updateProduct.getId(),str_name, str_price, str_image, str_describe, loaiSp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeAdminActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if(cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    ///----------------------------------------PART WE TESTING-----------------------------------------
    private void insertProduct() {
        String str_name = binding.addProductName.getText().toString().trim();
        String str_price = binding.addPriceProduct.getText().toString().trim();
        String str_describe = binding.addDescribeProduct.getText().toString().trim();
        String str_image = binding.addProductImage.getText().toString().trim();


        if(isValidInsert(str_name,str_price,str_describe)=="Invalid name product")
        {
            Toast.makeText(getApplicationContext(), "Name product is not empty, starts with a digit, or contains special characters", Toast.LENGTH_SHORT).show();
        }
        else if(isValidInsert(str_name,str_price,str_describe)=="Invalid price product")
        {
            Toast.makeText(getApplicationContext(), " Price is not empty or less than 1000", Toast.LENGTH_SHORT).show();
        }
        else if(isValidInsert(str_name,str_price,str_describe)=="Invalid describe product")
        {
            Toast.makeText(getApplicationContext(), " Describe should not be empty", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(str_name) ||TextUtils.isEmpty(str_price) ||TextUtils.isEmpty(str_describe) ||TextUtils.isEmpty(str_image) || loaiSp == 0){
            Toast.makeText(getApplicationContext(), "Please enter all information", Toast.LENGTH_SHORT).show();
        }
        else {
            compositeDisposable.add(apiSale.insertProduct(str_name, str_price, str_image, str_describe, loaiSp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    //-----------------------------------------------END------------------------------------------------------------------


    //Test log ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        Log.d("log", "onActivityResult: " + mediaPath);
    }

    private void uploadMultipleFiles() {
        Uri uri = Uri.parse(mediaPath);
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(getPath(uri));
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<MessageModel> call = apiSale.uploadFile(fileToUpload1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call <MessageModel > call, Response< MessageModel > response) {
                MessageModel messageModel = response.body();
                if (messageModel != null) {
                    if (messageModel.isSuccess()) {
                        binding.addProductImage.setText(messageModel.getName());
                        Toast.makeText(AddProductAdminActivity.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert messageModel != null;
                    Log.v("Response", messageModel.toString());
                }
            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });
    }

    private void initControl() {

    }
    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        spinner = findViewById(R.id.spinner_loai);
        toolbar = findViewById(R.id.toolbar_add);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    ///----------------------------------------PART WE TESTING-----------------------------------------
    public static String isValidInsert(String name, String price, String describe) {
        // Validate name
        if (name.length()==0) {
            return "Invalid name product";  // Name is empty
        } else if (Character.isDigit(name.charAt(0)) || !name.matches("[\\p{L}0-9 ]+")) {
            return "Invalid name product";  // Name starts with a digit or contains special characters
        }

        // Validate price
        else if (price.length()==0 || Double.parseDouble(price) < 1000) {
            return "Invalid price product";  // Price is empty or less than 1000
        }

        // Validate describe
        else if (describe.length()==0) {
            return "Invalid describe product";  // Describe is empty
        }

        return "Valid";  // All inputs are valid
    }
}

//-----------------------------------------------END------------------------------------------------------------------
