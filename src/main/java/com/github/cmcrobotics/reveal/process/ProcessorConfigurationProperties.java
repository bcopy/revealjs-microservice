package com.github.cmcrobotics.reveal.process;

import java.util.ArrayList;
import java.util.List;

public class ProcessorConfigurationProperties {
	private List<String> visitors = new ArrayList<>();

	private List<String> contentPaths = new ArrayList<>();

	public List<String> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<String> visitors) {
		this.visitors = visitors;
	}

	public List<String> getContentPaths() {
		return contentPaths;
	}

	public void setContentPaths(List<String> contentPaths) {
		this.contentPaths = contentPaths;
	}
}
