package com.example.appbanhangonlinereal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.model.AdminFunction;
import com.example.appbanhangonlinereal.model.LoaiSp;

import java.util.List;

public class AdminFunctionAdapter extends BaseAdapter {
    List<AdminFunction> array;
    Context context;


    public class ViewHolder{
        TextView texttensp;
        ImageView imghinhanh;
    }

    public AdminFunctionAdapter( Context context, List<AdminFunction> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdminFunctionAdapter.ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new AdminFunctionAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_product, null);
            viewHolder.texttensp = view.findViewById(R.id.item_tensp);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (AdminFunctionAdapter.ViewHolder) view.getTag();

        }
        viewHolder.texttensp.setText(array.get(i).getTensanpham());
        Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imghinhanh);
        return view;
    }
}
