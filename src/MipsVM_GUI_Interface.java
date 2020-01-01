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
	
	//public static Vector<MipsInstructions> instructionSet = null;
	public static HashMap<Integer, MipsInstructions> instructionSet = null;
	
	public static String registerBase = "decimal", dataSegmentBase = "decimal";
	
	
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
			instructionSet = new HashMap<Integer, MipsInstructions>();
		} else {
			instructionSet.clear();
		}
	
		updateMemory();
		updateRegisterFile();
		MipsVM_GUI.scrollUP();
		
		cutInstructions(); pc = 0; labelCnt = 0;
	}
	
	public static void clear() {
		MEM.clear(); REG.clear();
		updateMemory();
		updateRegisterFile();
	}
	
	public static void updateRegisterFile() {		
		MipsVM_GUI.regName.setText("");
		MipsVM_GUI.regValue.setText("");
		
		if 		( registerBase.compareTo("binary") == 0 ) {
			for ( int i = 0; i < 32; ++i ) {
				MipsVM_GUI.regName.append( getRegisterName(i) + "\n" );
				MipsVM_GUI.regValue.append( Integer.toBinaryString( REG.getData(i) ) + "\n" );
			}
		}
		else if ( registerBase.compareTo("decimal")  == 0 ) {
			for ( int i = 0; i < 32; ++i ) {
				MipsVM_GUI.regName.append( getRegisterName(i) + "\n" );
				MipsVM_GUI.regValue.append( REG.getData(i) + "\n" );
			}
		}
		else if ( registerBase.compareTo("hex") == 0 ) {
			for ( int i = 0; i < 32; ++i ) {
				MipsVM_GUI.regName.append( getRegisterName(i) + "\n" );
				MipsVM_GUI.regValue.append( "0x" + Integer.toHexString( REG.getData(i) ) + "\n" );
			}
		}
		MipsVM_GUI.scrollUP();
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
			if 		( dataSegmentBase.compareTo("decimal") == 0 ) {
				MipsVM_GUI.DataSegmentValues.append( "" + MEM.getValue(itr) + "\n" );
			}
			else if ( dataSegmentBase.compareTo("binary") == 0 ) {
				MipsVM_GUI.DataSegmentValues.append( "" + Integer.toBinaryString( MEM.getValue(itr) ) + "\n" );
			}
			else if ( dataSegmentBase.compareTo("hex") == 0 ) {
				MipsVM_GUI.DataSegmentValues.append( "" + "0x" + Integer.toHexString( MEM.getValue(itr) ) + "\n" );
			}
		}
		MipsVM_GUI.scrollUP();
	}
	
	public static boolean parseAll() {
		cutInstructions();
		for ( int i = 0; i < instructionList.size(); ++i ) {
			if ( isLabel( instructionList.get(i) ) ) {
				continue;
			}
			MipsInstructions object = parser.parse( instructionList.get(i) );
			if ( object == null ) {
				reportError( "ERROR in instruction (" + instructionList.get( i ) + ")" );
				return false;
			}
			else {
				instructionSet.put( i , object );
			}
		}
		return true;
	}
	
	public static void runNext() {	
		
		if ( isLabel( instructionList.get(pc/4) ) ) {
			pc += 4;
			if ( pc/4 > instructionSet.size() ) {
				MipsVM_GUI.compile.setEnabled(true);
				MipsVM_GUI.nextStep.setEnabled(false);
				MipsVM_GUI.runAll.setEnabled(false);
				
				MipsVM_GUI.showMessage( "Program Halt Successfully" , "Program State");
				return;
			}
		}
		
		
		int copyPC = pc / 4;
		
		Character instructionType = MipsParser.getType( instructionList.get(copyPC) ).charAt(0) ;
		MipsVM_GUI.txtAdds.setText( instructionList.elementAt(copyPC) );
		MipsVM_GUI.pcTxt.setText( String.valueOf( "0x" + Integer.toHexString( pc ) ) );
		MipsVM_GUI.typeTxt.setText( instructionType + " - Type" );

		String word = processor.execute( instructionSet.get(copyPC) );
		if 		( instructionType == 'R' ) {
			MipsVM_GUI.writeRtype( word );
		}
		else if ( instructionType == 'I' ) {
			MipsVM_GUI.writeItype( word );
		}
		else if ( instructionType == 'J' ) {
			MipsVM_GUI.writeJtype( word );
		}
		
		updateRegisterFile();
		updateMemory();
		MipsVM_GUI.scrollUP();
		
		if ( pc >= 4*instructionList.size() ) {
			MipsVM_GUI.compile.setEnabled(true);
			MipsVM_GUI.nextStep.setEnabled(false);
			MipsVM_GUI.runAll.setEnabled(false);
			
			MipsVM_GUI.showMessage( "Program Halt Successfully" , "Program State");
		}
	}
	
	public static void runAll() {
		while ( pc < 4*instructionList.size() ) {
			runNext();
		}
	}
	
	public static void cutInstructions() {
		String text = MipsVM_GUI.textSegmentValues.getText();
		pc = 0; labelCnt = 0;
		
		if ( instructionList == null ) {
			instructionList = new Vector<String>();
		}
		
		instructionList.clear();
		String all[] = text.split("\n");
		for ( int programCounter = 0; programCounter < all.length; ++programCounter ) {
			instructionList.add(all[programCounter]);
			if ( isLabel(all[programCounter]) ) {
				compressLabel.put( all[programCounter].substring(0 , all[programCounter].length() - 1) , labelCnt);
				labelToIdx.put( labelCnt , 4*programCounter );
				labelCnt++;
			}
		}
		
	}
	
	private static boolean isLabel( String s ) {
		int colonsCount = 0 , specialChars = 0;
		for ( int i = 0; i < s.length(); ++i ) {
			if ( s.charAt(i) == ':' ) {
				colonsCount++;
			}
			if ( s.charAt(i) != ':' && s.charAt(i) != '_' ) {
				if ( !Character.isAlphabetic( s.charAt(i) ) ) {
					if ( !Character.isDigit( s.charAt(i) ) ) {
						specialChars++;
					}
				}
			}
		}
		
		if ( colonsCount == 1 && s.length() > 1 && s.charAt( s.length() - 1 ) == ':' && !Character.isDigit( s.charAt(0) ) && specialChars == 0 ) {
			return true;
		}
		return false;
	}
	
	public static void reportError( String error ) {
		MipsVM_GUI.showError(error);
	}
	
	public static void changeRegisterBase( String _base ) {
		registerBase = _base; updateRegisterFile();
	}
	
	public static void changeDataSegmentBase( String _base ) {
		dataSegmentBase = _base; updateMemory();
	}
}
