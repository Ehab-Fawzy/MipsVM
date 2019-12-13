import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.Math;
import java.math.BigInteger ;
import java.lang.Math; 

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
                
        if (checkop(split) && checkArg(split) && checkReg(split) && check(split))
        {		
        	if(map.get(split[0]).equals("R"))
			{	 if (split[0].equals("jr")) 
				  ret = new MipsInstructions('R', 0,0, Register.getNumber( split[1]),func.get(split[0]));
			     else
			     ret = new MipsInstructions ('R' , Register.getNumber( split[2] ) , Register.getNumber( split[3] ) , Register.getNumber( split[1] ) , func.get(split[0])) ;	
			}
			else if (map.get(split[0]).equals("I"))
			{   if (specialCase(split[0]))
			    {
				   String x = split[2].substring(split[2].indexOf("$"),split[2].length()-1) ; 
				   int immidiate = Integer.parseInt(split[2].substring(0, split[2].indexOf("(")));       						   
				   ret= new MipsInstructions(opcode.get(split[0]),'I',Register.getNumber(x),Register.getNumber(split[1]) ,immidiate);
			    }
			    else
			      ret = new MipsInstructions (opcode.get(split[0]),'I', Register.getNumber( split[2] ) , Register.getNumber( split[1] ) , Integer.parseInt(split[3] ) ) ;
			}	
			else
			 ret = new MipsInstructions(opcode.get(split[0]),'J',Register.getNumber(split[1]));		
        }
        else
          return ret ;	
		return ret;
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
			 if (split[0].equals("lw") || split[0].equals("lui") || split[0].equals("sw"))  
			 {	 
			   if ( split.length !=3)  
			 	return false ;	 
			 }  
			 else
			 { 	 
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
			for (int i = 1 ; i< split.length ;i++ )		
		    {
				if (!(Character.toString(split[i].charAt(0))).equals("$"))
				{	
					return false;
				}		
	     	}
	    }    
	   else if (map.get(split[0]).equals("I"))
	   {	  
		   if (specialCase(split[0]))
		   { 
			  if( ! (Character.toString(split[1].charAt(0)).contains("$")) || ! split[2].contains("$") ) 
				  return false ;
			  if (! Character.toString(split[1].charAt(0)).equals("$"))
				  return false ;
		   }
		   else if (split[0].equals("beq") || split[0].equals("bne"))
		   {	   
			   for (int i = 1 ; i< split.length-1 ;i++ ) 
			   {	   
				 if (!(Character.toString(split[i].charAt(0))).equals("$"))
				 {	  
						 return false;
				 }	
			   }
			   if (Character.toString(split[3].charAt(0)).equals("$") || isStringInt(Character.toString(split[3].charAt(0)))) 
			     return false ;
		   }
		   else	   
		   {	   
		      for (int i = 1 ; i< split.length-1 ;i++ )
		      {	   
				  if (!(Character.toString(split[i].charAt(0))).equals("$"))
				  {	  
					 return false;
				  }	
		      }	   
		      if (split[3].contains("$"))		         
		          return false;
	      }
	    }  
	   else
	   {  	   
			if (!(Character.toString(split[1].charAt(0))).equals("$"))
			{	
				return false;
			}	
	   }
	   return true;	 
    }  
	public static boolean checkReg (String [] split)
	{
		if (split[1].equals("$0"))
		{	
			return false ;
		}
		if (map.get(split[0]).equals("R"))
		{	
			for (int i = 1 ; i < split.length ; i++ )
			{	
				if (Character.toString(split[i].charAt(1)).equals("s"))
				{
				  int x = Integer.parseInt(split[i].substring(2));
				  if ( !((x >= 0) && (x<=7)))	
				  {		  
					  return false ;
				  }  
				}	
				else if (Character.toString(split[i].charAt(1)).equals("t"))
				{
				  int x = Integer.parseInt(split[i].substring(2));
		
				  if (!((x >= 0) && (x<=9)))	
				  { 			
					  return false ;
				  }
				}	
				else 
				{		
					if (!split[i].equals("$0"))
					{ 		 
					  return false ; 
					}
				}
			}	

		}
		else if (map.get(split[0]).equals("I"))	
		{	
			if (specialCase(split[0]))
			{	
				int index = split[2].indexOf("$");
				if (Character.toString(split[2].charAt(index+1)).equals("s"))
				{	
				    int x = Integer.parseInt(split[2].substring(index+2,split[2].length()-1));
				    if ( !((x >= 0) && (x<=7)))	
				    {		  
					    return false ;
				    } 
				}
				else if (Character.toString(split[2].charAt(index+1)).equals("t"))
				{	
				    int x = Integer.parseInt(split[2].substring(index+2,split[2].length()-1));
					
				     if (!((x >= 0) && (x<=9)))	
				     { 			
					   return false ;
				     }
				}
			}
			else	
			{
               for (int i = 1 ; i < split.length-1 ; i++ )
			   {	
				  if (Character.toString(split[i].charAt(1)).equals("s"))
				  {
				    int x = Integer.parseInt(split[i].substring(2));
				    if ( !((x >= 0) && (x<=7)))	
				    {		  
					    return false ;
				    }  
			      }	
				  else if (Character.toString(split[i].charAt(1)).equals("t"))
				  {
				    int x = Integer.parseInt(split[i].substring(2));
		
				     if (!((x >= 0) && (x<=9)))	
				     { 			
					   return false ;
				     }
				  }	
				   else 
				   {		
					 if (!split[i].equals("$0")) 		 
					   return false ; 
				   }
			   }				
			}
		  }
		else	
		{	
			if (Character.toString(split[1].charAt(1)).equals("s"))
			{
			  int x = Integer.parseInt(split[1].substring(2));
			  if ( !((x >= 0) && (x<=7)))	
			  {		  
				  return false ;
			  }  
			}
			else if (Character.toString(split[1].charAt(1)).equals("t"))
			{
			  int x = Integer.parseInt(split[1].substring(2));
	
			  if (!((x >= 0) && (x<=9)))	
			  { 			
				  return false ;
			  }
			}
			else
				return false ;
		}
			
	return true ;
	}
	public static int GetNumberOfArguments (String type)
	{
		int number =0;
		if (type.equals("R"))
		{	
			number = 3 ;
		}
		else if (type.equals("I"))
		{	
			number = 3 ;
		}
		else 
		{	
			number = 1 ;
		}
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
	    opcode.put("J", 2);
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
	    
	    map.put("jr", "R");
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
		  if (split[i].contains("$"))
		  { 	  
			  if (!valid(split[i]))
				  return false;
		  }

		} 
		if ( map.get(split[0]).equals("R") && !specialCase(split[0]) )
		{	  
			 for(int i = 0 ; i<split.length;i++)  
			 {	 
				 if (isStringInt(split[i]) )
						 return false;
			 } 
		}
		return true ;
	}
	public static boolean valid (String x)
	{
		int index = x.indexOf("$");
		if (Character.toString(x.charAt(index+1)).equals("s") ||Character.toString(x.charAt(index+1)).equals("t") || Character.toString(x.charAt(index+1)).equals("0"))
		  return true ;
		return false ;
	}
	
}
