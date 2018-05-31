package com.example.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rxjava.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;



/**
 * 作者： ZlyjD.
 * 时间：2018/4/16.
 */

public class Loader {
    //宽高比
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> style) {
        showLoading(context, style.name());
    }

    private static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
//        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);

        AVLoadingIndicatorView AvView = (AVLoadingIndicatorView) view.findViewById(R.id.avloadingIndicatorView_BallClipRotatePulse);
//        dialog.setContentView(avLoadingIndicatorView);

        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        AvView.setIndicator(type);

//        int deviceWight = DimenUtil.getScreenWindth();
//        int deviceHeight = DimenUtil.getScreenHeight();
//
//       final Window dialgwinder = dialog.getWindow();
//        if (dialgwinder != null) {
//            WindowManager.LayoutParams lp = dialgwinder.getAttributes();
//            lp.width = deviceWight / LOADER_SIZE_SCALE;
//            lp.height = deviceHeight / LOADER_SIZE_SCALE;
//            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
//            lp.gravity = Gravity.CENTER;
//        }
        LOADERS.add(dialog);
        dialog.show();
        AvView.smoothToShow();
    }

    //显示Loader
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
