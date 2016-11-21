package com.luminouslead.web;

import javax.inject.Inject;

import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.upm.api.license.PluginLicenseManager;

public class MyPluginComponentImpl {


    private PluginLicenseManager licenseManager;
    private ApplicationProperties applicationProperties;

	@Inject                                                                  
	public MyPluginComponentImpl(final ApplicationProperties applicationProperties, 
		@ComponentImport("pluginLicenseManager") PluginLicenseManager pluginLicenseManager) {
	    this.licenseManager = pluginLicenseManager;                          
	    this.applicationProperties = applicationProperties;                  
	}
}
