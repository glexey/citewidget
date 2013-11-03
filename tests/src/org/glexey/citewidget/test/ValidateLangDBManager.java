/**
 * 
 */
package org.glexey.citewidget.test;

import java.util.ArrayList;
import java.util.Locale;

import org.glexey.citewidget.Cite;
import org.glexey.citewidget.LangDBManager;

import android.content.Context;
import android.content.res.Resources;
import android.test.InstrumentationTestCase;
//import android.test.RenamingDelegatingContext;

/**
 * @author aagaidiu
 *
 */
public class ValidateLangDBManager extends InstrumentationTestCase {

	private Context tst_ctx;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		
		// context of the AndroidTestCase
		tst_ctx = getInstrumentation().getContext();

		super.setUp();
	}

	/* (non-Javadoc)
	 * @see android.test.InstrumentationTestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private String wrongQuote(Cite cite) {
		return "Wrong quote: \"" + cite.text + "\"";
	}
	
	public void testResourceIDs() {
		String pkg = tst_ctx.getPackageName();
		Resources res = tst_ctx.getResources();
		int id = res.getIdentifier("CiteArrRU", "array", pkg);
		assertEquals(R.array.CiteArrRU, id);
	}
	
	// The main functionality of LangDBManager class is to give us quotes
	// let's test that it always gives us some quote, even if a stub.
	public void testGetNextQuoteBasic() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		Cite cite = reader.getNextQuote();
		assertNotNull(cite);
	}
		
	// getNextQuote("lang", "src") should return null if corresponding DB is empty
	public void testGetNextQuoteLangSrcBasic() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite cite = reader.getNextQuote("ru", "web");
		assertNull(cite);
	}
	
	// LangDBManager should get the list of languages from resources upon
	// constructing, from string array 'languages'
	public void testLanguagesArrayConstruction() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		String[] langs = reader.getlanguageList();
		assertNotNull(langs);
		assertTrue(langs.length == 3);
		assertTrue(langs[0].equals("ru"));
		assertTrue(langs[1].equals("en"));
		assertTrue(langs[2].equals("es"));
	}
	
	public void testReaderInitialization() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite cite = reader.getNextQuote();
		assertTrue(wrongQuote(cite), cite.equals(new Cite("Цитата номер 1|Алексей")));
	}
	
	// Add a quote to the specified dictionary
	public void testAddQuote() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite cite1 = new Cite("Test quote");
		reader.addQuote("ru", cite1);
		Cite cite2 = reader.getNextQuote("ru", "web");
		assertTrue(cite1.equals(cite2));
	}
	
	public void testOrder() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		
		reader.addQuote("ru.fix", new Cite("ru_fix1"));
		reader.addQuote("en.fix", new Cite("en_fix1"));
		reader.addQuote("es.fix", new Cite("es_fix1"));
		reader.addQuote("es.fix", new Cite("es_fix2"));
		// Now we should have 12 quotes
		assertTrue(reader.getNextQuote().equals(new Cite("ru_fix1")));
		assertTrue(reader.getNextQuote().equals(new Cite("en_fix1")));
		assertTrue(reader.getNextQuote().equals(new Cite("es_fix2"))); // first in last out
		assertTrue(reader.getNextQuote().equals(new Cite("es_fix1")));
		assertTrue(reader.getNextQuote().equals(new Cite("Цитата номер 1|Алексей")));
		assertTrue(reader.getNextQuote().equals(new Cite("English quote 1|Alexey")));
		assertTrue(reader.getNextQuote().equals(new Cite("Cita Española 1|Alexei")));
		assertTrue(reader.getNextQuote().equals(new Cite("Цитата номер 2|Настя")));
		assertTrue(reader.getNextQuote().equals(new Cite("English quote 2|Nastuu")));
		assertTrue(reader.getNextQuote().equals(new Cite("Cita Española 2|Nastya")));
		assertTrue(reader.getNextQuote().equals(new Cite("Цитата номер 3|Федя|5 лет")));
		assertTrue(reader.getNextQuote().equals(new Cite("English quote 3|Fyodor|5 years")));
		assertTrue(reader.getNextQuote().equals(new Cite("Cita Española 3|Fedya|5 años")));
	}
	
	public void testHistory() {
		LangDBManager reader = new LangDBManager(tst_ctx, R.array.languages);
		// Create a set of 2 dictionaries per language, e.g.: "ru.fix", "ru.web"
		reader.initFromScratch();
		Cite[] cite = new Cite[10];
		for (int i=0; i<10; i++) {
			cite[i] = reader.getNextQuote();
		}
		// test set contains 9 quotes, so 10th should return a stub
		assertTrue(cite[9].equals(LangDBManager.stubCite));
		ArrayList<Cite> hist = reader.getQuoteHistory();
		assertNotNull(hist);
		assertEquals(9, hist.size());
		for (int i=8; i>=0; i--) {
			Cite histCite = hist.get(8-i);
			assertEquals(cite[i], histCite);
		}
		// Blow up past max history size and then check history stays at max size
		for(int i=0; i<LangDBManager.historySize+10; i++)
			reader.addQuote("ru.web", new Cite("Cite" + i));
		ArrayList<Cite> maxhist = reader.getQuoteHistory();
		assertEquals(LangDBManager.historySize, maxhist.size());
	}
	
	public void testLanguageDisabling() {
		fail("testLanguageDisabling() not yet written");
	}
}
