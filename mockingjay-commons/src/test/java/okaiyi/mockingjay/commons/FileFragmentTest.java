package okaiyi.mockingjay.commons;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import okaiyi.mockingjay.commons.fs.FileDirectory;
import okaiyi.mockingjay.commons.fs.FileFragment;
import okaiyi.mockingjay.commons.fs.FileFragmentReaderHandler;
import okaiyi.mockingjay.commons.fs.IOStreamUtils;
import okaiyi.mockingjay.commons.utils.StringUtils;

public class FileFragmentTest {
	/**
	 * 文件碎片的使用（网络传输时常用）
	 * @throws IOException
	 */
	@Test
	public void testFragment() throws IOException{
		//将文件按照指定的分割大小进行读取
		final File writeFile=new File("/Users/kaiyi/googlechrome_mac_50.0.2661.102.dmg");
		final Map<FileFragment, byte[]> map=new HashMap<FileFragment, byte[]>();
		IOStreamUtils.fileFragmentReader(new File("/Users/kaiyi/Downloads/googlechrome_mac_50.0.2661.102.dmg"), 4194304, new FileFragmentReaderHandler() {
			
			@Override
			public void fragmentRead(File file, FileFragment fragment, byte[] bytes) {
				try {
					//每读取一次分片，就会调用一次该方法
					//IOStreamUtils.fileFragmentWriter(writeFile, fragment, bytes);
					//System.out.println(fragment.getCurrentSegment()+":"+bytes.length);
					map.put(fragment, bytes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Set<FileFragment> keys=map.keySet();
		for(FileFragment key:keys){
			//将文件碎片写入文件
			IOStreamUtils.fileFragmentWriter(writeFile, key, map.get(key));
		}
	}
	
	@Test
	public void printTempDir(){
		String tempDir=System.getProperty(FileDirectory.TEMP_DIR);
		System.out.println(tempDir);
	}
	@Test
	public void fileSort(){
		File[] file=new File[]{
			new File("43214214213_1"),
			new File("43214214213_11"),
			new File("43214214213_3")	
		};
		Arrays.sort(file,new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				Integer i1=Integer.parseInt(StringUtils.rightSubString(o1.getName(), "_", false));
				Integer i2=Integer.parseInt(StringUtils.rightSubString(o2.getName(), "_", false));
				return i1.compareTo(i2);
			}
		});
		for(File f:file){
			System.out.println(f.getName());
		}
	}
	@Test
	public void testSrc(){
		System.out.println(StringUtils.rightSubString("43214214213_3", "_",false));
	}
}
