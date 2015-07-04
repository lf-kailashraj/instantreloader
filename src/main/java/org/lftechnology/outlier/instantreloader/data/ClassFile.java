package org.lftechnology.outlier.instantreloader.data;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class ClassFile {
	protected Long version = 0L;

	protected byte[] classFile;

	private File file;

	public ClassFile(File file) {
		try {
			this.file = file;
			this.version = file.lastModified();
			setClassFile(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void incVersion() {
		this.version++;
	}

	public long getVersion() {
		return version.longValue();
	}

	public byte[] getClassFile() {
		return classFile;
	}

	public void setClassFile(byte[] classFile) {
		this.classFile = classFile;
	}

	public boolean changed() {
		return file.lastModified() > getVersion();
	}

	public void reload() {
		this.version = file.lastModified();
		try {
			setClassFile(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(classFile);
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		ClassFile other = (ClassFile) obj;
		if (!Arrays.equals(classFile, other.classFile))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassFile [version=" + version + ", classFile="
				+ Arrays.toString(classFile) + ", file=" + file + "]";
	}
	
	
}
