package com.base.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.app.R;
import com.base.app.utils.main.CommonUtils;
import com.base.app.utils.main.Encrypt3DesUtils;


/**
 * describe: Activity 基类
 * creator: keding.zhou
 * date: 2017/11/29 8:47
 */
public abstract class BaseActivity extends AppCompatActivity {

    private String TAG_APP_NAME = "";
    private final static String TAG_IF_ELSE = "IF_ELSE";
    //加载动画
    // public MaterialDialog loadingDialog;
    public Dialog loadingDialog;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setConfigs();
            setContentView(savedInstanceState);
            mContext = this;
            TAG_APP_NAME = getResources().getString(R.string.app_name);
            initData();
            initView();

        } catch (Exception e) {
            Log.e(TAG_APP_NAME, this.getLocalClassName() + ":" + e.toString());
        }
    }

    /**
     * 设置属性
     */
    protected void setConfigs() {
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    /**
     * 标题栏相关设置
     *
     * @param tvTopbarTitle 标题TextView
     * @param title         标题
     * @param isCanBack     是否有返回键
     * @param vsTopbarBack  返回键 ViewStub
     */
    protected void setTopBarTitle(TextView tvTopbarTitle, String title, boolean isCanBack, ViewStub vsTopbarBack) {
        //设置标题
        tvTopbarTitle.setText(title);
        if (isCanBack) {
            //可以返回
            //返回 ViewStub
            vsTopbarBack.inflate();
            //所有的返回键LinearLayout id 都是一样的
            LinearLayout ll_back = findViewById(R.id.cl_top_bar_back);
            ll_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    CommonUtils.hideInputWindow(mContext);
                }
            });
        }

    }

    /**
     * 设置主布局文件
     */
    protected abstract void setContentView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     */
    protected abstract void initView();


    /**
     * Okhttp 失败回调
     *
     * @param call  回调
     * @param e 报错
     * @param id   方法id
     */
   // protected abstract void failStringBack(Call call, Exception e, int id);

    /**
     * Okhttp 成功回调
     *
     * @param response 返回
     * @param id       方法id
     */
    protected abstract void successStringBack(String response, int id);

    /**
     * @param text 显示内容
     * @param <T> 类型
     */
    protected <T> void showToast(T text) {
        if (null != text) {
            Toast.makeText(this, text + "", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG_APP_NAME, TAG_IF_ELSE + "：Toast值不能为空！");
        }
    }

    /**
     * Okhttp String 类型 返回回调接口
     */
   /* public class MyBaseStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            failStringBack(call, e, id);
        }

        @Override
        public void onResponse(String response, int id) {
            successStringBack(response, id);
        }
    }
*/
    /**
     * 跳转
     *
     * @param cls 目标页面
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 带值跳转
     *
     * @param cls 目标页面
     * @param bundle 值
     */
    protected void startActivityWithData(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转，有返回值
     *
     * @param cls 目标页面
     * @param requestCode 请求code
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转，有返回值,并传值
     *
     * @param cls 目标页面
     * @param requestCode 请求code
     * @param bundle 值
     */
    protected void startActivityForResultWithData(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 提交数据 Get
     *
     * @param url      url
     * @param funID    方法id
     * @param dataJson 上传的数据json String
     */
    protected void submitData(String url, int funID, String dataJson, String showStr) {
        //启动加载动画
        //loadingDialog = MaterialDialogUtils.makeLoadingDialog(mContext,showStr);
        //loadingDialog = new LoadingDialog().createLoadingDialog(mContext, showStr, false);
        String encryData = Encrypt3DesUtils.encrypt(dataJson).replace("\n", "");
        Log.i("zkd", "[BaseActivity][submitData]==> load json : " + dataJson);
        Log.i("zkd", "[BaseActivity][submitData]==> 3DES 加密 : " + encryData);
       // OkHttpServiceUtils.submitJsonToServiceGet(this, url, funID, new MyBaseStringCallBack(), encryData);
    }

    @Override
    protected void onPause() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mContext != null) {
            mContext = null;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        super.onDestroy();
    }
}
