package world.podo.travelable.domain.member;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
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
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "member")
    private List<MemberCountry> memberCountries = new ArrayList<>();

    static Member from(String uuid) {
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
