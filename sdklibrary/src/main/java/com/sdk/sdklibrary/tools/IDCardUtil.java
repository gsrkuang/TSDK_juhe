package com.sdk.sdklibrary.tools;

/**
 * Data:2023/2/17
 * Time:22:41
 * author:colin handsome
 */



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IDCardUtil {
    /**
     * 18位身份证号
     */
    private static final Integer EIGHTEEN_ID_CARD = 18;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据身份证号获取性别
     *
     * @param IDCard
     * @return
     */
    public static String getSex(String IDCard) {
        String sex = "";
        if (StringUtils.isNotEmpty(IDCard)) {
            if (IDCard.length() == EIGHTEEN_ID_CARD) {
                // 判断性别
                if (Integer.parseInt(IDCard.substring(16).substring(0, 1)) % 2 == 0) {
                    sex = "女";
                } else {
                    sex = "男";
                }
            }
        }
        return sex;
    }

    /**
     * 根据身份证号获取年龄
     *
     * @param IDCard
     * @return
     */
    public static Integer getAge(String IDCard) {
        Integer age = 0;
        Date date = new Date();

        if (StringUtils.isNotEmpty(IDCard) && isValid(IDCard)) {
            if (IDCard.length() == EIGHTEEN_ID_CARD) {
                // 身份证上的年份
                String year = IDCard.substring(6).substring(0, 4);
                // 身份证上的月份
                String yue = IDCard.substring(10).substring(0, 2);
                // 当前年份
                String fyear = format.format(date).substring(0, 4);
                // 当前月份
                String fyue = format.format(date).substring(5, 7);
                // 当前月份大于用户出身的月份表示已过生日
                if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
                    // 当前用户还没过生日
                } else {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year);
                }
            }
        }
        return age;
    }

    /**
     * 获取出生日期  yyyy年MM月dd日
     *
     * @param IDCard
     * @return
     */
    public static String getBirthday(String IDCard) {
        String birthday = "";
        String year = "";
        String month = "";
        String day = "";
        if (StringUtils.isNotEmpty(IDCard)) {
            if (IDCard.length() == EIGHTEEN_ID_CARD) {
                // 身份证上的年份
                year = IDCard.substring(6).substring(0, 4);
                // 身份证上的月份
                month = IDCard.substring(10).substring(0, 2);
                //身份证上的日期
                day = IDCard.substring(12).substring(0, 2);
            }
            birthday = year + "年" + month + "月" + day + "日";
        }
        return birthday;
    }

    /**
     * 判断是否为18岁
     *
     * @param IDCard
     * @return
     */

    public static boolean isAdult(String IDCard) {
        Integer age = 0;
        Date date = new Date();

        if (StringUtils.isNotEmpty(IDCard) && isValid(IDCard)) {
            if (IDCard.length() == EIGHTEEN_ID_CARD) {
                // 身份证上的年份
                String year = IDCard.substring(6).substring(0, 4);
                // 身份证上的月份
                String yue = IDCard.substring(10).substring(0, 2);
                // 当前年份
                String fyear = format.format(date).substring(0, 4);
                // 当前月份
                String fyue = format.format(date).substring(5, 7);
                // 当前月份大于用户出身的月份表示已过生日
                if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
                    // 当前用户还没过生日
                } else {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year);
                }
            }
        }

        if (age >=18){
            return true;
        }else {
            return false;
        }

    }

    /**
     * 身份证验证
     *
     * @param id 号码内容
     * @return 是否有效
     */
    public static boolean isValid(String id) {
        Boolean validResult = true;
        //校验长度只能为15或18
        int len = id.length();
        if (len != EIGHTEEN_ID_CARD) {
            validResult = false;
        }
        //校验生日
        if (!validDate(id)) {
            validResult = false;
        }
        return validResult;
    }

    /**
     * 校验生日
     *
     * @param id
     * @return
     */
    private static boolean validDate(String id) {
        try {
            String birth = id.length() == 15 ? "19" + id.substring(6, 12) : id.substring(6, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(birth);
            if (!birth.equals(sdf.format(birthDate))) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


}
