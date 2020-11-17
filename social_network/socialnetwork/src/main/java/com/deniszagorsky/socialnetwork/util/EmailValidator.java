package com.deniszagorsky.socialnetwork.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидатор электронной почты
 */
public class EmailValidator {

    /**
     * Регулярное выражение
     */
    private final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*"
            + "@" + "[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$");

    /**
     * Матчер для проверки соответствия строки регулярному выражению
     */
    private Matcher matcher;

    /**
     * Валидация электронной почты
     * @param email Электронная почта
     * @return true, если электронная почта соотвествует регулярному выражению;
     * false, если электронная почта не соответствует регулярному выражению
     */
    public boolean isValid(String email) {
        matcher = PATTERN.matcher(email);
        return matcher.matches();
    }

}
