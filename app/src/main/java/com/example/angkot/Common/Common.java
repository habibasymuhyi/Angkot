package com.example.angkot.Common;

import com.example.angkot.Remote.IGoogleAPI;
import com.example.angkot.Remote.RetrofitClient;

public class Common {

    public  static  final  String driver_tbl = "Drivers";
    public  static  final  String user_driver_tbl = "DriversInformation";
    public  static  final  String user_rider_tbl = "CustomersInformation";
    public  static  final  String pickup_request_tbl = "Customers";

    public static final String baseURL = "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
}
