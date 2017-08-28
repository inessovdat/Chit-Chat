import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output;
	private JTextField input;
	private JTextField polje;

	public ChatFrame() {
		super();
		this.setTitle("Chit Chat");
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel vzdevek = new JLabel("Vzdevek:");
		panel.add(vzdevek);
		polje = new JTextField(System.getProperty("user.name"), 15);
		panel.add(polje);
		GridBagConstraints panelConstraint = new GridBagConstraints();
		panelConstraint.fill = GridBagConstraints.HORIZONTAL;		
		panelConstraint.gridx = 0;
		panelConstraint.gridy = 0;
		pane.add(panel, panelConstraint);
		
		JButton prijava = new JButton("Prijava");
		panel.add(prijava);
		prijava.addActionListener(this);
		   
		JButton odjava = new JButton("Odjava");
		panel.add(odjava);
		odjava.addActionListener(this);
		
		
		this.output = new JTextArea(20, 40);
		JScrollPane scrollpane = new JScrollPane(output);
		this.output.setEditable(false);
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.fill = GridBagConstraints.BOTH;
		outputConstraint.weightx = 1;
		outputConstraint.weighty = 1;		
		outputConstraint.gridx = 0;
		outputConstraint.gridy = 1;
		pane.add(scrollpane, outputConstraint);
		
		this.input = new JTextField(40);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.fill = GridBagConstraints.BOTH;
		inputConstraint.weightx = 1;
		inputConstraint.weighty = 0;
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton eventButton = (JButton)e.getSource();
		if (eventButton.getText() == "Prijava"){
			
			try {URI uri = new URIBuilder("http://chitchat.andrej.com/users")
	  	          .addParameter("username", polje.getText())
	  	          .build();
	
	  	   String responseBody = Request.Post(uri)
	  	                               .execute()
	  	                               .returnContent()
	  	                               .asString();
	
	  	   System.out.println(responseBody);
		   }  catch (IOException e1) {
			   e1.printStackTrace();
		   } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		   }
		}
		else if(eventButton.getText() == "Odjava"){
			try {
			URI uri = new URIBuilder("http://chitchat.andrej.com/users")
	    	          .addParameter("username", polje.getText())
	    	          .build();

	    	  String responseBody = Request.Delete(uri)
	    	                               .execute()
	    	                               .returnContent()
	    	                               .asString();

	    	  System.out.println(responseBody);
			}  catch (IOException e1) {
				   e1.printStackTrace();
			   } catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			   }
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(polje.getText(), this.input.getText());
				this.input.setText("");
			}
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
