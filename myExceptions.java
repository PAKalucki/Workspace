
public class myExceptions extends Exception 
{
	private String message = "Błędna struktura pliku XML lub plik uszkodzony/pusty";
	public myExceptions(){}
	
	public String toString()
	{
		return message;
	}
	
	public String getMessage()
	{
		return message;
	}
}
