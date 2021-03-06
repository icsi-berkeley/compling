package compling.parser.ecgparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import compling.grammar.ecg.ECGGrammarUtilities;
import compling.grammar.ecg.Grammar;
import compling.grammar.ecg.Grammar.Construction;
import compling.grammar.ecg.GrammarWrapper;
import compling.grammar.unificationgrammar.FeatureStructureUtilities.DefaultStructureFormatter;
import compling.grammar.unificationgrammar.TypeSystem;
import compling.grammar.unificationgrammar.TypeSystemException;
import compling.grammar.unificationgrammar.UnificationGrammar.Constraint;
import compling.gui.AnalyzerPrefs;
import compling.gui.AnalyzerPrefs.AP;
import compling.parser.ParserException;
import compling.parser.UnknownWordException;
import compling.parser.ecgparser.ECGTokenReader.ECGToken;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.AnalysisFactory;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.AnalysisInContextFactory;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.BasicAnalysisFactory;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.CloneTable;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.ConstituentExpansionCostTable;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.DumbConstituentExpansionCostTable;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.ParamFileConstituentExpansionCostTable;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.ParamFileConstituentExpansionCostTableCFG;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.ParamFileConstituentExpansionCostTableFromCounts;
import compling.parser.ecgparser.LeftCornerParserTablesCxn.UnifyTable;
import compling.parser.ecgparser.ECGMorph;
import compling.parser.ecgparser.SemSpecScorer.BasicScorer;
import compling.parser.ecgparser.SemSpecScorer.BasicTableScorer;
import compling.parser.ecgparser.SemSpecScorer.ParamFileScorerFromCounts;
import compling.util.PriorityQueue;
import compling.util.fileutil.ExtensionFileFilter;
import compling.util.fileutil.FileUtils;
import compling.util.fileutil.TextFileLineIterator;
import compling.utterance.Sentence;
import compling.utterance.Utterance;
import compling.utterance.Word;

public class ECGAnalyzer implements compling.parser.Parser<Analysis> {

	private LeftCornerParser<Analysis> parser;

	private int beamSize;
	private int beamWidth;
	private int numAnalysesReturned;
	private double multiRootPenalty;

	private boolean robust;
	private boolean debug;
	private boolean analyzeInContext;
	
	// if this is true, use a built-in method for incrementing beam size
	// according to length of input, etc.
	private boolean variableBeam;

	private String paramsType;
	private boolean useBackoff;
	
	private AnalyzerPrefs preferences;
	
	private MappingReader mappingReader;
	

	private LCPGrammarWrapper grammar;
	
	private ECGMorph ecgmorph;
	
	private ECGTokenReader tokenReader;

	private ConstituentExpansionCostTable cect = null;

	public ECGAnalyzer(Grammar grammar) throws IOException {
		this(grammar, grammar.getPrefs() != null && grammar.getPrefs() instanceof AnalyzerPrefs ? (AnalyzerPrefs) grammar
				.getPrefs() : new AnalyzerPrefs());
	}
	
