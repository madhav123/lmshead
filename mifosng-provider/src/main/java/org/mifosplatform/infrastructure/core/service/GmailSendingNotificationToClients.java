/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.core.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.mifosplatform.infrastructure.core.domain.EmailDetail;
import org.springframework.stereotype.Service;

@Service
public class GmailSendingNotificationToClients {

    public void sendToUserAccount(final String mailAddress, final String approviedDate,final String type,final String money) {
        final Email email = new SimpleEmail();
        final String authuserName = "madhav@hugotechnologies.com";
        final String authuser = "madhav@hugotechnologies.com";
        final String authpwd = "madhav123";
        // Very Important, Don't use email.setAuthentication()
        email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
        email.setDebug(false); // true if you want to debug
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        try {
            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
            email.setFrom(authuser, authuserName);
            final StringBuilder subjectBuilder = new StringBuilder().append(type+": ");
            email.setSubject(subjectBuilder.toString());
            StringBuilder messageBuilder=null;
            if(money!=null)
               messageBuilder = new StringBuilder().append(type +": ").append(approviedDate).append("Amount Disbursed:")
            		.append(money);
            else
                messageBuilder = new StringBuilder().append(type +": ").append(approviedDate);
            email.setMsg(messageBuilder.toString());
            email.addTo(mailAddress, mailAddress);
            email.send();
        } catch (final EmailException e) {
            throw new PlatformEmailSendException(e);
        }
    }
}