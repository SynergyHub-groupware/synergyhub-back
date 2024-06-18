package synergyhubback.post.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "board")
@Getter
public class BoardEntity {
    @Id
    @Column(name = "board_code")
    private int BoardCode;
    @Column(name = "board_name")
    private String BoardName;
}
