package org.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

        getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getFrame().addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                mDisconnectActionPerformed(null);
                PapinhoApp.getApplication().exit();
            }
        });

        taInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    taInput.append("\n");
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bSendActionPerformed(null);
                    e.setKeyCode(java.awt.event.KeyEvent.VK_UNDEFINED);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
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
        Random r = new Random();
        appendString("<");
        appendString(msg.getName(), msg.getName());
        appendString("> ");
        appendString(msg.getMessage() + "\n");
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
            //taOutput.setCharacterAttributes(style, true);
            //taOutput.replaceSelection(str);
            //     Rectangle r = taOutput.modelToView(doc.getLength());
            //    if(r!=null){
            //       taOutput.scrollRectToVisible(r);
            //  }
            
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
        taInput.setRows(5);
        taInput.setName("taInput"); // NOI18N
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
}//GEN-LAST:event_mDisconnectActionPerformed

    private void mSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSettingsActionPerformed
        OptionsView ov = new OptionsView(client, this);
        ov.setVisible(true);
}//GEN-LAST:event_mSettingsActionPerformed
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
}