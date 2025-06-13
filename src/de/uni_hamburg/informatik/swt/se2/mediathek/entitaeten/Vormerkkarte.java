package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Mit Hilfe von Vormerkkarten werden beim Vormerken eines Mediums alle relevanten
 * Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde vorgemerkt? Wer
 * hat das Medium bereits vorgemerkt?
 * 
 *  Um die Verwaltung der Karten kümmert sich der VerleihService
 * 
 * @author Finn-Malte-Schmiddy
 * @version SoSe 2025
 */
public class Vormerkkarte
{

    // Eigenschaften einer Vormerkkarte
    private final Queue<Kunde> _vormerker;
    private final Medium _medium;

    /**
     * Initialisert eine neue Vormerkkarte mit den gegebenen Daten.
     *
     * @param medium Das Medium fuer die Vormerkkarte
     *
     * @require medium != null
     *
     * @ensure #getMedium() == medium
     */
    public Vormerkkarte(Medium medium)
    {
        assert medium != null : "Vorbedingung verletzt: medium != null";

        _medium = medium;
        _vormerker = new LinkedList<Kunde>();
    }

    /**
     * Gibt ArrayList aller Vormerker aus.
     *
     * @return Liste aller vormerker Kunden
     *
     */
    public List<Kunde> getVormerker() {
        List<Kunde> vormerkerList = new ArrayList<>();
        for (Kunde kunde: _vormerker) {
           vormerkerList.add(kunde);
        }

        return vormerkerList;
    }

    /**
     * Fuege Vormker am Ende der Vormerker Queue hinzu
     *
     * @param kunde der Kunde der sich Vormerken will
     */
    public void addVormerker(Kunde kunde) {
        _vormerker.add(kunde);
    }

    /**
     * Entfernt ersten Vormerker aus der Queue (FiFo)
     *
     */
    public void entferneOberstenVormerker() {
        if (!_vormerker.isEmpty()) {
            _vormerker.remove();
        }
    }

    /**
     * Gibt den ersten Vormerker zurueck
     *
     * @return der ersten Vormerker aus der queue
     *
     */
    public Kunde getErstenVormerker() {
        return _vormerker.peek();
    }

    /**
     * Prueft ob das Vormerken moeglich ist anhand davon ob es weniger als 3 vormerker gibt
     *
     * @return true oder false ob vormerken moeglich ist
     */
    public boolean istVormerkenMoeglich(Kunde kunde) {

        return _vormerker.size() < 3 && !_vormerker.contains(kunde);
    }

    /**
     * Gibt das Medium, dessen Ausleihe auf der Karte vermerkt ist, zurück.
     * 
     * @return Das Medium, dessen Ausleihe auf dieser Karte vermerkt ist.
     * 
     * @ensure result != null
     */
    public Medium getMedium()
    {
        return _medium;
    }
}
