package com.example.githubrepos.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Comments")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "repo_id")
    public int repo_id;
    @ColumnInfo(name= "comment")
    public String comment;
    @ColumnInfo(name = "user_name")
    public String user_name;

    @Ignore
    public Comment(int repo_id, String comment, String user_name) {
        this.repo_id = repo_id;
        this.comment = comment;
        this.user_name = user_name;
    }

    public Comment(int id, int repo_id, String comment, String user_name) {
        this.id = id;
        this.repo_id = repo_id;
        this.comment = comment;
        this.user_name = user_name;
    }


    public String getComment(){
        return  comment;
    }
}
