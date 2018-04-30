package com.masbi.cobmnn;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AnangHanafi on 28/04/2018.
 */

public class MyApplication extends Application {
    private static MyApplication mIntance;
    private RequestQueue requestQueue;
    private static Context mContext;

    private MyApplication(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MyApplication getIntance(Context context) {
        if (mIntance == null) {
            mIntance = new MyApplication(context);
        }
        return mIntance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
