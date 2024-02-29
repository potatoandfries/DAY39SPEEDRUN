package backend.backend.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import backend.backend.models.Gif;

import static backend.backend.Utils.*;

import java.time.Duration;
import java.util.Optional;

@Repository
public class GifRepo {
    
    @Autowired @Qualifier(BEAN_REDIS)
    private RedisTemplate<String, String> template;

    public void saveGif(Gif gif){
        ValueOperations<String, String> valueOps = template.opsForValue();
        valueOps.set(gif.getGid(), gif.toJson().toString(), Duration.ofHours(1));
    }

    public Optional<Gif> getGif(String gid){
        ValueOperations<String, String> valueOps = template.opsForValue();
        String data = valueOps.get(gid);
        if (data == null){
            return Optional.empty();
        }
        return Optional.of(Gif.toGif(data));
    }
}
