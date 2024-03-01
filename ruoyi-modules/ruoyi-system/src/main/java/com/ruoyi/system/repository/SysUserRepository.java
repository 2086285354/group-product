package com.ruoyi.system.repository;

import com.ruoyi.system.api.domain.SysUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends CrudRepository<SysUser,Integer> {
}
