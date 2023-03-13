package com.github.cmcrobotics.reveal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Encompasses the current state of a slideshow being assembled by 
 * a visitor implementation.
 */
public final class Cursor {
   Presentation currentSlideshow;
   public Presentation getCurrentSlideshow() {
	return currentSlideshow;
}
public void setCurrentSlideshow(Presentation currentSlideshow) {
	this.currentSlideshow = currentSlideshow;
}
public Category getCurrentCategory() {
	return currentCategory;
}
public void setCurrentCategory(Category currentCategory) {
	this.currentCategory = currentCategory;
}
public Item getCurrentItem() {
	return currentItem;
}
public void setCurrentItem(Item currentItem) {
	this.currentItem = currentItem;
}
public AtomicInteger getHierarchyLevel() {
	return hierarchyLevel;
}
public void setHierarchyLevel(AtomicInteger hierarchyLevel) {
	this.hierarchyLevel = hierarchyLevel;
}
public List<Presentation> getSlideshows() {
	return slideshows;
}
public void setSlideshows(List<Presentation> slideshows) {
	this.slideshows = slideshows;
}
public Map<String, Presentation> getSlideshowsMap() {
	return slideshowsMap;
}
public void setSlideshowsMap(Map<String, Presentation> slideshowsMap) {
	this.slideshowsMap = slideshowsMap;
}

Category currentCategory;
   Item currentItem;
   
   AtomicInteger hierarchyLevel = new AtomicInteger();
   
   List<Presentation> slideshows = new ArrayList<>();
   
   Map<String, Presentation> slideshowsMap = new HashMap<>();
   
   public Cursor setOrCreateSlideshow(String name) {
	   currentSlideshow = getOrCreateSlideshowByName(name);
	   return this;
   }
   public Presentation getOrCreateSlideshowByName(String name){
	  return slideshowsMap.computeIfAbsent(name, key -> {
		  Presentation slideShow = new Presentation();
		  slideShow.setName(key);
		  slideshows.add(slideShow);
		  
		  return slideShow;
	  });
   }
   
   public Cursor setOrCreateCategoryInCurrentSlideshow(String name) {
	   currentCategory = getOrCreateCategoryInCurrentSlideshowByName(name);
	   return this;
   }

   public Category getOrCreateCategoryInCurrentSlideshowByName(String name){
	  return currentSlideshow.getOrCreateCategoryByName(name);
   }
   
   public Cursor setOrCreateItemInCurrentCategory(String name) {
	   currentItem = getOrCreateItemInCurrentCategoryByName(name);
	   return this;
   }

   public Item getOrCreateItemInCurrentCategoryByName(String name){
	      if(currentCategory == null) {
	    	  this.setOrCreateCategoryInCurrentSlideshow("main");
	      }
	   
		  return currentCategory.getOrCreateItemByName(name);
	   }
	   

   
}
