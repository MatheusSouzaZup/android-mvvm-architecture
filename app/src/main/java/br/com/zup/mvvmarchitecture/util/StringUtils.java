package br.com.zup.mvvmarchitecture.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class StringUtils {

    public static String formatValue(final String str) {
        BigDecimal bigDecimal = new BigDecimal(str.replace(",", "."));
        return formatValue(bigDecimal);
    }

    public static String formatValue(BigDecimal value) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(value.doubleValue());
    }

    public static String formatValue(Double value) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return numberFormat.format(value);
    }

    public static String formatUnit(BigDecimal value) {
        return String.valueOf(isWholeNumber(value) ? value.intValue() : value);
    }

    public static String formatUnit(Double value) {
        if (isWholeNumber(value)) {
            return String.valueOf(value.intValue());
        } else {
            return String.valueOf(value);
        }
    }

    public static boolean isWholeNumber(BigDecimal number) {
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isWholeNumber(Double number) {
        return number == number.intValue();
    }


    /**
     * @param s        string to be completed
     * @param fillWith complete with
     * @param n        number of total chars
     * @return
     */
    public static String leftPad(String s, char fillWith, int n) {
        StringBuilder sb = new StringBuilder(s);
        int charsToGo = n - sb.length();
        while (charsToGo > 0) {
            sb.insert(0, fillWith);
            charsToGo--;
        }

        return sb.toString();
    }

}
