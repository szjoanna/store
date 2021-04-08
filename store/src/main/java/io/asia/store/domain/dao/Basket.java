package io.asia.store.domain.dao;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Basket {
    @Id
    @GeneratedValue
    private Long id;
    private Double quantity;
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;
    @CreatedDate
    private LocalDateTime createdDate;
}
