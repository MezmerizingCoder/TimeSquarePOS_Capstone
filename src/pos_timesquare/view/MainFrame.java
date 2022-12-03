/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import com.formdev.flatlaf.ui.FlatLineBorder;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import pos_timesquare.controller.CategoryService;
import pos_timesquare.controller.CustomerService;
import pos_timesquare.controller.HttpController;
import pos_timesquare.controller.NotificationService;
import pos_timesquare.controller.ProductService;
import pos_timesquare.controller.ReceiptService;
import pos_timesquare.controller.ReportService;
import pos_timesquare.controller.SalesComputationController;
import pos_timesquare.controller.ServiceTicketsService;
import pos_timesquare.controller.StoreInfoService;
import pos_timesquare.controller.TransactionHistoryService;
import pos_timesquare.controller.UserService;
import pos_timesquare.controller.VariantService;
import pos_timesquare.model.Category;
import pos_timesquare.model.Customer;
import pos_timesquare.model.Notification;
import pos_timesquare.model.Product;
import pos_timesquare.model.Receipt;
import pos_timesquare.model.PDFReport;
import pos_timesquare.model.Reports;
import pos_timesquare.model.ServiceReport;
import pos_timesquare.model.ServiceTickets;
import pos_timesquare.model.StoreInfo;
import pos_timesquare.model.TransactionHistory;
import pos_timesquare.model.User;
import pos_timesquare.model.Variants;
import static pos_timesquare.test.TestCategory.removeDuplicates;
import pos_timesquare.utils.CustomerLineChart;
import pos_timesquare.utils.HistogramPanel;
import pos_timesquare.utils.MultipleLinesChart;
import pos_timesquare.utils.ReportLineChart;
import pos_timesquare.utils.RingChart;
import pos_timesquare.utils.RingChartCustomer;
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
    public static User user = new User();
    public static Product selectedProduct = new Product();
    
    public static HashMap<String, String> viewSelectedVariant;
    public static HashMap<String, List<String>> addProductVariants = new HashMap<>();
    public static Set<List<String>> variantCombination = null;
    public static List<Variants> productVariants = new ArrayList<>();    
    public static HashMap<Integer, List<Variants>> checkoutProduct = new HashMap<>();
    
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
    float highestCostService = 0;
    
    public static boolean closePopupActive = false;
    float totalSales;
    int totalOrders;
    float totalRefund;
    float checkoutTotalPrice;
    
    HashMap<Integer, Integer> productTotalOrder = new HashMap<>();
    public static HashMap<Integer, TransactionHistory> selectedOrderHistory = new HashMap<>();
    String prevDate;
    String prevDate2;
    
    boolean isCashPayment = true;
    boolean editProfileEnable = false;
    boolean isPopupShowed = false;
    
    public static int favoriteCount = 0;
    public static int totalProductCount = 0;
    
    public static User selectedUser;
    public static Customer selectedCustomer;
    
    public static HashMap<Integer, Float> userSalesRank = new HashMap<>();
    
//    public static RingChartCustomer ringChartCustomer = new RingChartCustomer();
    
    public static ButtonGroup buttonGroup = new ButtonGroup();
    
    public static float totalNewCustomer;
    public static float totalRegisteredCustomer;
    
    public static Customer checkoutCustomer;
    public static Customer serviceCustomer;
    
    int unregisteredCustomer = 0;
    int registeredCustomer = 0;
    
    JLabel closeSearchLabel = new JLabel();
    
    public int totalPersonnels = 0;
    
    StoreInfo storeInfo;
    
    BufferedImage barcodeImage;
    
    public static float selectedProductPrice = 0;
    
    public AdminSalesPanel adminSalesPanel = new AdminSalesPanel();
    public AdminServiceTicketPanel adminServiceTicketPanel = new AdminServiceTicketPanel();
    
    CreateNewCustomerPanel createCustomerPanel = new CreateNewCustomerPanel();
    
    
    long loginDateTime;
    
    public class CircleBorder extends AbstractBorder {
        
        private Color color;
        private BasicStroke stroke = null;
        private RenderingHints hints;

        /**
         * Constructor
         */
        CircleBorder() {            
            color = Color.gray;//negro
            stroke = new BasicStroke(1);//grosor del borde
            hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        CircleBorder(Color color, int value) {            
            this.color = color;
            stroke = new BasicStroke(value);            
            hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }        
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            
            Graphics2D g2 = (Graphics2D) g;
            Ellipse2D circle2D = new Ellipse2D.Double();
            if (stroke.getLineWidth() == 0) {
                circle2D.setFrameFromCenter(
                        new Point(x + width / 2, y + height / 2),
                        new Point(width, height)
                );                
            } else {
                
                circle2D.setFrameFromCenter(
                        new Point(x + width / 2, y + height / 2),
                        new Point(width - (int) stroke.getLineWidth(), height - (int) stroke.getLineWidth())
                );                
            }            
            
            Polygon pointer = new Polygon();
            Area area = new Area(circle2D);            
            area.add(new Area(pointer));
            g2.setRenderingHints(hints);
            
            Component parent = c.getParent();
            if (parent != null) {
                Color bg = parent.getBackground();
                Rectangle rect = new Rectangle(0, 0, width, height);
                Area borderRegion = new Area(rect);
                borderRegion.subtract(area);
                g2.setClip(borderRegion);
                g2.setColor(bg);
                g2.fillRect(0, 0, width, height);
                g2.setClip(null);
            }            
            
            if (stroke.getLineWidth() > 0) {
                g2.setColor(color);
                g2.setStroke(stroke);                
            }            
            
            g2.draw(area);
        }
    }
    
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Modify" : value.toString());
            return this;
        }
    }
    
    class ButtonEditor extends DefaultCellEditor {

        private String label;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "Modify" : value.toString();
            button.setText(label);
            button.setBorder(new EmptyBorder(0, 0, 0, 0));
            button.setBackground(new Color(0, 144, 228));
            return button;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }
    
    public MainFrame(User user) {
        
        loginDateTime = Calendar.getInstance().getTime().getTime();
        
        initComponents();
        initStyle();
        salesPersonDetailsBG.add(jRadioButton5);
        salesPersonDetailsBG.add(jRadioButton6);
        
        this.user = user;
        usersName.setText(user.getName());
        
        userNameInput.setText(user.getName());
//        jPanel171.remove(userNameInput);
        
        userNameInput.setVisible(false);
        jButton29.setVisible(false);
        
        jTextField2.setText(user.getContactNum());
        jLabel67.setText(user.getContactNum());
        
        

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            Date dt = sdf.parse(String.valueOf(this.user.getHourWorked()));
            sdf = new SimpleDateFormat("HH:mm");
            System.out.println(sdf.format(dt));
            jLabel151.setText(sdf.format(dt).toString() + " hrs");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        try {
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat format2 = new SimpleDateFormat("MMMM d, yyyy");
            Date date3;
            date3 = format.parse(user.getMembershipDate());
            
            String membershipDate = format2.format(date3);
            jLabel155.setText(membershipDate); 
        } catch (ParseException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        
        
                 
        
        
        accountType.setText(user.getRole().substring(0, 1).toUpperCase() + user.getRole().substring(1));
        
        if(user.getImage() != null){
            BufferedImage profileImage = null;
            try {
                String cwd = System.getProperty("user.dir");
//                        bufferedImage = ImageIO.read(new File(cwd + productDetails.getImage()));
                profileImage = ImageIO.read(new File(cwd + user.getImage()));
//                profileImage = ImageIO.read(getClass().getResource(user.getImage()));
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image profileScaledImage = profileImage.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);

            ImageIcon image3 = new ImageIcon(profileScaledImage);

            jLabel156.setIcon(image3);

            Image scaledImage4 = profileImage.getScaledInstance(-1, accountProfilePicture.getHeight(), Image.SCALE_SMOOTH);
            accountProfilePicture.setIcon(new ImageIcon(scaledImage4));
        }
        
        userAddress.setText(user.getAddress());
        userAddressInput.setText(user.getAddress());
        jPanel170.remove(userAddressInput);
        
        long millis = System.currentTimeMillis();        
        java.sql.Date currentDate = new java.sql.Date(millis);        
        
        LocalDate date = Instant.ofEpochMilli(user.getBirthdate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

//        Period.between(user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
//                date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
        userAge.setText(String.valueOf(Period.between(date, currentDate.toLocalDate()).getYears()));
        jLabel133.setText(this.user.getGender().substring(0, 1).toUpperCase() + this.user.getGender().substring(1));
        
        birthdateChooser.setDate(this.user.getBirthdate());
        jLabel133.setText(DateFormat.getDateInstance().format(birthdateChooser.getDate()));
        
        jPanel168.remove(userAgeInput);
        jPanel169.remove(userGenderCB);
        jPanel172.remove(birthdateChooser);
        jPanel79.remove(jTextField2);
        
        userGender.setText(user.getGender().substring(0, 1).toUpperCase() + user.getGender().substring(1));
        userGenderCB.setSelectedItem(user.getGender().substring(0, 1).toUpperCase() + user.getGender().substring(1));
        jLabel173.setText(user.getUsername());
        
        
        
        jLayeredPane1.add(blurBGPanel, JLayeredPane.MODAL_LAYER);
        jLayeredPane1.add(popupPanel, JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(glassPanel, JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(selectCustomerPopupPanel, JLayeredPane.DRAG_LAYER);
        glassPanel.setVisible(false);
        
        jLayeredPane1.add(notificationPanel, JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(searchPanel, JLayeredPane.DRAG_LAYER);
//        notificationPanel.setBounds(jLabel56.getX()-300, jLabel56.getY(), 300, 400);

        notificationPanel.setVisible(false);

        //Test PopUp
//        popupContentPanel.add(viewProductPanel);
        popupContentPanel.add(editProductPanel);
        popupPanel.setBounds(this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
//        popupContentPanel.add(checkoutProductPanel);

//        ProductService productService = new ProductService();
//        viewProductName.setText(productService.getProductById(1).getName());
//        test = new PopupPanel();
//        test.setBounds(0, 0, 500, 500);
//        jLayeredPane1.add(test, JLayeredPane.POPUP_LAYER);
        contentPanel.add(dashboardPanel);
//        contentPanel.add(myProfilePanel);
//        contentPanel.add(TestPanel);
//        contentPanel.add(personelMainPanel);
//        contentPanel.add(customerMainPanel);
        
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 32, 32);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel27.setIcon(charticon);
        jLabel1.setForeground(Color.white);
        
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(154, 154, 154);
            }
        }));
        jButton10.setIcon(icon);
        jButton31.setIcon(icon);
        jPanel30.setLayout(new WrapLayout());
        jPanel165.setLayout(new WrapLayout());
//        jPanel51.setLayout(new WrapLayout());



        //Test Product Service
        ProductService productsService = new ProductService();
        //Category test UI Database
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
        });
        
        List<String> sortedType = removeDuplicates(type);

        showCategory();

        
        jPanel11.removeAll();
//        jPanel11.add(new ViewProductOptionPanel());

//        TransactionHistoryService transactionHistoryService = new TransactionHistoryService();
//        List<TransactionHistory> transactions = transactionHistoryService.getAllTransactionHistoryDetails();
        dashboardHistogramPanel.add(new HistogramPanel().createAndShowGUI(1));
        
        RingChart ringChart = new RingChart();
        HashMap<String, Float> ringData = new HashMap<>();
        TransactionHistoryService th = new TransactionHistoryService();
        List<TransactionHistory> transactionList = th.getAllTransactionHistoryDetails();

        //total sales
//        HashMap<Integer, Float> productTotalSale
        if (transactionList.size() != 0) {
            transactionList.forEach(e -> {
                if (e.getStatus().equals("Sold")) {
                    totalSales += e.getTotalPrice();
                } else {
                    totalRefund += e.getTotalPrice();
                }
                
                totalOrders += e.getOrders();
                
                if (productTotalOrder.containsKey(e.getProductId())) {
                    int currentOrder = productTotalOrder.get(e.getProductId());
                    productTotalOrder.put(e.getProductId(), currentOrder + e.getOrders());
                } else {
                    productTotalOrder.put(e.getProductId(), e.getOrders());
                }
            });
            
            
        }
        
        jLabel102.setText("₱" + String.valueOf(totalSales));
        jLabel111.setText(String.valueOf(totalOrders));
        jLabel140.setText("₱" + String.valueOf(totalRefund));

        int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
        topProductName.setText(productsService.getProductById(topProductId).getName());
        jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
        
        
        ServiceTicketsService sts = new ServiceTicketsService();
        totalServiceRevenue = 0;
        
        sts.getAllServiceTicketsDetails().forEach(e -> {
            totalServiceRevenue += e.getPrice();
            if(highestCostService < e.getPrice()){
                topServiceName.setText("Service #"+String.valueOf(e.getId()));
                
                jLabel98.setText(String.valueOf(e.getPrice()));
                
                highestCostService = e.getPrice();
            }
        });
        jLabel136.setText("₱" + String.valueOf(totalServiceRevenue));
        
        

        //ring data
        sortedType.forEach(e -> {
            totalRevenue = 0;
            categoryService.getAllCategory().forEach(e2 -> {
                if (e.equals(e2.getType())) {
                    transactionList.forEach(e3 -> {
                        if (e3.getProductId() == e2.getProduct_id()) {
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

        
        FlatSVGIcon creditCardIcon = new FlatSVGIcon("img/icon/credit-card-regular.svg", 12, 12);
        creditCardIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel104.setIcon(creditCardIcon);
        
        FlatSVGIcon listicon = new FlatSVGIcon("img/icon/list-solid.svg", 12, 12);
        listicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel112.setIcon(listicon);
        
        FlatSVGIcon serviceicon = new FlatSVGIcon("img/icon/bolt-solid.svg", 12, 12);
        serviceicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel137.setIcon(serviceicon);
        
        FlatSVGIcon refundicon = new FlatSVGIcon("img/icon/rotate-left-solid.svg", 13, 13);
        refundicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel141.setIcon(refundicon);
        
        FlatSVGIcon topServiceIcon = new FlatSVGIcon("img/icon/bolt-solid.svg", 20, 20);
        topServiceIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel95.setIcon(topServiceIcon);

        jPanel142.add(new ReportLineChart());
        
        resetCheckoutPayment();
        
        jTextField24.getDocument().addDocumentListener(new DocumentListener() {
            
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                float change = Float.parseFloat(jTextField24.getText()) - checkoutTotalPrice;
                jLabel194.setText(String.valueOf(change));
                
                if (Float.parseFloat(jTextField24.getText()) < checkoutTotalPrice) {
                    jButton27.setEnabled(false);
                } else if (Float.parseFloat(jTextField24.getText()) >= checkoutTotalPrice) {
                    jButton27.setEnabled(true);
                } else {
                    jButton27.setEnabled(false);
                }
                
                if (jTextField24.getText().equals("")) {
                    jButton27.setEnabled(false);
                }
            }
            
        });
        
        FlatSVGIcon tagsicon = new FlatSVGIcon("img/icon/tags-solid.svg", 23, 23);
        tagsicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(0, 170, 243);
            }
        }));
        jLabel192.setIcon(tagsicon);
        jLabel210.setIcon(tagsicon);
        
        FlatSVGIcon favoriteicon = new FlatSVGIcon("img/icon/favorite-icon.svg", 23, 23);
        favoriteicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 136, 136);
            }
        }));
        jLabel197.setIcon(favoriteicon);
        jLabel213.setIcon(favoriteicon);

        //Sales product calendar
//        TransactionHistoryService ths = new TransactionHistoryService();
//        if (ths.getAllTransactionHistoryDetails() != null) {
//            ths.getAllTransactionHistoryDetails().forEach(e -> {
//                OrderHistoryThumb oht = new OrderHistoryThumb();
//                oht.setTransactionHistory(e);
//                jPanel178.add(oht);
//            });
//        }
//        
        prevDate = new java.sql.Date(jCalendar1.getDate().getTime()).toString();
        prevDate2 = new java.sql.Date(jCalendar2.getDate().getTime()).toString();
        jPanel181.removeAll();

        //Notification
//        jPanel162.removeAll();
//        NotificationService ns = new NotificationService();
//        ns.getAllNotificationDetails().forEach(e -> {
//            NotificationThumb2 notifThumb = new NotificationThumb2();
//            notifThumb.setNotification(e);
//            
//            jPanel162.add(notifThumb);
//        });
        
        FlatSVGIcon barcodesearch = new FlatSVGIcon("img/icon/magnifying-glass-solid.svg", 30, 30);
        barcodesearch.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(164, 164, 164);
            }
        }));
        jLabel238.setIcon(barcodesearch);
        
        FlatSVGIcon createbarcode = new FlatSVGIcon("img/icon/barcode-solid.svg", 30, 30);
        createbarcode.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(164, 164, 164);
            }
        }));
        jLabel239.setIcon(createbarcode);
        
        StoreInfoService storeInfoService = new StoreInfoService();
        storeInfo = storeInfoService.getStoreInfoDetails();
        
        storeNameInput.setText(storeInfo.getName());
        storeBranchInput.setText(storeInfo.getBranch());
        storeAddressInput.setText(storeInfo.getAddress());
        storeBranchInput.setText(storeInfo.getBranch());
        storeContactNumInput.setText(storeInfo.getContactNum());
        storeEmailInput.setText(storeInfo.getEmail());
        if(storeInfo.getSalesPersonDetail()== 1){
            jRadioButton5.setSelected(true);
        }else{
            jRadioButton6.setSelected(true);
        }

        //Personel Icon
        FlatSVGIcon pactiveicon = new FlatSVGIcon("img/icon/handshake-angle-solid.svg", 18, 18);
        pactiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelActiveIcon.setIcon(pactiveicon);
        
        FlatSVGIcon pdeactiveicon = new FlatSVGIcon("img/icon/handshake-simple-slash-solid.svg", 18, 18);
        pdeactiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelDeactiveIcon.setIcon(pdeactiveicon);
        
        FlatSVGIcon ptotalicon = new FlatSVGIcon("img/icon/users-solid.svg", 22, 18);
        ptotalicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelDeactiveIcon1.setIcon(ptotalicon);
        
//        FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
//        pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
//            public Color apply(Color t) {
//                return new Color(215, 122, 122);
//            }
//        }));
//        jLabel266.setIcon(pnegativeicon);
        
        
        FlatSVGIcon cactiveicon = new FlatSVGIcon("img/icon/user-check-solid.svg", 23, 18);
        cactiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        customerRegisteredIcon.setIcon(cactiveicon);
        
        FlatSVGIcon cunregistericon = new FlatSVGIcon("img/icon/user-xmark-solid.svg", 23, 18);
        cunregistericon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        customerUnregisteredIcon.setIcon(cunregistericon);
        
        FlatSVGIcon customergroupicon = new FlatSVGIcon("img/icon/user-group-solid.svg", 23, 18);
        customergroupicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelDeactiveIcon3.setIcon(customergroupicon);
        
        
        
        
        
        FlatSVGIcon phoneicon = new FlatSVGIcon("img/icon/phone-solid.svg", 15, 15);
        phoneicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(126, 180, 177);
            }
        }));
        jLabel24.setIcon(phoneicon);
        
//        FlatSVGIcon banicon = new FlatSVGIcon("img/icon/ban-solid.svg", 15, 15);
//        banicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
//            public Color apply(Color t) {
//                return new Color(230, 76, 76);
//            }
//        }));
//        jLabel315.setIcon(banicon);
//        
        jDateChooser3.setVisible(true);
        jMonthChooser2.setVisible(false);
        jYearChooser3.setVisible(false);
        
        jScrollPane34.setVisible(false);
        jPanel78.setVisible(false);
//        unregisteredCustomerListPanel.add(new CustomerUnregisteredThumb());

        jRadioButton3.setActionCommand("male");
        jRadioButton4.setActionCommand("female");
        checkoutGenderButtonGroup.add(jRadioButton3);
        checkoutGenderButtonGroup.add(jRadioButton4);
        
        jRadioButton7.setActionCommand("male");
        jRadioButton8.setActionCommand("female");
        customerGenderButtonGroup.add(jRadioButton7);
        customerGenderButtonGroup.add(jRadioButton8);
        
        jScrollPane29.getViewport().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(jTextField18.isShowing()){
                    Point searchPosition2 = jTextField18.getLocationOnScreen();
//                    int searchX2 = (int)searchPosition2.getX();
                    int searchX2 = 100;
                    searchPanel2.setBounds(searchX2, (int)searchPosition2.getY()+20, 270, 200);
                }
            }
            
        });
        jScrollPane30.getViewport().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(jTextField25.isShowing()){
                    Point searchPosition2 = jTextField25.getLocationOnScreen();
//                    int searchX2 = (int)searchPosition2.getX();
                    int searchX2 = 100;
                    searchPanel2.setBounds(searchX2, (int)searchPosition2.getY()+20, 270, 200);
                }
            }
            
        });
        
        
//        WrapLayout wrap = new WrapLayout();
//        wrap.setAlignment(0);
//        jPanel218.setLayout(wrap);
        
        FlatSVGIcon notifexpand = new FlatSVGIcon("img/icon/up-right-and-down-left-from-center-solid.svg", 13, 13);
        notifexpand.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return jLabel3.getForeground();
            }
        }));
        jLabel78.setIcon(notifexpand);
        
        FlatSVGIcon exiticon = new FlatSVGIcon("img/icon/door-open-solid.svg", 25, 25);
        exiticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel29.setIcon(exiticon);
        
        FlatSVGIcon banicon = new FlatSVGIcon("img/icon/ban-solid.svg", 15, 15);
        banicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(230, 76, 76);
            }
        }));
        jLabel309.setIcon(banicon);
        
        createCustomerDialog.add(createCustomerPanel);
        
        
        
        adminSalesPanel = new AdminSalesPanel();
//        jPanel9.add(adminSalesPanel);
        
        

        resetMainMenu();
        if (this.user.getRole().equals("admin")) {
//            dashboardMainMenu.setVisible(true);
            adminMenuPanel.setVisible(true);
            charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
                    return new Color(255, 255, 255);
                }
            }));
            jLabel27.setIcon(charticon);
    //        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/chart-icon-white.png")));
            jLabel1.setForeground(Color.white);
            contentPanel.add(dashboardPanel);
        } else {
//            dashboardMainMenu.setVisible(false);
            adminMenuPanel.setVisible(false);
            
            selectedMenu = ticketMainMenu;
            updateTicketPanel();
            ticketMainMenu.setBackground(new Color(101, 55, 255));
            ticketMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
//            FlatSVGIcon carticon = new FlatSVGIcon("img/icon/cart-icon.svg", 35, 35);
//            carticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
//                public Color apply(Color t) {
//                    return new Color(255, 255, 255);
//                }
//            }));
//            ticketMainMenu.setBackground(new Color(101, 55, 255));
//            ticketMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
//            jLabel33.setIcon(carticon);
//            jLabel32.setForeground(Color.white);
//            
//            contentPanel.add(ticketMainPanel);
//            
//            updateGraphics();
            
        }
        salesHistorySearchBar.setVisible(false);
        updateCreateCustomerTicketService();

    }
    
    public MainFrame() {
        
        loginDateTime = Calendar.getInstance().getTime().getTime();
        
        initComponents();
        initStyle();
        salesPersonDetailsBG.add(jRadioButton5);
        salesPersonDetailsBG.add(jRadioButton6);
        
//       cartPanelPopup.setOpaque(false);
//        cartPanelPopup.setBackground(new Color(0,0,0,125));
        jLayeredPane1.add(blurBGPanel, JLayeredPane.MODAL_LAYER);
        jLayeredPane1.add(popupPanel, JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(glassPanel, JLayeredPane.POPUP_LAYER);
        jLayeredPane1.add(selectCustomerPopupPanel, JLayeredPane.DRAG_LAYER);
//        checkoutCustomerSelectedPanel.setBounds(100, 0, 300, 270);
        
        jLayeredPane1.add(notificationPanel, JLayeredPane.PALETTE_LAYER);
        jLayeredPane1.add(searchPanel, JLayeredPane.DRAG_LAYER);
        jLayeredPane1.add(searchPanel2, JLayeredPane.DRAG_LAYER);
        
        notificationPanel.setVisible(false);
        
//        SearchPanel searchpanel = new SearchPanel();
//        searchpanel.setOpaque(false);
//        searchPanel.add(searchpanel);
        searchPanel.setVisible(false);
        searchPanel2.setVisible(false);
        
//        Rectangle searchPosition = contentPanel.getBounds();
//        int searchX = 28+(contentPanel.getWidth() / 2) - 190;
//        searchPanel.setBounds(searchX, 80, 380, 200);
//        notificationPanel.setBounds(jLabel56.getX()-300, jLabel56.getY(), 300, 400);

        //Test PopUp
//        popupContentPanel.add(viewProductPanel);
        popupContentPanel.add(editProductPanel);
        popupPanel.setBounds(this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        
//        popupContentPanel.add(checkoutProductPanel);

//        ProductService productService = new ProductService();
//        viewProductName.setText(productService.getProductById(1).getName());
//        test = new PopupPanel();
//        test.setBounds(0, 0, 500, 500);
//        jLayeredPane1.add(test, JLayeredPane.POPUP_LAYER);
        contentPanel.add(dashboardPanel);
//        contentPanel.add(myProfilePanel);
//        contentPanel.add(TestPanel);
//        contentPanel.add(personelMainPanel);
//        contentPanel.add(customerMainPanel);
        
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 32, 32);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel27.setIcon(charticon);
        jLabel1.setForeground(Color.white);
        
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(154, 154, 154);
            }
        }));
        jButton10.setIcon(icon);
        jButton31.setIcon(icon);
        jPanel30.setLayout(new WrapLayout());
        jPanel165.setLayout(new WrapLayout());
//        jPanel51.setLayout(new WrapLayout());



        //Test Product Service
        ProductService productsService = new ProductService();
        //Category test UI Database
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
        });
        
        List<String> sortedType = removeDuplicates(type);

        showCategory();

        
        jPanel11.removeAll();
//        jPanel11.add(new ViewProductOptionPanel());

//        TransactionHistoryService transactionHistoryService = new TransactionHistoryService();
//        List<TransactionHistory> transactions = transactionHistoryService.getAllTransactionHistoryDetails();
        dashboardHistogramPanel.add(new HistogramPanel().createAndShowGUI(1));
        
        RingChart ringChart = new RingChart();
        HashMap<String, Float> ringData = new HashMap<>();
        TransactionHistoryService th = new TransactionHistoryService();
        List<TransactionHistory> transactionList = th.getAllTransactionHistoryDetails();

        //total sales
//        HashMap<Integer, Float> productTotalSale
        if (transactionList.size() != 0) {
            transactionList.forEach(e -> {
                if (e.getStatus().equals("Sold")) {
                    totalSales += e.getTotalPrice();
                } else {
                    totalRefund += e.getTotalPrice();
                }
                
                totalOrders += e.getOrders();
                
                if (productTotalOrder.containsKey(e.getProductId())) {
                    int currentOrder = productTotalOrder.get(e.getProductId());
                    productTotalOrder.put(e.getProductId(), currentOrder + e.getOrders());
                } else {
                    productTotalOrder.put(e.getProductId(), e.getOrders());
                }
            });
            
            jLabel102.setText("₱" + String.valueOf(totalSales));
            jLabel111.setText(String.valueOf(totalOrders));
            jLabel140.setText("₱" + String.valueOf(totalRefund));

            int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
            topProductName.setText(productsService.getProductById(topProductId).getName());
            jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
        }
        
        
        
        
        ServiceTicketsService sts = new ServiceTicketsService();
        totalServiceRevenue = 0;
        
        sts.getAllServiceTicketsDetails().forEach(e -> {
            totalServiceRevenue += e.getPrice();
            if(highestCostService < e.getPrice()){
                topServiceName.setText("Service #"+String.valueOf(e.getId()));
                
                jLabel98.setText(String.valueOf(e.getPrice()));
                
                highestCostService = e.getPrice();
            }
        });
        jLabel136.setText("₱" + String.valueOf(totalServiceRevenue));
        
        

        //ring data
        sortedType.forEach(e -> {
            totalRevenue = 0;
            categoryService.getAllCategory().forEach(e2 -> {
                if (e.equals(e2.getType())) {
                    transactionList.forEach(e3 -> {
                        if (e3.getProductId() == e2.getProduct_id()) {
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

        
        FlatSVGIcon creditCardIcon = new FlatSVGIcon("img/icon/credit-card-regular.svg", 12, 12);
        creditCardIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel104.setIcon(creditCardIcon);
        
        FlatSVGIcon listicon = new FlatSVGIcon("img/icon/list-solid.svg", 12, 12);
        listicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel112.setIcon(listicon);
        
        FlatSVGIcon serviceicon = new FlatSVGIcon("img/icon/bolt-solid.svg", 12, 12);
        serviceicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel137.setIcon(serviceicon);
        
        FlatSVGIcon refundicon = new FlatSVGIcon("img/icon/rotate-left-solid.svg", 13, 13);
        refundicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel141.setIcon(refundicon);
        
        FlatSVGIcon topServiceIcon = new FlatSVGIcon("img/icon/bolt-solid.svg", 20, 20);
        topServiceIcon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel95.setIcon(topServiceIcon);

        jPanel142.add(new ReportLineChart());
        
        resetCheckoutPayment();
        
        jTextField24.getDocument().addDocumentListener(new DocumentListener() {
            
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            
            public void warn() {
                float change = Float.parseFloat(jTextField24.getText()) - checkoutTotalPrice;
                jLabel194.setText(String.valueOf(change));
                
                if (Float.parseFloat(jTextField24.getText()) < checkoutTotalPrice) {
                    jButton27.setEnabled(false);
                } else if (Float.parseFloat(jTextField24.getText()) >= checkoutTotalPrice) {
                    jButton27.setEnabled(true);
                } else {
                    jButton27.setEnabled(false);
                }
                
                if (jTextField24.getText().equals("")) {
                    jButton27.setEnabled(false);
                }
            }
            
        });
        
        FlatSVGIcon tagsicon = new FlatSVGIcon("img/icon/tags-solid.svg", 23, 23);
        tagsicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(0, 170, 243);
            }
        }));
        jLabel192.setIcon(tagsicon);
        jLabel210.setIcon(tagsicon);
        
        FlatSVGIcon favoriteicon = new FlatSVGIcon("img/icon/favorite-icon.svg", 23, 23);
        favoriteicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 136, 136);
            }
        }));
        jLabel197.setIcon(favoriteicon);
        jLabel213.setIcon(favoriteicon);

        //Sales product calendar
//        TransactionHistoryService ths = new TransactionHistoryService();
//        if (ths.getAllTransactionHistoryDetails() != null) {
//            ths.getAllTransactionHistoryDetails().forEach(e -> {
//                OrderHistoryThumb oht = new OrderHistoryThumb();
//                oht.setTransactionHistory(e);
//                jPanel178.add(oht);
//            });
//        }
//        
        prevDate = new java.sql.Date(jCalendar1.getDate().getTime()).toString();
        prevDate2 = new java.sql.Date(jCalendar2.getDate().getTime()).toString();
        jPanel181.removeAll();

        //Notification
//        jPanel162.removeAll();
//        NotificationService ns = new NotificationService();
//        ns.getAllNotificationDetails().forEach(e -> {
//            NotificationThumb2 notifThumb = new NotificationThumb2();
//            notifThumb.setNotification(e);
//            
//            jPanel162.add(notifThumb);
//        });
        
        FlatSVGIcon barcodesearch = new FlatSVGIcon("img/icon/magnifying-glass-solid.svg", 30, 30);
        barcodesearch.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(164, 164, 164);
            }
        }));
        jLabel238.setIcon(barcodesearch);
        
        FlatSVGIcon createbarcode = new FlatSVGIcon("img/icon/barcode-solid.svg", 30, 30);
        createbarcode.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(164, 164, 164);
            }
        }));
        jLabel239.setIcon(createbarcode);
        
        StoreInfoService storeInfoService = new StoreInfoService();
        storeInfo = storeInfoService.getStoreInfoDetails();
        
        storeNameInput.setText(storeInfo.getName());
        storeBranchInput.setText(storeInfo.getBranch());
        storeAddressInput.setText(storeInfo.getAddress());
        storeBranchInput.setText(storeInfo.getBranch());
        storeContactNumInput.setText(storeInfo.getContactNum());
        storeEmailInput.setText(storeInfo.getEmail());
        if(storeInfo.getSalesPersonDetail()== 1){
            jRadioButton5.setSelected(true);
        }else{
            jRadioButton6.setSelected(true);
        }

        //Personel Icon
        FlatSVGIcon pactiveicon = new FlatSVGIcon("img/icon/handshake-angle-solid.svg", 18, 18);
        pactiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelActiveIcon.setIcon(pactiveicon);
        
        FlatSVGIcon pdeactiveicon = new FlatSVGIcon("img/icon/handshake-simple-slash-solid.svg", 18, 18);
        pdeactiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelDeactiveIcon.setIcon(pdeactiveicon);
        
        FlatSVGIcon ptotalicon = new FlatSVGIcon("img/icon/users-solid.svg", 22, 18);
        ptotalicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelDeactiveIcon1.setIcon(ptotalicon);
        
//        FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
//        pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
//            public Color apply(Color t) {
//                return new Color(215, 122, 122);
//            }
//        }));
//        jLabel266.setIcon(pnegativeicon);
        
        
        FlatSVGIcon cactiveicon = new FlatSVGIcon("img/icon/user-check-solid.svg", 23, 18);
        cactiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        customerRegisteredIcon.setIcon(cactiveicon);
        
        FlatSVGIcon cunregistericon = new FlatSVGIcon("img/icon/user-xmark-solid.svg", 23, 18);
        cunregistericon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        customerUnregisteredIcon.setIcon(cunregistericon);
        
        FlatSVGIcon customergroupicon = new FlatSVGIcon("img/icon/user-group-solid.svg", 23, 18);
        customergroupicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(101, 55, 255);
            }
        }));
        personelDeactiveIcon3.setIcon(customergroupicon);
        
        
        
        
        
        FlatSVGIcon phoneicon = new FlatSVGIcon("img/icon/phone-solid.svg", 15, 15);
        phoneicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(126, 180, 177);
            }
        }));
        jLabel24.setIcon(phoneicon);
        
//        FlatSVGIcon banicon = new FlatSVGIcon("img/icon/ban-solid.svg", 15, 15);
//        banicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
//            public Color apply(Color t) {
//                return new Color(230, 76, 76);
//            }
//        }));
//        jLabel315.setIcon(banicon);
//        
        jDateChooser3.setVisible(true);
        jMonthChooser2.setVisible(false);
        jYearChooser3.setVisible(false);
        
        jScrollPane34.setVisible(false);
        jPanel78.setVisible(false);
//        unregisteredCustomerListPanel.add(new CustomerUnregisteredThumb());

        jRadioButton3.setActionCommand("male");
        jRadioButton4.setActionCommand("female");
        checkoutGenderButtonGroup.add(jRadioButton3);
        checkoutGenderButtonGroup.add(jRadioButton4);
        
        jRadioButton7.setActionCommand("male");
        jRadioButton8.setActionCommand("female");
        customerGenderButtonGroup.add(jRadioButton7);
        customerGenderButtonGroup.add(jRadioButton8);
        
        
        jScrollPane29.getViewport().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(jTextField18.isShowing()){
                    Point searchPosition2 = jTextField18.getLocationOnScreen();
//                    int searchX2 = (int)searchPosition2.getX();
                    int searchX2 = 100;
                    searchPanel2.setBounds(searchX2, (int)searchPosition2.getY()+20, 270, 200);
                }
            }
            
        });
        jScrollPane30.getViewport().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(jTextField25.isShowing()){
                    Point searchPosition2 = jTextField25.getLocationOnScreen();
//                    int searchX2 = (int)searchPosition2.getX();
                    int searchX2 =100;
                    searchPanel2.setBounds(searchX2, (int)searchPosition2.getY()+20, 270, 200);
                }
            }
            
        });
        
        
//        WrapLayout wrap = new WrapLayout();
//        wrap.setAlignment(0);
//        jPanel218.setLayout(wrap);
        
        FlatSVGIcon notifexpand = new FlatSVGIcon("img/icon/up-right-and-down-left-from-center-solid.svg", 13, 13);
        notifexpand.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return jLabel3.getForeground();
            }
        }));
        jLabel78.setIcon(notifexpand);
        
        FlatSVGIcon exiticon = new FlatSVGIcon("img/icon/door-open-solid.svg", 25, 25);
        exiticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel29.setIcon(exiticon);
        
        FlatSVGIcon banicon = new FlatSVGIcon("img/icon/ban-solid.svg", 15, 15);
        banicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(230, 76, 76);
            }
        }));
        jLabel309.setIcon(banicon);
        
        createCustomerDialog.add(createCustomerPanel);
        
        
//        contentPanel.removeAll();
        adminSalesPanel = new AdminSalesPanel();
        adminServiceTicketPanel = new AdminServiceTicketPanel();
        
//        contentPanel.add(adminSalesPanel);
//        contentPanel.add(adminServiceTicketPanel);
        
