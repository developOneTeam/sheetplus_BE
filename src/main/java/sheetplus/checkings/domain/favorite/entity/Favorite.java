package sheetplus.checkings.domain.favorite.entity;

import jakarta.persistence.*;
import lombok.*;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.member.entity.Member;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member favoriteMember;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest favoriteContest;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event favoriteEvent;

    public void setMemberFavorite(Member member){
        this.favoriteMember = member;
        member.getFavorites().add(this);
    }


}
