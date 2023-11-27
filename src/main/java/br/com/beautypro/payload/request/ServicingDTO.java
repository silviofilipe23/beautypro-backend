package br.com.beautypro.payload.request;

public class ServicingDTO {

    private Long id;
    private String description;
    private double price;
    private int averageTime;
    private String preService;
    private String postService;
    private String returnDays;
//    private Set<UserDTO> professionalList;

    // Construtores, getters e setters


    public ServicingDTO() {

    }

    // Exemplo de construtor
    public ServicingDTO(Long id, String description, double price, int averageTime, String preService,
                        String postService, String returnDays) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.averageTime = averageTime;
        this.preService = preService;
        this.postService = postService;
        this.returnDays = returnDays;
//        this.professionalList = professionalList;
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