//        jPanel9.removeAll();
//        jPanel9.add(adminSalesPanel);
//        updateGraphics();
        
        
//        BufferedImage bufferedImage = null;
//        String cwd = System.getProperty("user.dir");
//        File dirAboveCws = new File(cwd).getParentFile();
//        try {
////            bufferedImage = ImageIO.read(getClass().getResource("/img/product/3_1.jpeg"));
////            FileInputStream fileInputStream = new FileInputStream(new File(dirAboveCws.getAbsoluteFile() + "/img/product/3_1.jpeg"));
//            bufferedImage = ImageIO.read(new File(cwd+"/img/product/3_1.jpeg"));
//        } catch (IOException ex) {
//
//        }
//        Image scaledImage;
////        if(bufferedImage.getWidth() > bufferedImage.getHeight()){
////            scaledImage = bufferedImage.getScaledInstance(220, -1, Image.SCALE_SMOOTH);
//////                     scaledImage = bufferedImage.getScaledInstance(-1, 120, Image.SCALE_SMOOTH);
////        }else{
////                     scaledImage = bufferedImage.getScaledInstance(220, -1, Image.SCALE_SMOOTH);
//            scaledImage = bufferedImage.getScaledInstance(34, 34, Image.SCALE_SMOOTH);
////        }
//        ImageIcon imageIcon = new ImageIcon(scaledImage);
//        accountProfilePicture.setIcon(imageIcon);
        salesHistorySearchBar.setVisible(false);
        updateCreateCustomerTicketService();
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
        jPanel173 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(234, 246, 255));
                }else{
                    g2.setColor(new Color(38, 49, 57));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(194, 208, 255));
                }else{
                    g2.setColor(new Color(52, 78, 86));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

            }

        };
        jLabel192 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jPanel174 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(255, 236, 236));
                }else{
                    g2.setColor(new Color(62, 50, 50));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(255, 170, 170));
                }else{
                    g2.setColor(new Color(90, 66, 66));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

            }

        };
        jLabel197 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
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
        jLabel55 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel79 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
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
        jLabel243 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel244 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        addProductOptionButton = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jButton36 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
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
        jPanel99 = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel181 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel205 = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jPanel178 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderHistorySelectedTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel101 = new javax.swing.JPanel();
        jCalendar2 = new com.toedter.calendar.JCalendar();
        jScrollPane35 = new javax.swing.JScrollPane();
        jPanel216 = new javax.swing.JPanel();
        jPanel176 = new javax.swing.JPanel();
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
        jComboBox10 = new javax.swing.JComboBox<>();
        jPanel74 = new javax.swing.JPanel();
        jScrollPane34 = new javax.swing.JScrollPane();
        jPanel71 = new javax.swing.JPanel();
        checkoutCustomerNameInput = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        checkoutCustomerAddressInput = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel61 = new javax.swing.JLabel();
        checkoutCustomerBirthDateInput = new com.toedter.calendar.JDateChooser();
        checkoutCustomerContactInput = new javax.swing.JTextField();
        jPanel78 = new javax.swing.JPanel();
        jPanel77 = new javax.swing.JPanel();
        jButton37 = new javax.swing.JButton();
        checkoutCustomerSelectedPanel = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
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
        topProductName = new javax.swing.JLabel();
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
        topServiceName = new javax.swing.JLabel();
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
        dashboardHistogramPanel = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(50, 54, 62));
                }else{
                    g2.setColor(new Color(230, 230, 230));
                }
                g2.drawLine(1, 1, 1, this.getHeight());
                g2.drawLine(1, this.getHeight()-1, this.getWidth(), this.getHeight()-1);

            }

        };
        jLabel2 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel240 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        receiptDataSection = new javax.swing.JPanel();
        jLabel273 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
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
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jMonthChooser2 = new com.toedter.calendar.JMonthChooser();
        jYearChooser3 = new com.toedter.calendar.JYearChooser();
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
        storeAddressInput = new javax.swing.JTextField();
        jLabel167 = new javax.swing.JLabel();
        storeContactNumInput = new javax.swing.JTextField();
        jLabel168 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        storeNameInput = new javax.swing.JTextField();
        jLabel241 = new javax.swing.JLabel();
        storeBranchInput = new javax.swing.JTextField();
        jLabel242 = new javax.swing.JLabel();
        jButton38 = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        storeEmailInput = new javax.swing.JTextField();
        jPanel144 = new javax.swing.JPanel();
        jPanel141 = new javax.swing.JPanel();
        jLabel160 = new javax.swing.JLabel();
        jPanel142 = new javax.swing.JPanel();
        dashboardPanelHeader = new javax.swing.JPanel();
        myProfilePanel = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jPanel126 = new javax.swing.JPanel();
        jPanel123 = new javax.swing.JPanel();
        jPanel124 = new javax.swing.JPanel();
        jLabel156 = new JLabel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(this.getBackground());
                g2.setStroke(new BasicStroke(2));
                for(int i = this.getWidth(); i >= 0; i--){
                    g2.drawRoundRect(i*-1, i*-1, this.getWidth()+i*2, this.getHeight()+i*2, 200, 200);
                }
                //            g2.setColor(Color.GRAY);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 200, 200);

                //        if(isHover){
                    //            g2.setColor(new Color(0,144,228));
                    //            g2.setStroke(new BasicStroke(2));
                    //            g2.drawRoundRect(0, 0, this.getWidth()-2, this.getHeight()-2, 200, 200);
                    //        }

                if(editProfileEnable){
                    g2.setColor(new Color(63, 69, 82));

                    g2.setStroke(new BasicStroke(3));
                    g2.drawRoundRect(3, 3, this.getWidth()-6, this.getHeight()-6, 200, 200);

                    g2.fillRoundRect(this.getWidth()-50, this.getHeight()-50, 35, 35, 35, 35);

                    try{
                        BufferedImage image = ImageIO.read(this.getClass().getResource("/img/icon/edit-icon-white.png"));
                        g2.drawImage(image, this.getWidth()-45, this.getHeight()-45, null);
                    }catch(Exception e){}

                }
            }
        };
        accountType = new javax.swing.JLabel();
        jPanel167 = new javax.swing.JPanel();
        jButton20 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jPanel171 = new javax.swing.JPanel();
        usersName = new javax.swing.JLabel();
        userNameInput = new javax.swing.JTextField();
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
        jPanel168 = new javax.swing.JPanel();
        userAgeInput = new javax.swing.JTextField();
        userAge = new javax.swing.JLabel();
        jPanel169 = new javax.swing.JPanel();
        userGenderCB = new javax.swing.JComboBox<>();
        userGender = new javax.swing.JLabel();
        jPanel170 = new javax.swing.JPanel();
        userAddressInput = new javax.swing.JTextField();
        userAddress = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jPanel172 = new javax.swing.JPanel();
        birthdateChooser = new com.toedter.calendar.JDateChooser();
        jLabel133 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
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
        jLabel181 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel155 = new javax.swing.JPanel();
        jTextField21 = new javax.swing.JTextField();
        jLabel178 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jLabel182 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        notificationDialog = new javax.swing.JDialog();
        jPanel100 = new javax.swing.JPanel();
        jLabel183 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        jPanel158 = new javax.swing.JPanel();
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
        jLabel78 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jPanel162 = new javax.swing.JPanel();
        productMainPanel = new javax.swing.JPanel();
        ticketMainTabPane1 = new javax.swing.JTabbedPane();
        jPanel145 = new javax.swing.JPanel();
        productCategoryPanel1 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jPanel146 = new javax.swing.JPanel();
        jPanel164 = new javax.swing.JPanel();
        jPanel184 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(255, 236, 236));
                }else{
                    g2.setColor(new Color(62, 50, 50));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(255, 170, 170));
                }else{
                    g2.setColor(new Color(90, 66, 66));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

            }

        };
        jLabel213 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        jLabel215 = new javax.swing.JLabel();
        jPanel183 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(234, 246, 255));
                }else{
                    g2.setColor(new Color(38, 49, 57));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

                if(!darkRB.isSelected()){
                    g2.setColor(new Color(194, 208, 255));
                }else{
                    g2.setColor(new Color(52, 78, 86));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);

            }

        };
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jLabel212 = new javax.swing.JLabel();
        jScrollPane24 = new javax.swing.JScrollPane();
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
        productMainPanelHeader = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        ticketSearchBar1 = new javax.swing.JTextField();
        jButton31 = new javax.swing.JButton();
        addProductPanel = new javax.swing.JPanel();
        jLabel207 = new javax.swing.JLabel();
        jButton32 = new javax.swing.JButton();
        productImage2 = new javax.swing.JLabel(){

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
        jLabel208 = new javax.swing.JLabel();
        editProductNameTextField1 = new javax.swing.JTextField();
        jLabel209 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel216 = new javax.swing.JLabel();
        editProductType1 = new javax.swing.JComboBox<>();
        jLabel217 = new javax.swing.JLabel();
        editProductBrand1 = new javax.swing.JComboBox<>();
        jPanel182 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jPanel185 = new javax.swing.JPanel();
        jPanel186 = new javax.swing.JPanel();
        jLabel218 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        editProductStockField1 = new javax.swing.JSpinner();
        editProductPriceField1 = new javax.swing.JTextField();
        jPanel187 = new javax.swing.JPanel();
        jLabel222 = new javax.swing.JLabel();
        jPanel188 = new javax.swing.JPanel();
        addProductOptionButton1 = new javax.swing.JButton();
        jPanel189 = new javax.swing.JPanel();
        jLabel223 = new javax.swing.JLabel();
        jPanel190 = new javax.swing.JPanel();
        jPanel191 = new javax.swing.JPanel();
        jPanel192 = new javax.swing.JPanel();
        jLabel224 = new javax.swing.JLabel();
        jPanel193 = new javax.swing.JPanel();
        jLabel225 = new javax.swing.JLabel();
        jPanel194 = new javax.swing.JPanel();
        jLabel226 = new javax.swing.JLabel();
        jPanel195 = new javax.swing.JPanel();
        jLabel227 = new javax.swing.JLabel();
        jPanel196 = new javax.swing.JPanel();
        jPanel197 = new javax.swing.JPanel();
        barcodeFrame = new javax.swing.JDialog();
        jPanel203 = new javax.swing.JPanel();
        jLabel230 = new javax.swing.JLabel();
        jPanel205 = new JPanel(){
            boolean isHover = false;
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(!darkRB.isSelected()){
                    g2.setColor(new Color(246, 246, 246));
                }else{
                    g2.setColor(this.getBackground());
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

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
        jLabel231 = new javax.swing.JLabel();
        jPanel212 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 60, 60);

            }

        };
        jLabel238 = new javax.swing.JLabel();
        jPanel207 = new JPanel(){
            boolean isHover = false;
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(!darkRB.isSelected()){
                    g2.setColor(new Color(246, 246, 246));
                }else{
                    g2.setColor(this.getBackground());
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

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
        jLabel234 = new javax.swing.JLabel();
        jPanel210 = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 60, 60);

            }

        };
        jLabel239 = new javax.swing.JLabel();
        jPanel208 = new JPanel(){
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

                g2.drawLine(1, 1, 1, this.getHeight());

            }

        };
        jPanel204 = new javax.swing.JPanel();
        jTextField15 = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
        jLabel229 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jButton34 = new javax.swing.JButton();
        jPanel209 = new javax.swing.JPanel();
        jLabel235 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jButton35 = new javax.swing.JButton();
        jLabel236 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        jPanel211 = new javax.swing.JPanel();
        personelMainPanel = new javax.swing.JPanel();
        jScrollPane29 = new javax.swing.JScrollPane();
        jPanel180 = new javax.swing.JPanel();
        jPanel214 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(!darkRB.isSelected()){
                    //            g2.setColor(new Color(255, 154, 132));
                    //        }else{
                    //            g2.setColor(new Color(255, 171, 145));
                    //        }
                //        g2.setColor(new Color(255, 199, 168));

                g2.setColor(new Color(255, 171, 145));
                //        g2.setColor(Color.gray);
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 15, 15);
            }

        };
        jLabel245 = new javax.swing.JLabel();
        jLabel246 = new javax.swing.JLabel();
        jLabel247 = new javax.swing.JLabel();
        jLabel248 = new javax.swing.JLabel();
        jLabel249 = new javax.swing.JLabel();
        jLabel250 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        jScrollPane33 = new javax.swing.JScrollPane();
        jPanel215 = new javax.swing.JPanel();
        jPanel217 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(38, 41, 48));
                    //        }else{
                    //            g2.setColor(new Color(245, 245, 245));
                    //        }
                g2.setColor(new Color(255, 255, 255));
                g2.fillRoundRect(0, 8, this.getWidth()-1, this.getHeight()-9, 55, 55);
            }

        };
        jLabel251 = new javax.swing.JLabel();
        jLabel252 = new javax.swing.JLabel();
        jLabel253 = new javax.swing.JLabel();
        jPanel179 = new javax.swing.JPanel(){

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
        jLabel254 = new javax.swing.JLabel();
        jLabel255 = new javax.swing.JLabel();
        personelActiveIcon = new javax.swing.JLabel();
        jLabel256 = new javax.swing.JLabel();
        personelDeactiveIcon = new javax.swing.JLabel();
        jLabel257 = new javax.swing.JLabel();
        personelDeactiveIcon1 = new javax.swing.JLabel();
        jLabel267 = new javax.swing.JLabel();
        jLabel268 = new javax.swing.JLabel();
        jPanel218 = new javax.swing.JPanel();
        jPanel221 = new javax.swing.JPanel();
        jLabel269 = new javax.swing.JLabel();
        jLabel270 = new javax.swing.JLabel();
        jPanel223 = new javax.swing.JPanel(){

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
        jTextField18 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        personnelViewDetailsPanel = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanel70 = new javax.swing.JPanel();
        jPanel90 = new javax.swing.JPanel();
        jLabel282 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(33, 37, 43));
                }else{
                    g2.setColor(new Color(243,247,250));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
            }

        };
        jPanel56 = new javax.swing.JPanel();
        jPanel75 = new javax.swing.JPanel();
        jLabel285 = new JLabel(){
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
        jLabel288 = new javax.swing.JLabel();
        jLabel283 = new javax.swing.JLabel();
        jPanel87 = new javax.swing.JPanel();
        jLabel286 = new JLabel(){
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
        jLabel289 = new javax.swing.JLabel();
        jLabel284 = new javax.swing.JLabel();
        jPanel89 = new javax.swing.JPanel();
        jLabel287 = new JLabel(){
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
        jLabel290 = new javax.swing.JLabel();
        jLabel291 = new javax.swing.JLabel();
        jLabel129 = new JLabel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                }else{
                    g2.setColor(new Color(255, 255, 255));
                }
                for(int i = 24; i >= 0; i--){
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
                }
                //            g2.setColor(Color.GRAY);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                //            if(isHover){
                    //                g2.setColor(new Color(0,144,228));
                    //                g2.setStroke(new BasicStroke(1));
                    //                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
                    //            }
            }
        };
        jLabel130 = new javax.swing.JLabel();
        jPanel68 = new javax.swing.JPanel();
        jLabel274 = new javax.swing.JLabel();
        jLabel275 = new javax.swing.JLabel();
        jPanel69 = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(selectedUser.getRole().equals("admin")){
                    g2.setColor(new Color(255, 168, 38, 80));
                }else{
                    g2.setColor(new Color(38, 151, 255, 60));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);
            }
        };
        jLabel131 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel85 = new javax.swing.JPanel();
        jLabel276 = new javax.swing.JLabel();
        jLabel279 = new javax.swing.JLabel();
        jLabel277 = new javax.swing.JLabel();
        jLabel280 = new javax.swing.JLabel();
        jLabel278 = new javax.swing.JLabel();
        jLabel281 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        customerMainPanel = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        jPanel206 = new javax.swing.JPanel();
        jPanel213 = new javax.swing.JPanel(){

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
        jLabel301 = new javax.swing.JLabel();
        jLabel302 = new javax.swing.JLabel();
        customerRegisteredIcon = new javax.swing.JLabel();
        jLabel303 = new javax.swing.JLabel();
        customerUnregisteredIcon = new javax.swing.JLabel();
        jLabel304 = new javax.swing.JLabel();
        personelDeactiveIcon3 = new javax.swing.JLabel();
        jLabel305 = new javax.swing.JLabel();
        jLabel306 = new javax.swing.JLabel();
        jPanel226 = new javax.swing.JPanel();
        jPanel227 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                }else{
                    g2.setColor(new Color(255, 255, 255));
                }
                g2.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(55, 55, 65));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }

                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
            }

        };
        jLabel307 = new javax.swing.JLabel();
        jPanel229 = new javax.swing.JPanel(){

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
        jLabel314 = new javax.swing.JLabel();
        jLabel315 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(38, 41, 48));
                    //        }else{
                    //
                    //        }
                g2.setColor(new Color(126, 180, 177));
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
            }

        };
        jLabel25 = new javax.swing.JLabel();
        jPanel230 = new javax.swing.JPanel();
        jLabel317 = new javax.swing.JLabel();
        jPanel231 = new javax.swing.JPanel(){

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
        jTextField25 = new javax.swing.JTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton12 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jPanel98 = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        jComboBox8 = new javax.swing.JComboBox<>();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jPanel95 = new javax.swing.JPanel();
        jScrollPane28 = new javax.swing.JScrollPane();
        jPanel25 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        unregisteredCustomerPanel = new javax.swing.JPanel();
        unregisteredCustomerListPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        archiveMainPanel = new javax.swing.JPanel();
        archivedTabPane = new javax.swing.JTabbedPane();
        jPanel58 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jPanel88 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jScrollPane31 = new javax.swing.JScrollPane();
        jPanel20 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        jScrollPane32 = new javax.swing.JScrollPane();
        jPanel52 = new javax.swing.JPanel();
        customerViewDetailsPanel = new javax.swing.JPanel();
        jPanel76 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jPanel80 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jLabel292 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(33, 37, 43));
                }else{
                    g2.setColor(new Color(243,247,250));
                }
                g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
            }

        };
        jLabel60 = new javax.swing.JLabel();
        jPanel86 = new javax.swing.JPanel();
        jLabel233 = new JLabel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if(darkRB.isSelected()){
                    g2.setColor(new Color(38, 41, 48));
                }else{
                    g2.setColor(new Color(255, 255, 255));
                }
                for(int i = 24; i >= 0; i--){
                    g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, i, i);
                }
                //            g2.setColor(Color.GRAY);
                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);

                //            if(isHover){
                    //                g2.setColor(new Color(0,144,228));
                    //                g2.setStroke(new BasicStroke(1));
                    //                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
                    //            }
            }
        };
        jLabel237 = new javax.swing.JLabel();
        jPanel97 = new javax.swing.JPanel();
        jLabel309 = new javax.swing.JLabel();
        jLabel310 = new javax.swing.JLabel();
        jLabel312 = new javax.swing.JLabel();
        jLabel313 = new javax.swing.JLabel();
        jLabel316 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jTextField8 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jButton39 = new javax.swing.JButton();
        jPanel61 = new javax.swing.JPanel();
        jButton22 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        createCustomerDialog = new javax.swing.JDialog();
        checkoutGenderButtonGroup = new javax.swing.ButtonGroup();
        customerGenderButtonGroup = new javax.swing.ButtonGroup();
        searchPanel = new javax.swing.JPanel();
        searchPanel2 = new javax.swing.JPanel();
        salesPersonDetailsBG = new javax.swing.ButtonGroup();
        glassPanel = new javax.swing.JPanel();
        selectCustomerPopupPanel = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel6 = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        jPanel7 = new JPanel(){
            //    @Override
            //    public void paintComponent(Graphics g){
                //        super.paintComponent(g);
                //        Graphics2D g2 = (Graphics2D) g.create();
                //
                //        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //        
                //        
                //        if(darkRB.isSelected()){
                    //            g2.setColor(new Color(63, 63, 63));
                    //
                    //        }else{
                    //            g2.setColor(new Color(203, 203, 203));
                    //        }
                //        if(exitHover){
                    //            g2.setColor(new Color(196, 43, 28));
                    //        }
                //            g2.setStroke(new BasicStroke(1));
                //            g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 25, 25);
                //        
                //    }

        };
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
        adminMenuPanel = new javax.swing.JPanel();
        dashboardMainMenu = new MainMenuThumb();
        jLabel1 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        personnelMainMenu = new MainMenuThumb();
        jLabel271 = new javax.swing.JLabel();
        jLabel272 = new javax.swing.JLabel();
        customerMainMenu = new MainMenuThumb();
        jLabel53 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        archiveMainMenu = new MainMenuThumb();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        mainHeaderPanel = new javax.swing.JPanel();
        menuHeader = new javax.swing.JLabel();
        accountProfilePicture = new JLabel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(this.getBackground());
                g2.setStroke(new BasicStroke(2));
                for(int i = this.getWidth(); i >= 0; i--){
                    g2.drawRoundRect(i*-1, i*-1, this.getWidth()+i*2, this.getHeight()+i*2, 34, 34);
                }
                //            g2.setColor(new Color(242, 110, 0));
                if(darkRB.isSelected()){
                    g2.setColor(new Color(58, 61, 68));
                }else{
                    g2.setColor(new Color(225, 225, 225));
                }
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 34, 34);

                //        if(isHover){
                    //            g2.setColor(new Color(0,144,228));
                    //            g2.setStroke(new BasicStroke(2));
                    //            g2.drawRoundRect(0, 0, this.getWidth()-2, this.getHeight()-2, 200, 200);
                    //        }
            }

        };
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

        jPanel173.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel173.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel173.setPreferredSize(new java.awt.Dimension(104, 44));
        jPanel173.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel173MouseClicked(evt);
            }
        });

        jLabel195.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel195.setForeground(new java.awt.Color(0, 170, 243));
        jLabel195.setText("All Products");

        jLabel196.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(0, 170, 243));
        jLabel196.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel196.setText("2 items");

        javax.swing.GroupLayout jPanel173Layout = new javax.swing.GroupLayout(jPanel173);
        jPanel173.setLayout(jPanel173Layout);
        jPanel173Layout.setHorizontalGroup(
            jPanel173Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel173Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel192, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel195)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jLabel196)
                .addGap(15, 15, 15))
        );
        jPanel173Layout.setVerticalGroup(
            jPanel173Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel173Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel173Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel192, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel173Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel195, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel196)))
                .addGap(8, 8, 8))
        );

        jPanel174.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel174.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel174.setPreferredSize(new java.awt.Dimension(104, 44));
        jPanel174.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel174MouseClicked(evt);
            }
        });

        jLabel198.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(255, 136, 136));
        jLabel198.setText("Favorites");

        jLabel199.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel199.setForeground(new java.awt.Color(255, 136, 136));
        jLabel199.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel199.setText("2 items");

        javax.swing.GroupLayout jPanel174Layout = new javax.swing.GroupLayout(jPanel174);
        jPanel174.setLayout(jPanel174Layout);
        jPanel174Layout.setHorizontalGroup(
            jPanel174Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel174Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel197, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel198)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jLabel199)
                .addGap(15, 15, 15))
        );
        jPanel174Layout.setVerticalGroup(
            jPanel174Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel174Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel174Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel197, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel174Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel198, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel199)))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel174, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel173, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(19, 19, 19))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel173, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel174, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
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

        jScrollPane23.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane23.setViewportView(jPanel30);

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(productCategoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productCategoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane23)
                .addContainerGap())
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

        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField1.setFocusable(false);
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

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setText("Walk-in Date");

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel63.setText("Estimated Finish");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Progress", "Done" }));

        jLabel79.setText("Customer Info");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Select Registered Customer", "Create New Customer" }));
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel55)
                    .addComponent(jLabel63)
                    .addComponent(jLabel79))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jComboBox11, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        jLabel64.setText(" ");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(0, 94, Short.MAX_VALUE)))
                .addContainerGap(147, Short.MAX_VALUE))
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
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
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
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        ticketMainTabPane.addTab("Service", jPanel40);

        javax.swing.GroupLayout ticketMainPanelLayout = new javax.swing.GroupLayout(ticketMainPanel);
        ticketMainPanel.setLayout(ticketMainPanelLayout);
        ticketMainPanelLayout.setHorizontalGroup(
            ticketMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ticketMainPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ticketMainTabPane)
                .addGap(0, 0, 0))
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

        jPanel34.setPreferredSize(new java.awt.Dimension(586, 215));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Pricing");

        jLabel35.setText("Default price");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Inventory Stocks");

        jLabel37.setText("Stocks");

        editProductStockField.setPreferredSize(new java.awt.Dimension(30, 35));

        editProductPriceField.setPreferredSize(new java.awt.Dimension(7, 35));

        jLabel243.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel243.setText("Barcode");

        jLabel244.setText("Default Barcode");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel36)
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel34Layout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editProductPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel34Layout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(editProductStockField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel243)))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel244)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(340, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel243)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel244))
                .addGap(20, 20, 20))
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

        jPanel53.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel52.setText("Variant");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
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
                .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
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
                .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
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
                .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
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
                .addGap(35, 35, 35)
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
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel72, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel45.add(jPanel44);

        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.PAGE_AXIS));
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        jButton36.setText("Delete Product");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton41.setText("Revert Product");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

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
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(editProductPanelLayout.createSequentialGroup()
                        .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(productImage, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(editProductBrand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editProductType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editProductPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
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
        jSpinner4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner4StateChanged(evt);
            }
        });
        jSpinner4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSpinner4PropertyChange(evt);
            }
        });

        jPanel81.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel81.setPreferredSize(new java.awt.Dimension(54, 12));
        jPanel81.setLayout(new javax.swing.BoxLayout(jPanel81, javax.swing.BoxLayout.LINE_AXIS));

        viewProductSelectedVariants.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
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

        jPanel9.setLayout(new java.awt.BorderLayout());

        jCalendar1.setMaximumSize(new java.awt.Dimension(300, 2147483647));
        jCalendar1.setPreferredSize(new java.awt.Dimension(360, 350));
        jCalendar1.setSundayForeground(new java.awt.Color(0, 102, 102));
        jCalendar1.setWeekOfYearVisible(false);
        jCalendar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar1PropertyChange(evt);
            }
        });

        jPanel181.setLayout(new javax.swing.BoxLayout(jPanel181, javax.swing.BoxLayout.LINE_AXIS));

        jButton4.setText("Refund");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 144, 228));
        jButton5.setForeground(new java.awt.Color(240, 240, 240));
        jButton5.setText("Sold");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel205.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel205.setText("Bulk Action");

        jLabel206.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel206.setForeground(new java.awt.Color(17, 184, 255));
        jLabel206.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel206.setText("2 Selected");
        jLabel206.setBorder(BorderFactory.createDashedBorder(new Color(17,184,255), 7, 7));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel205)
                .addGap(26, 26, 26)
                .addComponent(jLabel206, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel206, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
            .addComponent(jLabel205, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel181.add(jPanel15);

        jScrollPane25.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(0, 0));

        jPanel178.setLayout(new javax.swing.BoxLayout(jPanel178, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane25.setViewportView(jPanel178);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 200));
        jScrollPane1.setName(""); // NOI18N

        orderHistorySelectedTable.setAutoCreateRowSorter(true);
        orderHistorySelectedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product", "Orders", "Date", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderHistorySelectedTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        orderHistorySelectedTable.setFillsViewportHeight(true);
        orderHistorySelectedTable.setRowHeight(25);
        jScrollPane1.setViewportView(orderHistorySelectedTable);

        javax.swing.GroupLayout jPanel99Layout = new javax.swing.GroupLayout(jPanel99);
        jPanel99.setLayout(jPanel99Layout);
        jPanel99Layout.setHorizontalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel99Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel181, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel99Layout.createSequentialGroup()
                        .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel99Layout.setVerticalGroup(
            jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel99Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel181, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel9.add(jPanel99, java.awt.BorderLayout.CENTER);

        salesHistoryTabPane.addTab("Order History", jPanel9);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jCalendar2.setMaximumSize(new java.awt.Dimension(300, 2147483647));
        jCalendar2.setPreferredSize(new java.awt.Dimension(360, 350));
        jCalendar2.setSundayForeground(new java.awt.Color(0, 102, 102));
        jCalendar2.setWeekOfYearVisible(false);
        jCalendar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jCalendar2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jCalendar2MouseExited(evt);
            }
        });
        jCalendar2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar2PropertyChange(evt);
            }
        });

        jScrollPane35.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane35.setPreferredSize(null);

        jPanel216.setLayout(new javax.swing.BoxLayout(jPanel216, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel176.setLayout(new javax.swing.BoxLayout(jPanel176, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel216.add(jPanel176);

        jScrollPane35.setViewportView(jPanel216);

        javax.swing.GroupLayout jPanel101Layout = new javax.swing.GroupLayout(jPanel101);
        jPanel101.setLayout(jPanel101Layout);
        jPanel101Layout.setHorizontalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel101Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane35, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel101Layout.setVerticalGroup(
            jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel101Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel101Layout.createSequentialGroup()
                        .addComponent(jCalendar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(235, Short.MAX_VALUE))))
        );

        jPanel8.add(jPanel101, java.awt.BorderLayout.CENTER);

        salesHistoryTabPane.addTab("Service History", jPanel8);

        javax.swing.GroupLayout salesHistoryPanelLayout = new javax.swing.GroupLayout(salesHistoryPanel);
        salesHistoryPanel.setLayout(salesHistoryPanelLayout);
        salesHistoryPanelLayout.setHorizontalGroup(
            salesHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesHistoryTabPane)
        );
        salesHistoryPanelLayout.setVerticalGroup(
            salesHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesHistoryTabPane)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Checkout Tickets");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(530, 356));

        checkoutThumbScrollPane.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                checkoutThumbScrollPaneComponentRemoved(evt);
            }
        });
        checkoutThumbScrollPane.setLayout(new javax.swing.BoxLayout(checkoutThumbScrollPane, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane3.setViewportView(checkoutThumbScrollPane);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Customer Details", "New Customer Details", "Existing Customer Details" }));
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });

        jPanel74.setLayout(new javax.swing.BoxLayout(jPanel74, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane34.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel15.setText("Customer Name");

        jLabel16.setText("Phone No.");

        jLabel26.setText("Address");

        jLabel54.setText("Gender");

        jRadioButton3.setSelected(true);
        jRadioButton3.setText("Male");

        jRadioButton4.setText("Female");

        jLabel61.setText("Birth Date");

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(checkoutCustomerNameInput)
            .addComponent(checkoutCustomerAddressInput)
            .addComponent(checkoutCustomerBirthDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel26)
                    .addComponent(jLabel54)
                    .addGroup(jPanel71Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4))
                    .addComponent(jLabel61))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(checkoutCustomerContactInput)
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutCustomerNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutCustomerContactInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutCustomerAddressInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutCustomerBirthDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane34.setViewportView(jPanel71);

        jPanel74.add(jScrollPane34);

        jPanel78.setLayout(new javax.swing.BoxLayout(jPanel78, javax.swing.BoxLayout.PAGE_AXIS));

        jButton37.setText("Select Customer");
        jButton37.setPreferredSize(new java.awt.Dimension(116, 40));
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        checkoutCustomerSelectedPanel.setLayout(new javax.swing.BoxLayout(checkoutCustomerSelectedPanel, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(checkoutCustomerSelectedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel77Layout.createSequentialGroup()
                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutCustomerSelectedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel78.add(jPanel77);

        jPanel74.add(jPanel78);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel161, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel24Layout.createSequentialGroup()
                            .addComponent(checkoutCashThumb, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(checkoutCreditThumb, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(18, 18, 18)
                .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
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
                .addContainerGap(503, Short.MAX_VALUE)
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

        jButton11.setBackground(new java.awt.Color(0, 144, 228));
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Checkout Tickets");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        ticketSearchBar.setBackground(new java.awt.Color(245, 245, 245));
        ticketSearchBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ticketSearchBarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ticketSearchBarFocusLost(evt);
            }
        });

        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

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
                .addContainerGap(16, Short.MAX_VALUE)
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

        topProductName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        topProductName.setForeground(new java.awt.Color(255, 255, 255));
        topProductName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topProductName.setText("Rolex");

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
            .addComponent(jLabel92, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
            .addGroup(jPanel92Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel92Layout.setVerticalGroup(
            jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel92Layout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(topProductName)
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

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Week", "Month", "Year", "All Data" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jPanel96.setOpaque(false);

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

        topServiceName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        topServiceName.setForeground(new java.awt.Color(255, 255, 255));
        topServiceName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        topServiceName.setText("Ewan ko ba");

        jLabel97.setBackground(new java.awt.Color(153, 153, 153));
        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("Top Service");

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("15");

        jLabel99.setBackground(new java.awt.Color(153, 153, 153));
        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel99.setText("/Repair cost");

        javax.swing.GroupLayout jPanel96Layout = new javax.swing.GroupLayout(jPanel96);
        jPanel96.setLayout(jPanel96Layout);
        jPanel96Layout.setHorizontalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topServiceName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel96Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel96Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
        );
        jPanel96Layout.setVerticalGroup(
            jPanel96Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel96Layout.createSequentialGroup()
                .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(topServiceName)
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

        jLabel102.setBackground(new java.awt.Color(102, 102, 102));
        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(51, 51, 51));
        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel102.setText("0");

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

        jLabel111.setBackground(new java.awt.Color(102, 102, 102));
        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(153, 153, 153));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("0");

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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel136.setBackground(new java.awt.Color(102, 102, 102));
        jLabel136.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(153, 153, 153));
        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel136.setText("0");

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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel140.setBackground(new java.awt.Color(102, 102, 102));
        jLabel140.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(153, 153, 153));
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel140.setText("0");

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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel111, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel115, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel129, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel131, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel110Layout.setVerticalGroup(
            jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel111, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
            .addComponent(jPanel115, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
            .addComponent(jPanel129, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
            .addComponent(jPanel131, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );

        dashboardHistogramPanel.setPreferredSize(new java.awt.Dimension(0, 280));
        dashboardHistogramPanel.setLayout(new javax.swing.BoxLayout(dashboardHistogramPanel, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Welcome back, ");

        jLabel103.setText("Here's what's happening to your store today!");

        jLabel240.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel240.setText("Store Performance");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total Sales", "Item Sold" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jYearChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jYearChooser1PropertyChange(evt);
            }
        });

        jLabel273.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel273.setText("Receipt Data");

        jPanel66.setLayout(new javax.swing.BoxLayout(jPanel66, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout receiptDataSectionLayout = new javax.swing.GroupLayout(receiptDataSection);
        receiptDataSection.setLayout(receiptDataSectionLayout);
        receiptDataSectionLayout.setHorizontalGroup(
            receiptDataSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(receiptDataSectionLayout.createSequentialGroup()
                .addGroup(receiptDataSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel273)
                    .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        receiptDataSectionLayout.setVerticalGroup(
            receiptDataSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, receiptDataSectionLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel273)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(receiptDataSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel94, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardHistogramPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel110, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel82Layout.createSequentialGroup()
                        .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel240, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel82Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel103)
                .addGap(10, 10, 10)
                .addComponent(jPanel110, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel240, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dashboardHistogramPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel94, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(receiptDataSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(78, 78, 78))
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
                .addComponent(ringChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
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
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

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
                .addComponent(jLabel157, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel139Layout = new javax.swing.GroupLayout(jPanel139);
        jPanel139.setLayout(jPanel139Layout);
        jPanel139Layout.setHorizontalGroup(
            jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel139Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel139Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(10, 10, 10)
                        .addComponent(jRadioButton2))
                    .addGroup(jPanel139Layout.createSequentialGroup()
                        .addComponent(jLabel158)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel139Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel159, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel143, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel148Layout.setVerticalGroup(
            jPanel148Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel148Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel164, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Monthly", "Yearly" }));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(0, 144, 228));
        jButton23.setForeground(new java.awt.Color(255, 255, 255));
        jButton23.setText("Print");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel147Layout = new javax.swing.GroupLayout(jPanel147);
        jPanel147.setLayout(jPanel147Layout);
        jPanel147Layout.setHorizontalGroup(
            jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel148, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
            .addGroup(jPanel147Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jMonthChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYearChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel147Layout.setVerticalGroup(
            jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel147Layout.createSequentialGroup()
                .addGroup(jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel147Layout.createSequentialGroup()
                        .addComponent(jPanel148, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel147Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jMonthChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jYearChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel149.setOpaque(false);
        jPanel149.setPreferredSize(new java.awt.Dimension(246, 490));

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
                .addComponent(jLabel165, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel166.setText("Store Address");

        storeAddressInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeAddressInputActionPerformed(evt);
            }
        });

        jLabel167.setText("Contact No.");

        jLabel168.setText("Include sales person details");

        jRadioButton5.setSelected(true);
        jRadioButton5.setText("Yes");

        jRadioButton6.setText("No");

        jLabel241.setText("Store Name");

        jLabel242.setText("Store Branch");

        jButton38.setBackground(new java.awt.Color(0, 144, 228));
        jButton38.setForeground(new java.awt.Color(255, 255, 255));
        jButton38.setText("Update Info");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jLabel77.setText("Email Address");

        javax.swing.GroupLayout jPanel149Layout = new javax.swing.GroupLayout(jPanel149);
        jPanel149.setLayout(jPanel149Layout);
        jPanel149Layout.setHorizontalGroup(
            jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel150, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel149Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel77)
                    .addComponent(jLabel242)
                    .addGroup(jPanel149Layout.createSequentialGroup()
                        .addComponent(jRadioButton5)
                        .addGap(10, 10, 10)
                        .addComponent(jRadioButton6))
                    .addComponent(jLabel168)
                    .addComponent(jLabel167)
                    .addComponent(jLabel166)
                    .addComponent(storeAddressInput, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(storeContactNumInput)
                    .addComponent(storeNameInput)
                    .addComponent(jLabel241, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(storeBranchInput)
                    .addComponent(storeEmailInput)
                    .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel149Layout.setVerticalGroup(
            jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel149Layout.createSequentialGroup()
                .addComponent(jPanel150, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel241)
                .addGap(0, 0, 0)
                .addComponent(storeNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel242)
                .addGap(0, 0, 0)
                .addComponent(storeBranchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel166)
                .addGap(0, 0, 0)
                .addComponent(storeAddressInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel167)
                .addGap(0, 0, 0)
                .addComponent(storeContactNumInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(storeEmailInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel168)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel149Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6))
                .addGap(18, 18, 18)
                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel140Layout = new javax.swing.GroupLayout(jPanel140);
        jPanel140.setLayout(jPanel140Layout);
        jPanel140Layout.setHorizontalGroup(
            jPanel140Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel140Layout.createSequentialGroup()
                .addContainerGap(167, Short.MAX_VALUE)
                .addGroup(jPanel140Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel149, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .addComponent(jPanel147, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                    .addComponent(jPanel139, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel140Layout.setVerticalGroup(
            jPanel140Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel140Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel147, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
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
                .addComponent(jPanel142, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel144Layout = new javax.swing.GroupLayout(jPanel144);
        jPanel144.setLayout(jPanel144Layout);
        jPanel144Layout.setHorizontalGroup(
            jPanel144Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel144Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel141, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
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
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel137Layout.setVerticalGroup(
            jPanel137Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
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

        jScrollPane17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel124.setPreferredSize(new java.awt.Dimension(200, 200));

        jLabel156.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel156.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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

        accountType.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        accountType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accountType.setText("Employee");

        jButton20.setText("Edit");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton29.setBackground(new java.awt.Color(0, 144, 228));
        jButton29.setForeground(new java.awt.Color(255, 255, 255));
        jButton29.setText("Save");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel167Layout = new javax.swing.GroupLayout(jPanel167);
        jPanel167.setLayout(jPanel167Layout);
        jPanel167Layout.setHorizontalGroup(
            jPanel167Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel167Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel167Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel167Layout.setVerticalGroup(
            jPanel167Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel167Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        usersName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usersName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usersName.setText("Paul Justine Faustino");

        userNameInput.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        userNameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        userNameInput.setMinimumSize(new java.awt.Dimension(130, 40));

        javax.swing.GroupLayout jPanel171Layout = new javax.swing.GroupLayout(jPanel171);
        jPanel171.setLayout(jPanel171Layout);
        jPanel171Layout.setHorizontalGroup(
            jPanel171Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel171Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel171Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usersName, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(userNameInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel171Layout.setVerticalGroup(
            jPanel171Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel171Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(usersName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(userNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel123Layout = new javax.swing.GroupLayout(jPanel123);
        jPanel123.setLayout(jPanel123Layout);
        jPanel123Layout.setHorizontalGroup(
            jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(accountType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel123Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel123Layout.createSequentialGroup()
                .addGroup(jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel167, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel171, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel123Layout.setVerticalGroup(
            jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel123Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jPanel124, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel171, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel167, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(accountType, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel127.setBackground(new java.awt.Color(243, 247, 250));

        jPanel128.setMinimumSize(new java.awt.Dimension(350, 100));
        jPanel128.setOpaque(false);
        jPanel128.setPreferredSize(new java.awt.Dimension(415, 280));

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
                .addContainerGap(320, Short.MAX_VALUE))
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

        jPanel168.setLayout(new javax.swing.BoxLayout(jPanel168, javax.swing.BoxLayout.LINE_AXIS));

        userAgeInput.setText("22");
        userAgeInput.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel168.add(userAgeInput);

        userAge.setText("jLabel132");
        jPanel168.add(userAge);

        jPanel169.setLayout(new javax.swing.BoxLayout(jPanel169, javax.swing.BoxLayout.LINE_AXIS));

        userGenderCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        userGenderCB.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel169.add(userGenderCB);

        userGender.setText("jLabel133");
        jPanel169.add(userGender);

        jPanel170.setLayout(new javax.swing.BoxLayout(jPanel170, javax.swing.BoxLayout.LINE_AXIS));

        userAddressInput.setText("Iba, wawa");
        userAddressInput.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel170.add(userAddressInput);

        userAddress.setText("jLabel192");
        jPanel170.add(userAddress);

        jLabel132.setText("Date of Birth:");

        jPanel172.setLayout(new javax.swing.BoxLayout(jPanel172, javax.swing.BoxLayout.LINE_AXIS));

        birthdateChooser.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel172.add(birthdateChooser);

        jLabel133.setText("jLabel133");
        jPanel172.add(jLabel133);

        jLabel62.setText("Contact #:");

        jPanel79.setLayout(new javax.swing.BoxLayout(jPanel79, javax.swing.BoxLayout.LINE_AXIS));

        jTextField2.setText("09********");
        jTextField2.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel79.add(jTextField2);

        jLabel67.setText("jLabel67");
        jPanel79.add(jLabel67);

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
                        .addComponent(jPanel168, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel132)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel172, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel145)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel169, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel146)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel170, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel128Layout.createSequentialGroup()
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel128Layout.setVerticalGroup(
            jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel128Layout.createSequentialGroup()
                .addComponent(jPanel125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel172, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel168, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel169, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel145, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel170, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel128Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
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
                    .addComponent(jLabel153, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
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
                    .addComponent(jLabel150, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jLabel154, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel133Layout = new javax.swing.GroupLayout(jPanel133);
        jPanel133.setLayout(jPanel133Layout);
        jPanel133Layout.setHorizontalGroup(
            jPanel133Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel133Layout.createSequentialGroup()
                .addComponent(jPanel135, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel136, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel134, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
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
        jLabel173.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel173.setText("user123");
        jLabel173.setPreferredSize(new java.awt.Dimension(39, 20));

        jLabel174.setBackground(new java.awt.Color(153, 153, 153));
        jLabel174.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel133, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addComponent(jPanel128, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel127Layout.setVerticalGroup(
            jPanel127Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel127Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel133, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel128, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel151, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );
        myProfilePanelLayout.setVerticalGroup(
            myProfilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 1112, Short.MAX_VALUE)
        );

        changeUserPassDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        changeUserPassDialog.setAlwaysOnTop(true);
        changeUserPassDialog.setMinimumSize(new java.awt.Dimension(400, 636));

        jScrollPane19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel156.setPreferredSize(new java.awt.Dimension(0, 0));

        jLabel175.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel175.setText("Change Username & Password");

        jLabel176.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel176.setText("Username");

        jLabel177.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel177.setText("Password");

        jLabel181.setBackground(new java.awt.Color(255, 102, 102));
        jLabel181.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel154Layout = new javax.swing.GroupLayout(jPanel154);
        jPanel154.setLayout(jPanel154Layout);
        jPanel154Layout.setHorizontalGroup(
            jPanel154Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel154Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel154Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel181, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel177)
                    .addComponent(jLabel176)
                    .addComponent(jTextField19, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(jPasswordField1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel181, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jLabel178.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel178.setText("New Username");

        jLabel179.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel179.setText("New Password");

        jLabel180.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel180.setText("Confirm Password");

        jButton25.setBackground(new java.awt.Color(0, 144, 228));
        jButton25.setForeground(new java.awt.Color(255, 255, 255));
        jButton25.setText("Save Changes");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setText("Cancel");

        jLabel182.setForeground(new java.awt.Color(255, 51, 51));

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
                    .addComponent(jLabel179)
                    .addComponent(jLabel178)
                    .addComponent(jTextField21, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(jLabel182, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField2)
                    .addComponent(jPasswordField3))
                .addContainerGap(25, Short.MAX_VALUE))
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
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel180)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel182, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel155, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(84, Short.MAX_VALUE)
                .addGroup(jPanel156Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel153, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel175, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel156Layout.setVerticalGroup(
            jPanel156Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel156Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel153, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jScrollPane19.setViewportView(jPanel156);

        javax.swing.GroupLayout changeUserPassDialogLayout = new javax.swing.GroupLayout(changeUserPassDialog.getContentPane());
        changeUserPassDialog.getContentPane().setLayout(changeUserPassDialogLayout);
        changeUserPassDialogLayout.setHorizontalGroup(
            changeUserPassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changeUserPassDialogLayout.createSequentialGroup()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        changeUserPassDialogLayout.setVerticalGroup(
            changeUserPassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );

        notificationDialog.setMinimumSize(new java.awt.Dimension(600, 481));

        jPanel100.setPreferredSize(new java.awt.Dimension(300, 82));

        jLabel183.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel183.setText("Notification");
        jLabel183.setToolTipText("Notification");

        jScrollPane20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane20.setMinimumSize(null);

        jPanel158.setLayout(new javax.swing.BoxLayout(jPanel158, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane20.setViewportView(jPanel158);

        javax.swing.GroupLayout jPanel100Layout = new javax.swing.GroupLayout(jPanel100);
        jPanel100.setLayout(jPanel100Layout);
        jPanel100Layout.setHorizontalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel183, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel100Layout.setVerticalGroup(
            jPanel100Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel100Layout.createSequentialGroup()
                .addComponent(jLabel183, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout notificationDialogLayout = new javax.swing.GroupLayout(notificationDialog.getContentPane());
        notificationDialog.getContentPane().setLayout(notificationDialogLayout);
        notificationDialogLayout.setHorizontalGroup(
            notificationDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationDialogLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jPanel100, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addGap(97, 97, 97))
        );
        notificationDialogLayout.setVerticalGroup(
            notificationDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        notificationPanel.setOpaque(false);

        jPanel160.setOpaque(false);
        jPanel160.setPreferredSize(new java.awt.Dimension(298, 40));

        jLabel186.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel186.setText("Notification");
        jLabel186.setPreferredSize(new java.awt.Dimension(105, 25));

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

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel78.setPreferredSize(new java.awt.Dimension(36, 0));
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel160Layout = new javax.swing.GroupLayout(jPanel160);
        jPanel160.setLayout(jPanel160Layout);
        jPanel160Layout.setHorizontalGroup(
            jPanel160Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel160Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel160Layout.setVerticalGroup(
            jPanel160Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel186, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel160Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel160Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel160Layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addComponent(jPanel161, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addComponent(jLabel78, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane21.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel162.setLayout(new javax.swing.BoxLayout(jPanel162, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane21.setViewportView(jPanel162);

        javax.swing.GroupLayout notificationPanelLayout = new javax.swing.GroupLayout(notificationPanel);
        notificationPanel.setLayout(notificationPanelLayout);
        notificationPanelLayout.setHorizontalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel160, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        notificationPanelLayout.setVerticalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel160, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
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

        jPanel184.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel184.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel184.setPreferredSize(new java.awt.Dimension(104, 44));
        jPanel184.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel184MouseClicked(evt);
            }
        });

        jLabel214.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel214.setForeground(new java.awt.Color(255, 136, 136));
        jLabel214.setText("Favorites");

        jLabel215.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel215.setForeground(new java.awt.Color(255, 136, 136));
        jLabel215.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel215.setText("2 items");

        javax.swing.GroupLayout jPanel184Layout = new javax.swing.GroupLayout(jPanel184);
        jPanel184.setLayout(jPanel184Layout);
        jPanel184Layout.setHorizontalGroup(
            jPanel184Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel184Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel213, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel214)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel215)
                .addGap(15, 15, 15))
        );
        jPanel184Layout.setVerticalGroup(
            jPanel184Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel184Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel184Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel213, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel184Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel214, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel215)))
                .addGap(8, 8, 8))
        );

        jPanel183.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel183.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel183.setPreferredSize(new java.awt.Dimension(104, 44));
        jPanel183.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel183MouseClicked(evt);
            }
        });

        jLabel211.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel211.setForeground(new java.awt.Color(0, 170, 243));
        jLabel211.setText("All Products");

        jLabel212.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel212.setForeground(new java.awt.Color(0, 170, 243));
        jLabel212.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel212.setText("2 items");

        javax.swing.GroupLayout jPanel183Layout = new javax.swing.GroupLayout(jPanel183);
        jPanel183.setLayout(jPanel183Layout);
        jPanel183Layout.setHorizontalGroup(
            jPanel183Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel183Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel210, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel211)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jLabel212)
                .addGap(15, 15, 15))
        );
        jPanel183Layout.setVerticalGroup(
            jPanel183Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel183Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel183Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel210, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel183Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel211, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel212)))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel146Layout = new javax.swing.GroupLayout(jPanel146);
        jPanel146.setLayout(jPanel146Layout);
        jPanel146Layout.setHorizontalGroup(
            jPanel146Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel146Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel146Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel183, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel146Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel184, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                        .addComponent(jPanel164, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        jPanel146Layout.setVerticalGroup(
            jPanel146Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel146Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel183, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel184, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel164, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jScrollPane22.setViewportView(jPanel146);

        javax.swing.GroupLayout productCategoryPanel1Layout = new javax.swing.GroupLayout(productCategoryPanel1);
        productCategoryPanel1.setLayout(productCategoryPanel1Layout);
        productCategoryPanel1Layout.setHorizontalGroup(
            productCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );
        productCategoryPanel1Layout.setVerticalGroup(
            productCategoryPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jScrollPane24.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane24.setViewportView(jPanel165);

        javax.swing.GroupLayout jPanel145Layout = new javax.swing.GroupLayout(jPanel145);
        jPanel145.setLayout(jPanel145Layout);
        jPanel145Layout.setHorizontalGroup(
            jPanel145Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel145Layout.createSequentialGroup()
                .addComponent(productCategoryPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane24)
                .addContainerGap())
        );
        jPanel145Layout.setVerticalGroup(
            jPanel145Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productCategoryPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel145Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane24)
                .addContainerGap())
        );

        ticketMainTabPane1.addTab("Products", jPanel145);

        javax.swing.GroupLayout productMainPanelLayout = new javax.swing.GroupLayout(productMainPanel);
        productMainPanel.setLayout(productMainPanelLayout);
        productMainPanelLayout.setHorizontalGroup(
            productMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productMainPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ticketMainTabPane1)
                .addGap(0, 0, 0))
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
        confirmTotalPrice.setText("0");

        jLabel193.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel193.setText("Change");

        jLabel194.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel194.setText("0");

        jButton27.setBackground(new java.awt.Color(0, 144, 228));
        jButton27.setForeground(new java.awt.Color(255, 255, 255));
        jButton27.setText("Confirm");
        jButton27.setEnabled(false);
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

        jButton30.setBackground(new java.awt.Color(0, 144, 228));
        jButton30.setForeground(new java.awt.Color(255, 255, 255));
        jButton30.setText("New Product");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        ticketSearchBar1.setBackground(new java.awt.Color(245, 245, 245));
        ticketSearchBar1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ticketSearchBar1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ticketSearchBar1FocusLost(evt);
            }
        });

        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productMainPanelHeaderLayout = new javax.swing.GroupLayout(productMainPanelHeader);
        productMainPanelHeader.setLayout(productMainPanelHeaderLayout);
        productMainPanelHeaderLayout.setHorizontalGroup(
            productMainPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productMainPanelHeaderLayout.createSequentialGroup()
                .addContainerGap(174, Short.MAX_VALUE)
                .addComponent(ticketSearchBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        productMainPanelHeaderLayout.setVerticalGroup(
            productMainPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ticketSearchBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jButton31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel207.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel207.setText("Create New Product");

        jButton32.setBackground(new java.awt.Color(0, 144, 228));
        jButton32.setForeground(new java.awt.Color(240, 240, 240));
        jButton32.setText("Save Product");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        productImage2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productImage2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productImage2MouseClicked(evt);
            }
        });

        jLabel208.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel208.setText("Product Name");

        editProductNameTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductNameTextField1ActionPerformed(evt);
            }
        });

        jLabel209.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel209.setText("Product Category");

        jSeparator4.setForeground(new java.awt.Color(234, 234, 234));

        jLabel216.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel216.setText("Type");

        editProductType1.setEditable(true);
        editProductType1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        editProductType1.setPreferredSize(new java.awt.Dimension(57, 35));

        jLabel217.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel217.setText("Brand");

        editProductBrand1.setEditable(true);
        editProductBrand1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane26.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel185.setLayout(new javax.swing.BoxLayout(jPanel185, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel186.setPreferredSize(new java.awt.Dimension(300, 145));

        jLabel218.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel218.setText("Pricing");

        jLabel219.setText("Default price");

        jLabel220.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel220.setText("Inventory Stocks");

        jLabel221.setText("Stocks");

        editProductStockField1.setPreferredSize(new java.awt.Dimension(30, 35));

        editProductPriceField1.setPreferredSize(new java.awt.Dimension(7, 35));

        javax.swing.GroupLayout jPanel186Layout = new javax.swing.GroupLayout(jPanel186);
        jPanel186.setLayout(jPanel186Layout);
        jPanel186Layout.setHorizontalGroup(
            jPanel186Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel186Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel186Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel218)
                    .addComponent(jLabel220)
                    .addGroup(jPanel186Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel186Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel186Layout.createSequentialGroup()
                                .addComponent(jLabel221)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editProductStockField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel186Layout.createSequentialGroup()
                                .addComponent(jLabel219)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editProductPriceField1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(421, Short.MAX_VALUE))
        );
        jPanel186Layout.setVerticalGroup(
            jPanel186Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel186Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel218)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel186Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel219)
                    .addComponent(editProductPriceField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel220)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel186Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel221)
                    .addComponent(editProductStockField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel185.add(jPanel186);

        jLabel222.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel222.setText("Available Options");

        jPanel188.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel188ComponentRemoved(evt);
            }
        });
        jPanel188.setLayout(new javax.swing.BoxLayout(jPanel188, javax.swing.BoxLayout.PAGE_AXIS));

        addProductOptionButton1.setBackground(new java.awt.Color(0, 144, 228));
        addProductOptionButton1.setForeground(new java.awt.Color(255, 255, 255));
        addProductOptionButton1.setText("Add Another");
        addProductOptionButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductOptionButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel187Layout = new javax.swing.GroupLayout(jPanel187);
        jPanel187.setLayout(jPanel187Layout);
        jPanel187Layout.setHorizontalGroup(
            jPanel187Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel188, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
            .addGroup(jPanel187Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel187Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel222)
                    .addComponent(addProductOptionButton1))
                .addContainerGap())
        );
        jPanel187Layout.setVerticalGroup(
            jPanel187Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel187Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel222)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel188, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductOptionButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel185.add(jPanel187);

        jPanel189.setPreferredSize(new java.awt.Dimension(586, 40));

        jLabel223.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel223.setText("Variants");

        javax.swing.GroupLayout jPanel189Layout = new javax.swing.GroupLayout(jPanel189);
        jPanel189.setLayout(jPanel189Layout);
        jPanel189Layout.setHorizontalGroup(
            jPanel189Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel189Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel223)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel189Layout.setVerticalGroup(
            jPanel189Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel189Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel223)
                .addGap(11, 11, 11))
        );

        jPanel185.add(jPanel189);

        jPanel190.setLayout(new javax.swing.BoxLayout(jPanel190, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel191.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
        jPanel191.setMaximumSize(new java.awt.Dimension(32767, 35));
        jPanel191.setMinimumSize(new java.awt.Dimension(100, 35));
        jPanel191.setPreferredSize(new java.awt.Dimension(141, 35));

        jPanel192.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel224.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel224.setText("Variant");

        javax.swing.GroupLayout jPanel192Layout = new javax.swing.GroupLayout(jPanel192);
        jPanel192.setLayout(jPanel192Layout);
        jPanel192Layout.setHorizontalGroup(
            jPanel192Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel192Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel224, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        );
        jPanel192Layout.setVerticalGroup(
            jPanel192Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel224, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel193.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel225.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel225.setText("Price");

        javax.swing.GroupLayout jPanel193Layout = new javax.swing.GroupLayout(jPanel193);
        jPanel193.setLayout(jPanel193Layout);
        jPanel193Layout.setHorizontalGroup(
            jPanel193Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel193Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel225, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );
        jPanel193Layout.setVerticalGroup(
            jPanel193Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel225, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel194.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel226.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel226.setText("Stocks");

        javax.swing.GroupLayout jPanel194Layout = new javax.swing.GroupLayout(jPanel194);
        jPanel194.setLayout(jPanel194Layout);
        jPanel194Layout.setHorizontalGroup(
            jPanel194Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel194Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel226, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        );
        jPanel194Layout.setVerticalGroup(
            jPanel194Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel226, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel195.setPreferredSize(new java.awt.Dimension(150, 14));

        jLabel227.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel227.setText("Barcode");

        javax.swing.GroupLayout jPanel195Layout = new javax.swing.GroupLayout(jPanel195);
        jPanel195.setLayout(jPanel195Layout);
        jPanel195Layout.setHorizontalGroup(
            jPanel195Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel195Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel227, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
        );
        jPanel195Layout.setVerticalGroup(
            jPanel195Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel227, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel191Layout = new javax.swing.GroupLayout(jPanel191);
        jPanel191.setLayout(jPanel191Layout);
        jPanel191Layout.setHorizontalGroup(
            jPanel191Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel191Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel192, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel193, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel194, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel195, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel191Layout.setVerticalGroup(
            jPanel191Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel192, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel193, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel194, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(jPanel195, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        jPanel190.add(jPanel191);

        jPanel196.setLayout(new javax.swing.BoxLayout(jPanel196, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel197.setLayout(new javax.swing.BoxLayout(jPanel197, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel196.add(jPanel197);

        jPanel190.add(jPanel196);

        jPanel185.add(jPanel190);

        jScrollPane26.setViewportView(jPanel185);

        javax.swing.GroupLayout jPanel182Layout = new javax.swing.GroupLayout(jPanel182);
        jPanel182.setLayout(jPanel182Layout);
        jPanel182Layout.setHorizontalGroup(
            jPanel182Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel182Layout.setVerticalGroup(
            jPanel182Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout addProductPanelLayout = new javax.swing.GroupLayout(addProductPanel);
        addProductPanel.setLayout(addProductPanelLayout);
        addProductPanelLayout.setHorizontalGroup(
            addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addProductPanelLayout.createSequentialGroup()
                        .addComponent(jLabel207)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(addProductPanelLayout.createSequentialGroup()
                        .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel216)
                            .addComponent(jLabel217)
                            .addComponent(jLabel209)
                            .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(editProductBrand1, javax.swing.GroupLayout.Alignment.LEADING, 0, 138, Short.MAX_VALUE)
                                .addComponent(editProductType1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(22, 22, 22)
                        .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addProductPanelLayout.createSequentialGroup()
                                .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel208, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(editProductNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 367, Short.MAX_VALUE))
                            .addComponent(jSeparator4)
                            .addComponent(jPanel182, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        addProductPanelLayout.setVerticalGroup(
            addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProductPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel207, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addProductPanelLayout.createSequentialGroup()
                        .addComponent(jLabel208)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductNameTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel182, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(addProductPanelLayout.createSequentialGroup()
                        .addComponent(productImage2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel209)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel216)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductType1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel217)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editProductBrand1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(161, Short.MAX_VALUE))))
        );

        barcodeFrame.setAlwaysOnTop(true);
        barcodeFrame.setMinimumSize(new java.awt.Dimension(657, 416));

        jLabel230.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel230.setText("Barcode ");

        jPanel205.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel205MouseClicked(evt);
            }
        });

        jLabel231.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel231.setText("Search Barcode");

        jPanel212.setOpaque(false);
        jPanel212.setPreferredSize(new java.awt.Dimension(60, 60));

        jLabel238.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel212Layout = new javax.swing.GroupLayout(jPanel212);
        jPanel212.setLayout(jPanel212Layout);
        jPanel212Layout.setHorizontalGroup(
            jPanel212Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel238, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
        jPanel212Layout.setVerticalGroup(
            jPanel212Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel238, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel205Layout = new javax.swing.GroupLayout(jPanel205);
        jPanel205.setLayout(jPanel205Layout);
        jPanel205Layout.setHorizontalGroup(
            jPanel205Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel205Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel205Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel231, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addGroup(jPanel205Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel212, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel205Layout.setVerticalGroup(
            jPanel205Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel205Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jPanel212, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel231)
                .addGap(15, 15, 15))
        );

        jPanel207.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel207MouseClicked(evt);
            }
        });

        jLabel234.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel234.setText("Generate Barcode");

        jPanel210.setOpaque(false);
        jPanel210.setPreferredSize(new java.awt.Dimension(60, 60));

        jLabel239.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel210Layout = new javax.swing.GroupLayout(jPanel210);
        jPanel210.setLayout(jPanel210Layout);
        jPanel210Layout.setHorizontalGroup(
            jPanel210Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel239, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
        jPanel210Layout.setVerticalGroup(
            jPanel210Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel239, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel207Layout = new javax.swing.GroupLayout(jPanel207);
        jPanel207.setLayout(jPanel207Layout);
        jPanel207Layout.setHorizontalGroup(
            jPanel207Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel207Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel207Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel234, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addGroup(jPanel207Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel210, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel207Layout.setVerticalGroup(
            jPanel207Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel207Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jPanel210, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel234)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel203Layout = new javax.swing.GroupLayout(jPanel203);
        jPanel203.setLayout(jPanel203Layout);
        jPanel203Layout.setHorizontalGroup(
            jPanel203Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel203Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel203Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel230, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel205, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel207, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel203Layout.setVerticalGroup(
            jPanel203Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel203Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel230, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel205, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel207, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel208.setLayout(new javax.swing.BoxLayout(jPanel208, javax.swing.BoxLayout.PAGE_AXIS));

        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField15.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton33.setBackground(new java.awt.Color(0, 144, 228));
        jButton33.setForeground(new java.awt.Color(255, 255, 255));
        jButton33.setText("Generate Barcode");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jLabel229.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel232.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel232.setText("Generate Barcode");

        jButton34.setText("Save & Print");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel204Layout = new javax.swing.GroupLayout(jPanel204);
        jPanel204.setLayout(jPanel204Layout);
        jPanel204Layout.setHorizontalGroup(
            jPanel204Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel204Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel204Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField15)
                    .addComponent(jButton34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel229, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jLabel232, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel204Layout.setVerticalGroup(
            jPanel204Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel204Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel232)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel229, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel208.add(jPanel204);

        jLabel235.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel235.setText("Search Barcode");

        jTextField16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
        });

        jButton35.setBackground(new java.awt.Color(0, 144, 228));
        jButton35.setForeground(new java.awt.Color(255, 255, 255));
        jButton35.setText("Search");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jLabel236.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel236.setText("Results");

        jScrollPane27.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel211.setLayout(new javax.swing.BoxLayout(jPanel211, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane27.setViewportView(jPanel211);

        javax.swing.GroupLayout jPanel209Layout = new javax.swing.GroupLayout(jPanel209);
        jPanel209.setLayout(jPanel209Layout);
        jPanel209Layout.setHorizontalGroup(
            jPanel209Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel209Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel209Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane27)
                    .addComponent(jTextField16)
                    .addComponent(jButton35, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel209Layout.createSequentialGroup()
                        .addGroup(jPanel209Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel236)
                            .addComponent(jLabel235))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel209Layout.setVerticalGroup(
            jPanel209Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel209Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel235)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel236)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        jPanel208.add(jPanel209);

        javax.swing.GroupLayout barcodeFrameLayout = new javax.swing.GroupLayout(barcodeFrame.getContentPane());
        barcodeFrame.getContentPane().setLayout(barcodeFrameLayout);
        barcodeFrameLayout.setHorizontalGroup(
            barcodeFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barcodeFrameLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel203, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel208, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        barcodeFrameLayout.setVerticalGroup(
            barcodeFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel203, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel208, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane29.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane29.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jScrollPane29MouseWheelMoved(evt);
            }
        });
        jScrollPane29.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jScrollPane29PropertyChange(evt);
            }
        });

        jPanel214.setOpaque(false);
        jPanel214.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel214MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel214MouseExited(evt);
            }
        });

        jLabel245.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel245.setForeground(new java.awt.Color(51, 51, 51));
        jLabel245.setText("Top Performers");

        jLabel246.setForeground(new java.awt.Color(51, 51, 51));
        jLabel246.setText("You have 50 personel on your market");

        jLabel247.setForeground(new java.awt.Color(51, 51, 51));
        jLabel247.setText("Annual Sales");

        jLabel248.setForeground(new java.awt.Color(51, 51, 51));
        jLabel248.setText("Sales /Day");

        jLabel249.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel249.setForeground(new java.awt.Color(51, 51, 51));
        jLabel249.setText("53400");

        jLabel250.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel250.setForeground(new java.awt.Color(51, 51, 51));
        jLabel250.setText("20");

        jPanel73.setBackground(new java.awt.Color(0, 102, 255));
        jPanel73.setOpaque(false);
        jPanel73.setPreferredSize(new java.awt.Dimension(0, 173));

        jScrollPane33.setBackground(new java.awt.Color(102, 102, 255));
        jScrollPane33.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane33.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane33.setOpaque(false);
        jScrollPane33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jScrollPane33MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jScrollPane33MouseExited(evt);
            }
        });

        jPanel215.setBackground(new java.awt.Color(255, 171, 145));
        jPanel215.setPreferredSize(new java.awt.Dimension(600, 171));
        jPanel215.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel215MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel215MouseExited(evt);
            }
        });
        jPanel215.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jPanel217.setMaximumSize(new java.awt.Dimension(150, 161));
        jPanel217.setMinimumSize(new java.awt.Dimension(150, 161));
        jPanel217.setOpaque(false);
        jPanel217.setPreferredSize(new java.awt.Dimension(150, 161));

        jLabel251.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel251.setPreferredSize(new java.awt.Dimension(46, 46));

        jLabel252.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel252.setForeground(new java.awt.Color(51, 51, 51));
        jLabel252.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel252.setText("Employee Name");

        jLabel253.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel253.setForeground(new java.awt.Color(51, 51, 51));
        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel253.setText("40%");

        javax.swing.GroupLayout jPanel217Layout = new javax.swing.GroupLayout(jPanel217);
        jPanel217.setLayout(jPanel217Layout);
        jPanel217Layout.setHorizontalGroup(
            jPanel217Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel252, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel253, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel217Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel251, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel217Layout.setVerticalGroup(
            jPanel217Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel217Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel251, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel252)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel253)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel215.add(jPanel217);

        jScrollPane33.setViewportView(jPanel215);

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane33, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane33, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel214Layout = new javax.swing.GroupLayout(jPanel214);
        jPanel214.setLayout(jPanel214Layout);
        jPanel214Layout.setHorizontalGroup(
            jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel214Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel214Layout.createSequentialGroup()
                        .addGroup(jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel247)
                            .addComponent(jLabel249))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel248, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel250)))
                    .addGroup(jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel245, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel246, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel73, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel214Layout.setVerticalGroup(
            jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel214Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel245)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel246)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel249, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel250, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(jPanel214Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel247)
                    .addComponent(jLabel248))
                .addGap(36, 36, 36))
            .addComponent(jPanel73, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );

        jLabel254.setText("Active");

        jLabel255.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel255.setText("9");

        personelActiveIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel256.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel256.setText("12");

        personelDeactiveIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel257.setText("Deactivated");

        personelDeactiveIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel267.setText("Total Personnel");

        jLabel268.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel268.setText("12");

        javax.swing.GroupLayout jPanel179Layout = new javax.swing.GroupLayout(jPanel179);
        jPanel179.setLayout(jPanel179Layout);
        jPanel179Layout.setHorizontalGroup(
            jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel179Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel179Layout.createSequentialGroup()
                        .addComponent(personelActiveIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel255, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel254))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel179Layout.createSequentialGroup()
                        .addComponent(personelDeactiveIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel256, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel257))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel179Layout.createSequentialGroup()
                        .addComponent(personelDeactiveIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel268, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel267))
                .addGap(15, 15, 15))
        );
        jPanel179Layout.setVerticalGroup(
            jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel179Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel179Layout.createSequentialGroup()
                        .addComponent(jLabel267)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel268, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(personelDeactiveIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel179Layout.createSequentialGroup()
                        .addComponent(jLabel257)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel256, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(personelDeactiveIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel179Layout.createSequentialGroup()
                        .addComponent(jLabel254)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel179Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel255, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(personelActiveIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel218.setPreferredSize(null);
        jPanel218.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        javax.swing.GroupLayout jPanel221Layout = new javax.swing.GroupLayout(jPanel221);
        jPanel221.setLayout(jPanel221Layout);
        jPanel221Layout.setHorizontalGroup(
            jPanel221Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel221Layout.setVerticalGroup(
            jPanel221Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel269.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel269.setText("Personnel Performance");

        jLabel270.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel270.setText("Personnel List");

        jPanel223.setOpaque(false);
        jPanel223.setPreferredSize(new java.awt.Dimension(387, 60));

        jTextField18.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField18FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField18FocusLost(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Employee", "Admin" }));
        jComboBox2.setPreferredSize(new java.awt.Dimension(86, 48));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(0, 144, 228));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Add new account");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel223Layout = new javax.swing.GroupLayout(jPanel223);
        jPanel223.setLayout(jPanel223Layout);
        jPanel223Layout.setHorizontalGroup(
            jPanel223Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel223Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel223Layout.setVerticalGroup(
            jPanel223Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel223Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel223Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel180Layout = new javax.swing.GroupLayout(jPanel180);
        jPanel180.setLayout(jPanel180Layout);
        jPanel180Layout.setHorizontalGroup(
            jPanel180Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel180Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel180Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel218, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel180Layout.createSequentialGroup()
                        .addComponent(jPanel179, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel221, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel180Layout.createSequentialGroup()
                        .addGroup(jPanel180Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel269, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel270, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel223, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                    .addComponent(jPanel214, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel180Layout.setVerticalGroup(
            jPanel180Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel180Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel269)
                .addGap(15, 15, 15)
                .addComponent(jPanel214, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel270)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel180Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel179, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel221, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel223, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel218, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 50, Short.MAX_VALUE))
        );

        jScrollPane29.setViewportView(jPanel180);

        javax.swing.GroupLayout personelMainPanelLayout = new javax.swing.GroupLayout(personelMainPanel);
        personelMainPanel.setLayout(personelMainPanelLayout);
        personelMainPanelLayout.setHorizontalGroup(
            personelMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personelMainPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane29)
                .addGap(20, 20, 20))
        );
        personelMainPanelLayout.setVerticalGroup(
            personelMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane29)
        );

        jScrollPane11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel90.setLayout(new javax.swing.BoxLayout(jPanel90, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel282.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel282.setText("Sales Data");

        jPanel50.setOpaque(false);
        jPanel50.setPreferredSize(new java.awt.Dimension(456, 48));

        jPanel56.setOpaque(false);
        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.LINE_AXIS));

        jPanel75.setOpaque(false);
        jPanel75.setPreferredSize(new java.awt.Dimension(150, 48));

        jLabel288.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel288.setText("Time Worked");

        jLabel283.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel283.setText("jLabel283");

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel285, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel288)
                    .addComponent(jLabel283))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel285, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel75Layout.createSequentialGroup()
                        .addComponent(jLabel288)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel283)))
                .addContainerGap())
        );

        jPanel56.add(jPanel75);

        jPanel87.setOpaque(false);
        jPanel87.setPreferredSize(new java.awt.Dimension(150, 48));

        jLabel289.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel289.setText("Transactions");

        jLabel284.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel284.setText("jLabel284");

        javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
        jPanel87.setLayout(jPanel87Layout);
        jPanel87Layout.setHorizontalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel87Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel286, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel289)
                    .addComponent(jLabel284))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel87Layout.setVerticalGroup(
            jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel87Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel87Layout.createSequentialGroup()
                        .addComponent(jLabel289)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel284))
                    .addComponent(jLabel286, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel56.add(jPanel87);

        jPanel89.setOpaque(false);
        jPanel89.setPreferredSize(new java.awt.Dimension(150, 48));

        jLabel290.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel290.setText("Member Since");

        jLabel291.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel291.setText("jLabel291");

        javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
        jPanel89.setLayout(jPanel89Layout);
        jPanel89Layout.setHorizontalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel287, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel291)
                    .addComponent(jLabel290))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel89Layout.setVerticalGroup(
            jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel89Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel287, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel89Layout.createSequentialGroup()
                        .addComponent(jLabel290)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel291)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel56.add(jPanel89);

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(jPanel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel70Layout.createSequentialGroup()
                        .addComponent(jLabel282)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel282)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane11.setViewportView(jPanel70);

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel130.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel130.setText("Paul Justine Faustino");

        jPanel68.setPreferredSize(new java.awt.Dimension(110, 30));

        jLabel275.setText("Deactivated");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel274, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel275)
                .addGap(12, 12, 12))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel274, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel275, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setText("Active");

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel131, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel131, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jButton15.setText("Set as Admin");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Deactivate");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel276.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel276.setText("Address:");

        jLabel279.setText("test");

        jLabel277.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel277.setText("Gender:");

        jLabel280.setText("Male");

        jLabel278.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel278.setText("Age:");

        jLabel281.setText("22");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setText("Birth Date:");

        jLabel69.setText("jLabel69");

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setText("Contact #:");

        jLabel71.setText("jLabel71");

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addComponent(jLabel276)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel279)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addComponent(jLabel277)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel280)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel278)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel281))
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel69))
                    .addGroup(jPanel85Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel276)
                    .addComponent(jLabel279))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel277)
                    .addComponent(jLabel280)
                    .addComponent(jLabel278)
                    .addComponent(jLabel281))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(jLabel69))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel130)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel130)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout personnelViewDetailsPanelLayout = new javax.swing.GroupLayout(personnelViewDetailsPanel);
        personnelViewDetailsPanel.setLayout(personnelViewDetailsPanelLayout);
        personnelViewDetailsPanelLayout.setHorizontalGroup(
            personnelViewDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personnelViewDetailsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        personnelViewDetailsPanelLayout.setVerticalGroup(
            personnelViewDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane30.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel301.setText("Registered");

        jLabel302.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel302.setText("9");

        customerRegisteredIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel303.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel303.setText("12");

        customerUnregisteredIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel304.setText("Unregistered");

        personelDeactiveIcon3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel305.setText("Total Customer");

        jLabel306.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel306.setText("12");

        javax.swing.GroupLayout jPanel213Layout = new javax.swing.GroupLayout(jPanel213);
        jPanel213.setLayout(jPanel213Layout);
        jPanel213Layout.setHorizontalGroup(
            jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel213Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel213Layout.createSequentialGroup()
                        .addComponent(customerRegisteredIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel302, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel301))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel213Layout.createSequentialGroup()
                        .addComponent(customerUnregisteredIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel303, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel304))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel213Layout.createSequentialGroup()
                        .addComponent(personelDeactiveIcon3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel306, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel305))
                .addGap(15, 15, 15))
        );
        jPanel213Layout.setVerticalGroup(
            jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel213Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel213Layout.createSequentialGroup()
                        .addComponent(jLabel305)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel306, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(personelDeactiveIcon3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel213Layout.createSequentialGroup()
                        .addComponent(jLabel304)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel303, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerUnregisteredIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel213Layout.createSequentialGroup()
                        .addComponent(jLabel301)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel213Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel302, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerRegisteredIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel226.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jPanel227.setMaximumSize(null);
        jPanel227.setOpaque(false);
        jPanel227.setPreferredSize(new java.awt.Dimension(280, 135));

        jLabel307.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel307.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel229.setOpaque(false);

        jLabel314.setText("Blocked");

        jLabel315.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel229Layout = new javax.swing.GroupLayout(jPanel229);
        jPanel229.setLayout(jPanel229Layout);
        jPanel229Layout.setHorizontalGroup(
            jPanel229Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel229Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel315, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel314)
                .addGap(12, 12, 12))
        );
        jPanel229Layout.setVerticalGroup(
            jPanel229Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel314, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(jLabel315, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Paul Justine Faustino");

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("099989898");

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addContainerGap())
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel227Layout = new javax.swing.GroupLayout(jPanel227);
        jPanel227.setLayout(jPanel227Layout);
        jPanel227Layout.setHorizontalGroup(
            jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel227Layout.createSequentialGroup()
                .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel227Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel229, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel227Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel307, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addGroup(jPanel227Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(15, 15, 15))
        );
        jPanel227Layout.setVerticalGroup(
            jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel227Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel307, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel227Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel227Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel229, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel226.add(jPanel227);

        javax.swing.GroupLayout jPanel230Layout = new javax.swing.GroupLayout(jPanel230);
        jPanel230.setLayout(jPanel230Layout);
        jPanel230Layout.setHorizontalGroup(
            jPanel230Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel230Layout.setVerticalGroup(
            jPanel230Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel317.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel317.setText("Customer List");

        jPanel231.setOpaque(false);
        jPanel231.setPreferredSize(new java.awt.Dimension(387, 60));

        jTextField25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField25FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField25FocusLost(evt);
            }
        });

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Blocked" }));
        jComboBox6.setPreferredSize(new java.awt.Dimension(86, 48));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(0, 144, 228));
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Add new customer");
        jButton12.setPreferredSize(new java.awt.Dimension(81, 48));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel231Layout = new javax.swing.GroupLayout(jPanel231);
        jPanel231.setLayout(jPanel231Layout);
        jPanel231Layout.setHorizontalGroup(
            jPanel231Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel231Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel231Layout.setVerticalGroup(
            jPanel231Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel231Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel231Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField25)
                    .addGroup(jPanel231Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Statistics ");

        jPanel49.setPreferredSize(new java.awt.Dimension(0, 300));
        jPanel49.setLayout(new javax.swing.BoxLayout(jPanel49, javax.swing.BoxLayout.LINE_AXIS));

        jPanel63.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel64.setPreferredSize(new java.awt.Dimension(290, 244));
        jPanel64.setLayout(new javax.swing.BoxLayout(jPanel64, javax.swing.BoxLayout.LINE_AXIS));

        jPanel98.setLayout(new javax.swing.BoxLayout(jPanel98, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel98, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel65.setPreferredSize(new java.awt.Dimension(93, 40));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("November 25");
        jLabel21.setPreferredSize(new java.awt.Dimension(100, 40));

        jYearChooser2.setPreferredSize(new java.awt.Dimension(64, 40));
        jYearChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jYearChooser2PropertyChange(evt);
            }
        });

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Monthly", "All Data" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });

        jMonthChooser1.setPreferredSize(new java.awt.Dimension(125, 40));
        jMonthChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jMonthChooser1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel65Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox8)))
            .addComponent(jMonthChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jYearChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel95.setMinimumSize(new java.awt.Dimension(100, 40));
        jPanel95.setLayout(new javax.swing.BoxLayout(jPanel95, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane28.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane28.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane28.setPreferredSize(new java.awt.Dimension(602, 75));

        jPanel25.setPreferredSize(new java.awt.Dimension(600, 65));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        jToggleButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jToggleButton1.setText("32");
        jToggleButton1.setMaximumSize(new java.awt.Dimension(54, 60));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(54, 60));
        jToggleButton1.setPreferredSize(new java.awt.Dimension(54, 60));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel25.add(jToggleButton1);

        jScrollPane28.setViewportView(jPanel25);

        jPanel95.add(jScrollPane28);

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                    .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel95, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel65.getAccessibleContext().setAccessibleDescription("");

        unregisteredCustomerPanel.setPreferredSize(new java.awt.Dimension(304, 127));

        unregisteredCustomerListPanel.setLayout(new javax.swing.BoxLayout(unregisteredCustomerListPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Unregistered Customer");

        javax.swing.GroupLayout unregisteredCustomerPanelLayout = new javax.swing.GroupLayout(unregisteredCustomerPanel);
        unregisteredCustomerPanel.setLayout(unregisteredCustomerPanelLayout);
        unregisteredCustomerPanelLayout.setHorizontalGroup(
            unregisteredCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(unregisteredCustomerListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        unregisteredCustomerPanelLayout.setVerticalGroup(
            unregisteredCustomerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unregisteredCustomerPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(unregisteredCustomerListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel206Layout = new javax.swing.GroupLayout(jPanel206);
        jPanel206.setLayout(jPanel206Layout);
        jPanel206Layout.setHorizontalGroup(
            jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel206Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel206Layout.createSequentialGroup()
                        .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel206Layout.createSequentialGroup()
                        .addGroup(jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel206Layout.createSequentialGroup()
                                .addComponent(jPanel213, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel230, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel206Layout.createSequentialGroup()
                                .addGroup(jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel317, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel206Layout.createSequentialGroup()
                        .addComponent(jPanel231, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel206Layout.createSequentialGroup()
                        .addComponent(jPanel226, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unregisteredCustomerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel206Layout.setVerticalGroup(
            jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel206Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel317)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel213, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel230, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel231, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel206Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel206Layout.createSequentialGroup()
                        .addComponent(jPanel226, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel206Layout.createSequentialGroup()
                        .addComponent(unregisteredCustomerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                        .addContainerGap(123, Short.MAX_VALUE))))
        );

        jScrollPane30.setViewportView(jPanel206);

        javax.swing.GroupLayout customerMainPanelLayout = new javax.swing.GroupLayout(customerMainPanel);
        customerMainPanel.setLayout(customerMainPanelLayout);
        customerMainPanelLayout.setHorizontalGroup(
            customerMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerMainPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        customerMainPanelLayout.setVerticalGroup(
            customerMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
        );

        jScrollPane15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane15.setViewportView(jPanel88);

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );

        archivedTabPane.addTab("Products", jPanel58);

        jScrollPane31.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane31.setViewportView(jPanel20);

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane31, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );

        archivedTabPane.addTab("Personnels", jPanel59);

        jScrollPane32.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane32.setViewportView(jPanel52);

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane32, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane32)
        );

        archivedTabPane.addTab("Customers", jPanel60);

        javax.swing.GroupLayout archiveMainPanelLayout = new javax.swing.GroupLayout(archiveMainPanel);
        archiveMainPanel.setLayout(archiveMainPanelLayout);
        archiveMainPanelLayout.setHorizontalGroup(
            archiveMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(archivedTabPane)
        );
        archiveMainPanelLayout.setVerticalGroup(
            archiveMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(archivedTabPane)
        );

        jScrollPane13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel91.setLayout(new javax.swing.BoxLayout(jPanel91, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel292.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel292.setText("Order Data");

        jPanel84.setOpaque(false);

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel60.setText("Ordered Products");

        jPanel86.setOpaque(false);
        jPanel86.setLayout(new javax.swing.BoxLayout(jPanel86, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel80Layout.createSequentialGroup()
                        .addComponent(jLabel292)
                        .addGap(0, 630, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel292)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel91, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane13.setViewportView(jPanel80);

        jLabel233.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel237.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel237.setText("Name");
        jLabel237.setMinimumSize(new java.awt.Dimension(100, 33));
        jLabel237.setPreferredSize(null);

        jPanel97.setPreferredSize(new java.awt.Dimension(110, 30));

        jLabel310.setText("Blocked");

        javax.swing.GroupLayout jPanel97Layout = new javax.swing.GroupLayout(jPanel97);
        jPanel97.setLayout(jPanel97Layout);
        jPanel97Layout.setHorizontalGroup(
            jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel97Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel309, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel310)
                .addGap(12, 12, 12))
        );
        jPanel97Layout.setVerticalGroup(
            jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel309, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel310, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLabel312.setText("Address:");
        jLabel312.setPreferredSize(new java.awt.Dimension(45, 30));

        jLabel313.setText("Gender:");
        jLabel313.setPreferredSize(new java.awt.Dimension(41, 30));

        jLabel316.setText("Birth Date:");
        jLabel316.setPreferredSize(new java.awt.Dimension(24, 30));

        jButton14.setBackground(new java.awt.Color(0, 144, 228));
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Edit");
        jButton14.setPreferredSize(new java.awt.Dimension(81, 40));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTextField8.setPreferredSize(new java.awt.Dimension(160, 30));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jTextField11.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jTextField11.setText("Name");
        jTextField11.setMinimumSize(new java.awt.Dimension(120, 33));
        jTextField11.setPreferredSize(null);

        jButton39.setBackground(new java.awt.Color(0, 144, 228));
        jButton39.setForeground(new java.awt.Color(255, 255, 255));
        jButton39.setText("Save");
        jButton39.setPreferredSize(new java.awt.Dimension(81, 40));
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton22.setText("Block Customer");
        jButton22.setPreferredSize(new java.awt.Dimension(114, 40));
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton40.setText("Delete");
        jButton40.setPreferredSize(new java.awt.Dimension(72, 40));
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel72.setText("Age:");

        jLabel73.setText("Contact #:");

        jTextField26.setPreferredSize(new java.awt.Dimension(160, 30));

        jLabel76.setText("0");

        jRadioButton7.setSelected(true);
        jRadioButton7.setText("Male");

        jRadioButton8.setText("Female");
        jRadioButton8.setFocusable(false);

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13)
            .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addComponent(jLabel233, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel97, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel76Layout.createSequentialGroup()
                                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel316, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel312, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                    .addComponent(jLabel313, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel76Layout.createSequentialGroup()
                                        .addComponent(jRadioButton7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton8)))))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel76Layout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel76))
                            .addGroup(jPanel76Layout.createSequentialGroup()
                                .addComponent(jLabel73)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel76Layout.createSequentialGroup()
                                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel76Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel237, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jButton39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel97, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel312, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel313, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel76)
                                    .addComponent(jRadioButton7)
                                    .addComponent(jRadioButton8)))
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel316, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addComponent(jLabel233, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout customerViewDetailsPanelLayout = new javax.swing.GroupLayout(customerViewDetailsPanel);
        customerViewDetailsPanel.setLayout(customerViewDetailsPanelLayout);
        customerViewDetailsPanelLayout.setHorizontalGroup(
            customerViewDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerViewDetailsPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        customerViewDetailsPanelLayout.setVerticalGroup(
            customerViewDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        createCustomerDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        createCustomerDialog.setAlwaysOnTop(true);
        createCustomerDialog.setMinimumSize(new java.awt.Dimension(631, 520));
        createCustomerDialog.getContentPane().setLayout(new javax.swing.BoxLayout(createCustomerDialog.getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        searchPanel.setOpaque(false);
        searchPanel.setLayout(new javax.swing.BoxLayout(searchPanel, javax.swing.BoxLayout.PAGE_AXIS));

        searchPanel2.setOpaque(false);
        searchPanel2.setLayout(new javax.swing.BoxLayout(searchPanel2, javax.swing.BoxLayout.LINE_AXIS));

        glassPanel.setOpaque(false);
        glassPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                glassPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout glassPanelLayout = new javax.swing.GroupLayout(glassPanel);
        glassPanel.setLayout(glassPanelLayout);
        glassPanelLayout.setHorizontalGroup(
            glassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        glassPanelLayout.setVerticalGroup(
            glassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        selectCustomerPopupPanel.setMaximumSize(new java.awt.Dimension(300, 270));
        selectCustomerPopupPanel.setMinimumSize(new java.awt.Dimension(300, 270));
        selectCustomerPopupPanel.setPreferredSize(new java.awt.Dimension(300, 270));
        selectCustomerPopupPanel.setLayout(new javax.swing.BoxLayout(selectCustomerPopupPanel, javax.swing.BoxLayout.PAGE_AXIS));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Point of Sale - TimeSquare");
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        menuPanel.setBackground(new java.awt.Color(238, 238, 238));
        menuPanel.setPreferredSize(new java.awt.Dimension(60, 559));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.setOpaque(false);
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

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
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 7)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Transaction");
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
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        salesMainMenuLayout.setVerticalGroup(
            salesMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel40)
                .addGap(9, 9, 9))
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
        jLabel42.setText("Inventory");
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
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        adminMenuPanel.setOpaque(false);

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
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
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

        personnelMainMenu.setBackground(new java.awt.Color(238, 238, 238));
        personnelMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        personnelMainMenu.setPreferredSize(new java.awt.Dimension(50, 59));
        personnelMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                personnelMainMenuMouseClicked(evt);
            }
        });

        jLabel271.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel271.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel271.setText("Personnel");
        jLabel271.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel272.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout personnelMainMenuLayout = new javax.swing.GroupLayout(personnelMainMenu);
        personnelMainMenu.setLayout(personnelMainMenuLayout);
        personnelMainMenuLayout.setHorizontalGroup(
            personnelMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel271, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel272, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        personnelMainMenuLayout.setVerticalGroup(
            personnelMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personnelMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel272, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel271, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        customerMainMenu.setBackground(new java.awt.Color(238, 238, 238));
        customerMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        customerMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                customerMainMenuMouseClicked(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Customer");
        jLabel53.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout customerMainMenuLayout = new javax.swing.GroupLayout(customerMainMenu);
        customerMainMenu.setLayout(customerMainMenuLayout);
        customerMainMenuLayout.setHorizontalGroup(
            customerMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerMainMenuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(customerMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        customerMainMenuLayout.setVerticalGroup(
            customerMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        archiveMainMenu.setBackground(new java.awt.Color(238, 238, 238));
        archiveMainMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        archiveMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                archiveMainMenuMouseClicked(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Archive");
        jLabel58.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout archiveMainMenuLayout = new javax.swing.GroupLayout(archiveMainMenu);
        archiveMainMenu.setLayout(archiveMainMenuLayout);
        archiveMainMenuLayout.setHorizontalGroup(
            archiveMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archiveMainMenuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(archiveMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        archiveMainMenuLayout.setVerticalGroup(
            archiveMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, archiveMainMenuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout adminMenuPanelLayout = new javax.swing.GroupLayout(adminMenuPanel);
        adminMenuPanel.setLayout(adminMenuPanelLayout);
        adminMenuPanelLayout.setHorizontalGroup(
            adminMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardMainMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(personnelMainMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(customerMainMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(archiveMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        adminMenuPanelLayout.setVerticalGroup(
            adminMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminMenuPanelLayout.createSequentialGroup()
                .addComponent(dashboardMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(personnelMainMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customerMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(archiveMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(adminMenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productMainMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(salesMainMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ticketMainMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, menuPanelLayout.createSequentialGroup()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addGap(31, 31, 31)
                .addComponent(adminMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(ticketMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salesMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        contentPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        contentPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contentPanelMouseClicked(evt);
            }
        });
        contentPanel.setLayout(new java.awt.BorderLayout());

        mainHeaderPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));

        menuHeader.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        menuHeader.setText("Dashboard");

        accountProfilePicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accountProfilePicture.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                    .addComponent(accountProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 804, Short.MAX_VALUE)
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
                    .addComponent(mainHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 1140, Short.MAX_VALUE)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
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
        if (darkRB.isSelected()) {
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
            productCategoryPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(55, 55, 65)));
            jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(55, 55, 65)));
//            jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(55, 55, 65)));
            jSeparator3.setForeground(new Color(55, 55, 65));
            ticketSearchBar.setBackground(new Color(40, 40, 50));
            salesHistorySearchBar.setBackground(new Color(40, 40, 50));
            ticketSearchBar1.setBackground(new Color(40, 40, 50));
            
            jSeparator1.setForeground(new Color(55, 55, 65));
//            jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(55, 55, 65)));
            jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(63, 63, 63)));
            
            dashboardMainMenu.setBackground(new Color(33, 37, 43));
            dashboardMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            ticketMainMenu.setBackground(new Color(33, 37, 43));
            ticketMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            salesMainMenu.setBackground(new Color(33, 37, 43));
            salesMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            productMainMenu.setBackground(new Color(33, 37, 43));
            productMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            personnelMainMenu.setBackground(new Color(33, 37, 43));
            personnelMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            customerMainMenu.setBackground(new Color(33, 37, 43));
            customerMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            archiveMainMenu.setBackground(new Color(33, 37, 43));
            archiveMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(63, 63, 63), 1, 25));
            
            FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
            icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
                    return new Color(154, 154, 154);
                }
            }));
            jButton10.setIcon(icon);
            jButton31.setIcon(icon);
//            jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/barcode-icon-white.png")));
            
            if (selectedMenu == dashboardMainMenu) {
                dashboardMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == ticketMainMenu) {
                ticketMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == salesMainMenu) {
                salesMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == productMainMenu) {
                productMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == personnelMainMenu){
                personnelMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == customerMainMenu){
                customerMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == archiveMainMenu){
                archiveMainMenu.setBackground(new Color(101, 55, 255));
            }

//            checkoutCashIconCont.setBackground(new Color(50, 55, 64));
//            checkoutCreditIconCont.setBackground(new Color(50, 55, 64));
            resetCheckoutPayment();
            
            updateGraphics();
        }
        
    }//GEN-LAST:event_darkRBActionPerformed

    private void lightRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lightRBActionPerformed
        if (lightRB.isSelected()) {

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
            productCategoryPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
            jPanel18.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(240, 240, 240)));
//            jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
            jSeparator3.setForeground(new Color(240, 240, 240));
            
            jSeparator1.setForeground(new Color(240, 240, 240));
//            jPanel48.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(240, 240, 240)));
            jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(234, 234, 234)));
            //Search Field
            ticketSearchBar.setBackground(new Color(245, 245, 245));
            salesHistorySearchBar.setBackground(new Color(245, 245, 245));
            ticketSearchBar1.setBackground(new Color(245, 245, 245));
            
            dashboardMainMenu.setBackground(new Color(238, 238, 238));
            dashboardMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            ticketMainMenu.setBackground(new Color(238, 238, 238));
            ticketMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            salesMainMenu.setBackground(new Color(238, 238, 238));
            salesMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            productMainMenu.setBackground(new Color(238, 238, 238));
            productMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            personnelMainMenu.setBackground(new Color(238, 238, 238));
            personnelMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            customerMainMenu.setBackground(new Color(238, 238, 238));
            customerMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            archiveMainMenu.setBackground(new Color(238, 238, 238));
            archiveMainMenu.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(203, 203, 203), 1, 25));
            
            FlatSVGIcon icon = new FlatSVGIcon("img/icon/barcode-scanner.svg", 25, 25);
            icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
//                    return new Color(64, 64, 64);
                    return new Color(154, 154, 154);
                }
            }));
            jButton10.setIcon(icon);
            jButton31.setIcon(icon);
//            jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/barcode-scanner-icon.png")));
            
            if (selectedMenu == dashboardMainMenu) {
                dashboardMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == ticketMainMenu) {
                ticketMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == salesMainMenu) {
                salesMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == productMainMenu) {
                productMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == personnelMainMenu){
                personnelMainMenu.setBackground(new Color(101, 55, 255));
            }  else if (selectedMenu == customerMainMenu){
                customerMainMenu.setBackground(new Color(101, 55, 255));
            } else if (selectedMenu == archiveMainMenu){
                archiveMainMenu.setBackground(new Color(101, 55, 255));
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
        glassPanel.setBounds(0, 0, getWidth(), getHeight());
        
        notificationPanel.setBounds(jLabel56.getX() - 210, jLabel56.getY(), 300, 400);
        
        Rectangle searchPosition = contentPanel.getBounds();
        int searchX = 28+(contentPanel.getWidth() / 2) - 190;
        searchPanel.setBounds(searchX, 84, 380, 200);
        
        
        
        
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
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            BufferedImage bufferedImage = null;
            try {
//                String cwd = System.getProperty("user.dir");
//                bufferedImage = ImageIO.read(new File(cwd + filename));
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
            
            System.out.println(getClass().getResource("/img/product/" + filename));
            
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
        } else {
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
        menuHeader.setText("Transaction Data");
        contentPanel.removeAll();
        contentPanel.add(salesHistoryPanel);
        
        resetMainMenu();
        FlatSVGIcon salesicon = new FlatSVGIcon("img/icon/sales-icon.svg", 35, 35);
        salesicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel41.setIcon(salesicon);
//        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/sales-icon-white.png")));
        jLabel40.setForeground(Color.white);
        
        jPanel9.removeAll();
        jPanel8.removeAll();
        if(!this.user.getRole().equals("admin")){
            java.sql.Date sqldate = new java.sql.Date(jCalendar1.getDate().getTime());
            java.sql.Date sqldate2 = new java.sql.Date(jCalendar2.getDate().getTime());

            jPanel9.add(jPanel99);
            jPanel8.add(jPanel101);
            
            jPanel178.removeAll();
            TransactionHistoryService ths = new TransactionHistoryService();
            if (ths.getAllTransactionHistoryDetails() != null) {
                ths.getAllTransactionHistoryDetails().forEach(e -> {
                    if (sqldate.toString().equals(e.getTransactionDate().toString())) {
                        OrderHistoryThumb oht = new OrderHistoryThumb();
                        oht.setTransactionHistory(e);
                        jPanel178.add(oht);
                    }
                });
            }

            jPanel216.removeAll();
            ServiceTicketsService serviceTicket = new ServiceTicketsService();
            serviceTicket.getAllServiceTicketsDetails().forEach(e -> {
                if (sqldate2.toString().equals(e.getWalkInDate().toString())) {
                    ServiceThumb2 sThumb = new ServiceThumb2();
                    sThumb.setRequiredAdmin(true);
                    sThumb.setServiceTicket(e);
                    jPanel216.add(sThumb);
                }
            });

            prevDate = new java.sql.Date(jCalendar1.getDate().getTime()).toString();
            prevDate2 = new java.sql.Date(jCalendar2.getDate().getTime()).toString();
        
            
        }else{
            jPanel9.add(adminSalesPanel);
            jPanel8.add(adminServiceTicketPanel);
        }
//        jPanel9.removeAll();
//        if(this.user.getRole().equals("admin")){
//            
//        }else{
//            
//        }
        
        updateGraphics();
    }//GEN-LAST:event_salesMainMenuMouseClicked
    public void updateTicketPanel(){
        menuHeader.setText("Create Tickets");
        contentPanel.removeAll();
        contentPanel.add(ticketMainPanel);
        
        resetMainMenu();
        FlatSVGIcon carticon = new FlatSVGIcon("img/icon/cart-icon.svg", 35, 35);
        carticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel33.setIcon(carticon);
//        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/cart-icon-white.png")));
        jLabel32.setForeground(Color.white);
        
        jPanel19.removeAll();

        //Category test UI Database
//        CategoryService categoryService = new CategoryService();
//        
//        List<String> type = new ArrayList();
//        List<Category> categoryList = new ArrayList<>();
//        
//        categoryList = categoryService.getAllCategory();
//        
//        categoryList.forEach(e -> {
//            type.add(e.getType());
//        });
//        
//        List<String> sortedType = removeDuplicates(type);
//        
//        sortedType.forEach(e -> {
//            CategorySectionPanel categoryPanel = new CategorySectionPanel();
//            categoryPanel.setCategoryType(e);
//            
//            HashMap<String, Integer> brands = new HashMap<>();
//
//            categoryService.getAllCategory().forEach(e2->{
//                if(e2.getType().equals(e)){
//
//                    if(brands.isEmpty()){
//                        brands.put(e2.getBrand(), 1);
//                        System.out.println("Empty until: "  + e2.getBrand());
//                    }else{
//                        
//                        if(brands.get(e2.getBrand()) != null){
//                            brands.put(e2.getBrand(), brands.get(e2.getBrand()) + 1);
//                        }else{
//                            brands.put(e2.getBrand(), 1);
//                        }
//                        
////                        for (String i : brands.keySet()) {
////                            if(i.equals(e2.getBrand())){
////                                brands.put(i, brands.get(i) + 1);
////                                System.out.println("Equals: " + i + " Value: " + String.valueOf(brands.get(i)));
////                            }else{
////                                System.out.println("Added new set");
////                                brands.put(e2.getBrand(), 1);
////                            }
////                        }
//                    }
//                    System.out.println("Category: " + e + " brands: " + brands.toString());
//                }
//            });
//            
//            for (String i : brands.keySet()) {
//                CategoryThumb thumb = new CategoryThumb(false);
//                thumb.setThumbTitle(i);
//                thumb.setThumbItems(brands.get(i));
//                thumb.setCategoryType(e);
//                categoryPanel.addThumb(thumb);
//            }
//            System.out.println("Category: " + e + " brands: " + brands.toString());
//
//            jPanel19.add(categoryPanel);
//        });
        showCategory();
        
        jPanel30.removeAll();
        ProductService productsService = new ProductService();
        List<Product> products = productsService.getAllProductDetails();
        
        updateGraphics();
        
        totalProductCount = 0;
        favoriteCount = 0;
        products.forEach(e -> {
            if(e.getStatus().equals("active")){
                ProductThumb testproductThumb = new ProductThumb();
                testproductThumb.setProductDetails(e);

//                ProductThumb2 productThumb2 = new ProductThumb2();
//                productThumb2.setProductDetails(e);
    //            productThumb.setProductName(e.getName());
    //            productThumb.setProductStocks(e.getStocks());
                jPanel30.add(testproductThumb);
//                jPanel165.add(productThumb2);

                totalProductCount++;
                if (e.getFavorite() == 1) {
                    favoriteCount++;
                }
            }
            
        });
        //All product count
        jLabel196.setText(totalProductCount + " item");
        jLabel212.setText(totalProductCount + " item");
        //favorite count
        jLabel199.setText(String.valueOf(favoriteCount) + " item");
        jLabel215.setText(String.valueOf(favoriteCount) + " item");
        
        jPanel113.removeAll();
        ServiceTicketsService serviceTicket = new ServiceTicketsService();
        serviceTicket.getAllServiceTicketsDetails().forEach(e -> {
            if(e.getStatus().equals("On Progress")){
                ServiceThumb2 sThumb = new ServiceThumb2();
                sThumb.setServiceTicket(e);
                jPanel113.add(sThumb);
            }
        });
        
        
//        updateGraphics();
        

    }
    private void ticketMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ticketMainMenuMouseClicked
        updateTicketPanel();
    }//GEN-LAST:event_ticketMainMenuMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        TransactionHistoryService th = new TransactionHistoryService();
        selectedOrderHistory.forEach((k, e) -> {
            th.UpdateTransactionHistory(k, e.getProductId(), (java.sql.Date) e.getTransactionDate(), e.getOrders(), e.getTotalPrice(), e.getReceiptId(), "Sold", e.getVariantId());
            System.out.println(k + "is Sold");
        });
        jPanel178.removeAll();
        
        java.sql.Date sqldate = new java.sql.Date(jCalendar1.getDate().getTime());
        
        System.out.println(sqldate);
        
        TransactionHistoryService ths = new TransactionHistoryService();
        
        ths.getAllTransactionHistoryDetails().forEach(e -> {
            System.out.println("th: " + e.getTransactionDate());
            
            if (sqldate.toString().equals(e.getTransactionDate().toString())) {
                OrderHistoryThumb oht = new OrderHistoryThumb();
                oht.setTransactionHistory(e);
                jPanel178.add(oht);
            }
            revalidate();
            repaint();
        });
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
        
        checkoutProduct.forEach((k, et) -> {
            
            et.forEach(e -> {
            
                ProductService productService = new ProductService();
                Product checkoutSelectedProduct = new Product();

                if (e.getId() == 0) {
                    CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
                    checkoutSelectedProduct = productService.getProductById(k);
                    checkout.setItems(e.getStocks());
                    checkout.setVariants(null);
                    checkout.setPrice(checkoutSelectedProduct.getPrice() * e.getStocks());
                    checkout.setProductId(k);

                    checkout.setProductName(checkoutSelectedProduct.getName());
                    checkout.setImage(checkoutSelectedProduct.getImage());
                    checkoutThumbScrollPane.add(checkout);
                } else {
                    CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
                    checkout.setItems(e.getStocks());
                    checkout.setVariants(e.getName());
                    checkout.setPrice(e.getPrice() * e.getStocks());
                    checkout.setVariantId(e.getId());
                    checkout.setProductId(k);

                    checkoutSelectedProduct = productService.getProductById(k);
                    checkout.setProductName(checkoutSelectedProduct.getName());
                    checkout.setImage(checkoutSelectedProduct.getImage());
                    checkoutThumbScrollPane.add(checkout);
                }
            
            });
            
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
        Collection<Receipt> sales = new ArrayList<>();
        checkoutTotalPrice = 0;
        VariantService vs = new VariantService();
        TransactionHistoryService ths = new TransactionHistoryService();
        ProductService ps = new ProductService();
        
        checkoutProduct.forEach((k, et) -> {
            et.forEach(e ->{
                if (e.getId() == 0) {
                    Product checkoutSelectedProduct = new Product();
                    checkoutSelectedProduct = ps.getProductById(k);
                    checkoutTotalPrice += checkoutSelectedProduct.getPrice() * e.getStocks();

                    sales.add(new Receipt(checkoutSelectedProduct.getName(), e.getStocks(), checkoutSelectedProduct.getPrice() * e.getStocks()));
                } else {

                    vs.getProductVariants(k).forEach(e2 -> {
                        if (e2.getMainVariant() == 0) {
                            if (e2.getName().equals(e.getName())) {
                                //                        System.out.println(e.getName() + " Checkout Success!");
                                //                        TransactionHistory th = new TransactionHistory();
                                //                        th.setOrders(e.getStocks());
                                //                        th.setProductId(e.getId());
                                //                        th.setTotalPrice(e.getPrice() * e.getStocks());
                                //                        java.sql.Date sqldate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                                //                        
                                //                        th.setTransactionDate(sqldate);
                                //                        ths.addTransactionHistory(th);

                                checkoutTotalPrice += e.getPrice() * e.getStocks();

                                Product product = ps.getProductById(k);
                                sales.add(new Receipt(product.getName() + "(" + e.getName() + ")", e.getStocks(), e.getPrice() * e.getStocks()));
                            }
                        }
                    });
                }
            });
        });
        jLabel19.setText("Total Amount: ₱" + String.valueOf(checkoutTotalPrice));
        
        
//        if(jComboBox10.getSelectedIndex() == 1){
//            jComboBox11.removeAllItems();
//            CustomerService cs = new CustomerService();
//            cs.getAllCustomer().forEach(e -> {
//                jComboBox11.addItem(e.getContactNum());
//            });
//        }
        
        
        updateGraphics();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
//        this.requestFocus();
        TransactionHistoryService th = new TransactionHistoryService();
        selectedOrderHistory.forEach((k, e) -> {
            th.UpdateTransactionHistory(k, e.getProductId(), (java.sql.Date) e.getTransactionDate(), e.getOrders(), e.getTotalPrice(), e.getReceiptId(), "Refund", e.getVariantId());
            System.out.println(k + "is Refunded");
        });
        jPanel178.removeAll();
        
        java.sql.Date sqldate = new java.sql.Date(jCalendar1.getDate().getTime());
        
        System.out.println(sqldate);
        
        TransactionHistoryService ths = new TransactionHistoryService();
        
        ths.getAllTransactionHistoryDetails().forEach(e -> {
            System.out.println("th: " + e.getTransactionDate());
            
            if (sqldate.toString().equals(e.getTransactionDate().toString())) {
                OrderHistoryThumb oht = new OrderHistoryThumb();
                oht.setTransactionHistory(e);
                jPanel178.add(oht);
            }
            revalidate();
            repaint();
        });

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
        menuHeader.setText("Inventory Management");
        resetMainMenu();
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/product-icon.svg", 35, 35);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel43.setIcon(icon);
        jLabel42.setForeground(Color.white);
        
        contentPanel.removeAll();
        contentPanel.add(productMainPanel);

        //Category test UI Database
//        CategoryService categoryService = new CategoryService();
//        
//        List<String> type = new ArrayList();
//        
//        categoryService.getAllCategory().forEach(e -> {
//            type.add(e.getType());
//        });
//        
//        List<String> sortedType = removeDuplicates(type);
//        
//        jPanel164.removeAll();
//        
//        sortedType.forEach(e -> {
//            CategorySectionPanel categoryPanel = new CategorySectionPanel();
//            categoryPanel.setCategoryType(e);
//            
//            HashMap<String, Integer> brands = new HashMap();
//
//            categoryService.getAllCategory().forEach(e2->{
//                if(e2.getType().equals(e)){
//
//                    if(brands.isEmpty()){
//                        brands.put(e2.getBrand(), 1);
//                    }else{
//                        for (String i : brands.keySet()) {
//                            if(i.equals(e2.getBrand())){
//                                brands.put(i, brands.get(i) + 1);
//                            }else{
//                                System.out.println("Added new set");
//                                brands.put(e2.getBrand(), 1);
//                            }
//                        }
//                    }
//                }
//            });
//            
//            for (String i : brands.keySet()) {
//                CategoryThumb thumb = new CategoryThumb(true);
//                thumb.setThumbTitle(i);
//                thumb.setThumbItems(brands.get(i));
//                thumb.setCategoryType(e);
//                categoryPanel.addThumb(thumb);
//            }
//
//            jPanel164.add(categoryPanel);
//        });
//        
//        updateGraphics();
        showCategory();
        
        jPanel165.removeAll();
        ProductService productsService = new ProductService();
        List<Product> products = productsService.getAllProductDetails();
        
        totalProductCount = 0;
        favoriteCount = 0;
        products.forEach(e -> {
            if(e.getStatus().equals("active")){
//                ProductThumb testproductThumb = new ProductThumb();
//                testproductThumb.setProductDetails(e);

                ProductThumb2 productThumb2 = new ProductThumb2();
                productThumb2.setProductDetails(e);
    //            productThumb.setProductName(e.getName());
    //            productThumb.setProductStocks(e.getStocks());
//                jPanel30.add(testproductThumb);
                jPanel165.add(productThumb2);

                totalProductCount++;
                if (e.getFavorite() == 1) {
                    favoriteCount++;
                }
            }
            
        });
        //All product count
        jLabel196.setText(totalProductCount + " item");
        jLabel212.setText(totalProductCount + " item");
        //favorite count
        jLabel199.setText(String.valueOf(favoriteCount) + " item");
        jLabel215.setText(String.valueOf(favoriteCount) + " item");
        
        
        updateGraphics();
        
        
    }//GEN-LAST:event_productMainMenuMouseClicked

    private void dashboardMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMainMenuMouseClicked
        // TODO add your handling code here:
        menuHeader.setText("Dashboard");
        contentPanel.removeAll();
        contentPanel.add(dashboardPanel);
//        ArrayList<String> items = new ArrayList<String>();
//        Locale[] locales = Locale.getAvailableLocales();
//        for (int i = 0; i < locales.length; i++) {
//            String item = locales[i].getDisplayName();
//            items.add(item);
//        }
//        //testSearchField
//        setupAutoComplete(ticketSearchBar, items);
        resetMainMenu();
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 32, 32);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel27.setIcon(charticon);
//        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/chart-icon-white.png")));
        jLabel1.setForeground(Color.white);
        
        jPanel66.removeAll();
        ReceiptService rs = new ReceiptService();
        
        
        rs.getAllReceipt().forEach(e -> {
            Receipt receiptData = rs.getReceiptById(e.getId());
            ReceiptDataThumb rdt = new ReceiptDataThumb();

            rdt.setReceiptData(receiptData, false);
            jPanel66.add(rdt);
        });
        
        StoreInfoService storeInfoService = new StoreInfoService();
        storeInfo = storeInfoService.getStoreInfoDetails();
        
        storeNameInput.setText(storeInfo.getName());
        storeBranchInput.setText(storeInfo.getBranch());
        storeAddressInput.setText(storeInfo.getAddress());
        storeBranchInput.setText(storeInfo.getBranch());
        storeContactNumInput.setText(storeInfo.getContactNum());
        storeEmailInput.setText(storeInfo.getEmail());
        if(storeInfo.getSalesPersonDetail() == 1){
            jRadioButton5.setSelected(true);
        }else{
            jRadioButton6.setSelected(true);
        }
        
        updateGraphics();
    }//GEN-LAST:event_dashboardMainMenuMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        if (!editProductNameTextField.getText().replaceAll("\\s+", "").equals("")) {
            
            ProductService service = new ProductService();
            
            Product product = new Product();
            Variants variant = new Variants();
            
            File file = selectedFile;
            if (file != null) {
                String filename = file.getAbsolutePath();
                
                InputStream inStream = null;
                OutputStream outStream = null;
                try {
                    File source = new File(filename);
                    File dest = new File(System.getProperty("user.dir") + "/img/product/", file.getName());
//                    File dest = new File(System.getProperty("user.dir") + "/src/img/product/", file.getName());
                    inStream = new FileInputStream(source);
                    outStream = new FileOutputStream(dest);
                    
                    byte[] buffer = new byte[1024];
                    
                    int length;
                    while ((length = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }
                    
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (outStream != null) {
                        outStream.close();
                    }
                    System.out.println("File Copied..");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
            product.setId(selectedProduct.getId());
            product.setBarcode(jTextField17.getText());
            product.setPrice(editProductPriceField.getText().equals("") ? 0 : Float.parseFloat(editProductPriceField.getText()));
            product.setColor("red");
            product.setStocks((int) editProductStockField.getValue());
            product.setSize("small");
            product.setName(editProductNameTextField.getText());
            product.setFavorite(selectedProduct.getFavorite());
            product.setStatus(selectedProduct.getStatus());
            
            
            if (file != null) {
                
                //DELETE IMAGE  
//                File dest = new File(System.getProperty("user.dir") + "/src" + selectedProduct.getImage());
//                if (dest.delete()) {                    
//                    System.out.println("Deleted the file: " + dest.getName());
//                } else {
//                    System.out.println("Failed to delete the file.");
//                }              


                product.setImage("/img/product/" + file.getName());
            } else {
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
            
            addProductVariants.forEach((k, e) -> {
                e.forEach(e2 -> {
                    if (!k.equals("generated")) {
                        variant.setProduct_id(selectedProduct.getId());
                        variant.setType(k);
                        variant.setName(e2);
                        variant.setMainVariant(1);
                        variant.setPrice(0);
                        variant.setStocks(0);
                        variant.setBarcode("0");
                        
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
            productVariants.forEach(e -> {
                variant.setProduct_id(selectedProduct.getId());
                variant.setType("generated");
                variant.setName(e.getName());
                variant.setMainVariant(0);
                variant.setPrice(e.getPrice());
                variant.setStocks(e.getStocks());
                variant.setBarcode(e.getBarcode());
                variant.setStatus(e.getStatus());
                
                vService.addVariant(variant);
                System.out.println(e.getName());
            });
            editProductNameTextField.putClientProperty("JComponent.outline", "");
            
            closePopup();
            
//            jPanel30.removeAll();
//
//            //reset CategoryUI
////            jPanel19.removeAll();
////            CategoryService categoryService2 = new CategoryService();
////            List<String> type = new ArrayList();
////
////            categoryService2.getAllCategory().forEach(e -> {
////                type.add(e.getType());
////            });
////
////            List<String> sortedType = removeDuplicates(type);
////
////            sortedType.forEach(e -> {
////                CategorySectionPanel categoryPanel = new CategorySectionPanel();
////                categoryPanel.setCategoryType(e);
////
////                HashMap<String, Integer> brands = new HashMap();
////
////                categoryService2.getAllCategory().forEach(e2->{
////                    if(e2.getType().equals(e)){
////
////                        if(brands.isEmpty()){
////                            brands.put(e2.getBrand(), 1);
////                        }else{
////                            for (String i : brands.keySet()) {
////                                if(i.equals(e2.getBrand())){
////                                    brands.put(i, brands.get(i) + 1);
////                                }else{
////                                    System.out.println("Added new set");
////                                    brands.put(e2.getBrand(), 1);
////                                }
////                            }
////                        }
////                    }
////                });
////
////                for (String i : brands.keySet()) {
////                    CategoryThumb thumb = new CategoryThumb(false);
////                    thumb.setThumbTitle(i);
////                    thumb.setThumbItems(brands.get(i));
////                    thumb.setCategoryType(e);
////                    categoryPanel.addThumb(thumb);
////                }
////
////                jPanel19.add(categoryPanel);
////            });
//            showCategory();
//
//            // Current Selected Category
//            if (!selectedCategoryType.equals("") || !selectedCategoryBrand.equals("")) {
//                CategoryService categoryService = new CategoryService();
//                List<Category> categories = categoryService.getAllCategory();
//                
//                ProductService productsService = new ProductService();
//                List<Product> products = productsService.getAllProductDetails();
//                
//                categories.forEach(element -> {
//                    if (element.getBrand().equals(selectedCategoryBrand) && element.getType().equals(selectedCategoryType)) {
//                        products.forEach(element2 -> {
//                            if (element2.getId() == element.getId() && element2.getStatus().equals("active")) {
//                                ProductThumb productThumb = new ProductThumb();
//                                productThumb.setProductDetails(element2);
//                                jPanel30.add(productThumb);
//                                System.out.println(element2.getName() + " Added");
//                            }
//                        });
//                    }
//                });
//            } else {
//                ProductService productsService = new ProductService();
//                List<Product> products = productsService.getAllProductDetails();
//                
//                products.forEach(e -> {
//                    if(e.getStatus().equals("active")){
//                        ProductThumb productThumb = new ProductThumb();
//                        productThumb.setProductDetails(e);
//                        jPanel30.add(productThumb);
//                    }
//                });
//            }
            
        } else {
            editProductNameTextField.putClientProperty("JComponent.outline", "error");
        }

    }//GEN-LAST:event_jButton1ActionPerformed
    public void updateProductListPanel(){
        showCategory();

            // Current Selected Category
        if(selectedMenu == ticketMainMenu){
            jPanel30.removeAll();
            if (!selectedCategoryType.equals("") || !selectedCategoryBrand.equals("")) {
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllCategory();

                ProductService productsService = new ProductService();
                List<Product> products = productsService.getAllProductDetails();

                categories.forEach(element -> {
                    if (element.getBrand().equals(selectedCategoryBrand) && element.getType().equals(selectedCategoryType)) {
                        Product element2 = productsService.getProductById(element.getProduct_id());
//                                .forEach(element2 -> {
                            if (element2.getStatus().equals("active")) {
                                ProductThumb productThumb = new ProductThumb();
                                productThumb.setProductDetails(element2);
                                jPanel30.add(productThumb);
                                System.out.println(element2.getName() + " Added");
                            }
//                        });
                    }
                });
            } else {
                ProductService productsService = new ProductService();
                List<Product> products = productsService.getAllProductDetails();

                products.forEach(e -> {
                    if(e.getStatus().equals("active")){
                        ProductThumb productThumb = new ProductThumb();
                        productThumb.setProductDetails(e);
                        jPanel30.add(productThumb);
                    }
                });
            }
        }else if(selectedMenu == productMainMenu){
            jPanel165.removeAll();
            if (!selectedCategoryType.equals("") || !selectedCategoryBrand.equals("")) {
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllCategory();

                ProductService productsService = new ProductService();
                List<Product> products = productsService.getAllProductDetails();

                categories.forEach(element -> {
                    if (element.getBrand().equals(selectedCategoryBrand) && element.getType().equals(selectedCategoryType)) {
                        Product element2 = productsService.getProductById(element.getProduct_id());
//                                .forEach(element2 -> {
                            if (element2.getStatus().equals("active")) {
                                ProductThumb productThumb = new ProductThumb();
                                productThumb.setProductDetails(element2);
                                jPanel165.add(productThumb);
                                System.out.println(element2.getName() + " Added");
                            }
//                        });
                    }
                });
            } else {
                ProductService productsService = new ProductService();
                List<Product> products = productsService.getAllProductDetails();

                products.forEach(e -> {
                    if(e.getStatus().equals("active")){
                        ProductThumb productThumb = new ProductThumb();
                        productThumb.setProductDetails(e);
                        jPanel165.add(productThumb);
                    }
                });
            }
        }
    }
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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        Collection<Receipt> sales = new ArrayList<>();
        checkoutTotalPrice = 0;
        
        VariantService vs = new VariantService();
        TransactionHistoryService ths = new TransactionHistoryService();
        ProductService ps = new ProductService();
        
        checkoutProduct.forEach((k, et) -> {
            et.forEach(e -> {
                if (e.getId() == 0) {
                    Product product = ps.getProductById(k);
                    checkoutTotalPrice += product.getPrice() * e.getStocks();
                } else {
                    vs.getProductVariants(k).forEach(e2 -> {
                        if (e2.getMainVariant() == 0) {
                            if (e2.getName().equals(e.getName())) {
                                //                        System.out.println(e.getName() + " Checkout Success!");
                                //                        TransactionHistory th = new TransactionHistory();
                                //                        th.setOrders(e.getStocks());
                                //                        th.setProductId(e.getId());
                                //                        th.setTotalPrice(e.getPrice() * e.getStocks());
                                ////                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                ////                        Date date = new Date();
                                ////                        th.setTransactionDate(dateFormat.format(date));
                                //                        java.sql.Date sqldate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                                //                        th.setTransactionDate(sqldate);
                                //
                                //                        ths.addTransactionHistory(th);

                                checkoutTotalPrice += e.getPrice() * e.getStocks();

                                Product product = ps.getProductById(k);
    //                            sales.add(new Receipt(product.getName()+"("+e.getName()+")", e.getStocks(), e.getPrice() * e.getStocks()));
                            }
                        }
                    });
                }
            });
        });
        confirmTotalPrice.setText(String.valueOf(checkoutTotalPrice));

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
        jTextField24.setText("0");
        updateGraphics();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        List<String> tempList = new ArrayList<>();
        VariantService vService = new VariantService();
        Variants tempVariants = new Variants();
        
        List<Variants> variants = vService.getProductVariants(selectedProduct.getId());
       
        
//        if (variants.size() == 0 || variants == null) {
//            ProductService ps = new ProductService();
//            
//            if (ps.getProductById(selectedProduct.getId()).getStocks() >= (int) jSpinner4.getValue() && ps.getProductById(selectedProduct.getId()).getStocks() != 0) {
//                tempVariants.setStocks((int) jSpinner4.getValue());
//                checkoutProduct.put(selectedProduct.getId(), tempVariants);
//                
//                closePopup();
//            } else {
//                jSpinner4.putClientProperty("JComponent.outline", "warning");
//                JOptionPane.showMessageDialog(this, selectedProduct.getName() + " not enough Stock! \nStock Available: " + selectedProduct.getStocks(), " Insuficient Stock",
//                        JOptionPane.WARNING_MESSAGE);
//            }
//        } else {
//            
//            viewSelectedVariant.forEach((k, e2) -> {
//                tempList.add(e2);
//            });
//            
//            String str = String.join("/", tempList);
//            
//            
//            variants.forEach(e -> {
//                if (e.getMainVariant() == 0) {
//                    if (e.getName().equals(str) && e.getStatus() == 1) {
//                        tempVariants.setPrice(e.getPrice());
//                        tempVariants.setId(e.getId());
//                    }
//                }
//            });
//            
//            if(tempVariants.getId() == 0){
//                JOptionPane.showMessageDialog(this, selectedProduct.getName() + "("+ str +")" + " not Available!", " Variant not Available",
//                        JOptionPane.ERROR_MESSAGE);
//            }else{
//                Variants variant = vService.getVariantsById(tempVariants.getId());
//    //            tempVariants.setId(selectedProduct.getId());
//                tempVariants.setName(str);
//                tempVariants.setStocks((int) jSpinner4.getValue());
//
//                if (variant.getStocks() >= (int) jSpinner4.getValue() && variant.getStocks() != 0) {
//                    checkoutProduct.put(selectedProduct.getId(), tempVariants);
//                    closePopup();
//                } else {
//                    jSpinner4.putClientProperty("JComponent.outline", "warning");
//                    JOptionPane.showMessageDialog(this, selectedProduct.getName() + "(" + tempVariants.getId() + ") not enough Stock! \nStock Available: " + variant.getStocks(), " Insuficient Stock",
//                            JOptionPane.WARNING_MESSAGE);
//                }
//            }
//            
//        }
//        System.out.println(checkoutProduct);
        
        
        if (variants.size() == 0 || variants == null) {
            ProductService ps = new ProductService();
            List<Variants> listvar = new ArrayList<>();
            
            if (ps.getProductById(selectedProduct.getId()).getStocks() >= (int) jSpinner4.getValue() && ps.getProductById(selectedProduct.getId()).getStocks() != 0) {
                tempVariants.setStocks((int) jSpinner4.getValue());
                
//                for(int i = 0; i < checkoutProduct.get(selectedProduct.getId() ).size(); i++){
//                    if(checkoutProduct.get(selectedProduct.getId() ).get(i).getId() == ){
//                    
//                    }
//                }
                if(checkoutProduct.containsKey(selectedProduct.getId())){
//                    listvar.add(tempVariants);
                    int addedStocks = checkoutProduct.get(selectedProduct.getId()).get(0).getStocks() + (int) jSpinner4.getValue();
                    checkoutProduct.get(selectedProduct.getId()).get(0).setStocks(addedStocks);
                }else{
                    listvar.add(tempVariants);
                    checkoutProduct.put(selectedProduct.getId(), listvar);
                }
                closePopup();
            } else {
                jSpinner4.putClientProperty("JComponent.outline", "warning");
                JOptionPane.showMessageDialog(this, selectedProduct.getName() + " not enough Stock! \nStock Available: " + selectedProduct.getStocks(), " Insuficient Stock",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            List<Variants> listvar = new ArrayList<>();
            viewSelectedVariant.forEach((k, e2) -> {
                tempList.add(e2);
            });
            
            String str = String.join("/", tempList);
            
            
            variants.forEach(e -> {
                if (e.getMainVariant() == 0) {
                    if (e.getName().equals(str) && e.getStatus() == 1) {
                        tempVariants.setPrice(e.getPrice());
                        tempVariants.setId(e.getId());
                    }
                }
            });
            
            if(tempVariants.getId() == 0){
                JOptionPane.showMessageDialog(this, selectedProduct.getName() + "("+ str +")" + " not Available!", " Variant not Available",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                Variants variant = vService.getVariantsById(tempVariants.getId());
    //            tempVariants.setId(selectedProduct.getId());
                tempVariants.setName(str);
                tempVariants.setStocks((int) jSpinner4.getValue());

                if (variant.getStocks() >= (int) jSpinner4.getValue() && variant.getStocks() != 0) {
                    if(checkoutProduct.containsKey(selectedProduct.getId())){
//                        listvar = checkoutProduct.get(selectedProduct.getId());
//                        listvar.add(tempVariants);
//                        checkoutProduct.put(selectedProduct.getId(), listvar);
                        boolean variantExist = false;
                        for(int i = 0; i < checkoutProduct.get(selectedProduct.getId()).size(); i++){
                            if(checkoutProduct.get(selectedProduct.getId()).get(i).getId() == variant.getId()){
                                int addedStocks = checkoutProduct.get(selectedProduct.getId()).get(i).getStocks() +
                                        (int) jSpinner4.getValue();
                                checkoutProduct.get(selectedProduct.getId()).get(i).setStocks(addedStocks);
                                variantExist = true;
                            }
                        }
                        if(!variantExist){
                            checkoutProduct.get(selectedProduct.getId()).add(tempVariants);
                        }
                    }else{
                        listvar.add(tempVariants);
                        checkoutProduct.put(selectedProduct.getId(), listvar);
                    }
                    
                    closePopup();
                } else {
                    jSpinner4.putClientProperty("JComponent.outline", "warning");
                    JOptionPane.showMessageDialog(this, selectedProduct.getName() + "(" + tempVariants.getId() + ") not enough Stock! \nStock Available: " + variant.getStocks(), " Insuficient Stock",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            
        }
        System.out.println(checkoutProduct);
//        closePopup();
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
//        System.out.println(ticketMainTabPane.getSelectedIndex());
//        switch (ticketMainTabPane.getSelectedIndex()) {
//            case 0:
////                jPanel19.removeAll();
////                CategoryService categoryService = new CategoryService();
////        
////                List<String> type = new ArrayList();
////
////                categoryService.getAllCategory().forEach(e -> {
////                    type.add(e.getType());
////                });
////
////                List<String> sortedType = removeDuplicates(type);
////
////                sortedType.forEach(e -> {
////                    CategorySectionPanel categoryPanel = new CategorySectionPanel();
////                    categoryPanel.setCategoryType(e);
////
////                    HashMap<String, Integer> brands = new HashMap();
////
////                    categoryService.getAllCategory().forEach(e2->{
////                        if(e2.getType().equals(e)){
////
////                            if(brands.isEmpty()){
////                                brands.put(e2.getBrand(), 1);
////                            }else{
////                                for (String i : brands.keySet()) {
////                                    if(i.equals(e2.getBrand())){
////                                        brands.put(i, brands.get(i) + 1);
////                                    }else{
////                                        System.out.println("Added new set");
////                                        brands.put(e2.getBrand(), 1);
////                                    }
////                                }
////                            }
////                        }
////                    });
////
////                    for (String i : brands.keySet()) {
////                        CategoryThumb thumb = new CategoryThumb();
////                        thumb.setThumbTitle(i);
////                        thumb.setThumbItems(brands.get(i));
////                        thumb.setCategoryType(e);
////                        categoryPanel.addThumb(thumb);
////                    }
////
////                    jPanel19.add(categoryPanel);
////                });
//                break;
//            case 1:                
////                jPanel113.removeAll();
////                ServiceTicketsService serviceTicket = new ServiceTicketsService();
////                serviceTicket.getAllServiceTicketsDetails().forEach(e -> {
////                    if(e.getStatus().equals("On Progress")){
////                        ServiceThumb sThumb = new ServiceThumb();
////                        sThumb.setServiceTicket(e);
////                        jPanel113.add(sThumb);
////                    }
////                });
//                break;
//            default:            
//            
//        }
        if(ticketMainTabPane.getSelectedIndex() == 0){
            ticketSearchBar.setVisible(true);
            jButton10.setVisible(true);
            jButton11.setVisible(true);
        }else{
            ticketSearchBar.setVisible(false);
            jButton10.setVisible(false);
            jButton11.setVisible(false);
        }
    }//GEN-LAST:event_ticketMainTabPaneStateChanged

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        ServiceTicketsService sts = new ServiceTicketsService();
        ServiceTickets st = new ServiceTickets();
        
//        st.setCustomerName(jTextField1.getText());
        if(serviceCustomer != null){
            st.setCustomerId(serviceCustomer.getId());
        }else{
            CustomerService cs = new CustomerService();
            Customer customer = new Customer();
            customer.setGender("");
            int customerId = cs.addCustomer(customer);
            st.setCustomerId(customerId);
        }
        st.setDefects(jTextArea1.getText());
        st.setPrice(Float.parseFloat(jTextField3.getText()));
        st.setStatus(jComboBox3.getSelectedItem().toString());

//        java.sql.Date sqldate = new java.sql.Date(jDateChooser1.getDate().getTime());
        st.setWalkInDate(new java.sql.Date(jDateChooser1.getDate().getTime()));
        
        st.setEstimateFinish(new java.sql.Date(jDateChooser2.getDate().getTime()));
        
        int ticketId = sts.addServiceTickets(st);
        
        ServiceThumb2 sThumb = new ServiceThumb2();
        sThumb.setServiceTicket(sts.getServiceTicketsDetailsById(ticketId));
        jPanel113.add(sThumb);
        
        jTextField1.setText("");
        jTextArea1.setText("");
        jTextField3.setText("");
//        jTextField6.setText("");
//        jDateChooser2.setText("");
        jComboBox3.setSelectedIndex(0);
        
        updateGraphics();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jPanel113ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel113ComponentRemoved
        // TODO add your handling code here:
        updateGraphics();
    }//GEN-LAST:event_jPanel113ComponentRemoved

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMenuItem1.isSelected()) {
            
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
        if (editProfileEnable) {
            System.out.println("Click");
            JFileChooser chooser = new JFileChooser();
            
            selectedFile = null;
            int option = chooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filename = file.getAbsolutePath();
                BufferedImage bufferedImage = null;
                try {
//                    String cwd = System.getProperty("user.dir");
//                    bufferedImage = ImageIO.read(new File(cwd + filename));
                    bufferedImage = ImageIO.read(new File(filename));
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                Image scaledImage = bufferedImage.getScaledInstance(-1, jLabel156.getHeight(), Image.SCALE_SMOOTH);
                
                ImageIcon image = new ImageIcon(scaledImage);
                
                jLabel156.setIcon(image);
                System.out.println(filename);
                updateGraphics();
                
                System.out.println(getClass().getResource("/img/profile/" + filename));
                
                selectedFile = file;
                
            } else {
                System.out.println("Canceled");
            }
        }
        

    }//GEN-LAST:event_jLabel156MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void storeAddressInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeAddressInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_storeAddressInputActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        changeUserPassDialog.setVisible(true);
        changeUserPassDialog.show();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        
        try {
            HttpController.postHttpRequest("https://timesquarerest-production.up.railway.app/api/email");
            ReportService rs = new ReportService();
        
            rs.addReport(new Reports(0, "email", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString()));
        
            jPanel142.removeAll();
            jPanel142.add(new ReportLineChart());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        // TODO add your handling code here:
//        notificationDialog.setVisible(true);
//        notificationDialog.show();

//        notificationPanel
        NotificationService ns = new NotificationService();
        
        jPanel162.removeAll();
        jPanel158.removeAll();
        ns.getLimitedNotification(10).forEach(e -> {
            NotificationThumb2 nthumb = new NotificationThumb2();
            
//            nthumb.setMaximumSize(new Dimension(238, 51));
//            nthumb.setPreferredSize(new Dimension(238, 51));
            nthumb.setNotification(e, this);
            nthumb.setLimitedSize();
//            NotificationThumb2 nthumb2 = new NotificationThumb2();
//            nthumb2.setNotification(e);
            jPanel162.add(nthumb);
            ns.UpdateNotification(
                    e.getId(), 
                    e.getProductId(), 
                    e.getDate(), 
                    e.getTitle(), 
                    1);
//            jPanel99.add(nthumb2);
        });
        
        

        notificationPanel.setVisible(true);
        notificationPanel.setBounds(jLabel56.getX() - 210, jLabel56.getY(), 300, 400);

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
        checkoutTotalPrice = 0;
        
        VariantService vs = new VariantService();
        TransactionHistoryService ths = new TransactionHistoryService();
        ProductService ps = new ProductService();
        
        CustomerService cs = new CustomerService();
        
        ReceiptService rs = new ReceiptService();
        Receipt receipt = new Receipt();
        
        int customerId = 0;
        
        String timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString();
        
        if(jComboBox10.getSelectedIndex() == 0){
            checkoutCustomer = new Customer();
            
            checkoutCustomer.setName("");
//            checkoutCustomer.setAddress("");
//            checkoutCustomer.setContactNum("");
//            checkoutCustomer.setGender("");
//            checkoutCustomer.setBirthDate();
            checkoutCustomer.setStatus("Active");
            checkoutCustomer.setMembershipDate(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString());
            
            
            customerId = cs.addCustomer(checkoutCustomer);
        }else if(jComboBox10.getSelectedIndex() == 1){
            checkoutCustomer = new Customer();
            
            checkoutCustomer.setName(checkoutCustomerNameInput.getText());
            checkoutCustomer.setAddress(checkoutCustomerAddressInput.getText());
            checkoutCustomer.setContactNum(checkoutCustomerContactInput.getText());
            checkoutCustomer.setGender(checkoutGenderButtonGroup.getSelection().getActionCommand());
            checkoutCustomer.setBirthDate(new java.sql.Timestamp(checkoutCustomerBirthDateInput.getDate().getTime()).toString());
            checkoutCustomer.setStatus("Active");
            checkoutCustomer.setMembershipDate(timestamp);
            
            
            customerId = cs.addCustomer(checkoutCustomer);
            
        }else if(jComboBox10.getSelectedIndex() == 2){
            customerId = checkoutCustomer.getId();
            
        }
        
        checkoutCustomerNameInput.setText("");
        checkoutCustomerAddressInput.setText("");
        checkoutCustomerContactInput.setText("");
        jRadioButton3.setSelected(true);
        checkoutCustomerBirthDateInput.setCalendar(null);
        
        if(isCashPayment){
            receipt.setType("cash");
        }else{
            receipt.setType("credit");
        }
        
        receipt.setCustomerId(customerId);
        receipt.setDate(timestamp);
        
        int receiptId = rs.addReceipt(receipt);
        
        
        
        checkoutProduct.forEach((k, et) -> {
            et.forEach(e -> {
                if (e.getId() == 0) {
                    Product product = ps.getProductById(k);
                    TransactionHistory th = new TransactionHistory();
                    th.setOrders(e.getStocks());
                    th.setProductId(k);
                    th.setVariantId(e.getName());
                    th.setTotalPrice(product.getPrice() * e.getStocks());

                    java.sql.Date sqldate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                    th.setTransactionDate(sqldate);
                    th.setStatus("Sold");

                    th.setReceiptId(receiptId);

                    ths.addTransactionHistory(th);

                    checkoutTotalPrice += product.getPrice() * e.getStocks();

                    sales.add(new Receipt(product.getName(), e.getStocks(), product.getPrice() * e.getStocks()));

                    product.setStocks(product.getStocks() - e.getStocks());
                    if (product.getStocks() <= 0) {
                        product.setStocks(0);

                        NotificationService ns = new NotificationService();
                        Notification notification = new Notification();

                        notification.setProductId(product.getId());
                        notification.setTitle(product.getName() + " is out of stock!");
                        notification.setDate(timestamp);
                        notification.setStatus(0);
                        ns.addNotification(notification);
                    }
                    ps.updateProduct(k, product);

                } else {
                    vs.getProductVariants(k).forEach(e2 -> {
                        if (e2.getMainVariant() == 0) {
                            if (e2.getName().equals(e.getName())) {
                                System.out.println(e.getName() + " Checkout Success!");
                                TransactionHistory th = new TransactionHistory();
                                th.setOrders(e.getStocks());
                                th.setProductId(k);
                                th.setVariantId(e.getName());
                                th.setTotalPrice(e.getPrice() * e.getStocks());
                                //                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                //                        Date date = new Date();
                                //                        th.setTransactionDate(dateFormat.format(date));
                                java.sql.Date sqldate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                                th.setTransactionDate(sqldate);
                                th.setStatus("Sold");
                                th.setReceiptId(receiptId);

                                ths.addTransactionHistory(th);

                                checkoutTotalPrice += e.getPrice() * e.getStocks();

                                Product product = ps.getProductById(k);
                                sales.add(new Receipt(product.getName() + "(" + e.getName() + ")", e.getStocks(), e.getPrice() * e.getStocks()));

                                Variants tempV = e2;
                                tempV.setStocks(e2.getStocks() - e.getStocks());
                                if (tempV.getStocks() <= 0) {
                                    tempV.setStocks(0);

                                    NotificationService ns = new NotificationService();
                                    Notification notification = new Notification();

                                    notification.setProductId(product.getId());
                                    notification.setTitle(product.getName()+"(" + tempV.getName() + ") is out of stock!");
                                    notification.setStatus(0);
                                    notification.setDate(timestamp);
                                    ns.addNotification(notification);
                                }
                                vs.updateVariant(e2.getId(), tempV);
                            }
                        }
                    });

                }
            });
        });
        
        try {
//            String homeDir = System.getProperty("user.home");
//            String outputFile = homeDir + File.separatorChar + "JasperTest.pdf";
            String cwd = System.getProperty("user.dir");
            String reportPath;
            //"src/pos_timesquare/utils/POS_Receipt_1.jrxml"
            StoreInfoService storeInfoService = new StoreInfoService();
            StoreInfo storeInfo = storeInfoService.getStoreInfoDetails();
            
            Map<String, Object> param = new HashMap<>();
            
            System.out.println("total price: " + checkoutTotalPrice);
            confirmTotalPrice.setText(String.valueOf(checkoutTotalPrice));
            
            double payment = Float.parseFloat(jTextField24.getText());
            
            //"src/img/icon/receiptLogo.jpg"
            
            param.put("logo", new File(cwd + "/img/icon/receiptLogo.jpg").getAbsolutePath());
            param.put("totalPrice", (double) checkoutTotalPrice);
            param.put("payment", (double) payment);
            param.put("change", (double) payment - checkoutTotalPrice);
            param.put("branchMobileNum", storeInfo.getContactNum());
            param.put("branchAddress", storeInfo.getAddress());
            
            if(storeInfo.getSalesPersonDetail() == 1){
                param.put("salesPersonName", this.user.getName());
                param.put("salesPersonContactNum", this.user.getContactNum());
                reportPath = cwd + "/POS_Receipt_Personnel_Include.jrxml";
            }else{
                reportPath = cwd + "/POS_Receipt.jrxml";
            }

//            JasperPrint print = JasperFillManager.fillReport("C:/Users/Acer/Documents/NetBeansProjects/POS_TimeSquare_1/src/test/Black_A4.jrxml", customerName);
            JasperDesign design = JRXmlLoader.load(new File(reportPath).getAbsolutePath());
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(sales);
            
            JasperReport jreport = JasperCompileManager.compileReport(design);

//            JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());
            JasperPrint print = JasperFillManager.fillReport(jreport, param, dataSource);
            
            JasperViewer.viewReport(print, false);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        checkoutThumbScrollPane.removeAll();
        checkoutProduct.clear();
        jLabel194.setText("0.0");
        
        checkoutConfirmation.setVisible(false);
        
        
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
//        jPanel168.remove(userAge);
//        jPanel168.add(userAgeInput);

        editProfileEnable = true;
        
        jPanel169.remove(userGender);
        jPanel169.add(userGenderCB);
        
        jPanel170.remove(userAddress);
        jPanel170.add(userAddressInput);
        
        userNameInput.setVisible(true);
        usersName.setVisible(false);
        
        jButton20.setVisible(false);
        jButton29.setVisible(true);
        
        jPanel172.add(birthdateChooser);
        jPanel172.remove(jLabel133);
        
        jPanel79.add(jTextField2);
        jPanel79.remove(jLabel67);
        
        jLabel156.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        updateGraphics();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        
        File file = selectedFile;
        if (file != null) {
            String filename = file.getAbsolutePath();
            
            InputStream inStream = null;
            OutputStream outStream = null;
            try {
                File source = new File(filename);
                File dest = new File(System.getProperty("user.dir") + "/img/profile/", file.getName());
//                File dest = new File(System.getProperty("user.dir") + "/src/img/profile/", file.getName());
                inStream = new FileInputStream(source);
                outStream = new FileOutputStream(dest);
                
                byte[] buffer = new byte[1024];
                
                int length;
                while ((length = inStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, length);
                }
                
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                System.out.println("File Copied..");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(new File(System.getProperty("user.dir") + "/img/profile/", file.getName()));
//                bufferedImage = ImageIO.read(new File(System.getProperty("user.dir") + "/src/img/profile/", file.getName()));
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image scaledImage = bufferedImage.getScaledInstance(-1, accountProfilePicture.getHeight(), Image.SCALE_SMOOTH);
            
            ImageIcon image = new ImageIcon(scaledImage);
            
            updateGraphics();
            accountProfilePicture.setIcon(image);
        }
        
        editProfileEnable = false;
        jLabel156.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        UserService userS = new UserService();
//        User tempUser = new User();
//        tempUser.setId(this.user.getId());
//        tempUser.setUsername(this.user.getUsername());
//        tempUser.setPassword(this.user.getPassword());
//        tempUser.setRole(this.user.getRole());
//        tempUser.setMembershipDate(this.user.getMembershipDate());
        this.user.setName(userNameInput.getText());
        this.user.setAddress(userAddressInput.getText());
        this.user.setGender(userGenderCB.getSelectedItem().toString().toLowerCase());
        this.user.setContactNum(jTextField2.getText());
        
        java.sql.Date sqldate = new java.sql.Date(birthdateChooser.getDate().getTime());
        
        this.user.setBirthdate(sqldate);
//        this.user.setGender("male");

//        tempUser.setHourWorked(this.user.getHourWorked());
//        
        if (file != null) {
            this.user.setImage("/img/profile/" + file.getName());
        }
//        
//        tempUser.setBirthdate(this.user.getBirthdate());

//        long millis=System.currentTimeMillis();  
//        java.sql.Date date=new java.sql.Date(millis);  
//        
//        this.user.setBirthdate(date);
        userS.updateUser(this.user.getId(), this.user);
        
        jButton20.setVisible(true);
        jButton29.setVisible(false);

//        jPanel168.remove(userAgeInput);
//        jPanel168.add(userAge);
        long millis = System.currentTimeMillis();        
        java.sql.Date currentDate = new java.sql.Date(millis);        
        LocalDate date = Instant.ofEpochMilli(user.getBirthdate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();        
        
        userAge.setText(String.valueOf(Period.between(date, currentDate.toLocalDate()).getYears()));
        
        jPanel169.remove(userGenderCB);
        jPanel169.add(userGender);
        userGender.setText(this.user.getGender().substring(0, 1).toUpperCase() + this.user.getGender().substring(1));
        
        jPanel170.remove(userAddressInput);
        userAddress.setText(userAddressInput.getText());
        jPanel170.add(userAddress);
        
        jPanel172.remove(birthdateChooser);
        jPanel172.add(jLabel133);
        
        jLabel133.setText(DateFormat.getDateInstance().format(birthdateChooser.getDate()));
        
        userNameInput.setVisible(false);
        usersName.setText(this.user.getName());
        usersName.setVisible(true);
        
        
        jPanel79.remove(jTextField2);
        jLabel67.setText(jTextField2.getText());
        jPanel79.add(jLabel67);
        
        

    }//GEN-LAST:event_jButton29ActionPerformed

    private void jPanel173MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel173MouseClicked
        // TODO add your handling code here:
        ProductService productsService = new ProductService();
        List<Product> products = productsService.getAllProductDetails();
        
        jPanel30.removeAll();
        products.forEach(e -> {
            if(e.getStatus().equals("active")){
                ProductThumb testproductThumb = new ProductThumb();
                testproductThumb.setProductDetails(e);

    //            ProductThumb2 productThumb2 = new ProductThumb2();
    //            productThumb2.setProductDetails(e);
                jPanel30.add(testproductThumb);
    //            jPanel165.add(productThumb2);
            }
        });
        updateGraphics();
        
    }//GEN-LAST:event_jPanel173MouseClicked

    private void jCalendar1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar1PropertyChange
        // TODO add your handling code here:
        
        java.sql.Date sqldate = new java.sql.Date(jCalendar1.getDate().getTime());
//        revalidate();
//        repaint();
        if (!prevDate.equals(sqldate.toString())) {
            
            jPanel178.removeAll();
        
            

            System.out.println(sqldate);
            System.out.println("Calendar date: "+ jCalendar1.getDate());
            TransactionHistoryService ths = new TransactionHistoryService();

            ths.getAllTransactionHistoryDetails().forEach(e -> {
                System.out.println("sql: " + sqldate.toString() + "th: " + e.getTransactionDate().toString());

                if (sqldate.toString().equals(e.getTransactionDate().toString())) {
                    OrderHistoryThumb oht = new OrderHistoryThumb();
                    oht.setTransactionHistory(e);
                    jPanel178.add(oht);
                }

            });
            
            prevDate = sqldate.toString();
            updateGraphics();
            System.out.println("Date Update");
        }
        
    }//GEN-LAST:event_jCalendar1PropertyChange

    private void jPanel183MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel183MouseClicked
        // TODO add your handling code here:
        
        selectedCategoryType = "";
        selectedCategoryBrand = "";
        
        ProductService productsService = new ProductService();
        List<Product> products = productsService.getAllProductDetails();
        
        jPanel165.removeAll();
        products.forEach(e -> {
            if(e.getStatus().equals("active")){
                ProductThumb2 productThumb = new ProductThumb2();
                productThumb.setProductDetails(e);

                jPanel165.add(productThumb);
            }
        });
        updateGraphics();
    }//GEN-LAST:event_jPanel183MouseClicked

    private void jPanel184MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel184MouseClicked
        // TODO add your handling code here:
        ProductService productsService = new ProductService();
        List<Product> products = productsService.getAllProductDetails();
        
        jPanel165.removeAll();
        products.forEach(e -> {
            if(e.getStatus().equals("active")){
                if (e.getFavorite() == 1) {
                    ProductThumb2 productThumb = new ProductThumb2();
                    productThumb.setProductDetails(e);

                    jPanel165.add(productThumb);
                }
            }
        });
        updateGraphics();
    }//GEN-LAST:event_jPanel184MouseClicked

    private void jPanel174MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel174MouseClicked
        // TODO add your handling code here:
        ProductService productsService = new ProductService();
        List<Product> products = productsService.getAllProductDetails();
        
        jPanel30.removeAll();
        products.forEach(e -> {
            if(e.getStatus().equals("active")){
                if (e.getFavorite() == 1) {
                    ProductThumb productThumb = new ProductThumb();
                    productThumb.setProductDetails(e);

                    jPanel30.add(productThumb);
                }
            }
        });
        updateGraphics();
    }//GEN-LAST:event_jPanel174MouseClicked

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        showPopup(addProductPanel);
        
        editProductNameTextField1.setText("");
        editProductPriceField1.setText("");
        editProductStockField1.setValue(0);
        jPanel188.removeAll();
        
        CategoryService categoryService = new CategoryService();
        List<String> type = new ArrayList();
        List<String> brand = new ArrayList();
        
        categoryService.getAllCategory().forEach(e -> {
            type.add(e.getType());
            brand.add(e.getBrand());
        });
        
        List<String> sortedType = removeDuplicates(type);
        
        editProductType1.removeAllItems();
        sortedType.forEach(e -> {
            editProductType1.addItem(e);
        });
        
        List<String> sortedBrand = removeDuplicates(brand);
        editProductBrand1.removeAllItems();
        sortedBrand.forEach(e -> {
            editProductBrand1.addItem(e);
        });
        
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        if (!editProductNameTextField1.getText().replaceAll("\\s+", "").equals("")) {
            
            ProductService service = new ProductService();
            
            Product product = new Product();
            Variants variant = new Variants();
            
            File file = selectedFile;
            if (file != null) {
                String filename = file.getAbsolutePath();
                
                InputStream inStream = null;
                OutputStream outStream = null;
                try {
                    File source = new File(filename);
                    File dest = new File(System.getProperty("user.dir") + "/img/product/", file.getName());
                    inStream = new FileInputStream(source);
                    outStream = new FileOutputStream(dest);
                    
                    byte[] buffer = new byte[1024];
                    
                    int length;
                    while ((length = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }
                    
                    if (inStream != null) {
                        inStream.close();
                    }
                    if (outStream != null) {
                        outStream.close();
                    }
                    System.out.println("File Copied..");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

//            product.setId(selectedProduct.getId());
            product.setBarcode(jTextField17.getText());
            product.setPrice(editProductPriceField1.getText().equals("") ? 0 : Float.parseFloat(editProductPriceField1.getText()));
            product.setColor("red");
            product.setStocks((int) editProductStockField1.getValue());
            product.setSize("small");
            product.setName(editProductNameTextField1.getText());
            product.setFavorite(0);
            if (file != null) {
//                File dest =new File(System.getProperty("user.dir") + "/src" + selectedProduct.getImage());
//                if (dest.delete()) { 
//                    System.out.println("Deleted the file: " + dest.getName());
//                } else {
//                    System.out.println("Failed to delete the file.");
//                } 
                product.setImage("/img/product/" + file.getName());
            } else {
//                product.setImage(selectedProduct.getImage());
            }
            product.setStatus("active");
            
            long newId = service.addProduct(product);
            
            CategoryService cs = new CategoryService();
            
            Category category = new Category();
            category.setProduct_id((int) newId);
            category.setBrand(editProductBrand1.getSelectedItem().toString());
            category.setType(editProductType1.getSelectedItem().toString());
            
            cs.addCategory(category);
            
            VariantService vService = new VariantService();
//            vService.deleteVariantByProductId(selectedProduct.getId());

            addProductVariants.forEach((k, e) -> {
                e.forEach(e2 -> {
                    if (!k.equals("generated")) {
                        variant.setProduct_id((int) newId);
                        variant.setType(k);
                        variant.setName(e2);
                        variant.setMainVariant(1);
                        variant.setPrice(0);
                        variant.setStocks(0);
                        variant.setBarcode("0");
                        
                        System.out.println("Key: " + k + " Val: " + e2);
                        
                        vService.addVariant(variant);
                    }
                });
            });
            
            productVariants.forEach(e -> {
                variant.setProduct_id((int) newId);
                variant.setType("generated");
                variant.setName(e.getName());
                variant.setMainVariant(0);
                variant.setPrice(e.getPrice());
                variant.setStocks(e.getStocks());
                variant.setBarcode(e.getBarcode());
                
                vService.addVariant(variant);
                System.out.println(e.getName());
            });
            editProductNameTextField1.putClientProperty("JComponent.outline", "");
            
            closePopup();
            
            jPanel165.removeAll();

            //reset CategoryUI
//            jPanel164.removeAll();
//            CategoryService categoryService2 = new CategoryService();
//            List<String> type = new ArrayList();
//
//            categoryService2.getAllCategory().forEach(e -> {
//                type.add(e.getType());
//            });
//
//            List<String> sortedType = removeDuplicates(type);
//
//            sortedType.forEach(e -> {
//                CategorySectionPanel categoryPanel = new CategorySectionPanel();
//                categoryPanel.setCategoryType(e);
//
//                HashMap<String, Integer> brands = new HashMap();
//
//                categoryService2.getAllCategory().forEach(e2->{
//                    if(e2.getType().equals(e)){
//
//                        if(brands.isEmpty()){
//                            brands.put(e2.getBrand(), 1);
//                        }else{
//                            for (String i : brands.keySet()) {
//                                if(i.equals(e2.getBrand())){
//                                    brands.put(i, brands.get(i) + 1);
//                                }else{
//                                    System.out.println("Added new set");
//                                    brands.put(e2.getBrand(), 1);
//                                }
//                            }
//                        }
//                    }
//                });
//
//                for (String i : brands.keySet()) {
//                    CategoryThumb thumb = new CategoryThumb(true);
//                    thumb.setThumbTitle(i);
//                    thumb.setThumbItems(brands.get(i));
//                    thumb.setCategoryType(e);
//                    categoryPanel.addThumb(thumb);
//                }
//
//                jPanel164.add(categoryPanel);
//            });
            showCategory();

            // Current Selected Category
            if (!selectedCategoryType.equals("") || !selectedCategoryBrand.equals("")) {
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllCategory();
                
                ProductService productsService = new ProductService();
                List<Product> products = productsService.getAllProductDetails();
                
                categories.forEach(element -> {
                    if (element.getBrand().equals(selectedCategoryBrand) && element.getType().equals(selectedCategoryType)) {
                        products.forEach(element2 -> {
                            if (element2.getId() == element.getId() && element2.getStatus().equals("active")) {
                                ProductThumb2 productThumb = new ProductThumb2();
                                productThumb.setProductDetails(element2);
                                jPanel165.add(productThumb);
                                System.out.println(element2.getName() + " Added");
                            }
                        });
                    }
                });
            } else {
                ProductService productsService = new ProductService();
                List<Product> products = productsService.getAllProductDetails();
                
                products.forEach(e -> {
                    if(e.getStatus().equals("active")){
                        ProductThumb2 productThumb = new ProductThumb2();
                        productThumb.setProductDetails(e);
                        jPanel165.add(productThumb);
                    }
                });
            }
            
        } else {
            editProductNameTextField1.putClientProperty("JComponent.outline", "error");
        }
    }//GEN-LAST:event_jButton32ActionPerformed

    private void productImage2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productImage2MouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        
        selectedFile = null;
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(new File(filename));
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image scaledImage = bufferedImage.getScaledInstance(productImage2.getWidth(), productImage2.getHeight(), Image.SCALE_SMOOTH);
            
            ImageIcon image = new ImageIcon(scaledImage);
            
            productImage2.setIcon(image);
            System.out.println(filename);
            updateGraphics();
            
            System.out.println(getClass().getResource("/img/product/" + filename));
            
            selectedFile = file;
            
        } else {
            System.out.println("Canceled");
        }
    }//GEN-LAST:event_productImage2MouseClicked

    private void editProductNameTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductNameTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductNameTextField1ActionPerformed

    private void jPanel188ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel188ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel188ComponentRemoved

    private void addProductOptionButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductOptionButton1ActionPerformed
        // TODO add your handling code here:
        JPanel panel = new JPanel();
//        panel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234,234,234)));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.LINE_AXIS));
        panel.add(new AddProductOptionPanel());
        jPanel188.add(panel);
        
        selectedFile = null;
        productImage2.setIcon(null);
        
        updateGraphics();
    }//GEN-LAST:event_addProductOptionButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        barcodeFrame.setVisible(true);
        barcodeFrame.show();
        jPanel204.setVisible(false);
        jPanel209.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        try {
            // TODO add your handling code here:
            Code128Bean code128 = new Code128Bean();
            code128.setHeight(15f);
            code128.setModuleWidth(0.3);
            code128.setQuietZone(10);
            code128.doQuietZone(true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(baos, "image/x-png", 300, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            code128.generateBarcode(canvas, jTextField15.getText());
            canvas.finish();
            
            barcodeImage = canvas.getBufferedImage();

//            Image image = new BufferedImage(bardcode.getWidth(), bardcode.getHeight(), Image.SCALE_SMOOTH);
            Image image = barcodeImage.getScaledInstance(-1, jLabel229.getHeight(), Image.SCALE_SMOOTH);
            
            ImageIcon imageIcon = new ImageIcon(image);
            
            jLabel229.setIcon(imageIcon);
            
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton33ActionPerformed
    public void searchTicketBarcode(){
        String barcode = jTextField16.getText();

        ProductService ps = new ProductService();
        List<Product> pList = ps.getProductByBarcode(barcode);

        VariantService vs = new VariantService();
        List<Variants> vList = new ArrayList<>();
        vList = vs.getProductVariantsByBarcode(barcode);

        jPanel211.removeAll();
        if(pList.size() != 0 && vList.size() != 0){
            pList.forEach(e -> {
                BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                barcodeResult.setFrame(this);
                barcodeResult.setName(e.getName());
                barcodeResult.setBarcode(barcode);
                barcodeResult.setProduct(e);
                jPanel211.add(barcodeResult);
            });

            vList.forEach(e -> {
                if (e.getMainVariant() == 0) {
                    System.out.println(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                    BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                    barcodeResult.setFrame(this);
                    barcodeResult.setName(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                    barcodeResult.setBarcode(barcode);
                    barcodeResult.setProduct(ps.getProductById(e.getProduct_id()));
                    jPanel211.add(barcodeResult);
                }
            });

        }else{

            if (pList != null && pList.size() != 0) {
                if(pList.size() == 1){
                    pList.forEach(e -> {
                        if(vs.getProductVariants(e.getId()).size() <= 0){

                            if (checkoutProduct.containsKey(e.getId())) {
                                int addedStocks = checkoutProduct.get(e.getId()).get(0).getStocks() + 1;
                                checkoutProduct.get(e.getId()).get(0).setStocks(addedStocks);

                            } else {
                                Variants tempV = new Variants();
                                tempV.setStocks(1);

                                List<Variants> tempListVar = new ArrayList<>();
                                tempListVar.add(tempV);
                                checkoutProduct.put(e.getId(), tempListVar);
                            }

                            checkoutThumbScrollPane.removeAll();

                            showCheckoutPanel();

                            if (!isPopupShowed) {
                                showPopup(checkoutProductPanel);
                            }
                        }
                    });

                }else{
                    pList.forEach(e -> {
                        BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                        barcodeResult.setFrame(this);
                        barcodeResult.setName(e.getName());
                        barcodeResult.setBarcode(barcode);
                        barcodeResult.setProduct(e);
                        jPanel211.add(barcodeResult);
                    });
                }

            }


            if (vList != null && vList.size() != 0) {
                if(vList.size() == 1){
                    vList.forEach(e -> {
                        if (e.getMainVariant() == 0) {

                            if(checkoutProduct.containsKey(e.getProduct_id())){
                                List<Variants> tempVarList = new ArrayList<>();
                                Variants tempV = new Variants();
                                tempV.setStocks(1);

                                boolean variantExist = false;
                                for(int i = 0; i < checkoutProduct.get(e.getProduct_id()).size(); i++){
                                    if(checkoutProduct.get(e.getProduct_id()).get(i).getId() == e.getId()){
                                        int addedStocks = checkoutProduct.get(e.getProduct_id()).get(i).getStocks() + 1;
                                        checkoutProduct.get(e.getProduct_id()).get(i).setStocks(addedStocks);
                                        variantExist = true;
                                    }
                                }
                                if(!variantExist){
                                    checkoutProduct.get(e.getProduct_id()).add(tempV);
                                }
                            }else{
                                List<Variants> tempVarList = new ArrayList<>();
                                e.setStocks(1);
                                tempVarList.add(e);
                                checkoutProduct.put(selectedProduct.getId(), tempVarList);
                            }

                        }
                    });
                    checkoutThumbScrollPane.removeAll();

                    showCheckoutPanel();

                    if (!isPopupShowed) {
                        showPopup(checkoutProductPanel);
                    }
                }else{
                    vList.forEach(e -> {
                        if (e.getMainVariant() == 0) {
                            System.out.println(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                            BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                            barcodeResult.setFrame(this);
                            barcodeResult.setName(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                            barcodeResult.setBarcode(barcode);
                            barcodeResult.setProduct(ps.getProductById(e.getProduct_id()));
                            jPanel211.add(barcodeResult);
                        }
                    });
                }
            }
        }

        List<Receipt> sales = new ArrayList<>();

        checkoutProduct.forEach((k, et) -> {
            et.forEach(e->{
                if (e.getId() == 0) {
                    Product checkoutSelectedProduct = new Product();
                    checkoutSelectedProduct = ps.getProductById(k);
                    checkoutTotalPrice += checkoutSelectedProduct.getPrice() * e.getStocks();

                    sales.add(new Receipt(checkoutSelectedProduct.getName(), e.getStocks(), checkoutSelectedProduct.getPrice() * e.getStocks()));
                } else {

                    vs.getProductVariants(k).forEach(e2 -> {
                        if (e2.getMainVariant() == 0) {
                            if (e2.getName().equals(e.getName())) {
                                checkoutTotalPrice += e.getPrice() * e.getStocks();

                                Product product = ps.getProductById(k);
                                sales.add(new Receipt(product.getName() + "(" + e.getName() + ")", e.getStocks(), e.getPrice() * e.getStocks()));
                            }
                        }
                    });
                }
            });
        });
        jLabel19.setText("Total Amount: ₱" + String.valueOf(checkoutTotalPrice));

        System.out.println(checkoutProduct.toString());
        jTextField16.selectAll();
        updateGraphics();
    }
    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
        if(selectedMenu == productMainMenu){
            jPanel211.removeAll();
            String barcode = jTextField16.getText();
            ProductService ps = new ProductService();
            ps.getProductByBarcode(barcode).forEach(e -> {
                System.out.println(e.getName());
                BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                barcodeResult.setFrame(this);
                barcodeResult.setName(e.getName());
                barcodeResult.setBarcode(barcode);
                barcodeResult.setProduct(e);
                jPanel211.add(barcodeResult);
            });

            VariantService vs = new VariantService();
            vs.getProductVariantsByBarcode(barcode).forEach(e -> {
                if (e.getMainVariant() == 0) {
                    System.out.println(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                    BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                    barcodeResult.setFrame(this);
                    barcodeResult.setName(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                    barcodeResult.setBarcode(barcode);
                    barcodeResult.setProduct(ps.getProductById(e.getProduct_id()));
                    jPanel211.add(barcodeResult);
                }
            });
            updateGraphics();
        }else if(selectedMenu == ticketMainMenu){
            searchTicketBarcode();
        }
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jPanel205MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel205MouseClicked
        // TODO add your handling code here:
        jPanel204.setVisible(false);
        jPanel209.setVisible(true);
    }//GEN-LAST:event_jPanel205MouseClicked

    private void jPanel207MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel207MouseClicked
        // TODO add your handling code here:
        jPanel204.setVisible(true);
        jPanel209.setVisible(false);
    }//GEN-LAST:event_jPanel207MouseClicked

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
        ProductService ps = new ProductService();
        selectedProduct.setStatus("deleted");
        ps.updateProduct(selectedProduct.getId(), selectedProduct);
//        ps.deleteProductById(selectedProduct.getId());
//        CategoryService cs = new CategoryService();
//        cs.deleteCategoryByProductId(selectedProduct.getId());
//        VariantService vs = new VariantService();
//        vs.deleteVariantByProductId(selectedProduct.getId());
        
        
        

//        jPanel164.removeAll();
//        CategoryService categoryService2 = new CategoryService();
//        List<String> type = new ArrayList();
//
//        categoryService2.getAllCategory().forEach(e -> {
//            type.add(e.getType());
//        });
//
//        List<String> sortedType = removeDuplicates(type);
//
//        sortedType.forEach(e -> {
//            CategorySectionPanel categoryPanel = new CategorySectionPanel();
//            categoryPanel.setCategoryType(e);
//
//            HashMap<String, Integer> brands = new HashMap();
//
//            categoryService2.getAllCategory().forEach(e2->{
//                if(e2.getType().equals(e)){
//
//                    if(brands.isEmpty()){
//                        brands.put(e2.getBrand(), 1);
//                    }else{
//                        for (String i : brands.keySet()) {
//                            if(i.equals(e2.getBrand())){
//                                brands.put(i, brands.get(i) + 1);
//                            }else{
//                                System.out.println("Added new set");
//                                brands.put(e2.getBrand(), 1);
//                            }
//                        }
//                    }
//                }
//            });
//
//            for (String i : brands.keySet()) {
//                CategoryThumb thumb = new CategoryThumb(true);
//                thumb.setThumbTitle(i);
//                thumb.setThumbItems(brands.get(i));
//                thumb.setCategoryType(e);
//                categoryPanel.addThumb(thumb);
//            }
//
//            jPanel164.add(categoryPanel);
//        });
        showCategory();

        // Current Selected Category
//        if (ticketMainMenu == selectedMenu) {
            jPanel30.removeAll();
//        } else {
            jPanel165.removeAll();
//        }
        
        if (!selectedCategoryType.equals("") || !selectedCategoryBrand.equals("")) {
            CategoryService categoryService = new CategoryService();
            List<Category> categories = categoryService.getAllCategory();
            
            ProductService productsService = new ProductService();
            List<Product> products = productsService.getAllProductDetails();
            
            categories.forEach(element -> {
                if (element.getBrand().equals(selectedCategoryBrand) && element.getType().equals(selectedCategoryType)) {
                    products.forEach(element2 -> {
                        if (element2.getId() == element.getId() && element2.getStatus().equals("active")) {
                            ProductThumb2 productThumb2 = new ProductThumb2();
                            productThumb2.setProductDetails(element2);
                            jPanel165.add(productThumb2);
                            
                            ProductThumb productThumb = new ProductThumb();
                            productThumb.setProductDetails(element2);
                            jPanel30.add(productThumb);
                            System.out.println(element2.getName() + " Added");
                        }
                    });
                }
            });
        } else {
            ProductService productsService = new ProductService();
            List<Product> products = productsService.getAllProductDetails();
            
            products.forEach(e -> {
                if(e.getStatus().equals("active")){
                    ProductThumb2 productThumb2 = new ProductThumb2();
                    productThumb2.setProductDetails(e);
                    jPanel165.add(productThumb2);

                    ProductThumb productThumb = new ProductThumb();
                    productThumb.setProductDetails(e);
                    jPanel30.add(productThumb);
                }
            });
        }
        
        System.out.println("Selected type: " + selectedCategoryType + " Selected brand: " + selectedCategoryBrand);
        selectedProduct = null;
        
        closePopup();
        
        updateGraphics();
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jYearChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jYearChooser1PropertyChange
        // TODO add your handling code here:
        dashboardHistogramPanel.removeAll();
        
        dashboardHistogramPanel.add(new HistogramPanel().createAndShowGUI(jComboBox1.getSelectedIndex()));
        
        jYearChooser1.setForeground(new Color(164, 164, 164));
        repaint();
        revalidate();
//        updateGraphics();
    }//GEN-LAST:event_jYearChooser1PropertyChange

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        dashboardHistogramPanel.removeAll();
        
        dashboardHistogramPanel.add(new HistogramPanel().createAndShowGUI(jComboBox1.getSelectedIndex()));
        
        repaint();
        revalidate();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    public void showCheckoutPanel(){
        checkoutProduct.forEach((k, el) -> {
            el.forEach(e -> {
                ProductService productService = new ProductService();
                Product checkoutSelectedProduct = new Product();

                if (e.getId() == 0) {
                    CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
                    checkoutSelectedProduct = productService.getProductById(k);
                    checkout.setItems(e.getStocks());
                    checkout.setVariants(null);
                    checkout.setPrice(checkoutSelectedProduct.getPrice() * e.getStocks());
                    checkout.setProductId(k);

                    checkout.setProductName(checkoutSelectedProduct.getName());
                    checkout.setImage(checkoutSelectedProduct.getImage());
                    checkoutThumbScrollPane.add(checkout);
                } else {
                    CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
                    checkout.setItems(e.getStocks());
                    checkout.setVariants(e.getName());
                    checkout.setPrice(e.getPrice() * e.getStocks());
                    checkout.setVariantId(e.getId());
                    checkout.setProductId(k);

                    checkoutSelectedProduct = productService.getProductById(k);
                    checkout.setProductName(checkoutSelectedProduct.getName());
                    checkout.setImage(checkoutSelectedProduct.getImage());
                    checkoutThumbScrollPane.add(checkout);
                }
            });
        });
    }
    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        // TODO add your handling code here:
        if(selectedMenu == ticketMainMenu){
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

//                String barcode = jTextField16.getText();
//
//                ProductService ps = new ProductService();
//                List<Product> pList = ps.getProductByBarcode(barcode);
//
//                if (pList != null && pList.size() != 0) {
//                    pList.forEach(e -> {
//
//                        if (checkoutProduct.containsKey(e.getId())) {
//
//
//                            int addedStocks = checkoutProduct.get(e.getId()).get(0).getStocks() + 1;
//                            checkoutProduct.get(e.getId()).get(0).setStocks(addedStocks);
//
//    //                        listvar.add(tempVariants);
//    //                        checkoutProduct.put(selectedProduct.getId(), listvar);
//
//
//    //                        Variants tempV = checkoutProduct.get(e.getId());
//    //                        tempV.setStocks(tempV.getStocks() + 1);
//    //                        checkoutProduct.put(e.getId(), tempV);
//                        } else {
//                            Variants tempV = new Variants();
//                            tempV.setStocks(1);
//
//                            List<Variants> tempListVar = new ArrayList<>();
//                            tempListVar.add(tempV);
//                            checkoutProduct.put(e.getId(), tempListVar);
//                        }
//                    });
//
//                    checkoutThumbScrollPane.removeAll();
//    //                checkoutProduct.forEach((k, e)->{
//    //                    ProductService productService = new ProductService();
//    //                    Product checkoutSelectedProduct = productService.getProductById(k);
//    //                    
//    //                    CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
//    //                    checkout.setItems(e.getStocks());
//    //                    checkout.setVariants("");
//    //                    checkout.setPrice(checkoutSelectedProduct.getPrice() * e.getStocks());
//    //
//    //                    checkout.setProductName(checkoutSelectedProduct.getName());
//    //                    checkout.setImage(checkoutSelectedProduct.getImage());
//    //                    
//    //                    checkoutThumbScrollPane.add(checkout);
//    //                });
//
//                    showCheckoutPanel();
//
//                    if (!isPopupShowed) {
//                        showPopup(checkoutProductPanel);
//                    }
//
//                }
//
//                VariantService vs = new VariantService();
//                List<Variants> vList = new ArrayList<>();
//                vList = vs.getProductVariantsByBarcode(barcode);
//                if (vList != null && vList.size() != 0) {
//                    vList.forEach(e -> {
//                        if (e.getMainVariant() == 0) {
//
//                            if(checkoutProduct.containsKey(e.getProduct_id())){
//                                List<Variants> tempVarList = new ArrayList<>();
//                                Variants tempV = new Variants();
//                                tempV.setStocks(1);
//
//                                boolean variantExist = false;
//                                for(int i = 0; i < checkoutProduct.get(e.getProduct_id()).size(); i++){
//                                    if(checkoutProduct.get(e.getProduct_id()).get(i).getId() == e.getId()){
//                                        int addedStocks = checkoutProduct.get(e.getProduct_id()).get(i).getStocks() + 1;
//                                        checkoutProduct.get(e.getProduct_id()).get(i).setStocks(addedStocks);
//                                        variantExist = true;
//                                    }
//                                }
//                                if(!variantExist){
//                                    checkoutProduct.get(e.getProduct_id()).add(tempV);
//                                }
//                            }else{
//                                List<Variants> tempVarList = new ArrayList<>();
//                                e.setStocks(1);
//                                tempVarList.add(e);
//                                checkoutProduct.put(selectedProduct.getId(), tempVarList);
//                            }
//
//
//
//    //                        if (checkoutProduct.containsKey(e.getProduct_id())) {
//    //                            Variants tempV = checkoutProduct.get(e.getProduct_id());
//    //                            tempV.setStocks(tempV.getStocks() + 1);
//    //                            checkoutProduct.put(e.getProduct_id(), tempV);
//    //                        } else {
//    //                            e.setStocks(1);
//    //                            checkoutProduct.put(e.getProduct_id(), e);
//    //                        }
//                        }
//                    });
//                    checkoutThumbScrollPane.removeAll();
//    //                checkoutProduct.forEach((k, e)->{
//    //                    ProductService productService = new ProductService();
//    //                    Product checkoutSelectedProduct = new Product();
//    //                    CheckoutProductThumbPanel checkout = new CheckoutProductThumbPanel(k);
//    //                    checkout.setItems(e.getStocks());
//    //                    checkout.setVariants(e.getName());
//    //                    checkout.setPrice(e.getPrice() * e.getStocks());
//    //
//    //
//    //                    checkoutSelectedProduct = productService.getProductById(k);
//    //                    checkout.setProductName(checkoutSelectedProduct.getName());
//    //                    checkout.setImage(checkoutSelectedProduct.getImage());
//    //                    checkoutThumbScrollPane.add(checkout);
//    //                });
//
//                    showCheckoutPanel();
//
//                    if (!isPopupShowed) {
//                        showPopup(checkoutProductPanel);
//                    }
//                }
//
//                List<Receipt> sales = new ArrayList<>();
//
//                checkoutProduct.forEach((k, et) -> {
//                    et.forEach(e->{
//                        if (e.getId() == 0) {
//                            Product checkoutSelectedProduct = new Product();
//                            checkoutSelectedProduct = ps.getProductById(k);
//                            checkoutTotalPrice += checkoutSelectedProduct.getPrice() * e.getStocks();
//
//                            sales.add(new Receipt(checkoutSelectedProduct.getName(), e.getStocks(), checkoutSelectedProduct.getPrice() * e.getStocks()));
//                        } else {
//
//                            vs.getProductVariants(k).forEach(e2 -> {
//                                if (e2.getMainVariant() == 0) {
//                                    if (e2.getName().equals(e.getName())) {
//                                        checkoutTotalPrice += e.getPrice() * e.getStocks();
//
//                                        Product product = ps.getProductById(k);
//                                        sales.add(new Receipt(product.getName() + "(" + e.getName() + ")", e.getStocks(), e.getPrice() * e.getStocks()));
//                                    }
//                                }
//                            });
//                        }
//                    });
//                });
//                jLabel19.setText("Total Amount: ₱" + String.valueOf(checkoutTotalPrice));
//
//                System.out.println(checkoutProduct.toString());
//                jTextField16.selectAll();
                searchTicketBarcode();

            } else {
    //            jTextField16.setText(jTextField16.getText() + evt.getKeyChar());
            }
        }else if(selectedMenu == productMainMenu){
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                jPanel211.removeAll();
                String barcode = jTextField16.getText();
                ProductService ps = new ProductService();
                ps.getProductByBarcode(barcode).forEach(e -> {
                    System.out.println(e.getName());
                    BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                    barcodeResult.setName(e.getName());
                    barcodeResult.setBarcode(barcode);
                    jPanel211.add(barcodeResult);
                });

                VariantService vs = new VariantService();
                vs.getProductVariantsByBarcode(barcode).forEach(e -> {
                    if (e.getMainVariant() == 0) {
                        System.out.println(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                        BarcodeResultThumb barcodeResult = new BarcodeResultThumb();
                        barcodeResult.setFrame(this);
                        barcodeResult.setName(ps.getProductById(e.getProduct_id()).getName() + " (" + e.getName() + ")");
                        barcodeResult.setBarcode(barcode);
                        barcodeResult.setProduct(ps.getProductById(e.getProduct_id()));
                        jPanel211.add(barcodeResult);
                    }
                });
                updateGraphics();
            }
        }
    }//GEN-LAST:event_jTextField16KeyReleased

    private void jSpinner4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSpinner4PropertyChange
        // TODO add your handling code here:
//        if((int)jSpinner4.getValue() < 1){
//            jSpinner4.setValue(1);
//        }
    }//GEN-LAST:event_jSpinner4PropertyChange

    private void jSpinner4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner4StateChanged
        // TODO add your handling code here:
        if ((int) jSpinner4.getValue() < 1) {
            jSpinner4.setValue(1);
        }
        float total = (float) selectedProductPrice * (int)jSpinner4.getValue();
        jLabel12.setText(String.valueOf(total));
    }//GEN-LAST:event_jSpinner4StateChanged

    private void personnelMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_personnelMainMenuMouseClicked
        // TODO add your handling code here:
//        updateGraphics();
        menuHeader.setText("Personnels");
        contentPanel.removeAll();
        contentPanel.add(personelMainPanel);
        
        resetMainMenu();
        FlatSVGIcon personnelicon = new FlatSVGIcon("img/icon/briefcase-solid.svg", 30, 30);
        personnelicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel272.setIcon(personnelicon);
        jLabel271.setForeground(Color.white);
        
        
        WrapLayout wrap = new WrapLayout();
        wrap.setAlignment(0);
        jPanel218.setLayout(wrap);
        
        updateGraphics();
        updatePersonnelData();
        

        jPanel215.removeAll();
        
        
        UserService us = new UserService();
        
        Map<Integer, Float> sortedMap = userSalesRank.entrySet().stream()
        .sorted(Comparator.comparingDouble(e -> -e.getValue()))
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> { throw new AssertionError(); },
                LinkedHashMap::new
        ));

        sortedMap.entrySet().forEach(System.out::println);
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        totalPersonnels=0;
        
        SalesComputationController scom  = new SalesComputationController();
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        float annualSales = scom.getSumofAnnuallySales(String.valueOf(currentYear));
        jLabel249.setText(String.valueOf(df.format(annualSales)));
        jLabel250.setText(String.valueOf(df.format(annualSales / 365)));
        
        sortedMap.forEach((k, e) -> {
            
            User user = us.getUserById(k);
            if(user.getStatus() != 2){
                TopPersonnelThumb topPersonnelThumb = new TopPersonnelThumb();
                topPersonnelThumb.setName(user.getName());
                topPersonnelThumb.setImage(user.getImage());
                topPersonnelThumb.setPerformance(String.valueOf(df.format(e)));
                jPanel215.add(topPersonnelThumb);
                
                totalPersonnels++;
            }
            
        });
        
        jLabel246.setText("You have "+ totalPersonnels +" personel on your market");

//        updateGraphics();
//        jPanel73.setPreferredSize(new Dimension(jPanel214.getWidth()- 267, 174));
        
//        jPanel215.setPreferredSize(new Dimension(160 * sortedMap.size() , 161));
        
//        revalidate();
//        repaint();
//        updateGraphics();
    }//GEN-LAST:event_personnelMainMenuMouseClicked

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        UserService us = new UserService();
        if(selectedUser.getStatus() == 1){
            selectedUser.setStatus(0);
            FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
            pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
                    return new Color(249, 92, 92);
                }
            }));
            jLabel274.setIcon(pnegativeicon);
            jLabel275.setText("Deactivated");

            jButton16.setText("Activate");
            
            us.updateUser(selectedUser.getId(), selectedUser);
        }else if(selectedUser.getStatus() == 2){
            selectedUser.setStatus(1);
            FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/check_circle_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
            pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
                    return new Color(77, 213, 151);
                }
            }));
            jLabel274.setIcon(pnegativeicon);
            jLabel275.setText("Active");
            
            us.updateUser(selectedUser.getId(), selectedUser);
            closePopup();
            
        }else{
            selectedUser.setStatus(1);
            FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/check_circle_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
            pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
                public Color apply(Color t) {
                    return new Color(77, 213, 151);
                }
            }));
            jLabel274.setIcon(pnegativeicon);
            jLabel275.setText("Active");

            jButton16.setText("Deactivate");
            
            us.updateUser(selectedUser.getId(), selectedUser);
        }
        
    }//GEN-LAST:event_jButton16ActionPerformed

    public void updateCustomerData(){
        
        unregisteredCustomer = 0;
        registeredCustomer = 0;
        
//        jPanel64.removeAll();
//        RingChartCustomer ringChartCustomer = new RingChartCustomer();
//        HashMap<String, Float> customers = new HashMap<>();
//        
//        customers.put("New Customer", (float)totalNewCustomer);
//        customers.put("Registered Customer", (float)totalRegisteredCustomer);
//        
//        ringChartCustomer.setData(customers);
//        jPanel64.add(ringChartCustomer);
        
        
        
        CustomerService cs = new CustomerService();
        
        unregisteredCustomerListPanel.removeAll();
        jPanel226.removeAll();
        cs.getAllCustomer().forEach(e->{
            
            
            if(e.getName().equals("")){
                
                CustomerUnregisteredThumb customerUnregisteredThumb = new CustomerUnregisteredThumb();
                customerUnregisteredThumb.setCustomerData(e);
                
                if(e.getStatus().equals("Deleted")){
                    customerUnregisteredThumb.setVisible(false);
                }else{
                    unregisteredCustomer++;
                }
                
                unregisteredCustomerListPanel.add(customerUnregisteredThumb);
                
            }else{
                CustomerThumb cthumb = new CustomerThumb();
                cthumb.setCustomerData(e);
                if(e.getStatus().equals("Deleted")){
                    cthumb.setVisible(false);
                }else{
                    registeredCustomer++;
                }
                if(jComboBox6.getSelectedIndex() == 0){
                    jPanel226.add(cthumb);
                }else if(jComboBox6.getSelectedIndex() == 1){
                    if(e.getStatus().equals("Active")){
                        jPanel226.add(cthumb);
                    }
                }else if(jComboBox6.getSelectedIndex() == 2){
                    if(e.getStatus().equals("Blocked")){
                        jPanel226.add(cthumb);
                    }
                }
                
            }
        });
        
        
        jLabel302.setText(String.valueOf(registeredCustomer));
        jLabel303.setText(String.valueOf(unregisteredCustomer));
        jLabel306.setText(String.valueOf(registeredCustomer + unregisteredCustomer));
        
        
        WrapLayout wrap = new WrapLayout();
        wrap.setAlignment(0);
        jPanel226.setLayout(wrap);
        
        jButton39.setVisible(false);
        
        
        
        updateGraphics();
    }
    
    private void customerMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerMainMenuMouseClicked
        // TODO add your handling code here:
        menuHeader.setText("Customers");
        contentPanel.removeAll();
        contentPanel.add(customerMainPanel);
       
        
        resetMainMenu();
        FlatSVGIcon customericon = new FlatSVGIcon("img/icon/user-tag-solid.svg", 29, 25);
        customericon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel57.setIcon(customericon);
