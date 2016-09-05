package de.taimos.dvalin.notification.push;

import java.util.HashMap;
import java.util.Map;

public class GCMNotificationMessage extends PushMessage {
    
    private final String message;
    private String title;
    private String icon;
    
    private Map<String, String> customData;
    
    public GCMNotificationMessage(String message) {
        this.message = message;
        this.customData.put("message", message);
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public void addCustomData(String key, String value) {
        if (this.customData == null) {
            this.customData = new HashMap<>();
        }
        this.customData.put(key, value);
    }
    
    @Override
    protected Map<String, Object> getPayload() {
        Map<String, Object> notification = new HashMap<>();
        notification.put("body",this.message);
        notification.put("title",this.title);
        notification.put("icon",this.icon);
        
        Map<String, Object> map = new HashMap<>();
        map.put("notification", notification);
        if (this.customData != null) {
            map.put("data", this.customData);
        }
        return map;
    }
    
    @Override
    public String getType() {
        return "GCM";
    }
}