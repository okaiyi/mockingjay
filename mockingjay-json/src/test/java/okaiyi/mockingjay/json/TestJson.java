package okaiyi.mockingjay.json;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import okaiyi.mockingjay.commons.data.DataTyper;
import okaiyi.mockingjay.commons.data.JavaBaseTyper;
import okaiyi.mockingjay.commons.utils.CollectionsUtils;
import okaiyi.mockingjay.json.creator.CollectionJsonCreator;
import okaiyi.mockingjay.json.creator.DateJsonCreator;
import okaiyi.mockingjay.json.creator.FieldReplacePolicy;
import okaiyi.mockingjay.json.creator.JsonCreator;
import okaiyi.mockingjay.json.creator.JsonValuePolicy;
import okaiyi.mockingjay.json.creator.SimpleCollectionJsonCreator;
import okaiyi.mockingjay.json.parse.JsonCollectionParser;
import okaiyi.mockingjay.json.parse.JsonCollectionParserImpl;

import org.junit.Test;

public class TestJson {
	@Test
	public void collectionSignleTest(){
		ArrayList<String> beanList=new ArrayList<String>();
		for(int n=0;n<2;n++){
			beanList.add("list:"+n);
		}
		JsonCreator creator=new CollectionJsonCreator<String>(beanList, null);
		System.out.println(creator);
	}
	
	@Test
	public void getParameterType() throws NoSuchMethodException, SecurityException{
		Class<?> clz=TestJsonBean.class;
		Method[] mt=clz.getMethods();
		for(Method m:mt){
			if(m.getName().equals("setAge")){
				Class[] cs=m.getParameterTypes();
				System.out.println(JavaBaseTyper.isIntType(cs[0]));
			}
		}
		Integer i=11;
		System.out.println(Integer.class.isAssignableFrom(i.getClass()));
	}
	
	@Test
	public void testType(){
		Character c='A';
		System.out.println(JavaBaseTyper.isCharType(c.getClass()));
		Byte b=0;
		System.out.println(JavaBaseTyper.isByteType(b.getClass()));
	}
	
	@Test
	public void collectionTest(){
		List<TestJsonBean> tjb=new ArrayList<TestJsonBean>();
		for(int i=0;i<2;i++){
			TestJsonBean bean=new TestJsonBean();
			bean.setAge(10+i);
			bean.setName("name:"+i);
			bean.setDate(new Date());
			ArrayList<String> beanList=new ArrayList<String>();
			for(int n=0;n<2;n++){
				beanList.add("list:"+i+":"+n);
			}
			//bean.setBeanList(beanList);
			tjb.add(bean);
		}
		JsonCreator creator=new CollectionJsonCreator<>(tjb, null,new JsonValuePolicy() {
			
			@Override
			public JsonCreator getCreator(String k, Object obj) {
				if(k.equals("date")){
					return new DateJsonCreator<>((Date)obj,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				}else if(k.equals("beanList")){
					List<String> list=CollectionsUtils.tryToList(obj);
					return new SimpleCollectionJsonCreator<>(list, "beanList");
				}
				return null;
			}
		},new FieldReplacePolicy() {
			
			@Override
			public String replace(String field) {
				return field.equals("name")?"my_name":field;
			}
		});
		System.out.println(creator.buildToJsonString());
	}
	@Test
	public void testParse(){
		String json="{'testJsonBean':[{'date':'2016-08-18 15:29:46','my_name':'name:0','age':'10'}]}";
		JsonCollectionParser jcp=new JsonCollectionParserImpl(json);
		TestJsonBean bean=jcp.parseToObject(TestJsonBean.class,new FieldReplacePolicy() {
			
			@Override
			public String replace(String field) {
				//System.out.println(field);
				return field.equals("name")?"my_name":field;
			}
		},new ParseObjectPolicy() {
			
			@Override
			public Object toObject(String fieldName,DataTyper data) {
				if(fieldName.equals("date")){
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						return df.parse(data.getString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
		System.out.println(bean.getAge());
	}
	@Test
	public void testToList(){
		String json="{'testJsonBean':[{'date':'2016-08-18 15:29:46','my_name':'name:0','age':'10'},{'date':'2016-08-18 15:29:46','my_name':'name:1','age':'11'}]}";
		JsonCollectionParser jcp=new JsonCollectionParserImpl(json);
		Collection<TestJsonBean> list=jcp.parseToCollection(TestJsonBean.class,new FieldReplacePolicy() {
			
			@Override
			public String replace(String field) {
				return field.equals("name")?"my_name":field;
			}
		},new ParseObjectPolicy() {
			
			@Override
			public Object toObject(String fieldName,DataTyper data) {
				if(fieldName.equals("date")){
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						return df.parse(data.getString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
		System.out.println(list.iterator().next().getName());
	}
	
}
