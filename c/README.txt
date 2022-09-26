DOCUMENTAZIONE DEL PROGRAMMA "mastermind"

Sommario:
- Compilazione del file.................Riga 13
- Parametri da riga di comando..........Riga 18
- Ciclo di vita dell'applicazione.......Riga 22
- Verifica di correttezza del codice....Riga 30
- Commenti al codice....................Riga 41
- Test di correttezza dell'esecuzione...Riga 73
- Test sui possibili input..............Riga 92


Compilazione del file:
gcc -c mastermind.c
gcc -o mastermind mastermind.o


Parametri da riga di comando:
Il programma necessita del parametro 0/1 a seconda che si voglia visualizzare a schermo il codice segreto generato dal computer oppure no.


Ciclo di vita dell'applicazione:
All'avvio del programma comparirà il menù principale, da cui è possibile iniziare una nuova partita, visualizzare il regolamento o terminare il programma.
Dopo aver visualizzato il regolamento, si verrà reindirizzati al menù principale.
Scegliendo di iniziare una nuova partita, comparirà la schermata di scelta della modalità, da cui è possibile scegliere di giocare in modalità Classica o Personalizzata, oppure tornare al menù principale.
Scegliendo di giocare in modalità Classica la partita inizierà immediatamente, mentre scegliendo quella Personalizzata, sarà possibile configurare le impostazioni prima di iniziare.
Terminata la partita, sarà possibile rigiocare o tornare al menù principale.


Algoritmo di verifica della correttezza del codice:
Inizializzo a zero due vettori di supporto, di lunghezza pari alla quantità di lettere possibili, per contare le lettere giuste (ma al posto sbagliato) inserite dall'utente.
Inizializzo a zero due contatori per memorizzare la quantità di lettere giuste al posto giusto e al posto sbagliato.
Tramite un primo ciclo for, scorro il codice segreto e quello inserito dall'utente e, per ogni carattere, verifico che corrispondano: se sì, incremento il relativo contatore, altrimenti tengo nota delle lettere incontrate incrementando il contatore nella relativa posizione del relativo vettore di supporto.
Tramite un secondo ciclo for, scorro ora i vettori di supporto e, per ogni lettera, scelgo il valore minimo e lo sommo al valore del relativo contatore.
Il primo ciclo for mi consente di trovare la quantità di lettere giuste al posto giusto.
Il secondo ciclo for mi consente di trovare la quantità di lettere giuste al posto sbagliato.
La complessità di questo algoritmo è lineare, poichè il numero di passi necessario ad eseguire questo algoritmo è pari alla somma tra la lunghezza del codice e la quantità di lettere disponibili.
Tale algoritmo è presente nel codice alle righe [326:359].


Commenti al codice:
[2:4]: includo le librerie <stdlib.h>, <string.h> e <time.h> per utilizzare le funzioni exit(), srand(), rand(), strlen() e time().
[6:7]: poichè in linguaggio C non esiste il tipo booleano, definisco per comodità due costanti TRUE e FALSE.
[9:12]: definisco i percorsi dei file e le dimensioni del buffer di lettura.
[16:20]: definisco un dato strutturato che conterrà le impostazioni della partita.
[24:26]: definisco i "moduli" che costituiranno il ciclo di vita dell'applicazione.
         Ho scelto di incapsulare questi blocchi di codice in funzioni così da rendere più comprensibile il ciclo di vita dell'applicazione attraverso la lettura del main().
         Tutte queste funzioni restituiscono un valore booleano, al fine di regolare il flusso del programma.
[29:39]: serie di funzioni che, a differenza delle precedenti, non rappresentano i moduli che compongono il ciclo di vita dell'applicazione, ma sono utili ai fini della lettura o scrittura del programma:
    [29]: "stampaContenutoFile()" legge e stampa a video il contenuto del file presente al percorso fornito in input.
    [30]: "scegliValore()" restituisce un intero scelto dall'utente. Riceve in input il messaggio da stampare e i valori entro cui l'intero scelto dovranno essere compresi. 
    [31]: "configuraImpostazioni()" setta i campi della struct Impostazioni coi valori ricevuti in input.
    [32]: "svuotaStdin()" legge tutti i caratteri presenti nel buffer di input standard.
    [34]: "effettuaTentativo()" verifica che il codice inserito dall'utente sia valido e lo memorizza.
    [35]: "verificaTentativo()" confronta i due codici e comunica all'utente il risultato della verifica.
    [39]: "terminaPartita()" chiede all'utente se vuole giocare di nuovo.
