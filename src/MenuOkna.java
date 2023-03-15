import javax.swing.*;

public class MenuOkna extends JMenuBar{
    JMenu plik = new JMenu("Plik");
    JMenuItem otworzPlik = new JMenuItem("Otworz plik");
    JMenuItem zapiszPlik = new JMenuItem("Zapisz plik");
    JMenuItem  zakoncz = new JMenuItem("Zakończ");
    JMenuItem lewCzysc = new JMenuItem("Wyczyść lewy");
    JMenuItem lewZad1 = new JMenuItem("Kopiuj");
    JMenuItem praCzysc = new JMenuItem("Wyczyść prawy");
    JMenu szare = new JMenu("Obrazy szaroodcieniowe");

    JMenuItem czerwony = new JMenuItem("wg R");

    JMenuItem zielony = new JMenuItem("wg G");

    JMenuItem niebieski = new JMenuItem("wg B");

    JMenuItem srednia = new JMenuItem("średnia RGB");

    JMenuItem YUV = new JMenuItem("Model YUV");
    JMenu kolory = new JMenu("Opeeracje na obrazach kolorowych");
    JMenuItem jasnoc = new JMenuItem("Zmiana jasności");
    JMenuItem kontrast = new JMenuItem("Zmiana kontrastu");
    JMenuItem negacja = new JMenuItem("Negacja obrazu");
    JMenuItem zakres = new JMenuItem("Zmiana zakresu jasnosci");
    public MenuOkna()
    {
        //menu Plik
        plik.add(otworzPlik);
        plik.add(zapiszPlik);
        plik.add(lewCzysc);
        plik.add(lewZad1);
        plik.add(praCzysc);
        //linia oddzielająca JMenuItem
        plik.add(new JSeparator());
        plik.add(zakoncz);
        add(plik);

        //menu szare
        szare.add(czerwony);
        szare.add(zielony);
        szare.add(niebieski);
        szare.add(srednia);
        szare.add(YUV);
        add(szare);

        //menu kolory
        kolory.add(jasnoc);
        kolory.add(kontrast);
        kolory.add(negacja);
        kolory.add(zakres);
        add(kolory);

    }
}
