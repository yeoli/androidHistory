package com.example.yeol.jolup1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeol on 2019-10-25.
 */

public class SearchmemRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/searchmem.php";
    private Map<String, String> parameters;

    public SearchmemRequest(String userName, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userName", userName);
        parameters.put("userEmail", userEmail);
    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }
}