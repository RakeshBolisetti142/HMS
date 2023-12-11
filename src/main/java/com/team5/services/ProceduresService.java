package com.team5.services;

import java.util.List;

import com.team5.beans.Procedures;

public interface ProceduresService {

	String addNewProcedures(Procedures procedures);

	List<Procedures> getAllProceduresByProceduresService();

	Procedures getProcedurescostById(int id);

	

	Procedures getprocedurescostfindByName(String name);
	Procedures updateProcedurescostById(int id,int scost);

	Procedures updateProceduresnameById(int id,String name);

	
	

	

	

	

	

	
/*
	Procedures updateProceduresnameService(int id);

	*/

	

	

	

}
