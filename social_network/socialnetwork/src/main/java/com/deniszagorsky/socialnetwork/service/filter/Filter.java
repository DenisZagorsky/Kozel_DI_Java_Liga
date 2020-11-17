package com.deniszagorsky.socialnetwork.service.filter;

import org.springframework.data.jpa.domain.Specification;

/**
 * Базовый интерфейс для группы фильтров
 */

@FunctionalInterface
public interface Filter<T> {

    /**
     * Определение спецификации для группы фильтров
     * @return
     */
    Specification<T> toSpecification();

}
