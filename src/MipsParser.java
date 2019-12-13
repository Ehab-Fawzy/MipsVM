
import java.util.*;

public class MipsParser {
	
    public static Map <String, String> map = new HashMap<String, String>();
    public static Map <String, Integer> func = new HashMap<String, Integer>();
    public static Map <String, Integer> opcode = new HashMap<String, Integer>();


	public MipsInstructions parse( String line ) {
		
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
        if (checkop(split) && checkArg(split) && checkReg(split) && check(split))
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

	public int getImmediate(String operation, String imm) {
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

	public static boolean checkop (String [] split)
	{
		if (map.get(split[0]) == null)
		{	
			return false ;
		}
		else 
		{
		   if (specialCase(split[0]))	
		   {   
			 if (split[0].equals("lw") || split[0].equals("lui") || split[0].equals("sw")){	 
			   if ( split.length !=3)  
			 	return false ;	 
			 }  
			 else{ 	 
				 if (split.length != 2)
					 return false ;
			 }
		   }
		   else
		   {	   
		       if (!(GetNumberOfArguments(map.get(split[0])) == split.length-1))
		         return false ;
		   }
		}
		return true ;
	}
	public static boolean checkArg (String [] split)
	{
		if (map.get(split[0]).equals("R"))
		{	
			if (split[0].equals("jr"))
			{	
				if (! Character.toString(split[1].charAt(0)).equals("$"))
					return false ;
			}
			for (int i = 1 ; i< split.length ;i++ ){
				if (!(Character.toString(split[i].charAt(0))).equals("$"))
				{	
					if (!split[0].equals("sll"))
							return false;
				}		
	     	}
	    }    
	   else if (map.get(split[0]).equals("I"))
	   {	  
		   if (specialCase(split[0]))
		   { 
			  if( ! (Character.toString(split[1].charAt(0)).contains("$")) ) 
				  return false ;
			  if (! Character.toString(split[1].charAt(0)).equals("$"))
				  return false ;
		   }
		   else if (split[0].equals("beq") || split[0].equals("bne"))
		   {	   
			   for (int i = 1 ; i< split.length-1 ;i++ ) 
			   {	   
				 if (!(Character.toString(split[i].charAt(0))).equals("$"))	  
						 return false;
			   }
			   if (Character.toString(split[3].charAt(0)).equals("$"))  
			     return false ;
		   }
		   else	   
		   {	   
		      for (int i = 1 ; i< split.length-1 ;i++ ){	   
				  if (!(Character.toString(split[i].charAt(0))).equals("$"))
					 return false;
		      }	   
		      if (split[3].contains("$"))		         
		          return false;
		   }
	    }  
	   else { // J type
		   
	   }
	   return true;	 
    }  
	public boolean checkReg (String [] split)
	{
		if (split[1].equals("$0"))
			return false ;
		//here
		if (map.get(split[0]).equals("R"))
		{	
			for (int i = 1 ; i < split.length ; i++ ){
				if (i == 3 && split[0].equals("sll")) {
					continue;
				}
				if (Character.toString(split[i].charAt(1)).equals("s")){
				  int x = Integer.parseInt(split[i].substring(2));
				  if ( !((x >= 0) && (x<=7)))	 
					  return false ;
				  
				}	
				else if (Character.toString(split[i].charAt(1)).equals("t")){
				  int x = Integer.parseInt(split[i].substring(2));
		
				  if (!((x >= 0) && (x<=9)))	
					  return false ;
				  
				}	
				else {		
					if (!split[i].equals("$0"))
						return false ; 
				}
			}	

		}
		else if (map.get(split[0]).equals("I"))	{	
			if (specialCase(split[0])){	// imm = 4100($0), 0x00001000, 0x00001000($0)
				int indexOfBracket = split[2].indexOf("(");
				if (indexOfBracket == -1) {
					indexOfBracket = split[2].length();
					String imm = split[2].substring(0,indexOfBracket);
					getImmediate(split[0], imm);
				}
				else {
					String imm = split[2].substring(0,indexOfBracket);
					getImmediate(split[0], imm);
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
			else { 
               for (int i = 1 ; i < split.length-1 ; i++ ){	
				  if (Character.toString(split[i].charAt(1)).equals("s")){
				    int x = Integer.parseInt(split[i].substring(2));
				    if ( !((x >= 0) && (x<=7)))	
				    	return false ;
				  }	
				  else if (Character.toString(split[i].charAt(1)).equals("t")){
				    int x = Integer.parseInt(split[i].substring(2));
		
				     if (!((x >= 0) && (x<=9)))	
				    	 return false ;
				     
				  }	
				   else {		
					 if (!split[i].equals("$0")) 		 
					   return false ; 
				   }
			   }				
		   }
		}
		else { // J type	
			if (split.length > 2) { 
				MipsVM_GUI_Interface.reportError("Invalid arguments");
				return false;
			}
		}
	return true ;

	}
	public static int GetNumberOfArguments (String type)
	{
		int number =0;
		if (type.equals("R") || type.equals("I"))
			number = 3 ;
		else //J
			number = 1 ;
		
		return number ;
	}
	public static boolean specialCase (String type)
	{
		if (type.equals("sw") || type.equals("lw") || type.equals("lui") || type.equals("jr"))
			return true;
		return false;
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
	public static void initialize()
	{
	    map.put("add", "R");
	    map.put("or", "R");
	    map.put("sub", "R");
	    map.put("and", "R");
	    map.put("sll", "R");
	    map.put("slt", "R");
	    map.put("jr", "R");

	    map.put("addi","I");
	    map.put("lw", "I");
	    map.put("sw", "I");
	    map.put("andi", "I");
	    map.put("ori", "I");
	    map.put("slti", "I");
	    map.put("lui", "I");
	    map.put("beq", "I");
	    map.put("bne", "I");	    
	    
	    map.put("j", "J");
	    

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
	public static boolean check (String [] split)
	{
		for (int i = 0 ; i<split.length; i++)
		{	
		  if (split[i].contains("$")){
			  if (!valid(split[i]))
				  return false;
		  }

		} 
		if ( map.get(split[0]).equals("R") && !specialCase(split[0]) ){	  
			 for(int i = 0 ; i<split.length;i++)  {	 
				 if (isStringInt(split[i]) && !split[0].equals("sll") )
						 return false;
			 } 
		}
		return true ;
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
	public static String getType (String instruction) {
		String function = null ;
        int indexOfSpace = instruction.indexOf(" ");
        function = instruction.substring(0,indexOfSpace);
		return map.get(function);
	}
	public static void removeSpaces (String [] split)
	{
		for (int i = 0 ; i< split.length ; i++) {
		   split[i] = split[i].replaceAll("\\s","");}
	}

}
