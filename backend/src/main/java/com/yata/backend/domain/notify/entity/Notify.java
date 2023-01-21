package com.yata.backend.domain.notify.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Notify extends Auditable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "notification_id")
   private Long id;

   private String content;
   /*@Embedded
   private NotificationContent content;*/

   //@Embedded
   //private RelatedURL url;
   private String url;
   @Column(nullable = false)
   private Boolean isRead;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private NotificationType notificationType;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "member_id")
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Member receiver;

   @Builder
   public Notify(Member receiver, NotificationType notificationType, String content, String url, Boolean isRead) {
      this.receiver = receiver;
      this.notificationType = notificationType;
      this.content = content;
      this.url = url;
      this.isRead = isRead;
   }

   public Notify() {

   }


   public enum NotificationType{
        YATA, REVIEW, CHAT
   }
}
