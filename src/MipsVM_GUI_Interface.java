import java.util.HashMap;

public class MipsVM_GUI_Interface {
	public static Register REG;
	public static MipsMemory MEM;
	
	public static Integer pc;
	public static HashMap<String, Integer> labelToIdx;
	
	public MipsVM_GUI_Interface() {
		REG = new Register();
		MEM = new MipsMemory(1024, "0x00001000");
		
		labelToIdx = new HashMap<String, Integer>();
		collectLables(); pc = 0; 
	}
	
	public void updateRegisterFile() {

	}
	
	public void updateMemory() {
		
	}
	
	
	public void collectLables() {
		
	}
	
}
