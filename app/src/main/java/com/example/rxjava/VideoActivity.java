package com.example.rxjava;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 作者： ZlyjD.
 * 时间：2018/5/31.
 */

public class VideoActivity extends AppCompatActivity {

    JZVideoPlayerStandard jzVideoPlayerStandard;
    private String url = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_video);
        Bitmap bitmap = getNetVideoBitmap(url);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
        jzVideoPlayerStandard.thumbImageView.setImageBitmap(bitmap);

//        Picasso.with(this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(jzVideoPlayerStandard.thumbImageView);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        //自动播放
        jzVideoPlayerStandard.startVideo();
        //直接全屏
        jzVideoPlayerStandard.startWindowFullscreen();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    /**
     * 获取视频第一帧
     */
    public Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取第一帧
            retriever.setDataSource(videoUrl, new HashMap());
            //获取本地视频的第一帧 //
            //retriever.setDataSource("/sdcard/03.mp4");
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
            //视频第一帧的压缩
//            FileOutputStream outStream = null;
//            outStream = new FileOutputStream(new File(getExternalCacheDir().getAbsolutePath() + "/" + "视频" + ".jpg"));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream);
//            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}

