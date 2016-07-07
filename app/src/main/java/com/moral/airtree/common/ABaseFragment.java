package com.moral.airtree.common;


import android.support.v4.app.Fragment;

import com.moral.airtree.R;
import com.moral.airtree.widget.LoadDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;

/**
 * Created by bin.shen on 5/18/16.
 */
public abstract class ABaseFragment extends Fragment {
    public LoadDialog mLoadDialog;
    private AModel model = null;
    private ABaseActivity mBaseActivity = null;
    public int fragmentId = 0;

    public ABaseFragment() {
    }

    public ABaseFragment(int id) {
        this.fragmentId = id;
    }

    public ABaseFragment(AModel model) {
        this.model = model;
    }

    public ABaseFragment(AModel model, int id) {
        this.model = model;
        fragmentId = id;
    }

    public AModel getModel() {
        return model;
    }

    public void setModel(AModel model) {
        this.model = model;
    }

    public ABaseActivity getBaseActivity() {
        return (ABaseActivity)getActivity();
    }

    public ABaseActivity getmBaseActivity() {
        return mBaseActivity;
    }

    public void setmBaseActivity(ABaseActivity mBaseActivity) {
        this.mBaseActivity = mBaseActivity;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadDialog = new LoadDialog(getActivity(), getDialogThemeResId());
    }

    protected Integer getDialogThemeResId() {
        return R.style.load_dialog;
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        mLoadDialog.dismiss();
        super.onPause();
    }
}
