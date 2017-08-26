package gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import finder.Indexador;
import finder.SearchIndex;

public class ViwerResultMetric extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel result;
	
	public ViwerResultMetric(JTextField query,List<TripAdvisorFile> results ){
		double recall= SearchIndex.m.getRecall(query.getText().toString(),results);
		double precision=SearchIndex.m.getPrecision(query.getText().toString(),results);
		String mensaje=null;
		String recallStr;
		String precisionStr;
		if (recall>0)
			recallStr =String.valueOf(recall).substring(0,5);
		else
			recallStr= "0";
		if (precision>0)
			precisionStr =String.valueOf(precision).substring(0,5);
		else
			precisionStr= "0";
		 mensaje ="Recall: "+recallStr +"  Precision: "+precisionStr;
		
		result = new JLabel();
		result.setText(mensaje);
		add(result);
		
        //definición de la patalla de la lista de resultados
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(WindowConstans.TITLE_RESTVIWER);
        this.setSize(WindowConstans.RESTVIWER_WIDTH,WindowConstans.RESTVIWER_HEGHT);
        this.setLocationRelativeTo(null);

		if(recall != 0 || precision !=0) 
			this.setVisible(true);
		else
			this.setVisible(false);
	}
}
