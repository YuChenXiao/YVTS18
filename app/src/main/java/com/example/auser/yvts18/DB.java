package com.example.auser.yvts18;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by auser on 2017/11/7.
 */

public class DB {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "notes";
    private static final String DATABASE_CREATE =
    "CREATE TABLE IF NOT EXISTS "+DATABASE_TABLE+"(_id INTEGER PRIMARY KEY,uname TEXT,email TEXT ,phone TEXT);";


    private static class DatabaseHelper extends SQLiteOpenHelper {
        Context mCtx;
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mCtx =context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            Toast.makeText(mCtx,"資料表更新完畢",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    private Context mCtx = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context ctx) {
        this.mCtx = ctx;
    }
       //資料庫開啟
    public DB open() throws SQLException {
        dbHelper = new DatabaseHelper(mCtx);
        db = dbHelper.getWritableDatabase();
        return this;
    }
        //資料庫關閉
    public void close() {
        dbHelper.close();
    }
    public static final String KEY_ROWID ="_id";
    public  static final String KEY_UNAME ="uname";
    public  static final String KEY_PHONE ="phone";
    public  static final String KEY_EMAIL ="email";
    String[] strCols = new String[]{
                KEY_ROWID,KEY_UNAME,KEY_PHONE,KEY_EMAIL
        };

        //查詢全部資料表
        public Cursor getAll(){
    //      return db.rawQuery("SELECT * FROM "+NEW_DATABASE_TABLE+"",null);
            return db.query(DATABASE_TABLE,strCols,null,null,null,null,null);
        }
        //新增資料
        public long create(String uname ,String phone,String email){
//            Date now = new Date();
            ContentValues args =new ContentValues();
            args.put(KEY_UNAME,uname);
            args.put(KEY_PHONE,phone);
            args.put(KEY_EMAIL,email);
            return db.insert(DATABASE_TABLE,null,args);
        }
        //刪除資料
        public  boolean delete(long rowId){
            if(rowId>0) {
                return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
            }else {
            return db.delete(DATABASE_TABLE,null, null) > 0;
        }
    }
//        //刪除全部資料
//    public  boolean delete(){
//        return delete(-1);
//    }
        //更新資料
    public boolean update(long rowId ,String uname ,String phone,String email){
        ContentValues args = new ContentValues();
        args.put(KEY_UNAME, uname);
        args.put(KEY_PHONE, phone);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
