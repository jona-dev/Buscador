
	import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


	public class Archivo  {
		
	  
	@SuppressWarnings("deprecation")
	public org.apache.lucene.document.Document  addFile(File inputFile) throws Exception{
		
		//declaración de datos importantes  de los post
		 Field threadID;
		 Field userID;
		 Field date;
		 Field content;
		 Field respuesta;
		 Field url;
		 Field all;
		 String allString = new String();
		 
		 //Estructura para poder leer el archivo xml
		 Document docXml = loadXMLFromString(readFileAsString(inputFile.getPath()));
         docXml.getDocumentElement().normalize();
         //Documento a importar al indice.
         org.apache.lucene.document.Document docField = new org.apache.lucene.document.Document(); 
   
         //Obtener los Field correspondientes
        url = new StringField("url", inputFile.getPath(),Store.YES);
        docField.add(url);
        
        threadID=getDataXml(docXml,"Thread","ThreadID",Store.NO);
        docField.add(threadID);
        allString+=threadID.stringValue();
        allString+="||";

        //inicial
        userID=getDataXml(docXml, "InitPost", "UserID", Store.YES);
        docField.add(userID);
        allString+=userID.stringValue();
        allString+="||";
        date=getDataXml(docXml, "InitPost", "Date", Store.YES);
        docField.add(date);
        allString+=date.stringValue();
        allString+="||";
        content=getDataXml(docXml, "InitPost", "icontent", Store.YES);
        docField.add(content);
        allString+=content.stringValue();
        allString+="||";
        respuesta=new TextField("respuesta", "NO",Store.NO); // si es respuesta, no se si vale la pena
        docField.add(respuesta);
        //respuestas
        NodeList nList = docXml.getElementsByTagName("Post");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            userID=getDataXml(docXml, "Post", "UserID", Store.NO);
            docField.add(userID);
            allString+=userID.stringValue();
            allString+="||";
            date=getDataXml(docXml, "Post", "Date", Store.YES);
            docField.add(date);
            allString+=date.stringValue();
            allString+="||";
            content=getDataXml(docXml, "Post", "rcontent", Store.YES);
            docField.add(content);
            allString+=content.stringValue();
            respuesta=new TextField("respuesta", "YES",Store.NO); // si es respuesta, no se si vale la pena
            docField.add(respuesta);
        }
        all = new TextField("all", allString,Store.NO);
        docField.add(all);
        return docField;
	} 	     	 	

	/**
	 * Reemplazar caracter & por &amp;
	 * @param xml : xml en formaton de texto
	 * @return : Document con el xml tratado por un filtro
	 * @throws Exception
	 */
	public static Document loadXMLFromString(String xml) throws Exception
	{
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
	
	/**
	 * Devuelve un Field recuperado de cuerpo del xml
	 * @param _docXml : Documento xml
	 * @param nodeParent : Tag contenedor de Tags
	 * @param node : Tag que tiene el dato en cuestion
	 * @param stored : Forma de almancenamiento
	 * @return : Devuelve el Field en cuestion
	 */
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