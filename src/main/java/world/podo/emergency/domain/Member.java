package world.podo.emergency.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;
    /**
     * 사용자 이름
     */
    private String name;
    /**
     * 사용자 모바일 기기 식별자
     */
    private String uuid;
    /**
     * fcm 푸시할 때 사용하는 토큰
     */
    private String fcmToken;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;
    @OneToMany
    private List<Country> countries = new ArrayList<>();

    public static Member from(String uuid) {
        return of(uuid, null);
    }

    public static Member of(String uuid, String fcmToken) {
        Member member = new Member();
        member.memberId = null;
        member.name = null;
        member.uuid = uuid;
        member.fcmToken = fcmToken;
        member.createdAt = null;
        member.updatedAt = null;
        return member;
    }
}
