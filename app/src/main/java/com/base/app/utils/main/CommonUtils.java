package com.base.app.utils.main;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * describe: 工具类
 * creator: keding.zhou
 * date: 2018/4/9 10:51
 */
public class CommonUtils {
    /**
     * 隐藏键盘
     * @param context context
     */
    public static void hideInputWindow(Context context){
        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(((Activity)context).getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    /**
     * 读取 assets 文件夹下的json文件
     * @param fileName 文件名
     * @param context context
     * @return string
     */
    public static String getAssetsJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
