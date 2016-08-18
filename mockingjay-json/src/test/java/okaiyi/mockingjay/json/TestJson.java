package okaiyi.mockingjay.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okaiyi.mockingjay.commons.utils.CollectionsUtils;
import okaiyi.mockingjay.json.creator.CollectionJsonCreator;
import okaiyi.mockingjay.json.creator.DateJsonCreator;
import okaiyi.mockingjay.json.creator.FieldReplacePolicy;
import okaiyi.mockingjay.json.creator.JsonCreator;
import okaiyi.mockingjay.json.creator.JsonValuePolicy;
import okaiyi.mockingjay.json.creator.SimpleCollectionJsonCreator;

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
			bean.setBeanList(beanList);
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
}
