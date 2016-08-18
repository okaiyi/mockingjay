package okaiyi.mockingjay.json.creator;

import org.json.JSONArray;

/**
 * json数据构建 
 *
 */
public interface JsonArrayCreator extends JsonCreator{
	/**
	 * 获取json数组
	 * @return
	 */
	JSONArray getJsonArray();
}
