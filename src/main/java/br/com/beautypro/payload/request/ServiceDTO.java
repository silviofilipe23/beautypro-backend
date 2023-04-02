package br.com.beautypro.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ServiceDTO {

    @NotNull
    private LocalDateTime dateHour;

    @NotNull
    private LocalDateTime dateHourReturn;

    private LocalDateTime endDate;

    private String observations;

    @NotNull
    private Long clientId;

    @NotNull
    private Long servicingId;

    @NotNull
    private Long userId;
}
