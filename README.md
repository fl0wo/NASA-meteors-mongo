# NASA Meteors Mongo

NASAMeteorsMongo è un progetto in grado di connettersi ad un database ad oggetti contentente tutti i meteoriti caduti sulla superfice terrestre, stampandone le posizioni con puntini rossi  in una oggetto sferico tridimensionale.

<img src="https://orig00.deviantart.net/42fe/f/2015/239/1/3/meteor_gif_by_boxphox-d97ctfm.gif" width="200">

## Collezioni
Il database è document-oriented, MongoDB viene usato come DBMS per interrogare il database hostato da Mongo-Atlas.
Stitch viene usato come server cloud per accedere al database e eseguire delle query tramite metodi http.

## Popolamento del DataBase
Per popolare il Database mongo, avente un file con estensione .json ho deciso di non utilizzare il comando shell "mongoimport" o "curl" , bensì ho deciso di scrivere un codice in NodeJs in grado di leggere un file e popolare il database, in quanto a differenza degli altri metodi così sono in grado di effetuare modifiche opportune ai documenti json prima di inserirli (come per esempio l'eliminazioni di campi sempre nulli o la formattazione di particolari campi come date e ore)
Il codice NodeJs in grado di popolare il database Atlas puoi trovarlo qui.

# CRUD

## CREA (CREATE)
Per inserire (creare) un nuovo meteorite, uso un metodo HTTP POST che in Stitch ho implementato nel seguente modo :

```
exports = function(payload) {
    const meteor = EJSON.parse(payload.body.text());
    return context.functions.execute("insert_meteor", meteor);
};
```
payload.body é il documento che si intende inserire all'interno del della collezione dei meteoriti, è un'oggetto che viene passato come argomento col HTTP POST. Il codice esegue la funzione "insert_meteor" passando come parametro il meteorite che si intede inserire.

```
  meteors.insertOne(meteor)
    .then(result => {
        return true;
    })
    .catch(err => {
        return false;
    });
```


## LEGGI (READ)
Per leggere il contenuto del database, uso un metodo HTTP GET che in Stitch ho implementato nel seguente modo :
```
  return meteors
    .find(selections.all_meteors(),projections.no_id())
    .limit(lim | 0)
    .toArray();
```
L'oggetto selections è un'oggetto che contiene alcune query di selezione : 
```
  const selections = {
    all_meteors : () => { return {} } ,
    meteors_by_name : target => {return {name : target} }
  };
```
L'oggetto selections è un'oggetto che contiene alcune query di proiezione : 
```
  const projections = { 
    no_id : () => { return { _id : 0 } }
  };
```
lim è un numero rappresentato in stringa, convertito in numero con l'operatore OR "|".


## AGGIORNA (UPDATE)
Per aggiornare un documento all'interno del database, uso un metodo HTTP POST, nonostante ci sia anche il metodo HTTP PATCH preferisco usare un metodo POST in quanto comunque è un metodo con la quale si possono passare oggetti come parametri che poi decido di interpretarli come un'oggetto contenente una query, che specifica il target che andrò a colpire e un to_update ovvero le modifiche che andrò a fare all'oggetto. Es :
```
  meteors.updateOne(query,meteor)
    .then(result => {
        return true;
    })
    .catch(err => {
        return false;
    });
```

## RIMUOVI (DELETE)
Per rimuovere un documento dal database, uso un metodo HTTP POST, nonostante ci sia anche il metodo HTTP DELETE preferisco usare un metodo POST in quanto comunque è un metodo con la quale si possono passare oggetti come parametri che poi decido di interpretarli come un'oggetto contenente una query, che specifica il target che andrò a eliminare. Es :
```
  meteors.removeMany(query,meteor)
    .then(result => {
        return true;
    })
    .catch(err => {
        return false;
    });
```
e un safedelete :
```
  meteors.removeOne(query,meteor)
    .then(result => {
        return true;
    })
    .catch(err => {
        return false;
    });
```

## LEGGIx2 (READx2)
Per leggere dal database specificando query più articolate ritenevo ragionevole passare un'oggetto come parametro, e non tanti dati "primitivi" come nel HTTP GET, quindi ho deciso di mettere a disposizione anche un metodo HTTP POST per la lettura dal database. Dove l'oggetto meteor è la query che si passa come parametro.

```
 return meteors
    .find(meteor,projections.no_id())
    .toArray();
```

# VALUES
Inoltre ritenevo interessante utlizzare le values di Stitch nella quale ho inserito nomi di collezioni e database, in quanto ritenevo importante poter cambiare velocemente il nome di alcuni elementi senza dover analizzare tutto il codice scritto andando a cercare tutte le volte che veniva usato quel nome :

collezioni : 
```
{
  "meteors": "meteors",
  "test": "test"
}
```
strings : 
```
{
  "deleted_ok": "not in database anymore!",
  "deleted_nok": "something went wrong deleting/",
  "inserted_ok": "it has been saved in the database!",
  "inserted_nok": "something went wrong inserting/"
}
```
db_name : 
```
"nasa_infos"
```

# Atlas Graph
Grafico rappresentante i meteoriti suddivisi per classe e ordinati per massa.
![graph_meteor](uploads/69f425f393627cc95a17c3ace479a14f/graph_meteor.png)

# CITAZIONI IMPORTANTI

>La seconda prova è fuffa <br />
> Divino Marchese, ~2018

