package de.timweb.blueocean;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class Test {
	public static void main(String[] args) throws Exception {
		FileUtils.write(new File("file.txt"), "הצü");
		System.out.println(FileUtils.readFileToString(new File("file.txt")));
	}
}
