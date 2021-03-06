/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ac.at.fhkufstein.entity;

//import ac.at.fhkufstein.processentity.EventProcess;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



/**
 *
 * @author Philipp
 */
@Entity
@Table(name = "bmw_event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BmwEvent.findAll", query = "SELECT b FROM BmwEvent b"),
    @NamedQuery(name = "BmwEvent.findById", query = "SELECT b FROM BmwEvent b WHERE b.id = :id"),
    @NamedQuery(name = "BmwEvent.findByName", query = "SELECT b FROM BmwEvent b WHERE b.name = :name"),
    @NamedQuery(name = "BmwEvent.upcoming", query = "SELECT b FROM BmwEvent b WHERE b.startEventdate> :now"),
    @NamedQuery(name = "BmwEvent.past", query = "SELECT b FROM BmwEvent b WHERE b.endEventdate< :now"),
    @NamedQuery(name = "BmwEvent.pastAndParticipant", query = "SELECT b FROM BmwEvent b WHERE b.endEventdate< :now AND b.bmwParticipants = :bmwPartId"),
    @NamedQuery(name = "BmwEvent.findByStartEventdate", query = "SELECT b FROM BmwEvent b WHERE b.startEventdate = :startEventdate"),
    @NamedQuery(name = "BmwEvent.findByEndEventdate", query = "SELECT b FROM BmwEvent b WHERE b.endEventdate = :endEventdate"),
    @NamedQuery(name = "BmwEvent.findByMaxParticipants", query = "SELECT b FROM BmwEvent b WHERE b.maxParticipants = :maxParticipants"),
    @NamedQuery(name = "BmwEvent.findByLocation", query = "SELECT b FROM BmwEvent b WHERE b.location = :location"),
    @NamedQuery(name = "BmwEvent.findByUrgencyDayLimit", query = "SELECT b FROM BmwEvent b WHERE b.urgencyDayLimit = :urgencyDayLimit"),
    @NamedQuery(name = "BmwEvent.findByEventUpdated", query = "SELECT b FROM BmwEvent b WHERE b.eventUpdated = :eventUpdated"),
    @NamedQuery(name = "BmwEvent.findByEventCreated", query = "SELECT b FROM BmwEvent b WHERE b.eventCreated = :eventCreated"),
    @NamedQuery(name = "BmwEvent.findByUserUpdated", query = "SELECT b FROM BmwEvent b WHERE b.userUpdated = :userUpdated"),
    @NamedQuery(name = "BmwEvent.findByUserCreated", query = "SELECT b FROM BmwEvent b WHERE b.userCreated = :userCreated"),
    @NamedQuery(name = "BmwEvent.findByProgress", query = "SELECT b FROM BmwEvent b WHERE b.progress = :progress"),
    @NamedQuery(name = "BmwEvent.findByProcessId", query = "SELECT b FROM BmwEvent b WHERE b.processId = :processId")})
