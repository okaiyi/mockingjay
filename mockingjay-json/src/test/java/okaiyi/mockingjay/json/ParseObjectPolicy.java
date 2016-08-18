package okaiyi.mockingjay.json;

import okaiyi.mockingjay.commons.data.DataTyper;

/**
 * json字符串转换为目标对象 
 *
 */
public interface ParseObjectPolicy {
	Object toObject(String fieldName,DataTyper obj);
}
