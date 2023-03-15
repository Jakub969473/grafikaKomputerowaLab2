import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PanelGraficzny extends JPanel {

    //zmienna pokazująca czy prawy panel jest pusty
    boolean czyPusty=true;

    //obiekt do przechowywania grafiki
    BufferedImage plotno;
    //konstruktor
    public PanelGraficzny(){
        super();
        setLayout(new GridLayout(2,1));
        ustawRozmiar(new Dimension(400,400));
        wyczysc();
    }
    //wczytanie obrazka z pliku
    public void wczytajPlikGraficzny(String sciezka){
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plikGraficzny = new File(sciezka);
        //próba odczytania pliku graficznego do bufora
        try {
            plotno = ImageIO.read(plikGraficzny);
            //odczytanie rozmiaru obrazka
            Dimension rozmiar = new Dimension(plotno.getWidth(), plotno.getHeight());
            //ustalenie rozmiaru panelu zgodnego z rozmiarem obrazka
            setPreferredSize(rozmiar);
            setMaximumSize(rozmiar);
            //ustalenie obramowania
            setBorder(BorderFactory.createLineBorder(Color.black));
            repaint();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null,"Blad odczytu pliku: " + sciezka);
            e.printStackTrace();
        }
    }

    public void zapiszPlikGraficzny(String sciezka){
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plikGraficzny = new File(sciezka);
        //próba zapisania pliku graficznego z bufora
        try {
            if(plotno != null)
            {
                if(!ImageIO.write(plotno, sciezka.substring(sciezka.lastIndexOf('.') + 1), new File(sciezka)))
                {
                    JOptionPane.showMessageDialog(null,"Nie udało sie zapisać pliku w " + sciezka);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Brak obrazu do zapisania");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Nie udało sie zapisać pliku w " + sciezka);
        }
    }

    public void wyczysc(){
        //wyrysowanie białego tła
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        //ustalenie obramowania
        setBorder(BorderFactory.createLineBorder(Color.black));
        repaint();
    }

    public void ustawRozmiar(Dimension r){
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
        setMaximumSize(r);
    }

    public void kopiuj(BufferedImage wejscie){
        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                czerw = ci.getRed();
                ziel = ci.getGreen();
                nieb = ci.getBlue();

                //jak ustawić kolor piksala w obiekcie BufferedImage
                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }
    //przesłonięta metoda paintComponent z klasy JPanel do rysowania
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego płótna na panelu
        g2d.drawImage(plotno, 0, 0, this);
    }
    //metoda zmieniająca janość obrazka
    public void jasność(BufferedImage wejscie,double jas) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(wejscie.getRGB(i,j));
                czerw = (int)(ci.getRed() + jas);
                ziel = (int)(ci.getGreen() + jas);
                nieb = (int)(ci.getBlue() + jas);

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć
                czerw=obcięcie(czerw);
                ziel=obcięcie(ziel);
                nieb=obcięcie(nieb);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }

    public void kontrast(BufferedImage wejscie,double kontr) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                //obliczenie wartości koloru według wzoru i zaookrąglenie do całości
                ci = new Color(wejscie.getRGB(i,j));
                czerw = (int)(kontr * ci.getRed());
                ziel =(int)(kontr * ci.getGreen());
                nieb = (int)(kontr * ci.getBlue());

                //kolor może mieć wartość od 0 do 255, wartości pozatym zakresem trzeba obciąć
                czerw=obcięcie(czerw);
                ziel=obcięcie(ziel);
                nieb=obcięcie(nieb);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());
            }
        repaint();
    }

    private int obcięcie(int kolor) {

        if(kolor>255){
            return 255;
        }else if(kolor<0){
            return 0;
        }else{
            return kolor;
        }
    }

    public void zmianaZakresuJasności(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));

        Color ci;
        int czerw, ziel, nieb;

        int maxNieb=0, minNieb=255, maxCzerw=0, minCzerw=255, maxZiel=0, minZiel=255;

        //znalazienie max i min kolorów
        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                ci = new Color(wejscie.getRGB(i,j));
                czerw =ci.getRed();
                ziel =ci.getGreen();
                nieb = ci.getBlue();

                //znalezienie maks wartosci każdego koloru
                maxNieb = czyMax(maxNieb,nieb);
                maxZiel = czyMax(maxZiel,ziel);
                maxCzerw = czyMax(maxCzerw,czerw);

                //znalezienie min wartości każdego koloru
                minNieb = czyMin(minNieb,nieb);
                minZiel = czyMin(minZiel,ziel);
                minCzerw = czyMin(minCzerw,czerw);

            }

        //przypisanie kolorów
        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                ci = new Color(wejscie.getRGB(i,j));
                czerw =ci.getRed()-minCzerw;
                ziel =ci.getGreen()-minZiel;
                nieb = ci.getBlue()-minNieb;

                czerw = (255*czerw)/(maxCzerw-minCzerw);
                nieb = (255*nieb)/(maxNieb-minNieb);
                ziel = (255*ziel)/(maxZiel-minZiel);

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());

            }

        repaint();

    }

    private int czyMin(int a, int b) {

        if(a>b){
            return b;
        }else{
            return a;
        }
    }

    private int czyMax(int a, int b) {

        if(a<b){
            return b;
        }else{
            return a;
        }
    }


    public void negacja(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));

        Color ci;
        int czerw, ziel, nieb;

        int maxNieb=0, maxCzerw=0, maxZiel=0;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                ci = new Color(wejscie.getRGB(i,j));
                czerw =ci.getRed();
                ziel =ci.getGreen();
                nieb = ci.getBlue();

                maxNieb = czyMax(maxNieb,nieb);
                maxZiel = czyMax(maxZiel,ziel);
                maxCzerw = czyMax(maxCzerw,czerw);

            }

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){

                ci = new Color(wejscie.getRGB(i,j));
                czerw =maxCzerw-ci.getRed();
                ziel =maxZiel-ci.getGreen();
                nieb = maxNieb-ci.getBlue();

                plotno.setRGB(i,j,new Color(czerw,ziel,nieb).getRGB());

            }

        repaint();
    }

    //obraz szaroodciwniowy wg wartości czerwonego
    public void szaryCzerwony(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                czerw = ci.getRed();

                plotno.setRGB(i,j,new Color(czerw,czerw,czerw).getRGB());
            }
        repaint();
    }

    //obraz szaroodciwniowy wg wartości zielonego
    public void szaryZieloy(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int ziel;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                ziel = ci.getGreen();

                plotno.setRGB(i,j,new Color(ziel,ziel,ziel).getRGB());
            }
        repaint();
    }

    //obraz szaroodciwniowy wg wartości niebieskiego
    public void szaryNiebieski(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int nieb;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                nieb = ci.getBlue();

                plotno.setRGB(i,j,new Color(nieb,nieb,nieb).getRGB());
            }
        repaint();
    }

    //obraz szaroodciwniowy wg średniej
    public void szarysrednia(BufferedImage wejscie) {

        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb,sre;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                czerw = ci.getRed();
                ziel = ci.getGreen();
                nieb = ci.getBlue();

                //obliczanie wartości koloru wg średniej ważonej 0.299 *R + 0.587*G + 0.114*B
                sre=(int)(czerw+ziel+nieb)/3;

                plotno.setRGB(i,j,new Color(sre,sre,sre).getRGB());
            }
        repaint();
    }

    //metoda przetwarzająca obraz kolorowy na obraz szaroodcienowy według modelu YUV
    public void modelYUV(BufferedImage wejscie){
        ustawRozmiar(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        int czerw, ziel, nieb,yuv;

        for(int i=0; i<wejscie.getWidth(); i++)
            for(int j=0; j<wejscie.getHeight(); j++){
                //jak dostać się do składowych poszczególnych pikseli w obiekcie BufferedImage
                ci = new Color(wejscie.getRGB(i,j));
                czerw = ci.getRed();
                ziel = ci.getGreen();
                nieb = ci.getBlue();

                //obliczanie wartości koloru wg średniej ważonej 0.299 *R + 0.587*G + 0.114*B
                yuv=(int)(0.299*czerw+0.587*ziel+0.114*nieb);

                plotno.setRGB(i,j,new Color(yuv,yuv,yuv).getRGB());
            }
        repaint();
    }
}
