package utils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pinta el contorno con un borde redondeado.
        RoundRectangle2D roundedBorderShape = new RoundRectangle2D.Float(0, 0,
                getWidth()-0.2f,getHeight()-0.2f, radius*2,radius*2);

        // Dibuja el borde alrededor de la forma redondeada.
        g.setColor(getBackground());
        ((Graphics2D)g).draw(roundedBorderShape);
        g.setClip(roundedBorderShape);
        setOpaque(false);
        super.paint(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        RoundRectangle2D roundedBorderShape = new RoundRectangle2D.Float(0.5f, 0.5f, getWidth()-1, getHeight()-1, radius*2,radius*2);
        g2.draw(roundedBorderShape);
        g2.dispose();
    }
}