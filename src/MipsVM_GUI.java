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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.border.LineBorder;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

public class MipsVM_GUI {

	private JFrame frmMipsvm;
	private JSeparator separator;
	private JSplitPane splitWindow;
	private JTabbedPane rightPage , leftPage;
	private JScrollPane registerScroll ;
	public static JTextArea regValue , regName;
	private JScrollPane TextSegment;
	private JScrollPane DataSegment;
	public static JTextArea textSegmentValues , DataSegmentValues;
	public static JTextArea codeArea;
	public static JTextField txtAdds;
	public static JTextField typeTxt;
	private JSeparator separator_1;
	public static JButton runAll , nextStep , compile;
	public static JTextField pcTxt;
	private JTable table;
	private TextLineNumber tln , textSegmentTLN , dataSegmentTLN;
	private JScrollPane TextEditor;
	private JMenuItem newM;
	private JMenuItem loadM;
	private JMenuItem saveM;
	private JMenuItem exitM;
	private JMenu registerFileM;
	private JMenu mnDataSegment;
	
	
	
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
		txtAdds.setFont(new Font("Simplified Arabic", Font.PLAIN, 16));
		txtAdds.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdds.setEditable(false);
		txtAdds.setBackground(SystemColor.menu);
		txtAdds.setColumns(10);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Instruction Type");
		lblNewJgoodiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesLabel.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		typeTxt = new JTextField();
		typeTxt.setHorizontalAlignment(SwingConstants.CENTER);
		typeTxt.setFont(new Font("Simplified Arabic", Font.PLAIN, 16));
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
		pcTxt.setFont(new Font("Vrinda", Font.BOLD, 18));
		pcTxt.setHorizontalAlignment(SwingConstants.CENTER);
		pcTxt.setEditable(false);
		pcTxt.setBackground(SystemColor.menu);
		pcTxt.setColumns(10);
		
		table = new JTable();
		table.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		table.setAlignmentX(Component.RIGHT_ALIGNMENT);
		table.setEnabled(false);
		table.setBackground(SystemColor.menu);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(35);
		/*table.setModel(new DefaultTableModel(
			new Object[][] {
				{"opcode", "ra", "rb", "rd", "shmant", "funct"},
				{"000000", "10001", "10010", "10000", "00000", "10000"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});*/
		
		compile = new JButton("Compile");
		compile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MipsVM_GUI_Interface.init();
				
				textSegmentValues.setText( codeArea.getText() );
				boolean validParse = MipsVM_GUI_Interface.parseAll();
				
				if ( validParse ) {
					nextStep.setEnabled(true);
					runAll.setEnabled(true);
					compile.setEnabled(false);
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
										.addComponent(lblProgramCounter, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
										.addComponent(pcTxt, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
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
										.addComponent(pcTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
		textSegmentTLN = new TextLineNumber(textSegmentValues);
		TextSegment.setViewportView(textSegmentValues);
		
		DataSegment = new JScrollPane();
		rightPage.addTab("Data Segment", null, DataSegment, null);
		
		DataSegmentValues = new JTextArea();
		DataSegmentValues.setForeground(new Color(0, 0, 255));
		DataSegmentValues.setDisabledTextColor(new Color(0, 0, 255));
		DataSegmentValues.setMargin(new Insets(2, 5, 2, 2));
		DataSegmentValues.setFont(new Font("Monospaced", Font.PLAIN, 16));
		DataSegmentValues.setDisabledTextColor(new Color(0, 0, 139));
		dataSegmentTLN = new TextLineNumber(DataSegmentValues , true);
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
		//TextEditor.setRowHeaderView(tln);
		
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
		
		JMenu File = new JMenu("  File   ");
		menuBar.add(File);
		
		newM = new JMenuItem("New");
		File.add(newM);
		
		JSeparator separator_3 = new JSeparator();
		File.add(separator_3);
		
		loadM = new JMenuItem("Load Project     ");
		File.add(loadM);
		
		saveM = new JMenuItem("Save Project");
		File.add(saveM);
		
		JSeparator separator_2 = new JSeparator();
		File.add(separator_2);
		
		exitM = new JMenuItem("Exit");
		File.add(exitM);
		
		registerFileM = new JMenu("Register File   ");
		menuBar.add(registerFileM);
		
		mnDataSegment = new JMenu("Data Segment");
		menuBar.add(mnDataSegment);
		
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
}
