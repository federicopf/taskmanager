package com.example.taskmanager.dto;

public class SelectOption {
    private String value;
    private String displayName;
    private boolean selected;
    
    public SelectOption(String value, String displayName, boolean selected) {
        this.value = value;
        this.displayName = displayName;
        this.selected = selected;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
