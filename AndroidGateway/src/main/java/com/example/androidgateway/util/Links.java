package com.example.androidgateway.util;

import java.net.URI;

public class Links {
    private static final String BASE_URL = "192.168.239.191";
    //private static final String BASE_URL = "192.168.1.113";
    public static final String DJANGO_URL = "http://"+BASE_URL+":800/api/";
    //public static final String DJANGO_URL_bot = "http://"+BASE_URL+":900/api/question/";
    public static final String DJANGO_URL_bot = "http://"+BASE_URL+":900/api/question/";
    // ACCOUNT URLS
    public static final String DJANGO_URL_REGISTER = DJANGO_URL + "register/";
    public static final String DJANGO_URL_LOGIN = DJANGO_URL + "token/";
    public static final String DJANGO_URL_USER_INFO = DJANGO_URL + "userinfo/";
    public static final String DJANGO_URL_USER_INFO_UPDATE = DJANGO_URL + "userinfo/update/";
    // PRODUCTS
    public static final String DJANGO_URL_PRODUCTS = DJANGO_URL + "products/";
    public static final String DJANGO_URL_ADD_PRODUCTS = DJANGO_URL + "product/new";
    public static final String DJANGO_URL_UPDATE_PRODUCTS = DJANGO_URL + "products/update/";
    public static final String DJANGO_URL_DELETE_PRODUCTS = DJANGO_URL + "products/delete/";
    public static final String DJANGO_URL_GET_ALL_OUTSIDE = DJANGO_URL + "outside/";
    // ORDERS
    public static final String DJANGO_URL_ORDER_NEW = DJANGO_URL + "orders/new/";
    public static final String DJANGO_URL_ORDER_GET = DJANGO_URL + "orders/";
}
