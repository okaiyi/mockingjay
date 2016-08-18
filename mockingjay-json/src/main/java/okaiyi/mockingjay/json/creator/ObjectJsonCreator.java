package okaiyi.mockingjay.json.creator;


public class ObjectJsonCreator<T>{

	private CollectionJsonCreator<T> cjr;
	
	public ObjectJsonCreator(T t,String fields,JsonValuePolicy valuePolicy){
		if(t==null)throw new NullPointerException();
		//cjr=new CollectionJsonCreator<>(collection, fields)
	}
	
}
