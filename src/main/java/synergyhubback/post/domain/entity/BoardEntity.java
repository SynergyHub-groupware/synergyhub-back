package synergyhubback.post.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "board")
public class BoardEntity {
    @Id
    @Column(name = "board_code")
    private int BoardCode;
    @Column(name = "board_name")
    private String BoardName;
}
