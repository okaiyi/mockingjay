package okaiyi.mockingjay.json.creator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import okaiyi.mockingjay.commons.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 通过集合构建json,集合中的对象不能为基础类型
 *
 */
public class CollectionJsonCreator<T> implements JsonArrayCreator{
	
	private Collection<T> collection;
	private String[] fields;
	private String key;
	private Method[] methods;
	private JsonValuePolicy jsonValuePolicy;
	private FieldReplacePolicy fieldReplacePolicy;
	/**
	 * 
	 * @param collection 集合
	 * @param fields 需要集合中的所有字段,传入null,则默认使用所有get和is开头的字段
	 */
	public CollectionJsonCreator(Collection<T> collection, String[] fields){
		this(collection, fields, null,null);
	}
	/**
	 * 
	 * @param collection 集合
	 * @param fields 需要集合中的所有字段,传入null,则默认使用所有get和is开头的字段
	 * @param jsonValuePolicy 取值策略
	 */
	public CollectionJsonCreator(Collection<T> collection, String[] fields,
			JsonValuePolicy jsonValuePolicy){
		this(collection, fields, jsonValuePolicy, null);
	}
	/**
	 * 
	 * @param collection 集合
	 * @param fields 需要集合中的所有字段,传入null,则默认使用所有get和is开头的字段
	 * @param fieldReplacePolicy 字段替换策略
	 */
	public CollectionJsonCreator(Collection<T> collection, String[] fields,
			FieldReplacePolicy fieldReplacePolicy) {
		this(collection, fields, null, fieldReplacePolicy);
	}
	/**
	 * 
	 * @param collection
	 * @param fields 需要集合中的所有字段,传入null,则默认使用所有get和is开头的字段
	 * @param jsonValuePolicy
	 * @param fieldReplacePolicy 字段替换策略
	 */
	public CollectionJsonCreator(Collection<T> collection, String[] fields,
			JsonValuePolicy jsonValuePolicy,FieldReplacePolicy fieldReplacePolicy) {
		this.collection = collection;
		this.jsonValuePolicy=jsonValuePolicy;
		this.fieldReplacePolicy=fieldReplacePolicy;
		this.fields = fields;
		
	}
	
	
	@Override
	public String buildToJsonString() {
		JSONObject jo = new JSONObject();
		if(collection==null||collection.isEmpty()){
			jo.put(key, EMPTY_JSON_DATA);
			return jo.toString();
		}
		Class<?> clz = collection.iterator().next().getClass();
		methods = clz.getMethods();
		key =StringUtils.lowerFirst(clz.getSimpleName());
		if(fields==null||fields.length==0){
			Field[] members=clz.getDeclaredFields();
			this.fields=new String[members.length];
			for(int i=0;i!=members.length;++i){
				this.fields[i]=members[i].getName();
			}
		}
		jo.put(key, getJsonArray());
		return jo.toString();
	}

	@Override
	public JSONArray getJsonArray() {
		JSONArray array = new JSONArray();
		for (Iterator<T> it = collection.iterator(); it.hasNext();) {
			T obj = it.next();
			JSONObject jo = new JSONObject();
			for (String field : fields) {
				Method callMethod=getMatchMethod(field);
				if(callMethod==null){
					continue;
				}
				try {
					Object value=callMethod.invoke(obj);
					if(value!=null&&jsonValuePolicy!=null){
						JsonCreator creator=jsonValuePolicy.getCreator(field, value);
						if(creator==null){
							value=value.toString();
						}else{
							if(creator!=null&&creator instanceof JsonArrayCreator){
								value=((JsonArrayCreator)creator).getJsonArray();
							}else{
								value=creator.buildToJsonString();
							}
						}
					}
					if(fieldReplacePolicy!=null){
						String newField=fieldReplacePolicy.replace(field);
						field=newField==null?field:newField;
					}
					jo.put(field, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			array.put(jo);
		}
		return array;
	}
	/**
	 * 获取匹配传入字段的get或is方法
	 * @param field
	 * @return
	 */
	private Method getMatchMethod(String field){
		for(Method method:methods){
			String getMethodName=StringUtils.trimGetMethodName(method.getName());
			if(getMethodName.equalsIgnoreCase(field)){
				return method;
			}
			String isMethodName=StringUtils.trimIsMethodName(method.getName());
			if(isMethodName.equalsIgnoreCase(field)){
				return method;
			}
		}
		return null;
	}
	@Override
	public String toString() {
		return buildToJsonString();
	}
}
