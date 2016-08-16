package com.moral.airtree.utils;

import java.math.BigDecimal;

/**
 * Created by bin.shen on 6/28/16.
 */
public class StringUtils {

    public static boolean isEmptyOrNull(String val) {
        if(val == null || val.isEmpty() || val.equals("null")) {
            return true;
        }
        return false;
    }

    public static String getFormatData(String val) {
        if(isEmptyOrNull(val)) {
            return "0";
        }
        return new BigDecimal(val).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String getFormaldehyde(double d) {
        if(d == 0) return "0";
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
}
