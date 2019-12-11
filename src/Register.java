import java.util.ArrayList;

public class Register {
	public Integer [] reg ;
	public Register() {
		reg = new Integer [32];
		for ( int idx = 0; idx < 32; ++idx ) {
			reg[idx] = 0;
		}
	}
	public int getData(int index) {
		return reg[index];
	}
	public void setData(int index, int element) {
		reg[index] = element;
	}
	
}
