
public class CPU {
	
	public String execute( MipsInstructions instruction ) {
		if (instruction.type == 'R') {
			if (instruction.funct == 0) {//sll
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.rb) << MipsVM_GUI_Interface.REG.getData(instruction.shmant);//rd=rt<<sa
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
			else if (instruction.funct == 32){//add
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) + MipsVM_GUI_Interface.REG.getData(instruction.rb);
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.funct == 42) {//slt
				//$d = ($s < $t)
				boolean answer = (MipsVM_GUI_Interface.REG.getData(instruction.ra) < MipsVM_GUI_Interface.REG.getData(instruction.rb));
				if (answer == true) {
					MipsVM_GUI_Interface.REG.setData(instruction.rd, 1);
				}
				else {
					MipsVM_GUI_Interface.REG.setData(instruction.rd, 0);					
				}
			}
		
		}
		else if(instruction.type == 'I') {
			if (instruction.opcode == 8) {//addi
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) + instruction.imm;
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.opcode == 10) {//slti
				//$t = ($s < SE(i))
				boolean answer = (MipsVM_GUI_Interface.REG.getData(instruction.ra) < instruction.imm);
				if (answer == true) {
					MipsVM_GUI_Interface.REG.setData(instruction.rd, 1);
				}
				else {
					MipsVM_GUI_Interface.REG.setData(instruction.rd, 0);					
				}
			}
			else if (instruction.opcode == 12) {//andi
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) & instruction.imm;
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.opcode == 13) {//ori
				int answer = MipsVM_GUI_Interface.REG.getData(instruction.ra) | instruction.imm;
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.opcode == 15) {//lui
				int answer = instruction.imm << 16; //rt=imm<<16
				MipsVM_GUI_Interface.REG.setData(instruction.rd, answer);
			}
			else if (instruction.opcode == 35) {//lw
				int offset = MipsVM_GUI_Interface.REG.getData(instruction.ra);
				offset += instruction.imm;
				String hexOffset = Integer.toHexString(offset);
				int memoryValue = MipsVM_GUI_Interface.MEM.getOffset(hexOffset);
				MipsVM_GUI_Interface.REG.setData(instruction.rd, memoryValue);
			}
			
		}
		else if(instruction.type == 'J') {
			
			
		}
		else {
			
		}
		
		String machineLanguage = toBinary(instruction);
		return machineLanguage;
	}
	
	
	public String toBinary( MipsInstructions instruction ) {
		String output ="";
		if (instruction.type == 'R') {
			output += convertToBinary(0, 6);//opcode is zero
			output += convertToBinary(instruction.ra, 5);
			output += convertToBinary(instruction.rb, 5);
			output += convertToBinary(instruction.rd, 5);
			output += convertToBinary(instruction.shmant, 5);
			output += convertToBinary(instruction.funct, 5);
		}
		else if (instruction.type == 'I') {
			output += convertToBinary(instruction.opcode, 6);
			output += convertToBinary(instruction.ra, 5);
			output += convertToBinary(instruction.rb, 5);
			output += convertToBinary(instruction.imm, 16);
		}
		else if (instruction.type == 'J') {
			output += convertToBinary(instruction.opcode, 6);
			output += convertToBinary(instruction.dist, 26);
		}
		return output;
	}
	
	public String convertToBinary(int i, int length) {
        String bin = Integer.toBinaryString(i);
        while (bin.length()< length) 
            bin = "0" + bin;
        return bin;
    }
}
