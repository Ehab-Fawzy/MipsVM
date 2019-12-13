
public class Register {
	public Integer [] reg ;
	public Register() {
		reg = new Integer [32];
		for ( int idx = 0; idx < 32; ++idx ) {
			reg[idx] = 0;
		}
	}
	
	public void clear() {
		for ( int i = 0; i < 32; ++i ) {
			reg[i] = 0;
		}
	}
	
	public int getData(int index) {
		return reg[index];
	}
	public void setData(int index, int element) {
		reg[index] = element;
	}
	
	public static Integer getNumber( String name ) {
		if		( name.compareTo("$0" )  == 0 ) return 0;
		else if ( name.compareTo("$at")  == 0 ) return 1;
		else if ( name.compareTo("$v0")  == 0 ) return 2;
		else if ( name.compareTo("$v1")  == 0 ) return 3;
		else if ( name.compareTo("$a0")  == 0 ) return 4;
		else if ( name.compareTo("$a1")  == 0 ) return 5;
		else if ( name.compareTo("$a2")  == 0 ) return 6;
		else if ( name.compareTo("$a3")  == 0 ) return 7;
		else if ( name.compareTo("$t0")  == 0 ) return 8;
		else if ( name.compareTo("$t1")  == 0 ) return 9;
		else if ( name.compareTo("$t2")  == 0 ) return 10;
		else if ( name.compareTo("$t3")  == 0 ) return 11;
		else if ( name.compareTo("$t4")  == 0 ) return 12;
		else if ( name.compareTo("$t5")  == 0 ) return 13;
		else if ( name.compareTo("$t6")  == 0 ) return 14;
		else if ( name.compareTo("$t7")  == 0 ) return 15;
		else if ( name.compareTo("$s0")  == 0 ) return 16;
		else if ( name.compareTo("$s1")  == 0 ) return 17;
		else if ( name.compareTo("$s2")  == 0 ) return 18;
		else if ( name.compareTo("$s3")  == 0 ) return 19;
		else if ( name.compareTo("$s4")  == 0 ) return 20;
		else if ( name.compareTo("$s5")  == 0 ) return 21;
		else if ( name.compareTo("$s6")  == 0 ) return 22;
		else if ( name.compareTo("$s7")  == 0 ) return 23;
		else if ( name.compareTo("$t8")  == 0 ) return 24;
		else if ( name.compareTo("$t9")  == 0 ) return 25;
		else if ( name.compareTo("$k0")  == 0 ) return 26;
		else if ( name.compareTo("$k1")  == 0 ) return 27;
		else if ( name.compareTo("$gp")  == 0 ) return 28 ;
		else if ( name.compareTo("$sp")  == 0 ) return 29 ;
		else if ( name.compareTo("$fp")  == 0 ) return 30;
		else if ( name.compareTo("$ra")  == 0 ) return 31 ;
		return 0;
	}
}
