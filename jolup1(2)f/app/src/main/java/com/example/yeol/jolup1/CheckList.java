package com.example.yeol.jolup1;

/**
 * Created by yeol on 2019-05-09.
 */

public class CheckList {

    String check_time;
    String check_date;

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public CheckList(String check_time, String check_date) {
        this.check_time = check_time;
        this.check_date = check_date;
    }
}
