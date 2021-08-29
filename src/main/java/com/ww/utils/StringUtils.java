package com.ww.utils;

public class StringUtils {
    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1))
                    .toString();
        }
    }

    /**
     * 将字符串的首字母转大写
     * @param s
     * @return
     */
    private static String toUpperFirstCharacter(String s) {
        // 利用ascii编码的前移，效率要高于截取字符串进行转换的操作
        char[] cs = s.toCharArray();
        if (Character.isLowerCase(cs[0])) {
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return s;
    }
    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1))
                    .toString();
        }
    }
    /**
     * 将字符串的首字母转小写
     * @param s
     * @return
     */
    private static String toLowerFirstCharacter(String s) {
        char[] cs = s.toCharArray();
        if (Character.isUpperCase(cs[0])) {
            cs[0] += 32;
            return String.valueOf(cs);
        }
        return s;
    }

}
