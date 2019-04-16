package net.akehurst.app.requirements.management.computational.requirementsInterface;

public class Requirement extends ChapterItem {

	public Requirement(final IChapter owner, final ChapterItemIdentity identity) {
		super(owner, identity);
	}

	String text;

	public String getText() {
		return this.text;
	}
}
