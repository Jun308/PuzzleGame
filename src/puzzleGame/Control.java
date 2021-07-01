package puzzleGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Control {
    
    private PuzzleGUI pz;
    private int size = 0, x, y;
    private int count = 0;
    private JButton[][] matrix;
    private Timer timer;
    private int time;
    private boolean isStart = false;
    
    public Control(PuzzleGUI pz) {
        this.pz = pz;
        addButton();
        
    }
    
    public void addButton() {
        //size=pz.cbxSize
        String txt = pz.cbxSize.getSelectedItem().toString();
        String[] s = txt.split("x");
        size = Integer.parseInt(s[0]);
        // moi hang co may nut, cach nhau bn
        pz.pnLayout.setLayout(new GridLayout(size, size, 10, 10));
        // set dai rong tung button
        pz.pnLayout.setPreferredSize(new Dimension(size * 100, size * 100));
        pz.pnLayout.removeAll();
        matrix = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton(i * size + j + 1 + "");
                matrix[i][j] = btn;
                pz.pnLayout.add(btn);
                //click button 
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (isStart) {
                            if (checkMove(btn)) {
                                moveButton(btn);
                                if (count == 1) {
                                    countTime();
                                }
                                if (checkWin()) {
                                    timer.stop();
                                    JOptionPane.showMessageDialog(pz, "you won");
                                    isStart = false;
                                }
                            }
                        } else {
                            
                            JOptionPane.showMessageDialog(null, "Press chose sizw & new Game to start");
                        }
                        
                    }
                });
            }
        }
        matrix[size - 1][size - 1].setText("");
        mixButton();
        //co size lai
        pz.pack();
    }
    
    public void mixButton() {
        x = size - 1;
        y = size - 1;
        for (int k = 0; k < size * 10; k++) {
            // khai bao tao do poin   
            Random r = new Random();//
            int choice = r.nextInt(4);
            String txt = "";
            switch (choice) {
                case 0://doi button tren
                    //????

                    if (x > 0) {
                        txt = matrix[x - 1][y].getText();
                        matrix[x][y].setText(txt);
                        matrix[x - 1][y].setText("");
                        x = x - 1;
                    }
                    break;
                case 1://duoi
                    if (x < size - 1) {
                        txt = matrix[x + 1][y].getText();
                        matrix[x][y].setText(txt);
                        matrix[x + 1][y].setText("");
                        x = x + 1;
                    }
                    break;
                case 2://trai
                    if (y > 0) {
                        txt = matrix[x][y - 1].getText();
                        matrix[x][y].setText(txt);
                        matrix[x][y - 1].setText("");
                        y = y - 1;
                    }
                    break;
                case 3://phai
                    if (y < size - 1) {
                        txt = matrix[x][y + 1].getText();
                        matrix[x][y].setText(txt);
                        matrix[x][y + 1].setText("");
                        y = y + 1;
                    }
                    break;
            }
        }
    }

    //tim vi tri cua button dc click
    public Point getPosClick(JButton btn) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].getText().equals(btn.getText())) {
                    return new Point(i, j);
                }
            }
        }
        return null;
        
    }

    //check xem button click vao btn co doi dc voi button rong ko
    public boolean checkMove(JButton btn) {
        Point p = getPosClick(btn);
        if (p.x == x && Math.abs(p.y - y) == 1) {
            return true;
        }
        if (p.y == y && Math.abs(p.x - x) == 1) {
            return true;
        }
        return false;
        
    }

    public void moveButton(JButton btn) {
        Point p = getPosClick(btn);
        String txt = btn.getText();
        matrix[x][y].setText(txt);
        x = p.x;
        y = p.y;
        btn.setText("");
        count++;
        pz.lblCountMove.setText(count + "");
        
        
    }

    public void countTime() {
        if (isStart) {
            timer.stop();
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                pz.lblCountTime.setText(time + "");
            }
        });
        timer.start();
        isStart = true;
        
    }

    public boolean checkWin() {
        if (matrix[size - 1][size - 1].getText().equals("")) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == size - 1 && j == size - 1) {
                        return true;
                    }
                    if (!matrix[i][j].getText().equals(i * size + j + 1 + "")) {
                        return false;
                    }
                }
            }
            return true;
        }
        // ---------------------------------------------
        return false;
        
    }

    public void newGame() {
        pz.lblCountMove.setText("0");
        pz.lblCountTime.setText("0");
        count = 0;
        time = 0;
        addButton();
        countTime();
        timer.stop();
        isStart = true;
    }
}
