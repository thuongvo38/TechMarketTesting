package com.example.appbanhangonlinereal.model.EventBus;

import com.example.appbanhangonlinereal.model.Order;

public class EditOrderEvent {
    Order order;

    public EditOrderEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
