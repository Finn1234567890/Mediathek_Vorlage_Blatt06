package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.entitaeten.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.ServiceObserver;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammServiceImpl;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandServiceImpl;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.wertobjekte.Kundennummer;

/**
 * @author SE2-Team
 */
public class VerleihServiceImplTest
{
    private Datum _datum;
    private Kunde _kunde;
    private VerleihService _service;
    private List<Medium> _medienListe;
    private Kunde _vormerkkunde;
    private Kunde _kunde1;
    private Kunde _kunde2;
    private Kunde _kunde3;

    public VerleihServiceImplTest()
    {
        _datum = new Datum(3, 4, 2009);
        KundenstammService kundenstamm = new KundenstammServiceImpl(
                new ArrayList<Kunde>());
        _kunde = new Kunde(new Kundennummer(123456), "ich", "du");

        _vormerkkunde = new Kunde(new Kundennummer(666999), "paul", "panter");

        _kunde1 = new Kunde(new Kundennummer(212223), "Frank", "Rost");
        _kunde2 = new Kunde(new Kundennummer(212223), "Frank", "Rost");
        _kunde3 = new Kunde(new Kundennummer(212223), "Frank", "Rost");

        kundenstamm.fuegeKundenEin(_kunde);
        kundenstamm.fuegeKundenEin(_vormerkkunde);
        kundenstamm.fuegeKundenEin(_kunde1);
        kundenstamm.fuegeKundenEin(_kunde2);
        kundenstamm.fuegeKundenEin(_kunde3);
        MedienbestandService medienbestand = new MedienbestandServiceImpl(
                new ArrayList<Medium>());
        Medium medium = new CD("CD1", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD2", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD3", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD4", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        _medienListe = medienbestand.getMedien();
        _service = new VerleihServiceImpl(kundenstamm, medienbestand,
                new ArrayList<Verleihkarte>());
    }

    @Test
    public void testeNachInitialisierungIstNichtsVerliehen() throws Exception
    {
        assertTrue(_service.getVerleihkarten()
            .isEmpty());
        assertFalse(_service.istVerliehen(_medienListe.get(0)));
        assertFalse(_service.sindAlleVerliehen(_medienListe));
        assertTrue(_service.sindAlleNichtVerliehen(_medienListe));
    }

    @Test
    public void testeVerleihUndRueckgabeVonMedien() throws Exception
    {
        // Lege eine Liste mit nur verliehenen und eine Liste mit ausschließlich
        // nicht verliehenen Medien an
        List<Medium> verlieheneMedien = _medienListe.subList(0, 2);
        List<Medium> nichtVerlieheneMedien = _medienListe.subList(2, 4);
        _service.verleiheAn(_kunde, verlieheneMedien, _datum);

        // Prüfe, ob alle sondierenden Operationen für das Vertragsmodell
        // funktionieren
        assertTrue(_service.istVerliehen(verlieheneMedien.get(0)));
        assertTrue(_service.istVerliehen(verlieheneMedien.get(1)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(0)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(1)));
        assertTrue(_service.sindAlleVerliehen(verlieheneMedien));
        assertTrue(_service.sindAlleNichtVerliehen(nichtVerlieheneMedien));
        assertFalse(_service.sindAlleNichtVerliehen(verlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(nichtVerlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(_medienListe));
        assertFalse(_service.sindAlleNichtVerliehen(_medienListe));
        assertTrue(_service.istVerliehenAn(_kunde, verlieheneMedien.get(0)));
        assertTrue(_service.istVerliehenAn(_kunde, verlieheneMedien.get(1)));
        assertFalse(
                _service.istVerliehenAn(_kunde, nichtVerlieheneMedien.get(0)));
        assertFalse(
                _service.istVerliehenAn(_kunde, nichtVerlieheneMedien.get(1)));
        assertTrue(_service.sindAlleVerliehenAn(_kunde, verlieheneMedien));
        assertFalse(
                _service.sindAlleVerliehenAn(_kunde, nichtVerlieheneMedien));

        // Prüfe alle sonstigen sondierenden Methoden
        assertEquals(2, _service.getVerleihkarten()
            .size());

        _service.nimmZurueck(verlieheneMedien, _datum);
        // Prüfe, ob alle sondierenden Operationen für das Vertragsmodell
        // funktionieren
        assertFalse(_service.istVerliehen(verlieheneMedien.get(0)));
        assertFalse(_service.istVerliehen(verlieheneMedien.get(1)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(0)));
        assertFalse(_service.istVerliehen(nichtVerlieheneMedien.get(1)));
        assertFalse(_service.sindAlleVerliehen(verlieheneMedien));
        assertTrue(_service.sindAlleNichtVerliehen(nichtVerlieheneMedien));
        assertTrue(_service.sindAlleNichtVerliehen(verlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(nichtVerlieheneMedien));
        assertFalse(_service.sindAlleVerliehen(_medienListe));
        assertTrue(_service.sindAlleNichtVerliehen(_medienListe));
        assertTrue(_service.getVerleihkarten()
            .isEmpty());
    }

    @Test
    public void testVerleihEreignisBeobachter() throws ProtokollierException
    {
        final boolean[] ereignisse = new boolean[1];
        ereignisse[0] = false;
        ServiceObserver beobachter = new ServiceObserver()
        {
            @Override
            public void reagiereAufAenderung()
            {
                ereignisse[0] = true;
            }
        };
        _service.verleiheAn(_kunde,
                Collections.singletonList(_medienListe.get(0)), _datum);
        assertFalse(ereignisse[0]);

        _service.registriereBeobachter(beobachter);
        _service.verleiheAn(_kunde,
                Collections.singletonList(_medienListe.get(1)), _datum);
        assertTrue(ereignisse[0]);

        _service.entferneBeobachter(beobachter);
        ereignisse[0] = false;
        _service.verleiheAn(_kunde,
                Collections.singletonList(_medienListe.get(2)), _datum);
        assertFalse(ereignisse[0]);
    }

    @Test
    public void testVorgemerktesMediumIstVorgemerkt() {
        List<Medium> vormerkMedien = _medienListe.subList(0, 2);
        _service.merkeVor(_kunde, vormerkMedien);
        for (Medium medium : vormerkMedien) {
            assertEquals(_service.getVormerker(medium, 0), _kunde);
        }
    }

    @Test
    public void testRichtigeReihenfolge() {
        List<Medium> vormerkMedien = _medienListe.subList(0, 1);
        Medium medium = _medienListe.get(0);

        _service.merkeVor(_kunde, vormerkMedien);
        _service.merkeVor(_kunde1, vormerkMedien);
        _service.merkeVor(_kunde2, vormerkMedien);

        assertEquals(_service.getVormerker(medium, 0), _kunde);
        assertEquals(_service.getVormerker(medium, 1), _kunde1);
        assertEquals(_service.getVormerker(medium, 2), _kunde2);
    }

    @Test
    public void testMaxDreiVormerker() {
        Medium medium = _medienListe.get(0);
        List<Medium> vormerkMedien = _medienListe.subList(0, 1);

        _service.merkeVor(_kunde, vormerkMedien);
        _service.merkeVor(_kunde1, vormerkMedien);
        _service.merkeVor(_kunde2, vormerkMedien);

        assertFalse(_service.istVormerkenMoeglich(_kunde3, vormerkMedien));
    }

    @Test
    public void testNurObersterVormerkerKannAusleihen() {
        Medium medium = _medienListe.get(0);
        List<Medium> vormerkMedien = _medienListe.subList(0, 1);


        _service.merkeVor(_kunde, vormerkMedien);
        _service.merkeVor(_kunde1, vormerkMedien);
        _service.merkeVor(_kunde2, vormerkMedien);

        assertTrue(_service.istVerleihenMoeglich(_kunde, vormerkMedien));
        assertFalse(_service.istVerleihenMoeglich(_kunde1, vormerkMedien));
        assertFalse(_service.istVerleihenMoeglich(_kunde2, vormerkMedien));
    }

    @Test
    public void testVormerkkarteLeerInitialisiert() {
        for  (Medium medium : _medienListe) {
            assertEquals(_service.getVormerker(medium, 0), null);
            assertEquals(_service.getVormerker(medium, 1), null);
            assertEquals(_service.getVormerker(medium, 2), null);
        }
    }

    @Test
    public void testVerleihenUpdatedVormerker() throws ProtokollierException {
        //platz 2 und 3 rücken nach, platz 3 wird frei, medium ist verliehen an ehem platz 1
        Medium medium = _medienListe.get(0);
        List<Medium> vormerkMedien = _medienListe.subList(0, 1);
        _service.merkeVor(_kunde, vormerkMedien);
        _service.merkeVor(_kunde1, vormerkMedien);
        _service.merkeVor(_kunde2, vormerkMedien);

        _service.verleiheAn(_kunde, vormerkMedien, _datum);

        assertEquals(_service.getVormerker(medium, 0), _kunde1);
        assertEquals(_service.getVormerker(medium, 1), _kunde2);
        assertEquals(_service.getVormerker(medium, 2), null);
    }

}
