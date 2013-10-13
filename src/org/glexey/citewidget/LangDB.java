package org.glexey.citewidget;

import android.content.Context;

public class LangDB {

	// Language identifier, for tracking purposes. E.g.: "ru"
	private String language;
	
	// The ID of the string array for initial DB population
	private int init_array_res_id;
	
	public LangDB(Context ctx, String language, int init_array_res_id) {
		this.language = language;
		this.init_array_res_id = init_array_res_id;
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

	
	public void deleteDB() {
		// TODO Auto-generated method stub
		// This method deletes the database file, if it exists
		if (!dbExists()) return;
	}
	
	public int length() throws LangDBException {
		if (!dbExists()) throw new LangDBException("DB does not exist");
		// TODO stub
		return(0);
	}

	public void createInitial() throws LangDBException {
		// TODO Auto-generated method stub
		
	}

	public boolean dbExists() {
		// TODO Auto-generated method stub
		return false;
	}

}
