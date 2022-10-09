/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_timesquare.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import pos_timesquare.model.Variants;
import static pos_timesquare.view.MainFrame.addProductVariants;
import static pos_timesquare.view.MainFrame.jPanel45;
import static pos_timesquare.view.MainFrame.jPanel47;
import static pos_timesquare.view.MainFrame.productVariants;
import static pos_timesquare.view.MainFrame.variantCombination;

/**
 *
 * @author Acer
 */
public class AddProductOptionPanel extends JPanel {
    
    public int count = -1;
    public List<String> inputs = new ArrayList<>();
    
    JPanel jPanel48 = new JPanel();
    JPanel jPanel53 = new JPanel();
    JPanel jPanel55 = new JPanel();
    JPanel jPanel54 = new JPanel();
    JPanel jPanel56 = new JPanel();
    JPanel jPanel44 = new JPanel();
    JPanel jPanel52 = new JPanel();
    JPanel jPanel46 = new JPanel();
//    JPanel jPanel47 = new JPanel();
    
    JLabel jLabel52 = new JLabel();
    JLabel jLabel53 = new JLabel();
    JLabel jLabel66 = new JLabel();
    JLabel jLabel67 = new JLabel();
    
    JButton jButton10 = new JButton();
    JTextField jTextField4 = new JTextField();
    JTextField jTextField5 = new JTextField();

    
    
    
    public AddProductOptionPanel(){
        
        
//        jPanel52.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
//        jPanel56.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
//        jPanel44.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
//        
//        this.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(234, 234, 234)));
        this.setPreferredSize(new java.awt.Dimension(313, 160));
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel52.setText("Variant Name");
        jLabel52.setPreferredSize(new java.awt.Dimension(64, 25));

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        this.add(jPanel53);

        jTextField4.setPreferredSize(new java.awt.Dimension(60, 20));

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        this.add(jPanel55);

        jPanel54.setPreferredSize(new java.awt.Dimension(105, 20));

        jLabel66.setText("Variant Value");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        this.add(jPanel54);

        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.PAGE_AXIS));

        jTextField5.setPreferredSize(new java.awt.Dimension(60, 35));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

//        jPanel56.add(jPanel44);

        this.add(jPanel56);

        jPanel52.setPreferredSize(new java.awt.Dimension(146, 57));

        jButton10.setText("Done");

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/trash-icon-gray.png"))); // NOI18N

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        this.add(jPanel52);
        
//        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
//        jPanel46.setLayout(jPanel46Layout);
//        jPanel46Layout.setHorizontalGroup(
//            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
//            .addComponent(this, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
//        );
//        jPanel46Layout.setVerticalGroup(
//            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel46Layout.createSequentialGroup()
//                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(0, 0, 0)
//                .addComponent(this, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
//        );

