package com.zhang.library.library_utils;

import android.os.Bundle;
import android.widget.ImageView;

import com.zhang.library.utils.utils.context.ContextUtils;
import com.zhang.library.utils.utils.context.GlideUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextUtils.set(getApplicationContext());

        ImageView iv_image = findViewById(R.id.iv_image);

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597656168777&di=7690ab1d76a584ab9e02849bf1319a00&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D3571592872%2C3353494284%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1200%26h%3D1290";

        GlideUtils.loadHead(R.drawable.ic_no_image, iv_image);
    }
}