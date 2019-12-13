/*
 * Authors: 
 * Ehap Fawzy
 * Hussien Feteiha
 * Khaled Ezzat
 * Hatem Mamdooh
 * 
 * FCAI - CU
 * 
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

public class MipsVM_GUI {

	private JFrame frmMipsvm;
	
	public static JTable table;
	private JSplitPane splitWindow;
	private JTabbedPane rightPage , leftPage;
	private JSeparator separator , separator_1;
	public static JButton runAll , nextStep , compile;
	public static JTextField txtAdds , typeTxt , pcTxt;
	public static JScrollPane registerScroll , TextEditor , TextSegment , DataSegment;
	public static JTextArea regValue , regName , textSegmentValues , DataSegmentValues , codeArea;
	
	private TextLineNumber tln , textSegmentTLN , dataSegmentTLN;
	private JMenuItem newM , loadM , saveM , exitM;
	private JMenu registerFileM , File , mnDataSegment;
	private JRadioButton regB , regD , regH , dataD , dataB , dataH;
	private JMenu mnOthers;
	private JMenuItem mntmVersion;
	private JMenuItem mntmAuthers;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MipsVM_GUI window = new MipsVM_GUI();
					window.frmMipsvm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MipsVM_GUI() {
		initialize();
	}
	
	public void runFinal() {
		TextEditor.setRowHeaderView(tln);
		TextSegment.setRowHeaderView(textSegmentTLN);
		DataSegment.setRowHeaderView( dataSegmentTLN );
		frmMipsvm.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		nextStep.setEnabled(false);
		runAll.setEnabled(false);
		
		MipsVM_GUI_Interface.init();
		
		regD.setSelected(true);
		dataD.setSelected(true);
		rightPage.setSelectedIndex(2);
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMipsvm = new JFrame();
		frmMipsvm.setTitle("MipsVM");
		frmMipsvm.setBounds(100, 100, 635, 490);
		frmMipsvm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		separator = new JSeparator();
		
		splitWindow = new JSplitPane();
		
		JLabel lblInstruct = DefaultComponentFactory.getInstance().createLabel("Current Assembly Code");
		lblInstruct.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstruct.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		txtAdds = new JTextField();
		txtAdds.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtAdds.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdds.setEditable(false);
		txtAdds.setBackground(SystemColor.menu);
		txtAdds.setColumns(10);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Instruction Type");
		lblNewJgoodiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesLabel.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		typeTxt = new JTextField();
		typeTxt.setHorizontalAlignment(SwingConstants.CENTER);
		typeTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		typeTxt.setEditable(false);
		typeTxt.setColumns(10);
		typeTxt.setBackground(SystemColor.menu);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		runAll = new JButton("Run All");
		runAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MipsVM_GUI_Interface.runAll();
			}
		});
		runAll.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		nextStep = new JButton("Next Step");
		nextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.runNext();
			}
		});
		nextStep.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		JLabel lblProgramCounter = DefaultComponentFactory.getInstance().createLabel("PC");
		lblProgramCounter.setBackground(SystemColor.menu);
		lblProgramCounter.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgramCounter.setFont(new Font("Vrinda", Font.BOLD, 16));
		
		pcTxt = new JTextField();
		pcTxt.setFont(new Font("Tahoma", Font.BOLD, 18));
		pcTxt.setHorizontalAlignment(SwingConstants.CENTER);
		pcTxt.setEditable(false);
		pcTxt.setBackground(SystemColor.menu);
		pcTxt.setColumns(10);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		table.setAlignmentX(Component.RIGHT_ALIGNMENT);
		table.setBackground(SystemColor.menu);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(50);
		compile = new JButton("Compile");
		compile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ( codeArea.getText().length() > 0 ) {
					MipsVM_GUI_Interface.init();
					regValue.setCaretPosition(0);
					DataSegmentValues.setCaretPosition(0);
					
					textSegmentValues.setText( eraseEmptyLines(codeArea.getText()) );
					
					boolean validParse = MipsVM_GUI_Interface.parseAll();
					
					if ( validParse ) {
						nextStep.setEnabled(true);
						runAll.setEnabled(true);
						compile.setEnabled(false);
						rightPage.setSelectedIndex(0);
						scrollUP();
					}
				}
				else {
					showMessage( "No code to compile" , "Info");
				}
			}
		});
		compile.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		
		//panel.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(frmMipsvm.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(splitWindow, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewJgoodiesLabel, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
										.addComponent(typeTxt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(pcTxt, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
										.addComponent(lblProgramCounter, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
									.addGap(8))
								.addComponent(lblInstruct, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtAdds, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
									.addGap(4)))
							.addGap(1)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(nextStep, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(runAll, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(compile, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
									.addGap(2))
								.addComponent(table, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)))
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitWindow, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(table, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(nextStep, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addComponent(runAll, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
										.addComponent(compile, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblInstruct)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtAdds, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addGap(15)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewJgoodiesLabel)
										.addComponent(lblProgramCounter))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(typeTxt, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(pcTxt, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
									.addGap(12)))
							.addGap(11))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		rightPage = new JTabbedPane(JTabbedPane.TOP);
		splitWindow.setRightComponent(rightPage);
		
		TextSegment = new JScrollPane();
		rightPage.addTab("Text Segment", null, TextSegment, null);
		
		textSegmentValues = new JTextArea();
		textSegmentValues.setEditable(false);
		textSegmentValues.setForeground(new Color(0, 0, 255));
		textSegmentValues.setDisabledTextColor(new Color(0, 0, 255));
		textSegmentValues.setMargin(new Insets(2, 5, 2, 2));
		textSegmentValues.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textSegmentValues.setDisabledTextColor(new Color(0, 0, 139));
		textSegmentTLN = new TextLineNumber(textSegmentValues , true , 0);
		TextSegment.setViewportView(textSegmentValues);
		
		DataSegment = new JScrollPane();
		rightPage.addTab("Data Segment", null, DataSegment, null);
		
		DataSegmentValues = new JTextArea();
		DataSegmentValues.setEditable(false);
		DataSegmentValues.setForeground(new Color(0, 0, 255));
		DataSegmentValues.setDisabledTextColor(new Color(0, 0, 255));
		DataSegmentValues.setMargin(new Insets(2, 5, 2, 2));
		DataSegmentValues.setFont(new Font("Monospaced", Font.PLAIN, 16));
		DataSegmentValues.setDisabledTextColor(new Color(0, 0, 139));
		dataSegmentTLN = new TextLineNumber(DataSegmentValues , true , 4096);
		DataSegment.setViewportView(DataSegmentValues);
		
		TextEditor = new JScrollPane();
		rightPage.addTab("TextEditor", null, TextEditor, null);
		
		codeArea = new JTextArea();
		codeArea.setForeground(new Color(0, 0, 255));
		codeArea.setDisabledTextColor(new Color(0, 0, 255));
		codeArea.setMargin(new Insets(2, 5, 2, 2));
		codeArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		codeArea.setDisabledTextColor(new Color(0, 0, 139));
		
		tln = new TextLineNumber(codeArea);
		TextEditor.setViewportView(codeArea);
		
		leftPage = new JTabbedPane(JTabbedPane.TOP);
		leftPage.setVerifyInputWhenFocusTarget(false);
		leftPage.setForeground(Color.BLACK);
		splitWindow.setLeftComponent(leftPage);
		
		registerScroll = new JScrollPane();
		registerScroll.setVerifyInputWhenFocusTarget(false);
		leftPage.addTab("Register File ", null, registerScroll, null);
		
		regValue = new JTextArea();
		regValue.setFont(new Font("Monospaced", Font.PLAIN, 16));
		regValue.setForeground(new Color(0, 0, 255));
		regValue.setDisabledTextColor(new Color(0, 0, 255));
		regValue.setMargin(new Insets(2, 5, 2, 2));
		regValue.setEditable(false);
		registerScroll.setViewportView(regValue);
		
		regName = new JTextArea();
		regName.setFont(new Font("Monospaced", Font.PLAIN, 16));
		regName.setDisabledTextColor(new Color(0, 0, 139));
		regName.setEnabled(false);
		regName.setEditable(false);
		regName.setBackground(new Color(240, 248, 255));
		regName.setColumns(4);
		registerScroll.setRowHeaderView(regName);
		splitWindow.setDividerLocation(200);
		
		frmMipsvm.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmMipsvm.setJMenuBar(menuBar);
		
		File = new JMenu("  File   ");
		menuBar.add(File);
		
		newM = new JMenuItem("New");
		newM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				codeArea.setText("");
				textSegmentValues.setText("");
				compile.setEnabled(true);
				nextStep.setEnabled(false);
				runAll.setEnabled(false);
				txtAdds.setText("");
				pcTxt.setText("");
				typeTxt.setText("");
				clearTable();
				MipsVM_GUI_Interface.clear();
				
				scrollUP();
			}
		});
		File.add(newM);
		
		JSeparator separator_3 = new JSeparator();
		File.add(separator_3);
		
		loadM = new JMenuItem("Load Project     ");
		loadM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = openFile.showOpenDialog(null);
				
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String dir = openFile.getSelectedFile().getAbsolutePath();

					try {
						BufferedReader infile = new BufferedReader( new FileReader(dir) );
						
						String line = "", txt = "";
						while ( (line = infile.readLine()) != null ) {
							txt += (line + "\n");
						}
						codeArea.setText(txt);
						rightPage.setSelectedIndex(2);
						
						infile.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					scrollUP();
				}
			}
		});
		File.add(loadM);
		
		saveM = new JMenuItem("Save Project");
		saveM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = openFile.showSaveDialog(null);
				
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					String dir = openFile.getSelectedFile().getAbsolutePath();

					try {
						BufferedWriter outfile = new BufferedWriter( new FileWriter(dir) );
						
						String txt = codeArea.getText();
						
						String line = "";
						for ( int i = 0; i < txt.length(); ++i ) {
							if ( txt.charAt(i) == '\n' ) {
								outfile.write(line);
								outfile.newLine();
								line = "";
							}
							else {
								line += txt.charAt(i);
							}
						}
						
						outfile.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					scrollUP();
				}
			}
		});
		File.add(saveM);
		
		JSeparator separator_2 = new JSeparator();
		File.add(separator_2);
		
		exitM = new JMenuItem("Exit");
		exitM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		File.add(exitM);
		
		registerFileM = new JMenu("Register File   ");
		menuBar.add(registerFileM);
		
		regB = new JRadioButton("Binary      ");
		regB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.changeRegisterBase("binary");
				regB.setSelected(true);
				regD.setSelected(false);
				regH.setSelected(false);
				scrollUP();
			}
		});
		registerFileM.add(regB);
		
		regD = new JRadioButton("Decimal        ");
		regD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.changeRegisterBase("decimal");
				regB.setSelected(false);
				regD.setSelected(true);
				regH.setSelected(false);
				scrollUP();
			}
		});
		registerFileM.add(regD);
		
		regH = new JRadioButton("Hex");
		regH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.changeRegisterBase("hex");
				regB.setSelected(false);
				regD.setSelected(false);
				regH.setSelected(true);
				scrollUP();
			}
		});
		registerFileM.add(regH);
		
		mnDataSegment = new JMenu("Data Segment   ");
		menuBar.add(mnDataSegment);
		
		dataB = new JRadioButton("Binary");
		dataB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.changeDataSegmentBase("binary");
				dataB.setSelected(true);
				dataD.setSelected(false);
				dataH.setSelected(false);
				scrollUP();
			}
		});
		mnDataSegment.add(dataB);
		
		dataD = new JRadioButton("Decimal        ");
		dataD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.changeDataSegmentBase("decimal");
				dataB.setSelected(false);
				dataD.setSelected(true);
				dataH.setSelected(false);
				scrollUP();
			}
		});
		mnDataSegment.add(dataD);
		
		dataH = new JRadioButton("Hex");
		dataH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MipsVM_GUI_Interface.changeDataSegmentBase("hex");
				dataB.setSelected(false);
				dataD.setSelected(false);
				dataH.setSelected(true);
				scrollUP();
			}
		});
		mnDataSegment.add(dataH);
		
		mnOthers = new JMenu("Others");
		menuBar.add(mnOthers);
		
		mntmVersion = new JMenuItem("Version     ");
		mntmVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";
				str += "Version 1 of Mips virtual machine\n";
				str += "instructions included in this version:              \n";
				str += "    R-Type:-\n";
				str += "        and  , or  , slt  , sll , jr.\n\n";
				str += "    I-Type:-\n";
				str += "        addi , ori , slti , lui , beq , bne , lw , sw.\n\n";
				str += "    J-Type:-\n";
				str += "        j.\n\n";
				
				showMessage( str , "Version");
			}
		});
		mnOthers.add(mntmVersion);
		
		mntmAuthers = new JMenuItem("Owners");
		mntmAuthers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";
				str += "Student 1: Ehab Fawzy                   \n";
				str += "Student 2: Khaled Ezzat                  \n";
				str += "Student 3: Hussien Feteha                   \n";
				str += "Student 4: Hatem Mamdoh                   \n";
				
				showMessage( str , "Owners");
			}
		});
		mnOthers.add(mntmAuthers);
		
		runFinal();
	}
	
	public static void showError( String _error ) {
		final JPanel panel = new JPanel();
	    JOptionPane.showMessageDialog(panel, _error, "Error", JOptionPane.ERROR_MESSAGE);   
	}
	
	public static void showMessage( String _message , String title ) {
		final JPanel panel = new JPanel();
	    JOptionPane.showMessageDialog(panel, _message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void writeRtype( String word ) {
		
		int colSize = 6;
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"opcode", "ra", "rb", "rd", "shmant", "funct"},
				{ word.substring(0 ,  6) , word.substring(6, 11), word.substring(11, 16) , word.substring(16, 21) , word.substring(21, 26), word.substring(26, 32)},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		/**
		 * 
		 * 	Center text alignment
		 * 
		 */
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for ( int i = 0; i < colSize; ++i ) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);	
		}
	}
	
	public static void writeItype( String word ) {

		int colSize = 4;
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"opcode", "ra", "rb", "imm"},
				{word.substring(0 ,  6) , word.substring(6, 11), word.substring(11, 16), word.substring(16, 32)},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		for ( int i = 0; i < colSize-1; ++i ) {
			table.getColumnModel().getColumn(i).setMaxWidth(75);
		}
		
		/**
		 * 
		 * 	Center text alignment
		 * 
		 */
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for ( int i = 0; i < colSize; ++i ) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);	
		}
		
	}
	
	public static void writeJtype( String word ) {
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"opcode", "dest"},
				{word.substring(0 ,  6), word.substring(6 , 32)},
			},
			new String[] {
				"New column", "New column"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		table.getColumnModel().getColumn(0).setMaxWidth(75);
		
		/**
		 * 
		 * 	Center text alignment
		 * 
		 */
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for ( int i = 0; i < 2; ++i ) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);	
		}
	}
	
	
	public static void clearTable() {
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
	}
	
	public static void scrollUP() {
		codeArea.setCaretPosition(0);
		regValue.setCaretPosition(0);
		regName.setCaretPosition(0);
		textSegmentValues.setCaretPosition(0);
		DataSegmentValues.setCaretPosition(0);
	}
	
	public static String eraseEmptyLines( String txt ) {
		String ret = "";
		String all[] = txt.split("\n");
		
		for ( int i = 0; i < all.length; ++i ) {
			if ( all[i].length() > 0 ) {
				ret += (all[i] + "\n");
			}
		}
		return ret;
	}
}