//        jTextField5.getDocument().addDocumentListener(new DocumentListener() {
//            int id = count + 1;
//            boolean newTextFieldShowed = false;
//
//            
//            public void changedUpdate(DocumentEvent e) {
//              warn();
//            }
//            public void removeUpdate(DocumentEvent e) {
//              warn();
//            }
//            public void insertUpdate(DocumentEvent e) {
//              warn();
//            }
//
//            public void warn(){
//                if(inputs.size() <= id){
//                    count = count + 1;
//                    System.out.println("Add list");
//                    inputs.add("");
//                }
//                inputs.set(id, jTextField5.getText());
//                
//                if(jTextField5.getText().equals("")){
//                    System.out.println("Empty");
//                }else{
//                    if(!newTextFieldShowed){
//                        newTextFieldShowed = true;
//                        jPanel56.add(new NewTextField());
//                        
//                        
//                        repaint();
//                        revalidate();
//                    }
//                }
//            }
//
//        });
        jPanel56.add(new NewTextField());
        
        
        jButton10.addActionListener(new ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                if(!jTextField4.getText().replaceAll("\\s+","").equals("")){
                    if(addProductVariants.containsKey(jTextField4.getText())){

//                        inputs.forEach(e -> {
//                            if(addProductVariants.get(jTextField4.getText()).contains(e)){
//                                System.out.println("Element already exist!");
//                            }else{
//                                List<String> temp = new ArrayList<>();
//                                temp = addProductVariants.get(jTextField4.getText());
//                                temp.add(e);
//                                addProductVariants.put(jTextField4.getText(), temp);
//                                System.out.println("Element added!");
//                            }
//                        });

                        inputs.removeAll(Collections.singleton(null));
                        inputs.removeAll(Collections.singleton(""));
//                        for(int i = 0; i < inputs.size(); i++){
//                            System.out.println("input: " + inputs.get(i));
////                            if(inputs.get(i).equals("") || inputs.get(i) == null){
////                                inputs.remove(i);
////                            }
//                        }
                        
                        addProductVariants.put(jTextField4.getText(), inputs);
                        
                    }else{
                        List<String> temp = new ArrayList<>();
                        addProductVariants.put(jTextField4.getText(), inputs);
                        System.out.println("Variant Element added!");
                    }
                }
                
                System.out.println(addProductVariants);
                List<List<String>> lists = new ArrayList<>();

                
                for (Map.Entry<String, List<String>> entry : addProductVariants.entrySet()) {
                    if(!entry.getKey().equals("generated")){
                        lists.add(entry.getValue());
                    }
                }
                
//                System.out.println(temp);
//                System.out.println(addProductVariants.containsKey(jTextField4.getText()));

//                List<String> l1 = Arrays.asList("red", "black");
//                List<String> l2 = Arrays.asList("gold", "silver");
//                List<Integer> l3 = Arrays.asList(6, 7);

                
//                lists.add(l3);

                jPanel47.removeAll();
                variantCombination = getCombinations(lists);
                List<Variants> tempProductVariants = new ArrayList<>();
                for (List<String> list : variantCombination) {
//                    System.out.println(list.toString());
                    boolean noMatch = true;
                    String str = String.join("/", list);
                    
                    for(int j = 0; j < productVariants.size(); j++){
                        Variants tempVariant = new Variants();
                        if(str.equals(productVariants.get(j).getName())){
                            EditProductTableRow tableRow = new EditProductTableRow();
                            tableRow.setVariantName(str);
                            tableRow.setVariantPrice(productVariants.get(j).getPrice());
                            tableRow.setVariantStocks(productVariants.get(j).getStocks());
                            tableRow.setVariantBarcode(productVariants.get(j).getBarcode());
                            jPanel47.add(tableRow);
                            
                            tempVariant.setName(str);
                            tempVariant.setPrice(productVariants.get(j).getPrice());
                            tempVariant.setStocks(productVariants.get(j).getStocks());
                            tempVariant.setBarcode(productVariants.get(j).getBarcode());
                            tempProductVariants.add(tempVariant);
                            
                            noMatch = false;
                            System.out.println("Row match");
                            break;
                        }else if(str.contains(productVariants.get(j).getName())){
                            EditProductTableRow tableRow = new EditProductTableRow();
                            tableRow.setVariantName(str);
                            tableRow.setVariantPrice(productVariants.get(j).getPrice());
                            tableRow.setVariantStocks(productVariants.get(j).getStocks());
                            tableRow.setVariantBarcode(productVariants.get(j).getBarcode());
                            jPanel47.add(tableRow);
                            
                            tempVariant.setName(str);
                            tempVariant.setPrice(productVariants.get(j).getPrice());
                            tempVariant.setStocks(productVariants.get(j).getStocks());
                            tempVariant.setBarcode(productVariants.get(j).getBarcode());
                            tempProductVariants.add(tempVariant);
                            
                            noMatch = false;
                            System.out.println("Row Contains");
                            break;
                        }
                    };
                    
                    if(noMatch){
                        
                        EditProductTableRow tableRow = new EditProductTableRow();
                        tableRow.setVariantName(str);
                        jPanel47.add(tableRow);
                        
                        Variants tempVariant = new Variants();
                        tempVariant.setName(str);
                        tempProductVariants.add(tempVariant);
                    }
                    
                    revalidate();
                    repaint();
                }
                
//                productVariants.clear();
                productVariants = tempProductVariants;
                
                tempProductVariants.forEach(e -> {
                    System.out.println("Product Variants Update");
                    System.out.println(e.getName());
                    System.out.println(e.getPrice());
                });
                
                initProductOptions();
                
            }
        });

        jTextField4.putClientProperty("JTextField.placeholderText", "Color");
        jTextField5.putClientProperty("JTextField.placeholderText", "Black");
        
        
