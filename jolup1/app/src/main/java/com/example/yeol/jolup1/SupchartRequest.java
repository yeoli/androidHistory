package com.example.yeol.jolup1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeol on 2019-06-09.
 */

public class SupchartRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/sup_chart.php";
    private Map<String, String> parameters;

    public SupchartRequest(String id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", id);
    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }
}