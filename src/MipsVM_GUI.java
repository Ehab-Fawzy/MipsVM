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
import java.awt.SystemColor;

public class MipsVM_GUI {

	private JFrame frmMipsvm;
	private JSeparator separator;

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
	
	public void staticInit() {
		
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
		
		JSplitPane splitWindow = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(frmMipsvm.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(splitWindow, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitWindow, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addGap(121))
		);
		
		JTabbedPane rightPage = new JTabbedPane(JTabbedPane.TOP);
		splitWindow.setRightComponent(rightPage);
		
		JTextArea textSegment = new JTextArea();
		rightPage.addTab("Text Segment", null, textSegment, null);
		
		JTextArea textArea = new JTextArea();
		rightPage.addTab("Data Segment", null, textArea, null);
		
		JTabbedPane leftPage = new JTabbedPane(JTabbedPane.TOP);
		leftPage.setVerifyInputWhenFocusTarget(false);
		leftPage.setForeground(Color.BLACK);
		splitWindow.setLeftComponent(leftPage);
		
		JScrollPane registerScroll = new JScrollPane();
		registerScroll.setVerifyInputWhenFocusTarget(false);
		leftPage.addTab("Register File ", null, registerScroll, null);
		
		JTextArea regValue = new JTextArea();
		registerScroll.setViewportView(regValue);
		
		JTextArea regName = new JTextArea();
		regName.setBackground(SystemColor.inactiveCaptionBorder);
		regName.setColumns(5);
		registerScroll.setRowHeaderView(regName);
		splitWindow.setDividerLocation(200);
		
		frmMipsvm.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmMipsvm.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		//staticInit();
	}
}
