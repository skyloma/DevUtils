package lomasky.ma.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author WangLi
 * @date 2014年9月12日 下午5:19:52
 * @Description
 * @version V1.0
 */

public class CameraUtil {
	/**
	 * @MethodName: toAlbum
	 * @Description: 将图片暴露在相册目录
	 * @param ac
	 * @param path
	 * @return
	 */
	public static void toAlbum(Activity ac, String path){
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(new File(path));
		intent.setData(uri);
		ac.sendBroadcast(intent);
	}

	/**
	 * @MethodName: getAlbumPath
	 * @Description: 获取图库目录
	 * @return
	 */
	public static File getAlbumDir(){
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
	}

	/**
	 * @MethodName: getCameraDir
	 * @Description: 获取默认的照片目录
	 * @return
	 */
	public static File getCameraDir(){
		File file = new File(CommonUtils.getSDCardPath() + "/DCIM/Camera/");
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}

	public static File createImgToAlbum(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timeStamp = format.format(new Date());
		String imageFileName = "wyy_" + timeStamp + ".jpg";
		File image = new File(getCameraDir(), imageFileName);
		return image;
	}
}
