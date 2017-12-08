package com.yy.sagit.util;


import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式化工具类
 *
 * @author song
 */
public class FormatUtil {

    /**
     * 格式化日期
     */
    public static String formatTime(long timeMillis) {
        Date d = new Date(timeMillis);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(d);
    }


    /**
     * 格式化价钱
     */
    public static String formatPrice(long price) {
        // DecimalFormat df = new DecimalFormat();
        // df.applyPattern("0.00#");
        // return "¥ " + df.format(price / 100.0);
        return "¥ " + (price / 100f);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 金额格式
     */
    public static String isTip(String tip) {
		String telRegex = "^[0-9]+\\.{0,1}[0-9]{0,2}$";//整数位有0也能行
//        String telRegex = "^([1-9][0-9]*)+(.[0-9]{1,2})?$";//整数位有0就不行
        if (TextUtils.isEmpty(tip)) {
            return false + "";
        } else {
//            if (tip.contains(".")) {//不用判断是否包含
//                result = tip.split("\\.")[0];
//
//            } else {
//                result = tip;
//            }
            String result = tip.split("\\.")[0];
            String[] ss=result.split("");
            if (ss.length==1){
                return tip.matches(telRegex) + "";
            }else {
//                MyLog.e(ss[0]);
//                MyLog.e(String.valueOf(result.charAt(0)));
                return String.valueOf(result.charAt(0)).equals("0") ? false + "" : tip.matches(telRegex) + "";
            }
        }
    }

    public static boolean isNumber1(String str) {// 判断整型
        return str.matches("^\\d+$$");
    }

    public static boolean isNumber2(String str) {// 判断小数，与判断整型的区别在与d后面的小数点（红色）
        return str.matches("\\d+\\.\\d+$");
    }

    /**
     * 判断是否是邮箱
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        // String strPattern =
        // "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 判断密码(数字加字母)
     */
    // public static boolean isPassword(String pass) {
    // // String strPattern = ".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]";
    // String strPattern = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,12})$";
    // Pattern p = Pattern.compile(strPattern);
    // Matcher m = p.matcher(pass);
    // return m.matches();
    // }

    /**
     * 判断昵称
     */
    public static boolean isNick(String nick) {
        String reg = "^[a-zA-Z0-9\u4E00-\u9FA5_-]*$";
        if (TextUtils.isEmpty(nick))
            return false;
        else
            return nick.matches(reg);
    }

    /**
     * 检测是否有emoji表情
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;

    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 去除emoji表情
     */
    public static String deleteEmoji(String source) {
        StringBuffer str = new StringBuffer(source);
        for (int i = 0; i < source.length(); i++) {
            char codePoint = source.charAt(i);
            String substring = source.substring(i, i + 1);
            if (!isEmojiCharacter(codePoint) || "☺".equals(substring)) {
                // 如果不能匹配,则该字符是Emoji表情
                StringBuffer deleteCharAt = str.deleteCharAt(i);
                return deleteEmoji(deleteCharAt.toString());
            }
        }
        return source;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 完整的判断中文汉字和符号
     *
     * @param strName
     * @return
     */

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断密码(数字)
     */
    public static boolean isPass(String pass) {
        boolean flag1 = true;
        char str = pass.charAt(0);
        for (int i = 0; i < pass.length(); i++) {
            if (str != pass.charAt(i)) {
                flag1 = false;
                break;
            }
        }
        boolean flag2 = true;
        for (int i = 0; i < pass.length(); i++) {
            if (i > 0) {// 判断如123456
                int num = Integer.parseInt(pass.charAt(i) + "");
                int num_ = Integer.parseInt(pass.charAt(i - 1) + "") + 1;
                if (num != num_) {
                    flag2 = false;
                    break;
                }
            }
        }
        boolean flag3 = true;
        for (int i = 0; i < pass.length(); i++) {
            if (i > 0) {// 判断如654321
                int num = Integer.parseInt(pass.charAt(i) + "");
                int num_ = Integer.parseInt(pass.charAt(i - 1) + "") - 1;
                if (num != num_) {
                    flag3 = false;
                    break;
                }
            }
        }
        if (flag1 == false && flag2 == false && flag3 == false) {
            return true;
        }

        return false;
    }

}
