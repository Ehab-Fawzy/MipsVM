import java.util.ArrayList;

public class Register {
	public ArrayList<Integer> reg ;
	public Register() {
		reg = new ArrayList<Integer>(32);
		reg.set(0, 0); /// $0 
	}
}
