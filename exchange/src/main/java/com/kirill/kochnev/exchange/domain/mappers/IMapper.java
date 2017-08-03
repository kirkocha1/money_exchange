package com.kirill.kochnev.exchange.domain.mappers;

/**
 * Created by kirill on 03.08.17.
 */

public interface IMapper<F, T> {
    T map(F from);
}
