package com.pipeclamp.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Brian Remedios
 */
public class FileUtil {

	private FileUtil() { }
	
	public static String getFileContents(String filename) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filename));

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
}
