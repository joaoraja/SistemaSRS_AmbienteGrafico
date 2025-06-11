package org.example.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.File;

public class EmailService {

    public static void enviarBoletim(String destinatario, File anexoPDF) throws Exception {
        final String remetente = "joao.doreste@al.infnet.edu.br";
        final String senha = "mgtx tzrr pcuj nfyj";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remetente, senha);
                    }
                }
        );

        Message mensagem = new MimeMessage(session);
        mensagem.setFrom(new InternetAddress(remetente));
        mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensagem.setSubject("Boletim Escolar (PDF)");

        MimeBodyPart corpoTexto = new MimeBodyPart();
        corpoTexto.setText("Olá, segue em anexo o boletim escolar.");

        MimeBodyPart anexo = new MimeBodyPart();
        anexo.attachFile(anexoPDF);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(corpoTexto);
        multipart.addBodyPart(anexo);

        mensagem.setContent(multipart);
        Transport.send(mensagem);
    }
}
