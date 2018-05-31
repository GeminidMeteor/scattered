package com.example.net;

import android.content.Context;

import com.example.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者： ZlyjD.
 * 时间：2018/3/24.
 */

public class RxRestClientBuilder {

    private String mUrl = null;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private Context mContext = null;
    private File mFile = null;
    private LoaderStyle mLoaderStyle = null;

    public RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("appliection/octet-stream"), raw);
        return this;
    }

    public final RxRestClientBuilder load(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder load(Context context) {
        load(context, LoaderStyle.PacmanIndicator);
        return this;
    }


    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS,
                mContext, mBody, mFile, mLoaderStyle);
    }
}



