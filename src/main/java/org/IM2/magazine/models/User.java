package org.IM2.magazine.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.IM2.magazine.Enum.RoleEnum;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String username;
    public String password;
    public String email;
    public boolean archive;
    @Enumerated(EnumType.STRING)
    public RoleEnum role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    public Bucket bucket;

}
