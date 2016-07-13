package lomasky.ma.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author wangli
 */
public class StringUtil {

    /**
     * 判断是否为空 为空返回true
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("");

    }

    /**
     * 判断是否为空 为空返回true
     *
     * @param str
     * @return
     */
    public static boolean noEmpty(String str) {
        return str != null && str.trim().length() > 0;

    }

    /**
     * @param str
     * @return
     */
    public static String returnShow(String str) {
        if (str != null && !str.equals("null"))
            return str;
        else
            return "";
    }

    /**
     * @param object
     * @return
     */
    public static String valueOf(Object object) {
        if (object != null) {
            return String.valueOf(object)
                    ;
        } else {
            return "";
        }

    }

    /**
     * 检验手机号码或者固定座机号是否合法
     *
     * @param phoneNumber 号码
     * @return
     */
    public  static  String telReg = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
            + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
            + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
            + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
    public static boolean checkPhoneNum(String phoneNumber) {
        boolean isValid = false;
//        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
//                + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
//                + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
//                + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(telReg);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    /**
     * 使用正则表达式检查邮箱地址格式
     *
     * @param email
     * @return
     */
    public static boolean checkEmailAddress(String email) {
        boolean isValid = false;
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            isValid = true;
        }

        return isValid;
    }

    /**
     * 检查是否输入了特殊字符
     *
     * @param s 输入字符
     * @return true 有特殊字符 false 没有
     */
    public static boolean checkStringSpecial(String s) {
        String regEx = "[`~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.find();
    }

    /**
     * @param str 检测的价格
     * @return 是否合格的价格
     */
    public static boolean checkPrice(String str) {
        String string = "/^(0|[1-9]/d*)$|^(0|[1-9]/d*)/.(/d+)$/";

        Pattern p = Pattern.compile(string);
        Matcher m = p.matcher(str);
        return m.matches();

    }

    public static boolean checkallnumber(String number) {
        String string = "^[0-9]*$";
        Pattern p = Pattern.compile(string);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 取出字符串里面的空格 特殊符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            // 替换
            dest = str.replaceAll("&nbsp;", " ");
            // 删除
            // Pattern p = Pattern.compile("&nbsp;");
            // Matcher m = p.matcher(str);
            // dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 通过多个以某个String间隔的多个String组成的String解析出String的list
     *
     * @param str         原始String
     * @param strInterval 间隔的字符串
     * @return
     */
    public static List<String> getStringListByString(String str,
                                                     String strInterval) {
        List<String> listUrls = new ArrayList<String>();
        if (noEmpty(str)) {
            try {

                while (str.indexOf(strInterval) != -1) {
                    listUrls.add(str.substring(0, str.indexOf(strInterval)));
                    str = str.substring(
                            str.indexOf(strInterval) + strInterval.length(),
                            str.length());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<String>();
            }

            listUrls.add(str);// 加入最末尾的一个url
        }


        return listUrls;
    }

    /**
     * 通过多个以某个String间隔的多个String组成的String解析出String的list
     *
     * @param str         原始String
     * @param strInterval 间隔的字符串
     * @return
     */
    public static ArrayList<String> getArrayListStringByString(String str, String strInterval) {
        ArrayList<String> listUrls = new ArrayList<String>();

        try {

            while (str.indexOf(strInterval) != -1) {
                listUrls.add(str.substring(0, str.indexOf(strInterval)));
                str = str.substring(
                        str.indexOf(strInterval) + strInterval.length(),
                        str.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }

        listUrls.add(str);// 加入最末尾的一个url

        return listUrls;
    }

    /**
     * 使用StringTokenizer类将字符串按分隔符转换成字符数组
     */
    public static String[] stringAnalytical(String string, String divisionChar) {
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);

        String[] str = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreTokens()) {
            str[i] = new String();
            str[i] = tokenizer.nextToken();
            i++;
        }

        return str;
    }

    /**
     * 过滤字符串中的html代码
     *
     * @param inputString
     * @return
     */
    public static String filtHtml(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        Pattern p_html1;
        Matcher m_html1;

        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            // }
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    /**
     * @param input
     * @return
     * @MethodName: replaceLine
     * @Description: 去除换行符
     */
    public static String replaceLine(String input) {
        String str = input.replaceAll("\r", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll(" ", "");
        return str;
    }

    /**
     * @param input
     * @return 将数字转化为全角
     */
    public static String ToDBC(String input) {
        return input.replaceAll("1", "１").replaceAll("2", "２")
                .replaceAll("3", "３").replaceAll("4", "４").replaceAll("5", "５")
                .replaceAll("6", "６").replaceAll("7", "７").replaceAll("8", "８")
                .replaceAll("9", "９").replaceAll("0", "０");
    }

    /**
     * @return 随机生成不重复的字符串或者数字
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //判断IP
    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

    //判断http
    public static boolean isHttp(String addr) {

        String rexp = "(\"http://(([a-zA-z0-9]|-){1,}\\\\.){1,}[a-zA-z0-9]{1,}-*\")";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        return mat.find();
    }


    public static String MD5(String val) {
        String rst = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] byteArray = md5.digest();// 加密
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));

            }
            rst = md5StrBuff.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        return rst;
    }
    //SHA1 加密实例
    public static String toSHA(String string) {
        byte[] digesta = null;
        try {
        // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            // 添加要进行计算摘要的信息
            alga.update(string.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs ;
    }
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static String getPrice(String price){
        try {
            if(price!=null&&!price.equals("")){
                BigDecimal rateDecimal = new BigDecimal(Double.valueOf(price));
                BigDecimal  num=rateDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
                return String.valueOf(num);
            }else {
                return  "0.00";
            }
        }catch ( NumberFormatException e){
            return  "0.00";
        }


    }

    public static String getPrice(int price){
        BigDecimal rateDecimal = new BigDecimal(Double.valueOf(price));
        BigDecimal  num=rateDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        return String.valueOf(num);
    }
    public static String getPrice(Double price){
        BigDecimal rateDecimal = new BigDecimal(Double.valueOf(price));
        BigDecimal  num=rateDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        return String.valueOf(num);
    }
    public static String getPrice(Float price){
        BigDecimal rateDecimal = new BigDecimal(Double.valueOf(price));
        BigDecimal  num=rateDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
        return String.valueOf(num);
    }
    public static String readStream(InputStream is) {

        try {

            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();

        } catch (IOException e) {

            return "";

        }

    }

}
