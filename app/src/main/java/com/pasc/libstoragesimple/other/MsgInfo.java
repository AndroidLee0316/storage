package com.pasc.libstoragesimple.other;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = MsgDb.class)
public class MsgInfo extends BaseModel {
    @Column(name = "userId") @PrimaryKey
    @SerializedName("userId") public String userId;//用户id
    @Column(name = "userName") @SerializedName("userName") public String userName;// 用户姓名
}
