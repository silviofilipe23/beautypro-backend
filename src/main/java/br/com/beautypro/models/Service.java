package br.com.beautypro.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_hour")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "appointment_time")
    @NotNull
    private int appointmentTime;

    @Column(name = "date_hour_return")
    private LocalDateTime dateHourReturn;

    @Column(name = "created_date")
    @NotNull
    private LocalDateTime createdDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Lob
    private String observations;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private TermOfConsent termOfConsent;

    @ManyToOne
    @JoinColumn(name = "servicing_id", referencedColumnName = "id")
    private Servicing servicing;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean open;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    public Service() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Servicing getServicing() {
        return servicing;
    }

    public void setServicing(Servicing servicing) {
        this.servicing = servicing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public TermOfConsent getTermOfConsent() {
        return termOfConsent;
    }

    public void setTermOfConsent(TermOfConsent termOfConsent) {
        this.termOfConsent = termOfConsent;
    }

    public LocalDateTime getDateHour() {
        return dateTime;
    }

    public void setDateHour(LocalDateTime dateHour) {
        this.dateTime = dateHour;
    }

    public LocalDateTime getDateHourReturn() {
        return dateHourReturn;
    }

    public void setDateHourReturn(LocalDateTime dateHourReturn) {
        this.dateHourReturn = dateHourReturn;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(int appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
