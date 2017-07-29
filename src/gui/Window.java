package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import finder.SearchIndex;

public class Window extends JFrame {
		
	public Window(){
		super(WindowConstans.TITLE_WINDOWS);
		this.setSize(WindowConstans.WINDOW_HEGHT, WindowConstans.WINDOW_WIDTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.AddComponents();
	}
	
	public void ShowWindows(){
		this.setVisible(true);
	}
	
	private void AddComponents(){
		
		//definición de filtros para la busqueda
	    JRadioButton optionUserID = new JRadioButton(WindowConstans.USER_ID);
        JRadioButton optionContent = new JRadioButton(WindowConstans.CONTENT);
        JRadioButton optionDate = new JRadioButton(WindowConstans.DATE);
        JRadioButton optionAll = new JRadioButton(WindowConstans.ALL);
        
        ButtonGroup RBGFileter = new ButtonGroup();
        RBGFileter.add(optionUserID);
        RBGFileter.add(optionContent);
        RBGFileter.add(optionDate);
        RBGFileter.add(optionAll);
        
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2,2));
//        filterPanel.setSize(WindowConstans.PANEL_FILTER_HEGHT, WindowConstans.PANEL_FILTER_WIDTH);
        
        filterPanel.add(optionUserID);
        filterPanel.add(optionContent);
        filterPanel.add(optionDate);
        filterPanel.add(optionAll);
        
		//Definición de input para la busqueda
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(2,2));
		JLabel search_label = new JLabel(WindowConstans.TEXT_LABEL_BUSCAR);
		JTextField search_text = new JTextField(50);
		dataPanel.add(search_label);
		dataPanel.add(search_text);
		dataPanel.add(new JLabel(" ")); //Agrego un label vacio porque si no no se como hacer para que no me coma espacio el panel de abajo

        //Definición del boton de busqueda
        JPanel buttonPanel = new JPanel();
		JButton buttonSearch = new JButton(WindowConstans.TEXT_BUTTON_BUSCAR);
		buttonPanel.add(buttonSearch);
		buttonSearch.addActionListener(new SearchIndex(search_text));
        
        
        //Asignación de panels al cotainer
		Container cp = getContentPane();
		cp.add(dataPanel,BorderLayout.NORTH);
        cp.add(filterPanel, BorderLayout.CENTER);
        cp.add(buttonPanel,BorderLayout.SOUTH);
        

		
	}

}
