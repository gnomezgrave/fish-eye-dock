package gnomezgrave.fisheye.packages;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * FishEyeDock is a icon deck with the hover effect on the icons.<br/> It's
 * recommended to set <var>defultSize,zoomLevel,imageZoomMode</var> and
 * <var>animationSpeed</var> before you add elements.
 *
 * @version 1.0
 * @author Praneeth
 */
public class FishEyeDock extends JPanel {
    /*
     * private variables
     */

    private Vector<JLabel> elements = new Vector<JLabel>();
    private int defaultSize = 50;
    private int zoomLevel = 30;
    private int imageZoomMode = Image.SCALE_DEFAULT;
    private int animationSpeed = 5;
    private SwingWorker m_ex;
    private SwingWorker m_en;
    private Vector<ImageIcon[]> images;
    /*
     * public variables
     */
    /**
     * Icons in the FishEyeDock will be aligned horizontally.
     */
    public static final int HORIZONTAL_ALIGNMENT = 0;
    /**
     * Icons in the FishEyeDock will be aligned vertically.
     */
    public static final int VERTICAL_ALIGNMENT = 1;
    /**
     * Choose an image-scaling algorithm that gives higher priority to image
     * smoothness than scaling speed.
     *
     * @see java.awt.Image
     * @since JDK1.1
     */
    public static final int ZOOM_MODE_SMOOTH = Image.SCALE_SMOOTH;
    /**
     * Use the Area Averaging image scaling algorithm. The image object is free
     * to substitute a different filter that performs the same algorithm yet
     * integrates more efficiently into the image infrastructure supplied by the
     * toolkit.
     *
     * @see java.awt.Image
     * @since JDK1.1
     */
    public static final int ZOOM_MODE_AVERAGE = Image.SCALE_AREA_AVERAGING;
    /**
     * Choose an image-scaling algorithm that gives higher priority to scaling
     * speed than smoothness of the scaled image.
     *
     * @see java.awt.Image
     * @since JDK1.1
     */
    public static final int ZOOM_MODE_FAST = Image.SCALE_FAST;
    /**
     * Use the image scaling algorithm embodied in the
     * <code>ReplicateScaleFilter</code> class. The
     * <code>Image</code> object is free to substitute a different filter that
     * performs the same algorithm yet integrates more efficiently into the
     * imaging infrastructure supplied by the toolkit.
     *
     * @see java.awt.Image
     * @since JDK1.1
     */
    public static final int ZOOM_MODE_REPLICATE = Image.SCALE_REPLICATE;
    /**
     * Use the default image-scaling algorithm.
     *
     * @see java.awt.Image
     * @since JDK1.1
     */
    public static final int ZOOM_MODE_DEFAULT = Image.SCALE_DEFAULT;

    /**
     * Creates a new FishEyeDock with <var>alignments</var> as the parameter.
     *
     * @param alignment Alignment of the icons, either
     * <code>HORIZONTAL_ALIGNMENT</code> or
     * <code>VERTICAL_ALIGNMENT</code>
     */
    public FishEyeDock(int alignment) {
        super(new RelativeLayout(alignment, 5));
        init();
    }

    /**
     * Creates a new FishEyeDock with <var>alignments</var> and <var>gap</var>
     * between icons as parameters.
     *
     * @param alignment Alignment of the icons, either
     * <code>HORIZONTAL_ALIGNMENT</code> or
     * <code>VERTICAL_ALIGNMENT</code>
     * @param gap Gap between icons
     */
    public FishEyeDock(int alignment, int gap) {
        super(new RelativeLayout(alignment, gap));
        init();
    }

    /**
     * Creates a new FishEyeDock with default settings.<br/> <var>gap = 5</var>
     * <var>defaultSize = 30</var> <br/><var>zoomLevel =
     * 30</var><br/><var>animationSpeed = 5</var><br/> <var>zoomMode =
     * ZOOM_MODE_DEFAULT</var>
     */
    public FishEyeDock() {
        super(new RelativeLayout(HORIZONTAL_ALIGNMENT, 5));
        init();
    }
    //adds two empty slots and creates the images for corresponding elements.

    private void init() {
        addEmptySlot();
        addEmptySlot();
        images = new Vector<ImageIcon[]>();
    }

    // This will add an empty slot which is used to keep the icons' VerticalAlign=Meddle
    private void addEmptySlot() {
        JLabel ref = new JLabel();
        ImageIcon img = new ImageIcon(getClass().getResource("/gnomezgrave/fisheye/pics/empty.png"));
        ref.setIcon(new ImageIcon(img.getImage().getScaledInstance(1, defaultSize + zoomLevel, getImageZoomMode())));
        JPanel jp = new JPanel(new RelativeLayout(VERTICAL_ALIGNMENT));
        jp.setSize(jp.getWidth(), jp.getHeight() + zoomLevel);
        jp.add(ref);
        add(jp);
    }

    /**
     * This will add a separator to the dock, that will separate the last icon
     * and the next icon added.
     */
    public void addSeperator() {
        JLabel ref = new JLabel();
        ImageIcon img = new ImageIcon(getClass().getResource("/gnomezgrave/fisheye/pics/seperator.png"));
        ref.setIcon(new ImageIcon(img.getImage().getScaledInstance(2, defaultSize + zoomLevel / 2, getImageZoomMode())));
        JPanel jp = new JPanel(new RelativeLayout(VERTICAL_ALIGNMENT));
        jp.setSize(jp.getWidth(), jp.getHeight() + zoomLevel);
        jp.add(ref);
        add(jp, getComponentCount() - 1);
    }

