package okaiyi.mockingjay.commons;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import okaiyi.mockingjay.commons.email.EmailMessage;
import okaiyi.mockingjay.commons.email.EmailSender;
import okaiyi.mockingjay.commons.email.EmailSenderHandler;
import okaiyi.mockingjay.commons.email.EmailSession;

public class EmailTest {
	public static void main(String[] args) throws Exception {
		new EmailTest().testSender();
	}
	
	@Test
	public void testSender() throws Exception{
		byte[] content=Files.readAllBytes(Paths.get("/Users/kaiyi/微信.txt"));
		String sendContent=new String(content);
		System.out.println(sendContent);
		EmailSession session=new EmailSession("smtp.163.com", EmailSession.SMTP_DEFAULT_PORT,
				"kingdengkai@163.com", "baggio.com", "kingdengkai@163.com", "有点奇葩");
		EmailSender sender=new EmailSender(session);
		EmailMessage msg=new EmailMessage(new String[]{
				"32713195@qq.com"
		}, "配置信息",sendContent);
		sender.setMessage(msg);
		sender.send(new EmailSenderHandler() {
			
			@Override
			public void sendError(Exception e) {
				e.printStackTrace();
			}
			
			@Override
			public void handler(EmailMessage message, long time) {
				System.out.println("time:"+time);
			}
		});
	}
}
