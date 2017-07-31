package finder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class Indexador implements ActionListener{
	public static IndexWriter writer;
	
	/**
	 *  Metodo que carga todos los documentos al indicen
	 **/
	private static void AddDocumentToIndex(String indexPath) throws Exception{

		//Se carga
		SpanishAnalyzer analizador = new SpanishAnalyzer(Version.LUCENE_40);                
        Directory directorioIndex = new SimpleFSDirectory(new File(indexPath+LuceneConstants.INDICE));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analizador);
        config.setOpenMode(OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(directorioIndex,config);
		
        long count=0;
		File directory = new File(indexPath+LuceneConstants.FILE_PATH);
		count=directory.length();
		for(File folders : directory.listFiles()){//newyork
			if (folders.isDirectory())
				for(File documents : folders.listFiles()){
					if (documents.isFile()){
					Archivo archivo = new Archivo();
					org.apache.lucene.document.Document documento = archivo.addFile(new File(documents.getPath()));
					iwriter.addDocument(documento);
					}
					System.out.println(". . ." + count--);
				}
			}
		iwriter.close();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			System.out.println("INDEXADO.....");
			AddDocumentToIndex(LuceneConstants.HOMEPATH);
			System.out.println("INDICE FINALIZADO");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
