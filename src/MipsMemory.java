
public class MipsMemory {
	String base ;
	public int memory [];
	public static void main(String[] args) {
		MipsMemory memory = new MipsMemory(1024, "0x00001000");
		System.out.println(memory.getOffset("0x00001004"));
		System.out.println(memory.getOffset("0x00001008"));
		System.out.println(memory.getOffset("0x0000100C"));
		System.out.println(memory.getOffset("0x00001010"));
	}
	public MipsMemory(int size, String base) {
		memory = new int[size];
		this.base = new String(base);
	}
	public void write (String index, int value) {
		int offset = getOffset(index);
		memory[offset] = value;
	}
	public int[] getMemory() {
		return memory;
	}
	public int getOffset(String index) {
		String indexNumber = index.substring(2);
		String baseNumber = this.base.substring(2);
		int start = Integer.parseInt(baseNumber,16);
		int decimal = Integer.parseInt(indexNumber,16);
		return (decimal-start)/4 - 1;
	}
}
