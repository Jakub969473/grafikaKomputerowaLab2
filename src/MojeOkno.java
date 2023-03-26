import java.io.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class MojeOkno  extends JFrame implements ActionListener {
    //Tworzymy panele okna głównego
    PanelGraficzny wejscie1 = new PanelGraficzny();
    PanelGraficzny wejscie2 = new PanelGraficzny();
    PanelGraficzny wyjscie = new PanelGraficzny();

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
        add(wejscie1);
        add(wejscie2);
        add(wyjscie);
        //przypisanie obsługi akcji
        ustawNasluchZdarzen();
        dopasujSieDoZawartosci();
        //wyswietlenie naszej ramki
        setVisible(true);
    }

    private void ustawNasluchZdarzen() {
        //czyli kto kogo podsłuchuje
        menu.wczytajLewy.addActionListener(this);
        menu.wczytajPrawy.addActionListener(this);
        menu.zapiszPlik.addActionListener(this);
        menu.zakoncz.addActionListener(this);
        menu.lewWECzysc.addActionListener(this);
        menu.praWeCzysc.addActionListener(this);
        menu.wejscieCzysc.addActionListener(this);

        menu.doWejscia1.addActionListener(this);
        menu.doWejscia2.addActionListener(this);

        menu.dodawanie.addActionListener(this);
        menu.odejmowanie.addActionListener(this);
        menu.mnożenie.addActionListener(this);
        menu.dzielenie.addActionListener(this);

    }

    //METODY OBSŁUGI ZDARZEŃ DLA INTERFEJSÓW
    //ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {

        //pobieramy etykietę z przycisku
        String label = e.getActionCommand();
        if (label.equals("Wczytaj lewy")) {
            wczytajLewy();
        }else if (label.equals("Wczytaj prawy")) {
            wczytajPrawy();
        } else if (label.equals("Zapisz plik")) {
            zapiszPlik();
        } else if (label.equals("Zakończ")) {
            System.exit(0);
        } else if (label.equals("Wyczyść lewe wejscie")) {

            wejscie1.wyczysc();

        } else if (label.equals("Wyczyść prawe wejscie")) {

            wejscie2.wyczysc();

        } else if (label.equals("Wyczyść wyjscie")) {

            wyjscie.wyczysc();

        } else if (label.equals("wyjscia do wejscia 1")) {

            int w = wyjscie.plotno.getWidth();
            int h = wyjscie.plotno.getHeight();

            //skopiuj prawy panel na bazie lewego
            wejscie1.kopiuj(wyjscie.plotno);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(w != wyjscie.plotno.getWidth() || h != wyjscie.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("wyjscia do wejscia 2")) {

            int w = wyjscie.plotno.getWidth();
            int h = wyjscie.plotno.getHeight();

            //skopiuj prawy panel na bazie lewego
            wejscie2.kopiuj(wyjscie.plotno);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(w != wyjscie.plotno.getWidth() || h != wyjscie.plotno.getHeight())
                dopasujSieDoZawartosci();

        } else if (label.equals("dodawanie")) {

            int w = wyjscie.plotno.getWidth();
            int h = wyjscie.plotno.getHeight();

            String input=JOptionPane.showInputDialog("Proszę podać alfe");

            double alfa=0;

            try {
                alfa= Double.parseDouble(input);
            }catch(NumberFormatException wrongInput){
                JOptionPane.showMessageDialog(null,"Wartość musi być liczbą,",
                        "Alert",JOptionPane.ERROR_MESSAGE);
            }

            //skopiuj prawy panel na bazie lewego
            wyjscie.dodawanie(wejscie1.plotno,wejscie2.plotno,alfa);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(w != wyjscie.plotno.getWidth() || h != wyjscie.plotno.getHeight())
                dopasujSieDoZawartosci();


        } else if (label.equals("odejmowanie")) {

            int w = wyjscie.plotno.getWidth();
            int h = wyjscie.plotno.getHeight();

            //skopiuj prawy panel na bazie lewego
            wyjscie.odejmowanie(wejscie1.plotno,wejscie2.plotno);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(w != wyjscie.plotno.getWidth() || h != wyjscie.plotno.getHeight())
                dopasujSieDoZawartosci();


        } else if (label.equals("mnożenie")) {

            int w = wyjscie.plotno.getWidth();
            int h = wyjscie.plotno.getHeight();

            //skopiuj prawy panel na bazie lewego
            wyjscie.mnożenie(wejscie1.plotno,wejscie2.plotno);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(w != wyjscie.plotno.getWidth() || h != wyjscie.plotno.getHeight())
                dopasujSieDoZawartosci();


        }else if (label.equals("dzielenie")) {

            int w = wyjscie.plotno.getWidth();
            int h = wyjscie.plotno.getHeight();

            //skopiuj prawy panel na bazie lewego
            wyjscie.dzielenie(wejscie1.plotno,wejscie2.plotno);
            //dopasowanie zawartości w przypadku zmiany wymiarów
            if(w != wyjscie.plotno.getWidth() || h != wyjscie.plotno.getHeight())
                dopasujSieDoZawartosci();
        }

    }

    //akcja w przypadku wyboru "otwórz plik z menu"
    private String otworzPlik() {

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
        }

        return sciezkaDoPlik;
    }

    private void wczytajLewy(){

        sciezkaDoPlik=otworzPlik();

        int w = wejscie1.plotno.getWidth();
        int h = wejscie1.plotno.getHeight();

        wejscie1.wczytajPlikGraficzny(sciezkaDoPlik);
        if (w != wejscie1.plotno.getWidth() || h != wejscie1.plotno.getHeight())
            dopasujSieDoZawartosci();

    }

    private void wczytajPrawy(){

        sciezkaDoPlik=otworzPlik();

        int w = wejscie2.plotno.getWidth();
        int h = wejscie2.plotno.getHeight();

        wejscie2.wczytajPlikGraficzny(sciezkaDoPlik);
        if (w != wejscie2.plotno.getWidth() || h != wejscie2.plotno.getHeight())
            dopasujSieDoZawartosci();

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
            wyjscie.zapiszPlikGraficzny(sciezkaDoPlik);
        }
    }

    private void dopasujSieDoZawartosci() {
        //dostosowanie okna do zawartości

        setLocationRelativeTo(null);

        pack();
        //wyśrodkowanie ramki
    }
}