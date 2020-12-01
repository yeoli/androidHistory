package com.example.yeol.jolup1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ThreeRequest extends  StringRequest {
    final static private String URL = "http://migo95.cafe24.com/three.php";
    private Map<String, String> parameters;

    public ThreeRequest(String front,String middle,String back,String userID,String count, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("front",front);
        parameters.put("middle",middle);
        parameters.put("back",back);
        parameters.put("userID",userID);
        parameters.put("count",count);

    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }

}
