package com.ifsp.edu.hto.sge.notificador.service;

import com.ifsp.edu.hto.sge.notificador.model.RelatorioMensal;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class EmailService {

    public void sendEmail(RelatorioMensal relatorioMensal) throws IOException {
        Email from = new Email("lenilsonts@gmail.com");
        String subject = "Relatório de Atividades";
        Email to = new Email(relatorioMensal.getEmail());
        Content content = new Content("text/html", "<h1>Caro aluno(a): "+relatorioMensal.getAluno()+ "</h1> <p> O relatório mensal do mes: "+relatorioMensal.getMesRelatorio() + "ano: "+relatorioMensal.getAnoRelatorio()+ " ainda não foi entregue.");
        Mail mail = new Mail(from, subject, to, content);

//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("Notificação para o email: {} foi realizada com sucesso", relatorioMensal.getEmail());
        } catch (IOException ex) {
            throw ex;
        }
    }

}
