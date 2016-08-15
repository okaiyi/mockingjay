package okaiyi.mockingjay.commons.fs;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;

/**
 * 文件描述
 *
 */
public interface  FileDescriptor {
	/**
	 * 获取文件
	 * @return
	 */
	public File getFile();
	/**
	 * 获取文件mimeType
	 * @return
	 */
	public String getMimeType();
	/**
	 * 获取文件后缀名，不包含.
	 * @return
	 */
	public String getSuffix();
	/**
	 * 文件文件所在目录路径
	 * @return
	 */
	public String getFolderPath();
	/**
	 * 获取文件大小,单位b
	 * @return
	 */
	public long getByteSize();
	/**
	 * 复制文件到指定的文件位置
	 * @param file
	 * @return
	 */
	public boolean copyToFile(File file);
	/**
	 * 复制文件到指定的文件位置
	 * @param path 绝对路径
	 * @return
	 */
	public boolean copyToFile(String path);
	/**
	 * 获取文件输入流,使用后需自行关闭
	 * @return
	 */
	public InputStream getInputStream();
	/**
	 * 获取reader
	 * @return
	 */
	public Reader getReader();
	/**
	 * 对文件进行md5
	 * @return
	 */
	public String md5();
	/**
	 * 对文件进行sha1
	 * @return
	 */
	public String sha1();
}