    /**
     * Gives the <var>zoomLevel</var> of the FishEyeDock
     *
     * @return the zoomLevel
     */
    public int getZoomLevel() {
        return zoomLevel;
    }

    /**
     * This sets the zoomLevel of the FishEyeDock
     *
     * @param zoomLevel the zoomLevel to set, value between 1 and 100
     */
    public void setZoomLevel(int zoomLevel) {
        if (zoomLevel <= 100 && zoomLevel > 0) {
            this.zoomLevel = zoomLevel;
        } else {
            this.zoomLevel = 30;
        }
    }

    /**
     * This method will insert an icon to the dock.
     *
     * @param img ImageIcon for the icon
     * @param caption Caption of the icon
     * @param mouseAdapter MouseAdapter for the icon
     */
    public void insert(ImageIcon img, String caption, MouseAdapter mouseAdapter) {
        JLabel ref = new JLabel();
        ref.setToolTipText(caption);
        ref.setIcon(new ImageIcon(img.getImage().getScaledInstance(defaultSize, defaultSize, getImageZoomMode())));

        JPanel jp = new JPanel(new RelativeLayout(VERTICAL_ALIGNMENT));
        jp.setSize(jp.getWidth(), jp.getHeight() + zoomLevel);
        jp.add(ref);
        add(jp, getComponentCount() - 1);
        ImageIcon[] icons = new ImageIcon[zoomLevel];
        for (int i = 0; i < zoomLevel; i++) {
            icons[i] = new ImageIcon(img.getImage().getScaledInstance(defaultSize + i, defaultSize + i, imageZoomMode));
        }
        ref.addMouseListener(new myMouseEvent(ref, icons));
        ref.addMouseListener(mouseAdapter);
        images.add(icons);
        elements.add(ref);
    }

    /**
     * Gives initialSize of the icons in the dock.
     *
     * @return the defaultSize
     */
    public int getDefaultSize() {
        return defaultSize;
    }

    /**
     * @param defaultSize the defaultSize to set, minimum value is 20.
     */
    public void setDefaultSize(int defaultSize) {
        if (defaultSize >= 20) {
            this.defaultSize = defaultSize;
        } else {
            this.defaultSize = 30;
        }

    }

    /**
     * Gives the current zoom mode set to the dock.
     *
     * @return the imageZoomMode
     */
    public int getImageZoomMode() {
        return imageZoomMode;
    }

    /**
     * Sets the imageZoomMode for the dock. If invalid values is passed, the
     * default zoom mode (
     * <code>FishEyeDock.ZOOM_MODE_DEFAULT</code>) will be used.
     *
     * @param imageZoomMode the imageZoomMode to set
     */
    public void setImageZoomMode(int imageZoomMode) {
        this.imageZoomMode = imageZoomMode;
    }

    /**
     * Gives the <var>animationSpeed</var> dock is current using.
     *
     * @return the animationSpeed
     */
    public int getAnimationSpeed() {
        return (10 - animationSpeed / 2) - 1;
    }

    /**
     * Sets the animationSpeed for the dock, a value between 1 and 10.<br> 1 is
     * the minimum speed and 10 is the maximum speed. <br/>If the parameter is
     * out of the range, 5 will be set as the default speed.
     *
     * @param animationSpeed the animationSpeed to set
     */
    public void setAnimationSpeed(int animationSpeed) {
        if (animationSpeed > 10 || animationSpeed < 1) {
            animationSpeed = 10;
        }
        this.animationSpeed = (10 - animationSpeed + 1) * 2;
    }

    // <editor-fold defaultstate="collapsed" desc="Mouse Listener">
    private class myMouseEvent extends MouseAdapter {

        private JLabel lbl;
        private ImageIcon[] img;

        public myMouseEvent(JLabel lbl, ImageIcon[] img) {
            super();
            this.lbl = lbl;
            this.img = img;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            m_ex = new SwingWorker() {

                @Override
                protected synchronized Object doInBackground() throws Exception {
                    int limit = lbl.getWidth() - defaultSize;
                    for (int i = limit - 1; 0 <= i; i--) {
                        int x = lbl.getWidth();
                        int y = lbl.getHeight();
                        lbl.setSize(x - 1, y - 1);
                        lbl.setIcon(img[i]);
                        Thread.sleep(animationSpeed);
                    }

                    return new Object();
                }
            };
            m_ex.execute();

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            m_en = new SwingWorker() {

                @Override
                protected synchronized Object doInBackground() throws Exception {
                    int limit = defaultSize + zoomLevel - lbl.getWidth();
                    for (int i = 0; i < limit; i++) {
                        int x = lbl.getWidth();
                        int y = lbl.getHeight();
                        lbl.setSize(x + 1, y + 1);
                        lbl.setIcon(img[i]);
                        Thread.sleep(animationSpeed);
                    }
                    return new Object();

                }
            };
            m_en.execute();

        }
    }
    // </editor-fold>
}
