import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GUIMain {
	
	private static String PATH;
	private static File fPATH;
	
	private static void getPath() {
		
		final JFileChooser fc = new JFileChooser();
		
		fc.setCurrentDirectory(new java.io.File("C:\\"));
		fc.setDialogTitle("Choose Ets2 server directory");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			fPATH = fc.getSelectedFile();
			PATH = fPATH.getPath().toString();
		} else {
			System.out.println("No selection ");
		}
	}
	
	private static void runServer() {
		
		try {
			Process p = Runtime.getRuntime().exec(PATH);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not load file in directory\n \"" 
					+ PATH + "\"");
		}
	}
	
	private static void runADB() {
		
		String[] cmd = { System.getProperty("user.home") + "\\AppData\\Local\\Android\\sdk\\platform-tools\\adb.exe", 
				"adb forward tcp:9898 tcp:9898", "adb forward tcp:9899 tcp:9899", "adb forward tcp:8251 tcp:8251"};
		
		
		try {
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			System.out.println("You might not have ADB installed or it might"
					+ " not be in default Android Studio directory..");
		}
	}
	
	public static void main(String [] args) {
		
		getPath();
		runServer();
		runADB();
	}
	
}