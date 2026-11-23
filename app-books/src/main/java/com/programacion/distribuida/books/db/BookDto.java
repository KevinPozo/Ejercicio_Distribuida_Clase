package com.programacion.distribuida.books.db;

import com.programacion.distribuida.books.dtos.AuthorDto;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class BookDto implements Serializable {
    String isbn;
    String title;
    BigDecimal price;
    Integer inventorySold;
    Integer inventorySupplied;
    private List<AuthorDto> authors;
}