
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.rs2.cache.Cache;
import com.rs2.model.config.ItemDef;
import com.rs2.util.Misc;

public class Jframe extends JFrame implements ActionListener {
	
	private JFrame frame;
	public String title = "Log viewer";
	private int width = 800;
	private int height = 600;
	
	static boolean onlyCoinTrades = true;
	boolean anonymousTrades = true;
	boolean takeTradePictures = true;
	
	private static JTable table_publicChat;

	private static final Object[][] rowData_publicChat = {{}};
    private static final Object[] columnNames_publicChat = {"Date", "Time", "Player", "Message"};
	
	private static JTable table_commands;

	private static final Object[][] rowData_commands = {{}};
    private static final Object[] columnNames_commands = {"Date", "Time", "Player", "Command"};
    
    private static JTable table_reports;

	private static final Object[][] rowData_reports = {{}};
    private static final Object[] columnNames_reports = {"Date", "Time", "Report ID", "Player", "Reported", "Reason", "Muted"};
	
	private static JTable table_privateChat;

	private static final Object[][] rowData_privateChat = {{}};
    private static final Object[] columnNames_privateChat = {"Date", "Time", "From", "To", "Message"};
	
	private static JTable table_trades;

	private static final Object[][] rowData_trades = {{}};
    private static final Object[] columnNames_trades = {"Date", "Time", "Trade ID", "Player1", "Player2"};
	
	private static final Object[][] rowData_I = {{}};
    private static final Object[] columnNames_I = {"", "Item", "ID", "Amount"};
    
    private static final Object[][] rowData_pList = {{}};
    private static final Object[] columnNames_pList = {"Show", "Player"};
	
	private static JTable table_Ip1;
	
	private static JTable table_Ip2;
	
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	
	private static JTable table_pList;
	
	JFrame playerList = new JFrame();
	
