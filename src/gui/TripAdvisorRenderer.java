package gui;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.w3c.dom.Document;

import finder.Archivo;

public class TripAdvisorRenderer extends JLabel implements ListCellRenderer<TripAdvisorFile> {
	TripAdvisorFile content;

	public TripAdvisorRenderer() {
        setOpaque(true);
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends TripAdvisorFile> list, TripAdvisorFile tripAdvisor, int index,
            boolean isSelected, boolean cellHasFocus) {
 
//        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/" + code + ".png"));
//        setIcon(imageIcon);
//        setText(tripAdvisor.getName());
    	content=tripAdvisor;
       setText(tripAdvisor.getFileName());
       setToolTipText(tripAdvisor.getUrl());
 
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }            
        
        return this;
    }
    
}
