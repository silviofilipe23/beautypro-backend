package br.com.beautypro.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "servicing")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Servicing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64)
    private String description;

    @NotNull
    private double price;

    @NotNull
    private int averageTime;

    @Lob
    private String preService;

    @Lob
    private String postService;

    private String returnDays;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "servicing_user", joinColumns = @JoinColumn(name = "servicing_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> professionalList = new HashSet<>();

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    public Servicing() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<User> getProfessionalList() {
        return professionalList;
    }

    public void setProfessionalList(Set<User> professionalList) {
        this.professionalList = professionalList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public String getPreService() {
        return preService;
    }

    public void setPreService(String preService) {
        this.preService = preService;
    }

    public String getPostService() {
        return postService;
    }

    public void setPostService(String postService) {
        this.postService = postService;
    }

    public String getReturnDays() {
        return returnDays;
    }

    public void setReturnDays(String returnDays) {
        this.returnDays = returnDays;
    }

}