	public AnalyzerPrefs getPrefs() {
		return preferences;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ECGAnalyzer(Grammar ecgGrammar, AnalyzerPrefs prefs) throws IOException {
		
		
		
		
		preferences = prefs;
		
		//ecgGrammar.buildTokenAndMorpher();

		grammar = new LCPGrammarWrapper(ecgGrammar);
		
		

		
		mappingReader = new MappingReader(grammar);
		//tokenReader = new ECGTokenReader(grammar);
		//ecgmorph = new ECGMorph(grammar, tokenReader);

		if (prefs == null) {
			throw new ParserException("AnalyzerPrefs object expected");
		}

		beamSize = prefs.getSetting(AP.BEAM_SIZE) == null ? 3 : Integer.valueOf(prefs.getSetting(AP.BEAM_SIZE));
		
		beamWidth = prefs.getSetting(AP.BEAM_WIDTH) == null ? 3 : Integer.valueOf(prefs.getSetting(AP.BEAM_WIDTH));

		numAnalysesReturned = prefs.getSetting(AP.NUM_ANALYSES_RETURNED) == null ? 3 : Integer.valueOf(prefs
				.getSetting(AP.NUM_ANALYSES_RETURNED));

		multiRootPenalty = prefs.getSetting(AP.MULTI_ROOT_PENALTY) == null ? -10 : Double.valueOf(prefs
				.getSetting(AP.MULTI_ROOT_PENALTY));

		robust = prefs.getSetting(AP.ROBUST) == null ? false : Boolean.valueOf(prefs.getSetting(AP.ROBUST));

		variableBeam = prefs.getSetting(AP.VARIABLE_BEAM) == null ? false : Boolean.valueOf(prefs.getSetting(AP.VARIABLE_BEAM));

		
		debug = prefs.getSetting(AP.DEBUG) == null ? false : Boolean.valueOf(prefs.getSetting(AP.DEBUG));

		analyzeInContext = prefs.getSetting(AP.ANALYZE_IN_CONTEXT) == null ? false : Boolean.valueOf(prefs
				.getSetting(AP.ANALYZE_IN_CONTEXT));

		AnalysisFactory factory;
		if (analyzeInContext) {
			factory = new AnalysisInContextFactory(grammar, ecgGrammar.getContextModel().getContextModelCache());
		}
		else {
			factory = new BasicAnalysisFactory(grammar);
		}

		if (prefs.getList(AP.GRAMMAR_PARAMS_PATHS).size() > 0) {

			String grammarParamsCxnExt = prefs.getSetting(AP.GRAMMAR_PARAMS_CXN_EXTENSION) == null ? "cxn" : prefs
					.getSetting(AP.GRAMMAR_PARAMS_CXN_EXTENSION);

			List<File> paramFiles = FileUtils.getFilesUnder(prefs.getBaseDirectory(),
					prefs.getList(AP.GRAMMAR_PARAMS_PATHS), new ExtensionFileFilter(grammarParamsCxnExt));

			paramsType = prefs.getSetting(AP.GRAMMAR_PARAMS_TYPE) == null ? "cfg" : prefs
					.getSetting(AP.GRAMMAR_PARAMS_TYPE);
			
			useBackoff = prefs.getSetting(AP.GRAMMAR_PARAMS_USE_CFGBACKOFF) == null ? true : Boolean.valueOf(prefs
					.getSetting(AP.GRAMMAR_PARAMS_USE_CFGBACKOFF));

			if (paramFiles.isEmpty()) {
				cect = new DumbConstituentExpansionCostTable(grammar);
			}
			else {
				if (paramsType.equalsIgnoreCase("normal")) {
					cect = new ParamFileConstituentExpansionCostTable(grammar, paramFiles.get(0).getAbsoluteFile()
							.getAbsolutePath());
				}
				else if (paramsType.equalsIgnoreCase("cfg")) {
					cect = new ParamFileConstituentExpansionCostTableCFG(grammar, paramFiles.get(0).getAbsoluteFile()
							.getAbsolutePath());
				}
				else if (paramsType.equalsIgnoreCase("counts")) {
					cect = new ParamFileConstituentExpansionCostTableFromCounts(grammar, paramFiles.get(0).getAbsoluteFile()
							.getAbsolutePath(), useBackoff,
							new UnifyTable(grammar, new CloneTable<Analysis>(grammar, factory)));
				}
				else { // default
					cect = new ParamFileConstituentExpansionCostTableCFG(grammar, paramFiles.get(0).getAbsoluteFile()
							.getAbsolutePath());
				}
			}

			String grammarParamsSemExt = prefs.getSetting(AP.GRAMMAR_PARAMS_SEM_EXTENSION) == null ? "sem" : prefs
					.getSetting(AP.GRAMMAR_PARAMS_SEM_EXTENSION);

			List<File> semParamFiles = FileUtils.getFilesUnder(prefs.getBaseDirectory(),
					prefs.getList(AP.GRAMMAR_PARAMS_PATHS), new ExtensionFileFilter(grammarParamsSemExt));
			if (!semParamFiles.isEmpty()) {
				BasicTableScorer scorer = new BasicTableScorer(semParamFiles.get(0).getAbsoluteFile().getAbsolutePath(),
						grammar.getSchemaTypeSystem(), ecgGrammar.getOntologyTypeSystem());
				// ParamFileScorerFromCounts scorer = new ParamFileScorerFromCounts(ecgGrammar,
				// semParamFiles.get(0).getAbsoluteFile().getAbsolutePath(), useBackoff);

				Analysis.setSemSpecScorer(scorer);
			}
			
			parser = new LeftCornerParser<Analysis>(ecgGrammar, factory, cect);
		}
		else {
			parser = new LeftCornerParser<Analysis>(ecgGrammar, factory);
		}

		parser.setParameters(robust, debug, beamSize, numAnalysesReturned, multiRootPenalty, beamWidth);
		getLexicon();
		

		



//    needs to be more code here to further process the grammar prefs
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public HashMap<String, String> getMappings() {
		return mappingReader.getMappings();
	}
	
	public ArrayList<String> getLexicon() {
		TypeSystem ts = grammar.getGrammar().getCxnTypeSystem();
		ArrayList<String> lexicon = new ArrayList<String>();
		
		for (String t : this.parser.getMorphInflections().keySet()) {
			lexicon.add(t);
		}
		Map<String, ArrayList<ECGToken>> tokens = this.parser.getTokens();
		for (String t : tokens.keySet()) {
			lexicon.add(t);
		}
		for (Construction cxn : grammar.getAllConcreteLexicalConstructions()) {
			try {
				if (!ts.subtype(ts.getInternedString(cxn.getName()), ts.getInternedString("GeneralTypeCxn"))) {
					for (Constraint c : cxn.getFormBlock().getConstraints()) {
						//System.out.println(c.getArguments().get(index));
						if (c.getArguments().get(0).toString().equals("self.f.orth")) {
							lexicon.add(c.getValue().replace("\"", ""));
						}
					}
				}
			} catch (TypeSystemException e) {
				// TODO Bad thing
				e.printStackTrace();
			}
		}
		
		//return this.parser.getTokens();
		return lexicon;
	}
	
	public ArrayList<String> getUtterances() {
		UtteranceGenerator generator = new UtteranceGenerator(grammar);
		return generator.generateUtterances("Utterance");
	}
	
	public ArrayList<String> getUtterances(String type) {
		UtteranceGenerator generator = new UtteranceGenerator(grammar);
		return generator.generateUtterances(type);
	}
	
	
	/** Reads token file into Parser again. Needs to be done when you want new tokens in lexicon. */
	public void reloadTokens() {
		try {
			this.parser.reloadTokens();
		} catch(IOException problem) {
			System.out.println("Error reloading tokens.");
		}
	}

	public ECGAnalyzer(String prefsFileName) throws Exception {
		this(ECGGrammarUtilities.read(prefsFileName));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadNewGrammar(Grammar ecgGrammar, StringBuffer grammarParamsCxn, StringBuffer grammarParamsSem) throws IOException {
		grammar = new LCPGrammarWrapper(ecgGrammar);
		AnalysisFactory factory;
		if (analyzeInContext) {
			factory = new AnalysisInContextFactory(grammar, grammar.getContextModel().getContextModelCache());
		}
		else {
			factory = new BasicAnalysisFactory(grammar);
		}

		if (grammarParamsCxn != null && grammarParamsCxn.length() > 0) {
			if (paramsType.equalsIgnoreCase("normal")) {
				cect = new ParamFileConstituentExpansionCostTable(grammar, new TextFileLineIterator(grammarParamsCxn));
			}
			else if (paramsType.equalsIgnoreCase("cfg")) {
				cect = new ParamFileConstituentExpansionCostTableCFG(grammar, new TextFileLineIterator(grammarParamsCxn));
			}
			else if (paramsType.equalsIgnoreCase("counts")) {
				cect = new ParamFileConstituentExpansionCostTableFromCounts(grammar, new TextFileLineIterator(
						grammarParamsCxn), useBackoff, new UnifyTable(grammar, new CloneTable<Analysis>(grammar, factory)));
			}
			else { // default
				cect = new ParamFileConstituentExpansionCostTableCFG(grammar, new TextFileLineIterator(grammarParamsCxn));
			}
		}
		else {
			cect = new DumbConstituentExpansionCostTable(grammar);
		}

		if (grammarParamsSem != null && grammarParamsSem.length() > 0) {
			ParamFileScorerFromCounts scorer = new ParamFileScorerFromCounts(ecgGrammar, new TextFileLineIterator(
					grammarParamsSem), useBackoff);
			Analysis.setSemSpecScorer(scorer);
		}
		else {
			Analysis.setSemSpecScorer(null);
		}

		parser = new LeftCornerParser<Analysis>(ecgGrammar, factory, cect);
		parser.setParameters(robust, debug, beamSize, numAnalysesReturned, multiRootPenalty, beamSize);
	}

	public Grammar getGrammar() {
		return grammar.grammar;
	}
	
	public GrammarWrapper getGrammarWrapper() {
		return grammar;
	}
	
	private PriorityQueue<Analysis> getParsesForUtterance(Utterance<Word, String> utterance) {
		PriorityQueue<List<Analysis>> pqa = parser.getBestPartialParses(utterance);
		PriorityQueue<Analysis> parses = new PriorityQueue<Analysis>();
		while (pqa.size() > 0) {
			double priority = pqa.getPriority();
			List<Analysis> al = pqa.next();
			if (al.size() > 1) {
				throw new ParserException("shouldn't have more than one root.");
			}
			Analysis a = al.get(0);

			// Associate the actual text of the utterance parsed with the analysis. Used for multiword token stuff.
			String txt = "";
			for (Word word : utterance.getElements()) {
				txt = txt + " " + word.getOrthography();
			}

			a.setText(txt);
			parses.add(a, priority);
		}
		return parses;
	}

	// will probably separate this method into non-variable and variable versions eventually
	public PriorityQueue<Analysis> getBestParses(Utterance<Word, String> utterance) {
	
		
		if (this.variableBeam) {
			//System.out.println("Utterance length is " + utterance.size());
			int maxBeam = utterance.size() * 10; // make this a function of utterance length
			// maybe utterance.size() * 10?
			int index = 5;
			while (index <= maxBeam) {
				//System.out.println(index);

				try {
					parser.setBeamWidth(index);
					PriorityQueue<Analysis> parses = getParsesForUtterance(utterance);
					return parses;
				} catch (ParserException e) {
					if (e.isUnknown()) {
						//System.out.println("Beam size on");
						throw new ParserException(e.getMessage());
					}
					index = index * 2;	
				}
			}
			throw new ParserException("No complete analysis for: '" + utterance.toString() + "'.");
		} else {
			return getParsesForUtterance(utterance);
		}

		
		 
		
	}

	public Analysis getBestParse(Utterance<Word, String> utterance) {
		return parser.getBestParse(utterance);
	}

	public PriorityQueue<List<Analysis>> getBestPartialParses(Utterance<Word, String> utterance) {
		return parser.getBestPartialParses(utterance);
	}

	public List<Analysis> getBestPartialParse(Utterance<Word, String> utterance) {
		return parser.getBestPartialParse(utterance);
	}

	public int getNumberOfStatesCreatedForLastUtterance() {
		return parser.getNumberOfStatesCreatedForLastUtterance();
	}

	public int getNumberOfStatesProcessedForLastUtterance() {
		return parser.getNumberOfStatesProcessedForLastUtterance();
	}

	public long getConstructorTime() {
		return parser.getConstructorTime();
	}

	public int getLargestAssignedSlotID() {
		return parser.getLargestAssignedSlotID();
	}

	public ConstituentExpansionCostTable getConstituentExpansionCostTable() {
		return cect;
	}

	public BasicScorer getSemSpecScorer() {
		return Analysis.getSemSpecScorer();
	}

	public boolean robust() {
		return robust;
	}

	public boolean debug() {
		return debug;
	}

	public String getParserLog() {
		return parser.getParserLog();
	}

	public static void main(String[] args) throws Exception {
		System.out.print("Initialiazing analyzer ...");
		ECGAnalyzer analyzer = new ECGAnalyzer(args[0]);
		System.out.println(" done.");
		
		
		
		System.out.print("Reading tokens ...");
		ECGTokenReader tokens = new ECGTokenReader(analyzer.getGrammar());
		System.out.println(" done.");
		
		System.out.print("Reading Morphology Dictionary ...");
		ECGMorph morph = new ECGMorph(analyzer.getGrammar(), tokens);
		System.out.println(" done.");

		TextFileLineIterator tfli = new TextFileLineIterator(args[1]);
		while (tfli.hasNext()) {
			String line = tfli.next();

			System.out.println("\n\n//////////////////////////////////////////////////////////////////////////////////");
			System.out.println("Analyzing Sentence: " + line);
			System.out.println("//////////////////////////////////////////////////////////////////////////////////");
			StringTokenizer st = new StringTokenizer(line);
			List<String> words = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				words.add(st.nextToken());
			}
			if (analyzer.robust()) {
				try {
					PriorityQueue<List<Analysis>> pqa = analyzer.getBestPartialParses(new Sentence(words, null, 0));

					while (pqa.size() > 0) {
						System.out.println("\n\nRETURNED ANALYSIS\n____________________________\n");
						System.out.println("Cost: " + pqa.getPriority());
						for (Analysis a : pqa.next()) {
							System.out.println(a);
							// System.out.println(a.getFeatureStructure());
						}
					}
					System.out.println(analyzer.getParserLog());
				}
				catch (NoECGAnalysisFoundException neafe) {
					System.out.println("\n\nEXCEPTIONALLY BAD ANALYSIS\n____________________________\n");
					for (Analysis a : neafe.getAnalyses()) {
						System.out.println(a);
					}
					System.out.println(analyzer.getParserLog());
				}
				catch (ParserException pe) {
					System.err.println(pe.getLocalizedMessage());
					pe.printStackTrace();
					System.out.println(analyzer.getParserLog());
				}
			}
			else {
				try {
					PriorityQueue<Analysis> pqa = analyzer.getBestParses(new Sentence(words, null, 0));
					while (pqa.size() > 0) {
						System.out.println("\n\nRETURNED ANALYSIS\n____________________________\n");
						System.out.println("Cost: " + pqa.getPriority());
						Analysis a = pqa.next();
						System.out.println(a);
						// TexFeatureStructureFormatter tf = new TexFeatureStructureFormatter();
						// System.out.println(tf.format(a.getFeatureStructure()));
						DefaultStructureFormatter df = new DefaultStructureFormatter();
						System.out.println(df.format(a.getFeatureStructure()));
						// AnalysisUtilities.FlatAnalysisFormatter ff = new
						// AnalysisUtilities.FlatAnalysisFormatter(analyzer.getGrammar(), line);
						// System.out.println(ff.format(a));
					}
					System.out.println(analyzer.getParserLog());
				}
				catch (Exception pe) {
					pe.printStackTrace();
					System.out.println(analyzer.getParserLog());
				}
			}
		}

		/*
		 * List<String> words = new ArrayList<String>(); for (int i = 1; i < args.length; i++){words.add(args[i]);}
		 * 
		 * if (analyzer.robust()){ try { PriorityQueue<List<Analysis>> pqa = analyzer.getBestPartialParses(new
		 * Sentence(words, null, 0));
		 * 
		 * while (pqa.size() > 0){ System.out.println("\n\nRETURNED ANALYSIS\n____________________________\n");
		 * System.out.println("Cost: "+pqa.getPriority()); for (Analysis a: pqa.next()){ System.out.println(a);
		 * //System.out.println(a.getFeatureStructure()); } } System.out.println(analyzer.getParserLog()); } catch
		 * (NoECGAnalysisFoundException neafe){
		 * System.out.println("\n\nEXCEPTIONALLY BAD ANALYSIS\n____________________________\n"); for (Analysis a :
		 * neafe.getAnalyses()){ System.out.println(a); } System.out.println(analyzer.getParserLog()); } catch
		 * (ParserException pe) { System.err.println(pe.getLocalizedMessage()); pe.printStackTrace();
		 * System.out.println(analyzer.getParserLog()); } } else { try { PriorityQueue<Analysis> pqa =
		 * analyzer.getBestParses(new Sentence(words, null, 0)); while (pqa.size() > 0){
		 * System.out.println("\n\nRETURNED ANALYSIS\n____________________________\n");
		 * System.out.println("Cost: "+pqa.getPriority()); System.out.println(pqa.next()); }
		 * System.out.println(analyzer.getParserLog()); } catch (Exception pe) { pe.printStackTrace();
		 * System.out.println(analyzer.getParserLog()); } } System.out.println(analyzer.getParserLog());
		 */
	}

}
