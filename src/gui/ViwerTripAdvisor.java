package gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ViwerTripAdvisor extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea textContent;
	private JScrollPane scrollPanel;

	public ViwerTripAdvisor (String xml){
		textContent = new JTextArea();
		textContent.setText(xml);
		scrollPanel = new JScrollPane(textContent); 
		textContent.setEditable(false);
		add(scrollPanel);
		
        //definición de la patalla de la lista de resultados
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(WindowConstans.TITLE_VIWER);
        this.setSize(WindowConstans.VIWER_HEGHT,WindowConstans.VIWER_WIDTH);
        this.setLocationRelativeTo(null);       
        this.setVisible(true);
	}
	
}
