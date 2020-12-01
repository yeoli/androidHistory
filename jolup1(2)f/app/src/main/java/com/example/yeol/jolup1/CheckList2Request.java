package com.example.yeol.jolup1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeol on 2019-05-18.
 */

public class CheckList2Request extends StringRequest {
        final static private String URL = "http://migo95.cafe24.com/list2.php";
        private Map<String, String> parameters;

        public CheckList2Request(String userID,String start,String end, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("userID", userID);
            parameters.put("check_start", start);
            parameters.put("check_end", end);

        }
        public  Map<String, String> getParams() {
            return parameters;
        }

    }