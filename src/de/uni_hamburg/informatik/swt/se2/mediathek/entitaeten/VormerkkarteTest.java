package de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Kundennummer;
import org.junit.Test;

import static org.junit.Assert.*;

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
        Vormerkkarte vormerkkarte = new Vormerkkarte(_medium);

        vormerkkarte.addVormerker(_kunde1);
        vormerkkarte.addVormerker(_kunde2);
        vormerkkarte.addVormerker(_kunde3);

        assertFalse(vormerkkarte.istVormerkenMoeglich(_kunde3)); //Vormerker darf nicht 2 mal auf Vormerkliste stehen
        assertFalse(vormerkkarte.istVormerkenMoeglich(_kunde4)); //nicht mehr als 3 Vormerker
    }

    @Test
    public void TestMerkeVor()
    {
        Vormerkkarte vormerkkarte = new Vormerkkarte(_medium);
        vormerkkarte.addVormerker(_kunde1);

        assertFalse(vormerkkarte.getVormerker().contains(_kunde2));
        assertTrue(vormerkkarte.getVormerker().contains(_kunde1));
    }

    @Test
    public void TestGetErstenVormerker()
    {
        Vormerkkarte vormerkkarte = new Vormerkkarte(_medium);

        vormerkkarte.addVormerker(_kunde1);
        vormerkkarte.addVormerker(_kunde2);

        assertEquals(_kunde1, vormerkkarte.getErstenVormerker());

        vormerkkarte.entferneOberstenVormerker();
        assertEquals(_kunde2, vormerkkarte.getErstenVormerker());
    }

    @Test
    public void TestGetMedium()
    {
        assertEquals(_medium, _vormerkkarte.getMedium());
    }

    @Test
    public void TestEntferneOberstenVormerker()
    {
        Vormerkkarte vormerkkarte = new Vormerkkarte(_medium);

        vormerkkarte.addVormerker(_kunde1);
        vormerkkarte.addVormerker(_kunde2);

        vormerkkarte.entferneOberstenVormerker();

        assertFalse(vormerkkarte.getVormerker().contains(_kunde3));
        assertEquals(_kunde2, vormerkkarte.getErstenVormerker());
    }

    @Test
    public void TestGetVormerker()
    {
        Vormerkkarte vormerkkarte = new Vormerkkarte(_medium);

        vormerkkarte.addVormerker(_kunde1);
        vormerkkarte.addVormerker(_kunde2);
        vormerkkarte.addVormerker(_kunde3);

        assertEquals(_kunde1, vormerkkarte.getVormerker().get(0));
        assertEquals(_kunde2, vormerkkarte.getVormerker().get(1));
        assertEquals(_kunde3, vormerkkarte.getVormerker().get(2));
    }

    @Test
    public void TestAddVormerker()
    {
       Vormerkkarte vormerkkarte = new Vormerkkarte(_medium);

        //kunde 1 nicht weil der bei initalisierung schon drin war
        vormerkkarte.addVormerker(_kunde1);
        vormerkkarte.addVormerker(_kunde2);
        vormerkkarte.addVormerker(_kunde3);

        // korrekt vorgemerkt?
        assertEquals(_kunde1, vormerkkarte.getVormerker().get(0));
        assertEquals(_kunde2, vormerkkarte.getVormerker().get(1));
        assertEquals(_kunde3, vormerkkarte.getVormerker().get(2));
    }
}
