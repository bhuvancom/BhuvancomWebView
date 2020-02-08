package com.redcat.bhuvancomwebview;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

final class ShowMessage {

    /**
     * @param message message to show in snack bar
     * @param view    view in which the snack bar show
     */
    static void showMessage(String message, View view, boolean showInSnackBar, Context context) {
        if (showInSnackBar) {
            Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_SHORT);
        } else
            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }

}
