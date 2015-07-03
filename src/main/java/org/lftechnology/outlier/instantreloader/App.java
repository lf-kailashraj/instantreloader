package org.lftechnology.outlier.instantreloader;

/**
 * Hello world!
 *
 */
public class App {
	public App() {
		try {
			for (;;) {
				doSomething();
				Thread.sleep(2000);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	private void doSomething() {
		System.out.println("from do something");
	}

	public static void main(String[] args) {
		new App();
	}
}
