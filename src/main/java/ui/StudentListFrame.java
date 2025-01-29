package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class StudentListFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private StudentDAO studentDAO;
    private Long groupId;

    private JTextField lastNameFilterField; // Поле для фильтрации по фамилии
    private JTextField groupNumberFilterField; // Поле для фильтрации по номеру группы

    public StudentListFrame(Long groupId) {
        this.groupId = groupId;
        studentDAO = new StudentDAO();
        setTitle("Список студентов");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData(null, null); // Загружаем данные без фильтрации
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Имя", "Фамилия", "Отчество", "Дата рождения", "ID группы"}, 0);
        table = new JTable(tableModel);

        // Панель для фильтрации
        JPanel filterPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтр"));

        filterPanel.add(new JLabel("Фамилия:"));
        lastNameFilterField = new JTextField();
        filterPanel.add(lastNameFilterField);

        filterPanel.add(new JLabel("Номер группы:"));
        groupNumberFilterField = new JTextField();
        filterPanel.add(groupNumberFilterField);

        JButton applyFilterButton = new JButton("Применить");
        filterPanel.add(applyFilterButton);

        add(filterPanel, BorderLayout.NORTH);

        // Кнопки управления
        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Изменить");
        JButton deleteButton = new JButton("Удалить");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        // Обработчик для кнопки "Применить"
        applyFilterButton.addActionListener(e -> applyFilters());
    }

    private void loadData(String lastNameFilter, String groupNumberFilter) {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getFilteredStudents(groupId, lastNameFilter, groupNumberFilter);
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getMiddleName(),
                    student.getBirthDate(),
                    student.getGroupId()
            });
        }
    }

    private void applyFilters() {
        String lastNameFilter = lastNameFilterField.getText().trim();
        String groupNumberFilter = groupNumberFilterField.getText().trim();
        loadData(lastNameFilter, groupNumberFilter); // Применяем фильтры
    }

    private void addStudent() {
        AddStudentDialog dialog = new AddStudentDialog(this, groupId);
        dialog.setVisible(true);

        Student newStudent = dialog.getStudent();
        if (newStudent != null) {
            studentDAO.addStudent(newStudent);
            loadData(null, null); // Обновляем таблицу без фильтрации
        }
    }

    private void editStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите студента для редактирования", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);
        String firstName = (String) tableModel.getValueAt(selectedRow, 1);
        String lastName = (String) tableModel.getValueAt(selectedRow, 2);
        String middleName = (String) tableModel.getValueAt(selectedRow, 3);
        LocalDate birthDate = LocalDate.parse(tableModel.getValueAt(selectedRow, 4).toString());
        Long groupId = (Long) tableModel.getValueAt(selectedRow, 5);

        Student selectedStudent = new Student(id, firstName, lastName, middleName, birthDate, groupId);
        EditStudentDialog dialog = new EditStudentDialog(this, selectedStudent);
        dialog.setVisible(true);

        Student updatedStudent = dialog.getUpdatedStudent(selectedStudent);
        if (updatedStudent != null) {
            studentDAO.updateStudent(updatedStudent);
            loadData(null, null); // Обновляем таблицу без фильтрации
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            studentDAO.deleteStudent(id);
            loadData(null, null); // Обновляем таблицу без фильтрации
        }
    }
}