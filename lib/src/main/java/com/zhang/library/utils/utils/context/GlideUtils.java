package com.zhang.library.utils.utils.context;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhang.library.utils.R;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 图片加载工具
 *
 * @author ZhangXiaoMing 2020-08-17 10:06 星期一
 */
public class GlideUtils extends ContextUtils {

    /** 创建Glide加载图片的对象 */
    private static RequestManager getGlide() {
        return Glide.with(get());
    }

    /**
     * 加载图片
     *
     * @param url 图片链接地址
     */
    public static RequestBuilder<Drawable> load(Object url) {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.ic_no_image)
                .placeholder(R.mipmap.ic_no_image);

        return getGlide()
                .load(url)
                .apply(options);
    }

    /**
     * 加载图片
     *
     * @param url        图片链接地址
     * @param placeResId 默认图片
     */
    public static RequestBuilder<Drawable> load(Object url, int placeResId) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeResId);

        return getGlide()
                .load(url)
                .apply(options);
    }

    /**
     * 加载图片
     *
     * @param url     图片链接地址
     * @param options 加载参数
     */
    public static RequestBuilder<Drawable> load(Object url, RequestOptions options) {
        return getGlide()
                .load(url)
                .apply(options);
    }

    /**
     * 加载图片
     *
     * @param url     图片链接地址
     * @param options 加载参数
     * @param target  显示图片
     */
    public static void load(Object url, RequestOptions options, ImageView target) {
        load(url, options).into(target);
    }

    /**
     * 加载图片
     *
     * @param url        图片链接地址
     * @param errorResId 加载失败图片
     * @param placeResId 默认图片
     */
    public static RequestBuilder<Drawable> load(Object url, int errorResId, int placeResId) {
        RequestOptions options = new RequestOptions()
                .error(errorResId)
                .placeholder(placeResId);

        return getGlide()
                .load(url)
                .apply(options);
    }

    /**
     * 加载图片
     *
     * @param url    图片链接地址
     * @param target 显示图片
     */
    public static void load(Object url, ImageView target) {
        load(url).into(target);
    }

    /**
     * 下载图片
     *
     * @param url 图片链接地址
     */
    public static RequestBuilder<File> download(Object url) {
        return getGlide()
                .download(url);
    }

    /**
     * 加载图片
     *
     * @param url    图片链接地址
     * @param target 显示图片
     */
    public static void download(Object url, final ImageView target) {
        download(url)
                .into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        Uri uri = Uri.fromFile(resource);
                        target.setImageURI(uri);
                    }
                });
    }

    /**
     * 加载头像图片
     *
     * @param url 图片链接地址
     */
    public static RequestBuilder<Drawable> loadHead(Object url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()
                .error(R.mipmap.ic_no_image_round)
                .placeholder(R.mipmap.ic_no_image_round);

        return getGlide()
                .load(url)
                .apply(options);
    }

    /**
     * 加载头像图片
     *
     * @param url    图片链接地址
     * @param target 显示图片
     */
    public static void loadHead(Object url, ImageView target) {
        loadHead(url).into(target);
    }

    /**
     * 加载gif图片
     *
     * @param url 图片链接地址
     */
    public static RequestBuilder<GifDrawable> loadGif(Object url) {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.ic_no_image)
                .placeholder(R.mipmap.ic_no_image);

        return getGlide()
                .asGif()
                .load(url)
                .apply(options);
    }

    /**
     * 加载gif图片
     *
     * @param url    图片链接地址
     * @param target 显示gif图片
     */
    public static void loadGif(Object url, ImageView target) {
        loadGif(url).into(target);
    }

    /**
     * 加载圆角图片
     *
     * @param url    图片链接地址
     * @param radius 圆角角度，单位像素
     */
    public static RequestBuilder<Drawable> loadRadiusImaage(Object url, int radius) {
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.ic_no_image)
                .placeholder(R.mipmap.ic_no_image)
                .transform(new RoundedCorners(radius));

        return getGlide()
                .load(url)
                .apply(options);
    }

    /**
     * 加载圆角图片
     *
     * @param url    图片链接地址
     * @param radius 圆角角度，单位像素
     * @param target 显示图片
     */
    public static void loadRadiusImage(Object url, int radius, ImageView target) {
        loadRadiusImaage(url, radius).into(target);
    }
}