//        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/cart-icon-white.png")));
        jLabel53.setForeground(Color.white);
        
        
        YearMonth yearMonthObject = YearMonth.of(jYearChooser2.getYear(), jMonthChooser1.getMonth());
        LocalDate firstOfMonth = yearMonthObject.atDay(1);
        LocalDate firstOfFollowingMonth = yearMonthObject.plusMonths(1).atDay(1);
//        int daysInMonth = yearMonthObject.lengthOfMonth();
//        ButtonGroup buttonGroup = new ButtonGroup();

        jPanel25.removeAll();
        firstOfMonth.datesUntil(firstOfFollowingMonth).forEach(e -> {
            JToggleButton button = new JToggleButton();
            button.setText(String.valueOf(e.getDayOfMonth()));
            button.setMaximumSize(new Dimension(54, 60));
            button.setMinimumSize(new Dimension(54, 60));
            button.setPreferredSize(new Dimension(54, 60));
            button.setActionCommand(String.valueOf(e.getDayOfMonth()));
            
            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    jPanel49.removeAll();
                    jPanel49.add(new CustomerLineChart());

                    jLabel21.setText(new DateFormatSymbols().getMonths()[jMonthChooser1.getMonth()] + " " + button.getText());

                    jPanel64.removeAll();
                    RingChartCustomer ringChartCustomer = new RingChartCustomer();
                    HashMap<String, Float> customers = new HashMap<>();

                    customers.put("New Customer", (float)totalNewCustomer);
                    customers.put("Registered Customer", (float)totalRegisteredCustomer);

                    ringChartCustomer.setData(customers);
                    jPanel64.add(ringChartCustomer);


                    repaint();
                    revalidate();
                }
                
            });
            
            
            if(e.getDayOfMonth() == 18){
                button.setSelected(true);
            }
            
            buttonGroup.add(button);
            jPanel25.add(button);
        });
        
        System.out.println("Selected Date: " + buttonGroup.getSelection().getActionCommand());
        
        Dimension d = new Dimension(54 * yearMonthObject.lengthOfMonth(), 65);
        jPanel25.setPreferredSize(d);
        jPanel25.setMinimumSize(d);
        jPanel25.setMaximumSize(d);
        System.out.println("PANEL SIZE: " + jPanel125.getSize() + ", " + d);
        
