package application.node.repository;

import application.document.domain.Document;
import application.document.repository.DocumentRepository;
import application.node.domain.Node;
import application.node.rest.NodeRestService;
import application.patient.domain.Patient;
import application.patient.repository.PatientRepository;
import application.staff.domain.Staff;
import application.staff.repository.StaffRepository;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;
import java.util.List;

@Startup
@Singleton
public class NodeSetup {

    @EJB
    private NodeRepository nodeRepo;
    @EJB
    private PatientRepository patientRepo;
    @EJB
    private StaffRepository staffRepo;
    @EJB
    private DocumentRepository docRepo;


    private void initHopital(){
        Node root = new Node("APHP", "QG");

        Node hopital1 = new Node(
                "HOPITAL SAINT-ANTOINE", "Hopital");
        Node poleCardio = new Node("SA_Cardiologie", "Pole");
        Node polePediatrie = new Node("SA_Pediatrie", "Pole");
        Node polePsychiatrie = new Node("SA_psychiatrie", "Pole");
        Node urgence = new Node("SA_Urgences", "urgences");

        Node uhcard1 = new Node("SA_UH-CARD1", "UH");
        Node uhcard2 = new Node("SA_UH-CARD2", "UH");
        Node uhcard3 = new Node("SA_UH-CARD3", "UH");

        Node uhPed1 = new Node("SA_UH-PED1", "UH");
        Node uhPed2 = new Node("SA_UH-PED2", "UH");
        Node uhPed3 = new Node("SA_UH-PED3", "UH");

        List<Node> nodes = new ArrayList<>();
        nodes.add(root);
        nodes.add(hopital1);

        nodes.add(poleCardio);
        nodes.add(polePediatrie);
        nodes.add(polePsychiatrie);
        nodes.add(urgence);

        nodes.add(uhcard1);
        nodes.add(uhcard2);
        nodes.add(uhcard3);

        nodes.add(uhPed1);
        nodes.add(uhPed2);
        nodes.add(uhPed3);




        root.addChild(hopital1);

        hopital1.addChild(poleCardio);
        hopital1.addChild(polePediatrie);
        hopital1.addChild(polePsychiatrie);
        hopital1.addChild(urgence);

        poleCardio.addChild(uhcard1);
        poleCardio.addChild(uhcard2);
        poleCardio.addChild(uhcard3);


        polePediatrie.addChild(uhPed1);
        polePediatrie.addChild(uhPed2);
        polePediatrie.addChild(uhPed3);


        Node hopital2 = new Node("HÔPITAL SAINT-LOUIS", "Hopital");
        Node slpoleCardio = new Node("SL_Cardiologie", "Pole");
        Node slpoleGastro = new Node("SL_Gastro-Enterologie", "Pole");
        Node slurgence = new Node("SL_Urgences", "urgences");

        Node sluhcard1 = new Node("SL_UH-CARD1", "UH");
        Node sluhcard2 = new Node("SL_UH-CARD2", "UH");
        Node sluhcard3 = new Node("SL_UH-CARD3", "UH");

        Node sluhGas1 = new Node("SL_UH-GAS1", "UH");
        Node sluhGas2 = new Node("SL_UH-GAS2", "UH");
        Node sluhGas3 = new Node("SL_UH-GAS3", "UH");

        Node slusGas1 = new Node("SL_US-GAS1", "US");
        Node slusGas2 = new Node("SL_US-GAS2", "US");
        Node slusGas3 = new Node("SL_US-GAS3", "US");



        root.addChild(hopital2);
        hopital2.addChild(slpoleCardio);
        hopital2.addChild(slpoleGastro);
        hopital2.addChild(slurgence);

        slpoleCardio.addChild(sluhcard1);
        slpoleCardio.addChild(sluhcard2);
        slpoleCardio.addChild(sluhcard3);

        slpoleGastro.addChild(sluhGas1);
        slpoleGastro.addChild(sluhGas2);
        slpoleGastro.addChild(sluhGas3);

        sluhGas1.addChild(slusGas1);
        sluhGas1.addChild(slusGas2);
        sluhGas1.addChild(slusGas3);

        nodes.add(hopital2);
        nodes.add(slpoleGastro);
        nodes.add(slpoleCardio);
        nodes.add(slurgence);

        nodes.add(sluhcard1);
        nodes.add(sluhcard2);
        nodes.add(sluhcard3);

        nodes.add(sluhGas1);
        nodes.add(sluhGas2);
        nodes.add(sluhGas3);

        nodes.add(slusGas1);
        nodes.add(slusGas2);
        nodes.add(slusGas3);


        nodeRepo.save(root);
        nodeRepo.flush();



        List<Patient> patients = new ArrayList<>();

        Patient patientTest = new Patient("Jean","Dupont",
                "190097645103214", "12 rue Picasso, 75003 Paris",
                "12/09/1990", "12 Rue de la Gloire, 75013 Paris",
                "M", false, urgence, urgence.getName());

        patients.add(new Patient("Frederic","Bouchart",
                "177097645103214", "8 rue Emile Zola, 75002 Paris",
                "11/09/1977", "90 rue Naissance, 75004 Paris",
                "M", false, urgence, urgence.getName()) );

        patients.add(
                new Patient("Jean","Pierre",
                "177096969215788", "25 Place du Jeu de Paume, 69400 VILLEFRANCHE-SUR-SAÔNE",
                "25/09/1977", "39, Plateau d'Ouilly Gleizé, 69400 GLEIZE",
                "M", false, urgence, urgence.getName())
        );

        patients.add(
                new Patient("Eleanor","Rocheleau",
                "295019169215719", "01 Rue de la liberté, 91230 MONTGERON",
                        "19/01/1995", "22 Rue Gambetta, 94190 Villeneuve-Saint-Georges",
                "F", false, urgence, urgence.getName())
        );

        patients.add(
                new Patient("Benoît","Patry",
                        "190119169215777", "46 Rue de Verdun, 91230 MONTGERON",
                        "30/11/1990", "22 Rue Gambetta, 94190 Villeneuve-Saint-Georges",
                "M", true, urgence, urgence.getName())
        );

        patients.add(
                new Patient("Henry","Moulin",
                        "177097645103214", "25 rue de la liberté, 75001 Paris",
                        "12/09/1990", "12 Rue de la Gloire, 75013 Paris",
                        "M", false, urgence, urgence.getName())
        );

        patients.add(
                new Patient("Elsa","Marie",
                        "290116969215712", "09 Square du Tennis, 69400 VILLEFRANCHE-SUR-SAÔNE",
                        "08/11/1990", "39, Plateau d'Ouilly Gleizé, 69400 GLEIZE",
                        "F", false, urgence, urgence.getName())
        );

        patients.add(
                new Patient("Olivie","Favreau",
                        "192019169965719", "09 Square du Tennis, 69400 VILLEFRANCHE-SUR-SAÔNE",
                        "08/11/1990", "08 Rue Gambetta, 94190 Villeneuve-Saint-Georges",
                        "F", false, urgence, urgence.getName())
        );



        patients.add( patientTest

        );


        for (Patient p : patients) {
            patientRepo.save(p);
        }
        patientRepo.flush();


        List<Staff> staffs = new ArrayList<>();
        staffs.add(new Staff("Alain","Hademin",
                "alain.hademin@aphp.fr","0675859285",
                "alain.hademin@aphp.fr","alainhademin",
                (long) root.getId(),"WORKING", "ADMIN"  ));

        staffs.add(new Staff("Nathalie","Duch",
                "nathalie.duch@aphp.fr","0674852285",
                "nathalie.duch@aphp.fr","nathalieduch",
                (long) root.getId(),"WORKING", "NURSE"  ));

        Staff d1 = new Staff("Gregory","Maison",
                "gregory.maison@aphp.fr","0674259285",
                "gregory.maison@aphp.fr","gregorymaison",
                (long) urgence.getId(),"WORKING", "DOCTOR"  );

        staffs.add(d1);

        staffs.add(new Staff("Anne","Dujardin",
                "anne.dujardin@aphp.fr","0674849285",
                "anne.dujardin@aphp.fr","annedujardin",
                (long) hopital1.getId(),"WORKING", "SECRETARY"  ));

        staffs.add(new Staff("Eric","Mador",
                "eric.mador@aphp.fr","0674849285",
                "eric.mador@aphp.fr","ericmador",
                (long) slpoleGastro.getId(),"WORKING", "DOCTOR"  ));


        for (Staff s: staffs) {
            staffRepo.save(s);
        }
        staffRepo.flush();
//                repository.save(new Staff("doctorTest","nom1","staff1@aphp.fr","0101010101",
//                "doctor","123",1L, "WORKING","DOCTOR"));
//
//        repository.save(new Staff("adminTest","nom2","staff2@aphp.fr","0202020202",
//                "admin","123",1L,"WORKING", "ADMIN"));
//
//        repository.save(new Staff("secretaryTest","nom3","staff3@aphp.fr","0303030303",
//                "secretary","123",1L,"RETIRED", "SECRETARY"));
//
//        repository.save(new Staff("nurseTest","nom3","staff3@aphp.fr","0303030303",
//                "nurse","123",1L,"RETIRED", "NURSE"));
        setFatherAllNodes(nodes);


//        Document doc1 = new Document(d1.getId(),"OBERSVATION","operation d'une crise d'appendicite", "fakeurl", patientTest.getId(), "VALIDATED");
//        doc1.setDateOfValidation("05/01/2018");
        Document doc2 = new Document(d1.getId(),"OBSERVATION","Vomissements aigues", "fakeurl", patientTest.getId(), "VALIDATED");
        doc2.setDateOfValidation("27/03/2018");

//        patientTest.addDocument(doc1);
        patientTest.addDocument(doc2);

        patientRepo.update(patientTest);
//        docRepo.save(doc1);

    }

    private void setFatherAllNodes(List<Node> nodes){
        for (Node n: nodes) {
            long fatherId = n.getId();
            for (Node child: n.getChildrens()) {
                child.setFatherId(fatherId);
            }
        }
    }


    @PostConstruct
    private void createData(){
        initHopital();
//        Node root = new Node("ROOT","ROOT");
//        repository.save(root);
//        repository.flush();
//
//
////        Long nodeId = root.getId();
////        System.out.println("test");
//
//
//        Node node1 = new Node("test","test");
//        root.addChild(node1);
//        System.out.println(root);
//        repository.update(root);
//
//
////        root.addChild(node1);

//        Node root = new Node("ROOT","ROOT");
//        Node node1 = new Node("test","test");
//        Node node2 = new Node("test","test");
//        Node node3 = new Node("test","test");
//
//        root.addChild(node1);
//        node1.addChild(node2);
//        node2.addChild(node3);
////        node1.ax
////        repository.save(root);
////        repository.save(node1);
////        repository.flush();


    }
}
