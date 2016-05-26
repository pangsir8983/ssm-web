package com.pangsir.helper.string;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 简单封装了一些操作,但是个人建议使用第三方类库
 * <p>lang3或者google</p>
 * @author 刘文铭
 * @see com.pangsir.helper.string.StringHelper
 * @since 1.0
 */
public class StringHelper {

    private StringHelper(){}



    public static StringHelper getInstancle() {
        return new StringHelper();
    }

    private static boolean isMatch(String regx, String content) {
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(content);

        return m.find();
    }

    public static String getSystime() {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dft.format(new Date());
    }

    public static String getSystime(String format) {
        DateFormat dft = new SimpleDateFormat(format);
        return dft.format(new Date());
    }

    public int compare(Object o1, Object o2) {
        String a = (String) o1;
        String b = (String) o2;

        if ((!isDigit(a)) || (!isDigit(b))) {
            throw new IllegalArgumentException("the object must a digit");
        }
        long aa = Long.valueOf(a).longValue();
        long bb = Long.valueOf(b).longValue();

        if (aa > bb)
            return 1;
        if (aa < bb) {
            return -1;
        }
        return 0;
    }

    public static String alterSpace(String content, String character) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            String c = new String(new char[]{content.charAt(i)});
            if (c.trim().length() == 0) {
                sb.append(character);
            } else
                sb.append(c);
        }
        return sb.toString();
    }

    public static boolean isEmail(String line, int length) {
        return (line.matches("\\w+[\\w.]*@[\\w.]+\\.\\w+$")) &&
                (line.length() <= length);
    }

    public static boolean isChineseName(String value, int length) {
        return (value.matches("^[一-龥]+$")) && (value.length() <= length);
    }

    public static boolean isHaveHtmlTag(String value) {
        return value.matches("<(\\S*?)[^>]*>.*?</\\1>|<.*? />");
    }

    public static boolean isURL(String value) {
        return value.matches("[a-zA-z]+://[^\\s]*");
    }

    public static boolean iskIP(String value) {
        return value.matches("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
    }

    public static boolean isQQ(String value) {
        return value.matches("[1-9][0-9]{4,13}");
    }

    public static boolean isPostCode(String value) {
        return value.matches("[1-9]\\d{5}(?!\\d)");
    }
    //简单验证
    public static boolean isIDCard(String value) {
        return value.matches("(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?");
    }
    private static String mobile_regx = "^[1][3578][0-9]{9}$";
    public static boolean isPhone(String line) {
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile(mobile_regx);
        m = p.matcher(line);

        return m.matches();
    }

    public static String fixPhoneno(String phoneno) {
        if ((phoneno.length() > 11) && (phoneno.startsWith("86")))
            return phoneno.substring(2);
        if ((phoneno.length() > 11) && (phoneno.startsWith("+86")))
            return phoneno.substring(3);
        return phoneno;
    }

    public static boolean hasPhone(String line) {
        return isMatch("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$", line);
    }

    public static String getPhone(String line) {
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile("1[3,5][4,5,6,7,8,9]\\d{8}|15[8,9]\\d{8}");

        for (int i = 0; i < line.length(); i++) {
            m = p.matcher(line);
            if (m.find()) {
                String str = line.substring(m.start(), m.end());
                return str;
            }
        }

        return "";
    }

    public static Set getTextBlock(String text, String compile) {
        Set set = new HashSet();
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile(compile);
        m = p.matcher(text);
        while (m.find()) {
            String str = text.substring(m.start(), m.end());
            set.add(str);
        }
        return set;
    }

    public static Set getCode(String strMail) {
        Set set = new HashSet();
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile("1[3,5][4,5,6,7,8,9]\\d{8}|15[8,9]\\d{8}");
        m = p.matcher(strMail);
        while (m.find()) {
            String str = strMail.substring(m.start(), m.end());
            set.add(str);
        }
        return set;
    }

    public static Set getMail(String content) {
        Set set = new HashSet();
        Pattern p = null;
        Matcher m = null;
        p =
                Pattern.compile("(?i)(?<=\\b)[a-z0-9][-a-z0-9_.]+[a-z0-9]@([a-z0-9][-a-z0-9]+\\.)+[a-z]{2,4}(?=\\b)");
        m = p.matcher(content);
        while (m.find()) {
            String str = content.substring(m.start(), m.end());
            set.add(str);
        }
        return set;
    }

    public static int getRandom(int min, int max) {
        return (int) (min + (max - min) * Math.random());
    }

    public static int getRandom(int length) {
        return Integer.valueOf(getRand(length)).intValue();
    }

    public static long getRandomL(int length) {
        return Long.valueOf(getRand(length)).longValue();
    }

    public static String getRandomStr(int length) {
        return Long.toString(getRandom(length));
    }

    private static String getRand(int length) {
        StringBuffer t = new StringBuffer();
        for (int j = 0; j < length; j++) {
            double d = Math.random() * 10.0D;
            int c = (int) d;
            t.append(c);
        }
        String result = t.toString();
        if (result.substring(0, 1).equalsIgnoreCase("0")) {
            result.replaceAll("0", "1");
        }

        if (result.length() > length)
            result = result.substring(0, length);
        else if (result.length() < length) {
            result = result + getRand(length - result.length());
        }

        return result;
    }

    public static boolean isNull(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (isNull(str[i]))
                return true;
        }
        return false;
    }

    public static boolean isNull(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    public static boolean isDigit(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (!isDigit(str[i]))
                return false;
        }
        return true;
    }

    public static boolean isDigit(String str) {
        if (isNull(str))
            throw new NullPointerException();
        int i = 0;
        for (int size = str.length(); i < size; i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static String getStackInfo(Throwable e) {
        StringBuffer info = new StringBuffer("Found Exception: ");

        info.append("\n");
        info.append(e.getClass().getName());
        info.append(" : ").append(e.getMessage() == null ? "" : e.getMessage());
        StackTraceElement[] st = e.getStackTrace();
        for (int i = 0; i < st.length; i++) {
            info.append("\t\n").append("at ");
            info.append(st[i].toString());
        }
        return info.toString();
    }

    private static String insteadCode(String str, String regEx, String code) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String s = m.replaceAll(code);
        return s;
    }

    public static String toHtml(String sourceStr) {
        String targetStr = insteadCode(sourceStr, ">", "&gt;");
        targetStr = insteadCode(targetStr, "<", "&lt;");
        targetStr = insteadCode(targetStr, "\n", "<br>");
        targetStr = insteadCode(targetStr, " ", "&nbsp;");
        return targetStr.trim();
    }

    public static String sendGetParameter(String parameter) {
        parameter = insteadCode(parameter, "&", "%26");
        parameter = insteadCode(parameter, " ", "%20");
        parameter = insteadCode(parameter, "%", "%25");
        parameter = insteadCode(parameter, "#", "%23");

        return parameter.trim();
    }

    public static String spiltStr(String content, String start, String end) {
        if ((content.indexOf(start) <= -1) || (content.indexOf(end) <= -1)) {
            throw new IndexOutOfBoundsException(
                    "[start Character or end Character,isn't exist in the specified content]");
        }
        int s = content.indexOf(start);

        int e = start.equals(end) ? content.substring(s + 1).indexOf(end) :
                content.indexOf(end);

        if (s >= e) {
            throw new IndexOutOfBoundsException(
                    "[the Character end is smallness Character start]");
        }
        content = new String(content.substring(s + 1, e));

        return content.trim();
    }

    public static String[] splitStr(String content, String split) {
        int s = 0;
        int e = content.indexOf(split);

        List list = new ArrayList();

        while (e <= content.length()) {
            if ((content.indexOf(split) == -1) && (list.size() != 0)) {
                list.add(content);
                break;
            }
            list.add(content.substring(s, e));
            content = content.substring(e + 1, content.length());
            e = s + content.indexOf(split);
        }
        return (String[]) list.toArray(new String[0]);
    }

    public static String splitStr(String str, int num, String end) {
        StringBuffer sb = new StringBuffer();
        if ((str == null) || (end == null))
            throw new NullPointerException();
        if (str.length() > num)
            str = str.substring(0, num) + end;
        return toHtml(str);
    }

    public static String completeText(String content, int count) {
        StringBuffer sb = new StringBuffer();
        if (count > content.length()) {
            for (int i = count - content.length(); (content.length() < count) &&
                    (i != 0); ) {
                sb.append("0");

                i--;
            }
        }
        sb.append(content);
        return sb.toString();
    }

    public static String completeText(int content, int count) {
        String c = Integer.toString(content);
        StringBuffer sb = new StringBuffer();
        if (count > c.length()) {
            for (int i = count - c.length(); (c.length() < count) && (i != 0); i--)
                sb.append("0");
        }
        sb.append(content);
        return sb.toString();
    }

    public static String completeTextSpace(String content, int count) {
        StringBuffer sb = new StringBuffer();

        sb.append(content);
        if (count > content.length()) {
            for (int i = 0; i < count - content.length(); i++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String completeTextSpace(int content, int count) {
        StringBuffer sb = new StringBuffer();
        String c = Integer.toString(content);
        sb.append(content);
        if (count > c.length()) {
            for (int i = 0; i < count - c.length(); i++)
                sb.append(" ");
        }
        return sb.toString();
    }

    public static String toHex(String content) {
        long i = new Long(content).longValue();
        String i_16 = Long.toHexString(i);
        return i_16;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) ||
                (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) ||
                (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) ||
                (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) ||
                (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) ||
                (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
    }

    public static String getBASE64(byte[] b) {
        String s = null;
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public static byte[] getFromBASE64(String s) {
        byte[] b = (byte[]) null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                return b;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static String getUrlEncode(String content)
            throws UnsupportedEncodingException {
        char[] a = content.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if (isChinese(a[i]))
                sb.append(URLEncoder.encode(String.valueOf(a[i]), "GBK"));
            else {
                sb.append(a[i]);
            }
        }

        return sb.toString();
    }

    public static String getUrlEncode(String content, String encode)
            throws UnsupportedEncodingException {
        char[] a = content.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if (isChinese(a[i]))
                sb.append(URLEncoder.encode(String.valueOf(a[i]), encode));
            else {
                sb.append(a[i]);
            }
        }

        return sb.toString();
    }

    public static String getUrlDecode(String content)
            throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(URLDecoder.decode(content, "GBK"));
        return sb.toString();
    }

    public static String getUrlDecode(String content, String encode)
            throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(URLDecoder.decode(content, encode));
        return sb.toString();
    }

    public static String toStringHex(String bytes) {
        String hexString = "0123456789ABCDEF ";
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);

        for (int i = 0; i < bytes.length(); i += 2)
            baos.write(hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1)));
        return new String(baos.toByteArray());
    }

    public static String toHexString(String str) {
        String hexString = "0123456789ABCDEF ";

        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xF0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0xF) >> 0));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(hasPhone("18911968624"));
        System.out.println(toHex("13811968624"));
        System.out.println(fixPhoneno("8613811968624"));
        System.out.println(fixPhoneno("13811968624"));
        System.out.println(sendGetParameter("&"));

        System.out.println();
        System.out
                .println(sendGetParameter("/send/100039.htm?cpid=1&adid=3&adverid=1"));
        System.out
                .println(getMail("<html><a href=\"testone@163.com\">163test</a>\n<a href='www.163.com@163-com.com'>163news</a>\n"));
        System.out.println(getSystime());
    }
}
