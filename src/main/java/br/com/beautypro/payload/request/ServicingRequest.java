package br.com.beautypro.payload.request;

import br.com.beautypro.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServicingRequest {

    @NotBlank
    @Size(max = 64)
    private String description;

    @NotNull
    private double price;

    @NotNull
    private int averageTime;

    private String preService;

    private String postService;

    private String returnDays;

    @NotNull
    private List<Long> professionalList = new ArrayList<>();

    public ServicingRequest() {
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

    public List<Long> getProfessionalList() {
        return professionalList;
    }

    public void setProfessionalList(List<Long> professionalList) {
        this.professionalList = professionalList;
    }

    public String getReturnDays() {
        return returnDays;
    }

    public void setReturnDays(String returnDays) {
        this.returnDays = returnDays;
    }
}
