package com.example.githubrepos.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.githubrepos.R;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

public class CommentUtil {
    MutableLiveData<String> commentData = new MutableLiveData<>();
    Activity activity;

    public CommentUtil(Activity activity) {
        this.activity = activity;
    }

    public void show_comment_view(String username) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.comment, null);
        alertDialog.setView(dialogView);
        TextView txt_title = dialogView.findViewById(R.id.title_comment);
        EditText edt_comment = dialogView.findViewById(R.id.edt_comment);
        txt_title.setText("Add comment for '"+username+"'");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = edt_comment.getText().toString();
                      if(!comment.equals("")){
                          commentData.postValue(comment);
                      }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public MutableLiveData<String> getCommentData(){
        return commentData;
    }
}
