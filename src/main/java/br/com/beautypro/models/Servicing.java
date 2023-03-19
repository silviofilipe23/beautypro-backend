package br.com.beautypro.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicing", uniqueConstraints = {
//        @UniqueConstraint(columnNames = "email"),
})
public class Servicing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToMany
    @JoinTable(name = "servicing_user", joinColumns = @JoinColumn(name = "servicing_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> professionalList = new ArrayList<>();

    @NotNull
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    public Servicing() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<User> getProfessionalList() {
        return professionalList;
    }

    public void setProfessionalList(List<User> professionalList) {
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
