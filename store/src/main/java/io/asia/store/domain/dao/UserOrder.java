package io.asia.store.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class UserOrder {
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
    private String orderNumber;
}
