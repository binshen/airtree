package com.moral.airtree.widget;

import android.graphics.drawable.AnimationDrawable;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;

import com.moral.airtree.R;

/**
 * Created by bin.shen on 5/18/16.
 */
public class LoadDialog {

    public Dialog mDialog;
    private AnimationDrawable mAnimationDrawable = null;

    public LoadDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lay_load_dialog, null);
        ImageView loadingImage = (ImageView)view.findViewById(R.id.iv_progress);
        loadingImage.setImageResource(R.anim.load_dialog_animation);
        mAnimationDrawable = (AnimationDrawable)loadingImage.getDrawable();
        if(mAnimationDrawable != null) {
            mAnimationDrawable.setOneShot(false);
            mAnimationDrawable.start();
        }
        mDialog = new Dialog(context, R.style.load_dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void show() {
        mDialog.show();
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
    }

    public void dismiss() {
        if(mDialog.isShowing()) {
            mDialog.dismiss();
            mAnimationDrawable.stop();
        }
    }

    public boolean isShowing() {
        if(mDialog.isShowing()) {
            return true;
        }
        return false;
    }
}