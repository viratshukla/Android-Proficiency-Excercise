package com.example.virat.shukla.androidproficiency.webrepo;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.virat.shukla.androidproficiency.database.AppDatabase;
import com.example.virat.shukla.androidproficiency.database.ResponseModel;

import org.json.JSONObject;

public class WebRepository {
    private static WebRepository instance;
    private final String URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
    private final String TAG = "volley_request_tag";
    private final int PRIMARY_KEY = 1;
    private RequestQueue queue;
    private Context context;
    private AppDatabase mDB;
    private LiveData<ResponseModel> responseModelLiveData;


    private WebRepository() {

    }

    public void pullData() {
        if (null != context) {
            mDB = AppDatabase.getInMemoryDatabase(context);
            responseModelLiveData = mDB.getDao().findJSONReponse(PRIMARY_KEY);
            if (isNetworkAvailable()) {
                setupVolleyRequestQueue();
            }
        }
    }

    public static WebRepository getInstance() {
        if (null == instance) {
            instance = new WebRepository();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LiveData<ResponseModel> getResponseModelLiveData() {
        return responseModelLiveData;
    }

    private void setupVolleyRequestQueue() {
        if (null != context) {
            if (queue != null) {
                queue.cancelAll(TAG);
            }
            queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ResponseModel model = new ResponseModel();
                            model._ID = PRIMARY_KEY;
                            model.jsonResponse = response.toString();

                            mDB.getDao().insertNewResponse(model);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            jsonObjectRequest.setTag(TAG);
            queue.add(jsonObjectRequest);
        }
    }

    private boolean isNetworkAvailable() {
        if (null != context) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = null;
            if (connectivityManager != null) {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
