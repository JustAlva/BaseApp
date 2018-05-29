package com.base.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.base.app.utils.main.Encrypt3DesUtils;

import java.io.File;


/**
 * describe:  Fragment 基类
 * creator: keding.zhou
 * date: 2018/1/24 14:19
 */
public abstract class BaseFragment extends Fragment {
    private final static String TAG_IF_ELSE = "IF_ELSE";
    protected Context mContext;
    protected View convertView;
    //加载动画
    //public MaterialDialog loadingDialog;
    public Dialog loadingDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        try {
            mContext = getActivity();
            convertView = setContentView(inflater );
            initData();
            initView(convertView);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("zkd","[BaseFragment][onCreateView]==> error : "+ e.toString() );
            showToast("BaseFragment:"+e.toString());
        }
        return convertView;
    }



    protected abstract View setContentView(LayoutInflater inflater  );

    protected abstract void initData();

    protected abstract void initView(View convertView);
    /**
     * Okhttp 失败回调
     *
     * @param call 回调
     * @param e 错误
     * @param id   方法id
     */
    //protected abstract void failStringBack(Call call, Exception e, int id);

    /**
     * Okhttp 成功回调
     *
     * @param response 返回
     * @param id       方法id
     */
    protected abstract void successStringBack(String response, int id);

    /**
     * 提交数据 Get
     *
     * @param url url
     * @param funID   方法id
     * @param dataJson    上传的数据json String
     */
    protected void submitData(String url, int funID, String dataJson, String showStr) {
        //启动加载动画
       //loadingDialog = MaterialDialogUtils.makeLoadingDialog(mContext,showStr);
        //loadingDialog =new LoadingDialog().createLoadingDialog(mContext, showStr, true);
        String encryData = Encrypt3DesUtils.encrypt(dataJson).replace("\n", "");
        Log.i("zkd","[BaseActivity][submitData]==> load json : "+ dataJson );
        Log.i("zkd","[BaseActivity][submitData]==> 3DES 加密 : "+ encryData );
       // OkHttpServiceUtils.submitJsonToServiceGet(mContext, url, funID, new  MyBaseStringCallBack(), encryData);
    }

    /**
     * 上传文件
     * @param url 链接
     * @param funID 方法id
     * @param dataJson data
     * @param file File 文件
     * @param showStr dialog显示
     */
    protected void submitFile(String url, int funID, String dataJson, File file, String showStr) {
        //启动加载动画
       // loadingDialog =new LoadingDialog().createLoadingDialog(mContext, showStr, true);
        String encryData = Encrypt3DesUtils.encrypt(dataJson).replace("\n", "");
        Log.i("zkd","[BaseActivity][submitData]==> load json : "+ dataJson );
        Log.i("zkd","[BaseActivity][submitData]==> 3DES 加密 : "+ encryData );
        //OkHttpServiceUtils.submitFileToService(mContext, url, funID, new MyBaseStringCallBack(), encryData, file);
    }
    /**
     * @param text t
     * @param <T> T
     */
    protected <T> void showToast(T text) {
        if (null != text) {
            Toast.makeText(mContext, text + "", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG_IF_ELSE, "输入值不能为空！");
        }
    }
    /**
     * 跳转
     *
     * @param cls 目标页面
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }
    /**
     * 带值跳转
     *
     * @param cls 目标页面
     * @param bundle 值
     */
    protected void startActivityWithData(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Okhttp String 类型 返回回调接口
     */
  /*  public class MyBaseStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            failStringBack(call, e, id);
        }

        @Override
        public void onResponse(String response, int id) {
            successStringBack(response, id);
        }
    }*/
}
