package com.example.yeol.jolup1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by demm on 2019-10-18.
 */

public class ListtimeUpdateRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/update_t.php";
    private Map<String, String> parameters;

    public ListtimeUpdateRequest(String userID, String check_t, String check_date, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("check_date", check_date);
        parameters.put("check_t", check_t);

    }
    public  Map<String, String> getParams() {
        return parameters;
    }
}
