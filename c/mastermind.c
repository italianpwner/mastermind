#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define FALSE 0
#define TRUE !FALSE

#define MENU_FILEPATH "text/menu.txt"
#define REGOLE_FILEPATH "text/regole.txt"
#define NEWGAME_FILEPATH "text/nuovapartita.txt"
#define DIMENSIONE_BUFFER 256


// ============================== DATI STRUTTURATI ==============================
struct Impostazioni {
    int letterePossibili;
    int lunghCodice;
    int numTentativi;
} impostazioni;

// ============================== PROTOTIPI FUNZIONI ==============================
// Ciclo di vita dell'applicazione
int menuPrincipale();
int sceltaModalita();
int nuovaPartita(char* codice, struct Impostazioni impostazioni, int debug);

// Funzioni utili
void stampaContenutoFile(char* nomeFile);
int scegliValore(char * messaggio, int min, int max);
void configuraImpostazioni(struct Impostazioni * i, int letterePossibili, int lunghCodice, int numTentativi);
void svuotaStdin();
void generaCodice(char* codice, struct Impostazioni impostazioni);
void effettuaTentativo(char* tentativo, struct Impostazioni impostazioni);
int verificaTentativo(char* codice, char* tentativo, struct Impostazioni impostazioni);
void inizializzaArrayVuoto(int a[], int lunghezza);
int minimo(int a, int b);
void stampaCodice(char* codice, int lunghezza);
int terminaPartita(int indovinato);


// =================================== MAIN ===================================
int main(int argc, char **argv) {

    if(argc > 2) {
        fprintf(stderr, "Inserire un solo parametro!\n");
        return 1;
    } else if(argc == 1) {
        fprintf(stderr, "Parametro mancante!\n");
        return 1;
    } else {
        if(strlen(argv[1]) != 1 || (argv[1][0] != '1' && argv[1][0] != '0')) {
            fprintf(stderr, "Parametro non valido!\n");
            return 1;
        }
    }

    printf("\n************************************************");
    printf("\n****************** MASTERMIND ******************");
    printf("\n************************************************\n");
    
    int debug;
    if(argv[1][0] != '1') {
        debug = FALSE;
    } else {
        debug = TRUE;
    }

    while(menuPrincipale()) {
        if(sceltaModalita()) {
            char codice[impostazioni.lunghCodice];
            while(nuovaPartita(codice, impostazioni, debug));
        }
    }
    printf("\nGrazie per aver giocato!\n\n");
    
    return 0;
}


// ============================== DEFINIZIONE FUNZIONI ==============================
void stampaContenutoFile(char* nomeFile) {
    
    FILE *stream, *fopen();
    char linea[DIMENSIONE_BUFFER];

    stream = fopen(nomeFile, "r");
   
    if(stream == NULL) {
        fprintf(stderr, "Il file \"%s\" non esiste.\n", nomeFile);
        exit(1);
    }
    
    printf("\n");
    while(fgets(linea, DIMENSIONE_BUFFER, stream) != NULL) {
        printf("%s", linea);
    }

    fflush(stream);
    fclose(stream);
}


int menuPrincipale() {

    stampaContenutoFile(MENU_FILEPATH);
    char cmd;

    do {
        cmd = getchar();
        if(cmd != '\n') {
            if(getchar() == '\n') {
                switch(cmd) {
                    case '0':
                        break;
                    case '1':    
                        stampaContenutoFile(REGOLE_FILEPATH);
                        if(getchar() != '\n') {
                            svuotaStdin();
                        }
                        stampaContenutoFile(MENU_FILEPATH);
                        break;
                    case '2':
                        return FALSE;
                    default:
                        fprintf(stderr, "L'opzione selezionata è inesistente!\n");
                }        
            } else {
                fprintf(stderr, "Premi un tasto solo!\n");
                svuotaStdin();
            }
        } else {
            fprintf(stderr, "Input non valido!\n");
        }
    } while(cmd != '0');
    return TRUE;
}
    

int sceltaModalita() {    
    
    stampaContenutoFile(NEWGAME_FILEPATH);
    char cmd;

    do {
        cmd = getchar();
        if(cmd != '\n') {
            if(getchar() == '\n') {
                
                struct Impostazioni * i = &impostazioni;
    
                switch(cmd) {
                    case '0':
                        configuraImpostazioni(i,6,4,12);
                        break;
                    case '1':
                        printf("\n");
                        configuraImpostazioni(i,
                            scegliValore("Inserisci il numero di possibili lettere che comporranno il codice (2-20): ", 2, 20),
                            scegliValore("Inserisci la lunghezza del codice (4-10): ", 4, 10),
                            scegliValore("Inserisci il numero massimo di tentativi disponibili per indovinare (7-20): ", 7, 20)
                        );
                        break;
                    case '2':
                        return FALSE;
                    default:
                        fprintf(stderr, "L'opzione selezionata è inesistente!\n");
                }
            } else {
                fprintf(stderr, "Premi un carattere solo!\n");
                svuotaStdin();
            }
        } else {
            fprintf(stderr, "Input non valido!\n");
        }
    } while(cmd != '0' && cmd != '1');

    printf("%d\n", impostazioni.lunghCodice);

    return TRUE;
}


