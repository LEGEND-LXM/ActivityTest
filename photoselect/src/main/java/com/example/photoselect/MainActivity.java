package com.example.photoselect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;

public class MainActivity extends AppCompatActivity {

    private ImageView showPhoto;

    public static final int CHOOSE_PHOTO = 1;
    // 判断 android 版本是否是Android 10.0以上
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button selectPhoto = findViewById(R.id.select_photo);
        showPhoto = findViewById(R.id.show_photo);

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                } else {
                    // 打开相册操作
                    openAlbum();
                }
            }
        });
    }

    /**
     * 打开相册
     * */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * 运行时权限回调函数
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CHOOSE_PHOTO :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                    Log.d("自定义调试", "运行时权限授予成功");
                } else {
                    Toast.makeText(this, "denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 关闭开启活动后的回调函数
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO :
                if (resultCode == RESULT_OK) {
                    String imagePath = parseUri(data);
                    displayImage(imagePath);
                }
        }
    }



    /**
     * 解析Uri
     * */
    public String parseUri(Intent data) {
        Uri uri=data.getData();
        String imagePath;
        // 第二个参数是想要获取的数据
        Cursor cursor = getContentResolver()
                .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA},
                        null, null, null);
        if (cursor == null) {
            imagePath = uri.getPath();
        } else {
            cursor.moveToFirst();
            // 获取数据所在的列下标
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            imagePath = cursor.getString(index);  // 获取指定列的数据
            cursor.close();
        }
        return imagePath;  // 返回图片地址
    }

    /**
     * 展示图片
     * */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bitmap = getBitmapFromUri(this, getImageContentUri(this,imagePath));
            showPhoto.setImageBitmap(bitmap);
            Log.d("自定义调试", "展示成功");
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    // 通过uri加载图片
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Uri getImageContentUri(Context context, String path) {
        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { path }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (new File(path).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}
