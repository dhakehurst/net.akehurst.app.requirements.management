package net.akehurst.app.requirements.management.engineering.channel.dataStore.transform;

import java.util.List;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.computational.requirementsInterface.ProjectIdentity;
import net.akehurst.app.requirements.management.computational.requirementsInterface.TopChapter;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo.JdoProject;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo.JdoTopChapter;
import net.akehurst.transform.binary.Relation;
import net.akehurst.transform.binary.TransformException;
import net.akehurst.transform.binary.Transformer;

public class Project2JdoProject implements IBinaryRule<Project, JdoProject> {

	@Override
	public boolean isValidForLeft2Right(final Project left) {
		return true;
	}

	@Override
	public boolean isValidForRight2Left(final JdoProject right) {
		return true;
	}

	@Override
	public JdoProject constructLeft2Right(final Project left, final ITransformer transformer) throws TransformException {
		final JdoProject right = new JdoProject();
		right.setIdentity(left.getIdentity().asPrimitive());
		return right;
	}

	@Override
	public Project constructRight2Left(final JdoProject right, final ITransformer transformer) throws TransformException {
		final Project left = new Project(new ProjectIdentity(right.getIdentity()));
		return left;
	}

	@Override
	public void configureLeft2Right(final Project left, final JdoProject right, final ITransformer transformer) throws TransformException {
		try {
			right.setName(left.getName());
			right.setDescription(left.getDescription());

			final List<JdoTopChapter> jdoChapters = (List<JdoTopChapter>) transformer.transformAllLeft2Right(TopChapter2JdoTopChapter.class, left.getChapter());
			right.setChapters(jdoChapters);
		} catch (final Exception ex) {
			throw new TransformException(ex.getMessage(), ex);
		}
	}

	@Override
	public void configureRight2Left(final Project left, final JdoProject right, final ITransformer transformer) throws TransformException {
		try {
			left.setName(right.getName());
			left.setDescription(right.getDescription());

			final List<TopChapter> chapters = (List<TopChapter>) transformer.transformAllRight2Left(TopChapter2JdoTopChapter.class, right.getChapters());
			left.getChapter().addAll(chapters);
		} catch (final Exception ex) {
			throw new TransformException(ex.getMessage(), ex);
		}
	}

}
