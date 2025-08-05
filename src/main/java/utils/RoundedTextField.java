package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    private int radius;
    private int padding;

    public RoundedTextField(int columns, int radius, int padding) {
        super(columns);
        this.radius = radius;
        this.padding = padding;
        setBackground(Color.white);
        setOpaque(true);
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    public RoundedTextField(String text, int radius) {
        super(text);
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = padding;
        int y = padding;
        int width = getWidth() - padding * 2;
        int height = getHeight() - padding * 2;

        // Pinta el contorno con un borde redondeado.
        RoundRectangle2D roundedBorderShape = new RoundRectangle2D.Float(x, y, width,
                height, radius * 2, radius * 2);

        // Dibuja el borde alrededor de la forma redondeada.
        g2d.setColor(getBackground());
        ((Graphics2D) g).draw(roundedBorderShape);
        g2d.setClip(roundedBorderShape);
        setOpaque(false);

        // Ajusta el área de dibujo para el texto
        Shape textClip = g2d.getClip();
        g2d.clipRect(x, y, width, height);
        super.paintComponent(g2d);
        g2d.setClip(textClip);

        g2d.dispose();
    }

}
