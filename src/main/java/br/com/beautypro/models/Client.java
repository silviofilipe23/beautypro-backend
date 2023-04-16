package br.com.beautypro.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf"),
        @UniqueConstraint(columnNames = "rg"),

})
public class Client extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 11)
    private String cpf;

    @NotBlank
    @Size(max = 11)
    private String rg;

    @NotNull
    private LocalDate dateOfBirth;

    private String nameResponsible;

    private String cpfResponsible;

    private String rgResponsible;

    private String anamnese;

    public Client() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNameResponsible() {
        return nameResponsible;
    }

    public void setNameResponsible(String nameResponsible) {
        this.nameResponsible = nameResponsible;
    }

    public String getCpfResponsible() {
        return cpfResponsible;
    }

    public void setCpfResponsible(String cpfResponsible) {
        this.cpfResponsible = cpfResponsible;
    }

    public String getRgResponsible() {
        return rgResponsible;
    }

    public void setRgResponsible(String rgResponsible) {
        this.rgResponsible = rgResponsible;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }
}
