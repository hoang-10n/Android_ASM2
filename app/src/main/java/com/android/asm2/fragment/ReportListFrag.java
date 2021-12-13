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
import com.android.asm2.adapter.ReportAdapter;
import com.android.asm2.model.Report;

import java.util.ArrayList;

public class ReportListFrag extends Fragment {
    private final ArrayList<Report> reportArrayList;

    public ReportListFrag(ArrayList<Report> reportArrayList) {
        this.reportArrayList = reportArrayList;
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
        ListView reportListContainer = view.findViewById(R.id.list_adapter_container);
        ReportAdapter adapter = new ReportAdapter(getContext(), reportArrayList);
        reportListContainer.setAdapter(adapter);

        return view;
    }
}