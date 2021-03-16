package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.filechooser.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * This class is dedicated to let the user explore different fractals and their areas*
 */
public class FractalExplorer {
    private JButton saveBut;
    private JButton resBut;
    private JComboBox comboBox;

    private final int displaySize;
    private int rowsRemaining;
    private final JImageDisplay display;
    private  FractalGenerator fractalGenerator;
    private final Rectangle2D.Double range;

    /* Class constructor */
    public FractalExplorer(int size) {
        displaySize = size;
        /* Initialize fractalGenerator and range objects */
        fractalGenerator = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractalGenerator.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }
    /* Generate and displays GUI*/
    public void createAndShowGUI(){
        /* Set the frame to use a BorderLayout for its contents */
        display.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Проверо4ка фракталь4иков by gumobu");
        /* Put the image displaying object in the center position */
        frame.add(display, BorderLayout.CENTER);
        /* Create a reset button */
        resBut = new JButton("Reset");
        /* ButtonHandler instance on the "reset" button*/
        ButtonHandler reset = new ButtonHandler();
        resBut.addActionListener(reset);
        /* MouseHandler instance on the fractal displaying component */
        Mouse click = new Mouse();
        display.addMouseListener(click);
        /* Set the frame default close operation as exit */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* Set up a combo box */
        comboBox = new JComboBox();
        /* Add all fractal types objects to the combo box */
        FractalGenerator mandelbrotFract = new Mandelbrot();
        comboBox.addItem(mandelbrotFract);
        FractalGenerator tricornFract = new Tricorn();
        comboBox.addItem(tricornFract);
        FractalGenerator burningShipFract = new BurningShip();
        comboBox.addItem(burningShipFract);
        /* ButtonHandler instance on the combo box */
        ButtonHandler fractalChoose = new ButtonHandler();
        comboBox.addActionListener(fractalChoose);
        /* Create a new JPanel object, add a JLabel and a JComboBox objects to it,
         then and add the panel to the frame in the NORTH position */
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Выберите фрактал:");
        panel.add(label);
        panel.add(comboBox);
        frame.add(panel, BorderLayout.NORTH);
        /* Create a save button, add it to a JPanel in the SOUTH position so as the reset button */
        saveBut = new JButton("Save");
        JPanel bottompanel = new JPanel();
        bottompanel.add(saveBut);
        bottompanel.add(resBut);
        frame.add(bottompanel, BorderLayout.SOUTH);
        /* ButtonHandler instance on the "save" button */
        ButtonHandler saveHandler = new ButtonHandler();
        saveBut.addActionListener(saveHandler);
        /* Set up contents of the frame, cause it to be visible, disallow window resizing */
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Displays the fractal. Loops through every pixel and sets its color depending on iterations number.
     * Then repaints JImageDisplay with all the new pixels
     */
    private void drawFractal(){
        enableUI(false);
        rowsRemaining = displaySize;
        for (int x=0; x<displaySize; x++){
            FractalWorker row = new FractalWorker(x);
            row.execute();
        }
    }

    /*Enables or disables UI controls*/
    private void enableUI(boolean value){
        comboBox.setEnabled(value);
        resBut.setEnabled(value);
        saveBut.setEnabled(value);
    }
    /**
     * This inner class is dedicated to handle ActionListener events.
     */
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /* Gets the action source */
            String com = e.getActionCommand();
            /* If the command came from combo box displays a user chosen fractal*/
            if (e.getSource() instanceof JComboBox) {
                JComboBox source = (JComboBox) e.getSource();
                fractalGenerator = (FractalGenerator) source.getSelectedItem();
                fractalGenerator.getInitialRange(range);
                drawFractal();

            }
            /* If the command came from the reset button, reset a display and draw a fractal*/
            else if (com.equals("Reset")){
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
            /* If the command came from the "save" button, save the current fractal image */
            else if (com.equals("Save")) {
                /* Allow choosing a file to save the image to */
                JFileChooser fileChooser = new JFileChooser();
                /* Save only .PNG images */
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(extensionFilter);
                /* The fileChooser will allow .png only filenames */
                fileChooser.setAcceptAllFileFilterUsed(false);
                /* Pop up a "Save file" window */
                int userSelection = fileChooser.showSaveDialog(display);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    /* Get the file and its name */
                    java.io.File file = fileChooser.getSelectedFile();
                    String fileName = file.toString();
                    try {
                        BufferedImage displayImg = display.getImage();
                        javax.imageio.ImageIO.write(displayImg, "png", file);
                    }
                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                                exception.getMessage(), "Cannot Save Image",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * Class dedicated to handle MouseListener events
     */
    private class Mouse extends MouseAdapter
    {
        /*
         * Having received a mouse click method maps the pixel that was clicked and calls recenterAndZoomRange()
         * Then redraws the fractal */
        public void mouseClicked(MouseEvent e)
        {
            /* Get x coordinate of mouse click */
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            /* Get y coordinate of mouse click */
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y,
                    range.y + range.height, displaySize, y);
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

    /**
     * Class is dedicated to process the only single line of fractal
     * **/
    private class FractalWorker extends SwingWorker <Object, Object> {
        int yCoordinate;
        int[] RGBValues;

        /**
         * Class constructor
         **/
        private FractalWorker(int rowNum) {
            yCoordinate = rowNum;
        }

        /*Method is called in a background thread.
        Determines RGB values for every pixel in a single row */
        protected Object doInBackground() {
            RGBValues = new int[displaySize];
            for (int iter = 0; iter < RGBValues.length; iter++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, iter);
                double yCoord = FractalGenerator.getCoord(range.y,
                        range.y + range.height, displaySize, yCoordinate);
                int iteration = fractalGenerator.numIterations(xCoord, yCoord);
                if (iteration == -1) {
                    RGBValues[iter] = 0;
                } else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int color = Color.HSBtoRGB(hue, 1f, 1f);
                    RGBValues[iter] = color;
                }
            }
            return null;
        }

        /*This method is called when the background task is completed.
         * Updates the display with new row*/
        protected void done() {
            for (int iter = 0; iter < RGBValues.length; iter++) {
                display.drawPixel(iter, yCoordinate, RGBValues[iter]);
            }
            display.repaint(0, 0, yCoordinate, displaySize, 1);

            // Decrement rows remaining.  If 0, call enableUI(true)
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }
    /**
     * Launches FractalExplorer
     */
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
