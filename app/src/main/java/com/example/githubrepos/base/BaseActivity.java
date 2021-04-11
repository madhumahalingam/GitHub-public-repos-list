package com.example.githubrepos.base;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

public class BaseActivity extends Activity {

    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = getApplicationContext();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        hideSoftKeyboard(this);
        super.onDestroy();
    }

    /**
     * method to call close soft keyboard
     * @param activity - class reference
     */
    //hide Keyboard
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     *method to call check network available
     * @return - network available or not
     */
    public boolean isNetworkAvailable(){
        return isNetworkAvailable(getApplicationContext());
    }

    public static boolean isNetworkAvailable(Context mContext) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr != null) {
                return (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("NullPointerException","",e);
        }
        return false;
    }
}
