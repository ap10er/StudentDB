package ui;

import model.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGroupDialog extends JDialog {
    private final JTextField groupNumberField;
    private final JTextField facultyNameField;
    private boolean isConfirmed = false;

    public AddGroupDialog(JFrame parent) {
        super(parent, "Добавить группу", true); // Модальное окно
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Номер группы:"));
        groupNumberField = new JTextField();
        inputPanel.add(groupNumberField);

        inputPanel.add(new JLabel("Факультет:"));
        facultyNameField = new JTextField();
        inputPanel.add(facultyNameField);

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
                setVisible(false);
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

    public Group getGroup() {
        if (!isConfirmed) {
            return null;
        }
        String groupNumber = groupNumberField.getText().trim();
        String facultyName = facultyNameField.getText().trim();
        return new Group(null, groupNumber, facultyName);
    }
}