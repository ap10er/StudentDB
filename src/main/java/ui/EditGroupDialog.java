package ui;

import model.Group;

import javax.swing.*;
import java.awt.*;

public class EditGroupDialog extends JDialog {
    private final JTextField groupNumberField;
    private final JTextField facultyNameField;
    private boolean isConfirmed = false;

    public EditGroupDialog(JFrame parent, Group group) {
        super(parent, "Редактировать группу", true); // Модальное окно
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Номер группы:"));
        groupNumberField = new JTextField(group.getGroupNumber());
        inputPanel.add(groupNumberField);

        inputPanel.add(new JLabel("Факультет:"));
        facultyNameField = new JTextField(group.getFacultyName());
        inputPanel.add(facultyNameField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("ОК");
        JButton cancelButton = new JButton("Отменить");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            isConfirmed = true;
            setVisible(false);

        });

        cancelButton.addActionListener(e -> {
            isConfirmed = false;
            setVisible(false);
        });
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public Group getUpdatedGroup(Group originalGroup) {
        if (!isConfirmed) {
            return null;
        }
        String updatedGroupNumber = groupNumberField.getText().trim();
        String updatedFacultyName = facultyNameField.getText().trim();
        return new Group(originalGroup.getId(), updatedGroupNumber, updatedFacultyName);
    }
}