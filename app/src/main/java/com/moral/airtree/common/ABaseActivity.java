package com.moral.airtree.common;

/**
 * Created by bin.shen on 5/18/16.
 */
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.moral.airtree.R;
import com.moral.airtree.update.UpdateManager;
import com.moral.airtree.widget.LoadDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

public abstract class ABaseActivity extends FragmentActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected String basePath = AConstants.MORAL_API_BASE_PATH;

    protected LoadDialog mLoadDialog;

    protected ABaseApplication application;

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        mLoadDialog = new LoadDialog(this, getDialogThemeResId(), getCanceledOnTouch());
        application = (ABaseApplication) getApplication();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        mLoadDialog.dismiss();
        super.onPause();
    }

    protected Integer getDialogThemeResId() {
        return R.style.load_dialog;
    }

    protected boolean getCanceledOnTouch() {
        return false;
    }

//////////////////////////////////////////////////////////////////////
    protected UpdateManager manager = new UpdateManager(this);

    private static final int REQUEST_EXTERNAL_STORAGE = 0x1;
    private static String[] PERMISSIONS_STORAGE = { Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    manager.showNoticeDialog();
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //TODO
                } else {

                }
        }
    }

    public void verifyStoragePermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //int permission = PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            manager.showNoticeDialog();
        }
    }
}
