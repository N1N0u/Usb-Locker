import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.AWTException;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.formdev.flatlaf.util.DerivedColor;
import com.k33ptoo.components.KButton;

import table.Table;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;
import javax.swing.JTextField;


public class Main {

	static JFrame frmExosafe;
	private JScrollPane jScrollPane1;
	static Table table;
	static DefaultTableModel model;
	private JTextField txtAdmin;
	private JTextField chPass;
	private JTextField mid;
	private JTextField keyField;
	private static String motherIdString;
	static JLabel idKey;
	KButton activateButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmExosafe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws AWTException 
	 */
	public Main() throws IOException, InterruptedException, SQLException, ClassNotFoundException, AWTException {
		String powerShellPath = System.getenv("SystemRoot") + "\\System32\\WindowsPowerShell\\v1.0\\powershell.exe";
        if(!new java.io.File(powerShellPath).exists())
        {	
        	System.exit(0);
        }
       
        
		initialize();
		
		
		RemovableDrive.CheckUsbDrives();
		
		AutoDetect.detect();
		checkLicence();
		
		
	}
	public void checkLicence() throws SQLException, ClassNotFoundException
	{
		dataCrud dc=new dataCrud();
		String dateinbaseString=dc.getDate();
		
		String startDateString = "";
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String keyString=dc.getKey();
		
		if(dateinbaseString==null || dateinbaseString.equals(""))
		{
			dc.setDate(date);
			startDateString=date;
		}
		else {
			startDateString=dateinbaseString;
		}
		
		LocalDate start = LocalDate.parse(startDateString);
		LocalDate end   = LocalDate.parse(date);

		long diffInDays = ChronoUnit.DAYS.between(start, end);
		
		if(diffInDays>30 && (keyString==null || keyString.equals("")))
		{
			JOptionPane.showMessageDialog(null, "Please contact developer to insert the key for licence");
			System.exit(0);
		}
		else if(diffInDays<=30 && keyString==null || keyString.equals("")){
			idKey.setText("you have: "+(30-diffInDays)+" days left to insert the "+idKey.getText());
		}
		else if(keyString!=null){
			keyField.setText(keyString);
			keyField.setEditable(false);
			activateButton.setEnabled(false);
			activateButton.setText("Program is Activated");
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws AWTException 
	 */
	private void initialize() throws IOException, InterruptedException, AWTException {
		frmExosafe = new JFrame();
		motherIdString=SystemMotherBoardNumber.getWindowsMotherboard_SerialNumber();
		frmExosafe.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Ninou\\Desktop\\Java-Projects\\UsbChecker\\icon.png"));
		frmExosafe.setTitle("Exo-Safe");
		frmExosafe.setBounds(100, 100, 450, 300);
		frmExosafe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmExosafe.setResizable(false);
		frmExosafe.setVisible(true);
		
		if (!SystemTray.isSupported()) {
		    System.out.println("System tray is not supported");
		    return;
		}
		Image image = Toolkit.getDefaultToolkit().getImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "Usb Locker");
		trayIcon.addActionListener(e -> frmExosafe.setVisible(true));
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(e -> frmExosafe.setVisible(true));

		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(e -> {
		    //trayIcon.
		    System.exit(0);
		});
		

		PopupMenu popup = new PopupMenu();
		popup.add(openItem);
		popup.add(exitItem);
		trayIcon.setPopupMenu(popup);
		
		SystemTray tray = SystemTray.getSystemTray();
		tray.add(trayIcon);
		frmExosafe.addWindowListener(new WindowAdapter() {

		    @Override

		    public void windowClosing(WindowEvent e) {

		        // Minimize the application to the system tray

		    	frmExosafe.setVisible(false);

		      //  trayIcon.displayMessage("Minimized", "My Java Application is minimized to the system tray", TrayIcon.MessageType.INFO);

		    }

		});
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmExosafe.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		JLabel lblNewLabel_1 = new JLabel("Usb Controller V1.0");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmExosafe.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("N-Tech @");
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));
		
