package com.example.appbanhangonlinereal.model.EventBus;

import com.example.appbanhangonlinereal.model.SanPhamMoi;

public class EditEvent {
    SanPhamMoi sanPhamMoi;

    public EditEvent(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }

    public SanPhamMoi getSanPhamMoi() {
        return sanPhamMoi;
    }

    public void setSanPhamMoi(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }
}
