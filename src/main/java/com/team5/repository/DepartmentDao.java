package com.team5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team5.beans.Department;
@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer>{
	@Query(value = "Select * from Department where head=:id", nativeQuery = true)
	List<Department> getDepartmentsByPhysicianId(@Param("id") int phyId);
 
	@Query(value = "update Department set head=:headId where departmentId=:deptId", nativeQuery = true)
	@Modifying
	int updateDepartmentHead(@Param("headId") int headId, @Param("deptId") int deptId);
}
