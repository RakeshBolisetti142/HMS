package com.team5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team5.beans.Nurse;
@Repository
public interface NurseDao extends JpaRepository<Nurse, Integer> {
	
    @Query(value="select* from nurse where EmployeeID=:empId", nativeQuery= true)
	List<Nurse> getRegisteredStatus(@Param("empId") int empId);

	

	
   
	 
		
		
	

	 
	
	
	
    
	

}
