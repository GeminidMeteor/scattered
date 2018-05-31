package com.example.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 作者： ZlyjD.
 * 时间：2018/5/16.
 */

public class Main extends AppCompatActivity implements View.OnClickListener {
    private Button main_RxJava;
    private Button main_PopupWindow;
    private Button main_Dialog;
    private Button main_MagicIndicator;
    private Button main_video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        main_RxJava = (Button) findViewById(R.id.main_RxJava);
        main_RxJava.setOnClickListener(this);
        main_PopupWindow = (Button) findViewById(R.id.main_PopupWindow);
        main_PopupWindow.setOnClickListener(this);
        main_Dialog = (Button) findViewById(R.id.main_Dialog);
        main_Dialog.setOnClickListener(this);
        main_MagicIndicator = (Button) findViewById(R.id.main_MagicIndicator);
        main_MagicIndicator.setOnClickListener(this);
        main_video = (Button) findViewById(R.id.main_video);
        main_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_RxJava:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.main_PopupWindow:
                Intent intent1 = new Intent(this, PopupWindowActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_Dialog:
                Intent intent2 = new Intent(this, DialogActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_MagicIndicator:
                Intent intent3 = new Intent(this, MagicIndicatorActivity.class);
                startActivity(intent3);
                break;
            case R.id.main_video:
                Intent intent4 = new Intent(this, VideoActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
