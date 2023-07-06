package com.epam.cs.mail;
import jakarta.servlet.http.HttpServletRequest;
import static com.epam.cs.command.AttributeName.E_MAIL;
public class MailSender {
    private static final String MESSAGE_CAR_SHARING_ACTIVATION_CODE = "car_sharing.activation.code";
    private static final String MESSAGE_YOUR_ACTIVATION_CODE = "your.activation.code.is";

    private MailSender(){}

    public static void sendActivationCodeByEmail(HttpServletRequest request, String activationCode) {
        String sendEmailTo = request.getParameter(E_MAIL);
        StringBuilder mailText = new StringBuilder("");
        mailText.append(MESSAGE_CAR_SHARING_ACTIVATION_CODE)
                .append("\n")
                .append((MESSAGE_YOUR_ACTIVATION_CODE))
                .append(" :")
                .append(activationCode);
        String mailSubject = MESSAGE_CAR_SHARING_ACTIVATION_CODE;
        MailService mailService = new MailService();
        mailService.sendEmail(sendEmailTo,mailSubject,mailText.toString());
    }
}
