package lomasky.ma.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * @ClassName: SharedPrefUtil
 * @Description: sharedprefrence数据
 * @author  WangLi
 * @data:  2014年11月25日 下午7:28:17
 * @version:  V1.0
 */
public class SharedPrefUtil {



	/**
	 * 存储数据(Long)
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putLong(Context context, String key, long value) {
		Editor sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();

		sp.putLong(key, value);
		sp.commit();
	}

	/**
	 * 存储数据(Int)
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putInt(Context context, String key, int value) {
		Editor sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();
		sp.putInt(key, value);
		sp.commit();
	}

	/**
	 * 存储数据(String)
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putString(Context context, String key, String value) {
		Editor sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();

		sp.putString(key, value);
		sp.commit();
	}

	/**
	 * 存储数据(boolean)
	 *
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context context, String key,
								  boolean value) {
		Editor sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

	/**
	 * 取出数据（Long）
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue) {
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
		long value = sp.getLong(key, defValue);
		return value;
	}

	/**
	 * 取出数据（int）
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue) {
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
		int value = sp.getInt(key, defValue);
		return value;
	}

	/**
	 * 取出数据（boolean）
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
		boolean value = sp.getBoolean(key, defValue);
		return value;
	}

	/**
	 * 取出数据（String）
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key,
								   String defValue) {
		SharedPreferences sp = context.getSharedPreferences(context.getPackageName(),
				Context.MODE_PRIVATE);
		String value = sp.getString(key, defValue);
		return value;
	}

	/**
	 * 清空所有数据
	 *
	 * @param context

	 * @return
	 */
	public static void clear(Context context) {
		Editor sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();
		sp.clear();
		sp.commit();
	}

	/**
	 * 清空所有数据
	 *
	 * @param context
	 * @param key

	 * @return
	 */
	public static void remove(Context context, String key) {
		Editor sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
				.edit();
		sp.remove(key);
		sp.commit();
	}
}