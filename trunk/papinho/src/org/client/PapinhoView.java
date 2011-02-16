package org.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.common.model.ChatMessage;

/**
 * The application's main frame.
 */
public class PapinhoView extends FrameView {

    public PapinhoView(SingleFrameApplication app) {
        super(app);

        initComponents();
        bSend.setEnabled(false);
        mDisconnect.setVisible(false);
        setTitle("Papinho");

        privateChats = new HashMap<String, PrivateChatView>();

        getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getFrame().addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                mDisconnectActionPerformed(null);
                PapinhoApp.getApplication().exit();
            }
        });
    }

    public final void setTitle(String title) {
        getFrame().setTitle(title);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PapinhoApp.getApplication().getMainFrame();
            aboutBox = new PapinhoAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PapinhoApp.getApplication().show(aboutBox);
    }

    public PapinhoClient getClient() {
        return client;
    }

    public void setClient(PapinhoClient client) {
        this.client = client;
    }

    public JButton getbSend() {
        return bSend;
    }

    public void appendMessage(ChatMessage msg) {
        appendString("<");
        appendString(msg.getName(), msg.getName());
        appendString("> ");
        appendString(msg.getMessage() + "\n");
    }

    public void appendPrivateMessage(ChatMessage msg, String to) {
        PrivateChatView pc = getPrivateChatFrame(to);
        pc.appendString("<");
        pc.appendString(msg.getName(), msg.getName());
        pc.appendString("> ");
        pc.appendString(msg.getMessage() + "\n");
    }

    public void appendString(String str) {
        appendString(StyleContext.DEFAULT_STYLE, str);
    }

    public void appendString(String styleName, String str) {
        try {
            Style style = taOutput.getStyle(styleName);
            Document doc = taOutput.getDocument();
            doc.insertString(doc.getLength(), str, style);
            taOutput.setCaretPosition(doc.getLength());
        } catch (BadLocationException blex) {
            System.out.println(blex.getMessage());
        }
    }

    public void appendClient(String name) {
        DefaultListModel model = (DefaultListModel) lUserList.getModel();
        model.addElement(name);
        Random r = new Random();
        Style style = taOutput.addStyle(name, null);
        StyleConstants.setForeground(style, new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
    }

    public void changeUserName(String oldName, String newName) {
        DefaultListModel model = (DefaultListModel) lUserList.getModel();
        Style oldStyle = taOutput.getStyle(oldName);
        taOutput.addStyle(newName, oldStyle);
        int i = model.indexOf(oldName);
        if (i >= 0) {
            model.set(i, newName);
        }
    }

    public void removeClient(String name) {
        DefaultListModel model = (DefaultListModel) lUserList.getModel();
        taOutput.removeStyle(name);
        for (int i = 0; i < model.getSize(); i++) {
            if (((String) model.get(i)).equals(name)) {
                model.remove(i);
                break;
            }
        }
    }

    public void purgeClientList() {
        DefaultListModel model = (DefaultListModel) lUserList.getModel();
        model.clear();
    }

    public JMenuItem getmConnect() {
        return mConnect;
    }

    public void setmConnect(JMenuItem mConnect) {
        this.mConnect = mConnect;
    }

    public JMenuItem getmDisconnect() {
        return mDisconnect;
    }

    public void setmDisconnect(JMenuItem mDisconnect) {
        this.mDisconnect = mDisconnect;
    }

    public JTextArea getTaInput() {
        return taInput;
    }

    public JTextPane getTaOutput() {
        return taOutput;
    }

    

    /**
     * 
     * @param user The name of the user the private chat is with
     * @return A reference to the PrivateChatView that corresponds to the private chat with user
     */
    public PrivateChatView getPrivateChatFrame(String user) {
        PrivateChatView pcf = privateChats.get(user);
        boolean isNew = false;
        if (pcf == null) {
            pcf = new PrivateChatView(user, client, getSelf());
            privateChats.put(user, pcf);
            isNew = true;
        }
        /*If the frame did not exist or if it was not visible, it is displayed and
        focussed*/
        if (isNew || !pcf.isVisible()) {
            pcf.setVisible(true);
            pcf.toFront();
            pcf.setState(JFrame.NORMAL);
        }
        return pcf;
    }

    private void ShowUlistPopup(java.awt.event.MouseEvent e) {
        if (e.isPopupTrigger()) {
            lUserList.setSelectedIndex(lUserList.locationToIndex(e.getPoint()));
            if (!lUserList.getSelectedValue().toString().equals(client.getName())) {
                JPopupMenu popup = new JPopupMenu();
                final String to = lUserList.getSelectedValue().toString();
                JMenuItem menuItem = new JMenuItem("Initiate a private chat with "
                        + to);
                menuItem.setMnemonic('p');
                menuItem.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        appendString("Opening a private chat window with"
                                + lUserList.getSelectedValue() + "\n");
                        getPrivateChatFrame(to);
                    }
                });
                popup.add(menuItem);
                popup.show(lUserList, e.getX(), e.getY());
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        taOutput = new javax.swing.JTextPane();
        spInputScroll = new javax.swing.JScrollPane();
        taInput = new javax.swing.JTextArea();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        lUserList = new javax.swing.JList();
        bSend = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        mConnect = new javax.swing.JMenuItem();
        mDisconnect = new javax.swing.JMenuItem();
        mSettings = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        jSplitPane1.setDividerLocation(450);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setName("jSplitPane2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        taOutput.setEditable(false);
        taOutput.setName("taOutput"); // NOI18N
        jScrollPane1.setViewportView(taOutput);

        jSplitPane2.setTopComponent(jScrollPane1);

        spInputScroll.setAutoscrolls(true);
        spInputScroll.setName("spInputScroll"); // NOI18N

        taInput.setColumns(20);
        taInput.setLineWrap(true);
        taInput.setName("taInput"); // NOI18N
        taInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taInputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                taInputKeyReleased(evt);
            }
        });
        spInputScroll.setViewportView(taInput);

        jSplitPane2.setRightComponent(spInputScroll);

        jSplitPane1.setLeftComponent(jSplitPane2);

        jSplitPane3.setDividerLocation(300);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setName("jSplitPane3"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        lUserList.setModel(new DefaultListModel());
        lUserList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lUserList.setName("lUserList"); // NOI18N
        lUserList.setSelectedIndex(0);
        lUserList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lUserListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lUserListMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(lUserList);

        jSplitPane3.setTopComponent(jScrollPane3);

        bSend.setMnemonic('S');
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.client.PapinhoApp.class).getContext().getResourceMap(PapinhoView.class);
        bSend.setText(resourceMap.getString("bSend.text")); // NOI18N
        bSend.setName("bSend"); // NOI18N
        bSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSendActionPerformed(evt);
            }
        });
        jSplitPane3.setRightComponent(bSend);

        jSplitPane1.setRightComponent(jSplitPane3);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        mConnect.setMnemonic('C');
        mConnect.setText(resourceMap.getString("mConnect.text")); // NOI18N
        mConnect.setName("mConnect"); // NOI18N
        mConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mConnectActionPerformed(evt);
            }
        });
        fileMenu.add(mConnect);

        mDisconnect.setMnemonic('D');
        mDisconnect.setText(resourceMap.getString("mDisconnect.text")); // NOI18N
        mDisconnect.setName("mDisconnect"); // NOI18N
        mDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDisconnectActionPerformed(evt);
            }
        });
        fileMenu.add(mDisconnect);

        mSettings.setMnemonic('S');
        mSettings.setText(resourceMap.getString("mSettings.text")); // NOI18N
        mSettings.setName("mSettings"); // NOI18N
        mSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSettingsActionPerformed(evt);
            }
        });
        fileMenu.add(mSettings);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.client.PapinhoApp.class).getContext().getActionMap(PapinhoView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private PapinhoView getSelf() {
        return this;
    }

    private void mConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mConnectActionPerformed
        ConnectView cv = new ConnectView(this);
        cv.setVisible(true);
    }//GEN-LAST:event_mConnectActionPerformed

    private void bSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSendActionPerformed
        if (taInput.getText().length() > 0) {
            client.sendMessage(client.getName(), taInput.getText());
            taInput.setText("");
        }
    }//GEN-LAST:event_bSendActionPerformed

    private void mDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDisconnectActionPerformed
        PapinhoApp.getApplication().releaseRemoteServerObject();
        taOutput.setText("");
        taInput.setText("");
        mDisconnect.setVisible(false);
        mConnect.setVisible(true);
        taInput.setEnabled(false);
        bSend.setEnabled(false);
        for (JFrame frame : privateChats.values()) {
            frame.dispose();
        }
        privateChats.clear();
}//GEN-LAST:event_mDisconnectActionPerformed

    private void mSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSettingsActionPerformed
        OptionsView ov = new OptionsView(client, this);
        ov.setVisible(true);
}//GEN-LAST:event_mSettingsActionPerformed

    private void taInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taInputKeyReleased
        if (this.taInput.getText().length() == 0) {
            bSend.setEnabled(false);//le bouton envoyer devient grise
        } else {
            bSend.setEnabled(true);
        }
    }//GEN-LAST:event_taInputKeyReleased

    private void taInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taInputKeyPressed
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_ENTER) {
            taInput.append("\n");
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            bSendActionPerformed(null);
            evt.setKeyCode(java.awt.event.KeyEvent.VK_UNDEFINED);
        }
    }//GEN-LAST:event_taInputKeyPressed

    private void lUserListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lUserListMousePressed
        ShowUlistPopup(evt);
    }//GEN-LAST:event_lUserListMousePressed

    private void lUserListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lUserListMouseReleased
        ShowUlistPopup(evt);
    }//GEN-LAST:event_lUserListMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JList lUserList;
    private javax.swing.JMenuItem mConnect;
    private javax.swing.JMenuItem mDisconnect;
    private javax.swing.JMenuItem mSettings;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane spInputScroll;
    private javax.swing.JTextArea taInput;
    private javax.swing.JTextPane taOutput;
    // End of variables declaration//GEN-END:variables
    private PapinhoClient client;
    private JDialog aboutBox;
    private Map<String, PrivateChatView> privateChats;
}
