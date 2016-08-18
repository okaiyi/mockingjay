package okaiyi.mockingjay.json.creator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对日期进行转换的json
 *
 */
public class DateJsonCreator<Data> implements JsonCreator{
	
	private SimpleDateFormat dataFormat;
	private Date date;
	
	public DateJsonCreator( Date date,SimpleDateFormat dataFormat) {
		this.dataFormat = dataFormat;
		this.date = date;
	}


	@Override
	public String buildToJsonString() {
		return dataFormat.format(date);
	}


	@Override
	public String toString() {
		return buildToJsonString();
	}
}
