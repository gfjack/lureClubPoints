package com.lureclub.points.repository;

import com.lureclub.points.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户数据访问接口
 *
 * @author system
 * @date 2025-06-19
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据手机号查找用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);

    /**
     * 根据用户名或手机号查找用户
     *
     * @param username 用户名
     * @param phone 手机号
     * @return 用户信息
     */
    @Query("SELECT u FROM User u WHERE (u.username = :username OR u.phone = :phone) AND u.isDeleted = 0")
    User findByUsernameOrPhone(@Param("username") String username, @Param("phone") String phone);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    boolean existsByPhone(String phone);

    /**
     * 查找所有未删除的用户
     *
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.isDeleted = 0 ORDER BY u.createTime DESC")
    List<User> findAllActiveUsers();

    /**
     * 根据ID查找未删除的用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isDeleted = 0")
    Optional<User> findActiveUserById(@Param("id") Long id);

    /**
     * 根据用户名模糊搜索
     *
     * @param username 用户名关键词
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% AND u.isDeleted = 0")
    List<User> findByUsernameContaining(@Param("username") String username);

    /**
     * 根据手机号模糊搜索
     *
     * @param phone 手机号关键词
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.phone LIKE %:phone% AND u.isDeleted = 0")
    List<User> findByPhoneContaining(@Param("phone") String phone);

    /**
     * 根据用户名和手机号模糊搜索
     *
     * @param username 用户名关键词
     * @param phone 手机号关键词
     * @return 用户列表
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% AND u.phone LIKE %:phone% AND u.isDeleted = 0")
    List<User> findByUsernameContainingAndPhoneContaining(@Param("username") String username, @Param("phone") String phone);

}