package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AddStudentDialog extends JDialog {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField middleNameField;
    private final JTextField birthDateField;
    private final Long groupId;
    private boolean isConfirmed = false;

    public AddStudentDialog(JFrame parent, Long groupId) {
        super(parent, "Добавить студента", true);
        this.groupId = groupId;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Имя:"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Фамилия:"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("Отчество:"));
        middleNameField = new JTextField();
        inputPanel.add(middleNameField);

        inputPanel.add(new JLabel("Дата рождения (гггг-мм-дд):"));
        birthDateField = new JTextField();
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
                setVisible(false); // Закрываем диалог
            }
        });
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public Student getStudent() {
        if (!isConfirmed) {
            return null;
        }
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        LocalDate birthDate = LocalDate.parse(birthDateField.getText().trim());
        return new Student(null, firstName, lastName, middleName, birthDate, groupId);
    }
}