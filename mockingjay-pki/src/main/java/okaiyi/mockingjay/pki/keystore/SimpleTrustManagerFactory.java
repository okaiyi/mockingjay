package okaiyi.mockingjay.pki.keystore;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;

public abstract class SimpleTrustManagerFactory extends TrustManagerFactory{

	protected SimpleTrustManagerFactory(TrustManagerFactorySpi factorySpi,
			Provider provider, String algorithm) {
		super(factorySpi, provider, algorithm);
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		TrustManagerFactory factory=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		//System.out.println(TrustManagerFactory.getDefaultAlgorithm());
	}
	
}
