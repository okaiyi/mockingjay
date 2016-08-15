package okaiyi.mockingjay.commons.fs;

import java.io.File;

/**
 * 文件碎片读取回调
 */
public interface FileFragmentReaderHandler {
	/**
	 * 完整的读取完一个文件碎片
	 * @param file
	 * @param fragment
	 * @param bytes
	 */
	void fragmentRead(File file,FileFragment fragment,byte[] bytes);
}
