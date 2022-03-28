package com.animee.constellation.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadDataAsyncTask  extends AsyncTask<String,Void,String> {
    Context context;
    OnGetNetDataListener listener;
    ProgressDialog dialog;
    boolean isShowDialog = false;
    private void initDialog(){
        dialog = new ProgressDialog(context);
        dialog.setTitle("Notification");
        dialog.setMessage("Loading data......");
    }
    public LoadDataAsyncTask(Context context, OnGetNetDataListener listener,boolean isShowDialog) {
        this.context = context;
        this.listener = listener;
        this.isShowDialog = isShowDialog;
        initDialog();
    }

    public interface OnGetNetDataListener{
        public void onSuccess(String json);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isShowDialog) {
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String json = HttpUtils.getJSONFromNet(params[0]);
        return json;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (isShowDialog) {
            dialog.dismiss();
        }

        listener.onSuccess(s);
    }
}
