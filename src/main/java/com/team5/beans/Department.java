package com.team5.beans;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

 

	@Id
    @Column(name = "DepartmentID")
    private int departmentId;

    @ManyToOne
    @JoinColumn(name = "Head",referencedColumnName = "employeeId")
    private Physician head;
    
    @Column(name = "Name")
    private String name;

  

    public Department() {
   
    }

    public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public Physician getHead() {
		return head;
	}

	public void setHead(Physician head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department(int departmentId, Physician head, String name) {
        this.departmentId = departmentId;
        this.head = head;
        this.name = name;
    }
    
	   @Override
		public int hashCode() {
			return Objects.hash(departmentId, head, name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Department other = (Department) obj;
			return departmentId == other.departmentId && Objects.equals(head, other.head)
					&& Objects.equals(name, other.name);
		}

    
}

