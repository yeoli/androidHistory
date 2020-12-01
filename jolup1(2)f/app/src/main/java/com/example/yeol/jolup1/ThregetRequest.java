package com.example.yeol.jolup1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeol on 2019-06-07.
 */

public class ThregetRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/three_s.php";
    private Map<String, String> parameters;

    public ThregetRequest(String id,String check_date, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", id);
        parameters.put("check_date", check_date);
    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }

}
