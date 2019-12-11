
public class MipsMemory {
	String base ;
	public int memory [];
	public static void main(String[] args) {
		MipsMemory memory = new MipsMemory(1024, "0x00001000");
		System.out.println(Integer.toHexString(4100));
		System.out.println(memory.getOffset("0x00001004"));
		System.out.println(memory.getOffset("0x00001008"));
		System.out.println(memory.getOffset("0x0000100C"));
		System.out.println(memory.getOffset("0x00001010"));
	}
	public MipsMemory(int size, String base) {
		memory = new int[size];
		this.base = new String(base);
		for ( int idx = 0; idx < size; ++idx ) {
			memory[idx] = 0;
		}
	}
	
	public void write (String index, int value) {
		int offset = getOffset(index);
		memory[offset] = value;
	}
	
	public int[] getMemory() {
		return memory;
	}
	
	public int getOffset(String index) {
		String indexNumber = index ,baseNumber = this.base;
		baseNumber = this.base.substring(2);
		if (index.contains("0x")) 
			indexNumber = index.substring(2);
		int start = Integer.parseInt(baseNumber,16);
		int decimal = Integer.parseInt(indexNumber,16);
		
		return (decimal-start)/4;
	}
	
	public int getValue (String hex) {
		int offset = getOffset(hex);
		return memory[offset];
	}
}
