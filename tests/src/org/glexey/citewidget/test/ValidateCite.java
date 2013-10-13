package org.glexey.citewidget.test;

import org.glexey.citewidget.Cite;

import android.test.AndroidTestCase;

public class ValidateCite extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSimpleConstructor() {
		Cite c1 = new Cite("Quote text 1|Quote Author 1|Quote Comments 1");
		Cite c2 = new Cite("Quote text 2|Quote Author 2");
		Cite c3 = new Cite("Quote text 3");
		assertTrue(c1.text.equals("Quote text 1"));
		assertTrue(c1.author.equals("Quote Author 1"));
		assertTrue(c1.comment.equals("Quote Comments 1"));
		assertTrue(c2.text.equals("Quote text 2"));
		assertTrue(c2.author.equals("Quote Author 2"));
		assertTrue(c2.comment.equals(""));
		assertTrue(c3.text.equals("Quote text 3"));
		assertTrue(c3.author.equals(""));
		assertTrue(c3.comment.equals(""));
	}

	public void testComparison() {
		Cite c1 = new Cite("Quote text 1|Quote Author 1|Quote Comments 1");
		Cite c2 = new Cite("Quote text 2|Quote Author 2");
		Cite c3 = new Cite("Quote text 1|Quote Author 3|Quote Comments 3");
		assertTrue(c1.equals(c3));
		assertFalse(c1.equals(c2));
	}
}
