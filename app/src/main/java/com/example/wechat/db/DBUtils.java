package com.example.wechat.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wechat.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * author:salmonzhang
 * Description:数据库工具类
 * Date:2017/8/14 0014 20:14
 */

public class DBUtils {

    public static List<String> getContact(String username){

        List<String> contactList = new ArrayList<>();

        ContactSQLiteOpenHelper contactSQLiteOpenHelper = new ContactSQLiteOpenHelper();
        SQLiteDatabase database = contactSQLiteOpenHelper.getReadableDatabase();
        //select contact from contact where username = "zhangsan" order by contact
        Cursor cursor = database.query(Constant.DB_NAME,new String[]{"contact"}, "username = ?", new String[]{username}, null, null, "contact");
        while (cursor.moveToNext()) {
            String contact = cursor.getString(0);
            contactList.add(contact);
        }

        //关闭资源
        database.close();
        cursor.close();
        contactSQLiteOpenHelper.close();
        return contactList;
    }

    /**
     * 先删除username的所有好友，然后再重新插入
     * @param contactsList
     * @param username
     */
    public static void updateContacts(List<String> contactsList, String username) {
        ContactSQLiteOpenHelper contactSQLiteOpenHelper = new ContactSQLiteOpenHelper();
        SQLiteDatabase database = contactSQLiteOpenHelper.getReadableDatabase();

        //用事务管理数据的删除和插入
        //开启事务
        database.beginTransaction();
        //delete from t_contact where username "zhangsan"
        database.delete(Constant.DB_NAME,"username = ?",new String[]{username});
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        for (String contacts : contactsList) {
            //insert into t_contact(contact,username) values("zhangsan","lisi");
            contentValues.put("contact", contacts);
            database.insert(Constant.DB_NAME,null, contentValues);
        }

        //事务成功
        database.setTransactionSuccessful();
        //事务结束
        database.endTransaction();
        database.close();
        contactSQLiteOpenHelper.close();
    }
}
