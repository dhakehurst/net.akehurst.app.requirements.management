package net.akehurst.app.requirements.management.engineering.channel.dataStore.transform;

import net.akehurst.app.requirements.management.computational.requirementsInterface.ChapterIdentity;
import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.computational.requirementsInterface.TopChapter;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo.JdoProject;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo.JdoTopChapter;
import net.akehurst.transform.binary.Relation;
import net.akehurst.transform.binary.TransformException;
import net.akehurst.transform.binary.Transformer;

public class TopChapter2JdoTopChapter implements IBinaryRule<TopChapter, JdoTopChapter> {

	@Override
	public boolean isValidForLeft2Right(final TopChapter left) {
		return true;
	}

	@Override
	public boolean isValidForRight2Left(final JdoTopChapter right) {
		return true;
	}

	@Override
	public JdoTopChapter constructLeft2Right(final TopChapter left, final ITransformer transformer) throws TransformException {
		try {
			final JdoTopChapter right = new JdoTopChapter();
			final JdoProject rightProject = transformer.transformLeft2Right(Project2JdoProject.class, left.getOwner());
			right.setOwner(rightProject);
			right.setIdentity(left.getIdentity().asPrimitive());
			return right;
		} catch (final Exception ex) {
			throw new TransformException(ex.getMessage(), ex);
		}
	}

	@Override
	public TopChapter constructRight2Left(final JdoTopChapter right, final ITransformer transformer) throws TransformException {
		try {
			final Project owner = transformer.transformRight2Left(Project2JdoProject.class, right.getOwner());
			final ChapterIdentity identity = new ChapterIdentity(right.getIdentity());
			final TopChapter left = new TopChapter(owner, identity);
			return left;
		} catch (final Exception ex) {
			throw new TransformException(ex.getMessage(), ex);
		}
	}

	@Override
	public void configureLeft2Right(final TopChapter left, final JdoTopChapter right, final ITransformer transformer) throws TransformException {
		right.setName(left.getName());
		right.setDescription(left.getDescription());
	}

	@Override
	public void configureRight2Left(final TopChapter left, final JdoTopChapter right, final ITransformer transformer) throws TransformException {
		left.setName(right.getName());
		left.setDescription(right.getDescription());
	}

}
