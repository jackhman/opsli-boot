package org.opsli.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获得 占位符中的内容
 *
 * @author Parker
 * @date 2021年6月2日15:41:36
 */
public class MessUtil {

    /**
     *  获得默认占位符 集合
     *  ${xxxx}
     *
     * @param soap 原始信息
     * @return List
     */
    public static List<String> getPlaceholderList(String soap) {
        // 使用 正则池
        Matcher matcher = DefPatternPool.PLACEHOLDER.matcher(soap);
        List<String> list = new ArrayList<>();
        int i = 1;
        while (matcher.find()) {
            list.add(matcher.group(i));
        }
        return list;
    }

    /**
     *  组装信息 
     * @param soap 原始信息
     * @param regex  条件 占位符
     * @param startStr  删除掉的内容
     * @param endStr 删除掉的内容
     * @param map 根据原始信息占位符中的内容作为key,获取替换进占位符中的内容
     * @return String
     */
    public static String getMes(String soap, String regex, String startStr, String endStr, Map<String, Object> map) {
        List<String> subUtil = getSubList(soap, regex);
        for (String s : subUtil) {
            if (map.containsKey(s) && null != map.get(s)) {
                soap = soap.replace(startStr + s + endStr, map.get(s).toString());
            }
        }
        return soap;
    }


    /**
     *  组装信息
     * @param soap 原始信息
     * @param regex  条件 占位符
     * @return List
     */
    public static List<String> getSubList(String soap, String regex) {
        // 使用 正则池
        Pattern pattern = DefPatternPool.get(regex);
        Matcher matcher = pattern.matcher(soap);
        List<String> list = new ArrayList<>();
        int i = 1;
        while (matcher.find()) {
            list.add(matcher.group(i));
        }
        return list;
    }


}