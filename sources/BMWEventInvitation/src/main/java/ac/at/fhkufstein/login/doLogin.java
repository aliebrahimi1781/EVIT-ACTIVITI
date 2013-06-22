/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.login;

import ac.at.fhkufstein.bean.BmwUserController;
import ac.at.fhkufstein.bean.PersonenController;
import ac.at.fhkufstein.entity.BmwUser;
import ac.at.fhkufstein.entity.Personen;
import ac.at.fhkufstein.session.BmwUserFacade;
import ac.at.fhkufstein.session.PersonenFacade;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author
 * wolfgangteves
 */
@ManagedBean(name = "login")
public class doLogin {

	Integer uid;
	Integer role;
	String user;
	String pw;
	BmwUserController bmwUserController;
	BmwUser bmwUser;
	PersonenController personenController;
	Personen personen;

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		//instanciate controllers
		bmwUserController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{bmwUserController}", BmwUserController.class);
		personenController = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{personenController}", PersonenController.class);


		//Find User Details by Email (User table)
		EntityManager em = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
		List p = em.createNamedQuery("BmwUser.findByEmail")
				.setParameter("email", user)
				.getResultList();
		if (p.size() > 0) {
			//if result this is the searched one!
			bmwUser = (BmwUser) p.get(0);
		}

		//Find User Details by Username (User Table)
		EntityManager em2 = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
		List p2 = em2.createNamedQuery("BmwUser.findByUsername")
				.setParameter("username", user)
				.getResultList();
		if (p2.size() > 0) {
			//if result this is the searched one!
			bmwUser = (BmwUser) p2.get(0);
		}
		//Find User Details by Email (Personen Table) JAy.

		EntityManager em3 = ((PersonenFacade) personenController.getFacade()).getEntityManager();
		List p3 = em3.createNamedQuery("Personen.findByEMail1")
				.setParameter("eMail1", user)
				.getResultList();
		if (p3.size() > 0) {
			//if result this is the searched one!
			personen = (Personen) p3.get(0);
			//Get the BMW User for the person
			EntityManager em4 = ((BmwUserFacade) bmwUserController.getFacade()).getEntityManager();
			List p4 = em4.createNamedQuery("BmwUser.findByPersonenID")
					.setParameter("personenID", personen.getPersonalID().toString())
					.getResultList();
			if (p4.size() > 0) {
				//check if bmw user is found
				bmwUser = (BmwUser) p4.get(0);
			} else {
				System.out.println("System Error Login");
			}
		}
		if (bmwUser != null) {
			//check if one user is found
			if (pw.equals(bmwUser.getPwd())) {//check if password is correct
				//Set uid and role
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", bmwUser.getUid());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", bmwUser.getRole());
				
				System.out.println("Role:" + bmwUser.getRole()); //output role
				if (bmwUser.getRole() == null) { //Return error if no role is defined
					context.addMessage(null, new FacesMessage("Fehler", "Für diesen User ist keine Rolle Definiert"));
					return "#";
				} else if (bmwUser.getRole() == 1) {
					//is BMW User -> Redirect to BMW User 
					return "/faces/index.xhtml";
				} else if (bmwUser.getRole() == 2) {
					//is Journalist -> Redirect to Journalist interface
					return "/faces/BMW_Journalist/journalistmenue.xhtml";
				} else if (bmwUser.getRole() == 3) {
					//is Travel Agency
					//Todo: Redirect to Travel Agency Interface
					context.addMessage(null, new FacesMessage("Fehler", "Es gibt noch keine Oberfläche für Reisebüros"));
					return "#";
				} else {
					//Unbekannte Rolle
					context.addMessage(null, new FacesMessage("Fehler", "Diese Rolle (" + bmwUser.getRole().toString() + ") ist dem System nicht bekannt"));
					return "#";
				}

			} else {
				//Password not correct
				context.addMessage(null, new FacesMessage("Fehler", "Passwort nicht Korrekt"));
				return "#";

			}
		} else {
			//User not found
			context.addMessage(null, new FacesMessage("Fehler", "User nicht gefunden"));
			return "#";
		}


	}

	public String logout() {
		//Delete Session
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//redirect to login page
		return "/faces/login.xhtml";

	}

	public Integer getUid() {
		//Get uid from session
		return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uid").toString());
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRole() {
		//get role from session
		return Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("role").toString());
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}