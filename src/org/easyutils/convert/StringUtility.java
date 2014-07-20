package org.easyutils.convert;


public class StringUtility {

	/**
	 * 
	 * @param str
	 *            传入的阿拉伯数字的字符串,但是只能做五位数以内的处理
	 * @return 返回转换成汉语的数字序列
	 */
	public String convertToChar(String str) {
		String nix = "  十百千万";
		// 这里故意留出两个空位用来匹配方便
		int len = str.length();
		String result = "";
		for (int i = 0; i < len; i++) {
			// 对于零的处理
			if (str.charAt(i) == '0') {
				continue;
			}
			result += convert(String.valueOf(str.charAt(i))).concat(
					String.valueOf(nix.charAt(len - i)));
		}
		// 对于一十几情况处理,包括一十万、一十等
		if (result.startsWith("一十")) {
			return result.substring(1);
		}
		return result;
	}

	/**
	 * 
	 * @param str传入的汉语的数字序列
	 *            ,但是只能做五位数以内的处理
	 * @return 阿拉伯数字的字符串
	 */
	public String convertToNum(String str) {
		String nix = " 十百千万";
		// 空出一个空位，这样使得10的索引的指数的值和单位的值相等
		int result = 0;

		// 对于一十几情况处理,包括一十万、一十等
		if (str.startsWith("十")) {
			if (str.length() == 1)
				return "10";
			return String.valueOf(10 + Integer.parseInt(convert(str
					.substring(1))));
		}
		for (int i = 0; i < str.length(); i++) {
			// 首先得到传入的字符串中每两个字符串的前一个转为整型
			int reg = Integer.parseInt(convert(String.valueOf(str.charAt(i))));
			if (1 + i >= str.length())
				result += reg;
			else {
				// 然后将后面的单位去nix匹配的到索引然后取10的指数相乘得到单位的倍数然后和前一个字符相乘
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
	 *            传入一个汉字或者数字的字符串类型
	 * @return 返回一个与之相对应的汉字或者是数字的字符串类型
	 */
	public String convert(String str) {
		String chr = "零一二三四五六七八九";
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
