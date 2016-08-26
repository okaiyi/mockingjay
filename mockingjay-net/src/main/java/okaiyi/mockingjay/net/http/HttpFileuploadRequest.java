package okaiyi.mockingjay.net.http;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * form表单方式的文件上传
 *
 */
public class HttpFileuploadRequest extends HttpPostRequest{

	private Map<String,File> fileMap;
	
	public HttpFileuploadRequest(String url) {
		super(url);
		fileMap=new HashMap<String,File>();
	}
	
	public void addFile(File file){
		addFile(file.getName(),file);
	}
	public void addFile(String fileName,File file){
		if(file.exists()){
			fileMap.put(fileName, file);
		}
	}

	public Map<String, File> getFileMap() {
		return fileMap;
	}
	
}
