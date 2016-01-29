import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Vector;


public class xmlHandler 
{
	
	
	
	Vector<Vector> getXML(File inputFile)
	{
		Vector<Vector> rowData = new Vector<Vector>();
		
		//String dane ="";
		try 
		{	
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("list");
	         for (int temp = 0; temp < nList.getLength(); temp++) 
	         {
	     		Node nNode = nList.item(temp);		
	     		//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
	     		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	     		{
	     			Element eElement = (Element) nNode;
	     			//vectorAll.
	     			Vector<String> vectorAll = new Vector<String>();
	     			vectorAll.addElement(eElement.getElementsByTagName("subject").item(0).getTextContent() + "\n");
	     			rowData.addElement(vectorAll);
	     			//dane += eElement.getElementsByTagName("subject").item(0).getTextContent() + "\n";
	     		}
	     	  }
	         
	    } 
		catch (Exception e) 
		{
	         e.printStackTrace();
	    }
		return rowData;
	}
	
	Vector<Vector> queryXML(File inputFile, String search)
	{
		//Vector<Vector> rowData = new Vector<Vector>();
		Vector<Vector> rowData = new Vector<Vector>();
		//Vector<String> vectorSearch = new Vector<String>();
		//String dane ="";
		try 
		{	
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("list");
	         for (int temp = 0; temp < nList.getLength(); temp++) 
	         {
	     		Node nNode = nList.item(temp);		
	     		//System.out.println("\nCurrent Element :" + nNode.getNodeName());		
	     		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	     		{
	     			Element eElement = (Element) nNode;
	     			if (eElement.getElementsByTagName("subject").item(0).getTextContent().contains(search))
	     			{
	     				Vector<String> vectorSearch = new Vector<String>();
		     			//rowData.addElement(vectorAll);
	     				vectorSearch.add(eElement.getElementsByTagName("subject").item(0).getTextContent() + "\n");
	     				rowData.addElement(vectorSearch);
	     			}
	     		}
	     	  }
	         
	    } 
		catch (Exception e) 
		{
	         e.printStackTrace();
	    }
		return rowData;
	}
	
	void addRecord()
	{
		//narazie nie robim, dorobic wybor i czytanie z pliku
	}
	
	void modifyXML(){}


public static void main(String[] arghs)
{
	//xmlHandler handler = new xmlHandler();
	//File przyklad = new File("./src/subjects.xml");
	//System.out.println(handler.getXML(przyklad));
	
}
}