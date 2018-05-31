package com.example.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 照片浏览适配器
 * 作者： ZlyjD.
 * 时间：2017/4/7.
 */

public class PhotoScanAdapter extends PagerAdapter{

    List<ImageView> imageList;

    public PhotoScanAdapter(List<ImageView> imageList){
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = imageList.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(view,position);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageList.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClickListener(View view, int position);
    }
}
