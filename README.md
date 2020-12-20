# Mars Agriculture and Research Service - Server (groep 14)

*door Timo De Clercq, Annelin De Gols, Robin De Kinders Laurens Giesen, Cedric Puystjens*

## Server

### Informatie

Om de server op te zetten start je met het [Clonen](#installatie) van de server. Als de server succesvol gecloned is kan
je deze [configureren](#configuratie) in de **conf/config.json**. Hier moet je een poort specifiëren waarop je de server
wilt draaien. In deze configuratie file kan u ook de gegevens voor de database wijzigen. In de
file: [MarsOpenApiBridge](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server/-/blob/master/src/main/java/be/howest/ti/mars/webserver/MarsOpenApiBridge.java)
worden alle requests afgehandeld die in
de [OpenAPI File](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server/-/blob/master/src/main/resources/openapi-group-14.yaml)
omschreven zijn. Deze file staat in contact met
de [MarsController](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server/-/blob/master/src/main/java/be/howest/ti/mars/logic/controller/MarsController.java)
, in deze MarsController worden de repo's aangemaakt waarmee we zaken kunnen doen zoals gebruikers aanmaken, gewassen
oplijsten, ...
<br>

### Installatie

```
git clone git@git.ti.howest.be:TI/2020-2021/s3/project-ii/projects/groep-14/server.git

```

Of

``` 
git clone https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server.git

```

### Configuratie

**Path: conf/config.json**

```json

{
  "http": {
    "port": 8080
  },
  "db" : {
    "url": "jdbc:h2:~/mars-db",
    "username": "group-14",
    "password": "t3sfe1k3nUe",
    "webconsole.port": 9000
  }
}

```

### Database

[Aanmaken van de database](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server/-/blob/master/src/main/resources/databaseStructure.sql)

[Toevoegen van gegevens aan de database](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server/-/blob/master/src/main/resources/populateDatabase.sql)
---

## OpenAPI

[Link](https://git.ti.howest.be/TI/2020-2021/s3/project-ii/projects/groep-14/server/-/blob/master/src/main/resources/openapi-group-14.yaml)