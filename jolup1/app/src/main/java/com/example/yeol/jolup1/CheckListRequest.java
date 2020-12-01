package com.example.yeol.jolup1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeol on 2019-05-09.
 */

public class CheckListRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/list.php";
    private Map<String, String> parameters;

    public CheckListRequest(String check_date, String check_t, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("check_date", check_date);
        parameters.put("check_t", check_t);
        parameters.put("userID", userID);

    }
    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }

}

