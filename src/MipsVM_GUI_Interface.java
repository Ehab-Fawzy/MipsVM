import java.util.HashMap;
import java.util.Vector;

public class MipsVM_GUI_Interface {
	public static Register REG;
	public static MipsMemory MEM;
	public static CPU processor;
	public static MipsParser parser;
	
	public static Integer pc , labelCnt , memSize;
	public static HashMap<String, Integer> compressLabel; 
	public static HashMap<Integer, Integer> labelToIdx;
	public static Vector<String> instructionList = null;
	
	/*public MipsVM_GUI_Interface() {
		REG = new Register();
		MEM = new MipsMemory(1024, "0x00001000");

		labelToIdx = new HashMap<Integer, Integer>();
		compressLabel = new HashMap<String, Integer>();
		cutInstructions(); pc = 0; labelCnt = 0;
	}*/
	
	/*public static void main( String args0[] ) {
		REG = new Register();
		MEM = new MipsMemory(1024, "0x00001000");
		
		pc = 0;
		REG.setData(0, 1); REG.setData(1, 5);
		processor = new CPU();
		MipsInstructions i = new MipsInstructions('R', 2, 0, 1, 2);
		processor.execute(i);
		System.out.println( REG.getData(2) );
		System.out.println( pc );
	}*/
	
	public static void init() {
		memSize = 1024;
		
		REG 		= new Register();
		MEM 		= new MipsMemory(memSize, "0x00001000");
		parser 		= new MipsParser();
		processor 	= new CPU();
		
		instructionList = new Vector<String>();
		labelToIdx = new HashMap<Integer, Integer>();
		compressLabel = new HashMap<String, Integer>();
		cutInstructions(); pc = 0; labelCnt = 0;
	}
	
	public static void updateRegisterFile() {		
		MipsVM_GUI.regName.setText("");
		MipsVM_GUI.regValue.setText("");
		for ( int i = 0; i < 32; ++i ) {
			MipsVM_GUI.regName.append( getRegisterName(i) + "\n" );
			MipsVM_GUI.regValue.append( REG.getData(i) + "\n" );
		}
	}
	
	private static String getRegisterName( Integer registerNumber ) {
		
		if 		( registerNumber == 0 ) return "$0";
		else if ( registerNumber == 1 ) return "$at";
		else if ( registerNumber >= 2 && registerNumber <= 3 ) {
			return "$v" + (registerNumber - 2);
		}
		else if ( registerNumber >= 4 && registerNumber <= 7 ) {
			return "$a" + (registerNumber - 4);
		}
		else if ( registerNumber >= 8 && registerNumber <= 15 ) {
			return "$t" + (registerNumber - 8);
		}
		else if ( registerNumber >= 16 && registerNumber <= 23 ) {
			return "$s" + (registerNumber - 16);
		}
		else if ( registerNumber >= 24 && registerNumber <= 25 ) {
			return "$t" + (registerNumber - 16);
		}
		else if ( registerNumber >= 26 && registerNumber <= 27 ) {
			return "$k" + (registerNumber - 26);
		}
		else if ( registerNumber == 28 ) {
			return "$gp";
		}
		else if ( registerNumber == 29 ) {
			return "$sp";
		}
		else if ( registerNumber == 30 ) {
			return "$fp";
		}
		else if ( registerNumber == 31 ) {
			return "$rp";
		}
		return "";
	}
	
	public static void updateMemory() {
		MipsVM_GUI.DataSegmentValues.setText("");
		for ( int itr = 0; itr < memSize; ++itr ) {
			//MipsVM_GUI.DataSegmentValues.append( MEM.get );
		}
	}
	
	public static void runNext() {
		System.out.println( instructionList.elementAt(pc) ); pc++;
		
		if ( pc >= instructionList.size() ) {
			MipsVM_GUI.compile.setEnabled(true);
			MipsVM_GUI.nextStep.setEnabled(false);
			MipsVM_GUI.runAll.setEnabled(false);
			
			MipsVM_GUI.showMessage( "Program Halt Successfully" , "Program State");
		}
		
		updateRegisterFile();
		updateMemory();
	}
	
	public static void runAll() {
		while ( pc < instructionList.size() ) {
			runNext();
		}
	}
	
	public static void cutInstructions() {
		String text = MipsVM_GUI.codeArea.getText();
		pc = 0; labelCnt = 0;
		
		if ( instructionList == null ) {
			instructionList = new Vector<String>();
		}
		
		instructionList.clear();
		String all[] = text.split("\n");
		for ( int programCounter = 0; programCounter < all.length; ++programCounter ) {
			instructionList.add(all[programCounter]);
			if ( isLabel(all[programCounter]) ) {
				compressLabel.put( all[programCounter] , labelCnt);
				labelToIdx.put( labelCnt , programCounter );
				labelCnt++;
			}
		}
		
	}
	
	private static boolean isLabel( String s ) {
		int colonsCount = 0;
		for ( int i = 0; i < s.length(); ++i ) {
			if ( s.charAt(i) == ':' ) {
				colonsCount++;
			}
		}
		
		if ( colonsCount == 1 && s.length() > 1 && s.charAt( s.length() - 1 ) == ':' ) {
			return true;
		}
		return false;
	}
	
	public static void reportError( String error ) {
		MipsVM_GUI.showError(error);
	}
	
}
