package com.example.wechat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wechat.gloab.WeChatApplication;

/**
 * author:salmonzhang
 * Description:保存联系人的数据库
 * Date:2017/8/14 0014 20:01
 */

public class ContactSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "contact.db";
    private static final int DB_VERSION = 1;

    private ContactSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ContactSQLiteOpenHelper(){
        this(WeChatApplication.context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table t_contact(_id integer primary key,contact varchar(20),username varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
