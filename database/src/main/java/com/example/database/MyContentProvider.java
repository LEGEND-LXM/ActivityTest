package com.example.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "eom.example.database.provider";

    private DatabaseHelper dbHelper;
    public static UriMatcher uriMatcher;  // 创建 UriMatcher 实例

    static {
        uriMatcher = new UriMatcher(uriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR :
                return db.delete("Book", selection, selectionArgs);
            case BOOK_ITEM :
                String bookId = uri.getPathSegments().get(1);
                return db.delete("Book", "id=?", new String[] {bookId});
            case CATEGORY_DIR :
                return db.delete("Category", selection, selectionArgs);
            case CATEGORY_ITEM :
                String categoryId = uri.getPathSegments().get(1);
                return db.delete("Category", "id=?", new String[] {categoryId});
            default:
                return -1;
        }

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR :
                return "vnd.android.cursor.dir/vnd.com.example.database..provider.book";
            case BOOK_ITEM :
                return "vnd.android.cursor.item/vnd.com.example.database..provider.book";
            case CATEGORY_DIR :
                return "vnd.android.cursor.dir/vnd.com.example.database..provider.category";
            case CATEGORY_ITEM :
                return "vnd.android.cursor.item/vnd.com.example.database..provider.category";
            default:
                return null;
        }
    }

    /**
     * 此方法返回一个能表示这条新增数据的URI，我们需要调用Uri.parse()将内容URI转化为Uri对象，
     * 以新增数据的ID结尾
     * */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR :
            case BOOK_ITEM :
                long newBookID = db.insert("Book", null, values);
                return Uri.parse("content://" + AUTHORITY + "/book/" + newBookID);
            case CATEGORY_DIR :
            case CATEGORY_ITEM :
                long newCategoryID = db.insert("Category", null, values);
                return Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryID);
            default:
                return null;
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new DatabaseHelper(getContext(), "BookStory.db", null, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR :
                return db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
            case BOOK_ITEM :
                /**
                 * uri.getPathSegments().get(1); : 将内容URI权限之后的部分以“/”进行分割，把分割后的结果存放在一个字符串列表中，
                 * 第2号位存放的是路径，第一个位置是id
                 * */
                String bookID = uri.getPathSegments().get(1);
                return db.query("Book", projection, "id=?", new String[] {bookID}, null, null, sortOrder);
            case CATEGORY_DIR :
                return db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
            case CATEGORY_ITEM :
                String categoryID = uri.getPathSegments().get(1);
                return db.query("Category", projection, "id=?", new String[] {categoryID}, null, null, sortOrder);
            default:
                return null;
        }

    }
    /**
     * 返回值为被删除的行数
     * */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR :
                return db.update("Book", values, selection, selectionArgs);
            case BOOK_ITEM :
                String bookID = uri.getPathSegments().get(1);
                return db.update("Book", values, "id=?", new String[] {bookID});
            case CATEGORY_DIR :
                return db.update("Category", values, selection, selectionArgs);
            case CATEGORY_ITEM :
                String categoryID = uri.getPathSegments().get(1);
                return db.update("Category", values, "id=?", new String[] {categoryID});
            default:
                return -1;
        }
    }
}
