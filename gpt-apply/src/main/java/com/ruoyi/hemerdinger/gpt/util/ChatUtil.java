package com.ruoyi.hemerdinger.gpt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    /**
     * 从字符串中提取被指定符号包围的内容。
     * @param input 输入的字符串
     * @param startDelimiter 开始符号，如中文书名号'《'或英文'<'
     * @param endDelimiter 结束符号，如中文书名号'》'或英文'>'
     * @return 符号内的内容，如果没有找到则返回null
     */
    public static String extractTitle(String input, String startDelimiter, String endDelimiter) {
        // 构造正则表达式，匹配开始符号和结束符号之间的内容，包括符号本身
        String regex = Pattern.quote(startDelimiter) + "(.*?)" + Pattern.quote(endDelimiter);

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 查找匹配项
        if (matcher.find()) {
            // 返回第一个匹配的结果，去掉开始和结束符号
            return matcher.group(1);
        } else {
            // 没有找到匹配项
            return null;
        }
    }

    /**
     * 从Markdown字符串中提取JSON。
     */
    public static String extractMarkDownJSON(String input) {
        return extractTitle( input,"```json" , "```" );
    }


    /**
     * 从字符串中提取包括开始和结束符号在内的完整标题。
     * @param input 输入的字符串
     * @param startDelimiter 开始符号，如中文书名号'《'或英文'<'
     * @param endDelimiter 结束符号，如中文书名号'》'或英文'>'
     * @return 包含开始和结束符号的完整标题，如果没有找到则返回null
     */
    public static String extractTitleWithDelimiters(String input, String startDelimiter, String endDelimiter) {
        // 构造正则表达式，直接匹配开始符号、中间的内容以及结束符号
        String regex = Pattern.quote(startDelimiter) + "(.+?)" + Pattern.quote(endDelimiter);

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建匹配器
        Matcher matcher = pattern.matcher(input);

        // 查找匹配项
        if (matcher.find()) {
            // 返回完整的匹配结果，即包含开始和结束符号的标题
            return matcher.group(0);
        } else {
            // 没有找到匹配项
            return null;
        }
    }

    /**
     * 从Markdown字符串中提取JSON。
     */
    public static String extractMarkDownJSONWithDelimiters(String input) {
        return extractTitle( input,"```json" , "```" );
    }


}
