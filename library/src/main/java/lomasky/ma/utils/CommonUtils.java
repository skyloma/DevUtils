package lomasky.ma.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class CommonUtils {

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

	/** 
     * 获取外置SD卡路径 (没有SD卡，就获取内置存储路径)
     *  
     * @return 
     */  
    public static String getSDCardPath() {  
        String cmd = "cat /proc/mounts";  
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象  
        try {  
            Process p = run.exec(cmd);// 启动另一个进程来执行命令  
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
  
            String lineStr;  
            while ((lineStr = inBr.readLine()) != null) {  
                // 获得命令执行后在控制台的输出信息  
                Log.i("zjt", lineStr);
                if (lineStr.contains("sdcard")     && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");  
                    if (strArray != null && strArray.length >= 5) {  
                        String result = strArray[1].replace("/.android_secure",  
                                "");  
                        return result;  
                    }  
                }  
                // 检查命令是否执行失败。  
                if (p.waitFor() != 0 && p.exitValue() == 1) {  
                    // p.exitValue()==0表示正常结束，1：非正常结束  
                    Log.e("zjt", "命令执行失败!");
                }  
            }  
            inBr.close();  
            in.close();  
        } catch (Exception e) {  
  
            return Environment.getExternalStorageDirectory().getPath();  
        }  
        return Environment.getExternalStorageDirectory().getPath();  
    } 
}
