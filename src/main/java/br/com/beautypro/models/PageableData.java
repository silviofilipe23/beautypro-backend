package br.com.beautypro.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageableData<T> {

    private List<T> data;
    private Long total;
    private Integer pages;
    private Integer size;
}
