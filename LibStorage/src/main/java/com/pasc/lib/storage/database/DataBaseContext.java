package com.pasc.lib.storage.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.InvalidDBConfiguration;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;

import java.io.File;

/**
 * 数据库专用Context
 * Created by duyuan797 on 17/10/30.
 */

public class DataBaseContext extends ContextWrapper {

    public String userId;
    private Class<?> mUserDBCls = null;

    public DataBaseContext(Class<?> userDBCls, Context base) {
        super(base.getApplicationContext());
        mUserDBCls = userDBCls;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName(String name) {
        try{
            if (FlowManager.getDatabase(mUserDBCls).getDatabaseName().equals(name)) {
                if (!TextUtils.isEmpty(userId)) {
                    name = String.format("%s_%s", userId, name);
                }
            }
        }catch (InvalidDBConfiguration e){
            e.printStackTrace();
        }

        return name;
    }

    /**
     * 切换用户数据库
     */
    public void switchUserDb(String userId) {
        // 停止当前用户数据库的处理事务
        // userName设置成功空以绕过DBFlow删除数据库的处理
        setUserId("");
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(mUserDBCls);
        databaseDefinition.reset();

        // 重新打开用户数据库，设置userId为新数据库名字的前缀
        OpenHelper openHelper = databaseDefinition.getHelper();
        openHelper.closeDB();
        setUserId(String.valueOf(userId));
        openHelper.getDatabase();
    }

    @Override
    public File getDatabasePath(String name) {
        return super.getDatabasePath(getName(name));
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(getName(name), mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return super.openOrCreateDatabase(getName(name), mode, factory, errorHandler);
    }

    /**
     * DBFlow中会获取一次AppContext，导致不会按照这个重写的类的方法执行，所以重写本方法直接返回本类实例
     *
     * @return
     */
    @Override
    public Context getApplicationContext() {
        return this;
    }
}