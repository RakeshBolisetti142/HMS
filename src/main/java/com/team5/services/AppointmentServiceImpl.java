package com.team5.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team5.beans.Appointment;
import com.team5.beans.Nurse;
import com.team5.beans.Patient;
import com.team5.beans.Physician;
import com.team5.exceptions.NotFoundException;
import com.team5.repository.AppointmentDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    AppointmentDao appointmentDao;

    @Override
    public String addNewAppointmentService(Appointment appointment) {
        if (appointment.getAppointmentID() == 0)
            throw new NotFoundException("Appointment Id should not be empty");

        appointmentDao.save(appointment);

        return "Record Created Successfully";
    }

    @Override
    public List<Appointment> getAllAppointmentService() {

        List<Appointment> appointments = appointmentDao.findAll();
        if (appointments.isEmpty())
            throw new NotFoundException("No Appointments Found");
        return appointments;
    }

    public List<Appointment> getAllAppointmentByDateService(LocalDateTime startDate) {

        List<Appointment> appointments = appointmentDao.findAllByAppointmentDateTime(startDate);
        if (appointments.isEmpty())
            throw new NotFoundException("No Appointments Found");
        return appointments;
    }

    @Override
    public Patient getPatientInformation(int appointmentId) {

        return appointmentDao.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Invalid Appointment with id : " + appointmentId))
                .getPatient();
    }

    @Override
    public Physician getPhysician(int appointmentId) {

        return appointmentDao.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Invalid Appointment with id : " + appointmentId))
                .getPhysician();
    }

    @Override
    public Nurse getNurse(int appointmentId) {
        return appointmentDao.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Invalid Appointment with id : " + appointmentId))
                .getPrepNurse();
    }

    @Override
    public String getExaminationRoomService(int appointmentId) {

        return appointmentDao.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Invalid Appointment with id : " + appointmentId))
                .getExaminationRoon();
    }

    @Override
    public List<Physician> getAllPhysicianByPatientIdService(int patientId) {
//        Patient p = em.createQuery("select p from Patient p where p.ssn=:ssn", Patient.class)
//                .setParameter("ssn", patientId).getSingleResult();
//        if (p == null)
//            throw new NotFoundException("patient with Id : " + patientId + " is not Found");
//        List<Appointment> appointmentList = appointmentDao.findByPatient(p);
//        if (appointmentList.isEmpty())
//            throw new NotFoundException("No Appointments for patient with id : " + patientId);
    	List<Appointment> appointmentList = appointmentDao.findByPatientSsn(patientId);
        List<Physician> physicians = new ArrayList<Physician>();
        appointmentList.forEach((appointment) -> {
            physicians.add(appointment.getPhysician());
        });
        return physicians;
    }

    public Physician getPhysicianOnParticularDate(Date appointmentDate, int patientId) {
//        Patient p = em.createQuery("select p from Patient p where p.ssn=:ssn", Patient.class)
//                .setParameter("ssn", patientId).getSingleResult();
//        if (p == null)
//            throw new NotFoundException("patient with Id : " + patientId + " is not Found");
//        Physician ph = appointmentDao.findByAppointmentDateTimeAndPatient(appointmentDate, p).getPhysician();
//        if (ph == null)
//            throw new NotFoundException(
//                    "No Physician found for AppointmentDate " + appointmentDate + " and patient " + p);
//        return ph;
    	Appointment ap = appointmentDao.findByAppointmentDateTimeAndPatientSsn(appointmentDate,patientId);
    	if(ap==null) throw new NotFoundException(
              "No Physician found for AppointmentDate " + appointmentDate + " and patient id" + patientId);
    	return ap.getPhysician();
    }

    public List<Nurse> getAllNurseByPatientIdService(int patientId) {
//        Patient p = em.createQuery("select p from Patient p where p.ssn=:ssn", Patient.class)
//                .setParameter("ssn", patientId).getSingleResult();
//        if (p == null)
//            throw new NotFoundException("patient with Id : " + patientId + " is not Found");

        List<Appointment> appointmentList = appointmentDao.findByPatientSsn(patientId);
        if (appointmentList.isEmpty())
            throw new NotFoundException("No Appointments Found for Patient with id: " + patientId);
        List<Nurse> Nurses = new ArrayList<Nurse>();
        appointmentList.forEach((appointment) -> {
            Nurses.add(appointment.getPrepNurse());
        });
        return Nurses;
    }

    public Nurse getNurseOnParticularDate(Date appointmentDate, int patientId) {
//        Patient p = em.createQuery("select p from Patient p where p.ssn=:ssn", Patient.class)
//                .setParameter("ssn", patientId).getSingleResult();
//        if (p == null)
//            throw new NotFoundException("patient with Id : " + patientId + " is not Found");
//        Nurse nurse = appointmentDao.findByAppointmentDateTimeAndPatient(appointmentDate, p).getPrepNurse();
//        if (nurse == null)
//            throw new NotFoundException(
//                    "No Nurse Found for Appointment Date time " + appointmentDate + " and patient with id "
//                            + patientId);
        Nurse nurse =appointmentDao.findByPatientSsnAndAppointmentDateTime(patientId, appointmentDate).getPrepNurse();
        if(nurse==null) throw new NotFoundException("No Nurse Found on appointmentDate "+appointmentDate + "with patient Id : "+patientId);
        return nurse;
    }

    @Override
    public List<Date> getAllDates(int patientId) {
//        Patient p = em.createQuery("select p from Patient p where p.ssn=:ssn", Patient.class)
//                .setParameter("ssn", patientId).getSingleResult();
//        if (p == null)
//            throw new NotFoundException("patient with Id : " + patientId + " is not Found");

        List<Appointment> appointmentList = appointmentDao.findByPatientSsn(patientId);
        if (appointmentList.isEmpty())
            throw new NotFoundException("No Appointments Found for Patient with id: " + patientId);
        List<Date> dates = new ArrayList<Date>();
        appointmentList.forEach(appointment -> {
            dates.add(appointment.getAppointmentDateTime());
        });

        return dates;
    }

    @Override
    @JsonManagedReference
    public List<Patient> getAllPatientByPhysicianOnDateService(int physicianId, LocalDateTime date) {
        List<Appointment> appList = appointmentDao.findByAppointmentDateTime(date);
        System.out.println(appList);

        // Use optional to handle the case where no matching appointment is found
        Optional<Appointment> optionalAppointment = appList.stream()
                .filter(a -> a.getPhysician().getEmployeeId() == physicianId)
                .findFirst();

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            List<Patient> patients = appointment.getPhysician().getPatients();
            return patients;
        } else {
            throw new NotFoundException                    ("No appointment found for the given physician and date");
        }
    }

    @Override
    public List<Patient> getAllPatientsByPhysician(int physicianId) {
        List<Appointment> appList = appointmentDao.findByPhysicianEmployeeId(physicianId);
        List<Patient>  patients = new ArrayList<>();
        appList.forEach(a->patients.add(a.getPatient()));
        return patients;
    }

    @Override
    public Patient getPatientByPhysicianByPatientIdService(int physicianId, int patientId) {
        List<Appointment> apps = appointmentDao.findByPhysicianEmployeeId(physicianId);
        for (Appointment a : apps) {
            if (a.getPatient().getSsn() == patientId) {
                return a.getPatient();
            }
        }
        throw new NotFoundException("No patient found for the given physician and patientId");
    }

    @Override
    public List<Patient> getAllPatientByNurse(int nurseId) {
        List<Appointment> apps = appointmentDao.findByPrepNurseEmployeeId(nurseId);
        List<Patient> patients = new ArrayList<Patient>();
        apps.forEach(a -> patients.add(a.getPatient()));
        return patients;
    }

    @Override
    public Patient getPatientByPatientIdByNurse(int nurseId, int patientId) {
        List<Patient> patients = getAllPatientByNurse(nurseId);
        Optional<Patient> optionalPatient = patients.stream().filter(p -> p.getSsn() == patientId).findFirst();

        if (optionalPatient.isPresent()) {
            return optionalPatient.get();
        } else {
            throw new NotFoundException("No patient found for the given nurse and patientId");
        }
    }

    @Override
    public List<Patient> getAllPatientByNurseOnDate(int nurseId, Date dateConv) {
        List<Appointment> apps = appointmentDao.findByPrepNurseEmployeeIdAndAppointmentDateTime(nurseId,dateConv);
        List<Patient> patients = new ArrayList<Patient>();
        apps.forEach(a -> {
          
                patients.add(a.getPatient());
      
        });
        return patients;
    }

    @Override
    public String getRoomByPatientIdOnDate(int patientId, Date date) {
        Appointment appointment = appointmentDao.findByPatientSsnAndAppointmentDateTime(patientId, date);
        if (appointment != null) {
            return appointment.getExaminationRoon();
        } else {
            throw new NotFoundException("No appointment found for the given patient and date");
        }
    }

    @Override
    public List<String> getAllRoomByPhysicianIdOnDate(int physicianId, Date dateConv) {
        List<Appointment> appmnts = appointmentDao.findByPhysicianEmployeeIdAndAppointmentDateTime(physicianId,
                dateConv);
        List<String> rooms = new ArrayList<String>();
        appmnts.forEach(a -> rooms.add(a.getExaminationRoon()));
        return rooms;
    }

    @Override
    public List<String> getAllRoomByNurseIdOnDate(int nurseId, Date dateConv) {
        List<Appointment> appmnts = appointmentDao.findByPrepNurseEmployeeIdAndAppointmentDateTime(nurseId, dateConv);
        List<String> rooms = new ArrayList<String>();
        appmnts.forEach(a -> rooms.add(a.getExaminationRoon()));
        return rooms;
    }

    @Override
    public String updateRoomById(int appointmentId, String room) {
        Optional<Appointment> optionalAppointment = appointmentDao.findById(appointmentId);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setExaminationRoon(room);
            appointmentDao.save(appointment);
            return room;
        } else {
            throw new NotFoundException("No appointment found for the given appointmentId");
        }
    }
}



	/*
	 * @Override
	 * 
	 * @JsonManagedReference public List<Patient>
	 * getAllPatientByPhysicianOnDateService(int physicianId, LocalDateTime date) {
	 * List<Appointment> appList = appointmentDao.findByAppointmentDateTime(date);
	 * 
	 * // Use optional to handle the case where no matching appointment is found
	 * Optional<Appointment> optionalAppointment = appList.stream() .filter(a ->
	 * a.getPhysician().getEmployeeId() == physicianId).findFirst();
	 * 
	 * if (optionalAppointment.isPresent()) { Appointment appointment =
	 * optionalAppointment.get(); List<Patient> patients =
	 * appointment.getPhysician().getPatients(); System.out.println(patients);
	 * return patients; } else { // Handle the case where no appointment is found
	 * for the given physician and // date return Collections.emptyList(); } }
	 * 
	 * @Override public List<Patient> getAllPatientsByPhysician(int physicianId) {
	 * // TODO Auto-generated method stub // return
	 * appointmentDao.findByPatientPhysician(physicianId); return null; }
	 * 
	 * @Override public Patient getPatientByPhysicianByPatientIdService(int
	 * physicianId, int patientId) { List<Appointment> apps =
	 * appointmentDao.findByPhysicianEmployeeId(physicianId); for (Appointment a :
	 * apps) { if (a.getPatient().getSsn() == patientId) return a.getPatient(); }
	 * return null; }
	 * 
	 * @Override public List<Patient> getAllPatientByNurse(int nurseId) {
	 * List<Appointment> apps = appointmentDao.findByPrepNurseEmployeeId(nurseId);
	 * List<Patient> patients = new ArrayList<Patient>(); apps.forEach(a ->
	 * patients.add(a.getPatient())); return patients; }
	 * 
	 * @Override public Patient getPatientByPatientIdByNurse(int nurseId, int
	 * patientId) { List<Patient> patients = getAllPatientByNurse(nurseId); return
	 * patients.stream().filter(p -> p.getSsn() == patientId).findFirst().get();
	 * 
	 * }
	 * 
	 * @Override public List<Patient> getAllPatientByNurseOnDate(int nurseId, Date
	 * dateConv) { List<Appointment> apps =
	 * appointmentDao.findByPrepNurseEmployeeId(nurseId); List<Patient> patients =
	 * new ArrayList<Patient>(); apps.forEach(a -> { if
	 * (a.getAppointmentDateTime().compareTo(dateConv) == 0) {
	 * patients.add(a.getPatient()); }
	 * 
	 * }); return patients; }
	 * 
	 * @Override public String getRoomByPatientIdOnDate(int patientId, Date date) {
	 * 
	 * return appointmentDao.findByPatientSsnAndAppointmentDateTime(patientId,
	 * date).getExaminationRoon();
	 * 
	 * }
	 * 
	 * @Override public List<String> getAllRoomByPhysicianIdOnDate(int physicianId,
	 * Date dateConv) { List<Appointment> appmnts =
	 * appointmentDao.findByPhysicianEmployeeIdAndAppointmentDateTime(physicianId,
	 * dateConv); List<String> rooms = new ArrayList<String>(); appmnts.forEach(a ->
	 * rooms.add(a.getExaminationRoon())); return rooms; }
	 * 
	 * @Override public List<String> getAllRoomByNurseIdOnDate(int nurseId, Date
	 * dateConv) { List<Appointment> appmnts =
	 * appointmentDao.findByPrepNurseEmployeeIdAndAppointmentDateTime(nurseId,
	 * dateConv); List<String> rooms = new ArrayList<String>(); appmnts.forEach(a ->
	 * rooms.add(a.getExaminationRoon())); return rooms; }
	 * 
	 * @Override public String updateRoomById(int appointmentId, String room) {
	 * Appointment appmt = appointmentDao.findById(appointmentId).get();
	 * appmt.setExaminationRoon(room); appointmentDao.save(appmt); return room; }
	 */


