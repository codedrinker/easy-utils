package org.easyutils.convert;


public class StringUtility {

	/**
	 * 
	 * @param str
	 *            ����İ��������ֵ��ַ���,����ֻ������λ�����ڵĴ���
	 * @return ����ת���ɺ������������
	 */
	public String convertToChar(String str) {
		String nix = "  ʮ��ǧ��";
		// �����������������λ����ƥ�䷽��
		int len = str.length();
		String result = "";
		for (int i = 0; i < len; i++) {
			// ������Ĵ���
			if (str.charAt(i) == '0') {
				continue;
			}
			result += convert(String.valueOf(str.charAt(i))).concat(
					String.valueOf(nix.charAt(len - i)));
		}
		// ����һʮ���������,����һʮ��һʮ��
		if (result.startsWith("һʮ")) {
			return result.substring(1);
		}
		return result;
	}

	/**
	 * 
	 * @param str����ĺ������������
	 *            ,����ֻ������λ�����ڵĴ���
	 * @return ���������ֵ��ַ���
	 */
	public String convertToNum(String str) {
		String nix = " ʮ��ǧ��";
		// �ճ�һ����λ������ʹ��10��������ָ����ֵ�͵�λ��ֵ���
		int result = 0;

		// ����һʮ���������,����һʮ��һʮ��
		if (str.startsWith("ʮ")) {
			if (str.length() == 1)
				return "10";
			return String.valueOf(10 + Integer.parseInt(convert(str
					.substring(1))));
		}
		for (int i = 0; i < str.length(); i++) {
			// ���ȵõ�������ַ�����ÿ�����ַ�����ǰһ��תΪ����
			int reg = Integer.parseInt(convert(String.valueOf(str.charAt(i))));
			if (1 + i >= str.length())
				result += reg;
			else {
				// Ȼ�󽫺���ĵ�λȥnixƥ��ĵ�����Ȼ��ȡ10��ָ����˵õ���λ�ı���Ȼ���ǰһ���ַ����
				result += reg
						* (int) Math.pow(10, nix.indexOf(str.charAt(i + 1)));
				i++;
			}
		}
		return String.valueOf(result);
	}

	/**
	 * 
	 * @param str
	 *            ����һ�����ֻ������ֵ��ַ�������
	 * @return ����һ����֮���Ӧ�ĺ��ֻ��������ֵ��ַ�������
	 */
	public String convert(String str) {
		String chr = "��һ�����������߰˾�";
		String num = "0123456789";
		for (int i = 0; i < str.length(); i++) {
			if (num.indexOf(str.charAt(i)) != -1) {
				return String.valueOf(chr.charAt(num.indexOf(str.charAt(i))));
			}
			if (chr.indexOf(str.charAt(i)) != -1) {
				return String.valueOf(num.charAt(chr.indexOf(str.charAt(i))));
			}
		}
		return null;
	}
}
