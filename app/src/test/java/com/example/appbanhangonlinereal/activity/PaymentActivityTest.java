package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import com.example.appbanhangonlinereal.model.GioHang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivityTest {

    @Test
    public void testCountItemWithEmptyList() {
        List<GioHang> emptyList = new ArrayList<>();
        int result =PaymentActivity.countItem(emptyList);
        assertEquals(0, result);
    }

    @Test
    public void testCountItemWithSingleItem() {
        List<GioHang> singleItemList = new ArrayList<>();
        singleItemList.add(new GioHang(1, "ItemA", 1000, "picture1.jpg", 1));
        int result = PaymentActivity.countItem(singleItemList);
        assertEquals(1, result);
    }

    @Test
    public void testCountItemWithMultipleItems() {
        List<GioHang> multipleItemList = new ArrayList<>();
        multipleItemList.add(new GioHang(1, "ItemA", 1000, "picture1.jpg", 2));
        multipleItemList.add(new GioHang(2, "ItemB", 1500, "picture2.jpg", 1));
        multipleItemList.add(new GioHang(3, "ItemC", 2000, "picture3.jpg", 3));
        int result = PaymentActivity.countItem(multipleItemList);
        assertEquals(6, result);
    }

    @Test
    public void testCountItemWithZeroQuantity() {
        List<GioHang> zeroQuantityList = new ArrayList<>();
        zeroQuantityList.add(new GioHang(1, "ItemA", 1000, "picture1.jpg", 0));
        int result = PaymentActivity.countItem(zeroQuantityList);
        assertEquals(0, result);
    }

    @Test
    public void testCountItemWithNegativeQuantity() {
        List<GioHang> negativeQuantityList = new ArrayList<>();
        negativeQuantityList.add(new GioHang(1, "ItemA", 1000, "picture1.jpg", -1));
        int result = PaymentActivity.countItem(negativeQuantityList);
        assertEquals(-1, result); // Assuming negative quantities are treated as zero
    }

    // Add more test cases as needed


}