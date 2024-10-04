package com.pasc.lib.storage.fileDiskCache;

import android.text.TextUtils;

/**
 * 配置文件
 */
public final class FileCacheBuilder {

    protected String dirName;
    protected int maxCacheSize;

    public FileCacheBuilder() {
        dirName = DiskLruCacheHelper.DEFAULT_DIR_NAME;//缓存文件夹名字
        maxCacheSize = DiskLruCacheHelper.DEFAULT_MAX_CACHE_SIZE;//缓存最大空间
    }


    /**
     * 设置缓存文件夹名字
     *
     * @param dirName
     * @return
     */
    public FileCacheBuilder setDirName(String dirName) {
        if (!TextUtils.isEmpty(dirName)) {
            this.dirName = dirName;
        }
        return this;
    }

    /**
     * 设置 最大缓存上限值
     *
     * @param maxCacheSize
     * @return
     */
    public FileCacheBuilder setMaxCacheSize(int maxCacheSize) {
        if (maxCacheSize > 0) {
            this.maxCacheSize = maxCacheSize;
        }
        return this;
    }

}
