package com.example.virat.shukla.androidproficiency.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.virat.shukla.androidproficiency.database.ResponseModel;
import com.example.virat.shukla.androidproficiency.model.InnerRowsClass;
import com.example.virat.shukla.androidproficiency.model.Model;
import com.example.virat.shukla.androidproficiency.adapters.MyAdapter;
import com.example.virat.shukla.androidproficiency.R;
import com.example.virat.shukla.androidproficiency.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ProgressBar progressBar;
    TextView errorText;
    Toolbar toolbar;
    private UserViewModel viewModel;
    private boolean isResponseReceivedForFirstTime = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        progressBar = view.findViewById(R.id.progressBar);
        errorText = view.findViewById(R.id.no_internet);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.refreshData();
            }
        });

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init();

        viewModel.getResponse().observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(@Nullable ResponseModel responseModel) {
                if (null == responseModel) {
                    // this boolean is added to stop displaying errorText on the very first start
                    if (isResponseReceivedForFirstTime) {
                        recyclerView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        errorText.setVisibility(View.VISIBLE);
                    }
                    isResponseReceivedForFirstTime = true;
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    errorText.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    Model model = gson.fromJson(responseModel.jsonResponse, Model.class);
                    InnerRowsClass[] listOfRows = model.getRows();
                    List<InnerRowsClass> newList = new ArrayList<>();

//                     check if there is any empty object, if any, remove them
                    for (InnerRowsClass i : listOfRows) {
                        if (!(null == i.getTitle() && null == i.getDescription() && null == i.getImageHref())) {
                            newList.add(i);
                        }
                    }

//                    update the adapter data
                    adapter.swapData(newList);

                    toolbar.setTitle(model.getTitle());
                }
            }
        });

        return view;
    }

}
