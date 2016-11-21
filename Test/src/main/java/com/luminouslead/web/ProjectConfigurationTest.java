package com.luminouslead.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.web.ExecutingHttpRequest;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.upm.api.license.PluginLicenseManager;
import com.atlassian.upm.api.license.entity.LicenseError;
import com.atlassian.upm.api.license.entity.PluginLicense;

@Scanned
public class ProjectConfigurationTest extends JiraWebActionSupport {

	private static final long serialVersionUID = 3813308853568647045L;

	@ComponentImport("pluginLicenseManager")
	@Inject
    private PluginLicenseManager _pluginLicenseManager;
    
	private String			_projectKey;

	public void setProjectKey(String projectKey) {
		_projectKey = projectKey;
	}
	public String getProjectKey() {
		return _projectKey;
	}

    public void setPluginLicenseManager(PluginLicenseManager pluginLicenseManager) {
    	_pluginLicenseManager = pluginLicenseManager;
    }
    
	public ProjectConfigurationTest() {
	}

	public String doExecute() {

		ProjectManager projectManager = getProjectManager();
		Project project = projectManager.getProjectObjByKey(_projectKey);
        HttpServletRequest request = ExecutingHttpRequest.get();
        request.setAttribute("com.atlassian.jira.projectconfig.util.ServletRequestProjectConfigRequestCache:project", project);

		String viewName = setViewPage();
		
		return viewName;
	}

	private String setViewPage() {
		String result = SUCCESS;
		if( validateLicense() ) {
			result = SUCCESS;
		} else {
			result = ERROR;
		}
		return result;
	}
	
	private boolean validateLicense() {
		boolean isValid = false;
		
		try {
			if( _pluginLicenseManager.getLicense().isDefined() ) {
				PluginLicense pluginLicense = _pluginLicenseManager.getLicense().get();
				if( pluginLicense.getError().isDefined()) {
//					LicenseError licenseError = pluginLicense.getError().get();
					LicenseError licenseError = pluginLicense.getError().get();
					System.out.println(licenseError.toString());
					isValid = false;
				} else {
					isValid = true;
				}
			} else {
				isValid = false;
				System.out.println("License is not defined.");
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return isValid;
	}
}
