/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.activiti.delegates;

import ac.at.fhkufstein.activiti.InvitationProcess;
import ac.at.fhkufstein.bean.BmwEventController;
import ac.at.fhkufstein.bean.BmwParticipantsController;
import ac.at.fhkufstein.entity.BmwEvent;
import ac.at.fhkufstein.entity.BmwParticipants;
import ac.at.fhkufstein.service.PersistenceService;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *
 * @author mike
 */
public class NotificateForFollowUpActions implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("################# sending notification for follow up actions #################");


        BmwEvent event = (BmwEvent) PersistenceService.loadByInteger(BmwEventController.class, execution.getVariable(InvitationProcess.DATABASE_EVENTID));

        // @todo implementMailFunction
//        MailService.sendMail(null, null, null);


        String mailSentMessage = "Notification für FollowUp Actions wurde an den Mitarbeiter " + event.getResponsibleUser() + " gesendet.";

        System.out.println(mailSentMessage);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(mailSentMessage));
    }
}