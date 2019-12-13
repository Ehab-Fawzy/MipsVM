import java.util.*;
import java.lang.Character ;
public class Parser2 {
	
    public static Map <String, String> map = new HashMap<String, String>();
    public static Map <String, Integer> func = new HashMap<String, Integer>();
    public static Map <String, Integer> opcode = new HashMap<String, Integer>(); 
    
	public static void main(String[] args) {
		initialize() ;
		
		String line = "sub	 $s2 , $s0 , $s1"  ;
		//SetFuncAndOpcode() ;
		
		String function = null ;
        int indexOfSpace = line.indexOf(" ");
        function = line.substring(0,indexOfSpace);
        line = line.substring(indexOfSpace+1);
        line = function+','+line;
        String [] split = line.split(",") ;
        MipsInstructions ret = null ;  
        
        if (check (split))
        	System.out.println("Success");
        else 
        	System.out.println("failed");
        
        if (split[0].equals("sll"))
        {	
        	
        }

	}
	public static boolean check (String [] split)
	{
		if (map.get(split[0]) == null)
			return false ;
		if (split[0].equals("add") || split[0].equals("sub") || split[0].equals("or") || split[0].equals("and") || split[0].equals("slt") ){          
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
	     	if (!valid(split[1]))  
                  return false ; 
	     	if (isStringInt(split[0]))
	     		  return false ;
			int indexOfBracket = split[2].indexOf("(");
			if (indexOfBracket == -1) {
				indexOfBracket = split[2].length();
				String imm = split[2].substring(0,indexOfBracket);
				int returnedImm = getImmediate(imm);
			}
			else {
				String imm = split[2].substring(0,indexOfBracket);
				int returnedImm = getImmediate(imm);
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
			if ( (! isStringInt(split[3]) && getImmediate(split[3]) == 0) )
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
			  if (! isStringInt(split[2]) || getImmediate(split[2])==0 )
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
		if (index == -1)
			return false ;
		if (Character.toString(x.charAt(index+1)).equals("s") ){
			int num = Integer.parseInt(x.substring(2));
			  if ( !((num >= 0) && (num<=7)))	
				  return false ;
		}
		else if (Character.toString(x.charAt(index+1)).equals("t")) {
			int num = Integer.parseInt(x.substring(2));
			if (!((num >= 0) && (num<=9)))	
				  return false ;
		}
		else
		{	
		   if (!Character.toString(x.charAt(index+1)).equals("0")) {
				return false;
			}
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
	public static int getImmediate(String string) {
		try
	    {
			if (string.contains("0x")) {
				string = string.substring(2);
				return Integer.parseInt(string, 16);
			}
			else {
				return Integer.parseInt(string);
			}
	    }
		catch (NumberFormatException ex)
	    {
			 MipsVM_GUI_Interface.reportError(string + " is not a number");
	    }
		return 0;
	}
	public static void removeSpaces (String [] split)
	{
		for (int i = 0 ; i< split.length ; i++) {
		   split[0].replaceAll("\\s","");}
	}

	

}
