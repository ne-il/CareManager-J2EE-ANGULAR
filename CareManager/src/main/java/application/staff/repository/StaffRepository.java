package application.staff.repository;

import application.staff.domain.Staff;

import java.util.List;

import javax.ejb.NoSuchEntityException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StaffRepository {

	@PersistenceContext(unitName = "JPAPU")
	private EntityManager entityManager;

	public List<Staff> list(){
		return entityManager.createNamedQuery("ALL_STAFF",Staff.class).getResultList();
	}

	public Staff find(Long id){
		return entityManager.find(Staff.class,id);
	}

	public Long save(Staff staff){
		entityManager.persist(staff);
		return staff.getId();
	}

	public void update(Staff staff){
		entityManager.merge(staff);
	}

	public void delete(Long id){
		Staff staff = find(id);
		if(staff == null){
			throw new NoSuchEntityException("No node with the id : "+id);
		}
		entityManager.remove(staff);
	}

	public void flush(){
		entityManager.flush();
	}

}