	DefaultTableModel tableModel = new DefaultTableModel() {

	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	
	private static JLabel lblFilterPast_1 = new JLabel();
	private static JLabel lblFilterPast_2 = new JLabel();
	private static JLabel lblFilterFuture_1 = new JLabel();
	private static JLabel lblFilterFuture_2 = new JLabel();
	
	private JTextField textField_pastTime = new JTextField();
	private JTextField textField_futureTime = new JTextField();
	
	private static JLabel trade1 = new JLabel("Player 1");
	private static JLabel trade2 = new JLabel("Player 2");
	
	public Jframe() {
		frame = new JFrame();
		
		frame.setTitle(title);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JLabel publicChatTitle = new JLabel("Public Chat");
		publicChatTitle.setBounds(300, 90, 100, 20);
		publicChatTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(publicChatTitle);
		JLabel privateChatTitle = new JLabel("Private Chat");
		privateChatTitle.setBounds(300, 420, 100, 20);
		privateChatTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(privateChatTitle);
		JLabel commandsTitle = new JLabel("Commands");
		commandsTitle.setBounds(300, 750, 100, 20);
		commandsTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(commandsTitle);
		JLabel tradesTitle = new JLabel("Trades");
		tradesTitle.setBounds(890, 90, 100, 20);
		tradesTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(tradesTitle);
		JLabel tradeWTitle = new JLabel("Trade Window");
		tradeWTitle.setBounds(1355, 90, 100, 20);
		tradeWTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(tradeWTitle);
		JLabel reportsTitle = new JLabel("Reports");
		reportsTitle.setBounds(1030, 750, 100, 20);
		reportsTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(reportsTitle);
		
		trade1.setBounds(1180, 420, 100, 20);
		trade1.setHorizontalAlignment(SwingConstants.CENTER);
		trade1.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(trade1);
		
		trade2.setBounds(1514, 420, 100, 20);
		trade2.setHorizontalAlignment(SwingConstants.CENTER);
		trade2.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.add(trade2);
		
		JScrollPane scrollPane_publicChat = new JScrollPane();
		scrollPane_publicChat.setBounds(10, 110, 720, 300);
		frame.add(scrollPane_publicChat);
		table_publicChat = new JTable();
		createTable(scrollPane_publicChat, table_publicChat, rowData_publicChat, columnNames_publicChat);
		table_publicChat.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_publicChat.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_publicChat.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_publicChat.getColumnModel().getColumn(3).setPreferredWidth(500);
		
		lblFilterPast_1.setBounds(40, 40, 70, 10);
		frame.add(lblFilterPast_1);
		lblFilterPast_2.setBounds(45, 55, 70, 10);
		frame.add(lblFilterPast_2);
		
		textField_pastTime.setBounds(100, 40, 35, 20);
		frame.add(textField_pastTime);
		textField_pastTime.setColumns(5);
		
		JButton btnPastHour = new JButton("Hour");
		btnPastHour.setBounds(140, 30, 70, 20);
		frame.add(btnPastHour);
		btnPastHour.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_pastTime.getText());
            	addPastTime("hour", i);
            	filterLogs();
            }
        });
		JButton btnPastDay = new JButton("Day");
		btnPastDay.setBounds(140, 55, 70, 20);
		frame.add(btnPastDay);
		btnPastDay.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_pastTime.getText());
            	addPastTime("day", i);
            	filterLogs();
            }
        });
		JButton btnPastWeek = new JButton("Week");
		btnPastWeek.setBounds(215, 30, 70, 20);
		frame.add(btnPastWeek);
		btnPastWeek.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_pastTime.getText());
            	addPastTime("week", i);
            	filterLogs();
            }
        });
		JButton btnPastMonth = new JButton("Month");
		btnPastMonth.setBounds(215, 55, 70, 20);
		frame.add(btnPastMonth);
		btnPastMonth.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_pastTime.getText());
            	addPastTime("month", i);
            	filterLogs();
            }
        });
		
		lblFilterFuture_1.setBounds(480, 40, 70, 10);
		frame.add(lblFilterFuture_1);
		lblFilterFuture_2.setBounds(485, 55, 70, 10);
		frame.add(lblFilterFuture_2);
		
		textField_futureTime.setBounds(540, 40, 35, 20);
		frame.add(textField_futureTime);
		textField_futureTime.setColumns(5);
		
		JButton btnFutureHour = new JButton("Hour");
		btnFutureHour.setBounds(580, 30, 70, 20);
		frame.add(btnFutureHour);
		btnFutureHour.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_futureTime.getText());
            	addFutureTime("hour", i);
            	filterLogs();
            }
        });
		JButton btnFutureDay = new JButton("Day");
		btnFutureDay.setBounds(580, 55, 70, 20);
		frame.add(btnFutureDay);
		btnFutureDay.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_futureTime.getText());
            	addFutureTime("day", i);
            	filterLogs();
            }
        });
		JButton btnFutureWeek = new JButton("Week");
		btnFutureWeek.setBounds(655, 30, 70, 20);
		frame.add(btnFutureWeek);
		btnFutureWeek.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_futureTime.getText());
            	addFutureTime("week", i);
            	filterLogs();
            }
        });
		JButton btnFutureMonth = new JButton("Month");
		btnFutureMonth.setBounds(655, 55, 70, 20);
		frame.add(btnFutureMonth);
		btnFutureMonth.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	int i = Integer.parseInt(textField_futureTime.getText());
            	addFutureTime("month", i);
            	filterLogs();
            }
        });
		
		JButton btnResetFilter = new JButton("Reset");
		btnResetFilter.setBounds(355, 40, 70, 20);
		frame.add(btnResetFilter);
		btnResetFilter.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	filterPast = earliestTime;
            	filterFuture = latestTime;
            	filterLogs();
            }
        });
		
		JScrollPane scrollPane_privateChat = new JScrollPane();
		scrollPane_privateChat.setBounds(10, 440, 820, 300);
		frame.add(scrollPane_privateChat);
		table_privateChat = new JTable();
		createTable(scrollPane_privateChat, table_privateChat, rowData_privateChat, columnNames_privateChat);
		table_privateChat.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_privateChat.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_privateChat.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_privateChat.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_privateChat.getColumnModel().getColumn(4).setPreferredWidth(500);
		
		JScrollPane scrollPane_Ip1 = new JScrollPane();
		scrollPane_Ip1.setBounds(1063, 440, 330, 300);
		frame.add(scrollPane_Ip1);
		table_Ip1 = new JTable();
		createTable(scrollPane_Ip1, table_Ip1, rowData_I, columnNames_I);
		table_Ip1.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_Ip1.getColumnModel().getColumn(1).setPreferredWidth(180);
		table_Ip1.getColumnModel().getColumn(2).setPreferredWidth(40);
		table_Ip1.getColumnModel().getColumn(3).setPreferredWidth(70);
		table_Ip1.setRowHeight(36);
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table_Ip1.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		table_Ip1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		JScrollPane scrollPane_Ip2 = new JScrollPane();
		scrollPane_Ip2.setBounds(1397, 440, 330, 300);
		frame.add(scrollPane_Ip2);
		table_Ip2 = new JTable();
		createTable(scrollPane_Ip2, table_Ip2, rowData_I, columnNames_I);
		table_Ip2.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_Ip2.getColumnModel().getColumn(1).setPreferredWidth(180);
		table_Ip2.getColumnModel().getColumn(2).setPreferredWidth(40);
		table_Ip2.getColumnModel().getColumn(3).setPreferredWidth(70);
		table_Ip2.setRowHeight(36);
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table_Ip2.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		table_Ip2.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		JScrollPane scrollPane_commands = new JScrollPane();
		scrollPane_commands.setBounds(10, 770, 720, 90);
		frame.add(scrollPane_commands);
		table_commands = new JTable();
		createTable(scrollPane_commands, table_commands, rowData_commands, columnNames_commands);
		table_commands.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_commands.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_commands.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_commands.getColumnModel().getColumn(3).setPreferredWidth(500);
		
		JScrollPane scrollPane_reports = new JScrollPane();
		scrollPane_reports.setBounds(750, 770, 620, 90);
		frame.add(scrollPane_reports);
		table_reports = new JTable();
		createTable(scrollPane_reports, table_reports, rowData_reports, columnNames_reports);
		table_reports.setModel(new DefaultTableModel( 
			    new Object [][] { 
			        {null, null},  
			    }, 
			    new String [] { 
			    		"Date", "Time", "Report ID", "Player", "Reported", "Reason", "Muted" 
			    } 
			) { 
			    Class[] types = new Class [] { 
			    		java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class 
			    }; 

			    public Class getColumnClass(int columnIndex) { 
			        return types [columnIndex]; 
			    } 
			    @Override
			    public boolean isCellEditable(int row, int column) {
			    	return false;
			    }
			});
		JCheckBox checkBox = new JCheckBox();
		table_reports.getColumn("Muted").setCellEditor(new DefaultCellEditor(checkBox));
		checkBox.setHorizontalAlignment(JCheckBox.CENTER);
		table_reports.getColumnModel().getColumn(0).setResizable(false);
		table_reports.getColumnModel().getColumn(1).setResizable(false);
		table_reports.getColumnModel().getColumn(2).setResizable(false);
		table_reports.getColumnModel().getColumn(3).setResizable(false);
		table_reports.getColumnModel().getColumn(4).setResizable(false);
		table_reports.getColumnModel().getColumn(5).setResizable(false);
		table_reports.getColumnModel().getColumn(6).setResizable(false);
		table_reports.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_reports.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_reports.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_reports.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_reports.getColumnModel().getColumn(4).setPreferredWidth(100);
		table_reports.getColumnModel().getColumn(5).setPreferredWidth(200);
		table_reports.getColumnModel().getColumn(6).setPreferredWidth(40);
		DefaultTableModel model = (DefaultTableModel) table_reports.getModel();
		model.removeRow(0);
		table_reports.getSelectionModel().addListSelectionListener(
		        new ListSelectionListener() {
		            public void valueChanged(ListSelectionEvent event) {
		                int viewRow = table_reports.getSelectedRow();
		                if (viewRow < 0) {
		                } else {
		                	int reportId = Integer.parseInt((String) table_reports.getModel().getValueAt(viewRow, 2));
		                	int yes = -1;
							yes = JOptionPane.showConfirmDialog(frame, "Apply filter for that report?", "Filter?", JOptionPane.YES_NO_OPTION);
							if(yes == 0){
								applyReportFilter(reportId);
							}else{
								return;
							}
		                }
		            }
		        });
		
		
		JScrollPane scrollPane_trades = new JScrollPane();
		scrollPane_trades.setBounds(750, 110, 370, 300);
		frame.add(scrollPane_trades);
		table_trades = new JTable();
		createTable(scrollPane_trades, table_trades, rowData_trades, columnNames_trades);
		table_trades.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_trades.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_trades.getColumnModel().getColumn(2).setPreferredWidth(50);
		table_trades.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_trades.getColumnModel().getColumn(4).setPreferredWidth(100);
		
		table_trades.getSelectionModel().addListSelectionListener(
		        new ListSelectionListener() {
		            public void valueChanged(ListSelectionEvent event) {
		                int viewRow = table_trades.getSelectedRow();
		                if (viewRow < 0) {
		                } else {
		                	int i = Integer.parseInt((String) table_trades.getModel().getValueAt(viewRow, 2));
		                	currentTrade = listOfTrades.get(i);
		                	updateTradeTables();
							tradePanel.repaint();
							if(takeTradePictures){
								final Runnable pennyStockPicker = new Runnable() {
									public void run() {
										saveToImage(tradePanel);
									}
								};
								EventQueue.invokeLater(pennyStockPicker);
							}
		                }
		            }
		        });
		
		tradePanel.setBounds(1150, 110, 488, 300);
	    frame.add(tradePanel);
	    tradePanel.repaint();
	    
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize(width,height);
		JMenuBar menuBar = new JMenuBar();
		frame.setVisible(true);
		menuBar.setBounds(0, 0, frame.getWidth(), 23);
		JButton btnFilterPlayers = new JButton("Filter Players");
		menuBar.add(btnFilterPlayers);
		btnFilterPlayers.addActionListener(this);
		frame.add(menuBar);
		frame.setLocationRelativeTo(null); //centering
		//frame.setVisible(true);
		//frame.setResizable(false); // resizeable frame
		
		playerList.setResizable(false);
		playerList.setTitle("Player list");
		playerList.setBounds(100, 100, 170, 670);
		playerList.setLayout(null);
		
		JScrollPane scrollPane_pList = new JScrollPane();
		scrollPane_pList.setBounds(10, 15, 140, 490);
		playerList.add(scrollPane_pList);
		table_pList = new JTable();
		createTable(scrollPane_pList, table_pList, rowData_pList, columnNames_pList);
		playerList.setLocation(frame.getWidth()/2-415/2, 0);
		table_pList.setModel(new DefaultTableModel( 
			    new Object [][] { 
			        {null, null},  
			    }, 
			    new String [] { 
			        "Show", "Player" 
			    } 
			) { 
			    Class[] types = new Class [] { 
			        java.lang.Boolean.class, java.lang.Object.class 
			    }; 

			    public Class getColumnClass(int columnIndex) { 
			        return types [columnIndex]; 
			    } 
			    @Override
			    public boolean isCellEditable(int row, int column) {
			    	if(column == 1)
			    		return false;
			    	return true;
			    }
			});
		JCheckBox checkBox1 = new JCheckBox();
		table_pList.getColumn("Show").setCellEditor(new DefaultCellEditor(checkBox1));
		checkBox1.setHorizontalAlignment(JCheckBox.CENTER);
		table_pList.getColumnModel().getColumn(0).setResizable(false);
		table_pList.getColumnModel().getColumn(1).setResizable(false);
		table_pList.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_pList.getColumnModel().getColumn(1).setPreferredWidth(100);
		DefaultTableModel model1 = (DefaultTableModel) table_pList.getModel();
		model1.removeRow(0);
		
		JButton btnSelectAll = new JButton("Select All");
		btnSelectAll.setBounds(10, 520, 70, 20);
		playerList.add(btnSelectAll);
		btnSelectAll.addActionListener(this);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(90, 520, 50, 20);
		playerList.add(btnClear);
		btnClear.addActionListener(this);
		
		JButton btnApplyChanges = new JButton("Apply Changes");
		btnApplyChanges.setBounds(35, 550, 90, 20);
		playerList.add(btnApplyChanges);
		btnApplyChanges.addActionListener(this);
	}
	
	void applyReportFilter(int reportId){
		Report report = listOfReports.get(reportId);
		String playerName1 = report.getReporter().getUsername();
		String playerName2 = report.getReported().getUsername();
		for(int i = 0; i < table_pList.getRowCount(); i++){
    		String s = (String) table_pList.getModel().getValueAt(i, 1);
    		boolean b = false;
    		if(s.equals(playerName1) || s.equals(playerName2))
    			b = true;
    		Player player = findPlayer(s);
			player.setShown(b);
			DefaultTableModel model = (DefaultTableModel) table_pList.getModel();
			model.setValueAt(b, i, 0);
    	}
		filterPast = report.getTime();
		filterFuture = report.getTime();
		addPastTime("minute", -1);
		addFutureTime("minute", 1);
		filterLogs();
		tradePanel.repaint();
	}
	
	void addPastTime(String s, int amount){
		long l = 0;
		if(s.equals("minute"))
			l = Time.addMinutes(filterPast, amount);
		if(s.equals("hour"))
			l = Time.addHours(filterPast, amount);
		if(s.equals("day"))
			l = Time.addDays(filterPast, amount);
		if(s.equals("week"))
			l = Time.addWeeks(filterPast, amount);
		if(s.equals("month"))
			l = Time.addMonths(filterPast, amount);
		if(Time.isBefore(l, earliestTime)){
			filterPast = earliestTime;
			JOptionPane.showMessageDialog(frame, "Now at earliest possible time.");
			return;
		}
		if(Time.isAfter(l, filterFuture)){
			filterPast = filterFuture;
			JOptionPane.showMessageDialog(frame, "Can't be more than the other filter.");
			return;
		}
		filterPast = l;
	}
	
	void addFutureTime(String s, int amount){
		long l = 0;
		if(s.equals("minute"))
			l = Time.addMinutes(filterFuture, amount);
		if(s.equals("hour"))
			l = Time.addHours(filterFuture, amount);
		if(s.equals("day"))
			l = Time.addDays(filterFuture, amount);
		if(s.equals("week"))
			l = Time.addWeeks(filterFuture, amount);
		if(s.equals("month"))
			l = Time.addMonths(filterFuture, amount);
		if(Time.isAfter(l, latestTime)){
			filterFuture = latestTime;
			JOptionPane.showMessageDialog(frame, "Now at latest possible time.");
			return;
		}
		if(Time.isBefore(l, filterPast)){
			filterFuture = filterPast;
			JOptionPane.showMessageDialog(frame, "Can't be less than the other filter.");
			return;
		}
		filterFuture = l;
	}
	
	void createTable(JScrollPane scrollpane, JTable table, Object[][] rowData, Object[] columnNames){
		DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		scrollpane.setViewportView(table);
		table.setModel(model);
		table.getTableHeader().setReorderingAllowed(false);
		model.removeRow(0);
		for(int i = 0; i < table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setResizable(false);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Filter Players"){
			playerList.setVisible(true);
		}
		if (e.getActionCommand()=="Select All"){
			DefaultTableModel model = (DefaultTableModel) table_pList.getModel();
			for(int i = 0; i < table_pList.getRowCount(); i++){
				model.setValueAt(true, i, 0);
			}
		}
		if (e.getActionCommand()=="Clear"){
			DefaultTableModel model = (DefaultTableModel) table_pList.getModel();
			for(int i = 0; i < table_pList.getRowCount(); i++){
				model.setValueAt(false, i, 0);
			}
		}
		if (e.getActionCommand()=="Apply Changes"){
			for(int i = 0; i < table_pList.getRowCount(); i++){
				boolean b = (boolean) table_pList.getModel().getValueAt(i, 0);
				String s = (String) table_pList.getModel().getValueAt(i, 1);
				Player player = findPlayer(s);
				player.setShown(b);
			}
			filterLogs();
			tradePanel.repaint();
		}
	}
	
	static long filterPast;
	static long filterFuture;
	
	static long earliestTime;
	static long latestTime;
	
	static void setTimes(long time){
		if(Time.isBefore(time, earliestTime) || earliestTime == 0)
			earliestTime = time;
		if(Time.isAfter(time, latestTime) || latestTime == 0)
			latestTime = time;
	}
	
	public static void filterLogs(){
		DefaultTableModel model;
		currentTrade = null;
		trade1.setText("Player 1");
		trade2.setText("Player 2");
		model = (DefaultTableModel) table_publicChat.getModel();
		model.setNumRows(0);
		model = (DefaultTableModel) table_privateChat.getModel();
		model.setNumRows(0);
		model = (DefaultTableModel) table_commands.getModel();
		model.setNumRows(0);
		model = (DefaultTableModel) table_trades.getModel();
		model.setNumRows(0);
		model = (DefaultTableModel) table_Ip1.getModel();
		model.setNumRows(0);
		model = (DefaultTableModel) table_Ip2.getModel();
		model.setNumRows(0);
    	for (PublicChat message : listOfPublicMessages) {
    		if(!message.getPlayer().isShown || !Time.isBetween(message.getTime(), filterPast, filterFuture))
    			continue;
    		Vector<String> newRow = new Vector<String>();
    		setTimes(message.getTime());
    		newRow.add(""+Time.getDate(message.getTime()));
    		newRow.add(""+Time.getTimeOfDay(message.getTime()));
    		newRow.add(message.getPlayer().getUsername());
    		newRow.add(message.getMessage());
            addNewRowToTable(newRow, table_publicChat, false);
    	}
    	for (PrivateChat message : listOfPrivateMessages) {
    		if(!message.getFrom().isShown && !message.getTo().isShown || !Time.isBetween(message.getTime(), filterPast, filterFuture))
    			continue;
    		Vector<String> newRow = new Vector<String>();
    		setTimes(message.getTime());
    		newRow.add(""+Time.getDate(message.getTime()));
    		newRow.add(""+Time.getTimeOfDay(message.getTime()));
    		newRow.add(message.getFrom().getUsername());
    		newRow.add(message.getTo().getUsername());
    		newRow.add(message.getMessage());
            addNewRowToTable(newRow, table_privateChat, false);
    	}
    	int r1 = 0;
    	for (Report report : listOfReports) {
    		int pos = r1;
    		r1++;
    		if(!report.getReporter().isShown && !report.getReported().isShown || !Time.isBetween(report.getTime(), filterPast, filterFuture))
    			continue;
    		Vector newRow = new Vector();
    		setTimes(report.getTime());
    		newRow.add(""+Time.getDate(report.getTime()));
    		newRow.add(""+Time.getTimeOfDay(report.getTime()));
    		newRow.add(""+pos);
    		newRow.add(report.getReporter().getUsername());
    		newRow.add(report.getReported().getUsername());
    		newRow.add(Report.reportNames[report.getRule()]);
    		newRow.add(report.isMuted());
            addNewRowToTable(newRow, table_reports, false);
    	}
    	for (Command command : listOfCommands) {
    		if(!command.getPlayer().isShown || !Time.isBetween(command.getTime(), filterPast, filterFuture))
    			continue;
    		Vector<String> newRow = new Vector<String>();
    		setTimes(command.getTime());
    		newRow.add(""+Time.getDate(command.getTime()));
    		newRow.add(""+Time.getTimeOfDay(command.getTime()));
    		newRow.add(command.getPlayer().getUsername());
    		newRow.add(command.getCommand());
            addNewRowToTable(newRow, table_commands, false);
    	}
    	int c12 = 0;
    	for (Trade trade : listOfTrades) {
    		int pos = c12;
    		c12++;
    		if(!trade.getPlayer1().isShown && !trade.getPlayer2().isShown || !Time.isBetween(trade.getTime(), filterPast, filterFuture))
    			continue;
    		Vector<String> newRow = new Vector<String>();
    		setTimes(trade.getTime());
    		newRow.add(""+Time.getDate(trade.getTime()));
    		newRow.add(""+Time.getTimeOfDay(trade.getTime()));
    		newRow.add(""+pos);
    		newRow.add(trade.getPlayer1().getUsername());
    		newRow.add(trade.getPlayer2().getUsername());
            addNewRowToTable(newRow, table_trades, false);
    		if(currentTrade == null){
    			currentTrade = trade;
    			int row = table_trades.getRowCount()-1;
    			table_trades.scrollRectToVisible(table_trades.getCellRect(row, 0, true));
    			table_trades.setRowSelectionInterval(row, row);
    		}
    	}
    	updateTradeTables();
    	if(filterPast == 0)
    		filterPast = earliestTime;
    	if(filterFuture == 0)
    		filterFuture = latestTime;
    	lblFilterPast_1.setText(""+Time.getDate(filterPast));
    	lblFilterPast_2.setText(""+Time.getTimeOfDay(filterPast));
    	lblFilterFuture_1.setText(""+Time.getDate(filterFuture));
    	lblFilterFuture_2.setText(""+Time.getTimeOfDay(filterFuture));
    }
	
	static void updateTradeTables(){
		DefaultTableModel model;
		model = (DefaultTableModel) table_Ip1.getModel();
		model.setNumRows(0);
		model = (DefaultTableModel) table_Ip2.getModel();
		model.setNumRows(0);
		if(currentTrade != null){
    		if(currentTrade.getPlayer1Items() != null){
        		for(int c = 0; c < 28; c++){//player1
        			if(currentTrade.getPlayer1Items()[c] == null)
        				break;
        			Vector newRow = new Vector();
        			newRow.add(itemImage[getItemSprite(currentTrade.getPlayer1Items()[c].getId(), 1)]);
        			ItemDef iDef = ItemDef.forId(currentTrade.getPlayer1Items()[c].getId());
        			String itemName = iDef.name;
					if(iDef.certTemplateID != -1){
						ItemDef item_2 = ItemDef.forId(iDef.certID);
						itemName = item_2.name;
					}
            		newRow.add(itemName);
            		newRow.add(""+currentTrade.getPlayer1Items()[c].getId());
            		newRow.add(""+currentTrade.getPlayer1Items()[c].getAmount());
                    addNewRowToTable(newRow, table_Ip1, false);
        		}
        		for(int c = 0; c < 28; c++){//player2
        			if(currentTrade.getPlayer2Items()[c] == null)
        				break;
        			Vector newRow = new Vector();
        			newRow.add(itemImage[getItemSprite(currentTrade.getPlayer2Items()[c].getId(), 1)]);
        			ItemDef iDef = ItemDef.forId(currentTrade.getPlayer2Items()[c].getId());
        			String itemName = iDef.name;
					if(iDef.certTemplateID != -1){
						ItemDef item_2 = ItemDef.forId(iDef.certID);
						itemName = item_2.name;
					}
            		newRow.add(itemName);
            		newRow.add(""+currentTrade.getPlayer2Items()[c].getId());
            		newRow.add(""+currentTrade.getPlayer2Items()[c].getAmount());
            		addNewRowToTable(newRow, table_Ip2, false);
        		}
        		trade1.setText(currentTrade.getPlayer1().getUsername()+"'s Offer");
        		trade2.setText(currentTrade.getPlayer2().getUsername()+"'s Offer");
    		}
    	}
	}
	
	public static void initUI() {
		try{
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel"); //Substance<NAME>LookAndFeel
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static Trade currentTrade;
	
	static Cache cache;
	static int totalItems = 0;
	
    public static void main(String[] args) {
    	Cache.load();
		cache = Cache.getSingleton();
        loadItems();
        loadItemImages();
		initUI();
        new Jframe();
        /*loadPublicChat();
        loadPrivateChat();
        loadReports();
        loadCommands();
        loadTrades();*/
        loadLogs();
        loadImages();
        initPlayerlist();
        filterLogs();
    }
    
    public static void initPlayerlist(){
    	for (Player player : listOfPlayers) {
    		Vector newRow = new Vector();
    		newRow.add(new Boolean(true));
    		newRow.add(player.getUsername());
            addNewRowToTable(newRow, table_pList, false);
    	}
    }
    
    public static void addNewRowToTable(Vector newRow, JTable table, boolean focus){
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
    	model.addRow(newRow);
		if(focus){
			int row = table.getRowCount()-1;
			table.scrollRectToVisible(table.getCellRect(row, 0, true));
			table.setRowSelectionInterval(row, row);
		}
    }
    
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static Image tradeBg;
    MyPanel tradePanel = new MyPanel();
    
    private void saveToImage(MyPanel table)  
    {  
        int w = table.getWidth();  
        int h = table.getHeight();  
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = bi.createGraphics();  
        table.paint(g2);  
        g2.dispose();  
        try  
        {  
            ImageIO.write(bi, "png", new File("./trades/"+listOfTrades.indexOf(currentTrade)+".png"));  
        }  
        catch(IOException ioe)  
        {  
            System.out.println("write: " + ioe.getMessage());  
        }  
    }
    
    public static void loadItems(){
		ItemDef.loadItemDat();
		totalItems = cache.getIndexTable().getItemDefinitionIndices().length;
		itemImage = new Image[totalItems];
	}
    
    public static Image itemImage[];
    
    public static void loadItemImages(){
		for(int id = 0; id < totalItems; id++){
			if(FileOperations.FileExists("./377 item images/"+id+".png")){
				Image image = toolkit.getImage("./377 item images/"+id+".png");
				image = ImageHandler.makeColorTransparent(image, new Color(0xff00ff));
				itemImage[id] = image;
			}
		}
	}
    
    public static void loadImages(){
    	if(FileOperations.FileExists("./sprites/trade.png")){
			tradeBg = toolkit.getImage("./sprites/trade.png");
		}
    }
    
    public static int getItemSprite(int id, int amount)
	{
		amount = 500;
		ItemDef item = ItemDef.forId(id);
		if(amount == 1 || item.stackAmounts == null)
			return id;
		if(amount > 1)
		{
			int i1 = -1;
			for(int j1 = 0; j1 < item.stackAmounts.length; j1++)
				if(amount >= item.stackAmounts[j1] && item.stackAmounts[j1] != 0)
					i1 = item.stackIDs[j1];
			if(i1 != -1)
				return i1;
		}
		return id;
	}
    
    public static ArrayList<Player> listOfPlayers = new ArrayList<Player>();
    
	public static ArrayList<PublicChat> listOfPublicMessages = new ArrayList<PublicChat>();
	public static ArrayList<PrivateChat> listOfPrivateMessages = new ArrayList<PrivateChat>();
	public static ArrayList<Report> listOfReports = new ArrayList<Report>();
	public static ArrayList<Trade> listOfTrades = new ArrayList<Trade>();
	public static ArrayList<Command> listOfCommands = new ArrayList<Command>();
    
    static Player findPlayer(String name){
		for (Player players : listOfPlayers) {
			if(players.getUsername().toLowerCase().equals(name.toLowerCase())){
				return players;
			}
		}
		Player player = new Player(name);
		listOfPlayers.add(player);
		return player;
    }
    
    public static void loadLogs(){
    	File folder = new File("./move/");
		File[] listOfLogFolders = folder.listFiles();
		for (File logFolder : listOfLogFolders) {
			File[] logFolder2 = logFolder.listFiles();
			for (File logFile : logFolder2) {
				if(logFile.getName().toLowerCase().equals("public chat.txt"))
					loadPublicChat(logFile);
				if(logFile.getName().toLowerCase().equals("private chat.txt"))
					loadPrivateChat(logFile);
				if(logFile.getName().toLowerCase().equals("reports.txt"))
					loadReports(logFile);
				if(logFile.getName().toLowerCase().equals("commands.txt"))
					loadCommands(logFile);
				if(logFile.getName().toLowerCase().equals("trades.txt"))
					loadTrades(logFile);
			}
		}
		Collections.sort(listOfPublicMessages, new Comparator<PublicChat>() {
		    public int compare(PublicChat f1, PublicChat f2) {
		    	long lvl1 = f1.getTime();
		    	long lvl2 = f2.getTime();
		    	return Long.compare(lvl1, lvl2);
		    }
		});
		Collections.sort(listOfPrivateMessages, new Comparator<PrivateChat>() {
		    public int compare(PrivateChat f1, PrivateChat f2) {
		    	long lvl1 = f1.getTime();
		    	long lvl2 = f2.getTime();
		    	return Long.compare(lvl1, lvl2);
		    }
		});
		Collections.sort(listOfReports, new Comparator<Report>() {
		    public int compare(Report f1, Report f2) {
		    	long lvl1 = f1.getTime();
		    	long lvl2 = f2.getTime();
		    	return Long.compare(lvl1, lvl2);
		    }
		});
		Collections.sort(listOfCommands, new Comparator<Command>() {
		    public int compare(Command f1, Command f2) {
		    	long lvl1 = f1.getTime();
		    	long lvl2 = f2.getTime();
		    	return Long.compare(lvl1, lvl2);
		    }
		});
		Collections.sort(listOfTrades, new Comparator<Trade>() {
		    public int compare(Trade f1, Trade f2) {
		    	long lvl1 = f1.getTime();
		    	long lvl2 = f2.getTime();
		    	return Long.compare(lvl1, lvl2);
		    }
		});
    }
    
    public static void loadPublicChat(File f){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader(f));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player player = findPlayer(lineSeparated[1]);
				PublicChat publicChat = new PublicChat(player, Long.parseLong(lineSeparated[0]), lineSeparated[5], Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]));
				listOfPublicMessages.add(publicChat);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadPrivateChat(File f){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader(f));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player from = findPlayer(lineSeparated[1]);
				Player to = findPlayer(lineSeparated[5]);
				PrivateChat privateChat = new PrivateChat(from, Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]), to, Integer.parseInt(lineSeparated[6]), Integer.parseInt(lineSeparated[7]), Integer.parseInt(lineSeparated[8]), Long.parseLong(lineSeparated[0]), lineSeparated[9]);
				listOfPrivateMessages.add(privateChat);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadReports(File f){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader(f));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player reporter = findPlayer(lineSeparated[1]);
				Player reported = findPlayer(lineSeparated[5]);
				Report report = new Report(reporter, Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]), reported, Integer.parseInt(lineSeparated[6]), Integer.parseInt(lineSeparated[7]), Integer.parseInt(lineSeparated[8]), Long.parseLong(lineSeparated[0]), Integer.parseInt(lineSeparated[9]), (Integer.parseInt(lineSeparated[10]) == 1 ? true : false));
				listOfReports.add(report);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadCommands(File f){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader(f));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player player = findPlayer(lineSeparated[1]);
				Command command = new Command(player, Long.parseLong(lineSeparated[0]), lineSeparated[5], Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]));
				listOfCommands.add(command);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadTrades(File f){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader(f));
			String line;
			Trade trade = null;
			String[] lineSeparated;
			boolean tradeStarted = false;
			boolean firstPlayer = false;
			Item[] player1Items = new Item[28];
			Item[] player2Items = new Item[28];
			int p1 = 0;
			int p2 = 0;
			while ((line = buf.readLine()) != null) {
				if(line.equals("NEW TRADE") && !tradeStarted){
					tradeStarted = true;
					continue;
				}
				if(line.equals("END") && tradeStarted){
					if(trade != null){
						trade.setPlayer1Items(player1Items);
						trade.setPlayer2Items(player2Items);
					}else
						System.out.println("Error: trade was null while ending trade");
					if(player1Items != null && player2Items != null && onlyCoinTrades){
						if(trade.getPlayer1Items()[0] != null && trade.getPlayer2Items()[0] != null){
							if(trade.getPlayer1Items()[0].getId() == 995 && trade.getPlayer1Items()[1] == null){
								listOfTrades.add(trade);
							}
							if(trade.getPlayer2Items()[0].getId() == 995 && trade.getPlayer2Items()[1] == null){
								listOfTrades.add(trade);
							}
						}
					}
					//listOfTrades.add(trade);
					tradeStarted = false;
					trade = null;
					player1Items = new Item[28];
					player2Items = new Item[28];
					p1 = 0;
					p2 = 0;
					continue;
				}
				if(line.equals("END") && !tradeStarted){
					System.out.println("Parsing error with trades: ending trade while there is no trade to end!");
					System.exit(0);
				}
				if(line.equals("NEW TRADE") && tradeStarted){
					System.out.println("Parsing error with trades: new trade starting while earlier one havent ended!");
					System.exit(0);
				}
				if(tradeStarted){
					if(line.equals("Player 1")){
						firstPlayer = true;
						continue;
					}
					if(line.equals("Player 2")){
						firstPlayer = false;
						continue;
					}
					lineSeparated = line.split("§");
					if(lineSeparated.length == 9){
						Player player1 = findPlayer(lineSeparated[1]);
						Player player2 = findPlayer(lineSeparated[5]);
						trade = new Trade(player1, Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]), player2, Integer.parseInt(lineSeparated[6]), Integer.parseInt(lineSeparated[7]), Integer.parseInt(lineSeparated[8]), Long.parseLong(lineSeparated[0]));
					}
					if(lineSeparated.length == 2){
						if(firstPlayer){
							player1Items[p1] = new Item(Integer.parseInt(lineSeparated[0]), Integer.parseInt(lineSeparated[1]));
							p1++;
						}else{
							player2Items[p2] = new Item(Integer.parseInt(lineSeparated[0]), Integer.parseInt(lineSeparated[1]));
							p2++;
						}
					}	
				}
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /*public static void loadPublicChat(){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader("./logs/public chat.txt"));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player player = findPlayer(lineSeparated[1]);
				PublicChat publicChat = new PublicChat(player, Long.parseLong(lineSeparated[0]), lineSeparated[5], Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]));
				listOfPublicMessages.add(publicChat);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadPrivateChat(){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader("./logs/private chat.txt"));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player from = findPlayer(lineSeparated[1]);
				Player to = findPlayer(lineSeparated[5]);
				PrivateChat privateChat = new PrivateChat(from, Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]), to, Integer.parseInt(lineSeparated[6]), Integer.parseInt(lineSeparated[7]), Integer.parseInt(lineSeparated[8]), Long.parseLong(lineSeparated[0]), lineSeparated[9]);
				listOfPrivateMessages.add(privateChat);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadReports(){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader("./logs/reports.txt"));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player reporter = findPlayer(lineSeparated[1]);
				Player reported = findPlayer(lineSeparated[5]);
				Report report = new Report(reporter, Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]), reported, Integer.parseInt(lineSeparated[6]), Integer.parseInt(lineSeparated[7]), Integer.parseInt(lineSeparated[8]), Long.parseLong(lineSeparated[0]), Integer.parseInt(lineSeparated[9]), (Integer.parseInt(lineSeparated[10]) == 1 ? true : false));
				listOfReports.add(report);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadCommands(){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader("./logs/commands.txt"));
			String line;
			String[] lineSeparated;
			while ((line = buf.readLine()) != null) {
				lineSeparated = line.split("§");
				Player player = findPlayer(lineSeparated[1]);
				Command command = new Command(player, Long.parseLong(lineSeparated[0]), lineSeparated[5], Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]));
				listOfCommands.add(command);
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void loadTrades(){
    	try {
			BufferedReader buf = new BufferedReader(new FileReader("./logs/trades.txt"));
			String line;
			Trade trade = null;
			String[] lineSeparated;
			boolean tradeStarted = false;
			boolean firstPlayer = false;
			Item[] player1Items = new Item[28];
			Item[] player2Items = new Item[28];
			int p1 = 0;
			int p2 = 0;
			while ((line = buf.readLine()) != null) {
				if(line.equals("NEW TRADE") && !tradeStarted){
					tradeStarted = true;
					continue;
				}
				if(line.equals("END") && tradeStarted){
					if(trade != null){
						trade.setPlayer1Items(player1Items);
						trade.setPlayer2Items(player2Items);
					}else
						System.out.println("Error: trade was null while ending trade");
					listOfTrades.add(trade);
					tradeStarted = false;
					trade = null;
					player1Items = new Item[28];
					player2Items = new Item[28];
					p1 = 0;
					p2 = 0;
					continue;
				}
				if(line.equals("END") && !tradeStarted){
					System.out.println("Parsing error with trades: ending trade while there is no trade to end!");
					System.exit(0);
				}
				if(line.equals("NEW TRADE") && tradeStarted){
					System.out.println("Parsing error with trades: new trade starting while earlier one havent ended!");
					System.exit(0);
				}
				if(tradeStarted){
					if(line.equals("Player 1")){
						firstPlayer = true;
						continue;
					}
					if(line.equals("Player 2")){
						firstPlayer = false;
						continue;
					}
					lineSeparated = line.split("§");
					if(lineSeparated.length == 9){
						Player player1 = findPlayer(lineSeparated[1]);
						Player player2 = findPlayer(lineSeparated[5]);
						trade = new Trade(player1, Integer.parseInt(lineSeparated[2]), Integer.parseInt(lineSeparated[3]), Integer.parseInt(lineSeparated[4]), player2, Integer.parseInt(lineSeparated[6]), Integer.parseInt(lineSeparated[7]), Integer.parseInt(lineSeparated[8]), Long.parseLong(lineSeparated[0]));
					}
					if(lineSeparated.length == 2){
						if(firstPlayer){
							player1Items[p1] = new Item(Integer.parseInt(lineSeparated[0]), Integer.parseInt(lineSeparated[1]));
							p1++;
						}else{
							player2Items[p2] = new Item(Integer.parseInt(lineSeparated[0]), Integer.parseInt(lineSeparated[1]));
							p2++;
						}
					}	
				}
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
    
    public class MyPanel extends JPanel {
    	
    	void drawString(String s, int x, int y, boolean shadowed, boolean centered, Graphics g){
    		Color c = g.getColor();
    		if(shadowed){
    			g.setColor(Color.black);
    			if(!centered)
    				g.drawString(s,x+1,y+1);
    			else
    				g.drawString(s,x+1-(metr.stringWidth(s)/2),y+1);
    		}
        	g.setColor(c);
        	if(!centered)
        		g.drawString(s,x,y);
        	else
        		g.drawString(s,x-(metr.stringWidth(s)/2),y);
    	}
    	
    	Font normal;
    	FontMetrics metr;
    	
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        normal = new Font("Arial", Font.PLAIN, 12);
	        metr = getFontMetrics(normal);
	        g.setFont(normal);
	        g.drawImage(tradeBg, 0, 0, this);
	        if(currentTrade != null){
	        	g.setColor(Color.yellow);
	        	drawString(""+Time.getDate(currentTrade.getTime()), 10, 20, true, false, g);
	        	drawString(""+Time.getTimeOfDay(currentTrade.getTime()), 430, 20, true, false, g);
	        	if(!anonymousTrades){
	        		drawString(currentTrade.getPlayer1().getUsername()+"'s Offer", 119, 45, true, true, g);
	        		drawString(currentTrade.getPlayer2().getUsername()+"'s Offer", 361, 45, true, true, g);
	        	}else{
	        		drawString("Player 1's Offer", 119, 45, true, true, g);
	        		drawString("Player 2's Offer", 361, 45, true, true, g);
	        	}
	        	if(currentTrade.getPlayer1Items() != null){
	        		for(int row = 0; row < 28; row++){//player1
	        			if(currentTrade.getPlayer1Items()[row] == null)
	        				break;
	    	        	g.drawImage(itemImage[getItemSprite(currentTrade.getPlayer1Items()[row].getId(), currentTrade.getPlayer1Items()[row].getAmount())], 20+42*(row%4), 51+33*(row/4), this);
	    	        	ItemDef iDef = ItemDef.forId(currentTrade.getPlayer1Items()[row].getId());
	    	        	if(iDef.stackable || iDef.noted)
	    	        		drawString(Misc.formatValue(currentTrade.getPlayer1Items()[row].getAmount()), 20+42*(row%4), 60+33*(row/4), true, false, g);
	    	        }
	        	}
	        	if(currentTrade.getPlayer2Items() != null){
	        		for(int row = 0; row < 28; row++){//player2
	        			if(currentTrade.getPlayer2Items()[row] == null)
	        				break;
	    	        	g.drawImage(itemImage[getItemSprite(currentTrade.getPlayer2Items()[row].getId(), currentTrade.getPlayer2Items()[row].getAmount())], 309+42*(row%4), 51+33*(row/4), this);
	    	        	ItemDef iDef = ItemDef.forId(currentTrade.getPlayer2Items()[row].getId());
	    	        	if(iDef.stackable || iDef.noted)
	    	        		drawString(Misc.formatValue(currentTrade.getPlayer2Items()[row].getAmount()), 309+42*(row%4), 60+33*(row/4), true, false, g);
	    	        }
	        	}
	        }//current trade != null above
	    }
	}
    
	
}
