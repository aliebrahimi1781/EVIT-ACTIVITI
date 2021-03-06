/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.mailing;

import ac.at.fhkufstein.entity.BmwEmailTemplates;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import java.util.List;
import javax.faces.context.FacesContext;
import org.antlr.stringtemplate.StringTemplate;

/**
 *
 * @author Philipp Diese Klasse wird verwendet um StringTemplates zu parsen und
 * anschließend zu versenden.
 */
public class NotificationService {


    //Methoden zum Versenden von Eventspezifischen Templates
    public static void parseTemplate(List<BmwUser> b, EmailTemplates e) {

        for (BmwUser u : b) {
            parseTemplate(u, e);
        }
    }
    
    public static void parseTemplate(List<BmwUser> b, EmailTemplates e, Object loggedInUID) {

        for (BmwUser u : b) {
            inside(u, e, loggedInUID);
        }
    }

    public static void parseTemplate(BmwUser[] b, EmailTemplates e) {

        for (BmwUser u : b) {
            parseTemplate(u, e);
        }
    }

    public static void inside(BmwUser u, EmailTemplateable e, Object loggedInUID) {

        StringTemplate template = new StringTemplate(e.getEmailContent());
        template.setAttribute("email", u.getPersonenID().getEMail1());
        template.setAttribute("password", u.getPwd());
        template.setAttribute("username", u.getUsername());
        template.setAttribute("vorname", u.getPersonenID().getVorname());
        template.setAttribute("nachname", u.getPersonenID().getNachname());
        template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
        //System.out.println(template.toString());
        String mailcontent = template.toString();

        MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType(), loggedInUID);
    }


    //Methoden zum Versenden von Login/Reset/ForgotPassword Templates
    public static void parseTemplate(BmwUser[] b, EmailTemplateable e) {

        for (BmwUser u : b) {

            parseTemplate(u, e);
        }
    }

    public static void parseTemplate(List<BmwUser> b, EmailTemplateable e) {

        for (BmwUser u : b) {

            parseTemplate(u, e);
        }
    }

    public static void parseTemplate(BmwUser u, EmailTemplateable e) {

        insideT(u, e, FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid"));
    }

    public static void parseTemplate(BmwUser u, EmailTemplateable e, Object loggedInUID) {

        insideT(u, e, loggedInUID);
    }

    public static void insideT(BmwUser u, EmailTemplateable e, Object loggedInUID) {
        StringTemplate template = new StringTemplate(e.getEmailContent());
        template.setAttribute("email", u.getPersonenID().getEMail1());
        template.setAttribute("password", u.getPwd());
        template.setAttribute("username", u.getUsername());
        template.setAttribute("vorname", u.getPersonenID().getVorname());
        template.setAttribute("nachname", u.getPersonenID().getNachname());
        template.setAttribute("briefanrede", u.getPersonenID().getBriefanredeSie());
        String mailcontent = template.toString();

        MailService.sendMail(u.getPersonenID().getEMail1(), e.getSubject(), mailcontent, e.getType(), loggedInUID);

    }

public static void parseTemplateNonJournalist(BmwUser u, EmailTemplateable e, Object loggedInUID) {

        StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", u.getEmail());
            template.setAttribute("password", u.getPwd());
            template.setAttribute("username", u.getUsername());
            template.setAttribute("vorname", u.getFirstName());
            template.setAttribute("nachname", u.getLastName());
            String mailcontent = template.toString();

            MailService.sendMail(u.getEmail(), e.getSubject(), mailcontent, e.getType(), loggedInUID);

    }

    public static void parseTemplateByMailAddress(String mailAddress, EmailTemplateable e, Object loggedInUID) {

        StringTemplate template = new StringTemplate(e.getEmailContent());
            template.setAttribute("email", mailAddress);
            String mailcontent = template.toString();

            MailService.sendMail(mailAddress, e.getSubject(), mailcontent, e.getType(), loggedInUID);

    }
}
