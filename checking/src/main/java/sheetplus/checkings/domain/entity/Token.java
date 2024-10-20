package sheetplus.checkings.domain.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "token", timeToLive = 2592000)
@AllArgsConstructor
@Getter @Builder @NoArgsConstructor
public class Token {

    @Id
    private Long id;
    private String refreshToken;
}
