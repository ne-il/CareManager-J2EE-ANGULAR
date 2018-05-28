
# CareManager

Il faut avoir installer au préalable : Payara, Maven et un IDE permettant de faire du J2EE (IntelliJ est réellement recommandé).e

Je vous recommande d'aller sur le lien suivant : https://sites.google.com/site/j2eeupem/tp-jax-rs qui est le TP de J2EE qui explique comment faire fonctionner Payara. 
Et lorsque vous allez voir : TP5 ou TP5-demo, remplacer simplement ce nom par CareManager. 
Il faut impérativement que ce soit ainsi, car c'est configuré ainsi dans le persistence.xml.

Donc : 

TP5-Pool deviendra CareManager-Pool
TP5DS deviendra CareManagerDS
TP5-demo-1.war deviendra CareManager-1.war 

Voici donc les différentes commande à effectuer : 

./payara41/bin/asadmin start-domain

./payara41/bin/asadmin start-database

$>  ./payara41/javadb/bin/ij
ij> PROTOCOL 'jdbc:derby:';
ij> CONNECT '//localhost:1527/CareManager;create=true';


./payara41/bin/asadmin create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname org.apache.derby.jdbc.ClientDataSource --property "databaseName=CareManager: user=app:password=app: create=true: url=jdbc\\:derby\\://localhost\\:1527" CareManager-Pool

./payara41/bin/asadmin create-jdbc-resource --connectionpoolid CareManager-Pool jdbc/CareManagerDS

$>  cd ./CareManager
$>  mvn clean install (normalement c'est mvn package, mais ça revient exactement au même)
$> cd ..

./payara41/bin/asadmin deploy ./CareManager/target/CareManager-1.war 

Et si vous souhaitez redéployez l'application : 

mvn clean install; ../payara41/bin/asadmin redeploy --name CareManager-1 ./target/CareManager-1.war


Et si vous voulez voir si vos modifications ont fonctionné, aller à l'adresse :
http://localhost:8080/CareManager-1/rs/

Ensuite pour faire vos requêtes POST ou PUT, etc ... Je vous recommande d'utiliser Postman (téléchargeable et gratuit)


# La documentation des requetes



Liste de toute la documentation accessible sur internet.

https://documenter.getpostman.com/view/3495103/authentification/RVnPJiUo

https://documenter.getpostman.com/view/3495103/caremanager-document/RVnPJinQ

https://documenter.getpostman.com/view/3495103/caremanager-node/RVnPJinT

https://documenter.getpostman.com/view/3495103/caremanager-patient/RVnPK3aa

https://documenter.getpostman.com/view/3495103/caremanager-staff/RVnPK3ab

https://documenter.getpostman.com/view/3495103/creation-darborescence/RVnPK3ac

..



