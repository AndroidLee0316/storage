package com.pasc.lib.storage.database;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class DatabaseManager {
    private static DataBaseContext dataBaseContext;

    public static DatabaseManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final DatabaseManager instance = new DatabaseManager();
    }

    // 临时构建的构造函数，后面需要将userDBCls拆出来，不应该放这里
    public void init(Context base, Class<?> userDBCls) {
        dataBaseContext = new DataBaseContext(userDBCls, base);
        FlowManager.init(new FlowConfig.Builder(dataBaseContext).build());
    }

    public DataBaseContext getDataBaseContext() {
        return dataBaseContext;
    }

}
