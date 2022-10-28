/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.HttpController;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.ServiceTicketsService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.controller.UserService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Category;
import pos_timesquare.model.Product;
import pos_timesquare.model.Receipt;
import pos_timesquare.model.Sale;
import pos_timesquare.model.ServiceTickets;
import pos_timesquare.model.TransactionHistory;
import pos_timesquare.model.User;
import pos_timesquare.model.Variants;
import static pos_timesquare.test.TestCategory.removeDuplicates;
import pos_timesquare.utils.HistogramPanel;
import pos_timesquare.utils.MultipleLinesChart;
import pos_timesquare.utils.RingChart;
import pos_timesquare.utils.WrapLayout;
import static pos_timesquare.view.Test.setupAutoComplete;

/**
 *
 * @author Acer
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    User user = new User();
    public static Product selectedProduct = new Product();
    
    public static HashMap<String, String> viewSelectedVariant;
    public static HashMap<String, List<String>> addProductVariants = new HashMap<>();
    public static Set<List<String>> variantCombination = null;
    public static List<Variants> productVariants = new ArrayList<>(); 
    public static HashMap<Integer, Variants> checkoutProduct = new HashMap<>();
    
    JPanel test;
    JToggleButton button = new JToggleButton();
    AbstractBorder circleBorder = new CircleBorder();
    
    public static JPanel tabPanel = new JPanel();
    public static JPanel selectedMenu;
    
    static String selectedCategoryType = "";
    static String selectedCategoryBrand = "";
    
    public static File selectedFile = null;
    
    Timer timer;
    float totalRevenue = 0;
    float totalServiceRevenue = 0;

    public static boolean closePopupActive = false;
    float totalSales;
    int totalOrders;
    float checkoutTotalPrice;
    
    HashMap<Integer, Integer> productTotalOrder = new HashMap<>();
    
    boolean isCashPayment = true;
    
    public class CircleBorder extends AbstractBorder{

        private Color color;
        private BasicStroke stroke = null;
        private RenderingHints hints;

        /** Constructor*/
        CircleBorder() {        
            color = Color.gray;//negro
            stroke = new BasicStroke(1);//grosor del borde
            hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        }

        CircleBorder( Color color, int value) {        
            this.color = color;
            stroke = new BasicStroke(value);     
            hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }        

        @Override
        public void paintBorder(Component c,Graphics g,int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g;
            Ellipse2D circle2D = new Ellipse2D.Double();
            if( stroke.getLineWidth()==0 )
            {
                circle2D.setFrameFromCenter( 
                        new Point(x+width/2,y+height/2),
                        new Point( width , height) 
                        );            
            }
            else
            {

                circle2D.setFrameFromCenter( 
                        new Point(x+width/2,y+height/2), 
                        new Point( width-(int)stroke.getLineWidth() , height-(int) stroke.getLineWidth())
                        );            
            }                

            Polygon pointer = new Polygon();
            Area area = new Area(circle2D);        
            area.add(new Area(pointer));
            g2.setRenderingHints(hints);


            Component parent  = c.getParent();
            if (parent!=null) {
                Color bg = parent.getBackground();
                Rectangle rect = new Rectangle(0,0,width, height);
                Area borderRegion = new Area(rect);
                borderRegion.subtract(area);
                g2.setClip(borderRegion);
                g2.setColor(bg);
                g2.fillRect(0, 0, width, height);
                g2.setClip(null);
            }                 

            if( stroke.getLineWidth()>0 )
            {
                g2.setColor(color);
                g2.setStroke(stroke);            
            }        

            g2.draw(area);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer 
    {
        public ButtonRenderer() {
          setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
          setText((value == null) ? "Modify" : value.toString());
          return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor 
    {
      private String label;

      public ButtonEditor(JCheckBox checkBox)
      {
        super(checkBox);
      }
      public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) 
      {
        label = (value == null) ? "Modify" : value.toString();
        button.setText(label);
        button.setBorder(new EmptyBorder(0,0,0,0));
        button.setBackground(new Color(0, 144, 228));
        return button;
      }
      public Object getCellEditorValue() 
      {
        return new String(label);
      }
    }
    
    public MainFrame(User user){
        initComponents();
        initStyle();
        
        this.user = user;
        
        resetMainMenu();
        if(this.user.getRole().equals("admin")){
            dashboardMainMenu.setVisible(true);
        
            
        }else{
            dashboardMainMenu.setVisible(false);
            
            selectedMenu = ticketMainMenu;
            FlatSVGIcon carticon = new FlatSVGIcon("img/icon/cart-icon.svg", 35, 35);
            carticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                public Color apply(Color t){
                    return new Color(255, 255, 255);
                }
            }));
            ticketMainMenu.setBackground(new Color(101,55,255));
            ticketMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
            jLabel33.setIcon(carticon);
            jLabel32.setForeground(Color.white);
            
            updateGraphics();
        }

        jLayeredPane1.add(blurBGPanel, JLayeredPane.MODAL_LAYER);
        jLayeredPane1.add(popupPanel, JLayeredPane.POPUP_LAYER);
        
        jLayeredPane1.add(notificationPanel, JLayeredPane.PALETTE_LAYER);
//        notificationPanel.setBounds(jLabel56.getX()-300, jLabel56.getY(), 300, 400);
        
        //Test PopUp
        
//        popupContentPanel.add(viewProductPanel);
        popupContentPanel.add(editProductPanel);
        popupPanel.setBounds(this.getWidth(), this.getHeight(),this.getWidth(), this.getHeight());
//        popupContentPanel.add(checkoutProductPanel);

//        ProductService productService = new ProductService();
//        viewProductName.setText(productService.getProductById(1).getName());
        
        
//        test = new PopupPanel();
//        test.setBounds(0, 0, 500, 500);
//        jLayeredPane1.add(test, JLayeredPane.POPUP_LAYER);

//        contentPanel.add(dashboardPanel);
//        contentPanel.add(myProfilePanel);
        contentPanel.add(TestPanel);

        
        
//        resetMainMenu();
//        
        FlatSVGIcon profiledetailsicon = new FlatSVGIcon("img/icon/profile-details-icon.svg", 25, 25);
        profiledetailsicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(104, 104, 104);
            }
        }));
        jLabel143.setIcon(profiledetailsicon);
        
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 32, 32);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel27.setIcon(charticon);
        jLabel1.setForeground(Color.white);
        
        
        
        
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(154, 154, 154);
            }
        }));
        jButton10.setIcon(icon);
        
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(getClass().getResource("/img/product/sea-dweller.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Image scaledImage = bufferedImage.getScaledInstance(220, 120, Image.SCALE_SMOOTH);
                Image scaledImage = bufferedImage.getScaledInstance(220, -1, Image.SCALE_SMOOTH);


        ImageIcon image = new ImageIcon(scaledImage);
        jLabel131.setIcon(image);
        
//        contentPanel.add(TestPanel);
//        testCategoryCont.add(new AddProductOptionPanel());
        
//        ticketMainMenu.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
//        ticketMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
//        jLabel65.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 30 ) );

//        JLabel label = new JLabel("Hello");
//        jTabbedPane1.putClientProperty("JTabbedPane.trailingComponent", new JButton("Test"));
        
        
//        jPanel3.add(selectedProductThumbPanel);
//        selectedProductThumbPanel.setBounds(0, 0, 300, 180);
        
//        checkoutThumbScrollPane.add(checkoutProductThumbPanel);
        
//        CheckoutProductThumbPanel product = new CheckoutProductThumbPanel();
//        product.setPrice(1000);
        
//        checkoutThumbScrollPane.add(product);
//        checkoutProductThumbPanel.setBounds(0, 0, 400, 180);
        
//        System.out.println(menuPanel.getBackground().getRed());
//        System.out.println(jPanel1.getBackground().getBlue());
       
        jPanel30.setLayout(new WrapLayout());
        jPanel165.setLayout(new WrapLayout());

//        jPanel51.setLayout(new WrapLayout());
        
        //Test Product Service
        ProductService productsService = new ProductService();
        List<Product>products = productsService.getAllProductDetails();
        
        products.forEach(e -> {
            ProductThumb2 productThumb = new ProductThumb2();
            productThumb.setProductDetails(e);
//            productThumb.setProductName(e.getName());
//            productThumb.setProductStocks(e.getStocks());
            jPanel30.add(productThumb);
            jPanel165.add(productThumb);
        });
        
//        String[] columns = new String[] {"Color", "Price", "Available"};
//        String[][] data = new String[][]{
//            {null, "1000","In stock: 12"},
//            {null, "2000","In stock: 2"},
//            {null, "3000","In stock: 15"}
//        };
//        DefaultTableModel dataModel = new DefaultTableModel(data, columns){
//            boolean[] canEdit = new boolean [] {
//                true, false, false
//            };
//
//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return canEdit [columnIndex];
//            }
//        };
//        viewProductVariantTable.setModel(dataModel);
//
//        viewProductVariantTable.getColumn("Color").setCellRenderer(new ButtonRenderer());
//        viewProductVariantTable.getColumn("Color").setCellEditor(new ButtonEditor(new JCheckBox()));
        
        
//        button.addActionListener(
//            new ActionListener()
//            {
//              public void actionPerformed(ActionEvent event)
//              {
//                JOptionPane.showMessageDialog(null,"Do you want to modify this line?");
//              }
//            }
//        );


        //Category test UI Database
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
            
//            if(brands.isEmpty()){
//                brands.put(e.getBrand(), 1);
//            }else{
//                for (String i : brands.keySet()) {
//                    if(i.equals(e.getBrand())){
//                        brands.put(i, brands.get(i) + 1);
//                    }else{
//                        System.out.println("Added new set");
//                        brands.put(e.getBrand(), 1);
//                    }
//                }
//            }
        });
        
        List<String> sortedType = removeDuplicates(type);
        
        sortedType.forEach(e -> {
            CategorySectionPanel categoryPanel = new CategorySectionPanel();
            categoryPanel.setCategoryType(e);
            
            HashMap<String, Integer> brands = new HashMap();

            categoryService.getAllCategory().forEach(e2->{
                if(e2.getType().equals(e)){

                    if(brands.isEmpty()){
                        brands.put(e2.getBrand(), 1);
                    }else{
                        for (String i : brands.keySet()) {
                            if(i.equals(e2.getBrand())){
                                brands.put(i, brands.get(i) + 1);
                            }else{
                                System.out.println("Added new set");
                                brands.put(e2.getBrand(), 1);
                            }
                        }
                    }
                }
            });
            
            for (String i : brands.keySet()) {
                CategoryThumb thumb = new CategoryThumb(false);
                thumb.setThumbTitle(i);
                thumb.setThumbItems(brands.get(i));
                thumb.setCategoryType(e);
                categoryPanel.addThumb(thumb);
            }

            jPanel19.add(categoryPanel);
        });
        
//        jPanel46.add(new ProductOptionPanel());
//        jPanel46.add(new AddProductOptionPanel());

//        jLabel65.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
//        jLabel65.setBackground(new Color(238,238,238));

//        jLabel65.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
        
//        jProgressBar1.setVisible(false);
        
//        for (String i : brands.keySet()) {
//            System.out.println("key: " + i + " value: " + brands.get(i));
//        }
//        List<String> list = new ArrayList<String>();
//        addProductVariants.put("color", list);

//        jPanel46.remove(jPanel48);
//        testCategoryCont.add(new AddProductOptionPanel());
//        updateGraphics();

//        jPanel45.add(new EditProductTableRow());

//        ButtonGroup buttonGroup = new ButtonGroup();
//        buttonGroup.add(jToggleButton2);
//        buttonGroup.add(jToggleButton3);
//        
//        jToggleButton2.setSelected(true);
        
        jPanel11.removeAll();
//        jPanel11.add(new ViewProductOptionPanel());
        dashboardHistogramPanel.add(new HistogramPanel().createAndShowGUI());
        RingChart ringChart = new RingChart();
        HashMap<String, Float> ringData = new HashMap<>();
        TransactionHistoryService th = new TransactionHistoryService();
        List<TransactionHistory> transactionList = th.getAllTransactionHistoryDetails();
        
        //total sales
//        HashMap<Integer, Float> productTotalSale
        transactionList.forEach(e->{
            totalSales += e.getTotalPrice();
            totalOrders += e.getOrders();
            
            if(productTotalOrder.containsKey(e.getId())){
                int currentOrder = productTotalOrder.get(e.getId());
                productTotalOrder.put(e.getId(), currentOrder + e.getOrders());
            }else{
                productTotalOrder.put(e.getId(), e.getOrders());
            }
        });
        jLabel102.setText("₱"+String.valueOf(totalSales));
        jLabel111.setText(String.valueOf(totalOrders));
        
        int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
        jLabel84.setText(String.valueOf(productsService.getProductById(topProductId).getName()));
        jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
        
        
        ServiceTicketsService sts = new ServiceTicketsService();
        totalServiceRevenue = 0;
        sts.getAllServiceTicketsDetails().forEach(e -> {
            totalServiceRevenue += e.getPrice();
        });
        jLabel136.setText(String.valueOf(totalServiceRevenue));
        
        //ring data
        sortedType.forEach(e -> {
            totalRevenue = 0;
            categoryService.getAllCategory().forEach(e2->{
                if(e.equals(e2.getType())){
                    transactionList.forEach(e3 -> {
                        if(e3.getProductId() == e2.getProduct_id()){
                            totalRevenue += e3.getTotalPrice();
                            System.out.println("category type match!");
                        }
                    });
                }
            });
            ringData.put(e, totalRevenue);
        });
        ringChart.setData(ringData);
        ringChartPanel.add(ringChart);
        
        
//        serviceThumb.remove(serviceThumbExpand);
//        jPanel113.removeAll();
//        jPanel113.add(new ServiceThumb());
//        jPanel113.add(new ServiceThumb());
        
//        jPanel117.removeAll();
//        jPanel117.add(new SalesCateogryThumb());
//        jPanel117.add(new SalesCateogryThumb());
//        sortedType.forEach(e -> {
//            SalesCateogryThumb sct = new SalesCateogryThumb();
//            sct.setTypeName(e);
//            jPanel117.add(sct);
//        });
//        jPanel30.removeAll();
//        jPanel30.add(new ProductThumb2());

        FlatSVGIcon creditCardIcon = new FlatSVGIcon("img/icon/credit-card-regular.svg", 12, 12);
        creditCardIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel104.setIcon(creditCardIcon);
        
        FlatSVGIcon listicon = new FlatSVGIcon("img/icon/list-solid.svg", 12, 12);
        listicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel112.setIcon(listicon);
        
        FlatSVGIcon serviceicon = new FlatSVGIcon("img/icon/bolt-solid.svg", 12, 12);
        serviceicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel137.setIcon(serviceicon);
        
        FlatSVGIcon refundicon = new FlatSVGIcon("img/icon/rotate-left-solid.svg", 13, 13);
        refundicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel141.setIcon(refundicon);
        

        FlatSVGIcon topServiceIcon = new FlatSVGIcon("img/icon/bolt-solid.svg", 20, 20);
        topServiceIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel95.setIcon(topServiceIcon);

        
        
//        jLabel151.setText("<html>First line<br>Second line</html>");
        BufferedImage bufferedImage2 = null;
        try {
            bufferedImage2 = ImageIO.read(getClass().getResource("/img/profile/Faustino_PaulJustine_R.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image scaledImage2 = bufferedImage2.getScaledInstance(-1, 220, Image.SCALE_SMOOTH);

        ImageIcon image2 = new ImageIcon(scaledImage2);

        jLabel156.setIcon(image2);
        
        Image scaledImage3 = bufferedImage2.getScaledInstance(-1, accountProfilePicture.getHeight(), Image.SCALE_SMOOTH);
        accountProfilePicture.setIcon(new ImageIcon(scaledImage3));
       
        jPanel142.add(new MultipleLinesChart());

    }
    
    public MainFrame() {
        initComponents();
        initStyle();
        

//       cartPanelPopup.setOpaque(false);
//        cartPanelPopup.setBackground(new Color(0,0,0,125));
        
        jLayeredPane1.add(blurBGPanel, JLayeredPane.MODAL_LAYER);
        jLayeredPane1.add(popupPanel, JLayeredPane.POPUP_LAYER);
        
        jLayeredPane1.add(notificationPanel, JLayeredPane.PALETTE_LAYER);
//        notificationPanel.setBounds(jLabel56.getX()-300, jLabel56.getY(), 300, 400);
        
        //Test PopUp
        
//        popupContentPanel.add(viewProductPanel);
        popupContentPanel.add(editProductPanel);
        popupPanel.setBounds(this.getWidth(), this.getHeight(),this.getWidth(), this.getHeight());
//        popupContentPanel.add(checkoutProductPanel);

//        ProductService productService = new ProductService();
//        viewProductName.setText(productService.getProductById(1).getName());
        
        
//        test = new PopupPanel();
//        test.setBounds(0, 0, 500, 500);
//        jLayeredPane1.add(test, JLayeredPane.POPUP_LAYER);

//        contentPanel.add(dashboardPanel);
//        contentPanel.add(myProfilePanel);
        contentPanel.add(TestPanel);

        
        
        
        
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 32, 32);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel27.setIcon(charticon);
        jLabel1.setForeground(Color.white);
        
        
        
        
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(154, 154, 154);
            }
        }));
        jButton10.setIcon(icon);
        
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(getClass().getResource("/img/product/sea-dweller.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image scaledImage = bufferedImage.getScaledInstance(220, 120, Image.SCALE_SMOOTH);
//                Image scaledImage = bufferedImage.getScaledInstance(220, -1, Image.SCALE_SMOOTH);


        ImageIcon image = new ImageIcon(scaledImage);
        jLabel131.setIcon(image);
        
//        contentPanel.add(TestPanel);
//        testCategoryCont.add(new AddProductOptionPanel());
        
//        ticketMainMenu.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
//        ticketMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
//        jLabel65.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 30 ) );

//        JLabel label = new JLabel("Hello");
//        jTabbedPane1.putClientProperty("JTabbedPane.trailingComponent", new JButton("Test"));
        
        
//        jPanel3.add(selectedProductThumbPanel);
//        selectedProductThumbPanel.setBounds(0, 0, 300, 180);
        
//        checkoutThumbScrollPane.add(checkoutProductThumbPanel);
        
//        CheckoutProductThumbPanel product = new CheckoutProductThumbPanel();
//        product.setPrice(1000);
        
//        checkoutThumbScrollPane.add(product);
//        checkoutProductThumbPanel.setBounds(0, 0, 400, 180);
        
//        System.out.println(menuPanel.getBackground().getRed());
//        System.out.println(jPanel1.getBackground().getBlue());
       
        jPanel30.setLayout(new WrapLayout());
        jPanel165.setLayout(new WrapLayout());
//        jPanel51.setLayout(new WrapLayout());
        
        //Test Product Service
        ProductService productsService = new ProductService();
        List<Product>products = productsService.getAllProductDetails();
        
        products.forEach(e -> {
            ProductThumb testproductThumb = new ProductThumb();
            testproductThumb.setProductDetails(e);
            
            ProductThumb2 productThumb2 = new ProductThumb2();
            productThumb2.setProductDetails(e);
//            productThumb.setProductName(e.getName());
//            productThumb.setProductStocks(e.getStocks());
            jPanel30.add(testproductThumb);
            jPanel165.add(productThumb2);
        });
        
//        String[] columns = new String[] {"Color", "Price", "Available"};
//        String[][] data = new String[][]{
//            {null, "1000","In stock: 12"},
//            {null, "2000","In stock: 2"},
//            {null, "3000","In stock: 15"}
//        };
//        DefaultTableModel dataModel = new DefaultTableModel(data, columns){
//            boolean[] canEdit = new boolean [] {
//                true, false, false
//            };
//
//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return canEdit [columnIndex];
//            }
//        };
//        viewProductVariantTable.setModel(dataModel);
//
//        viewProductVariantTable.getColumn("Color").setCellRenderer(new ButtonRenderer());
//        viewProductVariantTable.getColumn("Color").setCellEditor(new ButtonEditor(new JCheckBox()));
        
        
//        button.addActionListener(
//            new ActionListener()
//            {
//              public void actionPerformed(ActionEvent event)
//              {
//                JOptionPane.showMessageDialog(null,"Do you want to modify this line?");
//              }
//            }
//        );


        //Category test UI Database
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
            
//            if(brands.isEmpty()){
//                brands.put(e.getBrand(), 1);
//            }else{
//                for (String i : brands.keySet()) {
//                    if(i.equals(e.getBrand())){
//                        brands.put(i, brands.get(i) + 1);
//                    }else{
//                        System.out.println("Added new set");
//                        brands.put(e.getBrand(), 1);
//                    }
//                }
//            }
        });
        
        List<String> sortedType = removeDuplicates(type);
        
        sortedType.forEach(e -> {
            CategorySectionPanel categoryPanel = new CategorySectionPanel();
            categoryPanel.setCategoryType(e);
            
            HashMap<String, Integer> brands = new HashMap();

            categoryService.getAllCategory().forEach(e2->{
                if(e2.getType().equals(e)){

                    if(brands.isEmpty()){
                        brands.put(e2.getBrand(), 1);
                    }else{
                        for (String i : brands.keySet()) {
                            if(i.equals(e2.getBrand())){
                                brands.put(i, brands.get(i) + 1);
                            }else{
                                System.out.println("Added new set");
                                brands.put(e2.getBrand(), 1);
                            }
                        }
                    }
                }
            });
            
            for (String i : brands.keySet()) {
                CategoryThumb thumb = new CategoryThumb(false);
                thumb.setThumbTitle(i);
                thumb.setThumbItems(brands.get(i));
                thumb.setCategoryType(e);
                categoryPanel.addThumb(thumb);
            }

            jPanel19.add(categoryPanel);
        });
        
//        jPanel46.add(new ProductOptionPanel());
//        jPanel46.add(new AddProductOptionPanel());

//        jLabel65.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
//        jLabel65.setBackground(new Color(238,238,238));

//        jLabel65.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
        
//        jProgressBar1.setVisible(false);
        
//        for (String i : brands.keySet()) {
//            System.out.println("key: " + i + " value: " + brands.get(i));
//        }
//        List<String> list = new ArrayList<String>();
//        addProductVariants.put("color", list);

