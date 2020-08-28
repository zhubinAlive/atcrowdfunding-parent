package com.atguigu.atcrowdfunding.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

	/**
	 * 将用户输入时间返回为用户输入格式
	 * @param d
	 * @param f
	 * @return
	 */
	public static String format(Date d, String f) {
		SimpleDateFormat sdf = new SimpleDateFormat(f);
		return sdf.format(d);
	}

	/**
	 * 将当前时间返回为固定格式
	 * @return
	 */
	public static String getFormatTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = format.format(new Date());
		return string;
	}

	/**
	 * 将当前时间返回为用户输入格式
	 * @param pattern
	 * @return
	 */
	public static String getFormatTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String string = format.format(new Date());
		return string;
	}

}
