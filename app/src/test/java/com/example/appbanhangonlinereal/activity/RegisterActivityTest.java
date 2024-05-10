package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegisterActivityTest {

    //test username
    @Test
    public void testValidUsername() {
        assertTrue(RegisterActivity.isValidUser("account1"));
    }

    @Test
    public void testUsernameTooLong() {
        assertFalse(RegisterActivity.isValidUser("TooLongUsername123"));
    }

    @Test
    public void testUsernameStartsWithNumber() {
        assertFalse(RegisterActivity.isValidUser("677BBQHQ"));
    }

    @Test
    public void testUsernameStartsWithSpecialChar() {
        assertFalse(RegisterActivity.isValidUser("*nhokpro5"));
    }

    @Test
    public void testUsernameWithBlankInside() {
        assertFalse(RegisterActivity.isValidUser("Linh Trung"));
    }

    //test passWord
    @Test
    public void testValidPassword() {
        assertTrue(RegisterActivity.isValidPass("ValidPass123@"));
    }

    @Test
    public void testShortPassword() {
        assertFalse(RegisterActivity.isValidPass("Short1$"));
    }

    @Test
    public void testPasswordWithoutLetter() {
        assertFalse(RegisterActivity.isValidPass("12345678$"));
    }

    @Test
    public void testPasswordWithoutDigit() {
        assertFalse(RegisterActivity.isValidPass("NoDigit@Symbol"));
    }

    @Test
    public void testPassword1() {
        assertTrue(RegisterActivity.isValidPass("8Letter#"));
    }



    //test Phone
    @Test
    public void testValidPhone() {
        assertTrue(RegisterActivity.isValidPhone("0123456789"));

    }

    @Test
    public void testInvalidPhone() {
        assertFalse(RegisterActivity.isValidPhone("1234567890"));
        assertFalse(RegisterActivity.isValidPhone("012345"));
        assertFalse(RegisterActivity.isValidPhone("01234567890"));
        assertFalse(RegisterActivity.isValidPhone("01we444444"));
        assertFalse(RegisterActivity.isValidPhone("0123456789@"));
    }

    @Test
    public void testEmptyPhone() {
        assertFalse(RegisterActivity.isValidPhone(""));
    }




}