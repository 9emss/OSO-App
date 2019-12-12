/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oso_app;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author gemss
 */
public class produk extends javax.swing.JFrame {

    config conf = new config();
    private Object [][] dataproduk = null;
    private String[] label = {"ID Produk", "Nama Produk", "Unit Cost", "ID Supplier"};
    /**
     * Creates new form 
     */
    public produk() {
        initComponents();
        conf.configDB();
        nonaktif();
        bacaTableProduct();
        isiNamaKat();
        isiNamaSupp();
        fKategori.setVisible(false);
        fSupplier.setVisible(false);
    }

    void isiNamaKat(){
        
        try{
            String sql = "Select * from kategori";
            conf.rs = conf.st.executeQuery(sql);
            while(conf.rs.next()){
                cKategori.addItem(conf.rs.getString("n_kategori"));
            }   
        }catch (SQLException e){
            System.out.println("Error kategori");
        }
        
    }
    
    void isiNamaSupp(){
        
        try{
            
            String sql = "Select * From supplier";
            conf.rs = conf.st.executeQuery(sql);
            while(conf.rs.next()){
                
                cSupplier.addItem(conf.rs.getString("n_supplier"));
                
            }
        }catch (SQLException e){
            System.out.println("Error Supplier" + e.toString());    
        }
        
    }
    
    private String idProduct(){
        
        String no = null;
        
        try{
            conf.configDB();
            String sql = "Select right(id_produk,3)+1 from produk";
            ResultSet rs = conf.st.executeQuery(sql);
            
            if(rs.next()){
                
                rs.last();
                no = rs.getString(1);
                while(no.length() < 3){
                    no = "00" + no;
                    no = "B" + no;
                    fIdProduct.setText(no);
                }
                
            }else {
                
                no = "B001";
                fIdProduct.setText(no);
                
            }
            
        }catch (Exception e){
            
            System.out.println("Error ID Product");
        
        }
        
        return no;
    }
    
    private void bacaTableProduct(){
        
        try{
            
            String sql = "Select * from produk order by id_produk";
            conf.rs = conf.st.executeQuery(sql);
            ResultSetMetaData rmd = conf.rs.getMetaData();
            
            int kolom = rmd.getColumnCount();
            int baris = 0;
            
            while(conf.rs.next()){
                
                baris = conf.rs.getRow();
                
            }
            
            dataproduk = new Object[baris][kolom];
            int x = 0;
            conf.rs.beforeFirst();
            
            while(conf.rs.next()){
                dataproduk[x][0] = conf.rs.getString("id_produk");
                dataproduk[x][1] = conf.rs.getString("id_kategori");
                dataproduk[x][2] = conf.rs.getString("n_produk");
                dataproduk[x][3] = conf.rs.getString("unicost");
                dataproduk[x][4] = conf.rs.getString("id_supplier");
                x++;
            }
            
            tbProduct.setModel(new DefaultTableModel(dataproduk,label));
            
        }catch (Exception e){
            System.out.println("Error Table");
        }
        
    }
    
    private void bacaTableProduct2(){
        
        try{
            
            String sql = "Select * from product where n_product like '%"+ fCari.getText() +"%'";
            conf.rs = conf.st.executeQuery(sql);
            ResultSetMetaData rmd = conf.rs.getMetaData();
            int kolom = rmd.getColumnCount();
            int baris = 0;
            
            while(conf.rs.next()){
                baris = conf.rs.getRow();
            }
            
            dataproduk = new Object[baris][kolom];
            int x = 0;
            conf.rs.beforeFirst();
            
            while(conf.rs.next()){
                dataproduk[x][0] = conf.rs.getString("id_produk");
                dataproduk[x][1] = conf.rs.getString("id_kategori");
                dataproduk[x][2] = conf.rs.getString("n_produk");
                dataproduk[x][3] = conf.rs.getString("unicost");
                dataproduk[x][4] = conf.rs.getString("id_supplier");
                
                x++;
            }
            
            tbProduct.setModel(new DefaultTableModel(dataproduk, label));
            
        }catch(Exception e){
            System.out.println("Error cari");
        }
        
    }
    
    void isiNamaKat2(){
        
        try{
            
            String slq = "Select * From kategori where id_kategori = '"+ fKategori.getText() +"'";
            conf.rs = conf.st.executeQuery(slq);
            if(conf.rs.next()){
                
                cKategori.setSelectedItem(conf.rs.getString("n_kategori"));
                
            }
            
        }catch(SQLException e){
            System.out.println("Error Kategori 2");
        }
        
    }
    
