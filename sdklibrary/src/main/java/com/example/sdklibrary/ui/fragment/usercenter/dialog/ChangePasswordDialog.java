package com.example.sdklibrary.ui.fragment.usercenter.dialog;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseDialog;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.example.sdklibrary.ui.view.DialogTips;

/**
 * Date:2023-01-17
 * Time:18:56
 * author:colin
 */
public class ChangePasswordDialog extends SdkBaseDialog {


    private ImageView goback;
    private EditText oldpassword,newpassword,newpasswordConfirm;
    private ImageView clearOldpassword, clearNewpassword,clearNewpasswordConfirm;

    protected boolean oldpasswordTag,newpasswordTag,newpasswordConfirmTag;
    private Button confirm;

    private onCancelOnClickListener cancelOnClickListener;

    public ChangePasswordDialog(Activity act) {
        super(act);
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

    }

    public interface onCancelOnClickListener {
        public void onCancelClick();
    }
}
