package com.example.yeol.jolup1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SupportRequest extends  StringRequest {
    final static private String URL = "http://migo95.cafe24.com/support.php";
    private Map<String, String> parameters;

    public SupportRequest(String order_s,String userID, String count_s, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("order_s",order_s);
        parameters.put("userID",userID);
        parameters.put("count_s",count_s);

    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }

}
