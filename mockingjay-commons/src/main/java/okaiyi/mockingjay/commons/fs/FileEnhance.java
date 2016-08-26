package okaiyi.mockingjay.commons.fs;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import okaiyi.mockingjay.commons.utils.IOStreamUtils;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 文件描述,仅描述文件,不描述文件夹
 */
public class FileEnhance implements FileDescriptor,FileDirectory{
	
	private File file;
	
	private FileEnhance(File file){
		this.file=file;
	}
	/**
	 * 获取文件描述
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileDescriptor getDescriptor(File file)throws IOException{
		if(file.isDirectory())throw new IOException("File is must be file.");
		if(!file.exists())throw new IOException("File not found Exception");
		FileEnhance fe=new FileEnhance(file);
		return fe;
	}
	/**
	 * 获取文件夹描述,如果文件夹不存在,则创建
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileDirectory getDirectory(File file)throws IOException{
		if(file.isDirectory())throw new IOException("File is must be Directory.");
		Path path=Paths.get(file.toURI());
		if(!Files.exists(path, LinkOption.NOFOLLOW_LINKS)){//写文件目录不存在,创建写文件目录
			Files.createDirectories(path);
		}
		
		FileEnhance fe=new FileEnhance(file);
		return fe;
	}
	/**
	 * 构造文件描述对象
	 * @param path 文件绝对路径
	 * @throws FileNotFoundException 如果文件是一个目录,抛出此异常
	 */
	public FileEnhance(String path){
		this(new File(path));
	}
	/**
	 * 构造文件描述对象
	 * @param url 文件的url描述方式
	 * @throws IOException 如果文件是一个目录,抛出此异常
	 */
	public FileEnhance(URL url){
		this(new File(url.getFile()));
	}
	/**
	 * 获取文件mimeType
	 * @return
	 */
	public String getMimeType(){
		return FileMimeType.getMimeType(file);
	}
	/**
	 * 获取文件后缀名,包含.
	 * @return
	 */
	public String getSuffix(){
		String fileName=file.getName();
		int index=fileName.lastIndexOf(".");
		if(index<0){
			return null;
		}
		return fileName.substring(index+1,fileName.length());
	}
	/**
	 * 获取文件描述的File
	 * @return
	 */
	@Override
	public File getFile() {
		return this.file;
	}
	/**
	 * 获取文件所在文件夹路径
	 * @return
	 */
	@Override
	public String getFolderPath() {
		return file.getAbsolutePath().replace(file.getName(), "");
	}
	@Override
	public long getByteSize() {
		if(file.exists())return file.length();
		return 0;
	}
	@Override
	public boolean copyToFile(File f) {
		if(file.exists()){
			try {
				FileInputStream in=new FileInputStream(file);
				FileOutputStream out=new FileOutputStream(f);
				IOStreamUtils.writeToStream(in, out, true);
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	public boolean copyToFile(String path) {
		File f=new File(path);
		return copyToFile(f);
	}
	@Override
	public InputStream getInputStream() {
		try {
			FileInputStream fis=new FileInputStream(file);
			BufferedInputStream in=new BufferedInputStream(fis);
			return in;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Reader getReader() {
		try {
			FileReader fr=new FileReader(file);
			BufferedReader reader=new BufferedReader(fr);
			return reader;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean freeSpace(long size) {
		return file.getFreeSpace()>size;
	}
	@Override
	public File createFile(String fileName) {
		File f=new File(this.file.getAbsoluteFile(),fileName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	@Override
	public String md5() {
		FileInputStream in=null;
		String md5=null;
		try {
			in=new FileInputStream(this.file);
			md5 = DigestUtils.md5Hex(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOStreamUtils.close(in);
		}
		return md5;
	}
	@Override
	public String sha1() {
		FileInputStream in=null;
		String sha1=null;
		try {
			in=new FileInputStream(this.file);
			sha1 = DigestUtils.sha1Hex(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOStreamUtils.close(in);
		}
		return sha1;
	}
}