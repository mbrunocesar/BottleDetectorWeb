package utilities;

public class Logger {
	
	public static int LOG_LEVEL = 0;

	public synchronized static void println(String text) {
		if (LOG_LEVEL == 1) {
			System.out.println(LOG_LEVEL);
		}
	}

	public synchronized static void print(String text) {
		if (LOG_LEVEL == 1) {
			System.out.print(LOG_LEVEL);
		}
	}

	public static void setAsDebugger() {
		LOG_LEVEL = 1;
	}
	
}
