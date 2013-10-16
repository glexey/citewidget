package org.glexey.citewidget;

public class Cite {
	public boolean used = false;
	public String text = "";
	public String author = "";
	public String comment = "";

	public Cite(String s) {
		super();
		// Split the string to citing text and author
		String[] tokens = s.split("\\|");
		text = tokens[0];
		if (tokens.length > 1)	author = tokens[1];
		if (tokens.length > 2) comment = tokens[2];
	}
	
	public Cite(String text, String author, String comment, boolean used) {
		this.text = text;
		this.author = author;
		this.comment = comment;
		this.used = used;
	}

	public boolean equals(Object obj) {
		// if the two objects are equal in reference, they are equal
		if (this == obj) {
			return true;
		} else if (obj instanceof Cite) {
			// If the text of two quotes match, they are equal
			// Author/Comment doesn't matter in this case
			Cite cite = (Cite) obj;
			if (!cite.text.equals(text)) return false;
			if (!cite.author.equals(author)) return false;
			if (!cite.comment.equals(comment)) return false;
			return true;
		}
		return false;
	}

	/**
	 * Tests whether the quotes can be considered same quote,
	 * disregarding comments and author. May in future test
	 * for "approximate" text matching.
	 * 
	 * @param cite - quote to compare to
	 * @return
	 */
	public boolean sameQuoteAs(Cite cite) {
		if (equals(cite)) return true;
		return cite.text.equals(text);
	}
}
