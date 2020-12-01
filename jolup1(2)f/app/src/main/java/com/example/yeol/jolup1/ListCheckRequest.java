package com.example.yeol.jolup1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by demm on 2019-10-18.
 */
//리스트 테이블 중복 확인 리퀘스트
public class ListCheckRequest extends StringRequest {
    final static private String URL = "http://migo95.cafe24.com/check.php";
    private Map<String, String> parameters;

    public ListCheckRequest(String check_date, String userID, Response.Listener<String> listener) {
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