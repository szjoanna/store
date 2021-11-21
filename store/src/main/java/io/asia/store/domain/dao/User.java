package io.asia.store.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class User extends Auditable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String secondName;
    @Column (unique = true)
    private String email;
    @NotAudited
    private String password;
    @Version
    private Long version;
    @ManyToMany (fetch = FetchType.EAGER)
    private List<Role> roles;
}
