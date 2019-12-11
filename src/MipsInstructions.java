
public class MipsInstructions {
	public Character type;
	public int opcode , ra , rb , rd , shmant , funct , imm , dist;
	
	public MipsInstructions( char _type , int _ra , int _rb , int _rd , int _funct ) {
		/// constructor for R type
		this.opcode = 0; 
		this.shmant = 0;
		this.type = 'R';
		this.ra = _ra; //rs
		this.rb = _rb; //rt
		this.rd = _rd;
		this.funct = _funct;
	}
	
	public MipsInstructions( int _opcode , char _type , int _ra , int _rd , int _imm ) {
		/// constructor for I type
		this.opcode = _opcode; 
		this.type = 'I';
		this.ra = _ra;
		this.rd = _rd;
		this.imm= _imm;
	}

	public MipsInstructions( int _opcode , char _type , int dest ) {
		/// constructor for J type
		this.opcode = _opcode; 
		this.type = 'J';
		this.dist = dest;
	}
}
