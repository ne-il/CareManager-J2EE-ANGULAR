package application.document.repository;

import java.util.List;

import javax.ejb.NoSuchEntityException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import application.document.domain.Document;

@Stateless
public class DocumentRepository {

	@PersistenceContext(unitName = "JPAPU")
	private EntityManager entityManager;


	public List<Document> list() {
	    return entityManager.createNamedQuery("FIND_ALL_DOCUMENT",Document.class).getResultList();
    }

    public Document find(Long id) {
	    return entityManager.find(Document.class, id);
    }

    public Long save(Document document){
	    entityManager.persist(document);
	    return document.getId();
    }

    public void update(Document document){
	    entityManager.merge(document);
    }

    public void delete(Long id){
	    Document document = find(id);
	    if(document == null){
	        throw new NoSuchEntityException("No document with this id :"+id);
        }
        entityManager.remove(document);
    }

    public void flush(){
        entityManager.flush();
    }

}
