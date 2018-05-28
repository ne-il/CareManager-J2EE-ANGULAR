package application.node.repository;

import application.node.domain.Node;

import java.util.List;

import javax.ejb.NoSuchEntityException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class NodeRepository {
	@PersistenceContext(unitName = "JPAPU")
	private EntityManager entityManager;

	public List<Node> list(){
		return entityManager.createNamedQuery("ALL_NODE",Node.class).getResultList();
	}

	public Node find(Long id){
		return entityManager.find(Node.class,id);
	}

	public Node findByName(String name){
		List<Node> list = list();
		for (Node n: list) {
			if(n.getName().equals(name))
				return n;
		}
		return null;
	}




	public Long save(Node node){
		entityManager.persist(node);
		return node.getId();
	}

	public void update(Node node){
		entityManager.merge(node);
	}

	public void delete(Long id){
		Node node = find(id);
		if(node == null){
			throw new NoSuchEntityException("No node with the id : "+id);
		}
		entityManager.remove(node);
	}

	public void flush(){
		entityManager.flush();
	}
}
