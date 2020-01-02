package com.ninjacart.sample.helper.rest;

public class RestEndPoints {
    
    public static String buildUrl(String url, Object... values) {
        return String.format(url, values);
    }

}
