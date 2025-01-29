package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EditStudentDialog extends JDialog {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField middleNameField;
    private final JTextField birthDateField;
    private boolean isConfirmed = false;

    public EditStudentDialog(JFrame parent, Student student) {
        super(parent, "Редактировать студента", true); // Модальное окно
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Имя:"));
        firstNameField = new JTextField(student.getFirstName());
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Фамилия:"));
        lastNameField = new JTextField(student.getLastName());
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("Отчество:"));
        middleNameField = new JTextField(student.getMiddleName());
        inputPanel.add(middleNameField);

        inputPanel.add(new JLabel("Дата рождения (гггг-мм-дд):"));
        birthDateField = new JTextField(student.getBirthDate().toString());
        inputPanel.add(birthDateField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("ОК");
        JButton cancelButton = new JButton("Отменить");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConfirmed = true;
                setVisible(false); // Закрываем диалог
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConfirmed = false;
                setVisible(false);
            }
        });
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public Student getUpdatedStudent(Student originalStudent) {
        if (!isConfirmed) {
            return null;
        }
        String updatedFirstName = firstNameField.getText().trim();
        String updatedLastName = lastNameField.getText().trim();
        String updatedMiddleName = middleNameField.getText().trim();
        LocalDate updatedBirthDate = LocalDate.parse(birthDateField.getText().trim());
        return new Student(
                originalStudent.getId(),
                updatedFirstName,
                updatedLastName,
                updatedMiddleName,
                updatedBirthDate,
                originalStudent.getGroupId()
        );
    }
}