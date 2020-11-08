package com.example.search;

public class Groupss {
    private String GroupName;
    private String ImageUri;

    public Groupss(String groupName, String imageUri) {
        GroupName = groupName;
        ImageUri = imageUri;
    }

    public Groupss() {
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }
}
