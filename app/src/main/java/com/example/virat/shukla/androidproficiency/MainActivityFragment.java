package com.example.virat.shukla.androidproficiency;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class MainActivityFragment extends Fragment {
    RecyclerView recyclerView;
    MyAdapter adapter;
    final String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
    final String TAG = "volley_request_tag";
    RequestQueue queue;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new MyAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupVolleyRequestQueue();

        populateAdapter();
        return view;
    }

    private void setupVolleyRequestQueue() {
        queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);
    }

    private void populateAdapter() {
        InnerClass[] data = new InnerClass[10];
        data[0] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[1] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[2] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[3] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[4] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[5] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[6] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[7] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");

        data[8] = new InnerClass("Housing", "Warmer than you might think.",
                "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png");
        adapter.swapData(data);
    }

    @Override
    public void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
