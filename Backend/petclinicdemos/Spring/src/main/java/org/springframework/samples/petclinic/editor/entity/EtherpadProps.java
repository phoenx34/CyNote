package org.springframework.samples.petclinic.editor.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="etherpad")
public class EtherpadProps {
	private String url;
	private String apiKey;
	
	public String getApiKey() {
		return apiKey;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
