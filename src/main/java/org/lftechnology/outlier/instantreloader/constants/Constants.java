package org.lftechnology.outlier.instantreloader.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author frieddust
 *
 */
public class Constants {

	public static final String FIELD_DELIMITER = "-";
	
	public static final String OUTLIER_INSTANCE_FIELDS = "__outlier_instance_fields__";

	public static final String OUTLIER_CLASS_RELOADER_FIELDS = "__outlier_class_reloader_field__";
	public static final String OUTLIER_CLINIT_METHOD_NAME = "__outlier_clinit__";

	public static final Set<String> OUTLIER_ADDED_FIELDS = new HashSet<String>();

	static {
		OUTLIER_ADDED_FIELDS.add(OUTLIER_INSTANCE_FIELDS);
		OUTLIER_ADDED_FIELDS.add(OUTLIER_CLASS_RELOADER_FIELDS);
	}
}
