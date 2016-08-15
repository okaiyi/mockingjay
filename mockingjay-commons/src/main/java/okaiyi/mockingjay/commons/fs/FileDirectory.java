package okaiyi.mockingjay.commons.fs;
import java.io.File;

/**
 * 文件夹描述 
 *
 */
public interface  FileDirectory {
	/**
	 * 从System.getProperty中获取临时目录
	 */
	public static final String TEMP_DIR="java.io.tmpdir";
	/**
	 * 从System.getProperty中获取用户目录
	 */
	public static final String USER_DIR="user.dir";
	/**
	 * 判断是否还可以写入指定大小的文件
	 * @param size
	 * @return
	 */
	public boolean freeSpace(long size);
	/**
	 * 在该文件夹下创建文件,文件大小0
	 * @param fileName
	 * @return 创建成功返回File,失败返回null
	 */
	public File createFile(String fileName);
	
}