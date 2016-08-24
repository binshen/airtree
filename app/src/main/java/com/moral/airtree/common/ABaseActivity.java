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
                    //http://stackoverflow.com/questions/32699129/android-6-0-needs-restart-after-granting-user-permission-at-runtime?rq=1
                    //http://stackoverflow.com/questions/33062006/cant-write-to-external-storage-unless-app-is-restarted-after-granting-permissio
                    //manager.showNoticeDialog();
                    android.os.Process.killProcess(android.os.Process.myPid());
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //TODO
                } else {

                }
        }
    }

    public void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //int permission = PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }  else {
            manager.showNoticeDialog();
        }
    }
}
