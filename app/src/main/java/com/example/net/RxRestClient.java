package com.example.net;

import android.content.Context;

import com.example.loader.Loader;
import com.example.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.example.net.HttpMethod.GET;
import static com.example.net.HttpMethod.POST;


/**
 * 作者： ZlyjD.
 * 时间：2018/3/24.
 */

public class RxRestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final Context CONTEXT;
    private final RequestBody BOBY;
    private final File FILE;
    private final LoaderStyle LOADER_STYLE;

    public RxRestClient(String url,
                        Map<String, Object> params,
                        Context context,
                        RequestBody body,
                        File file,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.CONTEXT = context;
        this.BOBY = body;
        this.FILE = file;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestServer server = RestCreator.getRxRestService();
        Observable<String> observable = null;
        if (LOADER_STYLE != null) {
            Loader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = server.get(URL, PARAMS);
                break;
            case POST:
                observable = server.post(URL, PARAMS);
                break;
            case POST_RAM:
                observable = server.postRaw(URL, BOBY);
                break;
            case PUT:
                observable = server.put(URL, PARAMS);
                break;
            case PUT_RAM:
                observable = server.putRaw(URL, BOBY);
                break;
            case DELETE:
                observable = server.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = server.upload(URL, body);
                break;
            default:
                break;
        }
        return observable;
    }

    public final Observable<String> get() {
        return request(GET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public final Observable<String> post() {
//        if (BOBY == null) {
        return request(POST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
//        } else {
//            if (!PARAMS.isEmpty()) {
//                throw new RuntimeException("params must be null");
//            }
//            return request(HttpMethod.POST_RAM)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        }
    }

    public final Observable<String> post_raw() {
        return request(HttpMethod.POST_RAM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public final Observable<String> put() {
        if (BOBY == null) {
            return request(HttpMethod.PUT)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.PUT_RAM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //上传
    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //下载
    public final Observable<ResponseBody> download() {
        final Observable<ResponseBody> requestBodyObservable = RestCreator.getRxRestService().download(URL, PARAMS);
        return requestBodyObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
