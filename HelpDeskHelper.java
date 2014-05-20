/**
 * Author: Grayson Lorenz
 * 5/20/2014
 * Created to make taking call notes easier and more reliable.
 **//

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class HelpDeskHelper extends JFrame{
	//Fields
	private JButton submitB;
	private JPanel nameP;
	private JPanel topP;
	private JPanel ipP;
	private JPanel phoneP;
	private JScrollPane descriptionP;
	private JTextField phoneTF;
	private JTextField nameTF;
	private JTextField ipTF;
	private JTextArea descriptionTA;
	private BHandler submitHandler;
	private DateFormat dateFormat;
	private Date date;
	private PrintWriter writer;
	//size of window
	private static final int WIDTH=400,HEIGHT=300;
	
	//The setup and go
	public HelpDeskHelper(){
		//button instantiation
		submitB = new JButton("Submit");
		
		//Text fields for top panel instantiation
		phoneTF = new JTextField();
		nameTF = new JTextField();
		ipTF = new JTextField();
		
		//Description instantiation
		descriptionTA = new JTextArea(1,1);
		descriptionTA.setLineWrap(true);
		descriptionTA.setWrapStyleWord(true);
		descriptionTA.setAlignmentX(LEFT_ALIGNMENT);
		
		//panel instantiation
		topP = new JPanel();
		topP.setLayout(new BoxLayout(topP, BoxLayout.X_AXIS));
		nameP = new JPanel();
		nameP.setLayout(new BoxLayout(nameP, BoxLayout.PAGE_AXIS));
		nameP.setName("Name");
		ipP = new JPanel();
		ipP.setLayout(new BoxLayout(ipP, BoxLayout.PAGE_AXIS));
		descriptionP = new JScrollPane(descriptionTA);
		descriptionP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		phoneP = new JPanel(new BorderLayout());
		
		//The rest of the instantiation
		submitHandler = new BHandler();			
		dateFormat = new SimpleDateFormat("MM_dd_yyyy_HHmmss");
		date = new Date();	
		
		//Give the button a listener that we made
		submitB.addActionListener(submitHandler);
		
		//set up top panel components
		nameP.add(nameTF);
		nameP.setBorder(BorderFactory.createTitledBorder("Name"));
		
		phoneP.add(phoneTF);
		phoneP.setBorder(BorderFactory.createTitledBorder("Phone"));
		
		ipP.add(ipTF);
		ipP.setBorder(BorderFactory.createTitledBorder("IP Address"));
		
		//fill and setup top panel with name, phone, and ip panels and invisible border
		topP.add(nameP);
		topP.add(phoneP);
		topP.add(ipP);
		topP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//description panel border, panel was set up during instantiation
		descriptionP.setBorder(BorderFactory.createTitledBorder("Call Description"));
		
		//Set up window, make it visible, put stuff in it. You are now running.
		setTitle("Call Helper");
		setSize(WIDTH, HEIGHT);
		setVisible(true);
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container grid = getContentPane();
		grid.add(topP, BorderLayout.NORTH);
		grid.add(descriptionP, BorderLayout.CENTER);
		grid.add(submitB, BorderLayout.SOUTH);
	}
	
	//The business end
	public class BHandler implements ActionListener{
		String name;
		String theDate;
		String nameBuffer;
		String buffer;
		public BHandler(){
			name = "";
			nameBuffer = "";
			buffer = "";
		}
		
		/**
		 * This is what do when you click the submit button.
		 * Formats name and date to name the file it will create,
		 * then prints out name, number, ip, and description into it's own unique file for ease of use.
		 * Basically it's a quick save as for each call, where you save that little bit of time typing out everything and
		 * it keeps all the lines separate for you because I'm such a nice guy.
		 */
		public void actionPerformed(ActionEvent arg0) {
			//reset name just to be safe
			nameBuffer = "";
			name = nameTF.getText();
			buffer = "";
			
			//set up date for file name use
			theDate = dateFormat.format(date) + "_";
			
			
			//set up name buffer. Takes in the name and puts _ where the spaces are for file name use.
			for(int i = 0; i < name.length(); i ++){
				String letter = name.substring(i, i+1);
				if(letter.compareTo(" ") == 0){
					letter = "_";
				}
				nameBuffer += letter;
			}
			
			try {
				writer = new PrintWriter(theDate + nameBuffer + ".txt", "UTF-8");
				writer.println("NAME: " + nameTF.getText() );
				writer.println("");
				writer.println("PHONE: " + phoneTF.getText() );
				writer.println("");
				writer.println("IP ADDRESS: " + ipTF.getText() );
				writer.println("");
				writer.print("INFO:\t");
				buffer = descriptionTA.getText();
				for(int i = 0; i < buffer.length(); i ++){
					String letter = buffer.substring(i, i+1);
					if(letter.compareTo("\n") == 0){
						writer.println("");
						writer.print("\t");
					}
					else{
						writer.print(letter);
					}
				}
				nameTF.setText("");
				ipTF.setText("");
				phoneTF.setText("");
				descriptionTA.setText("");
				writer.close();
				JOptionPane.showMessageDialog(null, "File Created!", "Success!", 1);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not found :(", "Uh-oh!", ERROR);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Could not creat file! Unsupported Charset: UTF-8", "Uh-oh!", ERROR);
			}	
		}
	}
	
	public static void main(String[]args){
		@SuppressWarnings("unused")
		HelpDeskHelper help = new HelpDeskHelper();
	}
}
