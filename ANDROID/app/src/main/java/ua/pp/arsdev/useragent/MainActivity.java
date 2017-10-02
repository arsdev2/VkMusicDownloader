package ua.pp.arsdev.useragent;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Locale;
import java.net.*;
import java.io.*;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String appVersion;
        int appBuild;
        try {
            PackageInfo pkgInfo = this.getPackageManager().getPackageInfo("com.vkontakte.android", 0);
            appVersion = pkgInfo.versionName;
            appBuild = pkgInfo.versionCode;
            String userAgent = String.format(Locale.US, "VKAndroidApp/%s-%d (Android %s; SDK %d; %s; %s %s; %s)", new Object[]{appVersion, Integer.valueOf(appBuild), Build.VERSION.RELEASE, Integer.valueOf(Build.VERSION.SDK_INT), Build.CPU_ABI, Build.MANUFACTURER, Build.MODEL, System.getProperty("user.language")});
            Log.d("USER_AGENT", userAgent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