//        setLayout(new BorderLayout());
//        add(jPanel48);
    }
    
    
    
    
    public void initProductOptions(){
        this.removeAll();
        ProductOptionPanel productOption = new ProductOptionPanel();
//        System.out.println(jTextField4.getText());
//        System.out.println(addProductVariants.get(jTextField4.getText()));
        productOption.setType(jTextField4.getText());
        productOption.setValue(addProductVariants.get(jTextField4.getText()));
        
        this.add(productOption);
        
        revalidate();
        repaint();
    }
    
    public void initVariants(String name, List<String> value){
        jTextField4.setText(name);
        this.jPanel56.removeAll();
//        this.count = -1;
//        inputs = value;
        value.forEach(e -> {
            this.jPanel56.add(new NewTextField(e));
        });
        this.jPanel56.add(new NewTextField());

    }
    
    
    public static <T> Set<List<T>> getCombinations(List<List<T>> lists) {
        Set<List<T>> combinations = new HashSet<List<T>>();
        Set<List<T>> newCombinations;

        int index = 0;

        // extract each of the integers in the first list
        // and add each to ints as a new list
        for (T i : lists.get(0)) {
            List<T> newList = new ArrayList<T>();
            newList.add(i);
            combinations.add(newList);
        }
        index++;
        while (index < lists.size()) {
            List<T> nextList = lists.get(index);
            newCombinations = new HashSet<List<T>>();
            for (List<T> first : combinations) {
                for (T second : nextList) {
                    List<T> newList = new ArrayList<T>();
                    newList.addAll(first);
                    newList.add(second);
                    newCombinations.add(newList);
                }
            }
            combinations = newCombinations;
            index++;
        }
        return combinations;
    }
    
    public HashMap<String, List<String>> getVariants(){
        HashMap<String, List<String>> variants = new HashMap<>();
        List<String> variantList = new ArrayList<>();
        inputs.forEach(e -> {
            if(!e.equals("")) variantList.add(e);
        });
        variants.put(jTextField4.getText(), variantList);

        
        return variants;
    }
    
    class NewTextField extends JPanel{
        
        JTextField jTextField = new JTextField();
        
        public void setTextField(String value){
            jTextField.setText(value);
        }
        int id = count + 1;

        NewTextField(){

//            this.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
            this.setPreferredSize(new java.awt.Dimension(60, 35));
            jTextField.setPreferredSize(new java.awt.Dimension(60, 35));

            javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(this);
            this.setLayout(jPanel55Layout);
            jPanel55Layout.setHorizontalGroup(
                jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel55Layout.setVerticalGroup(
                jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
            
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
                boolean newTextFieldShowed = false;
                
                public void changedUpdate(DocumentEvent e) {
                  warn();
                }
                public void removeUpdate(DocumentEvent e) {
                  warn();
                }
                public void insertUpdate(DocumentEvent e) {
                  warn();
                }

                public void warn(){
                    System.out.println("ID: " + id);
                    System.out.println("Count: " + count);
                    System.out.println(inputs);
                    
                    if(inputs.size() <= id){
                        count = count + 1;
                        System.out.println("Add list");
                        inputs.add("");
                    }
                    inputs.set(id, jTextField.getText());
                    
                    if(jTextField.getText().equals("")){
                        System.out.println("Empty");
//                        inputs.remove(id);
                    }else{
                        if(!newTextFieldShowed){
                            newTextFieldShowed = true;
                            jPanel56.add(new NewTextField());
//                            jPanel48.setPreferredSize(new java.awt.Dimension(313, 160));
                            repaint();
                            revalidate();
                        }
                    }
                }

            });
            
        }
        
        NewTextField(String value){
            
//            this.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(234, 234, 234)));
            this.setPreferredSize(new java.awt.Dimension(60, 35));
            jTextField.setPreferredSize(new java.awt.Dimension(60, 35));
            
            jTextField.setText(value);

//            int id = count + 1;
//            if(inputs.size() <= id){
//                count = count + 1;
//                System.out.println("Add list");
//                inputs.add("");
//            }
//            
//            inputs.set(id, jTextField.getText());
            count = count + 1;
            inputs.add(jTextField.getText());
            
            javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(this);
            this.setLayout(jPanel55Layout);
            jPanel55Layout.setHorizontalGroup(
                jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel55Layout.setVerticalGroup(
                jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            );
            
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
                boolean newTextFieldShowed = false;
//                int id = count + 1;
                
                public void changedUpdate(DocumentEvent e) {
                  warn();
                }
                public void removeUpdate(DocumentEvent e) {
                  warn();
                }
                public void insertUpdate(DocumentEvent e) {
                  warn();
                }

                public void warn(){
                    System.out.println("ID: " + id);
                    System.out.println("Count: " + count);
                    System.out.println(inputs);

//                    if(inputs.size() <= id){
//                        count = count + 1;
//                        System.out.println("Add list");
//                        inputs.add("");
//                    }
                    inputs.set(id, jTextField.getText());
                    
                    if(jTextField.getText().equals("")){
                        System.out.println("Empty");
//                        inputs.remove(id);
                    }else{
//                        if(!newTextFieldShowed){
//                            newTextFieldShowed = true;
//                            jPanel56.add(new NewTextField());
//                            repaint();
//                            revalidate();
//                        }
                    }
                }

            });
            
        }
    }
}