		table = new Table();
		table.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Usb Drive", "Status"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		jScrollPane1 = new javax.swing.JScrollPane();
		panel_2.setOpaque(false);
		jScrollPane1.setViewportView(table);
		
		table.fixTable(jScrollPane1);
		panel_2.add(jScrollPane1, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		KButton btnNewButton = new KButton();
		btnNewButton.kHoverEndColor = new Color(0, 128, 192);
		btnNewButton.setkHoverEndColor(new Color(0, 128, 192));
		btnNewButton.kHoverStartColor = new Color(192, 192, 192);
		btnNewButton.kHoverForeGround = new Color(192, 192, 192);
		btnNewButton.setkHoverStartColor(new Color(192, 192, 192));
		btnNewButton.setkHoverForeGround(new Color(255, 255, 255));
		btnNewButton.setkEndColor(new Color(192, 192, 192));
		btnNewButton.setText("Disable Protection");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				dataCrud dCrud=new dataCrud();
				String cPassString="";
				try {
					cPassString=dCrud.getPass();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Enter a password:");
				JPasswordField pass = new JPasswordField(10);
				panel.add(label);
				panel.add(pass);
				pass.setFocusable(true);
			    pass.requestFocusInWindow();
			    pass.requestFocus();
				String[] options = new String[]{"OK", "Cancel"};
				int option = JOptionPane.showOptionDialog(null, panel, "Password",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
						if(option == 0) // pressing OK button
						{
							
						   String password = pass.getText();
						  
						    if (password.equals(cPassString)) {
					        	DefaultTableModel model=(DefaultTableModel) table.getModel();
								int row=table.getSelectedRow();
								String driveString=(String) model.getValueAt(row, 0);
								LockUnlock.UnlockUsb(driveString.substring(0, driveString.length() - 2));
								try {
						            Process process = Runtime.getRuntime().exec("cmd /c handle " +driveString+"");
						            process.waitFor();
						            process.destroy();
						        } catch (IOException | InterruptedException e1) {
						            e1.printStackTrace();
						        }
								try {
				    				Runtime.
				    				   getRuntime().
				    				   exec("powershell.exe Start-Process Unlock"+driveString.substring(0, driveString.length() - 2)+".bat -verb RunAs");
				    	        } catch (IOException e1) {
				    	            e1.printStackTrace();
				    	        }
								model.setValueAt("Protection is Off", row,1);
					          //  JOptionPane.showMessageDialog(null, "Password Correct!");
					        } else {
					            JOptionPane.showMessageDialog(null, "Incorrect Password. Try Again.");
					        }
						 //  System.out.println("Your password is: " + new String(password));
						}

			}
		});
		
