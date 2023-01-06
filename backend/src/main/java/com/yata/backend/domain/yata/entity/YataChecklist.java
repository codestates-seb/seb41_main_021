package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.checklist.entity.ChecklistEntity;
import com.yata.backend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YataChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long YTCId;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    public void addYata(Yata yata){
        this.yata = yata;
    }

//    @OneToMany(mappedBy = "")
//    private List<ChecklistEntity> checkListList = new ArrayList<>();
}
