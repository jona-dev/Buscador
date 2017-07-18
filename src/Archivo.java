
	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


	public class Archivo  {
		private Field threadID;
		private Field userID;
		private Field date;
		private Field content;
		private Field respuesta;
		
	  
	public org.apache.lucene.document.Document  addFile(File inputFile) throws Exception{
			
			 //Estructura para poder leer el archivo xml
			 Document docXml = loadXMLFromString(readFileAsString(inputFile.getPath()));//espera un string 
	         docXml.getDocumentElement().normalize();
	         //Documento a importar al indice.
	         org.apache.lucene.document.Document docField = new org.apache.lucene.document.Document(); 
	         
	         //Obtener los Field correspondientes
            this.threadID=getDataXml(docXml,"Thread","ThreadID",Store.NO);
            docField.add(this.threadID);

	        //inicial
	        this.userID=getDataXml(docXml, "InitPost", "UserID", Store.YES);
	        docField.add(this.userID);
            this.date=getDataXml(docXml, "InitPost", "Date", Store.YES);
            docField.add(this.date);
            this.content=getDataXml(docXml, "InitPost", "icontent", Store.YES);
            docField.add(this.content);
            this.respuesta=new TextField("respuesta", "NO",Store.NO); // si es respuesta, no se si vale la pena
            docField.add(this.respuesta);
            //respuestas
	        NodeList nList = docXml.getElementsByTagName("Post");
	        for (int temp = 0; temp < nList.getLength(); temp++) {
	            this.userID=getDataXml(docXml, "Post", "UserID", Store.NO);
	            docField.add(this.userID);
	            this.date=getDataXml(docXml, "Post", "Date", Store.YES);
	            docField.add(this.date);
	            this.content=getDataXml(docXml, "Post", "rcontent", Store.YES);
	            docField.add(this.content);
	            this.respuesta=new TextField("respuesta", "YES",Store.NO); // si es respuesta, no se si vale la pena
	            docField.add(this.respuesta);
	        }
            
            return docField;
	      } 	     	 	

	
	public static Document loadXMLFromString(String xml) throws Exception
	{
		/**
		 * Reemplazar caracter & por &amp;
		 */
		String xmlModifiado=xml.replaceAll("&", "&amp;");
		
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xmlModifiado));
	    return builder.parse(is);
	}
	
	private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	
	
	private Field getDataXml(Document _docXml,String nodeParent,String node,Store stored){
        NodeList nlParent = _docXml.getElementsByTagName(nodeParent);
        Node nChild = nlParent.item(0);
	    if (nChild.getNodeType() == Node.ELEMENT_NODE) {
            Element eThread = (Element) nChild;
            //Si es icontent o rcontent aplico el filtro de stemmer + Stopword
            if (node.toString().equals("rcontent") || node.toString().equals("icontent"))
            	return new TextField(node, Stopwords.removeStemmedStopWords(eThread.getElementsByTagName(node).item(0).getTextContent()), stored);
            else
            	return new TextField(node, eThread.getElementsByTagName(node).item(0).getTextContent(), stored);
            }
		return null;
	}
}