package com.team5.services;

import java.util.List;

import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.beans.TrainedIn;

public interface DepartmentService {
	String addNewDepartment(Department d);
	 
	List<Department> getAllDepartmentService();
 
	Department findByDepartmentId(int Id);
 
	List<Department> getDepartmentsByPhysicianId(int headId);
 
	boolean checkPhysicianIsHeadOfDepartment(int headId);
 
	Department updateDepartmentHead(Physician physician, int deptId);
 
	Department updateDepartmentName(String deptName, int deptId);

	List<TrainedIn> getHeadCertificationService(int deptId);
	

}
