package com.team5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.team5.beans.Department;
import com.team5.beans.Physician;
import com.team5.beans.TrainedIn;
import com.team5.repository.DepartmentDao;
import com.team5.repository.TrainedInDao;
import com.team5.services.DepartmentServiceImpl;

public class DepartmentServiceImplTest {
    @Mock
    private DepartmentDao departmentDao;

    @Mock
    private TrainedInDao trainedInDao;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNewDepartment() {
        Department department = new Department();
        when(departmentDao.save(any(Department.class))).thenReturn(department);

        String result = departmentService.addNewDepartment(department);

        assertEquals("Record created sucessfully", result);
    }

    @Test
    public void testGetAllDepartmentService() {
        List<Department> departmentList = new ArrayList<>();
        when(departmentDao.findAll()).thenReturn(departmentList);

        List<Department> result = departmentService.getAllDepartmentService();

        assertEquals(departmentList, result);
    }

 

    @Test
    public void testGetHeadCertificationService() {
        int deptId = 1;
        int physicianId = 100;
        Department department = new Department();
        Physician physician = new Physician();
        physician.setEmployeeId(physicianId);
        department.setHead(physician);
        when(departmentDao.findById(deptId)).thenReturn(Optional.of(department));

        List<TrainedIn> trainedInList = new ArrayList<>();
        when(trainedInDao.findByIdPhysicianEmployeeId(physicianId)).thenReturn(trainedInList);

        List<TrainedIn> result = departmentService.getHeadCertificationService(deptId);

        assertEquals(trainedInList, result);
    }

   
    @Test
    public void testFindByDepartmentId() {
        int departmentId = 1;
        Department department = new Department();
        when(departmentDao.findById(departmentId)).thenReturn(Optional.of(department));

        Department result = departmentService.findByDepartmentId(departmentId);

        assertEquals(department, result);
    }

    @Test
    public void testGetDepartmentsByPhysicianId() {
        int headId = 123;
        List<Department> departments = new ArrayList<>();
        Department d = new Department();
        d.setDepartmentId(headId);
        departments.add(d);
        when(departmentDao.getDepartmentsByPhysicianId(headId)).thenReturn(departments);

        List<Department> result = departmentService.getDepartmentsByPhysicianId(headId);

        assertEquals(departments, result);
    }

    @Test
    public void testCheckPhysicianIsHeadOfDepartment() {
        int headId = 456;
        List<Department> departments = new ArrayList<>();
        when(departmentDao.getDepartmentsByPhysicianId(headId)).thenReturn(departments);

        boolean result = departmentService.checkPhysicianIsHeadOfDepartment(headId);

        assertEquals(!departments.isEmpty(), result);
    }

    @Test
    public void testUpdateDepartmentHead() {
        int deptId = 789;
        Physician physician = new Physician();
        Department department = new Department();

        when(departmentDao.findById(deptId)).thenReturn(Optional.of(department));

        Department result = departmentService.updateDepartmentHead(physician, deptId);

        assertEquals(department, result);
        assertEquals(physician, department.getHead());
    }

    @Test
    public void testUpdateDepartmentName() {
        int deptId = 101;
        String deptName = "New Department Name";
        Department department = new Department();
        department.setDepartmentId(deptId);
        when(departmentDao.findById(deptId)).thenReturn(Optional.of(department));
        when(departmentDao.save(department)).thenReturn(department);

        Department result = departmentService.updateDepartmentName(deptName, deptId);

        assertEquals(department, result);
        assertEquals(deptName, department.getName());
    }


}

