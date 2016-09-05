package okaiyi.mockingjay.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.Comparator;

import okaiyi.mockingjay.commons.fs.FileDirectory;
import okaiyi.mockingjay.commons.fs.FileFragment;
import okaiyi.mockingjay.commons.fs.FileFragmentReaderHandler;
import okaiyi.mockingjay.commons.fs.IOMonitor;

/**
 * IO流工具
 */
public class IOStreamUtils {
	private static final int DEFAULT_BUFF = 1024;

	/**
	 * 读取文件碎片
	 * 
	 * @param file
	 *            读取的文件
	 * @param splitSize
	 *            分片大小
	 * @param handler
	 *            读取完一个分片后的回调
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static final void fileFragmentReader(File file, int splitSize, FileFragmentReaderHandler handler)
			throws FileNotFoundException, IOException {
		if (handler == null)
			return;
		FileFragment[] fragments = FileFragment.filtToFragment(file, splitSize);
		RandomAccessFile read=new RandomAccessFile(file, "rw");
		FileChannel channel=read.getChannel();
		FileLock lock=channel.lock();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		for(FileFragment fragment:fragments){
			int len=-1;
			int offset=fragment.getOffset();
			boolean canRead=true;
			int readLength=fragment.getCurrentLength();
			byte[] bytes=new byte[readLength];
			do{
				read.seek(offset);
				len=read.read(bytes, 0, readLength);
				out.write(bytes, 0, len);
				if(len<readLength){
					offset+=len;
					readLength-=len;
				}else{
					canRead=false;
				}
			}while(canRead);
			handler.fragmentRead(file, fragment,out.toByteArray());
			out.reset();
		}
		lock.release();
		close(read);
		
	}

	/**
	 * 将文件碎片写入文件 
	 * 
	 * @param file
	 * @param fragment
	 * @param bytes
	 * @throws IOException
	 */
	public static final void fileFragmentWriter(File file,final FileFragment fragment, byte[] bytes) throws IOException {
		//先写入到临时文件,当写入完毕后在拷贝到指定的file下
		File tempDir=new File(System.getProperty(FileDirectory.TEMP_DIR));
		String tempFileName=fragment.getRandomId()+"_"+fragment.getCurrentSegment();
		File tempFile=new File(tempDir, tempFileName);
		FileOutputStream out=new FileOutputStream(tempFile);
		out.write(bytes, 0, bytes.length);
		out.flush();
		out.close();
		//判断所有碎片是否完全写入完毕
		File[] files=tempDir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathName) {
				if(pathName.getName().startsWith(fragment.getRandomId())){
					return true;
				}
				return false;
			}
		});
		if(files.length==fragment.getTotalSegment()){
			Arrays.sort(files,new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					Integer i1=Integer.parseInt(StringUtils.rightSubString(f1.getName(), "_", false));
					Integer i2=Integer.parseInt(StringUtils.rightSubString(f2.getName(), "_", false));
					return i1.compareTo(i2);
				}
			});
			if(file.exists()){
				file.delete();
			}
			FileOutputStream fos=new FileOutputStream(file, true);
			for(File readFile:files){
				FileInputStream in=new FileInputStream(readFile);
				writeToStream(in, fos, false);
				close(in);
				readFile.delete();
			}
			close(fos);
		}
	}
	public static final boolean writeToStream(InputStream in, OutputStream out, boolean isClose, int buff){
		return writeToStream(in, out, isClose, buff,null);
	}
	/**
	 * 将输入流写出到输出流
	 */
	public static final boolean writeToStream(InputStream in, OutputStream out, boolean isClose, int buff,
			IOMonitor ioMonitor) {
		try {
			int len = -1;
			byte[] bytes = new byte[buff];
			int available=in.available();
			int total=0;
			while ((len = in.read(bytes, 0, buff)) != -1) {
				out.write(bytes, 0, len);
				if(ioMonitor!=null){
					ioMonitor.process(available, total, len);
					total+=len;
				}
			}
			if (isClose) {
				close(in);
				close(out);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将输入流写出到输出流
	 */
	public static final boolean writeToStream(InputStream in, OutputStream out, boolean isClose) {
		return writeToStream(in, out, isClose, DEFAULT_BUFF);
	}

	/**
	 * 关闭IO流
	 * 
	 * @param closeable
	 */
	public static final void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
				closeable = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}