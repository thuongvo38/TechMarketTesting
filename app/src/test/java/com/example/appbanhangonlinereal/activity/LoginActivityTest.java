package com.example.appbanhangonlinereal.activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginActivityTest {
    @Test
    public void testValidInput() {
        assertEquals(11, LoginActivity.isValidLoginInput("test@gmail.com", "ValidPass123@"));
    }

    @Test
    public void testNoInput() {

        assertEquals(0, LoginActivity.isValidLoginInput("", ""));

    }
    @Test
    public void testNoInputEmail() {

        assertEquals(1, LoginActivity.isValidLoginInput("", "Short1$"));
        assertEquals(1, LoginActivity.isValidLoginInput("", "12345678#"));
        assertEquals(1, LoginActivity.isValidLoginInput("", "NoDigit@Symbol"));
        assertEquals(1, LoginActivity.isValidLoginInput("", "0c@pitalletter"));
        assertEquals(1, LoginActivity.isValidLoginInput("", "noSymbol1"));
        assertEquals(1, LoginActivity.isValidLoginInput("", "ValidPass123@"));

        
    }

 

    @Test
    public void testNoPassword() {
        assertEquals(2, LoginActivity.isValidLoginInput("test@gmail.com", ""));
    }

    @Test
    public void testInvalidPassword() {
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "invalid"));
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "Short1$"));
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "12345678#"));
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "NoDigit@Symbol"));
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "0c@pitalletter"));
        assertEquals(21, LoginActivity.isValidLoginInput("test@gmail.com", "noSymbol1"));
        assertEquals(11, LoginActivity.isValidLoginInput("test@gmail.com", "ValidPass123@"));

        
    }


}