//        for(int i = 1 ; i <= daysInMonth; i++){
//            JToggleButton button = new JToggleButton();
//            button.setText(String.valueOf(i));
//            button.setPreferredSize(new Dimension(54, 60));
//            jPanel25.add(button);
//        }
        
        jPanel49.add(new CustomerLineChart());
        
        updateCustomerData();
        
        
//        updateGraphics();
    }//GEN-LAST:event_customerMainMenuMouseClicked

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void archiveMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_archiveMainMenuMouseClicked
        // TODO add your handling code here:
        menuHeader.setText("Archives");
        contentPanel.removeAll();
        contentPanel.add(archiveMainPanel);
        
        resetMainMenu();
        FlatSVGIcon archiveicon = new FlatSVGIcon("img/icon/file-zipper-solid.svg", 23, 27);
        archiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(255, 255, 255);
            }
        }));
        jLabel59.setIcon(archiveicon);
        jLabel58.setForeground(Color.white);
        
        jPanel88.removeAll();
        ProductService ps = new ProductService();
        ps.getDeletedProduct().forEach(e -> {
            ProductThumb2 productThumb = new ProductThumb2();
            productThumb.setProductDetails(e);
            
            jPanel88.add(productThumb);
        });
        
        jPanel20.removeAll();
        UserService us = new UserService();
        us.getAllUserDeleted().forEach(e -> {
            PersonnelThumb personnelThumb = new PersonnelThumb();
            personnelThumb.setUserData(e);
            
            jPanel20.add(personnelThumb);
        });
