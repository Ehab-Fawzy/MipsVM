
public class MipsMemory {
	
	String base ;
	public int memory [];
	public MipsMemory(int size, String base) {
		memory = new int[size];
		this.base = new String(base);
		for ( int idx = 0; idx < size; ++idx ) {
			memory[idx] = 0;
		}
	}
	
	public void clear() {
		for ( int i = 0; i < memory.length; ++i ) {
			memory[i] = 0;
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
	public int getValue (Integer index) {
		return memory[index];
	}
}
