package com.example.cameralum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int takePhoto = 1;
    private static final int fromAlbum = 2;
    private Uri imageUri;
    private ImageView imageView;
    private File outputImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button takePhotoBtn = findViewById(R.id.takePhotoBtn);  // 拍照按钮
        Button fromAlbumByn = findViewById(R.id.fromAlbumByn);  // 选择按钮

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 拍照逻辑
                // 创建File对象，用于存储拍照后的图片
                // getExternalCacheDir() : 获取应用关联缓存目录（可以跳过运行时权限处理）
                outputImage = new File(getExternalCacheDir(),"out_image.jpg");
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  // 版本是否大于 7.0
                    // 有疑问，待定
                    /** 从7.0版本开始，直接使用本地Uri被认为不安全
                     * FileProvider.getUriForFile的三个参数
                     * 第一个 ：context对象
                     * 第二个 ：任意唯一字符串
                     * 第三个 ：File对象
                     * */
                    imageUri = FileProvider.getUriForFile(getBaseContext(), "com.example.cameralum.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                // 启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, takePhoto);
            }
        });

        fromAlbumByn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开文件选择器
                Intent intent = new  Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // 指定只显示图片
                intent.setType("image/*");
                startActivityForResult(intent, fromAlbum);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case takePhoto :
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的图片显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap bitmap1 = rotateIfRequired(bitmap);
                        if (bitmap1 != null) {
                            imageView.setImageBitmap(rotateIfRequired(bitmap));
                        } else {
                            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case fromAlbum :
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        Bitmap bitmap = getBitmapFromUri(data.getData());
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    // 转换成Bitmap对象
    private Bitmap getBitmapFromUri(Uri uri) throws FileNotFoundException {
        ParcelFileDescriptor r = getContentResolver().openFileDescriptor(uri, "r");
          return   BitmapFactory.decodeFileDescriptor(r.getFileDescriptor());
    }

    // 判断是否需要旋转
    private Bitmap rotateIfRequired(Bitmap bitmap) throws IOException {
        ExifInterface exif = new  ExifInterface(outputImage.getPath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90 :
                return rotateBitmap(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180 :
                return rotateBitmap(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270 :
                return rotateBitmap(bitmap, 270);
            default:
                return bitmap;
        }
    }
    // 将图片旋转一定角度
    private Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) degree);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();  // 将不需要的bitmap对象回收
        return rotatedBitmap;
    }
}
