package com.pasc.lib.storage.fileDiskCache;


import android.content.Context;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;


import javax.crypto.Cipher;

/**
 * 文件缓存类
 * by zc 2018-09-03 10:46:58
 */
class FileCacheProxy {
    private static final String TAG = FileCacheProxy.class.getName();
    private static DiskLruCacheHelper diskLruCacheHelper;
    private static Context context;
    private static FileCacheBuilder builder;

    public static FileCacheProxy getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final FileCacheProxy instance = new FileCacheProxy();
    }

//    private FileCacheProxy(){
//        try {
//            diskLruCacheHelper = new DiskLruCacheHelper(context, builder.dirName, builder.maxCacheSize);
//        } catch (Exception e) {
//            Log.e(TAG, "初始化缓存配置异常---->>>>");
//            e.printStackTrace();
//        }
//        context=null;
//        builder=null;
//    }
    /**
     * 初始化
     *
     * @param ctx
     * @param fileCacheBuilder 一些配置参数
     */
    public static void init(Context ctx, FileCacheBuilder fileCacheBuilder) {
        context=ctx.getApplicationContext();
        builder=fileCacheBuilder;
//        FileCacheProxy.getInstance();
        try {
            diskLruCacheHelper = new DiskLruCacheHelper(context, builder.dirName, builder.maxCacheSize);
        } catch (Exception e) {
            Log.e(TAG, "初始化缓存配置异常---->>>>");
            e.printStackTrace();
        }
        context=null;
        builder=null;

    }

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    @WorkerThread
    public String getAsString(String key) {
        if (diskLruCacheHelper == null) {
            Log.e(TAG, "获取数据异常---->>>>");
            return "";
//            throw new IllegalArgumentException("FileCacheProxy must init first");
        }
        /**解密**/
        String value= diskLruCacheHelper.getAsString(key);
        return value == null ? "" : value;
    }

    /**
     * 存储字符串
     *
     * @param key
     * @param value
     */
    @WorkerThread
    public void put(String key, String value) {
        if (diskLruCacheHelper == null) {
            Log.e(TAG, "存储数据异常---->>>>");
            return;
//            throw new IllegalArgumentException("FileCacheProxy must init first");
        }
        diskLruCacheHelper.put(key, value);

    }


    /**
     * 移除
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return diskLruCacheHelper.remove(key);
    }
}
