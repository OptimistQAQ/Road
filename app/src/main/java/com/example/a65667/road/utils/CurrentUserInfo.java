package com.example.a65667.road.utils;

import com.example.a65667.road.bean.User;

public class CurrentUserInfo {
    public static User user = null;
    public static Integer uno = 0;  //当前用户id
    public static String name = "";
    public static String password = "";

    public static String time = "";   //工作总时长
    public static String distance = ""; //工作总距离
    public static String line = "";   //工作总条数

    public static boolean pbase = true;   //用户基本权限，即代表账号是否可用
    public static boolean photo = true;   //拍照上传识别权限
    public static boolean pvideo = true;  //视频推流识别权限
    public static boolean pinfo = true;   //破损信息管理权限
    public static boolean padmin = true;  //最高权限，是否可管理管理员
}
