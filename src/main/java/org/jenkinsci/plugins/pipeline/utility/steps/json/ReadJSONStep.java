/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Nikolas Falco
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.jenkinsci.plugins.pipeline.utility.steps.json;

import javax.annotation.Nonnull;

import org.jenkinsci.plugins.pipeline.utility.steps.AbstractFileOrTextStep;
import org.jenkinsci.plugins.pipeline.utility.steps.AbstractFileOrTextStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;

/**
 * Reads a JSON file from the workspace.
 *
 * @author Nikolas Falco
 */
public class ReadJSONStep extends AbstractFileOrTextStep {
    /**
     * If true POJO object will always be returned instead of the default net.sf.json.JSON objects. You can set this
     * property with the System property
     * <code>org.jenkinsci.plugins.pipeline.utility.steps.json.ReadJSONStep.returnPojo</code> or with an environment
     * variable <code>PIPELINE_UTILITY_JSON_RETURN_POJOS</code>.
     */
    public static final boolean RETURN_POJO_DEFAULT =
            Boolean.parseBoolean(System.getProperty(ReadJSONStep.class.getName() + ".returnPojo")) ||
                    Boolean.parseBoolean(System.getenv("PIPELINE_UTILITY_JSON_RETURN_POJOS"));

    protected Boolean returnPojo;

    @DataBoundConstructor
    public ReadJSONStep() {
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new ReadJSONStepExecution(this, context);
    }

    @Extension
    public static class DescriptorImpl extends AbstractFileOrTextStepDescriptorImpl {

        public DescriptorImpl() {

        }

        @Override
        public String getFunctionName() {
            return "readJSON";
        }

        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.ReadJSONStep_DescriptorImpl_displayName();
        }
    }

    /**
     * Whether to return a pure Java POJO made of Map and List or the deserialized JSON object (from json-lib).
     * Default is JSON.
     *
     * <p>
     * Another way to activate the returning POJOs can be by setting the following system property:
     * <code>-Dorg.jenkinsci.plugins.pipeline.utility.steps.json.ReadJSONStep.returnPojo=true</code> in your Jenkins
     * start script.
     * </p>
     *
     * @return whether to return a pure Java POJO made of Map and List or the deserialized JSON object (from json-lib).
     * Default is JSON.
     */
    public Boolean getReturnPojo() {
        return returnPojo;
    }

    /**
     * Whether to return a pure Java POJO made of Map and List or the deserialized JSON object (from json-lib).
     * Default is JSON.
     *
     * <p>
     * Another way to activate the returning POJOs can be by setting the following system property:
     * <code>-Dorg.jenkinsci.plugins.pipeline.utility.steps.json.ReadJSONStep.returnPojo=true</code> in your Jenkins
     * start script. If return Pojo
     * </p>
     *
     * @param returnPojo whether to return a pure Java POJO made of Map and List or the deserialized JSON object
     *                   (from json-lib). Default is JSON.
     */
    @DataBoundSetter
    public void setReturnPojo(Boolean returnPojo) {
        this.returnPojo = returnPojo;
    }

}
