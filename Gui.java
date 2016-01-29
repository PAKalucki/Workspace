import java.awt.EventQueue;

import javax.swing.JFrame;
//import javax.swing.JToolBar;
import java.awt.BorderLayout;
//import javax.swing.JTextArea;
//import javax.swing.JEditorPane;
import javax.swing.JMenuBar;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import java.awt.event.ActionEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import javax.swing.JTextField;
//import javax.swing.JLabel;
//import java.awt.event.InputMethodListener;
//import java.awt.event.InputMethodEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import javax.swing.JScrollPane;
//import javax.swing.JScrollBar;
//import java.awt.event.AdjustmentListener;
//import java.awt.event.AdjustmentEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;


public class Gui {

	private JFrame frame;
	private JTextField textField;
	//private JTable table;
	private JTable table_1;

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
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		xmlHandler handler = new xmlHandler();
		File przyklad = new File("subjects.xml");
		Vector<String> columnNames = new Vector<String>();
	    columnNames.addElement("Temat");
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 15));
		

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		//DefaultTableModel model = new DefaultTableModel();
		table_1 = new JTable(handler.getXML(przyklad),columnNames);
		DefaultTableModel model = (DefaultTableModel)table_1.getModel();
		scrollPane.setViewportView(table_1);
		
		JButton btnSzukaj = new JButton("Szukaj");
		btnSzukaj.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSzukaj.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (textField.getText().isEmpty() == false)
				{
					for(int i = model.getRowCount()-1; i>=0; i--)
					{
						model.removeRow(i);
					}
					for(int temp = 0; temp < handler.queryXML(przyklad, textField.getText()).size(); temp++ )
					{
						model.addRow(handler.queryXML(przyklad, textField.getText()).elementAt(temp));
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
					for(int temp = 0; temp < handler.getXML(przyklad).size(); temp++ )
					{
						model.addRow(handler.getXML(przyklad).elementAt(temp));
					}
				}
			}
		});
		
		JButton btnOpen = new JButton("Otwórz");
		btnOpen.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser openFile = new JFileChooser();
				 if (arg0.getSource() == btnOpen) 
				 {
				        openFile.showOpenDialog(btnOpen);
				 }
			}
		});

		btnOpen.setFont(new Font("Tahoma", Font.BOLD, 11));
		menuBar.add(btnOpen);
		
		JButton btnUsu = new JButton("Usuń");
		btnUsu.setFont(new Font("Tahoma", Font.BOLD, 11));
		menuBar.add(btnUsu);
		
		JButton btnOpcje = new JButton("Opcje");
		btnOpcje.setFont(new Font("Tahoma", Font.BOLD, 11));
		menuBar.add(btnOpcje);
		menuBar.add(btnSzukaj);
		menuBar.add(textField);
		textField.setColumns(10);
		
	}

}