public class BmwEvent implements Serializable, ActivitiProcessHolder {
    @Column(name = "released")
    private Boolean released = false;
    @Column(name = "sendFollowup")
    private Integer sendFollowup;
    @Column(name = "bmwParticipants")
    private Integer bmwParticipants;
    @Size(max = 255)
    @Column(name = "documents")
    private String documents;
    @Column(name = "embargo")
    private Boolean embargo;
    @Size(max = 50)
    @Column(name = "responsibleUser")
    private String responsibleUser;
    @Column(name = "sendReminder")
    private Integer sendReminder;
    @Column(name = "cancelInvitation")
    private Integer cancelInvitation;
    @Column(name = "closeInvitation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeInvitation;
    @Column(name = "embargoDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date embargoDate;
    @JoinColumn(name = "travelAgency", referencedColumnName = "uid")
    @ManyToOne
    private BmwUser travelAgency;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eid")
    private Collection<EmailTemplates> emailTemplatesCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_eventdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startEventdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_eventdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endEventdate;
    @Column(name = "max_participants")
    private Integer maxParticipants;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 250)
    @Column(name = "location")
    private String location;
    @Column(name = "urgency_day_limit")
    private Integer urgencyDayLimit;
    @Column(name = "event_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "event_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventCreated;
    @Column(name = "user_updated")
    private Integer userUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_created")
    private int userCreated;
    @Column(name = "progress")
    private Integer progress;
    @Column(name = "process_id")
    private Integer processId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<BmwFlight> bmwFlightCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<BmwParticipants> bmwParticipantsCollection;

    public BmwEvent() {
    }

    public BmwEvent(Integer id) {
        this.id = id;
    }

    public BmwEvent(Integer id, String name, Date startEventdate, Date endEventdate, Date eventCreated, int userCreated) {
        this.id = id;
        this.name = name;
        this.startEventdate = startEventdate;
        this.endEventdate = endEventdate;
        this.eventCreated = eventCreated;
        this.userCreated = userCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartEventdate() {
        return startEventdate;
    }

    public void setStartEventdate(Date startEventdate) {
        this.startEventdate = startEventdate;
    }

    public Date getEndEventdate() {
        return endEventdate;
    }

    public void setEndEventdate(Date endEventdate) {
        this.endEventdate = endEventdate;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUrgencyDayLimit() {
        return urgencyDayLimit;
    }

    public void setUrgencyDayLimit(Integer urgencyDayLimit) {
        this.urgencyDayLimit = urgencyDayLimit;
    }

    public Date getEventUpdated() {
        return eventUpdated;
    }

    public void setEventUpdated(Date eventUpdated) {
        this.eventUpdated = eventUpdated;
    }

    public Date getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated() {
        //Um aktuelles Datum und Zeit zu bekommen
        Date now = new Date();
        this.eventCreated = now;
    }

    public Integer getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(Integer userUpdated) {
        this.userUpdated = userUpdated;
    }

    public int getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(int userCreated) {
        this.userCreated = userCreated;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    @XmlTransient
    public Collection<BmwFlight> getBmwFlightCollection() {
        return bmwFlightCollection;
    }

    public void setBmwFlightCollection(Collection<BmwFlight> bmwFlightCollection) {
        this.bmwFlightCollection = bmwFlightCollection;
    }

    @XmlTransient
    public Collection<BmwParticipants> getBmwParticipantsCollection() {
        return bmwParticipantsCollection;
    }

    public void setBmwParticipantsCollection(Collection<BmwParticipants> bmwParticipantsCollection) {
        this.bmwParticipantsCollection = bmwParticipantsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BmwEvent)) {
            return false;
        }
        BmwEvent other = (BmwEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ac.at.fhkufstein.entity.BmwEvent[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<EmailTemplates> getEmailTemplatesCollection() {
        return emailTemplatesCollection;
    }

    public void setEmailTemplatesCollection(Collection<EmailTemplates> emailTemplatesCollection) {
        this.emailTemplatesCollection = emailTemplatesCollection;
    }

    public Integer getSendFollowup() {
        return sendFollowup;
    }

    public void setSendFollowup(Integer sendFollowup) {
        this.sendFollowup = sendFollowup;
    }

    public Integer getBmwParticipants() {
        return bmwParticipants;
    }

    public void setBmwParticipants(Integer bmwParticipants) {
        this.bmwParticipants = bmwParticipants;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public Boolean getEmbargo() {
        return embargo;
    }

    public void setEmbargo(Boolean embargo) {
        this.embargo = embargo;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public Integer getSendReminder() {
        return sendReminder;
    }

    public void setSendReminder(Integer sendReminder) {
        this.sendReminder = sendReminder;
    }

    public Integer getCancelInvitation() {
        return cancelInvitation;
    }

    public void setCancelInvitation(Integer cancelInvitation) {
        this.cancelInvitation = cancelInvitation;
    }

    public Date getCloseInvitation() {
        return closeInvitation;
    }

    public void setCloseInvitation(Date closeInvitation) {
        this.closeInvitation = closeInvitation;
    }

    public Date getEmbargoDate() {
        return embargoDate;
    }

    public void setEmbargoDate(Date embargoDate) {
        this.embargoDate = embargoDate;
    }

    public BmwUser getTravelAgency() {
        return travelAgency;
    }

    public void setTravelAgency(BmwUser travelAgency) {
        this.travelAgency = travelAgency;
    }

    /**
     * @return the released
     */
    public Boolean getReleased() {
        return released;
    }

    /**
     * @param released the released to set
     */
    public void setReleased(Boolean released) {
        this.released = released;
    }
}
