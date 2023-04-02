package br.com.beautypro.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_hour")
    @NotNull
    private String dateHour;

    @Column(name = "date_hour_return")
    private String dateHourReturn;

    @Column(name = "created_date")
    @NotNull
    private String createdDate;

    @Column(name = "end_date")
    private String endDate;

    @Lob
    private String observations;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "servicing_id", referencedColumnName = "id")
    private Servicing servicing;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean termOfConsent;

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean open;

    @PrePersist
    public void prePersist() {
        this.createdDate = String.valueOf(LocalDateTime.now());
    }

    public Service() {
    }

    public Service(String dateHour, String dateHourReturn, String createdDate, String endDate, String observations, Client client, Servicing servicing, User user, boolean termOfConsent, boolean open) {
        this.dateHour = dateHour;
        this.dateHourReturn = dateHourReturn;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.observations = observations;
        this.client = client;
        this.servicing = servicing;
        this.user = user;
        this.termOfConsent = termOfConsent;
        this.open = open;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public String getDateHourReturn() {
        return dateHourReturn;
    }

    public void setDateHourReturn(String dateHourReturn) {
        this.dateHourReturn = dateHourReturn;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public boolean isTermOfConsent() {
        return termOfConsent;
    }

    public void setTermOfConsent(boolean termOfConsent) {
        this.termOfConsent = termOfConsent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
