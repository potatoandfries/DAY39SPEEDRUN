package backend.backend.repos;

import java.util.Comparator;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import backend.backend.models.Comment;

@Repository
public class CommentRepo {
    
    @Autowired
    private MongoTemplate template;

    public void saveComment(Comment comment){
        Document doc = Document.parse(comment.toJson().toString());
        template.insert(doc, "comments");
    }

    public List<Comment> getComments(String gid){
        Query query = Query.query(
            Criteria.where("gid").is(gid)
        );
        List<Document> result = template.find(query, Document.class, "comments");
        return result.stream()
                .map(doc -> Comment.toComment(doc))
                .sorted(Comparator.comparing(Comment::getTimestamp).reversed())
                .limit(10)
                .toList();
    }
}
