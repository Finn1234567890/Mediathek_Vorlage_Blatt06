package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Kundennummer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/*
 * Testklasse für die Vormerkkarte testet
 */
public class VormerkkarteTest
{
    private final Medium _medium;
    private final Kunde _kunde1;
    private final Kunde _kunde2;
    private final Kunde _kunde3;
    private final Kunde _kunde4;
    private final Vormerkkarte _vormerkkarte;

    public VormerkkarteTest()
    {
        _medium = new CD("Scream", "Geil", "Ghost", 1); // Medium erstellen

        // Kunden erstellen
        _kunde1 = new Kunde(new Kundennummer(111111), "Nico", "Schmidt");
        _kunde2 = new Kunde(new Kundennummer(222222), "Finn", "Kämper");
        _kunde3 = new Kunde(new Kundennummer(333333), "Malte", "Engel");
        _kunde4 = new Kunde(new Kundennummer(444444), "Max", "Abe");
        
        _vormerkkarte = new Vormerkkarte(_medium); // Vormerkkarte erstellen
        _vormerkkarte.addVormerker(_kunde1);
    }


    @Test
    public void TestIstVormerkenMoeglich()
    {
        _vormerkkarte.addVormerker(_kunde2);
        _vormerkkarte.addVormerker(_kunde3);

        assertFalse(_vormerkkarte.istVormerkenMoeglich(_kunde3)); //Vormerker darf nicht 2 mal auf Vormerkliste stehen
        assertFalse(_vormerkkarte.istVormerkenMoeglich(_kunde4)); //nicht mehr als 3 Vormerker
    }

    @Test
    public void TestMerkeVor()
    {
        _vormerkkarte.merkeVor(_kunde2);
        _vormerkkarte.merkeVor(_kunde3);

        assertTrue(_vormerkkarte.getVormerkerListe().contains(_kunde2));
        assertTrue(_vormerkkarte.getVormerkerListe().contains(_kunde1));
    }

    @Test
    public void TestGetErstenVormerker()
    {
        _vormerkkarte.merkeVor(_kunde2);
        _vormerkkarte.merkeVor(_kunde3);

        assertEquals(_kunde1, _vormerkkarte.gibErstenVormerker());

        _vormerkkarte.entferneVormerker();
        assertEquals(_kunde2, _vormerkkarte.gibErstenVormerker());
    }

    @Test
    public void TestGetMedium()
    {
        assertEquals(_medium, _vormerkkarte.getMedium());
    }

    @Test
    public void TestEntferneOberstenVormerker()
    {
        _vormerkkarte.merkeVor(_kunde2);
        _vormerkkarte.entferneVormerker();
        assertFalse(_vormerkkarte.getVormerkerListe().contains(_kunde1));
        assertEquals(_kunde2, _vormerkkarte.gibErstenVormerker());
        assertTrue(_vormerkkarte.getVormerkerListe().contains(_kunde2));
    }

    @Test
    public void TestGetVormerker()
    {
        assertEquals(_kunde1, _vormerkkarte.getVormerker(0));

        _vormerkkarte.merkeVor(_kunde2);
        _vormerkkarte.merkeVor(_kunde3);

        assertEquals(_kunde2, _vormerkkarte.getVormerker(1));
        assertEquals(_kunde3, _vormerkkarte.getVormerker(2));
    }
    
    @Test
    public void TestAddVormerker()
    {
    	//kunde 1 nicht weil der bei initalisierung schon drin war
    	_vormerkkarte.addVormerker(_kunde2);
        _vormerkkarte.addVormerker(_kunde3);

        // korrekt vorgemerkt?
        assertEquals(_kunde1, _vormerkkarte.getVormerker(0));
        assertEquals(_kunde2, _vormerkkarte.getVormerker(1));
        assertEquals(_kunde3, _vormerkkarte.getVormerker(2));
    }

}
