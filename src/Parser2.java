import java.util.*;
import java.lang.Character ;
public class Parser2 {
	
    public static Map <String, String> map = new HashMap<String, String>();
    public static Map <String, Integer> func = new HashMap<String, Integer>();
    public static Map <String, Integer> opcode = new HashMap<String, Integer>(); 
    
		
	public static MipsInstructions parse (String line)
	{
		
	     initialize() ;
    	  SetFuncAndOpcode() ;
	
	    String function = null ;
        int indexOfSpace = line.indexOf(" ");
        function = line.substring(0,indexOfSpace);
        line = line.substring(indexOfSpace+1);
        line = function+','+line;
        String [] split = line.split(",") ;
        MipsInstructions ret = null ;
         removeSpaces(split);
        
        if (check(split)) 	
        {	
        	if(map.get(split[0]).equals("R"))
			{	 
        		if (split[0].equals("jr")) 
        			ret = new MipsInstructions('R', Register.getNumber( split[1]),0, 0,func.get(split[0]));
        		else if (split[0].equals("sll")) {
        			ret = new MipsInstructions('R', 0, Register.getNumber( split[2]) , Register.getNumber( split[1]), func.get(split[0]));
        			ret.shmant = getImmediate("sll", split[3]);
        			
        		}

			    else
			    	ret = new MipsInstructions ('R' , Register.getNumber( split[2] ) , Register.getNumber( split[3] ) , Register.getNumber( split[1] ) , func.get(split[0])) ;	
			}
			else if (map.get(split[0]).equals("I"))
			{   if (specialCase(split[0]))
			    {
				   String x = "$0";
				   int immidiate = 0;
				   if (split[2].contains("$")) {
					   x = split[2].substring(split[2].indexOf("$"),split[2].length()-1) ; 
					   immidiate = getImmediate(split[0], split[2].substring(0, split[2].indexOf("(")));       						   
				   }
				   else { // num , hex
					   immidiate = getImmediate(split[0], split[2]);
				   }
				   ret= new MipsInstructions(opcode.get(split[0]),'I',Register.getNumber(x),Register.getNumber(split[1]) ,immidiate);
			    }
			    else
			      ret = new MipsInstructions (opcode.get(split[0]),'I', Register.getNumber( split[2] ) , Register.getNumber( split[1] ) , getImmediate(split[0], split[3])  ) ;
				
			}	
			else{
				int labelID = -1;
				if (MipsVM_GUI_Interface.compressLabel.containsKey(split[1]))
					labelID = MipsVM_GUI_Interface.compressLabel.get(split[1]);
				else 
					return null;
				ret = new MipsInstructions(opcode.get(split[0]),'J',labelID);		
			}
        }
        else
          return null ;
        if (ret.imm == -1) {
			return null;
		}
		return ret;
   }

