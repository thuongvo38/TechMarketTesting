package com.example.appbanhangonlinereal.Retrofit;

import com.example.appbanhangonlinereal.model.AdminModel;
import com.example.appbanhangonlinereal.model.LoaiSpModel;
import com.example.appbanhangonlinereal.model.MessageModel;
import com.example.appbanhangonlinereal.model.OrderModel;
import com.example.appbanhangonlinereal.model.SanPhamMoiModel;
import com.example.appbanhangonlinereal.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiSale {
    //Get data from database
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
    @GET("getadminfunction.php")
    Observable<LoaiSpModel> getfunction();
    @GET("vieworderadmin.php")
    Observable<OrderModel> viewOrderAdmin();
    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();
    @GET("thongke.php")
    Observable<MessageModel> getStatistical();
    //Post data
    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("loai") int loai);


    @POST("register.php")
    @FormUrlEncoded
    Observable<UserModel> register(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile
    );
    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("pass") String pass
    );
    @POST("order.php")
    @FormUrlEncoded
    Observable<UserModel> createOrder(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("cost") String cost,
            @Field("iduser") int iduser,
            @Field("address") String address,
            @Field("count") int count,
            @Field("detail") String detail
    );

    @POST("vieworder.php")
    @FormUrlEncoded
    Observable<OrderModel> viewOrder(
            @Field("iduser") int iduser
    );

    @POST("search.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );
    @POST("loginadmin.php")
    @FormUrlEncoded
    Observable<AdminModel> loginadmin(
            @Field("username") String username,
            @Field("pass") String pass
    );

    @POST("insertproduct.php")
    @FormUrlEncoded
    Observable<MessageModel> insertProduct(
            @Field("tensp") String tensp,
            @Field("giasp") String giasp,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai
    );
    @Multipart
    @POST("uploadimage.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);
    @POST("deleteproduct.php")
    @FormUrlEncoded
    Observable<MessageModel> delete(
            @Field("id") int id
    );
    @POST("update.php")
    @FormUrlEncoded
    Observable<MessageModel> update(
            @Field("id") int id,
             @Field("tensp") String tensp,
            @Field("giasp") String giasp,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai
    );
    @POST("editstatusorder.php")
    @FormUrlEncoded
    Observable<MessageModel> editstatus(
            @Field("id") int id,
            @Field("status") int status
    );


}
