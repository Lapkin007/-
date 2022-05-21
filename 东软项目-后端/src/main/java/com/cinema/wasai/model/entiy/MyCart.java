package com.cinema.wasai.model.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class MyCart implements Serializable {
    public Arrangement arr;
    public Film film;
    public Cart cart;

    public Arrangement getArr() {
        return arr;
    }

    public void setArr(Arrangement arr) {
        this.arr = arr;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
