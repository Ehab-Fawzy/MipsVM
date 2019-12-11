
public class CPU {
	
	public String execute( MipsInstructions instruction ) {
		if (instruction.type == 'R') {
			if (instruction.funct == 32){//add
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) + MipsVM_GUI_Interface.REG.getData(instruction.rb);
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.funct == 1) {//sub
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) - MipsVM_GUI_Interface.REG.getData(instruction.rb);
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.funct == 4) {//and
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) & MipsVM_GUI_Interface.REG.getData(instruction.rb);
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.funct == 5) {//or
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) | MipsVM_GUI_Interface.REG.getData(instruction.rb);
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
		
		}
		else if(instruction.type == 'I') {
			if (instruction.opcode == 8) {//addi
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) + instruction.imm;
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.opcode == 12) {//andi
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) & instruction.imm;
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.opcode == 13) {//ori
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) | instruction.imm;
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
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
		/*
		if (instruction.type == "I") {
			output
		}
		*/
		return "";
	}
}
