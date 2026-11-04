// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 1: il Punteggio. Scadenza: domenica 25 ottobre 2026.
package pong;

import java.util.ArrayList;
import java.util.List;

/**
 * I conti di una partita: chi ha segnato, quanti punti ha ciascun lato, chi ha vinto.
 * La Partita gli dice chi ha fatto punto; lo Schermo chiede i punti alla Partita, che li chiede a lui.
 *
 * La regola per vincere: un lato vince quando ha almeno {@code puntiPerVincere} punti e almeno
 * {@code scartoMinimo} punti più dell'altro. Esempi, con 11 punti e scarto 2: 11 a 9 vince;
 * 11 a 10 si continua; 12 a 10 vince. Con scarto 1 vince chi arriva per primo a 11.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi qui sotto (e i campi che vi servono).
 * Ogni metodo ha il suo contratto, nel commento sopra: cosa pretende, cosa promette.
 * I test ufficiali (cartella test) controllano i contratti.
 */
public class Punteggio {

    private int puntiPerVincere;
    private int scartoMinimo;
    private List<Lato> storia;

    /**
     * Una partita nuova, 0 a 0, con la sua regola per vincere.
     * Pretende: puntiPerVincere è almeno 1; scartoMinimo è almeno 1.
     * Promette: una partita 0 a 0, non finita, senza vincitore e con la storia vuota.
     */
    public Punteggio(int puntiPerVincere, int scartoMinimo) {
        if (puntiPerVincere < 1) {
            throw new IllegalArgumentException("puntiPerVincere deve essere almeno 1: " + puntiPerVincere);
        }
        if (scartoMinimo < 1) {
            throw new IllegalArgumentException("scartoMinimo deve essere almeno 1: " + scartoMinimo);
        }
        this.puntiPerVincere = puntiPerVincere;
        this.scartoMinimo = scartoMinimo;
        this.storia = new ArrayList<>();
    }

    /**
     * Il lato chi ha fatto un punto.
     * Pretende: chi non è null.
     * Promette: se la partita non era finita, il lato chi ha un punto in più, l'altro lato non
     *           cambia e il punto si aggiunge in fondo alla storia; se con questo punto il lato chi
     *           vince (regola in cima alla classe), la partita è finita e il vincitore è chi.
     *           Se la partita era già finita, non cambia niente.
     */
    public void segnaPunto(Lato chi) {
        if (chi == null) {
            throw new IllegalArgumentException("Il lato che segna punto non può essere nullo");
        }
        if (!isFinita()) {
            storia.add(chi);
        }
    }

    /**
     * Pretende: chi non è null.
     * Promette: i punti del lato chi. Non cambia niente.
     */
    public int getPunti(Lato chi) {
        if (chi == null) {
            throw new IllegalArgumentException("Impossibile fornire i punri di un lato nullo");
        }
        int contatore = 0;
        for (Lato l : storia) {    // per ogni Lato l in storia
            if (l == chi) {
                contatore ++;
            }
        }
        return contatore;
    }

    /**
     * Aggiungo un metodo ausiliario per scrivere meno volte le condizioni di vittoria
     * nei due metodi successivi così se volessi cambiarle sono più precisa e veloce. 
     * Pretende: chi non è null.
     * Promette: vero se ha vinto Lato chi, falso altrimenti.
     */
    private boolean haVinto(Lato chi) {
        Lato altro;
        if (chi == Lato.DESTRA) {
            altro = Lato.SINISTRA;
        } else {
            altro = Lato.DESTRA;
        }
        if (getPunti(chi) >= puntiPerVincere && getPunti(chi) - getPunti(altro) >= scartoMinimo){
            return true;
        } else {
            return false;
        }

    }

    /**
     * Promette: vero se la partita è finita, cioè se un lato ha vinto. Non cambia niente.
     */
    public boolean isFinita() {
        if (haVinto(Lato.DESTRA) || haVinto(Lato.SINISTRA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Promette: il lato che ha vinto; null se la partita non è finita. Non cambia niente.
     */
    public Lato getVincitore() {
        if (haVinto(Lato.DESTRA)) {
            return Lato.DESTRA;
        } else if (haVinto(Lato.SINISTRA)) {
            return Lato.SINISTRA;
        } else {
            return null;
        }
    }

    /**
     * L'arbitro ci ripensa: toglie l'ultimo punto della storia (l'ultimo segnato e non già annullato).
     * Pretende: niente.
     * Promette: se nella storia c'è almeno un punto, l'ultimo punto viene tolto, dai conti e dalla
     *           storia, come se non fosse mai stato segnato. Se era il punto che aveva chiuso la
     *           partita, la partita torna in corso, senza vincitore. Se la storia è vuota, non cambia
     *           niente.
     */
    public void annullaUltimoPunto() {
        if (storia.size() > 0) {
            storia.remove(storia.size() - 1);
        }
    }

    /**
     * La storia dei punti, dal primo all'ultimo: per ogni punto segnato (e non annullato), il lato
     * che l'ha segnato.
     * Pretende: niente.
     * Promette: una lista con i punti in ordine. Chi riceve la lista non può cambiare il Punteggio
     *           attraverso di lei: aggiungere, togliere o cambiare elementi della lista ricevuta
     *           (se la lista lo permette) non cambia i punti, il vincitore né la storia del Punteggio.
     *           Non promette altro: la lista ricevuta può restare com'era o mostrare anche i punti
     *           segnati dopo; chi la usa non deve contare né sull'una né sull'altra cosa.
     */
    public List<Lato> getStoria() {
        return List.copyOf(storia);
    }

    /**
     * Una partita nuova con la stessa regola.
     * Pretende: niente.
     * Promette: 0 a 0, non finita, senza vincitore, storia vuota; la regola per vincere non cambia.
     */
    public void azzera() {
        storia.clear();
    }


    boolean repOK() {
        for (Lato l : storia) {    // per ogni Lato l in storia
            if (l == null) {
                return false;
            }    
            
        }
        return true;
    }

    
}