//        jPanel46.remove(jPanel48);
//        testCategoryCont.add(new AddProductOptionPanel());
//        updateGraphics();

//        jPanel45.add(new EditProductTableRow());

//        ButtonGroup buttonGroup = new ButtonGroup();
//        buttonGroup.add(jToggleButton2);
//        buttonGroup.add(jToggleButton3);
//        
//        jToggleButton2.setSelected(true);
        
        jPanel11.removeAll();
//        jPanel11.add(new ViewProductOptionPanel());
        dashboardHistogramPanel.add(new HistogramPanel().createAndShowGUI());
        RingChart ringChart = new RingChart();
        HashMap<String, Float> ringData = new HashMap<>();
        TransactionHistoryService th = new TransactionHistoryService();
        List<TransactionHistory> transactionList = th.getAllTransactionHistoryDetails();
        
        //total sales
//        HashMap<Integer, Float> productTotalSale
        transactionList.forEach(e->{
            totalSales += e.getTotalPrice();
            totalOrders += e.getOrders();
            
            if(productTotalOrder.containsKey(e.getId())){
                int currentOrder = productTotalOrder.get(e.getId());
                productTotalOrder.put(e.getId(), currentOrder + e.getOrders());
            }else{
                productTotalOrder.put(e.getId(), e.getOrders());
            }
        });
        jLabel102.setText("₱"+String.valueOf(totalSales));
        jLabel111.setText(String.valueOf(totalOrders));
        
        int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
        jLabel84.setText(String.valueOf(productsService.getProductById(topProductId).getName()));
        jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
        
        
        ServiceTicketsService sts = new ServiceTicketsService();
        totalServiceRevenue = 0;
        sts.getAllServiceTicketsDetails().forEach(e -> {
            totalServiceRevenue += e.getPrice();
        });
        jLabel136.setText(String.valueOf(totalServiceRevenue));
        
        //ring data
        sortedType.forEach(e -> {
            totalRevenue = 0;
            categoryService.getAllCategory().forEach(e2->{
                if(e.equals(e2.getType())){
                    transactionList.forEach(e3 -> {
                        if(e3.getProductId() == e2.getProduct_id()){
                            totalRevenue += e3.getTotalPrice();
                            System.out.println("category type match!");
                        }
                    });
                }
            });
            ringData.put(e, totalRevenue);
        });
        ringChart.setData(ringData);
        ringChartPanel.add(ringChart);
        
        
//        serviceThumb.remove(serviceThumbExpand);
//        jPanel113.removeAll();
//        jPanel113.add(new ServiceThumb());
//        jPanel113.add(new ServiceThumb());
        
//        jPanel117.removeAll();
//        jPanel117.add(new SalesCateogryThumb());
//        jPanel117.add(new SalesCateogryThumb());
//        sortedType.forEach(e -> {
//            SalesCateogryThumb sct = new SalesCateogryThumb();
//            sct.setTypeName(e);
//            jPanel117.add(sct);
//        });
//        jPanel30.removeAll();
//        jPanel30.add(new ProductThumb2());

        FlatSVGIcon creditCardIcon = new FlatSVGIcon("img/icon/credit-card-regular.svg", 12, 12);
        creditCardIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel104.setIcon(creditCardIcon);
        
        FlatSVGIcon listicon = new FlatSVGIcon("img/icon/list-solid.svg", 12, 12);
        listicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel112.setIcon(listicon);
        
        FlatSVGIcon serviceicon = new FlatSVGIcon("img/icon/bolt-solid.svg", 12, 12);
        serviceicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel137.setIcon(serviceicon);
        
        FlatSVGIcon refundicon = new FlatSVGIcon("img/icon/rotate-left-solid.svg", 13, 13);
        refundicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel141.setIcon(refundicon);
        

        FlatSVGIcon topServiceIcon = new FlatSVGIcon("img/icon/bolt-solid.svg", 20, 20);
        topServiceIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel95.setIcon(topServiceIcon);

        
        
