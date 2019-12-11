import java.util.HashMap;
import java.util.Vector;

public class MipsVM_GUI_Interface {
	public static Register REG = null;
	public static MipsMemory MEM = null;
	public static CPU processor = null;
	public static MipsParser parser = null;
	
	public static Integer pc , labelCnt , memSize;
	public static HashMap<String, Integer> compressLabel = null; 
	public static HashMap<Integer, Integer> labelToIdx = null;
	public static Vector<String> instructionList = null;
	public static Vector<MipsInstructions> instructionSet = null;

	
	public static void init() {
		memSize = 1024;
		
		if ( REG == null ) {
			REG = new Register();
		}
		
		if ( MEM == null ) {
			MEM = new MipsMemory(memSize, "0x00001000");
		}
		
		if ( parser == null ) {
			parser = new MipsParser();	
		}
		
		if ( processor == null ) {
			processor = new CPU();	
		}
	
			
		if ( instructionList == null ) {
			instructionList = new Vector<String>();
		}
		else {
			instructionList.clear();
		}
		
		if ( labelToIdx == null ) {
			labelToIdx = new HashMap<Integer, Integer>();
		} else {
			labelToIdx.clear();
		}
		
		if ( compressLabel == null ) {
			compressLabel = new HashMap<String, Integer>();
		} else {
			compressLabel.clear();
		}
		
		if ( instructionSet == null ) {
			instructionSet = new Vector<MipsInstructions>();
		} else {
			instructionSet.clear();
		}
	
		updateMemory();
		updateRegisterFile();
		
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
			MipsVM_GUI.DataSegmentValues.append( "" + MEM.getValue(itr) + "\n" );
		}
	}
	
	public static boolean parseAll() {
		cutInstructions();
		/*MipsInstructions obj = new MipsInstructions(8, 'I', 0, 16, 5);
		instructionSet.add(obj);*/
		for ( int i = 0; i < instructionList.size(); ++i ) {
			MipsInstructions object = parser.parse( instructionList.get(i) );
			if ( object == null ) {
				reportError( "ERROR in line " + (i+1) );
				return false;
			}
			else {
				instructionSet.add(object);
			}
		}
		return true;
	}
	
	public static void runNext() {	
		MipsVM_GUI.txtAdds.setText( instructionList.elementAt(pc) );
		MipsVM_GUI.pcTxt.setText( String.valueOf(pc) );
		MipsVM_GUI.typeTxt.setText( instructionSet.get(pc).type + " - Type" );
		
		if 		( instructionSet.get(pc).type == 'R' ) {
			MipsVM_GUI.writeRtype();
		}
		else if ( instructionSet.get(pc).type == 'I' ) {
			MipsVM_GUI.writeItype();
		}
		else if ( instructionSet.get(pc).type == 'J' ) {
			MipsVM_GUI.writeJtype();
		}
		
		processor.execute( instructionSet.get(pc) );
		
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
