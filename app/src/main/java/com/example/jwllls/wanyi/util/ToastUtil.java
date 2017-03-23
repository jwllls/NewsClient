package com.example.jwllls.wanyi.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jwllls on 2016/12/7.
 */

public class ToastUtil {
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
