import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.Math;
import java.math.BigInteger ;
import java.lang.Math; 

public class MipsParser {
	
    public static Map<String, String> map = new HashMap<String, String>();

	public MipsInstructions parse( String line ) {
		
	    String[] split = line.split(",") ;
	    String instruction = split[0];
	    
		MipsInstructions ret = null;
		
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

	    map.put("lui", "value1");
	    map.put("jr", "value2");
	    map.put("j", "value3");
	    map.put("beq", "value1");
	    map.put("bne", "value2");
	}
	public static MipsInstructions checkop (String [] split)
	{
		if (map.get(split[0]) == null)
		{	
			System.out.println( "You entered wrong operand"); // check the printed message
			return null ;
		}
		else {
		   if (!(GetNumberOfArguments(map.get(split[0])) == split.length-1))
		   {	
		     System.out.println(split[0]+ "Take three parameters");	
		     return null ;
		   }
		} 	
		return null ;
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
			
		}
		return number ;
	}
}
