/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.admin;

import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.EmailTemplates;
import ac.at.fhkufstein.mailing.NotificationService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Philipp
 */
@ManagedBean(name = "sendLogin")
@RequestScoped
public class SendLogin {

    private BmwUser[] selected = null;
    private EmailTemplates loginTemplate = new EmailTemplates();

    /**
     * Creates a new instance of SendLogin
     */
    public SendLogin() {

        loginTemplate.setSubject("BMWEvent Registrierung");
        loginTemplate.setType("Login");
        loginTemplate.setEmailContent("Sehr geehrte Damen und Herren,<div><br/></div><div>Anbei befindet sich Ihre Benutzerdaten für das BMW Eventmanagementsystem.</div><div><br/></div><div>Benutzer: $email$</div><div>Passwort: $password$</div><div><br/></div><div>Mit freundlichen Grüßen,</div><div>BMW Group Austria</div>");

    }

    public void sendLogin() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selected == null) {
            context.addMessage(null, new FacesMessage("Registration versenden", "Keinen Benutzer ausgewählt!"));
        } else {

            NotificationService.parseTemplate(selected, loginTemplate);
            context.addMessage(null, new FacesMessage("Registration versenden", "E-mail erfolgreich versendet!"));
        }

    }

    public BmwUser[] getSelected() {
        return selected;
    }

    public void setSelected(BmwUser[] selected) {
        this.selected = selected;
    }
}