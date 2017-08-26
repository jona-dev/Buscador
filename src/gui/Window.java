package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import finder.Indexador;
import finder.SearchIndex;
	

public class Window extends JFrame {
	
	public static JTextField pathText;
	
	
	public Window( ){
		super(WindowConstans.TITLE_WINDOWS);
//		this.resultWindows=_resultados;
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

        optionAll.setSelected(true);
        
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
		JLabel path_label = new JLabel(WindowConstans.TEXT_LABEL_PATH);
		pathText =new JTextField(100);
		JTextField search_text = new JTextField(100);
		dataPanel.add(search_label);
		dataPanel.add(search_text);
		dataPanel.add(path_label);
		dataPanel.add(pathText);


        //Definición del boton de busqueda
        JPanel buttonPanel = new JPanel();
		JButton buttonIndexar = new JButton(WindowConstans.TEXT_BUTTON_INDEXAR);
		JButton buttonSearch = new JButton(WindowConstans.TEXT_BUTTON_BUSCAR);
		buttonPanel.add(buttonIndexar);
		buttonPanel.add(buttonSearch);
		buttonSearch.addActionListener(new SearchIndex(search_text,optionUserID,optionContent,optionDate,optionAll,this.pathText));
		buttonIndexar.addActionListener(new Indexador(pathText,this));
        
        
        //Asignación de panels al cotainer
		Container cp = getContentPane();
		cp.add(dataPanel,BorderLayout.NORTH);
        cp.add(filterPanel, BorderLayout.CENTER);
        cp.add(buttonPanel,BorderLayout.SOUTH);
        

		
	}

}

