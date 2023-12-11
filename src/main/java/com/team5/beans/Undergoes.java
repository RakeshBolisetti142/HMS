package com.team5.beans;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="undergoes")
public class Undergoes {

   
	@EmbeddedId
    private UndergoesId id;

    @ManyToOne
    @JoinColumn(name = "AssistingNurse",referencedColumnName = "employeeId")
    private Nurse assistingNurse;

    @ManyToOne
    @JoinColumn(name = "Physician",referencedColumnName = "employeeId")
    private Physician physician;
    public Undergoes() {
    }
}

