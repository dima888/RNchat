package client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

public class ChatApplication extends javax.swing.JFrame {

    //**************************** ATTRIBUTE **********************************
    private Client userClient;
    private Map<String, String> usersMap = new HashMap<>();
    private boolean loggedIn = false;
    private final int MAX_LENGTH_OF_LINE = 100; //Anzahl Zeichen in einer Zeile
    
    //*************************** KONSTRUKTOR *********************************
    public ChatApplication() {
        initComponents();
        //Client Objekt erzeugen und diese GUI übergeben
        userClient = new Client(this);
        
        //Bereich beim Start der GUI deaktivieren --> aktiviert, sobald eingeloggt
        chatArea(false);
        
        //Automatisches "Scrollen" aktivieren
        //TODO: TESTEN DIESER FUNKTIONEN
        receivedMessageTextArea.setAutoscrolls(true);
        usersTextArea.setAutoscrolls(true);
    }
    
    //************************** PUBLIC METHODEN ******************************
    /**
     * Setzt die UsersMap auf die übergebene
     * @param Map<String, String> usersMap
     */
    public void setUsers(Map<String, String> usersMap) {
        //Aktuelle Map der angemeldeten Users beim Server
        this.usersMap = usersMap;
       
        //Nachdem die aktuelle UsersMap eingetroffen ist, soll die GUI aktualisiert werden
        refreshGUI();
    }
    
    /**
     * Getter für die Map mit Usern und Hostnames
     * @return 
     */
    public Set<String> getHostnames() {
        return usersMap.keySet();
    }
    
    /**
     * Stellt den GUI bereich auf eingeloggt um
     */
    public void userAccepted() {
        loggedIn = true;
        //Login bereich ausgrauen
        loginArea(false);
        //Chat bereich zur Verfügung stellen
        chatArea(true);
        //startet die beiden anderen Arbeitsthreads (b und c)
        userClient.startJob();
    }
    
    /**
     * Stellt den GUI bereich auf nicht eingeloggt ein
     */
    public void userDeclined(String reason) {
        JOptionPane.showMessageDialog(null, reason);
    }
    
