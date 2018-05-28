package application.patient.repository;

import application.node.domain.Node;
import application.patient.domain.Patient;
import java.util.ArrayList;

import java.util.List;

import javax.ejb.NoSuchEntityException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PatientRepository{

	@PersistenceContext(unitName = "JPAPU")
	private EntityManager entityManager;

	public List<Patient> list() {
		return entityManager.createNamedQuery("ALL_PATIENT", Patient.class).getResultList();
	}

	public Patient find(Long id) {
		return entityManager.find(Patient.class, id);
	}

	public Long save(Patient patient) {
		entityManager.persist(patient);
		return patient.getId();
	}

	public List<Patient> getPatientByNode(Node targetNode) {
		List<Patient> patients = this.list();
		List<Patient> targetPatients = new ArrayList<>();
		for(Patient p : patients) {
			if(targetNode.equals(p.getAffectedNode())) {
				targetPatients.add(p);
			}
		}
		return targetPatients;
	}

	public void update(Patient patient) {
		entityManager.merge(patient);
	}

	public void delete(Long id) {
		Patient patient = find(id);
		if (patient == null) {
			throw new NoSuchEntityException("No patient with the id : " + id);
		}
		entityManager.remove(patient);
	}
	public void flush(){
		entityManager.flush();
	}


}
