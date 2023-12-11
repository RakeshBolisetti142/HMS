package com.team5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.team5.beans.AffiliatedWith;
import com.team5.beans.AffiliatedWithId;

@Repository
public interface AffiliatedWithDao extends JpaRepository<AffiliatedWith, AffiliatedWithId>{

	List<AffiliatedWith> findByIdDepartmentDepartmentId(int deptId);

	List<AffiliatedWith> findByIdPhysicianEmployeeId(int physicianId);

	 @Query("SELECT a.primaryAffiliation FROM AffiliatedWith a WHERE a.id.physician.employeeId = :physicianId")
	   boolean isPrimaryAffiliation(int physicianId);

}
