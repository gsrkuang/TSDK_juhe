package com.example.sdklibrary.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sdklibrary.R;


/**
 * Date:2022-04-06
 * Time:14:49
 * author:colin
 */
public class DialogTips extends Dialog
 {

    private Button cancel;//取消按钮
    private Button confirm;//登陆按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;// 外部传入设置标题文本
    private String messageStr;// 外部传入设置消息文本

    private String cancelStr; //外部传入 取消按钮文本
    private String loginStr; //外部传入 登陆按钮文本

    private onLoginOnClickListener loginOnClickListener;
    private onCancelOnClickListener cancelOnClickListener;

    public DialogTips(Context context) {
        super(context, R.style.TipsDialog);
    }

    public void setLoginOnClickListener(onLoginOnClickListener onLoginOnClickListener){
        this.loginOnClickListener = onLoginOnClickListener;
    }
    public void setCancelOnClickListener(onCancelOnClickListener onCancelOnClickListener){
        this.cancelOnClickListener = onCancelOnClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_tips);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    private void initEvent() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginOnClickListener!=null){
                    loginOnClickListener.onLoginClick();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancelOnClickListener !=null){
                    cancelOnClickListener.onCancelClick();
                }
            }
        });
    }

    private void initView() {
        confirm = (Button) findViewById(R.id.confirmTipsButton);
        cancel = (Button) findViewById(R.id.cancelTipsButton);
        titleTv = (TextView) findViewById(R.id.titleTips);
        messageTv = (TextView) findViewById(R.id.messageTips);

    }

    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
        if (loginStr != null) {
            confirm.setText(loginStr);
        }
        if (cancelStr != null) {
            cancel.setText(cancelStr);
        }
    }

    /**
     * 从外界Activity为Dialog设置标题
     */
    public void setTitle(String title){
        titleStr = title;
    }
    public void setMessage(String message){
        messageStr = message;
    }
    public void setLoginText(String loginText){
        loginStr = loginText;
    }

    public void setCancelText(String cancelText){
        cancelStr = cancelText;
    }

    public interface onLoginOnClickListener {
        public void onLoginClick();
    }

    public interface onCancelOnClickListener {
        public void onCancelClick();
    }

}
