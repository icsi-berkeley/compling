package compling.gui.grammargui.builder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import compling.context.ContextModel;
import compling.grammar.GrammarException;
import compling.grammar.ecg.ECGConstants;
import compling.grammar.ecg.Grammar;
import compling.grammar.ecg.GrammarChecker;
import compling.grammar.ecg.ecgreader.BasicGrammarErrorListener;
import compling.grammar.ecg.ecgreader.IErrorListener;
import compling.grammar.ecg.ecgreader.IErrorListener.Severity;
import compling.grammar.ecg.ecgreader.Location;
import compling.grammar.unificationgrammar.TypeSystem;
import compling.grammar.unificationgrammar.TypeSystemException;
import compling.grammar.unificationgrammar.TypeSystemNode;
import compling.gui.AnalyzerPrefs;
import compling.gui.AnalyzerPrefs.AP;
import compling.gui.grammargui.Application;
import compling.gui.grammargui.model.PrefsManager;
import compling.gui.grammargui.util.ISpecificationReader;
import compling.gui.grammargui.util.Log;
import compling.gui.grammargui.util.ResourceGatherer;
import compling.ontology.OWLOntology;

/**
 * <code>GrammarBuilder</code> is used for process grammar files and mark errors
 * in the source files.
 * 
 * @author lucag
 */
public class GrammarBuilder extends IncrementalProjectBuilder {

	/** The default character set. Let's see if it needs to be moved elsewhere. */
	private static final Charset DEFAULT_CHARSET = Charset.forName(ECGConstants.DEFAULT_ENCODING);
	