    /**
     * Zeigt die erhaltene Nachricht von einem Benutzer in der GUI an
     * @param String message  - Nachricht von einem Benutzer
     */
    public void showReceivedMessage(String message) {
        receivedMessageTextArea.append(message + "\n");
    }
    
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
            java.util.logging.Logger.getLogger(ChatApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatApplication().setVisible(true);
            }
        });
    }
    
    //*********************** PRIVATE METHODEN ********************************
    /**
     * Setzt die Zugriffserlaubnis auf den Chatbereich
     * @param boolean bool - bei true Chatbereich aktiviert, sonst nicht 
     */
    private void chatArea(boolean bool) {
        this.receivedMessageTextArea.setEnabled(bool);
        this.sendMessageTextField.setEnabled(bool);
        this.usersTextArea.setEnabled(bool);
        this.sendButton.setEnabled(bool);
        if(bool == true) {
            //FOKUS auf Sendeleiste setzten
            this.sendMessageTextField.requestFocus();
        }
    }
    
    /**
     * Setzt die Zugriffserlaubnis auf den Loginbereich
     * @param boolean bool - bei true Loginbereich aktiviert, sonst nicht 
     */
    private void loginArea(boolean bool) {
        this.userNameTextField.setEnabled(bool);
        this.ipTextField.setEnabled(bool);
        this.loginButton.setEnabled(bool);
        if (bool == true) {
            //FOKUS auf userNamen setzten
            this.userNameTextField.requestFocus();
        }
    }
    
    /**
     * Aktualisiert die Liste der aktuell eingeloggten Users in der GUI
     */
    private void refreshGUI() {
        //Usernamen extrahieren
        Collection<String> userNames = usersMap.values();
        //resetet den Inhalt um nicht duppliziert anzuzeigen
        usersTextArea.setText("");
        
        //Alle Usernamen hinzufügen
        for(String userName : userNames) {
            usersTextArea.append(userName + "\n");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sendMessageTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        receivedMessageTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersTextArea = new javax.swing.JTextArea();
        receivedMessageLabel = new javax.swing.JLabel();
        usersLabel = new javax.swing.JLabel();
        sendMessageLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        userNameLabel = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        ipLabel = new javax.swing.JLabel();
        ipTextField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        leftCharsLabel = new javax.swing.JLabel();
        counterOfTypedCharsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat-Anwendung");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        sendMessageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageTextFieldActionPerformed(evt);
            }
        });
        sendMessageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sendMessageTextFieldKeyPressed(evt);
            }
        });

        receivedMessageTextArea.setEditable(false);
        receivedMessageTextArea.setColumns(20);
        receivedMessageTextArea.setRows(5);
        jScrollPane1.setViewportView(receivedMessageTextArea);

        usersTextArea.setEditable(false);
        usersTextArea.setColumns(20);
        usersTextArea.setRows(5);
        jScrollPane2.setViewportView(usersTextArea);

        receivedMessageLabel.setText("Erhaltene Nachrichten");

        usersLabel.setText("Teilnehmer");

        sendMessageLabel.setText("Zu sendende Nachricht");

        sendButton.setText("Senden");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        userNameLabel.setText("Username:");

        userNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameTextFieldActionPerformed(evt);
            }
        });

        ipLabel.setText("Hostname:");

        ipTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipTextFieldActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        leftCharsLabel.setText("Übrige Zeichen: ");

        counterOfTypedCharsLabel.setText("100");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addComponent(sendMessageTextField)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(receivedMessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(userNameLabel)
                                                .addGap(18, 18, 18)
                                                .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(92, 92, 92)
                                                .addComponent(ipLabel)
                                                .addGap(18, 18, 18)
                                                .addComponent(ipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 74, Short.MAX_VALUE)))
                                .addGap(19, 19, 19))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sendMessageLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(leftCharsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(counterOfTypedCharsLabel)
                                .addGap(21, 21, 21))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(sendButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usersLabel)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ipLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userNameLabel)
                        .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loginButton)
                        .addComponent(ipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(receivedMessageLabel)
                    .addComponent(usersLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendMessageLabel)
                            .addComponent(leftCharsLabel)
                            .addComponent(counterOfTypedCharsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendMessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sendMessageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageTextFieldActionPerformed
        sendButtonActionPerformed(evt);
    }//GEN-LAST:event_sendMessageTextFieldActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String input = sendMessageTextField.getText();
        //Nur abschicken, wenn nicht leer
        if(! input.isEmpty()) {
            userClient.sendMessage(input);
            sendMessageTextField.setText("");
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        //eingegebenen Usernamen holen
        String userName = userNameTextField.getText();
        String ip = ipTextField.getText();
        userClient.connect(userName, ip);
    }//GEN-LAST:event_loginButtonActionPerformed

    private void userNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameTextFieldActionPerformed
        loginButtonActionPerformed(evt);
    }//GEN-LAST:event_userNameTextFieldActionPerformed

    private void ipTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTextFieldActionPerformed
        loginButtonActionPerformed(evt);
    }//GEN-LAST:event_ipTextFieldActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //Wird aufgerufen when die GUI geschlossen wird --> BYE AN SERVER
        userClient.interruptThreads();
    }//GEN-LAST:event_formWindowClosing

    private void sendMessageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sendMessageTextFieldKeyPressed
        //Anzeigen, wieviel Zeichen noch zur Verfügung stehen für diese Nachricht
        counterOfTypedCharsLabel.setText( "" + (MAX_LENGTH_OF_LINE - sendMessageTextField.getText().length()) );
        
        //Falls die Maximale Anzahl an Zeichen erreicht ist, darf nichts weiteres eingegeben werden
        if(sendMessageTextField.getText().length() >= MAX_LENGTH_OF_LINE) {
            sendMessageTextField.setEditable(false);
        }
    }//GEN-LAST:event_sendMessageTextFieldKeyPressed

    //****************** GENERIERTE ATTRIBUTE *****************************
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel counterOfTypedCharsLabel;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JTextField ipTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel leftCharsLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel receivedMessageLabel;
    private javax.swing.JTextArea receivedMessageTextArea;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel sendMessageLabel;
    private javax.swing.JTextField sendMessageTextField;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JTextField userNameTextField;
    private javax.swing.JLabel usersLabel;
    private javax.swing.JTextArea usersTextArea;
    // End of variables declaration//GEN-END:variables
}
