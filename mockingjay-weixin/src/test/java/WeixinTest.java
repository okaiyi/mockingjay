import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;


public class WeixinTest {
	@Test
	public void sign(){
		String str=DigestUtils.sha1Hex("jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value");
		System.out.println(str);
	}
}
