package okaiyi.mockingjay.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import okaiyi.mockingjay.commons.utils.StringUtils;
/**
 * 集合json对象
 *
 */
public class CollectionJsonString extends JsonString{
	private Collection<?> collection;
	private String[] members;
	
	
	
	public CollectionJsonString(Collection<?> collection, String[] members,KeyPolicy keyPolicy,
			ValuePolicy valuePolicy) {
		super();
		this.collection = collection;
		this.members = members;
		this.keyPolicy=keyPolicy;
		this.valuePolicy=valuePolicy;
	}
	
	
	private Method getMatchMethod(Method[] methods, String methodName) {
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}

	@Override
	protected String buildToString() {
		if(collection==null||collection.isEmpty()||members==null||members.length==0){
			return EMPTY_JSON_DATA;
		}
		JSONObject jo=new JSONObject();
		Class<?> clz = collection.iterator().next().getClass();
		Method[] methods = clz.getMethods();
		String key = StringUtils.lowerFirst(clz.getSimpleName());
		
		return null;
	}
	/*@Override
	protected String buildToString() {
		if(collection==null||collection.isEmpty()||members==null||members.length==0){
			return EMPTY_JSON_DATA;
		}
		JSONObject jo = new JSONObject();
		Class<?> clz = collection.iterator().next().getClass();
		Method[] methods = clz.getMethods();
		String key = StringUtils.lowerFirst(clz.getSimpleName());
		JSONArray array = getJsonArray(collection, members, methods,keyPolicy,valuePolicy);
		jo.put(key, array);
		return jo.toString();
	}
	private <T> JSONArray getJsonArray(Collection<T> c,
			String[] reflectKey, Method[] methods,
			KeyPolicy replaceKeyPolicy, ValuePolicy valueProlicy) {
		JSONArray array = new JSONArray();
		if (c != null && !c.isEmpty() && methods != null) {
			for (Iterator<T> it = c.iterator(); it.hasNext();) {
				T obj = it.next();
				JSONObject jo = new JSONObject();
				for (String ref : reflectKey) {
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
						if (replaceKeyPolicy != null){
							key = replaceKeyPolicy.replace(ref);
						}
						if(key==null){
							key=ref;
						}
						Object newValue=null;
						if (valueProlicy != null) {
							newValue = valueProlicy.getValue(ref, value);
						}
						if(newValue!=null){
							value=newValue;
						}
						if(value==null){
							value="";					
						}
						jo.put(key, value);
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
		}
		return array;
	}
*/
	
}
