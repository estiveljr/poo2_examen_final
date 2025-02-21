package view;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import control.ZooService;
import model.entity.Animal;
import model.entity.Enclos;


//Voici comment le principe de Responsabilité Unique (SRP) est appliqué dans le projet:

/*
1. ZooDatabaseManager
- Responsabilité unique: Gérer la connexion et les opérations de base de données
- Ne contient pas de logique métier

2. ZooService
- Fait le lien entre la couche présentation et la couche données
- Délègue les opérations de base de données au ZooDatabaseManager

3. Animal/Enclos/Soigneur (Entités)
- Responsabilité unique: Représenter les données d'un objet spécifique
- Contiennent uniquement leurs attributs et getters/setters
- Pas de logique métier ni d'accès aux données

4. MainView
- Responsabilité unique: Gérer l'interface utilisateur
- S'occupe uniquement de l'affichage et des interactions utilisateur
- Délègue le traitement au ZooService
*/

public class MainView {
    private JFrame frame;
    private ZooService zooService;
    private JTable animalTable;
    private JTable enclosTable;
    private DefaultTableModel animalModel;
    private DefaultTableModel enclosModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainView window = new MainView();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainView() {
        zooService = new ZooService();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestion du Zoo");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Create main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Animals Panel
        JPanel animalsPanel = createAnimalsPanel();
        tabbedPane.addTab("Animaux", animalsPanel);
        
        // Enclos Panel
        JPanel enclosPanel = createEnclosPanel();
        tabbedPane.addTab("Enclos", enclosPanel);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createAnimalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table
        String[] animalColumns = {"ID", "Nom", "Espèce", "Age", "Régime Alimentaire"};
        animalModel = new DefaultTableModel(animalColumns, 0);
        animalTable = new JTable(animalModel);
        JScrollPane scrollPane = new JScrollPane(animalTable);
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton refreshButton = new JButton("Afficher");
        
        // Ajout d'une fonctionnalité de recherche
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        JComboBox<String> searchCriteria = new JComboBox<>(new String[] {
            "Nom", "Espèce", "Régime alimentaire"
        });

        searchPanel.add(new JLabel("Rechercher par:"));
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim().toLowerCase();
            String criteria = (String) searchCriteria.getSelectedItem();
            
            animalModel.setRowCount(0);
            List<Animal> animaux = zooService.listerAnimaux();
            
            for (Animal animal : animaux) {
                boolean match = false;
                
                if (criteria.equals("Nom")) {
                    match = animal.getNom().toLowerCase().contains(searchTerm);
                } else if (criteria.equals("Espèce")) {
                    match = animal.getEspece().toLowerCase().contains(searchTerm);
                } else if (criteria.equals("Régime alimentaire")) {
                    match = animal.getRegimeAlimentaire().toLowerCase().contains(searchTerm);
                }
                
                if (match || searchTerm.isEmpty()) {
                    animalModel.addRow(new Object[]{
                        animal.getId(),
                        animal.getNom(),
                        animal.getEspece(),
                        animal.getAge(),
                        animal.getRegimeAlimentaire()
                    });
                }
            }
        });

        // Ajouter le panel de recherche au-dessus des boutons
        buttonsPanel.add(searchPanel, BorderLayout.NORTH);
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);


        // Add action listeners
        addButton.addActionListener(e -> showAddAnimalDialog());
        editButton.addActionListener(e -> editSelectedAnimal());
        deleteButton.addActionListener(e -> deleteSelectedAnimal());
        refreshButton.addActionListener(e -> refreshAnimalsList());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createEnclosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table
        String[] enclosColumns = {"ID", "Nom", "Capacité", "Type Habitat"};
        enclosModel = new DefaultTableModel(enclosColumns, 0);
        enclosTable = new JTable(enclosModel);
        JScrollPane scrollPane = new JScrollPane(enclosTable);
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton refreshButton = new JButton("Afficher");
        
//        buttonsPanel.add(addButton);
//        buttonsPanel.add(editButton);
//        buttonsPanel.add(deleteButton);
        buttonsPanel.add(refreshButton);

        // Add action listeners
