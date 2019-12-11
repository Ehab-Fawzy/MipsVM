
public class MipsMemory {
	int size = 1024;
	public int memory [];
	public MipsMemory() {
		memory = new int[size];
	}
	public void write (int index, int value) {
		memory[index] = value;
	}
	public int[] getMemory() {
		return memory;
	}
	public int convertHexToBinary(int index) {
		
		return 0;
	}
}
