
public class CPU {
	
	public String execute( MipsInstructions instruction ) {
		if (instruction.type == 'R') {
			if (instruction.funct == 0){//add
				int answer = REG.get(instruction.ra) + REG.get(instruction.rb);
				REG.set(instruction.rd, answer);
				
				
			}
			else if (instruction.funct == 1) {//sub
				int answer = REG.get(instruction.ra) - REG.get(instruction.rb);
				REG.set(instruction.rd, answer);
			}
			else if (instruction.funct == 2) {
				
			}
		
		}
		else if(instruction.type == 'I') {
			if (instruction.opcode == 0) {//addi
				int answer = REG.get(instruction.ra) + instruction.imm;
				REG.set(instruction.rd, answer);
			}
			
			
			
		}
		else if(instruction.type == 'J') {
			
			
		}
		else {
			
		}
		
		
		return "";
	}
	
	
	public String toBinary( MipsInstructions instruction ) {
		String output;
		if (instruction.type == "I") {
			output
		}
		
		return "";
	}
}
