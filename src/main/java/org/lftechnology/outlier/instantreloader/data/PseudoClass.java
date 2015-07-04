package org.lftechnology.outlier.instantreloader.data;

import java.util.Set;
import java.util.TreeSet;

import org.lftechnology.outlier.instantreloader.PseudoField;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PseudoClass other = (PseudoClass) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PseudoClass [className=" + className + ", fields=" + fields	+ "]";
	}

}