   void isiNamaSupp2(){
       
       try{
           
           String sql = "Select * from supplier where id_supplier = '"+ fSupplier.getText() +"'";
           conf.rs = conf.st.executeQuery(sql);
           if(conf.rs.next()){
               cSupplier.setSelectedItem(conf.rs.getString("n_supplier"));
           }
           
       }catch(SQLException e){
           System.out.println("Error Supplier 2");
       }
       
   }
   
   private void setTable(){
       int row = tbProduct.getSelectedRow();
       fIdProduct.setText((String)tbProduct.getValueAt(row, 0));
       fKategori.setText((String)tbProduct.getValueAt(row, 1));
       fProduct.setText((String)tbProduct.getValueAt(row, 2));
       fUnicost.setText((String)tbProduct.getValueAt(row, 3));
       fSupplier.setText((String)tbProduct.getValueAt(row, 4));
   }
   
   private void clear(){
       fIdProduct.setText("");
       fKategori.setText("");
       fProduct.setText("");
       fUnicost.setText("");
       fSupplier.setText("");
       fCari.setText("");       
   }
   
   private void aktif(){
       fIdProduct.setEnabled(true);
       fProduct.setEnabled(true);
       fUnicost.setEnabled(true);
       cKategori.setEnabled(true);
       cSupplier.setEnabled(true);
   }
   
   private void nonaktif(){
       fIdProduct.setEnabled(false);
       fProduct.setEnabled(false);
       fUnicost.setEnabled(false);
       cKategori.setEnabled(false);
       cSupplier.setEnabled(false);
       btEdit.setEnabled(false);
       btUpdate.setEnabled(false);
       btDelete.setEnabled(false);
       btSave.setEnabled(false);
   }
   
   private void saveData(){
        
       try{
           
           String sql = "insert into produk values('"+ fIdProduct.getText() +"', '"+ fKategori.getText() +"', '"+ fProduct.getText() +"', '"+ fUnicost.getText() +"', '"+ fSupplier.getText() +"')";
           conf.st.executeQuery(sql);
           JOptionPane.showMessageDialog(null, "Data Tersimpan");
           clear();
           bacaTableProduct();
       }catch(SQLException e){
           
           System.out.println("Error Save Data");
           
       }
       
   }
   
   private void editData(){
       try{
           String sql = "Update produk set id_produk = '"+ fIdProduct.getText() +"', id_kategori'"+ fKategori.getText() +"', id_supplier = '"+ fSupplier.getText() +"', n_produk = '"+ fProduct.getText() +"', unicost = '"+ fUnicost.getText() +"' where id_produk = '"+ fIdProduct.getText() +"'";
           conf.st.executeUpdate(sql);
           JOptionPane.showMessageDialog(null,"Data Berhasil Diubah");
           clear();
           bacaTableProduct();
           conf.st.close();
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
           
       }
   }
   
