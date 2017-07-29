package finder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.xml.sax.SAXException;

import gui.Window;



public class Indexador {
	
	public static IndexWriter writer;

	public static void main(String[] args) throws Exception {
//		AddDocumentToIndex(LuceneConstants.HOMEPATH);
		Window window = new Window();
		window.ShowWindows();
		
	}

	
	/**
	 *  Metodo que carga todos los documentos al indicen
	 **/
	public static void AddDocumentToIndex(String indexPath) throws Exception{

		//Se carga
		SpanishAnalyzer analizador = new SpanishAnalyzer(Version.LUCENE_40);                
        Directory directorioIndex = new SimpleFSDirectory(new File(indexPath+LuceneConstants.INDICE));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analizador);
        config.setOpenMode(OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(directorioIndex,config);
		
		File directory = new File(indexPath+LuceneConstants.FILE_PATH);
		for(File folders : directory.listFiles()){//newyork
			if (folders.isDirectory())
				for(File documents : folders.listFiles()){
					if (documents.isFile()){
					Archivo archivo = new Archivo();
					org.apache.lucene.document.Document documento = archivo.addFile(new File(documents.getPath()));
					iwriter.addDocument(documento);
					}
					//indexFile(documents);
				}
			}
		iwriter.close();
	}

}
	

