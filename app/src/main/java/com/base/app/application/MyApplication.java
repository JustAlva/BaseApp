package com.base.app.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * describe: Application
 * creator: keding.zhou
 * date: 2018/3/29 13:43
 */
public class MyApplication extends Application {
    private RefWatcher refWatcher = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //  initBlankj();
        //  initOkhttp();
        refWatcher = initLeak();

        //  initTBS();
        // clipboardDeal();
    }

    /**
     * 腾讯 X5  内核
     */
   /* private void initTBS() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.i("zkd", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.i("zkd","加载内核是否成功:"+b);
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }*/
/*
    public static RefWatcher getRefWatcher(Context context){
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }*/

    /**
     * 内存泄漏初始化
     */
    private RefWatcher initLeak() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    /**
     * 初始化 Blankj 工具类
     */
   /* private void initBlankj() {
         Utils.init(this);
    }*/

    /**
     * 初始化OkHttp
     */
   /* private void initOkhttp() {
        HttpsUtils.SSLParams sslParams;
        sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("OKHttp_TAG"))
                .hostnameVerifier(new HostnameVerifier() {
                    @SuppressLint("BadHostnameVerifier")
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }*/


    /**
     * 获取Manifest中定义的meta-data int类型
     *
     * @param metaName     meta-data定义的名字
     * @param defaultValue 默认值
     * @return int
     */
    public static int getAppMetaDataInt(Context context, String metaName, int defaultValue) {
        try {
            //application标签下用getApplicationinfo，如果是activity下的用getActivityInfo
            //Sting类型的用getString，Boolean类型的getBoolean，其他具体看api
            return context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getInt(metaName, defaultValue);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 获取Manifest中定义的meta-data String类型
     *
     * @param context      上下午
     * @param metaName     meta-data定义的名字
     * @param defaultValue 默认值
     * @return string
     */
    public static String getAppMetaDataString(Context context, String metaName, String defaultValue) {
        try {
            //application标签下用getApplicationinfo，如果是activity下的用getActivityInfo
            //Sting类型的用getString，Boolean类型的getBoolean，其他具体看api
            return context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString(metaName, defaultValue);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
