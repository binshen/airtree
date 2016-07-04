package com.moral.airtree.common;

/**
 * Created by bin.shen on 5/18/16.
 */
import android.support.v4.app.FragmentActivity;
import com.moral.airtree.widget.LoadDialog;
import android.os.Bundle;

public abstract class ABaseActivity extends FragmentActivity {

    protected String basePath = AConstants.MORAL_API_BASE_PATH;

    protected LoadDialog mLoadDialog;

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
