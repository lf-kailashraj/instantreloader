package org.lftechnology.outlier.instantreloader.utils;

/**
 * 
 * @author anish
 *
 */
public class FormatUtils {

	/**
	 * 
	 * @param in
	 * @return
	 */
  public static String stripZeros(String in) {
    String result = in;
    try {
      long num = Long.parseLong(result);
      result = String.format("%d", num);
    } catch (Exception ex) {
      System.err.println("Exception while string zeros " + ex);
    }
    return result;
  }


}
