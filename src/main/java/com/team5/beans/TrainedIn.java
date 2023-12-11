package com.team5.beans;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trained_in")
public class TrainedIn {

    @EmbeddedId
    private TrainedInId id;

   

    @Column(name = "CertificationDate")
    private LocalDateTime DateificationDate;

    @Column(name = "CertificationExpires")
    private LocalDateTime certificationExpires;
    
    

 
    public TrainedIn() {
       
    }


	public TrainedInId getId() {
		return id;
	}


	public void setId(TrainedInId id) {
		this.id = id;
	}


	public LocalDateTime getCertificationDate() {
		return DateificationDate;
	}


	public void setCertificationDate(LocalDateTime dateificationDate) {
		DateificationDate = dateificationDate;
	}


	public LocalDateTime getCertificationExpires() {
		return certificationExpires;
	}


	public void setCertificationExpires(LocalDateTime certificationExpires) {
		this.certificationExpires = certificationExpires;
	}
    

   
   
}

