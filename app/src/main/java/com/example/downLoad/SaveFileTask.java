package com.example.downLoad;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * 作者： ZlyjD.
 * 时间：2018/3/26.
 */

public class SaveFileTask extends AsyncTask<Object, Integer, File> {
    TextView main_down_load_tv;

    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];

        final InputStream is = body.byteStream();
        final long total = body.contentLength();
        long sum = 0L;
        int len = 0;
        byte[] buf = new byte[1024];
        File file = new File(downloadDir, name + extension);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                Log.i("总进度", sum + "/" + total + "     " + (int) Math.floor((sum * (1.0) / total) * 100));
                publishProgress((int) Math.floor((sum * (1.0) / total) * 100));
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i("总进度1", values[0] + "%");
    }

    //    //如果是apk直接安装
//    private void autoInstallApk(File file) {
//        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
//            final Intent install = new Intent();
//            //新建一个栈
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            install.setAction(Intent.ACTION_VIEW);
//            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
////            Latte.getApplication().startActivity(install);
//        }
//
//    }
}
