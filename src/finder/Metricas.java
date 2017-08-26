package finder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.Line;
import javax.swing.JTextField;

import org.apache.hadoop.record.compiler.JField;
import org.apache.lucene.queryparser.ext.Extensions.Pair;

import gui.TripAdvisorFile;

public class Metricas {
	
public Metricas (JTextField pathText){
	ArrayList<Pair<String,Integer>> aux = new ArrayList<Pair<String,Integer>>();
	File directory = new File(pathText.getText()+"/queryRelJudgements");
	StringBuffer fileData = new StringBuffer ();
	String title = null;
	Indexador.valor = new HashMap <String, ArrayList<Pair<String,Integer>>> ();
	try {
		BufferedReader br = new BufferedReader (new FileReader (directory.getPath()));
		char [] buf = new char [1024];
		int numReader = 0;	
		String line = null;
		Pair<String,Integer> data = new Pair <String,Integer>(null,null);
		Integer sum = 0;
		while ((line = br.readLine())!= null && line.length()!= 0){
			if (line.substring(0,7).equals("<Query>")){
				if (title != null){
					aux.add(new Pair <String, Integer>("total",sum));
					sum = 0;
					Indexador.valor.put(title, aux);
					aux = new ArrayList<Pair<String,Integer>>();
				}
				title = line.replaceAll("<Query>","");
				title = title.replaceAll("</Query>", "");
			}
			else {
				int largo = line.length();
				data = new Pair<String,Integer>(line.substring(0,largo-2),Integer.valueOf(line.substring(largo - 1, largo)));
				sum += data.cud;
				aux.add(data);
			}
		}
		aux.add(new Pair <String, Integer>("total",sum));
		}
	 catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
}
public double getRecall (String query, List<TripAdvisorFile> results){
	double num = 0;
	int count = 0;
	boolean encontrado = false;
	List<Pair<String,Integer>> aux = Indexador.valor.get(query);
	if (aux != null){
		for (Iterator<TripAdvisorFile> i = results.iterator(); i.hasNext();) {
			TripAdvisorFile tAdvFile = i.next();
			while (aux.size()> count && !encontrado){
				if (tAdvFile.getFileName().equals(aux.get(count).cur+".xml")){
					System.out.println(aux.get(count).cur);
					num+= aux.get(count).cud;
					encontrado = true;
				}
				count++;
		     }
			encontrado = false;
			count=0;
		}
	}else 
		System.out.println("Esta consulta no ha sido evaluada.");
		double rslt;
		try {
			
			rslt = num/(aux.get(aux.size()-1).cud);
		}
		catch (Exception ae){
			
			rslt =  0;
		}
		return rslt;
}
public double getPrecision (String query, List<TripAdvisorFile> results){
	double num = 0;
	int count = 0;
	boolean encontrado = false;
	List<Pair<String,Integer>> aux = Indexador.valor.get(query);
	if (aux != null){
		for (Iterator<TripAdvisorFile> i = results.iterator(); i.hasNext();) {
			TripAdvisorFile tAdvFile = i.next();
			while (aux.size()> count && !encontrado){
				if (tAdvFile.getFileName().equals(aux.get(count).cur)&&(aux.get(count).cud >= 1) ){
					num++;
					encontrado = true;
				}
				
				count++;
				encontrado = false;
				}
			
		}
	}else 
		System.out.println("Esta consulta no ha sido evaluada.");
		double rslt;
		try {
			
			rslt = num/results.size();
		}
		catch (Exception ae){
			
			rslt = 0;
		}
		return rslt;
}

}	