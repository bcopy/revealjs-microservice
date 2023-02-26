package com.github.cmcrobotics.reveal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Category {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getModified() {
		return modified;
	}

	public void setModified(Long modified) {
		this.modified = modified;
	}

	public Boolean getDisplayed() {
		return displayed;
	}

	public void setDisplayed(Boolean displayed) {
		this.displayed = displayed;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Map<String, Item> getItemsMap() {
		return itemsMap;
	}

	public void setItemsMap(Map<String, Item> itemsMap) {
		this.itemsMap = itemsMap;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	String id;
	String title;
	Long created;
	Long modified;
	
	Boolean displayed;

	Category parent;

	Map<String, Item> itemsMap = new HashMap<>();
	
	List<Item> items = new ArrayList<>();
	
	Map<String, String> metadata;

	public Item getOrCreateItemByName(String name) {
		return itemsMap.computeIfAbsent(name, key -> {
			Item item = new Item();
			item.setTitle(name);
			items.add(item);
			return item;
		});
	}
}
