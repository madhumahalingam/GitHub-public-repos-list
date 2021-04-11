package com.example.githubrepos.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CommentsDao {
    @Query("Select * from comments where repo_id = :repo_id")
    LiveData<List<Comment>> getCommentsForRepo(int repo_id);

    @Insert
    void addComment(Comment comment);

    @Query("delete from comments where repo_id = :repo_id")
    void deleteCommentsForRepo(int repo_id);

    @Query("delete from comments where id = :id")
    void deleteSingleComment(int id);
}
