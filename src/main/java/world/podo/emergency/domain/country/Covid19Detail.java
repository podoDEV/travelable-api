package world.podo.emergency.domain.country;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Covid19Detail {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Country country;
    /**
     * 총 사망자 수
     */
    private Integer totalDeathToll;
    /**
     * 총 확진자 수
     */
    private Integer totalConfirmCases;
    /**
     * 어제 오늘 확진자 수 차이
     */
    private Integer deltaConfirmCases;
    /**
     * 조회 시점
     */
    private LocalDateTime updatedAt;
}
