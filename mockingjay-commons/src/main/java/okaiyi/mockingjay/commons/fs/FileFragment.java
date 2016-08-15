package okaiyi.mockingjay.commons.fs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okaiyi.mockingjay.commons.utils.RandomUtils;

/**
 * 文件片段，调用该类的静态方法filtToFragment获取文件分片
 *
 */
public class FileFragment {
	// 分片参考长度
	private int splitLength;
	// 文件长度
	private long fileLength;
	// 总片段数
	private int totalSegment;
	// 当前片段
	private int currentSegment;
	// 当前分片长度
	private int currentLength;
	//开始偏移量
	private int offset;
	//随机id号
	private String randomId;
	/**
	 * 获取分片参考长度
	 * @return
	 */
	public int getSplitLength() {
		return splitLength;
	}
	/**
	 * 获取文件总长度
	 * @return
	 */
	public long getFileLength() {
		return fileLength;
	}
	/**
	 * 获取总分片书
	 * @return
	 */
	public int getTotalSegment() {
		return totalSegment;
	}
	/**
	 * 获取当前分片书
	 * @return
	 */
	public int getCurrentSegment() {
		return currentSegment;
	}
	/**
	 * 获取当前分片长度
	 * @return
	 */
	public int getCurrentLength() {
		return currentLength;
	}
	private FileFragment(String randomId,int splitLength, long fileLength, int totalSegment, int currentSegment, int currentLength,
			int offset) {
		this.splitLength = splitLength;
		this.fileLength = fileLength;
		this.totalSegment = totalSegment;
		this.currentSegment = currentSegment;
		this.currentLength = currentLength;
		this.offset=offset;
		this.randomId=randomId;
	}
	/**
	 * 获取随机访问符，统一个文件的随机访问符一致
	 * @return
	 */
	public String getRandomId() {
		return randomId;
	}
	/**
	 * 获取文件起始偏移量
	 * @return
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * 对文件进行快速分片
	 * @param file 要进行分片的文件
	 * @param splitSize 分片大小
	 * @throws FileNotFoundException
	 * @throws IOException 如果分片过多，超过int类型所能承受的范围,抛出该异常
	 * @return 
	 */
	public static final FileFragment[] filtToFragment(File file,int splitSize)throws FileNotFoundException,IOException{
		String random=null;
		random=RandomUtils.getRandomString();
		if(!file.exists()){
			throw new FileNotFoundException("File is not exists."+file.getAbsolutePath());
		}
		if(file.isDirectory()){
			throw new IOException("File is Directory.");
		}
		if(splitSize<=0){
			throw new IllegalArgumentException("SplitSize must greater than zero.");
		}
		long fileLength=file.length();
		//计算分片大小
		long count=fileLength/splitSize;
		long lastLength=-1;
		if(fileLength%splitSize!=0){
			lastLength=fileLength-(splitSize*count);
			++count;
			
		}
		if(count>Integer.MAX_VALUE){
			throw new IOException("Split count "+count+", but max integer is "+Integer.MAX_VALUE);
		}
		FileFragment[] fragments=new FileFragment[(int)count];
		for(int i=0;i<fragments.length;i++){
			int os=splitSize*i;
			if(lastLength!=-1&&i==fragments.length-1){
				fragments[i]=new FileFragment(random,splitSize, fileLength, (int)count,i,(int)lastLength,os);
			}else{
				fragments[i]=new FileFragment(random,splitSize, fileLength, (int)count,i,splitSize,os);
			}
		}
		return fragments;
	}
	
	@Override
	public String toString() {
		return "FileFragment [splitLength=" + splitLength + ", fileLength=" + fileLength + ", totalSegment="
				+ totalSegment + ", currentSegment=" + currentSegment + ", currentLength=" + currentLength + ", offset="
				+ offset + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentSegment;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileFragment other = (FileFragment) obj;
		if (currentSegment != other.currentSegment)
			return false;
		return true;
	}
}
