package com.example.yeol.jolup1;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeol on 2019-06-09.
 */

public class BTsetRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/blu.php";
    private Map<String, String> parameters;

    public BTsetRequest(String userID,String b_left,String b_right, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("b_left", b_left);
        parameters.put("b_right", b_right);
    }

    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }
}
