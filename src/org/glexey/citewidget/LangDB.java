package org.glexey.citewidget;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LangDB extends SQLiteOpenHelper {

	// Database name
	private String name;
	
	private String dbFileName;

	// main table name
	private static final String TABLE_QUOTES = "quotes";
	// main table field names
	private static final String KEY_TEXT = "text";
	private static final String KEY_AUTHOR = "author";
	private static final String KEY_COMMENT = "comment";
	private static final String KEY_USED = "used";
	
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
	
	public long length() throws LangDBException {
		if (!dbExists()) throw new LangDBException("DB does not exist");
		SQLiteDatabase db = getReadableDatabase();
		long n = DatabaseUtils.queryNumEntries(db, TABLE_QUOTES);
		db.close();
		return(n);
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
	 * Appends the quote record to the end of the list/table
	 * @param cite - The quote to add
	 * @throws LangDBException 
	 */
	public void append(Cite cite) throws LangDBException {
		if (!dbExists()) throw new LangDBException("DB does not exist");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TEXT, cite.text);
		values.put(KEY_AUTHOR, cite.author);
		values.put(KEY_COMMENT, cite.comment);
		values.put(KEY_USED, cite.used);
		db.insert(TABLE_QUOTES, null, values);
		db.close();
	}

	/**
	 * @param i
	 * @return
	 * @throws LangDBException 
	 */
	public Cite get(int i) throws LangDBException {
		if (!dbExists()) throw new LangDBException("DB does not exist");
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 * @throws LangDBException 
	 */
	public Cite shift() throws LangDBException {
		// TODO Auto-generated method stub
		return get(0);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES
                + "( id INTEGER PRIMARY KEY, "
        		+ KEY_TEXT + " quote_text TEXT, "
                + KEY_AUTHOR + " TEXT, "
                + KEY_COMMENT + " TEXT, "
                + KEY_USED + " INTEGER"
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
