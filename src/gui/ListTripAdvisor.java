package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.w3c.dom.Document;

import finder.Archivo;

public class ListTripAdvisor extends JFrame  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListTripAdvisor( List<TripAdvisorFile> _listFile){
		
		DefaultListModel<TripAdvisorFile> listFile = new DefaultListModel<>();
		for (Iterator<TripAdvisorFile> i = _listFile.iterator(); i.hasNext();) {
			TripAdvisorFile item = i.next();
		    listFile.addElement(item);
		}
		
        //create the list
        JList<TripAdvisorFile> tripAdvisorList = new JList<>(listFile);
        add(new JScrollPane(tripAdvisorList));
        TripAdvisorRenderer rend=new TripAdvisorRenderer();
        tripAdvisorList.setCellRenderer(rend);
        
        tripAdvisorList.addMouseListener(new MouseAdapter()  
        {  
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		//Estructura para poder leer el archivo xml
        		 JList theList = (JList) e.getSource();
        	     int index = theList.locationToIndex(e.getPoint());
    	          if (index >= 0) {
    	            TripAdvisorFile o =(TripAdvisorFile) theList.getModel().getElementAt(index);
    	            theList.getModel().getElementAt(index);
           			try {
               		    ViwerTripAdvisor viwerTripAdvisor= new ViwerTripAdvisor(Archivo.readFileAsString(o.getUrl()));
           			}
						catch (Exception e1) {
					}
        	     }
        	}
        	
          });
 
        //definición de la patalla de la lista de resultados
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(WindowConstans.TITLE_RESULT);
        this.setSize(WindowConstans.RESULT_HEGHT,WindowConstans.RESULT_WIDTH);
        this.setLocationRelativeTo(null);       
        this.setVisible(true);
		
	}

	
	
	
}
