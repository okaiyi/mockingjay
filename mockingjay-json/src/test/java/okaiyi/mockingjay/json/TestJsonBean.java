package okaiyi.mockingjay.json;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestJsonBean {
	private String name;
	private int age;
	private Date date;
	private Map<String, String> map;
	private List<String> beanList;
	
	
	
	public List<String> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<String> beanList) {
		this.beanList = beanList;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
