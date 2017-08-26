package finder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.ext.Extensions.Pair;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import gui.Window;

public class Indexador implements ActionListener{
	public static IndexWriter writer;
	private JTextField TextFieldM;

	public static HashMap <String, ArrayList<Pair<String,Integer>>> valor;
	
	
	public Indexador(JTextField _pathText,Window _window){
		this.TextFieldM=_pathText; 

	}
	
	/**
	 * Metodo que carga todos los documentos al indicen
	 * @param indexPath, directorio con los archivos
	 * @throws Exception
	 */
	private static void AddDocumentToIndex(String indexPath) throws Exception{
		//Se carga
		SpanishAnalyzer analizador = new SpanishAnalyzer(Version.LUCENE_40);                
        Directory directorioIndex = new SimpleFSDirectory(new File(indexPath+LuceneConstants.INDICE));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analizador);
        config.setOpenMode(OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(directorioIndex,config);
		
        long count=0;
		File directory = new File(indexPath+LuceneConstants.FILE_PATH);
//		count=directory.length();
		for(File folders : directory.listFiles()){//newyork
			if (folders.isDirectory())
				for(File documents : folders.listFiles()){
					System.out.println(documents.getPath());
					if (documents.isFile()){
					Archivo archivo = new Archivo();
					org.apache.lucene.document.Document documento = archivo.addFile(new File(documents.getPath()));
					if (documento != null)
						iwriter.addDocument(documento);
					}
//					System.out.println(". . ." + count--);
				}
			}
		iwriter.close();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			System.out.println("INDEXADO....."+this.TextFieldM.getText().toLowerCase());
			AddDocumentToIndex(this.TextFieldM.getText().toLowerCase());
			System.out.println("INDICE FINALIZADO");
//			m = new Metricas (this.TextFieldM);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
