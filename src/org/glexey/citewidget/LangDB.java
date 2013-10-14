package org.glexey.citewidget;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LangDB extends SQLiteOpenHelper {

	// Database name
	private String name;
	
	private String dbFileName;

	// Local copy of the context for database and resources access
	private Context ctx;

	public LangDB(Context ctx, String name) {
    	super(ctx, name, null, 1);
		this.name = name;
        this.ctx = ctx;
        dbFileName = ctx.getDatabasePath(name).getAbsolutePath();
	}

	/**
	 * Create a new empty database file. If database file exists already, first remove it.
	 * @throws LangDBException 
	 */
	public void createDB() throws LangDBException {
		deleteDB();
	    SQLiteDatabase db = getReadableDatabase();
	    db.close();
	}

	
	/**
	 * Delete the database file, if it exists
	 * @throws LangDBException
	 */
	public void deleteDB() throws LangDBException {
		File file = new File(dbFileName);
		if (!file.exists()) return;
		close(); // In case database is open, close it
		if (!file.delete()) throw new LangDBException("Couldn't delete database file");
		File file2 = new File(dbFileName + "-journal");
		file2.delete(); // Will not check, it's not essential to delete this one
	}
	
	public int length() throws LangDBException {
		if (!dbExists()) throw new LangDBException("DB does not exist");
		// TODO stub
		return(0);
	}

	// Check if the database file exists in a file system
	public boolean dbExists() {
		File file = new File(dbFileName);
		return file.exists(); 
	}

	/**
	 * Read string array specified by resID, convert them to Cite objects and 
	 * insert them into the database.  
	 * @param ctx    - Context to get the resources from
	 * @param resid  - String array resource ID
	 */
	public void updateFromResource(int resID) {
		// TODO Auto-generated method stub
		
	}

	
	/**
     * Thrown when there were problems accessing the database
     * 	- not initialized
     *  - no space left to append to the DB
     */
    public static class LangDBException extends Exception {
		private static final long serialVersionUID = 1L;

		public LangDBException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public LangDBException(String detailMessage) {
            super(detailMessage);
        }
    }


	/**
	 * @param cite
	 */
	public void append(Cite cite) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param i
	 * @return
	 */
	public Cite get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public Cite shift() {
		// TODO Auto-generated method stub
		return get(0);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        String CREATE_QUOTES_TABLE = "CREATE TABLE quotes ("
                + " id INTEGER PRIMARY KEY,"
        		+ " quote_text TEXT,"
                + " quote_author TEXT,"
                + " quote_comment TEXT,"
                + " used INTEGER"
        		+ ")";
        db.execSQL(CREATE_QUOTES_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		double a = 1.0 / 0.0;
	}

	/**
	 * @return the database name
	 */
	public String getName() {
		return name;
	}
}
