package com.example.appbanhangonlinereal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.model.Item;
import com.example.appbanhangonlinereal.utils.Utils;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;

    public DetailOrderAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtTen.setText(item.getTensp()+ "");
        holder.txtSoLuong.setText("Quantity: " +item.getCount()+ "");
        if(item.getHinhanh().contains("http")){
            Glide.with(context).load(item.getHinhanh()).into(holder.imDetail);
        }else {
            String image = Utils.BASE_URL+"images/"+item.getHinhanh();
            Glide.with(context).load(image).into(holder.imDetail);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imDetail;
        TextView txtTen, txtSoLuong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imDetail = itemView.findViewById(R.id.item_imdetail);
            txtTen = itemView.findViewById(R.id.item_nameproduct_detail);
            txtSoLuong = itemView.findViewById(R.id.item_count_detail);
        }
    }
}
