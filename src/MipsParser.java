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
                
        if (checkop(split))
        {		
        	if (checkArg(split))
        	{	
        		if (checkReg(split))
        		{	
        			if(map.get(split[0]).equals("R"))
        			{	
        		         ret = new MipsInstructions ('R' ,0 ,0, 0,func.get(split[0])) ;

        				
        			}
        			else if (map.get(split[0]).equals("I"))
        			{   
        			    ret = new MipsInstructions (opcode.get(split[0]),'I',0,0,0) ;
        			}
        		}
        		else
        			return null ;
        	}
        	else
        		return null;
        }
        else
        	return null ;
	    
		
		
		return ret;
	}
	
	
	public int getCode( String code ) {
		int ret = 0;
		
		return ret;
	}
	public static void initialize()
	{
	    map.put("add", "R");
	    map.put("or", "R");
	    map.put("sub", "R");
	    map.put("and", "R");
	    map.put("sll", "R");
	    map.put("slt", "R");

	    map.put("addi","I");
	    map.put("lw", "I");
	    map.put("sw", "I");
	    map.put("andi", "I");
	    map.put("ori", "I");
	    map.put("slti", "I");
	    map.put("lui", "I");
	    
	    map.put("jr", "J");
	    map.put("j", "J");
	    
	    map.put("beq", "value1");
	    map.put("bne", "value2");
	}
	public static boolean checkop (String [] split)
	{
		if (map.get(split[0]) == null)
		{	
			return false ;
		}
		else 
		{
		   if (split[0].equals("lw") || split[0].equals("sw") || split[0].equals("lui"))	
		   {   
			 if (! (split.length ==3))  
				return false ;	 
		   }
		   else
		   {	   
		       if (!(GetNumberOfArguments(map.get(split[0])) == split.length-1))
		       {	
		         return false ;
		       }
		   }
		 }
		return true ;
	}
	public static boolean checkArg (String [] split)
	{
		for (int i = 1 ; i< split.length ;i++ )
		{	
			if (!(Character.toString(split[i].charAt(0))).equals("$"))
			{	
				return false;
			}
		}
		return true ;
	}
	public static boolean checkReg (String [] split)
	{
		if (split[1].equals("$0"))
		{	
			return false ;
		}
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
				return false ;
			}
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
	public static void SetFuncAndOpcode ()
	{
	    func.put("add", 32);
	    func.put("or",  5);
	    func.put("sub", 1);
	    func.put("and", 4);
	    func.put("sll", 0);
	    func.put("slt", 42);

	    opcode.put("addi", 8);
	    opcode.put("lw", 35);
	    opcode.put("sw", 43);
	    opcode.put("andi", 12);
	    opcode.put("ori", 13);
	    opcode.put("slti", 10);
	    opcode.put("lui", 15);
	    opcode.put("beq", 4);
	    

	}
	
}
