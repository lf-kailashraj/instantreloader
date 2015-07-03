package org.lftechnology.outlier.instantreloader;

/**
 * @author Kailash Bijayananda <fried.dust@gmail.com>
 */
public class PseudoField implements java.lang.Comparable {
	private int access;
	private String name;
	private String desc;

	public PseudoField(int access, String name, String desc) {
		this.access = access;
		this.name = name;
		this.desc = desc;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof PseudoField)
				&& StringUtils.equals(this.toString(), o.toString());
	}

	@Override
	public String toString() {
		return Constants.FIELD_DELIMITER + name + Constants.FIELD_DELIMITER
				+ name;
	}

	public int compareTo(Object o) {
		return this.toString().compareTo(o.toString());
	}
}
