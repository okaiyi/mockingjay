package okaiyi.mockingjay.pki.keystore;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/**
 * java秘钥管理器
 */
public class KeyStoreManager{
	private KeyStore keystore;
	
	private String firstAlias;
	/**
	 * KeyStore 类型
	 *
	 */
	public static enum KeyStoreType{
		/**
		 * 默认java提供的p12或pfx文件
		 * 提供者SunJSSE
		 */
		PKCS12{
			@Override
			public String toString() {
				return "PKCS12";
			}
		},
		/**
		 * 默认java提供的keystore文件
		 * 提供者SunJCE
		 */
		JCEKS{
			@Override
			public String toString() {
				return "JCEKS";
			}
		},
		/**
		 * windows个人证书库
		 * 提供者SunMSCAPI 
		 */
		WINDOWS_MY{
			@Override
			public String toString() {
				return "Windows-MY";
			}
		},
		/**
		 * windows根证书库
		 * 提供者SunMSCAPI
		 */
		WINDOWS_ROOT{
			@Override
			public String toString() {
				return "Windows-ROOT";
			}
		}
	}
	/**
	 * 秘钥提供者
	 *
	 */
	public static enum KeyStoreProvider{
		/**
		 * 提供者，访问JCEKS类型时使用
		 */
		PROVIDER_SUNJCE{
			@Override
			public String toString() {
				return "SunJCE";
			}
		},
		/**
		 * 提供者，访问WINDOWS_ROOT时使用
		 */
		PROVIDER_MSC{
			@Override
			public String toString() {
				return "SunMSCAPI";
			}
		},
		/**
		 * 默认sun提供者
		 */
		PROVIDER_JSSE{
			@Override
			public String toString() {
				return "SunJSSE";
			}
		}
	}
	
	/**
	 * 从p12文件中构建KeyStoreManager
	 * @param file
	 * @param password
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static KeyStoreManager buildForP12(File file,String password) throws CertificateException, IOException{
		FileInputStream is=null;
		try{
			is=new FileInputStream(file);
			return buildForP12(is,password);
		}finally{
			if(is!=null){
				is.close();
			}
		}
	}
	/**
	 * 从p12文件中构建KeyStoreManager
	 * @param stream
	 * @param password
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static KeyStoreManager buildForP12(InputStream stream,String password) throws CertificateException, IOException{
		return buildFileKS(KeyStoreType.PKCS12, KeyStoreProvider.PROVIDER_JSSE, stream, password);
	}
	/**
	 * 从文件获取jks
	 * @param file
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws CertificateException
	 */
	public static KeyStoreManager buildForJCE(File file,String password) throws IOException, CertificateException{
		FileInputStream is=null;
		try{
			is=new FileInputStream(file);
			return buildForJCE(is,password);
		}finally{
			if(is!=null){
				is.close();
			}
		}
	}
	/**
	 * 获取sun提供的keyStore
	 * @param stream
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws CertificateException
	 */
	public static KeyStoreManager buildForJCE(InputStream stream,String password) throws IOException, CertificateException{
		return buildFileKS(KeyStoreType.JCEKS,KeyStoreProvider.PROVIDER_SUNJCE,
				stream, password);
	}
	/**
	 * 获取windows证书库
	 * @param type 取值WINDOWS_ROOT或WINDOWS_ME
	 * @return
	 * @throws Exception
	 */
	public static KeyStoreManager buildForMSC(KeyStoreType microsoftType) throws Exception{
		KeyStoreManager ksm=new KeyStoreManager();
		ksm.keystore=KeyStore.getInstance(microsoftType.toString(),KeyStoreProvider.PROVIDER_MSC.toString());
		ksm.keystore.load(null, null);
		Enumeration<String> aliases=ksm.keystore.aliases();
		if(aliases.hasMoreElements()){
			ksm.firstAlias=aliases.nextElement();
		}
		return ksm;
	}
	
	
	private static KeyStoreManager buildFileKS(KeyStoreType type,KeyStoreProvider provider,InputStream stream,String password)throws IOException, CertificateException{
		KeyStoreManager ks=new KeyStoreManager();
		try {
			ks.keystore=KeyStore.getInstance(type.toString(),provider.toString());
			ks.keystore.load(stream, password.toCharArray());
			Enumeration<String> aliases=ks.keystore.aliases();
			if(aliases.hasMoreElements()){
				ks.firstAlias=aliases.nextElement();
			}
			return ks;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}
	private KeyStoreManager(){
		
	}
	/**
	 * 获取keystoe中的所有别名
	 * @return
	 */
	public Enumeration<String> getAlias(){
		try {
			return keystore.aliases();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过域名获取证书
	 * @param alias 别名，设置null默认采用第一个,如果没有返回null
	 * @return
	 */
	public Certificate getCertificate(String alias){
		String a=alias==null?firstAlias:alias;
		try {
			return keystore.getCertificate(a);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 设置信任证书
	 * @param cert
	 */
	public void setTrustCertificate(String alias,Certificate cert){
		KeyStore.TrustedCertificateEntry trust=new KeyStore.TrustedCertificateEntry(cert);
		try {
			keystore.setEntry(alias,trust,null);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 存储改keystore
	 */
	public void store(OutputStream stream,String password){
		try {
			keystore.store(stream, password.toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取指定别名的私钥
	 * @param alias 别名，设置null默认采用第一个,如果没有返回null
	 * @param password
	 * @return
	 */
	public PrivateKey getPrivateKey(String alias,String password){
		String a=alias==null?firstAlias:alias;
		try {
			return (PrivateKey)keystore.getKey(a, password.toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定别名的公钥
	 * @param alias 别名，设置null默认采用第一个,如果没有返回null
	 * @return
	 */
	public PublicKey getPublicKey(String alias){
		try{
			Certificate c=getCertificate(alias);
			return c.getPublicKey();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
} 
