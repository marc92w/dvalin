package de.taimos.dvalin.template.velocity;

import java.util.Map;

public interface ITemplateResolver {

    String resolveTemplate(String location, Map<String, Object> context);

    String resolveRawTemplate(String template, Map<String, Object> context);

}