package org.easyutils.yeepay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class YeepayUtility {
	private static String encodingCharset = "UTF-8";

	/**
	 * @param aValue
	 *            String ����Ҫ������ַ���
	 * @param aKey
	 *            String ����Ҫ�����yeepay�ṩ��key
	 * @return String ���ر���õ��ַ���
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}

	/**
	 * ����hmac����
	 * 
	 * @param p0_Cmd
	 *            ҵ������
	 * @param p1_MerId
	 *            �̻����
	 * @param p2_Order
	 *            �̻�������
	 * @param p3_Amt
	 *            ֧�����
	 * @param p4_Cur
	 *            ���ױ���
	 * @param p5_Pid
	 *            ��Ʒ����
	 * @param p6_Pcat
	 *            ��Ʒ����
	 * @param p7_Pdesc
	 *            ��Ʒ����
	 * @param p8_Url
	 *            �̻�����֧���ɹ����ݵĵ�ַ
	 * @param p9_SAF
	 *            �ͻ���ַ
	 * @param pa_MP
	 *            �̻���չ��Ϣ
	 * @param pd_FrpId
	 *            ���б���
	 * @param pr_NeedResponse
	 *            Ӧ�����
	 * @param keyValue
	 *            �̻���Կ
	 * @return
	 */
	public static String buildHmac(String p0_Cmd, String p1_MerId,
			String p2_Order, String p3_Amt, String p4_Cur, String p5_Pid,
			String p6_Pcat, String p7_Pdesc, String p8_Url, String p9_SAF,
			String pa_MP, String pd_FrpId, String pr_NeedResponse,
			String keyValue) {
		StringBuffer sValue = new StringBuffer();
		// ҵ������
		sValue.append(p0_Cmd);
		// �̻����
		sValue.append(p1_MerId);
		// �̻�������
		sValue.append(p2_Order);
		// ֧�����
		sValue.append(p3_Amt);
		// ���ױ���
		sValue.append(p4_Cur);
		// ��Ʒ����
		sValue.append(p5_Pid);
		// ��Ʒ����
		sValue.append(p6_Pcat);
		// ��Ʒ����
		sValue.append(p7_Pdesc);
		// �̻�����֧���ɹ����ݵĵ�ַ
		sValue.append(p8_Url);
		// �ͻ���ַ
		sValue.append(p9_SAF);
		// �̻���չ��Ϣ
		sValue.append(pa_MP);
		// ���б���
		sValue.append(pd_FrpId);
		// Ӧ�����
		sValue.append(pr_NeedResponse);

		String sNewString = hmacSign(sValue.toString(), keyValue);
		return sNewString;
	}

	/**
	 * ����У��hmac����
	 * 
	 * @param hmac
	 *            ֧�����ط����ļ�����֤��
	 * @param p1_MerId
	 *            �̻����
	 * @param r0_Cmd
	 *            ҵ������
	 * @param r1_Code
	 *            ֧�����
	 * @param r2_TrxId
	 *            �ױ�֧��������ˮ��
	 * @param r3_Amt
	 *            ֧�����
	 * @param r4_Cur
	 *            ���ױ���
	 * @param r5_Pid
	 *            ��Ʒ����
	 * @param r6_Order
	 *            �̻�������
	 * @param r7_Uid
	 *            �ױ�֧����ԱID
	 * @param r8_MP
	 *            �̻���չ��Ϣ
	 * @param r9_BType
	 *            ���׽����������
	 * @param keyValue
	 *            ��Կ
	 * @return
	 */
	public static Map<String, String> verifyCallback(String hmac,
			String p1_MerId, String r0_Cmd, String r1_Code, String r2_TrxId,
			String r3_Amt, String r4_Cur, String r5_Pid, String r6_Order,
			String r7_Uid, String r8_MP, String r9_BType, String keyValue) {
		StringBuffer sValue = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		// �̻����
		sValue.append(p1_MerId);
		// ҵ������
		sValue.append(r0_Cmd);
		// ֧�����
		sValue.append(r1_Code);
		// �ױ�֧��������ˮ��
		sValue.append(r2_TrxId);
		// ֧�����
		sValue.append(r3_Amt);
		// ���ױ���
		sValue.append(r4_Cur);
		// ��Ʒ����
		sValue.append(r5_Pid);
		// �̻�������
		sValue.append(r6_Order);
		// �ױ�֧����ԱID
		sValue.append(r7_Uid);
		// �̻���չ��Ϣ
		sValue.append(r8_MP);
		// ���׽����������
		sValue.append(r9_BType);
		String sNewString = hmacSign(sValue.toString(), keyValue);

		if (hmac.equals(sNewString)) {
			map.put("p1_MerId", p1_MerId);
			map.put("r0_Cmd", r0_Cmd);
			map.put("r1_Code", r1_Code);
			map.put("r2_TrxId", r2_TrxId);
			map.put("r3_Amt", r3_Amt);
			map.put("r4_Cur", r4_Cur);
			map.put("r5_Pid", r5_Pid);
			map.put("r6_Order", r6_Order);
			map.put("r7_Uid", r7_Uid);
			map.put("r8_MP", r8_MP);
			map.put("r9_BType", r9_BType);
			return map;
		}
		return null;
	}
}
