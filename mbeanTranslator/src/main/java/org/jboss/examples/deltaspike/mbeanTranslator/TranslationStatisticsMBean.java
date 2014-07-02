/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.examples.deltaspike.mbeanTranslator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.deltaspike.core.api.jmx.JmxManaged;
import org.apache.deltaspike.core.api.jmx.MBean;
import org.jboss.examples.deltaspike.mbeanTranslator.messages.Language;

@ApplicationScoped
@MBean(description = "Manages statistics of language translations.")
public class TranslationStatisticsMBean {

    private int translations = 0;
    private int frenchTranslations = 0;
    private int germanTranslations = 0;

    @Inject
    Translator translator;

    @JmxManaged(description = "Number of all translations.")
    public int getTranslations() {
        return translations;
    }

    @JmxManaged(description = "Percentage of German translations.")
    public double getGermanTranslationsPercentage() {
        double percentage = 0;
        if (translations > 0) {
            percentage = 100 * (double) germanTranslations / translations;
        }
        return Math.round(percentage);
    }

    @JmxManaged(description = "Percentage of German translations.")
    public double getFrenchTranslationsPercentage() {
        double percentage = 0;
        if (translations > 0) {
            percentage = 100 * (double) frenchTranslations / translations;
        }
        return Math.round(percentage);
    }

    public String passToTranslator(String text, Language lang) {

        translations++;
        if (lang.equals(Language.GERMAN)) {
            germanTranslations++;
        } else {
            frenchTranslations++;
        }

        return translator.translate(text);
    }

    public void reset(){
        translations = 0;
        frenchTranslations = 0;
        germanTranslations = 0;
    }
}
