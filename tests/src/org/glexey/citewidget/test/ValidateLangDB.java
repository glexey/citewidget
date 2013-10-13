package org.glexey.citewidget.test;

import org.glexey.citewidget.LangDB;
import org.glexey.citewidget.LangDB.LangDBException;

import android.content.Context;
import android.content.res.Resources;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

public class ValidateLangDB extends InstrumentationTestCase {

	private LangDB db_ru;
	private Context tst_ctx;
	
	protected void setUp() throws Exception {
		
		// context of the AndroidTestCase, not the app under test..
		tst_ctx = getInstrumentation().getContext();

		db_ru = new LangDB(tst_ctx, "ru", R.array.testCiteArrRU);
		//db_ru.deleteDB();
		//db_ru.createInitial();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testDeleteCreateExists() throws LangDBException {
		db_ru.deleteDB();
		assertFalse(db_ru.dbExists());
		db_ru.createInitial();
		assertTrue(db_ru.dbExists());
		try {
			db_ru.createInitial();
			fail("Creating the DB w/o deleting it first should throw an exception");
		} catch (LangDBException e) {
			// This is the expected behavior
		} 
	}
	
	public void testCreateFromResource() throws LangDBException {
		db_ru.deleteDB();
		db_ru.createInitial();
		// Check that the correct number of elements were read from the resource
        Resources res = tst_ctx.getResources();
        String[] quotes = res.getStringArray(R.array.testCiteArrRU);
        assertEquals(quotes.length, db_ru.length());
	}

}
