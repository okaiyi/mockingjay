package okaiyi.mockingjay.json.parse;

import okaiyi.mockingjay.json.ParseObjectPolicy;
import okaiyi.mockingjay.json.creator.FieldReplacePolicy;

/**
 * JSON字符串的对象解析器
 *
 */
public interface JsonObjectParser{
	/**
	 * 将json字符串解析为指定的字符
	 * @param jsonText
	 * @return
	 */
	<T> T parseToObject(Class<T> clz,FieldReplacePolicy fieldReplacePolicy,ParseObjectPolicy parseObjectPolicy);
}
