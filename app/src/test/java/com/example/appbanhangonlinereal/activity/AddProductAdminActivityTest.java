package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddProductAdminActivityTest {
    @Test
    public void testValidInputs() {
        String result = AddProductAdminActivity.isValidInsert("Product Name", "1000", "Product Description");
        assertEquals("Valid", result);
    }
    //-------------NULL PRODUCT NAME----------------
    @Test
    public void testInvalidInsertUD() {
        String result = AddProductAdminActivity.isValidInsert("", "100", "Product Description");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD1() {
        String result = AddProductAdminActivity.isValidInsert("", "0", "abcd");
        assertEquals("Invalid name product", result);
    }

        @Test
    public void testInvalidInsertUD2() {
        String result = AddProductAdminActivity.isValidInsert("", "0", "");
        assertEquals("Invalid name product", result);
    }
    
    @Test
    public void testInvalidInsertUD3() {
        String result = AddProductAdminActivity.isValidInsert("", "999999", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD4() {
        String result = AddProductAdminActivity.isValidInsert("", "999999", "abcd");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD5() {
        String result = AddProductAdminActivity.isValidInsert("", "10000000000000000", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD6() {
        String result = AddProductAdminActivity.isValidInsert("", "10000000000000000", "abcd");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD7() {
        String result = AddProductAdminActivity.isValidInsert("", "1000", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD8() {
        String result = AddProductAdminActivity.isValidInsert("", "1000", "abcd");
        assertEquals("Invalid name product", result);
    }
   //----------------------------------------end---------------------------//

//------------- PRODUCT NAME have special character----------------

    @Test
    public void testInvalidInsertUD9() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "0", "");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidInsertUD10() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "0", "abcd");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD12() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "999999", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD13() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "999999", "abcd");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD14() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "10000000000000000", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD15() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "10000000000000000", "abcd");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidInsertUD16() {
        String result = AddProductAdminActivity.isValidInsert("Điện tho@i", "1000", "abcd");
        assertEquals("Invalid name product", result);
    }

    //----------name start with a number-----------------------------   
     @Test
    public void testInvalidInsertUD17() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "0", "");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidInsertUD18() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "0", "abcd");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD19() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "999999", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD20() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "999999", "abcd");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD21() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "10000000000000000", "");
        assertEquals("Invalid name product", result);
    }
    @Test
    public void testInvalidInsertUD22() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "10000000000000000", "abcd");
        assertEquals("Invalid name product", result);
    }

    @Test
    public void testInvalidInsertUD23() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "1000", "");
        assertEquals("Invalid name product", result);
    }
        @Test
    public void testInvalidInsertUD24() {
        String result = AddProductAdminActivity.isValidInsert("1 bàn phím", "1000", "abcd");
        assertEquals("Invalid name product", result);
    }

     //----------Valid name product-----------------------------   
    @Test
    public void testInvalidInsertUD25() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "0", "");
        assertEquals("Invalid price product", result);
    }

    @Test
    public void testInvalidInsertUD26() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "0", "abcd");
        assertEquals("Invalid price product", result);
    }
    @Test
    public void testInvalidInsertUD27() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "999999", "");
        assertEquals("Invalid describe product", result);
    }
    @Test
    public void testInvalidInsertUD28() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "999999", "abcd");
        assertEquals("Valid", result);
    }
    @Test
    public void testInvalidInsertUD29() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "10000000000000000", "");
        assertEquals("Invalid describe product", result);
    }
    @Test
    public void testInvalidInsertUD30() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "10000000000000000", "abcd");
        assertEquals("Valid", result);
    }

    @Test
    public void testInvalidInsertUD31() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "1000", "");
        assertEquals("Invalid describe product", result);
    }
        @Test
    public void testInvalidInsertUD32() {
        String result = AddProductAdminActivity.isValidInsert("Tên sản phẩm", "1000", "abcd");
        assertEquals("Valid", result);
    }



    //-----------------BUG FOR PRICE-----------------//
    @Test
    public void testInvalidPriceStringUD33() {
        String result = AddProductAdminActivity.isValidInsert("Product Name", "1VND", "Product Description");
        assertEquals("Invalid price product", result);
    }
    //-----------------END----------------------------//




 
}