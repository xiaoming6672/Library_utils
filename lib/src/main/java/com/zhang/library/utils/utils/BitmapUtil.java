package com.zhang.library.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片工具类
 */
public class BitmapUtil extends ContextUtils {

    /**
     * 转成圆形图片显示
     *
     * @param bitmap 图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2F;
            top = 0;
            bottom = width * 1F;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width * 1F;
        } else {
            roundPx = height / 2F;
            float clip = (width - height) / 2F;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;

    }

    /**
     * 将.9.png资源转换为指定大小的bitmap(保证不失真)输出
     *
     * @param resId  资源id
     * @param res    资源
     * @param width  指定宽
     * @param height 指定高
     */
    public static Bitmap convertNinePatchDrawableToBitmap(int resId, Resources res, int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap src = BitmapFactory.decodeResource(res, resId);
        Bitmap bitmap = src.copy(Config.ARGB_8888, true);
        NinePatchDrawable drawable = new NinePatchDrawable(res, bitmap, src.getNinePatchChunk(), new Rect(), null);
        drawable.setBounds(0, 0, width, height);
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        drawable.draw(canvas);
        return output;
    }

    /**
     * convert Bitmap to byte array
     *
     * @param b
     *
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    public static byte[] compressImageByQuality(Bitmap image) {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = (1024 * 200 * 100) / (baos.toByteArray().length);//已200KB比列压缩
        if (options < 100) {
            //			ULog.debug("compressImageByQuality", "the picture size is %s", baos.toByteArray().length / 1024 + "-->" + options);
            LogUtils.debug("compressImageByQuality", "the picture size is %s", baos.toByteArray().length / 1024 + "-->" + options);
            baos.reset();//重置baos即清空baos
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

            /********************* 保存图片到本地 ***************************/
            //ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            //Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            //saveBitMap(bitmap, "size-" + baos.toByteArray().length / 1024, Bitmap.CompressFormat.JPEG);

        } else {
            //			ULog.debug("compressImageByQuality", "the picture size is %s", baos.toByteArray().length / 1024);
            LogUtils.debug("compressImageByQuality", "the picture size is %s", baos.toByteArray().length / 1024);
        }

        /************************************ 循环压缩 ***************************/
        /*while (baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            ULog.debug("compressImageByQuality", "the picture size is %s", baos.toByteArray().length / 1024);
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            if (options < 0)
                break;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            saveBitMap(bitmap, "size-" + baos.toByteArray().length / 1024, Bitmap.CompressFormat.JPEG);
        }*/
        //        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        //        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return baos.toByteArray();
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     *
     * @return
     */
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * convert byte array to Bitmap
     *
     * @param b
     *
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public static Bitmap byteToBitmap(byte[] b, int size) {
        Bitmap bitmap = null;
        if (b == null || b.length == 0) {
            bitmap = null;
        } else {
            if (size <= 0) {
                size = 83;
            }
            Options options = new Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Config.RGB_565;
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;
            if (outWidth > outHeight) {
                options.inSampleSize = outWidth / size;
            } else {
                options.inSampleSize = outHeight / size;
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
        }
        return bitmap;
    }

    /**
     * convert Drawable to Bitmap
     *
     * @param d
     *
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param b
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     *
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     *
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     *
     * @param org
     * @param newWidth
     * @param newHeight
     *
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     *
     * @param org
     * @param scaleWidth  sacle of width
     * @param scaleHeight scale of height
     *
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    /**
     * 把bitmap保存为本地图片
     *
     * @param bitMap
     * @param fileName 保存图片名称，不需要图片的后缀
     * @param format   图片类型，jpg或者png
     */
    public static void saveBitMap(Bitmap bitMap, String fileName, CompressFormat format) {
        File f = new File("sdcard/" + fileName + ".png");
        try {
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitMap.compress(format, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存拍摄的照片到手机的sd卡
     *
     * @return
     */
    public static String saveBitmapToLocal(Context context, Bitmap bitmap) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;// 字节数组输出流

        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
            byte[] byteArray = baos.toByteArray();// 字节数组输出流转换成字节数组
            // + UUID.randomUUID().toString();
            String picName = UUID.randomUUID().toString() + ".jpg";
            File file = new File(FileUtils.getCachePath(), picName);
            // 讲字节数组写入到刚创建的图片文件中
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);

            // 发送广播给图片，通知图库更新，这样进入相册就可以找到保存的图片了
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);

            return uri.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 缩小图片
     * <p>
     * if width willing be zoom is bigger than default draw width, then return default draw
     * immediately;
     * </P>
     *
     * @param drawable
     * @param w
     * @param h
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        if (null == drawable) {
            return drawable;
        }

        int width = drawable.getIntrinsicWidth();
        if (w > width) {
            return drawable;
        }

        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        oldbmp.recycle();

        return new BitmapDrawable(newbmp);
    }

    public static Bitmap drawableToBitmap_(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将uri转换成String路径
     *
     * @param uri
     *
     * @return
     * @throws Exception
     */
    public static String convertUriToFilePath(Uri uri, Context context) throws Exception {
        String[] projects = new String[]{MediaStore.Images.Media.DATA /*, MediaStore.Images.Media.ORIENTATION*/};
        @SuppressWarnings("deprecation")
        Cursor cursor = ((Activity) context).managedQuery(uri, projects, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        //String orientation = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION));
        if (VERSION.SDK_INT < 14) {
            cursor.close();
        }
        //        cursor.close();
        return imagePath;
    }

    /**
     * 旋转图片角度
     *
     * @param degree
     * @param bitmap
     *
     * @return
     */
    public static Bitmap rotatingHeadImg(int degree, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 通过Base64将Bitmap转换成Base64字符串
     *
     * @param bitmap
     */
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 50, baos);// 参数100表示不压缩
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