//        jPanel20

        jPanel52.removeAll();
        CustomerService cs = new CustomerService();
        cs.getAllDeletedCustomer().forEach(e -> {
            CustomerThumb customerThumb = new CustomerThumb();
            customerThumb.setCustomerData(e);
            jPanel52.add(customerThumb);
            
            System.out.println("Deleted Customer: " + e);
        });
        
        
        updateGraphics();
        
    }//GEN-LAST:event_archiveMainMenuMouseClicked

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
        jPanel49.removeAll();
        jPanel49.add(new CustomerLineChart());
        
        jPanel64.removeAll();
        RingChartCustomer ringChartCustomer = new RingChartCustomer();
        HashMap<String, Float> customers = new HashMap<>();

        customers.put("New Customer", (float)totalNewCustomer);
        customers.put("Registered Customer", (float)totalRegisteredCustomer);

        ringChartCustomer.setData(customers);
        jPanel64.add(ringChartCustomer);
        
        jPanel95.remove(jScrollPane28);
//        jScrollPane28.setVisible(false);
        if(jComboBox8.getSelectedIndex() == 0){
//                jScrollPane28.setVisible(true);
            jPanel95.add(jScrollPane28);
            jYearChooser2.setVisible(true);
            jMonthChooser1.setVisible(true);
        }else if(jComboBox8.getSelectedIndex() == 1){
            jYearChooser2.setVisible(true);
            jMonthChooser1.setVisible(false);
        }else if(jComboBox8.getSelectedIndex() == 2){
        
            jYearChooser2.setVisible(false);
            jMonthChooser1.setVisible(false);

        }
        
        
        
