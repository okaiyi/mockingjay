package okaiyi.mockingjay.net.data;

import java.io.File;
import java.io.IOException;

import okaiyi.mockingjay.commons.fs.FileDescriptor;
import okaiyi.mockingjay.commons.fs.FileEnhance;
import okaiyi.mockingjay.net.NetworkData;

public class FileNetworkData implements NetworkData<FileDescriptor>{

	private FileDescriptor fd;

	public FileNetworkData(File file){
		try {
			fd = FileEnhance.getDescriptor(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public FileDescriptor getData() {
		return fd;
	}

}
