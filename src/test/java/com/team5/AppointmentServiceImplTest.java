package com.team5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.team5.beans.Appointment;
import com.team5.beans.Nurse;
import com.team5.beans.Patient;
import com.team5.beans.Physician;
import com.team5.repository.AppointmentDao;
import com.team5.services.AppointmentServiceImpl;

@SpringBootTest
public class AppointmentServiceImplTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentDao appointmentDao;
    Appointment appointment;
    Patient patient ;
    List<Patient> patients;
    Physician physician ;
    Nurse nurse;
   
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    
    
    @BeforeEach
	public void setUp(){
    	appointment =  new Appointment();
    	patient =  new Patient();
    	physician = new Physician();
    	nurse  = new Nurse();
    
    patient.setSsn(100000001);
    patient.setInsuranceId(68476213);
    patient.setAddress("42 Foobar Lane");
    patient.setName("John Smith");
    patient.setPhone("555-0256");



    
    physician.setEmployeeId(1);
    physician.setName("John Dorian");
    physician.setPosition("Staff Internist");
    physician.setSsn(111111111);
    physician.setPatients(Arrays.asList(patient));

    nurse.setEmployeeId(101);
    nurse.setRegistered(true);
    nurse.setSsn(100000001);
    nurse.setName("John Smith");
    nurse.setPosition("Head Nurse");
    
    appointment.setAppointmentID(1);
    appointment.setPatient(patient);
    appointment.setPhysician(physician);
    appointment.setPrepNurse(nurse);
    appointment.setExaminationRoon("Room123");
    
    
    try {
    Date date = dateFormat.parse("04/07/2023 04:30:00");
    appointment.setAppointmentDateTime(date);
    }
    catch(Exception e) {
    	System.out.println(e.getMessage());
    }
    }

    @Test
    public void testAddNewAppointmentService() {
       

        when(appointmentDao.save(appointment)).thenReturn(appointment);

        String result = appointmentService.addNewAppointmentService(appointment);
        
        assertEquals("Record Created Successfully", result);
    }

    @Test
    public void testGetAllAppointmentService() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentDao.findAll()).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointmentService();

        assertEquals(appointments, result);
        
    }
    @Test
    public void testGetPatientInformation() {
        int appointmentId = 1;
       

        when(appointmentDao.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Patient result = appointmentService.getPatientInformation(appointmentId);
       

        assertEquals(appointment.getPatient(), result);
       
    }

    @Test
    public void testGetPhysician() {
        int appointmentId = 1;
        when(appointmentDao.findById(appointmentId)).thenReturn(Optional.of(appointment));
        

        Physician result = appointmentService.getPhysician(appointmentId);

        assertEquals(appointment.getPhysician(), result);
 
    }

    @Test
    public void testGetNurse() {
        int appointmentId = 1;
   

        when(appointmentDao.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Nurse result = appointmentService.getNurse(appointmentId);

        assertEquals(appointment.getPrepNurse(), result);

    }

    @Test
    public void testGetExaminationRoomService() {
        int appointmentId = 1;
        
        when(appointmentDao.findById(appointmentId)).thenReturn(Optional.of(appointment));


        String result = appointmentService.getExaminationRoomService(appointmentId);

        assertEquals(appointment.getExaminationRoon(), result);
  
    }
    @Test
    public void testGetAllAppointmentByDateService() {
        LocalDateTime startDate = LocalDateTime.now();
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentDao.findAllByAppointmentDateTime(startDate)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointmentByDateService(startDate);

        assertEquals(appointments, result);
       
    }

    @Test
    public void testGetPhysicianOnParticularDate() throws ParseException {
    	
    	Date date = dateFormat.parse("04/07/2023 04:30:00");
        
    	int patientId = patient.getSsn();
        when(appointmentDao.findByAppointmentDateTimeAndPatientSsn(date, patientId)).thenReturn(appointment);
     
Physician result = appointmentService.getPhysicianOnParticularDate(date,patientId);
       assertEquals(appointment.getPhysician(), result);

      
    }
    @Test
    public void testGetAllPhysicianByPatientIdService() {
        int patientId = 100000001;
        List<Physician> physicians = Arrays.asList(appointment.getPhysician());

        when(appointmentDao.findByPatientSsn(patientId)).thenReturn(Arrays.asList(appointment));

        List<Physician> result = appointmentService.getAllPhysicianByPatientIdService(patientId);

        assertEquals(physicians, result);
    }
  

    @Test
    public void testGetAllNurseByPatientIdService() {
        int patientId = 100000001;
      

        when(appointmentDao.findByPatientSsn(patientId)).thenReturn(Arrays.asList(appointment));

        List<Nurse> result = appointmentService.getAllNurseByPatientIdService(patientId);

        assertEquals(Arrays.asList(appointment.getPrepNurse()), result);
    }

    @Test
    public void testGetNurseOnParticularDate() throws ParseException {
        Date date = dateFormat.parse("04/07/2023 04:30:00");
        int patientId = patient.getSsn();

        when(appointmentDao.findByPatientSsnAndAppointmentDateTime(patientId,date)).thenReturn(appointment);

        Nurse result = appointmentService.getNurseOnParticularDate(date, patientId);

        assertEquals(appointment.getPrepNurse(), result);
    }

    @Test
    public void testGetAllDates() {
        int patientId = 100000001;
    

        when(appointmentDao.findByPatientSsn(patientId)).thenReturn(Arrays.asList(appointment));

        List<Date> result = appointmentService.getAllDates(patientId);

        assertEquals(Arrays.asList(appointment.getAppointmentDateTime()), result);
    }

  

    @Test
    public void testGetAllPatientsByPhysician() {
        int physicianId = 1;
        System.out.println(appointment);

        when(appointmentDao.findByPhysicianEmployeeId(physicianId)).thenReturn(Arrays.asList(appointment));

        List<Patient> result = appointmentService.getAllPatientsByPhysician(physicianId);
        

        assertEquals(appointment.getPhysician().getPatients(), result);
    }

    @Test
    public void testGetAllPatientsByPhysicianOnDateService() throws ParseException {
        int physicianId = 1;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse("04/07/2023 04:30:00",formatter);
        
       
        when(appointmentDao.findByAppointmentDateTime(date))
            .thenReturn(Arrays.asList(appointment));

        List<Patient> result = appointmentService.getAllPatientByPhysicianOnDateService(physicianId, date);
      

        assertEquals(Arrays.asList(appointment.getPatient()), result);
    }
   

    @Test
    public void testGetPatientByPhysicianByPatientIdService() {
        int physicianId = 1;
        int patientId = 100000001;

        when(appointmentDao.findByPhysicianEmployeeId(physicianId))
            .thenReturn(Arrays.asList(appointment));

        Patient result = appointmentService.getPatientByPhysicianByPatientIdService(physicianId, patientId);

        assertEquals(appointment.getPatient(), result);
    }

    @Test
    public void testGetAllPatientByNurse() {
        int nurseId = 101;
      

        when(appointmentDao.findByPrepNurseEmployeeId(nurseId)).thenReturn(Arrays.asList(appointment));

        List<Patient> result = appointmentService.getAllPatientByNurse(nurseId);

        assertEquals(Arrays.asList(appointment.getPatient()), result);
    }

    @Test
    public void testGetPatientByPatientIdByNurse() {
        int nurseId = 101;
        int patientId = 100000001;

        when(appointmentDao.findByPrepNurseEmployeeId(nurseId))
            .thenReturn(Arrays.asList(appointment));

        Patient result = appointmentService.getPatientByPatientIdByNurse(nurseId, patientId);

        assertEquals(patient, result);
    }

    @Test
    public void testGetAllPatientByNurseOnDate() throws ParseException {
        int nurseId = 101;
        Date date = dateFormat.parse("04/07/2023 04:30:00");
      

        when(appointmentDao.findByPrepNurseEmployeeIdAndAppointmentDateTime(nurseId, date))
            .thenReturn(Arrays.asList(appointment));

        List<Patient> result = appointmentService.getAllPatientByNurseOnDate(nurseId, date);

        assertEquals(Arrays.asList(appointment.getPatient()), result);
    }

    @Test
    public void testGetRoomByPatientIdOnDate() throws ParseException {
        int patientId = 100000001;
        Date date = dateFormat.parse("04/07/2023 04:30:00");
      

        when(appointmentDao.findByPatientSsnAndAppointmentDateTime(patientId, date))
            .thenReturn(appointment);

        String result = appointmentService.getRoomByPatientIdOnDate(patientId, date);

        assertEquals(appointment.getExaminationRoon(), result);
    }

}



   


