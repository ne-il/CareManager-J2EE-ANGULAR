/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.node.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@NamedQuery(name = "ALL_NODE", query = "SELECT n FROM Node n")
@XmlRootElement(name = "node")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String type;

    private Long fatherId;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Node> childrens;

    public Node(){
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        if(obj == this){
            return true;
        }

        Node n = (Node) obj;
        return this.name.equals(n.name)
                && this.id.equals(n.id)
                && this.name.equals(n.name)
                && this.type.equals(n.type)
                && (this.fatherId == n.fatherId);
    }

    public Node(String name, String type){
        this.name = name;
        this.type = type;
        this.childrens = new ArrayList<Node>();
    }

    public Node(String name, String type, Long fatherId){
        this.name = name;
        this.type = type;
        this.fatherId = fatherId;
        this.childrens = new ArrayList<Node>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<Node> getChildrens() {
        return childrens;

    }

    public void setChildrens(List<Node> childrens) {
        this.childrens = childrens;
    }

    public void addChild(Node child) {
        child.setFatherId(this.getId());
        this.childrens.add(child);
    }



//    public String parcoursSousArbreAux(Node node) {
//	    StringBuilder sb = new StringBuilder();
//        if(node == null){
//            return " ";
//        }
//
//        sb.append( node.getName() ).append("\n") ;
//        for (Node n: node.childrens){
//            sb.append(parcoursSousArbreAux(n));
//        }
//        return sb.toString();
//    }
//    public String parcoursSousArbre() {
//	    return parcoursSousArbreAux(this);
//
//    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }
}
