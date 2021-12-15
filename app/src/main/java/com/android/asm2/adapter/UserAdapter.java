package com.android.asm2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.asm2.R;
import com.android.asm2.model.User;

import java.util.ArrayList;

/***
 * User Adapter to generate block in User List Fragment
 */
public class UserAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<User> userArrayList;

    public UserAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return userArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_user, viewGroup, false);
        TextView usernameTxt = view.findViewById(R.id.user_adapter_username_txt);
        TextView nameTxt = view.findViewById(R.id.user_adapter_name_txt);
        TextView emailTxt = view.findViewById(R.id.user_adapter_email_txt);
        TextView roleTxt = view.findViewById(R.id.user_adapter_role_txt);

        User user = (User) getItem(i);
        usernameTxt.setText("Username: " + user.getUsername());
        nameTxt.setText("Name: " + user.getName());
        emailTxt.setText("Email: " + user.getEmail());
        roleTxt.setText("Role: " + user.getRole());
        return view;
    }
}
