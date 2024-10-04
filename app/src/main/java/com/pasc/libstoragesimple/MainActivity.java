package com.pasc.libstoragesimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pasc.lib.storage.database.DbFlowManager;
import com.pasc.lib.storage.fileDiskCache.FileCacheUtils;
import com.pasc.libstoragesimple.model.UserDb;
import com.pasc.libstoragesimple.model.UserInfo;
import com.pasc.libstoragesimple.other.MsgDb;
import com.pasc.libstoragesimple.other.MsgInfo;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TEST_KEY = "test_key111";
    TextView tvDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        tvDb = findViewById (R.id.tvDb);
        ((CheckBox) findViewById (R.id.check)).setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String dbName = FlowManager.getDatabase (UserDb.class).getDatabaseName ();
                System.err.println (dbName);
                String DefaultDbName = DbFlowManager.getDefaultDbName (UserDb.class);
                DbFlowManager.switchDb (UserDb.class, isChecked ? DefaultDbName + "_123" : DefaultDbName);

                DbFlowManager.switchDb (MsgDb.class, isChecked ? DbFlowManager.getDefaultDbName (MsgDb.class) + "_123" : DbFlowManager.getDefaultDbName (MsgDb.class));


                dbName = FlowManager.getDatabase (UserDb.class).getDatabaseName ();
                tvDb.setText ("当前数据库名称： " + dbName);


            }
        });
        String dbName = FlowManager.getDatabase (UserDb.class).getDatabaseName ();

        tvDb.setText ("当前数据库名称： " + dbName);

    }

    public void add(View view) {
        FileCacheUtils.put (TEST_KEY, "test");
    }

    public void delete(View view) {
        FileCacheUtils.remove (TEST_KEY);
    }

    public void query(View view) {
        String value = FileCacheUtils.getAsString (TEST_KEY);
        Toast.makeText (this, value, Toast.LENGTH_LONG).show();


    }

    /**
     * 从本地数据库获取最近浏览数据
     */
    public static Flowable<List<UserInfo>> queryUserList() {
        return RXSQLite.rx (SQLite.select ().from (UserInfo.class))
                .queryList ()
                .onErrorReturnItem (Collections.<UserInfo>emptyList ())
                .toFlowable ()
                .filter (new Predicate<List<UserInfo>> () {
                    @Override
                    public boolean test(List<UserInfo> list) throws Exception {
                        return !list.isEmpty ();
                    }
                })
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ());
    }

    public void insetDb(View view) {
        UserInfo userInfo = new UserInfo ();
        userInfo.userId = UUID.randomUUID ().toString ();
        userInfo.userName = System.currentTimeMillis () + "";
        userInfo.insert ();

        MsgInfo msgInfo = new MsgInfo ();
        msgInfo.userId = UUID.randomUUID ().toString ();
        msgInfo.userName = System.currentTimeMillis () + "";
        msgInfo.insert ();

    }

    public void queryDb(View view) {
        List<UserInfo> userInfos = SQLite.select ().from (UserInfo.class).queryList ();

        List<MsgInfo> msgInfos = SQLite.select ().from (MsgInfo.class).queryList ();

        System.err.println (userInfos);

        System.err.println (msgInfos);

    }
}
