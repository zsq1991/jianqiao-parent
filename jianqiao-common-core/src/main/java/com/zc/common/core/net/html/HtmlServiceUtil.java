/**
 * Project Name:czb
 * Package Name:org.czb.utils.htmls
 * File Name:HtmlServiceUtil.java
 * Create Date:2013-9-27 上午11:19:45
 * SVN:$Id$
 * Copyright © 2013 厦门尚科网络科技有限责任公司 All rights reserved.
 */
package com.zc.common.core.net.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * html 文件处理工具类
 * HtmlServiceUtil.java类文件描述说明.
 * html转义字符及css清除；涉及log4j,common-lang
 * @author czb
 * @since JDK 1.6
 * @see
 */
public class HtmlServiceUtil {
    /**
     * 过滤Html标签,只留纯文本，清除xss代码
     * 
     * @param bodyHtml 内容
     * @return
     */
    public static String cleanHtmlToSimpleText(String bodyHtml) {
        String content = Jsoup.clean(bodyHtml, Whitelist.basic());
        return content;
    }
    
    /**
     * 
     * 方法的作用:通过正则过滤html元素，只留村文本.<br/>
     * 
     * @param bodyHtml
     * @return
     * 
     * @author mike
     * @create-time 2014-1-8 下午10:31:59
     */
    public static String cleanHtmlToText(String bodyHtml){
        String content = bodyHtml.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        return content;
        
    }
    
    
    /**
     * 清除html代码
     * <p>所有包括在'&lt;'与'&gt;'之间的内容全部都会被清除掉,并返回</P>
     * @param args
     * @return String
     */
    public static String clearHTMLToString(String args){
        return clearHTMLToString(args,false);
    }
    /**
     * 清除html代码
     * <p>所有包括在'&lt;'与'&gt;'之间的内容全部都会被清除掉,并返回</P>
     * @param args
     * @param replaceNull 是否替换空格等制表符
     * @return String
     */
    public static String clearHTMLToString(String args,boolean replaceNull){
        if(StringUtils.isEmpty(args)){
            return "";
        }
        args= args.replaceAll("(?is)<(.*?)>","");
        if(replaceNull){
            args = args.replaceAll("\\s*|\t|\r|\n","");
        }
        return args;
    }
    /**
     * 清除html代码
     * <p>所有包括在'&lt;'与'&gt;'之间的内容全部都会被清除掉，并指定返回的长度</P>
     * @param args
     * @return String
     */
    public static String clearHTMLToString(String args,int maxSize){
        return clearHTMLToString(args, maxSize, "");
    }
    /**
     * 清除html代码
     * <p>所有包括在'&lt;'与'&gt;'之间的内容全部都会被清除掉，并指定返回的长度</P>
     * @param args
     * @return String
     */
    public static String clearHTMLToString(String args,int maxSize,String replace){
        args=clearHTMLToString(args);
        if(maxSize<=0){
            return args;
        }
        if(args.length()<=maxSize){
            return args;
        }
        return args.substring(0,maxSize).concat(replace);
    }
    /**
     * 将字符串截取指定长度
     * @param args
     * @param maxSize
     * @param replace
     * @return String
     */
    public static String clearHTMLToSize(String args,int maxSize,String replace){
        if(args.length()<=maxSize){
            return args;
        }
        return args.substring(0,maxSize).concat(replace);
    }
    
    
}
