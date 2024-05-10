package com.example.appbanhangonlinereal.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonlinereal.Interface.ItemClickListener;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.model.EventBus.EditEvent;
import com.example.appbanhangonlinereal.model.SanPhamMoi;
import com.example.appbanhangonlinereal.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public ProductAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPham = array.get(position);
        holder.tensp.setText(sanPham.getTensp().trim());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###" );
        holder.giasp.setText("Price: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasp())) + "$");
//        holder.mota.setText(sanPham.getMota());
        if(sanPham.getHinhanh().contains("http")){
            Glide.with(context).load(sanPham.getHinhanh()).into(holder.imhinhanh);
        }else {
            String image = Utils.BASE_URL+"images/"+sanPham.getHinhanh();
            Glide.with(context).load(image).into(holder.imhinhanh);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    EventBus.getDefault().postSticky(new EditEvent(sanPham));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener, View.OnLongClickListener {
        TextView tensp, giasp, mota;
        ImageView imhinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_tensp);
            giasp = itemView.findViewById(R.id.itemdt_giasp);
            //mota = itemView.findViewById(R.id.itemdt_mota);
            imhinhanh = itemView.findViewById(R.id.itemdt_image);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0 , 0, getAdapterPosition(), "Edit");
            contextMenu.add(0 , 1, getAdapterPosition(), "Delete");
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }

    }
}