void configuraImpostazioni(struct Impostazioni * i, int letterePossibili, int lunghCodice, int numTentativi) {
    i->letterePossibili = letterePossibili;
    i->lunghCodice = lunghCodice;
    i->numTentativi = numTentativi;
}


int scegliValore(char* messaggio, int min, int max) {
    
    int scelta = 0;
    int sceltaValida = FALSE;

    printf("%s", messaggio);

    while(!sceltaValida) {
        
        if(scanf("%d", &scelta) != 0) {
            if(scelta >= min && scelta <= max) {
                sceltaValida = TRUE;
            } else {
                fprintf(stderr, "Il numero selezionato dev'essere compreso tra %d e %d!\n", min, max);
            }
        } else {
            fprintf(stderr, "Inserimento non valido. Inserire un numero!\n");
            svuotaStdin();
        }
    }
    svuotaStdin();
    return scelta;
}


void svuotaStdin() {
    while(getchar() != '\n');
}


void generaCodice(char* codice, struct Impostazioni impostazioni) {
    srand(time(0));
    for(int i=0; i<impostazioni.lunghCodice; i++) {
        codice[i] = 65 + (rand() % impostazioni.letterePossibili);
    }
}


int nuovaPartita(char* codice, struct Impostazioni impostazioni, int debug) {
    
    int indovinato = FALSE;
    int round = 1;
    int tentativiDisponibili = impostazioni.numTentativi;
    char tentativo[impostazioni.lunghCodice];

    generaCodice(codice, impostazioni);

    if(debug) {
        printf("\n[DEBUG] Codice segreto: ");
        stampaCodice(codice, impostazioni.lunghCodice);
        printf("\n");
    }  

    while(tentativiDisponibili > 0) {
        printf("\nTentativi rimasti: %d\nTentativo n.%d: ", tentativiDisponibili, round);
        
        effettuaTentativo(tentativo, impostazioni);
        indovinato = verificaTentativo(tentativo, codice, impostazioni);

        if(indovinato) {
            break;
        }

        round++;
        tentativiDisponibili--;
    }
    
    return terminaPartita(indovinato);
}


int terminaPartita(int indovinato) {

    printf("\n");
    if(indovinato) {
        printf("Complimenti! Hai indovinato il codice!");
    } else {
        printf("Peccato! Non hai indovinato il codice :(");
    }

    printf("\nPremere '0' per giocare ancora o un tasto qualsiasi per tornare al menù principale.\n");

    char c = getchar();
    if(c != '\n') {
        if(getchar() == '\n') {
            if(c == '0') {
                return TRUE;
            }
        } else {
            svuotaStdin();
        }
    }
    return FALSE;
}


void effettuaTentativo(char* tentativo, struct Impostazioni impostazioni) {

    int primaLettera = 65;
    int ultimaLettera = primaLettera + impostazioni.letterePossibili - 1;
    int codiceValido;
    char c;

    do {
        codiceValido = TRUE;

        for(int i=0; i<impostazioni.lunghCodice; i++) {
            c = getchar();

            if(c >= primaLettera && c <= ultimaLettera) {
                tentativo[i] = c;
            } else {
                if(c != '\n') {
                    svuotaStdin();
                }
                codiceValido = FALSE;
                break;
            }
        }
    
        if(codiceValido) {
            c = getchar();
            if(c != '\n') {
                codiceValido = FALSE;
                svuotaStdin();
            }
        }

        if(!codiceValido) {
            fprintf(stderr, "Il codice inserito non è valido. Assicurati che sia lungo %d caratteri e sia composto da lettere del seguente intervallo (%c-%c).\n", impostazioni.lunghCodice, primaLettera, ultimaLettera);
        }
    } while(!codiceValido);
}


int verificaTentativo(char* codice, char* tentativo, struct Impostazioni impostazioni) {
    
    int letterePossibili = impostazioni.letterePossibili;

    int contaLetCodice[letterePossibili];
    int contaLetTentativo[letterePossibili];

    inizializzaArrayVuoto(contaLetCodice, letterePossibili);
    inizializzaArrayVuoto(contaLetTentativo, letterePossibili);

    int letPosCorretta = 0;
    int letPosErrata = 0;
    
    for(int i=0; i<impostazioni.lunghCodice; i++) {
        if(codice[i] == tentativo[i]) {
            letPosCorretta++;
        } else {
            contaLetCodice[codice[i]-65]++;
            contaLetTentativo[tentativo[i]-65]++;
        }
    }

    for(int i=0; i<letterePossibili; i++) {
        letPosErrata += minimo(contaLetCodice[i], contaLetTentativo[i]);
    }

    printf("Lettere giuste in posizione giusta: %d\n", letPosCorretta);
    printf("Lettere giuste in posizione errata: %d\n", letPosErrata);

    if(letPosCorretta == impostazioni.lunghCodice) {
        return TRUE;
    }
    return FALSE;
}


void inizializzaArrayVuoto(int a[], int lunghezza) {
    for(int i=0; i<lunghezza; i++) {
        a[i] = 0;
    }
}


int minimo(int a, int b) {
    if(a<b) {
        return a;
    }
    return b;
}


void stampaCodice(char* codice, int lunghezza) {
    for(int i=0; i<lunghezza; i++) {
        printf("%c", codice[i]);
    }
}