//        addButton.addActionListener(e -> showAddEnclosDialog());
//        editButton.addActionListener(e -> editSelectedEnclos());
        deleteButton.addActionListener(e -> deleteSelectedEnclos());
        refreshButton.addActionListener(e -> refreshEnclosList());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void showAddAnimalDialog() {
        JDialog dialog = new JDialog(frame, "Ajouter un animal", true);
        dialog.getContentPane().setLayout(new GridLayout(6, 2));
        
        JTextField nomField = new JTextField();
        JTextField especeField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField regimeField = new JTextField();
        
        dialog.getContentPane().add(new JLabel("Nom:"));
        dialog.getContentPane().add(nomField);
        dialog.getContentPane().add(new JLabel("Espèce:"));
        dialog.getContentPane().add(especeField);
        dialog.getContentPane().add(new JLabel("Age:"));
        dialog.getContentPane().add(ageField);
        dialog.getContentPane().add(new JLabel("Régime Alimentaire:"));
        dialog.getContentPane().add(regimeField);
        
        JButton saveButton = new JButton("Sauvegarder");
        saveButton.addActionListener(e -> {
            try {
                Animal animal = new Animal(
                    0,
                    nomField.getText(),
                    especeField.getText(),
                    Integer.parseInt(ageField.getText()),
                    regimeField.getText()
                );
                zooService.ajouterAnimal(animal);
                refreshAnimalsList();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Age invalide!");
            }
        });
        
        dialog.getContentPane().add(saveButton);
        dialog.getContentPane().add(new JButton("Annuler"));
        
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void editSelectedAnimal() {
        int selectedRow = animalTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(animalModel.getValueAt(selectedRow, 0).toString());
            Animal animal = zooService.rechercherAnimal(id);
            
            if (animal != null) {
                JDialog dialog = new JDialog(frame, "Modifier un animal", true);
                dialog.setLayout(new GridLayout(6, 2));
                
                JTextField nomField = new JTextField(animal.getNom());
                JTextField especeField = new JTextField(animal.getEspece());
                JTextField ageField = new JTextField(String.valueOf(animal.getAge()));
                JTextField regimeField = new JTextField(animal.getRegimeAlimentaire());
                
                dialog.add(new JLabel("Nom:"));
                dialog.add(nomField);
                dialog.add(new JLabel("Espèce:"));
                dialog.add(especeField);
                dialog.add(new JLabel("Age:"));
                dialog.add(ageField);
                dialog.add(new JLabel("Régime Alimentaire:"));
                dialog.add(regimeField);
                
                JButton saveButton = new JButton("Sauvegarder");
                saveButton.addActionListener(e -> {
                    try {
                        Animal updatedAnimal = new Animal(
                            animal.getId(),
                            nomField.getText(),
                            especeField.getText(),
                            Integer.parseInt(ageField.getText()),
                            regimeField.getText()
                        );
                        zooService.modifierAnimal(updatedAnimal);
                        refreshAnimalsList();
                        dialog.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Age invalide!");
                    }
                });
                
                JButton cancelButton = new JButton("Annuler");
                cancelButton.addActionListener(e -> dialog.dispose());
                
                dialog.add(saveButton);
                dialog.add(cancelButton);
                
                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        }
    }


    private void deleteSelectedAnimal() {
        int selectedRow = animalTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = Integer.parseInt(animalModel.getValueAt(selectedRow, 0).toString());
            Animal animal = zooService.rechercherAnimal(id);
            if (animal != null) {
                zooService.supprimerAnimal(animal);
                refreshAnimalsList();
            }
        }
    }

    private void deleteSelectedEnclos() {
        int selectedRow = enclosTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Implementation for deleting selected enclos
        }
    }

    private void refreshAnimalsList() {
        animalModel.setRowCount(0);
        for (Animal animal : zooService.listerAnimaux()) {
            animalModel.addRow(new Object[]{
                animal.getId(),
                animal.getNom(),
                animal.getEspece(),
                animal.getAge(),
                animal.getRegimeAlimentaire()
            });
        }
    }

    private void refreshEnclosList() {
        enclosModel.setRowCount(0);
        for (Enclos enclos : zooService.listerEnclos()) {
            enclosModel.addRow(new Object[]{
                enclos.getId(),
                enclos.getNom(),
                enclos.getCapacite(),
                enclos.getTypeHabitat()
            });
        }
    }
}
