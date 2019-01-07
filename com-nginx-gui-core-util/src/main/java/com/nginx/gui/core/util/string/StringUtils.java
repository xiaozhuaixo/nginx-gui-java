package com.nginx.gui.core.util.string;



/**
 * @author: hengbin_wu
 * @Date: 2019/1/3 15:19
 * @Description:
 */
public class StringUtils {
    public static boolean isEmpty(final String str){
        int strLen;
        if(str == null || (strLen = str.length()) == 0){
            return true;
        }
        return  false;
    }

    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))){
            return s;
        }
        else{
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))){
            return s;
        }
        else{
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