		KButton btnNewButton_1 = new KButton();
		btnNewButton_1.kHoverEndColor = new Color(0, 128, 192);
		btnNewButton_1.setkHoverEndColor(new Color(0, 128, 192));
		btnNewButton_1.kHoverStartColor = new Color(192, 192, 192);
		btnNewButton_1.kHoverForeGround = new Color(192, 192, 192);
		btnNewButton_1.setkHoverStartColor(new Color(192, 192, 192));
		btnNewButton_1.setkHoverForeGround(new Color(255, 255, 255));
		btnNewButton_1.setkEndColor(new Color(192, 192, 192));
		btnNewButton_1.setText("Enable Protection");
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel) table.getModel();
				int row=table.getSelectedRow();
				
				String driveString=(String) model.getValueAt(row, 0);
				LockUnlock.lockUsb(driveString.substring(0, driveString.length() - 2));
				try {
		            Process process = Runtime.getRuntime().exec("cmd /c handle " +driveString+"");
		            process.waitFor();
		            process.destroy();
		        } catch (IOException | InterruptedException e1) {
		            e1.printStackTrace();
		        }
				try {
    				Runtime.
    				   getRuntime().
    				   exec("powershell.exe Start-Process  lock.bat -verb RunAs");
    	        } catch (IOException e1) {
    	            e1.printStackTrace();
    	        }
				model.setValueAt("Protection is On", row,1);
			}
		});
			
		btnNewButton_1.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		panel_3.add(btnNewButton_1);
		btnNewButton.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		panel_3.add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add(panel_2);
		tabbedPane.setTitleAt(0, " USB Detection");
		frmExosafe.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("User Account", null, panel_5, null);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		KButton btnNewButton_2 = new KButton();
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newPString=chPass.getText();
				dataCrud dcCrud=new dataCrud();
				String xString="";
				try {
					xString=dcCrud.getPass();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(newPString.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please provide a password");
				}
				else {
					
					
					String password = JOptionPane.showInputDialog(null, "Enter Current Password:");
			        if (password.equals(xString)) {
			        	try {
							dcCrud.setPass(newPString);
							chPass.setText("");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			            JOptionPane.showMessageDialog(null, "Password Changed!");
			        } else {
			            JOptionPane.showMessageDialog(null, "Incorrect Current Password. Try Again.");
			        }
				}
			}
		});
		btnNewButton_2.setText("Change Password");
		btnNewButton_2.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		btnNewButton_2.kHoverEndColor = new Color(0, 128, 192);
		btnNewButton_2.setkHoverEndColor(new Color(0, 128, 192));
		btnNewButton_2.kHoverStartColor = new Color(192, 192, 192);
		btnNewButton_2.kHoverForeGround = new Color(192, 192, 192);
		btnNewButton_2.setkHoverStartColor(new Color(192, 192, 192));
		btnNewButton_2.setkHoverForeGround(new Color(255, 255, 255));
		btnNewButton_2.setkEndColor(new Color(192, 192, 192));
		btnNewButton_2.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 16));
		panel_6.add(btnNewButton_2);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Default Password (1st Use)");
		lblNewLabel_2.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(lblNewLabel_2);
		
		txtAdmin = new JTextField();
		txtAdmin.setText("Admin");
		txtAdmin.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		txtAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdmin.setEnabled(false);
		txtAdmin.setEditable(false);
		panel_7.add(txtAdmin);
		txtAdmin.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("New Password");
		lblNewLabel_3.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(lblNewLabel_3);
		
		chPass = new JTextField();
		chPass.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		chPass.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(chPass);
		chPass.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Activation", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_4.add(panel_8, BorderLayout.SOUTH);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		activateButton = new KButton();
		activateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyString=keyField.getText();
				if(keyString.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please Povide a Key");
				}
				else
				{
					String xString=StringHashing.hashString(mid.getText());
					if(xString.equals(keyString))
					{
						dataCrud dCrud=new dataCrud();
						try {
							dCrud.setKey(keyString);
							keyField.setEditable(false);
							keyField.setText(xString);
							idKey.setText("Key");
							activateButton.setEnabled(false);
							activateButton.setText("Program is Avticated");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else
					{
						keyField.setText("");
						JOptionPane.showMessageDialog(null, "Incorrect Key, Please Contact the Developer");
					}
					
				}
				
			}
		});
		activateButton.setText("Activate");
		activateButton.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		activateButton.kHoverEndColor = new Color(0, 128, 192);
		activateButton.setkHoverEndColor(new Color(0, 128, 192));
		activateButton.kHoverStartColor = new Color(192, 192, 192);
		activateButton.kHoverForeGround = new Color(192, 192, 192);
		activateButton.setkHoverStartColor(new Color(192, 192, 192));
		activateButton.setkHoverForeGround(new Color(255, 255, 255));
		activateButton.setkEndColor(new Color(192, 192, 192));
		panel_8.add(activateButton);
		
		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new GridLayout(4, 0, 0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("ID PC");
		lblNewLabel_4.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblNewLabel_4);
		
		mid = new JTextField();
		mid.setText(motherIdString);
		mid.setEnabled(false);
		mid.setEditable(false);
		mid.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		mid.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(mid);
		mid.setColumns(10);
		
		idKey = new JLabel("Key");
		idKey.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		idKey.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(idKey);
		
		keyField = new JTextField();
		keyField.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 14));
		keyField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(keyField);
		keyField.setColumns(10);
		
	}
}
