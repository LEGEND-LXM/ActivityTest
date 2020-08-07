package com.example.androidthreadtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.text.PrecomputedText.Params;
import android.widget.Toast;

public class DownloadTsak extends AsyncTask<Void, Integer, Boolean> {

    private ProgressDialog progressDialog;
    private Object Context;

    @Override
    protected Boolean doInBackground(Void... voids) {
        while (true) {
            int downloadPercent = dowmload();  // 这里是一个虚构的方法,作用 ：计算当前下载进度并返回
            publishProgress(downloadPercent);  // 传入当前下载进度
            if (downloadPercent >= 100) {  // 判定下载是否完成
                break;
            }
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();  // 显示进度对话框
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // 更新下载进度
        // 此时的value为 publishProgress 方法传入
        progressDialog.setMessage("Dpwnload" + values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();  // 关闭进度对话框
        // 提示下载结果
        if (aBoolean) {
            Toast.makeText((android.content.Context) Context, "Download finish", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText((android.content.Context) Context, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }
}
