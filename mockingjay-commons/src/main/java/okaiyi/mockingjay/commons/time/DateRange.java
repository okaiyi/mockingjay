package okaiyi.mockingjay.commons.time;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期区间
 */
public class DateRange implements Serializable{
	private static final long serialVersionUID = -6866575825088699626L;

	private Date startDate;
	
	private Date endDate;
	
	/**
	 * 获取开始时间
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取结束时间
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}
	void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
