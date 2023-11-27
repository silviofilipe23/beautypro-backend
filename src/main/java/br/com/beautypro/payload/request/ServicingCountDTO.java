package br.com.beautypro.payload.request;

public class ServicingCountDTO {

    private Long servicingId;
    private String description;
    private long count;

    public ServicingCountDTO(Long servicingId, String description, long count) {
        this.servicingId = servicingId;
        this.description = description;
        this.count = count;
    }

    public Long getServicingId() {
        return servicingId;
    }

    public void setServicingId(Long servicingId) {
        this.servicingId = servicingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
