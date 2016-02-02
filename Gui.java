import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

//import org.xml.sax.SAXParseException;

import java.io.*;
import java.util.*;

public class Gui {

	private JFrame frame;
	private JTextField textField;
	//private JTable table;
	private JTable table_1;
	private String value = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() 
	{
		initialize();
	}

	public static void infoBox(String infoMessage, String titleBar)
	{
		JOptionPane.showMessageDialog(null, infoMessage, "Uwaga: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String valueBox(String infoMessage, String titleBar)
	{
		String nazwa = JOptionPane.showInputDialog(infoMessage, titleBar);
		return nazwa;
	}
	
	public static File chooseInput()
	{
		JFileChooser openFile = new JFileChooser();
	 	FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "XML", "xml");
	 	openFile.setFileFilter(filter);
        openFile.showOpenDialog(null);
        
        return openFile.getSelectedFile();
	}
	
	public void setValue(String s)
	{
		value = s;
		value = value.replaceAll("\n", "").replace("\r", "");;
	}
	
	public String getValue()
	{
		return value;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	try
	{	
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		xmlHandler handler = new xmlHandler();
		//final String value = "";
		//File przyklad = new File("subjects.xml");
		//handler.setInput(przyklad);
		Vector<String> columnNames = new Vector<String>();//kontener na nazwe kolumny do modelu
	    columnNames.addElement("Temat");
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		//DefaultTableModel model = new DefaultTableModel();
		table_1 = new JTable(handler.getXML(),columnNames);
		table_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				int column = table_1.getSelectedColumn();
				int row = table_1.getSelectedRow();
				setValue(table_1.getValueAt(row, column).toString());	
			}
		});
		table_1.setRowSelectionAllowed(false);
		DefaultTableModel model = (DefaultTableModel)table_1.getModel();
		scrollPane.setViewportView(table_1);
		
		JTextField tf = new JTextField();//blokada
		tf.setEditable(false);
		DefaultCellEditor editor = new DefaultCellEditor( tf );
		table_1.setDefaultEditor(Object.class, editor);
		
		JButton btnSzukaj = new JButton("Szukaj");
		btnSzukaj.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSzukaj.addActionListener(new ActionListener() //po wcisnieciu szukaj
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (textField.getText().isEmpty() == false)
				{
					for(int i = model.getRowCount()-1; i>=0; i--)
					{
						model.removeRow(i);
					}
					for(int temp = 0; temp < handler.queryXML(textField.getText()).size(); temp++ )
					{
						model.addRow(handler.queryXML(textField.getText()).elementAt(temp));
					}
					
					//table_1.remove
					//table_1 = new JTable(handler.queryXML(przyklad, textField.getText()),columnNames);
				}
				else
				{
					for(int i = model.getRowCount()-1; i>=0; i--)
					{
						model.removeRow(i);
					}
					for(int temp = 0; temp < handler.getXML().size(); temp++ )
					{
						model.addRow(handler.getXML().elementAt(temp));
					}
				}
			}
		});
		
		
		JButton btnOpen = new JButton("Otwórz");
		btnOpen.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				 if (arg0.getSource() == btnOpen) 
				 {
					File file = chooseInput();
					if(file!=null)
					{
					 	handler.setInput(file);
						for(int i = model.getRowCount()-1; i>=0; i--)
						{
							model.removeRow(i);
						}
						for(int temp = 0; temp < handler.getXML().size(); temp++ )
						{
							model.addRow(handler.getXML().elementAt(temp));
						}
					}
				 }
			}
		});

		btnOpen.setFont(new Font("Tahoma", Font.BOLD, 11));
		menuBar.add(btnOpen);
		
		JButton btnUsun = new JButton("Usuń");
		btnUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String value="";
				if(getValue().isEmpty() == false)
				{
					handler.deleteRecord(getValue());
					for(int i = model.getRowCount()-1; i>=0; i--)
					{
						model.removeRow(i);
					}
					for(int temp = 0; temp < handler.getXML().size(); temp++ )
					{
						model.addRow(handler.getXML().elementAt(temp));
					}
				}
				else
				{
					infoBox("Wybierz temat do usunięcia","Błąd");
				}
			}
		});
		btnUsun.setFont(new Font("Tahoma", Font.BOLD, 11));
		menuBar.add(btnUsun);
		
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String subject = "";
				String subject = valueBox("Proszę podać nazwę tematu do dodania", "");
				if(subject!=null)
				{
					if(subject.isEmpty() == false)
					{
						handler.addRecord(subject);
						for(int i = model.getRowCount()-1; i>=0; i--)
						{
							model.removeRow(i);
						}
						for(int temp = 0; temp < handler.getXML().size(); temp++ )
						{
							model.addRow(handler.getXML().elementAt(temp));
						}
					}
				}
			}
		});
		btnDodaj.setFont(new Font("Tahoma", Font.BOLD, 11));
		menuBar.add(btnDodaj);
		menuBar.add(btnSzukaj);
		menuBar.add(textField);
		textField.setColumns(10);
		
	}
	catch (Exception e) 
	{
        infoBox("Błąd:\n" + e.toString(), "Błąd");
    }
	}

}
