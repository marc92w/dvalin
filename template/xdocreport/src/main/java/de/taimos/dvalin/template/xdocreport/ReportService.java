/*
 * Copyright (c) 2016. Taimos GmbH http://www.taimos.de
 */

package de.taimos.dvalin.template.xdocreport;

/*-
 * #%L
 * Dvalin XDocReport support
 * %%
 * Copyright (C) 2016 - 2017 Taimos GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.stereotype.Service;

import de.taimos.dvalin.template.velocity.tools.DateTool;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@Service
public class ReportService {

    /**
     * @param template   the name of the template file relative to /xdocreport/
     * @param contextMap the KV map containing the context to replace
     * @param out        the stream to write the created PDF to
     */
    public void generatePDF(String template, Map<String, Object> contextMap, OutputStream out) {
        try {
            // Load .docx file by filling Velocity template engine and cache it to the registry
            InputStream in = ReportService.class.getResourceAsStream("/xdocreport/" + template);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // Create context Java model
            IContext context = report.createContext();
            context.putMap(contextMap);
            context.put("dateTool", new DateTool());

            // Generate report by merging Java model with the docx file
            report.convert(context, Options.getTo(ConverterTypeTo.PDF), out);
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException("Error creating PDF", e);
        }
    }

}