[45:56]: verifico la correttezza dei parametri passati da riga di comando. Se il controllo fallisce, il programma termina restituendo 1.
[62:67]: [dis]abilito il debug.
[69:75]: ciclo di vita dell'applicazione (vedi relativo capitolo).
[95:97]: ho scelto di usare la funzione fgets() poichè la lettura bufferizzata è più efficiente rispetto a leggere un carattere alla volta e stamparlo.
[110:112, 118, 146:146, 274:276, 303, 313]: questi controlli assicurano che l'utente abbia premuto un solo carattere (diverso da INVIO) e non multipli.
[119, 128:134, 169:175, 280, 304, 315]: grazie al precedente controllo posso conoscere lo stato del buffer di input ed eventualmente ripulirlo per evitare che un successivo richiamo di getchar() legga caratteri "vecchi".
[208]: pulisco il buffer in caso l'utente abbia digitato caratteri multipli.
[211]: pulisco il buffer dai caratteri "vecchi".
[222]: setto il seme del generatore di numeri pseudocasuali della funzione "rand()" all'attuale tempo di calendario, per garantire che il codice generato ad ogni iterazione non sia identico al precedente.
[224]: setto il valore di ogni lettera del codice, sommando a 65 (la posizione del carattere 'A' nella tabella ASCII) un numero generato casualmente tra 0 e n, dove n è la quantità di lettere possibili.
[326:359]: algoritmo di verifica della correttezza del codice (vedi relativo capitolo):
    [328:337]: inizializzazione variabili di supporto.
    [339:350]: corpo dell'algoritmo.
    [355]: verifica correttezza del tentativo.


Serie di test eseguiti per verificare la correttezza dell'esecuzione:
- All'avvio del programma, il file "menu.txt" deve esistere.
- Scegliendo di visualizzare il regolamento, il file "regole.txt" deve esistere.
- Scegliendo di iniziare una nuova partita, il file "nuovapartita.txt" deve esistere.
- La lettura e stampa del contenuto di un file non deve produrre errori.
- Giocando in modalità Personalizzata:
    - La lunghezza del codice deve corrispondere a quella scelta dall'utente.
    - Il numero di tentativi disponibili deve corrispondere a quello scelto dall'utente.
    - Inserendo un codice invalido, il messaggio d'errore prodotto deve riportare la lunghezza del codice e l'intervallo dei caratteri scelto dall'utente.
- Il codice generato deve rispettare le caratteristiche impostate.
- Il numero di tentativi disponibili deve corrispondere a quello impostato.
- Inserendo un codice valido ma errato, la partita deve continuare.
- Inserendo un codice valido ma errato, il numero di tentativi disponibili deve diminuire.
- Inserendo il codice corretto, la partita deve terminare.
- Inserendo il codice corretto, deve comparire il messaggio di vittoria.
- Se l'utente esaurisce i tentativi, la partita deve terminare.
- Se l'utente esaurisce i tentativi, deve comparire il messaggio di sconfitta.


Serie di test eseguiti su tutti i possibili input che l'utente potrebbe fornire:
- RIGA DI COMANDO
    - Lanciare il programma con più di un parametro produce un messaggio d'errore.
    - Lanciare il programma con un parametro diverso da 0/1 produce un messaggio d'errore.
- MENU PRINCIPALE:
    - Premere '0' conduce alla scelta della modalità di gioco.
    - Premere '1' mostra il regolamento di gioco.
        - Premere un tasto qualsiasi (compreso INVIO) riporta al menù principale.
        - Premere più tasti riporta al menù principale.
    - Premere '2' produce un messaggio d'addio e termina il programma.
    - Premere un tasto diverso dai precedenti (compreso INVIO) produce un messaggio d'errore.
    - Premere più tasti produce un messaggio d'errore.
- SCELTA MODALITA DI GIOCO
    - Premere '0' inizia una nuova partita.
    - Premere '1' conduce alla modalità personalizzata.
    - Premere '2' riporta al menù principale.
    - Premere un tasto diverso dai precedenti (compreso INVIO) produce un messaggio d'errore.
    - Premere più tasti produce un messaggio d'errore.
- MODALITA PERSONALIZZATA
    - Inserire un "NaN" produce un messaggio d'errore.
    - Inserire un numero minore del minimo produce un messaggio d'errore.
    - Inserire un numero maggiore del massimo produce un messaggio d'errore.
    - Inserire un numero compreso tra il min e il max (inclusi) conduce alla scelta successiva.
    - Inserire correttamente tutti i parametri inizia una nuova partita.
- NUOVA PARTITA
    - Inserire un codice più corto del previsto produce un messaggio d'errore.
    - Inserire un codice più lungo del previsto produce un messaggio d'errore.
    - Inserire un codice contenente caratteri invalidi produce un messaggio d'errore.
    - Inserire un codice valido fa avanzare il programma.
- TERMINA PARTITA
    - Premere '0' inizia una nuova partita.
    - Premere un tasto diverso da '0' (compreso INVIO) riporta al menù principale.
    - Premere più tasti riporta al menù principale.
