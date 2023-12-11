package com.team5.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team5.beans.Procedures;
import com.team5.beans.TrainedIn;
import com.team5.beans.TrainedInId;



@Repository
public interface TrainedInDao extends JpaRepository<TrainedIn, TrainedInId> {


	List<TrainedIn> findByIdPhysicianEmployeeId(int employeeId);


	List<TrainedIn> findByIdTreatmentCode(int treatmentId);

	@Query(value = "Select p.* from procedures p join trained_in t where t.physician=2 and p.code=t.treatment and t.CertificationExpires <= NOW() + INTERVAL 30 DAY", nativeQuery = true)
	List<Procedures> getAllProceduresWithCertificationExpireInAMonth(@Param("id") int phyId);

	@Query(value = "update trained_in set CertificationExpires=:expDate where physician=:phyId and treatment=:treatmentId", nativeQuery = true)
	@Modifying
	boolean updateCertificationExpiryDate(@Param("expDate") Date expDate, @Param("phyId") int phyId,
			@Param("treatmentId") int treatmentId);
	TrainedIn findByIdPhysicianEmployeeIdAndIdTreatmentCode(int employeeId,int code);
	@Query("SELECT t.id.treatment FROM TrainedIn t WHERE t.id.physician.id = :physicianId " +
            "AND t.certificationExpires <= :expirationDate")
    List<Procedures> findProceduresExpiringSoonForPhysician(
            @Param("physicianId") Long physicianId,
            @Param("expirationDate") LocalDateTime expirationDate);
}
