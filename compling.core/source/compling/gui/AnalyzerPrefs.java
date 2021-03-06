// =============================================================================
//File        : AnalyzerPrefs.java
//Author      : emok
//Change Log  : Created on Dec 10, 2007
//=============================================================================

package compling.gui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import compling.grammar.ecg.ECGGrammarUtilities;
import compling.grammar.ecg.Grammar;
import compling.grammar.ecg.Prefs;
import compling.grammar.ecg.Prefs.Property;
import compling.grammar.unificationgrammar.TypeSystemException;
import compling.util.fileutil.TextFileLineIterator;

//=============================================================================

public class AnalyzerPrefs implements Prefs {
	/** Type for ONTOLOGY_TYPE */
	public static final String OWL_TYPE = "OWL";
	
	protected File base = null;
	
	String baseDirectory = null;

	HashMap<Property, List<String>> listsTable = new HashMap<Property, List<String>>();
	HashMap<Property, String> settingsTable = new HashMap<Property, String>();
	Charset encoding;

	public static enum AP implements Property {

		BASE(Datatype.STRING),

		FILE_ENCODING(Datatype.STRING),
		
		MORPHOLOGY_PATH(Datatype.LISTSTRING),
		
		IMPORT_PATHS(Datatype.LISTSTRING),
		
		TABLE_PATH(Datatype.STRING),
		
		PACKAGE_NAME(Datatype.LISTSTRING), 

		TOKEN_PATH(Datatype.LISTSTRING),
		
		MAPPING_PATH(Datatype.STRING),

		GRAMMAR_PATHS(Datatype.LISTSTRING),
		GRAMMAR_EXTENSIONS(Datatype.STRING),

		ONTOLOGY_PATHS(Datatype.LISTSTRING),
		ONTOLOGY_EXTENSIONS(Datatype.STRING),
		ONTOLOGY_TYPE(Datatype.STRING),
		ONTOLOGY_NAMESPACE(Datatype.STRING),

		GRAMMAR_PARAMS_PATHS(Datatype.LISTSTRING),
		GRAMMAR_PARAMS_SEM_EXTENSION(Datatype.STRING),
		GRAMMAR_PARAMS_CXN_EXTENSION(Datatype.STRING),
		GRAMMAR_PARAMS_LOCALITY_EXTENSION(Datatype.STRING),
		GRAMMAR_PARAMS_NGRAM_EXTENSION(Datatype.STRING),
		GRAMMAR_PARAMS_TYPE(Datatype.STRING),
		GRAMMAR_PARAMS_USE_CFGBACKOFF(Datatype.BOOL),

		EXAMPLE_SENTENCES(Datatype.LISTSTRING),
		TEST_SENTENCES(Datatype.LISTSTRING),

		PARSER_SETTINGS(Datatype.STRING),
		
		// if true, use built-in implementation of variable beam size
		// if false, just proceed as normally
		VARIABLE_BEAM(Datatype.BOOL),

		ROBUST(Datatype.BOOL),
		DEBUG(Datatype.BOOL),
		MULTI_ROOT_PENALTY(Datatype.DOUBLE),
		ANALYZE_IN_CONTEXT(Datatype.BOOL),
		BEAM_SIZE(Datatype.INTEGER),
		BEAM_WIDTH(Datatype.INTEGER),
		NUM_ANALYSES_RETURNED(Datatype.INTEGER),
//      ANALYSIS_COSTDIFFERENCE_CUTOFF(Datatype.DOUBLE),

		DEFAULT_OMISSION_PROB(Datatype.DOUBLE),
		DEFAULT_NONLOCAL_PROB(Datatype.DOUBLE),

		RD_NAME(Datatype.STRING),
		RESOLVED_REFERENT_ROLE_NAME(Datatype.STRING),
		ONTOLOGICAL_CATEGORY_ROLE_NAME(Datatype.STRING),

		SKIP(Datatype.BOOL);

		private Datatype type = null;

		private AP(Datatype type) {
			this.type = type;
		}

		public Datatype getDataType() {
			return type;
		}
	}

	public AnalyzerPrefs() {
		this(Charset.defaultCharset());
	}

	public AnalyzerPrefs(Charset encoding) {
		this.encoding = encoding;

		listsTable.put(AP.GRAMMAR_PATHS, new ArrayList<String>());
		listsTable.put(AP.IMPORT_PATHS, new ArrayList<String>());
		listsTable.put(AP.ONTOLOGY_PATHS, new ArrayList<String>());
		listsTable.put(AP.GRAMMAR_PARAMS_PATHS, new ArrayList<String>());
		listsTable.put(AP.EXAMPLE_SENTENCES, new ArrayList<String>());
		listsTable.put(AP.TOKEN_PATH, new ArrayList<String>());
		listsTable.put(AP.MORPHOLOGY_PATH, new ArrayList<String>());
		listsTable.put(AP.PACKAGE_NAME, new ArrayList<String>());
//		listsTable.put(AP.TEST_SENTENCES, new ArrayList<String>());
	}

	public AnalyzerPrefs clone() {
		AnalyzerPrefs ap = new AnalyzerPrefs();
		for (Property p : ap.listsTable.keySet()) {
			ap.listsTable.get(p).addAll(listsTable.get(p));
		}
		ap.settingsTable.putAll(settingsTable);
		ap.encoding = encoding;

		return ap;
	}
	
	public String baseDirectory() {
		return baseDirectory;
	}

	public AnalyzerPrefs(String prefsFilesPath) throws IOException {
		this(prefsFilesPath, Charset.defaultCharset());
	}
	
