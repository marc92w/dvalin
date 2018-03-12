/**
 *
 */
package de.taimos.dvalin.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author aeichel/psigloch
 */
@Component
public class I18nLoader implements II18nCallback, II18nAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nLoader.class);
    private static final Map<String, Map<String, String>> stringMap = new HashMap<>();

    @Value("${i18n.locale.default:en}")
    private String DEFAULT_LOCALE_STRING;
    private Locale DEFAULT_LOCALE;

    @Autowired
    @Qualifier("${i18n.type:i18n-xml}")
    private II18nResourceHandler resourceHandler;

    @PostConstruct
    private void initializeResources() {
        this.DEFAULT_LOCALE = Locale.forLanguageTag(this.DEFAULT_LOCALE_STRING);
        this.resourceHandler.initializeResources(this);
    }

    @Override
    public void addText(String key, String locale, String label) {
        Map<String, String> entry = I18nLoader.stringMap.computeIfAbsent(locale, k -> new HashMap<>());
        entry.put(key, label);

    }

    @Override
    public String getString(String identifier) {
        return this.getString(null, identifier);
    }

    @Override
    public String getString(Locale locale, String identifier) {
        String theResult = null;
        Locale usedLocal = locale == null ? this.DEFAULT_LOCALE : locale;
        Map<String, String> table = I18nLoader.stringMap.get(usedLocal.getLanguage());
        if(table != null) {
            theResult = table.get(identifier);
        }
        if(theResult == null) {
            if(!usedLocal.equals(this.DEFAULT_LOCALE)) {
                return this.getString(this.DEFAULT_LOCALE, identifier);
            }
            I18nLoader.LOGGER.error("Did not find text key " + identifier);
            return '!' + identifier + '!';
        }
        return theResult;
    }

    @Override
    public String getString(String identifier, String... arguments) {
        return this.getString(null, identifier, arguments);
    }

    @Override
    public String getString(Locale locale, String identifier, String... arguments) {
        String text = this.getString(locale, identifier);
        if(text == null || text.trim().isEmpty()) {
            return text;
        }
        if(arguments == null || arguments.length < 1) {
            return text;
        }

        for(int i = 0; i < arguments.length; i++) {
            if(arguments[i] != null && !arguments[i].isEmpty()) {
                text = text.replace("{" + i + "}", arguments[i]);
            }
        }
        return text;
    }

    @Override
    public String getString(Enum<?> identifier) {
        return this.getString(null, identifier);
    }

    @Override
    public String getString(Locale locale, Enum<?> identifier) {
        final String key = identifier.getClass().getCanonicalName() + "." + identifier.name();
        return this.getString(locale, key);
    }
}