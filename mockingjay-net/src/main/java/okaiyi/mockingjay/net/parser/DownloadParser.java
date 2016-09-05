package okaiyi.mockingjay.net.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import okaiyi.mockingjay.commons.fs.FileDescriptor;
import okaiyi.mockingjay.commons.fs.FileDirectory;
import okaiyi.mockingjay.commons.fs.FileEnhance;
import okaiyi.mockingjay.commons.fs.IOMonitor;
import okaiyi.mockingjay.commons.utils.IOStreamUtils;
import okaiyi.mockingjay.net.NetworkData;
import okaiyi.mockingjay.net.NetworkException;
import okaiyi.mockingjay.net.ResponseParser;
import okaiyi.mockingjay.net.data.FileNetworkData;

/**
 * 文件下载解析
 *
 */
public class DownloadParser extends ResponseParser<FileDescriptor>{
	
	private IOMonitor monitor;
	
	private FileDirectory downloadDir;
	
	public DownloadParser(){
		super();
	}
	
	public DownloadParser(int buf){
		this();
		this.buf=buf;
	}
	/**
	 * 设置下载文件夹
	 */
	public void setDownloadDir(FileDirectory downloadDir) {
		this.downloadDir = downloadDir;
	}

	public void setMonitor(IOMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public NetworkData<FileDescriptor> parse(InputStream in,int available, String charset)
			throws NetworkException{
		try {
			if(downloadDir==null){
				String tmpPath=System.getProperty(FileEnhance.TEMP_DIR);
				downloadDir=FileEnhance.getDirectory(new File(tmpPath));				
			}
			String tmpFileName=UUID.randomUUID().toString();
			if(downloadDir.freeSpace(available)){
				File saveFile=new File(downloadDir.getFileDir(),tmpFileName);
				FileOutputStream out=new FileOutputStream(saveFile);
				IOStreamUtils.writeToStream(new DownloadInputstream(in, available),out,true, buf,
						monitor);
				return new FileNetworkData(saveFile);
			}else{
				throw new NetworkException("Temp Directory space insufficient");
			}
		} catch (IOException e) {
			throw new NetworkException(e);
		}
	}
}
