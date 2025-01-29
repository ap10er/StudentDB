package ui;

import dao.GroupDAO;
import dao.StudentDAO;

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
        JButton viewStudentsButton = new JButton("Просмотреть студентов"); // Новая кнопка

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewStudentsButton); // Добавляем кнопку

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGroup());
        editButton.addActionListener(e -> editGroup());
        deleteButton.addActionListener(e -> deleteGroup());
        viewStudentsButton.addActionListener(e -> viewStudents()); // Обработчик для новой кнопки
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<model.Group> groups = groupDAO.getAllGroups();
        for (model.Group group : groups) {
            tableModel.addRow(new Object[]{group.getId(), group.getGroupNumber(), group.getFacultyName()});
        }
    }

    private void addGroup() {
        AddGroupDialog dialog = new AddGroupDialog(this);
        dialog.setVisible(true);

        model.Group newGroup = dialog.getGroup();
        if (newGroup != null) {
            groupDAO.addGroup(newGroup);
            loadData();
        }
    }

    private void editGroup() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите группу для редактирования", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        String groupNumber = (String) tableModel.getValueAt(selectedRow, 1);
        String facultyName = (String) tableModel.getValueAt(selectedRow, 2);

        model.Group selectedGroup = new model.Group(id, groupNumber, facultyName);
        EditGroupDialog dialog = new EditGroupDialog(this, selectedGroup);
        dialog.setVisible(true);

        model.Group updatedGroup = dialog.getUpdatedGroup(selectedGroup);
        if (updatedGroup != null) {
            groupDAO.updateGroup(updatedGroup);
            loadData();
        }
    }

    private void deleteGroup() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            groupDAO.deleteGroup(id);
            loadData();
        }
    }

    private void viewStudents() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите группу для просмотра студентов", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long groupId = (Long) tableModel.getValueAt(selectedRow, 0);
        new StudentListFrame(groupId).setVisible(true); // Открываем окно списка студентов
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GroupListFrame().setVisible(true));
    }
}