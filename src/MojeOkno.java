import java.io.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
public class MojeOkno  extends JFrame implements ActionListener {
    //Tworzymy panele okna głównego
    PanelGraficzny lewy = new PanelGraficzny();
    PanelGraficzny prawy = new PanelGraficzny();
    //Tworzymy menu okna
    MenuOkna menu = new MenuOkna();
    //scieżka dostępu wraz z nazwą pliku
    String sciezkaDoPlik;

    //Konstruktor
    public MojeOkno() {
        //wywolanie konstruktora klasy nadrzednej (JFrame)
        super("Grafika komputerowa");
        //ustawienie standardowej akcji po naciśnięciu przycisku zamkniecia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //blokada zmiany rozmiaru okna
        setResizable(false);
        //rozmieszczenie elementow - menadzer rozkladu
        //FlowLayout ustawia elementy jeden za drugim
        //w tym przypadku dodatkowo wysrodkowane na ekranie, z odstępem w pionie i poziomie
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        //ustawienie stworzonego menu
        setJMenuBar(menu);
        //dodanie paneli
        add(lewy);
        add(prawy);
        //przypisanie obsługi akcji
        ustawNasluchZdarzen();
        dopasujSieDoZawartosci();
        //wyswietlenie naszej ramki
        setVisible(true);
    }

    private void ustawNasluchZdarzen() {
        //czyli kto kogo podsłuchuje
        menu.otworzPlik.addActionListener(this);
        menu.zapiszPlik.addActionListener(this);
        menu.zakoncz.addActionListener(this);

        menu.lewCzysc.addActionListener(this);
        menu.lewZad1.addActionListener(this);

        menu.praCzysc.addActionListener(this);

        menu.czerwony.addActionListener(this);
        menu.zielony.addActionListener(this);
        menu.niebieski.addActionListener(this);
        menu.srednia.addActionListener(this);
        menu.YUV.addActionListener(this);


        menu.jasnoc.addActionListener(this);
        menu.kontrast.addActionListener(this);
        menu.negacja.addActionListener(this);
        menu.zakres.addActionListener(this);
    }

    //METODY OBSŁUGI ZDARZEŃ DLA INTERFEJSÓW
    //ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {

        //pobieramy etykietę z przycisku
        String label = e.getActionCommand();
        if (label.equals("Otworz plik")) {
            otworzPlik();
        } else if (label.equals("Zapisz plik")) {
            zapiszPlik();
        } else if (label.equals("Zakończ")) {
            System.exit(0);
        } else if (label.equals("Wyczyść lewy")) {
            lewy.wyczysc();
        } else if (label.equals("Kopiuj")) {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();
            prawy.czyPusty = false;
            //skopiuj prawy panel na bazie lewego
            lewy.kopiuj(prawy.plotno);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();
        } else if (label.equals("Wyczyść prawy")) {
            prawy.czyPusty = true;
            prawy.wyczysc();
        } else if (label.equals("Zmiana jasności")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();

            String input = JOptionPane.showInputDialog("Proszę podać zmianę jasnosci");

            double jasnosc = 0;

            try {
                jasnosc = Double.parseDouble(input);
            } catch (NumberFormatException wrongInput) {
                JOptionPane.showMessageDialog(null, "Wartość musi być liczbą,",
                        "Alert", JOptionPane.ERROR_MESSAGE);
            }


            prawy.jasność(lewy.plotno, jasnosc);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("Zmiana kontrastu")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();

            String input = JOptionPane.showInputDialog("Proszę podać zmianę kotrastu");

            double kontrast = 1;

            try {
                kontrast = Double.parseDouble(input);
            } catch (NumberFormatException wrongInput) {
                JOptionPane.showMessageDialog(null, "Wartość musi być liczbą,",
                        "Alert", JOptionPane.ERROR_MESSAGE);
            }


            prawy.kontrast(lewy.plotno, kontrast);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("Negacja obrazu")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();


            prawy.negacja(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("Zmiana zakresu jasnosci")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();


            prawy.zmianaZakresuJasności(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();
        } else if (label.equals("wg R")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();

            prawy.szaryCzerwony(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("wg G")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();


            prawy.szaryZieloy(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("wg B")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();

            prawy.szaryNiebieski(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("średnia RGB")) {

            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();

            prawy.szarysrednia(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("Model YUV")) {
            int w = prawy.plotno.getWidth();
            int h = prawy.plotno.getHeight();

            prawy.modelYUV(lewy.plotno);
            prawy.czyPusty = false;

            if (w != prawy.plotno.getWidth() || h != prawy.plotno.getHeight())
                dopasujSieDoZawartosci();
        }
    }

    //akcja w przypadku wyboru "otwórz plik z menu"
    private void otworzPlik() {

        //okno dialogowe do wyboru pliku graficznego
        JFileChooser otworz = new JFileChooser();
        //zdefiniowanie filtra dla wybranych typu plików
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("JPG & BMP & PNG Images", "jpg", "bmp", "png");
        //ustawienie filtra dla JFileChooser
        otworz.setFileFilter(filtr);
        //wyświetlenie okna dialogowego wyboru pliku
        int wynik = otworz.showOpenDialog(this);
        //analiza rezultatu zwróconego przez okno dialogowe
        if (wynik == JFileChooser.APPROVE_OPTION) {
            //wyłuskanie ścieżki do wybranego pliku
            sciezkaDoPlik = otworz.getSelectedFile().getPath();
            int w = lewy.plotno.getWidth();
            int h = lewy.plotno.getHeight();
            //wczytanie pliku graficznego na lewy panel
            lewy.wczytajPlikGraficzny(sciezkaDoPlik);
            if (w != lewy.plotno.getWidth() || h != lewy.plotno.getHeight())
                dopasujSieDoZawartosci();
        }
    }

    private void zapiszPlik() {

        //okno dialogowe do wyboru pliku graficznego
        JFileChooser zapisz;
        //otwarcie JFileChoosera w tym samym katalogu, z którego wczytano plik
        if (sciezkaDoPlik != null)
            zapisz = new JFileChooser(sciezkaDoPlik);
        else
            zapisz = new JFileChooser();
        //zdefiniowanie filtra dla wybranych typu plików
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("JPG & BMP & PNG Images", "jpg", "bmp", "png");
        //ustawienie filtra dla JFileChooser
        zapisz.setFileFilter(filtr);
        //wyświetlenie okna dialogowego wyboru pliku
        int wynik = zapisz.showSaveDialog(this);
        //analiza rezultatu zwróconego przez okno dialogowe
        if (wynik == JFileChooser.APPROVE_OPTION) {
            //wyłuskanie ścieżki do wybranego pliku
            sciezkaDoPlik = zapisz.getSelectedFile().getPath();
            //JOptionPane.showMessageDialog(null,"Blad odczytu pliku: " + sciezkaDoPlik);
            prawy.zapiszPlikGraficzny(sciezkaDoPlik);
        }
    }

    private void dopasujSieDoZawartosci() {
        //dostosowanie okna do zawartości
        pack();
        //wyśrodkowanie ramki
        setLocationRelativeTo(null);
    }
}
