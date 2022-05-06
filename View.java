package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.javarush.task.task32.task3209.MenuHelper.*;

public class View extends JFrame implements ActionListener {
    private Controller controller;

    private JTabbedPane tabbedPane = new JTabbedPane(); // панель с двумя вкладками
    private JTextPane htmlTextPane = new JTextPane(); // компоненет для визуального редактирования html. н будет на первой вкладке.
    private JEditorPane plainTextPane = new JEditorPane(); // компонент для рекактирования html dв виде текста, он будет отображать код html (теги и их содержимое)

    private UndoManager undoManager = new UndoManager();

    public UndoListener getUndoListener() {
        return undoListener;
    }

    private UndoListener undoListener = new UndoListener(undoManager);

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void undo() {
        try {
            undoManager.undo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void redo() {
        try {
            undoManager.redo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo() {
        undoManager.discardAllEdits();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if(command.equals("Новый")) controller.createNewDocument();
        else if (command.equals("Открыть")) controller.openDocument();
        else if (command.equals("Сохранить")) controller.saveDocument();
        else if (command.equals("Сохранить как...")) controller.saveDocumentAs();
        else if (command.equals("Сохранить как...")) controller.saveDocumentAs();
        else if (command.equals("Выход")) controller.exit();
        else if (command.equals("О программе")) showAbout();
    }

    public void init() {
        initGui();
        addWindowListener(new FrameListener(this));
        setVisible(true);
    }

    public void exit() {
        controller.exit();
    }

    public void initMenuBar() {
        //инициализация меню
        JMenuBar menuBar = new JMenuBar();

        initFileMenu(this, menuBar);
        initEditMenu(this, menuBar);
        initStyleMenu(this, menuBar);
        initAlignMenu(this, menuBar);
        initColorMenu(this, menuBar);
        initFontMenu(this, menuBar);
        initHelpMenu(this, menuBar);

        getContentPane().add(menuBar, BorderLayout.NORTH);

    }

    public void initEditor() {
        //инициализация панелей редактора
        htmlTextPane.setContentType("text/html");
        JScrollPane htmlScrollPane = new JScrollPane(htmlTextPane);
        tabbedPane.add("HTML", htmlScrollPane);
        JScrollPane plainScrollPane = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст", plainScrollPane);
        tabbedPane.setPreferredSize(new Dimension(300, 300));
        TabbedPaneChangeListener tabbedPaneChangeListener = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(tabbedPaneChangeListener);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void initGui() { //инициализация графического интерфейса
        initMenuBar();
        initEditor();
        pack();
    }

    public void selectedTabChanged() {
        int numberTab = tabbedPane.getSelectedIndex();

        if(numberTab == 0){
            controller.setPlainText(plainTextPane.getText());
        } else {
            plainTextPane.setText(controller.getPlainText());
        }

        resetUndo();
    }

    public boolean isHtmlTabSelected() {
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void selectHtmlTab() {
        //выбор HTML вкладки
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public void update() {
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(this,
                "Пробный редактор. Просьба не ругаться на разработчика.",
                "О программе...",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