//        jLabel151.setText("<html>First line<br>Second line</html>");
        BufferedImage bufferedImage2 = null;
        try {
            bufferedImage2 = ImageIO.read(getClass().getResource("/img/profile/Faustino_PaulJustine_R.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image scaledImage2 = bufferedImage2.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);

        ImageIcon image2 = new ImageIcon(scaledImage2);

        jLabel156.setIcon(image2);
        
        Image scaledImage3 = bufferedImage2.getScaledInstance(-1, accountProfilePicture.getHeight(), Image.SCALE_SMOOTH);
        accountProfilePicture.setIcon(new ImageIcon(scaledImage3));
       
        jPanel142.add(new MultipleLinesChart());
        
        resetCheckoutPayment();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blurBGPanel = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(20, 20, 30, 150));
                }else{
                    g2.setColor(new Color(230, 230, 230, 150));
                }
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

        };
        themesButtonGroup = new javax.swing.ButtonGroup();
        popupPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //                    if(darkRB.isSelected()){
                    //                        g2.setColor(new Color(33, 37, 43));
                    //                    }else{
                    //                        g2.setColor(new Color(255, 255, 255));
                    //                    }
                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }

        };
        closePopupIcon = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(53, 57, 63));
                }else{
                    g2.setColor(new Color(240, 240, 240));
                }

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
            }

        };
        jLabel9 = new javax.swing.JLabel();
        popupContentPanel = new javax.swing.JPanel();
        popupClosePanel = new javax.swing.JPanel();
        ticketMainPanel = new javax.swing.JPanel();
        ticketMainTabPane = new javax.swing.JTabbedPane();
        jPanel43 = new javax.swing.JPanel();
        productCategoryPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel16 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel38 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField3 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton13 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jPanel113 = new javax.swing.JPanel();
        serviceThumb = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
                    g2.setColor(new Color(58, 61, 68));
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 20, 20);
                }else{
                    //            g2.setColor(new Color(245, 245, 245));
                    g2.setColor(contentPanel.getBackground());
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-10, 20, 20);
                    g2.setColor(new Color(225, 225, 225));
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-11, 20, 20);
                }

            }

        };
        jPanel114 = new javax.swing.JPanel();
        jPanel120 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jLabel107 = new javax.swing.JLabel();
        jPanel121 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        serviceThumbExpand = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                    g2.drawLine(1, 1, this.getWidth()-2, 1);
                }else{
                    g2.setColor(new Color(225, 225, 225));
                    g2.drawLine(1, 1, this.getWidth()-2, 1);
                }
            }

        };
        jLabel118 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton18 = new javax.swing.JButton();
        jLabel119 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel122 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel123 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        editProductPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        productImage = new javax.swing.JLabel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(38, 42, 48));
                    //        }else{
                    //            g2.setColor(new Color(232,232,232));
                    //        }
                //        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);

                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(70, 70, 80));
                    //        }else{
                    //            g2.setColor(new Color(205,205,205));
                    //        }
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(63, 69, 82));
                    //        }else{
                    //            g2.setColor(new Color(255, 139, 101));
                    //        }
                g2.setColor(new Color(63, 69, 82));
                g2.fillRoundRect(this.getWidth()-40, this.getHeight()-30, 100, 100, 30, 30);

                g2.setColor(contentPanel.getBackground());
                //        g2.setStroke(new BasicStroke(10));
                for(int i = 30; i > 0 ; i = i-2 ){
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
                }

                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(63, 69, 82));
                    //        }else{
                    //            g2.setColor(new Color(255, 139, 101));
                    //        }
                g2.setColor(new Color(63, 69, 82));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, this.getWidth()-2, this.getHeight()-2, 30, 30);

                try{
                    BufferedImage image = ImageIO.read(this.getClass().getResource("/img/icon/edit-icon-white.png"));
                    g2.drawImage(image, this.getWidth()-30, this.getHeight()-27, null);
                }catch(Exception e){}
            }

        };
        jLabel5 = new javax.swing.JLabel();
        editProductNameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        editProductType = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        editProductBrand = new javax.swing.JComboBox<>();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        editProductStockField = new javax.swing.JSpinner();
        editProductPriceField = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        addProductOptionButton = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel53 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        jPanel73 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel74 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel78 = new javax.swing.JPanel();
        jSpinner3 = new javax.swing.JSpinner();
        jPanel79 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        viewProductPanel = new javax.swing.JPanel();
        viewProductName = new javax.swing.JLabel();
        productImage1 = new javax.swing.JLabel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(38, 42, 48));
                    //        }else{
                    //            g2.setColor(new Color(232,232,232));
                    //        }
                //        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
                g2.setColor(contentPanel.getBackground());
                for(int i = 25; i >= 0; i--){
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
                }

                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 80));
                }else{
                    g2.setColor(new Color(205,205,205));
                }

                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

            }

        };
        jSeparator2 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jPanel51 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        selectedProductThumbPanel1 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        g2.setColor(new Color(245,245,245));

                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(40, 40, 50));
                    //        }else{
                    //            g2.setColor(new Color(245,245,245));
                    //        }
                //
                //        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 35, 35);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 80));
                }else{
                    g2.setColor(new Color(205,205,205));
                }

                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }

        };
        jLabel65 = new javax.swing.JLabel();
        viewProductPrice = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jSpinner4 = new javax.swing.JSpinner();
        jPanel81 = new javax.swing.JPanel();
        viewProductSelectedVariants = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        viewProductVariantTable = new javax.swing.JTable();
        salesHistoryPanel = new javax.swing.JPanel();
        salesHistoryTabPane = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        selectedProductThumbPanel = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        g2.setColor(new Color(245,245,245));

                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(40, 40, 50));
                    //        }else{
                    //            g2.setColor(new Color(245,245,245));
                    //        }
                //
                //        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 35, 35);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 80));
                }else{
                    g2.setColor(new Color(205,205,205));
                }

                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }

        };
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton6 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        checkoutProductPanel = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        checkoutThumbScrollPane = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel24 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        checkoutCashThumb = new JPanel(){
            boolean isHover = false;
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                if(isHover){
                    g2.setColor(new Color(0,144,228));
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                }

            }

            {
                this.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHover = false;
                        repaint();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        isHover = true;
                        repaint();
                    }
                });
            }

        };
        jLabel163 = new javax.swing.JLabel();
        checkoutCashIconCont = new javax.swing.JPanel();
        checkoutCashIcon = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        checkoutCreditThumb = new JPanel(){
            boolean isHover = false;
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                if(isHover){
                    g2.setColor(new Color(0,144,228));
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                }

            }

            {
                this.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHover = false;
                        repaint();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        isHover = true;
                        repaint();
                    }
                });
            }

        };
        jLabel190 = new javax.swing.JLabel();
        checkoutCreditIconCont = new javax.swing.JPanel();
        checkoutCreditIcon = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        checkoutProductThumbPanel = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                //        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        g2.setColor(new Color(232,232,232));
                //        g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);

                //        g2.setColor(Color.GRAY);
                //        g2.setStroke(new BasicStroke(1));
                //        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 42, 48));
                }else{
                    g2.setColor(new Color(232,232,232));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 80));
                }else{
                    g2.setColor(new Color(205,205,205));
                }

                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }

        };
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jButton9 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        productThumb = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }

        };
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(33, 37, 43));
                }else{
                    g2.setColor(new Color(235,235,235));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
            }

        };
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        TestPanel = new javax.swing.JPanel();
        testCategoryCont = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jPanel60 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jTextField9 = new javax.swing.JTextField();
        jPanel63 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jTextField10 = new javax.swing.JTextField();
        jPanel67 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jPanel68 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        jTextField11 = new javax.swing.JTextField();
        jPanel70 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jPanel89 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jPanel90 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jPanel75 = new javax.swing.JPanel();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        testSearchField = new javax.swing.JTextField();
        productThumb1 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        g2.setColor(Color.GRAY);
                //        g2.setStroke(new BasicStroke(1));
                //        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

            }

        };
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jPanel122 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(33, 37, 43));
                }else{
                    g2.setColor(new Color(235,235,235));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
            }

        };
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                for(int i = 25; i > 0; i = i-2){
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
                }
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }

        };
        jButton22 = new javax.swing.JButton();
        jSpinner5 = new javax.swing.JSpinner();
        ticketMainPanelHeader = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        ticketSearchBar = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        salesHistoryPanelHeader = new javax.swing.JPanel();
        salesHistorySearchBar = new javax.swing.JTextField();
        categorySectionPanel = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        dashboardTabPane = new javax.swing.JTabbedPane();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jPanel82 = new javax.swing.JPanel();
        jPanel94 = //new javax.swing.JPanel(){
            //
            //    public void paintComponent(Graphics g){
                //        super.paintComponent(g);
                //        Graphics2D g2 = (Graphics2D) g.create();
                //
                //        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(63, 63, 63));
                    //
                    //        }else{
                    //            g2.setColor(new Color(203, 203, 203));
                    //        }
                //        g2.setStroke(new BasicStroke(1));
                //        g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
                //    }
            //
            //}

        new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                }else{
                    g2.setColor(new Color(245, 245, 245));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
            }

        };
        jPanel92 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(30, 30, 30));
                }else{
                    g2.setColor(new Color(20, 20, 20));
                }
                //        g2.setColor(contentPanel.getBackground());

                g2.fillRoundRect(0, 15, this.getWidth(), this.getHeight()-15, 25, 25);
            }

        };
        jPanel39 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(30, 30, 30));
                }else{
                    g2.setColor(new Color(20, 20, 20));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
            }

        };
        jLabel82 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel96 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(30, 30, 30));
                }else{
                    g2.setColor(new Color(20, 20, 20));
                }
                g2.fillRoundRect(0, 15, this.getWidth(), this.getHeight()-15, 25, 25);
            }

        };
        jPanel83 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(30, 30, 30));
                }else{
                    g2.setColor(new Color(20, 20, 20));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
            }

        };
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jPanel110 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                }else{
                    g2.setColor(new Color(245, 245, 245));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
            }

        };
        jPanel111 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(207, 244, 101));
                    //        }else{
                    //            g2.setColor(new Color(250, 255, 101));
                    //        }
                g2.setColor(new Color(184, 234, 194));

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
                g2.fillRect(10, 0, this.getWidth()-10, this.getHeight());

                //        int x = this.getWidth()-100;
                //        int y = 7;
                //        GradientPaint redtowhite = new GradientPaint(x, y, new Color(184, 234, 194), this.getWidth(), y,
                    //        new Color(201, 243, 209));
                //        g2.setPaint(redtowhite);
                //
                //        g2.fillRoundRect(this.getWidth()-100, 20, 80, 80, 80, 80);
                //        g2.setPaint(redtowhite);
                //        g2.fillRoundRect(this.getWidth()-80, 30, 30, 30, 30, 30);
                try{
                    final BufferedImage image = ImageIO.read(getClass().getResource("/img/icon/TotalSaleUpBG.png"));
                    g2.drawImage(image, 100, 0, null);
                }catch(Exception e){}
            }

        };
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jPanel112 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(255, 255, 255));
                    //        }else{
                    //            g2.setColor(new Color(0, 0, 0));
                    //        }
                //        g2.setColor(contentPanel.getBackground());
                g2.setColor(new Color(0, 0, 0));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
            }

        };
        jLabel104 = new javax.swing.JLabel();
        jPanel115 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(50, 54, 62));
                }else{
                    g2.setColor(new Color(230, 230, 230));
                }
                g2.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());

            }

        };
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jPanel116 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 70));
                }else{
                    g2.setColor(new Color(0, 0, 0));
                }
                //        g2.setColor(contentPanel.getBackground());

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
            }

        };
        jLabel112 = new javax.swing.JLabel();
        jPanel129 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(50, 54, 62));
                }else{
                    g2.setColor(new Color(230, 230, 230));
                }
                g2.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());

            }

        };
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jPanel130 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 70));
                }else{
                    g2.setColor(new Color(0, 0, 0));
                }
                //        g2.setColor(contentPanel.getBackground());

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
            }

        };
        jLabel137 = new javax.swing.JLabel();
        jPanel131 = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jPanel132 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(70, 70, 70));
                }else{
                    g2.setColor(new Color(0, 0, 0));
                }
                //        g2.setColor(contentPanel.getBackground());

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
            }

        };
        jLabel141 = new javax.swing.JLabel();
        dashboardHistogramPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jPanel93 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                }else{
                    g2.setColor(new Color(245, 245, 245));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
            }

        };
        ringChartPanel = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jPanel117 = new javax.swing.JPanel();
        jPanel118 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(40, 44, 52));
                }else{
                    g2.setColor(new Color(255, 255, 255));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight()-5, 15, 15);
            }

        };
        jLabel114 = new javax.swing.JLabel();
        jPanel119 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(85, 85, 255));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 25, 25);
            }

        };
        jLabel116 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jPanel137 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        jPanel138 = new javax.swing.JPanel();
        jPanel140 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(33, 37, 43));
                }else{
                    g2.setColor(new Color(243,247,250));
                }
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

        };
        jPanel139 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jLabel158 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel159 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton21 = new javax.swing.JButton();
        jPanel143 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
            }

        };
        jLabel157 = new javax.swing.JLabel();
        jPanel147 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jPanel148 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
            }

        };
        jLabel164 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jButton23 = new javax.swing.JButton();
        jPanel149 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jPanel150 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
            }

        };
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel167 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jPanel144 = new javax.swing.JPanel();
        jPanel141 = new javax.swing.JPanel();
        jLabel160 = new javax.swing.JLabel();
        jPanel142 = new javax.swing.JPanel();
        dashboardPanelHeader = new javax.swing.JPanel();
        customTable = new javax.swing.JPanel();
        jPanel88 = new javax.swing.JPanel();
        jPanel95 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jPanel97 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jPanel76 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jPanel98 = new javax.swing.JPanel();
        jPanel99 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jPanel100 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jPanel101 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jPanel102 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel103 = new javax.swing.JPanel();
        jPanel104 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jPanel105 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel106 = new javax.swing.JPanel();
        jPanel107 = new javax.swing.JPanel();
        jPanel108 = new javax.swing.JPanel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel109 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        myProfilePanel = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jPanel126 = new javax.swing.JPanel();
        jPanel123 = new javax.swing.JPanel();
        jPanel124 = new javax.swing.JPanel();
        jLabel156 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jPanel127 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(33, 37, 43));
                }else{
                    g2.setColor(new Color(243,247,250));
                }
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

        };
        jPanel128 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jPanel125 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
            }

        };
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jTextField16 = new javax.swing.JTextField();
        jPanel133 = new javax.swing.JPanel();
        jPanel134 = new javax.swing.JPanel();
        jLabel149 = new JLabel(){
            {
                FlatSVGIcon icon = new FlatSVGIcon("img/icon/calendar-solid.svg", 20, 20);
                icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                    public Color apply(Color t){
                        return new Color(3, 108, 175);
                    }
                }));
                this.setIcon(icon);
            }
        };
        jLabel153 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jPanel135 = new javax.swing.JPanel();
        jLabel147 = new JLabel(){
            {
                FlatSVGIcon icon = new FlatSVGIcon("img/icon/clock-solid.svg", 20, 20);
                icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                    public Color apply(Color t){
                        return new Color(3, 108, 175);
                    }
                }));
                this.setIcon(icon);
            }
        };
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jPanel136 = new javax.swing.JPanel();
        jLabel148 = new JLabel(){
            {
                FlatSVGIcon icon = new FlatSVGIcon("img/icon/clipboard-icon.svg", 18, 23);
                icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                    public Color apply(Color t){
                        return new Color(3, 108, 175);
                    }
                }));
                this.setIcon(icon);
            }
        };
        jLabel152 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jPanel151 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jPanel152 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
            }

        };
        jLabel169 = new javax.swing.JLabel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jButton24 = new javax.swing.JButton();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        changeUserPassDialog = new javax.swing.JDialog();
        jScrollPane19 = new javax.swing.JScrollPane();
        jPanel156 = new javax.swing.JPanel();
        jLabel175 = new javax.swing.JLabel();
        jPanel153 = new javax.swing.JPanel();
        jPanel154 = new javax.swing.JPanel();
        jTextField19 = new javax.swing.JTextField();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel181 = new javax.swing.JLabel();
        jPanel155 = new javax.swing.JPanel();
        jTextField21 = new javax.swing.JTextField();
        jLabel178 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jLabel179 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel180 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jLabel182 = new javax.swing.JLabel();
        notificationDialog = new javax.swing.JDialog();
        jPanel157 = new javax.swing.JPanel();
        jLabel183 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jPanel158 = new javax.swing.JPanel();
        jPanel159 = new javax.swing.JPanel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        notificationPanel = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(contentPanel.getBackground());
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(200, 200, 200));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jPanel160 = new javax.swing.JPanel();
        jLabel186 = new javax.swing.JLabel();
        jPanel161 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(53, 57, 63));
                }else{
                    g2.setColor(new Color(240, 240, 240));
                }

                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
            }

        };
        jLabel187 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jPanel162 = new javax.swing.JPanel();
        jPanel163 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(215, 215, 215));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
            }

        };
        jLabel188 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        productMainPanel = new javax.swing.JPanel();
        ticketMainTabPane1 = new javax.swing.JTabbedPane();
        jPanel145 = new javax.swing.JPanel();
        productCategoryPanel1 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jPanel146 = new javax.swing.JPanel();
        jPanel164 = new javax.swing.JPanel();
        jPanel165 = new javax.swing.JPanel();
        checkoutConfirmation = new javax.swing.JDialog();
        jPanel166 = new javax.swing.JPanel();
        jTextField24 = new javax.swing.JTextField();
        jLabel162 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        confirmTotalPrice = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel6 = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        dashboardMainMenu = new MainMenuThumb();
        jLabel1 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel7 = new MainMenuThumb();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ticketMainMenu = new MainMenuThumb();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        salesMainMenu = new MainMenuThumb();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        productMainMenu = new MainMenuThumb();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        mainHeaderPanel = new javax.swing.JPanel();
        menuHeader = new javax.swing.JLabel();
        accountProfilePicture = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        lightRB = new javax.swing.JRadioButtonMenuItem();
        darkRB = new javax.swing.JRadioButtonMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();

        blurBGPanel.setBackground(new java.awt.Color(0, 0, 0));
        blurBGPanel.setOpaque(false);
        blurBGPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blurBGPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout blurBGPanelLayout = new javax.swing.GroupLayout(blurBGPanel);
        blurBGPanel.setLayout(blurBGPanelLayout);
        blurBGPanelLayout.setHorizontalGroup(
            blurBGPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 752, Short.MAX_VALUE)
        );
        blurBGPanelLayout.setVerticalGroup(
            blurBGPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        popupPanel.setBackground(new java.awt.Color(204, 204, 204));
        popupPanel.setOpaque(false);

        jPanel2.setOpaque(false);

        closePopupIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closePopupIcon.setOpaque(false);
        closePopupIcon.setPreferredSize(new java.awt.Dimension(20, 20));
        closePopupIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closePopupIconMouseClicked(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/close-icon-gray.png"))); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout closePopupIconLayout = new javax.swing.GroupLayout(closePopupIcon);
        closePopupIcon.setLayout(closePopupIconLayout);
        closePopupIconLayout.setHorizontalGroup(
            closePopupIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
            .addGroup(closePopupIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(closePopupIconLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        closePopupIconLayout.setVerticalGroup(
            closePopupIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
            .addGroup(closePopupIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(closePopupIconLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        popupContentPanel.setLayout(new javax.swing.BoxLayout(popupContentPanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(closePopupIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(534, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(popupContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(closePopupIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(popupContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );

        popupClosePanel.setBackground(new java.awt.Color(153, 153, 153));
        popupClosePanel.setOpaque(false);
        popupClosePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                popupClosePanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout popupClosePanelLayout = new javax.swing.GroupLayout(popupClosePanel);
        popupClosePanel.setLayout(popupClosePanelLayout);
        popupClosePanelLayout.setHorizontalGroup(
            popupClosePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        popupClosePanelLayout.setVerticalGroup(
            popupClosePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout popupPanelLayout = new javax.swing.GroupLayout(popupPanel);
        popupPanel.setLayout(popupPanelLayout);
        popupPanelLayout.setHorizontalGroup(
            popupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupPanelLayout.createSequentialGroup()
                .addComponent(popupClosePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        popupPanelLayout.setVerticalGroup(
            popupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(popupClosePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, popupPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ticketMainPanel.setPreferredSize(new java.awt.Dimension(848, 582));

        ticketMainTabPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ticketMainTabPaneStateChanged(evt);
            }
        });

        productCategoryPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
        productCategoryPanel.setPreferredSize(new java.awt.Dimension(269, 420));

        jScrollPane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane5.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jScrollPane5.setViewportView(jPanel16);

        javax.swing.GroupLayout productCategoryPanelLayout = new javax.swing.GroupLayout(productCategoryPanel);
        productCategoryPanel.setLayout(productCategoryPanelLayout);
        productCategoryPanelLayout.setHorizontalGroup(
            productCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );
        productCategoryPanelLayout.setVerticalGroup(
            productCategoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(productCategoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productCategoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ticketMainTabPane.addTab("Products", jPanel43);

        jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
        jPanel18.setPreferredSize(new java.awt.Dimension(269, 420));

        jScrollPane9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel30.setText("Create Service");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Customer name");

        jTextField1.setPreferredSize(new java.awt.Dimension(60, 35));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Defects");

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane8.setViewportView(jTextArea1);

        jTextField3.setPreferredSize(new java.awt.Dimension(60, 35));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("Repair cost");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("Status");

        jTextField6.setPreferredSize(new java.awt.Dimension(60, 35));

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setText("Walk-in Date");

        jTextField7.setPreferredSize(new java.awt.Dimension(60, 35));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setText("Estimated Finish");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Progress", "Done" }));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel55)
                    .addComponent(jLabel63))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addGap(3, 3, 3)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addGap(3, 3, 3)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addGap(3, 3, 3)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel55)
                .addGap(3, 3, 3)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel63)
                .addGap(3, 3, 3)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jScrollPane9.setViewportView(jPanel38);

        jButton13.setText("Add to checkout");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setText("Total: 5,300");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel64))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jScrollPane9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel113.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel113ComponentRemoved(evt);
            }
        });
        jPanel113.setLayout(new javax.swing.BoxLayout(jPanel113, javax.swing.BoxLayout.PAGE_AXIS));

        serviceThumb.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 10, 1));
        serviceThumb.setLayout(new javax.swing.BoxLayout(serviceThumb, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel114.setMaximumSize(new java.awt.Dimension(32767, 71));
        jPanel114.setMinimumSize(new java.awt.Dimension(100, 71));
        jPanel114.setOpaque(false);
        jPanel114.setPreferredSize(new java.awt.Dimension(400, 71));

        jPanel120.setOpaque(false);

        jButton17.setText("Set to Done");

        javax.swing.GroupLayout jPanel120Layout = new javax.swing.GroupLayout(jPanel120);
        jPanel120.setLayout(jPanel120Layout);
        jPanel120Layout.setHorizontalGroup(
            jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel120Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jButton17)
                .addGap(15, 15, 15))
        );
        jPanel120Layout.setVerticalGroup(
            jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel120Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(0, 204, 204));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setText("On Progress");

        jPanel121.setOpaque(false);

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel105.setText("Paul Justine");

        jLabel106.setText("Oct 20 2022");

        jLabel108.setText("Order# 1");

        javax.swing.GroupLayout jPanel121Layout = new javax.swing.GroupLayout(jPanel121);
        jPanel121.setLayout(jPanel121Layout);
        jPanel121Layout.setHorizontalGroup(
            jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel121Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel121Layout.setVerticalGroup(
            jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel121Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel108)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel105)
                .addGap(0, 0, 0)
                .addComponent(jLabel106)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel114Layout = new javax.swing.GroupLayout(jPanel114);
        jPanel114.setLayout(jPanel114Layout);
        jPanel114Layout.setHorizontalGroup(
            jPanel114Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel114Layout.createSequentialGroup()
                .addComponent(jPanel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel114Layout.setVerticalGroup(
            jPanel114Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        serviceThumb.add(jPanel114);

        serviceThumbExpand.setOpaque(false);
        serviceThumbExpand.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel118.setText("Defects");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane10.setViewportView(jTextArea2);

        jButton18.setText("Delete");

        jLabel119.setText("Walk-in Date");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Progress", "Done" }));

        jLabel120.setText("Status");

        jLabel121.setText("Customer name");

        jLabel122.setText("Repair cost");

        jLabel123.setText("Estimated Finish");

        jButton19.setBackground(new java.awt.Color(0, 144, 228));
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Save changes");

        javax.swing.GroupLayout serviceThumbExpandLayout = new javax.swing.GroupLayout(serviceThumbExpand);
        serviceThumbExpand.setLayout(serviceThumbExpandLayout);
        serviceThumbExpandLayout.setHorizontalGroup(
            serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel119)
                            .addComponent(jTextField5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123)
                            .addComponent(jTextField14)))
                    .addComponent(jScrollPane10)
                    .addComponent(jTextField12)
                    .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel118)
                            .addComponent(jLabel121)
                            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel120))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel122)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 93, Short.MAX_VALUE)))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        serviceThumbExpandLayout.setVerticalGroup(
            serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel121)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel119)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel118)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(serviceThumbExpandLayout.createSequentialGroup()
                        .addComponent(jLabel120)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel122))
                .addGap(18, 18, 18)
                .addGroup(serviceThumbExpandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        serviceThumb.add(serviceThumbExpand);

        jPanel113.add(serviceThumb);

        jScrollPane16.setViewportView(jPanel113);

        jLabel124.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel124.setText("On Progress Services");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addGap(0, 7, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel124)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel124)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane16))
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        ticketMainTabPane.addTab("Service", jPanel40);

        javax.swing.GroupLayout ticketMainPanelLayout = new javax.swing.GroupLayout(ticketMainPanel);
        ticketMainPanel.setLayout(ticketMainPanelLayout);
        ticketMainPanelLayout.setHorizontalGroup(
            ticketMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ticketMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ticketMainTabPane)
                .addContainerGap())
        );
        ticketMainPanelLayout.setVerticalGroup(
            ticketMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ticketMainTabPane)
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Edit Product");

        jButton1.setBackground(new java.awt.Color(0, 144, 228));
        jButton1.setForeground(new java.awt.Color(240, 240, 240));
        jButton1.setText("Save Changes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        productImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productImageMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Product Name");

        editProductNameTextField.setText("Rolex 230c");
        editProductNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductNameTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Product Category");

        jSeparator1.setForeground(new java.awt.Color(234, 234, 234));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Type");

        editProductType.setEditable(true);
        editProductType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        editProductType.setPreferredSize(new java.awt.Dimension(57, 35));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Brand");

        editProductBrand.setEditable(true);
        editProductBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel33.setLayout(new javax.swing.BoxLayout(jPanel33, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel34.setPreferredSize(new java.awt.Dimension(300, 145));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Pricing");

        jLabel35.setText("Default price");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Inventory Stocks");

        jLabel37.setText("Stocks");

        editProductStockField.setPreferredSize(new java.awt.Dimension(30, 35));

        editProductPriceField.setPreferredSize(new java.awt.Dimension(7, 35));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editProductStockField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editProductPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(421, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(editProductPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(editProductStockField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel33.add(jPanel34);

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Available Options");

        jPanel46.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel46ComponentRemoved(evt);
            }
        });
        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.PAGE_AXIS));

        addProductOptionButton.setBackground(new java.awt.Color(0, 144, 228));
        addProductOptionButton.setForeground(new java.awt.Color(255, 255, 255));
        addProductOptionButton.setText("Add Another");
        addProductOptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductOptionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(addProductOptionButton))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductOptionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel33.add(jPanel35);

        jPanel48.setPreferredSize(new java.awt.Dimension(586, 40));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Variants");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addGap(11, 11, 11))
        );

        jPanel33.add(jPanel48);

        jPanel45.setLayout(new javax.swing.BoxLayout(jPanel45, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
        jPanel44.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel44.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel44.setPreferredSize(new java.awt.Dimension(141, 35));

        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel53.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel52.setText("Variant");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel54.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel66.setText("Price");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel55.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel74.setText("Stocks");

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel72.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel75.setText("Barcode");

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel72, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel72, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel45.add(jPanel44);

        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel52.setLayout(new javax.swing.BoxLayout(jPanel52, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel73.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel73.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel73.setPreferredSize(new java.awt.Dimension(141, 35));

        jCheckBox2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel74.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel76.setText("Black / Leather");

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel77.setPreferredSize(new java.awt.Dimension(150, 14));

        jTextField2.setText("Hello");

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField2)
        );

        jPanel78.setPreferredSize(new java.awt.Dimension(150, 14));

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSpinner3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSpinner3)
        );

        jPanel79.setPreferredSize(new java.awt.Dimension(150, 14));

        jTextField4.setText("Hello");

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel79Layout.createSequentialGroup()
                .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField4)
        );

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel74, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel78, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel52.add(jPanel73);

        jPanel47.add(jPanel52);

        jPanel45.add(jPanel47);

        jPanel33.add(jPanel45);

        jScrollPane4.setViewportView(jPanel33);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout editProductPanelLayout = new javax.swing.GroupLayout(editProductPanel);
        editProductPanel.setLayout(editProductPanelLayout);
        editProductPanelLayout.setHorizontalGroup(
            editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProductPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editProductPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(editProductPanelLayout.createSequentialGroup()
                        .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(editProductBrand, javax.swing.GroupLayout.Alignment.LEADING, 0, 138, Short.MAX_VALUE)
                                .addComponent(editProductType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(22, 22, 22)
                        .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editProductPanelLayout.createSequentialGroup()
                                .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(editProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 367, Short.MAX_VALUE))
                            .addComponent(jSeparator1)
                            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        editProductPanelLayout.setVerticalGroup(
            editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editProductPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editProductPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(editProductPanelLayout.createSequentialGroup()
                        .addComponent(productImage, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(161, Short.MAX_VALUE))))
        );

        viewProductName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        viewProductName.setText("Product Name");

        productImage1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        productImage1.setPreferredSize(new java.awt.Dimension(180, 150));

        jPanel14.setPreferredSize(new java.awt.Dimension(373, 317));

        jButton2.setText("Remove all");

        jButton3.setBackground(new java.awt.Color(0, 144, 228));
        jButton3.setForeground(new java.awt.Color(240, 240, 240));
        jButton3.setText("Add to Checkout");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Total Amount:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("0.00");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setText(" ");

        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.Y_AXIS));

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT);
        flowLayout1.setAlignOnBaseline(true);
        jPanel27.setLayout(flowLayout1);

        jToggleButton2.setText("Red");
        jToggleButton2.setMaximumSize(new java.awt.Dimension(150, 23));
        jToggleButton2.setMinimumSize(new java.awt.Dimension(70, 23));
        jToggleButton2.setPreferredSize(new java.awt.Dimension(80, 40));
        jPanel27.add(jToggleButton2);

        jToggleButton3.setText("afsdfdfdfdf");
        jToggleButton3.setMaximumSize(new java.awt.Dimension(150, 23));
        jToggleButton3.setPreferredSize(new java.awt.Dimension(80, 40));
        jPanel27.add(jToggleButton3);

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Color");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addComponent(jLabel48)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jPanel11.add(jPanel12);

        jScrollPane7.setViewportView(jPanel11);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        jPanel13.setPreferredSize(new java.awt.Dimension(338, 317));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane2.setForeground(new java.awt.Color(153, 153, 153));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel65.setText("Product Name");

        viewProductPrice.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        viewProductPrice.setText("P 1.00");

        jLabel83.setText("Order Amount");

        jSpinner4.setValue(1);

        jPanel81.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel81.setPreferredSize(new java.awt.Dimension(54, 12));
        jPanel81.setLayout(new javax.swing.BoxLayout(jPanel81, javax.swing.BoxLayout.LINE_AXIS));

        viewProductSelectedVariants.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        viewProductSelectedVariants.setText("red/leather");
        jPanel81.add(viewProductSelectedVariants);

        javax.swing.GroupLayout selectedProductThumbPanel1Layout = new javax.swing.GroupLayout(selectedProductThumbPanel1);
        selectedProductThumbPanel1.setLayout(selectedProductThumbPanel1Layout);
        selectedProductThumbPanel1Layout.setHorizontalGroup(
            selectedProductThumbPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectedProductThumbPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(selectedProductThumbPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinner4)
                    .addGroup(selectedProductThumbPanel1Layout.createSequentialGroup()
                        .addGroup(selectedProductThumbPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(viewProductPrice)
                            .addComponent(jLabel83))
                        .addGap(0, 200, Short.MAX_VALUE))
                    .addComponent(jPanel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        selectedProductThumbPanel1Layout.setVerticalGroup(
            selectedProductThumbPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectedProductThumbPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewProductPrice)
                .addGap(10, 10, 10)
                .addComponent(jPanel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel3.add(selectedProductThumbPanel1);

        jScrollPane2.setViewportView(jPanel3);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Selected Variants");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Available Stocks");

        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane6.setForeground(new java.awt.Color(153, 153, 153));

        viewProductVariantTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Red", "1000.20", "In stock: 12"},
                {"Black", "2000", "In stock: 4"},
                {"Gold", "899.50", "In stock: 10"}
            },
            new String [] {
                "Variant", "Price", "Available"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        viewProductVariantTable.setRowHeight(30);
        viewProductVariantTable.setShowGrid(false);
        viewProductVariantTable.setShowVerticalLines(true);
        jScrollPane6.setViewportView(viewProductVariantTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout viewProductPanelLayout = new javax.swing.GroupLayout(viewProductPanel);
        viewProductPanel.setLayout(viewProductPanelLayout);
        viewProductPanelLayout.setHorizontalGroup(
            viewProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewProductPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(viewProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(viewProductPanelLayout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(viewProductPanelLayout.createSequentialGroup()
                        .addComponent(viewProductName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(viewProductPanelLayout.createSequentialGroup()
                        .addComponent(productImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(viewProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(viewProductPanelLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        viewProductPanelLayout.setVerticalGroup(
            viewProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewProductPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(viewProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(viewProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(viewProductPanelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(productImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)))
        );

        salesHistoryPanel.setBackground(new Color(0, 0, 0, 0));

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 144, 228));
        jButton5.setForeground(new java.awt.Color(240, 240, 240));
        jButton5.setText("Edit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "1", "Some Text", "2", "9/1/22", null},
                {null, "2", "test", "4", "123123", null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "ID", "Product", "Orders", "Date", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setFillsViewportHeight(true);
        jTable1.setRowHeight(25);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
        );

        salesHistoryTabPane.addTab("Order History", jPanel9);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 698, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );

        salesHistoryTabPane.addTab("Service History", jPanel8);

        javax.swing.GroupLayout salesHistoryPanelLayout = new javax.swing.GroupLayout(salesHistoryPanel);
        salesHistoryPanel.setLayout(salesHistoryPanelLayout);
        salesHistoryPanelLayout.setHorizontalGroup(
            salesHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salesHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salesHistoryTabPane)
                .addContainerGap())
        );
        salesHistoryPanelLayout.setVerticalGroup(
            salesHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesHistoryTabPane)
        );

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Product Name");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("P 1.00");

        jLabel16.setText("Order Amount");

        jSpinner1.setValue(1);

        jButton6.setText("Cancel");

        jLabel54.setText("red/leather");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel54)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addContainerGap())
        );

        javax.swing.GroupLayout selectedProductThumbPanelLayout = new javax.swing.GroupLayout(selectedProductThumbPanel);
        selectedProductThumbPanel.setLayout(selectedProductThumbPanelLayout);
        selectedProductThumbPanelLayout.setHorizontalGroup(
            selectedProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectedProductThumbPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(selectedProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1)
                    .addGroup(selectedProductThumbPanelLayout.createSequentialGroup()
                        .addGroup(selectedProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(0, 281, Short.MAX_VALUE))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        selectedProductThumbPanelLayout.setVerticalGroup(
            selectedProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectedProductThumbPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addGap(10, 10, 10)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Checkout Tickets");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        checkoutThumbScrollPane.setPreferredSize(new java.awt.Dimension(571, 354));
        checkoutThumbScrollPane.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                checkoutThumbScrollPaneComponentRemoved(evt);
            }
        });
        checkoutThumbScrollPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
        jScrollPane3.setViewportView(checkoutThumbScrollPane);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSeparator3.setForeground(new java.awt.Color(240, 240, 240));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel24.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel24.setPreferredSize(new java.awt.Dimension(200, 35));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Payment");

        checkoutCashThumb.setBackground(new java.awt.Color(255, 102, 102));
        checkoutCashThumb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkoutCashThumb.setPreferredSize(new java.awt.Dimension(98, 92));
        checkoutCashThumb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkoutCashThumbMouseClicked(evt);
            }
        });

        jLabel163.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel163.setText("Cash");

        checkoutCashIconCont.setBackground(new java.awt.Color(204, 204, 204));
        checkoutCashIconCont.setMaximumSize(new java.awt.Dimension(50, 50));
        checkoutCashIconCont.setMinimumSize(new java.awt.Dimension(50, 50));
        checkoutCashIconCont.setPreferredSize(new java.awt.Dimension(45, 45));

        checkoutCashIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout checkoutCashIconContLayout = new javax.swing.GroupLayout(checkoutCashIconCont);
        checkoutCashIconCont.setLayout(checkoutCashIconContLayout);
        checkoutCashIconContLayout.setHorizontalGroup(
            checkoutCashIconContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutCashIconContLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkoutCashIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        checkoutCashIconContLayout.setVerticalGroup(
            checkoutCashIconContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutCashIconContLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkoutCashIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout checkoutCashThumbLayout = new javax.swing.GroupLayout(checkoutCashThumb);
        checkoutCashThumb.setLayout(checkoutCashThumbLayout);
        checkoutCashThumbLayout.setHorizontalGroup(
            checkoutCashThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel163, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(checkoutCashThumbLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(checkoutCashIconCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        checkoutCashThumbLayout.setVerticalGroup(
            checkoutCashThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, checkoutCashThumbLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(checkoutCashIconCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        jLabel161.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel161.setText("Payment method");

        checkoutCreditThumb.setBackground(new java.awt.Color(255, 102, 102));
        checkoutCreditThumb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkoutCreditThumb.setPreferredSize(new java.awt.Dimension(98, 92));
        checkoutCreditThumb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkoutCreditThumbMouseClicked(evt);
            }
        });

        jLabel190.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel190.setText("Credit");

        checkoutCreditIconCont.setBackground(new java.awt.Color(204, 204, 204));
        checkoutCreditIconCont.setMaximumSize(new java.awt.Dimension(50, 50));
        checkoutCreditIconCont.setMinimumSize(new java.awt.Dimension(50, 50));
        checkoutCreditIconCont.setPreferredSize(new java.awt.Dimension(45, 45));

        checkoutCreditIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout checkoutCreditIconContLayout = new javax.swing.GroupLayout(checkoutCreditIconCont);
        checkoutCreditIconCont.setLayout(checkoutCreditIconContLayout);
        checkoutCreditIconContLayout.setHorizontalGroup(
            checkoutCreditIconContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutCreditIconContLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkoutCreditIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        checkoutCreditIconContLayout.setVerticalGroup(
            checkoutCreditIconContLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutCreditIconContLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkoutCreditIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout checkoutCreditThumbLayout = new javax.swing.GroupLayout(checkoutCreditThumb);
        checkoutCreditThumb.setLayout(checkoutCreditThumbLayout);
        checkoutCreditThumbLayout.setHorizontalGroup(
            checkoutCreditThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel190, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(checkoutCreditThumbLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(checkoutCreditIconCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        checkoutCreditThumbLayout.setVerticalGroup(
            checkoutCreditThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, checkoutCreditThumbLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(checkoutCreditIconCont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel190, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel161, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                        .addComponent(checkoutCashThumb, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkoutCreditThumb, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel161)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkoutCashThumb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkoutCreditThumb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        jButton7.setBackground(new java.awt.Color(0, 144, 228));
        jButton7.setForeground(new java.awt.Color(240, 240, 240));
        jButton7.setText("Confirm checkouts");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Cancel all");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Total Amount:");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout checkoutProductPanelLayout = new javax.swing.GroupLayout(checkoutProductPanel);
        checkoutProductPanel.setLayout(checkoutProductPanelLayout);
        checkoutProductPanelLayout.setHorizontalGroup(
            checkoutProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        checkoutProductPanelLayout.setVerticalGroup(
            checkoutProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutProductPanelLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Product Name");

        jLabel22.setText("Items");

        jButton9.setText("Cancel");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("Size");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("P3,400");

        jLabel26.setText("Small");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel26))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout checkoutProductThumbPanelLayout = new javax.swing.GroupLayout(checkoutProductThumbPanel);
        checkoutProductThumbPanel.setLayout(checkoutProductThumbPanelLayout);
        checkoutProductThumbPanelLayout.setHorizontalGroup(
            checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                        .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        checkoutProductThumbPanelLayout.setVerticalGroup(
            checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(checkoutProductThumbPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(checkoutProductThumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        productThumb.setPreferredSize(new java.awt.Dimension(220, 95));

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setText("Rolex xxx");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel59.setText("In Stock:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel60.setText("20");

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel59)
                .addGap(0, 0, 0)
                .addComponent(jLabel60)
                .addGap(7, 7, 7))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/favorite-icon-gray.png"))); // NOI18N

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pencil-icon-gray.png"))); // NOI18N

        javax.swing.GroupLayout productThumbLayout = new javax.swing.GroupLayout(productThumb);
        productThumb.setLayout(productThumbLayout);
        productThumbLayout.setHorizontalGroup(
            productThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productThumbLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(productThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57)
                    .addGroup(productThumbLayout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(138, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productThumbLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        productThumbLayout.setVerticalGroup(
            productThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productThumbLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addGap(5, 5, 5)
                .addGroup(productThumbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        testCategoryCont.setBackground(new java.awt.Color(204, 204, 204));
        testCategoryCont.setLayout(new javax.swing.BoxLayout(testCategoryCont, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel57.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
        jPanel57.setPreferredSize(new java.awt.Dimension(313, 160));
        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel53.setText("Variant Name");
        jLabel53.setPreferredSize(new java.awt.Dimension(64, 20));

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel57.add(jPanel58);

        jTextField8.setPreferredSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel57.add(jPanel59);

        jPanel60.setPreferredSize(new java.awt.Dimension(105, 20));

        jLabel68.setText("Variant Value");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel60Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel57.add(jPanel60);

        jPanel61.setLayout(new javax.swing.BoxLayout(jPanel61, javax.swing.BoxLayout.PAGE_AXIS));

        jTextField9.setPreferredSize(new java.awt.Dimension(60, 35));

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel61.add(jPanel62);

        jPanel57.add(jPanel61);

        jPanel63.setPreferredSize(new java.awt.Dimension(146, 57));

        jButton14.setText("Done");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/trash-icon-gray.png"))); // NOI18N

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel57.add(jPanel63);

        testCategoryCont.add(jPanel57);

        jPanel64.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
        jPanel64.setPreferredSize(new java.awt.Dimension(313, 160));
        jPanel64.setLayout(new javax.swing.BoxLayout(jPanel64, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel70.setText("Variant Name");
        jLabel70.setPreferredSize(new java.awt.Dimension(64, 20));

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel64.add(jPanel65);

        jTextField10.setPreferredSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel66Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel64.add(jPanel66);

        jPanel67.setPreferredSize(new java.awt.Dimension(105, 20));

        jLabel71.setText("Variant Value");

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel64.add(jPanel67);

        jPanel68.setLayout(new javax.swing.BoxLayout(jPanel68, javax.swing.BoxLayout.PAGE_AXIS));

        jTextField11.setPreferredSize(new java.awt.Dimension(60, 35));

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel69Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel68.add(jPanel69);

        jPanel64.add(jPanel68);

        jPanel70.setPreferredSize(new java.awt.Dimension(146, 57));

        jButton16.setText("Done");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/trash-icon-gray.png"))); // NOI18N

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel64.add(jPanel70);

        testCategoryCont.add(jPanel64);

        jPanel50.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel50.setPreferredSize(new java.awt.Dimension(405, 60));
        jPanel50.setLayout(new javax.swing.BoxLayout(jPanel50, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel87.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel87.setPreferredSize(new java.awt.Dimension(586, 40));

        jLabel77.setText("Color");

        jButton12.setText("Edit");
        jButton12.setPreferredSize(new java.awt.Dimension(79, 35));

        javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel87Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel50.add(jPanel87);

        jPanel89.setMaximumSize(new java.awt.Dimension(32767, 55));
        jPanel89.setPreferredSize(new java.awt.Dimension(586, 35));

        jScrollPane13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane13.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel90.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel90.setLayout(new javax.swing.BoxLayout(jPanel90, javax.swing.BoxLayout.LINE_AXIS));

        jPanel91.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("jLabel65");
        jLabel78.setMinimumSize(new java.awt.Dimension(70, 30));
        jLabel78.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel91.add(jLabel78);

        jPanel90.add(jPanel91);

        jScrollPane13.setViewportView(jPanel90);

        javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
        jPanel89.setLayout(jPanel89Layout);
        jPanel89Layout.setHorizontalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel89Layout.setVerticalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel50.add(jPanel89);

        testCategoryCont.add(jPanel50);

        jButton15.setText("Add new Ticket");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jScrollBar1.setOrientation(javax.swing.JScrollBar.HORIZONTAL);

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel75Layout.createSequentialGroup()
                .addGap(0, 73, Short.MAX_VALUE)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane11.setViewportView(jTable3);

        productThumb1.setPreferredSize(new java.awt.Dimension(220, 95));

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel125.setText("Rolex xxx");

        jLabel127.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel127.setText("In Stock:");

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel128.setText("20");

        javax.swing.GroupLayout jPanel122Layout = new javax.swing.GroupLayout(jPanel122);
        jPanel122.setLayout(jPanel122Layout);
        jPanel122Layout.setHorizontalGroup(
            jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel122Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel127)
                .addGap(0, 0, 0)
                .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );
        jPanel122Layout.setVerticalGroup(
            jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel122Layout.createSequentialGroup()
                .addGroup(jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/favorite-icon-gray.png"))); // NOI18N

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pencil-icon-gray.png"))); // NOI18N

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setPreferredSize(new java.awt.Dimension(220, 120));

        jButton22.setBackground(new java.awt.Color(51, 51, 51));
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Add to Checkout");
        jButton22.setPreferredSize(new java.awt.Dimension(79, 40));

        jSpinner5.setPreferredSize(new java.awt.Dimension(64, 38));

        javax.swing.GroupLayout productThumb1Layout = new javax.swing.GroupLayout(productThumb1);
        productThumb1.setLayout(productThumb1Layout);
        productThumb1Layout.setHorizontalGroup(
            productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productThumb1Layout.createSequentialGroup()
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productThumb1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(productThumb1Layout.createSequentialGroup()
                        .addComponent(jSpinner5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productThumb1Layout.createSequentialGroup()
                        .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel125)
                            .addGroup(productThumb1Layout.createSequentialGroup()
                                .addComponent(jLabel126)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        productThumb1Layout.setVerticalGroup(
            productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productThumb1Layout.createSequentialGroup()
                .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(productThumb1Layout.createSequentialGroup()
                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel122, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(productThumb1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel126))
        );

        javax.swing.GroupLayout TestPanelLayout = new javax.swing.GroupLayout(TestPanel);
        TestPanel.setLayout(TestPanelLayout);
        TestPanelLayout.setHorizontalGroup(
            TestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testCategoryCont, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(TestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TestPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(productThumb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(testSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(112, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TestPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );
        TestPanelLayout.setVerticalGroup(
            TestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(testCategoryCont, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
            .addGroup(TestPanelLayout.createSequentialGroup()
                .addGroup(TestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(TestPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TestPanelLayout.createSequentialGroup()
                        .addGroup(TestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TestPanelLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(TestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(TestPanelLayout.createSequentialGroup()
                                        .addComponent(testSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(94, 94, 94)))
                                .addGap(130, 130, 130))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TestPanelLayout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(productThumb1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jButton11.setBackground(new java.awt.Color(0, 144, 228));
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Checkout Tickets");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        ticketSearchBar.setBackground(new java.awt.Color(245, 245, 245));

        jButton10.setOpaque(false);

        javax.swing.GroupLayout ticketMainPanelHeaderLayout = new javax.swing.GroupLayout(ticketMainPanelHeader);
        ticketMainPanelHeader.setLayout(ticketMainPanelHeaderLayout);
        ticketMainPanelHeaderLayout.setHorizontalGroup(
            ticketMainPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ticketMainPanelHeaderLayout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(ticketSearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        ticketMainPanelHeaderLayout.setVerticalGroup(
            ticketMainPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ticketSearchBar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        salesHistorySearchBar.setBackground(new java.awt.Color(245, 245, 245));
        salesHistorySearchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesHistorySearchBarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout salesHistoryPanelHeaderLayout = new javax.swing.GroupLayout(salesHistoryPanelHeader);
        salesHistoryPanelHeader.setLayout(salesHistoryPanelHeaderLayout);
        salesHistoryPanelHeaderLayout.setHorizontalGroup(
            salesHistoryPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salesHistoryPanelHeaderLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(salesHistorySearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(269, Short.MAX_VALUE))
        );
        salesHistoryPanelHeaderLayout.setVerticalGroup(
            salesHistoryPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesHistorySearchBar, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel32.setLayout(new javax.swing.BoxLayout(jPanel32, javax.swing.BoxLayout.Y_AXIS));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel44.setText("Watch");
        jLabel44.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        jLabel44.setPreferredSize(new java.awt.Dimension(47, 49));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel32.add(jPanel28);

        jPanel41.setLayout(new javax.swing.BoxLayout(jPanel41, javax.swing.BoxLayout.Y_AXIS));

        jPanel42.setMaximumSize(new java.awt.Dimension(32767, 50));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Something");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel46.setText("22 items");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel45)
                .addGap(0, 0, 0)
                .addComponent(jLabel46)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        jPanel41.add(jPanel36);

        jPanel32.add(jPanel41);

        javax.swing.GroupLayout categorySectionPanelLayout = new javax.swing.GroupLayout(categorySectionPanel);
        categorySectionPanel.setLayout(categorySectionPanelLayout);
        categorySectionPanelLayout.setHorizontalGroup(
            categorySectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
            .addGroup(categorySectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(categorySectionPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        categorySectionPanelLayout.setVerticalGroup(
            categorySectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(categorySectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(categorySectionPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jScrollPane12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel92.setOpaque(false);
        jPanel92.setPreferredSize(new java.awt.Dimension(140, 140));

        jPanel39.setOpaque(false);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/tags-icon-white.png"))); // NOI18N

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setText("Rolex");

        jLabel92.setBackground(new java.awt.Color(153, 153, 153));
        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Top Product");

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("15");

        jLabel91.setBackground(new java.awt.Color(153, 153, 153));
        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel91.setText("/Items Sold");

        javax.swing.GroupLayout jPanel92Layout = new javax.swing.GroupLayout(jPanel92);
        jPanel92.setLayout(jPanel92Layout);
        jPanel92Layout.setHorizontalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel92, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
        );
        jPanel92Layout.setVerticalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel92Layout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel84)
                .addGap(0, 0, 0)
                .addComponent(jLabel92)
                .addGap(18, 18, 18)
                .addGroup(jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel92Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel93.setText("Top Offers");

        jLabel94.setText("Business statistics for this");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Week", "Month", "Year" }));

        jPanel96.setOpaque(false);
        jPanel96.setPreferredSize(new java.awt.Dimension(140, 140));

        jPanel83.setOpaque(false);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("Ewan ko ba");

        jLabel97.setBackground(new java.awt.Color(153, 153, 153));
        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("Top Service");

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("15");

        jLabel99.setBackground(new java.awt.Color(153, 153, 153));
        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel99.setText("/Items Sold");

        javax.swing.GroupLayout jPanel96Layout = new javax.swing.GroupLayout(jPanel96);
        jPanel96.setLayout(jPanel96Layout);
        jPanel96Layout.setHorizontalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel96Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel96Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
        );
        jPanel96Layout.setVerticalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel96Layout.createSequentialGroup()
                .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel96)
                .addGap(0, 0, 0)
                .addComponent(jLabel97)
                .addGap(18, 18, 18)
                .addGroup(jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel96Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel94Layout = new javax.swing.GroupLayout(jPanel94);
        jPanel94.setLayout(jPanel94Layout);
        jPanel94Layout.setHorizontalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel93)
                    .addComponent(jLabel94)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jPanel92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel94Layout.setVerticalGroup(
            jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel94Layout.createSequentialGroup()
                .addGroup(jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel96, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel94Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel94Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel92, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel94Layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jLabel93)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel94)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );

        jPanel110.setPreferredSize(new java.awt.Dimension(218, 120));

        jPanel111.setOpaque(false);
        jPanel111.setPreferredSize(new java.awt.Dimension(230, 120));

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(51, 51, 51));
        jLabel100.setText("Total Sales");

        jLabel101.setForeground(new java.awt.Color(51, 51, 51));
        jLabel101.setText("20% ");

        jLabel102.setBackground(new java.awt.Color(102, 102, 102));
        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(51, 51, 51));
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("20000");

        jPanel112.setOpaque(false);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel112Layout = new javax.swing.GroupLayout(jPanel112);
        jPanel112.setLayout(jPanel112Layout);
        jPanel112Layout.setHorizontalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel112Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel112Layout.setVerticalGroup(
            jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel112Layout.createSequentialGroup()
                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel111Layout = new javax.swing.GroupLayout(jPanel111);
        jPanel111.setLayout(jPanel111Layout);
        jPanel111Layout.setHorizontalGroup(
            jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel111Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel111Layout.createSequentialGroup()
                        .addComponent(jPanel112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel100)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel111Layout.createSequentialGroup()
                        .addGroup(jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel111Layout.setVerticalGroup(
            jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel111Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel112, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel102)
                .addGap(0, 0, 0)
                .addComponent(jLabel101)
                .addGap(18, 18, 18))
        );

        jPanel115.setOpaque(false);
        jPanel115.setPreferredSize(new java.awt.Dimension(218, 120));

        jLabel109.setBackground(new java.awt.Color(153, 153, 153));
        jLabel109.setForeground(new java.awt.Color(153, 153, 153));
        jLabel109.setText("Total Orders");

        jLabel110.setBackground(new java.awt.Color(153, 153, 153));
        jLabel110.setForeground(new java.awt.Color(153, 153, 153));
        jLabel110.setText("20% ");

        jLabel111.setBackground(new java.awt.Color(102, 102, 102));
        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(153, 153, 153));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("20000");

        jPanel116.setOpaque(false);

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel116Layout = new javax.swing.GroupLayout(jPanel116);
        jPanel116.setLayout(jPanel116Layout);
        jPanel116Layout.setHorizontalGroup(
            jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel116Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel116Layout.setVerticalGroup(
            jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel116Layout.createSequentialGroup()
                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel115Layout = new javax.swing.GroupLayout(jPanel115);
        jPanel115.setLayout(jPanel115Layout);
        jPanel115Layout.setHorizontalGroup(
            jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel115Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel115Layout.createSequentialGroup()
                        .addComponent(jPanel116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel109)
                        .addContainerGap(129, Short.MAX_VALUE))
                    .addGroup(jPanel115Layout.createSequentialGroup()
                        .addGroup(jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel115Layout.setVerticalGroup(
            jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel115Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel116, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel111)
                .addGap(0, 0, 0)
                .addComponent(jLabel110)
                .addGap(18, 18, 18))
        );

        jPanel129.setOpaque(false);
        jPanel129.setPreferredSize(new java.awt.Dimension(218, 120));

        jLabel134.setBackground(new java.awt.Color(153, 153, 153));
        jLabel134.setForeground(new java.awt.Color(153, 153, 153));
        jLabel134.setText("Total Service");

        jLabel135.setBackground(new java.awt.Color(153, 153, 153));
        jLabel135.setForeground(new java.awt.Color(153, 153, 153));
        jLabel135.setText("20% ");

        jLabel136.setBackground(new java.awt.Color(102, 102, 102));
        jLabel136.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(153, 153, 153));
        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("20000");

        jPanel130.setOpaque(false);

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel130Layout = new javax.swing.GroupLayout(jPanel130);
        jPanel130.setLayout(jPanel130Layout);
        jPanel130Layout.setHorizontalGroup(
            jPanel130Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel130Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel130Layout.setVerticalGroup(
            jPanel130Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel130Layout.createSequentialGroup()
                .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel129Layout = new javax.swing.GroupLayout(jPanel129);
        jPanel129.setLayout(jPanel129Layout);
        jPanel129Layout.setHorizontalGroup(
            jPanel129Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel129Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel129Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel129Layout.createSequentialGroup()
                        .addComponent(jPanel130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel134)
                        .addContainerGap(127, Short.MAX_VALUE))
                    .addGroup(jPanel129Layout.createSequentialGroup()
                        .addGroup(jPanel129Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel135, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel136, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel129Layout.setVerticalGroup(
            jPanel129Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel129Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel129Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel134, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel130, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel136)
                .addGap(0, 0, 0)
                .addComponent(jLabel135)
                .addGap(18, 18, 18))
        );

        jPanel131.setOpaque(false);
        jPanel131.setPreferredSize(new java.awt.Dimension(218, 120));

        jLabel138.setBackground(new java.awt.Color(153, 153, 153));
        jLabel138.setForeground(new java.awt.Color(153, 153, 153));
        jLabel138.setText("Total Refund");

        jLabel139.setBackground(new java.awt.Color(153, 153, 153));
        jLabel139.setForeground(new java.awt.Color(153, 153, 153));
        jLabel139.setText("20% ");

        jLabel140.setBackground(new java.awt.Color(102, 102, 102));
        jLabel140.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(153, 153, 153));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("20000");

        jPanel132.setOpaque(false);

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel132Layout = new javax.swing.GroupLayout(jPanel132);
        jPanel132.setLayout(jPanel132Layout);
        jPanel132Layout.setHorizontalGroup(
            jPanel132Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel132Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel132Layout.setVerticalGroup(
            jPanel132Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel132Layout.createSequentialGroup()
                .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel131Layout = new javax.swing.GroupLayout(jPanel131);
        jPanel131.setLayout(jPanel131Layout);
        jPanel131Layout.setHorizontalGroup(
            jPanel131Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel131Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel131Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel131Layout.createSequentialGroup()
                        .addComponent(jPanel132, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel138)
                        .addContainerGap(127, Short.MAX_VALUE))
                    .addGroup(jPanel131Layout.createSequentialGroup()
                        .addGroup(jPanel131Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel139, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel140, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel131Layout.setVerticalGroup(
            jPanel131Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel131Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel131Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel138, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel132, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel140)
                .addGap(0, 0, 0)
                .addComponent(jLabel139)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel110Layout = new javax.swing.GroupLayout(jPanel110);
        jPanel110.setLayout(jPanel110Layout);
        jPanel110Layout.setHorizontalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel110Layout.createSequentialGroup()
                .addComponent(jPanel111, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel115, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel129, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel131, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel110Layout.setVerticalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel111, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
            .addComponent(jPanel115, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
            .addComponent(jPanel129, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
            .addComponent(jPanel131, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        dashboardHistogramPanel.setLayout(new javax.swing.BoxLayout(dashboardHistogramPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Welcome back, ");

        jLabel103.setText("Here's what's happening to your store today!");

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel82Layout.createSequentialGroup()
                        .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel103))
                        .addGap(341, 725, Short.MAX_VALUE))
                    .addGroup(jPanel82Layout.createSequentialGroup()
                        .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel94, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dashboardHistogramPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel110, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel103)
                .addGap(10, 10, 10)
                .addComponent(jPanel110, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dashboardHistogramPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );

        jScrollPane12.setViewportView(jPanel82);

        jScrollPane14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane14.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setToolTipText("");

        ringChartPanel.setOpaque(false);
        ringChartPanel.setRequestFocusEnabled(false);
        ringChartPanel.setLayout(new javax.swing.BoxLayout(ringChartPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel113.setText("Sales by category");

        jPanel117.setOpaque(false);
        jPanel117.setLayout(new javax.swing.BoxLayout(jPanel117, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel118.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 10, 1));
        jPanel118.setMaximumSize(new java.awt.Dimension(32767, 55));
        jPanel118.setOpaque(false);
        jPanel118.setPreferredSize(new java.awt.Dimension(278, 50));

        jLabel114.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel114.setText("Watch");

        jPanel119.setPreferredSize(new java.awt.Dimension(30, 35));

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/pie-chart-icon-white.png"))); // NOI18N

        javax.swing.GroupLayout jPanel119Layout = new javax.swing.GroupLayout(jPanel119);
        jPanel119.setLayout(jPanel119Layout);
        jPanel119Layout.setHorizontalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel119Layout.setVerticalGroup(
            jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel116, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel115.setText("20%");

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel117.setText("20000");

        javax.swing.GroupLayout jPanel118Layout = new javax.swing.GroupLayout(jPanel118);
        jPanel118.setLayout(jPanel118Layout);
        jPanel118Layout.setHorizontalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel118Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel119, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        jPanel118Layout.setVerticalGroup(
            jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel118Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel119, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(5, 5, 5))
            .addGroup(jPanel118Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel118Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel118Layout.createSequentialGroup()
                        .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel117.add(jPanel118);

        javax.swing.GroupLayout jPanel93Layout = new javax.swing.GroupLayout(jPanel93);
        jPanel93.setLayout(jPanel93Layout);
        jPanel93Layout.setHorizontalGroup(
            jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel93Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ringChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel93Layout.setVerticalGroup(
            jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel93Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ringChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel117, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jScrollPane14.setViewportView(jPanel93);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane14)
        );

        dashboardTabPane.addTab("Sales Data", jPanel37);

        jScrollPane18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel140.setBackground(new java.awt.Color(243, 247, 250));
        jPanel140.setOpaque(false);

        jPanel139.setOpaque(false);
        jPanel139.setPreferredSize(new java.awt.Dimension(400, 220));

        jLabel158.setText("Send email report every");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Never", "Day", "Week", "Month" }));

        jLabel159.setText("Include list of item sold");

        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Yes");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("No");

        jButton21.setBackground(new java.awt.Color(0, 144, 228));
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Send Now");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jPanel143.setOpaque(false);
        jPanel143.setPreferredSize(new java.awt.Dimension(154, 57));

        jLabel157.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel157.setText("Email Invoice");

        javax.swing.GroupLayout jPanel143Layout = new javax.swing.GroupLayout(jPanel143);
        jPanel143.setLayout(jPanel143Layout);
        jPanel143Layout.setHorizontalGroup(
            jPanel143Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel143Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel157, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel143Layout.setVerticalGroup(
            jPanel143Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel143Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel157, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel139Layout = new javax.swing.GroupLayout(jPanel139);
        jPanel139.setLayout(jPanel139Layout);
        jPanel139Layout.setHorizontalGroup(
            jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel139Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel159)
                        .addGroup(jPanel139Layout.createSequentialGroup()
                            .addComponent(jRadioButton1)
                            .addGap(10, 10, 10)
                            .addComponent(jRadioButton2))
                        .addGroup(jPanel139Layout.createSequentialGroup()
                            .addComponent(jLabel158)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel139Layout.createSequentialGroup()
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)))
                .addContainerGap(202, Short.MAX_VALUE))
            .addComponent(jPanel143, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
        );
        jPanel139Layout.setVerticalGroup(
            jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel139Layout.createSequentialGroup()
                .addComponent(jPanel143, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel158, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel159, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel147.setOpaque(false);
        jPanel147.setPreferredSize(new java.awt.Dimension(400, 126));

        jPanel148.setOpaque(false);
        jPanel148.setPreferredSize(new java.awt.Dimension(0, 57));

        jLabel164.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel164.setText("PDF Report");

        javax.swing.GroupLayout jPanel148Layout = new javax.swing.GroupLayout(jPanel148);
        jPanel148.setLayout(jPanel148Layout);
        jPanel148Layout.setHorizontalGroup(
            jPanel148Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel148Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(311, Short.MAX_VALUE))
        );
        jPanel148Layout.setVerticalGroup(
            jPanel148Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel148Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel164, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "This Day", "This Week", "This Month", "This Year" }));

        jButton23.setBackground(new java.awt.Color(0, 144, 228));
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setText("Print");

        javax.swing.GroupLayout jPanel147Layout = new javax.swing.GroupLayout(jPanel147);
        jPanel147.setLayout(jPanel147Layout);
        jPanel147Layout.setHorizontalGroup(
            jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel148, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
            .addGroup(jPanel147Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel147Layout.setVerticalGroup(
            jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel147Layout.createSequentialGroup()
                .addComponent(jPanel148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel149.setOpaque(false);

        jPanel150.setOpaque(false);

        jLabel165.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel165.setText("Receipt Settings");

        javax.swing.GroupLayout jPanel150Layout = new javax.swing.GroupLayout(jPanel150);
        jPanel150.setLayout(jPanel150Layout);
        jPanel150Layout.setHorizontalGroup(
            jPanel150Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel150Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel165, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel150Layout.setVerticalGroup(
            jPanel150Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel150Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel165, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel166.setText("Store address");

        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });

        jLabel167.setText("Contact No.");

        jLabel168.setText("Include sales person details");

        jRadioButton5.setSelected(true);
        jRadioButton5.setText("Yes");

        jRadioButton6.setText("No");

        javax.swing.GroupLayout jPanel149Layout = new javax.swing.GroupLayout(jPanel149);
        jPanel149.setLayout(jPanel149Layout);
        jPanel149Layout.setHorizontalGroup(
            jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel150, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel149Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel149Layout.createSequentialGroup()
                        .addComponent(jRadioButton5)
                        .addGap(10, 10, 10)
                        .addComponent(jRadioButton6))
                    .addComponent(jLabel168)
                    .addGroup(jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel167)
                        .addComponent(jLabel166)
                        .addComponent(jTextField17, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(jTextField18)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel149Layout.setVerticalGroup(
            jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel149Layout.createSequentialGroup()
                .addComponent(jPanel150, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel166)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel167)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel168)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel140Layout = new javax.swing.GroupLayout(jPanel140);
        jPanel140.setLayout(jPanel140Layout);
        jPanel140Layout.setHorizontalGroup(
            jPanel140Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel140Layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addGroup(jPanel140Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel147, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(jPanel139, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel140Layout.setVerticalGroup(
            jPanel140Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel140Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel147, 132, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel141.setPreferredSize(new java.awt.Dimension(458, 310));

        jLabel160.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel160.setText("Report Data");

        jPanel142.setLayout(new javax.swing.BoxLayout(jPanel142, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel141Layout = new javax.swing.GroupLayout(jPanel141);
        jPanel141.setLayout(jPanel141Layout);
        jPanel141Layout.setHorizontalGroup(
            jPanel141Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel141Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel160, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel142, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel141Layout.setVerticalGroup(
            jPanel141Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel141Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel160)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel142, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel144Layout = new javax.swing.GroupLayout(jPanel144);
        jPanel144.setLayout(jPanel144Layout);
        jPanel144Layout.setHorizontalGroup(
            jPanel144Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel144Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel141, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel144Layout.setVerticalGroup(
            jPanel144Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel144Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jPanel141, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel138Layout = new javax.swing.GroupLayout(jPanel138);
        jPanel138.setLayout(jPanel138Layout);
        jPanel138Layout.setHorizontalGroup(
            jPanel138Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel140, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel144, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel138Layout.setVerticalGroup(
            jPanel138Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel138Layout.createSequentialGroup()
                .addComponent(jPanel144, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel140, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane18.setViewportView(jPanel138);

        javax.swing.GroupLayout jPanel137Layout = new javax.swing.GroupLayout(jPanel137);
        jPanel137.setLayout(jPanel137Layout);
        jPanel137Layout.setHorizontalGroup(
            jPanel137Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel137Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel137Layout.setVerticalGroup(
            jPanel137Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        );

        dashboardTabPane.addTab("Reports", jPanel137);

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(dashboardTabPane)
                .addGap(0, 0, 0))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardTabPane)
        );

        dashboardPanelHeader.setPreferredSize(new java.awt.Dimension(453, 40));

        javax.swing.GroupLayout dashboardPanelHeaderLayout = new javax.swing.GroupLayout(dashboardPanelHeader);
        dashboardPanelHeader.setLayout(dashboardPanelHeaderLayout);
        dashboardPanelHeaderLayout.setHorizontalGroup(
            dashboardPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 453, Short.MAX_VALUE)
        );
        dashboardPanelHeaderLayout.setVerticalGroup(
            dashboardPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel88.setMinimumSize(new java.awt.Dimension(0, 35));
        jPanel88.setPreferredSize(new java.awt.Dimension(580, 350));

        jPanel95.setLayout(new javax.swing.BoxLayout(jPanel95, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel97.setLayout(new javax.swing.BoxLayout(jPanel97, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel56.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
        jPanel56.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel56.setPreferredSize(new java.awt.Dimension(619, 35));

        jPanel71.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel67.setText("Price");

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel76.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel79.setText("Stocks");

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel79, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel80.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel80.setText("Barcode");

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel80, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel84.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel81.setText("Actions");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel81, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel97.add(jPanel56);

        jPanel98.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
        jPanel98.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel98.setMinimumSize(new java.awt.Dimension(0, 35));
        jPanel98.setPreferredSize(new java.awt.Dimension(619, 35));

        jPanel99.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel85.setText("200");

        javax.swing.GroupLayout jPanel99Layout = new javax.swing.GroupLayout(jPanel99);
        jPanel99.setLayout(jPanel99Layout);
        jPanel99Layout.setHorizontalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel99Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel99Layout.setVerticalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel100.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel86.setText("20");

        javax.swing.GroupLayout jPanel100Layout = new javax.swing.GroupLayout(jPanel100);
        jPanel100.setLayout(jPanel100Layout);
        jPanel100Layout.setHorizontalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel100Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel100Layout.setVerticalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel86, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel101.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel87.setText("Barcode");

        javax.swing.GroupLayout jPanel101Layout = new javax.swing.GroupLayout(jPanel101);
        jPanel101.setLayout(jPanel101Layout);
        jPanel101Layout.setHorizontalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel101Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel101Layout.setVerticalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel102.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel88.setText("Actions");

        javax.swing.GroupLayout jPanel102Layout = new javax.swing.GroupLayout(jPanel102);
        jPanel102.setLayout(jPanel102Layout);
        jPanel102Layout.setHorizontalGroup(
            jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel102Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel102Layout.setVerticalGroup(
            jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel98Layout = new javax.swing.GroupLayout(jPanel98);
        jPanel98.setLayout(jPanel98Layout);
        jPanel98Layout.setHorizontalGroup(
            jPanel98Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel98Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        jPanel98Layout.setVerticalGroup(
            jPanel98Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel99, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel102, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel97.add(jPanel98);

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel97.add(jPanel85);

        jScrollPane15.setViewportView(jPanel97);

        jPanel95.add(jScrollPane15);

        jPanel86.setPreferredSize(new java.awt.Dimension(170, 57));

        jPanel103.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
        jPanel103.setMaximumSize(new java.awt.Dimension(32767, 35));

        jPanel104.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel73.setText("Variants");

        javax.swing.GroupLayout jPanel104Layout = new javax.swing.GroupLayout(jPanel104);
        jPanel104.setLayout(jPanel104Layout);
        jPanel104Layout.setHorizontalGroup(
            jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel104Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel73)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel104Layout.setVerticalGroup(
            jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        jPanel105.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));
        jPanel105.setPreferredSize(new java.awt.Dimension(35, 35));

        jCheckBox3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel105Layout = new javax.swing.GroupLayout(jPanel105);
        jPanel105.setLayout(jPanel105Layout);
        jPanel105Layout.setHorizontalGroup(
            jPanel105Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel105Layout.createSequentialGroup()
                .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel105Layout.setVerticalGroup(
            jPanel105Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel103Layout = new javax.swing.GroupLayout(jPanel103);
        jPanel103.setLayout(jPanel103Layout);
        jPanel103Layout.setHorizontalGroup(
            jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel103Layout.createSequentialGroup()
                .addComponent(jPanel105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel103Layout.setVerticalGroup(
            jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel103Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel103Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel105, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel104, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel106.setBackground(new java.awt.Color(204, 204, 204));
        jPanel106.setPreferredSize(new java.awt.Dimension(0, 300));
        jPanel106.setLayout(new javax.swing.BoxLayout(jPanel106, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel107.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
        jPanel107.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel107.setPreferredSize(new java.awt.Dimension(35, 35));

        jPanel108.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jCheckBox4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel108Layout = new javax.swing.GroupLayout(jPanel108);
        jPanel108.setLayout(jPanel108Layout);
        jPanel108Layout.setHorizontalGroup(
            jPanel108Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel108Layout.createSequentialGroup()
                .addComponent(jCheckBox4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel108Layout.setVerticalGroup(
            jPanel108Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel109.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(234, 234, 234)));

        jLabel89.setText("Black / Leather");

        javax.swing.GroupLayout jPanel109Layout = new javax.swing.GroupLayout(jPanel109);
        jPanel109.setLayout(jPanel109Layout);
        jPanel109Layout.setHorizontalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel109Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
        );
        jPanel109Layout.setVerticalGroup(
            jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel107Layout = new javax.swing.GroupLayout(jPanel107);
        jPanel107.setLayout(jPanel107Layout);
        jPanel107Layout.setHorizontalGroup(
            jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel107Layout.createSequentialGroup()
                .addComponent(jPanel108, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel107Layout.setVerticalGroup(
            jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel106.add(jPanel107);

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel86Layout.createSequentialGroup()
                .addComponent(jPanel103, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel106, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel88Layout = new javax.swing.GroupLayout(jPanel88);
        jPanel88.setLayout(jPanel88Layout);
        jPanel88Layout.setHorizontalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel88Layout.createSequentialGroup()
                .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel88Layout.setVerticalGroup(
            jPanel88Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout customTableLayout = new javax.swing.GroupLayout(customTable);
        customTable.setLayout(customTableLayout);
        customTableLayout.setHorizontalGroup(
            customTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(customTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(customTableLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel88, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        customTableLayout.setVerticalGroup(
            customTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
            .addGroup(customTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(customTableLayout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(jPanel88, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jScrollPane17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel124.setPreferredSize(new java.awt.Dimension(200, 200));

        jLabel156.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel156.setBorder(new CircleBorder());
        jLabel156.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel156MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel124Layout = new javax.swing.GroupLayout(jPanel124);
        jPanel124.setLayout(jPanel124Layout);
        jPanel124Layout.setHorizontalGroup(
            jPanel124Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel156, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel124Layout.setVerticalGroup(
            jPanel124Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel156, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel132.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("Paul Justine Faustino");

        jLabel133.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setText("Employee");

        jButton20.setText("Eidt");

        javax.swing.GroupLayout jPanel123Layout = new javax.swing.GroupLayout(jPanel123);
        jPanel123.setLayout(jPanel123Layout);
        jPanel123Layout.setHorizontalGroup(
            jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel133, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel123Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel123Layout.setVerticalGroup(
            jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel123Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel127.setBackground(new java.awt.Color(243, 247, 250));

        jPanel128.setMinimumSize(new java.awt.Dimension(350, 100));
        jPanel128.setOpaque(false);
        jPanel128.setPreferredSize(new java.awt.Dimension(415, 211));

        jPanel125.setOpaque(false);

        jLabel142.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel142.setText("Basic Information");

        javax.swing.GroupLayout jPanel125Layout = new javax.swing.GroupLayout(jPanel125);
        jPanel125.setLayout(jPanel125Layout);
        jPanel125Layout.setHorizontalGroup(
            jPanel125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel125Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel142)
                .addContainerGap(288, Short.MAX_VALUE))
        );
        jPanel125Layout.setVerticalGroup(
            jPanel125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel125Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel143, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel144.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel144.setText("Age: ");

        jLabel145.setText("Gender:");

        jLabel146.setText("Address: ");

        jTextField15.setEditable(false);
        jTextField15.setText("22");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jTextField16.setText("Iba, wawa");

        javax.swing.GroupLayout jPanel128Layout = new javax.swing.GroupLayout(jPanel128);
        jPanel128.setLayout(jPanel128Layout);
        jPanel128Layout.setHorizontalGroup(
            jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel125, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel128Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel144)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel146)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel145)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(295, Short.MAX_VALUE))
        );
        jPanel128Layout.setVerticalGroup(
            jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel128Layout.createSequentialGroup()
                .addComponent(jPanel125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel144)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel145)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel146)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel133.setOpaque(false);
        jPanel133.setPreferredSize(new java.awt.Dimension(500, 68));

        jPanel134.setBackground(new java.awt.Color(255, 153, 51));
        jPanel134.setForeground(new java.awt.Color(102, 153, 255));
        jPanel134.setOpaque(false);
        jPanel134.setPreferredSize(new java.awt.Dimension(154, 36));

        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel153.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(102, 153, 255));
        jLabel153.setText("Member Since");

        jLabel155.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel155.setText("January 20, 2022");

        javax.swing.GroupLayout jPanel134Layout = new javax.swing.GroupLayout(jPanel134);
        jPanel134.setLayout(jPanel134Layout);
        jPanel134Layout.setHorizontalGroup(
            jPanel134Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel134Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel134Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel134Layout.createSequentialGroup()
                        .addComponent(jLabel153)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel134Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel155, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel134Layout.setVerticalGroup(
            jPanel134Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel134Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel134Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel153, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel135.setBackground(new java.awt.Color(255, 102, 153));
        jPanel135.setOpaque(false);
        jPanel135.setPreferredSize(new java.awt.Dimension(154, 36));

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel150.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(102, 153, 255));
        jLabel150.setText("Time Worked");

        jLabel151.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel151.setText("45 hrs");

        javax.swing.GroupLayout jPanel135Layout = new javax.swing.GroupLayout(jPanel135);
        jPanel135.setLayout(jPanel135Layout);
        jPanel135Layout.setHorizontalGroup(
            jPanel135Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel135Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel135Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel135Layout.createSequentialGroup()
                        .addComponent(jLabel150)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel135Layout.setVerticalGroup(
            jPanel135Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel135Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel135Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel147, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel150, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel151, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel136.setOpaque(false);
        jPanel136.setPreferredSize(new java.awt.Dimension(154, 36));

        jLabel148.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel152.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(102, 153, 255));
        jLabel152.setText("Transactions");

        jLabel154.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel154.setText("15/items sold");

        javax.swing.GroupLayout jPanel136Layout = new javax.swing.GroupLayout(jPanel136);
        jPanel136.setLayout(jPanel136Layout);
        jPanel136Layout.setHorizontalGroup(
            jPanel136Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel136Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel148, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel136Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel136Layout.createSequentialGroup()
                        .addComponent(jLabel152)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel154, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel136Layout.setVerticalGroup(
            jPanel136Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel136Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel136Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel148, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel154, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel133Layout = new javax.swing.GroupLayout(jPanel133);
        jPanel133.setLayout(jPanel133Layout);
        jPanel133Layout.setHorizontalGroup(
            jPanel133Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel133Layout.createSequentialGroup()
                .addComponent(jPanel135, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel136, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel134, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
        );
        jPanel133Layout.setVerticalGroup(
            jPanel133Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel134, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addComponent(jPanel135, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
            .addComponent(jPanel136, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );

        jPanel151.setOpaque(false);

        jPanel152.setOpaque(false);

        jLabel169.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel169.setText("Login Details");

        javax.swing.GroupLayout jPanel152Layout = new javax.swing.GroupLayout(jPanel152);
        jPanel152.setLayout(jPanel152Layout);
        jPanel152Layout.setHorizontalGroup(
            jPanel152Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel152Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel170, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel169, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel152Layout.setVerticalGroup(
            jPanel152Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel152Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel152Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel170, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel169, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel171.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel171.setText("Username");

        jLabel172.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel172.setText("Password");

        jButton24.setBackground(new java.awt.Color(0, 144, 228));
        jButton24.setForeground(new java.awt.Color(255, 255, 255));
        jButton24.setText("Change user & password");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel173.setBackground(new java.awt.Color(204, 204, 204));
        jLabel173.setText("user123");
        jLabel173.setPreferredSize(new java.awt.Dimension(39, 20));

        jLabel174.setBackground(new java.awt.Color(153, 153, 153));
        jLabel174.setText("*Encrypted*");
        jLabel174.setPreferredSize(new java.awt.Dimension(61, 20));

        javax.swing.GroupLayout jPanel151Layout = new javax.swing.GroupLayout(jPanel151);
        jPanel151.setLayout(jPanel151Layout);
        jPanel151Layout.setHorizontalGroup(
            jPanel151Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel152, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel151Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel151Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel173, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel172)
                    .addComponent(jLabel171)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel174, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel151Layout.setVerticalGroup(
            jPanel151Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel151Layout.createSequentialGroup()
                .addComponent(jPanel152, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel171)
                .addGap(1, 1, 1)
                .addComponent(jLabel173, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel172)
                .addGap(0, 0, 0)
                .addComponent(jLabel174, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel127Layout = new javax.swing.GroupLayout(jPanel127);
        jPanel127.setLayout(jPanel127Layout);
        jPanel127Layout.setHorizontalGroup(
            jPanel127Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel127Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel127Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel151, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel133, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                    .addComponent(jPanel128, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel127Layout.setVerticalGroup(
            jPanel127Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel127Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel133, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel128, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel151, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel126Layout = new javax.swing.GroupLayout(jPanel126);
        jPanel126.setLayout(jPanel126Layout);
        jPanel126Layout.setHorizontalGroup(
            jPanel126Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel123, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel126Layout.setVerticalGroup(
            jPanel126Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel126Layout.createSequentialGroup()
                .addComponent(jPanel123, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane17.setViewportView(jPanel126);

        javax.swing.GroupLayout myProfilePanelLayout = new javax.swing.GroupLayout(myProfilePanel);
        myProfilePanel.setLayout(myProfilePanelLayout);
        myProfilePanelLayout.setHorizontalGroup(
            myProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17)
        );
        myProfilePanelLayout.setVerticalGroup(
            myProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
        );

        changeUserPassDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        changeUserPassDialog.setAlwaysOnTop(true);
        changeUserPassDialog.setMinimumSize(new java.awt.Dimension(631, 636));

        jScrollPane19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel156.setPreferredSize(new java.awt.Dimension(0, 0));

        jLabel175.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel175.setText("Change Username & Password");

        jPanel154.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Authentication"));

        jLabel176.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel176.setText("Username");

        jLabel177.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel177.setText("Password");

        jLabel181.setBackground(new java.awt.Color(255, 102, 102));
        jLabel181.setForeground(new java.awt.Color(255, 51, 51));
        jLabel181.setText("Password and Username not match!");

        javax.swing.GroupLayout jPanel154Layout = new javax.swing.GroupLayout(jPanel154);
        jPanel154.setLayout(jPanel154Layout);
        jPanel154Layout.setHorizontalGroup(
            jPanel154Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel154Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel154Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel181, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel154Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel177)
                        .addComponent(jLabel176)
                        .addComponent(jTextField19, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                        .addComponent(jTextField20)))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        jPanel154Layout.setVerticalGroup(
            jPanel154Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel154Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel176)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel177)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel181, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel155.setBorder(javax.swing.BorderFactory.createTitledBorder("New Authentication"));

        jLabel178.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel178.setText("New Username");

        jLabel179.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel179.setText("New Password");

        jLabel180.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel180.setText("Confirm Password");

        jButton25.setBackground(new java.awt.Color(0, 144, 228));
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setText("Save Changes");

        jButton26.setText("Cancel");

        jLabel182.setForeground(new java.awt.Color(255, 51, 51));
        jLabel182.setText("Password not match!");

        javax.swing.GroupLayout jPanel155Layout = new javax.swing.GroupLayout(jPanel155);
        jPanel155.setLayout(jPanel155Layout);
        jPanel155Layout.setHorizontalGroup(
            jPanel155Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel155Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel155Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel155Layout.createSequentialGroup()
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton26))
                    .addComponent(jLabel180)
                    .addComponent(jTextField23)
                    .addComponent(jLabel179)
                    .addComponent(jTextField22)
                    .addComponent(jLabel178)
                    .addComponent(jTextField21)
                    .addComponent(jLabel182, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel155Layout.setVerticalGroup(
            jPanel155Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel155Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel178)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel179)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel180)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel182)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel155Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel153Layout = new javax.swing.GroupLayout(jPanel153);
        jPanel153.setLayout(jPanel153Layout);
        jPanel153Layout.setHorizontalGroup(
            jPanel153Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel154, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel155, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel153Layout.setVerticalGroup(
            jPanel153Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel153Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel154, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel155, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel156Layout = new javax.swing.GroupLayout(jPanel156);
        jPanel156.setLayout(jPanel156Layout);
        jPanel156Layout.setHorizontalGroup(
            jPanel156Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel156Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(jPanel156Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel156Layout.setVerticalGroup(
            jPanel156Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel156Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jScrollPane19.setViewportView(jPanel156);

        javax.swing.GroupLayout changeUserPassDialogLayout = new javax.swing.GroupLayout(changeUserPassDialog.getContentPane());
        changeUserPassDialog.getContentPane().setLayout(changeUserPassDialogLayout);
        changeUserPassDialogLayout.setHorizontalGroup(
            changeUserPassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changeUserPassDialogLayout.createSequentialGroup()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        changeUserPassDialogLayout.setVerticalGroup(
            changeUserPassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );

        notificationDialog.setMinimumSize(new java.awt.Dimension(395, 481));

        jLabel183.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel183.setText("Notification");

        javax.swing.GroupLayout jPanel157Layout = new javax.swing.GroupLayout(jPanel157);
        jPanel157.setLayout(jPanel157Layout);
        jPanel157Layout.setHorizontalGroup(
            jPanel157Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel157Layout.createSequentialGroup()
                .addComponent(jLabel183, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel157Layout.setVerticalGroup(
            jPanel157Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel157Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel183, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel184.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel184.setText("Product is out of stock!");

        jLabel185.setText("10/30/22 | 22:30");

        javax.swing.GroupLayout jPanel159Layout = new javax.swing.GroupLayout(jPanel159);
        jPanel159.setLayout(jPanel159Layout);
        jPanel159Layout.setHorizontalGroup(
            jPanel159Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel159Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel159Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel184, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(jLabel185, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel159Layout.setVerticalGroup(
            jPanel159Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel159Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel184, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel185)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel158Layout = new javax.swing.GroupLayout(jPanel158);
        jPanel158.setLayout(jPanel158Layout);
        jPanel158Layout.setHorizontalGroup(
            jPanel158Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel159, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel158Layout.setVerticalGroup(
            jPanel158Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel158Layout.createSequentialGroup()
                .addComponent(jPanel159, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 350, Short.MAX_VALUE))
        );

        jScrollPane20.setViewportView(jPanel158);

        javax.swing.GroupLayout notificationDialogLayout = new javax.swing.GroupLayout(notificationDialog.getContentPane());
        notificationDialog.getContentPane().setLayout(notificationDialogLayout);
        notificationDialogLayout.setHorizontalGroup(
            notificationDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationDialogLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(notificationDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel157, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane20))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        notificationDialogLayout.setVerticalGroup(
            notificationDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationDialogLayout.createSequentialGroup()
                .addComponent(jPanel157, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane20)
                .addContainerGap())
        );

        notificationPanel.setOpaque(false);

        jPanel160.setOpaque(false);

        jLabel186.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel186.setText("Notification");

        jPanel161.setPreferredSize(new java.awt.Dimension(20, 20));

        jLabel187.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel187.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/close-icon-gray.png"))); // NOI18N
        jLabel187.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel187.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel187MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel161Layout = new javax.swing.GroupLayout(jPanel161);
        jPanel161.setLayout(jPanel161Layout);
        jPanel161Layout.setHorizontalGroup(
            jPanel161Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel187, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );
        jPanel161Layout.setVerticalGroup(
            jPanel161Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel187, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel160Layout = new javax.swing.GroupLayout(jPanel160);
        jPanel160.setLayout(jPanel160Layout);
        jPanel160Layout.setHorizontalGroup(
            jPanel160Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel160Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel160Layout.setVerticalGroup(
            jPanel160Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel160Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jPanel161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel186, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel162.setLayout(new javax.swing.BoxLayout(jPanel162, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel163.setMaximumSize(new java.awt.Dimension(246, 61));
        jPanel163.setMinimumSize(new java.awt.Dimension(246, 61));

        jLabel188.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel188.setText("Product out of stocks!");

        jLabel189.setText("10/30/22 | 23:00");

        javax.swing.GroupLayout jPanel163Layout = new javax.swing.GroupLayout(jPanel163);
        jPanel163.setLayout(jPanel163Layout);
        jPanel163Layout.setHorizontalGroup(
            jPanel163Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel163Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel163Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel189))
                .addContainerGap())
        );
        jPanel163Layout.setVerticalGroup(
            jPanel163Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel163Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel188, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel189)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel162.add(jPanel163);

        jScrollPane21.setViewportView(jPanel162);

        javax.swing.GroupLayout notificationPanelLayout = new javax.swing.GroupLayout(notificationPanel);
        notificationPanel.setLayout(notificationPanelLayout);
        notificationPanelLayout.setHorizontalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel160, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        notificationPanelLayout.setVerticalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel160, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane21))
        );

        productMainPanel.setPreferredSize(new java.awt.Dimension(848, 582));

        ticketMainTabPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ticketMainTabPane1StateChanged(evt);
            }
        });

        productCategoryPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
        productCategoryPanel1.setPreferredSize(new java.awt.Dimension(269, 420));

        jScrollPane22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane22.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane22.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel164.setLayout(new javax.swing.BoxLayout(jPanel164, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout jPanel146Layout = new javax.swing.GroupLayout(jPanel146);
        jPanel146.setLayout(jPanel146Layout);
        jPanel146Layout.setHorizontalGroup(
            jPanel146Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel146Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel164, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );
        jPanel146Layout.setVerticalGroup(
            jPanel146Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel146Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel164, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jScrollPane22.setViewportView(jPanel146);

        javax.swing.GroupLayout productCategoryPanel1Layout = new javax.swing.GroupLayout(productCategoryPanel1);
        productCategoryPanel1.setLayout(productCategoryPanel1Layout);
        productCategoryPanel1Layout.setHorizontalGroup(
            productCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22)
        );
        productCategoryPanel1Layout.setVerticalGroup(
            productCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel145Layout = new javax.swing.GroupLayout(jPanel145);
        jPanel145.setLayout(jPanel145Layout);
        jPanel145Layout.setHorizontalGroup(
            jPanel145Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel145Layout.createSequentialGroup()
                .addComponent(productCategoryPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel165, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
        );
        jPanel145Layout.setVerticalGroup(
            jPanel145Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productCategoryPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
            .addGroup(jPanel145Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel165, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ticketMainTabPane1.addTab("Products", jPanel145);

        javax.swing.GroupLayout productMainPanelLayout = new javax.swing.GroupLayout(productMainPanel);
        productMainPanel.setLayout(productMainPanelLayout);
        productMainPanelLayout.setHorizontalGroup(
            productMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ticketMainTabPane1)
                .addContainerGap())
        );
        productMainPanelLayout.setVerticalGroup(
            productMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ticketMainTabPane1)
        );

        checkoutConfirmation.setTitle("Checkout Confirmation");
        checkoutConfirmation.setAlwaysOnTop(true);
        checkoutConfirmation.setMinimumSize(new java.awt.Dimension(481, 487));

        jLabel162.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel162.setText("Payment Amount");

        jLabel191.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel191.setText("Total Price");

        confirmTotalPrice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        confirmTotalPrice.setText("20000.00");

        jLabel193.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel193.setText("Change");

        jLabel194.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel194.setText("243.00");

        jButton27.setBackground(new java.awt.Color(0, 144, 228));
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setText("Confirm");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setText("Go Back");

        javax.swing.GroupLayout jPanel166Layout = new javax.swing.GroupLayout(jPanel166);
        jPanel166.setLayout(jPanel166Layout);
        jPanel166Layout.setHorizontalGroup(
            jPanel166Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel166Layout.createSequentialGroup()
                .addComponent(jLabel193)
                .addContainerGap(189, Short.MAX_VALUE))
            .addGroup(jPanel166Layout.createSequentialGroup()
                .addGroup(jPanel166Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel194, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmTotalPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel162, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel191, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel166Layout.setVerticalGroup(
            jPanel166Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel166Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel191)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmTotalPrice)
                .addGap(18, 18, 18)
                .addComponent(jLabel193)
                .addGap(8, 8, 8)
                .addComponent(jLabel194)
                .addGap(39, 39, 39)
                .addComponent(jLabel162)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout checkoutConfirmationLayout = new javax.swing.GroupLayout(checkoutConfirmation.getContentPane());
        checkoutConfirmation.getContentPane().setLayout(checkoutConfirmationLayout);
        checkoutConfirmationLayout.setHorizontalGroup(
            checkoutConfirmationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, checkoutConfirmationLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(jPanel166, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );
        checkoutConfirmationLayout.setVerticalGroup(
            checkoutConfirmationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel166, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Point of Sale - TimeSquare");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        menuPanel.setBackground(new java.awt.Color(238, 238, 238));
        menuPanel.setPreferredSize(new java.awt.Dimension(60, 559));

        dashboardMainMenu.setBackground(new java.awt.Color(101, 55, 255));
        dashboardMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dashboardMainMenu.setPreferredSize(new java.awt.Dimension(50, 59));
        dashboardMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMainMenuMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dashboard");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout dashboardMainMenuLayout = new javax.swing.GroupLayout(dashboardMainMenu);
        dashboardMainMenu.setLayout(dashboardMainMenuLayout);
        dashboardMainMenuLayout.setHorizontalGroup(
            dashboardMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardMainMenuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(dashboardMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        dashboardMainMenuLayout.setVerticalGroup(
            dashboardMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.setOpaque(false);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Exit");
        jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/door-open-solid.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TS");

        ticketMainMenu.setBackground(new java.awt.Color(238, 238, 238));
        ticketMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ticketMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ticketMainMenuMouseClicked(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Ticket");
        jLabel32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout ticketMainMenuLayout = new javax.swing.GroupLayout(ticketMainMenu);
        ticketMainMenu.setLayout(ticketMainMenuLayout);
        ticketMainMenuLayout.setHorizontalGroup(
            ticketMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ticketMainMenuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(ticketMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
        );
        ticketMainMenuLayout.setVerticalGroup(
            ticketMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ticketMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        salesMainMenu.setBackground(new java.awt.Color(238, 238, 238));
        salesMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salesMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesMainMenuMouseClicked(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Sales");
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout salesMainMenuLayout = new javax.swing.GroupLayout(salesMainMenu);
        salesMainMenu.setLayout(salesMainMenuLayout);
        salesMainMenuLayout.setHorizontalGroup(
            salesMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salesMainMenuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(salesMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
        );
        salesMainMenuLayout.setVerticalGroup(
            salesMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        productMainMenu.setBackground(new java.awt.Color(238, 238, 238));
        productMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productMainMenuMouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Product");
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout productMainMenuLayout = new javax.swing.GroupLayout(productMainMenu);
        productMainMenu.setLayout(productMainMenuLayout);
        productMainMenuLayout.setHorizontalGroup(
            productMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productMainMenuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(productMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
        );
        productMainMenuLayout.setVerticalGroup(
            productMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(salesMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ticketMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dashboardMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(5, 5, 5))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addGap(25, 25, 25)
                .addComponent(dashboardMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(ticketMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salesMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        contentPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        contentPanel.setLayout(new java.awt.BorderLayout());

        mainHeaderPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));

        menuHeader.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        menuHeader.setText("Dashboard");

        accountProfilePicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accountProfilePicture.setBorder(new CircleBorder());
        accountProfilePicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountProfilePictureMouseClicked(evt);
            }
        });

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/notifications-icon-gray.png"))); // NOI18N
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainHeaderPanelLayout = new javax.swing.GroupLayout(mainHeaderPanel);
        mainHeaderPanel.setLayout(mainHeaderPanelLayout);
        mainHeaderPanelLayout.setHorizontalGroup(
            mainHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainHeaderPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(menuHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(accountProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        mainHeaderPanelLayout.setVerticalGroup(
            mainHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainHeaderPanelLayout.createSequentialGroup()
                .addComponent(menuHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainHeaderPanelLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(mainHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(accountProfilePicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3))
        );

        jPanel17.setPreferredSize(new java.awt.Dimension(70, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 7)); // NOI18N
        jLabel23.setText("Created @ BulSu Hagonoy");

        jProgressBar1.setValue(50);
        jProgressBar1.setIndeterminate(true);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE)
                    .addComponent(mainHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(mainHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLayeredPane1.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenuBar1.setBorder(null);

        jMenu1.setText("Themes");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        themesButtonGroup.add(lightRB);
        lightRB.setSelected(true);
        lightRB.setText("Light ");
        lightRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lightRBActionPerformed(evt);
            }
        });
        jMenu1.add(lightRB);

        themesButtonGroup.add(darkRB);
        darkRB.setText("Dark");
        darkRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                darkRBActionPerformed(evt);
            }
        });
        jMenu1.add(darkRB);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Settings");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Popup Animation");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void darkRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_darkRBActionPerformed
        if(darkRB.isSelected()){
//            FlatAtomOneDarkContrastIJTheme.install();
//            FlatAtomOneDarkContrastIJTheme.updateUI();
            try {
                //                            FlatMaterialLighterIJTheme.install();
                UIManager.setLookAndFeel(new FlatAtomOneDarkContrastIJTheme());
                FlatMaterialLighterIJTheme.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            menuPanel.setBackground(new Color(33, 37, 43));
           
//            jTextField1.setOpaque(false);
//            jTextField1.setBorder(null);
//            
            mainHeaderPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(55, 55, 65)));
            productCategoryPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(55, 55, 65)));
            jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(55, 55, 65)));
//            jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(55, 55, 65)));
            jSeparator3.setForeground(new Color(55, 55, 65));
            ticketSearchBar.setBackground(new Color(40, 40, 50));
            salesHistorySearchBar.setBackground(new Color(40, 40, 50));
            
            jSeparator1.setForeground(new Color(55, 55, 65));
//            jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(55, 55, 65)));
            jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(63, 63, 63)));
            
            dashboardMainMenu.setBackground(new Color(33, 37, 43));
            dashboardMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
            
            ticketMainMenu.setBackground(new Color(33, 37, 43));
            ticketMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
            
            salesMainMenu.setBackground(new Color(33, 37, 43));
            salesMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
            
            productMainMenu.setBackground(new Color(33, 37, 43));
            productMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(63, 63, 63), 1, 25 ) );
            
            FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
            icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                public Color apply(Color t){
                    return new Color(154, 154, 154);
                }
            }));
            jButton10.setIcon(icon);
            
//            jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/barcode-icon-white.png")));
            
            if(selectedMenu == dashboardMainMenu){
                dashboardMainMenu.setBackground(new Color(101,55,255));
            }else if(selectedMenu == ticketMainMenu){
                ticketMainMenu.setBackground(new Color(101,55,255));
            }else if(selectedMenu == salesMainMenu){
                salesMainMenu.setBackground(new Color(101,55,255));
            }else if(selectedMenu == productMainMenu){
                productMainMenu.setBackground(new Color(101,55,255));
            }
            
//            checkoutCashIconCont.setBackground(new Color(50, 55, 64));
//            checkoutCreditIconCont.setBackground(new Color(50, 55, 64));
            resetCheckoutPayment();
            
            updateGraphics();
        }
        
    }//GEN-LAST:event_darkRBActionPerformed

    private void lightRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lightRBActionPerformed
        if(lightRB.isSelected()){

//            FlatMaterialLighterIJTheme.install();
//            FlatMaterialLighterIJTheme.updateUI();
            try {
                //                            FlatMaterialLighterIJTheme.install();
                UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
                FlatMaterialLighterIJTheme.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            menuPanel.setBackground(new Color(238, 238, 238));
//            
//            jTextField1.setOpaque(false);
//            jTextField1.setBorder(null);
            
            mainHeaderPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
            productCategoryPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
            jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
//            jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
            jSeparator3.setForeground(new Color(240, 240, 240));
            
            jSeparator1.setForeground(new Color(240, 240, 240));
//            jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(240, 240, 240)));
            jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
            //Search Field
            ticketSearchBar.setBackground(new Color(245,245,245));
            salesHistorySearchBar.setBackground(new Color(245,245,245));
            
            dashboardMainMenu.setBackground(new Color(238,238,238));
            dashboardMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
            
            ticketMainMenu.setBackground(new Color(238,238,238));
            ticketMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
            
            salesMainMenu.setBackground(new Color(238,238,238));
            salesMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
            
            productMainMenu.setBackground(new Color(238,238,238));
            productMainMenu.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
            
            FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
            icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
                public Color apply(Color t){
                    return new Color(64, 64, 64);
                }
            }));
            jButton10.setIcon(icon);
//            jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/barcode-scanner-icon.png")));
            
            if(selectedMenu == dashboardMainMenu){
                dashboardMainMenu.setBackground(new Color(101,55,255));
            }else if(selectedMenu == ticketMainMenu){
                ticketMainMenu.setBackground(new Color(101,55,255));
            }else if(selectedMenu == salesMainMenu){
                salesMainMenu.setBackground(new Color(101,55,255));
            }else if(selectedMenu == productMainMenu){
                productMainMenu.setBackground(new Color(101,55,255));
            }
            
//            checkoutCashIconCont.setBackground(new Color(240, 240, 240));
//            checkoutCreditIconCont.setBackground(new Color(240, 240, 240));
            resetCheckoutPayment();
            
            updateGraphics();
        }
    }//GEN-LAST:event_lightRBActionPerformed

    private void blurBGPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blurBGPanelMouseClicked
        
    }//GEN-LAST:event_blurBGPanelMouseClicked

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        blurBGPanel.setBounds(0, 0, getWidth(), getHeight());
        popupPanel.setBounds(0, 0, getWidth(), getHeight());
        notificationPanel.setBounds(jLabel56.getX()-210, jLabel56.getY(), 300, 400);
        
        updateGraphics();
    }//GEN-LAST:event_formComponentResized

    private void popupClosePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_popupClosePanelMouseClicked
        blurBGPanel.setVisible(false);
//        popupPanel.setVisible(false);

//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                while(popupPanel.getX() < getWidth()){
//                    popupPanel.setBounds(popupPanel.getX() + 10, 0, getWidth(), getHeight());
//                    try {
//                        Thread.sleep((long) 1);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                popupPanel.setVisible(false);
//            }
//        });
//        t.start();
//        if(!popupPanel.isVisible()) t.stop();
        
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                timer = new Timer(1, new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent ae) {
//                        popupPanel.setBounds(popupPanel.getX() + 30, 0, getWidth(), getHeight());
//
//                        if(popupPanel.getX() > getWidth()){
//                            popupPanel.setBounds(getWidth(), getHeight(), getWidth(), getHeight());
//                            popupPanel.setVisible(false);
//                            timer.stop();
//                        }
//                    }
//                });
//                timer.start();
//            }
//        });
//        t.start();
//        if(!popupPanel.isVisible()) t.stop();
        closePopup();
        
//        System.out.println(contentPanel.getBackground());
    }//GEN-LAST:event_popupClosePanelMouseClicked

    private void closePopupIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closePopupIconMouseClicked
        blurBGPanel.setVisible(false);
//        popupPanel.setVisible(false);

        closePopup();
    }//GEN-LAST:event_closePopupIconMouseClicked

    private void productImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productImageMouseClicked
        JFileChooser chooser = new JFileChooser();
//        chooser.showOpenDialog(null);
        
//        chooser.addChoosableFileFilter(new ImageFilter());
//        chooser.setAcceptAllFileFilterUsed(false);
//        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
//        FileFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "tif");
//        chooser.addChoosableFileFilter(filter);
//FileFilter imageFilter = new FileNameExtensionFilter(
//    "Image files", ImageIO.getReaderFileSuffixes());
//        chooser.setFileFilter(imageFilter);

//        chooser.addChoosableFileFilter(new ImageFilter());
//        chooser.setAcceptAllFileFilterUsed(false);

        selectedFile = null;
        int option = chooser.showOpenDialog(this);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(new File(filename));
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
            
//            BufferedImage rounded = makeRoundedCorner(bufferedImage, 30);
//            Image scaledImage = rounded.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);

//            ImageIO.write(rounded, "png", new File("icon.rounded.png"));
            ImageIcon image = new ImageIcon(scaledImage);
            
//            productImage = new ImageRounded(bufferedImage, 30);
//            productImage.paintImmediately(productImage.getBounds());
                    
            productImage.setIcon(image);
            System.out.println(filename);
            updateGraphics();
            
            System.out.println(getClass().getResource("/img/product/"+filename));
            
            selectedFile = file;
            
//            InputStream inStream = null;
//            OutputStream outStream = null;
//            try{
//                File source =new File(filename);
//                File dest =new File(System.getProperty("user.dir") + "/src/img/product/", file.getName());
//                inStream = new FileInputStream(source);
//                outStream = new FileOutputStream(dest);
//
//                byte[] buffer = new byte[1024];
//
//                int length;
//                while ((length = inStream.read(buffer)) > 0){
//                    outStream.write(buffer, 0, length);
//                }
//
//                if (inStream != null)inStream.close();
//                if (outStream != null)outStream.close();
//                System.out.println("File Copied..");
//            }catch(IOException e1){
//                e1.printStackTrace();
//            }
//            System.out.println("File Loaded: " + file.getName() + "\n\n\n" + "Hit 'Run Code'");
           
        }else{
            System.out.println("Canceled");
        }

        
//        File file = chooser.getSelectedFile();
//        String filename = file.getAbsolutePath();
        
//        System.out.println(filename);
        
//        BufferedImage bufferedImage = null;
//        try {
//            bufferedImage = ImageIO.read(new File(filename));
//        } catch (IOException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Image scaledImage = bufferedImage.getScaledInstance(productImage.getWidth(), productImage.getHeight(), Image.SCALE_SMOOTH);
//        ImageIcon image = new ImageIcon(scaledImage);
//        productImage.setIcon(image);
    }//GEN-LAST:event_productImageMouseClicked

    private void salesMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesMainMenuMouseClicked
        menuHeader.setText("Sales Data");
        contentPanel.removeAll();
        contentPanel.add(salesHistoryPanel);
        
        resetMainMenu();
        FlatSVGIcon salesicon = new FlatSVGIcon("img/icon/sales-icon.svg", 35, 35);
        salesicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel41.setIcon(salesicon);
//        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/sales-icon-white.png")));
        jLabel40.setForeground(Color.white);
        
        updateGraphics();
    }//GEN-LAST:event_salesMainMenuMouseClicked

    private void ticketMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ticketMainMenuMouseClicked
        menuHeader.setText("Create Tickets");
        contentPanel.removeAll();
        contentPanel.add(ticketMainPanel);
        
        resetMainMenu();
        FlatSVGIcon carticon = new FlatSVGIcon("img/icon/cart-icon.svg", 35, 35);
        carticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel33.setIcon(carticon);
//        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/cart-icon-white.png")));
        jLabel32.setForeground(Color.white);
        
        jPanel19.removeAll();
        
        
        //Category test UI Database
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
        });
        
        List<String> sortedType = removeDuplicates(type);
        
        sortedType.forEach(e -> {
            CategorySectionPanel categoryPanel = new CategorySectionPanel();
            categoryPanel.setCategoryType(e);
            
            HashMap<String, Integer> brands = new HashMap();

            categoryService.getAllCategory().forEach(e2->{
                if(e2.getType().equals(e)){

                    if(brands.isEmpty()){
                        brands.put(e2.getBrand(), 1);
                    }else{
                        for (String i : brands.keySet()) {
                            if(i.equals(e2.getBrand())){
                                brands.put(i, brands.get(i) + 1);
                            }else{
                                System.out.println("Added new set");
                                brands.put(e2.getBrand(), 1);
                            }
                        }
                    }
                }
            });
            
            for (String i : brands.keySet()) {
                CategoryThumb thumb = new CategoryThumb(false);
                thumb.setThumbTitle(i);
                thumb.setThumbItems(brands.get(i));
                thumb.setCategoryType(e);
                categoryPanel.addThumb(thumb);
            }

            jPanel19.add(categoryPanel);
        });
        
        updateGraphics();
        
        
    }//GEN-LAST:event_ticketMainMenuMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        repaint();
        revalidate();
        refresh();
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void salesHistorySearchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesHistorySearchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salesHistorySearchBarActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
//        System.out.println("Show cart");
        showPopup(checkoutProductPanel);
//        this.requestFocusInWindow();    
//        refresh();
//        revalidate();
//        repaint();

        //nag bubug yung tab
//        tabPanel.remove(ticketMainPanelHeader);
//        tabPanel.add(ticketMainPanelHeader);

                
        blurBGPanel.setVisible(true);
        popupPanel.setVisible(true);
        
        popupContentPanel.removeAll();
        popupContentPanel.add(checkoutProductPanel);
        
        checkoutThumbScrollPane.removeAll();
        
        
        
        checkoutProduct.forEach((k, e)->{
            ProductService productService = new ProductService();
            Product checkoutSelectedProduct = new Product();
            CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
            checkout.setItems(e.getStocks());
            checkout.setVariants(e.getName());
            checkout.setPrice(e.getPrice() * e.getStocks());
            

            checkoutSelectedProduct = productService.getProductById(k);
            checkout.setProductName(checkoutSelectedProduct.getName());
            checkout.setImage(checkoutSelectedProduct.getImage());
            checkoutThumbScrollPane.add(checkout);
            
            
        });

//        JFrame frame = this;
//        
//        popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                while(popupPanel.getX() > 0){
//                    popupPanel.setBounds(popupPanel.getX() - 15, 0, frame.getWidth(), frame.getHeight());
//                    try {
//                        Thread.sleep((long) 1);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                if(popupPanel.getX() < 0){
//                    popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//                }
////                timer = new Timer(1, new ActionListener() {
////                    @Override
////                    public void actionPerformed(ActionEvent ae) {
//////                        while(popupPanel.getX() > 0){
////                            popupPanel.setBounds(popupPanel.getX() - 50, 0, frame.getWidth(), frame.getHeight());
//////                        }
////                        if(popupPanel.getX() < 0){
////                            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
////                            timer.stop();
////                        }
////                        System.out.println("timer");
////                    }
////                });
////                timer.start();
//            }
//        });
//        t.start();
//        if(popupPanel.getX() <= 0) t.stop();
        
//        JFrame frame = this;
//        popupPanel.setVisible(true);
//        popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
//        
//        timer = new Timer(1, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
////                popupPanel.setBounds(popupPanel.getX() - 100, 0, frame.getWidth(), frame.getHeight());
//                popupPanel.setLocation(popupPanel.getX() - 50, 0);
//                System.out.println(popupPanel.getX());
//                if(popupPanel.getX() < 0){
//                    popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//                    timer.stop();
//                }
//            }
//        });
//        timer.start();
        
        updateGraphics();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
//        this.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
//        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
        this.requestFocusInWindow();        
//        this.requestFocus();

        updateGraphics();
        System.out.println("Focus change");
        System.out.println(this.isFocused());
    }//GEN-LAST:event_jMenu1MouseClicked

    private void productMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productMainMenuMouseClicked
        menuHeader.setText("Product Manager");
        resetMainMenu();
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/product-icon.svg", 35, 35);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel43.setIcon(icon);
        jLabel42.setForeground(Color.white);
        
        contentPanel.removeAll();
        contentPanel.add(productMainPanel);
        
        //Category test UI Database
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
        });
        
        List<String> sortedType = removeDuplicates(type);
        
        jPanel164.removeAll();
        
        sortedType.forEach(e -> {
            CategorySectionPanel categoryPanel = new CategorySectionPanel();
            categoryPanel.setCategoryType(e);
            
            HashMap<String, Integer> brands = new HashMap();

            categoryService.getAllCategory().forEach(e2->{
                if(e2.getType().equals(e)){

                    if(brands.isEmpty()){
                        brands.put(e2.getBrand(), 1);
                    }else{
                        for (String i : brands.keySet()) {
                            if(i.equals(e2.getBrand())){
                                brands.put(i, brands.get(i) + 1);
                            }else{
                                System.out.println("Added new set");
                                brands.put(e2.getBrand(), 1);
                            }
                        }
                    }
                }
            });
            
            for (String i : brands.keySet()) {
                CategoryThumb thumb = new CategoryThumb(true);
                thumb.setThumbTitle(i);
                thumb.setThumbItems(brands.get(i));
                thumb.setCategoryType(e);
                categoryPanel.addThumb(thumb);
            }

            jPanel164.add(categoryPanel);
        });
        
        updateGraphics();
        
    }//GEN-LAST:event_productMainMenuMouseClicked

    private void dashboardMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMainMenuMouseClicked
        // TODO add your handling code here:
        menuHeader.setText("Dashboard");
        contentPanel.removeAll();
        contentPanel.add(dashboardPanel);
        ArrayList<String> items = new ArrayList<String>();
        Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            String item = locales[i].getDisplayName();
            items.add(item);
        }    
        //testSearchField
        setupAutoComplete(ticketSearchBar, items);
        resetMainMenu();
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 32, 32);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(255, 255, 255);
            }
        }));
        jLabel27.setIcon(charticon);
//        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/chart-icon-white.png")));
        jLabel1.setForeground(Color.white);
        
        updateGraphics();
    }//GEN-LAST:event_dashboardMainMenuMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        if(!editProductNameTextField.getText().replaceAll("\\s+","").equals("")){
            
            
            
            ProductService service = new ProductService();

            Product product = new Product();
            Variants variant = new Variants();
            
            File file = selectedFile;
            if(file != null){
                String filename = file.getAbsolutePath();

                InputStream inStream = null;
                OutputStream outStream = null;
                try{
                    File source =new File(filename);
                    File dest =new File(System.getProperty("user.dir") + "/src/img/product/", file.getName());
                    inStream = new FileInputStream(source);
                    outStream = new FileOutputStream(dest);

                    byte[] buffer = new byte[1024];

                    int length;
                    while ((length = inStream.read(buffer)) > 0){
                        outStream.write(buffer, 0, length);
                    }

                    if (inStream != null)inStream.close();
                    if (outStream != null)outStream.close();
                    System.out.println("File Copied..");
                }catch(IOException e1){
                    e1.printStackTrace();
                }
            }

            product.setId(selectedProduct.getId());
            product.setBarcode(12345);
            product.setPrice(editProductPriceField.getText().equals("") ? 0 : Float.parseFloat(editProductPriceField.getText()));
            product.setColor("red");
            product.setStocks(2);
            product.setSize("small");
            product.setName(editProductNameTextField.getText());
            if(file != null){
                File dest =new File(System.getProperty("user.dir") + "/src" + selectedProduct.getImage());
                if (dest.delete()) { 
                    System.out.println("Deleted the file: " + dest.getName());
                } else {
                    System.out.println("Failed to delete the file.");
                } 
                product.setImage("/img/product/" + file.getName());
            }else{
                product.setImage(selectedProduct.getImage());
            }

            service.updateProduct(selectedProduct.getId(), product);

            VariantService vService = new VariantService();
            vService.deleteVariantByProductId(selectedProduct.getId());
            
            CategoryService cs = new CategoryService();
            
            Category category = new Category();
            category.setProduct_id(selectedProduct.getId());
            category.setBrand(editProductBrand.getSelectedItem().toString());
            category.setType(editProductType.getSelectedItem().toString());
            
            cs.updateCategory(selectedProduct.getId(), category);


            addProductVariants.forEach((k, e)->{
                e.forEach(e2 -> {
                    if(!k.equals("generated")){
                        variant.setProduct_id(selectedProduct.getId());
                        variant.setType(k);
                        variant.setName(e2);
                        variant.setMainVariant(1);
                        variant.setPrice(0);
                        variant.setStocks(0);
                        variant.setBarcode(0);

                        System.out.println("Key: " + k + " Val: " + e2);

                        vService.addVariant(variant);
                    }
                });
            });


    //        for (List<String> variantComb : variantCombination) {
    //            
    ////            System.out.println(variantComb.toString());
    ////            if(variantComb.size() > 1){
    ////                String temp="";
    ////                variantComb.forEach(e -> {
    ////                    temp.concat(e);
    ////                });
    ////            }
    //            String str = String.join("/", variantComb);
    //            
    //
    //            
    //            variant.setProduct_id(1);
    //            variant.setType("generated");
    //            variant.setName(str);
    //            variant.setMainVariant(0);
    //            variant.setPrice(0);
    //            variant.setStocks(0);
    //            variant.setBarcode(0);
    //            
    //            vService.addVariant(variant);
    //            System.out.println(str);
    //        }


            productVariants.forEach(e->{
                variant.setProduct_id(selectedProduct.getId());
                variant.setType("generated");
                variant.setName(e.getName());
                variant.setMainVariant(0);
                variant.setPrice(e.getPrice());
                variant.setStocks(e.getStocks());
                variant.setBarcode(e.getBarcode());

                vService.addVariant(variant);
                System.out.println(e.getName());
            });
            editProductNameTextField.putClientProperty("JComponent.outline", "");
            
            closePopup();
            
            
            jPanel30.removeAll();
            
            
            //reset CategoryUI
            jPanel19.removeAll();
            CategoryService categoryService2 = new CategoryService();
            List<String> type = new ArrayList();

            categoryService2.getAllCategory().forEach(e -> {
                type.add(e.getType());
            });

            List<String> sortedType = removeDuplicates(type);

            sortedType.forEach(e -> {
                CategorySectionPanel categoryPanel = new CategorySectionPanel();
                categoryPanel.setCategoryType(e);

                HashMap<String, Integer> brands = new HashMap();

                categoryService2.getAllCategory().forEach(e2->{
                    if(e2.getType().equals(e)){

                        if(brands.isEmpty()){
                            brands.put(e2.getBrand(), 1);
                        }else{
                            for (String i : brands.keySet()) {
                                if(i.equals(e2.getBrand())){
                                    brands.put(i, brands.get(i) + 1);
                                }else{
                                    System.out.println("Added new set");
                                    brands.put(e2.getBrand(), 1);
                                }
                            }
                        }
                    }
                });

                for (String i : brands.keySet()) {
                    CategoryThumb thumb = new CategoryThumb(false);
                    thumb.setThumbTitle(i);
                    thumb.setThumbItems(brands.get(i));
                    thumb.setCategoryType(e);
                    categoryPanel.addThumb(thumb);
                }

                jPanel19.add(categoryPanel);
            });
            
            
            
            
            // Current Selected Category
            if(!selectedCategoryType.equals("") || !selectedCategoryBrand.equals("")){
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllCategory();

                ProductService productsService = new ProductService();
                List<Product>products = productsService.getAllProductDetails();

                categories.forEach(element -> {
                    if(element.getBrand().equals(selectedCategoryBrand) && element.getType().equals(selectedCategoryType)){
                        products.forEach(element2 -> {
                            if(element2.getId() == element.getId()){
                                ProductThumb productThumb = new ProductThumb();
                                productThumb.setProductDetails(element2);
                                jPanel30.add(productThumb);
                                System.out.println(element2.getName() + " Added");
                            }
                        });
                    }
                });
            }else{
                ProductService productsService = new ProductService();
                List<Product>products = productsService.getAllProductDetails();

                products.forEach(e -> {
                    ProductThumb productThumb = new ProductThumb();
                    productThumb.setProductDetails(e);
                    jPanel30.add(productThumb);
                });
            }
            
            
        }else{
            editProductNameTextField.putClientProperty("JComponent.outline", "error");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void addProductOptionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductOptionButtonActionPerformed
        // TODO add your handling code here:
        JPanel panel = new JPanel();
//        panel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234,234,234)));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.LINE_AXIS));
        panel.add(new AddProductOptionPanel());
        jPanel46.add(panel);
        
        updateGraphics();
    }//GEN-LAST:event_addProductOptionButtonActionPerformed

    private void editProductNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductNameTextFieldActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        JTextField jTextField = new JTextField();
        JPanel panel = new JPanel();

        jTextField.setPreferredSize(new java.awt.Dimension(60, 35));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );


        jPanel61.add(panel);
        updateGraphics();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        testCategoryCont.add(new AddProductOptionPanel());
        updateGraphics();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        JTextField jTextField = new JTextField();
        JPanel panel = new JPanel();

        jTextField.setPreferredSize(new java.awt.Dimension(60, 35));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );


        jPanel68.add(panel);
        updateGraphics();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
//        Collection<Receipt> sales = new ArrayList<>();
//        
//        VariantService vs = new VariantService();
//        TransactionHistoryService ths = new TransactionHistoryService();
//        ProductService ps = new ProductService();
//        
//        checkoutProduct.forEach((k, e)->{
//            vs.getProductVariants(k).forEach(e2 -> {
//                if(e2.getMainVariant() == 0){
//                    if(e2.getName().equals(e.getName())){
//                        System.out.println(e.getName() + " Checkout Success!");
//                        TransactionHistory th = new TransactionHistory();
//                        th.setOrders(e.getStocks());
//                        th.setProductId(e.getId());
//                        th.setTotalPrice(e.getPrice() * e.getStocks());
//                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                        Date date = new Date();
//                        th.setTransactionDate(dateFormat.format(date));
//                        ths.addTransactionHistory(th);
//                        
//                        checkoutTotalPrice += e.getPrice() * e.getStocks();
//                        
//                        Product product = ps.getProductById(k);
//                        sales.add(new Receipt(product.getName()+"("+e.getName()+")", e.getStocks(), e.getPrice() * e.getStocks()));
//                    }
//                }
//            });
//        });
//        
//        
//        try{
////            String homeDir = System.getProperty("user.home");
////            String outputFile = homeDir + File.separatorChar + "JasperTest.pdf";
//            
//            Map<String, Object> param = new HashMap<>();
//            
//            System.out.println("total price: " + checkoutTotalPrice);
//
//            param.put("totalPrice", (double) checkoutTotalPrice);
//            param.put("payment", (double) 6000);
//            param.put("change", (double) 1000);
//
////            JasperPrint print = JasperFillManager.fillReport("C:/Users/Acer/Documents/NetBeansProjects/POS_TimeSquare_1/src/test/Black_A4.jrxml", customerName);
//            JasperDesign design = JRXmlLoader.load(new File("src/pos_timesquare/utils/POS_Receipt.jrxml").getAbsolutePath());
//            
//
//            
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sales);
//
//            
//            JasperReport jreport = JasperCompileManager.compileReport(design);
//            
////            JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());
//            JasperPrint print = JasperFillManager.fillReport(jreport, param, dataSource);
//            
//            JasperViewer.viewReport(print);
//            
//        }catch(Exception e){
//            System.out.println(e);
//        }
//        
//        checkoutThumbScrollPane.removeAll();
//        checkoutProduct.clear();
        
        checkoutConfirmation.setVisible(true);
        
        updateGraphics();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        List<String> tempList = new ArrayList<>();
        VariantService vService = new VariantService();
        Variants tempVariants = new Variants();
        
        List<Variants> variants = vService.getProductVariants(selectedProduct.getId());


        viewSelectedVariant.forEach((k,e2)->{
            tempList.add(e2);
        });
        String str = String.join("/", tempList);
        variants.forEach(e->{
            if(e.getMainVariant() == 0){
                if(e.getName().equals(str)){
                    tempVariants.setPrice(e.getPrice());
                }
            }
        });
        
        tempVariants.setId(selectedProduct.getId());
        tempVariants.setName(str);
        tempVariants.setStocks((int) jSpinner4.getValue());
        checkoutProduct.put(selectedProduct.getId(), tempVariants);
        System.out.println(checkoutProduct);
        closePopup();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        updateGraphics();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void checkoutThumbScrollPaneComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_checkoutThumbScrollPaneComponentRemoved
        // TODO add your handling code here:
        updateGraphics();
    }//GEN-LAST:event_checkoutThumbScrollPaneComponentRemoved

    private void jPanel46ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel46ComponentRemoved
        // TODO add your handling code here:
        updateGraphics();
    }//GEN-LAST:event_jPanel46ComponentRemoved

    private void ticketMainTabPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ticketMainTabPaneStateChanged
        System.out.println(ticketMainTabPane.getSelectedIndex());
        switch(ticketMainTabPane.getSelectedIndex()){
            case 0:
//                jPanel19.removeAll();
//                CategoryService categoryService = new CategoryService();
//        
//                List<String> type = new ArrayList();
//
//                categoryService.getAllCategory().forEach(e -> {
//                    type.add(e.getType());
//                });
//
//                List<String> sortedType = removeDuplicates(type);
//
//                sortedType.forEach(e -> {
//                    CategorySectionPanel categoryPanel = new CategorySectionPanel();
//                    categoryPanel.setCategoryType(e);
//
//                    HashMap<String, Integer> brands = new HashMap();
//
//                    categoryService.getAllCategory().forEach(e2->{
//                        if(e2.getType().equals(e)){
//
//                            if(brands.isEmpty()){
//                                brands.put(e2.getBrand(), 1);
//                            }else{
//                                for (String i : brands.keySet()) {
//                                    if(i.equals(e2.getBrand())){
//                                        brands.put(i, brands.get(i) + 1);
//                                    }else{
//                                        System.out.println("Added new set");
//                                        brands.put(e2.getBrand(), 1);
//                                    }
//                                }
//                            }
//                        }
//                    });
//
//                    for (String i : brands.keySet()) {
//                        CategoryThumb thumb = new CategoryThumb();
//                        thumb.setThumbTitle(i);
//                        thumb.setThumbItems(brands.get(i));
//                        thumb.setCategoryType(e);
//                        categoryPanel.addThumb(thumb);
//                    }
//
//                    jPanel19.add(categoryPanel);
//                });
                break;
            case 1: 
                jPanel113.removeAll();
                ServiceTicketsService serviceTicket = new ServiceTicketsService();
                serviceTicket.getAllServiceTicketsDetails().forEach(e->{
                    ServiceThumb sThumb = new ServiceThumb();
                    sThumb.setServiceTicket(e);
                    jPanel113.add(sThumb);
                });
                break;
            default: 
                
        }
    }//GEN-LAST:event_ticketMainTabPaneStateChanged

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        ServiceTicketsService sts = new ServiceTicketsService();
        ServiceTickets st = new ServiceTickets();
        
        st.setCustomerName(jTextField1.getText());
        st.setDefects(jTextArea1.getText());
        st.setPrice(Float.parseFloat(jTextField3.getText()));
        st.setStatus(jComboBox3.getSelectedItem().toString());
        st.setWalkInDate(jTextField6.getText());
        st.setEstimateFinish(jTextField7.getText());
        
        sts.addServiceTickets(st);
        
        ServiceThumb sThumb = new ServiceThumb();
        sThumb.setServiceTicket(st);
        jPanel113.add(sThumb);
        
        jTextField1.setText("");
        jTextArea1.setText("");
        jTextField3.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jComboBox3.setSelectedIndex(0);
        
        updateGraphics();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jPanel113ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel113ComponentRemoved
        // TODO add your handling code here:
        updateGraphics();
    }//GEN-LAST:event_jPanel113ComponentRemoved

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        // TODO add your handling code here:
        if(jCheckBoxMenuItem1.isSelected()){
            
        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void accountProfilePictureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountProfilePictureMouseClicked
        // TODO add your handling code here:
        contentPanel.removeAll();
        contentPanel.add(myProfilePanel);
        updateGraphics();
    }//GEN-LAST:event_accountProfilePictureMouseClicked

    private void jLabel156MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel156MouseClicked
        // TODO add your handling code here:
//        File file = selectedFile;
//        if(file != null){
//            String filename = file.getAbsolutePath();
//
//            InputStream inStream = null;
//            OutputStream outStream = null;
//            try{
//                File source =new File(filename);
//                File dest =new File(System.getProperty("user.dir") + "/src/img/profile/", file.getName());
//                inStream = new FileInputStream(source);
//                outStream = new FileOutputStream(dest);
//
//                byte[] buffer = new byte[1024];
//
//                int length;
//                while ((length = inStream.read(buffer)) > 0){
//                    outStream.write(buffer, 0, length);
//                }
//
//                if (inStream != null)inStream.close();
//                if (outStream != null)outStream.close();
//                System.out.println("File Copied..");
//            }catch(IOException e1){
//                e1.printStackTrace();
//            }
//        }

//        BufferedImage bufferedImage = null;
//        try {
//            bufferedImage = ImageIO.read(getClass().getResource("/img/profile/Faustino_PaulJustine_R.jpg"));
//        } catch (IOException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Image scaledImage = bufferedImage.getScaledInstance(-1, jLabel156.getHeight(), Image.SCALE_SMOOTH);
//
//        ImageIcon image = new ImageIcon(scaledImage);
//
//        jLabel156.setIcon(image);

    }//GEN-LAST:event_jLabel156MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        changeUserPassDialog.setVisible(true);
        changeUserPassDialog.show();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        // TODO add your handling code here:
//        notificationDialog.setVisible(true);
//        notificationDialog.show();

        
        notificationPanel.setVisible(true);
        notificationPanel.setBounds(jLabel56.getX()-210, jLabel56.getY(), 300, 400);

    }//GEN-LAST:event_jLabel56MouseClicked

    private void jLabel187MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel187MouseClicked
        // TODO add your handling code here:
        notificationPanel.setVisible(false);
    }//GEN-LAST:event_jLabel187MouseClicked

    private void ticketMainTabPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ticketMainTabPane1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ticketMainTabPane1StateChanged

    private void checkoutCashThumbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkoutCashThumbMouseClicked
        // TODO add your handling code here:
        
        isCashPayment = true;
        resetCheckoutPayment();
//        if(!darkRB.isSelected()){
//            checkoutCashThumb.setBackground(new Color(240, 240, 240));
//            checkoutCashIconCont.setBackground(contentPanel.getBackground());
//        }else{
//            checkoutCashThumb.setBackground(new Color(50, 55, 64));
//            checkoutCashIconCont.setBackground(contentPanel.getBackground());
//        }
    }//GEN-LAST:event_checkoutCashThumbMouseClicked

    private void checkoutCreditThumbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkoutCreditThumbMouseClicked
        // TODO add your handling code here:
        
        isCashPayment = false;
        resetCheckoutPayment();
//        if(!darkRB.isSelected()){
//            checkoutCreditThumb.setBackground(new Color(240, 240, 240));
//            checkoutCreditIconCont.setBackground(contentPanel.getBackground());
//        }else{
//            checkoutCreditThumb.setBackground(new Color(50, 55, 64));
//            checkoutCreditIconCont.setBackground(contentPanel.getBackground());
//        }
    }//GEN-LAST:event_checkoutCreditThumbMouseClicked

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        Collection<Receipt> sales = new ArrayList<>();
        
        VariantService vs = new VariantService();
        TransactionHistoryService ths = new TransactionHistoryService();
        ProductService ps = new ProductService();
        
        checkoutProduct.forEach((k, e)->{
            vs.getProductVariants(k).forEach(e2 -> {
                if(e2.getMainVariant() == 0){
                    if(e2.getName().equals(e.getName())){
                        System.out.println(e.getName() + " Checkout Success!");
                        TransactionHistory th = new TransactionHistory();
                        th.setOrders(e.getStocks());
                        th.setProductId(e.getId());
                        th.setTotalPrice(e.getPrice() * e.getStocks());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        th.setTransactionDate(dateFormat.format(date));
                        ths.addTransactionHistory(th);
                        
                        checkoutTotalPrice += e.getPrice() * e.getStocks();
                        
                        Product product = ps.getProductById(k);
                        sales.add(new Receipt(product.getName()+"("+e.getName()+")", e.getStocks(), e.getPrice() * e.getStocks()));
                    }
                }
            });
        });
        confirmTotalPrice.setText(String.valueOf(checkoutTotalPrice));
        
        try{
//            String homeDir = System.getProperty("user.home");
//            String outputFile = homeDir + File.separatorChar + "JasperTest.pdf";
            
            Map<String, Object> param = new HashMap<>();
            
            System.out.println("total price: " + checkoutTotalPrice);
            double payment = Double.parseDouble(jTextField24.getText());
            param.put("totalPrice", (double) checkoutTotalPrice);
            param.put("payment", (double) payment);
            param.put("change", (double) payment - checkoutTotalPrice);

//            JasperPrint print = JasperFillManager.fillReport("C:/Users/Acer/Documents/NetBeansProjects/POS_TimeSquare_1/src/test/Black_A4.jrxml", customerName);
            JasperDesign design = JRXmlLoader.load(new File("src/pos_timesquare/utils/POS_Receipt.jrxml").getAbsolutePath());
            

            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sales);

            
            JasperReport jreport = JasperCompileManager.compileReport(design);
            
//            JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());
            JasperPrint print = JasperFillManager.fillReport(jreport, param, dataSource);
            
            JasperViewer.viewReport(print);
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        checkoutThumbScrollPane.removeAll();
        checkoutProduct.clear();
    }//GEN-LAST:event_jButton27ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //        FlatAtomOneDarkContrastIJTheme.install();
        try {
            //                            FlatMaterialLighterIJTheme.install();
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
            FlatMaterialLighterIJTheme.updateUI();
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        UIManager.put( "Button.arc", 18 );
        UIManager.put( "ProgressBar.arc", 999 );
        UIManager.put( "TextComponent.arc", 10 );
        UIManager.put( "Component.arc", 18 );
        UIManager.put( "ScrollBar.thumbArc", 999 );
        UIManager.put( "ScrollBar.thumbInsets", new Insets( 3, 3, 3, 3 ) );
        
        
//        UIManager.put( "ScrollBar.trackArc", 999 );
//        UIManager.put( "ScrollBar.thumbArc", 999 );
//        UIManager.put( "ScrollBar.trackInsets", new Insets( 2, 4, 2, 4 ) );
//        UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
//        UIManager.put( "ScrollBar.track", new Color( 0xe0e0e0 ) );
//        UIManager.put( "Button.innerFocusWidth", 1 );
        
        JFrame.setDefaultLookAndFeelDecorated( true );
        JDialog.setDefaultLookAndFeelDecorated( true );
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
                updateGraphics();
            }
        });
    }
    
    public void closePopup(){
        if(jCheckBoxMenuItem1.isSelected()){
            if(!closePopupActive){
                closePopupActive = true;
                popupPanel.setBounds(0, 0, getWidth(), getHeight());

                blurBGPanel.setVisible(false);
                timer = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        popupPanel.setBounds(popupPanel.getX() + 50, 0, getWidth(), getHeight());

                        if(popupPanel.getX() > getWidth()){
                            popupPanel.setBounds(getWidth(), getHeight(), getWidth(), getHeight());
                            popupPanel.setVisible(false);
                            closePopupActive = false;
                            timer.stop();
                        }
                    }
                });
                timer.start();

                updateGraphics();
            }
        }else{
            popupPanel.setBounds(getWidth(), 0, getWidth(), getHeight());
            popupPanel.setVisible(false);
            blurBGPanel.setVisible(false);
        }
    }

    public void showPopup(Component comp){
        this.requestFocusInWindow();    
//        refresh();
//        revalidate();
//        repaint();
        
        blurBGPanel.setVisible(true);
        popupPanel.setVisible(true);
        
        popupContentPanel.removeAll();
        popupContentPanel.add(comp);

//        JFrame frame = this;
//        popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                while(popupPanel.getX() > 0){
//                    popupPanel.setBounds(popupPanel.getX() - 15, 0, frame.getWidth(), frame.getHeight());
//                    try {
//                        Thread.sleep((long) 1);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                if(popupPanel.getX() < 0){
//                    popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//                }
//            }
//        });
//        t.start();
//        if(popupPanel.getX() <= 0) t.stop();
        if(jCheckBoxMenuItem1.isSelected()){
        JFrame frame = this;
        popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                popupPanel.setBounds(popupPanel.getX() - 50, 0, frame.getWidth(), frame.getHeight());
                
                if(popupPanel.getX() < 0){
                    popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                    updateGraphics();
                    timer.stop();
                }
            }
        });
        timer.start();
        }else{
            JFrame frame = this;
            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        }
        
    }
    
    public void resetCheckoutPayment(){
        
        checkoutCashThumb.setBackground(contentPanel.getBackground());
        checkoutCashThumb.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
        
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/credit-card-regular.svg", 22, 22);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(104, 104, 104);
            }
        }));
        checkoutCashIcon.setIcon(icon);
        if(darkRB.isSelected()){
            checkoutCashIconCont.setBackground(new Color(50, 55, 64));
        }else{
            checkoutCashIconCont.setBackground(new Color(240, 240, 240));
        }
        
        checkoutCashIconCont.putClientProperty( FlatClientProperties.STYLE, "arc: 50" );
        
        checkoutCreditThumb.setBackground(contentPanel.getBackground());
        checkoutCreditThumb.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
        
        FlatSVGIcon icon2 = new FlatSVGIcon("img/icon/hand-holding-dollar-solid.svg", 22, 22);
        icon2.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(104, 104, 104);
            }
        }));
        checkoutCreditIcon.setIcon(icon2);
        
        if(darkRB.isSelected()){
            checkoutCreditIconCont.setBackground(new Color(50, 55, 64));
        }else{
            checkoutCreditIconCont.setBackground(new Color(240, 240, 240));
        }
        
        checkoutCreditIconCont.putClientProperty( FlatClientProperties.STYLE, "arc: 50" );
        
        if(isCashPayment){
            if(!darkRB.isSelected()){
                checkoutCashThumb.setBackground(new Color(240, 240, 240));
                checkoutCashIconCont.setBackground(contentPanel.getBackground());
            }else{
                checkoutCashThumb.setBackground(new Color(50, 55, 64));
                checkoutCashIconCont.setBackground(contentPanel.getBackground());
            }
        }else{
            if(!darkRB.isSelected()){
                checkoutCreditThumb.setBackground(new Color(240, 240, 240));
                checkoutCreditIconCont.setBackground(contentPanel.getBackground());
            }else{
                checkoutCreditThumb.setBackground(new Color(50, 55, 64));
                checkoutCreditIconCont.setBackground(contentPanel.getBackground());
            }
        }
        
        
        updateGraphics();
        
    }
    
    public void resetMainMenu(){
        jLabel32.setForeground(Color.gray);
        jLabel40.setForeground(Color.gray);
        jLabel1.setForeground(Color.gray);
        jLabel42.setForeground(Color.gray);
        
        FlatSVGIcon carticon = new FlatSVGIcon("img/icon/cart-icon.svg", 30, 30);
        carticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(64, 64, 64);
            }
        }));
        jLabel33.setIcon(carticon);
//        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/cart-icon-gray.png")));
        FlatSVGIcon salesicon = new FlatSVGIcon("img/icon/sales-icon.svg", 30, 30);
        salesicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(64, 64, 64);
            }
        }));
        jLabel41.setIcon(salesicon);
//        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/sales-icon-gray.png")));
        
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 30, 30);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(64, 64, 64);
            }
        }));
        jLabel27.setIcon(charticon);
//        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/chart-icon-gray.png")));
        
        FlatSVGIcon producticon = new FlatSVGIcon("img/icon/product-icon.svg", 30, 30);
        producticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>(){
            public Color apply(Color t){
                return new Color(64, 64, 64);
            }
        }));
        jLabel43.setIcon(producticon);
    }
    
    public static void updateGraphics(){
        
//        this.requestFocusInWindow();    
//        refresh();
//        revalidate();
//        repaint();
        if(darkRB.isSelected()){
            UIManager.put( "ScrollBar.thumb", new Color(74, 74, 74) );
        }else{
            UIManager.put( "ScrollBar.thumb", new Color( 0xe0e0e0 ) );
        }
        
        tabPanel.add(ticketMainPanelHeader);

            
        if(darkRB.isSelected()){
            try {
                //                            FlatAtomOneDarkContrastIJTheme.install();
                UIManager.setLookAndFeel(new FlatAtomOneDarkContrastIJTheme());
                FlatAtomOneDarkContrastIJTheme.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                //                            FlatMaterialLighterIJTheme.install();
                UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
                FlatMaterialLighterIJTheme.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Graphics update");
        
    }
    
    public void initStyle(){
        
        blurBGPanel.setVisible(false);
        popupPanel.setVisible(false);
        popupPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        
        jScrollPane4.getVerticalScrollBar().setUnitIncrement(12);
        jScrollPane12.getVerticalScrollBar().setUnitIncrement(12);
        jScrollPane16.getVerticalScrollBar().setUnitIncrement(12);
        jScrollPane17.getVerticalScrollBar().setUnitIncrement(12);
        jScrollPane12.getVerticalScrollBar().setUnitIncrement(12);
        jScrollPane14.getVerticalScrollBar().setUnitIncrement(12);
        
        tabPanel.setLayout(new javax.swing.BoxLayout(tabPanel, javax.swing.BoxLayout.X_AXIS));
        tabPanel.add(ticketMainPanelHeader);
        ticketMainTabPane.putClientProperty("JTabbedPane.trailingComponent", tabPanel);
        ticketMainTabPane.putClientProperty("JTabbedPane.tabHeight", 40);
        
        
        ticketSearchBar.putClientProperty("JComponent.roundRect", true);
        ticketSearchBar.putClientProperty("JTextField.placeholderText", "Search");
        ticketSearchBar.putClientProperty("JTextField.padding", new Insets(2, 9, 2, 9));
//        ticketSearchBar.putClientProperty("JTextField.trailingIcon", new javax.swing.ImageIcon(getClass().getResource("/img/icon/close-icon-gray.png")));
        
        ArrayList<String> items = new ArrayList<String>();
        Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            String item = locales[i].getDisplayName();
            items.add(item);
        }    
        //testSearchField
        setupAutoComplete(ticketSearchBar, items);


        JButton ticketSearch = new JButton();
        ticketSearch.setPreferredSize(new Dimension(35, 35));
        ticketSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/search-icon-gray.png")));
//        testButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 9, 1));
        ticketSearchBar.putClientProperty("JTextField.trailingComponent", ticketSearch);
        
        
        salesHistoryTabPane.putClientProperty("JTabbedPane.trailingComponent", salesHistoryPanelHeader);
        salesHistoryTabPane.putClientProperty("JTabbedPane.tabHeight", 40);
        salesHistorySearchBar.putClientProperty("JComponent.roundRect", true);
        salesHistorySearchBar.putClientProperty("JTextField.placeholderText", "Search");
        salesHistorySearchBar.putClientProperty("JTextField.padding", new Insets(2, 9, 2, 9));
        
        JButton salesSearch = new JButton();
        salesSearch.setPreferredSize(new Dimension(35, 35));
        salesSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/search-icon-gray.png")));
        salesHistorySearchBar.putClientProperty("JTextField.trailingComponent", salesSearch);
        
        
        dashboardTabPane.putClientProperty("JTabbedPane.trailingComponent", dashboardPanelHeader);
        dashboardTabPane.putClientProperty("JTabbedPane.tabHeight", 40);
//        dashboardSearchBar.putClientProperty("JComponent.roundRect", true);
//        dashboardSearchBar.putClientProperty("JTextField.placeholderText", "Search");
//        dashboardSearchBar.putClientProperty("JTextField.padding", new Insets(2, 9, 2, 9));
        
//        jButton3.putClientProperty( "JButton.buttonType", "roundRect" );
//        jPanel113.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
////        productImage.setBackground(new Color(238,238,238));
//        jPanel113.setBorder( new FlatLineBorder( new Insets( 0, 0, 0, 0 ), new Color(203, 203, 203), 1, 25 ) );
//        productImage.putClientProperty( FlatClientProperties.STYLE, "arc: 25" );
//        productImage.setBorder( new FlatLineBorder( new Insets( 10, 10, 10, 10 ), new Color(203, 203, 203), 25, 25 ) );

        ticketMainTabPane1.putClientProperty("JTabbedPane.tabHeight", 40);

    }
    
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel TestPanel;
    private javax.swing.JLabel accountProfilePicture;
    private javax.swing.JButton addProductOptionButton;
    public static javax.swing.JPanel blurBGPanel;
    private javax.swing.JPanel categorySectionPanel;
    private javax.swing.JDialog changeUserPassDialog;
    private javax.swing.JLabel checkoutCashIcon;
    private javax.swing.JPanel checkoutCashIconCont;
    private javax.swing.JPanel checkoutCashThumb;
    private javax.swing.JDialog checkoutConfirmation;
    private javax.swing.JLabel checkoutCreditIcon;
    private javax.swing.JPanel checkoutCreditIconCont;
    private javax.swing.JPanel checkoutCreditThumb;
    private javax.swing.JPanel checkoutProductPanel;
    private javax.swing.JPanel checkoutProductThumbPanel;
    public static javax.swing.JPanel checkoutThumbScrollPane;
    private javax.swing.JPanel closePopupIcon;
    private javax.swing.JLabel confirmTotalPrice;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel customTable;
    public static javax.swing.JRadioButtonMenuItem darkRB;
    private javax.swing.JPanel dashboardHistogramPanel;
    public static javax.swing.JPanel dashboardMainMenu;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel dashboardPanelHeader;
    private javax.swing.JTabbedPane dashboardTabPane;
    public static javax.swing.JComboBox<String> editProductBrand;
    public static javax.swing.JTextField editProductNameTextField;
    public static javax.swing.JPanel editProductPanel;
    public static javax.swing.JTextField editProductPriceField;
    public static javax.swing.JSpinner editProductStockField;
    public static javax.swing.JComboBox<String> editProductType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    public static javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    public static javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel101;
    private javax.swing.JPanel jPanel102;
    private javax.swing.JPanel jPanel103;
    private javax.swing.JPanel jPanel104;
    private javax.swing.JPanel jPanel105;
    private javax.swing.JPanel jPanel106;
    private javax.swing.JPanel jPanel107;
    private javax.swing.JPanel jPanel108;
    private javax.swing.JPanel jPanel109;
    public static javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel111;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel114;
    private javax.swing.JPanel jPanel115;
    private javax.swing.JPanel jPanel116;
    public static javax.swing.JPanel jPanel117;
    private javax.swing.JPanel jPanel118;
    private javax.swing.JPanel jPanel119;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel120;
    private javax.swing.JPanel jPanel121;
    private javax.swing.JPanel jPanel122;
    private javax.swing.JPanel jPanel123;
    private javax.swing.JPanel jPanel124;
    private javax.swing.JPanel jPanel125;
    private javax.swing.JPanel jPanel126;
    private javax.swing.JPanel jPanel127;
    private javax.swing.JPanel jPanel128;
    private javax.swing.JPanel jPanel129;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel130;
    private javax.swing.JPanel jPanel131;
    private javax.swing.JPanel jPanel132;
    private javax.swing.JPanel jPanel133;
    private javax.swing.JPanel jPanel134;
    private javax.swing.JPanel jPanel135;
    private javax.swing.JPanel jPanel136;
    private javax.swing.JPanel jPanel137;
    private javax.swing.JPanel jPanel138;
    private javax.swing.JPanel jPanel139;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel140;
    private javax.swing.JPanel jPanel141;
    private javax.swing.JPanel jPanel142;
    private javax.swing.JPanel jPanel143;
    private javax.swing.JPanel jPanel144;
    private javax.swing.JPanel jPanel145;
    private javax.swing.JPanel jPanel146;
    private javax.swing.JPanel jPanel147;
    private javax.swing.JPanel jPanel148;
    private javax.swing.JPanel jPanel149;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel150;
    private javax.swing.JPanel jPanel151;
    private javax.swing.JPanel jPanel152;
    private javax.swing.JPanel jPanel153;
    private javax.swing.JPanel jPanel154;
    private javax.swing.JPanel jPanel155;
    private javax.swing.JPanel jPanel156;
    private javax.swing.JPanel jPanel157;
    private javax.swing.JPanel jPanel158;
    private javax.swing.JPanel jPanel159;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel160;
    private javax.swing.JPanel jPanel161;
    private javax.swing.JPanel jPanel162;
    private javax.swing.JPanel jPanel163;
    private javax.swing.JPanel jPanel164;
    public static javax.swing.JPanel jPanel165;
    private javax.swing.JPanel jPanel166;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    public static javax.swing.JPanel jPanel45;
    public static javax.swing.JPanel jPanel46;
    public static javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JPanel jPanel99;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    public static javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JRadioButtonMenuItem lightRB;
    private javax.swing.JPanel mainHeaderPanel;
    private javax.swing.JLabel menuHeader;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel myProfilePanel;
    private javax.swing.JDialog notificationDialog;
    private javax.swing.JPanel notificationPanel;
    private javax.swing.JPanel popupClosePanel;
    public static javax.swing.JPanel popupContentPanel;
    public static javax.swing.JPanel popupPanel;
    private javax.swing.JPanel productCategoryPanel;
    private javax.swing.JPanel productCategoryPanel1;
    public static javax.swing.JLabel productImage;
    public static javax.swing.JLabel productImage1;
    public static javax.swing.JPanel productMainMenu;
    private javax.swing.JPanel productMainPanel;
    private javax.swing.JPanel productThumb;
    private javax.swing.JPanel productThumb1;
    private javax.swing.JPanel ringChartPanel;
    private javax.swing.JPanel salesHistoryPanel;
    private javax.swing.JPanel salesHistoryPanelHeader;
    private javax.swing.JTextField salesHistorySearchBar;
    private javax.swing.JTabbedPane salesHistoryTabPane;
    public static javax.swing.JPanel salesMainMenu;
    private javax.swing.JPanel selectedProductThumbPanel;
    private javax.swing.JPanel selectedProductThumbPanel1;
    private javax.swing.JPanel serviceThumb;
    private javax.swing.JPanel serviceThumbExpand;
    private javax.swing.JPanel testCategoryCont;
    private javax.swing.JTextField testSearchField;
    private javax.swing.ButtonGroup themesButtonGroup;
    public static javax.swing.JPanel ticketMainMenu;
    private javax.swing.JPanel ticketMainPanel;
    public static javax.swing.JPanel ticketMainPanelHeader;
    private javax.swing.JTabbedPane ticketMainTabPane;
    private javax.swing.JTabbedPane ticketMainTabPane1;
    private javax.swing.JTextField ticketSearchBar;
    public static javax.swing.JLabel viewProductName;
    public static javax.swing.JPanel viewProductPanel;
    public static javax.swing.JLabel viewProductPrice;
    public static javax.swing.JLabel viewProductSelectedVariants;
    public static javax.swing.JTable viewProductVariantTable;
    // End of variables declaration//GEN-END:variables
}
