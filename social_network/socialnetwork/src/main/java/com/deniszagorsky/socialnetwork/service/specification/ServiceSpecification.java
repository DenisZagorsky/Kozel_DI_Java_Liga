package com.deniszagorsky.socialnetwork.service.specification;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * Спецификация для фильтров
 */

public class ServiceSpecification {

    /**
     * Поиск по вхождению строки
     * @param column Атрибут
     * @param string Значение
     * @return Спецификация
     */
    public static <T> Specification<T> like(final String column, final String string) {
        return StringUtils.isEmpty(column) || StringUtils.isEmpty(string)
                ? null
                : (root, query, cb) -> cb.like(cb.lower(root.get(column)),"%" + string.toLowerCase() + "%");
    }

    /**
     * Поиск по эквивалетности строки
     * @param column Атрибут
     * @param string Значение
     * @return Спецификация
     */
    public static <T> Specification<T> alike(final String column, final String string) {
        return StringUtils.isEmpty(column) || StringUtils.isEmpty(string)
                ? null
                : (root, query, cb) -> cb.like(cb.lower(root.get(column)), string.toLowerCase());
    }

    /**
     * Поиск по эквивалетности объекта
     * @param column Атрибут
     * @param object Значение
     * @return Спецификация
     */
    public static <T> Specification<T> equal(final String column, final Object object) {
        return StringUtils.isEmpty(column) || ObjectUtils.isEmpty(object)
                ? null
                : (root, query, cb) -> cb.equal(root.get(column), object);
    }

}
