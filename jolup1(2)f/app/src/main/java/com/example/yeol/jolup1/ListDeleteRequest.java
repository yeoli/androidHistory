package com.example.yeol.jolup1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by demm on 2019-10-20.
 */

public class ListDeleteRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/delete.php";
    private Map<String, String> parameters;

    public ListDeleteRequest(String check_date, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("check_date",check_date);
        parameters.put("userID",userID);


    }
    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }
}
