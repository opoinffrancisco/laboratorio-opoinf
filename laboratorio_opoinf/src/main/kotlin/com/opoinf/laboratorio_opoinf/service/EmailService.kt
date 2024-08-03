package com.opoinf.laboratorio_opoinf.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(val mailSender: JavaMailSender) {

    fun send(to: String, subject: String, text: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text, true)
        mailSender.send(message)
    }
}
