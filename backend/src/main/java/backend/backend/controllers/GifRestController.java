package backend.backend.controllers;

import jakarta.json.JsonObject;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.models.Comment;
import backend.backend.services.GifService;
import jakarta.json.Json;
import jakarta.json.JsonArray;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class GifRestController {

    @Autowired
    private GifService gifSvc;

    @GetMapping(path = "/gif/search")
    public ResponseEntity<String> searchGifs(@RequestParam String query){
        Optional<JsonArray> opt = gifSvc.searchGifs(query);
        if (opt.isEmpty()){
            String resp = Json.createObjectBuilder()
                                .add("error", "internal server error")
                                .build().toString();
            return ResponseEntity.status(500).body(resp);
        }
        return ResponseEntity.status(200).body(opt.get().toString());
    }

    @GetMapping(path = "/gif/{gid}")
    public ResponseEntity<String> getGifById(@PathVariable String gid){
        Optional<JsonObject> opt = gifSvc.getGif(gid);
        if (opt.isEmpty()){
            String resp = Json.createObjectBuilder()
                                .add("error", "gif does not exist")
                                .build().toString();
            return ResponseEntity.status(404).body(resp);
        }
        return ResponseEntity.status(200).body(opt.get().toString());
    }

    @PostMapping(path = "/gif/{gid}/comment")
    public ResponseEntity<String> saveComment(@PathVariable String gid, @RequestBody String payload){
        JsonObject data = Json.createReader(new StringReader(payload)).readObject();
        Comment comment = new Comment(gid, data.getString("comment"));
        try {
            gifSvc.saveComment(comment);
            String resp = Json.createObjectBuilder()
                                .add("result", "created")
                                .build().toString();
            return ResponseEntity.status(201).body(resp);
        }
        catch (Exception e){
            String resp = Json.createObjectBuilder()
                                .add("error", "internal server error")
                                .build().toString();
            return ResponseEntity.status(500).body(resp);
        }
    }
    
}
