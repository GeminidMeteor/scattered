package com.example.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.Bean.Bean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 照片浏览适配器
 * 作者： ZlyjD.
 * 时间：2017/4/7.
 */

public class PhotoScanUrlAdapter extends PagerAdapter {

    List<Bean> UrlList;
    private Context context;
    List<View> imageViewList = new ArrayList<>();
    private JZVideoPlayerStandard standard;
    private ImageView view = null;


    public PhotoScanUrlAdapter(Context context, List<Bean> imageList) {
        this.UrlList = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return UrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Log.i("加载图片", "第" + position + "张");
        if (UrlList.get(position).getUrl().endsWith("jpg")) {
            view = new ImageView(context);
            Picasso.with(context).load(UrlList.get(position).getUrl()).into(view);
            imageViewList.add(view);
            container.addView(view);
        } else if (UrlList.get(position).getUrl().endsWith("mp4")) {
            standard = new JZVideoPlayerStandard(context);
            standard.setUp(UrlList.get(position).getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            Bitmap bitmap = getNetVideoBitmap(UrlList.get(position).getUrl());
            standard.thumbImageView.setImageBitmap(bitmap);
            imageViewList.add(standard);
            container.addView(standard);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(view, position);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewList.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
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
