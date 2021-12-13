package com.android.asm2.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.asm2.R;
import com.android.asm2.adapter.UserAdapter;
import com.android.asm2.model.User;

import java.util.ArrayList;

public class UserListFrag extends Fragment {
    private final ArrayList<User> userArrayList;

    public UserListFrag(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView userListContainer = view.findViewById(R.id.list_adapter_container);
        UserAdapter adapter = new UserAdapter(getContext(), userArrayList);
        userListContainer.setAdapter(adapter);

        return view;
    }
}