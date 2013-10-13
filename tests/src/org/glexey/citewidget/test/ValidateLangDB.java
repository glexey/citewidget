package org.glexey.citewidget.test;

import org.glexey.citewidget.Cite;
import org.glexey.citewidget.LangDB;
import org.glexey.citewidget.LangDB.LangDBException;

import android.content.Context;
import android.content.res.Resources;
import android.test.InstrumentationTestCase;

public class ValidateLangDB extends InstrumentationTestCase {

	private LangDB db_ru;
	private Context tst_ctx;
	
	protected void setUp() throws Exception {
		
		// context of the AndroidTestCase, not the app under test..
		tst_ctx = getInstrumentation().getContext();

		db_ru = new LangDB(tst_ctx, "ru_test.db");
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
		db_ru.createDB();
		assertTrue(db_ru.dbExists());
		assertEquals(0, db_ru.length());
	}
	
	public void testUpdateFromResource() throws LangDBException {
		db_ru.createDB();
		db_ru.updateFromResource(R.array.testCiteArrRU);
		// Check that the correct number of elements were read from the resource
        Resources res = tst_ctx.getResources();
        String[] quotes = res.getStringArray(R.array.testCiteArrRU);
        assertEquals(quotes.length, db_ru.length());
	}

	public void testAppendShift() throws LangDBException {
		db_ru.createDB();
		Cite c0 = new Cite("Quote 0|Author 0|Comment 0");
		Cite c1 = new Cite("Quote 1|Author 1");
		Cite c2 = new Cite("Quote 2");
		db_ru.append(c0);
		assertEquals(1, db_ru.length());
		db_ru.append(c1);
		assertEquals(2, db_ru.length());
		db_ru.append(c2);
		assertEquals(3, db_ru.length());
		
		assertTrue(c0.equals(db_ru.get(0)));
		assertTrue(c1.equals(db_ru.get(1)));
		assertTrue(c2.equals(db_ru.get(2)));
		
		Cite c0p = db_ru.shift();
		assertEquals(2, db_ru.length());
		assertTrue(c0.equals(c0p));
		
	}
	
}
