package com.example.rxjava;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.downLoad.SaveFileTask;
import com.example.net.BaseSubscriber;
import com.example.net.RxRestClient;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String GET_TUI_GUANG = "Money/GetTuiGuang";
    //上传图片
    public static final String POST_FILE = "Money/postFile";
    private Button main_get_btn;
    private Button main_up_load_btn;
    private Button main_down_load_btn;
    private TextView main_down_load_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_get_btn = (Button) findViewById(R.id.main_get_btn);
        main_get_btn.setOnClickListener(this);
        main_up_load_btn = (Button) findViewById(R.id.main_up_load_btn);
        main_up_load_btn.setOnClickListener(this);
        main_down_load_btn = (Button) findViewById(R.id.main_down_load_btn);
        main_down_load_btn.setOnClickListener(this);

        main_down_load_tv = (TextView) findViewById(R.id.main_down_load_tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_get_btn:
                RxRestClient.builder()
                        .url(GET_TUI_GUANG)
                        .build()
                        .get()
                        .subscribe(new BaseSubscriber<String>(this) {
                            @Override
                            public void onNext(String s) {
                                super.onNext(s);
                                Log.i("Get", s);
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.main_up_load_btn:
                File file = new File(Environment.getExternalStorageDirectory(), "123.jpg");
                if (!file.exists()) {
                    Toast.makeText(MainActivity.this, file.getAbsolutePath() + "文件夹不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("文件长度", file.length() / 1024 + "KB");
                RxRestClient.builder()
                        .url(POST_FILE)
//                        .file(file)
                        .raw(file.getPath())
                        .build()
//                        .upload()
                        .post_raw()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<String>(this) {
                            @Override
                            public void onNext(String s) {
                                super.onNext(s);
                            }
                        });
                break;
            case R.id.main_down_load_btn:
                RxRestClient.builder()
                        .url("Money/files/123.png")
                        .build()
                        .download()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<ResponseBody>(this) {
                            @Override
                            public void onNext(ResponseBody response) {
                                super.onNext(response);
                                SaveFileTask task = new SaveFileTask();
                                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                        Environment.getExternalStorageDirectory().getPath(),
                                        ".png", response, "789");
                                if (task.isCancelled()) {
                                    Log.i("总进度", "下载完毕");
                                }
                            }
                        });
                break;
        }
    }
}
