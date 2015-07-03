package org.lftechnology.outlier.instantreloader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author frieddust
 *
 */
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
}
