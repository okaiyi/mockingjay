package okaiyi.mockingjay.json.creator;
/**
 * json取值策略
 *
 */
public interface JsonValuePolicy {
	/**
	 * 返回json构造器
	 * @param k key或者字段名称
	 * @param obj 实例对象
	 * @return 返回null,则调用者会直接调用obj的toString方法来赋值
	 */
	JsonCreator getCreator(String k,Object obj);
}
