package com.example.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;

import java.io.File;

/**
 * 作者： ZlyjD.
 * 时间：2018/5/16.
 */

public class DialogActivity extends AppCompatActivity implements View.OnClickListener, FileChooserDialog.FileCallback {
    private TextView main_dialog_start;
    private TextView main_dialog_file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dialog);
        main_dialog_start = (TextView) findViewById(R.id.main_dialog_start);
        main_dialog_start.setOnClickListener(this);
        main_dialog_file = (TextView) findViewById(R.id.main_dialog_file);
        main_dialog_file.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_dialog_start:
                MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title("开始下载")
                        .content("内容")
                        .progress(false, 100, true)
                        .negativeText("取消下载")
                        .show();
                dialog.setProgress(0);
                // Loop until the dialog's progress value reaches the max (150)
//                while (dialog.getCurrentProgress() != dialog.getMaxProgress()) {
//                    // If the progress dialog is cancelled (the user closes it before it's done), break the loop
//                    if (dialog.isCancelled())
//                        break;
//                    // Wait 50 milliseconds to simulate doing work that requires progress
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        break;
//                    }
//                    // Increment the dialog's progress by 1 after sleeping for 50ms
//                    dialog.incrementProgress(1);
//                }
//                dialog.setContent("下载完成");
                break;
            case R.id.main_dialog_file:
                FileChooserDialog dialog1 = new FileChooserDialog
                        .Builder(DialogActivity.this)
                        .initialPath("/sdcard/Download")  // changes initial path, defaults to external storage directory
                        .mimeType("image/*") // Optional MIME type filter
                        .extensionsFilter(".png", ".jpg") // Optional extension filter, will override mimeType()
                        .tag("optional-identifier")
                        .goUpLabel("Up") // custom go up label, default label is "..."
                        .show();
                break;

        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onFileSelection(@NonNull FileChooserDialog dialog, @NonNull File file) {
        Toast.makeText(DialogActivity.this, file.toPath() + "", Toast.LENGTH_SHORT).show();
    }
}
