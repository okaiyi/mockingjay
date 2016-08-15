package okaiyi.mockingjay.commons;
import java.util.Date;
import org.junit.Test;
import okaiyi.mockingjay.commons.time.DateFormatter;
import okaiyi.mockingjay.commons.time.DateRange;
import okaiyi.mockingjay.commons.time.DateType;
import okaiyi.mockingjay.commons.time.TimeUtils;
/**
 * 时间日期测试
 *
 */
public class TimeTest {
	@Test
	public void printMill(){
		System.out.println("getSecondMillisecond:"+TimeUtils.getSecondMillisecond());
		System.out.println("getMinutesMillisecond:"+TimeUtils.getMinutesMillisecond());
		System.out.println("getHourMillisecond:"+TimeUtils.getHourMillisecond());
		System.out.println("getDayMillisecond:"+TimeUtils.getDayMillisecond());
		System.out.println("getMonthMillisecond:"+TimeUtils.getMonthMillisecond(new Date()));
		System.out.println("getYearMillisecond:"+TimeUtils.getYearMillisecond(new Date()));
	}
	@Test
	public void testTime(){
		DateRange range=TimeUtils.getForwardDay(2);
		String str=TimeUtils.compareDateFormat(new Date(), range.getStartDate(),new DateFormatter() {
			
			@Override
			public String formatPolicy(boolean isBefore, DateType dt, int val) {
				System.out.println(isBefore);
				System.out.println(dt);
				if(dt.equals(DateType.DAY)){
					return val+"天";
				}else if(dt.equals(DateType.SECODE)){
					return val+"分";
				}
				return "年";
			}
		});
		System.out.println(str);
	}
}
