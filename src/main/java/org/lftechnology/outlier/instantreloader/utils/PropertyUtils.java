package org.lftechnology.outlier.instantreloader.utils;

import java.util.Properties;

/**
 * 
 * @author anish
 *
 */
public class PropertyUtils {

	/**
	 * 
	 * @param props
	 */
	public static void checkRequiredProps(Properties props) {
		System.out.println("Checking properties files : " + props.toString());
		String[] requiredProps = { "reload.packages", "reload.dir","class.loader.suffix" };
		for (String s : requiredProps) {
			if (props.getProperty(s) == null || props.getProperty(s).equals("")) {
				System.err.println("Agent error! Property not specified in reload.properties:"+ s);
				System.exit(-1);
			}
		}
	}
}