	public static boolean check (String [] split)
	{
		if (map.get(split[0]) == null)
			return false ;
		else if (split[0].equals("add") || split[0].equals("sub") || split[0].equals("or") || split[0].equals("and") || split[0].equals("slt") ){          
			if (split.length != 4)
				   return false ;
			for (int i = 1 ; i< split.length ;i++ )		
		    {
			  if (!(Character.toString(split[i].charAt(0))).equals("$"))
					return false;		
	     	  if (!valid(split[i]))  
                    return false ;  	     		  
	     	  if (split[i].contains("(") || split[i].contains(")"))
	     		  return false ;
	     	  if (NumberOf(split[i],"(") != NumberOf(split[i],")") )
	     		   return false;
	     	  if (isStringInt(split[i]))
	     		  return false ;
		    }	
			return true ;
        }
		else if (split[0].equals("jr")) {
			if (split[0].equals("jr"))
			{	
				if (! Character.toString(split[1].charAt(0)).equals("$"))
					return false ;
			}
			
		}
		else if (split[0].equals("sll")) {
			if (split.length != 4)
				   return false ;
			for (int i = 0; i < split.length-1 ;i++ )
			{	
				  if (!(Character.toString(split[i].charAt(0))).equals("$"))
						return false;		
		     	  if (!valid(split[i]))  
	                    return false ; 
		     	  if (isStringInt(split[i]))
		     		  return false ;
			}
			if (! isStringInt(split[3]))
				return false; 
			return true ;
			
		}
		else if (split[0].equals("lw") || split[0].equals("sw")) {	
			if (split.length != 3)
				return false ;
			if (!(Character.toString(split[1].charAt(0))).equals("$"))
					return false;
			
	     	if (NumberOf(split[2],"(") != NumberOf(split[2],")") )
	     		   return false;
	     	if (!valid(split[1]) || isStringInt(split[0]))  
                  return false ; 
			int indexOfBracket = split[2].indexOf("(");
			if (indexOfBracket == -1) {
				indexOfBracket = split[2].length();
				String imm = split[2].substring(0,indexOfBracket);
			    getImmediate(split[2],imm);
			}
			else {
				String imm = split[2].substring(0,indexOfBracket);
				 getImmediate(split[0],imm);
				if (split[2].charAt(indexOfBracket+1) != '$') {
					MipsVM_GUI_Interface.reportError("Wrong Register");
					return false;
				}
				else {
					boolean isValid = valid(split[2].substring(indexOfBracket+1, split[2].length()-1));
					if (!isValid) {
						MipsVM_GUI_Interface.reportError("Wrong Register");
						return false;
					}
				}
			}
		}
		else if (split[0].equals("addi") || split[0].equals("andi") || split[0].equals("ori") || split[0].equals("slti") ) {
			if (split.length != 4)
			  return false ;	
			for (int i = 1 ; i< split.length-1 ;i++ )		
		    {
			  if (!(Character.toString(split[i].charAt(0))).equals("$"))
					return false;		
	     	  if (!valid(split[i]))  
                    return false ;  	     		  
	     	  if (split[i].contains("(") || split[i].contains(")"))
	     		  return false ;
	     	  if (isStringInt(split[1]))
	     		  return false ;
		    }
			if ( (! isStringInt(split[3]) && getImmediate("addi",split[3]) == -1) )
			      return false ;
	     	  if (NumberOf(split[3],"(") != NumberOf(split[3],")") )
	     		  return false ;	
		}
		else if (split[0].equals("lui")) {
			  if (!(Character.toString(split[1].charAt(0))).equals("$"))
				  return false ;
	     	  if (!valid(split[1]))  
                  return false ;  
	     	  if (split[1].contains("(") || split[1].contains(")"))
	     		  return false ;
	     	  if (isStringInt(split[1]))
	     		  return false ;
			  if (! isStringInt(split[2]) || getImmediate("lui",split[2])==-1 )
				      return false ;
		}
		else if(split[0].equals("beq") || split[0].equals("bne")) {
			   for (int i = 1 ; i< split.length-1 ;i++ ) 
			   {	   
				 if (!(Character.toString(split[i].charAt(0))).equals("$"))	  
					  return false;
				 if (!valid(split[i]))
					 return false ;
				 for(int x = 0 ; x < split[i].length();x++) 
				 {	 
					 if (Character.isSpaceChar(split[i].charAt(x)) && !Character.toString(split[i].charAt(x)).equals("$"))
						 return false ;
				 }
			   }
			   if (Character.toString(split[3].charAt(0)).equals("$"))  
			     return false ;
		}
		else if (split[0].equals("j")) {
			if (split.length > 2) { 
				MipsVM_GUI_Interface.reportError("Invalid arguments");
				return false;}
			
			if (!(Character.toString(split[1].charAt(0))).equals("$"))
				return false;
	     	if (isStringInt(split[1]))
	     		  return false ;
	     	if (!valid(split[1]))  
                  return false ; 
		}

		return true;
	}
	public static void initialize()
	{
	    map.put("add", "R");  //done
	    map.put("or", "R");  // done
	    map.put("sub", "R"); // done
	    map.put("and", "R"); // done
	    map.put("sll", "R"); // done
	    map.put("slt", "R"); // done 
	    map.put("jr", "R");

	    map.put("addi","I"); // done
	    map.put("lw", "I");  // done
	    map.put("sw", "I");  // done
	    map.put("andi", "I");// done
	    map.put("ori", "I"); // done
	    map.put("slti", "I"); // done
	    map.put("lui", "I"); //  
	    
	    map.put("beq", "I");
	    map.put("bne", "I");	    
	    
	    map.put("j", "J");
	}
	public static int GetNumberOfArguments (String type)
	{
		int number =0;
		if (type.equals("R") || type.equals("I"))
			number = 3 ;
		else 
			number = 1 ;
		
		return number ;
	}
	public static boolean valid (String x)
	{
		int index = x.indexOf("$");
		if (Character.toString(x.charAt(index+1)).equals("s") ){
			if (x.contains(")"))
				x = x.replace(")", "");
			
			int num = Integer.parseInt(x.substring(index+2));
			  if ( !((num >= 0) && (num<=7)))	
				  return false ;
		}
		else if (Character.toString(x.charAt(index+1)).equals("t")) {
			if (x.contains(")"))
				x = x.replace(")", "");
			int num = Integer.parseInt(x.substring(index + 2));
			if (!((num >= 0) && (num<=9)))	
				  return false ;
		}
		else if (!x. contains("$0")) {
			return false;
		}
		return true ;
	}
	public static int NumberOf(String x ,String character)
	{
		int counter = 0;
		for(int i = 0;i < x.length();i++)
		{	
		  if (Character.toString(x.charAt(i)).equals(character))	
		  {		  
			  counter++ ;
		  }
		}
		return counter;
			
	}
	public static boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	public static int getImmediate(String operation, String imm) {
		if (operation.equals("bne") || operation.equals("beq")) {
			int labelID = -1;
			if (MipsVM_GUI_Interface.compressLabel.containsKey(imm)){
				labelID = MipsVM_GUI_Interface.compressLabel.get(imm);
			}
			if (labelID == -1)
				MipsVM_GUI_Interface.reportError(imm + " is not a number");
			return labelID; 
		}
		try
	    {
			if (imm.contains("0x")) {
				imm = imm.substring(2);
				return Integer.parseInt(imm, 16);
			}
			else {
				return Integer.parseInt(imm);
			}
	    }
		catch (NumberFormatException ex)
	    {
			 MipsVM_GUI_Interface.reportError(imm + " is not a number");
	    }
		return -1;
	}
	public static void removeSpaces (String [] split)
	{
		for (int i = 0 ; i< split.length ; i++) {
		  split[0] = split[0].replaceAll("\\s","");}
	}
	public static void SetFuncAndOpcode ()
	{
	    func.put("add", 32);
	    func.put("or",  5);
	    func.put("sub", 1);
	    func.put("and", 4);
	    func.put("sll", 0);
	    func.put("slt", 42);
        func.put("jr", 8) ;
        
	    opcode.put("addi", 8);
	    opcode.put("lw", 35);
	    opcode.put("sw", 43);
	    opcode.put("andi", 12);
	    opcode.put("ori", 13);
	    opcode.put("slti", 10);
	    opcode.put("lui", 15);
	    opcode.put("beq", 4);
	    opcode.put("bne", 5);
	    opcode.put("J", 2);
	    opcode.put("j", 2);
	}
	public static boolean specialCase (String type)
	{
		if (type.equals("sw") || type.equals("lw") || type.equals("lui") || type.equals("jr"))
			return true;
		return false;
	}

	
}

