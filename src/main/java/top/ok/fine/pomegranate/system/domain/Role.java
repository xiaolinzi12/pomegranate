package top.ok.fine.pomegranate.system.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/23
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String nameZh;

}
