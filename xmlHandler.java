import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
//import java.util.EmptyStackException;
import java.util.Vector;

public class xmlHandler
{	
	private File inputFile;
	private Document doc;
	
	public xmlHandler()
	{
		inputFile = new File("subjects.xml");
		if (!inputFile.exists())
		{
			inputFile = Gui.chooseInput();
		}
	}
	
	public Vector<Vector> getXML()
	{
		Vector<Vector> rowData = new Vector<Vector>();
		
		//String dane ="";
		try 
		{	
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("list");
	         if(nList.getLength()==0)
	         {
	        	 throw new myExceptions();
	         }
	         for (int temp = 0; temp < nList.getLength(); temp++) 
	         {
	     		Node nNode = nList.item(temp);				
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
			Gui.infoBox("Błąd\n" + e.toString(), "Błąd");
	    }
		return rowData;
	}
	
	public Vector<Vector> queryXML(String search)
	{
		Vector<Vector> rowData = new Vector<Vector>();
		//Vector<String> vectorSearch = new Vector<String>();
		//String dane ="";
		try 
		{	
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         //Document doc = dBuilder.parse(inputFile);
	         //doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("list");
	         if(nList.getLength()==0)
	         {
	        	 throw new myExceptions();
	         }
	         for (int temp = 0; temp < nList.getLength(); temp++) 
	         {
	     		Node nNode = nList.item(temp);				
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
			Gui.infoBox("Błąd\n" + e.toString(), "Błąd");
	    }
		return rowData;
	}
	
	public void addRecord()
	{
		//narazie nie robim, dorobic wybor i czytanie z pliku
	}
	
	public void deleteRecord(String value)
	{
		try
		{
			NodeList nodes = doc.getElementsByTagName("list");
		    for (int i = 0; i < nodes.getLength(); i++) 
		    {
		      Element el = (Element)nodes.item(i);
		      Element name = (Element)el.getElementsByTagName("name").item(0);
		      String subject = name.getTextContent();
		      if (subject.equals(value)) 
		      {
		         el.getParentNode().removeChild(el);
		      }
		      TransformerFactory transformerFactory = TransformerFactory.newInstance();
		      Transformer transformer = transformerFactory.newTransformer();
		      DOMSource source = new DOMSource(doc);
		      StreamResult result = new StreamResult(inputFile);
		      transformer.transform(source, result);
		    }
		}
		catch (Exception e)
		{
			Gui.infoBox("Błąd\n" + e.toString(), "Błąd");
		}
	}

	public void setInput(File file)
	{
		inputFile = file;
	}
	
	public File getInput()
	{
		return inputFile;
	}
	
	public void setDocument(Document d)
	{
		doc = d;
	}
	
	public Document getDocument()
	{
		return doc;
	}

}