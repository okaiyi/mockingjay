package okaiyi.mockingjay.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import okaiyi.mockingjay.commons.utils.StringUtils;

/**
 * JSON字符串构造器
 */
public class JsonBuilder {
	/**
	 * 空json数据
	 */
	public static final String EMPTY_JSON_DATA="{}";
	/**
	 * 对象处理器，构造JSON字符串时,提供对对象的处理
	 *
	 */
	public interface ObjectProcessor{
		/**
		 * 获取对象的Class
		 * @param memberName 成员名称
		 * @return
		 */
		public Class<?> getObjectClass(String memberName);
		/**
		 * 获取需要处理的成员名称
		 * @param memberName 成员名称
		 * @return
		 */
		public String[] getMemberNames(String memberName);
		
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
	
	public static final <K,V> JSONArray mapToJsonArray(Map<K,V> map){
		Set<K> keys=map.keySet();
		JSONArray array=new JSONArray();
		for(K k:keys){
			V v=map.get(k);
			JSONObject jo=new JSONObject();
			jo.put(k.toString(),v);
			array.put(jo);
		}
		return array;
	}
	public static void main(String[] args) {
		Map<String, String> map=new HashMap<>();
		map.put("a", "b");
		map.put("c", "d");
		JSONObject jo=new JSONObject();
		jo.put("map", mapToJsonArray(map));
		System.err.println(jo.toString());
	}
	
	
	/**
	 * 将集合转换为JSON字符串
	 * @param collection
	 * @param members
	 * @param processor
	 * @return
	 */
	public static final String buildCollection(Collection<?> collection,String[] members,ObjectProcessor processor,KeyPolicy keyPolicy){
		if(collection==null||collection.isEmpty()||members==null||members.length==0){
			return EMPTY_JSON_DATA;
		}
		JSONObject jo=new JSONObject();
		Class<?> clz = collection.iterator().next().getClass();
		Method[] methods = clz.getMethods();
		String key = StringUtils.lowerFirst(clz.getSimpleName());
		JSONArray array = getJsonArray(collection, members, methods,processor,keyPolicy);
		jo.put(key, array);
		return jo.toString();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final <T> JSONArray getJsonArray(Collection<T> c,
			String[] members, Method[] methods,ObjectProcessor processor,KeyPolicy keyPolicy) {
		JSONArray array = new JSONArray();
		if (c != null && !c.isEmpty() && methods != null) {
			for (Iterator<T> it = c.iterator(); it.hasNext();) {
				T obj = it.next();
				JSONObject jo = new JSONObject();
				for (String ref : members) {
					String methodName = "get" + StringUtils.upperFirst(ref);
					Method method =getMatchMethod(methods,
							methodName);
					if(method==null){
						methodName.replace("get", "is");
						method =getMatchMethod(methods,
								methodName);
						if(method==null){
							throw new IllegalArgumentException("Field fail:"+ref);
						}
					}
					try {
						Object value = method.invoke(obj);
						String key = ref;
						if (keyPolicy != null){
							key = keyPolicy.replace(ref);
						}
						if(key==null){
							key=ref;
						}
						if(value instanceof Collection&&value instanceof Iterator){
							//如果是集合,并且实现了Iterator接口
							String[] subMembers=processor.getMemberNames(ref);
							Class<?> subClass=processor.getObjectClass(ref);
							jo.put(key,getJsonArray((Collection)value,
									subMembers, subClass.getMethods(), processor, keyPolicy));
						}else if(value instanceof Map){
							//如果是Map接口
							JSONArray mapArray=new JSONArray();
							Set<String> keys=((Map)value).keySet();
							for(String k:keys){
								
							}
							jo.put(key, mapArray);
						}else{
							jo.put(key, value);
						}
						array.put(jo);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return array;
	}
	
	private static final Method getMatchMethod(Method[] methods, String methodName) {
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}
}