//        updateGraphics();
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        jLabel237.setVisible(false);
//        jLabel318.setVisible(false);
//        jLabel319.setVisible(false);
//        jLabel320.setVisible(false);

        jTextField8.setEnabled(true);
//        jTextField9.setFocusable(true);
//        jTextField10.setFocusable(true);
        jTextField26.setEnabled(true);
        jDateChooser4.setEnabled(true);
        jRadioButton7.setEnabled(true);
        jRadioButton8.setEnabled(true);
        
        
        jTextField11.setVisible(true);
//        jTextField8.setVisible(true);
//        jTextField9.setVisible(true);
//        jTextField10.setVisible(true);
        
        jButton14.setVisible(false);
        jButton39.setVisible(true);
        
        jTextField11.setText(selectedCustomer.getName());
        jTextField8.setText(selectedCustomer.getAddress());
//        jTextField9.setText(selectedCustomer.getGender());
//        jTextField10.setText(selectedCustomer.getBirthDate());
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
        jLabel237.setVisible(!false);
//        jLabel318.setVisible(!false);
//        jLabel319.setVisible(!false);
//        jLabel320.setVisible(!false);

        jTextField8.setEnabled(false);
//        jTextField9.setFocusable(false);
//        jTextField10.setFocusable(false);
        jTextField26.setEnabled(false);
        jDateChooser4.setEnabled(false);
        jRadioButton7.setEnabled(false);
        jRadioButton8.setEnabled(false);
        
        jTextField11.setVisible(!true);
