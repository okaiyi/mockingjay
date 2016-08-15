package okaiyi.mockingjay.commons.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间日期
 *
 */
public abstract class DateFormatter {
	/**
	 * 默认的日期格式化,格式化方式 2012-11-22 16:44:33
	 */
	public static final SimpleDateFormat DEFAULT_24_FORMAT;
	static{
		DEFAULT_24_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 快速格式化
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static final String fastFormat(Date date,String pattern){
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		return format.format(date);
	}
	/**
	 * 格式化输出策略
	 * @param isBefore 当前日期在指定日期之前
	 * @param dt 类型
	 * @param val 值
	 * @return
	 */
	public abstract String formatPolicy(boolean isBefore,DateType dt,int val);
}
