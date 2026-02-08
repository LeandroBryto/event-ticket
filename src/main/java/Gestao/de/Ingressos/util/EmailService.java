package Gestao.de.Ingressos.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendWelcomeEmail(String to, String name) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Bem-vindo ao Gestão de Ingressos!");

            String htmlContent = "<html><body>" +
                    "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd;'>" +
                    "<h1 style='font-size: 24px; color: #333;'>Bem-vindo ao Gestão de Ingressos!</h1>" +
                    "<p>Olá, <strong>" + name + "</strong>!</p>" +
                    "<p>Estamos muito felizes em ter você conosco. Agora você pode explorar e comprar ingressos para os melhores eventos da cidade.</p>" +
                    "<hr>" +
                    "<p style='font-size: 12px; color: #888;'>Atenciosamente,<br>Equipe Gestão de Ingressos</p>" +
                    "</div>" +
                    "</body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Em um app real, aqui teríamos um log mais robusto (ex: log.error)
            System.err.println("Falha ao enviar email de boas-vindas para " + to + ": " + e.getMessage());
        }
    }

    @Async
    public void sendPasswordRecoveryEmail(String to, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Recuperação de Senha - Gestão de Ingressos");

            String htmlContent = "<html><body>" +
                    "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd;'>" +
                    "<h1 style='font-size: 24px; color: #333;'>Recuperação de Senha</h1>" +
                    "<p>Olá!</p>" +
                    "<p>Recebemos uma solicitação para redefinir sua senha. Use o token abaixo para criar uma nova senha:</p>" +
                    "<p style='font-size: 20px; font-weight: bold; color: #0056b3;'>" + token + "</p>" +
                    "<p>Se você não solicitou isso, por favor, ignore este email.</p>" +
                    "<hr>" +
                    "<p style='font-size: 12px; color: #888;'>Atenciosamente,<br>Equipe Gestão de Ingressos</p>" +
                    "</div>" +
                    "</body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.err.println("Falha ao enviar email de recuperação para " + to + ": " + e.getMessage());
        }
    }
}