//        jTextField8.setVisible(!true);
//        jTextField9.setVisible(!true);
//        jTextField10.setVisible(!true);
        
        jButton14.setVisible(!false);
        jButton39.setVisible(!true);
        
        
        
        CustomerService cs = new CustomerService();
        selectedCustomer.setName(jTextField11.getText());
        selectedCustomer.setAddress(jTextField8.getText());
//        selectedCustomer.setGender(jTextField9.getText());
//        selectedCustomer.setBirthDate(jTextField10.getText());
        selectedCustomer.setContactNum(jTextField26.getText());
        selectedCustomer.setBirthDate(new java.sql.Timestamp(jDateChooser4.getDate().getTime()).toString());
        selectedCustomer.setGender(customerGenderButtonGroup.getSelection().getActionCommand());
        
        jLabel237.setText(selectedCustomer.getName());
//        jLabel318.setText(selectedCustomer.getAddress());
//        jLabel319.setText(selectedCustomer.getGender());
//        jLabel320.setText(selectedCustomer.getBirthDate());
        
        cs.updateCustomer(selectedCustomer.getId(), selectedCustomer);
        
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
//        CreateNewCustomerPanel createCustomerPanel = new CreateNewCustomerPanel();
        createCustomerPanel.isForCustomerService(false);
        createCustomerDialog.setVisible(true);
        updateGraphics();
    }//GEN-LAST:event_jButton12ActionPerformed

    int lastYearSelected = 0;
    private void jYearChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jYearChooser2PropertyChange
        // TODO add your handling code here:
        jPanel49.removeAll();
        jPanel49.add(new CustomerLineChart());
        
        
        if(lastYearSelected != jYearChooser2.getYear()){
            System.out.println("Last Year: "+lastYearSelected);
            lastYearSelected = jYearChooser2.getYear();
            
            jPanel64.removeAll();
            RingChartCustomer ringChartCustomer = new RingChartCustomer();
            HashMap<String, Float> customers = new HashMap<>();

            customers.put("New Customer", (float)totalNewCustomer);
            customers.put("Registered Customer", (float)totalRegisteredCustomer);

            ringChartCustomer.setData(customers);
            jPanel64.add(ringChartCustomer);
            
        }

        
        repaint();
        revalidate();
        System.out.println("CHanged");
        System.out.println(jYearChooser2.getYear());
    }//GEN-LAST:event_jYearChooser2PropertyChange

    public void updateCustomerChartMonth(){
        YearMonth yearMonthObject = YearMonth.of(jYearChooser2.getYear(), jMonthChooser1.getMonth());
        LocalDate firstOfMonth = yearMonthObject.atDay(1);
        LocalDate firstOfFollowingMonth = yearMonthObject.plusMonths(1).atDay(1);
//        int daysInMonth = yearMonthObject.lengthOfMonth();


        jPanel25.removeAll();
        firstOfMonth.datesUntil(firstOfFollowingMonth).forEach(e -> {
            JToggleButton button = new JToggleButton();
            button.setText(String.valueOf(e.getDayOfMonth()));
            button.setMaximumSize(new Dimension(54, 60));
            button.setMinimumSize(new Dimension(54, 60));
            button.setPreferredSize(new Dimension(54, 60));
            button.setActionCommand(String.valueOf(e.getDayOfMonth()));

            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    jPanel49.removeAll();
                    jPanel49.add(new CustomerLineChart());

                    jLabel21.setText(new DateFormatSymbols().getMonths()[jMonthChooser1.getMonth()] + " " + button.getText());

                    jPanel64.removeAll();
                    RingChartCustomer ringChartCustomer = new RingChartCustomer();
                    HashMap<String, Float> customers = new HashMap<>();

                    customers.put("New Customer", (float)totalNewCustomer);
                    customers.put("Registered Customer", (float)totalRegisteredCustomer);

                    ringChartCustomer.setData(customers);
                    jPanel64.add(ringChartCustomer);


                    repaint();
                    revalidate();
                }

            });

            buttonGroup.add(button);
            jPanel25.add(button);
        });
        Dimension d = new Dimension(54 * yearMonthObject.lengthOfMonth(), 65);
        jPanel25.setPreferredSize(d);
        jPanel25.setMinimumSize(d);
        jPanel25.setMaximumSize(d);
        System.out.println("PANEL SIZE: " + jPanel125.getSize() + ", " + d);

        revalidate();
    }
    
    int lastSelectedMonth = 0;
    private void jMonthChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jMonthChooser1PropertyChange
        // TODO add your handling code here:
        switch(jComboBox8.getSelectedIndex()){
            case 0:
                updateCustomerChartMonth();
                break;
        }
        
        if(lastSelectedMonth != jMonthChooser1.getMonth()){
            System.out.println("Selected Month: " + lastSelectedMonth);
            lastSelectedMonth = jMonthChooser1.getMonth();
            
//            jPanel64.removeAll();
//            RingChartCustomer ringChartCustomer = new RingChartCustomer();
//            HashMap<String, Float> customers = new HashMap<>();
//
//            customers.put("New Customer", (float)totalNewCustomer);
//            customers.put("Registered Customer", (float)totalRegisteredCustomer);
//
//            ringChartCustomer.setData(customers);
//            jPanel64.add(ringChartCustomer);
            
        }
        
        System.out.println(jMonthChooser1.getMonth());
    }//GEN-LAST:event_jMonthChooser1PropertyChange

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        UserService us = new UserService();
        selectedUser.setStatus(2);
        us.updateUser(selectedUser.getId(), selectedUser);
        
        FlatSVGIcon pnegativeicon = new FlatSVGIcon("img/icon/do_not_disturb_on_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
        pnegativeicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(168, 168, 168);
            }
        }));
        jLabel274.setIcon(pnegativeicon);
        jLabel275.setText("Deleted");
        
        
        
//        jPanel218.removeAll();
//        List<User> userList = new ArrayList<>();
//        userList = us.getAllUserDetails();
//        
//        int personnelActive = 0;
//        int personnelDeactivated = 0;
//        int personnelTotal = userList.size();
//        
//        for(int i = 0; i < userList.size(); i++) {
//            
//            PersonnelThumb personelThumb = new PersonnelThumb();
//            personelThumb.setUserData(userList.get(i));
//            
//            if(userList.get(i).getStatus() == 2){
//                personelThumb.setVisible(false);
//            }
//            
//            jPanel218.add(personelThumb);
//                
//            if(userList.get(i).getStatus() == 1){
//                personnelActive++;
//            }else{
//                personnelDeactivated++;
//            }
//        }
        
        closePopup();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        UserService us = new UserService();
        
        if(selectedUser.getRole().equals("admin")){
            selectedUser.setRole("employee");
            jLabel131.setForeground(new java.awt.Color(0, 102, 255));
            jLabel131.setText("Employee");
        }else {
            selectedUser.setRole("admin");
            jLabel131.setForeground(new java.awt.Color(255, 151, 55));
            jLabel131.setText("Admin");
        }
        us.updateUser(selectedUser.getId(), selectedUser);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        CustomerService cs = new CustomerService();
        selectedCustomer.setStatus("Deleted");
        cs.updateCustomer(selectedCustomer.getId(), selectedCustomer);
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
        ProductService ps = new ProductService();

        selectedProduct.setStatus("active");

        ps.updateProduct(selectedProduct.getId(), selectedProduct);
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        
        if(selectedCustomer.getStatus().equals("Active")){
            jPanel97.setVisible(true);

//            jButton22.setVisible(true);
            jButton22.setText("Activate");
            CustomerService cs = new CustomerService();
            selectedCustomer.setStatus("Blocked");
            cs.updateCustomer(selectedCustomer.getId(), selectedCustomer);
        }else{
            jPanel97.setVisible(false);
            jButton22.setText("Block Customer");
//            jButton22.setVisible(false);
            CustomerService cs = new CustomerService();
            selectedCustomer.setStatus("Active");
            cs.updateCustomer(selectedCustomer.getId(), selectedCustomer);
        }
        
        revalidate();
//        closePopup();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        String cwd = System.getProperty("user.dir");
        String reportPath = cwd + "/Invoice.jrxml";
        //"src/pos_timesquare/utils/Invoice.jrxml"
        
        
        if(jComboBox9.getSelectedIndex() == 0){
            float reportTotalSales = 0;
            int reportTotalOrder = 0;
            float reportTotalService = 0;
            try {
                // TODO add your handling code here:

    //            String homeDir = System.getProperty("user.home");
    //            String outputFile = homeDir + File.separatorChar + "JasperTest.pdf";

                StoreInfoService storeInfoService = new StoreInfoService();
                StoreInfo storeInfo = storeInfoService.getStoreInfoDetails();

                Map<String, Object> param = new HashMap<>();

                List<PDFReport> productReport = new ArrayList<>();
//                productReport.add(new Report("Casio", 1, 20000, "11/20/2022"));
//                productReport.add(new Report("Casio", 1, 20000, "11/20/2022"));
                
                TransactionHistoryService ths = new TransactionHistoryService();
                ProductService ps = new ProductService();
                List<TransactionHistory> thList = ths.getAllTransactionHistoryDetails();
                for(int i = 0 ; i < thList.size() ; i++){
                    if(new java.sql.Date(jDateChooser3.getDate().getTime()).toString().equals( thList.get(i).getTransactionDate().toString())){
//                    if(new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString().equals( e.getTransactionDate().toString())){
                        Product product = ps.getProductById(thList.get(i).getProductId());
                        String productName = thList.get(i).getVariantId() == null ? product.getName() : product.getName() + "(" + thList.get(i).getVariantId() + ")";
                        reportTotalSales += thList.get(i).getTotalPrice();
                        reportTotalOrder += thList.get(i).getOrders();
                        productReport.add(new PDFReport(productName, 1, thList.get(i).getTotalPrice(), thList.get(i).getTransactionDate().toString()));
                    }
                }


                List<ServiceReport> serviceReport = new ArrayList<>();
//                serviceReport.add(new ServiceReport("1", (float)2000, "11/11/2022"));

                ServiceTicketsService sts = new ServiceTicketsService();
                List<ServiceTickets> stList = sts.getAllServiceTicketsDetails();
                for(int i = 0; i < stList.size(); i++){
                    if(new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString().equals( stList.get(i).getWalkInDate().toString())){
                        serviceReport.add(new ServiceReport("#"+String.valueOf(stList.get(i).getId()), (float)stList.get(i).getPrice(), stList.get(i).getWalkInDate().toString()));
                        reportTotalService += stList.get(i).getPrice();
                    }
                }


                param.put("companyName", storeInfo.getName()+"("+storeInfo.getBranch()+")");
                param.put("phoneNum", storeInfo.getContactNum());
                param.put("address", storeInfo.getAddress());

                param.put("totalSales", String.valueOf(reportTotalSales));
                param.put("totalOrders", String.valueOf(reportTotalOrder));
                param.put("totalService", String.valueOf(reportTotalService));
                
                param.put("companyLogo", new File(cwd + "/img/icon/receiptLogo.jpg").getAbsolutePath());

                //            JasperPrint print = JasperFillManager.fillReport("C:/Users/Acer/Documents/NetBeansProjects/POS_TimeSquare_1/src/test/Black_A4.jrxml", customerName);
                JasperDesign design = JRXmlLoader.load(new File(reportPath).getAbsolutePath());

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productReport);
                JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(serviceReport);

    //            Map<String, Object> parameters = new HashMap<>();
                param.put("CollectionBeanParam", dataSource);
                param.put("ServiceReport", dataSource2);

                JasperReport jreport = JasperCompileManager.compileReport(design);

                //            JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());
                JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());

                JasperViewer.viewReport(print, false);

            } catch (JRException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(jComboBox9.getSelectedIndex() == 1){
            float reportTotalSales = 0;
            int reportTotalOrder = 0;
            float reportTotalService = 0;
            try {
                
                

                StoreInfoService storeInfoService = new StoreInfoService();
                StoreInfo storeInfo = storeInfoService.getStoreInfoDetails();

                Map<String, Object> param = new HashMap<>();

                List<PDFReport> productReport = new ArrayList<>();
                
                TransactionHistoryService ths = new TransactionHistoryService();
                ProductService ps = new ProductService();
                List<TransactionHistory> thList = ths.getAllTransactionHistoryDetails();
                for(int i = 0 ; i < thList.size() ; i++){
                    
                    
                    java.sql.Date dat = java.sql.Date.valueOf(thList.get(i).getTransactionDate().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(month == jMonthChooser2.getMonth() && year == jYearChooser3.getYear()){
                        Product product = ps.getProductById(thList.get(i).getProductId());
                        String productName = thList.get(i).getVariantId() == null ? product.getName() : product.getName() + "(" + thList.get(i).getVariantId() + ")";
                        reportTotalSales += thList.get(i).getTotalPrice();
                        reportTotalOrder += thList.get(i).getOrders();
                        productReport.add(new PDFReport(productName, 1, thList.get(i).getTotalPrice(), thList.get(i).getTransactionDate().toString()));
                    }
                }


                List<ServiceReport> serviceReport = new ArrayList<>();

                ServiceTicketsService sts = new ServiceTicketsService();
                List<ServiceTickets> stList = sts.getAllServiceTicketsDetails();
                for(int i = 0; i < stList.size(); i++){
                    java.sql.Date dat = java.sql.Date.valueOf(stList.get(i).getWalkInDate().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(month == jMonthChooser2.getMonth() && year == jYearChooser3.getYear()){
                        serviceReport.add(new ServiceReport("#"+String.valueOf(stList.get(i).getId()), (float)stList.get(i).getPrice(), stList.get(i).getWalkInDate().toString()));
                        reportTotalService += stList.get(i).getPrice();
                    }
                }


                param.put("companyName", storeInfo.getName()+"("+storeInfo.getBranch()+")");
                param.put("phoneNum", storeInfo.getContactNum());
                param.put("address", storeInfo.getAddress());

                param.put("totalSales", String.valueOf(reportTotalSales));
                param.put("totalOrders", String.valueOf(reportTotalOrder));
                param.put("totalService", String.valueOf(reportTotalService));
                
                param.put("companyLogo", new File(cwd+"/img/icon/receiptLogo.jpg").getAbsolutePath());

                JasperDesign design = JRXmlLoader.load(new File(reportPath).getAbsolutePath());

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productReport);
                JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(serviceReport);

                param.put("CollectionBeanParam", dataSource);
                param.put("ServiceReport", dataSource2);

                JasperReport jreport = JasperCompileManager.compileReport(design);

                //            JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());
                JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());

                JasperViewer.viewReport(print, false);

            } catch (JRException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            float reportTotalSales = 0;
            int reportTotalOrder = 0;
            float reportTotalService = 0;
            try {
                
                

                StoreInfoService storeInfoService = new StoreInfoService();
                StoreInfo storeInfo = storeInfoService.getStoreInfoDetails();

                Map<String, Object> param = new HashMap<>();

                List<PDFReport> productReport = new ArrayList<>();
                
                TransactionHistoryService ths = new TransactionHistoryService();
                ProductService ps = new ProductService();
                List<TransactionHistory> thList = ths.getAllTransactionHistoryDetails();
                for(int i = 0 ; i < thList.size() ; i++){
                    
                    
                    java.sql.Date dat = java.sql.Date.valueOf(thList.get(i).getTransactionDate().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(year == jYearChooser3.getYear()){
                        Product product = ps.getProductById(thList.get(i).getProductId());
                        String productName = thList.get(i).getVariantId() == null ? product.getName() : product.getName() + "(" + thList.get(i).getVariantId() + ")";
                        reportTotalSales += thList.get(i).getTotalPrice();
                        reportTotalOrder += thList.get(i).getOrders();
                        productReport.add(new PDFReport(productName, 1, thList.get(i).getTotalPrice(), thList.get(i).getTransactionDate().toString()));
                    }
                }


                List<ServiceReport> serviceReport = new ArrayList<>();

                ServiceTicketsService sts = new ServiceTicketsService();
                List<ServiceTickets> stList = sts.getAllServiceTicketsDetails();
                for(int i = 0; i < stList.size(); i++){
                    java.sql.Date dat = java.sql.Date.valueOf(stList.get(i).getWalkInDate().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dat);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(year == jYearChooser3.getYear()){
                        serviceReport.add(new ServiceReport("#"+String.valueOf(stList.get(i).getId()), (float)stList.get(i).getPrice(), stList.get(i).getWalkInDate().toString()));
                        reportTotalService += stList.get(i).getPrice();
                    }
                }


                param.put("companyName", storeInfo.getName()+"("+storeInfo.getBranch()+")");
                param.put("phoneNum", storeInfo.getContactNum());
                param.put("address", storeInfo.getAddress());

                param.put("totalSales", String.valueOf(reportTotalSales));
                param.put("totalOrders", String.valueOf(reportTotalOrder));
                param.put("totalService", String.valueOf(reportTotalService));
                
                param.put("companyLogo", new File(cwd +"/img/icon/receiptLogo.jpg").getAbsolutePath());

                JasperDesign design = JRXmlLoader.load(new File(reportPath).getAbsolutePath());

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productReport);
                JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(serviceReport);

                param.put("CollectionBeanParam", dataSource);
                param.put("ServiceReport", dataSource2);

                JasperReport jreport = JasperCompileManager.compileReport(design);

                //            JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());
                JasperPrint print = JasperFillManager.fillReport(jreport, param, new JREmptyDataSource());

                JasperViewer.viewReport(print, false);

            } catch (JRException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ReportService rs = new ReportService();
        
        rs.addReport(new Reports(0, "pdf", new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString()));
        
        jPanel142.removeAll();
        jPanel142.add(new ReportLineChart());
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        // TODO add your handling code here:
        if(jComboBox9.getSelectedIndex() == 0){
            jDateChooser3.setVisible(true);
            jMonthChooser2.setVisible(false);
            jYearChooser3.setVisible(false);
        }else if(jComboBox9.getSelectedIndex() == 1){
            jDateChooser3.setVisible(false);
            jMonthChooser2.setVisible(true);
            jYearChooser3.setVisible(true);
        }else{
            jDateChooser3.setVisible(false);
            jMonthChooser2.setVisible(false);
            jYearChooser3.setVisible(true);
        }
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        // TODO add your handling code here:
        jScrollPane34.setVisible(false);
        jPanel78.setVisible(false);
        if(jComboBox10.getSelectedIndex() == 0){
            checkoutCustomer = null;
        }else if(jComboBox10.getSelectedIndex() == 1){
            jScrollPane34.setVisible(true);
        }else if(jComboBox10.getSelectedIndex() == 2){
            jPanel78.setVisible(true);
        }
        revalidate();
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
        CheckoutSelectCustomerPanel checkoutSelectCustomer = new CheckoutSelectCustomerPanel();
        Point loc = jButton37.getLocationOnScreen();
        checkoutSelectCustomer.setBounds((int)loc.getX() - 100, (int)loc.getY(), 300, 270);
        jLayeredPane1.add(checkoutSelectCustomer, JLayeredPane.DRAG_LAYER);
        
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        SignupAccount signupFrame = new SignupAccount("Employee Account");
        signupFrame.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jCalendar2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCalendar2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendar2MouseEntered

    private void jCalendar2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCalendar2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendar2MouseExited
    
    private void jCalendar2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar2PropertyChange
        
        java.sql.Date sqldate = new java.sql.Date(jCalendar2.getDate().getTime());
        
//        revalidate();
//        repaint();
        
        
        if (!prevDate2.equals(sqldate.toString())) {
            prevDate2 = sqldate.toString();
            updateGraphics();
            System.out.println("Date Update");
            
            
            jPanel216.removeAll();
        
            

            System.out.println(sqldate);
            System.out.println("Calendar date: " + jCalendar2.getDate());

            ServiceTicketsService serviceTicket = new ServiceTicketsService();
            serviceTicket.getAllServiceTicketsDetails().forEach(e -> {
                if (sqldate.toString().equals(e.getWalkInDate().toString())) {
                    ServiceThumb2 sThumb = new ServiceThumb2();
                    sThumb.setRequiredAdmin(true);
                    sThumb.setServiceTicket(e);
                    jPanel216.add(sThumb);
                }
            });
        }
    }//GEN-LAST:event_jCalendar2PropertyChange

    private void ticketSearchBarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ticketSearchBarFocusLost
        // TODO add your handling code here:
        searchPanel.setVisible(false);
        
    }//GEN-LAST:event_ticketSearchBarFocusLost

    private void ticketSearchBarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ticketSearchBarFocusGained
        // TODO add your handling code here:
        searchPanel.setVisible(true);
       
        
        ticketSearchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void removeUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void insertUpdate(DocumentEvent e) {
              showSearchResult();
            }

            public void showSearchResult() {
                searchPanel.removeAll();
                SearchPanel sp = new SearchPanel();
                sp.ticketSearch(ticketSearchBar.getText());
                searchPanel.add(sp);
                
                revalidate();
                repaint();
            }
          });
        
        
        
    }//GEN-LAST:event_ticketSearchBarFocusGained

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        this.requestFocusInWindow();
    }//GEN-LAST:event_formMouseClicked

    private void contentPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contentPanelMouseClicked
        // TODO add your handling code here:
        this.requestFocusInWindow();
    }//GEN-LAST:event_contentPanelMouseClicked

    private void ticketSearchBar1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ticketSearchBar1FocusGained
        // TODO add your handling code here:
        searchPanel.setVisible(true);
       
        
        ticketSearchBar1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void removeUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void insertUpdate(DocumentEvent e) {
              showSearchResult();
            }

            public void showSearchResult() {
                searchPanel.removeAll();
                SearchPanel sp = new SearchPanel();
                sp.productSearch(ticketSearchBar1.getText());
                searchPanel.add(sp);
                
                revalidate();
                repaint();
            }
          });
    }//GEN-LAST:event_ticketSearchBar1FocusGained

    private void ticketSearchBar1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ticketSearchBar1FocusLost
        // TODO add your handling code here:
        searchPanel.setVisible(false);
    }//GEN-LAST:event_ticketSearchBar1FocusLost

    private void jTextField18FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField18FocusGained
        // TODO add your handling code here:
        searchPanel2.setVisible(true);
        jTextField18.putClientProperty("JTextField.trailingComponent", closeSearchLabel);
        
        jTextField18.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void removeUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void insertUpdate(DocumentEvent e) {
              showSearchResult();
            }

            public void showSearchResult() {
                searchPanel2.removeAll();
                SearchPanel sp = new SearchPanel();
                sp.personnelSearch(jTextField18.getText());
                
                searchPanel2.add(sp);
                
                revalidate();
                repaint();
            }
          });
    }//GEN-LAST:event_jTextField18FocusGained

    private void jScrollPane29PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jScrollPane29PropertyChange
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jScrollPane29PropertyChange

    private void jScrollPane29MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jScrollPane29MouseWheelMoved
        // TODO add your handling code here:
        
        
        
        
    }//GEN-LAST:event_jScrollPane29MouseWheelMoved

    private void jTextField18FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField18FocusLost
        // TODO add your handling code here:
        searchPanel2.setVisible(false);
        jTextField18.putClientProperty("JTextField.trailingComponent", null);
    }//GEN-LAST:event_jTextField18FocusLost

    private void jTextField25FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField25FocusGained
        // TODO add your handling code here:
        searchPanel2.setVisible(true);
       jTextField25.putClientProperty("JTextField.trailingComponent", closeSearchLabel);
        
        jTextField25.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void removeUpdate(DocumentEvent e) {
              showSearchResult();
            }
            public void insertUpdate(DocumentEvent e) {
              showSearchResult();
            }

            public void showSearchResult() {
                searchPanel2.removeAll();
                SearchPanel sp = new SearchPanel();
                sp.customerSearch(jTextField25.getText());
                
                searchPanel2.add(sp);
                
                revalidate();
                repaint();
            }
        });
    }//GEN-LAST:event_jTextField25FocusGained

    private void jTextField25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField25FocusLost
        // TODO add your handling code here:
        
        searchPanel2.setVisible(false);
        jTextField25.putClientProperty("JTextField.trailingComponent", null);
    }//GEN-LAST:event_jTextField25FocusLost

    private void jPanel215MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel215MouseEntered
        // TODO add your handling code here:
        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }//GEN-LAST:event_jPanel215MouseEntered

    private void jPanel215MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel215MouseExited
        // TODO add your handling code here:
//        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }//GEN-LAST:event_jPanel215MouseExited

    private void jScrollPane33MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane33MouseEntered
        // TODO add your handling code here:
        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }//GEN-LAST:event_jScrollPane33MouseEntered

    private void jScrollPane33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane33MouseExited
        // TODO add your handling code here:
//        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }//GEN-LAST:event_jScrollPane33MouseExited

    private void jPanel214MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel214MouseExited
        // TODO add your handling code here:
        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }//GEN-LAST:event_jPanel214MouseExited

    private void jPanel214MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel214MouseEntered
        // TODO add your handling code here:
        jScrollPane33.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }//GEN-LAST:event_jPanel214MouseEntered

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        updatePersonnelData();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
        updateCustomerData();
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        ProductService productsService = new ProductService();
        TransactionHistoryService th = new TransactionHistoryService();
        List<TransactionHistory> transactionList = th.getAllTransactionHistoryDetails();

        
        
        Calendar cal = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        productTotalOrder.clear();
        highestCostService = 0;
        
        if(transactionList.size() != 0) {
            if(jComboBox4.getSelectedIndex() == 0){
                for(int i = 0 ; i < 7; i++){
                    java.sql.Date weekDate = new java.sql.Date(cal.getTime().getTime());
                    transactionList.forEach(e -> {
                        
                        if(weekDate.toString().equals(e.getTransactionDate().toString())){
                            if(productTotalOrder.containsKey(e.getProductId())) {
                                int currentOrder = productTotalOrder.get(e.getProductId());
                                productTotalOrder.put(e.getProductId(), currentOrder + e.getOrders());
                            } else {
                                productTotalOrder.put(e.getProductId(), e.getOrders());
                            }
                        }

                        
                    });
                    cal.add(Calendar.DATE, -1);
                }
                int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
                topProductName.setText(productsService.getProductById(topProductId).getName());
                jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
                
                ServiceTicketsService sts = new ServiceTicketsService();
                
                cal.setTime(date);
                for(int i = 0 ; i < 7; i++){
                    java.sql.Date weekDate = new java.sql.Date(cal.getTime().getTime());
                    sts.getAllServiceTicketsDetails().forEach(e -> {
                        java.sql.Date date2 = java.sql.Date.valueOf(e.getWalkInDate().toString());
                        
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int year = cal.get(Calendar.YEAR);

                        if(weekDate.toString().equals(e.getWalkInDate().toString())){
                            totalServiceRevenue += e.getPrice();
                            if(highestCostService < e.getPrice()){
                                topServiceName.setText("Service #"+String.valueOf(e.getId()));

                                jLabel98.setText(String.valueOf(e.getPrice()));

                                highestCostService = e.getPrice();
                            }
                        }
                        System.out.println(weekDate.toString()+" : "+e.getWalkInDate().toString());
                    });
                    cal.add(Calendar.DATE, -1);
                }
                
            }else if(jComboBox4.getSelectedIndex() == 1){
                transactionList.forEach(e -> {
                    java.sql.Date date2 = java.sql.Date.valueOf(e.getTransactionDate().toString());
                    cal.setTime(date2);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(month == currentMonth && year == currentYear){
                        if(productTotalOrder.containsKey(e.getProductId())) {
                            int currentOrder = productTotalOrder.get(e.getProductId());
                            productTotalOrder.put(e.getProductId(), currentOrder + e.getOrders());
                        } else {
                            productTotalOrder.put(e.getProductId(), e.getOrders());
                        }
                    }
                    
                });
                int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
                topProductName.setText(productsService.getProductById(topProductId).getName());
                jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
                
                ServiceTicketsService sts = new ServiceTicketsService();

                sts.getAllServiceTicketsDetails().forEach(e -> {
                    java.sql.Date date2 = java.sql.Date.valueOf(e.getWalkInDate().toString());
                    cal.setTime(date2);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(month == currentMonth && year == currentYear){
                        totalServiceRevenue += e.getPrice();
                        if(highestCostService < e.getPrice()){
                            topServiceName.setText("Service #"+String.valueOf(e.getId()));

                            jLabel98.setText(String.valueOf(e.getPrice()));

                            highestCostService = e.getPrice();
                        }
                    }
                    System.out.println(currentMonth + " : " + month);
                });
                
            }else if(jComboBox4.getSelectedIndex() == 2){
                transactionList.forEach(e -> {
                    java.sql.Date date2 = java.sql.Date.valueOf(e.getTransactionDate().toString());
                    cal.setTime(date2);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(year == currentYear){
                        if(productTotalOrder.containsKey(e.getProductId())) {
                            int currentOrder = productTotalOrder.get(e.getProductId());
                            productTotalOrder.put(e.getProductId(), currentOrder + e.getOrders());
                        } else {
                            productTotalOrder.put(e.getProductId(), e.getOrders());
                        }
                    }
                    
                });
                int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
                topProductName.setText(productsService.getProductById(topProductId).getName());
                jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
                
                cal.setTime(date);
                ServiceTicketsService sts = new ServiceTicketsService();

                sts.getAllServiceTicketsDetails().forEach(e -> {
                    java.sql.Date date2 = java.sql.Date.valueOf(e.getWalkInDate().toString());
                    cal.setTime(date2);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    
                    if(year == currentYear){
                        totalServiceRevenue += e.getPrice();
                        if(highestCostService < e.getPrice()){
                            topServiceName.setText("Service #"+String.valueOf(e.getId()));

                            jLabel98.setText(String.valueOf(e.getPrice()));

                            highestCostService = e.getPrice();
                        }
                    }
                });
                
                
            }else if(jComboBox4.getSelectedIndex() == 3){

                transactionList.forEach(e -> {
                    if (productTotalOrder.containsKey(e.getProductId())) {
                        int currentOrder = productTotalOrder.get(e.getProductId());
                        productTotalOrder.put(e.getProductId(), currentOrder + e.getOrders());
                    } else {
                        productTotalOrder.put(e.getProductId(), e.getOrders());
                    }
                });

                int topProductId = Collections.max(productTotalOrder.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
                topProductName.setText(productsService.getProductById(topProductId).getName());
                jLabel90.setText(String.valueOf(productTotalOrder.get(topProductId)));
                
                cal.setTime(date);
                ServiceTicketsService sts = new ServiceTicketsService();

                sts.getAllServiceTicketsDetails().forEach(e -> {
                    totalServiceRevenue += e.getPrice();
                    if(highestCostService < e.getPrice()){
                        topServiceName.setText("Service #"+String.valueOf(e.getId()));

                        jLabel98.setText(String.valueOf(e.getPrice()));

                        highestCostService = e.getPrice();
                    }
                });
                
            }
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jLabel78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseClicked
        // TODO add your handling code here:
        jPanel158.removeAll();
        NotificationService ns = new NotificationService();
        ns.getAllNotificationDetails().forEach(e->{
            NotificationThumb2 nthumb = new NotificationThumb2();
            nthumb.setNotification(e, this);
            jPanel158.add(nthumb);
            
            ns.UpdateNotification(
                    e.getId(), 
                    e.getProductId(), 
                    e.getDate(), 
                    e.getTitle(), 
                    1);
            
        });
        
        
        notificationDialog.setVisible(true);
        notificationPanel.setVisible(false);
    }//GEN-LAST:event_jLabel78MouseClicked

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        StoreInfoService sinfoService = new StoreInfoService();
        
        storeInfo.setName(storeNameInput.getText());
        storeInfo.setBranch(storeBranchInput.getText());
        storeInfo.setAddress(storeAddressInput.getText());
        storeInfo.setContactNum(storeContactNumInput.getText());
        storeInfo.setEmail(storeEmailInput.getText());
        if(jRadioButton5.isSelected()){
            storeInfo.setSalesPersonDetail(1);
        }else{
            storeInfo.setSalesPersonDetail(0);
        }
        
        sinfoService.updateStoreInfo(1, storeInfo);
        
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        jLabel181.setText("");
        jLabel182.setText("");
        
        boolean isFailed = false;
        
        jTextField19.putClientProperty("JComponent.outline", "default");
        jPasswordField1.putClientProperty("JComponent.outline", "default");
        jTextField21.putClientProperty("JComponent.outline", "default");
        jPasswordField2.putClientProperty("JComponent.outline", "default");
        jPasswordField3.putClientProperty("JComponent.outline", "default");
        
        UserService us = new UserService();
        
        
        if(jPasswordField1.getText().replaceAll("\\s+","").equals("")){
            jPasswordField1.putClientProperty("JComponent.outline", "error");
            jLabel181.setText("Password is empty!");
        }
        if(jTextField19.getText().replaceAll("\\s+","").equals("")){
            jTextField19.putClientProperty("JComponent.outline", "error");
            jLabel181.setText("Username is empty!");
        }
        if(!jPasswordField2.getText().equals(jPasswordField3.getText())){
            jLabel182.setText("Password is not match!");
            jPasswordField2.putClientProperty("JComponent.outline", "warning");
            jPasswordField3.putClientProperty("JComponent.outline", "warning");
            isFailed = true;
        }

        if(jPasswordField2.getText().replaceAll("\\s+","").equals("")){
            jLabel182.setText("Password is empty!");
            jPasswordField2.putClientProperty("JComponent.outline", "error");
            isFailed = true;
        }
        if(jPasswordField3.getText().replaceAll("\\s+","").equals("")){
            jLabel182.setText("Password is empty!");
            jPasswordField3.putClientProperty("JComponent.outline", "error");
            isFailed = true;
        }

        if(jTextField21.getText().replaceAll("\\s+","").equals("")){
            jLabel182.setText("Username is empty!");
            jTextField21.putClientProperty("JComponent.outline", "error");
            isFailed = true;
        }
        if(us.isUsernameExist(jTextField21.getText()) && !this.user.getUsername().equals(jTextField21.getText())){
            jTextField21.putClientProperty("JComponent.outline", "error");
            jLabel182.setText("Username already exist!");
            isFailed = true;
        }
        
        if(this.user.getUsername().equals(jTextField19.getText()) && this.user.getPassword().equals(jPasswordField1.getText())){
            if(!isFailed){
                this.user.setUsername(jTextField21.getText());
                this.user.setPassword(jPasswordField2.getText());
                us.updateUser(this.user.getId(), this.user);
            }
            
        }else{
            jLabel181.setText("Username and Password not match!");
        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser(); 
        String choosertitle = "";
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION && barcodeImage != null) { 
//            System.out.println("getCurrentDirectory(): " 
//               +  chooser.getCurrentDirectory());
//            System.out.println("getSelectedFile() : " 
//               +  chooser.getSelectedFile());
            
            System.out.println(chooser.getSelectedFile().toString() + "/barcode");


            
            try {
                ImageIO.write(barcodeImage, "jpg", new File(chooser.getSelectedFile().toString() + "/barcode" + jTextField15.getText()+".jpg"));
//                ImageIO.write(barcodeImage, "PNG", chooser.getSelectedFile().getAbsoluteFile());
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_jButton34ActionPerformed
//    boolean exitHover = false;
    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        // TODO add your handling code here:
        FlatSVGIcon exiticon = new FlatSVGIcon("img/icon/door-open-solid.svg", 25, 25);
        exiticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(196, 43, 28);
            }
        }));
        jLabel29.setIcon(exiticon);
        
        jLabel28.setForeground(new Color(196, 43, 28));
        
