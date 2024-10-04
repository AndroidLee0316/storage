package com.pasc.lib.storage.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;


public class DbFlowManager {

    /***为了兼容之前数据库没加密，而用加密的去打开没加密的会崩溃**/
    public static final String dbExStr = "NoCipher";


    /***
     * 初始化数据库
     * @param context
     * @param databaseHolderClass
     * @param databaseClass 数据库类
     */
    public static void initDb(@NonNull Context context,
                              @NonNull Class<? extends DatabaseHolder> databaseHolderClass,
                              @NonNull Class<?> databaseClass) {
        FlowManager.init (FlowConfig.builder (context)
                .addDatabaseHolder (databaseHolderClass)
                .addDatabaseConfig (dbConfig (context, databaseClass, getDefaultDbName (databaseClass)))
                .build ());
    }


    /***
     * 数据库加密配置
     * @param context
     * @param databaseClass
     * @param databaseName
     * @return
     */
    private static DatabaseConfig dbConfig(final @NonNull Context context, @NonNull Class<?> databaseClass, @NonNull String databaseName) {
        if (databaseName == null || TextUtils.isEmpty (databaseName)) {
            databaseName = databaseClass.getName () + dbExStr;
        }
        return new DatabaseConfig.Builder (databaseClass)
                .databaseName (databaseName)
                // 不用数据库加密了，会有问题
//                .openHelper (new DatabaseConfig.OpenHelperCreator () {
//                    @Override
//                    public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
//                        return new SQLCipherHelperImpl (context, databaseDefinition, helperListener);
//                    }
//                })
                .build ();
    }

    /**
     * 切换数据库
     *
     * @param databaseClass 数据库 class
     * @param databaseName  数据库名称  格式 getDefaultDbName（class）+ _userId
     */
    public static void switchDb(@NonNull Class<?> databaseClass, @NonNull String databaseName) {
        FlowManager.getDatabase (databaseClass).reopen
                (new DatabaseConfig.Builder (databaseClass).databaseName (databaseName).build ());

    }

    /**
     * 获取默认的数据库名称
     * @param databaseClass
     * @return
     */
    public static String getDefaultDbName(@NonNull Class<?> databaseClass){
        return databaseClass.getName () + dbExStr;
    }
}
