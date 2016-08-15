package okaiyi.mockingjay.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.json.JSONObject;

/**
 * json字符串
 */
public abstract class JsonString {
	protected ValuePolicy valuePolicy;
	protected KeyPolicy keyPolicy;
	/**
	 * 空json数据
	 */
	public static final String EMPTY_JSON_DATA="{}";
	/**
	 * 构建json字符串
	 */
	protected abstract String buildToString();
	/**
	 * 取值策略
	 *
	 */
	public interface ValuePolicy {
		/**
		 * @param fieldName 反射字段名
		 * @param obj 数据对象
		 * @return
		 */
		Object getValue(String fieldName, Object obj);
	}
	/**
	 * key替换策略
	 *
	 */
	public interface KeyPolicy{
		/**
		 * 替换Key名称,如果不替换,直接返回key
		 * 
		 * @param key
		 * @return
		 */
		public String replace(String key);
	}
	/**
	 * 构建key,value形式的json
	 */
	public static JsonString buildEntryJson(Map<String, String> entry){
		EntryJsonString jsonString=new EntryJsonString(entry);
		return  jsonString;
	}
	/**
	 * 通过对象构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @return
	 */
	public static <T> JsonString buildObjectJson(T t,String[] members){
		return buildObjectJson(t, members, null, null);
	}
	/**
	 * 通过对象构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @param keyPolicy key替换策略
	 * @return
	 */
	public static <T> JsonString buildObjectJson(T t,String[] members,KeyPolicy keyPolicy){
		return buildObjectJson(t, members, keyPolicy, null);
	}
	/**
	 * 通过对象构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @param valuePolicy 值替换策略
	 * @return
	 */
	public static <T> JsonString buildObjectJson(T t,String[] members,ValuePolicy valuePolicy){
		return buildObjectJson(t, members, null, valuePolicy);
	}
	/**
	 * 
	 * 通过集合构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @param keyPolicy key替换策略
	 * @param valuePolicy 值替换策略
	 * @return
	 */
	public static <T> JsonString buildCollectionJson(Collection<T> collection,String[] members,KeyPolicy keyPolicy,ValuePolicy valuePolicy){
		return new CollectionJsonString(collection, members, keyPolicy, valuePolicy);
	}
	/**
	 * 
	 * 通过集合构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @param keyPolicy key替换策略
	 * @return
	 */
	public static <T> JsonString buildCollectionJson(Collection<T> collection,String[] members,KeyPolicy keyPolicy){
		return new CollectionJsonString(collection, members, keyPolicy,null);
	}
	/**
	 * 
	 * 通过集合构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @param valuePolicy 值替换策略
	 * @return
	 */
	public static <T> JsonString buildCollectionJson(Collection<T> collection,String[] members,ValuePolicy valuePolicy){
		return new CollectionJsonString(collection, members, null, valuePolicy);
	}
	/**
	 * 
	 * 通过集合构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @return
	 */
	public static <T> JsonString buildCollectionJson(Collection<T> collection,String[] members){
		return new CollectionJsonString(collection, members, null, null);
	}
	/**
	 * 通过对象构建JsonString
	 * @param t 对象
	 * @param members 需要转换到json中的成员字段
	 * @param keyPolicy key替换策略
	 * @param valuePolicy 值替换策略
	 * @return
	 */
	public static <T> JsonString buildObjectJson(T t,String[] members,KeyPolicy keyPolicy,ValuePolicy valuePolicy){
		ArrayList<T> list=new ArrayList<T>(1);
		list.add(t);
		return buildCollectionJson(list, members, keyPolicy, valuePolicy);
	}
	/**
	 * 用Key和Value构建JSON，格式如下 {"json-key":"json-vaue"}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static final String toSimpleString(String key, String value) {
		JSONObject jo = new JSONObject();
		jo.put(key, value);
		return jo.toString();
	}
	/**
	 * 组合多个JsonString
	 * @param jsonStrings
	 * @return
	 */
	public static final String linkMultipleJson(JsonString... jsonStrings){
		String[] jsons=new String[jsonStrings.length];
		for(int i=0;i!=jsonStrings.length;++i){
			jsons[i]=jsonStrings[i].buildToString();
		}
		return linkMultipleJson(jsons);
	}
	/**
	 * 组合多个JSON字符串,组合的字符串必须以双引号{}开头和结尾
	 * @param jsons
	 * @return
	 */
	public static final String linkMultipleJson(String... jsons){
		StringBuilder builder=new StringBuilder();
		ArrayList<String> al=new ArrayList<String>();
		for(String json:jsons){
			if(json.length()>3){
				al.add(json);
			}
		}
		if(al.size()==1){
			return al.get(0);
		}
		for(int i=0;i<al.size();i++){
			String j=al.get(i);
			if(i==0){
				builder.append(j.substring(0, j.length()-1));
			}else if(i!=al.size()-1){
				builder.append(",").append(j.substring(1, j.length()-1));
			}else if(i==al.size()-1){
				builder.append(",").append(j.substring(1, j.length()));
			}
		}
		return builder.toString();
	}
}
