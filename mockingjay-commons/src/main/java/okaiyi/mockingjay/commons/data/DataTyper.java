package okaiyi.mockingjay.commons.data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 通用数据类型
 */
public class DataTyper {
	public Object obj;
	/**
	 * 初始化一个数据类型
	 * @param obj
     */
	public DataTyper(Object obj){
		this.obj=obj;
	}
	/**
	 * 构建一个数据类型
	 * @param obj
     */
	public static final DataTyper create(Object obj){
		return new DataTyper(obj);
	}
	
	public Long getLong(long def){
		try{
			return Long.valueOf(obj.toString());
		}catch(Exception e){
			return def;
		}
	}
	
	public Object getObject(){
		return obj;
	}
	
	public Boolean getBoolean(boolean def){
		try{
			return Boolean.valueOf(obj.toString());
		}catch(Exception e){
			return def;
		}
	}
	
	public Float getFloat(float def){
		try{
			return Float.valueOf(obj.toString());
		}catch(Exception e){
			return def;
		}
	}
	public Double getDouble(double def){
		try{
			return Double.valueOf(obj.toString());
		}catch(Exception e){
			return def;
		}
	}
	public Integer getInt(int def){
		try{
			return Integer.valueOf(obj.toString());
		}catch(Exception e){
			return def;
		}
	}
	/**
	 * 无法获取返回null
	 */
	public Map<String, DataTyper> getSignleResult(){
		List<Map<String, DataTyper>> list=getList();
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, DataTyper>> getList(){
		@SuppressWarnings("unchecked")
		List<Map<String, DataTyper>> list = (List<Map<String, DataTyper>>)obj;
		if(list==null){
			list=new ArrayList<Map<String, DataTyper>>();
		}
		return list;
	}
	
	
	public String getString(){
		try{
			return String.valueOf(obj.toString());
		}catch(Exception e){
			return "";
		}
	}
	
	@Override
	public String toString() {
		if(obj!=null){
			return getString();
		}
		return super.toString();
	}
}
