package sub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExcelGUI extends JFrame{

    // read
    private JLabel inputRead;
    private JButton buttonRead;
    private JButton buttonFileRead;
    // write
    private JLabel inputWrite;
    private JButton buttonWrite;
    private JButton buttonFileWrite;
    // exit
    private JButton buttonExit;
    //
    private Container container;
    //
    private Excel excel;
    private ArrayList<double[]> arrayList;

    public ExcelGUI(){
        super("Apache");
        this.setBounds(500,400,700,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // read
        buttonRead = new JButton("Импорт");
        buttonFileRead = new JButton("Выбрать файл");
        inputRead = new JLabel("Путь к файлу");
        // write
        buttonWrite = new JButton("Экспорт");
        buttonFileWrite = new JButton("Сохранить файл");
        inputWrite = new JLabel("Путь к файлу");
        // exit
        buttonExit = new JButton("Выход");

        container = this.getContentPane();
        container.setLayout(new GridLayout(3,3));
        // read
        buttonRead.addActionListener(new ButtonReadEventListener());
        container.add(buttonRead);
        buttonFileRead.addActionListener(new ButtonChooseReadFileEventListener());
        container.add(buttonFileRead);
        container.add(inputRead);
        // write
        buttonWrite.addActionListener(new ButtonWriteEventListener());
        container.add(buttonWrite);
        buttonFileWrite.addActionListener(new ButtonChooseWriteFileEventListener());
        container.add(buttonFileWrite);
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
            try{ // разбить на две ошибки: calc & writeExc
                String linkToWrite = inputWrite.getText();
                ArrayList<LinkedHashMap<String, Double>> mapList = new ArrayList<LinkedHashMap<String, Double>>();
                for(int i=0; i<arrayList.size(); i++){
                    Calc calc = new Calc(arrayList.get(i), arrayList.get((i+1)%arrayList.size()));
                    mapList.add(calc.getHashMap());
                }
                try{
                    excel.writeExcel(mapList, linkToWrite);
                    String message = "Данные экспортированы в "+linkToWrite;
                    JOptionPane.showMessageDialog(null, message,"Вывод",JOptionPane.PLAIN_MESSAGE);
                } catch(Exception exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(),"Вывод",JOptionPane.PLAIN_MESSAGE);
                }
            } catch(Exception exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(),"Вывод",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class ButtonChooseReadFileEventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                JFileChooser fileChooser = new JFileChooser("D:/_Mehi/6sem/INF/web/src/");
                int ret = fileChooser.showDialog(null, "Выбрать файл");
                if(ret == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    inputRead.setText(file.getAbsolutePath());
                }
            } catch(Exception exception){
                JOptionPane.showMessageDialog(null, exception.getMessage(),"Вывод",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    class ButtonChooseWriteFileEventListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                JFileChooser fileChooser = new JFileChooser("D:/_Mehi/6sem/INF/web/src/");
                int ret = fileChooser.showDialog(null, "Сохранить файл");
                if(ret == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    inputWrite.setText(file.getAbsolutePath());
                }
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
