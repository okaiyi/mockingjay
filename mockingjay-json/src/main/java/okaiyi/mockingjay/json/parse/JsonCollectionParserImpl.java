package okaiyi.mockingjay.json.parse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okaiyi.mockingjay.commons.data.DataTyper;
import okaiyi.mockingjay.commons.data.JavaBaseTyper;
import okaiyi.mockingjay.commons.utils.StringUtils;
import okaiyi.mockingjay.json.ParseObjectPolicy;
import okaiyi.mockingjay.json.creator.FieldReplacePolicy;

public class JsonCollectionParserImpl implements JsonCollectionParser{
	private Map<Class<?>, Set<Method>> methodMap;
	//解析后的字符
	private Map<String, DataTyper> parserData;
	
	public JsonCollectionParserImpl(String jsonText){
		this.parserData=JsonParseUtil.parser(jsonText);
		methodMap=new HashMap<Class<?>, Set<Method>>();
	}
	@SuppressWarnings("unchecked")
	private <T> T toObject(Class<?> clz,Map<String, DataTyper> fieldAndValue,
			FieldReplacePolicy fieldReplacePolicy,ParseObjectPolicy parseObjectPolicy){
		try {
			T t = (T) clz.newInstance();
			Set<Method> methods=getSetMethod(clz);
			for(Method method:methods){
				try {
					String fieldName=StringUtils.trimSetMethodName(method.getName()).toLowerCase();
					if(fieldReplacePolicy!=null){
						String newName=fieldReplacePolicy.replace(fieldName);
						fieldName=newName==null?fieldName:newName;
					}
					DataTyper value=fieldAndValue.get(fieldName);
					if(value!=null){
						if(parseObjectPolicy!=null){
							Object newObj=parseObjectPolicy.toObject(fieldName,value);
							if(newObj!=null){
								method.invoke(t,newObj);
							}else{
								method.invoke(t, getBaseObjectType(method,value));
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return t;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T> T parseToObject(Class<T> clz,FieldReplacePolicy fieldReplacePolicy,
			ParseObjectPolicy parseObjectPolicy) {
		String key =StringUtils.lowerFirst(clz.getSimpleName());
		DataTyper typer=parserData.get(key);
		if(typer!=null){
			Map<String, DataTyper> fieldAndValue=typer.getSignleResult();
			return toObject(clz,fieldAndValue, fieldReplacePolicy, parseObjectPolicy);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> parseToCollection(Class<T> clz,FieldReplacePolicy fieldReplacePolicy,
			ParseObjectPolicy parseObjectPolicy) {
		List<T> rl=new ArrayList<T>();
		String key =StringUtils.lowerFirst(clz.getSimpleName());
		DataTyper typer=parserData.get(key);
		if(typer!=null){
			List<Map<String,DataTyper>> list=typer.getList();
			for(Map<String,DataTyper> l:list){
				rl.add((T)toObject(clz, l, fieldReplacePolicy, parseObjectPolicy));
			}
		}
		return rl;
	}
	
	private Object getBaseObjectType(Method method, DataTyper value) {
		Class<?>[] clz=method.getParameterTypes();
		Object obj = value.getObject();
		if(clz!=null&&clz.length==1){
			Class<?> c=clz[0];
			if(JavaBaseTyper.isIntType(c)){
				return value.getInt(0);
			}else if(JavaBaseTyper.isBooleanType(c)){
				return value.getBoolean(false);
			}else if(JavaBaseTyper.isLongType(c)){
				return value.getLong(0);
			}else if(JavaBaseTyper.isFloatType(c)){
				return value.getFloat(0f);
			}else if(JavaBaseTyper.isDoubleType(c)){
				return value.getDouble(0d);
			}
		}
		return obj;
	}
	/**
	 * 获取指定类的所有set方法
	 * @param clz
	 * @return
	 */
	private Set<Method> getSetMethod(Class<?> clz){
		Set<Method> mts=methodMap.get(clz);
		if(mts==null){
			mts=new HashSet<Method>();
			Method[] methods=clz.getMethods();
			for(Method method:methods){
				if(method.getName().startsWith("set")){
					mts.add(method);
				}
			}
			methodMap.put(clz, mts);
		}
		return mts;
	}
}
