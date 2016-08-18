package okaiyi.mockingjay.json.creator;

public interface JsonCreator {
	/**
	 * 空json数据
	 */
	public static final String EMPTY_JSON_DATA="{}";
	/**
	 * 构建json字符串
	 * @return
	 */
	public String buildToJsonString();
}
