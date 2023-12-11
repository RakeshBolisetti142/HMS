package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.team5.beans.Physician;
import com.team5.beans.Procedures;
import com.team5.beans.TrainedIn;
import com.team5.beans.TrainedInId;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.TrainedInDao;
import com.team5.services.TrainedInServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TrainedInServiceImplTest {

	@InjectMocks
	TrainedInServiceImpl trainedInServiceImpl;

	@Mock
	TrainedInDao trainedInDao;

	@Test
	void addNewCertificationTest() {
		when(trainedInDao.save(any())).thenReturn(null);
		String response = trainedInServiceImpl.addNewCertification(new TrainedIn());
		assertEquals("Record Created Successfully", response);
	}

	@Test
	void getAllProceduresByTreatmentTest() {
		List<TrainedIn> trainedInList = new ArrayList<>();
		trainedInList.add(buildTrainedIn());
		when(trainedInDao.findAll()).thenReturn(trainedInList);
		List<Procedures> response = trainedInServiceImpl.getAllProceduresByTreatment();
		assertEquals(2, response.get(0).getCode());
		assertEquals("Nandini", response.get(0).getName());
		assertEquals(100, response.get(0).getCost());
	}

	@Test
	void getAllProceduresByTreatmentTestException() {
		when(trainedInDao.findAll()).thenReturn(new ArrayList<TrainedIn>());
		NotFoundException ex = assertThrows(NotFoundException.class, () -> {
		trainedInServiceImpl.getAllProceduresByTreatment();
		});
		assertEquals("No TrainedIn found", ex.getMessage());
	}

	@Test
	void getAllProceduresDoneByPhysicianTest() {
		List<TrainedIn> trainedInList = new ArrayList<>();
		trainedInList.add(buildTrainedIn());
		when(trainedInDao.findByIdPhysicianEmployeeId(anyInt())).thenReturn(trainedInList);
		List<Procedures> response = trainedInServiceImpl.getAllProceduresDoneByPhysician(1);
		assertEquals(2, response.get(0).getCode());
		assertEquals("Nandini", response.get(0).getName());
		assertEquals(100, response.get(0).getCost());
	}

	@Test
	void getAllProceduresDoneByPhysicianTestException() {
		when(trainedInDao.findByIdPhysicianEmployeeId(anyInt())).thenReturn(new ArrayList<TrainedIn>());
		NotFoundException ex = assertThrows(NotFoundException.class, () -> {
		trainedInServiceImpl.getAllProceduresDoneByPhysician(2);
		});
		assertEquals("No procedures found for physician with ID: 2", ex.getMessage());
	}

	@Test
	void getAllPhysiciansByTreatmentTest() {
		List<TrainedIn> trainedInList = new ArrayList<>();
		trainedInList.add(buildTrainedIn());
		when(trainedInDao.findByIdTreatmentCode(anyInt())).thenReturn(trainedInList);
		List<Physician> response = trainedInServiceImpl.getAllPhysiciansByTreatment(2);
		assertEquals(1, response.get(0).getEmployeeId());
		assertEquals("Venkatesh", response.get(0).getName());
		assertEquals("Staff Internist", response.get(0).getPosition());
		assertEquals(11111111, response.get(0).getSsn());
	}

	@Test
	void getAllPhysiciansByTreatmentTestException() {
		when(trainedInDao.findByIdTreatmentCode(anyInt())).thenReturn(new ArrayList<TrainedIn>());
		NotFoundException ex = assertThrows(NotFoundException.class, () -> {
		trainedInServiceImpl.getAllPhysiciansByTreatment(1);
		});
		assertEquals("No physicians found for treatment with ID: 1", ex.getMessage());
	}

	@Test
	void getProceduresExpiringSoonForPhysicianTest() {

		Procedures procedures = new Procedures();
		procedures.setCode(100);
		procedures.setName("Test");
		procedures.setCost(200);
		List<Procedures> proceduresList = new ArrayList<>();
		proceduresList.add(procedures);

		when(trainedInDao.findProceduresExpiringSoonForPhysician(anyLong(), any())).thenReturn(proceduresList);
		List<Procedures> response = trainedInServiceImpl.getProceduresExpiringSoonForPhysician(2L);
		assertEquals(100, response.get(0).getCode());
		assertEquals("Test", response.get(0).getName());
		assertEquals(200, response.get(0).getCost());
	}

	@Test
	void getProceduresExpiringSoonForPhysicianTestException() {
		when(trainedInDao.findProceduresExpiringSoonForPhysician(anyLong(), any())).thenReturn(new ArrayList<Procedures>());
		NotFoundException ex = assertThrows(NotFoundException.class, () -> {
		trainedInServiceImpl.getProceduresExpiringSoonForPhysician(1L);
		});
		assertEquals("No procedures found for physician with ID: 1", ex.getMessage());
	}

	@Test
	void updateCertificationExpiryTest() {
		when(trainedInDao.findByIdPhysicianEmployeeIdAndIdTreatmentCode(anyInt(), anyInt())).thenReturn(new TrainedIn());
		boolean response = trainedInServiceImpl.updateCertificationExpiry(1, 2, LocalDateTime.now());
		assertTrue(response);
	}

	@Test
	void updateCertificationExpiryTestFail() {
	    when(trainedInDao.findByIdPhysicianEmployeeIdAndIdTreatmentCode(1, 2)).thenReturn(null);
	    boolean response = trainedInServiceImpl.updateCertificationExpiry(1, 2, LocalDateTime.now());
	    assertEquals(false, response);
	}


	private TrainedIn buildTrainedIn() {

		Physician physician = new Physician();
		physician.setEmployeeId(1);
		physician.setName("Venkatesh");
		physician.setPosition("Staff Internist");
		physician.setSsn(11111111);

		Procedures procedures = new Procedures();
		procedures.setCode(2);
		procedures.setName("Nandini");
		procedures.setCost(100);

		TrainedIn trainedIn = new TrainedIn();
		TrainedInId trainedInId = new TrainedInId(physician, procedures);
		trainedIn.setCertificationDate(LocalDateTime.now().minusYears(1));
		trainedIn.setCertificationExpires(LocalDateTime.now());
		trainedIn.setId(trainedInId);
		return trainedIn;
	}
	
}
