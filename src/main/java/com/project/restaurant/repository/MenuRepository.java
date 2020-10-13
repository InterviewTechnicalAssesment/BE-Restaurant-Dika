package com.project.restaurant.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.restaurant.entity.Menu;


@Repository
@Transactional(readOnly = true)
public interface MenuRepository  extends JpaRepository<Menu, Long> {
	
	
	@Query(value="SELECT * FROM menu m WHERE m.id IN :ids", nativeQuery=true) 
	List<Menu> findMenuByIds(@Param("ids") Set<Long> ids);
	
}
