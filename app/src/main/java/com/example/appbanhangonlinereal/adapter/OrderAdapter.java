package com.example.appbanhangonlinereal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangonlinereal.Interface.ItemClickListener;
import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.model.EventBus.EditOrderEvent;
import com.example.appbanhangonlinereal.model.Order;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Order> orderList;
    Context context;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtOrder.setText("Total cost: " + order.getCost() + "$" );
        holder.address.setText("Delivery address: " + order.getAddress());
        holder.status.setText(getStatusOrder(order.getStatus()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewDetail.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(order.getItem().size());
        //adapter detail order
        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(context, order.getItem());
        holder.recyclerViewDetail.setLayoutManager(layoutManager);
        holder.recyclerViewDetail.setAdapter(detailOrderAdapter);
        holder.recyclerViewDetail.setRecycledViewPool(viewPool);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    EventBus.getDefault().postSticky(new EditOrderEvent(order));
                }
            }
        });
    }
    private String getStatusOrder(int status){
        String rs = "";
        switch (status){
            case 0: rs = "Processing";break;
            case 1: rs = "Delivering";break;
            case 2: rs = "Completed";break;
            case 3: rs = "Canceled";break;
        }
        return  rs;
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtOrder, address, status;
        RecyclerView recyclerViewDetail;
        ItemClickListener itemClickListener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(R.id.id_order);
            recyclerViewDetail = itemView.findViewById(R.id.RecylerView_detail_order);
            address = itemView.findViewById(R.id.address_order);
            status = itemView.findViewById(R.id.id_status_order);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
