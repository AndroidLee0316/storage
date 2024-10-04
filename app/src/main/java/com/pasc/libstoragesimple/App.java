package com.pasc.libstoragesimple;

import android.app.Application;

import com.pasc.lib.safe.SafeUtil;
import com.pasc.lib.storage.database.DbFlowManager;
import com.pasc.lib.storage.fileDiskCache.FileCacheBuilder;
import com.pasc.lib.storage.fileDiskCache.FileCacheUtils;
import com.pasc.libstoragesimple.model.UserDb;
import com.raizlabs.android.dbflow.config.appGeneratedDatabaseHolder;

/**
 * Created by yintangwen952 on 2018/9/6.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库操作
//        FlowManager.init (FlowConfig.builder(this)
//                .addDatabaseHolder (appGeneratedDatabaseHolder.class)
//                .addDatabaseConfig (new DatabaseConfig.Builder (UserDb.class).databaseName ("user").build ())
//                .build());
//        DatabaseManager.getInstance().init(this, UserDb.class);
//        SafeUtil.init (this);
        DbFlowManager.initDb (this,appGeneratedDatabaseHolder.class,UserDb.class);
//        OtherManager.initDb (this);
        FileCacheUtils.init(this,new FileCacheBuilder());
    }
}
