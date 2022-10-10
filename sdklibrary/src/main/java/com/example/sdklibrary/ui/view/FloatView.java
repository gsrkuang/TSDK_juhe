package com.example.sdklibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sdklibrary.R;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * Date:2022-09-29
 * Time:14:53
 * author:colin
 */
public class FloatView extends FrameLayout implements View.OnTouchListener {

    public FloatView(@NonNull Context context) {
        super(context);
        initView(context);
    }

//    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context);
//    }

    private void initView(Context context) {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

//        layoutParams = lp;
//
//        val imageView = ShapeableImageView(context)
//        imageView.setImageResource(R.mipmap.ic_avatar)

        ShapeableImageView imageView = new ShapeableImageView(context);
        imageView.setImageResource(R.drawable.gamesdk_username);
//        ImageView imageView = new ImageView(context);
//        imageView.set
//        imageView.setImageResource(R.drawable.gamesdk_username);

        addView(imageView);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        float mDownX = 0;
        float mDownY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                offsetTopAndBottom((int) (y - mDownY));
                offsetLeftAndRight((int) (x - mDownX));

                break;
            case MotionEvent.ACTION_UP:

                break;
            default:

        }

        return true;
    }
}