	public void setBase(String prefsFilesPath) {
		if (settingsTable.get(AP.BASE) != null) {
			base = new File(settingsTable.get(AP.BASE));
		}
		else {
			base = new File(prefsFilesPath).getParentFile();
		}
	}

	public AnalyzerPrefs(String prefsFilesPath, Charset encoding) throws IOException {
		this(encoding);
		
		setBase(prefsFilesPath);

		processFile(prefsFilesPath, false, null);
		
		setBase(prefsFilesPath);
		
	}

	protected void processFile(String prefsFilesPath, boolean included, String includedPath) throws IOException {
		
//		System.out.println("-------------------");
//		System.out.println(prefsFilesPath);
//		System.out.println(includedPath);
		TextFileLineIterator tfli = new TextFileLineIterator(prefsFilesPath, encoding.name());
		// this method is written with extension to the AnalyzerPref class in mind

		//TODO
		//base = new File(prefsFilesPath).getParentFile();
		

		int lineNum = 0;
		AP currentBlock = null;

		while (tfli.hasNext()) {
			lineNum++;
			String line = tfli.next();
			if (line.contains("include")) {
				if (line.split(" ").length <= 1) {
					throw new GUIException("Error with includes statement. No prefs file specified.");
				}
				
				
				String newPath = line.split(" ")[1];
				
				if (includedPath != null) {
					String[] finalSplit = includedPath.split("/");
					String finalPath = "";
					for (int i=0; i<finalSplit.length-1; i+=1) {
						finalPath += finalSplit[i] + "/";
					}
					newPath = finalPath + newPath;
				}
				
				File file = new File(newPath);
				if (file.isAbsolute()) {
					processFile(newPath, true, newPath);
				} else {
					processFile(getBaseDirectory() + "/" + newPath, true, newPath);
				}
			}
			else if (line.indexOf("::==") >= 0) {
				if (currentBlock != null) {
					throw new GUIException("Block " + line.split("::==")[0] + " is beginning in the middle of the block "
							+ currentBlock);
				}
				try {
					currentBlock = AP.valueOf(line.split("::==")[0].trim());
				}
				catch (IllegalArgumentException iae) {
					currentBlock = AP.SKIP;
				}
			}
			else if (line.indexOf(";") >= 0) {
				if (currentBlock == null) {
					throw new GUIException("Unexpected semi-colon encountered in preference file around line " + lineNum);
				}
				currentBlock = null;
			}
			else {
				line = line.replaceAll("\"", "");
				if (line.indexOf("=") >= 0) {
					String[] pair = line.split("=");
					String p1 = pair[1].trim();
					if (included && includedPath != null && pair[0].trim().equals("TABLE_PATH")) {// && pair[0].trim() == AP.TABLE_PATH) {
						String[] s = includedPath.split("/");
						String s2 = "";
						for (int i=0; i<s.length-1; i+=1) {
							s2 += s[i] + "/";
						}
						p1 = s2 + p1;
					}
					AP setting = null;
					try {
						setting = AP.valueOf(pair[0].trim());
						//AP.values
					}
					catch (IllegalArgumentException iae) {
					}
					if (setting != null) {
						//settingsTable.put(setting, pair[1].trim());
						settingsTable.put(setting, p1);
					}
				}
				else {
					if (currentBlock == AP.GRAMMAR_PATHS || currentBlock == AP.ONTOLOGY_PATHS
							|| currentBlock == AP.GRAMMAR_PARAMS_PATHS || currentBlock == AP.EXAMPLE_SENTENCES
							|| currentBlock == AP.IMPORT_PATHS
							|| currentBlock == AP.TOKEN_PATH
							|| currentBlock == AP.MORPHOLOGY_PATH
							|| currentBlock == AP.PACKAGE_NAME) {
						if (included && includedPath != null) {// && false) {
							if (currentBlock != AP.PACKAGE_NAME && currentBlock != AP.EXAMPLE_SENTENCES) {
								/*
								System.out.println("----------------------");
								File prefsFile = new File(prefsFilesPath);
								File prefsBase = new File(base, includedPath);
								System.out.println(prefsFilesPath);
								System.out.println(prefsBase.getParent() + "/" + line);
								System.out.println(includedPath + "/" + line);
								line = prefsBase.getParent() + "/" + line;
								*/
								//System.out.println(includedPath);
								String[] s = includedPath.split("/");
								String s2 = "";
								for (int i=0; i<s.length-1; i+=1) {
									s2 += s[i] + "/";
								}
								
								line = s2 + line;
								
							}
						}
						listsTable.get(currentBlock).add(line);
					}
				}
			}
		}
	}

	public String setSetting(Property property, String value) {
		return settingsTable.put(property, value);
	}

	public String getSetting(Property property) {
		return settingsTable.get(property);
	}

	public List<String> getList(Property property) {
		return listsTable.get(property);
	}

	public File getBaseDirectory() {
		return base;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Analyzer Prefs:\n");
		for (Property setting : listsTable.keySet()) {
			sb.append(setting).append(" = ").append(listsTable.get(setting)).append("\n");
		}

		for (Property setting : settingsTable.keySet()) {
			sb.append(setting).append(" = ").append(settingsTable.get(setting)).append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException, TypeSystemException {
		if (args.length != 1) {
			throw new GUIException("Must be called with the path of the prefs file");
		}
		AnalyzerPrefs preferences = new AnalyzerPrefs(args[0]);
		System.out.println(preferences);
		Grammar grammar = ECGGrammarUtilities.read(preferences);
		System.out.println(grammar);
	}

}
