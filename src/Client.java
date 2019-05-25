import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Client extends MainWindow
{
	static JTextField Nombre, Saludo;

	Client()
	{
		//JCGE: Propiedades Generales
		this.setExtendedState(MAXIMIZED_BOTH);
		
		//JCGE: Propiedades Particulares
		Nombre = new JTextField(50);
		Nombre.requestFocusInWindow();
		Saludo = new JTextField(50);
		Saludo.requestFocusInWindow();
		MyLabel l_user = new MyLabel("Nombre: ");
		MyLabel l_passt = new MyLabel("Saludo: ");
		JPanel loginBox = new JPanel();
		JButton boton = new JButton("Enviar");
		boton.addActionListener(this);
		
		loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
		loginBox.add(l_user);
		loginBox.add(Nombre);
		loginBox.add(l_passt);
		loginBox.add(Saludo);
		loginBox.add(boton);
		//JCGE: Vamos a prepararnos para poner una imagen aca loca
		int x = 320,y = 450,b = 500,h = 100;
		loginBox.setBounds((WIDTH.intValue()/2)-260,y,b,h+20);
		loginBox.setBackground(colores.get(4));
		panelCentro.add(loginBox);
	}
	
	public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.finGUI();
	}
	
	public void RequestServer (String Nombre, String Saludo) throws IOException
	{
		// TODO Auto-generated method stub
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket s = null;
		
		try
		{
			s = new Socket("127.0.0.1",5400);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			oos.writeObject(String.format("%s,%s", Nombre.replaceAll(",", ""), Saludo.replaceAll(",", "")));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error: No hubo conexion, ¿Esta encendido el servidor?");
		}
		finally
		{
			if( ois != null ) ois.close();
			if( oos != null ) oos.close();
			if( s != null ) s.close();
		}
	}
	//JCGE: Este es el metodo que se encarga de tomar las acciones en los botones
	public void actionPerformed(ActionEvent arg0)
	{
		String boton = arg0.getActionCommand();
		System.out.println(boton);
		if (boton == "Enviar")
		{
			if (Nombre.getText().length() == 0)
			{
				//JCGE: le decimos que no manche, que ponga un usuario
				JOptionPane.showMessageDialog(null,"Error: Favor de capturar el nombre");
				return;
			}
			if (Saludo.getText().length() == 0)
			{
				//JCGE: le decimos que no manche, que ponga un usuario
				JOptionPane.showMessageDialog(null,"Error: Favor de capturar el saludo");
				return;
			}
			try {
				this.RequestServer(Nombre.getText(), Saludo.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
