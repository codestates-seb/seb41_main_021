package com.yata.backend.domain.yata.entity;

import com.yata.backend.domain.review.entity.Checklist;
import lombok.*;

import javax.persistence.*;

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
    private Long YataChecklistId;

    @ManyToOne
    @JoinColumn(name = "YATA_ID")
    private Yata yata;

    @ManyToOne
    @JoinColumn(name = "CHECKLIST_ID")
    private Checklist checklist;

    public void addYata(Yata yata){
        this.yata =yata;
    }

    public void addChecklist(Checklist checklist){
        this.checklist = checklist;
    }
}
