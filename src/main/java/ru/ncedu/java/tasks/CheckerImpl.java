package ru.ncedu.java.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * un1acker
 * 12.03.2015
 */
public class CheckerImpl implements Checker {
    @Override
    public Pattern getPLSQLNamesPattern() {
        return Pattern.compile("^[A-Za-z]([A-Za-z0-9_$]){0,29}$");
    }

    @Override
    public Pattern getHrefURLPattern() {
        return Pattern.compile("<\\s*a\\s*href\\s*=\\s*((\"[^\"]*\")|([^\"\\s]+))\\s*>.*(<\\s*/a\\s*>)?", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public Pattern getEMailPattern() {
        return Pattern.compile("^[A-Za-z0-9]([A-Za-z0-9]|(\\.|-|_)[A-Za-z0-9]){0,21}@[A-Za-z0-9]+(-|\\.)?([A-Za-z0-9]+)*([A-Za-z0-9]+\\.(ru|com|net|org))$");
    }

    @Override
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null ^ pattern == null) throw new IllegalArgumentException();
        if (inputString == null && pattern == null) return true;
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null || pattern == null) throw new IllegalArgumentException();
        List<String> allTemplates = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputString);
        int count = 0;
        while (matcher.find()) {
            allTemplates.add(count, matcher.group());
            count++;
        }
        return allTemplates;
    }
}
