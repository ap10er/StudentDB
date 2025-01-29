package ui;

import dao.GroupDAO;
import model.Group;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GroupListFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private final GroupDAO groupDAO;

    public GroupListFrame() {
        groupDAO = new GroupDAO();
        setTitle("Список групп");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Номер группы", "Факультет"}, 0);
        table = new JTable(tableModel);

        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Изменить");
        JButton deleteButton = new JButton("Удалить");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGroup());
        editButton.addActionListener(e -> editGroup());
        deleteButton.addActionListener(e -> deleteGroup());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Group> groups = groupDAO.getAllGroups();
        for (Group group : groups) {
            tableModel.addRow(new Object[]{group.getId(), group.getGroupNumber(), group.getFacultyName()});
        }
    }

    private void addGroup() {
        // Открытие модального окна для добавления группы
    }

    private void editGroup() {
        // Открытие модального окна для редактирования группы
    }

    private void deleteGroup() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            groupDAO.deleteGroup(id);
            loadData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GroupListFrame().setVisible(true));
    }
}