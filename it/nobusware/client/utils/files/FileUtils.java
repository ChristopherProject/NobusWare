package it.nobusware.client.utils.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import it.nobusware.client.NobusWare;

public abstract class FileUtils {
	
	public static List<String> readFile(String file) {
		try {
			return Files.readAllLines(Paths.get(String.valueOf(file), new String[0]));
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static void writeFile(String file, List<String> newcontent) {
		try {
			FileWriter fw = new FileWriter(NobusWare.nobitaDir + File.separator +  String.valueOf(file) + ".txt");
			for (String s : newcontent)
				fw.append(String.valueOf(s) + "\r\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createFileAI(String name) {
		try {
			File file = new File(NobusWare.nobitaDir + File.separator + "NobusWareAI",
					String.valueOf(name) + ".txt");
			if (!file.exists()) {
				PrintWriter printWriter = new PrintWriter(new FileWriter(file));
				printWriter.println();
				printWriter.close();
			}
			System.out.println(NobusWare.nobitaDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Deletefile(String name) {
		File file = new File(NobusWare.nobitaDir, String.valueOf(name) + ".txt");
		if(file.exists()) {
			file.delete();
		}
	}

	public static void createFile(String name) {
		try {
			File file = new File(NobusWare.nobitaDir, String.valueOf(name) + ".txt");
			if (!file.exists()) {
				PrintWriter printWriter = new PrintWriter(new FileWriter(file));
				printWriter.println();
				printWriter.close();
			}
			System.out.println(NobusWare.nobitaDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
