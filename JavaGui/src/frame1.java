import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.naming.ConfigurationException;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import org.dcm4che2.tool.dcmsnd.*;
import org.apache.log4j.*;
import org.apache.log4j.Logger;
import org.apache.commons.cli.*;
import org.dcm4che2.data.*;
import org.dcm4che2.imageio.*;
import org.slf4j.*;

@SuppressWarnings("unused")
public class frame1 {

	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private String selectedDirectory;
	private String calledAET;
	private String ipAddress;
	private String port;
		
	protected static final Component Component = null;
	
	private static Logger logger = Logger.getLogger(frame1.class);
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 window = new frame1();
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
	public frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 465, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(null, "Source", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 212, 120);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(71, 23, 115, 20);
		panel.add(textField_1);
		
		JLabel lblAeTitle = new JLabel("AE title");
		lblAeTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAeTitle.setBounds(22, 26, 39, 14);
		panel.add(lblAeTitle);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Destination", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(230, 10, 212, 120);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(75, 23, 115, 20);
		panel_2.add(textField_2);
		
		JLabel label = new JLabel("AE title");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(10, 26, 39, 14);
		panel_2.add(label);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(75, 53, 115, 20);
		panel_2.add(textField_3);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIpAddress.setBounds(10, 56, 61, 14);
		panel_2.add(lblIpAddress);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(75, 83, 115, 20);
		panel_2.add(textField_4);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPort.setBounds(10, 86, 39, 14);
		panel_2.add(lblPort);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 136, 429, 80);
		frame.getContentPane().add(textArea);
		textArea.setEditable(false);
		
		JButton btnAddFilesAndor = new JButton("Add File(s) and/or Directory(ies)");
		btnAddFilesAndor.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddFilesAndor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fileChooser.showOpenDialog(Component);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            selectedDirectory = file.getAbsolutePath();
		            System.out.println("get filepath "+selectedDirectory);
		            textArea.setText(selectedDirectory);
		            
		            try {
		              // return the file path 
		            } catch (Exception ex) {
		              System.out.println("problem accessing file"+file.getAbsolutePath());
		            }
		        } 
		        else {
		            System.out.println("File access cancelled by user.");
		        }
				}
		});

		btnAddFilesAndor.setBounds(10, 227, 202, 23);
		frame.getContentPane().add(btnAddFilesAndor);
		
		JButton btnRemoveEntryies = new JButton("Remove Entry(ies)");
		btnRemoveEntryies.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemoveEntryies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedDirectory = null;
	            System.out.println("get filepath "+selectedDirectory);
	            textArea.setText(selectedDirectory);
			}
		});
		btnRemoveEntryies.setBounds(222, 227, 136, 23);
		frame.getContentPane().add(btnRemoveEntryies);
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dcmsnd();
			}
		});
		btnSend.setBounds(368, 227, 71, 23);
		frame.getContentPane().add(btnSend);
		
	}
	
	
	private void dcmsnd(){
		calledAET = textField_2.getText();
		ipAddress = textField_3.getText();
		int port = Integer.parseInt(textField_4.getText());
		DcmSnd dcmSend = new DcmSnd("DCMSND");
		dcmSend.setCalledAET(calledAET);
		dcmSend.setRemoteHost(ipAddress);
		dcmSend.setRemotePort(port);
		dcmSend.setOfferDefaultTransferSyntaxInSeparatePresentationContext(false);
		dcmSend.setSendFileRef(false);
		dcmSend.setStorageCommitment(false);
		dcmSend.setPackPDV(true);
		dcmSend.setTcpNoDelay(true);
		File file = new File(selectedDirectory);
		
		dcmSend.addFile(file);
		
		dcmSend.configureTransferCapability();
		try {
			dcmSend.start();
			} catch (Exception e1) {
				logger.error("ERROR: Failed to start server for receiving " +
			"Storage Commitment results:" + e1.getMessage());
			return;
			}

			try {
			long t1 = System.currentTimeMillis();
			dcmSend.open();
			long t2 = System.currentTimeMillis();
			logger.info("Connected to " + calledAET + " in "
			+ ((t2 - t1) / 1000F) + "s");

			dcmSend.send();
			dcmSend.close();
			System.out.println("Released connection to " + calledAET);
			} catch (IOException e1) {
			logger.error("ERROR: Failed to establish association:"
			+ e1.getMessage());
			} catch (InterruptedException e1) {
			logger.error("ERROR: Failed to establish association:"
			+ e1.getMessage());
			} catch (org.dcm4che2.net.ConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
			dcmSend.stop();
			}
	
	}
}
