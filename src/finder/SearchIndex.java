package finder;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;


public class SearchIndex implements ActionListener {
	
	//Se define el indice
	private SpanishAnalyzer analizador;                
    private Directory directorioIndex;
    private JTextField search_text;

	public SearchIndex(JTextField _search_text){
		super();	
		this.search_text=_search_text;
	}
	
    private List<String> searchIndex(String searchString) {
        // Variables
        List<String> listaResultado = new ArrayList<String>();
    
        System.out.println("Searching.... '" + searchString + "'");
        
        try {
        	
        	//Se define el indice
    		analizador = new SpanishAnalyzer(Version.LUCENE_40);                
            directorioIndex = new SimpleFSDirectory(new File(LuceneConstants.HOMEPATH+LuceneConstants.INDICE));
            
            IndexReader reader = IndexReader.open(directorioIndex);
            IndexSearcher searcher = new IndexSearcher(reader);

            // Analizador en español. Ya contiene los StopWords en español.
            // El mismo analizador se tiene que usar en el indexado y en la busqueda.
                     

            QueryParser qp = new QueryParser(Version.LUCENE_40, LuceneConstants.ALL, analizador);
            Query query = qp.parse(searchString); // parse the query and construct the Query object

            TopDocs hits = searcher.search(query, 1000); // run the query

            if (hits.totalHits == 0) {
                System.out.println("No data found.");
            }else {
                for (int i = 0; i < hits.totalHits; i++) {
                    Document doc = searcher.doc(hits.scoreDocs[i].doc); // get the next document
                    String url = doc.get("url"); // get its path field
                    System.out.println("Found in :: " + url);
                    listaResultado.add(url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaResultado;
    } // Fin de la clase publica searchIndex.

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("BUSCANDO...");
		searchIndex(this.search_text.getText().toString());
		System.out.println("FIN DE LA BUSQUEDA");
	}
}