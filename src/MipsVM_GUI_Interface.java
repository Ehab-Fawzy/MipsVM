
public class MipsVM_GUI_Interface {
	public static Register REG;
	public static MipsMemory MEM;
	
	public MipsVM_GUI_Interface() {
		REG = new Register();
		MEM = new MipsMemory(1024, "0x00001000");
	}
	
	public void updateRegisterFile() {
		
	}
	
	public void updateMemory() {
		
	}
	
}
