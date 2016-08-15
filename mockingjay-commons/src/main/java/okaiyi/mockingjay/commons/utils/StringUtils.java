package okaiyi.mockingjay.commons.utils;
public class StringUtils {
	/**
	 * 从指定的字符开始向右边截取<br>
	 * 例:rightSubString("abc_d","d",true),返回_d<br>
	 * rightSubString("a_bc_d","d",false),返回d
	 * @param src 需要截取的原字符串
	 * @param c 需要截取的字符
	 * @param contain 返回结果是否包含原字符
	 * @return
	 */
	public static final String rightSubString(String src,String c,boolean contain){
		int index=src.lastIndexOf(c);
		return src.substring(contain?index:index+1, src.length());
	}
	@SuppressWarnings("restriction")
	private static String lineSeparator = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));
	/**
	 * 截取掉Java方法名set,get,并将首字母小写
	 * @return
	 
	public final static String methodNameTrim(String methodName){
		if(methodName.startsWith("get")||methodName.startsWith("set")){
			methodName=methodName.substring("get".length(), methodName.length());
			char[] first=new char[]{methodName.charAt(0)};
			return new String(first).toLowerCase()+methodName.substring(1,methodName.length());
		}else{
			return methodName;
		}
	}
	*/
	/**
	 * 从开始字符开始截取
	 * @param startString
	 * @param string
	 * @return
	 */
	public final static String trimStartString(String startString,String string){
		if(string.startsWith(startString)){
			return string.substring(startString.length(), string.length());
		}else{
			return string;
		}
	}
	
	/**
	 * 截取掉方法名的get字符
	 * @param methodName
	 * @return
	 */
	public final static String trimGetMethodName(String methodName){
		return trimStartString("get", methodName);
	}
	/**
	 * 截取掉方法名的set字符
	 * @param methodName
	 * @return
	 */
	public final static String trimSetMethodName(String methodName){
		return trimStartString("set", methodName);
	}
	/**
	 * 截取掉方法名的is字符
	 * @param methodName
	 * @return
	 */
	public final static String trimIsMethodName(String methodName){
		return trimStartString("is", methodName);
	}
	/**首字母大写*/
	public final static String upperFirst(String str){
		String temp=str.toUpperCase();
		return temp.substring(0, 1)+str.substring(1, str.length());
	}
	/**首字母小写*/
	public final static String lowerFirst(String str){
		String temp=str.toLowerCase();
		return temp.substring(0, 1)+str.substring(1, str.length());
	}
	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}
	public final static String newLine(){
		return StringUtils.lineSeparator;
	}
	/**
	 * 替换双引号为单引号
	 * @param text
	 * @return
	 */
	public final static String singleQuotationMark(String text){
		return text.replace("\"", "'");
	}
	/**
	 * 替换单引号为双引号
	 * @param text
	 * @return
	 */
	public final static String doubleQuotationMark(String text){
		return text.replace("\'", "\"");
	}
}
