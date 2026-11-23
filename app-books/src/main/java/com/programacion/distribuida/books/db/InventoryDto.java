package com.programacion.distribuida.books.db;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.programacion.distribuida.books.db.Inventory}
 */
@Value
public class InventoryDto implements Serializable {
    Integer sold;
    Integer supplied;
}