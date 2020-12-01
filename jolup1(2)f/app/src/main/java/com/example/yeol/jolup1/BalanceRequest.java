package com.example.yeol.jolup1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BalanceRequest extends  StringRequest {
    final static private String URL = "http://migo95.cafe24.com/balance.php";
    private Map<String, String> parameters;

    public BalanceRequest(String bal_l,String bal_r,String userID,Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("bal_l",bal_l);
        parameters.put("bal_r",bal_r);
        parameters.put("userID",userID);


    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }

}
