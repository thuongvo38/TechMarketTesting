package com.example.appbanhangonlinereal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonlinereal.Interface.IImageClickListener;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.activity.EditProductAdminActivity;
import com.example.appbanhangonlinereal.model.EventBus.totalCostEvent;
import com.example.appbanhangonlinereal.model.GioHang;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.internal.Util;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<GioHang> cartList;

    public CartAdapter(Context context, List<GioHang> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang cart = cartList.get(position);
        holder.cartNameProduct.setText(cart.getTensp());
        holder.cartCount.setText(cart.getSoluong() + " ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###" );
        holder.cartPrice.setText(decimalFormat.format(cart.getGiasp()) + "$");
        if(cart.getHinhsp().contains("http")){
            Glide.with(context).load(cart.getHinhsp()).into(holder.cartImg);
        }else {
            String image = Utils.BASE_URL+"images/"+cart.getHinhsp();
            Glide.with(context).load(image).into(holder.cartImg);
        }
        long price = cart.getSoluong() * cart.getGiasp();
        holder.cartCost.setText(decimalFormat.format(price)+ "$");
        holder.setImageClickListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                Log.d("TAG", "onImageClick: " + pos + "..." + value);
                if (value == 1) {
                    if(cartList.get(pos).getSoluong() > 1) {
                        int newCount = cartList.get(pos).getSoluong() - 1;
                        cartList.get(pos).setSoluong(newCount);
                        holder.cartCount.setText(cartList.get(pos).getSoluong() + "");
                        long price = cartList.get(pos).getSoluong() * cartList.get(pos).getGiasp();
                        holder.cartCost.setText(decimalFormat.format(price));
                        EventBus.getDefault().postSticky(new totalCostEvent());
                    }else if(cartList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Notice");
                        builder.setMessage("Do you really want to remove this product?");
                        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new totalCostEvent());
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    }
                }else if (value == 2){
                    if(cartList.get(pos).getSoluong() < 11) {
                        int newCount = cartList.get(pos).getSoluong() + 1;
                        cartList.get(pos).setSoluong(newCount);
                    }
                    holder.cartCount.setText(cartList.get(pos).getSoluong() + " ");
                    long price = cartList.get(pos).getSoluong() * cartList.get(pos).getGiasp();
                    holder.cartCost.setText(decimalFormat.format(price));
                    EventBus.getDefault().postSticky(new totalCostEvent());
                }
                holder.cartCount.setText(cartList.get(pos).getSoluong() + " ");
                long price = cartList.get(pos).getSoluong() * cartList.get(pos).getGiasp();
                holder.cartCost.setText(decimalFormat.format(price));
                EventBus.getDefault().postSticky(new totalCostEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cartImg, cartAdd, cartRemove;
        TextView cartNameProduct, cartPrice, cartCount, cartCost;
        IImageClickListener imageClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cartNameProduct = itemView.findViewById(R.id.item_name_cart);
            cartPrice = itemView.findViewById(R.id.item_price_cart);
            cartCount = itemView.findViewById(R.id.item_count_cart);
            cartCost = itemView.findViewById(R.id.item_totalprice_cart);
            cartImg = itemView.findViewById(R.id.item_image_cart);
            cartAdd = itemView.findViewById(R.id.cartcong);
            cartRemove = itemView.findViewById(R.id.carttru);
            //set event click add and remove
            cartAdd.setOnClickListener(this);
            cartRemove.setOnClickListener(this);
        }

        public void setImageClickListener(IImageClickListener imageClickListener) {
            this.imageClickListener = imageClickListener;
        }

        @Override
        public void onClick(View view) {
            if (view == cartRemove) {
                imageClickListener.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == cartAdd) {
                imageClickListener.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
