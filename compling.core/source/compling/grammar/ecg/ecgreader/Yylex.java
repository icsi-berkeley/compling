/* The following code was generated by JFlex 1.4.3 on 7/20/15 11:09 AM */


package compling.grammar.ecg.ecgreader;

import java_cup.runtime.Symbol;
import compling.grammar.ecg.ECGConstants;
import compling.grammar.ecg.ecgreader.Location;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 7/20/15 11:09 AM from the specification file
 * <tt>ECGReader.lex</tt>
 */
public class Yylex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int commentstyle2 = 4;
  public static final int YYINITIAL = 0;
  public static final int commentstyle1 = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\10\0\1\1\1\54\1\5\2\0\1\4\22\0\1\54\1\0\1\3"+
    "\1\24\6\0\1\6\1\0\1\51\1\12\1\15\1\7\1\14\1\17"+
    "\10\16\1\50\1\0\1\22\1\0\1\23\1\0\1\13\1\35\1\43"+
    "\1\25\1\42\1\40\1\46\1\47\1\44\1\34\1\10\1\53\1\36"+
    "\1\45\1\27\1\26\1\37\1\10\1\32\1\30\1\31\1\33\1\52"+
    "\1\10\1\41\2\10\1\20\1\2\1\21\1\0\1\10\1\0\1\35"+
    "\1\43\1\25\1\42\1\40\1\46\1\47\1\44\1\34\1\10\1\53"+
    "\1\36\1\45\1\27\1\26\1\37\1\10\1\32\1\30\1\31\1\33"+
    "\1\52\1\10\1\41\2\10\57\0\1\10\12\0\1\10\4\0\1\10"+
    "\5\0\27\10\1\0\37\10\1\0\u01ca\10\4\0\14\10\16\0\5\10"+
    "\7\0\1\10\1\0\1\10\201\0\5\10\1\0\2\10\2\0\4\10"+
    "\10\0\1\10\1\0\3\10\1\0\1\10\1\0\24\10\1\0\123\10"+
    "\1\0\213\10\10\0\236\10\11\0\46\10\2\0\1\10\7\0\47\10"+
    "\110\0\33\10\5\0\3\10\55\0\53\10\25\0\12\11\4\0\2\10"+
    "\1\0\143\10\1\0\1\10\17\0\2\10\7\0\2\10\12\11\3\10"+
    "\2\0\1\10\20\0\1\10\1\0\36\10\35\0\131\10\13\0\1\10"+
    "\16\0\12\11\41\10\11\0\2\10\4\0\1\10\5\0\26\10\4\0"+
    "\1\10\11\0\1\10\3\0\1\10\27\0\31\10\253\0\66\10\3\0"+
    "\1\10\22\0\1\10\7\0\12\10\4\0\12\11\1\0\7\10\1\0"+
    "\7\10\5\0\10\10\2\0\2\10\2\0\26\10\1\0\7\10\1\0"+
    "\1\10\3\0\4\10\3\0\1\10\20\0\1\10\15\0\2\10\1\0"+
    "\3\10\4\0\12\11\2\10\23\0\6\10\4\0\2\10\2\0\26\10"+
    "\1\0\7\10\1\0\2\10\1\0\2\10\1\0\2\10\37\0\4\10"+
    "\1\0\1\10\7\0\12\11\2\0\3\10\20\0\11\10\1\0\3\10"+
    "\1\0\26\10\1\0\7\10\1\0\2\10\1\0\5\10\3\0\1\10"+
    "\22\0\1\10\17\0\2\10\4\0\12\11\25\0\10\10\2\0\2\10"+
    "\2\0\26\10\1\0\7\10\1\0\2\10\1\0\5\10\3\0\1\10"+
    "\36\0\2\10\1\0\3\10\4\0\12\11\1\0\1\10\21\0\1\10"+
    "\1\0\6\10\3\0\3\10\1\0\4\10\3\0\2\10\1\0\1\10"+
    "\1\0\2\10\3\0\2\10\3\0\3\10\3\0\14\10\26\0\1\10"+
    "\25\0\12\11\25\0\10\10\1\0\3\10\1\0\27\10\1\0\12\10"+
    "\1\0\5\10\3\0\1\10\32\0\2\10\6\0\2\10\4\0\12\11"+
    "\25\0\10\10\1\0\3\10\1\0\27\10\1\0\12\10\1\0\5\10"+
    "\3\0\1\10\40\0\1\10\1\0\2\10\4\0\12\11\1\0\2\10"+
    "\22\0\10\10\1\0\3\10\1\0\51\10\2\0\1\10\20\0\1\10"+
    "\21\0\2\10\4\0\12\11\12\0\6\10\5\0\22\10\3\0\30\10"+
    "\1\0\11\10\1\0\1\10\2\0\7\10\72\0\60\10\1\0\2\10"+
    "\14\0\7\10\11\0\12\11\47\0\2\10\1\0\1\10\2\0\2\10"+
    "\1\0\1\10\2\0\1\10\6\0\4\10\1\0\7\10\1\0\3\10"+
    "\1\0\1\10\1\0\1\10\2\0\2\10\1\0\4\10\1\0\2\10"+
    "\11\0\1\10\2\0\5\10\1\0\1\10\11\0\12\11\2\0\2\10"+
    "\42\0\1\10\37\0\12\11\26\0\10\10\1\0\44\10\33\0\5\10"+
    "\163\0\53\10\24\0\1\10\12\11\6\0\6\10\4\0\4\10\3\0"+
    "\1\10\3\0\2\10\7\0\3\10\4\0\15\10\14\0\1\10\1\0"+
    "\12\11\6\0\46\10\12\0\53\10\1\0\1\10\3\0\u0149\10\1\0"+
    "\4\10\2\0\7\10\1\0\1\10\1\0\4\10\2\0\51\10\1\0"+
    "\4\10\2\0\41\10\1\0\4\10\2\0\7\10\1\0\1\10\1\0"+
    "\4\10\2\0\17\10\1\0\71\10\1\0\4\10\2\0\103\10\45\0"+
    "\20\10\20\0\125\10\14\0\u026c\10\2\0\21\10\1\0\32\10\5\0"+
    "\113\10\25\0\15\10\1\0\4\10\16\0\22\10\16\0\22\10\16\0"+
    "\15\10\1\0\3\10\17\0\64\10\43\0\1\10\4\0\1\10\3\0"+
    "\12\11\46\0\12\11\6\0\130\10\10\0\51\10\1\0\1\10\5\0"+
    "\106\10\12\0\35\10\51\0\12\11\36\10\2\0\5\10\13\0\54\10"+
    "\25\0\7\10\10\0\12\11\46\0\27\10\11\0\65\10\53\0\12\11"+
    "\6\0\12\11\15\0\1\10\135\0\57\10\21\0\7\10\4\0\12\11"+
    "\51\0\36\10\15\0\2\10\12\11\6\0\46\10\32\0\44\10\34\0"+
    "\12\11\3\0\3\10\12\11\44\10\153\0\4\10\1\0\4\10\16\0"+
    "\300\10\100\0\u0116\10\2\0\6\10\2\0\46\10\2\0\6\10\2\0"+
    "\10\10\1\0\1\10\1\0\1\10\1\0\1\10\1\0\37\10\2\0"+
    "\65\10\1\0\7\10\1\0\1\10\3\0\3\10\1\0\7\10\3\0"+
    "\4\10\2\0\6\10\4\0\15\10\5\0\3\10\1\0\7\10\164\0"+
    "\1\10\15\0\1\10\20\0\15\10\145\0\1\10\4\0\1\10\2\0"+
    "\12\10\1\0\1\10\3\0\5\10\6\0\1\10\1\0\1\10\1\0"+
    "\1\10\1\0\4\10\1\0\13\10\2\0\4\10\5\0\5\10\4\0"+
    "\1\10\64\0\2\10\u0a7b\0\57\10\1\0\57\10\1\0\205\10\6\0"+
    "\4\10\21\0\46\10\12\0\66\10\11\0\1\10\20\0\27\10\11\0"+
    "\7\10\1\0\7\10\1\0\7\10\1\0\7\10\1\0\7\10\1\0"+
    "\7\10\1\0\7\10\1\0\7\10\120\0\1\10\u01d5\0\2\10\52\0"+
    "\5\10\5\0\2\10\4\0\126\10\6\0\3\10\1\0\132\10\1\0"+
    "\4\10\5\0\51\10\3\0\136\10\21\0\33\10\65\0\20\10\u0200\0"+
    "\u19b6\10\112\0\u51cc\10\64\0\u048d\10\103\0\56\10\2\0\u010d\10\3\0"+
    "\20\10\12\11\2\10\24\0\57\10\20\0\31\10\10\0\106\10\61\0"+
    "\11\10\2\0\147\10\2\0\4\10\1\0\2\10\16\0\12\10\120\0"+
    "\10\10\1\0\3\10\1\0\4\10\1\0\27\10\35\0\64\10\16\0"+
    "\62\10\34\0\12\11\30\0\6\10\3\0\1\10\4\0\12\11\34\10"+
    "\12\0\27\10\31\0\35\10\7\0\57\10\34\0\1\10\12\11\46\0"+
    "\51\10\27\0\3\10\1\0\10\10\4\0\12\11\6\0\27\10\3\0"+
    "\1\10\5\0\60\10\1\0\1\10\3\0\2\10\2\0\5\10\2\0"+
    "\1\10\1\0\1\10\30\0\3\10\43\0\6\10\2\0\6\10\2\0"+
    "\6\10\11\0\7\10\1\0\7\10\221\0\43\10\15\0\12\11\6\0"+
    "\u2ba4\10\14\0\27\10\4\0\61\10\u2104\0\u012e\10\2\0\76\10\2\0"+
    "\152\10\46\0\7\10\14\0\5\10\5\0\1\10\1\0\12\10\1\0"+
    "\15\10\1\0\5\10\1\0\1\10\1\0\2\10\1\0\2\10\1\0"+
    "\154\10\41\0\u016b\10\22\0\100\10\2\0\66\10\50\0\14\10\164\0"+
    "\5\10\1\0\207\10\23\0\12\11\7\0\32\10\6\0\32\10\13\0"+
    "\131\10\3\0\6\10\2\0\6\10\2\0\6\10\2\0\3\10\43\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\2\3\1\2\1\4\3\2\1\5"+
    "\1\6\1\7\1\2\1\10\14\4\1\11\1\12\1\1"+
    "\2\13\1\1\2\3\1\14\2\0\1\15\1\16\1\17"+
    "\1\0\1\20\1\0\1\5\2\0\2\4\1\21\7\4"+
    "\1\22\12\4\3\0\1\23\1\0\1\15\1\0\1\24"+
    "\1\5\1\25\1\26\16\4\1\27\25\4\1\30\10\4"+
    "\1\31\10\4\1\32\5\4\1\33\3\4\1\34\1\35"+
    "\3\4\1\36\1\37\7\4\1\40\3\4\1\41\1\4"+
    "\1\42\1\43\1\44\3\4\1\45\1\4\1\46\4\4"+
    "\1\47\4\4\1\50\1\4\1\51\1\4\1\52\1\53"+
    "\1\4\1\54";

  private static int [] zzUnpackAction() {
    int [] result = new int[191];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\55\0\132\0\207\0\264\0\341\0\207\0\u010e"+
    "\0\u013b\0\u0168\0\u0195\0\u01c2\0\u01ef\0\207\0\207\0\u021c"+
    "\0\207\0\u0249\0\u0276\0\u02a3\0\u02d0\0\u02fd\0\u032a\0\u0357"+
    "\0\u0384\0\u03b1\0\u03de\0\u040b\0\u0438\0\207\0\207\0\207"+
    "\0\u0465\0\207\0\u0492\0\u04bf\0\u04ec\0\u0519\0\264\0\u0546"+
    "\0\207\0\207\0\207\0\u0573\0\u05a0\0\u01c2\0\u01c2\0\u05cd"+
    "\0\u05fa\0\u0627\0\u0654\0\u013b\0\u0681\0\u06ae\0\u06db\0\u0708"+
    "\0\u0735\0\u0762\0\u078f\0\u013b\0\u07bc\0\u07e9\0\u0816\0\u0843"+
    "\0\u0870\0\u089d\0\u08ca\0\u08f7\0\u0924\0\u0951\0\u04ec\0\u0519"+
    "\0\u097e\0\207\0\u09ab\0\264\0\u09d8\0\u0a05\0\207\0\u0a32"+
    "\0\207\0\u0a5f\0\u0a8c\0\u0ab9\0\u0ae6\0\u0b13\0\u0b40\0\u0b6d"+
    "\0\u0b9a\0\u0bc7\0\u0bf4\0\u0c21\0\u0c4e\0\u0c7b\0\u0ca8\0\u013b"+
    "\0\u0cd5\0\u0d02\0\u0d2f\0\u0d5c\0\u0d89\0\u0db6\0\u0de3\0\u0e10"+
    "\0\u0e3d\0\u0e6a\0\u0e97\0\u0ec4\0\u0ef1\0\u0f1e\0\u0f4b\0\u0f78"+
    "\0\u0fa5\0\u0fd2\0\u0fff\0\u102c\0\u1059\0\u013b\0\u1086\0\u10b3"+
    "\0\u10e0\0\u110d\0\u113a\0\u1167\0\u1194\0\u11c1\0\u013b\0\u11ee"+
    "\0\u121b\0\u1248\0\u1275\0\u12a2\0\u12cf\0\u12fc\0\u1329\0\u013b"+
    "\0\u1356\0\u1383\0\u13b0\0\u13dd\0\u140a\0\u013b\0\u1437\0\u1464"+
    "\0\u1491\0\u013b\0\u013b\0\u14be\0\u14eb\0\u1518\0\u013b\0\u013b"+
    "\0\u1545\0\u1572\0\u159f\0\u15cc\0\u15f9\0\u1626\0\u1653\0\u013b"+
    "\0\u1680\0\u16ad\0\u16da\0\u013b\0\u1707\0\u013b\0\u013b\0\u013b"+
    "\0\u1734\0\u1761\0\u178e\0\u013b\0\u17bb\0\u013b\0\u17e8\0\u1815"+
    "\0\u1842\0\u186f\0\u013b\0\u189c\0\u18c9\0\u18f6\0\u1923\0\u013b"+
    "\0\u1950\0\u013b\0\u197d\0\u19aa\0\u013b\0\u19d7\0\u013b";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[191];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\3\4\1\5\1\6\1\7\1\4\1\10\1\11\2\4"+
    "\1\12\1\13\1\14\1\4\1\15\1\16\1\17\1\20"+
    "\1\4\1\21\1\22\1\23\1\11\1\24\1\11\1\25"+
    "\1\11\1\26\1\27\1\11\1\30\1\31\2\11\1\32"+
    "\1\11\1\33\1\34\1\35\1\36\1\37\2\11\5\40"+
    "\1\41\1\42\47\40\4\43\1\44\1\45\1\46\46\43"+
    "\55\0\2\47\1\50\1\51\2\0\47\47\5\0\1\7"+
    "\55\0\1\52\1\53\55\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\23\11\2\0\2\11\11\0\1\55\14\0"+
    "\23\55\2\0\2\55\16\0\1\56\53\0\1\57\1\0"+
    "\2\57\52\0\1\60\51\0\1\61\52\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\1\11\1\62\21\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\12\11\1\63\6\11\1\64\1\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\1\65\5\11"+
    "\1\66\1\67\3\11\1\70\7\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\1\11\1\71"+
    "\21\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\20\11\1\72\1\11\1\73\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\3\11"+
    "\1\74\12\11\1\75\4\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\10\11\1\76\12\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\14\11\1\77\6\11\2\0\1\100\1\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\13\11\1\101"+
    "\7\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\10\11\1\102\2\11\1\103\7\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\1\11\1\104\11\11\1\105\7\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\13\11\1\106"+
    "\7\11\2\0\2\11\6\0\1\42\47\0\4\43\2\107"+
    "\1\110\46\43\5\107\1\45\1\111\54\107\1\111\46\107"+
    "\4\43\2\107\1\110\1\112\45\43\1\47\1\113\1\50"+
    "\1\114\1\0\1\115\46\47\1\113\10\0\1\116\14\0"+
    "\23\116\2\0\2\116\11\0\3\55\1\0\1\55\1\0"+
    "\2\55\5\0\23\55\2\0\2\55\15\0\1\117\52\0"+
    "\1\120\10\0\1\121\41\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\2\11\1\122\20\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\4\11\1\123"+
    "\16\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\17\11\1\124\3\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\16\11\1\125"+
    "\4\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\4\11\1\126\16\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\20\11\1\127"+
    "\2\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\11\11\1\130\11\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\12\11\1\131"+
    "\10\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\2\11\1\132\20\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\3\11\1\133"+
    "\17\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\1\134\22\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\4\11\1\135\16\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\1\11\1\136\21\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\21\11\1\137\1\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\12\11\1\140\10\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\10\11\1\141\2\11"+
    "\1\142\7\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\5\11\1\143\15\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\10\11"+
    "\1\144\12\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\2\11\1\145\20\11\2\0\2\11"+
    "\1\0\6\107\1\111\1\112\45\107\1\47\1\113\1\50"+
    "\1\51\1\0\1\115\46\47\1\113\1\0\1\115\1\47"+
    "\2\0\1\115\46\0\1\115\10\0\3\116\1\0\1\116"+
    "\1\54\2\116\5\0\23\116\2\0\2\116\24\0\1\121"+
    "\41\0\3\11\1\0\1\11\1\54\2\11\5\0\3\11"+
    "\1\146\17\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\7\11\1\147\13\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\13\11"+
    "\1\150\7\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\1\151\22\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\6\11\1\152"+
    "\14\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\10\11\1\153\12\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\13\11\1\154"+
    "\7\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\1\11\1\155\21\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\1\11\1\156"+
    "\21\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\4\11\1\157\16\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\23\11\2\0"+
    "\1\11\1\160\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\5\11\1\161\15\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\23\11\2\0\1\11"+
    "\1\162\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\1\11\1\163\21\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\2\11\1\164\20\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\4\11\1\165\16\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\20\11\1\166\2\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\4\11\1\167\16\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\13\11\1\170\7\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\4\11\1\171\16\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\1\11\1\172\21\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\20\11\1\173\2\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\10\11\1\174\12\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\10\11\1\175\12\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\2\11\1\176\20\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\3\11\1\177\17\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\5\11\1\200\15\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\5\11\1\201\15\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\5\11\1\202\15\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\10\11\1\203\12\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\10\11\1\204\12\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\13\11\1\205\7\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\5\11\1\206\15\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\7\11\1\207\13\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\3\11\1\210\17\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\6\11\1\211\14\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\5\11\1\212\15\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\5\11\1\213\1\11\1\214\13\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\2\11\1\215"+
    "\20\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\10\11\1\216\12\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\3\11\1\217"+
    "\17\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\4\11\1\220\16\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\4\11\1\221"+
    "\16\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\4\11\1\222\16\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\13\11\1\223"+
    "\7\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\10\11\1\224\12\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\22\11\1\225"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\12\11\1\226\10\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\3\11\1\227\17\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\13\11\1\230\7\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\2\11\1\231\20\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\5\11\1\232\15\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\10\11\1\233\12\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\6\11\1\234\1\11\1\235\12\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\4\11"+
    "\1\236\16\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\10\11\1\237\12\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\13\11"+
    "\1\240\7\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\7\11\1\241\13\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\7\11"+
    "\1\242\13\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\1\243\22\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\13\11\1\244"+
    "\7\11\2\0\2\11\11\0\3\11\1\0\1\11\1\54"+
    "\2\11\5\0\1\11\1\245\21\11\2\0\2\11\11\0"+
    "\3\11\1\0\1\11\1\54\2\11\5\0\22\11\1\246"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\13\11\1\247\7\11\2\0\2\11\11\0\3\11"+
    "\1\0\1\11\1\54\2\11\5\0\11\11\1\250\11\11"+
    "\2\0\2\11\11\0\3\11\1\0\1\11\1\54\2\11"+
    "\5\0\1\251\22\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\7\11\1\252\13\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\6\11\1\253\14\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\11\11\1\254\11\11\2\0"+
    "\2\11\11\0\3\11\1\0\1\11\1\54\2\11\5\0"+
    "\1\11\1\255\21\11\2\0\2\11\11\0\3\11\1\0"+
    "\1\11\1\54\2\11\5\0\1\256\22\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\4\11"+
    "\1\250\16\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\3\11\1\257\17\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\4\11"+
    "\1\260\16\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\2\11\1\261\20\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\13\11"+
    "\1\262\7\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\2\11\1\263\20\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\13\11"+
    "\1\264\7\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\7\11\1\265\13\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\4\11"+
    "\1\266\16\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\2\11\1\267\20\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\15\11"+
    "\1\270\5\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\1\11\1\271\21\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\3\11"+
    "\1\272\17\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\4\11\1\273\16\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\2\11"+
    "\1\274\20\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\3\11\1\275\17\11\2\0\2\11"+
    "\11\0\3\11\1\0\1\11\1\54\2\11\5\0\10\11"+
    "\1\276\12\11\2\0\2\11\11\0\3\11\1\0\1\11"+
    "\1\54\2\11\5\0\11\11\1\277\11\11\2\0\2\11"+
    "\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6660];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\1\1\11\2\1\1\11\6\1\2\11\1\1"+
    "\1\11\14\1\3\11\1\1\1\11\4\1\2\0\3\11"+
    "\1\0\1\1\1\0\1\1\2\0\25\1\3\0\1\11"+
    "\1\0\1\1\1\0\1\1\1\11\1\1\1\11\156\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[191];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	private int lineNum;
	public String file = "unknown file";
	public StringBuffer scannerErrors = new StringBuffer();
	public int lastOpenCommentLine = -1;
	
	public int getLineNumber() { return yyline; }
	public Location getLocation() { 
		return new Location(yytext(), file, getLineNumber(), yychar);
	}
	public String getScannerErrors() { return scannerErrors.toString(); }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Yylex(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Yylex(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1720) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          zzR = false;
          break;
        case '\r':
          yyline++;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
          }
          break;
        default:
          zzR = false;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 2: 
          { scannerErrors.append("ERROR: In file "+file+": Unknown character " + yytext() + " at line " + yyline +"\n");
          }
        case 45: break;
        case 38: 
          { return new Symbol(sym.SEMANTIC);
          }
        case 46: break;
        case 15: 
          { yybegin(commentstyle1);
          }
        case 47: break;
        case 9: 
          { return new Symbol(sym.COLON);
          }
        case 48: break;
        case 41: 
          { return new Symbol(sym.CONSTRAINTS);
          }
        case 49: break;
        case 40: 
          { return new Symbol(sym.EXTRAPOSED);
          }
        case 50: break;
        case 8: 
          { return new Symbol(sym.NEGATE, ECGConstants.NEGATE);
          }
        case 51: break;
        case 37: 
          { return new Symbol(sym.OPTIONAL);
          }
        case 52: break;
        case 13: 
          { return new Symbol(sym.STR, new String(yytext()));
          }
        case 53: break;
        case 27: 
          { return new Symbol(sym.SCHEMA);
          }
        case 54: break;
        case 32: 
          { return new Symbol(sym.SUBCASE);
          }
        case 55: break;
        case 18: 
          { return new Symbol(sym.AS);
          }
        case 56: break;
        case 3: 
          { ++lineNum;
          }
        case 57: break;
        case 36: 
          { return new Symbol(sym.ABSTRACT);
          }
        case 58: break;
        case 11: 
          { ++lineNum; yybegin(YYINITIAL);
          }
        case 59: break;
        case 26: 
          { return new Symbol(sym.MEETS, ECGConstants.MEETS);
          }
        case 60: break;
        case 25: 
          { return new Symbol(sym.ROLES);
          }
        case 61: break;
        case 35: 
          { return new Symbol(sym.FEATURE);
          }
        case 62: break;
        case 42: 
          { return new Symbol(sym.CONSTRUCTION);
          }
        case 63: break;
        case 1: 
          { /* nothing */
          }
        case 64: break;
        case 29: 
          { return new Symbol(sym.IGNORE);
          }
        case 65: break;
        case 10: 
          { return new Symbol(sym.COMMA);
          }
        case 66: break;
        case 12: 
          { scannerErrors.append("ERROR In file "+file+": Unclosed comment starting on line "+lastOpenCommentLine+"\n" );
          }
        case 67: break;
        case 17: 
          { return new Symbol(sym.OF);
          }
        case 68: break;
        case 39: 
          { return new Symbol(sym.SITUATION);
          }
        case 69: break;
        case 7: 
          { return new Symbol(sym.CLOSEBRACKET);
          }
        case 70: break;
        case 21: 
          { return new Symbol(sym.ASSIGN, ECGConstants.ASSIGN);
          }
        case 71: break;
        case 33: 
          { return new Symbol(sym.PACKAGE);
          }
        case 72: break;
        case 34: 
          { return new Symbol(sym.MEANING);
          }
        case 73: break;
        case 24: 
          { return new Symbol(sym.FORM);
          }
        case 74: break;
        case 28: 
          { return new Symbol(sym.IMPORT);
          }
        case 75: break;
        case 16: 
          { return new Symbol(sym.EXTERNALTYPE, new String(yytext()));
          }
        case 76: break;
        case 44: 
          { return new Symbol(sym.CONSTRUCTIONAL);
          }
        case 77: break;
        case 6: 
          { return new Symbol(sym.OPENBRACKET);
          }
        case 78: break;
        case 5: 
          { return new Symbol(sym.PROB, new String(yytext()));
          }
        case 79: break;
        case 30: 
          { return new Symbol(sym.EVOKES);
          }
        case 80: break;
        case 23: 
          { return new Symbol(sym.MAP);
          }
        case 81: break;
        case 14: 
          { yybegin(commentstyle2);
          }
        case 82: break;
        case 31: 
          { return new Symbol(sym.BEFORE, ECGConstants.BEFORE);
          }
        case 83: break;
        case 43: 
          { return new Symbol(sym.CONSTITUENTS);
          }
        case 84: break;
        case 20: 
          { return new Symbol(sym.SLOTCHAIN, new String(yytext()));
          }
        case 85: break;
        case 22: 
          { return new Symbol(sym.IDENTIFY, ECGConstants.IDENTIFY);
          }
        case 86: break;
        case 4: 
          { return new Symbol(sym.IDENTIFIER, 
                                                    1 + lineNum, // left
                                                    yychar, // right
                                                    yytext());
          }
        case 87: break;
        case 19: 
          { yybegin(YYINITIAL);  lastOpenCommentLine = lineNum;
          }
        case 88: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
