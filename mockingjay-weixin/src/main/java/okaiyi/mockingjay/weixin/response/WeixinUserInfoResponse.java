package okaiyi.mockingjay.weixin.response;
/**
 * 获取微信用户信息
 *
 */
public class WeixinUserInfoResponse extends WeixinResponse{
	public static enum SEX{
		MAN{
			@Override
			public String toString() {
				return "男性";
			}
		},
		WOMAN{
			@Override
			public String toString() {
				return "女性";
			}
		},
		UNKOWN{
			@Override
			public String toString() {
				return "未知";
			}
		}
	}
	public WeixinUserInfoResponse(String msg) {
		super(msg);
	}
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 * @return
	 */
	public String unionId(){
		return getDataTypeToString("unionid");
	}
	/**
	 * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	 * @return
	 */
	public String getPrivilege(){
		return getDataTypeToString("privilege");
	}
	/**
	 * 用户头像，最后一个数值代表正方形头像大小
	 * （有0、46、64、96、132数值可选，0代表640*640正方形头像），
	 * 用户没有头像时该项为空。
	 * 若用户更换头像，原有头像URL将失效。
	 * @return http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ
4eMsv84eavHiaiceqxibJxCfHe/46
	 */
	public String getHeadImgUrl(){
		return getDataTypeToString("headimgurl");
	}
	/**
	 * 国家，如中国为CN
	 * @param msg
	 */
	public String getCountry(){
		return getDataTypeToString("country");
	}
	/**
	 * 普通用户个人资料填写的城市
	 * @return
	 */
	public String getCity(){
		return getDataTypeToString("city");
	}
	/**
	 * 用户个人资料填写的省份
	 * @return
	 */
	public String getProvince(){
		return getDataTypeToString("province");
	}
	/**
	 * 获取用户性别
	 * @return
	 */
	public SEX getSex(){
		String sex=getDataTypeToString("sex");
		if(sex.equals("1")){
			return SEX.MAN;
		}else if(sex.endsWith("2")){
			return SEX.WOMAN;
		}else{
			return SEX.UNKOWN;
		}
	}
	/**
	 * 用户的唯一标识
	 * @return
	 */
	public String getOpenId(){
		return getDataTypeToString("openid");
	}
	/**
	 * 用户昵称
	 * @return
	 */
	public String getNickName(){
		return getDataTypeToString("nickname");
	}
}
