package org.lftechnology.outlier.instantreloader.utils;

/**
 * 
 * @author anish
 *
 */
public class NumberUtils {

	/**
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static int convertStringToInteger(String str) throws Exception {
        if (str != null) {
            str = str.replaceAll(" ", "");
            if (str.endsWith("-")) {
                str = str.substring(0, str.length() - 1);
                str = "-" + str;
            }
            return Integer.valueOf(str);
        } else {
            throw new Exception("Cannot convert string => " + str + " to number.");
        }
    }
}
