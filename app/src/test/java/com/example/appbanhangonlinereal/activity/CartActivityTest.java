package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import com.example.appbanhangonlinereal.model.GioHang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CartActivityTest {
    @Test
    public void testCalculateTotalCost_EmptyList() {
        List<GioHang> itemList = new ArrayList<>();
        long result = CartActivity.calculateTotalCost(itemList);
        assertEquals(0, result);
    }

    @Test
    public void testCalculateTotalCost_SingleItem() {
        List<GioHang> itemList = new ArrayList<>();
        itemList.add(new GioHang(1000,"aokhoac",15,"den",5));
        long result = CartActivity.calculateTotalCost(itemList);
        assertEquals(15 * 5, result);
    }

    @Test
    public void testCalculateTotalCost_MultipleItems() {
        List<GioHang> itemList = new ArrayList<>();
        itemList.add(new GioHang(1000,"aokhoac",10,"den",2));
        itemList.add(new GioHang(1001,"aokhoac",20,"den",5));
        long result = CartActivity.calculateTotalCost(itemList);
        assertEquals(10 * 2 + 20 * 5, result);
    }

//    @Test
//    public void testCalculateTotalCost_ItemListWithZeroQuantity() {
//        List<GioHang> itemList = new ArrayList<>();
//        itemList.add(new GioHang("Item A", 10, 0));
//        itemList.add(new GioHang("Item B", 15, 0));
//        long result = CartActivity.calculateTotalCost(itemList);
//        assertEquals(0, result);
//    }

    @Test
    public void testCalculateTotalCost_ItemListWithNegativeQuantity() {
        List<GioHang> itemList = new ArrayList<>();
        itemList.add(new GioHang(1010,"Item A", 10, "trang.png",-2));
        itemList.add(new GioHang(1011,"Item B", 15,"trang", -3));
        long result = CartActivity.calculateTotalCost(itemList);
        assertEquals(-65, result);
    }

    @Test
    public void testCalculateTotalCost_ItemListWithNullPrices() {
        List<GioHang> itemList = new ArrayList<>();
        itemList.add(new GioHang(1011,"Item A", 0,"trang", 3));
        itemList.add(new GioHang(1012,"Item B", 0,"den", 2));
        long result = CartActivity.calculateTotalCost(itemList);
        assertEquals(0, result);
    }

    @Test
    public void testCalculateTotalCost_ItemListWithMixedNullAndPositivePrices() {
        List<GioHang> itemList = new ArrayList<>();
        itemList.add(new GioHang(1013,"Item A",30,"den", 1));
        itemList.add(new GioHang(1122,"Item B", 0, "trang" ,2));
        itemList.add(new GioHang(1111,"Item C", 25,"vang", 3));
        long result = CartActivity.calculateTotalCost(itemList);
        // Item B with null price should not contribute to the total cost
        assertEquals(30 * 1 + 25 * 3, result);
    }
}