package okaiyi.mockingjay.json.parse;

import java.util.Collection;

import okaiyi.mockingjay.json.ParseObjectPolicy;
import okaiyi.mockingjay.json.creator.FieldReplacePolicy;
/**
 * json字符串解析为集合
 *
 * @param <T>
 */
public interface JsonCollectionParser extends JsonObjectParser{
	/**
	 * 将json字符串解析为集合
	 * @param jsonText
	 * @return
	 */
	<T> Collection<T> parseToCollection(Class<T> clz,FieldReplacePolicy fieldReplacePolicy,
			ParseObjectPolicy parseObjectPolicy);
}
