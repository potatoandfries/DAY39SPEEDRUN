package backend.backend.models;

import java.io.StringReader;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Gif {
    private String gid;
    private String title;
    private String username;
    private String url;
    private List<Comment> comments;

    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                    .add("gid", gid)
                    .add("title", title)
                    .add("username", username)
                    .add("url", url)
                    .build();
    }

    public static Gif toGif(String data){
        JsonObject obj = Json.createReader(new StringReader(data)).readObject();
        Gif gif = new Gif();
        gif.setGid(obj.getString("gid"));
        gif.setTitle(obj.getString("title"));
        gif.setUsername(obj.getString("username"));
        gif.setUrl(obj.getString("url"));
        return gif;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public JsonObject toCompleteJson(){
        JsonArrayBuilder builder = Json.createArrayBuilder();
        comments.forEach(comment -> builder.add(comment.toJson()));
        return Json.createObjectBuilder()
                    .add("gid", gid)
                    .add("title", title)
                    .add("username", username)
                    .add("url", url)
                    .add("comments", builder.build())
                    .build();
    }
}
