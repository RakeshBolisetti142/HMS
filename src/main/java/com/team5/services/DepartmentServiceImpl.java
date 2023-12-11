package com.team5.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.beans.TrainedIn;
import com.team5.exceptions.InvalidDepartmentException;
import com.team5.repository.DepartmentDao;
import com.team5.repository.TrainedInDao;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	TrainedInDao trainedInDao;
 
	
	public String addNewDepartment(Department d) {
		departmentDao.save(d);
		return "Record created sucessfully";
	}
 
	public List<Department> getAllDepartmentService() {
		
		    try {
		    	return departmentDao.findAll();
		       
		    } catch (Exception e) {
		        throw new InvalidDepartmentException("Error while retrieving the list of departments.");
		    }
		
	}
 
	
	public Department findByDepartmentId(int Id) {
		Optional<Department> dept = departmentDao.findById(Id);
		return dept.isPresent() ? dept.get() : dept.orElseThrow(() -> new InvalidDepartmentException("Department with ID " + Id + " not found"));
	}
 
	public List<Department> getDepartmentsByPhysicianId(int headId) {
	    List<Department> departments = departmentDao.getDepartmentsByPhysicianId(headId);
 
	    return Optional.ofNullable(departments)
	                   .filter(list -> !list.isEmpty())
	                   .orElseThrow(() -> new InvalidDepartmentException("No departments found for Physician ID: " + headId));
 
	
	}
 
	public boolean checkPhysicianIsHeadOfDepartment(int headId) {
		List<Department> departments = departmentDao.getDepartmentsByPhysicianId(headId);
		return departments.size() == 0 ? false : true;
		
	}
 
	public Department updateDepartmentHead(Physician physician, int deptId) {
		
		Department dept = departmentDao.findById(deptId).orElse(null);

	    if (dept == null) {
	        throw new InvalidDepartmentException("Department with ID " + deptId + " not found.");
	    }

	    dept.setHead(physician);
	    departmentDao.save(dept);
	    return dept;
	}
 
	public Department updateDepartmentName(String deptName, int deptId) {
		
		Department department =findByDepartmentId(deptId);
		 if (department == null) {
		        throw new InvalidDepartmentException("Department with ID " + deptId + " not found.");
		 }
		department.setName(deptName);
		return departmentDao.save(department);
	}

	@Override
	public List<TrainedIn> getHeadCertificationService(int deptId) {
		int physicianId = findByDepartmentId(deptId).getHead().getEmployeeId();
		
		List<TrainedIn> trainedInList = trainedInDao.findByIdPhysicianEmployeeId(physicianId);
		
		return trainedInList;
	}

}
