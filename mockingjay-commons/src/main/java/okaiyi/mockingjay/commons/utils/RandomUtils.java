package okaiyi.mockingjay.commons.utils;

import java.util.Random;

public class RandomUtils {
	private static Random random;
	static{
		random=new Random(System.currentTimeMillis());
	}
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getRandomString() {
		int n1=random.nextInt(99);
		int n2=random.nextInt(99);
		String pre=n1<10?"0"+String.valueOf(n1):String.valueOf(n1);
		String suffix=n2<10?"0"+String.valueOf(n2):String.valueOf(n2);
		return pre+String.valueOf(System.nanoTime())+suffix;
	}
}
