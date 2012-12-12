package jacobphillips;

public final class Log {
    private Log() {} // Not instantiable
    
    // Logging levels
    public static final int NONE    = 0;
    public static final int ERROR   = 1;
    public static final int WARN    = 2;
    public static final int DEBUG   = 3;
    public static final int INFO    = 4;
    public static final int VERBOSE = 5;

    // Delimiter used between "columns" of a message.
    private static final String COL_DELIM = "|";

    // Constant values for the level column.
    private static final String LEVEL_ERROR   = "(ERROR)   " + COL_DELIM + " ";
    private static final String LEVEL_WARN    = "(WARNING) " + COL_DELIM + " ";
    private static final String LEVEL_DEBUG   = "(DEBUG)   " + COL_DELIM + " ";
    private static final String LEVEL_INFO    = "(INFO)    " + COL_DELIM + " ";
    private static final String LEVEL_VERBOSE = "(VERBOSE) " + COL_DELIM + " ";
    
    // Current logging level
    private static int sLevel = INFO;
    
    // Width of the tag column.
    private static int sTagWidth = 15;
    
    /** Get the current maximum level of logging the log responds to. */
    public static int getLevel() {
        return sLevel;
    }
    
    /** Change the maximum level of logging the log responds to. Default is INFO. */
    public static void setLevel(int level) {
        sLevel = level;
    }
    
    public static int getTagWidth() {
        return sTagWidth;
    } 
    
    public static void setTagWidth(int width) {
        sTagWidth = width;
    } 
    
    /** Log an error message. */
    public static void e(String tag, String message) {
        e(0, tag, message);
    }
    /** Log an error message. */
    public static void e(int clock, String tag, String message) {
        if (sLevel < ERROR) return;        
        System.err.println(makeMessage(LEVEL_ERROR, clock, tag, message));
    }
    
    /** Log a warning message. */
    public static void w(String tag, String message) {
        w(0, tag, message);
    }
    /** Log a warning message. */
    public static void w(int clock, String tag, String message) {
        if (sLevel < WARN) return;        
        System.err.println(makeMessage(LEVEL_WARN, clock, tag, message));
    }
    
    /** Log a debug message. */
    public static void d(String tag, String message) {
        d(0, tag, message);
    }
    /** Log a debug message. */
    public static void d(int clock, String tag, String message) {
        if (sLevel < DEBUG) return;        
        System.err.println(makeMessage(LEVEL_DEBUG, clock, tag, message));
    }
    
    /** Log an info message. */
    public static void i(String tag, String message) {
        i(0, tag, message);
    }
    /** Log an info message. */
    public static void i(int clock, String tag, String message) {
        if (sLevel < INFO) return;        
        System.out.println(makeMessage(LEVEL_INFO, clock, tag, message));
    }
    
    /** Log a verbose message. */
    public static void v(String tag, String message) {
        v(0, tag, message);
    }
    /** Log a verbose message. */
    public static void v(int clock, String tag, String message) {
        if (sLevel < VERBOSE) return;        
        System.out.println(makeMessage(LEVEL_VERBOSE, clock, tag, message));
    }
    
    private static String makeMessage(String level, int clock, String tag, String message) {
        return level + String.format("%-4s", String.valueOf(clock)) + " " + COL_DELIM + " "
                + String.format("%-" + sTagWidth + "s", tag) + " " + COL_DELIM + " "
                + message;
    }
}