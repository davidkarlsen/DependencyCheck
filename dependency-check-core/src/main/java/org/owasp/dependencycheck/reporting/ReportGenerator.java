/*
 * This file is part of dependency-check-core.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) 2012 Jeremy Long. All Rights Reserved.
 */
package org.owasp.dependencycheck.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.owasp.dependencycheck.analyzer.Analyzer;
import org.owasp.dependencycheck.data.nvdcve.DatabaseProperties;
import org.owasp.dependencycheck.dependency.Dependency;
import org.owasp.dependencycheck.utils.Settings;

/**
 * The ReportGenerator is used to, as the name implies, generate reports. Internally the generator uses the Velocity
 * Templating Engine. The ReportGenerator exposes a list of Dependencies to the template when generating the report.
 *
 * @author Jeremy Long <jeremy.long@owasp.org>
 */
public class ReportGenerator {

    /**
     * An enumeration of the report formats.
     */
    public enum Format {

        /**
         * Generate all reports.
         */
        ALL,
        /**
         * Generate XML report.
         */
        XML,
        /**
         * Generate HTML report.
         */
        HTML,
        /**
         * Generate HTML Vulnerability report.
         */
        VULN
    }
    /**
     * The Velocity Engine.
     */
    private final VelocityEngine engine;
    /**
     * The Velocity Engine Context.
     */
    private final Context context;

    /**
     * Constructs a new ReportGenerator.
     *
     * @param applicationName the application name being analyzed
     * @param dependencies the list of dependencies
     * @param analyzers the list of analyzers used
     * @param properties the database properties (containing timestamps of the NVD CVE data)
     */
    public ReportGenerator(String applicationName, List<Dependency> dependencies, List<Analyzer> analyzers, DatabaseProperties properties) {
        engine = createVelocityEngine();
        context = createContext();

        engine.init();

        context.put("applicationName", applicationName);
        context.put("dependencies", dependencies);
        context.put("analyzers", analyzers);
        context.put("properties", properties);
        context.put("version", Settings.getString("application.version", "Unknown"));
    }

    /**
     * Creates a new Velocity Engine.
     *
     * @return a velocity engine.
     */
    private VelocityEngine createVelocityEngine() {
        final VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, VelocityLoggerRedirect.class.getName());
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        return ve;
    }

    /**
     * Creates a new Velocity Context initialized with escape and date tools.
     *
     * @return a Velocity Context.
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_INFERRED",
            justification = "No plan to fix this style issue")
    private Context createContext() {
        final ToolManager manager = new ToolManager();
        final Context c = manager.createContext();
        final EasyFactoryConfiguration config = new EasyFactoryConfiguration();
        config.addDefaultTools();
        config.toolbox("application").tool("esc", "org.apache.velocity.tools.generic.EscapeTool").tool("org.apache.velocity.tools.generic.DateTool");
        manager.configure(config);
        return c;
    }

    /**
     * Generates the Dependency Reports for the identified dependencies.
     *
     * @param outputDir the path where the reports should be written
     * @param format the format the report should be written in
     * @throws IOException is thrown when the template file does not exist
     * @throws Exception is thrown if there is an error writing out the reports.
     */
    public void generateReports(String outputDir, Format format) throws IOException, Exception {
        if (format == Format.XML || format == Format.ALL) {
            generateReport("XmlReport", outputDir + File.separator + "DependencyCheck-Report.xml");
        }
        if (format == Format.HTML || format == Format.ALL) {
            generateReport("HtmlReport", outputDir + File.separator + "DependencyCheck-Report.html");
        }
        if (format == Format.VULN || format == Format.ALL) {
            generateReport("VulnerabilityReport", outputDir + File.separator + "DependencyCheck-Vulnerability.html");
        }
    }

    /**
     * Generates the Dependency Reports for the identified dependencies.
     *
     * @param outputDir the path where the reports should be written
     * @param outputFormat the format the report should be written in (XML, HTML, ALL)
     * @throws IOException is thrown when the template file does not exist
     * @throws Exception is thrown if there is an error writing out the reports.
     */
    public void generateReports(String outputDir, String outputFormat) throws IOException, Exception {
        final String format = outputFormat.toUpperCase();
        if (format.matches("^(XML|HTML|VULN|ALL)$")) {
            if ("XML".equalsIgnoreCase(format)) {
                generateReports(outputDir, Format.XML);
            }
            if ("HTML".equalsIgnoreCase(format)) {
                generateReports(outputDir, Format.HTML);
            }
            if ("VULN".equalsIgnoreCase(format)) {
                generateReports(outputDir, Format.VULN);
            }
            if ("ALL".equalsIgnoreCase(format)) {
                generateReports(outputDir, Format.ALL);
            }
        }
    }

    /**
     * Generates a report from a given Velocity Template. The template name provided can be the name of a template
     * contained in the jar file, such as 'XmlReport' or 'HtmlReport', or the template name can be the path to a
     * template file.
     *
     * @param templateName the name of the template to load.
     * @param outFileName the filename and path to write the report to.
     * @throws IOException is thrown when the template file does not exist.
     * @throws Exception is thrown when an exception occurs.
     */
    protected void generateReport(String templateName, String outFileName) throws IOException, Exception {
        InputStream input = null;
        String templatePath = null;
        final File f = new File(templateName);
        if (f.exists() && f.isFile()) {
            try {
                templatePath = templateName;
                input = new FileInputStream(f);
            } catch (FileNotFoundException ex) {
                final String msg = "Unable to generate the report, the report template file could not be found.";
                Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, msg);
                Logger.getLogger(ReportGenerator.class.getName()).log(Level.FINE, null, ex);
            }
        } else {
            templatePath = "templates/" + templateName + ".vsl";
            input = this.getClass().getClassLoader().getResourceAsStream(templatePath);
        }
        if (input == null) {
            throw new IOException("Template file doesn't exist");
        }

        final InputStreamReader reader = new InputStreamReader(input, "UTF-8");
        OutputStreamWriter writer = null;
        OutputStream outputStream = null;

        try {
            final File outDir = new File(outFileName).getParentFile();
            if (!outDir.exists()) {
                final boolean created = outDir.mkdirs();
                if (!created) {
                    throw new Exception("Unable to create directory '" + outDir.getAbsolutePath() + "'.");
                }
            }

            outputStream = new FileOutputStream(outFileName);
            writer = new OutputStreamWriter(outputStream, "UTF-8");

            if (!engine.evaluate(context, writer, templatePath, reader)) {
                throw new Exception("Failed to convert the template into html.");
            }
            writer.flush();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReportGenerator.class.getName()).log(Level.FINEST, null, ex);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReportGenerator.class.getName()).log(Level.FINEST, null, ex);
                }
            }
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ReportGenerator.class.getName()).log(Level.FINEST, null, ex);
            }
        }
    }
}
