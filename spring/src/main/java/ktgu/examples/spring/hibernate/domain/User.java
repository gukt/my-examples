package ktgu.examples.spring.hibernate.domain;

import javax.persistence.*;

/**
 * Created by ktgu on 15/8/20.
 */
@Entity
@Table(name = "ccn_users")
public class User {
    private Long id;
    private String name;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {

        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
