package com.team5.services;

import java.util.List;

import com.team5.beans.AffiliatedWith;
import com.team5.beans.Department;
import com.team5.beans.Physician;

public interface AffiliatedWithService {

	Physician addAffliatedWithService(AffiliatedWith affiliatedWith);

	List<Physician> getPhysiciansByDepartment(int deptid);

	List<Department> getDepartmentsByPhysician(int physicianId);

	boolean isPrimaryAffiliation(int physicianId);

	boolean updatePrimaryAffiliation(int physicianId,boolean affiliation);

}
