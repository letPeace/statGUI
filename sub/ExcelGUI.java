package sub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExcelGUI extends JFrame{

    // read
    private JTextField inputRead;
    private JButton buttonRead;
    // write
    private JTextField inputWrite;
    private JButton buttonWrite;
    // exit
    private JButton buttonExit;
    //
    private Container container;
    //
    private Excel excel;
    private ArrayList<double[]> arrayList;

    public ExcelGUI(){
        super("Apache");
        this.setBounds(500,400,500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // read
        inputRead = new JTextField();
        buttonRead = new JButton("Импорт");
        // write
        inputWrite = new JTextField();
        buttonWrite = new JButton("Экспорт");
        // exit
        buttonExit = new JButton("Выход");

        container = this.getContentPane();
        container.setLayout(new GridLayout(3,2));
        // read
        buttonRead.addActionListener(new ButtonReadEventListener());
        container.add(buttonRead);
        container.add(inputRead);
        // write
        buttonWrite.addActionListener(new ButtonWriteEventListener());
        container.add(buttonWrite);
        container.add(inputWrite);
        // exit
        buttonExit.addActionListener(new ButtonExitEventListener());
        container.add(buttonExit);

        excel = new Excel();
        arrayList = new ArrayList<double[]>();

    }

    class ButtonReadEventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                String linkToRead = inputRead.getText();
                arrayList = excel.readExcel(linkToRead);
                String message = "Данные импортированы из "+linkToRead;
                JOptionPane.showMessageDialog(null, message,"Вывод",JOptionPane.PLAIN_MESSAGE);
            } catch(Exception exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(),"Вывод",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class ButtonWriteEventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                String linkToWrite = inputWrite.getText();
                ArrayList<LinkedHashMap<String, Double>> mapList = new ArrayList<LinkedHashMap<String, Double>>();
                for(int i=0; i<arrayList.size(); i++){
                    Calc calc = new Calc(arrayList.get(i), arrayList.get((i+1)%arrayList.size()));
                    mapList.add(calc.getHashMap());
                }
                excel.writeExcel(mapList, linkToWrite);
                String message = "Данные экспортированы в "+linkToWrite;
                JOptionPane.showMessageDialog(null, message,"Вывод",JOptionPane.PLAIN_MESSAGE);
            } catch(Exception exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(),"Вывод",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class ButtonExitEventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
        }
    }

}
