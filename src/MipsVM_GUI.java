import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
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
import javax.swing.border.LineBorder;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.ComponentOrientation;

public class MipsVM_GUI {

	private JFrame frmMipsvm;
	private JSeparator separator;
	private JSplitPane splitWindow;
	private JTabbedPane rightPage , leftPage;
	private JScrollPane registerScroll ;
	private JTextArea regValue , regName;
	private JScrollPane TextSegment;
	private JScrollPane DataSegment;
	private JTextArea textSegmentValues;
	private JTextArea DataSegmentValues;
	private JTextArea codeArea;
	private JTextField txtAdds;
	private JTextField txtRType;
	private JSeparator separator_1;
	private JTextArea note;
	private JSeparator separator_2;
	private JButton btnNewButton_1;
	private JButton btnRun;
	private JTextField textField;
	private JTable table;
	private TextLineNumber tln , textSegmentTLN , dataSegmentTLN;
	private JScrollPane TextEditor;
	
	
	
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
	}

	
	public void staticInit() {
		String registerName = "";
		for ( int i = 0; i < 32; ++i ) {
			if ( i < 10 ) {
				registerName += ( " R   " + i + "   = \n" );
			}
			else {
				registerName += (" R " + i + "   = \n");
			}
		}
		regName.setText(registerName);
		
		
		String registerValues = "";
		for ( int i = 0; i < 32; ++i ) {
			registerValues += "0\n";
		}
		regValue.setText(registerValues);
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
		
		txtRType = new JTextField();
		txtRType.setHorizontalAlignment(SwingConstants.CENTER);
		txtRType.setFont(new Font("Simplified Arabic", Font.PLAIN, 16));
		txtRType.setEditable(false);
		txtRType.setColumns(10);
		txtRType.setBackground(SystemColor.menu);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		note = new JTextArea();
		note.setFont(new Font("Monospaced", Font.PLAIN, 14));
		note.setBorder(new LineBorder(new Color(0, 0, 0)));
		note.setEditable(false);
		note.setBackground(SystemColor.menu);
		
		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		btnNewButton_1 = new JButton("Run All");
		btnNewButton_1.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		btnRun = new JButton("Next Step");
		btnRun.setFont(new Font("Vrinda", Font.BOLD, 14));
		
		JLabel lblProgramCounter = DefaultComponentFactory.getInstance().createLabel("PC");
		lblProgramCounter.setBackground(SystemColor.menu);
		lblProgramCounter.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgramCounter.setFont(new Font("Vrinda", Font.BOLD, 16));
		
		textField = new JTextField();
		textField.setFont(new Font("Vrinda", Font.BOLD, 18));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		textField.setBackground(SystemColor.menu);
		textField.setColumns(10);
		
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
										.addComponent(txtRType, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblProgramCounter, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
									.addGap(8))
								.addComponent(lblInstruct, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtAdds, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
									.addGap(4)))
							.addGap(1)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(11)
									.addComponent(note, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
										.addComponent(btnRun, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(table, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))))
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
									.addComponent(table, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
											.addComponent(btnRun, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
										.addComponent(separator_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
										.addComponent(note, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
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
										.addComponent(txtRType, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
		regValue.setForeground(new Color(0, 0, 255));
		regValue.setDisabledTextColor(new Color(0, 0, 255));
		regValue.setMargin(new Insets(2, 5, 2, 2));
		regValue.setEditable(false);
		registerScroll.setViewportView(regValue);
		
		regName = new JTextArea();
		regName.setDisabledTextColor(new Color(0, 0, 139));
		regName.setEnabled(false);
		regName.setEditable(false);
		regName.setBackground(new Color(240, 248, 255));
		regName.setColumns(5);
		registerScroll.setRowHeaderView(regName);
		splitWindow.setDividerLocation(200);
		
		frmMipsvm.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmMipsvm.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		//frmMipsvm.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		/*staticInit();*/ runFinal();
	}
}
