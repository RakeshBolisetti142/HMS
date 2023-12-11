

  package com.team5;
  
  import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
  
  import java.util.List;
  
  import org.assertj.core.api.Assertions;
  
  import org.junit.jupiter.api.BeforeEach;
  
  import org.junit.jupiter.api.Test;
  
  import org.junit.jupiter.api.extension.ExtendWith;
  
  import org.mockito.InjectMocks;
  
  import org.mockito.Mock;
  
  import org.mockito.Mockito;
  
  import org.mockito.MockitoAnnotations;
  
  import org.mockito.junit.jupiter.MockitoExtension;
  
  import com.team5.beans.AffiliatedWith;
import com.team5.beans.AffiliatedWithId;
import com.team5.beans.Department;
  
  import com.team5.beans.Physician;
  
  import com.team5.repository.AffiliatedWithDao;
import com.team5.services.AffiliatedWithServiceImpl;
  
  @ExtendWith(MockitoExtension.class)
  
  public class AffiliatedWithServiceImplTest {
  
  @InjectMocks
  
  private AffiliatedWithServiceImpl affiliatedWithService;
  
  @Mock
  
  private AffiliatedWithDao affiliatedWithDao;
  
  @BeforeEach
  
  void setUp() {
  
  MockitoAnnotations.openMocks(this);
  
  }
  
@Test
void testAddAffliatedWithService() {
    AffiliatedWith affiliatedWith = new AffiliatedWith(); // Initialize with required fields
    AffiliatedWithId id = new AffiliatedWithId();
    Department d = new Department();
    d.setDepartmentId(2);
    id.setDepartment(d);
    Physician p = new Physician();
    p.setEmployeeId(3);
    id.setPhysician(p);
    affiliatedWith.setId(id);
    Mockito.when(affiliatedWithDao.save(Mockito.any())).thenReturn(affiliatedWith);

    Physician physician = affiliatedWithService.addAffliatedWithService(affiliatedWith);
    assertEquals(physician, affiliatedWith.getId().getPhysician());


}
  
  @Test
  
  void testGetPhysiciansByDepartment() {
  
  int deptId = 123; 
  
  List<AffiliatedWith> affiliatedWithList = new ArrayList<>(); 
 
  
  Mockito.when(affiliatedWithDao.findByIdDepartmentDepartmentId(deptId)).
  thenReturn(affiliatedWithList);
  
 
  
  List<Physician> physicians =
  affiliatedWithService.getPhysiciansByDepartment(deptId);
  

  
  }
  
  @Test
  
  void testGetDepartmentsByPhysician() {
  
  int physicianId = 456; 
  
  List<AffiliatedWith> affiliatedWithList = new ArrayList<>(); 

  
  Mockito.when(affiliatedWithDao.findByIdPhysicianEmployeeId(physicianId)).
  thenReturn(affiliatedWithList);
  

  
  List<Department> departments =
  affiliatedWithService.getDepartmentsByPhysician(physicianId);
  

  
  }
  
  @Test
  
  void testIsPrimaryAffiliation() {
  
  int physicianId = 789; 
  
  Mockito.when(affiliatedWithDao.isPrimaryAffiliation(physicianId)).thenReturn(
  true);
 
  
  boolean isPrimary = affiliatedWithService.isPrimaryAffiliation(physicianId);
  

  
  Assertions.assertThat(isPrimary);
  
  
  
  }
  
  @Test
  
  void testUpdatePrimaryAffiliation() {
  
  int physicianId = 999; 
  
  boolean newAffiliation = true; 
  
  List<AffiliatedWith> affiliatedWithList = new ArrayList<>(); 
 
  
  Mockito.when(affiliatedWithDao.findByIdPhysicianEmployeeId(physicianId)).
  thenReturn(affiliatedWithList);
  
  
  
  boolean updated = affiliatedWithService.updatePrimaryAffiliation(physicianId,
  newAffiliation);
  
  
  
  Assertions.assertThat(updated);

  
  }
  
  }
  
 