// ----------------------------------------------------
// The following code was generated by CUP v0.10k TUM Edition 20050516
// Mon Sep 03 00:36:46 PDT 2007
//----------------------------------------------------

package compling.simulator;

/**
 * CUP v0.10k TUM Edition 20050516 generated parser.
 * 
 * @version Mon Sep 03 00:36:46 PDT 2007
 */
public class ScriptSplitter extends java_cup.runtime.lr_parser {

	/** Default constructor. */
	public ScriptSplitter() {
		super();
	}

	/** Constructor which sets the default scanner. */
	public ScriptSplitter(java_cup.runtime.Scanner s) {
		super(s);
	}

	/** Production table. */
	protected static final short _production_table[][] = unpackFromStrings(new String[] { "\000\004\000\002\004\004\000\002\002\004\000\002\004"
			+ "\003\000\002\003\005" });

	/** Access to production table. */
	public short[][] production_table() {
		return _production_table;
	}

	/** Parse-action table. */
	protected static final short[][] _action_table = unpackFromStrings(new String[] { "\000\010\000\004\004\004\001\002\000\004\005\011\001"
			+ "\002\000\006\002\uffff\004\uffff\001\002\000\006\002\010"
			+ "\004\004\001\002\000\006\002\001\004\001\001\002\000"
			+ "\004\002\000\001\002\000\004\006\012\001\002\000\006" + "\002\ufffe\004\ufffe\001\002" });

	/** Access to parse-action table. */
	public short[][] action_table() {
		return _action_table;
	}

	/** <code>reduce_goto</code> table. */
	protected static final short[][] _reduce_table = unpackFromStrings(new String[] { "\000\010\000\006\003\004\004\005\001\001\000\002\001"
			+ "\001\000\002\001\001\000\004\003\006\001\001\000\002"
			+ "\001\001\000\002\001\001\000\002\001\001\000\002\001" + "\001" });

	/** Access to <code>reduce_goto</code> table. */
	public short[][] reduce_table() {
		return _reduce_table;
	}

	/** Instance of action encapsulation class. */
	protected CUP$ScriptSplitter$actions action_obj;

	/** Action encapsulation object initializer. */
	protected void init_actions() {
		action_obj = new CUP$ScriptSplitter$actions(this);
	}

	/** Invoke a user supplied parse action. */
	public java_cup.runtime.Symbol do_action(int act_num, java_cup.runtime.lr_parser parser, java.util.Stack stack,
			int top) throws java.lang.Exception {
		/* call code in generated class */
		return action_obj.CUP$ScriptSplitter$do_action(act_num, parser, stack, top);
	}

	/** Indicates start state. */
	public int start_state() {
		return 0;
	}

	/** Indicates start production. */
	public int start_production() {
		return 1;
	}

	/** <code>EOF</code> Symbol index. */
	public int EOF_sym() {
		return 0;
	}

	/** <code>error</code> Symbol index. */
	public int error_sym() {
		return 1;
	}

	protected Simulator s;

	public void setSimulator(Simulator simulator) {
		s = simulator;
	}

	public void report_error(String message, Object info) {
		super.report_error("Error at line number " + ((ScriptSplitterLexer) getScanner()).getLineNumber() + ":\n"
				+ message, null);
	}

}

/** Cup generated class to encapsulate user supplied action code. */
class CUP$ScriptSplitter$actions {
	private final ScriptSplitter parser;

	/** Constructor */
	CUP$ScriptSplitter$actions(ScriptSplitter parser) {
		this.parser = parser;
	}

	/** Method with the actual generated action code. */
	public final java_cup.runtime.Symbol CUP$ScriptSplitter$do_action(int CUP$ScriptSplitter$act_num,
			java_cup.runtime.lr_parser CUP$ScriptSplitter$parser, java.util.Stack CUP$ScriptSplitter$stack,
			int CUP$ScriptSplitter$top) throws java.lang.Exception {
		/* Symbol object for return from actions */
		java_cup.runtime.Symbol CUP$ScriptSplitter$result;

		/* select the action based on the action number */
		switch (CUP$ScriptSplitter$act_num) {
		/* . . . . . . . . . . . . . . . . . . . . */
		case 3: // script ::= SCRIPT IDENTIFIER CONTENT
		{
			Object RESULT = null;
			int scriptnameleft = ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 1)).left;
			int scriptnameright = ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack
					.elementAt(CUP$ScriptSplitter$top - 1)).right;
			String scriptname = (String) ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack
					.elementAt(CUP$ScriptSplitter$top - 1)).value;
			int contentleft = ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).left;
			int contentright = ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).right;
			String content = (String) ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack
					.elementAt(CUP$ScriptSplitter$top - 0)).value;

			// System.out.println(scriptname + ":\n" + content);
			parser.s.addScript(scriptname, content);

			CUP$ScriptSplitter$result = new java_cup.runtime.Symbol(1/* script */,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 2)).left,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).right, RESULT);
		}
			return CUP$ScriptSplitter$result;

			/* . . . . . . . . . . . . . . . . . . . . */
		case 2: // file ::= script
		{
			Object RESULT = null;

			CUP$ScriptSplitter$result = new java_cup.runtime.Symbol(2/* file */,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).left,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).right, RESULT);
		}
			return CUP$ScriptSplitter$result;

			/* . . . . . . . . . . . . . . . . . . . . */
		case 1: // $START ::= file EOF
		{
			Object RESULT = null;
			int start_valleft = ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 1)).left;
			int start_valright = ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 1)).right;
			Object start_val = (Object) ((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack
					.elementAt(CUP$ScriptSplitter$top - 1)).value;
			RESULT = start_val;
			CUP$ScriptSplitter$result = new java_cup.runtime.Symbol(0/* $START */,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 1)).left,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).right, RESULT);
		}
			/* ACCEPT */
			CUP$ScriptSplitter$parser.done_parsing();
			return CUP$ScriptSplitter$result;

			/* . . . . . . . . . . . . . . . . . . . . */
		case 0: // file ::= file script
		{
			Object RESULT = null;

			CUP$ScriptSplitter$result = new java_cup.runtime.Symbol(2/* file */,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 1)).left,
					((java_cup.runtime.Symbol) CUP$ScriptSplitter$stack.elementAt(CUP$ScriptSplitter$top - 0)).right, RESULT);
		}
			return CUP$ScriptSplitter$result;

			/* . . . . . . */
		default:
			throw new Exception("Invalid action number found in internal parse table");

		}
	}
}