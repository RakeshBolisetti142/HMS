package com.team5.beans;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "affiliated_with")
public class AffiliatedWith {

    @EmbeddedId
    private AffiliatedWithId id;

   

    @Column(name = "PrimaryAffiliation")
    private boolean primaryAffiliation;

    

    public AffiliatedWithId getId() {
		return id;
	}



	public void setId(AffiliatedWithId id) {
		this.id = id;
	}


	public boolean isPrimaryAffiliation() {
		return primaryAffiliation;
	}



	public void setPrimaryAffiliation(boolean primaryAffiliation) {
		this.primaryAffiliation = primaryAffiliation;
	}



	public AffiliatedWith() {
        
    }
	
}
