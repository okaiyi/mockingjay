package okaiyi.mockingjay.commons.fs;
/**
 * IO读写过程
 *
 */
public interface IOMonitor {
	/**
	 * 读写过程
	 * @param available 总大小
	 * @param total 已处理
	 * @param length 当前处理大小
	 */
	public void process(int available,int total,int length);
}
