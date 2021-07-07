package com.roooqaTv.roooqatv.data.locale.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomVideos {
    @PrimaryKey(autoGenerate = true)

    int Id;
    int itemId;
    public String NameItem;
    public String Link;
    boolean isFavor;

    public RoomVideos() {
    }

    public RoomVideos(int itemId, String nameItem, String link) {
        this.itemId = itemId;
        NameItem = nameItem;
        Link = link;
        this.isFavor = isFavor;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getNameItem() {
        return NameItem;
    }

    public void setNameItem(String nameItem) {
        NameItem = nameItem;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public boolean isFavor() {
        return isFavor;
    }

    public void setFavor(boolean favor) {
        isFavor = favor;
    }
}