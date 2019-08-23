package top.ok.fine.pomegranate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.ok.fine.pomegranate.domain.User;

/**
 * @program
 * @description:
 * @author: zhengLin
 * @date 2019/08/23
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

}
