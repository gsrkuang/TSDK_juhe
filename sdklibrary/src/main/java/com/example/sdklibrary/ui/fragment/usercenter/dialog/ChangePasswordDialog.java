package com.example.sdklibrary.ui.fragment.usercenter.dialog;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseDialog;
import com.example.sdklibrary.mvp.Imp.ChangePasswordPresenterImp;
import com.example.sdklibrary.mvp.Imp.ForgetPasswordPresenterImp;
import com.example.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.example.sdklibrary.mvp.model.MVPChangePasswordBean;
import com.example.sdklibrary.mvp.model.MVPForgetPasswordBean;
import com.example.sdklibrary.mvp.presenter.ChangePasswordPresenter;
import com.example.sdklibrary.mvp.presenter.ForgetPasswordPresenter;
import com.example.sdklibrary.mvp.view.MVPChangePasswordView;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.example.sdklibrary.ui.fragment.login.ForgetPasswordFragment;
import com.example.sdklibrary.ui.view.DialogTips;

/**
 * Date:2023-01-17
 * Time:18:56
 * author:colin
 */
public class ChangePasswordDialog extends SdkBaseDialog implements MVPChangePasswordView {


    private ImageView goback;
    private EditText oldpassword,newpassword,newpasswordConfirm;
    private String mOldpassword,mNewpassword,mNewpasswordConfirm;
    private ImageView clearOldpassword, clearNewpassword,clearNewpasswordConfirm;

    protected boolean oldpasswordTag,newpasswordTag,newpasswordConfirmTag;
    private Button confirm;

    private ChangePasswordPresenter changePasswordPresenterImp;

    private onCancelOnClickListener cancelOnClickListener;
    private onConfirmSuccessListener confirmSuccessListener;

    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    private final String PASSWORD_FORMERROR = "密码长度格式错误";
    private final String PASSWORD_NOT_SAME = "新密码与确认密码不一致";
    private final String LENGTH_EMPTY = "输入密码为空";
    private final String CHANGE_SUCCESS = "密码修改成功";


    private Activity act ;
    public ChangePasswordDialog(Activity act) {
        super(act);
        this.act = act;
    }

    public void setCancelOnClickListener(onCancelOnClickListener onCancelOnClickListener){
        this.cancelOnClickListener = onCancelOnClickListener;
    }

    public void setConfirmSuccessListener(onConfirmSuccessListener confirmSuccessListener){
        this.confirmSuccessListener = confirmSuccessListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.changepassword;
    }

    @Override
    public void initViews() {
        oldpassword = $(R.id.change_oldpassword);
        newpassword = $(R.id.change_newpassword);
        clearOldpassword = $(R.id.iv_clear_oldpassword);
        clearNewpassword = $(R.id.iv_clear_newpassword);
        clearNewpasswordConfirm = $(R.id.iv_clear_newpasswordconfirm);
        confirm = $(R.id.confirm);

        newpasswordConfirm = $(R.id.change_newpassword_confirm);
        goback = $(R.id.goback);
    }

    @Override
    public void initListener() {
        setOnClick(confirm);
        setOnClick(clearOldpassword);
        setOnClick(clearNewpassword);
        setOnClick(clearNewpasswordConfirm);
        setOnClick(goback);
        setEditTextListener();
    }

    public void setEditTextListener() {
        oldpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0) {
                    clearOldpassword.setVisibility(View.VISIBLE);
                    oldpasswordTag = true;
                } else {
                    clearOldpassword.setVisibility(View.INVISIBLE);
                    oldpasswordTag = false;
                }
                checkConfirmEnable(oldpasswordTag, newpasswordTag, newpasswordConfirmTag);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0) {
                    clearNewpassword.setVisibility(View.VISIBLE);
                    newpasswordTag = true;
                } else {
                    clearNewpassword.setVisibility(View.INVISIBLE);
                    newpasswordTag = false;
                }

                checkConfirmEnable(oldpasswordTag, newpasswordTag, newpasswordConfirmTag);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newpasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0) {
                    clearNewpasswordConfirm.setVisibility(View.VISIBLE);
                    newpasswordConfirmTag = true;
                } else {
                    clearNewpasswordConfirm.setVisibility(View.INVISIBLE);
                    newpasswordConfirmTag = false;
                }

                checkConfirmEnable(oldpasswordTag, newpasswordTag, newpasswordConfirmTag);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancelOnClickListener !=null){
                    cancelOnClickListener.onCancelClick();
                }
            }
        });
    }

    public void checkConfirmEnable(boolean oldpasswordTag ,boolean newpasswordTag , boolean newpasswordConfirmTag){
        if (oldpasswordTag && newpasswordTag && newpasswordConfirmTag){
            confirm.setEnabled(true);
        }else {
            confirm.setEnabled(false);
        }
    }

    @Override
    public void initData() {
        changePasswordPresenterImp = new ChangePasswordPresenterImp();
        changePasswordPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirm){
            confirmMethod();
        }else if (id == R.id.goback){

        }else if (id == R.id.iv_clear_oldpassword) {
            oldpassword.setText("");
        } else if (id == R.id.iv_clear_newpassword) {
            newpassword.setText("");
        }else if (id == R.id.iv_clear_newpasswordconfirm) {
            newpasswordConfirm.setText("");
        }
    }

    private void confirmMethod(){

        mOldpassword = oldpassword.getText().toString().trim();
        mNewpassword = newpassword.getText().toString().trim();
        mNewpasswordConfirm = newpasswordConfirm.getText().toString().trim();

        oldpasswordTag = (mOldpassword.length() > PASSWORD_MIN_LENGTH) && (mOldpassword.length() < PASSWORD_MAX_LENGTH);
        newpasswordTag = (mNewpassword.length() > PASSWORD_MIN_LENGTH) && (mNewpassword.length() < PASSWORD_MAX_LENGTH);
        newpasswordConfirmTag = (mNewpasswordConfirm.length() > PASSWORD_MIN_LENGTH) && (mNewpasswordConfirm.length() < PASSWORD_MAX_LENGTH);

        if ((TextUtils.isEmpty(mOldpassword)) && (TextUtils.isEmpty(mNewpassword))&& (TextUtils.isEmpty(mNewpasswordConfirm))    ) {
            showToast(LENGTH_EMPTY);
            return;

        } else {
            if(!mNewpassword.equals(mNewpasswordConfirm)){
                showToast(PASSWORD_NOT_SAME);
                return;
            }
            if (oldpasswordTag && newpasswordTag && newpasswordConfirmTag) {
                MVPChangePasswordBean bean = new MVPChangePasswordBean(mOldpassword, mNewpassword,mNewpasswordConfirm);
                changePasswordPresenterImp.change(bean, act);
            } else {
                showToast(PASSWORD_FORMERROR);
                return;
            }
        }

    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void success(String msg, String data) {
        if( confirmSuccessListener!=null){
            confirmSuccessListener.success();
        }
        if(cancelOnClickListener !=null){
            cancelOnClickListener.onCancelClick();
        }
        showToast(CHANGE_SUCCESS);
    }

    @Override
    public void fail(String msg, String data) {
//        showToast(msg);
    }



    public interface onCancelOnClickListener {
         void onCancelClick();
    }
    public interface onConfirmSuccessListener {
         void success();
    }
}
