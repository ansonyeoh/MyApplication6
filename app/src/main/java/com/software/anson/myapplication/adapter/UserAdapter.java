package com.software.anson.myapplication.adapter;

import android.content.Context;

import com.software.anson.myapplication.model.User;

/**
 * Created by Anson on 2016/8/7.
 */
public class UserAdapter {
    private User user;
    private Context context;
    public UserAdapter(Context context, User user){
        this.context = context;
        this.user = user;
    }
}
