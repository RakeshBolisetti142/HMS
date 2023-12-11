package com.team5.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team5.beans.AffiliatedWith;
import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.repository.AffiliatedWithDao;

@Service
public class AffiliatedWithServiceImpl implements AffiliatedWithService{
	
	@Autowired
	AffiliatedWithDao affiliatedWithDao;
	public Physician addAffliatedWithService(AffiliatedWith affiliatedWith) {
		 affiliatedWithDao.save(affiliatedWith);
		 return affiliatedWith.getId().getPhysician();
		

}
	@Override
	public List<Physician> getPhysiciansByDepartment(int deptId) {
		
		 List<AffiliatedWith> affiliatedWithList= affiliatedWithDao.findByIdDepartmentDepartmentId(deptId);
		 List<Physician> physicians= new ArrayList<>();
		 affiliatedWithList.forEach(aff->{
			 physicians.add(aff.getId().getDepartment().getHead());
		 });
	return physicians;
	}
	@Override
	public List<Department> getDepartmentsByPhysician(int physicianId) {
		List<AffiliatedWith> affiliatedWithList	=affiliatedWithDao.findByIdPhysicianEmployeeId(physicianId);
		List<Department> departments= new ArrayList<>();
		 affiliatedWithList.forEach(aff->{
			 departments.add(aff.getId().getDepartment());
		 });
		return departments;
	}
	@Override
	public boolean isPrimaryAffiliation(int physicianId) {
		
		return affiliatedWithDao.isPrimaryAffiliation(physicianId);
	}
	@Override
	public boolean updatePrimaryAffiliation(int physicianId,boolean affiliation) {
		List<AffiliatedWith> affiliatedWithList	=affiliatedWithDao.findByIdPhysicianEmployeeId(physicianId);
		 affiliatedWithList.forEach(aff->{
			 aff.setPrimaryAffiliation(affiliation);
			 affiliatedWithDao.save(aff);
		 });
		return affiliation;
	}
}