//        exitHover = true;
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        // TODO add your handling code here:
        FlatSVGIcon exiticon = new FlatSVGIcon("img/icon/door-open-solid.svg", 25, 25);
        exiticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel29.setIcon(exiticon);
        
        jLabel28.setForeground(Color.gray);
//        exitHover = false;
    }//GEN-LAST:event_jPanel7MouseExited

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(PromptResult==JOptionPane.YES_OPTION)
        {
            long currentHourWorked =  getDateDiff(new java.sql.Date(loginDateTime),
                   new java.sql.Date(Calendar.getInstance().getTime().getTime()),TimeUnit.MINUTES);
            UserService us = new UserService();
            
            int userWorkedHour = this.user.getHourWorked() + (int)currentHourWorked;
            
            this.user.setHourWorked(userWorkedHour);
            us.updateUser(this.user.getId(), this.user);
            
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        String ObjButtons[] = {"Yes", "Go to Login", "Cancel"};
        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
        if(PromptResult == 0){
            
            long currentHourWorked =  getDateDiff(new java.sql.Date(loginDateTime),
                   new java.sql.Date(Calendar.getInstance().getTime().getTime()),TimeUnit.MINUTES);
            UserService us = new UserService();
            
            int userWorkedHour = this.user.getHourWorked() + (int)currentHourWorked;
            
            this.user.setHourWorked(userWorkedHour);
            us.updateUser(this.user.getId(), this.user);
            
            System.exit(0);
        }else if(PromptResult == 1)
        {
            long currentHourWorked =  getDateDiff(new java.sql.Date(loginDateTime),
                   new java.sql.Date(Calendar.getInstance().getTime().getTime()),TimeUnit.MINUTES);
            UserService us = new UserService();
            
            int userWorkedHour = this.user.getHourWorked() + (int)currentHourWorked;
            
            this.user.setHourWorked(userWorkedHour);
            us.updateUser(this.user.getId(), this.user);
            
            this.dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
//            System.exit(0);
            
        }
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        barcodeFrame.setVisible(true);
        barcodeFrame.show();
        jPanel204.setVisible(false);
        jPanel209.setVisible(true);
    }//GEN-LAST:event_jButton31ActionPerformed

    public void updateCreateCustomerTicketService(){
        if(jComboBox11.getSelectedIndex() == 0){
            serviceCustomer = null;
            jTextField1.setText("No Selected");
        }else if(jComboBox11.getSelectedIndex() == 1){
            glassPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
            glassPanel.setVisible(true);
            
            CheckoutSelectCustomerPanel checkoutSelectCustomer = new CheckoutSelectCustomerPanel();
            Point loc = jComboBox11.getLocationOnScreen();
            
//            selectCustomerPopupPanel.setBounds((int)loc.getX(), (int)loc.getY(), 300, 270);
            selectCustomerPopupPanel.setBounds((int)loc.getX(), (int)loc.getY(), 300, 270);
            
            checkoutSelectCustomer.isProductCheckout(false);
            checkoutSelectCustomer.setMinimumSize(new Dimension(300, 270));
            checkoutSelectCustomer.setPreferredSize(new Dimension(300, 270));
            
            selectCustomerPopupPanel.removeAll();
            selectCustomerPopupPanel.add(checkoutSelectCustomer);
            selectCustomerPopupPanel.setVisible(true);
            updateGraphics();
//            jLayeredPane1.add(checkoutSelectCustomer, JLayeredPane.DRAG_LAYER);
            
        }else if(jComboBox11.getSelectedIndex() == 2){
//            createCustomerDialog.removeAll();
//            CreateNewCustomerPanel createCustomerPanel = new CreateNewCustomerPanel();
            createCustomerPanel.isForCustomerService(true);
//            createCustomerDialog.add(createCustomerPanel);
            createCustomerDialog.setVisible(true);
        }
    }
    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed
        // TODO add your handling code here:
        updateCreateCustomerTicketService();
        
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void glassPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_glassPanelMouseClicked
        // TODO add your handling code here:
//        glassPanel.setVisible(false);
        selectCustomerPopupPanel.setVisible(false);
        glassPanel.setVisible(false);
        updateGraphics();
        System.out.println("GlassClicked");
    }//GEN-LAST:event_glassPanelMouseClicked

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
        
        UIManager.put("Button.arc", 18);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Component.arc", 18);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(3, 3, 3, 3));
        

//        UIManager.put( "ScrollBar.trackArc", 999 );
//        UIManager.put( "ScrollBar.thumbArc", 999 );
//        UIManager.put( "ScrollBar.trackInsets", new Insets( 0, 0, 0, 0 ) );
//        UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
//        UIManager.put( "ScrollBar.track", null );
//        UIManager.put( "Button.innerFocusWidth", 1 );
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
                updateGraphics();
            }
        });
    }
    
    public void updatePersonnelData(){
        
        jPanel218.removeAll();
        
        List<User> userList = new ArrayList<>();
        UserService us = new UserService();
        userList = us.getAllUserDetails();

        int personnelActive = 0;
        int personnelDeactivated = 0;
        int personnelTotal = userList.size();
        
        
            for(int i = 0; i < userList.size(); i++) {

                PersonnelThumb personelThumb = new PersonnelThumb();
                personelThumb.setUserData(userList.get(i));

                if(userList.get(i).getId() == this.user.getId()){
                    personelThumb.isMyAccount();
                }

                if(userList.get(i).getStatus() == 2){
                    personelThumb.setVisible(false);
                }else{
                    
                    if(jComboBox2.getSelectedIndex() == 0){
                        jPanel218.add(personelThumb);
                    }else if(jComboBox2.getSelectedIndex() == 1){
                        if(userList.get(i).getRole().equals("employee")){
                            jPanel218.add(personelThumb);
                        }
                    }else if(jComboBox2.getSelectedIndex() == 2){
                        if(userList.get(i).getRole().equals("admin")){
                            jPanel218.add(personelThumb);
                        }
                    }
                    

                    if(userList.get(i).getStatus() == 1){
                        personnelActive++;
                    }else{
                        personnelDeactivated++;
                    }
                }
            }
            System.out.println(jComboBox2.getSelectedIndex());
            personnelTotal = personnelActive + personnelDeactivated;

            jLabel255.setText(String.valueOf(personnelActive));
            jLabel256.setText(String.valueOf(personnelDeactivated));
            jLabel268.setText(String.valueOf(personnelTotal));
        

        
        
    }
    
    public void closePopup() {
        if (jCheckBoxMenuItem1.isSelected()) {
            if (!closePopupActive) {
                closePopupActive = true;
                popupPanel.setBounds(0, 0, getWidth(), getHeight());
                
                blurBGPanel.setVisible(false);
                timer = new Timer(1, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        popupPanel.setBounds(popupPanel.getX() + 50, 0, getWidth(), getHeight());
                        
                        if (popupPanel.getX() > getWidth()) {
                            popupPanel.setBounds(getWidth(), getHeight(), getWidth(), getHeight());
                            popupPanel.setVisible(false);
                            closePopupActive = false;
                            timer.stop();
                        }
                    }
                });
                timer.start();
                isPopupShowed = false;
                
                updateGraphics();
            }
        } else {
            popupPanel.setBounds(getWidth(), 0, getWidth(), getHeight());
            popupPanel.setVisible(false);
            blurBGPanel.setVisible(false);
        }
        
        
        if(selectedMenu == personnelMainMenu){
            updatePersonnelData();
        }else if(selectedMenu == customerMainMenu){
            updateCustomerData();
        }else if(selectedMenu == ticketMainMenu || selectedMenu == productMainMenu){
            updateProductListPanel();
        }
        
    }
    
    public void showPopup(Component comp) {
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
        if (jCheckBoxMenuItem1.isSelected()) {
            JFrame frame = this;
            popupPanel.setBounds(frame.getWidth(), 0, frame.getWidth(), frame.getHeight());
            timer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    popupPanel.setBounds(popupPanel.getX() - 50, 0, frame.getWidth(), frame.getHeight());
                    
                    if (popupPanel.getX() < 0) {
                        popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                        updateGraphics();
                        timer.stop();
                    }
                }
            });
            timer.start();
        } else {
            JFrame frame = this;
            popupPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        }
        
        isPopupShowed = true;
        
    }
    
    public void showCategory() {
        //Category test UI Database
        
        if (ticketMainMenu == selectedMenu) {
            jPanel19.removeAll();
        } else {
            jPanel164.removeAll();
        }
        
        CategoryService categoryService = new CategoryService();
        
        List<String> type = new ArrayList();
        List<Category> categoryList = new ArrayList<>();
        ProductService ps = new ProductService();
        
        categoryList = categoryService.getAllCategory();
        
        categoryList.forEach(e -> {
            if(ps.getProductById(e.getProduct_id()).getStatus().equals("active")){
                type.add(e.getType());
            }
        });
        
        List<String> sortedType = removeDuplicates(type);
        
        sortedType.forEach(e -> {
            CategorySectionPanel categoryPanel = new CategorySectionPanel();
            categoryPanel.setCategoryType(e);
            
            HashMap<String, Integer> brands = new HashMap<>();
            
            categoryService.getAllCategory().forEach(e2 -> {
                if(ps.getProductById(e2.getProduct_id()).getStatus().equals("active")){
                    
                    if (e2.getType().equals(e)) {

                        if (brands.isEmpty()) {
                            brands.put(e2.getBrand(), 1);
                            System.out.println("Empty until: " + e2.getBrand());
                        } else {

                            if (brands.get(e2.getBrand()) != null) {
                                brands.put(e2.getBrand(), brands.get(e2.getBrand()) + 1);
                            } else {
                                brands.put(e2.getBrand(), 1);
                            }

    //                        for (String i : brands.keySet()) {
    //                            if(i.equals(e2.getBrand())){
    //                                brands.put(i, brands.get(i) + 1);
    //                                System.out.println("Equals: " + i + " Value: " + String.valueOf(brands.get(i)));
    //                            }else{
    //                                System.out.println("Added new set");
    //                                brands.put(e2.getBrand(), 1);
    //                            }
    //                        }
                        }
                        System.out.println("Category: " + e + " brands: " + brands.toString());
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
            System.out.println("Category: " + e + " brands: " + brands.toString());
            
            if (ticketMainMenu == selectedMenu) {
                jPanel19.add(categoryPanel);
            } else {
                jPanel164.add(categoryPanel);
            }
        });
        
        updateGraphics();
    }
    
    public void resetCheckoutPayment() {
        
        checkoutCashThumb.setBackground(contentPanel.getBackground());
        checkoutCashThumb.putClientProperty(FlatClientProperties.STYLE, "arc: 25");
        
        FlatSVGIcon icon = new FlatSVGIcon("img/icon/credit-card-regular.svg", 22, 22);
        icon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(104, 104, 104);
            }
        }));
        checkoutCashIcon.setIcon(icon);
        if (darkRB.isSelected()) {
            checkoutCashIconCont.setBackground(new Color(50, 55, 64));
        } else {
            checkoutCashIconCont.setBackground(new Color(240, 240, 240));
        }
        
        checkoutCashIconCont.putClientProperty(FlatClientProperties.STYLE, "arc: 50");
        
        checkoutCreditThumb.setBackground(contentPanel.getBackground());
        checkoutCreditThumb.putClientProperty(FlatClientProperties.STYLE, "arc: 25");
        
        FlatSVGIcon icon2 = new FlatSVGIcon("img/icon/hand-holding-dollar-solid.svg", 22, 22);
        icon2.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(104, 104, 104);
            }
        }));
        checkoutCreditIcon.setIcon(icon2);
        
        if (darkRB.isSelected()) {
            checkoutCreditIconCont.setBackground(new Color(50, 55, 64));
        } else {
            checkoutCreditIconCont.setBackground(new Color(240, 240, 240));
        }
        
        checkoutCreditIconCont.putClientProperty(FlatClientProperties.STYLE, "arc: 50");
        
        if (isCashPayment) {
            if (!darkRB.isSelected()) {
                checkoutCashThumb.setBackground(new Color(240, 240, 240));
                checkoutCashIconCont.setBackground(contentPanel.getBackground());
            } else {
                checkoutCashThumb.setBackground(new Color(50, 55, 64));
                checkoutCashIconCont.setBackground(contentPanel.getBackground());
            }
        } else {
            if (!darkRB.isSelected()) {
                checkoutCreditThumb.setBackground(new Color(240, 240, 240));
                checkoutCreditIconCont.setBackground(contentPanel.getBackground());
            } else {
                checkoutCreditThumb.setBackground(new Color(50, 55, 64));
                checkoutCreditIconCont.setBackground(contentPanel.getBackground());
            }
        }
        
        updateGraphics();
        
    }
    
    public void resetMainMenu() {
        jLabel32.setForeground(Color.gray);
        jLabel40.setForeground(Color.gray);
        jLabel1.setForeground(Color.gray);
        jLabel42.setForeground(Color.gray);
        jLabel271.setForeground(Color.gray);
        jLabel58.setForeground(Color.gray);
        jLabel53.setForeground(Color.gray);
        
        FlatSVGIcon usericon = new FlatSVGIcon("img/icon/briefcase-solid.svg", 27, 27);
        usericon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel272.setIcon(usericon);
        
        FlatSVGIcon archiveicon = new FlatSVGIcon("img/icon/file-zipper-solid.svg", 23, 27);
        archiveicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel59.setIcon(archiveicon);
        
        FlatSVGIcon customericon = new FlatSVGIcon("img/icon/user-tag-solid.svg", 29, 25);
        customericon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel57.setIcon(customericon);
        
        
        FlatSVGIcon carticon = new FlatSVGIcon("img/icon/cart-icon.svg", 30, 30);
        carticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel33.setIcon(carticon);
//        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/cart-icon-gray.png")));
        FlatSVGIcon salesicon = new FlatSVGIcon("img/icon/sales-icon.svg", 30, 30);
        salesicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel41.setIcon(salesicon);
//        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/sales-icon-gray.png")));
        
        FlatSVGIcon charticon = new FlatSVGIcon("img/icon/chart-icon.svg", 30, 30);
        charticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel27.setIcon(charticon);
//        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/chart-icon-gray.png")));
        
        FlatSVGIcon producticon = new FlatSVGIcon("img/icon/product-icon.svg", 30, 30);
        producticon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(64, 64, 64);
            }
        }));
        jLabel43.setIcon(producticon);
        
        
        
        
    }
    
    public static void updateGraphics() {

//        this.requestFocusInWindow();    
//        refresh();
//        revalidate();
//        repaint();
        
        
        
        if(selectedMenu == ticketMainMenu){
            tabPanel.add(ticketMainPanelHeader);
            contentPanel.add(ticketMainPanel);
        }else if(selectedMenu == productMainMenu){
            tabPanel.add(productMainPanelHeader);
            contentPanel.add(productMainPanel);
        }
        
        if (darkRB.isSelected()) {
            UIManager.put("ScrollBar.thumb", new Color(74, 74, 74));
        } else {
            UIManager.put("ScrollBar.thumb", new Color(0xe0e0e0));
        }
        
        
        
        if (darkRB.isSelected()) {
            try {
                //                            FlatAtomOneDarkContrastIJTheme.install();
                UIManager.setLookAndFeel(new FlatAtomOneDarkContrastIJTheme());
                FlatAtomOneDarkContrastIJTheme.updateUI();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
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
    
    public void initStyle() {
        
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
        tabPanel.removeAll();
        tabPanel.add(ticketMainPanelHeader);
        ticketMainTabPane.putClientProperty("JTabbedPane.trailingComponent", tabPanel);
        ticketMainTabPane.putClientProperty("JTabbedPane.tabHeight", 40);
        
        ticketSearchBar.putClientProperty("JComponent.roundRect", true);
        ticketSearchBar.putClientProperty("JTextField.placeholderText", "Search:  Casio, Battery");
        ticketSearchBar.putClientProperty("JTextField.padding", new Insets(2, 9, 2, 9));
//        ticketSearchBar.putClientProperty("JTextField.trailingIcon", new javax.swing.ImageIcon(getClass().getResource("/img/icon/close-icon-gray.png")));
        
        ticketSearchBar1.putClientProperty("JComponent.roundRect", true);
        ticketSearchBar1.putClientProperty("JTextField.placeholderText", "Search:  Casio, Battery");
        ticketSearchBar1.putClientProperty("JTextField.padding", new Insets(2, 9, 2, 9));
        
//        ArrayList<String> items = new ArrayList<String>();
//        Locale[] locales = Locale.getAvailableLocales();
//        for (int i = 0; i < locales.length; i++) {
//            String item = locales[i].getDisplayName();
//            items.add(item);
//        }
//        //testSearchField
//        setupAutoComplete(ticketSearchBar, items);
        
        JButton ticketSearch = new JButton();
        ticketSearch.setPreferredSize(new Dimension(35, 35));
        ticketSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/search-icon-gray.png")));
//        testButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 9, 1));
        ticketSearchBar.putClientProperty("JTextField.trailingComponent", ticketSearch);
        ticketSearchBar1.putClientProperty("JTextField.trailingComponent", ticketSearch);
        
        salesHistoryTabPane.putClientProperty("JTabbedPane.trailingComponent", salesHistoryPanelHeader);
        salesHistoryTabPane.putClientProperty("JTabbedPane.tabHeight", 40);
        salesHistorySearchBar.putClientProperty("JComponent.roundRect", true);
        salesHistorySearchBar.putClientProperty("JTextField.placeholderText", "Search:  Customer, Product");
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
        ticketMainTabPane1.putClientProperty("JTabbedPane.trailingComponent", productMainPanelHeader);
        ticketMainTabPane1.putClientProperty("JTabbedPane.tabHeight", 40);
        
        archivedTabPane.putClientProperty("JTabbedPane.tabHeight", 40);
        
        jScrollPane33.putClientProperty( "JScrollBar.showButtons", true );
        
        jScrollPane28.putClientProperty( "JScrollBar.showButtons", true );
        
        
        jTextField25.putClientProperty("JTextField.placeholderText", "Search:  Customer Name");
        jTextField18.putClientProperty("JTextField.placeholderText", "Search:  Personnel Name");
        
        FlatSVGIcon closesearchicon = new FlatSVGIcon("img/icon/cancel_FILL1_wght400_GRAD0_opsz48.svg", 20, 20);
        closesearchicon.setColorFilter(new FlatSVGIcon.ColorFilter(new Function<Color, Color>() {
            public Color apply(Color t) {
                return new Color(168, 168, 168);
            }
        }));
        
        
        closeSearchLabel.setPreferredSize(new Dimension(25, 25));
        closeSearchLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeSearchLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jTextField25.isFocusOwner()){
                    jTextField25.setFocusable(false);
                    jTextField25.setFocusable(true);
                    
                    jTextField25.putClientProperty("JTextField.trailingComponent", null);
                }else if(jTextField18.isFocusOwner()){
                    jTextField18.setFocusable(false);
                    jTextField18.setFocusable(true);
                    
                    jTextField18.putClientProperty("JTextField.trailingComponent", null);
                }
//                thisFrame().requestFocusInWindow();
            }
            
        });
        
        closeSearchLabel.setIcon(closesearchicon);
//        jTextField18.putClientProperty("JTextField.trailingComponent", closeSearchLabel);
        jTextField18.putClientProperty("JTextField.padding", new Insets(2, 2, 2, 9));
        
//        jTextField25.putClientProperty("JTextField.trailingComponent", closeSearchLabel);
        jTextField25.putClientProperty("JTextField.padding", new Insets(2, 2, 2, 9));
        
//        jButton30.putClientProperty("Button.bordercolor", Color.red);

//        jTextField15.putClientProperty("JTextField.placeholderText", "Enter Barcode");
//        jTextField16.putClientProperty("JTextField.placeholderText", "Enter Barcode");
        
    }
    
    public JFrame thisFrame(){
        return this;
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
    private javax.swing.JLabel accountProfilePicture;
    private javax.swing.JLabel accountType;
    private javax.swing.JButton addProductOptionButton;
    private javax.swing.JButton addProductOptionButton1;
    public static javax.swing.JPanel addProductPanel;
    private javax.swing.JPanel adminMenuPanel;
    public static javax.swing.JPanel archiveMainMenu;
    private javax.swing.JPanel archiveMainPanel;
    private javax.swing.JTabbedPane archivedTabPane;
    public static javax.swing.JDialog barcodeFrame;
    private com.toedter.calendar.JDateChooser birthdateChooser;
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
    private javax.swing.JTextField checkoutCustomerAddressInput;
    private com.toedter.calendar.JDateChooser checkoutCustomerBirthDateInput;
    private javax.swing.JTextField checkoutCustomerContactInput;
    private javax.swing.JTextField checkoutCustomerNameInput;
    public static javax.swing.JPanel checkoutCustomerSelectedPanel;
    private javax.swing.ButtonGroup checkoutGenderButtonGroup;
    private javax.swing.JPanel checkoutProductPanel;
    public static javax.swing.JPanel checkoutThumbScrollPane;
    private javax.swing.JPanel closePopupIcon;
    private javax.swing.JLabel confirmTotalPrice;
    public static javax.swing.JPanel contentPanel;
    public static javax.swing.JDialog createCustomerDialog;
    private javax.swing.ButtonGroup customerGenderButtonGroup;
    public static javax.swing.JPanel customerMainMenu;
    private javax.swing.JPanel customerMainPanel;
    private javax.swing.JLabel customerRegisteredIcon;
    private javax.swing.JLabel customerUnregisteredIcon;
    public static javax.swing.JPanel customerViewDetailsPanel;
    public static javax.swing.JRadioButtonMenuItem darkRB;
    private javax.swing.JPanel dashboardHistogramPanel;
    public static javax.swing.JPanel dashboardMainMenu;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JPanel dashboardPanelHeader;
    private javax.swing.JTabbedPane dashboardTabPane;
    public static javax.swing.JComboBox<String> editProductBrand;
    public static javax.swing.JComboBox<String> editProductBrand1;
    public static javax.swing.JTextField editProductNameTextField;
    public static javax.swing.JTextField editProductNameTextField1;
    public static javax.swing.JPanel editProductPanel;
    public static javax.swing.JTextField editProductPriceField;
    public static javax.swing.JTextField editProductPriceField1;
    public static javax.swing.JSpinner editProductStockField;
    public static javax.swing.JSpinner editProductStockField1;
    public static javax.swing.JComboBox<String> editProductType;
    public static javax.swing.JComboBox<String> editProductType1;
    public static javax.swing.JPanel glassPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    public static javax.swing.JButton jButton14;
    public static javax.swing.JButton jButton15;
    public static javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    public static javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    public static javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    public static javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    public static javax.swing.JButton jButton40;
    public static javax.swing.JButton jButton41;
    private javax.swing.JButton jButton5;
    public static javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JCalendar jCalendar2;
    public static javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    public static javax.swing.JComboBox<String> jComboBox7;
    public static javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    public static com.toedter.calendar.JDateChooser jDateChooser4;
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
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    public static javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    public static javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel130;
    public static javax.swing.JLabel jLabel131;
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
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    public static javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel205;
    public static javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    public static javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    public static javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    public static javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel243;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel245;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel247;
    private javax.swing.JLabel jLabel248;
    private javax.swing.JLabel jLabel249;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel250;
    private javax.swing.JLabel jLabel251;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel254;
    private javax.swing.JLabel jLabel255;
    private javax.swing.JLabel jLabel256;
    private javax.swing.JLabel jLabel257;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel267;
    private javax.swing.JLabel jLabel268;
    private javax.swing.JLabel jLabel269;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel270;
    private javax.swing.JLabel jLabel271;
    private javax.swing.JLabel jLabel272;
    private javax.swing.JLabel jLabel273;
    public static javax.swing.JLabel jLabel274;
    public static javax.swing.JLabel jLabel275;
    private javax.swing.JLabel jLabel276;
    private javax.swing.JLabel jLabel277;
    private javax.swing.JLabel jLabel278;
    public static javax.swing.JLabel jLabel279;
    private javax.swing.JLabel jLabel28;
    public static javax.swing.JLabel jLabel280;
    public static javax.swing.JLabel jLabel281;
    private javax.swing.JLabel jLabel282;
    public static javax.swing.JLabel jLabel283;
    public static javax.swing.JLabel jLabel284;
    private javax.swing.JLabel jLabel285;
    private javax.swing.JLabel jLabel286;
    private javax.swing.JLabel jLabel287;
    private javax.swing.JLabel jLabel288;
    private javax.swing.JLabel jLabel289;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel290;
    public static javax.swing.JLabel jLabel291;
    private javax.swing.JLabel jLabel292;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel301;
    private javax.swing.JLabel jLabel302;
    private javax.swing.JLabel jLabel303;
    private javax.swing.JLabel jLabel304;
    private javax.swing.JLabel jLabel305;
    private javax.swing.JLabel jLabel306;
    private javax.swing.JLabel jLabel307;
    public static javax.swing.JLabel jLabel309;
    private javax.swing.JLabel jLabel31;
    public static javax.swing.JLabel jLabel310;
    private javax.swing.JLabel jLabel312;
    private javax.swing.JLabel jLabel313;
    private javax.swing.JLabel jLabel314;
    private javax.swing.JLabel jLabel315;
    private javax.swing.JLabel jLabel316;
    private javax.swing.JLabel jLabel317;
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
    public static javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    public static javax.swing.JLabel jLabel59;
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
    public static javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    public static javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    public static javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    public static javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    public static com.toedter.calendar.JMonthChooser jMonthChooser1;
    private com.toedter.calendar.JMonthChooser jMonthChooser2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel101;
    public static javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel111;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel114;
    private javax.swing.JPanel jPanel115;
    private javax.swing.JPanel jPanel116;
    public static javax.swing.JPanel jPanel117;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel120;
    private javax.swing.JPanel jPanel121;
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
    public static javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel150;
    private javax.swing.JPanel jPanel151;
    private javax.swing.JPanel jPanel152;
    private javax.swing.JPanel jPanel153;
    private javax.swing.JPanel jPanel154;
    private javax.swing.JPanel jPanel155;
    private javax.swing.JPanel jPanel156;
    private javax.swing.JPanel jPanel158;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel160;
    private javax.swing.JPanel jPanel161;
    private javax.swing.JPanel jPanel162;
    private javax.swing.JPanel jPanel164;
    public static javax.swing.JPanel jPanel165;
    private javax.swing.JPanel jPanel166;
    private javax.swing.JPanel jPanel167;
    private javax.swing.JPanel jPanel168;
    private javax.swing.JPanel jPanel169;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel170;
    private javax.swing.JPanel jPanel171;
    private javax.swing.JPanel jPanel172;
    private javax.swing.JPanel jPanel173;
    private javax.swing.JPanel jPanel174;
    private javax.swing.JPanel jPanel176;
    private javax.swing.JPanel jPanel178;
    private javax.swing.JPanel jPanel179;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel180;
    public static javax.swing.JPanel jPanel181;
    private javax.swing.JPanel jPanel182;
    private javax.swing.JPanel jPanel183;
    private javax.swing.JPanel jPanel184;
    private javax.swing.JPanel jPanel185;
    private javax.swing.JPanel jPanel186;
    private javax.swing.JPanel jPanel187;
    public static javax.swing.JPanel jPanel188;
    private javax.swing.JPanel jPanel189;
    private javax.swing.JPanel jPanel19;
    public static javax.swing.JPanel jPanel190;
    private javax.swing.JPanel jPanel191;
    private javax.swing.JPanel jPanel192;
    private javax.swing.JPanel jPanel193;
    private javax.swing.JPanel jPanel194;
    private javax.swing.JPanel jPanel195;
    public static javax.swing.JPanel jPanel196;
    private javax.swing.JPanel jPanel197;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel203;
    private javax.swing.JPanel jPanel204;
    private javax.swing.JPanel jPanel205;
    private javax.swing.JPanel jPanel206;
    private javax.swing.JPanel jPanel207;
    private javax.swing.JPanel jPanel208;
    private javax.swing.JPanel jPanel209;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel210;
    private javax.swing.JPanel jPanel211;
    private javax.swing.JPanel jPanel212;
    private javax.swing.JPanel jPanel213;
    private javax.swing.JPanel jPanel214;
    private javax.swing.JPanel jPanel215;
    private javax.swing.JPanel jPanel216;
    private javax.swing.JPanel jPanel217;
    private javax.swing.JPanel jPanel218;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel221;
    private javax.swing.JPanel jPanel223;
    public static javax.swing.JPanel jPanel226;
    private javax.swing.JPanel jPanel227;
    private javax.swing.JPanel jPanel229;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel230;
    private javax.swing.JPanel jPanel231;
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
    public static javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    public static javax.swing.JPanel jPanel64;
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
    public static javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    public static javax.swing.JPanel jPanel90;
    public static javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    public static javax.swing.JPanel jPanel97;
    public static javax.swing.JPanel jPanel98;
    private javax.swing.JPanel jPanel99;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    public static javax.swing.JRadioButton jRadioButton7;
    public static javax.swing.JRadioButton jRadioButton8;
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
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane33;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    public static javax.swing.JSpinner jSpinner4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    public static javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    public static javax.swing.JTextField jTextField17;
    public static javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField24;
    public static javax.swing.JTextField jTextField25;
    public static javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    public static javax.swing.JTextField jTextField8;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    public static com.toedter.calendar.JYearChooser jYearChooser1;
    public static com.toedter.calendar.JYearChooser jYearChooser2;
    private com.toedter.calendar.JYearChooser jYearChooser3;
    private javax.swing.JRadioButtonMenuItem lightRB;
    private javax.swing.JPanel mainHeaderPanel;
    private javax.swing.JLabel menuHeader;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel myProfilePanel;
    private javax.swing.JDialog notificationDialog;
    private javax.swing.JPanel notificationPanel;
    public static javax.swing.JTable orderHistorySelectedTable;
    private javax.swing.JLabel personelActiveIcon;
    private javax.swing.JLabel personelDeactiveIcon;
    private javax.swing.JLabel personelDeactiveIcon1;
    private javax.swing.JLabel personelDeactiveIcon3;
    private javax.swing.JPanel personelMainPanel;
    public static javax.swing.JPanel personnelMainMenu;
    public static javax.swing.JPanel personnelViewDetailsPanel;
    private javax.swing.JPanel popupClosePanel;
    public static javax.swing.JPanel popupContentPanel;
    public static javax.swing.JPanel popupPanel;
    private javax.swing.JPanel productCategoryPanel;
    private javax.swing.JPanel productCategoryPanel1;
    public static javax.swing.JLabel productImage;
    public static javax.swing.JLabel productImage1;
    public static javax.swing.JLabel productImage2;
    public static javax.swing.JPanel productMainMenu;
    public static javax.swing.JPanel productMainPanel;
    public static javax.swing.JPanel productMainPanelHeader;
    private javax.swing.JPanel receiptDataSection;
    private javax.swing.JPanel ringChartPanel;
    private javax.swing.JPanel salesHistoryPanel;
    private javax.swing.JPanel salesHistoryPanelHeader;
    private javax.swing.JTextField salesHistorySearchBar;
    private javax.swing.JTabbedPane salesHistoryTabPane;
    public static javax.swing.JPanel salesMainMenu;
    private javax.swing.ButtonGroup salesPersonDetailsBG;
    public static javax.swing.JPanel searchPanel;
    public static javax.swing.JPanel searchPanel2;
    public static javax.swing.JPanel selectCustomerPopupPanel;
    private javax.swing.JPanel selectedProductThumbPanel1;
    private javax.swing.JPanel serviceThumb;
    private javax.swing.JPanel serviceThumbExpand;
    private javax.swing.JTextField storeAddressInput;
    private javax.swing.JTextField storeBranchInput;
    private javax.swing.JTextField storeContactNumInput;
    private javax.swing.JTextField storeEmailInput;
    private javax.swing.JTextField storeNameInput;
    private javax.swing.ButtonGroup themesButtonGroup;
    public static javax.swing.JPanel ticketMainMenu;
    public static javax.swing.JPanel ticketMainPanel;
    public static javax.swing.JPanel ticketMainPanelHeader;
    private javax.swing.JTabbedPane ticketMainTabPane;
    private javax.swing.JTabbedPane ticketMainTabPane1;
    private javax.swing.JTextField ticketSearchBar;
    private javax.swing.JTextField ticketSearchBar1;
    private javax.swing.JLabel topProductName;
    private javax.swing.JLabel topServiceName;
    private javax.swing.JPanel unregisteredCustomerListPanel;
    private javax.swing.JPanel unregisteredCustomerPanel;
    private javax.swing.JLabel userAddress;
    private javax.swing.JTextField userAddressInput;
    private javax.swing.JLabel userAge;
    private javax.swing.JTextField userAgeInput;
    private javax.swing.JLabel userGender;
    private javax.swing.JComboBox<String> userGenderCB;
    private javax.swing.JTextField userNameInput;
    private javax.swing.JLabel usersName;
    public static javax.swing.JLabel viewProductName;
    public static javax.swing.JPanel viewProductPanel;
    public static javax.swing.JLabel viewProductPrice;
    public static javax.swing.JLabel viewProductSelectedVariants;
    public static javax.swing.JTable viewProductVariantTable;
    // End of variables declaration//GEN-END:variables
}
