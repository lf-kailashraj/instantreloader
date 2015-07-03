package org.lftechnology.outlier.instantreloader;

import java.util.Set;
import java.util.TreeSet;

public class PseudoClass {

	private String className;
	private Set<PseudoField> fields = new TreeSet<PseudoField>();

	public Set<PseudoField> getFields() {
		return fields;
	}

	public void setFields(Set<PseudoField> fields) {
		this.fields = fields;
	}

	public boolean hasField(String name, String desc) {
		return fields.contains(new PseudoField(0, name, desc));
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
