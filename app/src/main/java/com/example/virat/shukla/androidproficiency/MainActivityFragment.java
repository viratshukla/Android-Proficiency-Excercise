package com.example.virat.shukla.androidproficiency;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    final String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
    final String TAG = "volley_request_tag";
    RequestQueue queue;
    ProgressBar progressBar;
    TextView errorText;
    Toolbar toolbar;

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
                setupVolleyRequestQueue();
            }
        });

//      check if network is available or not
        if (isNetworkAvailable()) {
//          using Volley library to download data from link
            setupVolleyRequestQueue();
        } else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
        }


        return view;
    }

    private void setupVolleyRequestQueue() {
        if (null != getContext()) {
            if (queue != null) {
                queue.cancelAll(TAG);
            }
            queue = Volley.newRequestQueue(getContext());
            progressBar.setVisibility(View.VISIBLE);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            errorText.setVisibility(View.GONE);

//                          Map JSON data into objects
                            Gson gson = new Gson();
                            Model model = gson.fromJson(response.toString(), Model.class);
                            InnerRowsClass[] listOfRows = model.getRows();
                            List<InnerRowsClass> newList = new ArrayList<>();

                            // check if there is any empty object, if any, remove them
                            for (InnerRowsClass i : listOfRows) {
                                if (!(null == i.getTitle() && null == i.getDescription() && null == i.getImageHref())) {
                                    newList.add(i);
                                }
                            }

//                          update the adapter data
                            adapter.swapData(newList);

                            toolbar.setTitle(model.getTitle());
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            errorText.setVisibility(View.VISIBLE);
                        }
                    });
            jsonObjectRequest.setTag(TAG);
            queue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    private boolean isNetworkAvailable() {
        if (null != getContext()) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = null;
            if (connectivityManager != null) {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
