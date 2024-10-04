package com.pasc.lib.storage.fileDiskCache;

import android.content.Context;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

;

/**
 * 文件缓存相关工具类 by zc 2018年09月03日20:07:10
 * 给予rxjava 、gson等 二次封装 便于使用
 */
public class FileCacheUtils {


    /**
     * 初始化缓存
     * @param context
     * @param builder 可配置缓存大小、目录名称
     */
    public static void init(Context context,FileCacheBuilder builder) {
        FileCacheProxy.init(context, builder);
    }
    /**
     * @param key   获取存储的key值
     * @param value 要转换成json串 存储的对象
     */
    @WorkerThread
    public static void put(String key, Object value) {
        Log.e("FileCacheUtils", "当前线程：" + Thread.currentThread().getName());
        FileCacheProxy.getInstance().put(key, toJson(value));
    }

    /**
     * 删除对应key的缓存
     * @param key   获取存储的key值
     */
    @WorkerThread
    public static void remove(String key) {
        Log.e("FileCacheUtils", "当前线程：" + Thread.currentThread().getName());
        FileCacheProxy.getInstance().remove(key);
    }

    /**
     * json 转集合
     *
     * @param json
     * @param clazz 元素对象类型
     * @param <T>
     * @return 不会返回为null，如果没有数据返回的是空集合。
     */
    private static <T> List<T> parseString2List(String json, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list = null;
//        if (AppProxy.isDebug()) {
//            list = new Gson().fromJson(json, type);
//        } else {//针对线上环境添加异常捕获逻辑(容错)
        try {
            list = new Gson().fromJson(json, type);
        } catch (JsonSyntaxException e) {
            Log.e("FileCacheUtils", "获取缓存数据异常->>>>>");
            e.printStackTrace();
        }
//        }
        return list == null ? new ArrayList<T>() : list;
    }

    private static <T> T parseString2Model(String json, Class<T> clazz) {
//        if (AppProxy.isDebug()) {
//            return new Gson().fromJson(json, clazz);
//        } else {//针对线上环境添加异常捕获逻辑(容错)
        try {
            return new Gson().fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            Log.e("FileCacheUtils", "获取缓存数据异常->>>>>");
            e.printStackTrace();
        }
//        }
        return null;
    }


    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     * 获取list（读取文件被默认放在主线程）
     */
    public static <T> List<T> getListSync(final String key, final Class<T> clazz) {
        String json = FileCacheProxy.getInstance().getAsString(key);
        return FileCacheUtils.parseString2List(json, clazz);
    }

    /**
     * 获取包含list 的 flowable (读取文件是放在子线程的)
     * 如果缓存为空，则会返回空集合
     */
    public static <T> Flowable<List<T>> getListFlowable(final String key, final Class<T> clazz) {
        return Flowable.create(new FlowableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(FlowableEmitter<List<T>> emitter) {
                Log.e("FileCacheUtils", "当前取数据的线程：" + Thread.currentThread().getName());
                String json = FileCacheProxy.getInstance().getAsString(key);
                List<T> list = FileCacheUtils.parseString2List(json, clazz);
                emitter.onNext(list);
                emitter.onComplete();

            }
        }, BackpressureStrategy.LATEST).onErrorReturnItem(new ArrayList<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取包含T 的 flowable (读取文件是放在子线程的)
     */
    public static <T> Flowable<T> getModelFlowable(final String key, final Class<T> clazz) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) {
                Log.e("FileCacheUtils", "当前取数据的线程：" + Thread.currentThread().getName());
                String json = FileCacheProxy.getInstance().getAsString(key);
                T t = FileCacheUtils.parseString2Model(json, clazz);
                if (t != null)
                    emitter.onNext(t);
                emitter.onComplete();

            }
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> T getModelSync(String key, Class<T> clazz) {
        String json = FileCacheProxy.getInstance().getAsString(key);
        return FileCacheUtils.parseString2Model(json, clazz);
    }


    public static String getAsString(String key) {
        Log.e("FileCacheUtils", "当前取数据的线程：" + Thread.currentThread().getName());
        return FileCacheProxy.getInstance().getAsString(key);
    }


    private static String toJson(Object obj) {

        return toJson(obj, null);
    }

    private static String toJson(Object obj, Type typeOfT) {
        if (obj == null) {
            return "{}";
        }
        if (obj.getClass() == String.class) {
            return obj.toString();
        }
        if (typeOfT != null) {
            return new Gson().toJson(obj, typeOfT);
        } else {
            return new Gson().toJson(obj);
        }
    }


}
