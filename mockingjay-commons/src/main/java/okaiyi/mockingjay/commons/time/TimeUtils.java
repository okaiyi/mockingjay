package okaiyi.mockingjay.commons.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * @author kaiyi
 *
 */
public class TimeUtils {
	private static final long SECOND_MILLISECOND=1000l;
	/**
	 * 获取1小时的毫秒数
	 * @return
	 */
	public static final long getHourMillisecond(){
		return getMinutesMillisecond()*60l;
	}
	/**
	 * 获取1秒的毫秒数
	 * @return
	 */
	public static final long getSecondMillisecond(){
		return SECOND_MILLISECOND;
	}
	/**
	 * 获取一分钟的毫秒数
	 * @return
	 */
	public static final long getMinutesMillisecond(){
		return getSecondMillisecond()*60l;
	}
	/**
	 * 获取一天的毫秒数
	 * @return
	 */
	public static final long getDayMillisecond(){
		return getHourMillisecond()*24;
	}
	/**
	 * 获取指定期所在月份的所有毫秒数总和
	 * @param date
	 * @return
	 */
	public static final long getMonthMillisecond(Date date){
		Calendar c=Calendar.getInstance(Locale.getDefault());
		c.setTime(date);
		return getDayMillisecond()*c.getActualMaximum(Calendar.DATE);
	}
	/**
	 * 获取指定日期所在年份的所有毫秒数总和
	 * @param date
	 * @return
	 */
	public static final long getYearMillisecond(Date date){
		Calendar c=Calendar.getInstance(Locale.getDefault());
		c.setTime(date);
		return getDayMillisecond()*c.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
	/**
	 * 获取当日时间区间
	 * @return
	 */
	public static DateRange getCurrenDayRange(){
		Calendar c=Calendar.getInstance(Locale.getDefault());
		DateRange range=new DateRange();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		range.setStartDate(c.getTime());
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		range.setEndDate(c.getTime());
		return range;
	}
	/**
	 * 获取前一日时间周期
	 * @return
	 */
	public static DateRange getPreviousDayRange(){
		Calendar c=Calendar.getInstance(Locale.getDefault());
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-1);
		DateRange range=new DateRange();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		range.setStartDate(c.getTime());
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		range.setEndDate(c.getTime());
		return range;
	}
	/**
	 * 获取上一月时间区间
	 * @return
	 */
	public static DateRange getPreviousMonthRange(){
		Calendar c=Calendar.getInstance(Locale.getDefault());
		c.set(Calendar.MONTH,c.get(Calendar.MONTH)-1);
		c.set(Calendar.DATE,c.getActualMinimum(Calendar.DATE));
		DateRange range=new DateRange();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		range.setStartDate(c.getTime());
		c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		range.setEndDate(c.getTime());
		return range;
	}
	/**
	 * 获取当月时间区间
	 * @return
	 */
	public static DateRange getCurrentMonthRange(){
		Calendar c=Calendar.getInstance(Locale.getDefault());
		c.set(Calendar.DATE,c.getActualMinimum(Calendar.DATE));
		DateRange range=new DateRange();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		range.setStartDate(c.getTime());
		c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		range.setEndDate(c.getTime());
		return range;
	}
	/**
	 * 获取当前日期往前几日的日期
	 * @param day
	 * @return
	 */
	public static DateRange getForwardDay(int day){
		Calendar c=Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-day);
		DateRange range=new DateRange();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		range.setStartDate(c.getTime());
		c.set(Calendar.DATE,c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		range.setEndDate(c.getTime());
		return range;
	}
	/**
	 * 将毫秒转换为指定类型的值
	 * @param millisecond
	 * @param type
	 * @return
	 */
	public static int convert(long millisecond,DateType type){
		if(type.equals(DateType.SECODE)){
			return new Long(millisecond/getSecondMillisecond()).intValue();
		}else if(type.equals(DateType.MINUTES)){
			return new Long(millisecond/getMinutesMillisecond()).intValue();
		}else if(type.equals(DateType.HOUR)){
			return new Long(millisecond/getHourMillisecond()).intValue();
		}else if(type.equals(DateType.DAY)){
			return new Long(millisecond/getDayMillisecond()).intValue();
		}else if(type.equals(DateType.MONTH)){
			return new Long(millisecond/getMonthMillisecond(new Date())).intValue();
		}else if(type.equals(DateType.YEAR)){
			return new Long(millisecond/getYearMillisecond(new Date())).intValue();
		}else{
			 return new Long(millisecond).intValue();
		}
	}
	/**
	 * 比较两个时间并作格式化输出
	 * @param currDate 当前时间
	 * @param compareDate 比较的时间
	 * @param formatter 必须实现formatPolicy
	 * @return
	 */
	public static String compareDateFormat(Date currDate,Date compareDate,DateFormatter formatter){
		long curr=currDate.getTime();
		long compare=compareDate.getTime();
		boolean isBefore=curr<compare?true:false;
		long difference=Math.abs(curr-compare);
		if(difference>=0&&difference<getSecondMillisecond()){
			return formatter.formatPolicy(isBefore,DateType.MILLIS,convert(difference, DateType.MILLIS));
		}else if(difference>=getSecondMillisecond()&&difference<getMinutesMillisecond()){
			return formatter.formatPolicy(isBefore,DateType.SECODE,convert(difference, DateType.SECODE));
		}else if(difference>=getMinutesMillisecond()&&difference<getHourMillisecond()){
			return formatter.formatPolicy(isBefore,DateType.MINUTES,convert(difference, DateType.MINUTES));
		}else if(difference>=getHourMillisecond()&&difference<getDayMillisecond()){
			return formatter.formatPolicy(isBefore,DateType.HOUR,convert(difference, DateType.HOUR));
		}else if(difference>=getDayMillisecond()&&difference<=getMonthMillisecond(currDate)){
			return formatter.formatPolicy(isBefore,DateType.DAY,convert(difference, DateType.DAY));
		}else if(difference>=getMonthMillisecond(currDate)&&difference<=getYearMillisecond(currDate)){
			return formatter.formatPolicy(isBefore,DateType.MONTH,convert(difference, DateType.MONTH));
		}else{
			return formatter.formatPolicy(isBefore,DateType.YEAR,convert(difference, DateType.YEAR));
		}
	}
}
