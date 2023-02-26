package com.github.cmcrobotics.reveal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public final class Presentation {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Category> getCategoriesMap() {
		return categoriesMap;
	}

	public void setCategoriesMap(Map<String, Category> categoriesMap) {
		this.categoriesMap = categoriesMap;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	String id = UUID.randomUUID().toString();
	String name;

	Map<String, Category> categoriesMap = new HashMap<>();
	
	List<Category> categories = new ArrayList<>();

	Map<String, String> metadata;
	
	String template;
	
	public Category getOrCreateCategoryByName(String name) {
		return categoriesMap.computeIfAbsent(name, key -> {
			Category category = new Category();
			category.setTitle(name);
			categories.add(category);
			return category;
		});
	}
}
