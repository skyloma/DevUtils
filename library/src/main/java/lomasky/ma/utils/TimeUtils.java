package lomasky.ma.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author way
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

	public static String converTime(long time) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time / 1000;// 与现在时间相差秒数
		String timeStr = null;
		if(timeGap > 3*24 *60*60){
			timeStr = getDayTime(time) + " " + getMinTime(time);
		}
		else if (timeGap > 24 * 2 * 60 * 60) {// 2天以上就返回标准时间
			timeStr = "前天 " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60) {// 1天-2天
			timeStr = timeGap / (24 * 60 * 60) + "昨天 " + getMinTime(time);
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "今天 " + getMinTime(time);
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "今天 " + getMinTime(time);
		} else {// 1秒钟-59秒钟
			timeStr = "今天 " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getChatTime(long time) {
		return getMinTime(time);
	}

	public static String getPrefix(long time) {
		long currentSeconds = System.currentTimeMillis();
		long timeGap = currentSeconds - time;// 与现在时间差
		String timeStr = null;
	 
		if (timeGap > 24 * 3 * 60 * 60 * 1000) {
			timeStr = getDayTime(time) + " " + getMinTime(time);
		} else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
			timeStr = "前天 " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60 * 1000) {
			timeStr = "昨天 " + getMinTime(time);
		} else {
			timeStr = "今天 " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getDayTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		return format.format(new Date(time));
	}

	public static String getMinTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		return format.format(new Date(time));
	}
	
	/**
	 * 将字符串转位日期类型
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	 private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * 以友好的方式显示时间
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		
		//判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
			return ftime;
		}
		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"天前";			
		}
		else if(days > 10){			
			ftime = dateFormater2.format(time);
		}
		return ftime;
	}

	/**
	 * 以友好的方式显示时间
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(long sdate) {
		Date time = new Date(sdate);
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		//判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else
				ftime = hour+"小时前";
			return ftime;
		}

		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){
			ftime = days+"天前";
		}
		else if(days > 10){
			ftime = dateFormater2.format(time);
		}
		return ftime;
	}
	/**
	 * 以友好的方式显示时间
	 * @param date
	 * @return
	 */
	public static String friendly_time(Date date) {

		String ftime = "";
		Calendar cal = Calendar.getInstance();

		//判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(date);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - date.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000,1)+"分钟前";
			else
				ftime = hour+"小时前";
			return ftime;
		}

		long lt = date.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - date.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000,1)+"分钟前";
			else
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){
			ftime = days+"天前";
		}
		else if(days > 10){
			ftime = dateFormater2.format(date);
		}
		return ftime;
	}
	/**
	 * 判断给定字符串时间是否为今日
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate){
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if(time != null){
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if(nowDate.equals(timeDate)){
				b = true;
			}
		}
		return b;
	}
	public static String getTime(String time) {
		if (StringUtil.isEmpty(time))
			return "";
		return time.replaceAll("T", " ").substring(0, 16);
	}


    public static String getDate(String time) {
        if (TextUtils.isEmpty(time))
            return "";
        else{
            if (time.length()>=16){
                return time.substring(0, 10);
            }else {
                return time.substring(0, time.length());
            }
        }



    }
	public static String getTimeSeconds(String time){
		if (StringUtil.isEmpty(time))
			return "";
		return time.replaceAll("T", " ").substring(0, 19);
	}

}
