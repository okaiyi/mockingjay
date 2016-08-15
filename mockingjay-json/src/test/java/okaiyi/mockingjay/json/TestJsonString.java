package okaiyi.mockingjay.json;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TestJsonString {
	@Test
	public void testMapJson(){
		Map<String, String> kv=new HashMap<String, String>();
		kv.put("ab", "cd");
		kv.put("de", "fa");
		JsonString js=JsonString.buildEntryJson(kv);
		System.out.println(js.buildToString());
	}
	@Test
	public void testCollection(){
		Set<TestJsonBean> tjb=new HashSet<TestJsonBean>();
		for(int i=0;i<2;i++){
			TestJsonBean tb=new TestJsonBean();
			tb.setAge(20+i);
			tb.setDate(new Date());
			tb.setName("name_"+i);
			Map<String, String> map=new HashMap<>();
			map.put("k1", "v1");
			map.put("k2", "v2");
			tb.setMap(map);
			tjb.add(tb);
		}
		JsonString js=JsonString.buildCollectionJson(tjb, new String[]{
			"age","date","name","map"
		},new JsonString.ValuePolicy() {
			
			@Override
			public Object getValue(String fieldName, Object obj) {
				if(fieldName.equals("map")){
					Map<String, String> m=(Map<String, String>)obj;
					JsonString mjs=JsonString.buildEntryJson(m);
					
					return mjs.buildToString();
				}
				return null;
			}
		});
		System.out.println(js.buildToString());
	}
}
