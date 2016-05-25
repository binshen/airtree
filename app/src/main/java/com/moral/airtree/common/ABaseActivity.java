package com.moral.airtree.common;

/**
 * Created by bin.shen on 5/18/16.
 */
import android.support.v4.app.FragmentActivity;
import android.os.Handler;
import com.moral.airtree.widget.LoadDialog;
import android.os.Bundle;

public abstract class ABaseActivity extends FragmentActivity {

    protected String basePath = "http://192.168.2.128:3000";

    public Handler mHandler;
    protected LoadDialog mLoadDialog;

//    public abstract void replaceContentWith(int p1, ABaseFragment p2);
//
//
//    public abstract void replaceContentWith(ABaseFragment p1);


    public void changeTitle(int id) {
    }

    public void changeTabSelectedColorState(int which) {
    }

    protected ABaseApplication application;


    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mLoadDialog = new LoadDialog(this);
        application = (ABaseApplication) getApplication();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        mLoadDialog.dismiss();
        super.onPause();
    }
}
