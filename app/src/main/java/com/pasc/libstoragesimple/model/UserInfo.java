package com.pasc.libstoragesimple.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by yintangwen952 on 2018/9/6.
 */

@Table(database = UserDb.class) public class UserInfo extends BaseModel {

    @Column(name = "userId") @PrimaryKey @SerializedName("userId") public String userId;//用户id
    @Column(name = "userName") @SerializedName("userName") public String userName;// 用户姓名
    @Column(name = "sex") @SerializedName("sex") public String sex;// 用户性别0，未知；1，男；2，女
    @Column(name = "mobileNo") @SerializedName("mobileNo") public String mobileNo;// 手机
    @Column(name = "IDcard") @SerializedName("IDcard") public String idCard;// 身份证
    @Column(name = "address") @SerializedName("address") public String address;// 住址
    @Column(name = "headImg") @SerializedName("headImg") public String headImg;// 头像地址
    @Column(name = "IDpassed") @SerializedName("IDpassed") public String idPassed;//是否认证0,否；1，是
}
