package okaiyi.mockingjay.commons.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类 
 *
 */
public class CollectionsUtils {
	/**
	 * 获取集合中的泛型类型,如果集合类没有内容,无法正常返回
	 * @param collection
	 * @return
	 */
	public static final Class<?> getGenericType(Collection<?> collection){
		if(collection==null||collection.isEmpty()){
			return Object.class;
		}else{
			return collection.iterator().next().getClass();
		}
	}
	/**
	 * 将List集合转换为Set
	 * @param list
	 * @return
	 */
	public static final <T> Set<T> listToSet(List<T> list){
		Set<T> set=Collections.emptySet();
		for(int i=0;i!=list.size();++i){
			set.add(list.get(i));
		}
		return set;
	}
	/**
	 * 将Set集合转换为List
	 * @param set
	 * @return
	 */
	public static final <T> List<T> setToList(Set<T> set){
		List<T> list=Collections.emptyList();
		for(Iterator<T> it=set.iterator();it.hasNext();){
			list.add(it.next());
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> List<T> tryToList(Object obj){
		return (List<T>)obj;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> Set<T> tryToSet(Object obj){
		return (Set<T>)obj;
	}
}
