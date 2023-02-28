package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.config.ConstData;

/**
 * Date:2023-02-06
 * Time:19:30
 * author:colin
 *
 * 自定义Toast，在顶部弹出
 */
public class LoginSuccessToastView {
    private static Toast toast;
    public static void showToast( Context context, String str)
    {
        if (toast==null) {
            toast = new Toast(context);
//            toast = Toast.makeText(context, str,Toast.LENGTH_SHORT);
        }

//        toast.setDuration(duration);
        LayoutInflater inflater=LayoutInflater.from(context);
        View toast_view =inflater.inflate(R.layout.toast_login_success,null);
        ImageView img = toast_view.findViewById(R.id.toast_login_success_icon);
        TextView toastStr = toast_view.findViewById(R.id.toast_game);
        toastStr.setText("欢迎,"+str);
//        img.setImageURI("");

        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(toast_view);
        toast.show();
    }

}
