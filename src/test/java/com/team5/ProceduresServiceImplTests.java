
package com.team5;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.team5.beans.Procedures;
import com.team5.repository.ProceduresDao;
import com.team5.services.ProceduresServiceImpl;
 
@SpringBootTest

public class ProceduresServiceImplTests {
 
    @Mock

    private ProceduresDao proceduresDao;
 
    @InjectMocks

    private ProceduresServiceImpl proceduresService;
 
    @BeforeEach

    public void setup() {

        MockitoAnnotations.openMocks(this);

    }
 
    @Test

    public void testAddNewProcedures() {

        Procedures procedures = new Procedures();

        procedures.setCode(1);

        when(proceduresDao.save(any(Procedures.class))).thenReturn(procedures);
 
        String result = proceduresService.addNewProcedures(procedures);
 
//        assertEquals("Record Created Successfully", result);

    }
 
    
 
    @Test

    public void testGetAllProceduresByProceduresService() {

        // Arrange

     
        Procedures p = new Procedures();
        p.setCode(10);
        List<Procedures> proceduresList = new ArrayList<Procedures>();
        proceduresList.add(p);
        

        when(proceduresDao.findAll()).thenReturn(proceduresList);
 
        // Act

        List<Procedures> result = proceduresService.getAllProceduresByProceduresService();
 
        // Assert

        assertEquals(proceduresList, result);

    }

    // Other test methods...
 
 
    
 
    @Test

    public void testGetProcedurescostById() {

        int id = 1;

        Procedures procedures = new Procedures();

        when(proceduresDao.findById(id)).thenReturn(Optional.of(procedures));
 
        Procedures result = proceduresService.getProcedurescostById(id);
 
//        assertNotNull(result);

    }
 
    
 
    @Test

    public void testGetprocedurescostfindByName() {

        String name = "ProcedureName";

        Procedures procedures = new Procedures();

        when(proceduresDao.findByName(name)).thenReturn(procedures);
 
        Procedures result = proceduresService.getprocedurescostfindByName(name);
 
//        assertNotNull(result);

    }
 
    

    @Test

    public void testUpdateProcedurescostById() {

        int id = 1;

        int newCost = 100;

        Procedures procedures = new Procedures();

        when(proceduresDao.findById(id)).thenReturn(Optional.of(procedures));
 
        Procedures result = proceduresService.updateProcedurescostById(id, newCost);
 
//        assertNotNull(result);

//        assertEquals(newCost, result.getCost());

    }
 
    

    @Test

    public void testUpdateProceduresnameById() {

        int id = 1;

        String newName = "NewProcedureName";

        Procedures procedures = new Procedures();

        when(proceduresDao.findById(id)).thenReturn(Optional.of(procedures));
 
        Procedures result = proceduresService.updateProceduresnameById(id, newName);
 
//        assertNotNull(result);

//        assertEquals(newName, result.getName());

    }
 
    

}

