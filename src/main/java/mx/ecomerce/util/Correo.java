package mx.ecomerce.util;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mx.ecomerce.model.Usuario;

public class Correo {

    public static void enviarCorreo(Usuario usuario, int sub) throws AddressException, MessagingException {

            String mailServidor = "prisma901@outlook.com";
            String passwordServidor = "12345admin";
            // Propiedades de la conexión.
            Properties props = new Properties();  
	        props.put("mail.smtp.starttls.enable","true");
	        props.put("mail.smtp.host","smtp.live.com");  
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.port", "587");

            Session session=Session.getDefaultInstance(props, null);
        session.setDebug(true);

            // Se construye el mensaje a enviar
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailServidor));
			// FIXME Sacar el mail de contactos

			//prueba de usuario
            //la contraseña con tamaño 6
            message.addRecipients(Message.RecipientType.TO,
            InternetAddress.parse(usuario.getCorreo()));
            String contenido;
            if (sub == 0) {

                message.setSubject("ECommerce: Información de la cuenta");
                contenido = "<center>"
                			+ "<div style=\"color: #084B8A; border: 1px dotted black;\">"
                				+ "<table>"
			                		+ "<tr>"
										+ "<td>"
												+ "<h2><b>Bienvenido(a) a ECommerce</b></h2>"
											
										+ "</td>" + "</tr>"
								+ "</table>"
							+ "</div>"
                			+ "<div style=\"color: #FFFFFF;background-color: #084B8A;opacity: .85;\">"
                				+ "<table>"
                					+ "<tr>" 
                						+ "<td>"
                							+"Los datos con los que deberá iniciar sesión son:"
                						+ "</td>" + "</tr>"
                					+ "<tr>" 
                						+ "<td>"
                							+ "Nombre de usuario: " + usuario.getCorreo()
                						+ "</td>" + "</tr>"
                					+ "<tr>" 
                						+ "<td>"
                							+ "Contraseña: " + usuario.getPassword()
                							+ "</td>" + "</tr>"
                				+ "</table>"
                			+ "</div>"
                			+ "</center>";
                        

                message.setContent(contenido, "text/html");

            }
            if (sub == 1) {
                message.setSubject("ECommerce: Recuperación de contraseña");
                contenido = "<center><div style=\"color: #084B8A; border: 1px dotted black;\"><table>"
                        + "<tr>"
                        + "<td>"
                        + "<h2><b>Hola "
                        + usuario.getNombre()
                        + "</td>" + "</tr>"
                        + "<div style=\"color: #FFFFFF;background-color: #084B8A;opacity: .85;\">Recientemente ha solicitado recuperar la contraseña de su cuenta del sistema <i>ECommerce</i>."
                        + "<tr>" + "<td>"
                        + "Contraseña: " + usuario.getPassword()
                        + "</td>" + "</tr>"
                        + "</div>"
                        + "</table></center>";

                message.setContent(contenido, "text/html");

            }
            //send the message
            
            Transport t = session.getTransport("smtp");
            t.connect(mailServidor, passwordServidor);
            
            //t.sendMessage(message, message.getAllRecipients());
            t.sendMessage(message, message.getAllRecipients());
            System.out.println("Correo enviado...");

            // Cierre.
            t.close();
    }
}