   private void hapusData(){
       try{
           String sql = "Delete from produk where id_produk = '"+ fIdProduct.getText() +"'";
           conf.st.executeUpdate(sql);
           clear();
           bacaTableProduct();
           conf.st.close();
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
       }
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fIdProduct = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cKategori = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cSupplier = new javax.swing.JComboBox<>();
        fProduct = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fUnicost = new javax.swing.JTextField();
        fKategori = new javax.swing.JTextField();
        fSupplier = new javax.swing.JTextField();
        btAdd = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        fCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProduct = new javax.swing.JTable();
        btExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Product"));

        jLabel1.setText("ID Product");

        jLabel2.setText("Kategori");

        cKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-=PILIH=-" }));
        cKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cKategoriActionPerformed(evt);
            }
        });

        jLabel3.setText("Supplier");

        jLabel4.setText("Product");

        cSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-=PILIH=-" }));
        cSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cSupplierActionPerformed(evt);
            }
        });

        jLabel5.setText("Unicost");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(71, 71, 71)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cSupplier, 0, 176, Short.MAX_VALUE)
                                .addComponent(cKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fIdProduct))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fKategori)
                                .addComponent(fSupplier)))
                        .addComponent(fProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fUnicost, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fIdProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fUnicost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 520, -1));

        btAdd.setText("Tambah");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });
        getContentPane().add(btAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 68, -1));

        btSave.setText("Simpan");
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, -1, -1));

        btEdit.setText("Edit");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        getContentPane().add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 68, -1));

        btUpdate.setText("Update");
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, -1, -1));

        btCancel.setText("Batal");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, 81, -1));

        btDelete.setText("Hapus");
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, 89, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Product"));

        jLabel6.setText("Cari");

        fCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fCariActionPerformed(evt);
            }
        });

        tbProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProduct);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(fCari, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(fCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 520, -1));

        btExit.setText("Exit");
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });
        getContentPane().add(btExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 670, 92, -1));

        setBounds(0, 0, 572, 736);
    }// </editor-fold>//GEN-END:initComponents

    private void fCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fCariActionPerformed
        // TODO add your handling code here:
        conf.configDB();
        bacaTableProduct2();
    }//GEN-LAST:event_fCariActionPerformed

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btExitActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        clear();
        nonaktif();
    }//GEN-LAST:event_formWindowActivated

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        // TODO add your handling code here:
        idProduct();
        aktif();
        btCancel.setEnabled(true);
        btAdd.setEnabled(false);
        btSave.setEnabled(true);
    }//GEN-LAST:event_btAddActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        // TODO add your handling code here:
        if(fIdProduct.getText().isEmpty() || fProduct.getText().isEmpty() || 
                fUnicost.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Lengkapi Data", "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
            btAdd.setEnabled(true);
        }else{
            btAdd.setEnabled(true);            
            btExit.setEnabled(true); 
            saveData();
            cKategori.setSelectedItem("-=PILIH=-");
            cSupplier.setSelectedItem("-=PILIH=-");
            try{
                conf.st.close();
            }catch(SQLException e){
                Logger.getLogger(produk.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
    }//GEN-LAST:event_btSaveActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        aktif();
        fIdProduct.setEnabled(false);
        btEdit.setEnabled(false);
        btUpdate.setEnabled(true);
        btDelete.setEnabled(false);
        btAdd.setEnabled(false);
    }//GEN-LAST:event_btEditActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        // TODO add your handling code here:
        btEdit.setEnabled(false);        
        btAdd.setEnabled(true);
        editData();
        cKategori.setSelectedItem("-=PILIH=-");
        cSupplier.setSelectedItem("-=PILIH=-");
    }//GEN-LAST:event_btUpdateActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:
        nonaktif();
        clear();
        btAdd.setEnabled(true);
        cKategori.setSelectedItem("-=PILIH=-");
        cSupplier.setSelectedItem("-=PILIH=-");
        try{
            conf.st.close();
        }catch(SQLException e){
            Logger.getLogger(produk.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btCancelActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(this, "Yakin Mau Dihapus?", "Konfirmasi",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            hapusData();
            btAdd.setEnabled(true);
            nonaktif();
            clear();
            cKategori.setSelectedItem("-=PILIH=-");
            cSupplier.setSelectedItem("-=PILIH=-");
        }else{
            JOptionPane.showMessageDialog(this, "Data Terhapus", "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
            nonaktif();
            clear();
            cKategori.setSelectedItem("-=PILIH=-");
            cSupplier.setSelectedItem("-=PILIH=-");
            
            return;
        }
        formWindowActivated(null);
    }//GEN-LAST:event_btDeleteActionPerformed

    private void cKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cKategoriActionPerformed
        // TODO add your handling code here:
        try{
            conf.configDB();
            String sql = "Select * from kategori where n_kategori = '"+ cKategori.getSelectedItem() +"'";
            conf.rs = conf.st.executeQuery(sql);
            if(conf.rs.next()){
                fKategori.setText(conf.rs.getString("id_kategori"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal: " + e.toString());
            
        }
    }//GEN-LAST:event_cKategoriActionPerformed

    private void cSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cSupplierActionPerformed
        // TODO add your handling code here:
        try{
            conf.configDB();
            String sql = "Select * from supplier where n_supplier = '"+ cSupplier.getSelectedItem() +"'";
            conf.rs = conf.st.executeQuery(sql);
            if(conf.rs.next()){
                fSupplier.setText(conf.rs.getString("id_supplier"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal: " + e.toString());
            
        }
    }//GEN-LAST:event_cSupplierActionPerformed

    private void tbProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductMouseClicked
        // TODO add your handling code here:
        setTable();
        isiNamaKat2();
        isiNamaSupp2();
        btDelete.setEnabled(true);
        btEdit.setEnabled(true);
        btAdd.setEnabled(false);
    }//GEN-LAST:event_tbProductMouseClicked

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
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new produk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btExit;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btUpdate;
    private javax.swing.JComboBox<String> cKategori;
    private javax.swing.JComboBox<String> cSupplier;
    private javax.swing.JTextField fCari;
    private javax.swing.JTextField fIdProduct;
    private javax.swing.JTextField fKategori;
    private javax.swing.JTextField fProduct;
    private javax.swing.JTextField fSupplier;
    private javax.swing.JTextField fUnicost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbProduct;
    // End of variables declaration//GEN-END:variables
}
