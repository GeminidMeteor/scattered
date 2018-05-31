package com.example.rxjava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.Adapter.PhotoScanAdapter;
import com.example.Adapter.PhotoScanUrlAdapter;
import com.example.Bean.Bean;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ZlyjD.
 * 时间：2018/5/16.
 */

public class PopupWindowActivity extends AppCompatActivity implements View.OnClickListener {
    private NineGridView main_PopupWindow_nine;
    private ImageView main_PopupWindow_iv;
    private PopupWindow window; //显示图片window
    String ImageUrl = "http://img4.imgtn.bdimg.com/it/u=3813655879,3975954109&fm=27&gp=0.jpg";
    String mp4Url = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_popupwinder);
        main_PopupWindow_nine = (NineGridView) findViewById(R.id.main_PopupWindow_nine);
        main_PopupWindow_nine.setImageLoader(new PicassoImageLoader());
        SetNineData();
        main_PopupWindow_iv = (ImageView) findViewById(R.id.main_PopupWindow_iv);
        main_PopupWindow_iv.setOnClickListener(this);
    }

    private void SetNineData() {
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl("http://img4.imgtn.bdimg.com/it/u=3813655879,3975954109&fm=27&gp=0.jpg");
            info.setBigImageUrl("http://img4.imgtn.bdimg.com/it/u=3813655879,3975954109&fm=27&gp=0.jpg");
            imageInfo.add(info);
        }
        main_PopupWindow_nine.setAdapter(new NineGridViewClickAdapter(this, imageInfo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_PopupWindow_iv:
                ArrayList<Bean> list = new ArrayList<>();

                //http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4
//                for (int i = 0; i < 6; i++) {
//                    Bean bean = new Bean();
//                    bean.setUrl(ImageUrl);
//                    list.add(bean);
//                }
                Bean bean = new Bean();
                bean.setUrl(mp4Url);
                list.add(bean);
                Bean bean1 = new Bean();
                bean1.setUrl(mp4Url);
                list.add(bean1);
                Bean bean2 = new Bean();
                bean2.setUrl(mp4Url);
                list.add(bean2);
                Bean bean3 = new Bean();
                bean3.setUrl(mp4Url);
                list.add(bean3);
//                List<ImageView> imageViewList = new ArrayList<>();
//                for (int i = 0; i < 6; i++) {
//                    ImageView imageView = new ImageView(this);
//                    Picasso.with(this).load(list.get(i).getUrl()).into(imageView);
//                    imageViewList.add(imageView);
//                }
//                scanPhoto(0, imageViewList);
                scanUrlPhoto(0, list);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void scanPhoto(int currentPosition, List<ImageView> imageViewList) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.full_show_photo_window, null);
        window = new PopupWindow(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, true);
        window.setFocusable(true);
        LinearLayout background = (LinearLayout) contentView.findViewById(R.id.ll_window_layout);
        final TextView page = (TextView) contentView.findViewById(R.id.tv_page);
        ViewPager viewPager = (ViewPager) contentView.findViewById(R.id.view_pager);
        final int totalSize = imageViewList.size();
        PhotoScanAdapter photoScanAdapter = new PhotoScanAdapter(imageViewList);
        viewPager.setAdapter(photoScanAdapter);
        photoScanAdapter.setOnItemClickListener(new PhotoScanAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(
                    View view, int position) {
                window.dismiss();
            }
        });
        viewPager.setCurrentItem(currentPosition);

        page.setText(String.valueOf(currentPosition + 1) + "/" + String.valueOf(totalSize));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                page.setText(String.valueOf(position + 1) + "/" + String.valueOf(totalSize));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window.setOutsideTouchable(false);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    /**
     * 优化，滑动时动态加载
     *
     * @param currentPosition
     * @param imageViewList
     */
    @SuppressLint("SetTextI18n")
    public void scanUrlPhoto(int currentPosition, final List<Bean> imageViewList) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.full_show_photo_window, null);
        window = new PopupWindow(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, true);
        window.setFocusable(true);
        LinearLayout background = (LinearLayout) contentView.findViewById(R.id.ll_window_layout);
        final TextView page = (TextView) contentView.findViewById(R.id.tv_page);
        ViewPager viewPager = (ViewPager) contentView.findViewById(R.id.view_pager);
//        standard = contentView.findViewById(R.id.videoplayer);
//        standard.setUp(mp4Url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "123456");
        final int totalSize = imageViewList.size();
        PhotoScanUrlAdapter photoScanAdapter = new PhotoScanUrlAdapter(PopupWindowActivity.this, imageViewList);
        viewPager.setAdapter(photoScanAdapter);
        photoScanAdapter.setOnItemClickListener(new PhotoScanUrlAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
//                if (imageViewList.get(position).getUrl().endsWith("mp4")) {
//                    standard.setVisibility(View.VISIBLE);
//                    view.setVisibility(View.GONE);
//                    standard.startVideo();
//                } else {
                    window.dismiss();
//                }
            }
        });
        viewPager.setCurrentItem(currentPosition);

        page.setText(String.valueOf(currentPosition + 1) + "/" + String.valueOf(totalSize));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                page.setText(String.valueOf(position + 1) + "/" + String.valueOf(totalSize));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window.setOutsideTouchable(false);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    class ClickListener implements com.bigkoo.convenientbanner.listener.OnItemClickListener {

        @Override
        public void onItemClick(int position) {

        }
    }

    /**
     * Picasso 加载
     */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}