	class SampleDeltaVisitor implements IResourceDeltaVisitor {
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// handle added resource
				check(resource);
				break;
			case IResourceDelta.REMOVED:
				// TODO: handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				check(resource);
				break;
			}
			// return true to continue visiting children.
			return true;
		}
	}

	class SampleResourceVisitor implements IResourceVisitor {
		public boolean visit(IResource resource) {
			check(resource);
			return true;
		}
	}

	public static final String BUILDER_ID = "compling.gui.grammarBuilder";
	public static final String MARKER_TYPE = "compling.gui.grammarProblem";

	private void addMarker(IResource file, String message, Location location, Severity severity) {
		try {
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message.trim());
			int lineNumber = Math.max(location.getLineNumber(), 1);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttribute(IMarker.LOCATION, String.format("line %d", lineNumber));
			int charStart = location.getStart();
			if (charStart > -1) {
				marker.setAttribute(IMarker.CHAR_START, charStart);
				marker.setAttribute(IMarker.CHAR_END, location.getEnd());
			}
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		}
		catch (CoreException e) {
			Log.logError(e, "addMarker");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		// Ignore non-full builds
		if (kind == FULL_BUILD) {
			fullBuild(PrefsManager.getDefault(), monitor);
		}
		return null;
	}

	void check(IResource resource) {
		if (resource == null)
			throw new IllegalArgumentException("resource cannot be null");

		if (resource instanceof IFile) {
			final IFile file = (IFile) resource;

			if (file.getName().equals(".project"))
				return;

			deleteMarkers(file);

			try {
				ISpecificationReader reader = getParserFor(file);

				if (reader == null)
					return;

				reader.read(file, new IErrorListener() {
					public void notify(String errorMessage, Location location, Severity severity) {
						System.out.println(errorMessage);
						addMarker(file, errorMessage, location, severity);
					}

					@Override
					public void notify(String errorMessage, Severity severity) {
						System.out.println(errorMessage);
						
					}
				});
			}
			catch (Exception e) {
				Log.logError(e, "Problem parsing file '%s'", file.getName());
			}
		}
	}

	void buildGrammar(final IFile file, Grammar grammar) {
		if (file == null)
			throw new IllegalArgumentException("resource cannot be null");

		try {
			deleteMarkers(file);
			new GrammarReader(grammar).read(file, new IErrorListener() {
				public void notify(String message, Location location, Severity severity) {
					addMarker(file, message, location, severity);
				}

				@Override
				public void notify(String errorMessage, Severity severity) {
					System.out.println(errorMessage);
					
				}
			});
		}
		catch (Exception e) {
			Log.logError(e, "Can't parse file %s: exception: %s\n", file.getName(), e);
		}
	}

	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
			//file.getParent().deleteMarkers(type, includeSubtypes, depth);
			//file.getParent().deleteMarkers("compling.gui.grammarProblem", true, 0);
		}
		catch (CoreException e) {
			Log.logError(e, "Can't delete marker for file %s: exception: %s\n", file.getName(), e);
		}
	}

	/**
	 * Lifted from ECGGrammarUtilities.read(...).
	 * 
	 * @param gatherer
	 * @return
	 */
	public ContextModel buildContextModel(ResourceGatherer gatherer) throws CoreException {
		SampleResourceVisitor visitor = new SampleResourceVisitor();
//		for (IResource r : gatherer.getOntologyFiles(getProject())) {
//			r.accept(visitor);
//		}
		boolean onts = true;
		String[] exts = gatherer.getOntologyExtensions().split(" ");
		List<File> files = gatherer.getOntologyFiles();
		if (files.size() == 1) {
			return new ContextModel(files.get(0).getAbsolutePath(), DEFAULT_CHARSET);
		} else if (onts) {
			return new ContextModel(files, exts[2], DEFAULT_CHARSET);
		}
		else {
			return new ContextModel(files, exts[0], exts[1], DEFAULT_CHARSET);
		}
	}

	protected TypeSystem<? extends TypeSystemNode> buildOntologyTypeSystem(PrefsManager manager) 
				throws TypeSystemException, CoreException, IOException {
		return OWLOntology.fromPreferences(manager.getPreferences()).getTypeSystem();
	}
	
	
		

	// Setup grammar with a ContextModel or just an ontology
	protected Grammar prebuildGrammar(PrefsManager manager) {
		Grammar grammar = new Grammar();

		try {
			if (AnalyzerPrefs.OWL_TYPE.equalsIgnoreCase(manager.getPreferences().getSetting(AP.ONTOLOGY_TYPE))) {
				grammar.setOntologyTypeSystem(buildOntologyTypeSystem(manager));
			}
			else {
				ResourceGatherer gatherer = new ResourceGatherer(manager.getPreferences());
				ContextModel contextModel = buildContextModel(gatherer);
				grammar.setContextModel(contextModel);
			}
		}
		catch (Exception e) {
			Log.logError(e, "Impossible to prebuild grammar");
		}

		return grammar;
	}

	protected void partialBuild(final IProgressMonitor monitor) throws CoreException {
		try {
			getProject().accept(new SampleResourceVisitor());
		}
		catch (CoreException e) {
			Log.logError(e, "Problem during partial build");
		}
	}

	/**
	 * Performas a full "build" of the grammar, i.e. all the files specified in
	 * the .prefs file are reparsed.
	 * 
	 * @param manager
	 *           - the model, cannot be null
	 * @param monitor
	 *           - monitors progress; optional (it can be null)
	 * @throws CoreException
	 * @throws IOException
	 */
	protected void fullBuild(PrefsManager manager, final IProgressMonitor monitor) throws CoreException {
		
		
		ResourceGatherer gatherer = new ResourceGatherer(manager.getPreferences());
		List<File> files = gatherer.getImportFiles(); //new ResourceGatherer(manager.getPreferences()).getGrammarFiles();
		
		
		int stepCount = 1 + 1 + files.size();
		monitor.beginTask("Building grammar", stepCount);
		
		Grammar grammar = prebuildGrammar(manager);
		//grammar.addImport(manager.getPreferences().getSetting(AP.PACKAGE_NAME));
		List<String> packageNames = manager.getPreferences().getList(AP.PACKAGE_NAME);
		for (String name : packageNames) {
	    	grammar.addImport(name);
	    	grammar.addToDeclared(name);
	    }
		
		if (gatherer.getImportFiles().size() > 0) {

			ArrayList<Grammar> grammarList = new ArrayList<Grammar>();
			List<List<File>> importList = gatherer.getImportFilesDir();
			List<String> seenPackages = new ArrayList<String>();
			for (List<File> fileList : importList) {
				Grammar tempGrammar = prebuildGrammar(manager);
				for (File f : fileList) {
					IFile grammarFile = (IFile) getProject().findMember(f.getPath());
					buildGrammar(grammarFile, tempGrammar);
					monitor.worked(1);
				}
				grammarList.add(tempGrammar);
			} 
			
			
			for (Grammar g : grammarList) {
				grammar.addRelations(g.getPackageRelations());
				grammar.sortDeclaredPackages();
				for (String request : g.getImport()) {
					grammar.addImport(request);
				}
				for (Grammar.Schema schema : g.getSchemasNoUpdate()) {
					if (grammar.getDeclaredPackages().contains(schema.getPackage())
							&& !seenPackages.contains(schema.getPackage())) {
//					if (grammar.getImport().contains(schema.getPackage()) 
//							&& !seenPackages.contains(schema.getPackage())) {
						Grammar.Schema s = grammar.new Schema(schema.getName(), schema.getKind(), schema.getParents(), schema.getContents());
						s.setLocation(schema.getLocation());
						grammar.addSchema(s);
					}
				}
				for (Grammar.Construction cxn : g.getCxnsNoUpdate()) {
					if (grammar.getDeclaredPackages().contains(cxn.getPackage())
//					if (grammar.getImport().contains(cxn.getPackage())
							&& !seenPackages.contains(cxn.getPackage())) {
						Grammar.Construction c = grammar.new Construction(cxn.getName(), cxn.getKind(), cxn.getParents(), 
								 cxn.getFormBlock(), cxn.getMeaningBlock(), cxn.getConstructionalBlock());
						c.setLocation(cxn.getLocation());
						grammar.addConstruction(c);
					}
				}
				seenPackages.addAll(g.getPackages());
			}
			
		
		}
		
		


		updateLocality(grammar);

		GrammarChecker.setErrorListener(new BasicGrammarErrorListener() {
			public void notify(String message, Location location, Severity severity) {
				IResource file = getProject().findMember(location.getFile());
				if (file != null)
					addMarker(file, message, location, severity);
				else {
					Log.logInfo("Problem: no resource associated with %s; error: %s", location, message);
				}
				super.notify(message, location, severity);
			}
		});
		try {
			grammar.setPrefs(manager.getPreferences());
			grammar.update();
			monitor.worked(1);
		}
		catch (final GrammarException e) {
			final IStatus status = new Status(IStatus.ERROR, Application.PLUGIN_ID, e.getMessage(), e);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					ErrorDialog.openError(null, "Problem Reading Grammar", null, status);
				}
			});
		}
		catch (NullPointerException e) {
			Log.logError(e, "Problem during update");
		}
		finally {
			monitor.done();
		}
		grammar.setPrefs(manager.getPreferences());
		GrammarChecker.setErrorListener(GrammarChecker.DEFAULT_ERROR_LISTENER);
		manager.setGrammar(grammar);
		updateDecorators();
		
		//grammar.buildTokenAndMorpher();

	}

	private void updateDecorators() {
		GrammarFileLabelDecorator decorator = (GrammarFileLabelDecorator) PlatformUI.getWorkbench().getDecoratorManager()
				.getBaseLabelProvider(GrammarFileLabelDecorator.ID);
		if (decorator != null) {
			decorator.update();
		}
	}

	protected void updateLocality(Grammar grammar) {
		// TODO: implement this!
	}

	private ISpecificationReader getParserFor(IFile file) throws CoreException {
		PrefsManager model = PrefsManager.getDefault();
		AnalyzerPrefs prefs = model.getPreferences();
		if (prefs == null) {
			for (IResource m : getProject().members(false))
				if ("prefs".equalsIgnoreCase(m.getFileExtension())) {
					model.setPreferences(m.getRawLocation().toOSString());
				}
		}
		if (prefs != null)
			return model.getParserFor(file);
		else
			return null;
	}

	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new SampleDeltaVisitor());
	}
}
