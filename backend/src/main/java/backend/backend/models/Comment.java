package backend.backend.models;

import java.time.LocalDateTime;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    private String gid;
    private String comment;
    private LocalDateTime timestamp;

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }
    
    public Comment(String gid, String comment) {
        this.gid = gid;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                    .add("gid", gid)
                    .add("comment", comment)
                    .add("timestamp", timestamp.toString())
                    .build();
    }

    public static Comment toComment(Document doc){
        Comment comment = new Comment(doc.getString("gid"), doc.getString("comment"));
        comment.setTimestamp(LocalDateTime.parse(doc.getString("timestamp")));
        return comment;
    }
    
}
