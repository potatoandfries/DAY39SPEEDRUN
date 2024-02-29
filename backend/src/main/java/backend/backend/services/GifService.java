package backend.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import backend.backend.models.Comment;
import backend.backend.models.Gif;
import backend.backend.repos.CommentRepo;
import backend.backend.repos.GifRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import static backend.backend.Utils.*;

import java.io.StringReader;
import java.util.Optional;

@Service
public class GifService {

    @Autowired
    private GifRepo gifRepo;
    
    @Autowired
    private CommentRepo commentRepo;
    
    private RestTemplate template = new RestTemplate();

    @Value("${apikey}")
    private String apiKey;

    public Optional<JsonArray> searchGifs(String query) {
        String url = UriComponentsBuilder.fromUriString(GIF_SEARCH)
                        .queryParam("q", query)
                        .queryParam("limit", 20)
                        .queryParam("offset", 0)
                        .queryParam("api_key", apiKey)
                        .build()
                        .toUriString();
        RequestEntity<Void> req = RequestEntity.get(url)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .build();
        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
            String data = resp.getBody();
            JsonObject obj = Json.createReader(new StringReader(data)).readObject();
            JsonArray array = obj.getJsonArray("data");
            JsonArrayBuilder builder = Json.createArrayBuilder();
            array.stream().map(jsonValue -> jsonValue.asJsonObject()).forEach(o -> {
                builder.add(Json.createObjectBuilder().add("gid", o.getString("id")).add("title", o.getString("title")).build());
                Gif gif = new Gif();
                gif.setGid(o.getString("id"));
                gif.setTitle(o.getString("title"));
                gif.setUsername(o.getString("username"));
                gif.setUrl(o.getJsonObject("images").getJsonObject("original").getString("url"));
                gifRepo.saveGif(gif);
            });
            return Optional.of(builder.build());
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<JsonObject> getGif(String gid){
        Gif gif;
        Optional<Gif> opt = gifRepo.getGif(gid);
        if (opt.isPresent()){
            gif = opt.get();
        }
        else {
            String url = UriComponentsBuilder.fromUriString("%s/%s".formatted(GIF_GET_BY_ID, gid))
                            .queryParam("api_key", apiKey)
                            .build()
                            .toUriString();
            RequestEntity<Void> req = RequestEntity.get(url)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .build();
            try {
                ResponseEntity<String> resp = template.exchange(req, String.class);
                String data = resp.getBody();
                JsonObject o = Json.createReader(new StringReader(data)).readObject().getJsonObject("data");
                gif = new Gif();
                gif.setGid(o.getString("id"));
                gif.setTitle(o.getString("title"));
                gif.setUsername(o.getString("username"));
                gif.setUrl(o.getJsonObject("images").getJsonObject("original").getString("url"));
                gifRepo.saveGif(gif);
            }
            catch (Exception e){
                return Optional.empty();
            }
        }
        gif.setComments(commentRepo.getComments(gif.getGid()));
        return Optional.of(gif.toCompleteJson());
    }

    public void saveComment(Comment comment){
        commentRepo.saveComment(comment);
    }
}
