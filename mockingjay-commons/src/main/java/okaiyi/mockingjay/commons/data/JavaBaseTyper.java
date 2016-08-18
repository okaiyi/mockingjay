package okaiyi.mockingjay.commons.data;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Java基本类型
 *
 */
public class JavaBaseTyper {
	private static final Map<Class<?>, Integer> javaTypeToSqlTypeMap = new HashMap<Class<?>, Integer>(32);
	static {
		javaTypeToSqlTypeMap.put(boolean.class, Types.BOOLEAN);
		javaTypeToSqlTypeMap.put(Boolean.class, Types.BOOLEAN);
		javaTypeToSqlTypeMap.put(String.class, Types.VARCHAR);
		javaTypeToSqlTypeMap.put(byte.class, Types.TINYINT);
		javaTypeToSqlTypeMap.put(Byte.class, Types.TINYINT);
		javaTypeToSqlTypeMap.put(short.class, Types.SMALLINT);
		javaTypeToSqlTypeMap.put(Short.class, Types.SMALLINT);
		javaTypeToSqlTypeMap.put(int.class, Types.INTEGER);
		javaTypeToSqlTypeMap.put(Integer.class, Types.INTEGER);
		javaTypeToSqlTypeMap.put(long.class, Types.BIGINT);
		javaTypeToSqlTypeMap.put(Long.class, Types.BIGINT);
		javaTypeToSqlTypeMap.put(BigInteger.class, Types.BIGINT);
		javaTypeToSqlTypeMap.put(float.class, Types.FLOAT);
		javaTypeToSqlTypeMap.put(Float.class, Types.FLOAT);
		javaTypeToSqlTypeMap.put(double.class, Types.DOUBLE);
		javaTypeToSqlTypeMap.put(Double.class, Types.DOUBLE);
		javaTypeToSqlTypeMap.put(BigDecimal.class, Types.DECIMAL);
		javaTypeToSqlTypeMap.put(java.sql.Date.class, Types.DATE);
		javaTypeToSqlTypeMap.put(java.sql.Time.class, Types.TIME);
		javaTypeToSqlTypeMap.put(java.sql.Timestamp.class, Types.TIMESTAMP);
		javaTypeToSqlTypeMap.put(Blob.class, Types.BLOB);
		javaTypeToSqlTypeMap.put(Clob.class, Types.CLOB);
	}
	/**
	 * 是否为字符串类型
	 * @param inValueType
	 * @return
	 */
	public static boolean isStringValue(Class<?> inValueType) {
		return (CharSequence.class.isAssignableFrom(inValueType) ||
				StringWriter.class.isAssignableFrom(inValueType));
	}
	/**
	 * 是否为int类型
	 * @param clz
	 * @return
	 */
	public static boolean isIntType(Class<?> clz){
		return Integer.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("int");
	}
	/**
	 * 是否为boolean类型
	 * @param clz
	 * @return
	 */
	public static boolean isBooleanType(Class<?> clz){
		return Boolean.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("boolean");
	}
	/**
	 * 是否为float类型
	 * @param clz
	 * @return
	 */
	public static boolean isFloatType(Class<?> clz){
		return Float.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("float");
	}
	/**
	 * 是否为double类型
	 * @param clz
	 * @return
	 */
	public static boolean isDoubleType(Class<?> clz){
		return Double.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("double");
	}
	/**
	 * 是否为short
	 * @param clz
	 * @return
	 */
	public static boolean isShortType(Class<?> clz){
		return Short.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("short");
	}
	/**
	 * 是否为byte类型
	 * @param clz
	 * @return
	 */
	public static boolean isByteType(Class<?> clz){
		return Byte.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("byte");
	}
	/**
	 * 是否为long类型
	 * @param clz
	 * @return
	 */
	public static boolean isLongType(Class<?> clz){
		return Long.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("long");
	}
	/**
	 * 是否为char类型
	 * @param clz
	 * @return
	 */
	public static boolean isCharType(Class<?> clz){
		return Character.class.isAssignableFrom(clz)||clz.getName().equalsIgnoreCase("char");
	}
	
	
}
